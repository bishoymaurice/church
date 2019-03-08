package church.ministry.control.father;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import church.ministry.control.commons.CommonsHandler;
import church.ministry.control.ecode.EMapper;
import church.ministry.control.log.Logger;
import church.ministry.model.oracle.DatabaseConnectionManager;
import church.ministry.model.oracle.DatabaseExecutor;
import church.ministry.model.oracle.StatementBuilder;
import church.ministry.util.Mapper;
import church.ministry.util.Validator;

public class FatherHandler implements Serializable {

	private static final long serialVersionUID = -6679100364773431602L;

	private static String type = "father";

	public static int newFather(HashMap<String, String> requestData) {

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

			ArrayList<ArrayList<Object>> nameResult = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectNameByNameType(name, type));
			if (nameResult.size() > 0) {
				return EMapper.ECODE_ALREADY_EXISTS;
			}

			String id = new DatabaseExecutor().executeQuery(connection, StatementBuilder.selectNextFreeId())
					.get(0).get(0).toString();

			int newMemberResult = CommonsHandler.newMember(connection, id, type, name);
			int newFatherResult = CommonsHandler.newFather(connection, id, notes);
			int newProfileResult = CommonsHandler.newProfile(connection, id, birthday, nationalId, education,
					eccEducation, null, courses, skills);
			int newContactResult = CommonsHandler.newContact(connection, id, phone, mobile1, mobile2,
					addressNum, addressStreet, addressRegion, addressDistrict, addressFree, email);

			int result = CommonsHandler.validateResults(newMemberResult, 1, newFatherResult, 1,
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

	public static HashMap<String, String> getFatherData(HashMap<String, String> requestData) {

		Connection connection = null;

		try {

			connection = DatabaseConnectionManager.getConnection();

			String fatherName = requestData.get("fatherName");

			HashMap<String, String> fatherData = new HashMap<String, String>();

			fatherData.put("name", fatherName);

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMemberByTypeAndName(type, fatherName));

			fatherData.put("id", result.get(0).get(0).toString());
			fatherData.put("activeDatetime", result.get(0).get(1).toString());
			fatherData.put("status", result.get(0).get(2).toString());

			result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectFatherById(fatherData.get("id")));

			fatherData.put("notes", result.get(0).get(0).toString());

			result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectProfileById(fatherData.get("id")));

			fatherData.put("birthday", result.get(0).get(0).toString());
			fatherData.put("education", result.get(0).get(1).toString());
			fatherData.put("eccEducation", result.get(0).get(2).toString());
			fatherData.put("courses", result.get(0).get(4).toString());
			fatherData.put("skills", result.get(0).get(5).toString());

			result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectContactById(fatherData.get("id")));

			fatherData.put("phone", result.get(0).get(0).toString());
			fatherData.put("mobile1", result.get(0).get(1).toString());
			fatherData.put("mobile2", result.get(0).get(2).toString());
			fatherData.put("addressNum", result.get(0).get(3).toString());
			fatherData.put("addressStreet", result.get(0).get(4).toString());
			fatherData.put("addressRegion", result.get(0).get(5).toString());
			fatherData.put("addressDistrict", result.get(0).get(6).toString());
			fatherData.put("addressFree", result.get(0).get(7).toString());
			fatherData.put("email", result.get(0).get(8).toString());

			return fatherData;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int updateFather(HashMap<String, String> requestData) {

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
			int updateFatherResult = CommonsHandler.updateFather(connection, id, notes);
			int updateProfileResult = CommonsHandler.updateProfile(connection, id, birthday, nationalId,
					education, eccEducation, null, courses, skills);
			int updateContactResult = CommonsHandler.updateContact(connection, id, phone, mobile1, mobile2,
					addressNum, addressStreet, addressRegion, addressDistrict, addressFree, email);

			int result = CommonsHandler.validateResults(updateMemberResult, 1, updateFatherResult, 1,
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

	public static ArrayList<ArrayList<Object>> searchFather(HashMap<String, String> requestData,
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
			String education = requestData.get("education");
			String eccEducation = requestData.get("eccEducation");
			String courses = requestData.get("courses");
			String skills = requestData.get("skills");
			String notes = requestData.get("notes");

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
			boolean ifSelectEducation = Mapper.mapBinary(selectData.get("education"));
			boolean ifSelectEccEducation = Mapper.mapBinary(selectData.get("eccEducation"));
			boolean ifSelectCourses = Mapper.mapBinary(selectData.get("courses"));
			boolean ifSelectSkills = Mapper.mapBinary(selectData.get("skills"));
			boolean ifSelectNotes = Mapper.mapBinary(selectData.get("notes"));
			boolean ifSelectActiveDate = Mapper.mapBinary(selectData.get("activeDate"));

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectFatherAll(id, ifSelectId, name, ifSelectName, notes,
							ifSelectNotes, birthday, ifSelectBirthday, education, ifSelectEducation,
							eccEducation, ifSelectEccEducation, courses, ifSelectCourses, skills,
							ifSelectSkills, phone, ifSelectPhone, mobile1, ifSelectMobile1, mobile2,
							ifSelectMobile2, addressNum, ifSelectAddressNum, addressStreet,
							ifSelectAddressStreet, addressRegion, ifSelectAddressRegion, addressDistrict,
							ifSelectAddressDistrict, addressFree, ifSelectAddressFree, email, ifSelectEmail,
							ifSelectActiveDate, birthdayDay, birthdayMonth, birthdayYear));

			return result;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int deleteFather(HashMap<String, String> requestData) {
		Connection connection = null;
		try {
			connection = DatabaseConnectionManager.getConnection();

			String id = requestData.get("id");

			int deleteMemberResult = CommonsHandler.deleteMember(connection, id);

			int result = CommonsHandler.validateResults(deleteMemberResult, 1);

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
