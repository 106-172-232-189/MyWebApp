package com.umamusumelist.bean;

/**
 * ウマ娘を取り扱うBean
 *
 * @author Umamusumelist.com
 * @version 5.1
 */
public final class UmamusumeBean {

	/** 図鑑番号 */
	private final int umadexNo;

	/** 名前 */
	private final String name;

	/** ウマ娘公式ポータルサイトにおける識別子 */
	private final String parameter;

	/** 勝負服番号 */
	private final int racingSuitNo;

	/**
	 * 新規インスタンス作成
	 *
	 * @param umadexNo 図鑑番号
	 * @param name 名前
	 * @param parameter ウマ娘公式ポータルサイトにおける識別子
	 * @param racingSuitNo 勝負服番号
	 * @return 同一の引数を用いる新たなBeanオブジェクトのインスタンス
	 * @exception NullPointerException 名前がnull
	 * @exception IllegalArgumentException 勝負服番号もしくは図鑑番号が0未満
	 */
	public static UmamusumeBean create(final int umadexNo, final String name, final String parameter, final int racingSuitNo) {
		if (name == null) {
			throw new NullPointerException("名前がnullです");
		}

		if (umadexNo < 0 || racingSuitNo < 0) {
			throw new IllegalArgumentException("勝負服番号もしくは図鑑番号が0未満です");
		}

		return new UmamusumeBean(umadexNo, name, parameter, racingSuitNo);
	}

	/**
	 * 新規インスタンス作成時のコンストラクター
	 *
	 * @param umadexNo 図鑑番号
	 * @param name 名前
	 * @param parameter ウマ娘公式ポータルサイトにおける識別子
	 * @param racingSuitNo 勝負服番号
	 */
	private UmamusumeBean(final int umadexNo, final String name, final String parameter, final int racingSuitNo) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.umadexNo = umadexNo;
		this.name = name;
		this.parameter = parameter;
		this.racingSuitNo = racingSuitNo;
	}

	/**
	 * 図鑑番号
	 *
	 * @return 図鑑番号
	 */
	public int umadexNo() {
		return umadexNo;
	}

	/**
	 * 名前
	 *
	 * @return 名前
	 */
	public String name() {
		return name;
	}

	/**
	 * ウマ娘公式ポータルサイトにおける識別子
	 *
	 * @return ウマ娘公式ポータルサイトにおける識別子
	 */
	public String parameter() {
		return parameter;
	}

	/**
	 * 勝負服番号
	 *
	 * @return 勝負服番号
	 */
	public int racingSuitNo() {
		return racingSuitNo;
	}
}
