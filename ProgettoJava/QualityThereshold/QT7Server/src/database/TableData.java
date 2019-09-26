package database;

import database.TableSchema.Column;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * The table data class.
 */
public class TableData {

	/**
	 * Database on which make queries.
	 */
	DbAccess db;

	/**
	 * Public constructor.
	 * @param db is the database on which make queries
	 */
	public TableData(DbAccess db) {
		this.db = db;
	}

	/**
	 * Get the schema of the table identified by 'table'. Make the query to extract tuples from the database's table.
	 * For each tuple of the resultset, methods creates an object instance of Example, whose reference will be
	 * included in list in return. In particular, by current tuple of resultset, the values ​​of the individual fields are extracted
	 * (using getDouble() and getString()), and they are added at the object instance of the class Example that is being built
	 * @param table is the identifier of the table in database
	 * @return transSet that is a list of example that are build from database's data
	 * @throws SQLException for every errors in the query's execution 
	 * @throws EmptySetException when resultset is empty
	 */
	public List<Example> getDistinctTransazioni(String table) throws SQLException, EmptySetException {
		LinkedList<Example> transSet = new LinkedList<Example>();
		Statement statement;
		TableSchema tSchema = new TableSchema(db, table);


		String query = "select distinct ";

		for (int i = 0; i < tSchema.getNumberOfAttributes(); i++) {
			Column c = tSchema.getColumn(i);
			if (i > 0) {
				query += ",";
			}
			query += c.getColumnName();
		}
		if (tSchema.getNumberOfAttributes() == 0) {
			throw new SQLException();
		}
		query += " FROM " + table;
		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		boolean empty = true;
		while (rs.next()) {
			empty = false;
			Example currentTuple = new Example();
			for (int i = 0; i < tSchema.getNumberOfAttributes(); i++) {
				if (tSchema.getColumn(i).isNumber()) {
					currentTuple.add(rs.getDouble(i + 1));
				} else {
					currentTuple.add(rs.getString(i + 1));
				}
			}
			transSet.add(currentTuple);
		}
		rs.close();
		statement.close();
		if (empty) {
			throw new EmptySetException();
		}
		return transSet;
	}

	/**
	 * Makes and executes a SQL query to extract distinct ordered values of column 
	 * and populates a set to return.
	 * @param table is the identifier of the table in database
	 * @param column from which get data
	 * @return valueSet is a set of ordered values
	 * @throws SQLException for every errors in the query's execution
	 */
	public Set<Object> getDistinctColumnValues(String table, Column column) throws SQLException {
		Set<Object> valueSet = new TreeSet<Object>();
		Statement statement;
		TableSchema tSchema = new TableSchema(db, table);

		String query = "select distinct ";
		query += column.getColumnName();
		query += " FROM " + table;
		query += " ORDER BY " + column.getColumnName();

		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		while (rs.next()) {
			if (column.isNumber()) {
				valueSet.add(rs.getDouble(1));
			} else {
				valueSet.add(rs.getString(1));
			}
		}
		rs.close();
		statement.close();

		return valueSet;

	}

	/**
	 * Makes and executes a SQL query to extract the aggregate value (max value or min value) searched 
	 * in the column 'column' of the table.
	 * @param table is the identifier of the table in database
	 * @param column from which get data 
	 * @param aggregate instance of the enumerative class QUERY_TYPE
	 * @return value that is the aggregate value computed
	 * @throws SQLException for every errors in the query's execution
	 * @throws NoValueException when resultSet is empty or the calculated value is equal to null
	 */
	public Object getAggregateColumnValue(String table, Column column, QUERY_TYPE aggregate) throws SQLException, NoValueException {
		Statement statement;
		TableSchema tSchema = new TableSchema(db, table);
		Object value = null;
		String aggregateOp = "";

		String query = "select ";
		if (aggregate == QUERY_TYPE.MAX) {
			aggregateOp += "max";
		} else {
			aggregateOp += "min";
		}
		query += aggregateOp + "(" + column.getColumnName() + ") FROM " + table;


		statement = db.getConnection().createStatement();
		ResultSet rs = statement.executeQuery(query);
		if (rs.next()) {
			if (column.isNumber()) {
				value = rs.getFloat(1);
			} else {
				value = rs.getString(1);
			}
		}
		rs.close();
		statement.close();
		if (value == null) {
			throw new NoValueException("No " + aggregateOp + " on " + column.getColumnName());
		}
		return value;

	}

}
