package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The MultiThread server main class.
 */
public class MultiServer {

	/**
	 * The port that ServerSocket will use to accept Clients' connection 
	 * and build Socket.
	 */
	private static int PORT = 8080;
	
	/**
	 * The ServerSocket that manages MultiServer.
	 */
	private static ServerSocket s;
	
	/**
	 * Public Constructor.
	 * @throws IOException for every IO error
	 */
	public MultiServer() throws IOException {
		s = new ServerSocket(PORT);
	}
	
	/**
	 * MAIN that stats the Server.
	 * @param args default
	 * @throws IOException for every IO error
	 */
	public static void main(String[] args) throws IOException {
		run();
	}

	/**
	 * Builds ServerSocket to the PORT 8080 and accept every client building 
	 * an instance of ServerOneClient (subClass of Thread) that manage the connection
	 * with a single Client through its own Thread. 
	 * @throws IOException when connection between ServerSocket and PORT fails
	 */
	private static void run() throws IOException {
		s = new ServerSocket(PORT);
		InetAddress a = s.getInetAddress();
		System.out.println("Server Started : IP= " + a.getLocalHost().getHostAddress() + " Port= " + PORT);
		try {
			while (true) {
				Socket socket = s.accept();
				System.out.println("accepting " + socket);
				try {
					new ServerOneClient(socket);
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} finally {
			close();
		}
	}

	/**
	 * Close the ServerSocket and every Socket still connected to some Client.
	 */
	public static void close() {
		try {
			s.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
}

