package data;

import java.util.Iterator;
import java.util.Set;

/**
 * Attribute characterized by a discrete value.
 */
public class DiscreteAttribute extends Attribute implements Iterable<String> {
	
	/**
	 * Set of values for the continuous attribute.
	 */
	private Set<String> values;

	/**
	 * Package constructor.
	 * @param name of the Discrete Attribute
	 * @param index of the Discrete Attribute
	 * @param values is a Set of values for attribute's domain
	 */
	DiscreteAttribute(String name, int index, Set<String> values) {
		super(name, index);
		this.values = values;
	}

	/**
	 * Return size of the set "values".
	 * @return size of the set "values"
	 */
	int getNumberOfDistinctValues() {
		return values.size();
	}

	/**
	 * Return an iterator from the set "values".
	 * @return an iterator from the set "values"
	 */
	@Override public Iterator<String> iterator() {
		return values.iterator();
	}
}
