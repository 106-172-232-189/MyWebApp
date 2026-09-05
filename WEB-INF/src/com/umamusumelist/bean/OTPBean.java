package com.umamusumelist.bean;

import java.time.OffsetDateTime;

/**
 * ワンタイムパスワードを取り扱うBean
 *
 * @author Umamusumelist.com
 * @version 8.0
 */
public final class OTPBean {

	/** ワンタイムパスワード */
	private final String otp;

	/** 有効期限 */
	private final OffsetDateTime expire;

	/**
	 * 新規インスタンス作成
	 *
	 * @param otp ワンタイムパスワード
	 * @param expire 有効期限
	 * @return 同一の引数を用いる新たなBeanオブジェクトのインスタンス
	 * @exception NullPointerException ワンタイムパスワードがnull
	 */
	public static OTPBean create(final String otp, final OffsetDateTime expire) {
		if (otp == null) {
			throw new NullPointerException("ワンタイムパスワードがnullです");
		}

		if (expire == null) {
			throw new NullPointerException("有効期限がnullです");
		}

		return new OTPBean(otp, expire);
	}

	/**
	 * 新規インスタンス作成時のコンストラクター
	 *
	 * @param otp ワンタイムパスワード
	 * @param expire 有効期限
	 */
	private OTPBean(final String otp, final OffsetDateTime expire) {
		this.otp = otp;
		this.expire = expire;
	}

	/**
	 * ワンタイムパスワード
	 *
	 * @return ワンタイムパスワード
	 */
	public String otp() {
		return otp;
	}

	/**
	 * 有効期限
	 *
	 * @return 有効期限
	 */
	public OffsetDateTime expire() {
		return expire;
	}

}
