package church.ministry.model.oracle;

import java.sql.Connection;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.ui.AbstractComponent;

import church.ministry.control.log.Logger;

public class DatabaseConnectionManager extends AbstractComponent {

	private static final long serialVersionUID = -741612616926125137L;

	private static String jdbcUrl = "jdbc:oracle:thin:@localhost:1521/XE";
	private static String oracleDriverName = "oracle.jdbc.OracleDriver";
	private static String userid = "ministryr2";
	private static String password = "ministryr2p";
	private static int minNumberOfConnections = 5;
	private static int maxNumberOfConnections = 20;
	private static JDBCConnectionPool pool = null;

	public static Connection getConnection() {
		try {
			Connection connection = pool.reserveConnection();
			connection.setAutoCommit(false);

			Logger.info("Connection reserved");

			return connection;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		}
	}

	public static boolean releaseConnection(Connection connection) {
		try {
			if (connection != null) {
				pool.releaseConnection(connection);
				Logger.info("Connection released");
			}
			return true;
		} catch (Exception e) {
			Logger.exception(e);
			return false;
		}
	}

	public static boolean connect() {
		try {
			if (pool == null) {

				pool = new SimpleJDBCConnectionPool(oracleDriverName, jdbcUrl, userid, password,
						minNumberOfConnections, maxNumberOfConnections);

				Logger.info("Connection pool initiated to database: " + jdbcUrl + " using username: "
						+ userid);
			} else {
				Logger.info("Connection pool already initiated to database: " + jdbcUrl + " using username: "
						+ userid);
			}
			return true;
		} catch (Exception e) {
			Logger.exception(e);
			return false;
		}
	}

	public static boolean disconnect() {
		try {
			pool.destroy();

			Logger.info("Connection pool destroyed normally");

			return true;
		} catch (Exception e) {
			Logger.exception(e);
			return false;
		}
	}

}