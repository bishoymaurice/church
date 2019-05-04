package church.ministry.control.minister;

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

public class MinisterHandler implements Serializable {

	private static final long serialVersionUID = 7814146672539072935L;

	private static String type = "minister";

	public static int newMinister(HashMap<String, String> requestData) {

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
			String job = requestData.get("job");
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

			int newMinisterResult = CommonsHandler.newMinister(connection, id, notes);

			int newProfileResult = CommonsHandler.newProfile(connection, id, birthday, nationalId, education,
					eccEducation, job, courses, skills);

			int newContactResult = CommonsHandler.newContact(connection, id, phone, mobile1, mobile2,
					addressNum, addressStreet, addressRegion, addressDistrict, addressFree, email);

			int result = CommonsHandler.validateResults(newMemberResult, 1, newMinisterResult, 1,
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

	public static HashMap<String, String> getMinisterData(HashMap<String, String> requestData) {

		Connection connection = null;

		try {
			connection = DatabaseConnectionManager.getConnection();

			String ministerName = requestData.get("ministerName");

			HashMap<String, String> ministerData = new HashMap<String, String>();

			ministerData.put("name", ministerName);

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMemberByTypeAndName(type, ministerName));

			ministerData.put("id", result.get(0).get(0).toString());
			ministerData.put("activeDatetime", result.get(0).get(1).toString());
			ministerData.put("status", result.get(0).get(2).toString());

			result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinisterById(ministerData.get("id")));

			ministerData.put("notes", result.get(0).get(0).toString());

			result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectProfileById(ministerData.get("id")));

			ministerData.put("birthday", result.get(0).get(0).toString());
			ministerData.put("education", result.get(0).get(1).toString());
			ministerData.put("eccEducation", result.get(0).get(2).toString());
			ministerData.put("job", result.get(0).get(3).toString());
			ministerData.put("courses", result.get(0).get(4).toString());
			ministerData.put("skills", result.get(0).get(5).toString());

			result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectContactById(ministerData.get("id")));

			ministerData.put("phone", result.get(0).get(0).toString());
			ministerData.put("mobile1", result.get(0).get(1).toString());
			ministerData.put("mobile2", result.get(0).get(2).toString());
			ministerData.put("addressNum", result.get(0).get(3).toString());
			ministerData.put("addressStreet", result.get(0).get(4).toString());
			ministerData.put("addressRegion", result.get(0).get(5).toString());
			ministerData.put("addressDistrict", result.get(0).get(6).toString());
			ministerData.put("addressFree", result.get(0).get(7).toString());
			ministerData.put("email", result.get(0).get(8).toString());

			return ministerData;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int updateMinister(HashMap<String, String> requestData) {

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
			String job = requestData.get("job");
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
			int updateMinisterResult = CommonsHandler.updateMinister(connection, id, notes);
			int updateProfileResult = CommonsHandler.updateProfile(connection, id, birthday, nationalId,
					education, eccEducation, job, courses, skills);
			int updateContactResult = CommonsHandler.updateContact(connection, id, phone, mobile1, mobile2,
					addressNum, addressStreet, addressRegion, addressDistrict, addressFree, email);

			int result = CommonsHandler.validateResults(updateMemberResult, 1, updateMinisterResult, 1,
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

	public static ArrayList<ArrayList<Object>> searchMinister(HashMap<String, String> requestData,
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
			String job = requestData.get("job");
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
			boolean ifSelectJob = Mapper.mapBinary(selectData.get("job"));
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

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor().executeQuery(connection,
					StatementBuilder.selectMinisterAll(id, ifSelectId, name, ifSelectName, notes,
							ifSelectNotes, birthday, ifSelectBirthday, education, ifSelectEducation,
							eccEducation, ifSelectEccEducation, courses, ifSelectCourses, skills,
							ifSelectSkills, phone, ifSelectPhone, mobile1, ifSelectMobile1, mobile2,
							ifSelectMobile2, addressNum, ifSelectAddressNum, addressStreet,
							ifSelectAddressStreet, addressRegion, ifSelectAddressRegion, addressDistrict,
							ifSelectAddressDistrict, addressFree, ifSelectAddressFree, email, ifSelectEmail,
							job, ifSelectJob, ifSelectActiveDate, ifLevelSelected, levelItem, levelValue,
							ifSectionSelected, ifFamilySelected, ifYearSelected, ifClassSelected,
							birthdayDay, birthdayMonth, birthdayYear));

			return result;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		} finally {
			DatabaseConnectionManager.releaseConnection(connection);
		}
	}

	public static int deleteMinister(HashMap<String, String> requestData) {

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
