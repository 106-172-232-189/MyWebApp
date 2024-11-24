package com.umamusumelist.bean;

/**
 * ウマ娘でないトレセン学園関係者を取り扱うBean
 *
 * @author Umamusumelist.com
 * @version 5.0
 */
public class NotUmamusumeBean {

	/** 名前 */
	private final String name;

	/** ウマ娘公式ポータルサイトにおける識別子 */
	private final String parameter;

	/**
	 * 新規インスタンス作成
	 *
	 * @param name 名前
	 * @param parameter ウマ娘公式ポータルサイトにおける識別子
	 * @return 同一の引数を用いる新たなBeanオブジェクトのインスタンス
	 * @exception NullPointerException 名前がnull
	 */
	public static NotUmamusumeBean create(String name, String parameter) {
		if (name == null) {
			throw new NullPointerException("名前がnullです");
		}

		return new NotUmamusumeBean(name, parameter);
	}

	/**
	 * 新規インスタンス作成時のコンストラクター
	 *
	 * @param name 名前
	 * @param parameter ウマ娘公式ポータルサイトにおける識別子
	 */
	private NotUmamusumeBean(String name, String parameter) {
		// TODO 自動生成されたコンストラクター・スタブ
		this.name = name;
		this.parameter = parameter;
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

}
