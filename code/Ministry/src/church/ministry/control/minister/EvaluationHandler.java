package church.ministry.control.minister;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import church.ministry.control.commons.CommonsHandler;
import church.ministry.control.ecode.EMapper;
import church.ministry.control.log.Logger;
import church.ministry.control.workaround.MinisterEvaluationForChildrenAttendanceForMinistry;
import church.ministry.model.oracle.DatabaseConnectionManager;
import church.ministry.model.oracle.DatabaseExecutor;
import church.ministry.model.oracle.StatementBuilder;

public class EvaluationHandler implements Serializable {

	private static final long serialVersionUID = 1585516845935985538L;

	public static int setME_mass(String name, String actionDate, String value) {

		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			int result = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateME_mass(name, actionDate, value));

			if (result == 0) {
				result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.insertME(name, actionDate));

				result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateME_mass(name, actionDate, value));
			}

			result = CommonsHandler.validateResults(result, 1);

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
			return -1;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int setME_ministry(String name, String actionDate, String value) {

		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			int result = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateME_ministry(name, actionDate, value));

			if (result == 0) {
				result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.insertME(name, actionDate));

				result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateME_ministry(name, actionDate, value));
			}

			result = CommonsHandler.validateResults(result, 1);

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
			return -1;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int setME_ministersMeeting(String name, String actionDate, String value) {

		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			int result = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateME_ministersMeeting(name, actionDate, value));

			if (result == 0) {
				result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.insertME(name, actionDate));

				result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateME_ministersMeeting(name, actionDate, value));
			}

			result = CommonsHandler.validateResults(result, 1);

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
			return -1;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int setME_familyMeeting(String name, String actionDate, String value) {

		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			int result = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateME_familyMeeting(name, actionDate, value));

			if (result == 0) {
				result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.insertME(name, actionDate));

				result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateME_familyMeeting(name, actionDate, value));
			}

			result = CommonsHandler.validateResults(result, 1);

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
			return -1;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int setME_lessonPreparation(String name, String actionDate, String value) {

		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			int result = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateME_lessonPreparation(name, actionDate, value));

			if (result == 0) {
				result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.insertME(name, actionDate));

				result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateME_lessonPreparation(name, actionDate, value));
			}

			result = CommonsHandler.validateResults(result, 1);

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
			return -1;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int setME_illustrationTool(String name, String actionDate, String value) {

		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			int result = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateME_illustrationTool(name, actionDate, value));

			if (result == 0) {
				result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.insertME(name, actionDate));

				result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateME_illustrationTool(name, actionDate, value));
			}

			result = CommonsHandler.validateResults(result, 1);

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
			return -1;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static HashMap<String, String> getMinisterEvaluation(String ministerName, String actionDate) {

		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinisterEvaluation(ministerName, actionDate));

			HashMap<String, String> ministerEvaluation = new HashMap<String, String>();

			if (result.size() == 1) {
				ministerEvaluation.put("mass", result.get(0).get(0).toString());
				ministerEvaluation.put("ministersMeeting", result.get(0).get(1).toString());
				ministerEvaluation.put("familyMeeting", result.get(0).get(2).toString());
				ministerEvaluation.put("ministry", result.get(0).get(3).toString());
				ministerEvaluation.put("lessonPreparation", result.get(0).get(4).toString());
				ministerEvaluation.put("illustrationTool", result.get(0).get(5).toString());
			}

			return ministerEvaluation;

		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<ArrayList<String>> getEvaluationReport(String itemType, String itemName,
			String dateFrom, String dateTo) {
		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			String[] itemNames = new String[] { "«· —»Ì… «·ﬂ‰”Ì…", "ﬁÿ«⁄", "√”—…", "’›", "›’·" };

			ArrayList<ArrayList<Object>> ministers = new ArrayList<ArrayList<Object>>();

			if (itemType.equals(itemNames[0])) {
				ministers = new DatabaseExecutor().executeQuery(connection,
						StatementBuilder.selectAllMinistersNamesAndIds());
			} else if (itemType.equals(itemNames[1])) {
				ministers = new DatabaseExecutor().executeQuery(connection,
						StatementBuilder.selectMinisterIdAndNameBySection(itemName));
			} else if (itemType.equals(itemNames[2])) {
				ministers = new DatabaseExecutor().executeQuery(connection,
						StatementBuilder.selectMinisterIdAndNameByFamily(itemName));
			} else if (itemType.equals(itemNames[3])) {
				ministers = new DatabaseExecutor().executeQuery(connection,
						StatementBuilder.selectMinisterIdAndNameByYear(itemName));
			} else if (itemType.equals(itemNames[4])) {
				ministers = new DatabaseExecutor().executeQuery(connection,
						StatementBuilder.selectMinisterIdAndNameByClass(itemName));
			}

			int workaroundResult = MinisterEvaluationForChildrenAttendanceForMinistry
					.MinisterEvaluationForChildrenAttendanceForMinistry_WORKAROUND(ministers, dateFrom,
							dateTo);

			if (workaroundResult != EMapper.ECODE_SUCCESS) {
				return null;
			}

			ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();

			for (int i = 0; i < ministers.size(); i++) {
				result.add(EvaluationHandler.getMinisterEvaluationReport(ministers.get(i).get(0).toString(),
						ministers.get(i).get(1).toString(), dateFrom, dateTo));
			}

			ArrayList<Double> generalEvaluation = new ArrayList<Double>();
			for (int i = 0; i < result.size(); i++) {
				generalEvaluation.add(Double.parseDouble(result.get(i).get(10)));
			}

			ArrayList<ArrayList<String>> sortedResult = new ArrayList<ArrayList<String>>();

			int index;
			while (generalEvaluation.size() != 0) {
				index = generalEvaluation.indexOf(Collections.max(generalEvaluation));
				generalEvaluation.remove(index);
				sortedResult.add(result.remove(index));
			}

			for (int i = 0; i < sortedResult.size(); i++) {
				for (int j = 1; j < sortedResult.get(i).size(); j++) {
					sortedResult
							.get(i)
							.set(j,
									String.valueOf(Math.round((Double.parseDouble(sortedResult.get(i).get(j))) * 100.0) / 100.0)
											+ " %");
				}
			}

			return sortedResult;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<String> getMinisterEvaluationReport(String id, String name, String dateFrom,
			String dateTo) {
		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			ArrayList<String> record = new ArrayList<String>();
			record.add(name);

			ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();
			result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinisterEvaluationReport(id, dateFrom, dateTo));

			record.add(result.get(0).get(0).toString());
			record.add(result.get(0).get(1).toString());
			record.add(result.get(0).get(2).toString());
			record.add(result.get(0).get(3).toString());
			record.add(result.get(0).get(4).toString());
			record.add(result.get(0).get(5).toString());

			ArrayList<String> fridays = CommonsHandler.getFridays(dateFrom, dateTo);

			double totalCount = 0;
			double attendantsCount = 0;
			double followupCount = 0;
			double attendantsFollowupCount = 0;

			ArrayList<Double> attendancePercentages = new ArrayList<Double>();
			ArrayList<Double> followupPercentages = new ArrayList<Double>();
			ArrayList<Double> attendantsFollowupPercentages = new ArrayList<Double>();

			double attendancePercentage = 0;
			double followupPercentage = 0;
			double attendantsFollowupPercentage = 0;

			for (int k = 0; k < fridays.size(); k++) {
				// ***// COUNT // ***//
				result = new DatabaseExecutor().executeQuery(connection,
						StatementBuilder.selectChildrenOfMinisterCount(id, fridays.get(k)));

				totalCount = Double.parseDouble(result.get(0).get(0).toString());
				// ***//
				result = new DatabaseExecutor().executeQuery(connection,
						StatementBuilder.selectAttendantsCount(id, fridays.get(k)));

				attendantsCount = Double.parseDouble(result.get(0).get(0).toString());
				// ***//
				result = new DatabaseExecutor().executeQuery(connection,
						StatementBuilder.selectFollowupCount(id, fridays.get(k)));

				followupCount = Double.parseDouble(result.get(0).get(0).toString());
				// ***//
				result = new DatabaseExecutor().executeQuery(connection,
						StatementBuilder.selectAttendantsFollowupCount(id, fridays.get(k)));

				attendantsFollowupCount = Double.parseDouble(result.get(0).get(0).toString());
				// ***//

				// ***// AVERAGE // ***//
				if (totalCount != 0) {
					attendancePercentage = ((attendantsCount / totalCount) * 100);
				}
				// ***//
				if (totalCount == 0) {
					followupPercentage = 0;
				} else if (totalCount == attendantsCount) {
					followupPercentage = 100;
				} else if (totalCount != 0) {
					followupPercentage = ((followupCount / (totalCount - attendantsCount)) * 100);
				}
				// ***//
				if (attendantsCount != 0 && totalCount != 0) {
					attendantsFollowupPercentage = ((attendantsFollowupCount / attendantsCount) * 100);
				}

				attendancePercentages.add(attendancePercentage);
				followupPercentages.add(followupPercentage);
				attendantsFollowupPercentages.add(attendantsFollowupPercentage);
			}

			// ***//
			double avgAttendance = 0;
			for (int i = 0; i < attendancePercentages.size(); i++) {
				avgAttendance += attendancePercentages.get(i);
			}
			if (attendancePercentages.size() != 0) {
				avgAttendance = avgAttendance / attendancePercentages.size();
			}
			// ***//
			double avgFollowup = 0;
			for (int i = 0; i < followupPercentages.size(); i++) {
				avgFollowup += followupPercentages.get(i);
			}
			if (followupPercentages.size() != 0) {
				avgFollowup = avgFollowup / followupPercentages.size();
			}
			// ***//
			double avgAttendantsFollowup = 0;
			for (int i = 0; i < attendantsFollowupPercentages.size(); i++) {
				avgAttendantsFollowup += attendantsFollowupPercentages.get(i);
			}
			if (attendantsFollowupPercentages.size() != 0) {
				avgAttendantsFollowup = avgAttendantsFollowup / attendantsFollowupPercentages.size();
			}
			// ***//

			record.add(String.valueOf(avgAttendance));
			record.add(String.valueOf(avgFollowup));
			record.add(String.valueOf(avgAttendantsFollowup));

			double generalEvaluationValue = 0;

			for (int i = 1; i < record.size() - 1; i++) {
				generalEvaluationValue += Double.parseDouble(record.get(i));
			}

			String generalEvaluation = String.valueOf((generalEvaluationValue / 800) * 100);
			record.add(generalEvaluation);

			return record;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}
}