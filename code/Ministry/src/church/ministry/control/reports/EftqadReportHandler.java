package church.ministry.control.reports;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import church.ministry.control.converter.HtmlToPdf;
import church.ministry.control.log.Logger;
import church.ministry.model.file.FileAccess;

public class EftqadReportHandler implements Serializable {

	private static final long serialVersionUID = -211551416581059234L;

	/****************************************************************/

	private static String HTML_FILE_HEAD = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">"
			+ "\r\n<html xmlns=\"http://www.w3.org/1999/xhtml\" DIR=\"RTL\" style=\"font-size:15px;\">"
			+ "\r\n<head>"
			+ "\r\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
			+ "\r\n<title> ﬁ—Ì— «› ﬁ«œ</title>"
			+ "\r\n</head>"
			+ "\r\n<body>"
			+ "\r\n<table cellspacing=\"0\" cellpadding=\"3\" align=\"center\">"
			+ "\r\n<thead>"
			+ "\r\n<tr>"
			+ "\r\n<th width=\"530\" align=\"center\">„—ﬂ“ ¬›« „Ê”Ï  -     ﬁ—Ì— ≈› ﬁ«œ</th>"
			+ "\r\n<th width=\"530\" align=\"center\">«·›’·: $FAMILY_NAME</th>"
			+ "\r\n</tr>"
			+ "\r\n<tr>"
			+ "\r\n<th width=\"530\" align=\"center\">«· «—ÌŒ: $DATE</th>"
			+ "\r\n<th width=\"530\" align=\"center\">«”„ «·Œ«œ„: $MINISTER_NAME</th>"
			+ "\r\n</tr>"
			+ "\r\n</thead>" + "\r\n</table>";

	private static String HTML_FILE_TAIL = "\r\n</body>" + "\r\n</html>";

	/****************************************************************/

	private static String HTML_ATTENDANT_HEAD = "\r\n<table border = \"1px solid\" cellspacing=\"0\" cellpadding=\"3\">"
			+ "\r\n<h2 align=\"center\">«·Õ«÷—Ì‰</h2>"
			+ "\r\n<thead>"
			+ "\r\n<tr>"
			+ "\r\n<th>«Ã„«·Ì<BR/>«·€Ì«»</th>"
			+ "\r\n<th>«Ã„«·Ì<BR/>«·«› ﬁ«œ<BR/>»«· ·Ì›Ê‰</th>"
			+ "\r\n<th>«Ã„«·Ì<BR/>«·«› ﬁ«œ<BR/>»«·„‰“·</th>"
			+ "\r\n<th bgcolor=\"#D4D4E3\">€Ì«»<BR/>„  «·Ì<BR/>··ﬁœ«”</th>"
			+ "\r\n<th bgcolor=\"#D4D4E3\">Õ÷Ê—<BR/>ﬁœ«”<BR/>«·ÌÊ„</th>"
			+ "\r\n<th>«·⁄‰Ê«‰</th>"
			+ "\r\n<th bgcolor=\"#D4D4E3\">€Ì«»<BR/>„  «·Ì</th>"
			+ "\r\n<th bgcolor=\"#D4D4E3\">«› ﬁ«œ<BR/>„  «·Ì</th>"
			+ "\r\n<th>«· ·Ì›Ê‰</th>"
			+ "\r\n<th>«·«”„</th>"
			+ "\r\n<th>ÿ—Ìﬁ… «·≈› ﬁ«œ</th>"
			+ "\r\n<th style=\"width: 1008px; word-break:break-all;\">„·«ÕŸ« </th>"
			+ "\r\n</tr>"
			+ "\r\n</thead>" + "\r\n<tbody>";

	private static String HTML_ATTENDANT_ROW = "\r\n<tr>"
			+ "\r\n<td style=\"width: 70px; word-break:break-all; text-align:center;\">$TOTAL_ABSENCE</td>"
			+ "\r\n<td style=\"width: 70px; word-break:break-all; text-align:center;\">$TOTLA_FOLLOWUP_PHONE</td>"
			+ "\r\n<td style=\"width: 70px; word-break:break-all; text-align:center;\">$TOTLA_FOLLOWUP_HOME</td>"
			+ "\r\n<td bgcolor=\"#D4D4E3\" style=\"width: 70px; word-break:break-all; text-align:center;\">$CONSECUTIVE_MASS_ABSENCE</td>"
			+ "\r\n<td bgcolor=\"#D4D4E3\" style=\"width: 50px; word-break:break-all; text-align:center;\">$TODAY_MASS_ATTENDANCE</td>"
			+ "\r\n<td style=\"width: 1200px; word-break:break-all; font-size:12px;\">$ADDRESS</td>"
			+ "\r\n<td bgcolor=\"#D4D4E3\" style=\"width: 60px; word-break:break-all; text-align:center;\">$CONSECUTIVE_MINISTRY_ABSENCE</td>"
			+ "\r\n<td bgcolor=\"#D4D4E3\" style=\"width: 60px; word-break:break-all; text-align:center;\">$CONSECUTIVE_FOLLOWUP</td>"
			+ "\r\n<td style=\"width: 260px; word-break:break-all;\">$PHONE</td>"
			+ "\r\n<td style=\"width: 500px; word-break:break-all;\">$NAME</td>"
			+ "\r\n<td style=\"width: 50px; word-break:break-all; text-align:center;\"></td>"
			+ "\r\n<td style=\"width: 1008px; word-break:break-all;\"></td>" + "\r\n</tr>";

