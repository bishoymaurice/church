package church.ministry.control.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;

import church.ministry.control.log.Logger;

/**
 * 
 * @author JBWB
 */
public class HtmlToPdf implements Serializable{

	private static final long serialVersionUID = 7567113403656262142L;

	public static String convert(String htmlFileAbsolutePath, String osType) {
		try {
			if (osType.equals("windows")) {

				String pdfOutputFileAbsolutePath = htmlFileAbsolutePath.substring(0,
						htmlFileAbsolutePath.lastIndexOf(".html"))
						+ ".pdf";

				String command = "cmd /c C:&&cd C:/Program Files/wkhtmltopdf&&wkhtmltopdf -O Landscape \""
						+ htmlFileAbsolutePath + "\" \"" + pdfOutputFileAbsolutePath + "\"";

				Process process = Runtime.getRuntime().exec(command);

				InputStream out = process.getInputStream();
				InputStream err = process.getErrorStream();

				new Thread(new Pipe(out, System.out)).start();
				new Thread(new Pipe(err, System.err)).start();

				process.waitFor();

				return pdfOutputFileAbsolutePath;

			} else if (osType.equals("linux")) {
				String pdfOutputFileAbsolutePath = htmlFileAbsolutePath.substring(0,
						htmlFileAbsolutePath.lastIndexOf(".html"))
						+ ".pdf";
				String command = "/usr/bin/wkhtmltopdf -O Landscape \"" + htmlFileAbsolutePath + "\" \""
						+ pdfOutputFileAbsolutePath + "\"";
				Process process = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", command });

				InputStream out = process.getInputStream();
				InputStream err = process.getErrorStream();

				new Thread(new Pipe(out, System.out)).start();
				new Thread(new Pipe(err, System.err)).start();

				process.waitFor();

				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				StringBuffer output = new StringBuffer();
				String line = "";
				while ((line = reader.readLine()) != null) {
					output.append(line + "\n");
				}
				return pdfOutputFileAbsolutePath;
			} else {
				return null;
			}
		} catch (IOException | InterruptedException ex) {
			Logger.exception(ex);
			return null;
		}
	}

	private static class Pipe implements Runnable {
		InputStream in;
		OutputStream out;

		Pipe(InputStream in, OutputStream out) {
			this.in = in;
			this.out = out;
		}

		public void run() {
			try {
				int c;
				while ((c = in.read()) != -1) {
					out.write(c);
				}
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}
}
