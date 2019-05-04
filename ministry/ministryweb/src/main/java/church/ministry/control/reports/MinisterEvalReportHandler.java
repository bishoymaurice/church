package church.ministry.control.reports;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import church.ministry.control.converter.HtmlToPdf;
import church.ministry.control.log.Logger;
import church.ministry.model.file.FileAccess;

public class MinisterEvalReportHandler implements Serializable {

	private static final long serialVersionUID = 3812687561872790699L;

	public static String saveCSV(ArrayList<ArrayList<String>> evaluationResult, String dateFrom,
			String dateTo, String itemType, String itemValue) {
		try {
			String reportFolder = System.getProperty("user.dir") + "/ministry_evaluation_reports/";
			SimpleDateFormat dateFomatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");

			FileAccess.createFolder(reportFolder);

			String csvFileName = reportFolder + "evaluation_report_" + dateFomatter.format(new Date())
					+ ".csv";

			StringBuilder csvFileContent = new StringBuilder();

			String header0 = "تقييم الخدام" + "\r\n" + itemType + ": " + itemValue + "\r\n";
			String header1 = "من: " + dateFrom + "\r\n" + "إلى: " + dateTo + "\r\n";
			String header2 = "الاسم,اجتماع الخدام,قداس الخدمة,الخدمة,تحضير الدرس,وسيلة الايضاح,اجتماع الاسرة,حضور المخدومين,افتقاد الغائبين,افتقاد الحاضرين +,التقييم العام\r\n";
			csvFileContent.append(header0);
			csvFileContent.append(header1);
			csvFileContent.append(header2);

			for (int i = 0; i < evaluationResult.size(); i++) {
				for (int j = 0; j < evaluationResult.get(0).size(); j++) {
					csvFileContent.append(evaluationResult.get(i).get(j));
					csvFileContent.append(",");
				}
				csvFileContent.append("\r\n");
			}

			FileAccess.createFileDefaultEncoding(csvFileName, csvFileContent.toString());

			return csvFileName;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		}
	}

	public static String savePDF(ArrayList<ArrayList<String>> evaluation, String dateFrom, String dateTo,
			String itemType, String itemValue) {
		String searchReportFolder = System.getProperty("user.dir") + "/ministry_evaluation_reports/";
		SimpleDateFormat dateFomatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");

		FileAccess.createFolder(searchReportFolder);

		String htmlReportFileName = searchReportFolder + "evaluation_report_"
				+ dateFomatter.format(new Date()) + ".html";

		String htmlReportContent = generateHTMLReport(evaluation, dateFrom, dateTo, itemType, itemValue);

		FileAccess.createFile(htmlReportFileName, htmlReportContent);

		String pdfReportFileName = HtmlToPdf.convert(htmlReportFileName, "windows");

		FileAccess.deleteFile(htmlReportFileName);

		return pdfReportFileName;
	}

