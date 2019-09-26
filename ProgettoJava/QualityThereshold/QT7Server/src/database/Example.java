package database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The example tuple class.
 */
public class Example implements Comparable<Example>, Serializable {
	
	/**
	 * A list of Objects that models a transition from database.
	 */
	private List<Object> example;
	
	/**
	 * Public Constructor.
	 */
	public Example() {
		example = new ArrayList<Object>();
	}
	
	/**
	 * Add object 'o' to example.
	 * @param o is the object to add
	 */
	public void add(Object o) {
		example.add(o);
	}
	
	/**
	 * Return Object in position i on the example.
	 * @param i is the index of the object to return
	 * @return return the object in position i from example
	 */
	public Object get(int i) {
		return example.get(i);
	}
	
	/**
	 * Implements method compareTo() of Comparable.
	 * @param ex is the example to compare with current example
	 * @return 0 if current Example and ex are equals, otherwise compareTo()
	 * 		   method of Objects in the same position of the examples 
	 */
	public int compareTo(Example ex) {
		int i = 0;
		for (Object o : ex.example) {
			if (!o.equals(this.example.get(i))) {
				return ((Comparable) o).compareTo(example.get(i));
			}
			i++;
		}
		return 0;
	}
	
	/**
	 * Overrides Object's toString.
	 * @return the string made from concatenation of example's objects' toString()
	 */
	public String toString() {
		String str = "";
		for (Object o : example) {
			str += o.toString() + " ";
		}
		return str;
	}
}