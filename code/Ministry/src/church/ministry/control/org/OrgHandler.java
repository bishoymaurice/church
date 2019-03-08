package church.ministry.control.org;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import church.ministry.control.commons.CommonsHandler;
import church.ministry.control.conf.Constants;
import church.ministry.control.ecode.EMapper;
import church.ministry.control.log.Logger;
import church.ministry.control.reports.EftqadReportHandler;
import church.ministry.model.oracle.DatabaseConnectionManager;
import church.ministry.model.oracle.DatabaseExecutor;
import church.ministry.model.oracle.StatementBuilder;
import church.ministry.util.Validator;

public class OrgHandler implements Serializable {

	private static final long serialVersionUID = 7122148902037761408L;

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

	public static int newSection(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String name = requestData.get("name");

			if (!Validator.validateString(name)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			ArrayList<ArrayList<Object>> nameResult = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectSectionByName(name));
			if (nameResult.size() > 0) {
				return EMapper.ECODE_ALREADY_EXISTS;
			}

			int newSectionResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.insertNewSection(name));

			int result = CommonsHandler.validateResults(newSectionResult, 1);

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

	public static int updateSection(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String oldName = requestData.get("oldName");
			String newName = requestData.get("newName");

			if (!Validator.validateString(oldName, newName)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			if (oldName.equals(newName)) {
				return EMapper.ECODE_NO_UPDATE_NEEDED;
			}

			ArrayList<ArrayList<Object>> nameResult = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectSectionByName(newName));
			if (nameResult.size() > 0) {
				return EMapper.ECODE_ALREADY_EXISTS;
			}

			int updateSectionResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateSection(oldName, newName));

			int result = CommonsHandler.validateResults(updateSectionResult, 1);

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

	public static int deleteSection(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String name = requestData.get("name");

			if (!Validator.validateString(name)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			int deleteSectionResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.deleteSection(name));

			int result = CommonsHandler.validateResults(deleteSectionResult, 1);

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

	// ////////////////////////////////////////

	public static ArrayList<String> getFamiliesBySection(String sectionName) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<String> families = new ArrayList<String>();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectFamiliesBySection(sectionName));

			for (int i = 0; i < result.size(); i++) {
				families.add(result.get(i).get(0).toString());
			}

			return families;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int newFamily(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String name = requestData.get("name");
			String sectionName = requestData.get("sectionName");

			if (!Validator.validateString(name, sectionName)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			ArrayList<ArrayList<Object>> nameResult = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectFamilyByName(name));
			if (nameResult.size() > 0) {
				return EMapper.ECODE_ALREADY_EXISTS;
			}

			int newFamilyResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.insertNewFamily(name, sectionName));

			int result = CommonsHandler.validateResults(newFamilyResult, 1);

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

	public static int updateFamily(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String oldName = requestData.get("oldName");
			String newName = requestData.get("newName");

			if (!Validator.validateString(oldName, newName)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			if (oldName.equals(newName)) {
				return EMapper.ECODE_NO_UPDATE_NEEDED;
			}

			ArrayList<ArrayList<Object>> nameResult = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectFamilyByName(newName));
			if (nameResult.size() > 0) {
				return EMapper.ECODE_ALREADY_EXISTS;
			}

			int updateFamilyResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateFamily(oldName, newName));

			int result = CommonsHandler.validateResults(updateFamilyResult, 1);

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

	public static int deleteFamily(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String name = requestData.get("name");

			if (!Validator.validateString(name)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			int deleteFamilyResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.deleteFamily(name));

			int result = CommonsHandler.validateResults(deleteFamilyResult, 1);

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

	// ////////////////////////////////////////

	public static ArrayList<String> getYearsByFamily(String familyName) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<String> years = new ArrayList<String>();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectYearsByFamily(familyName));

			for (int i = 0; i < result.size(); i++) {
				years.add(result.get(i).get(0).toString());
			}

			return years;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int newYear(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String name = requestData.get("name");
			String familyName = requestData.get("familyName");

			if (!Validator.validateString(name, familyName)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			ArrayList<ArrayList<Object>> nameResult = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectYearByName(name));
			if (nameResult.size() > 0) {
				return EMapper.ECODE_ALREADY_EXISTS;
			}

			int newYearResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.insertNewYear(name, familyName));

			int result = CommonsHandler.validateResults(newYearResult, 1);

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

	public static int updateYear(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String oldName = requestData.get("oldName");
			String newName = requestData.get("newName");

			if (!Validator.validateString(oldName, newName)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			if (oldName.equals(newName)) {
				return EMapper.ECODE_NO_UPDATE_NEEDED;
			}

			ArrayList<ArrayList<Object>> nameResult = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectYearByName(newName));
			if (nameResult.size() > 0) {
				return EMapper.ECODE_ALREADY_EXISTS;
			}

			int updateYearResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateYear(oldName, newName));

