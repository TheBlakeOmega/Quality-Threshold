package data;

/**
 * The discrete realization of Item.
 */
public class DiscreteItem extends Item {
	
	/**
	 * Package constructor that uses the constructor of superclass Item.
	 * @param attribute initialization
	 * @param value initialization
	 */
	DiscreteItem(Attribute attribute, Object value) {
		super(attribute, value);
	}

	/**
	 * Computes the distance between current Item and parameter a.
	 * @param a is the object on wich compute distance with current Item
	 * @return 0 if getValue.equals(a) is true, 1 otherwise
	 */
	double distance(Object a) {
		if (getValue().equals(a)) {
			return 0;
		} else {
			return 1;
		}
	}
}
