package test.server;

import java.io.IOException;
import org.junit.Test;
import server.MultiServer;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * MultiServer class tester.
 */
public class MultiServerTest {

	/**
	 * The first Server.
	 */
	private static MultiServer serverOne;
	
	/**
	 * The second Server.
	 */
	private static MultiServer serverTwo;
	
	/**
	 * Test what happens if someone try to run Server program
	 * while another one is running.
	 */
	@Test public void testRun() {
		try {
			 serverOne = new MultiServer();
		} catch (IOException e) {
			fail(e.toString());
		}
		try {
			 serverTwo = new MultiServer();
			fail();
		} catch (IOException e) {
			assertTrue(true);
		} finally {
			MultiServer.close();
		}
	}
	
}






