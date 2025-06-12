package data;


/**
 * La classe ContinuousItem modella una coppia attributo continuo - valore
 * numerico ed estende la classe Item.
 */
class ContinuousItem extends Item {

	/** Identificatore di versione universale per Serializable. */
	private static final long serialVersionUID = 1L;

	/**
	 * Il metodo ContinuousItem() richiama il costruttore della superclasse.
	 *
	 * @param attribute attributo continuo
	 * @param value     valore numerico
	 */
	ContinuousItem(Attribute attribute, Double value) {
		super(attribute, value);
	}

	/**
	 * Il metodo distance() determina la distanza (in valore assoluto) tra il valore
	 * scalato memorizzato nell'item corrente e quello scalato associato al
	 * parametro a, utilizzando il metodo getScaledValue.
	 *
	 * @param a valore scalato
	 * @return distanza
	 */
	@Override
	double distance(Object a) {
		double dist;
		ContinuousItem aci = (ContinuousItem) a;
		dist = Math.abs((((ContinuousAttribute) super.attribute).getScaledValue((Double) super.value))
				- ((ContinuousAttribute) aci.attribute).getScaledValue((Double) aci.getValue()));
		return dist;
	}

	/**
	 * Il metodo toString() stampa il valore numerico contenuto all'interno del  ContinuousItem.
	 *
	 * @return valore numerico
	 */
	public String toString() {
		return Double.toString((Double) value);

	}

}
