package com.umamusumelist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import com.umamusumelist.bean.OTPBean;

/**
 * ワンタイムパスワードを取り扱うDAO
 *
 * @author Umamusumelist.com
 * @version 8.0
 */
public final class OTPDAO implements AutoCloseable {

	/** データベースのURL */
	private static final String URL = "jdbc:postgresql://localhost:5432/my_database_2";

	/** データベースのユーザー */
	private static final String USER = "postgres";

	/** データベースの"ユーザーと紐づいたパスワード" */
	private static final String PASSWORD = "Nsns";

	/** データベースへの接続 */
	private final Connection c;

	/**
	 * 新規インスタンス作成時のコンストラクター
	 *
	 * @throws ClassNotFoundException JDBCドライバーが見つからない
	 * @throws SQLException データベースに関する処理時に何らかの異常が発生
	 */
	public OTPDAO() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		c = DriverManager.getConnection(URL, USER, PASSWORD);
	}

	/**
	 * 有効期限内のワンタイムパスワードを取得
	 *
	 * @return ワンタイムパスワードを取得するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 * @throws SQLException データベースに関する処理時に何らかの異常が発生
	 */
	private ResultSet select() throws SQLException {
		return c.prepareStatement("SELECT * FROM OTP WHERE expire > NOW();").executeQuery();
	}

	/**
	 * 新たなワンタイムパスワードを登録
	 *
	 * @return 新たなワンタイムパスワードを登録するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 * @throws SQLException データベースに関する処理時に何らかの異常が発生
	 */
	private PreparedStatement insert() throws SQLException {
		return c.prepareStatement("INSERT INTO OTP (otp, expire) VALUES (?, NOW() + INTERVAL '10 minutes');");
	}

	/**
	 * データベースへの接続を終了
	 *
	 * @throws SQLException データベースに関する処理時に何らかの異常が発生
	 */
	@Override
	public void close() throws SQLException {
		if (c == null) {
			return;
		} else {
			c.close();
		}
	}

	/**
	 * 有効期限内のワンタイムパスワードを取得
	 *
	 * @return ワンタイムパスワードを格納するArrayListオブジェクト
	 * @throws SQLException データベースに関する処理時に何らかの異常が発生
	 */
	public List<OTPBean> getOTP() throws SQLException {
		final List<OTPBean> obl = new ArrayList<>();
		final ResultSet rs = select();

		while (rs.next()) {
			obl.add(OTPBean.create(rs.getString("otp"), rs.getObject("expire", OffsetDateTime.class)));
		}

		return obl;
	}

	/**
	 * 新たなワンタイムパスワードを登録
	 *
	 * @param otp
	 *            ワンタイムパスワード
	 * @throws SQLException データベースに関する処理時に何らかの異常が発生
	 */
	public void setOTP(final String otp) throws SQLException {
		final PreparedStatement ps = insert();
		ps.setString(1, otp);
		ps.executeUpdate();
	}

	/**
	 * 期限切れのワンタイムパスワードを削除
	 *
	 * @throws SQLException データベースに関する処理時に何らかの異常が発生
	 */
	public void deleteExpiredOTP() throws SQLException {
		c.prepareStatement("DELETE FROM OTP WHERE expire <= NOW();").executeUpdate();
	}

	/**
	 * ワンタイムパスワードを削除
	 *
	 * @throws SQLException データベースに関する処理時に何らかの異常が発生
	 */
	public void delete() throws SQLException {
		c.prepareStatement("DELETE FROM OTP;").executeUpdate();
	}

	/**
	 * 有効期限内のワンタイムパスワードが存在するか否か
	 *
	 * @return 有効期限内のワンタイムパスワードが存在するか否か
	 */
	public boolean isExist() {
		try {
			final ResultSet rs = c.prepareStatement("SELECT COUNT(*) AS volume FROM OTP WHERE expire > NOW();").executeQuery();
			int volume = 0;

			while (rs.next()) {
				volume = rs.getInt("volume");
			}

			return volume > 0;
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return false;
		}
	}

}
