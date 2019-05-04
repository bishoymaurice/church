package church.ministry.model.oracle;

import java.io.Serializable;

import church.ministry.util.Validator;

public class StatementBuilder implements Serializable {

	private static final long serialVersionUID = -11303274895338448L;

	public static String insertMember(String id, String type, String name) {
		String QUERY = "insert into member (id, type, name, active_date) values ($ID, (select id from member_type where des = '$TYPE'), '$NAME', sysdate)";

		QUERY = QUERY.replace("$ID", id);
		QUERY = QUERY.replace("$TYPE", type);
		QUERY = QUERY.replace("$NAME", name);

		return QUERY;
	}

	public static String insertFather(String id, String notes) {
		String QUERY = "insert into father (id, notes) values ($ID, '$NOTES')";

		QUERY = QUERY.replace("$ID", id);

		if (Validator.validateString(notes)) {
			QUERY = QUERY.replace("$NOTES", notes);
		} else {
			QUERY = QUERY.replace("'$NOTES'", "null");
		}

		return QUERY;
	}

	public static String insertProfile(String id, String birthday, String nationalId, String education,
			String eccEducation, String job, String courses, String skills) {
		String QUERY = "insert into profile (id, birthday, education, ecclesiastic_education, job, courses, skills, national_id) values ($ID, to_date('$BIRTHDAY', 'dd-mm-yyyy'), '$EDUCATION', '$ECC_EDUCATION', '$JOB', '$COURSES', '$SKILLS', '$NATIONAL_ID')";

		QUERY = QUERY.replace("$ID", id);

		if (Validator.validateString(birthday)) {
			QUERY = QUERY.replace("$BIRTHDAY", birthday);
		} else {
			QUERY = QUERY.replace("'$BIRTHDAY'", "null");
		}

		if (Validator.validateString(nationalId)) {
			QUERY = QUERY.replace("$NATIONAL_ID", nationalId);
		} else {
			QUERY = QUERY.replace("'$NATIONAL_ID'", "null");
		}

		if (Validator.validateString(education)) {
			QUERY = QUERY.replace("$EDUCATION", education);
		} else {
			QUERY = QUERY.replace("'$EDUCATION'", "null");
		}

		if (Validator.validateString(eccEducation)) {
			QUERY = QUERY.replace("$ECC_EDUCATION", eccEducation);
		} else {
			QUERY = QUERY.replace("'$ECC_EDUCATION'", "null");
		}

		if (Validator.validateString(job)) {
			QUERY = QUERY.replace("$JOB", job);
		} else {
			QUERY = QUERY.replace("'$JOB'", "null");
		}

		if (Validator.validateString(courses)) {
			QUERY = QUERY.replace("$COURSES", courses);
		} else {
			QUERY = QUERY.replace("'$COURSES'", "null");
		}

		if (Validator.validateString(skills)) {
			QUERY = QUERY.replace("$SKILLS", skills);
		} else {
			QUERY = QUERY.replace("'$SKILLS'", "null");
		}

		return QUERY;
	}

	public static String insertContact(String id, String phone, String mobile1, String mobile2,
			String addressNum, String addressStreet, String addressRegion, String addressDistrict,
			String addressFree, String email) {
		String QUERY = "insert into contact (id, phone, mobile1, mobile2, address_num, address_street, address_region, address_district, address_free, email) values ($ID, '$PHONE', '$MOBILE1', '$MOBILE2', '$ADDRESS_NUM', (select id from streets where name = '$ADDRESS_STREET'), (select id from regions where name = '$ADDRESS_REGION'), (select id from districts where name = '$ADDRESS_DISTRICT'), '$ADDRESS_FREE', '$EMAIL')";

		QUERY = QUERY.replace("$ID", id);

		if (Validator.validateString(phone)) {
			QUERY = QUERY.replace("$PHONE", phone);
		} else {
			QUERY = QUERY.replace("'$PHONE'", "null");
		}

		if (Validator.validateString(mobile1)) {
			QUERY = QUERY.replace("$MOBILE1", mobile1);
		} else {
			QUERY = QUERY.replace("'$MOBILE1'", "null");
		}

		if (Validator.validateString(mobile2)) {
			QUERY = QUERY.replace("$MOBILE2", mobile2);
		} else {
			QUERY = QUERY.replace("'$MOBILE2'", "null");
		}

		if (Validator.validateString(addressNum)) {
			QUERY = QUERY.replace("$ADDRESS_NUM", addressNum);
		} else {
			QUERY = QUERY.replace("'$ADDRESS_NUM'", "null");
		}

		if (Validator.validateString(addressStreet)) {
			QUERY = QUERY.replace("$ADDRESS_STREET", addressStreet);
		} else {
			QUERY = QUERY.replace("'$ADDRESS_STREET'", "null");
		}

		if (Validator.validateString(addressRegion)) {
			QUERY = QUERY.replace("$ADDRESS_REGION", addressRegion);
		} else {
			QUERY = QUERY.replace("'$ADDRESS_REGION'", "null");
		}

		if (Validator.validateString(addressDistrict)) {
			QUERY = QUERY.replace("$ADDRESS_DISTRICT", addressDistrict);
		} else {
			QUERY = QUERY.replace("'$ADDRESS_DISTRICT'", "null");
		}

		if (Validator.validateString(addressFree)) {
			QUERY = QUERY.replace("$ADDRESS_FREE", addressFree);
		} else {
			QUERY = QUERY.replace("'$ADDRESS_FREE'", "null");
		}

		if (Validator.validateString(email)) {
			QUERY = QUERY.replace("$EMAIL", email);
		} else {
			QUERY = QUERY.replace("'$EMAIL'", "null");
		}

		return QUERY;
	}

	public static String selectNextFreeId() {
		String QUERY = "select nvl(max(id)+1, 1) from member";
		return QUERY;
	}

	public static String selectNameByNameType(String name, String type) {
		String QUERY = "select name from member where name = '$NAME' and type = (select id from member_type where des = '$TYPE') order by name";

		QUERY = QUERY.replace("$NAME", name);
		QUERY = QUERY.replace("$TYPE", type);

		return QUERY;
	}

	public static String selectNameByNameType_ExcludeId(String name, String type, String id) {
		String QUERY = "select name from member where name = '$NAME' and type = (select id from member_type where des = '$TYPE') and id <> $ID and member.inactive_date is null order by name";

		QUERY = QUERY.replace("$NAME", name);
		QUERY = QUERY.replace("$TYPE", type);
		QUERY = QUERY.replace("$ID", id);

		return QUERY;
	}

	public static String selectNamesByType(String type) {
		String QUERY = "select name from member where type = (select id from member_type where des = '$TYPE') and member.inactive_date is null order by name";

		QUERY = QUERY.replace("$TYPE", type);

		return QUERY;
	}

	public static String selectAllNamesByType(String type) {
		String QUERY = "select name from member where type = (select id from member_type where des = '$TYPE') order by name";

		QUERY = QUERY.replace("$TYPE", type);

		return QUERY;
	}

	public static String selectAllMinistersNamesExcludingSubClass(String subClassName) {
		String QUERY = "select name from member where type = (select id from member_type where des = 'minister') and id not in (select id from minister where sub_class_id = (select id from sub_class where name = '$SUB_CLASS_NAME')) and member.inactive_date is null order by name";

		QUERY = QUERY.replace("$SUB_CLASS_NAME", subClassName);

		return QUERY;
	}

	public static String selectIdAndNameByType(String type) {
		String QUERY = "select id, name from member where type = (select id from member_type where des = '$TYPE') and member.inactive_date is null order by name";

		QUERY = QUERY.replace("$TYPE", type);

		return QUERY;
	}

	public static String selectAllStreets() {
		String QUERY = "select name from streets order by name";

		return QUERY;
	}

	public static String selectAllRegions() {
		String QUERY = "select name from regions order by name";

		return QUERY;
	}

	public static String selectAllDistricts() {
		String QUERY = "select name from districts order by name";

		return QUERY;
	}

	public static String insertNewStreet(String streetName) {
		String QUERY = "insert into streets (id, name) values ((select nvl(max(id)+1, 1) from streets), '$STREET_NAME')";

		QUERY = QUERY.replace("$STREET_NAME", streetName);

		return QUERY;
	}

	public static String insertNewRegion(String regionName) {
		String QUERY = "insert into regions (id, name) values ((select nvl(max(id)+1, 1) from regions), '$REGION_NAME')";

		QUERY = QUERY.replace("$REGION_NAME", regionName);

		return QUERY;
	}

	public static String insertNewDistrict(String districtName) {
		String QUERY = "insert into districts (id, name) values ((select nvl(max(id)+1, 1) from districts), '$DISTRICT_NAME')";

		QUERY = QUERY.replace("$DISTRICT_NAME", districtName);

		return QUERY;
	}

	public static String selectStreetByName(String streetName) {
		String QUERY = "select name from streets where name = '$STREET_NAME' order by name";

		QUERY = QUERY.replace("$STREET_NAME", streetName);

		return QUERY;
	}

	public static String selectRegionByName(String regionName) {
		String QUERY = "select name from regions where name = '$REGION_NAME' order by name";

		QUERY = QUERY.replace("$REGION_NAME", regionName);

		return QUERY;
	}

	public static String selectDistrictByName(String districtName) {
		String QUERY = "select name from districts where name = '$DISTRICT_NAME' order by name";

		QUERY = QUERY.replace("$DISTRICT_NAME", districtName);

		return QUERY;
	}

	public static String selectMemberByTypeAndName(String type, String name) {
		String QUERY = "select id, active_date, decode(inactive_date, null, '1', '0') from member where type = (select id from member_type where des = '$TYPE') and name = '$NAME'";

		QUERY = QUERY.replace("$TYPE", type);
		QUERY = QUERY.replace("$NAME", name);

		return QUERY;
	}

	public static String selectFatherById(String id) {
		String QUERY = "select notes from father where id = $ID";

		QUERY = QUERY.replace("$ID", id);

		return QUERY;
	}

	public static String selectProfileById(String id) {
		String QUERY = "select to_char(birthday, 'dd-mm-yyyy'), education, ecclesiastic_education, job, courses, skills, national_id from profile where id = $ID";

		QUERY = QUERY.replace("$ID", id);

		return QUERY;
	}

	public static String selectContactById(String id) {
		String QUERY = "select phone, mobile1, mobile2, address_num, (select name from streets where id = address_street), (select name from regions where id = address_region), (select name from districts where id = address_district), address_free, email from contact where id = $ID";

		QUERY = QUERY.replace("$ID", id);

		return QUERY;
	}

	public static String updateMember(String id, String name) {
		String QUERY = "update member set name = '$NAME' where id = $ID";

		QUERY = QUERY.replace("$ID", id);
		QUERY = QUERY.replace("$NAME", name);

		return QUERY;
	}

	public static String updateFather(String id, String notes) {
		String QUERY = "update father set notes = '$NOTES' where id = $ID";

		QUERY = QUERY.replace("$ID", id);

		if (Validator.validateString(notes)) {
			QUERY = QUERY.replace("$NOTES", notes);
		} else {
			QUERY = QUERY.replace("'$NOTES'", "null");
		}

		return QUERY;
	}

	public static String updateProfile(String id, String birthday, String nationalId, String education,
			String eccEducation, String job, String courses, String skills) {
		String QUERY = "update profile set birthday = to_date ('$BIRTHDAY', 'dd-mm-yyyy'), education = '$EDUCATION', ecclesiastic_education = '$ECC_EDUCATION', job = '$JOB', courses = '$COURSES', skills = '$SKILLS', national_id = '$NATIONAL_ID' where id = $ID";

		QUERY = QUERY.replace("$ID", id);

		if (Validator.validateString(birthday)) {
			QUERY = QUERY.replace("$BIRTHDAY", birthday);
		} else {
			QUERY = QUERY.replace("'$BIRTHDAY'", "null");
		}

		if (Validator.validateString(nationalId)) {
			QUERY = QUERY.replace("$NATIONAL_ID", nationalId);
		} else {
			QUERY = QUERY.replace("'$NATIONAL_ID'", "null");
		}

		if (Validator.validateString(education)) {
			QUERY = QUERY.replace("$EDUCATION", education);
		} else {
			QUERY = QUERY.replace("'$EDUCATION'", "null");
		}

		if (Validator.validateString(eccEducation)) {
			QUERY = QUERY.replace("$ECC_EDUCATION", eccEducation);
		} else {
			QUERY = QUERY.replace("'$ECC_EDUCATION'", "null");
		}

		if (Validator.validateString(job)) {
			QUERY = QUERY.replace("$JOB", job);
		} else {
			QUERY = QUERY.replace("'$JOB'", "null");
		}

		if (Validator.validateString(courses)) {
			QUERY = QUERY.replace("$COURSES", courses);
		} else {
			QUERY = QUERY.replace("'$COURSES'", "null");
		}

		if (Validator.validateString(skills)) {
			QUERY = QUERY.replace("$SKILLS", skills);
		} else {
			QUERY = QUERY.replace("'$SKILLS'", "null");
		}

		return QUERY;
	}