	public static String generateHTMLReport(ArrayList<ArrayList<String>> evaluation, String dateFrom,
			String dateTo, String itemType, String itemValue) {
		try {
			StringBuilder htmlFileContent = new StringBuilder();
			String head = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
					+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" DIR=\"RTL\" style=\"font-size:18px;\">\n"
					+ "<head>\n"
					+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n"
					+ "<title>تقييم الخدام</title>\n" + "</head>\n" + "<body>\n";
			if (itemValue == null || itemValue.equals("null")) {
				head += "<h2>تقييم الخدام - $itemType</h2>\n";
			} else {
				head += "<h2>تقييم الخدام - $itemType: $itemValue</h2>\n";
			}
			head += "<h2>من: $dateFrom  -  إلى: $dateTo</h2>\n"
					+ "<table border = \"1px solid\" cellspacing=\"0\" cellpadding=\"3\">\n"
					+ "<thead>\n"
					+ "<tr>\n"
					+ "<th style=\"width: 200px; word-break:break-word; text-align:center;\">الاسم</th>\n"
					+ "<th style=\"width: 120px; word-break:break-word; text-align:center;\">اجتماع<BR/>الخدام</th>\n"
					+ "<th style=\"width: 120px; word-break:break-word; text-align:center;\">قداس<BR/>الخدمة</th>\n"
					+ "<th style=\"width: 120px; word-break:break-word; text-align:center;\">الخدمة</th>\n"
					+ "<th style=\"width: 120px; word-break:break-word; text-align:center;\">تحضير<BR/>الدرس</th>\n"
					+ "<th style=\"width: 120px; word-break:break-word; text-align:center;\">وسيلة<BR/>الايضاح</th>\n"
					+ "<th style=\"width: 120px; word-break:break-word; text-align:center;\">اجتماع<BR/>الاسرة</th>\n"
					+ "<th style=\"width: 120px; word-break:break-word; text-align:center;\">حضور<BR/>المخدومين</th>\n"
					+ "<th style=\"width: 120px; word-break:break-word; text-align:center;\">افتقاد<BR/>الغائبين</th>\n"
					+ "<th style=\"width: 120px; word-break:break-word; text-align:center;\">افتقاد<BR/>الحاضرين +</th>\n"
					+ "<th style=\"width: 120px; word-break:break-word; text-align:center;\">التقييم<BR/>العام</th>\n"
					+ "</tr>\n" + "</thead>\n" + "<tbody>\n";

			head = head.replace("$itemType", itemType).replace("$itemValue", itemValue)
					.replace("$dateFrom", dateFrom).replace("$dateTo", dateTo);

			htmlFileContent.append(head);

			String row = "<tr>\n"
					+ "<td style=\"width: 200px; word-break:break-word;\">$name</td>\n"
					+ "<td style=\"width: 120px; word-break:break-word; text-align:center;\">$ministerMeeting</td>\n"
					+ "<td style=\"width: 120px; word-break:break-word; text-align:center;\">$ministryMass</td>\n"
					+ "<td style=\"width: 120px; word-break:break-word; text-align:center;\">$ministry</td>\n"
					+ "<td style=\"width: 120px; word-break:break-word; text-align:center;\">$lessonPreparation</td>\n"
					+ "<td style=\"width: 120px; word-break:break-word; text-align:center;\">$illustrationTool</td>\n"
					+ "<td style=\"width: 120px; word-break:break-word; text-align:center;\">$familyMeeting</td>\n"
					+ "<td style=\"width: 120px; word-break:break-word; text-align:center;\">$childrenAttendance</td>\n"
					+ "<td style=\"width: 120px; word-break:break-word; text-align:center;\">$absentsFollowup</td>\n"
					+ "<td style=\"width: 120px; word-break:break-word; text-align:center;\">$attendantsFollowup</td>\n"
					+ "<td style=\"width: 120px; word-break:break-word; text-align:center;\">$generalEvaluation</td>\n"
					+ "</tr>";

			String foot = "</tbody>\n" + "</table>\n" + "</body>\n" + "</html>\n";

			if (evaluation.size() > 1) {
				for (int i = 0; i < evaluation.size(); i++) {
					htmlFileContent.append(row.replace("$name", evaluation.get(i).get(0))
							.replace("$ministerMeeting", evaluation.get(i).get(1))
							.replace("$ministryMass", evaluation.get(i).get(2))
							.replace("$ministry", evaluation.get(i).get(3))
							.replace("$lessonPreparation", evaluation.get(i).get(4))
							.replace("$illustrationTool", evaluation.get(i).get(5))
							.replace("$familyMeeting", evaluation.get(i).get(6))
							.replace("$childrenAttendance", evaluation.get(i).get(7))
							.replace("$absentsFollowup", evaluation.get(i).get(8))
							.replace("$attendantsFollowup", evaluation.get(i).get(9))
							.replace("$generalEvaluation", evaluation.get(i).get(10)));
				}
			}
			htmlFileContent.append(foot);
			return htmlFileContent.toString();

		} catch (Exception e) {
			Logger.exception(e);
			return null;
		}
	}
}