	private static String HTML_ATTENDANT_TAIL = "\r\n</tbody>" + "\r\n</table>";

	/****************************************************************/

	private static String HTML_ABSENT_HEAD = "\r\n<table border = \"1px solid\" cellspacing=\"0\" cellpadding=\"3\">"
			+ "\r\n<h2 align=\"center\">«·€«∆»Ì‰</h2>"
			+ "\r\n<thead>"
			+ "\r\n<tr>"
			+ "\r\n<th>«Ã„«·Ì<BR/>«·€Ì«»</th>"
			+ "\r\n<th>«Ã„«·Ì<BR/>«·«› ﬁ«œ<BR/>»«· ·Ì›Ê‰</th>"
			+ "\r\n<th>«Ã„«·Ì<BR/>«·«› ﬁ«œ<BR/>»«·„‰“·</th>"
			+ "\r\n<th bgcolor=\"#D4D4E3\">€Ì«»<BR/>„  «·Ì<BR/>··ﬁœ«”</th>"
			+ "\r\n<th bgcolor=\"#D4D4E3\">Õ÷Ê—<BR/>ﬁœ«”<BR/>«·ÌÊ„</th>"
			+ "\r\n<th>«·⁄‰Ê«‰</th>"
			+ "\r\n<th bgcolor=\"#D4D4E3\">€Ì«»<BR/>„  «·Ì</th>"
			+ "\r\n<th bgcolor=\"#D4D4E3\">«› ﬁ«œ<BR/>„  «·Ì</th>"
			+ "\r\n<th>«· ·Ì›Ê‰</th>"
			+ "\r\n<th>«·«”„</th>"
			+ "\r\n<th>ÿ—Ìﬁ… «·≈› ﬁ«œ</th>"
			+ "\r\n<th style=\"width: 1000px; word-break:break-all;\">«”»«»<BR/>«·€Ì«»</th>"
			+ "\r\n</tr>"
			+ "\r\n</thead>" + "\r\n<tbody>";

	private static String HTML_ABSENT_ROW = "\r\n<tr>"
			+ "\r\n<td style=\"width: 70px; word-break:break-all; text-align:center;\">$TOTAL_ABSENCE</td>"
			+ "\r\n<td style=\"width: 70px; word-break:break-all; text-align:center;\">$TOTLA_FOLLOWUP_PHONE</td>"
			+ "\r\n<td style=\"width: 70px; word-break:break-all; text-align:center;\">$TOTLA_FOLLOWUP_HOME</td>"
			+ "\r\n<td bgcolor=\"#D4D4E3\" style=\"width: 70px; word-break:break-all; text-align:center;\">$CONSECUTIVE_MASS_ABSENCE</td>"
			+ "\r\n<td bgcolor=\"#D4D4E3\" style=\"width: 50px; word-break:break-all; text-align:center;\">$TODAY_MASS_ATTENDANCE</td>"
			+ "\r\n<td style=\"width: 1200px; word-break:break-all; font-size:12px;\">$ADDRESS</td>"
			+ "\r\n<td bgcolor=\"#D4D4E3\" style=\"width: 60px; word-break:break-all; text-align:center;\">$CONSECUTIVE_MINISTRY_ABSENCE</td>"
			+ "\r\n<td bgcolor=\"#D4D4E3\" style=\"width: 60px; word-break:break-all; text-align:center;\">$CONSECUTIVE_FOLLOWUP</td>"
			+ "\r\n<td style=\"width: 260px; word-break:break-all;\">$PHONE</td>"
			+ "\r\n<td style=\"width: 500px; word-break:break-all;\">$NAME</td>"
			+ "\r\n<td style=\"width: 50px; word-break:break-all; text-align:center;\"></td>"
			+ "\r\n<td style=\"width: 1000px; word-break:break-all;\"></td>" + "\r\n</tr>";

	private static String HTML_ABSENT_TAIL = "\r\n</tbody>" + "\r\n</table>";

	/****************************************************************/