	public static String updateContact(String id, String phone, String mobile1, String mobile2,
			String addressNum, String addressStreet, String addressRegion, String addressDistrict,
			String addressFree, String email) {
		String QUERY = "update contact set phone = '$PHONE', mobile1 = '$MOBILE1', mobile2 = '$MOBILE2', address_num = '$ADDRESS_NUM', address_street = (select id from streets where name = '$ADDRESS_STREET'), address_region = (select id from regions where name = '$ADDRESS_REGION'), address_district = (select id from districts where name = '$ADDRESS_DISTRICT'), address_free = '$ADDRESS_FREE', email = '$EMAIL' where id = $ID";

		QUERY = QUERY.replace("$ID", id);

		if (Validator.validateString(phone)) {
			QUERY = QUERY.replace("$PHONE", phone);
		} else {
			QUERY = QUERY.replace("'$PHONE'", "null");
		}

		if (Validator.validateString(mobile1)) {
			QUERY = QUERY.replace("$MOBILE1", mobile1);
		} else {
			QUERY = QUERY.replace("'$MOBILE1'", "null");
		}

		if (Validator.validateString(mobile2)) {
			QUERY = QUERY.replace("$MOBILE2", mobile2);
		} else {
			QUERY = QUERY.replace("'$MOBILE2'", "null");
		}

		if (Validator.validateString(addressNum)) {
			QUERY = QUERY.replace("$ADDRESS_NUM", addressNum);
		} else {
			QUERY = QUERY.replace("'$ADDRESS_NUM'", "null");
		}

		if (Validator.validateString(addressStreet)) {
			QUERY = QUERY.replace("$ADDRESS_STREET", addressStreet);
		} else {
			QUERY = QUERY.replace("'$ADDRESS_STREET'", "null");
		}

		if (Validator.validateString(addressRegion)) {
			QUERY = QUERY.replace("$ADDRESS_REGION", addressRegion);
		} else {
			QUERY = QUERY.replace("'$ADDRESS_REGION'", "null");
		}

		if (Validator.validateString(addressDistrict)) {
			QUERY = QUERY.replace("$ADDRESS_DISTRICT", addressDistrict);
		} else {
			QUERY = QUERY.replace("'$ADDRESS_DISTRICT'", "null");
		}

		if (Validator.validateString(addressFree)) {
			QUERY = QUERY.replace("$ADDRESS_FREE", addressFree);
		} else {
			QUERY = QUERY.replace("'$ADDRESS_FREE'", "null");
		}

		if (Validator.validateString(email)) {
			QUERY = QUERY.replace("$EMAIL", email);
		} else {
			QUERY = QUERY.replace("'$EMAIL'", "null");
		}

		return QUERY;
	}

	public static String selectFatherAll(String id, boolean ifSelectId, String name, boolean ifSelectName,
			String notes, boolean ifSelectNotes, String birthday, boolean ifSelectBirthday, String education,
			boolean ifSelectEducation, String eccEducation, boolean ifSelectEccEducation, String courses,
			boolean ifSelectCourses, String skills, boolean ifSelectSkills, String phone,
			boolean ifSelectPhone, String mobile1, boolean ifSelectMobile1, String mobile2,
			boolean ifSelectMobile2, String addressNum, boolean ifSelectAddressNum, String addressStreet,
			boolean ifSelectAddressStreet, String addressRegion, boolean ifSelectAddressRegion,
			String addressDistrict, boolean ifSelectAddressDistrict, String addressFree,
			boolean ifSelectaddressFree, String email, boolean ifSelectEmail, boolean ifSelectActiveDate,
			String birthdayDay, String birthdayMonth, String birthdayYear) {

		String headerQuery = "select 'ÇáÑÞã', 'ÇáÇÓã'";
		String QUERY = "select to_char(member.id), member.name";

		if (ifSelectAddressNum) {
			headerQuery += ", 'ÇáÚäæÇä - ÑÞã'";
			QUERY += ", contact.address_num";
		}
		if (ifSelectAddressStreet) {
			headerQuery += ", 'ÇáÚäæÇä - ÔÇÑÚ'";
			QUERY += ", (select name from streets where streets.id = contact.address_street)";
		}
		if (ifSelectAddressRegion) {
			headerQuery += ", 'ÇáÚäæÇä - ãäØÞÉ'";
			QUERY += ", (select name from regions where regions.id = contact.address_region)";
		}
		if (ifSelectAddressDistrict) {
			headerQuery += ", 'ÇáÚäæÇä - Íí'";
			QUERY += ", (select name from districts where districts.id = contact.address_district)";
		}
		if (ifSelectaddressFree) {
			headerQuery += ", 'ÈÇÞí ÇáÚäæÇä'";
			QUERY += ", contact.address_free";
		}
		if (ifSelectPhone) {
			headerQuery += ", 'ÇáÊáíÝæä'";
			QUERY += ", contact.phone";
		}
		if (ifSelectMobile1) {
			headerQuery += ", 'ãæÈÇíá 1'";
			QUERY += ", contact.mobile1";
		}
		if (ifSelectMobile2) {
			headerQuery += ", 'ãæÈÇíá 2'";
			QUERY += ", contact.mobile2";
		}
		if (ifSelectBirthday) {
			headerQuery += ", 'ÊÇÑíÎ ÇáãíáÇÏ'";
			QUERY += ", to_char(profile.birthday, 'dd-mm-yyyy')";
		}
		if (ifSelectEmail) {
			headerQuery += ", 'ÇáÇíãíá'";
			QUERY += ", contact.email";
		}
		if (ifSelectEducation) {
			headerQuery += ", 'ÇáÊÚáíã'";
			QUERY += ", profile.education";
		}
		if (ifSelectEccEducation) {
			headerQuery += ", 'ÇáÊÚáíã ÇáßäÓí'";
			QUERY += ", profile.ecclesiastic_education";
		}
		if (ifSelectCourses) {
			headerQuery += ", 'ßæÑÓÇÊ'";
			QUERY += ", profile.courses";
		}
		if (ifSelectSkills) {
			headerQuery += ", 'ãåÇÑÇÊ'";
			QUERY += ", profile.skills";
		}
		if (ifSelectNotes) {
			headerQuery += ", 'ãáÇÍÙÇÊ'";
			QUERY += ", father.notes";
		}
		if (ifSelectActiveDate) {
			headerQuery += ", 'ÊÇÑíÎ ÇáÊÝÚíá'";
			QUERY += ", to_char(member.active_date, 'dd-mm-yyyy hh24:mi')";
		}

		headerQuery += " from dual";
		QUERY += " from member, father, profile, contact where member.id = father.id and member.id = profile.id and member.id = contact.id and member.inactive_date is null";

		QUERY = headerQuery + " UNION ALL " + QUERY;

		String idCond = " and member.id = $ID";
		String nameCond = " and lower(member.name) like lower('%$NAME%')";
		String notesCond = " and lower(father.notes) like lower('%$NOTES%')";
		String birthdayCond = " and profile.birthday = to_date ('$BIRTHDAY', 'dd-mm-yyyy')";
		String educationCond = " and lower(profile.education) like lower('%$EDUCATION%')";
		String eccEducationCond = "and lower(profile.ecclesiastic_education) like lower('%$ECC_EDUCATION%')";
		String coursesCond = " and lower(profile.courses) like lower('%$COURSES%')";
		String skillsCond = " and lower(profile.skills) like lower('%$SKILLS%')";
		String phoneCond = " and lower(contact.phone) like lower('%$PHONE%')";
		String mobile1Cond = " and lower(contact.mobile1) like lower('%$MOBILE1%')";
		String mobile2Cond = " and lower(contact.mobile2) like lower('%$MOBILE2%')";
		String addressNumCond = " and lower(contact.address_num) like lower('%$ADDRESS_NUM%')";
		String addressStreetCond = " and contact.address_street = (select id from streets where name = '$ADDRESS_STREET')";
		String addressRegionCond = " and contact.address_region = (select id from regions where name = '$ADDRESS_REGION')";
		String addressDistrictCond = " and contact.address_district = (select id from districts where name = '$ADDRESS_DISTRICT')";
		String addressFreeCond = " and lower(contact.address_free) like lower('%$ADDRESS_FREE%')";
		String emailCond = " and lower(contact.email) like lower('%$EMAIL%')";

		String birthdayDayCond = " and to_char(profile.birthday, 'dd') = '$DAY'";
		String birthdayMonthCond = " and to_char(profile.birthday, 'mm') = '$MONTH'";
		String birthdayYearCond = " and to_char(profile.birthday, 'yyyy') = '$YEAR'";

		if (Validator.validateString(id)) {
			idCond = idCond.replace("$ID", id);
			QUERY += idCond;
		}

		if (Validator.validateString(name)) {
			nameCond = nameCond.replace("$NAME", name);
			QUERY += nameCond;
		}

		if (Validator.validateString(notes)) {
			notesCond = notesCond.replace("$NOTES", notes);
			QUERY += notesCond;
		}

		if (Validator.validateString(birthday)) {
			birthdayCond = birthdayCond.replace("$BIRTHDAY", birthday);
			QUERY += birthdayCond;
		}

		if (Validator.validateString(education)) {
			educationCond = educationCond.replace("$EDUCATION", education);
			QUERY += educationCond;
		}

		if (Validator.validateString(eccEducation)) {
			eccEducationCond = eccEducationCond.replace("$ECC_EDUCATION", eccEducation);
			QUERY += eccEducationCond;
		}

		if (Validator.validateString(courses)) {
			coursesCond = coursesCond.replace("$COURSES", courses);
			QUERY += coursesCond;
		}

		if (Validator.validateString(skills)) {
			skillsCond = skillsCond.replace("$SKILLS", skills);
			QUERY += skillsCond;
		}

		if (Validator.validateString(phone)) {
			phoneCond = phoneCond.replace("$PHONE", phone);
			QUERY += phoneCond;
		}

		if (Validator.validateString(mobile1)) {
			mobile1Cond = mobile1Cond.replace("$MOBILE1", mobile1);
			QUERY += mobile1Cond;
		}

		if (Validator.validateString(mobile2)) {
			mobile2Cond = mobile2Cond.replace("$MOBILE2", mobile2);
			QUERY += mobile2Cond;
		}

		if (Validator.validateString(addressNum)) {
			addressNumCond = addressNumCond.replace("$ADDRESS_NUM", addressNum);
			QUERY += addressNumCond;
		}

		if (Validator.validateString(addressStreet)) {
			addressStreetCond = addressStreetCond.replace("$ADDRESS_STREET", addressStreet);
			QUERY += addressStreetCond;
		}

		if (Validator.validateString(addressRegion)) {
			addressRegionCond = addressRegionCond.replace("$ADDRESS_REGION", addressRegion);
			QUERY += addressRegionCond;
		}

		if (Validator.validateString(addressDistrict)) {
			addressDistrictCond = addressDistrictCond.replace("$ADDRESS_DISTRICT", addressDistrict);
			QUERY += addressDistrictCond;
		}

		if (Validator.validateString(addressFree)) {
			addressFreeCond = addressFreeCond.replace("$ADDRESS_FREE", addressFree);
			QUERY += addressFreeCond;
		}

		if (Validator.validateString(email)) {
			emailCond = emailCond.replace("$EMAIL", email);
			QUERY += emailCond;
		}

		if (Validator.validateString(birthdayDay)) {
			birthdayDayCond = birthdayDayCond.replace("$DAY", birthdayDay);
			QUERY += birthdayDayCond;
		}

		if (Validator.validateString(birthdayMonth)) {
			birthdayMonthCond = birthdayMonthCond.replace("$MONTH", birthdayMonth);
			QUERY += birthdayMonthCond;
		}

		if (Validator.validateString(birthdayYear)) {
			birthdayYearCond = birthdayYearCond.replace("$YEAR", birthdayYear);
			QUERY += birthdayYearCond;
		}

		return QUERY;
	}

	public static String insertMinister(String id, String notes) {
		String QUERY = "insert into minister (id, notes) values ($ID, '$NOTES')";

		QUERY = QUERY.replace("$ID", id);

		if (Validator.validateString(notes)) {
			QUERY = QUERY.replace("$NOTES", notes);
		} else {
			QUERY = QUERY.replace("'$NOTES'", "null");
		}

		return QUERY;
	}

	public static String selectMinisterById(String id) {
		String QUERY = "select notes from minister where id = $ID";

		QUERY = QUERY.replace("$ID", id);

		return QUERY;
	}

