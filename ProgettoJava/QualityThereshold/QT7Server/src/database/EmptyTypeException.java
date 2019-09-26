package database;

/**
 * The empty type exception class.
 */
public class EmptyTypeException extends Exception {

	/**
	 * Public constructor of the exception; it prints on
	 * System.err if connection to SQL database fails.
	 */
	public EmptyTypeException() {
		System.err.println("Error type of field ");
	}
}
