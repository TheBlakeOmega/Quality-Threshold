package database;

/**
 * The no value exception class.
 */
public class NoValueException extends Exception {

	/**
	 * Public constructor of the exception; it prints on
	 * System.err if there are not a value in a resultset.
	 * @param string to print during the Exception
	 */
	public NoValueException(String string) {
		System.err.println(string + "founded");
	}
}
