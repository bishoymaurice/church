package church.ministry.model.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import church.ministry.control.log.Logger;

public class DatabaseExecutor {

	public ArrayList<ArrayList<Object>> executeQuery(Connection connection, String statementString) {
		try {
			ArrayList<ArrayList<Object>> resultArrayList = new ArrayList<ArrayList<Object>>();
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet resultSet = statement.executeQuery(statementString);
			int numberOfColumns = resultSet.getMetaData().getColumnCount();
			while (resultSet.next()) {
				ArrayList<Object> record = new ArrayList<Object>();
				for (int i = 1; i <= numberOfColumns; i++) {
					record.add(resultSet.getObject(i));
				}
				resultArrayList.add(record);
			}

			for (int i = 0; i < resultArrayList.size(); i++) {
				for (int j = 0; j < resultArrayList.get(i).size(); j++) {
					if (resultArrayList.get(i).get(j) == null) {
						resultArrayList.get(i).set(j, "");
					}
				}
			}

			Logger.info("SQL Query Statement Executed { " + statementString + " } { "
					+ resultArrayList.size() + " }");

			resultSet.close();
			statement.close();

			return resultArrayList;
		} catch (Exception e) {
			Logger.error(e.getMessage() + "\r\n{ SQL: " + statementString + " }");
			Logger.exception(e);
			return null;
		}
	}

	public int executeUpdate(Connection connection, String statementString) {
		try {
			Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			int affectedRowsCount = statement.executeUpdate(statementString);
			statement.close();

			Logger.info("SQL Update Statement Executed { " + statementString + " } { " + affectedRowsCount
					+ " }");

			return affectedRowsCount;
		} catch (Exception e) {
			try {
				connection.rollback();
			} catch (Exception e1) {
				Logger.info("Failed to execute rollback");
				Logger.exception(e1);
			}
			Logger.error(e.getMessage() + "\r\n{ SQL: " + statementString + " }");
			Logger.exception(e);
			return -1;
		}
	}

	public boolean rollback(Connection connection) {
		try {
			connection.rollback();
			Logger.info("Rollback executed");
			return true;
		} catch (Exception e) {
			Logger.exception(e);
			return false;
		}
	}

	public boolean commit(Connection connection) {
		try {
			connection.commit();
			Logger.info("Commit executed");
			return true;
		} catch (Exception e) {
			Logger.exception(e);
			return false;
		}
	}
}