	public static String updateMinister(String id, String notes) {
		String QUERY = "update minister set notes = '$NOTES' where id = $ID";

		QUERY = QUERY.replace("$ID", id);

		if (Validator.validateString(notes)) {
			QUERY = QUERY.replace("$NOTES", notes);
		} else {
			QUERY = QUERY.replace("'$NOTES'", "null");
		}

		return QUERY;
	}

	public static String selectMinisterAll(String id, boolean ifSelectId, String name, boolean ifSelectName,
			String notes, boolean ifSelectNotes, String birthday, boolean ifSelectBirthday, String education,
			boolean ifSelectEducation, String eccEducation, boolean ifSelectEccEducation, String courses,
			boolean ifSelectCourses, String skills, boolean ifSelectSkills, String phone,
			boolean ifSelectPhone, String mobile1, boolean ifSelectMobile1, String mobile2,
			boolean ifSelectMobile2, String addressNum, boolean ifSelectAddressNum, String addressStreet,
			boolean ifSelectAddressStreet, String addressRegion, boolean ifSelectAddressRegion,
			String addressDistrict, boolean ifSelectAddressDistrict, String addressFree,
			boolean ifSelectaddressFree, String email, boolean ifSelectEmail, String job,
			boolean ifSelectJob, boolean ifSelectActiveDate, boolean ifLevelSelected, String levelItem,
			String levelValue, boolean ifSelectSection, boolean ifSelectFamily, boolean ifSelectYear,
			boolean ifSelectClass, String birthdayDay, String birthdayMonth, String birthdayYear) {

		String headerQuery = "select 'ÇáÑÞã', 'ÇáÇÓã'";
		String QUERY = "select to_char(member.id), member.name";
		String[] levelsNames = new String[] { "ÇáÊÑÈíÉ ÇáßäÓíÉ", "ÞØÇÚ", "ÃÓÑÉ", "ÕÝ", "ÝÕá" };

		if (ifSelectAddressNum) {
			headerQuery += ", 'ÇáÚäæÇä - ÑÞã'";
			QUERY += ", contact.address_num";
		}
		if (ifSelectAddressStreet) {
			headerQuery += ", 'ÇáÚäæÇä - ÔÇÑÚ'";
			QUERY += ", (select name from streets where streets.id = contact.address_street)";
		}
		if (ifSelectAddressRegion) {
			headerQuery += ", 'ÇáÚäæÇä - ãäØÞÉ'";
			QUERY += ", (select name from regions where regions.id = contact.address_region)";
		}
		if (ifSelectAddressDistrict) {
			headerQuery += ", 'ÇáÚäæÇä - Íí'";
			QUERY += ", (select name from districts where districts.id = contact.address_district)";
		}
		if (ifSelectaddressFree) {
			headerQuery += ", 'ÈÇÞí ÇáÚäæÇä'";
			QUERY += ", contact.address_free";
		}
		if (ifSelectPhone) {
			headerQuery += ", 'ÇáÊáíÝæä'";
			QUERY += ", contact.phone";
		}
		if (ifSelectMobile1) {
			headerQuery += ", 'ãæÈÇíá 1'";
			QUERY += ", contact.mobile1";
		}
		if (ifSelectMobile2) {
			headerQuery += ", 'ãæÈÇíá 2'";
			QUERY += ", contact.mobile2";
		}
		if (ifSelectBirthday) {
			headerQuery += ", 'ÊÇÑíÎ ÇáãíáÇÏ'";
			QUERY += ", to_char(profile.birthday, 'dd-mm-yyyy')";
		}
		if (ifSelectEmail) {
			headerQuery += ", 'ÇáÇíãíá'";
			QUERY += ", contact.email";
		}
		if (ifSelectJob) {
			headerQuery += ", 'ÇáæÙíÝÉ'";
			QUERY += ", profile.job";
		}
		if (ifSelectEducation) {
			headerQuery += ", 'ÇáÊÚáíã'";
			QUERY += ", profile.education";
		}
		if (ifSelectEccEducation) {
			headerQuery += ", 'ÇáÊÚáíã ÇáßäÓí'";
			QUERY += ", profile.ecclesiastic_education";
		}
		if (ifSelectCourses) {
			headerQuery += ", 'ßæÑÓÇÊ'";
			QUERY += ", profile.courses";
		}
		if (ifSelectSkills) {
			headerQuery += ", 'ãåÇÑÇÊ'";
			QUERY += ", profile.skills";
		}
		if (ifSelectNotes) {
			headerQuery += ", 'ãáÇÍÙÇÊ'";
			QUERY += ", minister.notes";
		}
		if (ifSelectActiveDate) {
			headerQuery += ", 'ÊÇÑíÎ ÇáÊÝÚíá'";
			QUERY += ", to_char(member.active_date, 'dd-mm-yyyy hh24:mi')";
		}
		if (ifSelectSection) {
			headerQuery += ", 'ÇáÞØÇÚ'";
			QUERY += ", section.name";
		}
		if (ifSelectFamily) {
			headerQuery += ", 'ÇáÃÓÑÉ'";
			QUERY += ", family.name";
		}
		if (ifSelectYear) {
			headerQuery += ", 'ÇáÕÝ'";
			QUERY += ", year.name";
		}
		if (ifSelectClass) {
			headerQuery += ", 'ÇáÝÕá'";
			QUERY += ", class.name";
		}

		headerQuery += " from dual";
		QUERY += " from member, minister, profile, contact, sub_class, class, year, family, section where minister.sub_class_id(+) = sub_class.id and sub_class.class_id = class.id and class.year_id = year.id and year.family_id = family.id and family.section_id = section.id and member.id = minister.id and member.id = profile.id and member.id = contact.id and member.inactive_date is null";

		QUERY = headerQuery + " UNION ALL " + QUERY;

		String idCond = " and member.id = $ID";
		String nameCond = " and lower(member.name) like lower('%$NAME%')";
		String notesCond = " and lower(minister.notes) like lower('%$NOTES%')";
		String birthdayCond = " and profile.birthday = to_date ('$BIRTHDAY', 'dd-mm-yyyy')";
		String educationCond = " and lower(profile.education) like lower('%$EDUCATION%')";
		String eccEducationCond = "and lower(profile.ecclesiastic_education) like lower('%$ECC_EDUCATION%')";
		String coursesCond = " and lower(profile.courses) like lower('%$COURSES%')";
		String skillsCond = " and lower(profile.skills) like lower('%$SKILLS%')";
		String phoneCond = " and lower(contact.phone) like lower('%$PHONE%')";
		String mobile1Cond = " and lower(contact.mobile1) like lower('%$MOBILE1%')";
		String mobile2Cond = " and lower(contact.mobile2) like lower('%$MOBILE2%')";
		String addressNumCond = " and lower(contact.address_num) like lower('%$ADDRESS_NUM%')";
		String addressStreetCond = " and contact.address_street = (select id from streets where name = '$ADDRESS_STREET')";
		String addressRegionCond = " and contact.address_region = (select id from regions where name = '$ADDRESS_REGION')";
		String addressDistrictCond = " and contact.address_district = (select id from districts where name = '$ADDRESS_DISTRICT')";
		String addressFreeCond = " and lower(contact.address_free) like lower('%$ADDRESS_FREE%')";
		String emailCond = " and lower(contact.email) like lower('%$EMAIL%')";
		String jobCond = " and lower(profile.job) like lower('%$JOB%')";

		String classCond = " and class.name = '$CLASS_NAME'";

		String yearCond = " and year.name = '$YEAR_NAME'";

		String familyCond = " and family.name = '$FAMILY_NAME'";

		String sectionCond = " and section.name = '$SECTION_NAME'";

		String birthdayDayCond = " and to_char(profile.birthday, 'dd') = '$DAY'";
		String birthdayMonthCond = " and to_char(profile.birthday, 'mm') = '$MONTH'";
		String birthdayYearCond = " and to_char(profile.birthday, 'yyyy') = '$YEAR'";

		if (Validator.validateString(id)) {
			idCond = idCond.replace("$ID", id);
			QUERY += idCond;
		}

		if (Validator.validateString(name)) {
			nameCond = nameCond.replace("$NAME", name);
			QUERY += nameCond;
		}

		if (Validator.validateString(notes)) {
			notesCond = notesCond.replace("$NOTES", notes);
			QUERY += notesCond;
		}

		if (Validator.validateString(birthday)) {
			birthdayCond = birthdayCond.replace("$BIRTHDAY", birthday);
			QUERY += birthdayCond;
		}

		if (Validator.validateString(education)) {
			educationCond = educationCond.replace("$EDUCATION", education);
			QUERY += educationCond;
		}

		if (Validator.validateString(eccEducation)) {
			eccEducationCond = eccEducationCond.replace("$ECC_EDUCATION", eccEducation);
			QUERY += eccEducationCond;
		}

		if (Validator.validateString(courses)) {
			coursesCond = coursesCond.replace("$COURSES", courses);
			QUERY += coursesCond;
		}

		if (Validator.validateString(skills)) {
			skillsCond = skillsCond.replace("$SKILLS", skills);
			QUERY += skillsCond;
		}

		if (Validator.validateString(phone)) {
			phoneCond = phoneCond.replace("$PHONE", phone);
			QUERY += phoneCond;
		}

		if (Validator.validateString(mobile1)) {
			mobile1Cond = mobile1Cond.replace("$MOBILE1", mobile1);
			QUERY += mobile1Cond;
		}

		if (Validator.validateString(mobile2)) {
			mobile2Cond = mobile2Cond.replace("$MOBILE2", mobile2);
			QUERY += mobile2Cond;
		}

		if (Validator.validateString(addressNum)) {
			addressNumCond = addressNumCond.replace("$ADDRESS_NUM", addressNum);
			QUERY += addressNumCond;
		}

		if (Validator.validateString(addressStreet)) {
			addressStreetCond = addressStreetCond.replace("$ADDRESS_STREET", addressStreet);
			QUERY += addressStreetCond;
		}

		if (Validator.validateString(addressRegion)) {
			addressRegionCond = addressRegionCond.replace("$ADDRESS_REGION", addressRegion);
			QUERY += addressRegionCond;
		}

		if (Validator.validateString(addressDistrict)) {
			addressDistrictCond = addressDistrictCond.replace("$ADDRESS_DISTRICT", addressDistrict);
			QUERY += addressDistrictCond;
		}

		if (Validator.validateString(addressFree)) {
			addressFreeCond = addressFreeCond.replace("$ADDRESS_FREE", addressFree);
			QUERY += addressFreeCond;
		}

		if (Validator.validateString(email)) {
			emailCond = emailCond.replace("$EMAIL", email);
			QUERY += emailCond;
		}

		if (Validator.validateString(job)) {
			jobCond = jobCond.replace("$JOB", job);
			QUERY += jobCond;
		}

		if (Validator.validateString(birthdayDay)) {
			birthdayDayCond = birthdayDayCond.replace("$DAY", birthdayDay);
			QUERY += birthdayDayCond;
		}

		if (Validator.validateString(birthdayMonth)) {
			birthdayMonthCond = birthdayMonthCond.replace("$MONTH", birthdayMonth);
			QUERY += birthdayMonthCond;
		}

		if (Validator.validateString(birthdayYear)) {
			birthdayYearCond = birthdayYearCond.replace("$YEAR", birthdayYear);
			QUERY += birthdayYearCond;
		}

		if (ifLevelSelected) {
			if (levelItem.equals(levelsNames[4])) {
				classCond = classCond.replace("$CLASS_NAME", levelValue);
				QUERY += classCond;
			}

			else if (levelItem.equals(levelsNames[3])) {
				yearCond = yearCond.replace("$YEAR_NAME", levelValue);
				QUERY += yearCond;
			}

			else if (levelItem.equals(levelsNames[2])) {
				familyCond = familyCond.replace("$FAMILY_NAME", levelValue);
				QUERY += familyCond;
			}

			else if (levelItem.equals(levelsNames[1])) {
				sectionCond = sectionCond.replace("$SECTION_NAME", levelValue);
				QUERY += sectionCond;
			}
		}

		return QUERY;
	}

	public static String insertChild(String id, String notes) {
		String QUERY = "insert into child (id, notes) values ($ID, '$NOTES')";

		QUERY = QUERY.replace("$ID", id);

		if (Validator.validateString(notes)) {
			QUERY = QUERY.replace("$NOTES", notes);
		} else {
			QUERY = QUERY.replace("'$NOTES'", "null");
		}

		return QUERY;
	}

	public static String selectChildById(String id) {
		String QUERY = "select notes from child where id = $ID";

		QUERY = QUERY.replace("$ID", id);

		return QUERY;
	}

