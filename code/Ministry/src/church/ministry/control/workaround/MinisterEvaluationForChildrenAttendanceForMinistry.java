package church.ministry.control.workaround;

import java.sql.Connection;
import java.util.ArrayList;

import church.ministry.control.commons.CommonsHandler;
import church.ministry.control.ecode.EMapper;
import church.ministry.control.log.Logger;
import church.ministry.model.oracle.DatabaseConnectionManager;
import church.ministry.model.oracle.DatabaseExecutor;
import church.ministry.model.oracle.StatementBuilder;

public class MinisterEvaluationForChildrenAttendanceForMinistry {
	public static int MinisterEvaluationForChildrenAttendanceForMinistry_WORKAROUND(
			ArrayList<ArrayList<Object>> ministersIDs, String dateFrom, String dateTo) {

		Connection connection = null;
		try {
			connection = DatabaseConnectionManager.getConnection();

			int result = EMapper.ECODE_SUCCESS;

			ArrayList<String> fridays = CommonsHandler.getFridays(dateFrom, dateTo);

			for (ArrayList<Object> ministerID : ministersIDs) {

				for (String friday : fridays) {

					result = new DatabaseExecutor().executeUpdate(connection, StatementBuilder
							.updateMinisterEvluationForChildrenAttendanceForMinistry(ministerID.get(0)
									.toString(), friday));

					if (result == 0) {
						new DatabaseExecutor().executeUpdate(connection,
								StatementBuilder.insertMEByMinisterID(ministerID.get(0).toString(), friday));

						result = new DatabaseExecutor().executeUpdate(connection, StatementBuilder
								.updateMinisterEvluationForChildrenAttendanceForMinistry(ministerID.get(0)
										.toString(), friday));

						if (result == 1) {
							result = EMapper.ECODE_SUCCESS;
						} else {
							result = EMapper.ECODE_FAILURE;
							break;
						}
					} else if (result == 1) {
						result = EMapper.ECODE_SUCCESS;
					} else {
						result = EMapper.ECODE_FAILURE;
						break;
					}
				}
			}

			result = CommonsHandler.validateResults(result, EMapper.ECODE_SUCCESS);

			if (result == EMapper.ECODE_SUCCESS) {
				if (new DatabaseExecutor().commit(connection)) {
					return EMapper.ECODE_SUCCESS;
				} else {
					new DatabaseExecutor().rollback(connection);
					return EMapper.ECODE_FAILURE;
				}
			} else {
				new DatabaseExecutor().rollback(connection);
				return EMapper.ECODE_FAILURE;
			}
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}
}
