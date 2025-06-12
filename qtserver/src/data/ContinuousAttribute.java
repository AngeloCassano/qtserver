package data;

import java.io.Serializable;

/**
 * La classe ContinuousAttribute modella un attributo continuo di una tabella
 * nell'intervallo [min,max], estende la classe Attribute e implementa
 * l'interfaccia Serializable.
 * 
 */
class ContinuousAttribute extends Attribute implements Serializable {

	/** Identificatore di versione universale per Serializable. */
	private static final long serialVersionUID = 1L;

	/**
	 * max è il massimo estremo che può assumere l'attributo nel dominio.
	 */
	private double max;

	/**
	 * min è il minimo estremo che può assumere l'attributo nel dominio.
	 */
	private double min;

	/**
	 * Il metodo ContinuousAttribute() invoca il costruttore della classe madre,
	 * inizializzando i membri aggiunti per estensione. Questo metodo ha come
	 * parametri il nome, l'identificativo numerico, valore minimo e massimo
	 * dell'attributo.
	 *
	 * @param name  nome dell'attributo
	 * @param index identificativo numerico dell'attributo
	 * @param min   valore minimo dell'attributo
	 * @param max   valore massimo dell'attributo
	 */
	ContinuousAttribute(String name, int index, double min, double max) {
		super(name, index);
		this.min = min;
		this.max = max;
	}

	/**
	 * Il metodo getScaledValue() calcola e restituisce il valore normalizzato del
	 * parametro v passato in input. La normalizzazione ha come codominio
	 * l'intervallo [0,1].
	 *
	 * @param v valore da normalizzare
	 * @return valore normalizzato di v
	 */
	double getScaledValue(double v) {
		double scV = (v - min) / (max - min);
		return scV;
	}
}
