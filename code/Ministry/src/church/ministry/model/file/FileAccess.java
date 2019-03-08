package church.ministry.model.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileAccess implements Serializable {

	private static final long serialVersionUID = -6398970685979674566L;

	public static int appendToFile(String fileName, String content) {
		try {
			try (FileWriter fileWriter = new FileWriter(fileName, true)) {
				fileWriter.append(content);
				fileWriter.flush();
			}
			return 0;
		} catch (Exception ex) {
			System.err.println(ex + "\n" + ex.getMessage() + "\nSystem failed to write below\n***\n"
					+ content + "\non file name: " + fileName + "\n***");
			Logger.getLogger(FileAccess.class.getName()).log(Level.SEVERE, null, ex);
			return -1;
		}
	}

	public static int createFile(String fileName, String content) {
		try {
			try (PrintWriter printWrite = new PrintWriter(fileName, "UTF-8")) {
				printWrite.print(content);
				printWrite.flush();
			}
			return 0;
		} catch (Exception ex) {
			System.err.println(ex + "\n" + ex.getMessage() + "\nSystem failed to write below\n***\n"
					+ content + "\non file name: " + fileName + "\n***");
			Logger.getLogger(FileAccess.class.getName()).log(Level.SEVERE, null, ex);
			return -1;
		}
	}

	public static int createFileDefaultEncoding(String fileName, String content) {
		try {
			try (PrintWriter printWrite = new PrintWriter(fileName)) {
				printWrite.print(content);
				printWrite.flush();
			}
			return 0;
		} catch (Exception ex) {
			System.err.println(ex + "\n" + ex.getMessage() + "\nSystem failed to write below\n***\n"
					+ content + "\non file name: " + fileName + "\n***");
			Logger.getLogger(FileAccess.class.getName()).log(Level.SEVERE, null, ex);
			return -1;
		}
	}

	public static int createFolder(String folderName) {
		try {
			File file = new File(folderName);
			if (!file.exists()) {
				if (file.mkdir()) {
					return 0;
				} else {
					return -1;
				}
			}
			return 0;
		} catch (Exception ex) {
			System.err.println(ex + "\n" + ex.getMessage()
					+ "\nSystem failed to create folder\n***\nfolder name: " + folderName + "\n***");
			Logger.getLogger(FileAccess.class.getName()).log(Level.SEVERE, null, ex);
			return -1;
		}
	}

	public static String[] readFileIntoArray(String fileName) {
		try {
			ArrayList<String> fileContentList = new ArrayList<>();
			FileInputStream file = new FileInputStream(fileName);
			@SuppressWarnings("resource")
			Scanner readFile = new Scanner(file);
			while (readFile.hasNextLine()) {
				fileContentList.add(readFile.nextLine());
			}
			String[] fileContentArray = new String[fileContentList.size()];
			for (int i = 0; i < fileContentArray.length; i++) {
				fileContentArray[i] = fileContentList.get(i);
			}
			return fileContentArray;
		} catch (Exception ex) {
			System.err.println(ex + "\n" + ex.getMessage() + "\nSystem failed to read file\n***\nfile name: "
					+ fileName + "\n***");
			Logger.getLogger(FileAccess.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}

	public static int deleteFile(String filePath) {
		try {
			File file = new File(filePath);
			if (file.delete()) {
				return 0;
			}
			return -1;
		} catch (Exception ex) {
			System.err.println(ex + "\n" + ex.getMessage()
					+ "\nSystem failed to delete file\n***\nfile name: " + filePath + "\n***");
			Logger.getLogger(FileAccess.class.getName()).log(Level.SEVERE, null, ex);
			return -1;
		}
	}
}