	public static String updateChild(String id, String notes) {
		String QUERY = "update child set notes = '$NOTES' where id = $ID";

		QUERY = QUERY.replace("$ID", id);

		if (Validator.validateString(notes)) {
			QUERY = QUERY.replace("$NOTES", notes);
		} else {
			QUERY = QUERY.replace("'$NOTES'", "null");
		}

		return QUERY;
	}

	public static String selectChildAll(String id, boolean ifSelectId, String name, boolean ifSelectName,
			String notes, boolean ifSelectNotes, String birthday, boolean ifSelectBirthday,
			String nationalId, boolean ifSelectNationalId, String education, boolean ifSelectEducation,
			String eccEducation, boolean ifSelectEccEducation, String courses, boolean ifSelectCourses,
			String skills, boolean ifSelectSkills, String phone, boolean ifSelectPhone, String mobile1,
			boolean ifSelectMobile1, String mobile2, boolean ifSelectMobile2, String addressNum,
			boolean ifSelectAddressNum, String addressStreet, boolean ifSelectAddressStreet,
			String addressRegion, boolean ifSelectAddressRegion, String addressDistrict,
			boolean ifSelectAddressDistrict, String addressFree, boolean ifSelectaddressFree, String email,
			boolean ifSelectEmail, boolean ifSelectActiveDate, boolean ifLevelSelected, String levelItem,
			String levelValue, boolean ifSelectSection, boolean ifSelectFamily, boolean ifSelectYear,
			boolean ifSelectClass, boolean ifSelectMinister, String birthdayDay, String birthdayMonth,
			String birthdayYear) {

		String headerQuery = "select 'ÇáÑÞã', 'ÇáÇÓã'";
		String QUERY = "select to_char(member.id), member.name";
		String[] levelsNames = new String[] { "ÇáÊÑÈíÉ ÇáßäÓíÉ", "ÞØÇÚ", "ÃÓÑÉ", "ÕÝ", "ÝÕá", "ÎÇÏã" };

		if (ifSelectAddressNum) {
			headerQuery += ", 'ÇáÚäæÇä - ÑÞã'";
			QUERY += ", contact.address_num";
		}
		if (ifSelectAddressStreet) {
			headerQuery += ", 'ÇáÚäæÇä - ÔÇÑÚ'";
			QUERY += ", (select name from streets where streets.id = contact.address_street)";
		}
		if (ifSelectAddressRegion) {
			headerQuery += ", 'ÇáÚäæÇä - ãäØÞÉ'";
			QUERY += ", (select name from regions where regions.id = contact.address_region)";
		}
		if (ifSelectAddressDistrict) {
			headerQuery += ", 'ÇáÚäæÇä - Íí'";
			QUERY += ", (select name from districts where districts.id = contact.address_district)";
		}
		if (ifSelectaddressFree) {
			headerQuery += ", 'ÈÇÞí ÇáÚäæÇä'";
			QUERY += ", contact.address_free";
		}
		if (ifSelectPhone) {
			headerQuery += ", 'ÇáÊáíÝæä'";
			QUERY += ", contact.phone";
		}
		if (ifSelectMobile1) {
			headerQuery += ", 'ãæÈÇíá 1'";
			QUERY += ", contact.mobile1";
		}
		if (ifSelectMobile2) {
			headerQuery += ", 'ãæÈÇíá 2'";
			QUERY += ", contact.mobile2";
		}
		if (ifSelectBirthday) {
			headerQuery += ", 'ÊÇÑíÎ ÇáãíáÇÏ'";
			QUERY += ", to_char(profile.birthday, 'dd-mm-yyyy')";
		}
		if (ifSelectEmail) {
			headerQuery += ", 'ÇáÇíãíá'";
			QUERY += ", contact.email";
		}
		if (ifSelectEducation) {
			headerQuery += ", 'ÇáÊÚáíã'";
			QUERY += ", profile.education";
		}
		if (ifSelectNationalId) {
			headerQuery += ", 'ÇáÑÞã ÇáÞæãí'";
			QUERY += ", profile.national_id";
		}
		if (ifSelectEccEducation) {
			headerQuery += ", 'ÇáÊÚáíã ÇáßäÓí'";
			QUERY += ", profile.ecclesiastic_education";
		}
		if (ifSelectCourses) {
			headerQuery += ", 'ßæÑÓÇÊ'";
			QUERY += ", profile.courses";
		}
		if (ifSelectSkills) {
			headerQuery += ", 'ãåÇÑÇÊ'";
			QUERY += ", profile.skills";
		}
		if (ifSelectNotes) {
			headerQuery += ", 'ãáÇÍÙÇÊ'";
			QUERY += ", child.notes";
		}
		if (ifSelectActiveDate) {
			headerQuery += ", 'ÊÇÑíÎ ÇáÊÝÚíá'";
			QUERY += ", to_char(member.active_date, 'dd-mm-yyyy hh24:mi')";
		}
		if (ifSelectSection) {
			headerQuery += ", 'ÇáÞØÇÚ'";
			QUERY += ", section.name";
		}
		if (ifSelectFamily) {
			headerQuery += ", 'ÇáÃÓÑÉ'";
			QUERY += ", family.name";
		}
		if (ifSelectYear) {
			headerQuery += ", 'ÇáÕÝ'";
			QUERY += ", year.name";
		}
		if (ifSelectClass) {
			headerQuery += ", 'ÇáÝÕá'";
			QUERY += ", class.name";
		}
		if (ifSelectMinister) {
			headerQuery += ", 'ÇáÎÇÏã'";
			QUERY += ", (select name from member where id = minister.id)";
		}

		headerQuery += " from dual";

		// String relationTable =
		// "(select a.minister_id, a.child_id from (select minister_id, child_id, active_from from minister_child_assigned where minister_child_assigned.inactive_date is null) a, ( select child_id, max (active_from) as active_from from minister_child_assigned where minister_child_assigned.inactive_date is null group by child_id) b where a.child_id = b.child_id and a.active_from = b.active_from) relation";

		QUERY += " FROM minister, sub_class, class, year, family, section, MEMBER, child, profile, contact WHERE minister.sub_class_id(+) = sub_class.id AND child.sub_class_id = sub_class.id AND sub_class.class_id = class.id AND class.year_id = year.id AND year.family_id = family.id AND family.section_id = section.id AND MEMBER.id = child.id AND MEMBER.id = profile.id AND MEMBER.id = contact.id AND MEMBER.inactive_date IS NULL";

		QUERY = headerQuery + " UNION ALL " + QUERY;

		String idCond = " and member.id = $ID";
		String nameCond = " and lower(member.name) like lower('%$NAME%')";
		String notesCond = " and lower(child.notes) like lower('%$NOTES%')";
		String birthdayCond = " and profile.birthday = to_date ('$BIRTHDAY', 'dd-mm-yyyy')";
		String nationalIdCond = " and lower(profile.national_id) like lower('%$NATIONAL_ID%')";
		String educationCond = " and lower(profile.education) like lower('%$EDUCATION%')";
		String eccEducationCond = "and lower(profile.ecclesiastic_education) like lower('%$ECC_EDUCATION%')";
		String coursesCond = " and lower(profile.courses) like lower('%$COURSES%')";
		String skillsCond = " and lower(profile.skills) like lower('%$SKILLS%')";
		String phoneCond = " and lower(contact.phone) like lower('%$PHONE%')";
		String mobile1Cond = " and lower(contact.mobile1) like lower('%$MOBILE1%')";
		String mobile2Cond = " and lower(contact.mobile2) like lower('%$MOBILE2%')";
		String addressNumCond = " and lower(contact.address_num) like lower('%$ADDRESS_NUM%')";
		String addressStreetCond = " and contact.address_street = (select id from streets where name = '$ADDRESS_STREET')";
		String addressRegionCond = " and contact.address_region = (select id from regions where name = '$ADDRESS_REGION')";
		String addressDistrictCond = " and contact.address_district = (select id from districts where name = '$ADDRESS_DISTRICT')";
		String addressFreeCond = " and lower(contact.address_free) like lower('%$ADDRESS_FREE%')";
		String emailCond = " and lower(contact.email) like lower('%$EMAIL%')";
		// String ministerCond =
		// " and member.id in (select a.child_id from (select minister_id, child_id, active_from from minister_child_assigned where minister_child_assigned.inactive_date is null) a, (select child_id, max(active_from) as active_from from minister_child_assigned where minister_child_assigned.inactive_date is null group by child_id) b where a.child_id = b.child_id and a.active_from = b.active_from and a.minister_id in (select id from member where name = '$MINISTER_NAME' and type = (select id from member_type where des = 'minister')))";

		String ministerCond = " AND MEMBER.id IN (select id from child where sub_class_id in (select sub_class_id from minister where id in (SELECT id FROM MEMBER WHERE name = '$MINISTER_NAME' AND TYPE = (SELECT id FROM member_type WHERE des = 'minister'))))";

		String classCond = " and class.name = '$CLASS_NAME'";

		String yearCond = " and year.name = '$YEAR_NAME'";

		String familyCond = " and family.name = '$FAMILY_NAME'";

		String sectionCond = " and section.name = '$SECTION_NAME'";

		String birthdayDayCond = " and to_char(profile.birthday, 'dd') = '$DAY'";
		String birthdayMonthCond = " and to_char(profile.birthday, 'mm') = '$MONTH'";
		String birthdayYearCond = " and to_char(profile.birthday, 'yyyy') = '$YEAR'";

		if (Validator.validateString(id)) {
			idCond = idCond.replace("$ID", id);
			QUERY += idCond;
		}

		if (Validator.validateString(name)) {
			nameCond = nameCond.replace("$NAME", name);
			QUERY += nameCond;
		}

		if (Validator.validateString(notes)) {
			notesCond = notesCond.replace("$NOTES", notes);
			QUERY += notesCond;
		}

		if (Validator.validateString(birthday)) {
			birthdayCond = birthdayCond.replace("$BIRTHDAY", birthday);
			QUERY += birthdayCond;
		}

		if (Validator.validateString(nationalId)) {
			nationalIdCond = nationalIdCond.replace("$NATIONAL_ID", nationalId);
			QUERY += nationalIdCond;
		}

		if (Validator.validateString(education)) {
			educationCond = educationCond.replace("$EDUCATION", education);
			QUERY += educationCond;
		}

		if (Validator.validateString(eccEducation)) {
			eccEducationCond = eccEducationCond.replace("$ECC_EDUCATION", eccEducation);
			QUERY += eccEducationCond;
		}

		if (Validator.validateString(courses)) {
			coursesCond = coursesCond.replace("$COURSES", courses);
			QUERY += coursesCond;
		}

		if (Validator.validateString(skills)) {
			skillsCond = skillsCond.replace("$SKILLS", skills);
			QUERY += skillsCond;
		}

		if (Validator.validateString(phone)) {
			phoneCond = phoneCond.replace("$PHONE", phone);
			QUERY += phoneCond;
		}

		if (Validator.validateString(mobile1)) {
			mobile1Cond = mobile1Cond.replace("$MOBILE1", mobile1);
			QUERY += mobile1Cond;
		}

		if (Validator.validateString(mobile2)) {
			mobile2Cond = mobile2Cond.replace("$MOBILE2", mobile2);
			QUERY += mobile2Cond;
		}

		if (Validator.validateString(addressNum)) {
			addressNumCond = addressNumCond.replace("$ADDRESS_NUM", addressNum);
			QUERY += addressNumCond;
		}

		if (Validator.validateString(addressStreet)) {
			addressStreetCond = addressStreetCond.replace("$ADDRESS_STREET", addressStreet);
			QUERY += addressStreetCond;
		}

		if (Validator.validateString(addressRegion)) {
			addressRegionCond = addressRegionCond.replace("$ADDRESS_REGION", addressRegion);
			QUERY += addressRegionCond;
		}

		if (Validator.validateString(addressDistrict)) {
			addressDistrictCond = addressDistrictCond.replace("$ADDRESS_DISTRICT", addressDistrict);
			QUERY += addressDistrictCond;
		}

		if (Validator.validateString(addressFree)) {
			addressFreeCond = addressFreeCond.replace("$ADDRESS_FREE", addressFree);
			QUERY += addressFreeCond;
		}

		if (Validator.validateString(email)) {
			emailCond = emailCond.replace("$EMAIL", email);
			QUERY += emailCond;
		}

		if (Validator.validateString(birthdayDay)) {
			birthdayDayCond = birthdayDayCond.replace("$DAY", birthdayDay);
			QUERY += birthdayDayCond;
		}

		if (Validator.validateString(birthdayMonth)) {
			birthdayMonthCond = birthdayMonthCond.replace("$MONTH", birthdayMonth);
			QUERY += birthdayMonthCond;
		}

		if (Validator.validateString(birthdayYear)) {
			birthdayYearCond = birthdayYearCond.replace("$YEAR", birthdayYear);
			QUERY += birthdayYearCond;
		}

		if (ifLevelSelected) {
			if (levelItem.equals(levelsNames[5])) {
				ministerCond = ministerCond.replace("$MINISTER_NAME", levelValue);
				QUERY += ministerCond;
			}

			else if (levelItem.equals(levelsNames[4])) {
				classCond = classCond.replace("$CLASS_NAME", levelValue);
				QUERY += classCond;
			}

			else if (levelItem.equals(levelsNames[3])) {
				yearCond = yearCond.replace("$YEAR_NAME", levelValue);
				QUERY += yearCond;
			}

			else if (levelItem.equals(levelsNames[2])) {
				familyCond = familyCond.replace("$FAMILY_NAME", levelValue);
				QUERY += familyCond;
			}

			else if (levelItem.equals(levelsNames[1])) {
				sectionCond = sectionCond.replace("$SECTION_NAME", levelValue);
				QUERY += sectionCond;
			}
		}

		return QUERY;
	}

