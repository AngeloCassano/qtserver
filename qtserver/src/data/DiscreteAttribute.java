package data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * La classe DiscreteAttribute rappresenta un attributo discreto, estende la
 * classe Attribute e implementa le interfacce Iterable e Serializable.
 */
class DiscreteAttribute extends Attribute implements Iterable<String>, Serializable {

	/** Identificatore di versione universale per Serializable. */
	private static final long serialVersionUID = 1L;

	/**
	 * values è l'insieme dei valori distinti contennuti nell'attributo discreto.
	 */
	private TreeSet<String> values;

	/**
	 * Il metodo DiscreteAttribute() invoca il costruttore della classe madre e
	 * inizializza il membro values  con il parametro in input.
	 *
	 * @param name   nome dell'attributo
	 * @param index  indice dell'attributo
	 * @param values insieme di stringhe rappresentati i valori dell'attributo discreto
	 */
	DiscreteAttribute(String name, int index, TreeSet<String> values) {
		super(name, index);
		this.values = values;
	}

	/**
	 * Il metodo getNumberOfDistinctValues() restituisce la dimensione di values.
	 *
	 * @return dimensione di values
	 */
	int getNumberOfDistinctValues() {
		return values.size();
	}

	/**
	 * Il metodo Iterator() restituisce un iteratore che opera sull'insieme di
	 * values.
	 *
	 * @return the iterator
	 */
	// controlla
	public Iterator<String> iterator() {
		return values.iterator();

	}

}
