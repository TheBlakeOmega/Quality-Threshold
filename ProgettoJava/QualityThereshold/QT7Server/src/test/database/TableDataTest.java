package test.database;

import database.DbAccess;
import database.QUERY_TYPE;
import database.TableData;
import database.TableSchema;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * TableData class tester.
 */
public class TableDataTest {

	/**
	 * The DataSet.
	 */
	private static DbAccess db;
	
	/**
	 * The testing TableData.
	 */
	private static TableData testTableData;
	
	/**
	 * The right configuration of testTableData to check getDistinctTransazioni() method.
	 */
	private static String testOutput;
	
	/**
	 * The testing TableSchema.
	 */
	private static TableSchema testSchema;
	
	/**
	 * Minimum index of column.
	 */
	private static int minColumn;
	
	/**
	 * An internal index of column.
	 */
	private static int inColumn;
	
	/**
	 * Maximum index of column.
	 */
	private static int maxColumn;
	
	/**
	 * An index over the bounds of the TableData.
	 */
	private static int extraColumn;
	
	/**
	 * The right configuration of InColumn to check getDistinctColumnValues() method.
	 */
	private static String outputInColumn;
	
	/**
	 * The right configuration of MaxColumn to check getDistinctColumnValues() method.
	 */
	private static String outputMaxColumn;
	
	/**
	 * The right configuration of MinColumn to check getDistinctColumnValues() method.
	 */
	private static String outputMinColumn;
	
	/**
	 * Initializes all necessary attributes.
	 */
	@BeforeAll public static void setUp() {
		try {
			db = new DbAccess();
			testTableData = new TableData(db);
			inColumn = 2;
			maxColumn = 4;
			extraColumn = 10;
			minColumn = 0;
			
			testOutput = "[sunny 30.3 high weak no , "
					+ "sunny 30.3 high strong no , "
					+ "overcast 30.0 high weak yes , "
					+ "rain 13.0 high weak yes , "
					+ "rain 0.0 normal weak yes , "
					+ "rain 0.0 normal strong no , "
					+ "overcast 0.1 normal strong yes , "
					+ "sunny 13.0 high weak no , "
					+ "sunny 0.1 normal weak yes , "
					+ "rain 12.0 normal weak yes , "
					+ "sunny 12.5 normal strong yes , "
					+ "overcast 12.5 high strong yes , "
					+ "overcast 29.21 normal weak yes , "
					+ "rain 12.5 high strong no ]";
			
			outputInColumn = "[high, normal]";
			outputMinColumn = "[overcast, rain, sunny]";
			outputMaxColumn = "[no, yes]";
			
			DbAccess.initConnection();
			testSchema = new TableSchema(db, "playtennis");
			DbAccess.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test getDistinctTransazioni() method of TableData.
	 */
	@Test public void testGetDistinctTransazioni() {
		try {
			db.initConnection();
			assertEquals(testTableData.getDistinctTransazioni("playtennis").toString(), testOutput);
			db.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test getDistinctColumnValues(String table,Column column) method of TableData.
	 */
	@Test public void testGetDistinctColumnValues() {
		setUp();
		try {
			db.initConnection();
			
			assertEquals(testTableData.getDistinctColumnValues("playtennis", testSchema.getColumn(inColumn)).toString(), outputInColumn);
			assertEquals(testTableData.getDistinctColumnValues("playtennis", testSchema.getColumn(minColumn)).toString(), outputMinColumn);
			assertEquals(testTableData.getDistinctColumnValues("playtennis", testSchema.getColumn(maxColumn)).toString(), outputMaxColumn);
			
			Exception e = new IndexOutOfBoundsException();
			assertThrows(e.getClass(), () -> testTableData.getDistinctColumnValues("playtennis", testSchema.getColumn(extraColumn)));
			
			db.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Test getAggregateColumnValue(String table,Column column,QUERY_TYPE aggregate)
	 * method of TableData.
	 */
	@Test public void testGetAggregateColumnValues() {
		try {
			db.initConnection();
			
			assertEquals(testTableData.getAggregateColumnValue("playtennis", testSchema.getColumn(inColumn), QUERY_TYPE.MIN).toString(), "high");
			assertEquals(testTableData.getAggregateColumnValue("playtennis", testSchema.getColumn(inColumn), QUERY_TYPE.MAX).toString(), "normal");
			assertEquals(testTableData.getAggregateColumnValue("playtennis", testSchema.getColumn(minColumn), QUERY_TYPE.MIN).toString(), "overcast");
			assertEquals(testTableData.getAggregateColumnValue("playtennis", testSchema.getColumn(minColumn), QUERY_TYPE.MAX), "sunny");
			assertEquals(testTableData.getAggregateColumnValue("playtennis", testSchema.getColumn(maxColumn), QUERY_TYPE.MIN), "no");
			assertEquals(testTableData.getAggregateColumnValue("playtennis", testSchema.getColumn(maxColumn), QUERY_TYPE.MAX), "yes");
			
			Exception e = new IndexOutOfBoundsException();
			assertThrows(e.getClass(), () -> testTableData.getAggregateColumnValue("playtennis", testSchema.getColumn(extraColumn), QUERY_TYPE.MIN));
			assertThrows(e.getClass(), () -> testTableData.getAggregateColumnValue("playtennis", testSchema.getColumn(extraColumn), QUERY_TYPE.MAX));
			
			db.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}



















