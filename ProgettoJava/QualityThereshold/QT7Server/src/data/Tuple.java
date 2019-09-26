package data;

import java.io.Serializable;
import java.util.Set;

/**
 * Sequence of items.
 */
public class Tuple implements Serializable {

	/**
	 * Tuple is a vector of Item that represents a sequence 
	 * of couple attribute-value.
	 */
	private Item[] tuple;

	/**
	 * Public constructor.
	 * @param size of the vector of Item
	 */
	public Tuple(int size) {
		this.tuple = new Item[size];
	}

	/**
	 * Return the length of the vector tuple.
	 * @return the length of the vector tuple
	 */
	public int getLength() {
		return tuple.length;
	}

	/**
	 * Return the Item on the index i of the vector tuple.
	 * @param i index of the Item that you want to get
	 * @return Item on the index i of the vector tuple
	 */
	public Item get(int i) {
		return tuple[i];
	}

	/**
	 * Add a new Item in the vector tuple.
	 * @param c id the new Item to add
	 * @param i is the index in which add the new Item
	 */
	public void add(Item c, int i) {
		tuple[i] = c;
	}


	/**
	 * Computes distance between the current tuple and the tuple obj. The distance is the sum 
	 * of the distance between the Items in the same position in tuples.
	 * @param obj is the external tuple
	 * @return distance between the current tuple and the tuple obj
	 */
	public double getDistance(Tuple obj) {
		double dist = 0.0;
		for (int i = 0; i < obj.getLength(); i++) {
			if (tuple[i].getAttribute().getName().equals(obj.get(i).getAttribute().getName())) {
				dist += this.get(i).distance(obj.get(i).getValue());
			}
		}
		return dist;
	}

	/**
	 * Return the average of the distances between the current tuple and those obtainable 
	 * from the lines of data with index content in clusteredData.
	 * @param data is the table on which computes the distance with the current tuple
	 * @param clusteredData is a set of Integer from which get the indexes of tuples in data
	 * @return distance between data and current Tuple
	 */
	public double avgDistance(Data data, Set<Integer> clusteredData) {
		double p = 0.0;
		double sumD = 0.0;
		for (Integer it : clusteredData) {
			double d = getDistance(data.getItemSet(it));
			sumD += d;
		}
		p = sumD / clusteredData.size();
		return p;
	}

	/**
	 * Overrides Object's toString.
	 * @return the name of every attribute in the tuple
	 */	
	@Override public String toString() {
		String str = " ";
		for (int i = 0; i < this.getLength(); i++) {
			str += this.get(i) + " ";
		}
		return str;
	}

}

