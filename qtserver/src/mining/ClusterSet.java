package mining;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import data.Data;

/**
 * La classe ClusterSet rappresenta un insieme di cluster determinati da QT. La
 * classe ClusterSet implementa le interfacce Iterable e Serializable.
 * 
 */
class ClusterSet implements Iterable<Cluster>, Serializable {

	/** Identificatore di versione universale per Serializable. */
	private static final long serialVersionUID = 1L;

	/** ClusterSet C (TreeSet di oggetti di tipo Cluster). */
	private Set<Cluster> C = new TreeSet<Cluster>();

	/**
	 * Il metodo add() aggiunge un nuovo cluster c al ClusterSet C.
	 *
	 * @param c cluster da aggiungere
	 */
	void add(Cluster c) {
		C.add(c);
	}

	/**
	 * Il metodo toString() restituisce una stringa fatta da ciascun centroide
	 * dell'insieme dei cluster.
	 *
	 * @return stringa contenente i centroidi
	 */
	@Override
	public String toString() {
		//

		int i = 1;
		String str = "";
		for (Iterator<Cluster> it = C.iterator(); it.hasNext();) {
			str += i + ":" + it.next() + "\n";
			i++;
		}
		return str;
	}

	/**
	 * Il metodo toString(Data data) restituisce una stringa che descrive lo stato
	 * di ciascun cluster in C.
	 *
	 * @param data insieme di tuple da raggruppare in cluster
	 * @return stato cluster
	 */
	public String toString(Data data) {
		Iterator<Cluster> e = C.iterator();
		String str = "";
		int i = 1;
		while (e.hasNext()) {
			str += i + ":" + e.next().toString(data) + "\n";
			i++;
		}
		return str;
	}

	/**
	 * Il metodo Iterator() restituisce un iteratore che opera sul ClusterSet C. 
	 *
	 * @return iteratore
	 */
	@Override
	public Iterator<Cluster> iterator() {
		return C.iterator();
	}

}