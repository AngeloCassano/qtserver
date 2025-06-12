package data;

import java.io.Serializable;

/**
 * The Class DiscreteItem estende la classe Item e implementa l'interfaccia
 * Serializable.
 */
class DiscreteItem extends Item implements Serializable {

	/** Identificatore di versione universale per Serializable. */
	private static final long serialVersionUID = 1L;

	/**
	 * Il metodo DiscreteItem() crea l'istanza di un nuovo attributo discreto.
	 *
	 * @param attribute attributo discreto
	 * @param value     valore attributo
	 */
	DiscreteItem(DiscreteAttribute attribute, String value) {
		super(attribute, value);
	}

	/**
	 * Il metodo distance() restituisce la distanza, che può essere 0 se l'elemento
	 * da valutare è uguale ad a, altrimenti 1.
	 *
	 * @param a valore da confrontare
	 * @return distanza
	 */
	@Override
	double distance(Object a) {
		double dist;
		String s = (String) super.getValue();
		String s2 = (String) ((DiscreteItem) a).getValue();
		if (s.equals(s2)) {
			dist = 0;
		} else {
			dist = 1;
		}
		return dist;
	}
}
