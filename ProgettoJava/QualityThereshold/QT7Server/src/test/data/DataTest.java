package test.data;

import data.Data;
import database.DbAccess;
import java.sql.SQLException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Data class tester.
 */
public class DataTest {

	/**
	 * The DataSet.
	 */
	private static Data data;
	
	/**
	 * Name of a table present in database.
	 */
	private static String rightTable = "playtennis";
	
	/**
	 * Name of a table present in database, but empty.
	 */
	private static String wrongTable = "playfootball";
	
	/**
	 * Name of a table not present in the database.
	 */
	private static String inexistentTable = "playbasket";
	
	/**
	 * An empty String.
	 */
	private static String emptyString = "";
	
	/**
	 * Minimum possible table lines' index.
	 */
	private static int minLimitLine;
	
	/**
	 * Maximum possible table lines' index.
	 */
	private static int maxLimitLine = 13;
	
	/**
	 * Minimum possible table columns' index.
	 */
	private static int minLimitColumn;
	
	/**
	 * Maximum possible table columns' index.
	 */
	private static int maxLimitColumn = 4;
	
	/**
	 * A lines' index over the bounds of the table.
	 */
	private static int extraLimitLine = 20;
	
	/**
	 * A columns' index over the bounds of the table.
	 */
	private static int extraLimitColumn = 10;
	
	/**
	 * An internal index for lines.
	 */
	private static int inLimitLine = 7;
	
	/**
	 * An internal index for columns.
	 */
	private static int inLimitColumn = 3;
	
	/**
	 * The right configuration of DataSet to check toString() method.
	 */
	private static String dataOutput = "   Outlook,  Temperature,  Humidity,  Wind,  PlayTennis,\n" 
			+ "1: sunny,     30.30,     high,     weak,     no,     \n" 
			+ "2: sunny,     30.30,     high,     strong,     no,     \n" 
			+ "3: overcast,     30.00,     high,     weak,     yes,     \n" 
			+ "4: rain,     13.00,     high,     weak,     yes,     \n" 
			+ "5: rain,     0.00,     normal,     weak,     yes,     \n" 
			+ "6: rain,     0.00,     normal,     strong,     no,     \n"
			+ "7: overcast,     0.10,     normal,     strong,     yes,     \n" 
			+ "8: sunny,     13.00,     high,     weak,     no,     \n" 
			+ "9: sunny,     0.10,     normal,     weak,     yes,     \n" 
			+ "10: rain,     12.00,     normal,     weak,     yes,     \n"
			+ "11: sunny,     12.50,     normal,     strong,     yes,     \n" 
			+ "12: overcast,     12.50,     high,     strong,     yes,     \n" 
			+ "13: overcast,     29.21,     normal,     weak,     yes,     \n"
			+ "14: rain,     12.50,     high,     strong,     no,     \n";
	
	/**
	 * Initializes all necessary attributes.
	 */
	@BeforeAll public static void setUp() {
		try {
			DbAccess.initConnection();
			data = new Data(rightTable);
			DbAccess.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test Data's constructor :
	 * check what happens if tableName has the name of a right,
	 * wrong and inexistent table.
	 */
	@Test public void testData() {
		assertNotNull(data);
		assertEquals(5, data.getNumberOfAttributes());
		assertEquals(14, data.getNumberOfExamples());
		assertNotNull(data.getAttribute());

		Exception e = new SQLException();
		assertThrows(e.getClass(), () -> new Data(wrongTable));
		assertThrows(e.getClass(), () -> new Data(inexistentTable));
		assertThrows(e.getClass(), () -> new Data(emptyString));

	}

	/**
	 * Test Data getAttributeValue() method.
	 */
	@Test public void testGetAttributeValue() {
		assertEquals((String) data.getAttributeValue(inLimitLine, inLimitColumn), "weak");
		assertEquals((String) data.getAttributeValue(minLimitLine, minLimitColumn), "sunny");
		assertEquals((String) data.getAttributeValue(maxLimitLine, minLimitColumn), "rain");
		assertEquals((String) data.getAttributeValue(minLimitLine, maxLimitColumn), "no");
		assertEquals((String) data.getAttributeValue(maxLimitLine, maxLimitColumn), "no");

		Exception e = new IndexOutOfBoundsException();
		assertThrows(e.getClass(), () -> data.getAttributeValue(inLimitLine, extraLimitColumn));
		assertThrows(e.getClass(), () -> data.getAttributeValue(extraLimitLine, inLimitColumn));
		assertThrows(e.getClass(), () -> data.getAttributeValue(extraLimitLine, extraLimitColumn));

	}

	/**
	 * Test Data getItemSet() method.
	 */
	@Test public void testGetItemSet() {
		setUp();
		assertEquals(data.getItemSet(inLimitLine).toString(),
				" sunny 13.0 high weak no ");
		assertEquals(data.getItemSet(minLimitLine).toString(),
				" sunny 30.3 high weak no ");
		assertEquals(data.getItemSet(maxLimitLine).toString(),
				" rain 12.5 high strong no ");

		Exception e = new IndexOutOfBoundsException();
		assertThrows(e.getClass(), () -> data.getItemSet(extraLimitLine));
	}

	/**
	 * Test Data toString() method.
	 */
	@Test public void testToString() {
		assertEquals(dataOutput, data.toString());
	}
}