	public static String savePDF(ArrayList<HashMap<String, String>> attendants,
			ArrayList<HashMap<String, String>> absents, String date, String className, String ministerName) {
		try {
			String searchReportFolder = System.getProperty("user.dir") + "/ministry_eftqad_reports/";
			SimpleDateFormat dateFomatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");

			FileAccess.createFolder(searchReportFolder);

			String htmlReportFileName = searchReportFolder + "eftqad_report_"
					+ dateFomatter.format(new Date()) + ".html";

			String htmlReportContent = generateHTMLReport(attendants, absents, date, className, ministerName);

			FileAccess.createFile(htmlReportFileName, htmlReportContent);

			String pdfReportFileName = HtmlToPdf.convert(htmlReportFileName, "windows");

			FileAccess.deleteFile(htmlReportFileName);

			return pdfReportFileName;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		}
	}

	private static String generateHTMLReport(ArrayList<HashMap<String, String>> attendants,
			ArrayList<HashMap<String, String>> absents, String date, String className, String ministerName) {
		try {

			StringBuilder htmlBuilder = new StringBuilder();

			String htmlFileHead = HTML_FILE_HEAD;
			htmlFileHead = htmlFileHead.replace("$FAMILY_NAME", className);
			htmlFileHead = htmlFileHead.replace("$MINISTER_NAME", ministerName);
			htmlFileHead = htmlFileHead.replace("$DATE", date);

			htmlBuilder.append(htmlFileHead);

			if (absents.size() > 0) {
				htmlBuilder.append(HTML_ABSENT_HEAD);
				String absentRow;

				for (int i = 0; i < absents.size(); i++) {
					absentRow = HTML_ABSENT_ROW;

					absentRow = absentRow.replace("$NAME", absents.get(i).get("name"));
					absentRow = absentRow.replace("$PHONE", absents.get(i).get("phone"));
					absentRow = absentRow.replace("$ADDRESS", absents.get(i).get("address"));
					absentRow = absentRow.replace("$TOTAL_ABSENCE", absents.get(i).get("total_absence"));
					absentRow = absentRow.replace("$TOTLA_FOLLOWUP_PHONE",
							absents.get(i).get("total_followup_phone"));
					absentRow = absentRow.replace("$TOTLA_FOLLOWUP_HOME",
							absents.get(i).get("total_followup_home"));
					absentRow = absentRow.replace("$CONSECUTIVE_MASS_ABSENCE",
							absents.get(i).get("consecutive_mass_absence"));
					absentRow = absentRow.replace("$TODAY_MASS_ATTENDANCE",
							absents.get(i).get("today_mass_attendance"));
					absentRow = absentRow.replace("$CONSECUTIVE_MINISTRY_ABSENCE",
							absents.get(i).get("consecutive_ministry_absence"));
					absentRow = absentRow.replace("$CONSECUTIVE_FOLLOWUP",
							absents.get(i).get("consecutive_followup"));

					htmlBuilder.append(absentRow);
				}
				htmlBuilder.append(HTML_ABSENT_TAIL);
			}

			/****************************************************************/

			if (attendants.size() > 0) {
				htmlBuilder.append(HTML_ATTENDANT_HEAD);
				String attendantRow;

				for (int i = 0; i < attendants.size(); i++) {
					attendantRow = HTML_ATTENDANT_ROW;

					attendantRow = attendantRow.replace("$NAME", attendants.get(i).get("name"));
					attendantRow = attendantRow.replace("$PHONE", attendants.get(i).get("phone"));
					attendantRow = attendantRow.replace("$ADDRESS", attendants.get(i).get("address"));
					attendantRow = attendantRow.replace("$TOTAL_ABSENCE",
							attendants.get(i).get("total_absence"));
					attendantRow = attendantRow.replace("$TOTLA_FOLLOWUP_PHONE",
							attendants.get(i).get("total_followup_phone"));
					attendantRow = attendantRow.replace("$TOTLA_FOLLOWUP_HOME",
							attendants.get(i).get("total_followup_home"));
					attendantRow = attendantRow.replace("$CONSECUTIVE_MASS_ABSENCE",
							attendants.get(i).get("consecutive_mass_absence"));
					attendantRow = attendantRow.replace("$TODAY_MASS_ATTENDANCE",
							attendants.get(i).get("today_mass_attendance"));
					attendantRow = attendantRow.replace("$CONSECUTIVE_MINISTRY_ABSENCE", attendants.get(i)
							.get("consecutive_ministry_absence"));
					attendantRow = attendantRow.replace("$CONSECUTIVE_FOLLOWUP",
							attendants.get(i).get("consecutive_followup"));

					htmlBuilder.append(attendantRow);
				}
				htmlBuilder.append(HTML_ATTENDANT_TAIL);
			}

			htmlBuilder.append(HTML_FILE_TAIL);

			return htmlBuilder.toString();
		} catch (Exception ex) {
			Logger.exception(ex);
			return null;
		}
	}
}