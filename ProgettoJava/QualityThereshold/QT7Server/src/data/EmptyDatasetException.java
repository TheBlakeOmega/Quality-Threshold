package data;

/**
 * An exception that occurs when the DataSet has no entries.
 */
public class EmptyDatasetException extends Exception {

	/**
	 * Public constructor of the exception; it prints on
	 * System.err if the database that you want to use is empty.
	 */
	public EmptyDatasetException() {
		System.err.println("Empty dataset");
	}
}
