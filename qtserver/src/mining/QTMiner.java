package mining;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;

import data.Data;
import data.Tuple;

/**
 * La classe QTMiner include l'implementazione dell'algoritmo di clustering
 * QT(Quality Threshold).
 */
public class QTMiner implements Serializable {

	/** Identificatore di versione universale per Serializable. */
	private static final long serialVersionUID = 1L;

	/** ClusterSet C (insieme di clusters). */
	private ClusterSet C;

	/** Valore del raggio da usare per raggruppare le tuple in clusters. */
	private double radius;

	/**
	 * Il metodo QTMiner() crea un oggetto C di tipo ClusterSet (TreeSet di tipo
	 * Cluster) e inizializza radius con il valore del parametro passato come input.
	 *
	 * @param radius raggio dei cluster
	 */
	public QTMiner(double radius) {
		C = new ClusterSet();
		this.radius = radius;
	}

	/**
	 * Il metodo QTMiner() apre il file identificato da fileName, legge l'oggetto
	 * ivi memorizzato e lo assegna a C. Questo metodo ha come paramentro di input:
	 * 'percorso + nome file'.
	 *
	 * @param fileName nome del file
	 * @throws FileNotFoundException  eccezione file non trovato
	 * @throws IOException            eccezione I/O
	 * @throws ClassNotFoundException eccezione classe non trovata
	 */
	public QTMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
		QTMiner qt = (QTMiner) in.readObject();
		this.C = qt.C;
		this.radius = qt.radius;
		in.close();
	}

	/**
	 * Il metodo salva(), apre il file identificato da fileName (se non esiste ne
	 * crea uno con il nome specificato) e salva l'oggetto riferito da C in tale
	 * file. Questo metodo ha come paramentro di input: 'percorso + nome file'.
	 *
	 * @param fileName nome del file
	 * @throws IOException           eccezione I/O
	 * @throws FileNotFoundException eccezione file non trovato
	 */
	public void salva(String fileName) throws IOException, FileNotFoundException {
		FileOutputStream file;
		ObjectOutputStream out;
		try {
			file = new FileOutputStream(fileName);
			try {
				out = new ObjectOutputStream(file);
				out.writeObject(this);
				out.close();
			} catch (IOException e) {
				throw new IOException();
			}
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException();
		}
	}

	/**
	 * Il metodo getC() restituisce un ClusterSet C.
	 *
	 * @return ClusterSet C
	 */
	public ClusterSet getC() {
		return C;
	}

	/**
	 * Il metodo compute() esegue l’algoritmo QT. Al primo passo costruisce un
	 * cluster per ogni tupla di data non clusterizzata includendo nel cluster i
	 * punti che ricadono nell'intorno sferico della tupla avente raggio radius. Al
	 * secondo passo l'algoritmo salva il candidato cluster più popoloso e rimuove
	 * tutti punti del cluster dall'elenco delle tuple di data ancora da
	 * clusterizzare. L'algoritmo viene ripetuto fino ad esaurimento tuple.
	 *
	 * Il metodo compute() lancia l'eccezione ClusteringRadiusException. L'oggetto
	 * eccezione viene creato quando tutte le tuple sono raggruppate in un unico
	 * cluster.
	 *
	 * @param data 						 insieme di tuple
	 * @return numclusters				 numero dei cluster
	 * @throws ClusteringRadiusException l'eccezione lanciata in caso di unico
	 *                                   cluster e causata da un raggio maggiore
	 *                                   della massima distanza possibile
	 */
	public int compute(Data data) throws ClusteringRadiusException {
		int numclusters = 0;
		boolean isClustered[] = new boolean[data.getNumberOfExamples()];
		for (int i = 0; i < isClustered.length; i++) {
			isClustered[i] = false;
		}
		int countClustered = 0;
		while (countClustered != data.getNumberOfExamples()) {
			Cluster c = buildCandidateCluster(data, isClustered);
			C.add(c);
			numclusters++;
			for (Iterator<Integer> clusteredTupleId = c.iterator(); clusteredTupleId.hasNext();) {
				isClustered[clusteredTupleId.next()] = true;
			}
			countClustered += c.getSize();
		}
		if (numclusters == 1)
			throw new ClusteringRadiusException();
		return numclusters;
	}

	/**
	 * Il metodo buildCandidateCluster() costruisce un cluster per ogni tupla di
	 * data non ancora clusterizzata in un cluster di C. Inoltre questo metodo
	 * restituisce il cluster candidato più popoloso, prendendo come input l'insieme
	 * di tuple da raggruppare il cluster, l'informazione booleana sullo stato di
	 * clusterizzazione di una tupla e restituisce il cluser. L'informazione
	 * booleana isClustered è falsa se non è ancora assegnata ad alcun cluster di C
	 * la tupla i-esima di data, è vera altrimenti.
	 *
	 * @param data        insieme di tuple da raggruppare in cluster
	 * @param isClustered stato clusterizzazione di una tupla
	 * @return cluster
	 */
	private Cluster buildCandidateCluster(Data data, boolean isClustered[]) {
		Cluster candidateC = null, cl = null;
		int size = 0;
		for (int i = 0; i < isClustered.length; i++) {
			if (isClustered[i] == false) {
				Tuple t = data.getItemSet(i);
				cl = new Cluster(t);
				cl.addData(i);
				for (int j = 0; j < isClustered.length; j++) {
					if ((isClustered[j] == false) && (j != i)) {
						Tuple t2 = data.getItemSet(j);
						double dist = cl.getCentroid().getDistance(t2);
						if (Double.compare(dist, radius) < 1) {
							cl.addData(j);
						}
					}
				}
				if (cl.getSize() > size) {
					candidateC = cl;
					size = cl.getSize();
				}
			}
		}
		return candidateC;
	}

	/**
	 * Il metodo toString() richiama il toString di ClusterSet.
	 *
	 * @return stampa dei cluster
	 */
	@Override
	public String toString() {
		String str = (C.toString());
		return str;
	}

}
