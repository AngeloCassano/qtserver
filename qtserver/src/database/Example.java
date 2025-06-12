package database;

import java.util.LinkedList;
import java.util.List;

/**
 * La classe Example modella una transazione letta dalla base di dati. 
 */
public class Example implements Comparable<Example> {
	
	/**  Lista di transazioni memorizzate nella tabella. */
	private List<Object> example = new LinkedList<Object>(); 

	/**
	 * Il metodo add() aggiunge un elemento alla lista example.
	 *
	 * @param o oggetto
	 */
	void add(Object o) {
		example.add(o);
	}

	/**
	 * Il metodo get() restituisce l'elemento di example alla posizione i.
	 *
	 * @param i indice
	 * @return oggetto
	 */
	public Object get(int i) {
		return example.get(i);
	}

	/**
	 * Il metodo compareTo() confronta l'oggetto o con gli elementi di example.
	 *
	 * @param ex example
	 * @return indice
	 */
	//controlla e modifica
	public int compareTo(Example ex) {

		int i = 0;
		for (Object o : ex.example) {
			if (!o.equals(this.example.get(i)))
				return ((Comparable) o).compareTo(example.get(i));
			i++;
		}
		return 0;
	}

	/**
	 * Il metodo toString() stampa gli elementi di example.
	 *
	 * @return the string
	 */
	//controlla e modifica
	public String toString() {
		String str = "";
		for (Object o : example)
			str += o.toString() + " ";
		return str;
	}

}