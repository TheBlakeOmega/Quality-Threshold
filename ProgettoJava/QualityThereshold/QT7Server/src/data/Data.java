package data;

import database.DbAccess;
import database.EmptySetException;
import database.Example;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Source data class.
 */
public class Data implements Serializable {

	/**
	 * A list in which every Examples models a transition of values.
	 */
	private List<Example> data;
	
	/**
	 * A list that contains the type of Attributes that
	 * every tuple in data contains.
	 */
	private List<Attribute> attributeSet;

	/**
	 * Public constructor,
	 * it load data from database's table called as parameter 'table' into 
	 * an ArrayList, initializes attributeSet with five objects of type 
	 * Attributes, one for each attribute. 
	 * @param table is the name of the table in SQL database
	 * @throws EmptyDatasetException when the table modeled by data is empty
	 * @throws SQLException when there are an error on connection with database
	 * @throws EmptySetException when the list of attributes is empty
	 */
	public Data(String table) throws EmptyDatasetException, SQLException, EmptySetException {

		data = new ArrayList<Example>();

		attributeSet = new LinkedList<Attribute>();



		Set<String> outLookValues = new TreeSet<String>();
		outLookValues.add("overcast");
		outLookValues.add("rain");
		outLookValues.add("sunny");
		attributeSet.add(0, new DiscreteAttribute("Outlook", 0, outLookValues));
		attributeSet.add(1, new ContinuousAttribute("Temperature", 1, 0, 30.0));


		Set<String> humidityValues = new TreeSet<String>();
		humidityValues.add("high");
		humidityValues.add("normal");
		attributeSet.add(2, new DiscreteAttribute("Humidity", 2, humidityValues));


		Set<String> windValuesValues = new TreeSet<String>();
		windValuesValues.add("weak");
		windValuesValues.add("strong");
		attributeSet.add(3, new DiscreteAttribute("Wind", 3, windValuesValues));


		Set<String> playTennisValues = new TreeSet<String>();
		playTennisValues.add("no");
		playTennisValues.add("si");
		attributeSet.add(4, new DiscreteAttribute("PlayTennis", 4, playTennisValues));

		Statement statement;

		String query = "select * FROM " + table;

		statement = DbAccess.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		ResultSetMetaData rsmd = rs.getMetaData();

		try {
			while (rs.next()) {
				Example currentTuple = new Example();
				for (int i = 0; i < rsmd.getColumnCount(); i++) {
					if (rsmd.getColumnTypeName(i + 1).equals("DOUBLE")) {
						currentTuple.add(rs.getDouble(i + 1));
					} else {
						currentTuple.add(rs.getString(i + 1));
					}
				}
				data.add(currentTuple);
			}
			rs.close();
			statement.close();

		} catch (SQLException SQLexc) {
			SQLexc.printStackTrace();
		}
		if (data.size() == 0) {
			throw new EmptyDatasetException();
		}
		if (attributeSet.isEmpty()) {
			throw new EmptyDatasetException();
		}
	}

	/**
	 * Return number of tuples stored in data.
	 * @return the number of tuples stored in data
	 */
	public int getNumberOfExamples() {
		return data.size();
	}

	/**
	 * Return number of types of attribute that data have.
	 * @return the number of attribute stored in attributeSet 
	 */
	public int getNumberOfAttributes() {
		return attributeSet.size();
	}

	/**
	 * Return the attribute in position (exampleIndex,attributeIndex).
	 * @param exampleIndex is the index of data from which get the attribute
	 * @param attributeIndex is the index of tuple from which get the attribute
	 * @return the attribute in position (exampleIndex,attributeIndex) of data
	 */
	public Object getAttributeValue(int exampleIndex, int attributeIndex) {
		return data.get(exampleIndex).get(attributeIndex);
	}

	/**
	 * Return attributeSet.
	 * @return the list of attributes of data
	 */
	public List<Attribute> getAttribute() {
		return attributeSet;
	}

	/**
	 *  Overrides Object's toString.
	 *  @return a string that models the object's status as a matrix 
	 *      with enumerated lines and columns specified by the name of the attribute 
	 */
	public String toString() {
		String output = " ";
		for (int i = 0; i < getNumberOfAttributes(); i++) {
			output = output + "  " + ((Attribute) attributeSet.get(i)).getName() + ",";
		}
		output = output + "\n";
		for (int i = 0; i < data.size(); i++) {
			output = output + (i + 1) + ": ";
			for (int j = 0; j < getNumberOfAttributes(); j++) {
				output = output + data.get(i).get(j) + ",     ";
			}
			output = output + "\n";
		}
		return output;
	}

	/**
	 * Build and return a Tuple type object that models as a sequence of couples attribute-value 
	 * the line of data with index in input.
	 * @param index is the line that will be modeled 
	 * @return a Tuple type object
	 */
	public Tuple getItemSet(int index) {
		Tuple tuple = new Tuple(attributeSet.size());
		for (int i = 0; i < attributeSet.size(); i++) {
			if (attributeSet.get(i) instanceof ContinuousAttribute) {
				tuple.add(new ContinuousItem((ContinuousAttribute) attributeSet.get(i), new Double(data.get(index).get(i).toString())), i);
			} else {
				tuple.add(new DiscreteItem((DiscreteAttribute) attributeSet.get(i), (String) data.get(index).get(i)), i);
			}
		}
		return tuple;
	}

}
