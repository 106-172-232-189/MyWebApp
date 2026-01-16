package com.umamusumelist.util;

/**
 * 数字の後に「st, nd, rd, th」のどれかを付与
 *
 * @author Umamusumelist.com
 * @version 5.5
 */
public class NumberSuffix {

	/**
	 * どの数字かによって「st, nd, rd, th」のどれかを付与
	 *
	 * @param number 対象の数字
	 * @return 数字の後に「st, nd, rd, th」のどれかを付与した文字列
	 */
	public static String addSuffix(int number) {
		String suffix = "";

		if (number % 100 >= 11 && number % 100 <= 13) {
			suffix = "th";
			return number + suffix;
		}

		switch (number % 10) {
		case 1:
			suffix = "st";
			break;

		case 2:
			suffix = "nd";
			break;

		case 3:
			suffix = "rd";
			break;

		default:
			suffix = "th";
			break;
		}

		return number + suffix;
	}

}