	public static String deleteMember(String id) {
		String QUERY = "update member set member.inactive_date = sysdate where id = $ID";

		QUERY = QUERY.replace("$ID", id);

		return QUERY;
	}

	public static String selectAllSections() {
		String QUERY = "select name from section order by name";

		return QUERY;
	}

	public static String selectSectionByName(String name) {
		String QUERY = "select name from section where name = '$NAME'";

		QUERY = QUERY.replace("$NAME", name);

		return QUERY;
	}

	public static String insertNewSection(String name) {
		String QUERY = "insert into section(id, name) values ((select nvl(max(id)+1, 1) from section), '$NAME')";

		QUERY = QUERY.replace("$NAME", name);

		return QUERY;
	}

	public static String updateSection(String oldName, String newName) {
		String QUERY = "update section set name = '$NEW_NAME' where name = '$OLD_NAME'";

		QUERY = QUERY.replace("$OLD_NAME", oldName);
		QUERY = QUERY.replace("$NEW_NAME", newName);

		return QUERY;
	}

	public static String deleteSection(String sectionName) {
		String QUERY = "delete section where name = '$SECTION_NAME'";

		QUERY = QUERY.replace("$SECTION_NAME", sectionName);

		return QUERY;
	}

	public static String selectFamiliesBySection(String sectionName) {
		String QUERY = "select name from family where section_id = (select id from section where name = '$SECTION_NAME') order by name";

		QUERY = QUERY.replace("$SECTION_NAME", sectionName);

		return QUERY;
	}

	public static String insertNewFamily(String name, String sectionName) {
		String QUERY = "insert into family(id, name, section_id) values ((select nvl(max(id)+1, 1) from family), '$NAME', (select id from section where name = '$SECTION_NAME'))";

		QUERY = QUERY.replace("$NAME", name);
		QUERY = QUERY.replace("$SECTION_NAME", sectionName);

		return QUERY;
	}

	public static String updateFamily(String oldName, String newName) {
		String QUERY = "update family set name = '$NEW_NAME' where name = '$OLD_NAME'";

		QUERY = QUERY.replace("$OLD_NAME", oldName);
		QUERY = QUERY.replace("$NEW_NAME", newName);

		return QUERY;
	}

	public static String deleteFamily(String name) {
		String QUERY = "delete family where name = '$NAME'";

		QUERY = QUERY.replace("$NAME", name);

		return QUERY;
	}

	public static String selectFamilyByName(String name) {
		String QUERY = "select * from family where name = '$NAME'";

		QUERY = QUERY.replace("$NAME", name);

		return QUERY;
	}

	public static String selectYearsByFamily(String familyName) {
		String QUERY = "select name from year where family_id = (select id from family where name = '$FAMILY_NAME') order by name";

		QUERY = QUERY.replace("$FAMILY_NAME", familyName);

		return QUERY;
	}

	public static String insertNewYear(String name, String familyName) {
		String QUERY = "insert into year(id, name, family_id) values ((select nvl(max(id)+1, 1) from year), '$NAME', (select id from family where name = '$FAMILY_NAME'))";

		QUERY = QUERY.replace("$NAME", name);
		QUERY = QUERY.replace("$FAMILY_NAME", familyName);

		return QUERY;
	}

	public static String updateYear(String oldName, String newName) {
		String QUERY = "update year set name = '$NEW_NAME' where name = '$OLD_NAME'";

		QUERY = QUERY.replace("$OLD_NAME", oldName);
		QUERY = QUERY.replace("$NEW_NAME", newName);

		return QUERY;
	}

	public static String deleteYear(String name) {
		String QUERY = "delete year where name = '$NAME'";

		QUERY = QUERY.replace("$NAME", name);

		return QUERY;
	}

	public static String selectYearByName(String name) {
		String QUERY = "select * from year where name = '$NAME'";

		QUERY = QUERY.replace("$NAME", name);

		return QUERY;
	}

	public static String selectClassesByYear(String yearName) {
		String QUERY = "select name from class where year_id = (select id from year where name = '$YEAR_NAME') order by name";

		QUERY = QUERY.replace("$YEAR_NAME", yearName);

		return QUERY;
	}

	public static String insertNewClass(String name, String yearName) {
		String QUERY = "insert into class(id, name, year_id) values ((select nvl(max(id)+1, 1) from class), '$NAME', (select id from year where name = '$YEAR_NAME'))";

		QUERY = QUERY.replace("$NAME", name);
		QUERY = QUERY.replace("$YEAR_NAME", yearName);

		return QUERY;
	}

	public static String insertNewSubClass(String name, String className) {
		String QUERY = "insert into sub_class (id, name, class_id) values ((select nvl(max(id)+1, 1) from sub_class), '$NAME', (select id from class where name = '$CLASS_NAME'))";

		QUERY = QUERY.replace("$NAME", name);
		QUERY = QUERY.replace("$CLASS_NAME", className);

		return QUERY;
	}

	public static String updateClass(String oldName, String newName) {
		String QUERY = "update class set name = '$NEW_NAME' where name = '$OLD_NAME'";

		QUERY = QUERY.replace("$OLD_NAME", oldName);
		QUERY = QUERY.replace("$NEW_NAME", newName);

		return QUERY;
	}

	public static String updateSubClass(String oldName, String newName) {
		String QUERY = "update sub_class set name = '$NEW_NAME' where name = '$OLD_NAME'";

		QUERY = QUERY.replace("$OLD_NAME", oldName);
		QUERY = QUERY.replace("$NEW_NAME", newName);

		return QUERY;
	}

	public static String deleteClass(String name) {
		String QUERY = "delete class where name = '$NAME'";

		QUERY = QUERY.replace("$NAME", name);

		return QUERY;
	}

	public static String deleteSubClass(String name) {
		String QUERY = "delete sub_class where name = '$NAME'";

		QUERY = QUERY.replace("$NAME", name);

		return QUERY;
	}

	public static String selectClassByName(String name) {
		String QUERY = "select * from class where name = '$NAME'";

		QUERY = QUERY.replace("$NAME", name);

		return QUERY;
	}

	public static String selectSubClassByName(String name) {
		String QUERY = "select * from sub_class where name = '$NAME'";

		QUERY = QUERY.replace("$NAME", name);

		return QUERY;
	}

	public static String selectSubClassByClass(String className) {
		String QUERY = "select name from sub_class where class_id = (select id from class where name = '$CLASS_NAME') order by name";

		QUERY = QUERY.replace("$CLASS_NAME", className);

		return QUERY;
	}

	public static String selectMinisterBySubClass(String subClassName) {
		String QUERY = "select name from member where id = (select id from minister where sub_class_id = (select id from sub_class where name = '$SUB_CLASS_NAME')) and member.inactive_date is null";

		QUERY = QUERY.replace("$SUB_CLASS_NAME", subClassName);

		return QUERY;
	}

	public static String selectChildrenBySubClass(String subClassName) {
		String QUERY = "select name from member where id in (select id from child where sub_class_id = (select id from sub_class where name = '$SUB_CLASS_NAME')) and member.inactive_date is null order by name";

		QUERY = QUERY.replace("$SUB_CLASS_NAME", subClassName);

		return QUERY;
	}

	public static String selectMinistersByClass(String className) {
		String QUERY = "select name from member where id in (select id from minister where sub_class_id in (select id from sub_class where class_id = (select id from class where name = '$CLASS_NAME'))) and member.inactive_date is null order by name";

		QUERY = QUERY.replace("$CLASS_NAME", className);

		return QUERY;
	}

	public static String selectMinistersByYear(String yearName) {
		String QUERY = "select name from member where id in (select id from minister where sub_class_id in (select id from sub_class where class_id in(select id from class where year_id = (select id from year where name = '$YEAR_NAME')))) and member.inactive_date is null order by name";

		QUERY = QUERY.replace("$YEAR_NAME", yearName);

		return QUERY;
	}

	public static String selectMinistersByFamily(String familyName) {
		String QUERY = "select name from member where id in (select id from minister where sub_class_id in (select id from sub_class where class_id in (select id from class where year_id in (select id from year where family_id = (select id from family where name = '$FAMILY_NAME'))))) and member.inactive_date is null order by name";

		QUERY = QUERY.replace("$FAMILY_NAME", familyName);

		return QUERY;
	}

	public static String selectMinistersBySection(String sectionName) {
		String QUERY = "select name from member where id in (select id from minister where sub_class_id in (select id from sub_class where class_id in (select id from class where year_id in (select id from year where family_id in (select id from family where section_id = (select id from section where name = '$SECTION_NAME')))))) and member.inactive_date is null order by name";

		QUERY = QUERY.replace("$SECTION_NAME", sectionName);

		return QUERY;
	}

	public static String selectMinisterIdAndNameByClass(String className) {
		String QUERY = "select id, name from member where id in (select id from minister where sub_class_id in (select id from sub_class where class_id = (select id from class where name = '$CLASS_NAME'))) and member.inactive_date is null order by name";

		QUERY = QUERY.replace("$CLASS_NAME", className);

		return QUERY;
	}

	public static String selectMinisterIdAndNameByYear(String yearName) {
		String QUERY = "select id, name from member where id in (select id from minister where sub_class_id in (select id from sub_class where class_id in (select id from class where year_id = (select id from year where name = '$YEAR_NAME')))) and member.inactive_date is null order by name";

		QUERY = QUERY.replace("$YEAR_NAME", yearName);

		return QUERY;
	}

	public static String selectMinisterIdAndNameByFamily(String familyName) {
		String QUERY = "select id, name from member where id in (select id from minister where sub_class_id in (select id from sub_class where class_id in (select id from class where year_id in (select id from year where family_id = (select id from family where name = '$FAMILY_NAME'))))) and member.inactive_date is null order by name";

		QUERY = QUERY.replace("$FAMILY_NAME", familyName);

		return QUERY;
	}

	public static String selectMinisterIdAndNameBySection(String sectionName) {
		String QUERY = "select id, name from member where id in (select id from minister where sub_class_id in (select id from sub_class where class_id in (select id from class where year_id in (select id from year where family_id in (select id from family where section_id = (select id from section where name = '$SECTION_NAME')))))) and member.inactive_date is null order by name";

		QUERY = QUERY.replace("$SECTION_NAME", sectionName);

		return QUERY;
	}

	public static String updateClassOfSubClass(String className, String SubClassName) {
		String QUERY = "update sub_class set class_id = (select id from class where name = '$CLASS_NAME') where name = '$SUB_CLASS_NAME'";

		QUERY = QUERY.replace("$CLASS_NAME", className);
		QUERY = QUERY.replace("$SUB_CLASS_NAME", SubClassName);

		return QUERY;
	}

	public static String selectChildrenByMinister(String ministerName) {
		String QUERY = "select id, name from member where id in (select id from child where sub_class_id in (select sub_class_id from minister where id in (select id from member where name = '$MINISTER_NAME' and type = (select id from member_type where des = 'minister')))) order by name";

		QUERY = QUERY.replace("$MINISTER_NAME", ministerName);

		return QUERY;
	}

	public static String selectChildrenByMinisterAndDate(String ministerName, String date) {
		String QUERY = "SELECT id, name FROM MEMBER WHERE id IN (SELECT a.child_id FROM (SELECT minister_id, child_id, active_from FROM minister_child_assigned where inactive_date >= TO_DATE ('$DATE', 'dd-mm-yyyy') or inactive_date is null) a, ( SELECT child_id, MAX (active_from) AS active_from FROM minister_child_assigned WHERE active_from <= TO_DATE ('$DATE', 'dd-mm-yyyy') GROUP BY child_id) b WHERE a.child_id = b.child_id AND a.active_from = b.active_from AND a.minister_id = (SELECT id FROM MEMBER WHERE name = '$MINISTER_NAME' AND TYPE = (SELECT id FROM member_type WHERE des = 'minister'))) ORDER BY name";

		QUERY = QUERY.replace("$MINISTER_NAME", ministerName);
		QUERY = QUERY.replace("$DATE", date);

		return QUERY;
	}

