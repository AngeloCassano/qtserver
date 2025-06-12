package mining;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import data.Data;
import data.Tuple;

// TODO: Auto-generated Javadoc
/**
 * La classe Cluster modella un cluster. La classe Cluster implementa Comparable
 * e Serializable
 */
class Cluster implements Comparable<Cluster>, Serializable {

	/** Identificatore di versione universale per Serializable. */
	private static final long serialVersionUID = 1L;

	/** Tupla individuata come centroide del cluster. */
	private Tuple centroid;

	/**
	 * Dati clusterizzati (altre tuple costituenti,insieme al centroide, il
	 * cluster).
	 */
	private Set<Integer> clusteredData = new HashSet<Integer>();

	/**
	 * Il costruttore di Cluster() inizializza il centroide della classe
	 * avvalorandolo con quello passato in input.
	 *
	 * @param centroid centroide
	 */
	Cluster(Tuple centroid) {
		this.centroid = centroid;
	}

	/**
	 * Il metodo getCentroid() restituisce il centroide del cluster.
	 *
	 * @return centroide
	 */
	Tuple getCentroid() {
		return centroid;
	}

	/**
	 * Il metodo addData() restituisce true se la tupla sta cambiando il cluster,
	 * false altrimenti
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	boolean addData(int id) {
		return clusteredData.add(id);
	}

	/**
	 * Il metodo contain() verifica se una transazione è clusterizzata nell'array
	 * corrente.
	 *
	 * @param id indice della transazione
	 * @return true, se contenuta, false altrimenti
	 */
	boolean contain(int id) {
		return clusteredData.contains(id);
	}

	/**
	 * Il metodo removeTuple() rimuove il gruppo che ha cambiato il cluster.
	 *
	 * @param id indice della tupla da rimuovere
	 */
	void removeTuple(int id) {
		clusteredData.remove(id);
	}

	/**
	 * Il metodo getSize() restituisce la dimensione di clusteredData.
	 *
	 * @return dimensione
	 */
	int getSize() {
		return clusteredData.size();
	}

	/**
	 * Il metodo Iterator() restituisce un iteratore che opera su clusteredData.
	 *
	 * @return l'oggetto iteratore
	 */
	public Iterator<Integer> iterator() {
		return clusteredData.iterator();
	}

	/**
	 * Il metodo toString() stampa gli elementi del centroide.
	 *
	 * @return stampa del centroide
	 */
	@Override
	public String toString() {
		String str = "Centroid=(";
		for (int i = 0; i < centroid.getLength(); i++) {
			str += centroid.get(i);
		}
		str += ")";
		return str;
	}

	/**
	 * Il metodo toString() stampa gli elementi del database considerando la media
	 * delle distanze.
	 *
	 * @param data database
	 * @return stringa
	 */
	public String toString(Data data) {
		String str = "Centroid=(";
		for (int i = 0; i < centroid.getLength(); i++) {
			str += centroid.get(i) + " ";
		}
		str += ")\nExamples:\n";
		for (Iterator<Integer> it = clusteredData.iterator(); it.hasNext();) {
			Integer riga = it.next();
			str += "[";
			for (int j = 0; j < data.getNumberOfAttributes(); j++) {
				str += data.getAttributeValue(riga, j) + " ";
			}
			str += "] dist=" + getCentroid().getDistance(data.getItemSet(riga)) + "\n";
		}
		str += "\nAvgDistance=" + getCentroid().avgDistance(data, clusteredData);
		return str;
	}

	/**
	 * Il metodo compareTo confronta due cluster, restituisce 1 se sono uguali, -1
	 * se sono diversi, 0 in caso di confronto non a buon fine.
	 * 
	 * @param c cluster
	 * @return intero compreso fra -1 e 1
	 */
	@Override
	public int compareTo(Cluster c) {
		if (c.getSize() == this.getSize())
			return 1;
		else if (c.getSize() != this.getSize())
			return -1;
		return 0;
	}
}
