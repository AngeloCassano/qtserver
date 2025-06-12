package database;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La classe DbAccess contiene le informazioni e le operazioni necessarie alla
 * gestione dell'accesso al DB per la lettura dei dati di training.
 */
public class DbAccess {

	/* ATTRIBUTI */

	/** L'attributo DRIVER_CLASS_NAME contiene il Driver da utilizzare. */

	private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";

	/**
	 * L'attributo DBMS contiene la stringa che indica il tipo di DBMS a cui
	 * connettersi.
	 */
	private static final String DBMS = "jdbc:mysql";

	/**
	 * L'attributo SERVER contiene l'identificativo del server su cui risiede la
	 * base di dati.
	 */
	private final String SERVER = "localhost";

	/**
	 * L'attributo PORT contiene la porta su cui il DBMS MySQL accetta le
	 * connessioni.
	 */
	private int PORT = 3306;

	/** L'attributo DATABASE contiene il nome della base di dati. */
	private final String DATABASE = "MapDB";

	/**
	 * L'attributo USER_ID contiene il nome dell'utente per l'accesso alla base di
	 * dati.
	 */
	private final String USER_ID = "MapUser";

	/**
	 * L'attributo PASSWORD contiene la password di autenticazione per l’utente
	 * USER_ID.
	 */
	private final String PASSWORD = "map";

	/** L'attributo conn gestisce una connessione al database. */
	private Connection conn;

	/* METODI */
	/**
	 * Effettua la connessione al DB verificando che le informazioni per la
	 * connessione siano corrette.
	 * 
	 *
	 * @throws DatabaseConnectionException eccezione connessione al database
	 */
	public void initConnection() throws DatabaseConnectionException {

		String connectionString = DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE
				+ "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=CET";
		try {

			try {
				Class.forName(DRIVER_CLASS_NAME).getDeclaredConstructor().newInstance();
			} catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException
					| SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IllegalAccessException e) {

			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		} catch (InstantiationException e) {

			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		} catch (ClassNotFoundException e) {
			System.out.println("Impossibile trovare il Driver: " + DRIVER_CLASS_NAME);
			throw new DatabaseConnectionException(e.toString());
		}

		try {
			conn = DriverManager.getConnection(connectionString, USER_ID, PASSWORD);

		} catch (SQLException e) {
			System.out.println("Impossibile connettersi al DB");
			e.printStackTrace();
			throw new DatabaseConnectionException(e.toString());
		}

	}

	/**
	 * Il metodo getConnection() restituisce una connessione 'conn' al database.
	 *
	 * @return connessione
	 */
	public Connection getConnection() {
		return conn;
	}

	/**
	 * Il metodo closeConnection() chiude la connessione  al database.
	 *
	 * @throws SQLException eccezione SQL
	 */
	public void closeConnection() throws SQLException {
		conn.close();
	}

}
