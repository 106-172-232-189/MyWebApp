package com.umamusumelist.util;

/**
 * 全角カタカナを半角カタカナに変換
 *
 * @author https://www7a.biglobe.ne.jp/~java-master/samples/string/ZenkakuKatakanaToHankakuKatakana.html
 */
public final class KatakanaToHankaku {

	/** 全角カタカナ群 */
	private static final char[] ZENKAKU_KATAKANA = { 'ァ', 'ア', 'ィ', 'イ', 'ゥ',
		'ウ', 'ェ', 'エ', 'ォ', 'オ', 'カ', 'ガ', 'キ', 'ギ', 'ク', 'グ', 'ケ', 'ゲ',
		'コ', 'ゴ', 'サ', 'ザ', 'シ', 'ジ', 'ス', 'ズ', 'セ', 'ゼ', 'ソ', 'ゾ', 'タ',
		'ダ', 'チ', 'ヂ', 'ッ', 'ツ', 'ヅ', 'テ', 'デ', 'ト', 'ド', 'ナ', 'ニ', 'ヌ',
		'ネ', 'ノ', 'ハ', 'バ', 'パ', 'ヒ', 'ビ', 'ピ', 'フ', 'ブ', 'プ', 'ヘ', 'ベ',
		'ペ', 'ホ', 'ボ', 'ポ', 'マ', 'ミ', 'ム', 'メ', 'モ', 'ャ', 'ヤ', 'ュ', 'ユ',
		'ョ', 'ヨ', 'ラ', 'リ', 'ル', 'レ', 'ロ', 'ヮ', 'ワ', 'ヰ', 'ヱ', 'ヲ', 'ン',
		'ヴ', 'ヵ', 'ヶ' };

	/** 半角カタカナ群 */
	private static final String[] HANKAKU_KATAKANA = { "ｧ", "ｱ", "ｨ", "ｲ", "ｩ",
		"ｳ", "ｪ", "ｴ", "ｫ", "ｵ", "ｶ", "ｶﾞ", "ｷ", "ｷﾞ", "ｸ", "ｸﾞ", "ｹ",
		"ｹﾞ", "ｺ", "ｺﾞ", "ｻ", "ｻﾞ", "ｼ", "ｼﾞ", "ｽ", "ｽﾞ", "ｾ", "ｾﾞ", "ｿ",
		"ｿﾞ", "ﾀ", "ﾀﾞ", "ﾁ", "ﾁﾞ", "ｯ", "ﾂ", "ﾂﾞ", "ﾃ", "ﾃﾞ", "ﾄ", "ﾄﾞ",
		"ﾅ", "ﾆ", "ﾇ", "ﾈ", "ﾉ", "ﾊ", "ﾊﾞ", "ﾊﾟ", "ﾋ", "ﾋﾞ", "ﾋﾟ", "ﾌ",
		"ﾌﾞ", "ﾌﾟ", "ﾍ", "ﾍﾞ", "ﾍﾟ", "ﾎ", "ﾎﾞ", "ﾎﾟ", "ﾏ", "ﾐ", "ﾑ", "ﾒ",
		"ﾓ", "ｬ", "ﾔ", "ｭ", "ﾕ", "ｮ", "ﾖ", "ﾗ", "ﾘ", "ﾙ", "ﾚ", "ﾛ", "ﾜ",
		"ﾜ", "ｲ", "ｴ", "ｦ", "ﾝ", "ｳﾞ", "ｶ", "ｹ" };

	/** 全角カタカナ群の内の最初の文字 */
	private static final char ZENKAKU_KATAKANA_FIRST_CHAR = ZENKAKU_KATAKANA[0];

	/** 全角カタカナ群の内の最後の文字 */
	private static final char ZENKAKU_KATAKANA_LAST_CHAR = ZENKAKU_KATAKANA[ZENKAKU_KATAKANA.length - 1];

	/**
	 * 全角カタカナから半角カタカナへ変換
	 *
	 * @param c 変換前の文字
	 * @return 変換後の文字
	 */
	public static String katakanaToHankaku(char c) {
		if (c == 'ー') {
			return "ｰ";
		} else if (c >= ZENKAKU_KATAKANA_FIRST_CHAR && c <= ZENKAKU_KATAKANA_LAST_CHAR) {
			return HANKAKU_KATAKANA[c - ZENKAKU_KATAKANA_FIRST_CHAR];
		} else {
			return String.valueOf(c);
		}
	}

	/**
	 * 文字列中の全角カタカナを半角カタカナに変換
	 * @param s 変換前文字列
	 * @return 変換後文字列
	 */
	public static String katakanaToHankaku(String s) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < s.length(); i++) {
				char originalChar = s.charAt(i);
				String convertedChar = katakanaToHankaku(originalChar);
				sb.append(convertedChar);
			}
		return sb.toString();
	}

}
