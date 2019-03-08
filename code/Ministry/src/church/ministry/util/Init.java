package church.ministry.util;

import church.ministry.model.file.FileAccess;

public class Init {
	public static void initiate() {
		FileAccess.createFolder("downloads");
	}
}
