package church.ministry.control.reports;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import church.ministry.control.converter.HtmlToPdf;
import church.ministry.control.log.Logger;
import church.ministry.model.file.FileAccess;

public class SearchReportHandler implements Serializable {

	private static final long serialVersionUID = 8537598386952314989L;

	public static String saveCSV(ArrayList<ArrayList<Object>> searchResult) {
		try {
			String searchReportFolder = System.getProperty("user.dir") + "/ministry_search_reports/";
			SimpleDateFormat dateFomatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");

			FileAccess.createFolder(searchReportFolder);

			String csvFileName = searchReportFolder + "search_report_" + dateFomatter.format(new Date())
					+ ".csv";

			StringBuilder csvFileContent = new StringBuilder();

			for (int i = 0; i < searchResult.size(); i++) {
				for (int j = 0; j < searchResult.get(0).size(); j++) {
					csvFileContent.append(searchResult.get(i).get(j));
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

	public static String savePDF(ArrayList<ArrayList<Object>> searchResult) {
		try {
			String searchReportFolder = System.getProperty("user.dir") + "/ministry_search_reports/";
			SimpleDateFormat dateFomatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");

			FileAccess.createFolder(searchReportFolder);

			String htmlReportFileName = searchReportFolder + "search_report_"
					+ dateFomatter.format(new Date()) + ".html";

			String htmlReportContent = generateHTMLReport(searchResult);

			FileAccess.createFile(htmlReportFileName, htmlReportContent);

			String pdfReportFileName = HtmlToPdf.convert(htmlReportFileName, "windows");

			FileAccess.deleteFile(htmlReportFileName);

			return pdfReportFileName;
		} catch (Exception e) {
			Logger.exception(e);
			return null;
		}
	}

	private static String generateHTMLReport(ArrayList<ArrayList<Object>> searchResult) {
		try {
			StringBuilder htmlFileContent = new StringBuilder();

			String header = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n"
					+ "<html xmlns=\"http://www.w3.org/1999/xhtml\" DIR=\"RTL\" style=\"font-size:18px;\">\r\n"
					+ "<head>\r\n"
					+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n"
					+ "<title>‰ «∆Ã «·»ÕÀ</title>\r\n"
					+ "</head>\r\n"
					+ "<body>\r\n"
					+ "<h3>‰ «∆Ã «·»ÕÀ</h3>\r\n"
					+ "<table border = \"1px solid\" cellspacing=\"0\" cellpadding=\"1\">\r\n"
					+ "<tbody>\r\n";
			String footer = "</tbody>\r\n" + "</table>\r\n" + "</body>\r\n" + "</html>\r\n";
			String rowHeader = "<tr>\r\n";
			String rowfooter = "</tr>\r\n";
			String cell = "<td>$VALUE</td>\r\n";

			htmlFileContent.append(header);

			for (ArrayList<Object> record : searchResult) {
				htmlFileContent.append(rowHeader);
				for (Object item : record) {
					htmlFileContent.append(cell.replace("$VALUE", item.toString()));
				}
				htmlFileContent.append(rowfooter);
			}

			htmlFileContent.append(footer);

			return htmlFileContent.toString();

		} catch (Exception ex) {
			Logger.exception(ex);
			return null;
		}
	}
}
