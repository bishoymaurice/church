package church.ministry.control.log;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import church.ministry.model.file.FileAccess;

public class Logger implements Serializable {

	private static final long serialVersionUID = -852286376672727218L;

	private static String INFO_LOG_FILE_NAME;
	private static String ERROR_LOG_FILE_NAME;

	public static void init() {
		String dateHour = new SimpleDateFormat("HH_dd_MM_yyyy").format(new Date());
		String logFolderName = "ministry_logs";
		String logFileExt = ".log";

		FileAccess.createFolder(logFolderName);

		INFO_LOG_FILE_NAME = logFolderName + "/info_" + dateHour + logFileExt;
		ERROR_LOG_FILE_NAME = logFolderName + "/error_" + dateHour + logFileExt;
	}

	public static void info(String msg) {
		msg = "{" + Calendar.getInstance().getTime().toString() + " - " + msg + "}\r\n";
//		System.out.println(msg);
		FileAccess.appendToFile(INFO_LOG_FILE_NAME, msg);
	}

	public static void error(String msg) {
		msg = "{" + Calendar.getInstance().getTime().toString() + " - " + msg + "}\r\n";
		System.err.println(msg);
		FileAccess.appendToFile(ERROR_LOG_FILE_NAME, msg);
	}

	public static void exception(Exception e) {
		StringBuilder stringBuilder = new StringBuilder();
		StackTraceElement[] stackTraceElement = e.getStackTrace();

		stringBuilder.append("{Exception: ");
		stringBuilder.append(e.toString());
		stringBuilder.append("}\r\n");

		stringBuilder.append("{StackTrace: ");
		for (int i = 0; i < stackTraceElement.length; i++) {
			stringBuilder.append(stackTraceElement[i].toString());
			stringBuilder.append("\r\n");
		}
		stringBuilder.append("}\r\n");

		stringBuilder.append("{ExceptionString: ");
		stringBuilder.append(e.toString());
		stringBuilder.append("}\r\n");

		stringBuilder.append("{Error Message: ");
		stringBuilder.append(e.getMessage());
		stringBuilder.append("}\r\n");

		Logger.error(stringBuilder.toString());
	}

}