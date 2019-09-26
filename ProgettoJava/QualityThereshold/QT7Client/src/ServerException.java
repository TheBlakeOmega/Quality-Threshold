
/**
 * The server exception class.
 */
public class ServerException extends Exception {

	/**
	 * Public constructor of the exception; it prints on
	 * System.err for every error with the Server connection.
	 * @param result is the massage that exception print on err
	 */
	public ServerException(String result) {
		System.err.println(result);
	}

}
