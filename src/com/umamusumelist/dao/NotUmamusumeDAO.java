package com.umamusumelist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.umamusumelist.bean.NotUmamusumeBean;
import com.umamusumelist.util.KatakanaToZenkaku;

/**
 * ウマ娘でないトレセン学園関係者を取り扱うDAO
 *
 * @author Umamusumelist.com
 * @version 5.0
 */
public class NotUmamusumeDAO {

	/** データベースのURL */
	private static final String URL = "jdbc:postgresql://localhost:5432/my_database";

	/** データベースのユーザー */
	private static final String USER = "postgres";

	/** データベースの"ユーザーと紐づいたパスワード" */
	private static final String PASSWORD = "Nsns";


	/** データベースへの接続 */
	private Connection c;

	/**
	 * 新規インスタンス作成時のコンストラクター
	 */
	public NotUmamusumeDAO() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * ウマ娘でないトレセン学園関係者一覧を全件取得
	 *
	 * @return ウマ娘でないトレセン学園関係者一覧を全件取得するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private ResultSet select() {
		try {
			return c.prepareStatement(
				"SELECT name, parameter FROM Not_Umamusume ORDER BY name;"
				).executeQuery();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 名前もしくは名前の一部でウマ娘でないトレセン学園関係者一覧の中から検索して取得
	 *
	 * @return 名前もしくは名前の一部でウマ娘でないトレセン学園関係者一覧の中から検索して取得するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private PreparedStatement search() {
		try {
			return c.prepareStatement(
				"SELECT name, parameter FROM Not_Umamusume WHERE name LIKE ? ORDER BY name;"
			);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 新たな「ウマ娘でないトレセン学園関係者」を一覧に登録
	 *
	 * @return 新たな「ウマ娘でないトレセン学園関係者」を一覧に登録するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private PreparedStatement insert() {
		try {
			return c.prepareStatement("INSERT INTO Not_Umamusume (name, parameter) VALUES (?, ?);");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ウマ娘でないトレセン学園関係者の情報を変更
	 *
	 * @return ウマ娘でないトレセン学園関係者の情報を変更するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private PreparedStatement update() {
		try {
			return c.prepareStatement("UPDATE Not_Umamusume SET name = ?, parameter = ? WHERE name = ?;");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 何らかの事情により登場できなくなった「ウマ娘でないトレセン学園関係者」を一覧から削除
	 *
	 * @return 何らかの事情により登場できなくなった「ウマ娘でないトレセン学園関係者」を一覧から削除するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private PreparedStatement delete() {
		try {
			return c.prepareStatement("DELETE FROM Not_Umamusume WHERE name = ?;");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ウマ娘でないトレセン学園関係者一覧を全件取得
	 *
	 * @return ウマ娘でないトレセン学園関係者一覧を全件取得した結果を格納するArrayListオブジェクト
	 */
	public List<NotUmamusumeBean> getList() {
		final List<NotUmamusumeBean> ubl = new ArrayList<>();
		final ResultSet rs = select();

		try {
			while (rs.next()) {
				ubl.add(NotUmamusumeBean.create(rs.getString("name"),
												rs.getString("parameter")));
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return ubl;
	}

	/**
	 * 名前もしくは名前の一部でウマ娘でないトレセン学園関係者一覧の中から検索
	 *
	 * @param name 名前
	 * @return 名前もしくは名前の一部でウマ娘でないトレセン学園関係者一覧の中から検索した結果を格納するArrayListオブジェクト
	 */
	public List<NotUmamusumeBean> getNotUmamusume(final String name) {
		final PreparedStatement ps = search();
		final List<NotUmamusumeBean> ubl = new ArrayList<>();

		try {
			ps.setString(1, "%" + KatakanaToZenkaku.katakanaToZenkaku(name) + "%");

			final ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ubl.add(NotUmamusumeBean.create(rs.getString("name"),
												rs.getString("parameter")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ubl;
	}

	/**
	 * 新たな「ウマ娘でないトレセン学園関係者」を一覧に登録
	 *
	 * @param name 名前
	 * @param parameter ウマ娘公式ポータルサイトにおける識別子
	 */
	public void setNotUmamusume(final String name, final String parameter) {
		final PreparedStatement ps = insert();

		try {
			ps.setString(1, name);
			ps.setString(2, parameter.equals("") ? null : parameter);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * すでに登録してある「ウマ娘でないトレセン学園関係者」を特定し、名前もしくは「ウマ娘公式ポータルサイトにおける識別子」の変更
	 *
	 * @param name 変更前の名前
	 * @param newName 変更後の名前
	 * @param newParameter 変更後の「ウマ娘公式ポータルサイトにおける識別子」
	 */
	public void updateName(final String name, final String newName, final String newParameter) {
		final PreparedStatement ps = update();

		try {
			ps.setString(1, newName);
			ps.setString(2, newParameter);
			ps.setString(3, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 何らかの事情により登場できなくなった「ウマ娘でないトレセン学園関係者」を特定し、一覧から削除
	 *
	 * @param name 名前
	 */
	public void deleteNotUmamusume(final String name) {
		final PreparedStatement ps = delete();

		try {
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
		}
	}

	/**
	 * ウマ娘でないトレセン学園関係者の総数
	 *
	 * @return ウマ娘でないトレセン学園関係者の総数
	 */
	public int noMax() {
		try {
			final ResultSet rs = c.prepareStatement("SELECT COUNT(*) AS noMax FROM Not_Umamusume").executeQuery();
			int noMax = 0;

			while (rs.next()) {
				noMax = rs.getInt("noMax");
			}

			return noMax;
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return 0;
		}
	}

}