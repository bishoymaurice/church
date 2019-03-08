package church.ministry.util;

public class Converter {

	public static String[] convertStringIntoArray(String str) {

		str = str.substring(1, str.length() - 1);

		String[] arr = str.split(",");

		for (int i = 0; i < arr.length; i++) {
			arr[i] = arr[i].trim();
		}

		return arr;
	}
}
