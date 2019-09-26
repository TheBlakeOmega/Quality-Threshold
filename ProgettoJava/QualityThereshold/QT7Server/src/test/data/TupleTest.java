package test.data;

import data.Data;
import data.Tuple;
import database.DbAccess;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tuple class tester.
 */
public class TupleTest {

	/**
	 * The DataSet.
	 */
	private static Data data;
	
	/**
	 * The testing Tuple.
	 */
	private static Tuple testTuple;
	
	/**
	 * A testing Tuple, but empty.
	 */
	private static Tuple emptyTuple;
	
	/**
	 * Minimum index of the Tuple.
	 */
	private static int minIndex;
	
	/**
	 * Maximum index of the Tuple.
	 */
	private static int maxIndex = 4;
	
	/**
	 * An internal index of the Tuple.
	 */
	private static int inIndex = 2;
	
	/**
	 * An index over the bounds of the Tuple.
	 */
	private static int extraIndex = 10;
	
	/**
	 * A testing Set that doesn't identifies any Example in DataSet.
	 */
	private static Set<Integer> emptyClusteredData;
	
	/**
	 * A testing Set that identifies all Examples in DataSet.
	 */
	private static Set<Integer> fullClusteredData;
	
	/**
	 * A testing Set that identifies some Examples in DataSet.
	 */
	private static Set<Integer> testClusteredData;
	
	/**
	 * Initializes all necessary attributes.
	 */
	@BeforeAll public void setUp() {
		try {
			DbAccess.initConnection();
			data = new Data("playtennis");
			DbAccess.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		testTuple = data.getItemSet(9);
		emptyTuple = new Tuple(5);
		emptyClusteredData = new HashSet<Integer>();	//doesn't insert any value
		fullClusteredData = new HashSet<Integer>();
		for (int i = 0; i < 14; i++) {
			fullClusteredData.add(i);	//insert all values from 1 to 14
		}
		testClusteredData = new HashSet<Integer>();
		int j;
		for (int i = 0; i < 7; i++) {
			j = 2 * i + 1;
			testClusteredData.add(j);	//insert odd values only from 1 to 13
		}
	}
	
	/**
	 * Test Tuple getLength() method.
	 */
	@Test public void testGetLength() {
		assertEquals(5, testTuple.getLength());
		assertEquals(5, emptyTuple.getLength());
	}
	
	/**
	 * Test Tuple get() method.
	 */
	@Test public void testGet() {
		assertEquals(testTuple.get(inIndex).toString(), "normal");
		assertEquals(testTuple.get(maxIndex).toString(), "yes");
		assertEquals(testTuple.get(minIndex).toString(), "rain");
		
		Exception e = new IndexOutOfBoundsException();
		assertThrows(e.getClass(), () -> testTuple.get(extraIndex));
		
		assertNull(emptyTuple.get(inIndex));
	}
	
	/**
	 * Test Tuple getDistance() method.
	 */
	@Test public void testGetDistance() {
		double value = testTuple.getDistance(data.getItemSet(inIndex));
		assertTrue(value > 2.5 && value < 2.7);
		
		value = testTuple.getDistance(testTuple);
		assertTrue(value == 0);
		
		Exception e = new NullPointerException();
		assertThrows(e.getClass(), () -> testTuple.getDistance(emptyTuple));
		
	}
	
	/**
	 * Test Tuple avgDistance() method.
	 */
	@Test public void testAvgDistance() {
		setUp();
		double value = testTuple.avgDistance(data, fullClusteredData);
		assertTrue(value > 2.2 && value < 2.3);   //approximately 2.2216
		value = testTuple.avgDistance(data, testClusteredData);
		assertTrue(value > 2.4 && value < 2.5);  //approximately 2.4442
		
		value = testTuple.avgDistance(data, emptyClusteredData);
		assertEquals(((Double) testTuple.avgDistance(data, emptyClusteredData)).toString(), "NaN");

		Exception e = new NullPointerException();
		assertThrows(e.getClass(), () -> emptyTuple.avgDistance(data, testClusteredData));
	}
	
	/**
	 * Test Tuple toString() method.
	 */
	@Test public void testToString() {
		assertEquals(testTuple.toString(), " rain 12.0 normal weak yes ");
		assertEquals(emptyTuple.toString(), " rain null null null null ");
	}
	
	/**
	 * Test Tuple add() method.
	 */
	@Test public void testAdd() {
		emptyTuple.add(testTuple.get(minIndex), minIndex);
		assertEquals(emptyTuple.get(minIndex).toString(), "rain");
	}
}


