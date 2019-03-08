package com.database.backup.program;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author JBWB
 */
public class Program {

	public static void main(String[] args) throws InterruptedException {

		Logger.init();

		while (true) {

			String dumpDir = "E:/dump/";

			String dumpFileName = start();

			String backupFileName = dumpDir + dumpFileName;

			if (dumpFileName != null) {
				File file = new File(backupFileName);
				if (file.exists()) {
					Logger.info("Backup file was created successfully, file name is: "
							+ backupFileName);
					break;
				}
			}

			Thread.sleep(60000);
		}
	}

	public static String start() {
		try {

			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy_MM_dd_HH_mm_ss_SSS");
			Date now = new Date();
			String nowStr = sdf.format(now);

			String fileName = "ministryr2_" + nowStr + ".dmp";
			String logFileName = "ministryr2_" + nowStr + ".log";

			String command = "cmd /c expdp ministryr2/ministryr2p@xe schemas=ministryr2 directory=dumpdir dumpfile="
					+ fileName + " logfile=" + logFileName;

			Process process = Runtime.getRuntime().exec(command);

			InputStream out = process.getInputStream();
			InputStream err = process.getErrorStream();

			new Thread(new Pipe(out, System.out)).start();
			new Thread(new Pipe(err, System.err)).start();

			process.waitFor();

			return fileName;
		} catch (Exception ex) {
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
