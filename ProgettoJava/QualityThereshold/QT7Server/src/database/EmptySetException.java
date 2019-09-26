package database;

/**
 * The empty set exception class.
 */
public class EmptySetException extends Exception {
	
	/**
	 * Public constructor of the exception; it prints on
	 * System.err when there are a return of an empty resultset.
	 */
	public EmptySetException() {
		System.err.println("Empty ResultSet! ");
	}

}
