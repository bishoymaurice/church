package church.ministry.util;

import java.io.Serializable;

import church.ministry.control.log.Logger;

public class Validator implements Serializable {
	private static final long serialVersionUID = -8884785577504735109L;

	public static boolean validateString(String... strings) {
		try {
			for (int i = 0; i < strings.length; i++) {
				if (strings[i] == null || strings[i].length() == 0 || strings[i].equals("null")) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			Logger.exception(e);
			return false;
		}
	}

	public static boolean isNumeric(String in) {
		try {
			for (int i = 0; i < in.length(); i++) {
				if (!Character.isDigit(in.charAt(i))) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			Logger.exception(e);
			return false;
		}
	}
}