	public static String deleteMinisterChildAssignedForSameChildAndDay(String childName) {
		String QUERY = "delete minister_child_assigned where child_id = (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child')) and active_from = to_date(to_char(sysdate, 'dd-mm-yyyy'), 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$CHILD_NAME", childName);

		return QUERY;
	}

	public static String updateAllMinisterChildAssignedByChildName(String childName) {
		String QUERY = "update minister_child_assigned set minister_child_assigned.inactive_date = sysdate where child_id = (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child')) and minister_child_assigned.inactive_date is null";

		QUERY = QUERY.replace("$CHILD_NAME", childName);

		return QUERY;
	}

	public static String updateAllMinisterChildAssignedByMinisterName(String ministerName) {
		String QUERY = "update minister_child_assigned set minister_child_assigned.inactive_date = sysdate where minister_id = (select id from member where name = '$MINISTER_NAME' and type = (select id from member_type where des = 'minister')) and minister_child_assigned.inactive_date is null";

		QUERY = QUERY.replace("$MINISTER_NAME", ministerName);

		return QUERY;
	}

	public static String insertMinisterChildAssigned(String ministerName, String childName) {
		String QUERY = "insert into minister_child_assigned (minister_id, child_id, active_from) values ((select id from member where name = '$MINISTER_NAME' and type = (select id from member_type where des = 'minister')), (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child')), to_date(to_char(sysdate, 'dd-mm-yyyy'), 'dd-mm-yyyy'))";

		QUERY = QUERY.replace("$MINISTER_NAME", ministerName);
		QUERY = QUERY.replace("$CHILD_NAME", childName);

		return QUERY;
	}

	public static String selectAllFollowupComments() {
		String QUERY = "select content from followup_comment where content is not null order by count desc";

		return QUERY;
	}

	public static String selectAllClasses() {
		String QUERY = "select name from class order by name";

		return QUERY;
	}

	public static String selectSubClassesExcludingSomeClass(String className) {
		String QUERY = "select name from sub_class where class_id not in (select id from class where name = '$CLASS_NAME')";

		QUERY = QUERY.replace("$CLASS_NAME", className);

		return QUERY;
	}

	public static String selectClassesExcludingSomeClassOfSubClass(String subClassName) {
		String QUERY = "select name from class where id not in (select class_id from sub_class where name = '$SUB_CLASS_NAME')";

		QUERY = QUERY.replace("$SUB_CLASS_NAME", subClassName);

		return QUERY;
	}

	public static String updateClassYear(String yearName, String className) {
		String QUERY = "update class set year_id = (select id from year where name = '$YEAR_NAME') where name = '$CLASS_NAME'";

		QUERY = QUERY.replace("$YEAR_NAME", yearName);
		QUERY = QUERY.replace("$CLASS_NAME", className);

		return QUERY;
	}

	public static String selectAllYears() {
		String QUERY = "select name from year order by name";

		return QUERY;
	}

	public static String updateYearFamily(String familyName, String yearName) {
		String QUERY = "update year set family_id = (select id from family where name = '$FAMILY_NAME') where name = '$YEAR_NAME'";

		QUERY = QUERY.replace("$FAMILY_NAME", familyName);
		QUERY = QUERY.replace("$YEAR_NAME", yearName);

		return QUERY;
	}

	public static String selectAllFamilies() {
		String QUERY = "select name from family order by name";

		return QUERY;
	}

	public static String updateFamilySection(String sectionName, String familyName) {
		String QUERY = "update family set section_id = (select id from section where name = '$SECTION_NAME') where name = '$FAMILY_NAME'";

		QUERY = QUERY.replace("$SECTION_NAME", sectionName);
		QUERY = QUERY.replace("$FAMILY_NAME", familyName);

		return QUERY;
	}

	public static String selectMinistryAttendantsByDate(String actionDate) {
		String QUERY = "select id from child_ministry_attendance where action_date = to_date ('$ACTION_DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String selectMassAttendantsByDate(String actionDate) {
		String QUERY = "select id from child_mass_attendance where action_date = to_date ('$ACTION_DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String selectFollowupDataByDate(String actionDate) {
		String QUERY = "select id, (select des from followup_methods where id = followup_by), (select content from followup_comment where id = comment_id) from child_followup where action_date = to_date ('$ACTION_DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String insertChildMinistryAttendance(String childName, String actionDate) {
		String QUERY = "insert into child_ministry_attendance (id, action_date) values ((select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child')), to_date('$ACTION_DATE', 'dd-mm-yyyy'))";

		QUERY = QUERY.replace("$CHILD_NAME", childName);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String insertChildMassAttendance(String childName, String actionDate) {
		String QUERY = "insert into child_mass_attendance (id, action_date) values ((select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child')), to_date('$ACTION_DATE', 'dd-mm-yyyy'))";

		QUERY = QUERY.replace("$CHILD_NAME", childName);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String deleteChildMinistryAttendance(String childName, String actionDate) {
		String QUERY = "delete child_ministry_attendance where id = (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child')) and action_date = to_date('$ACTION_DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$CHILD_NAME", childName);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String deleteChildMassAttendance(String childName, String actionDate) {
		String QUERY = "delete child_mass_attendance where id = (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child')) and action_date = to_date('$ACTION_DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$CHILD_NAME", childName);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String insertChildFollowup(String childName, String actionDate) {
		String QUERY = "insert into child_followup (id, action_date, followup_by) values ((select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child')), to_date('$ACTION_DATE', 'dd-mm-yyyy'), (select id from followup_methods where des = 'ãßÇáãÉ ÊáíÝæäíÉ'))";

		QUERY = QUERY.replace("$CHILD_NAME", childName);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String deleteChildFollowup(String childName, String actionDate) {
		String QUERY = "delete child_followup where id = (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child')) and action_date = to_date('$ACTION_DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$CHILD_NAME", childName);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String updateFollowupBy(String childName, String actionDate, String followupBy) {
		String QUERY = "update child_followup set followup_by = (select id from followup_methods where des = '$FOLLOWUP_BY') where id = (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child')) and action_date = to_date('$ACTION_DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$CHILD_NAME", childName);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);
		QUERY = QUERY.replace("$FOLLOWUP_BY", followupBy);

		return QUERY;
	}

	public static String updateFollowupComment(String childName, String actionDate, String followupComment) {
		String QUERY = "update child_followup set comment_id = (select id from followup_comment where content = '$FOLLOWUP_COMMENT') where id = (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child')) and action_date = to_date('$ACTION_DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$CHILD_NAME", childName);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);
		QUERY = QUERY.replace("$FOLLOWUP_COMMENT", followupComment);

		return QUERY;
	}

	public static String updateFollowupCommentCount(String comment) {
		String QUERY = "update followup_comment set count = (count+1) where content = '$COMMENT'";

		if (Validator.validateString(comment)) {
			QUERY = QUERY.replace("$COMMENT", comment);
		} else {
			QUERY = QUERY.replace("= '$COMMENT'", "is null");
		}

		return QUERY;
	}

	public static String insertNewFollowupComment(String comment) {
		String QUERY = "insert into followup_comment (id, count, content) values ((select nvl(max(id)+1, 1) from followup_comment), 1, '$COMMENT')";

		if (Validator.validateString(comment)) {
			QUERY = QUERY.replace("$COMMENT", comment);
		} else {
			QUERY = QUERY.replace("'$COMMENT'", "null");
		}

		return QUERY;
	}

	public static String selectChildEteqadData(String childId, String actionDate) {
		String QUERY = "select * from "
				+ "(select a.id, a.name, b.mobile1, b.address_num || ' ' || (select name from streets where id = b.address_street) || ' ' || (select name from regions where id = b.address_region) || ' ' || (select name from districts where id = b.address_district) || ' ' || b.address_free as address from member a, contact b where a.id = b.id and a.id = $ID) a, "
				+ "(select count(1) as today_ministry_attendance from child_ministry_attendance where id = $ID and action_date = to_date('$ACTION_DATE', 'dd-mm-yyyy')) b, "
				+ "(select (b.total_count - a.attendance_count) as ministry_total_gheyab from (select count(1) as attendance_count from child_ministry_attendance where id = $ID and action_date >= (select to_date(value, 'dd-mm-yyyy') from nvpair where name = 'ministry_start_date') and action_date <= to_date ('$ACTION_DATE', 'dd-mm-yyyy')) a, (select (((to_date('$ACTION_DATE', 'dd-mm-yyyy') - to_date(value, 'dd-mm-yyyy')) / 7) + 1) as total_count from nvpair where name = 'ministry_start_date') b) c, "
				+ "(select count(1) as total_followup_by_phone from child_followup where id = $ID and followup_by = (select id from followup_methods where des = 'ãßÇáãÉ ÊáíÝæäíÉ') and action_date >= (select to_date(value, 'dd-mm-yyyy') from nvpair where name = 'ministry_start_date') and action_date <= to_date ('$ACTION_DATE', 'dd-mm-yyyy')) d, "
				+ "(select count(1) as total_followup_by_home from child_followup where id = $ID and followup_by = (select id from followup_methods where des = 'ÒíÇÑÉ ãäÒáíÉ') and action_date >= (select to_date(value, 'dd-mm-yyyy') from nvpair where name = 'ministry_start_date') and action_date <= to_date ('$ACTION_DATE', 'dd-mm-yyyy')) e, "
				+ "(select nvl(a.count, (select (((to_date('$ACTION_DATE', 'dd-mm-yyyy') - to_date(value, 'dd-mm-yyyy')) / 7) + 1) from nvpair where name = 'ministry_start_date')) as mass_gheyab_motataly from (select ((to_date('$ACTION_DATE', 'dd-mm-yyyy') - max(action_date)) / 7) as count from child_mass_attendance where id = $ID and action_date >= (select to_date(value, 'dd-mm-yyyy') from nvpair where name = 'ministry_start_date') and action_date <= to_date ('$ACTION_DATE', 'dd-mm-yyyy')) a) f, "
				+ "(select decode(count(1), 0, 'áÇ', 'äÚã') as today_mass_attendance from child_mass_attendance where id = $ID and action_date = to_date('$ACTION_DATE', 'dd-mm-yyyy')) g, "
				+ "(select nvl(a.count, (select (((to_date('$ACTION_DATE', 'dd-mm-yyyy') - to_date(value, 'dd-mm-yyyy')) / 7) + 1) from nvpair where name = 'ministry_start_date')) as ministry_gheyab_motataly from (select ((to_date('$ACTION_DATE', 'dd-mm-yyyy') - max(action_date)) / 7) as count from child_ministry_attendance where id = $ID and action_date <= to_date ('$ACTION_DATE', 'dd-mm-yyyy') and action_date >= (select to_date(value, 'dd-mm-yyyy') from nvpair where name = 'ministry_start_date')) a) h, "
				+ "(select nvl(count(1), 0) as eftqad_motataly from child_followup where id = $ID AND action_date <= TO_DATE ('$ACTION_DATE', 'dd-mm-yyyy') AND action_date >= (SELECT MAX (action_date) FROM child_ministry_attendance WHERE id = $ID AND action_date <= TO_DATE ('$ACTION_DATE', 'dd-mm-yyyy') AND action_date >= (SELECT TO_DATE (VALUE, 'dd-mm-yyyy') FROM nvpair WHERE name = 'ministry_start_date'))) i";

		QUERY = QUERY.replace("$ID", childId);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String selectMinisterClass(String ministerName) {
		String QUERY = "select section.name || ' | ' || family.name || ' | ' || year.name || ' | ' || class.name from section, family, year, class, sub_class, member, minister where section.id = family.section_id and family.id = year.family_id and year.id = class.year_id and class.id = sub_class.class_id and sub_class.id = minister.sub_class_id and member.id = minister.id and member.name = '$MINISTER_NAME' and member.type = (select id from member_type where des = 'minister')";

		QUERY = QUERY.replace("$MINISTER_NAME", ministerName);

		return QUERY;
	}

	public static String updateME_mass(String name, String actionDate, String value) {

		String QUERY = "update minister_evaluation set mass = $MASS where id = (select id from member where name = '$NAME' and type = (select id from member_type where des = 'minister')) and action_date = to_date('$ACTION_DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$NAME", name);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		if (Validator.validateString(value)) {
			QUERY = QUERY.replace("$MASS", value);
		} else {
			QUERY = QUERY.replace("'$MASS'", "null");
		}
		return QUERY;
	}

	public static String updateME_ministersMeeting(String name, String actionDate, String value) {

		String QUERY = "update minister_evaluation set ministers_meeting = $MINISTERS_MEETING where id = (select id from member where name = '$NAME' and type = (select id from member_type where des = 'minister')) and action_date = to_date('$ACTION_DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$NAME", name);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		if (Validator.validateString(value)) {
			QUERY = QUERY.replace("$MINISTERS_MEETING", value);
		} else {
			QUERY = QUERY.replace("'$MINISTERS_MEETING'", "null");
		}
		return QUERY;
	}

	public static String updateME_familyMeeting(String name, String actionDate, String value) {

		String QUERY = "update minister_evaluation set family_meeting = $FAMILY_MEETING where id = (select id from member where name = '$NAME' and type = (select id from member_type where des = 'minister')) and action_date = to_date('$ACTION_DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$NAME", name);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		if (Validator.validateString(value)) {
			QUERY = QUERY.replace("$FAMILY_MEETING", value);
		} else {
			QUERY = QUERY.replace("'$FAMILY_MEETING'", "null");
		}
		return QUERY;
	}

	public static String updateME_ministry(String name, String actionDate, String value) {

		String QUERY = "update minister_evaluation set ministry = $MINISTRY where id = (select id from member where name = '$NAME' and type = (select id from member_type where des = 'minister')) and action_date = to_date('$ACTION_DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$NAME", name);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		if (Validator.validateString(value)) {
			QUERY = QUERY.replace("$MINISTRY", value);
		} else {
			QUERY = QUERY.replace("'$MINISTRY'", "null");
		}
		return QUERY;
	}

	public static String updateME_lessonPreparation(String name, String actionDate, String value) {

		String QUERY = "update minister_evaluation set lesson_preparation = $LESSON_PREPARATION where id = (select id from member where name = '$NAME' and type = (select id from member_type where des = 'minister')) and action_date = to_date('$ACTION_DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$NAME", name);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		if (Validator.validateString(value)) {
			QUERY = QUERY.replace("$LESSON_PREPARATION", value);
		} else {
			QUERY = QUERY.replace("'$LESSON_PREPARATION'", "null");
		}
		return QUERY;
	}

	public static String updateME_illustrationTool(String name, String actionDate, String value) {

		String QUERY = "update minister_evaluation set illustration_tool = $ILLUSTRATION_TOOL where id = (select id from member where name = '$NAME' and type = (select id from member_type where des = 'minister')) and action_date = to_date('$ACTION_DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$NAME", name);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		if (Validator.validateString(value)) {
			QUERY = QUERY.replace("$ILLUSTRATION_TOOL", value);
		} else {
			QUERY = QUERY.replace("'$ILLUSTRATION_TOOL'", "null");
		}
		return QUERY;
	}

	public static String insertME(String name, String actionDate) {
		String QUERY = "insert into minister_evaluation (id, action_date, mass, ministers_meeting, family_meeting, ministry, lesson_preparation, illustration_tool, attendants_count, followup_count, attendants_followup_count) values ((select id from member where name = '$NAME' and type = (select id from member_type where des = 'minister')), to_date('$ACTION_DATE', 'dd-mm-yyyy'), 0, 0, 0, 0, 0, 0, 0, 0, 0)";

		QUERY = QUERY.replace("$NAME", name);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String insertMEByMinisterID(String ID, String actionDate) {
		String QUERY = "insert into minister_evaluation (id, action_date, mass, ministers_meeting, family_meeting, ministry, lesson_preparation, illustration_tool, attendants_count, followup_count, attendants_followup_count) values ('$ID', to_date('$ACTION_DATE', 'dd-mm-yyyy'), 0, 0, 0, 0, 0, 0, 0, 0, 0)";

		QUERY = QUERY.replace("$ID", ID);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String selectMinisterEvaluation(String ministerName, String actionDate) {
		String QUERY = "select mass, ministers_meeting, family_meeting, ministry, lesson_preparation, illustration_tool from minister_evaluation where id = (select id from member where name = '$MINISTER_NAME' and type = (select id from member_type where des = 'minister')) and action_date = to_date('$ACTION_DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$MINISTER_NAME", ministerName);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String updateMEAttendantsCount(String childName, String actionDate, boolean increaseStatus) {

		// String QUERY =
		// "update minister_evaluation set attendants_count = (attendants_count $OPERAND 1) where id = (select a.minister_id from (select minister_id, child_id, active_from from minister_child_assigned where minister_child_assigned.inactive_date is null) a, (select child_id, max(active_from) as active_from from minister_child_assigned where active_from <= to_date ('$ACTION_DATE', 'dd-mm-yyyy') and minister_child_assigned.inactive_date is null group by child_id) b where a.child_id = b.child_id and a.active_from = b.active_from and a.child_id = (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child'))) and action_date = to_date ('$ACTION_DATE', 'dd-mm-yyyy')";

		String QUERY = "update minister_evaluation set attendants_count = (attendants_count $OPERAND 1) where id = (SELECT distinct a.minister_id FROM (SELECT minister_id, child_id, active_from FROM minister_child_assigned WHERE inactive_date >= TO_DATE ('$ACTION_DATE', 'dd-mm-yyyy') OR inactive_date IS NULL) a, ( SELECT child_id, MAX (active_from) AS active_from FROM minister_child_assigned WHERE active_from <= TO_DATE ('$ACTION_DATE', 'dd-mm-yyyy') GROUP BY child_id) b WHERE a.child_id = b.child_id AND a.active_from = b.active_from AND a.child_id = (SELECT id FROM MEMBER WHERE name = '$CHILD_NAME' AND TYPE = (SELECT id FROM member_type WHERE des = 'child'))) and action_date = to_date ('$ACTION_DATE', 'dd-mm-yyyy')";

		if (increaseStatus) {
			QUERY = QUERY.replace("$OPERAND", "+");
		} else {
			QUERY = QUERY.replace("$OPERAND", "-");
		}

		QUERY = QUERY.replace("$CHILD_NAME", childName);

		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String updateMEFollowupCount(String childName, String actionDate, boolean increaseStatus) {

		// String QUERY =
		// "update minister_evaluation set followup_count = (followup_count $OPERAND 1) where id = (select a.minister_id from (select minister_id, child_id, active_from from minister_child_assigned where minister_child_assigned.inactive_date is null) a, (select child_id, max(active_from) as active_from from minister_child_assigned where active_from <= to_date ('$ACTION_DATE', 'dd-mm-yyyy') and minister_child_assigned.inactive_date is null group by child_id) b where a.child_id = b.child_id and a.active_from = b.active_from and a.child_id = (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child'))) and action_date = to_date ('$ACTION_DATE', 'dd-mm-yyyy')";

		String QUERY = "update minister_evaluation set followup_count = (followup_count $OPERAND 1) where id = (SELECT distinct a.minister_id FROM (SELECT minister_id, child_id, active_from FROM minister_child_assigned WHERE inactive_date >= TO_DATE ('$ACTION_DATE', 'dd-mm-yyyy') OR inactive_date IS NULL) a, ( SELECT child_id, MAX (active_from) AS active_from FROM minister_child_assigned WHERE active_from <= TO_DATE ('$ACTION_DATE', 'dd-mm-yyyy') GROUP BY child_id) b WHERE a.child_id = b.child_id AND a.active_from = b.active_from AND a.child_id = (SELECT id FROM MEMBER WHERE name = '$CHILD_NAME' AND TYPE = (SELECT id FROM member_type WHERE des = 'child'))) and action_date = to_date ('$ACTION_DATE', 'dd-mm-yyyy')";

		if (increaseStatus) {
			QUERY = QUERY.replace("$OPERAND", "+");
		} else {
			QUERY = QUERY.replace("$OPERAND", "-");
		}

		QUERY = QUERY.replace("$CHILD_NAME", childName);

		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String updateMEAttendantsFollowupCount(String childName, String actionDate,
			boolean increaseStatus) {

		// String QUERY =
		// "update minister_evaluation set attendants_followup_count = (attendants_followup_count $OPERAND 1) where id = (select a.minister_id from (select minister_id, child_id, active_from from minister_child_assigned where minister_child_assigned.inactive_date is null) a, (select child_id, max(active_from) as active_from from minister_child_assigned where active_from <= to_date ('$ACTION_DATE', 'dd-mm-yyyy') and minister_child_assigned.inactive_date is null group by child_id) b where a.child_id = b.child_id and a.active_from = b.active_from and a.child_id = (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child'))) and action_date = to_date ('$ACTION_DATE', 'dd-mm-yyyy')";

		String QUERY = "update minister_evaluation set attendants_followup_count = (attendants_followup_count $OPERAND 1) where id = (SELECT distinct a.minister_id FROM (SELECT minister_id, child_id, active_from FROM minister_child_assigned WHERE inactive_date >= TO_DATE ('$ACTION_DATE', 'dd-mm-yyyy') OR inactive_date IS NULL) a, ( SELECT child_id, MAX (active_from) AS active_from FROM minister_child_assigned WHERE active_from <= TO_DATE ('$ACTION_DATE', 'dd-mm-yyyy') GROUP BY child_id) b WHERE a.child_id = b.child_id AND a.active_from = b.active_from AND a.child_id = (SELECT id FROM MEMBER WHERE name = '$CHILD_NAME' AND TYPE = (SELECT id FROM member_type WHERE des = 'child'))) and action_date = to_date ('$ACTION_DATE', 'dd-mm-yyyy')";

		if (increaseStatus) {
			QUERY = QUERY.replace("$OPERAND", "+");
		} else {
			QUERY = QUERY.replace("$OPERAND", "-");
		}

		QUERY = QUERY.replace("$CHILD_NAME", childName);

		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String insertMEByChildName(String childName, String actionDate) {
		// String QUERY =
		// "insert into minister_evaluation (id, action_date, mass, ministers_meeting, family_meeting, ministry, lesson_preparation, illustration_tool, attendants_count, followup_count, attendants_followup_count) values ((select a.minister_id from (select minister_id, child_id, active_from from minister_child_assigned where minister_child_assigned.inactive_date is null) a, (select child_id, max(active_from) as active_from from minister_child_assigned where active_from <= to_date ('$ACTION_DATE', 'dd-mm-yyyy') and minister_child_assigned.inactive_date is null group by child_id) b where a.child_id = b.child_id and a.active_from = b.active_from and a.child_id = (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child'))), to_date('$ACTION_DATE', 'dd-mm-yyyy'), 0, 0, 0, 0, 0, 0, 0, 0, 0)";

		String QUERY = "insert into minister_evaluation (id, action_date, mass, ministers_meeting, family_meeting, ministry, lesson_preparation, illustration_tool, attendants_count, followup_count, attendants_followup_count) values ((SELECT distinct a.minister_id FROM (SELECT minister_id, child_id, active_from FROM minister_child_assigned WHERE inactive_date >= TO_DATE ('$ACTION_DATE', 'dd-mm-yyyy') OR inactive_date IS NULL) a, ( SELECT child_id, MAX (active_from) AS active_from FROM minister_child_assigned WHERE active_from <= TO_DATE ('$ACTION_DATE', 'dd-mm-yyyy') GROUP BY child_id) b WHERE a.child_id = b.child_id AND a.active_from = b.active_from AND a.child_id = (SELECT id FROM MEMBER WHERE name = '$CHILD_NAME' AND TYPE = (SELECT id FROM member_type WHERE des = 'child'))), to_date('$ACTION_DATE', 'dd-mm-yyyy'), 0, 0, 0, 0, 0, 0, 0, 0, 0)";

		QUERY = QUERY.replace("$CHILD_NAME", childName);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String deleteMinisterFather(String ministerId) {
		String QUERY = "update minister set father_id = null where father_id = $ID";

		if (Validator.validateString(ministerId)) {
			QUERY = QUERY.replace("$ID", ministerId);
		}

		return QUERY;
	}

	public static String deleteChildFather(String childId) {
		String QUERY = "update child set father_id = null where father_id = $ID";

		QUERY = QUERY.replace("$ID", childId);

		return QUERY;
	}

	public static String deleteChildAllMinistryAttendace(String id) {
		String QUERY = "delete child_ministry_attendance where id = $ID";

		QUERY = QUERY.replace("$ID", id);

		return QUERY;
	}

	public static String deleteChildAllMassAttendace(String id) {
		String QUERY = "delete child_mass_attendance where id = $ID";

		QUERY = QUERY.replace("$ID", id);

		return QUERY;
	}

	public static String deleteChildAllFollowup(String id) {
		String QUERY = "delete child_followup where id = $ID";

		QUERY = QUERY.replace("$ID", id);

		return QUERY;
	}

	public static String deleteAllMinisterEvaluationByMinisterId(String id) {
		String QUERY = "delete minister_evaluation where id = $ID";

		QUERY = QUERY.replace("$ID", id);

		return QUERY;
	}

	public static String selectMinisterEvaluationReport(String ministerId, String dateFrom, String dateTo) {
		String QUERY = "select* from "
				+ "(select round(((count(1) / (((to_date('$DATE_TO', 'dd-mm-yyyy') - to_date('$DATE_FROM', 'dd-mm-yyyy')) / 7) + 1)) * 100), 2) as ministers_meeting from MINISTER_EVALUATION where id = $ID and action_date >= to_date('$DATE_FROM', 'dd-mm-yyyy') and action_date <= to_date('$DATE_TO', 'dd-mm-yyyy') and ministers_meeting = 1) a, "
				+ "(select round(((count(1) / (((to_date('$DATE_TO', 'dd-mm-yyyy') - to_date('$DATE_FROM', 'dd-mm-yyyy')) / 7) + 1)) * 100), 2) as mass from MINISTER_EVALUATION where id = $ID and action_date >= to_date('$DATE_FROM', 'dd-mm-yyyy') and action_date <= to_date('$DATE_TO', 'dd-mm-yyyy') and mass = 1) b, "
				+ "(select round(((count(1) / (((to_date('$DATE_TO', 'dd-mm-yyyy') - to_date('$DATE_FROM', 'dd-mm-yyyy')) / 7) + 1)) * 100), 2) as ministry_attendance from MINISTER_EVALUATION where id = $ID and action_date >= to_date('$DATE_FROM', 'dd-mm-yyyy') and action_date <= to_date('$DATE_TO', 'dd-mm-yyyy') and ministry = 1) c, "
				+ "(select round(((count(1) / (((to_date('$DATE_TO', 'dd-mm-yyyy') - to_date('$DATE_FROM', 'dd-mm-yyyy')) / 7) + 1)) * 100), 2) as lesson_preparation from MINISTER_EVALUATION where id = $ID and action_date >= to_date('$DATE_FROM', 'dd-mm-yyyy') and action_date <= to_date('$DATE_TO', 'dd-mm-yyyy') and lesson_preparation = 1) d, "
				+ "(select round(((count(1) / (((to_date('$DATE_TO', 'dd-mm-yyyy') - to_date('$DATE_FROM', 'dd-mm-yyyy')) / 7) + 1)) * 100), 2) as illustration_tool from MINISTER_EVALUATION where id = $ID and action_date >= to_date('$DATE_FROM', 'dd-mm-yyyy') and action_date <= to_date('$DATE_TO', 'dd-mm-yyyy') and illustration_tool = 1) e, "
				+ "(select round(((count(1) / (((to_date('$DATE_TO', 'dd-mm-yyyy') - to_date('$DATE_FROM', 'dd-mm-yyyy')) / 7) + 1)) * 100), 2) as family_meeting from MINISTER_EVALUATION where id = $ID and action_date >= to_date('$DATE_FROM', 'dd-mm-yyyy') and action_date <= to_date('$DATE_TO', 'dd-mm-yyyy') and family_meeting = 1) f";

		QUERY = QUERY.replace("$DATE_FROM", dateFrom);
		QUERY = QUERY.replace("$DATE_TO", dateTo);
		QUERY = QUERY.replace("$ID", ministerId);

		return QUERY;
	}

	public static String selectAttendantsCount(String ministerId, String date) {
		String QUERY = "select nvl(sum(attendants_count), 0) from MINISTER_EVALUATION where id = $ID and action_date = to_date('$DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$ID", ministerId);
		QUERY = QUERY.replace("$DATE", date);

		return QUERY;
	}

	public static String selectAttendantsFollowupCount(String ministerId, String date) {
		String QUERY = "select nvl(sum(attendants_followup_count), 0) from MINISTER_EVALUATION where id = $ID and action_date = to_date('$DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$ID", ministerId);
		QUERY = QUERY.replace("$DATE", date);

		return QUERY;
	}

	public static String selectFollowupCount(String ministerId, String date) {
		String QUERY = "select nvl(sum(followup_count), 0) from MINISTER_EVALUATION where id = $ID and action_date = to_date('$DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$ID", ministerId);
		QUERY = QUERY.replace("$DATE", date);

		return QUERY;
	}

	public static String selectChildrenOfMinisterCount(String ministerId, String date) {
		// String QUERY =
		// "select count(distinct child_id) from (select a.child_id from (select minister_id, child_id, active_from from minister_child_assigned where minister_child_assigned.inactive_date is null) a, (select child_id, max (active_from) as active_from from minister_child_assigned where active_from <= to_date ('$DATE', 'dd-mm-yyyy') and minister_child_assigned.inactive_date is null group by child_id) b where a.child_id = b.child_id and a.active_from = b.active_from and a.minister_id = $ID)";

		String QUERY = "SELECT COUNT (1) FROM MEMBER WHERE id IN (SELECT a.child_id FROM (SELECT minister_id, child_id, active_from FROM minister_child_assigned where inactive_date >= TO_DATE ('$DATE', 'dd-mm-yyyy') or inactive_date is null) a, ( SELECT child_id, MAX (active_from) AS active_from FROM minister_child_assigned WHERE active_from <= TO_DATE ('$DATE', 'dd-mm-yyyy') GROUP BY child_id) b WHERE a.child_id = b.child_id AND a.active_from = b.active_from AND a.minister_id = $ID)";

		QUERY = QUERY.replace("$ID", ministerId);
		QUERY = QUERY.replace("$DATE", date);

		return QUERY;
	}

	public static String selectMinisterOfChild(String childName) {
		String QUERY = "select id, name from member where id in (select id from minister where sub_class_id in (select sub_class_id from child where id in (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child'))))";

		QUERY = QUERY.replace("$CHILD_NAME", childName);

		return QUERY;
	}

	public static String reactivateMember(String id) {
		String QUERY = "update member set inactive_date = null where id = '$ID'";

		QUERY = QUERY.replace("$ID", id);

		return QUERY;
	}

	public static String selectSubClassMinister(String subClassName) {
		String QUERY = "select name from member where id = (select id from minister where sub_class_id = (select id from sub_class where name = '$SUB_CLASS_NAME'))";

		QUERY = QUERY.replace("$SUB_CLASS_NAME", subClassName);

		return QUERY;
	}

	public static String removeMinisterFromSubClass(String subClassName) {
		String QUERY = "update minister set sub_class_id = null where sub_class_id = (select id from sub_class where name = '$SUB_CLASS_NAME')";

		QUERY = QUERY.replace("$SUB_CLASS_NAME", subClassName);

		return QUERY;
	}

	public static String updateMinisterSubClass(String subClassName, String ministerName) {
		String QUERY = "update minister set sub_class_id = (select id from sub_class where name = '$SUB_CLASS_NAME') where id = (select id from member where name = '$MINISTER_NAME' and type = (select id from member_type where des = 'minister'))";

		QUERY = QUERY.replace("$SUB_CLASS_NAME", subClassName);
		QUERY = QUERY.replace("$MINISTER_NAME", ministerName);

		return QUERY;
	}

	public static String selectSubClassByMinister(String ministerName) {
		String QUERY = "select name from sub_class where id = (select sub_class_id from minister where id = (select id from member where name = '$MINISTER_NAME' and type = (select id from member_type where des = 'minister')))";

		QUERY = QUERY.replace("$MINISTER_NAME", ministerName);

		return QUERY;
	}

	public static String removeMinisterOfSubClass(String subClassName) {
		String QUERY = "update minister set sub_class_id = null where sub_class_id = (select id from sub_class where name = '$SUB_CLASS_NAME')";

		QUERY = QUERY.replace("$SUB_CLASS_NAME", subClassName);

		return QUERY;
	}

	public static String updateChildSubClass(String subClassName, String childName) {
		String QUERY = "update child set sub_class_id = (select id from sub_class where name = '$SUB_CLASS_NAME') where id = (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child'))";

		QUERY = QUERY.replace("$SUB_CLASS_NAME", subClassName);
		QUERY = QUERY.replace("$CHILD_NAME", childName);

		return QUERY;
	}

	public static String removeChildSubClass(String childName) {
		String QUERY = "update child set sub_class_id = null where id = (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child'))";

		QUERY = QUERY.replace("$CHILD_NAME", childName);

		return QUERY;
	}

	public static String removeChildrenSubclassBySubclassName(String subclassName) {
		String QUERY = "update child set sub_class_id = null where sub_class_id = (select id from sub_class where name = '$SUBCLASS_NAME')";

		QUERY = QUERY.replace("$SUBCLASS_NAME", subclassName);

		return QUERY;
	}

	public static String updateSubClassMinister(String subClassName, String ministerName) {
		String QUERY = "update minister set sub_class_id = (select id from sub_class where name = '$SUB_CLASS_NAME') where id = (select id from member where name = '$MINISTER_NAME' and type = (select id from member_type where des = 'minister'))";

		QUERY = QUERY.replace("$SUB_CLASS_NAME", subClassName);
		QUERY = QUERY.replace("$MINISTER_NAME", ministerName);

		return QUERY;
	}

	public static String updateMinisterEvluationForChildrenAttendanceForMinistry(String ministerID,
			String actionDate) {
		String QUERY = "update minister_evaluation set attendants_count = ( select nvl(count(1), 0) as attendants_count from( select (select name from member where id = d.id) as minister_name, d.id as child_id from child_ministry_attendance a, child b, sub_class c, minister d where action_date = to_date('$ACTION_DATE', 'dd-mm-yyyy') and a.id = b.id and d.id = '$MINISTER_ID' and b.sub_class_id = c.id and d.sub_class_id = c.id)) where id = '$MINISTER_ID' and action_date = to_date('$ACTION_DATE', 'dd-mm-yyyy')";

		QUERY = QUERY.replace("$MINISTER_ID", ministerID);
		QUERY = QUERY.replace("$ACTION_DATE", actionDate);

		return QUERY;
	}

	public static String selectMinistryHierarchy() {
		String QUERY = "select section.name as section_name, family.name as family_name, year.name as year_name, class.name as class_name, sub_class.name as subclass_name, minister.id as minister_id, (select name from member where member.id = minister.id) as minister_name, child.id as child_id, (select name from member where member.id = child.id) as child_name from section, family, year, class, sub_class, minister, child where section.id = family.section_id and family.id = year.family_id and year.id = class.year_id and class.id = sub_class.class_id and sub_class.id = child.sub_class_id and sub_class.id = minister.sub_class_id and child.id not in (select id from member where inactive_date is not null) and minister.id not in (select id from member where inactive_date is not null)";

		return QUERY;
	}

	public static String selectAllMinistersNamesAndIds() {
		String QUERY = "select distinct minister.id as minister_id, (select name from member where member.id = minister.id) as minister_name from section, family, year, class, sub_class, minister, child where section.id = family.section_id and family.id = year.family_id and year.id = class.year_id and class.id = sub_class.class_id and sub_class.id = child.sub_class_id and sub_class.id = minister.sub_class_id and child.id not in (select id from member where inactive_date is not null) and minister.id not in (select id from member where inactive_date is not null)";

		return QUERY;
	}

	public static String selectChildPositionInMinistry(String childName) {
		// String QUERY =
		// "select section.name || ' >> ' || family.name || ' >> ' || year.name || ' >> ' || class.name || ' >> ' || sub_class.name from section, family, year, class, sub_class, minister, child where section.id = family.section_id and family.id = year.family_id and year.id = class.year_id and class.id = sub_class.class_id and sub_class.id = child.sub_class_id and sub_class.id = minister.sub_class_id and child.id not in (select id from member where inactive_date is not null) and minister.id not in (select id from member where inactive_date is not null) and child.id in (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child'))";

		String QUERY = "select section.name || ' >> ' || family.name || ' >> ' || year.name || ' >> ' || class.name || ' >> ' || sub_class.name from section, family, year, class, sub_class, child where section.id = family.section_id and family.id = year.family_id and year.id = class.year_id and class.id = sub_class.class_id and sub_class.id = child.sub_class_id and child.id not in (select id from member where inactive_date is not null) and child.id in (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child'))";

		QUERY = QUERY.replace("$CHILD_NAME", childName);

		return QUERY;
	}

	public static String selectChildSubclass(String childName) {
		String QUERY = "select sub_class.name from sub_class, child where sub_class.id = child.sub_class_id and child.id in (select id from member where name = '$CHILD_NAME' and type = (select id from member_type where des = 'child'))";

		QUERY = QUERY.replace("$CHILD_NAME", childName);

		return QUERY;
	}

	public static String selectSubClassesExcludingSomeSubclass(String subclassName) {
		String QUERY = "select name from sub_class where name <> '$SUB_CLASS_NAME'";

		QUERY = QUERY.replace("$SUB_CLASS_NAME", subclassName);

		return QUERY;
	}

	public static String deleteSubclassMinister(String subclassName) {
		String QUERY = "update minister set sub_class_id = null where sub_class_id = (select id from sub_class where name = '$SUBCLASS_NAME')";

		QUERY = QUERY.replace("$SUBCLASS_NAME", subclassName);

		return QUERY;
	}

}