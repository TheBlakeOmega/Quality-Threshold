package data;

/**
 * The continuous realization of Item.
 */
public class ContinuousItem extends Item {

	/**
	 * Package constructor that uses the constructor of superclass Item.
	 * @param attribute to initialization
	 * @param value to initialization
	 */
	ContinuousItem(Attribute attribute, double value) {
		super(attribute, value);
	}

	/**
	 * Computes the distance between the scaled value of the current Item
	 * and the scaled value of associated at the parameter a.
	 * @param a is an Object on which compute distance
	 * @return distance between current value and parameter a
	 */
	double distance(Object a) {

		double currVal = ((ContinuousAttribute) this.getAttribute()).getScaledValue((Double) this.getValue());

		double aVal = ((ContinuousAttribute) this.getAttribute()).getScaledValue((Double) a);

		return Math.abs(currVal - aVal);
	}
	
}
