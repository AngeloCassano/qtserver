package data;


/**
 * La classe EmptyDatasetException modella una eccezione controllata quando il dataset è vuoto. 
 */
public class EmptyDatasetException extends Exception {

	/** Identificatore di versione universale per Serializable. */
	private static final long serialVersionUID = 1L;
	
	/** Messaggio eccezione EmptyDatasetException. */
	private String message = "Il dataset fornito è vuoto!";

	/**
	 * L'eccezione EmptyDatasetException controlla se un dataset è vuoto.
	 */
	public EmptyDatasetException() {
	};

	/**
	 * Il metodo getMessage() stampa il messaggio dell'eccezione EmptyDatasetException.
	 *
	 * @return message: messaggio eccezione
	 */
	public String getMessage() {
		return message;
	}

}
