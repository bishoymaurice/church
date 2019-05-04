package church.ministry.att.api.control.builder;

public class OracleStatementBuilder {

	public String selectMinisterIdByChildName(String childName, String todayInString) {

		String childNameVar = "$CHILD_NAME";
		String actionDateVar = "$ACTION_DATE";

		String query = "SELECT DISTINCT a.minister_id FROM ( SELECT minister_id, child_id, active_from FROM minister_child_assigned WHERE inactive_date >= TO_DATE('"
				+ actionDateVar
				+ "', 'dd-mm-yyyy') OR inactive_date IS NULL ) a, ( SELECT child_id, MAX(active_from) AS active_from FROM minister_child_assigned WHERE active_from <= TO_DATE('"
				+ actionDateVar
				+ "', 'dd-mm-yyyy') GROUP BY child_id ) b WHERE a.child_id = b.child_id AND a.active_from = b.active_from AND a.child_id = ( SELECT id FROM member WHERE name = '"
				+ childNameVar + "' AND type = ( SELECT id FROM member_type WHERE des = 'child' ) )";

		query = query.replace(childNameVar, childName);
		query = query.replace(actionDateVar, todayInString);

		return query;
	}

	public String selectMinisterIdByChildId(String childId, String todayInString) {

		String childIdVar = "$CHILD_ID";
		String actionDateVar = "$ACTION_DATE";

		String query = "SELECT DISTINCT a.minister_id FROM ( SELECT minister_id, child_id, active_from FROM minister_child_assigned WHERE inactive_date >= TO_DATE('"
				+ actionDateVar
				+ "', 'dd-mm-yyyy') OR inactive_date IS NULL ) a, ( SELECT child_id, MAX(active_from) AS active_from FROM minister_child_assigned WHERE active_from <= TO_DATE('"
				+ actionDateVar
				+ "', 'dd-mm-yyyy') GROUP BY child_id ) b WHERE a.child_id = b.child_id AND a.active_from = b.active_from AND a.child_id = "
				+ childIdVar;

		query = query.replace(childIdVar, childId);
		query = query.replace(actionDateVar, todayInString);

		return query;
	}

}
