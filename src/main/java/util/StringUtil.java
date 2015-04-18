package util;

public class StringUtil {
	public static boolean isDouble(String str) {
		return str.matches("^[1-9]\\d*\\.*\\d*$|^0\\.0*[1-9]\\d*$");
	}

	public static boolean isInteger(String str) {
		return str.matches("^[1-9]\\d*$");
	}

}
