
package data;

import java.io.Serializable;

/**
 * La classe Attribute modella l'entità attributo e implementa l'interfaccia
 * Serializable.
 */

public abstract class Attribute implements Serializable {

	/** Identificatore di versione universale per Serializable. */
	private static final long serialVersionUID = 1L;

	/** Nome simbolico dell'attributo. */
	private String name = "";

	/** Identificativo numerico dell'attributo. */
	private int index;

	/**
	 * Il metodo Attribute() prende in input il nome e l'identificativo numerico,
	 * inizializzando i valori degli attributi name ed index.
	 *
	 * @param name  nome simbolico dell'attributo
	 * @param index identificativo numerico dell'attributo
	 */
	Attribute(String name, int index) {
		this.name = name;
		this.index = index;
	}

	/**
	 * Il metodo getName() restituisce il nome dell'attributo.
	 *
	 * @return nome attributo
	 */
	String getName() {
		return this.name;
	}

	/**
	 * Il metodo getIndex() Restituisce l'indice dell'attributo.
	 *
	 * @return indice dell'attributo.
	 */
	int getIndex() {
		return this.index;
	}

	/**
	 * Il metodo toString() restituisce la stringa name che rappresenta lo stato.
	 * dell'oggetto.
	 *
	 * @return name
	 */
	@Override
	public String toString() {
		return this.name;
	}
}
