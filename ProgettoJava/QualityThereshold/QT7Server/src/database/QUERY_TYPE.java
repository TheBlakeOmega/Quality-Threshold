package database;

/**
 * The aggregation type (MIN or MAX).
 */
public enum QUERY_TYPE {
	/**
	 * Append 'min' after 'select' in the query.
	 */
	MIN,
	
	/**
	 * Append 'miax' after 'select' in the query.
	 */
	MAX
}