			int result = CommonsHandler.validateResults(updateYearResult, 1);

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

	public static int deleteYear(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String name = requestData.get("name");

			if (!Validator.validateString(name)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			int deleteYearResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.deleteYear(name));

			int result = CommonsHandler.validateResults(deleteYearResult, 1);

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

	// ////////////////////////////////////////

	public static ArrayList<String> getClassesByYear(String yearName) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<String> classes = new ArrayList<String>();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectClassesByYear(yearName));

			for (int i = 0; i < result.size(); i++) {
				classes.add(result.get(i).get(0).toString());
			}

			return classes;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int newClass(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String name = requestData.get("name");
			String yearName = requestData.get("yearName");

			if (!Validator.validateString(name, yearName)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			ArrayList<ArrayList<Object>> nameResult = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectClassByName(name));
			if (nameResult.size() > 0) {
				return EMapper.ECODE_ALREADY_EXISTS;
			}

			int newClassResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.insertNewClass(name, yearName));

			int result = CommonsHandler.validateResults(newClassResult, 1);

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

	public static int newSubClass(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String name = requestData.get("name");
			String className = requestData.get("className");

			if (!Validator.validateString(name, className)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			ArrayList<ArrayList<Object>> nameResult = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectSubClassByName(name));
			if (nameResult.size() > 0) {
				return EMapper.ECODE_ALREADY_EXISTS;
			}

			int newClassResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.insertNewSubClass(name, className));

			int result = CommonsHandler.validateResults(newClassResult, 1);

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

	public static int updateClass(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String oldName = requestData.get("oldName");
			String newName = requestData.get("newName");

			if (!Validator.validateString(oldName, newName)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			if (oldName.equals(newName)) {
				return EMapper.ECODE_NO_UPDATE_NEEDED;
			}

			ArrayList<ArrayList<Object>> nameResult = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectClassByName(newName));
			if (nameResult.size() > 0) {
				return EMapper.ECODE_ALREADY_EXISTS;
			}

			int updateClassResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateClass(oldName, newName));

			int result = CommonsHandler.validateResults(updateClassResult, 1);

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

	public static int updateSubClass(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String oldName = requestData.get("oldName");
			String newName = requestData.get("newName");

			if (!Validator.validateString(oldName, newName)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			if (oldName.equals(newName)) {
				return EMapper.ECODE_NO_UPDATE_NEEDED;
			}

			ArrayList<ArrayList<Object>> nameResult = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectSubClassByName(newName));
			if (nameResult.size() > 0) {
				return EMapper.ECODE_ALREADY_EXISTS;
			}

			int updateClassResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateSubClass(oldName, newName));

			int result = CommonsHandler.validateResults(updateClassResult, 1);

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

	public static int deleteClass(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String name = requestData.get("name");

			if (!Validator.validateString(name)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			int deleteClassResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.deleteClass(name));

			int result = CommonsHandler.validateResults(deleteClassResult, 1);

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

	public static int deleteSubClass(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String subclassName = requestData.get("name");

			if (!Validator.validateString(subclassName)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			ArrayList<ArrayList<Object>> children = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectChildrenBySubClass(subclassName));

			if (children.size() > 0) {
				return EMapper.ECODE_DELETE_SUBCLASS_HAS_CHILDREN;
			}

			int removeSubclassMinister = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.deleteSubclassMinister(subclassName));

			if (removeSubclassMinister >= 0) {
				removeSubclassMinister = 1;
			}

			int deleteSubClassResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.deleteSubClass(subclassName));

			int result = CommonsHandler.validateResults(deleteSubClassResult, 1, removeSubclassMinister, 1);

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

	// ////////////////////////////////////////

