package com.umamusumelist.util;

import java.security.SecureRandom;
import java.util.stream.Collectors;

/**
 * ランダム文字列生成機
 *
 * @author Umamusumelist.com
 * @version 8.0
 */
public final class RandomString {

	/**
	 * 指定された桁数のランダム文字列を生成
	 *
	 * @param length
	 *            桁数
	 */
	public static String generate(final int length) {
		String charas = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		return new SecureRandom().ints(length, 0, charas.length()).mapToObj(charas::charAt).map(Object::toString).collect(Collectors.joining());
	}

}
