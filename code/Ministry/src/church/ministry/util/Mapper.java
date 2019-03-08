package church.ministry.util;

import java.io.Serializable;

public class Mapper implements Serializable {

	private static final long serialVersionUID = -4173234312243788984L;

	public static boolean mapBinary(String in) {
		if (in.equals("1")) {
			return true;
		} else {
			return false;
		}
	}
}
