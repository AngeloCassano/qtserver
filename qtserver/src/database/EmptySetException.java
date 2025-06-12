package database;

/**
 * La classe EmptySetException estende Exception e viene sollevata in caso di
 * restituzione di un resultset vuoto.
 */

public class EmptySetException extends Exception {

	/** Identificatore di versione universale per Serializable. */
	private static final long serialVersionUID = 1L;

	/**
	 * EmptySetException è il costruttore della classe e usa il metodo della classe
	 * Exception per stampare un messaggio di errore custom.
	 */

	public EmptySetException() {
		super("insieme di training vuoto");
	}

}