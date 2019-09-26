package mining;

import data.Data;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * A set of clusters.
 */
public class ClusterSet implements Iterable<Cluster>, Serializable {

	/**
	 * A Set of clusters.
	 */
	private Set<Cluster> C = new TreeSet<Cluster>();

	/**
	 * Public constructor.
	 */
	public ClusterSet() {
		C = new TreeSet<Cluster>();
	}

	/**
	 * Adds a cluster to C.
	 * @param c the cluster that will be added to C
	 */
	void add(Cluster c) {
		C.add(c);
	}

	/**
	 * Overrides Object's toString.
	 * @return a string with every centroid of the Set C
	 */
	public String toString() {
		String output = "";
		int j = 1;
		for (Cluster it : C) {
			output += j + ": " + it + "\n";
			j++;
		}
		return output;
	}

	/**
	 * Overrides Object's toString.
	 * @param data is the table from what create cluster
	 * @return a string with every centroid of the Set C
	 */
	public String toString(Data data) {
		String output = "";
		int i = 0;
		Iterator<Cluster> CIterator = C.iterator();
		while (CIterator.hasNext()) {
			output += (i + 1) + ":" + CIterator.next().toString(data) + "\n";
			i++;
		}
		return output;
	}

	/**
	 * Implements iterator() from Iterable by using Set.iterator().
	 * @return an instance of Iterator
	 */
	@Override public Iterator<Cluster> iterator() {
		return C.iterator();
	}

}
