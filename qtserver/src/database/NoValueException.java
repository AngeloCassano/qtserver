package database;

/**
 * La classe NoValueException estende Exception e rappresenta l'assenza di un
 * valore all'interno di un resultset.
 */
public class NoValueException extends Exception {

	/** Identificatore di versione universale per Serializable. */
	private static final long serialVersionUID = 1L;

	/**
	 * Il metodo NoValueException viene chiamato quando manca un valore in un
	 * resultset.
	 *
	 * @param s stringa
	 */
	NoValueException(String s) {
		super(s);
	}
}