	public static ArrayList<String> getSubClassByClass(String className) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<String> subClasses = new ArrayList<String>();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectSubClassByClass(className));

			for (int i = 0; i < result.size(); i++) {
				subClasses.add(result.get(i).get(0).toString());
			}

			return subClasses;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static String getMinisterBySubClass(String subClassName) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String ministerName = null;

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinisterBySubClass(subClassName));

			if (result.size() == 1) {
				ministerName = String.valueOf(result.get(0).get(0));
			}

			return ministerName;

		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<String> getChildrenBySubClass(String subClassName) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<String> children = new ArrayList<String>();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectChildrenBySubClass(subClassName));

			for (int i = 0; i < result.size(); i++) {
				children.add(result.get(i).get(0).toString());
			}

			return children;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int assignSubClassToClass(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String className = requestData.get("className");
			String subClassName = requestData.get("subClassName");
			String newMinisterName = requestData.get("newMinisterName");
			String ministerAssignmentOption = requestData.get(Constants.MINISTER_ASSIGNMENT_OPTION);

			if (!Validator.validateString(className, subClassName)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			if (ministerAssignmentOption.equals(Constants.MINISTER_ASSIGNMENT_OPTION_S)) {

				int updateClassResult = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateClassOfSubClass(className, subClassName));

				int result = CommonsHandler.validateResults(updateClassResult, 1);

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
			} else if (ministerAssignmentOption.equals(Constants.MINISTER_ASSIGNMENT_OPTION_R)) {

				String subClassMinister = OrgHandler.getMinisterBySubClass(subClassName);

				ArrayList<String> children = OrgHandler.getChildrenBySubClass(subClassName);

				for (int i = 0; i < children.size(); i++) {
					HashMap<String, String> requestData1 = new HashMap<String, String>();
					requestData1.put("childName", children.get(i));
					requestData1.put("forMinisterChildAssingedOnly", "forMinisterChildAssingedOnly");
					OrgHandler.unassignChild(requestData1);
				}

				int updateClassResult = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateClassOfSubClass(className, subClassName));

				int removeMinisterFromSubClass = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.removeMinisterFromSubClass(subClassName));

				if (removeMinisterFromSubClass >= 0) {
					removeMinisterFromSubClass = 1;
				}

				int unassignChildrenResult = EMapper.ECODE_SUCCESS;

				if (Validator.validateString(subClassMinister)) {
					unassignChildrenResult = OrgHandler.unassignChildrenByMinisterName(connection, false,
							subClassMinister);
				}

				int result = CommonsHandler.validateResults(updateClassResult, 1, removeMinisterFromSubClass,
						1, unassignChildrenResult, EMapper.ECODE_SUCCESS);

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
			} else if (ministerAssignmentOption.equals(Constants.MINISTER_ASSIGNMENT_OPTION_N)) {

				int updateClassResult = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateClassOfSubClass(className, subClassName));

				int removeMinisterFromSubClass = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.removeMinisterOfSubClass(subClassName));

				if (removeMinisterFromSubClass >= 0) {
					removeMinisterFromSubClass = 1;
				}

				int updateMinisterSubClass = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateMinisterSubClass(subClassName, newMinisterName));

				int unassignChildrenResult = OrgHandler.unassignChildrenByMinisterName(connection, false,
						newMinisterName);

				int result = CommonsHandler.validateResults(updateClassResult, 1, updateMinisterSubClass, 1,
						removeMinisterFromSubClass, 1, unassignChildrenResult, EMapper.ECODE_SUCCESS);

				if (result == EMapper.ECODE_SUCCESS) {
					if (new DatabaseExecutor().commit(connection)) {

						int assignChildrenToAnotherMinister = OrgHandler
								.assignChildrenOfSubClassToAnotherMinister(connection, subClassName,
										newMinisterName);

						result = CommonsHandler.validateResults(assignChildrenToAnotherMinister,
								EMapper.ECODE_SUCCESS);

					} else {
						new DatabaseExecutor().rollback(connection);
					}
				} else {
					new DatabaseExecutor().rollback(connection);
				}

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
			} else {
				return EMapper.ECODE_FAILURE;
			}
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	// ////////////////////////////////////////

	public static ArrayList<String> getChildrenByMinister(String ministerName) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<String> children = new ArrayList<String>();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectChildrenByMinister(ministerName));

			for (int i = 0; i < result.size(); i++) {
				children.add(result.get(i).get(1).toString());
			}

			return children;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<ArrayList<Object>> getChildrenByMinisterAndDate(String ministerName, String date) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectChildrenByMinisterAndDate(ministerName, date));
			return result;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int assignChild(Connection connection, boolean ifHandleConnection,
			HashMap<String, String> requestData) {

		try {
			String subClassName = requestData.get("subClassName");

			String ministerName = OrgHandler.getMinisterBySubClass(subClassName);

			String childrenNamesString = requestData.get("childName").replace("\r", "");

			String childrenNames[] = childrenNamesString.split("\n");

			int[] results = new int[childrenNames.length];

			for (int i = 0; i < childrenNames.length; i++) {
				if (!Validator.validateString(subClassName, childrenNames[i])) {
					results[i] = EMapper.ECODE_INVALID_INPUT_DATA;
					continue;
				}

				int result = EMapper.ECODE_SUCCESS;

				if (Validator.validateString(ministerName)) {

					int unassignChildFromMinisterResult = new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.deleteMinisterChildAssignedForSameChildAndDay(childrenNames[i]));

					if (unassignChildFromMinisterResult >= 0) {
						unassignChildFromMinisterResult = 1;
					}

					int assignChildToMinisterResult = new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.insertMinisterChildAssigned(ministerName, childrenNames[i]));

					result = CommonsHandler.validateResults(unassignChildFromMinisterResult, 1,
							assignChildToMinisterResult, 1);
				} else {
					HashMap<String, String> requestData1 = new HashMap<String, String>();
					requestData1.put("childName", childrenNames[i]);
					requestData1.put("forMinisterChildAssingedOnly", "forMinisterChildAssingedOnly");
					unassignChild(requestData1);
				}

				int assignChildToSubclass = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateChildSubClass(subClassName, childrenNames[i]));

				if (assignChildToSubclass < 1) {
					result = EMapper.ECODE_FAILURE;
				}

				results[i] = result;
			}

			for (int i = 0; i < results.length; i++) {
				if (results[i] != EMapper.ECODE_SUCCESS) {
					return results[i];
				}
			}

			if (ifHandleConnection) {
				if (new DatabaseExecutor().commit(connection)) {
					DatabaseConnectionManager.releaseConnection(connection);
				} else {
					new DatabaseExecutor().rollback(connection);
					DatabaseConnectionManager.releaseConnection(connection);
					return EMapper.ECODE_FAILURE;
				}
			}

			return EMapper.ECODE_SUCCESS;
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static int unassignChildrenByMinisterName(Connection connection, boolean ifHandleConnection,
			String ministerName) {

		try {

			int unassignChildren = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateAllMinisterChildAssignedByMinisterName(ministerName));

			if (unassignChildren >= 0) {
				unassignChildren = 0;
			}

			int result = CommonsHandler.validateResults(unassignChildren, 0);

			if (ifHandleConnection) {
				if (result == EMapper.ECODE_SUCCESS) {
					if (new DatabaseExecutor().commit(connection)) {
					} else {
						new DatabaseExecutor().rollback(connection);
					}
				} else {
					new DatabaseExecutor().rollback(connection);
				}
			}

			return result;

		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static int unassignChild(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String childName = requestData.get("childName");
			String forMinisterChildAssingedOnly = requestData.get("forMinisterChildAssingedOnly");

			if (!Validator.validateString(childName)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			int unassignChildFromMinister = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateAllMinisterChildAssignedByChildName(childName));

			if (unassignChildFromMinister >= 0) {
				unassignChildFromMinister = 1;
			}

			int unassignChildFromSubclass = 1;

			if (!Validator.validateString(forMinisterChildAssingedOnly)) {
				unassignChildFromSubclass = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.removeChildSubClass(childName));
			}

			int result = CommonsHandler.validateResults(unassignChildFromMinister, 1,
					unassignChildFromSubclass, 1);

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

	public static int assignClass(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String yearName = requestData.get("yearName");
			String className = requestData.get("className");

			if (!Validator.validateString(yearName, className)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			int assignClassResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateClassYear(yearName, className));

			int result = CommonsHandler.validateResults(assignClassResult, 1);

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

	public static int assignYear(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String familyName = requestData.get("familyName");
			String yearName = requestData.get("yearName");

			if (!Validator.validateString(familyName, yearName)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			int assignClassResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateYearFamily(familyName, yearName));

			int result = CommonsHandler.validateResults(assignClassResult, 1);

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

	public static int assignFamily(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String sectionName = requestData.get("sectionName");
			String familyName = requestData.get("familyName");

			if (!Validator.validateString(sectionName, familyName)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			int assignClassResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateFamilySection(sectionName, familyName));

			int result = CommonsHandler.validateResults(assignClassResult, 1);

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

	public static ArrayList<ArrayList<Object>> getChildrenByClassAndDate(String className, String date) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> ministersOfClass = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinistersByClass(className));

			ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();

			for (int i = 0; i < ministersOfClass.size(); i++) {
				ArrayList<ArrayList<Object>> temp = getChildrenByMinisterAndDate(
						ministersOfClass.get(i).get(0).toString(), date);

				for (int j = 0; j < temp.size(); j++) {
					result.add(temp.get(j));
				}
			}

			return result;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<ArrayList<Object>> getChildrenByYearAndDate(String yearName, String date) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> ministersOfYear = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinistersByYear(yearName));

			ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();

			for (int i = 0; i < ministersOfYear.size(); i++) {
				ArrayList<ArrayList<Object>> temp = getChildrenByMinisterAndDate(ministersOfYear.get(i)
						.get(0).toString(), date);

				for (int j = 0; j < temp.size(); j++) {
					result.add(temp.get(j));
				}
			}
			return result;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<ArrayList<Object>> getChildrenByFamilyAndDate(String familyName, String date) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> ministersOfFamily = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinistersByFamily(familyName));

			ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();

			for (int i = 0; i < ministersOfFamily.size(); i++) {
				ArrayList<ArrayList<Object>> temp = getChildrenByMinisterAndDate(ministersOfFamily.get(i)
						.get(0).toString(), date);

				for (int j = 0; j < temp.size(); j++) {
					result.add(temp.get(j));
				}
			}
			return result;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<ArrayList<Object>> getChildrenBySectionAndDate(String sectionName, String date) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> ministersOfSection = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinistersBySection(sectionName));

			ArrayList<ArrayList<Object>> result = new ArrayList<ArrayList<Object>>();

			for (int i = 0; i < ministersOfSection.size(); i++) {
				ArrayList<ArrayList<Object>> temp = getChildrenByMinisterAndDate(ministersOfSection.get(i)
						.get(0).toString(), date);

				for (int j = 0; j < temp.size(); j++) {
					result.add(temp.get(j));
				}
			}
			return result;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<String> getMinistryAttendantsIdsByDate(String date) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<String> attendants = new ArrayList<String>();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinistryAttendantsByDate(date));

			for (int i = 0; i < result.size(); i++) {
				attendants.add(result.get(i).get(0).toString());
			}

			return attendants;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<String> getMassAttendantsIdsByDate(String date) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<String> attendants = new ArrayList<String>();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMassAttendantsByDate(date));

			for (int i = 0; i < result.size(); i++) {
				attendants.add(result.get(i).get(0).toString());
			}

			return attendants;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static ArrayList<ArrayList<Object>> getFollowupDataByDate(String actionDate) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectFollowupDataByDate(actionDate));
			return result;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static boolean addRemoveChildMinistryAttendance(String childName, String actionDate,
			boolean ifAttendant, boolean ifFollowup) {

		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			if (ifAttendant && !ifFollowup) {
				int result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.insertChildMinistryAttendance(childName, actionDate));

				int meResult = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateMEAttendantsCount(childName, actionDate, ifAttendant));

				if (meResult == 0) {
					meResult = new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.insertMEByChildName(childName, actionDate));

					meResult = new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.updateMEAttendantsCount(childName, actionDate, ifAttendant));
				}

				int finalResult = CommonsHandler.validateResults(result, 1, meResult, 1);

				if (finalResult == EMapper.ECODE_SUCCESS) {
					if (new DatabaseExecutor().commit(connection)) {
						return true;
					} else {
						new DatabaseExecutor().rollback(connection);
						return false;
					}
				} else {
					new DatabaseExecutor().rollback(connection);
					return false;
				}

			}

			else if (!ifAttendant && !ifFollowup) {
				int result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.deleteChildMinistryAttendance(childName, actionDate));

				int meResult = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateMEAttendantsCount(childName, actionDate, ifAttendant));

				if (meResult == 0) {
					meResult = new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.insertMEByChildName(childName, actionDate));

					meResult = new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.updateMEAttendantsCount(childName, actionDate, ifAttendant));
				}

				int finalResult = CommonsHandler.validateResults(result, 1, meResult, 1);

				if (finalResult == EMapper.ECODE_SUCCESS) {
					if (new DatabaseExecutor().commit(connection)) {
						return true;
					} else {
						new DatabaseExecutor().rollback(connection);
						return false;
					}
				} else {
					new DatabaseExecutor().rollback(connection);
					return false;
				}
			}

			else if (!ifAttendant && ifFollowup) {
				int result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.deleteChildMinistryAttendance(childName, actionDate));

				int meResult = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateMEAttendantsCount(childName, actionDate, ifAttendant));

				if (meResult == 0) {
					meResult = new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.insertMEByChildName(childName, actionDate));

					meResult = new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.updateMEAttendantsCount(childName, actionDate, ifAttendant));
				}

				int me1Result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateMEAttendantsFollowupCount(childName, actionDate, !ifFollowup));

				int me2Result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateMEFollowupCount(childName, actionDate, ifFollowup));

				int finalResult = CommonsHandler.validateResults(result, 1, meResult, 1, me1Result, 1,
						me2Result, 1);

				if (finalResult == EMapper.ECODE_SUCCESS) {
					if (new DatabaseExecutor().commit(connection)) {
						return true;
					} else {
						new DatabaseExecutor().rollback(connection);
						return false;
					}
				} else {
					new DatabaseExecutor().rollback(connection);
					return false;
				}

			}

			if (ifAttendant && ifFollowup) {
				int result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.insertChildMinistryAttendance(childName, actionDate));

				int meResult = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateMEAttendantsCount(childName, actionDate, ifAttendant));

				if (meResult == 0) {
					meResult = new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.insertMEByChildName(childName, actionDate));

					meResult = new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.updateMEAttendantsCount(childName, actionDate, ifAttendant));
				}

				int me1Result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateMEAttendantsFollowupCount(childName, actionDate, ifFollowup));

				int me2Result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateMEFollowupCount(childName, actionDate, !ifFollowup));

				int finalResult = CommonsHandler.validateResults(result, 1, meResult, 1, me1Result, 1,
						me2Result, 1);

				if (finalResult == EMapper.ECODE_SUCCESS) {
					if (new DatabaseExecutor().commit(connection)) {
						return true;
					} else {
						new DatabaseExecutor().rollback(connection);
						return false;
					}
				} else {
					new DatabaseExecutor().rollback(connection);
					return false;
				}

			}

			return false;
		} catch (Exception e) {
			Logger.exception(e);
			return false;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static boolean addRemoveChildMassAttendance(String childName, String actionDate,
			boolean ifAttendant) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			if (ifAttendant) {
				int result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.insertChildMassAttendance(childName, actionDate));

				int finalResult = CommonsHandler.validateResults(result, 1);

				if (finalResult == EMapper.ECODE_SUCCESS) {
					if (new DatabaseExecutor().commit(connection)) {
						return true;
					} else {
						new DatabaseExecutor().rollback(connection);
						return false;
					}
				} else {
					new DatabaseExecutor().rollback(connection);
					return false;
				}

			} else {
				int result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.deleteChildMassAttendance(childName, actionDate));

				int finalResult = CommonsHandler.validateResults(result, 1);

				if (finalResult == EMapper.ECODE_SUCCESS) {
					if (new DatabaseExecutor().commit(connection)) {
						return true;
					} else {
						new DatabaseExecutor().rollback(connection);
						return false;
					}
				} else {
					new DatabaseExecutor().rollback(connection);
					return false;
				}
			}

		} catch (Exception e) {
			Logger.exception(e);
			return false;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static boolean addRemoveChildFollowup(String childName, String actionDate, boolean ifFollowup,
			boolean ifAttendant) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			if (ifFollowup && !ifAttendant) {
				int result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.insertChildFollowup(childName, actionDate));

				int meResult = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateMEFollowupCount(childName, actionDate, ifFollowup));

				if (meResult == 0) {
					meResult = new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.insertMEByChildName(childName, actionDate));

					meResult = new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.updateMEFollowupCount(childName, actionDate, ifFollowup));
				}

				int finalResult = CommonsHandler.validateResults(result, 1, meResult, 1);

				if (finalResult == EMapper.ECODE_SUCCESS) {
					if (new DatabaseExecutor().commit(connection)) {
						return true;
					} else {
						new DatabaseExecutor().rollback(connection);
						return false;
					}
				} else {
					new DatabaseExecutor().rollback(connection);
					return false;
				}

			}

			else if (!ifFollowup && !ifAttendant) {
				int result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.deleteChildFollowup(childName, actionDate));

				int meResult = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateMEFollowupCount(childName, actionDate, ifFollowup));

				if (meResult == 0) {
					meResult = new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.insertMEByChildName(childName, actionDate));

					meResult = new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.updateMEFollowupCount(childName, actionDate, ifFollowup));
				}

				int finalResult = CommonsHandler.validateResults(result, 1, meResult, 1);

				if (finalResult == EMapper.ECODE_SUCCESS) {
					if (new DatabaseExecutor().commit(connection)) {
						return true;
					} else {
						new DatabaseExecutor().rollback(connection);
						return false;
					}
				} else {
					new DatabaseExecutor().rollback(connection);
					return false;
				}
			}

			else if (!ifFollowup && ifAttendant) {
				int result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.deleteChildFollowup(childName, actionDate));

				int meResult = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateMEAttendantsFollowupCount(childName, actionDate, ifFollowup));

				if (meResult == 0) {
					meResult = new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.insertMEByChildName(childName, actionDate));

					meResult = new DatabaseExecutor().executeUpdate(connection, StatementBuilder
							.updateMEAttendantsFollowupCount(childName, actionDate, ifFollowup));
				}

				int finalResult = CommonsHandler.validateResults(result, 1, meResult, 1);

				if (finalResult == EMapper.ECODE_SUCCESS) {
					if (new DatabaseExecutor().commit(connection)) {
						return true;
					} else {
						new DatabaseExecutor().rollback(connection);
						return false;
					}
				} else {
					new DatabaseExecutor().rollback(connection);
					return false;
				}
			}

			else if (ifFollowup && ifAttendant) {
				int result = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.insertChildFollowup(childName, actionDate));

				int meResult = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.updateMEAttendantsFollowupCount(childName, actionDate, ifFollowup));

				if (meResult == 0) {
					meResult = new DatabaseExecutor().executeUpdate(connection,
							StatementBuilder.insertMEByChildName(childName, actionDate));

					meResult = new DatabaseExecutor().executeUpdate(connection, StatementBuilder
							.updateMEAttendantsFollowupCount(childName, actionDate, ifFollowup));
				}

				int finalResult = CommonsHandler.validateResults(result, 1, meResult, 1);

				if (finalResult == EMapper.ECODE_SUCCESS) {
					if (new DatabaseExecutor().commit(connection)) {
						return true;
					} else {
						new DatabaseExecutor().rollback(connection);
						return false;
					}
				} else {
					new DatabaseExecutor().rollback(connection);
					return false;
				}
			}

			return false;
		} catch (Exception e) {
			Logger.exception(e);
			return false;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static boolean updateFollowupBy(String childName, String actionDate, String followupBy) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			int result = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateFollowupBy(childName, actionDate, followupBy));

			int finalResult = CommonsHandler.validateResults(result, 1);

			if (finalResult == EMapper.ECODE_SUCCESS) {
				if (new DatabaseExecutor().commit(connection)) {
					return true;
				} else {
					new DatabaseExecutor().rollback(connection);
					return false;
				}
			} else {
				new DatabaseExecutor().rollback(connection);
				return false;
			}

		} catch (Exception e) {
			Logger.exception(e);
			return false;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static boolean updateFollowupComment(String childName, String actionDate, String followupComment) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			followupComment = followupComment.trim();

			int updateCommentResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateFollowupCommentCount(followupComment));

			if (updateCommentResult == 0) {
				updateCommentResult = new DatabaseExecutor().executeUpdate(connection,
						StatementBuilder.insertNewFollowupComment(followupComment));
			}

			int updateResult = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateFollowupComment(childName, actionDate, followupComment));

			int finalResult = CommonsHandler.validateResults(updateResult, 1, updateCommentResult, 1);

			if (finalResult == EMapper.ECODE_SUCCESS) {
				if (new DatabaseExecutor().commit(connection)) {
					return true;
				} else {
					new DatabaseExecutor().rollback(connection);
					return false;
				}
			} else {
				new DatabaseExecutor().rollback(connection);
				return false;
			}

		} catch (Exception e) {
			Logger.exception(e);
			return false;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static String[] generateEftqadReportForFamily(String familyName, String goDate) {

		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> ministers = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinistersByFamily(familyName));

			String[] reportFilesPaths = new String[ministers.size()];

			for (int i = 0; i < reportFilesPaths.length; i++) {
				reportFilesPaths[i] = OrgHandler.generateEftqadReportPDF(ministers.get(i).get(0).toString(),
						goDate);
			}

			return reportFilesPaths;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static String[] generateEftqadReportForSection(String sectionName, String goDate) {

		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> ministers = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinistersBySection(sectionName));

			String[] reportFilesPaths = new String[ministers.size()];

			for (int i = 0; i < reportFilesPaths.length; i++) {
				reportFilesPaths[i] = OrgHandler.generateEftqadReportPDF(ministers.get(i).get(0).toString(),
						goDate);
			}

			return reportFilesPaths;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static String[] generateEftqadReportForYear(String yearName, String goDate) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> ministers = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinistersByYear(yearName));

			String[] reportFilesPaths = new String[ministers.size()];

			for (int i = 0; i < reportFilesPaths.length; i++) {
				reportFilesPaths[i] = OrgHandler.generateEftqadReportPDF(ministers.get(i).get(0).toString(),
						goDate);
			}

			return reportFilesPaths;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static String generateEftqadReportForSubClass(String classSubName, String goDate) {

		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinisterBySubClass(classSubName));

			String reportFilePath = null;

			if (result.size() > 0) {
				String ministerName = result.get(0).get(0).toString();
				reportFilePath = OrgHandler.generateEftqadReportPDF(ministerName, goDate);
			}

			return reportFilePath;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static String[] generateEftqadReportForClass(String className, String goDate) {

		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> ministers = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinistersByClass(className));

			String[] reportFilesPaths = new String[ministers.size()];

			for (int i = 0; i < reportFilesPaths.length; i++) {
				reportFilesPaths[i] = OrgHandler.generateEftqadReportPDF(ministers.get(i).get(0).toString(),
						goDate);
			}

			return reportFilesPaths;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static String generateEftqadReportPDF(String ministerName, String goDate) {

		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> reportData;
			HashMap<String, String> eftqadDataMap;
			ArrayList<HashMap<String, String>> attendantsMaps = new ArrayList<>();
			ArrayList<HashMap<String, String>> absentsMaps = new ArrayList<>();

			ArrayList<ArrayList<Object>> children = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectChildrenByMinisterAndDate(ministerName, goDate));

			ArrayList<ArrayList<Object>> classNameResult = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinisterClass(ministerName));

			String className = classNameResult.get(0).get(0).toString();

			for (int i = 0; i < children.size(); i++) {
				reportData = new DatabaseExecutor().executeQuery(connection,
						StatementBuilder.selectChildEteqadData(children.get(i).get(0).toString(), goDate));

				eftqadDataMap = new HashMap<String, String>();
				eftqadDataMap.put("name", reportData.get(0).get(1).toString());
				eftqadDataMap.put("phone", reportData.get(0).get(2).toString());
				eftqadDataMap.put("address", reportData.get(0).get(3).toString());
				eftqadDataMap.put("total_absence", reportData.get(0).get(5).toString());
				eftqadDataMap.put("total_followup_phone", reportData.get(0).get(6).toString());
				eftqadDataMap.put("total_followup_home", reportData.get(0).get(7).toString());
				eftqadDataMap.put("consecutive_mass_absence", reportData.get(0).get(8).toString());
				eftqadDataMap.put("today_mass_attendance", reportData.get(0).get(9).toString());
				eftqadDataMap.put("consecutive_ministry_absence", reportData.get(0).get(10).toString());
				eftqadDataMap.put("consecutive_followup", reportData.get(0).get(11).toString());

				if (reportData.get(0).get(4).toString().equals("0")) {
					absentsMaps.add(eftqadDataMap);
				} else {
					attendantsMaps.add(eftqadDataMap);
				}

			}

			String reportFilePath = EftqadReportHandler.savePDF(attendantsMaps, absentsMaps, goDate,
					className, ministerName);

			return reportFilePath;

		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int assignChildrenOfSubClassToAnotherMinister(Connection connection, String subClassName,
			String newMinisterName) {

		try {

			if (!Validator.validateString(subClassName, newMinisterName)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			ArrayList<String> childrenOfSubClass = OrgHandler.getChildrenBySubClass(subClassName);

			int assignMinisterBChildrenResult = 0;

			for (int i = 0; i < childrenOfSubClass.size(); i++) {
				HashMap<String, String> assignChildToMinisterReqData = new HashMap<String, String>();
				assignChildToMinisterReqData.put("childName", childrenOfSubClass.get(i));
				assignChildToMinisterReqData.put("ministerName", newMinisterName);
				assignChildToMinisterReqData.put("subClassName", subClassName);
				assignMinisterBChildrenResult += OrgHandler.assignChild(connection, false,
						assignChildToMinisterReqData);
			}

			int result = CommonsHandler.validateResults(assignMinisterBChildrenResult, EMapper.ECODE_SUCCESS);

			return result;
		} catch (Exception e) {
			Logger.exception(e);
			return EMapper.ECODE_FAILURE;
		}
	}

	public static String getSubClassMinister(String subClassName) {
		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String ministerName = null;

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectSubClassMinister(subClassName));

			if (result.size() == 1) {
				ministerName = String.valueOf(result.get(0).get(0));
			}

			return ministerName;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static String getSubClassByMinister(String ministerName) {
		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String subClass = null;

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectSubClassByMinister(ministerName));

			if (result.size() == 1) {
				subClass = String.valueOf(result.get(0).get(0));
			}

			return subClass;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int changeSubClassMinister(HashMap<String, String> requestData) {

		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			String subClassName = requestData.get("subClassName");
			String ministerName = requestData.get("ministerName");

			if (!Validator.validateString(subClassName, ministerName)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			ArrayList<String> currentChildrenOfMinister = OrgHandler.getChildrenByMinister(ministerName);

			for (int i = 0; i < currentChildrenOfMinister.size(); i++) {
				HashMap<String, String> requestData1 = new HashMap<String, String>();
				requestData1.put("childName", currentChildrenOfMinister.get(i));
				requestData1.put("forMinisterChildAssingedOnly", "forMinisterChildAssingedOnly");
				OrgHandler.unassignChild(requestData1);
			}

			OrgHandler.unassignChildrenByMinisterName(connection, true, ministerName);

			int removeMinisterFromSubClass = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.removeMinisterOfSubClass(subClassName));

			if (removeMinisterFromSubClass >= 0) {
				removeMinisterFromSubClass = 1;
			}

			int changeSubClassMinister = new DatabaseExecutor().executeUpdate(connection,
					StatementBuilder.updateSubClassMinister(subClassName, ministerName));

			int result1 = CommonsHandler.validateResults(removeMinisterFromSubClass, 1,
					changeSubClassMinister, 1);

			int assignChildrenResult = EMapper.ECODE_FAILURE;

			if (result1 == EMapper.ECODE_SUCCESS) {
				if (new DatabaseExecutor().commit(connection)) {
					assignChildrenResult = OrgHandler.assignChildrenOfSubClassToAnotherMinister(connection,
							subClassName, ministerName);
				} else {
					new DatabaseExecutor().rollback(connection);
				}
			} else {
				new DatabaseExecutor().rollback(connection);
			}

			if (assignChildrenResult == EMapper.ECODE_SUCCESS) {
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
