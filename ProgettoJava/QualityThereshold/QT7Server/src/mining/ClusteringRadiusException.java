package mining;

/**
 * An exception that occurs when the result of the clustering
 * algorithm is one single cluster.
 * This occurs when the radius is too large.
 */
public class ClusteringRadiusException extends Exception {

	/**
	 * Public constructor of the exception; it prints on
	 * System.err there are too much tuples in a single cluster.
	 */
	public ClusteringRadiusException() {
		System.err.println("more than 14 tuples in one cluster!");
	}

}
