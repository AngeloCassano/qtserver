package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import database.TableSchema.Column;

/**
 * The Class TableData modella l’insieme di transazioni collezionate in una
 * tabella.
 * 
 */
public class TableData {

	/** db attributo che rappresenta la connessione ad un database */
	private DbAccess db;

	/**
	 * Il metodo TableData() prende il riferimento ad un DbAccess e lo memorizza in
	 * db.
	 *
	 * @param db oggetto DbAccess
	 */
	public TableData(DbAccess db) {
		this.db = db;
	}

	/**
	 * Il metodo getDistinctTransazioni() restituisce le transazioni distinte
	 * contenute nella tabella table . Il metodo getDistinctTransazioni() solleva
	 * l'eccezione SQLException se esistono errori nella esecuzione della query, o
	 * solleva EmptySetException se il resultset è vuoto.
	 *
	 * @param table nome della tabella
	 * @return set transazioni
	 * @throws SQLException      eccezione SQL
	 * @throws EmptySetException eccezione resultset vuoto
	 */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException {
		LinkedList<Example> transSet = new LinkedList<Example>();
		Statement statement;
		TableSchema tSchema = new TableSchema(db, table);

		String query = "select distinct ";

		for (int i = 0; i < tSchema.getNumberOfAttributes(); i++) {
			Column c = tSchema.getColumn(i);
			if (i > 0)
				query += ",";
			query += c.getColumnName();
		}
		if (tSchema.getNumberOfAttributes() == 0)
			throw new SQLException();
		query += (" FROM " + table);

		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		boolean empty = true;
		while (rs.next()) {
			empty = false;
			Example currentTuple = new Example();
			for (int i = 0; i < tSchema.getNumberOfAttributes(); i++)
				if (tSchema.getColumn(i).isNumber())
					currentTuple.add(rs.getDouble(i + 1));
				else
					currentTuple.add(rs.getString(i + 1));
			transSet.add(currentTuple);
		}
		rs.close();
		statement.close();
		if (empty)
			throw new EmptySetException();

		return transSet;

	}

	/**
	 * Il metodo getDistinctColumnValues() formula ed esegue una interrogazione SQL
	 * per estrarre i valori distinti ordinati di column e popolare un insieme da
	 * restituire. 
	 *
	 * 
	 * @param table  nome della tabella
	 * @param column nome colonna della tabella
	 * @return valori distinti della colonna
	 * @throws SQLException eccezione SQL
	 */
	public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException {
		Set<Object> valueSet = new TreeSet<Object>();
		Statement statement;
		@SuppressWarnings("unused")
		TableSchema tSchema = new TableSchema(db, table);

		String query = "select distinct ";

		query += column.getColumnName();

		query += (" FROM " + table);

		query += (" ORDER BY " + column.getColumnName());

		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			if (column.isNumber())
				valueSet.add(rs.getDouble(1));
			else
				valueSet.add(rs.getString(1));

		}
		rs.close();
		statement.close();
		return valueSet;
	}

	/**
	 * 
	 * Il metodo getAggregateColumnValue() formula ed esegue una interrogazione SQL
	 * per estrarre il minimo o massimo valore aggregato cercato nella colonna di
	 * nome column della tabella di nome table. Questo
	 * metodo solleva e propaga l'eccezione NoValueException se il resultset è vuoto
	 * o se il valore calcolato è pari a null.
	 * 
	 * 
	 * @param table     nome della tabella
	 * @param column    nome della colonna
	 * @param aggregate operatore SQL di aggregazione
	 * @return aggregato cercato relativo al valore column
	 * @throws SQLException     eccezione SQL
	 * @throws NoValueException eccezione valore vuoto
	 */
	public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate)
			throws SQLException, NoValueException {
		Statement statement;
		@SuppressWarnings("unused")
		TableSchema tSchema = new TableSchema(db, table);
		Object value = null;
		String aggregateOp = "";

		String query = "select ";
		if (aggregate == QUERY_TYPE.MAX)
			aggregateOp += "max";
		else
			aggregateOp += "min";
		query += aggregateOp + "(" + column.getColumnName() + ") FROM " + table;

		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		if (rs.next()) {
			if (column.isNumber())
				value = rs.getFloat(1);
			else
				value = rs.getString(1);
		}
		rs.close();
		statement.close();
		if (value == null)
			throw new NoValueException("No " + aggregateOp + " on " + column.getColumnName());
		return value;

	}

}
