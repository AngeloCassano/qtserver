package data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

/**
 * La classe Tuple rappresenta una tupla come sequenza di coppie
 * attributo-valore.
 */
public class Tuple implements Serializable {

	/** Identificatore di versione universale per Serializable. */
	private static final long serialVersionUID = 1L;

	/** Tuple: array di item . */
	private Item[] tuple;

	/**
	 * Il metodo Tuple() costruisce un nuovo oggetto riferito da tuple, ha come
	 * input il numero di item che costituiranno l'array di tuple.
	 * 
	 * @param size numero di item
	 */
	Tuple(int size) {
		this.tuple = new Item[size];
	}

	/**
	 * Il metodo getLength() la dimensione dell'array tuple.
	 *
	 * @return dimensione di tuple 
	 */
	public int getLength() {
		return this.tuple.length;
	}

	/**
	 * Il metodo get() restituisce l'item alla i-esima posizione.
	 *
	 * @param i posizione item
	 * @return item alla posizione i
	 */
	public Item get(int i) {
		return tuple[i];
	}

	/**
	 * Il metodo add() memorizza un item c nell'iesima posizione di tuple.
	 *
	 * @param c elemento da memorizzare
	 * @param i posizione i
	 */
	void add(Item c, int i) {
		tuple[i] = c;
	}

	/**
	 * Il metodo getDistance() determina la distanza tra la tupla riferita da obj e
	 * la tupla corrente. La distanza è ottenuta come somma delle distanze tra item
	 * in posizioni eguali nelle due tuple.
	 *
	 * @param obj tupla
	 * @return distanza tra obj e tupla corrente
	 */
	public double getDistance(Tuple obj) {
		double sum = 0;
		for (int i = 0; i < tuple.length; i++) {
			if (tuple[i] instanceof DiscreteItem) {
				DiscreteItem d = (DiscreteItem) tuple[i];
				sum = sum + d.distance(obj.get(i));
			}
			if (tuple[i] instanceof ContinuousItem) {
				ContinuousItem d = (ContinuousItem) tuple[i];
				sum = sum + d.distance(obj.get(i));
			}
		}
		return sum;
	}

	/**
	 * Il metodo avgDistance() restituisce la media delle distanze tra la tupla
	 * corrente e quelle ottenibili dalle righe della matrice in data aventi indice
	 * in clusteredData.
	 *
	 * @param data   insieme di tuple con cui calcolare la distanza media
	 * @param clusteredData insieme degli indici di riga di data
	 * @return media delle distanze
	 */
	public double avgDistance(Data data, Set<Integer> clusteredData) {
		double p = 0.0, sumD = 0.0;
		for (Iterator<Integer> it = clusteredData.iterator(); it.hasNext();) {
			double d = getDistance(data.getItemSet(it.next()));
			sumD += d;
		}
		p = sumD / clusteredData.size();
		return p;
	}

	/**
	 * Il metodo toString() stampa i valori contenuti in tuple.
	 *
	 * @return valori contenuti in tuple
	 */
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < this.tuple.length; i++) {
			str = (this.get(i).toString());
		}
		return str;
	}
}
