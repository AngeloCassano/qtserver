package data;

import java.io.Serializable;

/**
 * La classe astratta Item modella un generico item (coppia attributo-valore).
 */
abstract class Item implements Serializable {

	/** Identificatore di versione universale per Serializable. */
	private static final long serialVersionUID = 1L;

	/** attributo contenuto nell'item. */
	Attribute attribute;

	/** valore assegnato all'attributo. */
	Object value;

	/**
	 * Il metodo Item() inizializza i valori dei membri attributi.
	 *
	 * @param attribute attributo nell'item
	 * @param value     valore dell'attributo
	 */
	Item(Attribute attribute, Object value) {
		this.attribute = attribute;
		this.value = value;
	}

	/**
	 * Il metodo getAttribute() restituisce l'attributo contenuto nell'item.
	 *
	 * @return attributo nell'item
	 */
	Attribute getAttribute() {
		return attribute;
	}

	/**
	 * Il metodo getValue() restituisce il valore dell'attributo contenuto nell'item.
	 *
	 * @return valore dell'attributo
	 */
	Object getValue() {
		return value;
	}

	/**
	 * Il metodo toString() restituisce la stringa contenente il valore
	 * dell'attributo.
	 *
	 * @return valore attributo
	 */
	@Override
	public String toString() {
		return (String) value;
	}

	/**
	 * Il metodo distance() restituisce la distanza tra l'attributo dell'item e l'oggetto a.
	 *
	 * @param a valore da confrontare
	 * @return distanza
	 */
	abstract double distance(Object a);
}
