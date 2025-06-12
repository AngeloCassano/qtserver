package data;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import database.DatabaseConnectionException;
import database.DbAccess;
import database.EmptySetException;
import database.Example;
import database.NoValueException;
import database.QUERY_TYPE;
import database.TableData;
import database.TableSchema;

/**
 * La classe Data modella l'insieme delle transazioni (o tuple).
 */
public class Data implements Serializable {

	/** Identificatore di versione universale per Serializable. */
	private static final long serialVersionUID = 1L;

	/**
	 * La lista data è una lista collegata di oggetti Example che rappresentano
	 * delle transazioni.
	 */
	private List<Example> data = new LinkedList<Example>();

	/** numberOfExamples indica la cardinalità dell’insieme di transazioni */
	private int numberOfExamples;

	/** explanatory è la lista collegata degli attributi. */
	private List<Attribute> explanatorySet = new LinkedList<Attribute>();

	/**
	 * Il costruttore della classe Data carica i dati di addestramento da una
	 * tabella della base di dati, utilizzando il parametro tableName (nome della
	 * tabella).
	 *
	 * @param tableName nome della tabella
	 * @throws SQLException          eccezione SQL
	 * @throws EmptyDatasetException eccezione dataset vuoto
	 */
	public Data(String tableName) throws SQLException, EmptyDatasetException {
		DbAccess db = new DbAccess();
		try {
			db.initConnection();
		} catch (DatabaseConnectionException e1) {
			e1.printStackTrace();
		}
		TableData tabData = new TableData(db);
		TableSchema tabSchema = new TableSchema(db, tableName);
		if (tabSchema.getNumberOfAttributes() == 0) {
			throw new EmptyDatasetException();
		}
		for (int i = 0; i < tabSchema.getNumberOfAttributes(); i++) {
			if (tabSchema.getColumn(i).isNumber()) {
				ContinuousAttribute citem = null;
				try {
					citem = new ContinuousAttribute(tabSchema.getColumn(i).getColumnName(), i,
							((Float) tabData.getAggregateColumnValue(tableName, tabSchema.getColumn(i), QUERY_TYPE.MIN))
									.doubleValue(),
							((Float) tabData.getAggregateColumnValue(tableName, tabSchema.getColumn(i), QUERY_TYPE.MAX))
									.doubleValue());
				} catch (NoValueException e) {
					e.printStackTrace();
				}
				explanatorySet.add(citem);
			} else {
				Set<Object> values = tabData.getDistinctColumnValues(tableName, tabSchema.getColumn(i));
				Iterator<Object> it = values.iterator();
				Set<String> val = new TreeSet<String>();
				while (it.hasNext()) {
					val.add((String) it.next());
				}
				DiscreteAttribute ditem = new DiscreteAttribute(tabSchema.getColumn(i).getColumnName(), i,
						(TreeSet<String>) val);
				explanatorySet.add(ditem);
			}

		}
		try {
			List<Example> examples = tabData.getDistinctTransazioni(tableName);
			numberOfExamples = examples.size();
			for (int i = 0; i < numberOfExamples; i++) {
				data.add(examples.get(i));
			}
		} catch (EmptySetException e) {
			System.out.println("Insieme transazioni vuoto!");
		}
		db.closeConnection();
	}

	/**
	 * Il metodo getNumberOfExample() restituisce la cardinalità dell'insieme di
	 * transazioni
	 *
	 * @return numero di transazioni
	 */
	public int getNumberOfExamples() {
		return numberOfExamples;
	}

	/**
	 * Il metodo getNumberOfAttributes() restituisce la cardinalita' dell'insieme
	 * degli attributi
	 *
	 * @return dimensione di explanatorySet
	 */
	public int getNumberOfAttributes() {
		return explanatorySet.size();
	}

	/**
	 * Il metodo getAttributeValue() restituisce data[exampleIndex][attributeIndex].
	 * Questo metodo ha come parametri di input: indice di riga, indice di colonna
	 * della matrice in data, e restituisce il valore assunto in data dall'attributo
	 * relativo ai corrispondenti indici riga e colonna.
	 *
	 * @param exampleIndex   indice di riga
	 * @param attributeIndex indice di colonna della matrice in data
	 * @return valore dell'attributo
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex) {
		return data.get(exampleIndex).get(attributeIndex);
	}

	/**
	 * Il metodo getAttribute() restituisce l'attributo relativo all'indice.
	 *
	 * @param index indice
	 * @return attributo
	 */
	public Attribute getAttribute(int index) {
		//
		return explanatorySet.get(index);

	}

	/**
	 * Il metodo getItemSet() restituisce un riferimento all'oggetto di Tuple che
	 * modella la i-esima riga in data come sequenza di coppie attributo-valore.
	 *
	 * @param index indice di riga
	 * @return tuple
	 */
	public Tuple getItemSet(int index) {
		Tuple tuple = new Tuple(explanatorySet.size());
		for (int i = 0; i < explanatorySet.size(); i++) {
			if (explanatorySet.get(i) instanceof DiscreteAttribute) {
				tuple.add(new DiscreteItem((DiscreteAttribute) explanatorySet.get(i), (String) data.get(index).get(i)),
						i);
			}
			if (explanatorySet.get(i) instanceof ContinuousAttribute) {
				tuple.add(new ContinuousItem((ContinuousAttribute) explanatorySet.get(i),
						(Double) data.get(index).get(i)), i);
			}
		}
		return tuple;
	}

	/**
	 * Il metodo toString() stampa la stringa che rappresenta la tabella data.
	 *
	 * @return stringa che rapprsenta la tabella data
	 */
	@Override
	public String toString() {
		String s = new String();
		s = "";
		int i, j;
		for (j = 0; j < explanatorySet.size(); j++) {
			s = s + explanatorySet.get(j) + ", ";
		}
		s = s + "\n";
		for (i = 0; i < numberOfExamples; i++) {
			s = s + (i + 1) + ": ";
			for (j = 0; j < explanatorySet.size(); j++) {
				s = s + this.getAttributeValue(i, j) + ", ";
			}
			s = s + "\n";
		}
		return s;
	}
}
