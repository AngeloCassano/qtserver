package mining;

/**
 * La classe ClusteringRadiusException modella una eccezione chiamata quando
 * viene generato un solo cluster.
 * 
 */
public class ClusteringRadiusException extends Exception {

	/** Identificatore di versione universale per Serializable. */
	private static final long serialVersionUID = 1L;

	/** Messaggio ClusteringRadiusException */
	private String message = "Tutte le tuple sono state raggruppate in un unico cluster!";

	/**
	 * Costruttore di default ClusteringRadiusException.
	 */
	public ClusteringRadiusException() {

	}

	/**
	 * Il metodo getMessage() restituisce il messaggio dell'eccezione.
	 *
	 * @return message: messaggio eccezione
	 */

	public String getMessage() {
		return message;
	}
}
