package tr.org.mmo.asansor.util;

import java.util.Random;

public class Util {

	public static String randomAlphanumeric() {
		char[] alphNum = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
				.toCharArray();

		Random rnd = new Random();

		StringBuilder sb = new StringBuilder((100000 + rnd.nextInt(900000)));
		for (int i = 0; i < 8; i++)
			sb.append(alphNum[rnd.nextInt(alphNum.length)]);

		String str = sb.toString();
		return str;
	}

	public static String toUpperCase(String str) {
		if (str == null)
			return "";
		return str.replace('i', 'İ').toUpperCase();
	}

	public static String rPad(String str, int len) {
		while (str.length() < len) {
			str += " ";
		}
		return str;

	}
	public static String nullif(String str){
		if (str==null)
			return "";
		else
			return str.trim();
	}
}