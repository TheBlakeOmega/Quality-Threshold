package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The database access class.
 */
public class DbAccess {
	
	/**
	 * Driver used to get connection with SQL.
	 */
	private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
	
	/**
	 * The protocol used.
	 */
	private static final String DBMS = "jdbc:mysql";
	
	/**
	 * The identifier of the server of the database.
	 */
	private static final String SERVER = "localhost";
	
	/**
	 * The name of the database.
	 */
	private static final String DATABASE = "MapDB";
	
	/**
	 * The port on which DBMS MySQL accepts connections.
	 */
	private static final String PORT = "3306";
	
	/**
	 * The login user name.
	 */
	private static final String USER_ID = "MapUser";
	
	/**
	 * The login password for the user 'MapUser'.
	 */
	private static final String PASSWORD = "map";
	
	/**
	 * The connection established with database.
	 */
	private static Connection conn;


	/**
	 * Impart to class loader the order of load MySQL's Driver,
	 * initialize the connection conn. 
	 * @throws DatabaseConnectionException when connection with database fails
	 * @throws ClassNotFoundException when class loader doesn't find a Driver 
	 * @throws SQLException for every SQL error
	 */
	public static void initConnection() throws DatabaseConnectionException, ClassNotFoundException, SQLException {	
		Class.forName(DRIVER_CLASS_NAME); 
		conn = DriverManager.getConnection(DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE, USER_ID, PASSWORD);
	}
	
	/**
	 * Return the connection established with SQL database.
	 * @return conn is a connection to MySQL
	 */
	public static Connection getConnection() {
		return conn;
	}

	/**
	 * Close the connection with SQL database.
	 */
	public static void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}

