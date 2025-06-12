package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * La classe TableSchema modella lo schema di una tabella nel database relazionale.
 */
public class TableSchema {
	
	/** db oggetto DbAccess che gestisce la connessione ad una base di dati. */
	@SuppressWarnings("unused")
	private DbAccess db;

	/**
	 * L'inner class Column modella una colonna della tabella..
	 */
	public class Column {
		
		/** name: nome della colonna. */
		private String name;
		
		/** type: stringa che indica il tipo della colonna. */
		private String type;

		/**
		 * Assegna un nome e il tipo dei valori della colonna.
		 *
		 * @param name nome della colonna
		 * @param type tipo della colonna
		 */
		private Column(String name, String type) {
			this.name = name;
			this.type = type;
		}

		/**
		 * Restituisce il nome della colonna
		 *
		 * @return nome della colonna
		 */
		public String getColumnName() {
			return name;
		}

		/**
		 * Verifica se il tipo della colonna è numerico.
		 *
		 * @return vero se il tipo è numerico, falso altrimenti
		 */
		public boolean isNumber() {
			return type.equals("number");
		}

		/**
		 * Restituisce una stringa che rappresenta la colonna indicandone il nome e il tipo.
		 *
		 * @return stringa che rappresenta la colonna
		 */
		public String toString() {
			return name + ":" + type;
		}
	}

	/**  Lista contenente le colonne della tabella.. */
	private List<Column> tableSchema = new LinkedList<Column>();

	/**
	 * Ricava dal DB lo schema della tabella prelevenadone nome e tipo delle sue colonne.
	 *
	 * @param db oggetto che permette l'accesso al database 
	 * @param tableName nome della tabella da cui ricavare lo schema 
	 * @throws SQLException sollevata in caso di errore durante l'accesso al database
	 */
	public TableSchema(DbAccess db, String tableName) throws SQLException {
		this.db = db;
		HashMap<String, String> mapSQL_JAVATypes = new HashMap<String, String>();
		// http://java.sun.com/j2se/1.3/docs/guide/jdbc/getstart/mapping.html
		mapSQL_JAVATypes.put("CHAR", "string");
		mapSQL_JAVATypes.put("VARCHAR", "string");
		mapSQL_JAVATypes.put("LONGVARCHAR", "string");
		mapSQL_JAVATypes.put("BIT", "string");
		mapSQL_JAVATypes.put("SHORT", "number");
		mapSQL_JAVATypes.put("INT", "number");
		mapSQL_JAVATypes.put("LONG", "number");
		mapSQL_JAVATypes.put("FLOAT", "number");
		mapSQL_JAVATypes.put("DOUBLE", "number");

		Connection con = db.getConnection();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getColumns(null, null, tableName, null);

		while (res.next()) {

			if (mapSQL_JAVATypes.containsKey(res.getString("TYPE_NAME")))
				tableSchema.add(
						new Column(res.getString("COLUMN_NAME"), mapSQL_JAVATypes.get(res.getString("TYPE_NAME"))));
		}
		res.close();
	}

	/**
	 * Restituisce il numero degli attributi
	 *
	 * @return numero degli attributi
	 */
	public int getNumberOfAttributes() {
		return tableSchema.size();
	}

	/**
	 * Restituisce la colonna di indicd index
	 *
	 * @param index indice della colonna
	 * @return colonna di indice index
	 */
	public Column getColumn(int index) {
		return tableSchema.get(index);
	}
}