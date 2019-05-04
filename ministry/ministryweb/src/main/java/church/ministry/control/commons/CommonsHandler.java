package church.ministry.control.commons;

import java.io.Serializable;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import church.ministry.control.ecode.EMapper;
import church.ministry.control.log.Logger;
import church.ministry.model.oracle.DatabaseExecutor;
import church.ministry.model.oracle.DatabaseConnectionManager;
import church.ministry.model.oracle.StatementBuilder;
import church.ministry.util.Validator;

public class CommonsHandler implements Serializable {

	private static final long serialVersionUID = 6868949001750010495L;

	public static String getIntegerInDecimalFormat(int digit, String decimalFormat) {
		DecimalFormat decimalFormatter = new DecimalFormat(decimalFormat);
		return decimalFormatter.format(digit);
	}

	public static int newMember(Connection connection, String id, String type, String name) {
		try {
			return new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.insertMember(id, type, name));
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static int newFather(Connection connection, String id, String notes) {
		try {
			return new DatabaseExecutor().executeUpdate(connection, StatementBuilder.insertFather(id, notes));
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static int newProfile(Connection connection, String id, String birthday, String nationalId,
			String education, String eccEducation, String job, String courses, String skills) {
		try {
			return new DatabaseExecutor().executeUpdate(connection, StatementBuilder.insertProfile(id,
					birthday, nationalId, education, eccEducation, job, courses, skills));
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static int newContact(Connection connection, String id, String phone, String mobilePhone1,
			String mobilePhone2, String addressNum, String addressStreet, String addressRegion,
			String addressDistrict, String addressFree, String email) {

		try {
			ArrayList<ArrayList<Object>> result;

			if (Validator.validateString(addressStreet)) {
				result = new DatabaseExecutor().executeQuery(connection,
						StatementBuilder.selectStreetByName(addressStreet));
				if (result.size() < 1) {
					new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.insertNewStreet(addressStreet));
				}
			}

			if (Validator.validateString(addressRegion)) {
				result = new DatabaseExecutor().executeQuery(connection,
						StatementBuilder.selectRegionByName(addressRegion));
				if (result.size() < 1) {
					new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.insertNewRegion(addressRegion));
				}
			}

			if (Validator.validateString(addressDistrict)) {
				result = new DatabaseExecutor().executeQuery(connection,
						StatementBuilder.selectDistrictByName(addressDistrict));
				if (result.size() < 1) {
					new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.insertNewDistrict(addressDistrict));
				}
			}

			return new DatabaseExecutor().executeUpdate(connection, StatementBuilder.insertContact(id, phone,
					mobilePhone1, mobilePhone2, addressNum, addressStreet, addressRegion, addressDistrict,
					addressFree, email));
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static ArrayList<String> getAllStreets() {
		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectAllStreets());

			ArrayList<String> allStreets = new ArrayList<String>();

			for (int i = 0; i < result.size(); i++) {
				allStreets.add(result.get(i).get(0).toString());
			}

			return allStreets;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<String> getAllFollowupComments() {
		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectAllFollowupComments());

			ArrayList<String> allFollowupComments = new ArrayList<String>();

			for (int i = 0; i < result.size(); i++) {
				allFollowupComments.add(result.get(i).get(0).toString());
			}

			return allFollowupComments;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<String> getAllRegions() {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectAllRegions());

			ArrayList<String> allRegions = new ArrayList<String>();

			for (int i = 0; i < result.size(); i++) {
				allRegions.add(result.get(i).get(0).toString());
			}

			return allRegions;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<String> getAllDistricts() {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectAllDistricts());

			ArrayList<String> allDistricts = new ArrayList<String>();

			for (int i = 0; i < result.size(); i++) {
				allDistricts.add(result.get(i).get(0).toString());
			}

			return allDistricts;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int validateResults(int... results) {
		try {
			for (int i = 1; i < results.length; i += 2) {
				if (results[i - 1] != results[i]) {
					return EMapper.ECODE_FAILURE;
				}
			}
			return EMapper.ECODE_SUCCESS;
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static ArrayList<String> getNamesByType(String type) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectNamesByType(type));

			ArrayList<String> allNames = new ArrayList<String>();

			for (int i = 0; i < result.size(); i++) {
				allNames.add(result.get(i).get(0).toString());
			}

			return allNames;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<String> getAllMinistersNamesExcludingSubClass(String subClassName) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectAllMinistersNamesExcludingSubClass(subClassName));

			ArrayList<String> allNames = new ArrayList<String>();

			for (int i = 0; i < result.size(); i++) {
				allNames.add(result.get(i).get(0).toString());
			}

			return allNames;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<String> getAllNamesByType(String type) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectAllNamesByType(type));

			ArrayList<String> allNames = new ArrayList<String>();

			for (int i = 0; i < result.size(); i++) {
				allNames.add(result.get(i).get(0).toString());
			}

			return allNames;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int updateMember(Connection connection, String id, String name) {
		try {
			return new DatabaseExecutor().executeUpdate(connection, StatementBuilder.updateMember(id, name));
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static int updateFather(Connection connection, String id, String notes) {
		try {
			return new DatabaseExecutor().executeUpdate(connection, StatementBuilder.updateFather(id, notes));
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static int updateProfile(Connection connection, String id, String birthday, String nationalId,
			String education, String eccEducation, String job, String courses, String skills) {
		try {
			return new DatabaseExecutor().executeUpdate(connection, StatementBuilder.updateProfile(id,
					birthday, nationalId, education, eccEducation, job, courses, skills));
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static int updateContact(Connection connection, String id, String phone, String mobile1,
			String mobile2, String addressNum, String addressStreet, String addressRegion,
			String addressDistrict, String addressFree, String email) {
		try {
			ArrayList<ArrayList<Object>> result;

			if (Validator.validateString(addressStreet)) {
				result = new DatabaseExecutor().executeQuery(connection,
						StatementBuilder.selectStreetByName(addressStreet));
				if (result.size() < 1) {
					new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.insertNewStreet(addressStreet));
				}
			}

			if (Validator.validateString(addressRegion)) {
				result = new DatabaseExecutor().executeQuery(connection,
						StatementBuilder.selectRegionByName(addressRegion));
				if (result.size() < 1) {
					new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.insertNewRegion(addressRegion));
				}
			}

			if (Validator.validateString(addressDistrict)) {
				result = new DatabaseExecutor().executeQuery(connection,
						StatementBuilder.selectDistrictByName(addressDistrict));
				if (result.size() < 1) {
					new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.insertNewDistrict(addressDistrict));
				}
			}

			return new DatabaseExecutor().executeUpdate(connection, StatementBuilder.updateContact(id, phone,
					mobile1, mobile2, addressNum, addressStreet, addressRegion, addressDistrict, addressFree,
					email));
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static int newMinister(Connection connection, String id, String notes) {
		try {
			return new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.insertMinister(id, notes));
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static int updateMinister(Connection connection, String id, String notes) {
		try {
			return new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateMinister(id, notes));
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static int newChild(Connection connection, String id, String notes) {
		try {
			return new DatabaseExecutor().executeUpdate(connection, StatementBuilder.insertChild(id, notes));
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static int updateChild(Connection connection, String id, String notes) {
		try {
			return new DatabaseExecutor().executeUpdate(connection, StatementBuilder.updateChild(id, notes));
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static int deleteMember(Connection connection, String id) {
		try {
			return new DatabaseExecutor().executeUpdate(connection, StatementBuilder.deleteMember(id));
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static ArrayList<String> getAllClasses() {
		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectAllClasses());

			ArrayList<String> allClasses = new ArrayList<String>();

			for (int i = 0; i < result.size(); i++) {
				allClasses.add(result.get(i).get(0).toString());
			}

			return allClasses;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<String> getAllClassesExcludingSomeClassOfSubClass(String subClassName) {
		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectClassesExcludingSomeClassOfSubClass(subClassName));

			ArrayList<String> allClasses = new ArrayList<String>();

			for (int i = 0; i < result.size(); i++) {
				allClasses.add(result.get(i).get(0).toString());
			}

			return allClasses;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<String> getAllSubClassesExcludingSomeClass(String className) {
		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectSubClassesExcludingSomeClass(className));

			ArrayList<String> allClasses = new ArrayList<String>();

			for (int i = 0; i < result.size(); i++) {
				allClasses.add(result.get(i).get(0).toString());
			}

			return allClasses;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<String> getAllSubClassesExcludingSomeSubclass(String className) {
		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectSubClassesExcludingSomeSubclass(className));

			ArrayList<String> allClasses = new ArrayList<String>();

			for (int i = 0; i < result.size(); i++) {
				allClasses.add(result.get(i).get(0).toString());
			}

			return allClasses;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<String> getAllYears() {
		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectAllYears());

			ArrayList<String> allYears = new ArrayList<String>();

			for (int i = 0; i < result.size(); i++) {
				allYears.add(result.get(i).get(0).toString());
			}

			return allYears;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<String> getAllFamilies() {
		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectAllFamilies());

			ArrayList<String> allFamilies = new ArrayList<String>();

			for (int i = 0; i < result.size(); i++) {
				allFamilies.add(result.get(i).get(0).toString());
			}

			return allFamilies;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<String> getAllSections() {
		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<String> sections = new ArrayList<String>();
			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectAllSections());
			for (int i = 0; i < result.size(); i++) {
				sections.add(result.get(i).get(0).toString());
			}
			return sections;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static String getChildSubclass(String childName) {
		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String subclass = null;
			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectChildSubclass(childName));

			if (result.size() == 1) {
				subclass = result.get(0).get(0).toString();
			}

			return subclass;

		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<String> getFridays(String dateFromStr, String dateToStr) {

		ArrayList<String> fridays = new ArrayList<String>();
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		Date dateFrom = getDateByString(dateFromStr);
		Date dateTo = getDateByString(dateToStr);

		Calendar calendarFrom = Calendar.getInstance();
		calendarFrom.setTime(dateFrom);

		Calendar calendarTo = Calendar.getInstance();
		calendarTo.setTime(dateTo);
		calendarTo.add(Calendar.DATE, 1);

		while (calendarTo.after(calendarFrom)) {
			if (calendarFrom.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
				fridays.add(dateFormat.format(calendarFrom.getTime()));
			}
			calendarFrom.add(Calendar.DATE, 1);
		}

		return fridays;
	}

	public static Date getDateByString(String dateStr) {
		DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (Exception e) {
			Logger.exception(e);
		}
		return date;
	}

	public static int reactivateMember(HashMap<String, String> requestData) {
		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String id = requestData.get("id");

			if (!Validator.validateString(id)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			int result = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.reactivateMember(id));

			result = CommonsHandler.validateResults(result);

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
