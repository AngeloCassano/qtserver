package database;

/**
 * La classe DatabaseConnectionException estende Exception e viene sollevata in
 * caso di fallimento nella connessione al database.
 */
public class DatabaseConnectionException extends Exception {

	/** Identificatore di versione universale per Serializable. */
	private static final long serialVersionUID = 1L;

	/**
	 * Il costruttore DatabaseConnectionException stampa un messaggio di errore
	 * usando il metodo della superclasse.
	 *
	 * @param string connessione fallita
	 */
	DatabaseConnectionException(String string) {
		super(string);
	}
}
