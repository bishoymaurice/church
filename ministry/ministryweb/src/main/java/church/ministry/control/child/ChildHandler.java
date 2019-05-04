package church.ministry.control.child;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import church.ministry.control.commons.CommonsHandler;
import church.ministry.control.ecode.EMapper;
import church.ministry.control.handler.RequestHandler;
import church.ministry.control.log.Logger;
import church.ministry.model.oracle.DatabaseConnectionManager;
import church.ministry.model.oracle.DatabaseExecutor;
import church.ministry.model.oracle.StatementBuilder;
import church.ministry.util.Mapper;
import church.ministry.util.Validator;

public class ChildHandler implements Serializable {

	private static final long serialVersionUID = -3937007804440576612L;

	public static String type = "child";

	public static int newChild(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String name = requestData.get("name");
			String addressNum = requestData.get("addressNum");
			String addressStreet = requestData.get("addressStreet");
			String addressRegion = requestData.get("addressRegion");
			String addressDistrict = requestData.get("addressDistrict");
			String addressFree = requestData.get("addressFree");
			String phone = requestData.get("phone");
			String mobile1 = requestData.get("mobile1");
			String mobile2 = requestData.get("mobile2");
			String birthday = requestData.get("birthday");
			String email = requestData.get("email");
			String nationalId = requestData.get("nationalId");
			String education = requestData.get("education");
			String eccEducation = requestData.get("eccEducation");
			String courses = requestData.get("courses");
			String skills = requestData.get("skills");
			String notes = requestData.get("notes");

			if (!Validator.validateString(name)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			if (name.split(" ").length < 3) {
				return EMapper.ECODE_INVALID_INPUT_DATA_NAME_3;
			}

			ArrayList<ArrayList<Object>> nameResult = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectNameByNameType(name, type));
			if (nameResult.size() > 0) {
				return EMapper.ECODE_ALREADY_EXISTS;
			}

			String id = new DatabaseExecutor().executeQuery(connection, StatementBuilder.selectNextFreeId())
					.get(0).get(0).toString();

			int newMemberResult = CommonsHandler.newMember(connection, id, type, name);
			int newChildResult = CommonsHandler.newChild(connection, id, notes);
			int newProfileResult = CommonsHandler.newProfile(connection, id, birthday, nationalId, education,
					eccEducation, null, courses, skills);
			int newContactResult = CommonsHandler.newContact(connection, id, phone, mobile1, mobile2,
					addressNum, addressStreet, addressRegion, addressDistrict, addressFree, email);

			int result = CommonsHandler.validateResults(newMemberResult, 1, newChildResult, 1,
					newProfileResult, 1, newContactResult, 1);

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

	public static HashMap<String, String> getChildData(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String childName = requestData.get("childName");

			HashMap<String, String> childData = new HashMap<String, String>();

			childData.put("name", childName);

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMemberByTypeAndName(type, childName));

			childData.put("id", result.get(0).get(0).toString());
			childData.put("activeDatetime", result.get(0).get(1).toString());
			childData.put("status", result.get(0).get(2).toString());

			result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectChildById(childData.get("id")));

			childData.put("notes", result.get(0).get(0).toString());

