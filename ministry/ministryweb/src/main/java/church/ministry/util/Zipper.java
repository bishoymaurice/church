package church.ministry.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import church.ministry.control.log.Logger;

public class Zipper {
	public static String zip(List<File> fileList, boolean ifDeleteTargetFiles) throws IOException {
		try {
			SimpleDateFormat dateFomatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS");
			String zippedFileName = System.getProperty("user.dir") + "/downloads/downloads_"
					+ dateFomatter.format(new Date());

			File directoryToZip = new File(zippedFileName);

			writeZipFile(directoryToZip, fileList);

			if (ifDeleteTargetFiles) {
				for (File file : fileList) {
					file.delete();
				}
			}

			return directoryToZip.getAbsolutePath() + ".zip";

		} catch (Exception e) {
			Logger.exception(e);
			return null;
		}
	}

	// public static void getAllFiles(File dir, List<File> fileList) {
	// try {
	// File[] files = dir.listFiles();
	// for (File file : files) {
	// fileList.add(file);
	// if (file.isDirectory()) {
	// getAllFiles(file, fileList);
	// }
	// }
	// } catch (Exception e) {
	// Logger.exception(e);
	// }
	// }

	public static void writeZipFile(File directoryToZip, List<File> fileList) {
		try {
			FileOutputStream fos = new FileOutputStream(directoryToZip.getAbsolutePath() + ".zip");
			ZipOutputStream zos = new ZipOutputStream(fos);

			for (File file : fileList) {
				if (!file.isDirectory()) {
					addToZip(directoryToZip, file, zos);
				}
			}

			zos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			Logger.exception(e);
		} catch (IOException e) {
			Logger.exception(e);
		} catch (Exception e) {
			Logger.exception(e);
		}
	}

	public static void addToZip(File directoryToZip, File file, ZipOutputStream zos)
			throws FileNotFoundException, IOException {
		try {
			FileInputStream fis = new FileInputStream(file);

			String zipFilePath = file.getCanonicalPath().substring(
					directoryToZip.getCanonicalPath().length() + 1, file.getCanonicalPath().length());

			ZipEntry zipEntry = new ZipEntry(zipFilePath);
			zos.putNextEntry(zipEntry);

			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zos.write(bytes, 0, length);
			}

			zos.closeEntry();
			fis.close();

		} catch (Exception e) {
			Logger.exception(e);
		}
	}
}
