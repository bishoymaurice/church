package com.ministry.test;

import java.sql.Connection;
import java.util.ArrayList;

import church.ministry.control.log.Logger;
import church.ministry.control.org.OrgHandler;
import church.ministry.model.file.FileAccess;
import church.ministry.model.oracle.DatabaseConnectionManager;
import church.ministry.model.oracle.DatabaseExecutor;
import church.ministry.util.Init;

public class EvaluationIssue {

	public static void main(String[] args) throws InterruptedException {
		Logger.init();
		Init.initiate();

		DatabaseConnectionManager.connect();
		Connection conn = DatabaseConnectionManager.getConnection();

		String allInvalidChildrenQuery = "select name from member where type = (select id from member_type where des = 'child') and inactive_date is null";

		String allChildrenQuery = "select name from member where id in ( select (select id from member where id = child.id) from section, family, year, class, sub_class, minister, child where section.id = family.section_id and family.id = year.family_id and year.id = class.year_id and sub_class.class_id = class.id and sub_class.id = child.sub_class_id and sub_class.id = minister.sub_class_id ) and inactive_date is null";

		String attendantsQuery = "select name from member where id in ( select (select id from member where id = child.id) from section, family, year, class, sub_class, minister, child where section.id = family.section_id and family.id = year.family_id and year.id = class.year_id and sub_class.class_id = class.id and sub_class.id = child.sub_class_id and sub_class.id = minister.sub_class_id ) and inactive_date is null and id in (select id from child_ministry_attendance where action_date = to_date ('06-10-2017', 'dd-mm-yyyy'))";

		ArrayList<ArrayList<Object>> childrenResult = new DatabaseExecutor().executeQuery(conn,
				allInvalidChildrenQuery);

		ArrayList<String> children = new ArrayList<String>();

		for (int i = 0; i < childrenResult.size(); i++) {
			children.add(childrenResult.get(i).get(0).toString());
		}

		System.out.println(children.size());

		String currentValue = "2";

		for (String s : children) {
			OrgHandler.addRemoveChildMinistryAttendance(s, "06-10-2017", true, false);

			ArrayList<ArrayList<Object>> result = new DatabaseExecutor()
					.executeQuery(
							conn,
							"select attendants_count from MINISTER_EVALUATION where id = 2542 and action_date = to_date('06-10-2017', 'dd-mm-yyyy')");
			if (result.size() > 0) {
				if (!result.get(0).get(0).toString().equals(currentValue)) {
					FileAccess.appendToFile("output.txt", s + "\r\n");
					currentValue = result.get(0).get(0).toString();
				}
			}
		}

		DatabaseConnectionManager.releaseConnection(conn);
	}
}