			result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectProfileById(childData.get("id")));

			childData.put("birthday", result.get(0).get(0).toString());
			childData.put("education", result.get(0).get(1).toString());
			childData.put("eccEducation", result.get(0).get(2).toString());
			childData.put("courses", result.get(0).get(4).toString());
			childData.put("skills", result.get(0).get(5).toString());
			childData.put("nationalId", result.get(0).get(6).toString());

			result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectContactById(childData.get("id")));

			childData.put("phone", result.get(0).get(0).toString());
			childData.put("mobile1", result.get(0).get(1).toString());
			childData.put("mobile2", result.get(0).get(2).toString());
			childData.put("addressNum", result.get(0).get(3).toString());
			childData.put("addressStreet", result.get(0).get(4).toString());
			childData.put("addressRegion", result.get(0).get(5).toString());
			childData.put("addressDistrict", result.get(0).get(6).toString());
			childData.put("addressFree", result.get(0).get(7).toString());
			childData.put("email", result.get(0).get(8).toString());

			result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinisterOfChild(childName));
			if (result.size() == 1) {
				childData.put("ministerName", result.get(0).get(1).toString());
			}

			result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectChildPositionInMinistry(childName));

			if (result.size() == 1) {
				childData.put("childPositionInMinistry", result.get(0).get(0).toString());
			}

			return childData;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static String getChildPositionInMinistry(String childName) {
		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectChildPositionInMinistry(childName));

			String childPositionInMinistry = null;

			if (result.size() == 1) {
				childPositionInMinistry = result.get(0).get(0).toString();
			}
			return childPositionInMinistry;

		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int updateChild(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String id = requestData.get("id");
			String name = requestData.get("name");
			String addressNum = requestData.get("addressNum");
			String addressStreet = requestData.get("addressStreet");
			String addressRegion = requestData.get("addressRegion");
			String addressDistrict = requestData.get("addressDistrict");
			String addressFree = requestData.get("addressFree");
			String phone = requestData.get("phone");
			String mobile1 = requestData.get("mobile1");
			String mobile2 = requestData.get("mobile2");
			String birthday = requestData.get("birthday");
			String email = requestData.get("email");
			String nationalId = requestData.get("nationalId");
			String education = requestData.get("education");
			String eccEducation = requestData.get("eccEducation");
			String courses = requestData.get("courses");
			String skills = requestData.get("skills");
			String notes = requestData.get("notes");

			if (!Validator.validateString(name)) {
				return EMapper.ECODE_INVALID_INPUT_DATA;
			}

			ArrayList<ArrayList<Object>> nameResult = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectNameByNameType_ExcludeId(name, type, id));

			if (nameResult.size() > 0) {
				return EMapper.ECODE_ALREADY_EXISTS;
			}

			int updateMemberResult = CommonsHandler.updateMember(connection, id, name);
			int updateChildResult = CommonsHandler.updateChild(connection, id, notes);
			int updateProfileResult = CommonsHandler.updateProfile(connection, id, birthday, nationalId,
					education, eccEducation, null, courses, skills);
			int updateContactResult = CommonsHandler.updateContact(connection, id, phone, mobile1, mobile2,
					addressNum, addressStreet, addressRegion, addressDistrict, addressFree, email);

			int result = CommonsHandler.validateResults(updateMemberResult, 1, updateChildResult, 1,
					updateProfileResult, 1, updateContactResult, 1);

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

	public static ArrayList<ArrayList<Object>> searchChild(HashMap<String, String> requestData,
			HashMap<String, String> selectData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String id = requestData.get("id");
			String name = requestData.get("name");
			String addressNum = requestData.get("addressNum");
			String addressStreet = requestData.get("addressStreet");
			String addressRegion = requestData.get("addressRegion");
			String addressDistrict = requestData.get("addressDistrict");
			String addressFree = requestData.get("addressFree");
			String phone = requestData.get("phone");
			String mobile1 = requestData.get("mobile1");
			String mobile2 = requestData.get("mobile2");
			String birthday = requestData.get("birthday");
			String email = requestData.get("email");
			String nationalId = requestData.get("nationalId");
			String education = requestData.get("education");
			String eccEducation = requestData.get("eccEducation");
			String courses = requestData.get("courses");
			String skills = requestData.get("skills");
			String notes = requestData.get("notes");
			String levelItem = requestData.get("levelItem");
			String levelValue = requestData.get("levelValue");

			String birthdayDay = requestData.get("birthdayDay");
			String birthdayMonth = requestData.get("birthdayMonth");
			String birthdayYear = requestData.get("birthdayYear");

			boolean ifSelectId = Mapper.mapBinary(selectData.get("id"));
			boolean ifSelectName = Mapper.mapBinary(selectData.get("name"));
			boolean ifSelectAddressNum = Mapper.mapBinary(selectData.get("addressNum"));
			boolean ifSelectAddressStreet = Mapper.mapBinary(selectData.get("addressStreet"));
			boolean ifSelectAddressRegion = Mapper.mapBinary(selectData.get("addressRegion"));
			boolean ifSelectAddressDistrict = Mapper.mapBinary(selectData.get("addressDistrict"));
			boolean ifSelectAddressFree = Mapper.mapBinary(selectData.get("addressFree"));
			boolean ifSelectPhone = Mapper.mapBinary(selectData.get("phone"));
			boolean ifSelectMobile1 = Mapper.mapBinary(selectData.get("mobile1"));
			boolean ifSelectMobile2 = Mapper.mapBinary(selectData.get("mobile2"));
			boolean ifSelectBirthday = Mapper.mapBinary(selectData.get("birthday"));
			boolean ifSelectEmail = Mapper.mapBinary(selectData.get("email"));
			boolean ifSelectNationalId = Mapper.mapBinary(selectData.get("nationalId"));
			boolean ifSelectEducation = Mapper.mapBinary(selectData.get("education"));
			boolean ifSelectEccEducation = Mapper.mapBinary(selectData.get("eccEducation"));
			boolean ifSelectCourses = Mapper.mapBinary(selectData.get("courses"));
			boolean ifSelectSkills = Mapper.mapBinary(selectData.get("skills"));
			boolean ifSelectNotes = Mapper.mapBinary(selectData.get("notes"));
			boolean ifSelectActiveDate = Mapper.mapBinary(selectData.get("activeDate"));
			boolean ifLevelSelected = Mapper.mapBinary(selectData.get("level"));
			boolean ifSectionSelected = Mapper.mapBinary(selectData.get("section"));
			boolean ifFamilySelected = Mapper.mapBinary(selectData.get("family"));
			boolean ifYearSelected = Mapper.mapBinary(selectData.get("year"));
			boolean ifClassSelected = Mapper.mapBinary(selectData.get("class"));
			boolean ifMinisterSelected = Mapper.mapBinary(selectData.get("minister"));

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectChildAll(id, ifSelectId, name, ifSelectName, notes, ifSelectNotes,
							birthday, ifSelectBirthday, nationalId, ifSelectNationalId, education,
							ifSelectEducation, eccEducation, ifSelectEccEducation, courses, ifSelectCourses,
							skills, ifSelectSkills, phone, ifSelectPhone, mobile1, ifSelectMobile1, mobile2,
							ifSelectMobile2, addressNum, ifSelectAddressNum, addressStreet,
							ifSelectAddressStreet, addressRegion, ifSelectAddressRegion, addressDistrict,
							ifSelectAddressDistrict, addressFree, ifSelectAddressFree, email, ifSelectEmail,
							ifSelectActiveDate, ifLevelSelected, levelItem, levelValue, ifSectionSelected,
							ifFamilySelected, ifYearSelected, ifClassSelected, ifMinisterSelected,
							birthdayDay, birthdayMonth, birthdayYear));

			return result;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int deleteChild(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String id = requestData.get("id");
			String childName = requestData.get("childName");

			int deleteMemberResult = CommonsHandler.deleteMember(connection, id);

			HashMap<String, String> requestData1 = new HashMap<String, String>();
			requestData1.put("childName", childName);

			int unassignChild = RequestHandler.handleUpdateRequest("unassignChild", requestData1);

			int result = CommonsHandler.validateResults(unassignChild, EMapper.ECODE_SUCCESS,
					deleteMemberResult, 1);

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
