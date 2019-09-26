package server;

import data.Data;
import database.DatabaseConnectionException;
import database.DbAccess;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import mining.ClusteringRadiusException;
import mining.QTMiner;

/**
 * Server one client thread class.
 */
public class ServerOneClient extends Thread {

	/**
	 * The DataSet.
	 */
	private Data data;
	
	/**
	 * Instance of QTMiner that computes table's data.
	 */
	private QTMiner qt;
	
	/**
	 * The name, received by client, of the table that will be search in database.
	 */
	private String tabella = "";
	
	/**
	 * An integer, received by client, that qt uses to compute data and makes clusters.
	 */
	private double radius;

	/**
	 * InputStream of object's connected to Client.
	 */
	private ObjectInputStream in;
	
	/**
	 * OutputStream of object's connected to Client.
	 */
	private ObjectOutputStream out;

	/**
	 * Public constructor.
	 * @param s is the socket connected to client
	 * @throws IOException for every error in IO
	 */
	public ServerOneClient(Socket s) throws IOException {
		out = new ObjectOutputStream(s.getOutputStream());
		in = new ObjectInputStream(s.getInputStream());
		start();
	}

	/**
	 * Overrides Thread's run.
	 * Receives instructions by client through a number that switches between some option
	 * case 0 : Server receives the name of the table to load from database by Client and builds an 
	 * 			instance of Data with data loaded from this table
	 * case 1 : Server receives a radius to build an instance of QTMiner that computes data loaded in
	 * 			case 0, than builds clusters that will be sent to Client 
	 * case 2 : Server builds a file named by the concatenation of table's name and radius and store it 
	 * 			in its File System
	 * case 3 : Server receives from Client name of the table and radius to search a file stored in 
	 * 			File System; if it exists, it is loaded in an instance of QTMiner and that is sent to Client
	 */
	@Override public void run() {
		try {
			while (true) {
				switch ((int) in.readObject()) {
				case 0:
					tabella = (String) in.readObject();
					try {
						DbAccess.initConnection();
						out.writeObject("OK");
					} catch (DatabaseConnectionException e2) {
						e2.printStackTrace();
					}

					data = new Data(tabella);
					DbAccess.closeConnection();

					break;

				case 1:
					radius = (double) in.readObject();
					qt = new QTMiner(radius);

					int numC;
					try {
						numC = qt.compute(data);
						if (numC == 1) {
							out.writeObject("ErroreC");
							throw new ClusteringRadiusException();
						}
						out.writeObject("OK");
						out.writeObject(numC);
						out.writeObject(qt.getC().toString(data));
					} catch (ClusteringRadiusException e1) {
						e1.printStackTrace();
					}

					break;

				case 2:
					try {
						String fileName = tabella + radius + ".dmp";
						qt.salva(fileName);
						out.writeObject("OK");
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;

				case 3:
					String fileNameRead = null;
					try {
						String tableName = (String) in.readObject();
						double radiusFile = (double) in.readObject();
						fileNameRead = tableName + radiusFile + ".dmp";
						out.writeObject("OK");
						QTMiner output = new QTMiner(fileNameRead);
						out.writeObject(output.toString());
					} catch (IOException e) {
						out.writeObject("File doesnt't exists!!");
						e.printStackTrace();
					}
					break;
				default:
					//nothing
				}
			}

		} catch (Exception e) {
			try {
				out.writeObject("throwing Exception");
			} catch (IOException f) {
				f.printStackTrace();
			}
			e.printStackTrace();
		}
	}
}
