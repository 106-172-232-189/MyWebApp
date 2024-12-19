package com.umamusumelist.bean;

import java.sql.Date;

/**
 * 勝負服を得たウマ娘を取り扱うBean
 *
 * @author Umamusumelist.com
 * @version 5.2
 */
public final class RacingUmamusumeBean {

	/** 排他的な勝負服であるか */
	private final boolean isExclusive;

	/** 勝負服番号 */
	private final int racingSuitNo;

	/** 名前 */
	private final String name;

	/** ウマ娘公式ポータルサイトにおける識別子 */
	private final String parameter;

	/** ウマ娘公式ポータルサイトにて勝負服が登録された日時 */
	private final Date appeared;

	/** 図鑑番号 */
	private final int umadexNo;

	/**
	 * 新規インスタンス作成
	 *
	 * @param isExclusive
	 *            排他的な勝負服であるか
	 * @param racingSuitNo
	 *            勝負服番号
	 * @param name
	 *            名前
	 * @param parameter
	 *            ウマ娘公式ポータルサイトにおける識別子
	 * @param appeared
	 *            ウマ娘公式ポータルサイトにて勝負服が登録された日時
	 * @param umadexNo
	 *            図鑑番号
	 * @return 同一の引数を用いる新たなBeanオブジェクトのインスタンス
	 * @exception NullPointerException
	 *                名前がnull
	 * @exception IllegalArgumentException
	 *                勝負服番号もしくは図鑑番号が0未満
	 */
	public static RacingUmamusumeBean create(final boolean isExclusive, final int racingSuitNo, final String name,
			final String parameter, final Date appeared, final int umadexNo) {
		if (name == null) {
			throw new NullPointerException("名前がnullです");
		}

		if (racingSuitNo < 0 || umadexNo < 0) {
			throw new IllegalArgumentException("勝負服番号もしくは図鑑番号が0未満です");
		}

		return new RacingUmamusumeBean(isExclusive, racingSuitNo, name, parameter, appeared == null ? null : new Date(appeared.getTime()),
				umadexNo);
	}

	/**
	 * 新規インスタンス作成時のコンストラクター
	 *
	 * @param isExclusive
	 *            排他的な勝負服であるか
	 * @param racingSuitNo
	 *            勝負服番号
	 * @param name
	 *            名前
	 * @param parameter
	 *            ウマ娘公式ポータルサイトにおける識別子
	 * @param appeared
	 *            ウマ娘公式ポータルサイトにて勝負服が登録された日時
	 * @param umadexNo
	 *            図鑑番号
	 */
	private RacingUmamusumeBean(final boolean isExclusive, final int racingSuitNo, final String name,
			final String parameter, final Date appeared, final int umadexNo) {
		this.isExclusive = isExclusive;
		this.racingSuitNo = racingSuitNo;
		this.name = name;
		this.parameter = parameter;
		this.appeared = appeared == null ? null : new Date(appeared.getTime());
		this.umadexNo = umadexNo;
	}

	/**
	 * 排他的な勝負服であるか
	 *
	 * @return 排他的な勝負服であるか
	 */
	public boolean isExclusive() {
		return isExclusive;
	}

	/**
	 * 勝負服番号
	 *
	 * @return 勝負服番号
	 */
	public int racingSuitNo() {
		return racingSuitNo;
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
	 * ウマ娘公式ポータルサイトにて勝負服が登録された日時
	 *
	 * @return ウマ娘公式ポータルサイトにて勝負服が登録された日時
	 */
	public Date appeared() {
		return appeared == null ? null : new Date(appeared.getTime());
	}

	/**
	 * 図鑑番号
	 *
	 * @return 図鑑番号
	 */
	public int umadexNo() {
		return umadexNo;
	}

}
