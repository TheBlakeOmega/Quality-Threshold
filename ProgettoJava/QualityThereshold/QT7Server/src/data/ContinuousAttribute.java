package data;

/**
 * Attribute characterized by a real value.
 */
public class ContinuousAttribute extends Attribute {

	/**
	 * Max limit of the domain.
	 */
	private double max;
	
	/**
	 * Min limit of the domain.
	 */
	private double min;


	/**
	 * Package constructor.
	 * @param name of the Continuous Attribute
	 * @param index of the Continuous Attribute
	 * @param min is the max limit of the domain
	 * @param max is the max limit of the domain
	 */
	ContinuousAttribute(String name, int index, double min, double max) {
		super(name, index);
		this.min = min;
		this.max = max;
	}

	/**
	 * Computes and returns a normalization of the input into a domain [0,1].
	 * @param v is values that will be normalized
	 * @return values normalized
	 */
	double getScaledValue(double v) {
		double V;
		V = (v - min) / (max - min);
		return V;
	}
}


