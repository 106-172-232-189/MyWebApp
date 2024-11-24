package com.umamusumelist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.umamusumelist.bean.UmamusumeBean;
import com.umamusumelist.util.KatakanaToZenkaku;

/**
 * ウマ娘を取り扱うDAO
 *
 * @author Umamusumelist.com
 * @version 5.1
 */
public final class UmamusumeDAO {

	/** データベースのURL */
	private static final String URL = "jdbc:postgresql://localhost:5432/my_database";

	/** データベースのユーザー */
	private static final String USER = "postgres";

	/** データベースの"ユーザーと紐づいたパスワード" */
	private static final String PASSWORD = "Nsns";

	/** データベースへの接続 */
	private final Connection c;

	/**
	 * 新規インスタンス作成時のコンストラクター
	 */
	public UmamusumeDAO() throws Exception {
		Class.forName("org.postgresql.Driver");
		c = DriverManager.getConnection(URL, USER, PASSWORD);
	}

	/**
	 * ウマ娘一覧を全件取得
	 *
	 * @param isExclusive
	 *            トレセン学園関係者であるか
	 * @return ウマ娘一覧を全件取得するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private ResultSet select(final boolean isExclusive) throws SQLException {
		return c.prepareStatement(isExclusive
				? "SELECT A.no AS noA, A.name, A.parameter, B.no AS noB FROM Umamusume_Exclusive AS A LEFT OUTER JOIN Racing_Umamusume_Exclusive AS B on (A.name IS null AND B.name IS null) OR A.name = B.name ORDER BY A.no;"
				: "SELECT A.no AS noA, A.name, A.parameter, B.no AS noB FROM Umamusume AS A LEFT OUTER JOIN Racing_Umamusume AS B on (A.name IS null AND B.name IS null) OR A.name = B.name ORDER BY A.no;")
				.executeQuery();
	}

	/**
	 * 名前もしくは名前の一部でウマ娘一覧の中から検索
	 *
	 * @return 名前もしくは名前の一部でウマ娘一覧の中から検索するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private PreparedStatement searchByName() throws SQLException {
		return c.prepareStatement("SELECT noA, name, parameter, noB FROM ("
				+ "SELECT A.no AS noA, A.name, A.parameter, B.no AS noB FROM Umamusume AS A LEFT OUTER JOIN Racing_Umamusume AS B on (A.name IS null AND B.name IS null) OR A.name = B.name "
				+ "UNION "
				+ "SELECT A.no AS noA, A.name, A.parameter, B.no AS noB FROM Umamusume_Exclusive AS A LEFT OUTER JOIN Racing_Umamusume_Exclusive AS B on (A.name IS null AND B.name IS null) OR A.name = B.name) "
				+ "AS Umamusume WHERE name LIKE ? ORDER BY noA;");
	}

	/**
	 * 図鑑番号でウマ娘一覧(800番台以上を除く)の中から検索
	 *
	 * @return 図鑑番号でウマ娘一覧(800番台以上を除く)の中から検索するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private PreparedStatement searchByNo() throws SQLException {
		return c.prepareStatement("SELECT noA, name, parameter, noB FROM ("
				+ "SELECT A.no AS noA, A.name, A.parameter, B.no AS noB FROM Umamusume AS A LEFT OUTER JOIN Racing_Umamusume AS B on (A.name IS null AND B.name IS null) OR A.name = B.name "
				+ ") AS Umamusume WHERE noA = ?;");
	}

	/**
	 * 勝負服を得ていないウマ娘一覧を取得
	 *
	 * @param isExclusive
	 *            トレセン学園関係者であるか
	 * @return 勝負服を得ていないウマ娘一覧を取得するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private ResultSet selectWhereRacingUmamusumeNoIsNull(final boolean isExclusive) throws SQLException {
		return c.prepareStatement(isExclusive
				? "SELECT A.no AS noA, A.name, A.parameter, B.no AS noB FROM Umamusume_Exclusive AS A LEFT OUTER JOIN Racing_Umamusume_Exclusive AS B ON ((A.name IS NULL AND B.name IS NULL) OR (A.name = B.name)) WHERE B.no IS NULL AND A.no > 802 ORDER BY A.no;"
				: "SELECT A.no AS noA, A.name, A.parameter, B.no AS noB FROM Umamusume AS A LEFT OUTER JOIN Racing_Umamusume AS B ON ((A.name IS NULL AND B.name IS NULL) OR (A.name = B.name)) WHERE B.no IS NULL ORDER BY A.no;")
				.executeQuery();
	}

	/**
	 * 新たなウマ娘を一覧に登録
	 *
	 * @param isExclusive
	 *            トレセン学園関係者であるか
	 * @return 新たなウマ娘を一覧に登録するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private PreparedStatement insert(final boolean isExclusive) throws SQLException {
		return c.prepareStatement(
				"INSERT INTO " + (isExclusive ? "Umamusume_Exclusive (no, name, parameter) VALUES (?, ?, ?);"
						: "Umamusume (name, parameter) VALUES (?, ?);"));
	}

	/**
	 * ウマ娘の情報を変更
	 *
	 * @param isExclusive
	 *            トレセン学園関係者であるか
	 * @return ウマ娘の情報を変更するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private PreparedStatement update(final boolean isExclusive) throws SQLException {
		return c.prepareStatement("UPDATE " + (isExclusive ? "Umamusume_Exclusive " : "Umamusume ")
				+ "SET name = ?, parameter = ? WHERE no = ?;");
	}

	/**
	 * 何らかの事情により登場できなくなったウマ娘を一覧から削除
	 *
	 * @param isExclusive
	 *            トレセン学園関係者であるか
	 * @return 何らかの事情により登場できなくなったウマ娘を一覧から削除するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private PreparedStatement delete(final boolean isExclusive) throws SQLException {
		return c.prepareStatement(
				"DELETE FROM " + (isExclusive ? "Umamusume_Exclusive " : "Umamusume ") + "WHERE no = ?;");
	}

	/**
	 * ウマ娘一覧(テーブル別)を全件取得
	 *
	 * @param isExclusive
	 *            トレセン学園関係者であるか
	 * @return ウマ娘一覧(テーブル別)を全件取得した結果を格納するArrayListオブジェクト
	 */
	public List<UmamusumeBean> getList(final boolean isExclusive) throws SQLException {
		final List<UmamusumeBean> ubl = new ArrayList<>();
		final ResultSet rs = select(isExclusive);

		while (rs.next()) {
			ubl.add(UmamusumeBean.create(rs.getInt("noA"), rs.getString("name"), rs.getString("parameter"),
					rs.getInt("noB")));
		}

		return ubl;
	}

	/**
	 * ウマ娘一覧を全件取得
	 *
	 * @return ウマ娘一覧を全件取得した結果を格納するArrayListオブジェクト
	 */
	public List<UmamusumeBean> getList() throws SQLException {
		final List<UmamusumeBean> ubl = getList(false);
		ubl.addAll(ubl.size(), getList(true));
		return ubl;
	}

	/**
	 * 勝負服を得ていないウマ娘一覧を取得
	 *
	 * @param isExclusive
	 *            トレセン学園関係者であるか
	 * @return 勝負服を得ていないウマ娘一覧を取得した結果を格納するArrayListオブジェクト
	 */
	public List<UmamusumeBean> getListWhereRacingUmamusumeNoIsNull(final boolean isExclusive) throws SQLException {
		final List<UmamusumeBean> ubl = new ArrayList<>();
		final ResultSet rs = selectWhereRacingUmamusumeNoIsNull(isExclusive);

		while (rs.next()) {
			ubl.add(UmamusumeBean.create(rs.getInt("noA"), rs.getString("name"), rs.getString("parameter"),
					rs.getInt("noB")));
		}

		return ubl;
	}

	/**
	 * 名前もしくは名前の一部でウマ娘一覧の中から検索
	 *
	 * @param name
	 *            名前
	 * @return 名前もしくは名前の一部でウマ娘一覧の中から検索した結果を格納するArrayListオブジェクト
	 */
	public List<UmamusumeBean> getUmamusume(final String name) throws SQLException {
		final PreparedStatement ps = searchByName();
		final List<UmamusumeBean> ubl = new ArrayList<>();

		ps.setString(1, "%" + KatakanaToZenkaku.katakanaToZenkaku(name) + "%");
		final ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			ubl.add(UmamusumeBean.create(rs.getInt("noA"), rs.getString("name"), rs.getString("parameter"),
					rs.getInt("noB")));
		}

		return ubl;
	}

	/**
	 * 図鑑番号でウマ娘一覧(800番台以上を除く)の中から検索
	 *
	 * @param umadexNo
	 *            図鑑番号
	 * @return 図鑑番号でウマ娘一覧(800番台以上を除く)の中から検索した結果を格納するArrayListオブジェクト
	 */
	public List<UmamusumeBean> getUmamusume(final int umadexNo) throws SQLException {
		final PreparedStatement ps = searchByNo();
		final List<UmamusumeBean> ubl = new ArrayList<>();

		ps.setInt(1, umadexNo);
		final ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			ubl.add(UmamusumeBean.create(rs.getInt("noA"), rs.getString("name"), rs.getString("parameter"),
					rs.getInt("noB")));
		}

		return ubl;
	}

	/**
	 * 新たなウマ娘を一覧(800番台以上を除く)に登録
	 *
	 * @param name
	 *            名前
	 * @param parameter
	 *            ウマ娘公式ポータルサイトにおける識別子
	 */
	public void setUmamusume(final String name, final String parameter) throws SQLException {
		setUmamusume(false, 0, name, parameter);
	}

	/**
	 * 新たなウマ娘を一覧(800番台以上)に登録
	 *
	 * @param isExclusive
	 *            トレセン学園関係者であるか
	 * @param umadexNo
	 *            特殊な図鑑番号
	 * @param name
	 *            名前
	 * @param parameter
	 *            ウマ娘公式ポータルサイトにおける識別子
	 */
	public void setUmamusume(final boolean isExclusive, final int umadexNo, final String name, final String parameter)
			throws SQLException {
		final PreparedStatement ps = insert(isExclusive);

		if (isExclusive) {
			// 特殊なウマ娘を新規登録する場合、特殊な図鑑番号を添える。
			ps.setInt(1, umadexNo);
			ps.setString(2, name);
			ps.setString(3, parameter.equals("") ? null : parameter);
		} else {
			ps.setString(1, name);
			ps.setString(2, parameter.equals("") ? null : parameter);
		}

		ps.executeUpdate();
	}

	/**
	 * 図鑑番号からすでに登録してあるウマ娘を特定し、名前もしくはパラメーターの変更
	 *
	 * @param isExclusive
	 *            トレセン学園関係者であるか
	 * @param umadexNo
	 *            図鑑番号
	 * @param newName
	 *            変更後の名前
	 * @param newParameter
	 *            変更後のウマ娘公式ポータルサイトにおける識別子
	 */
	public void updateName(final boolean isExclusive, final int umadexNo, final String newName,
			final String newParameter) throws SQLException {
		final PreparedStatement ps = update(isExclusive);

		ps.setString(1, newName);
		ps.setString(2, newParameter);
		ps.setInt(3, umadexNo);
		ps.executeUpdate();
	}

	/**
	 * 何らかの事情により登場できなくなったウマ娘を図鑑番号から特定し、一覧から削除
	 *
	 * @param isExclusive
	 *            トレセン学園関係者であるか
	 * @param umadexNo
	 *            図鑑番号
	 */
	public void deleteUmamusume(final boolean isExclusive, final int umadexNo) throws SQLException {
		final PreparedStatement ps = delete(isExclusive);

		try {
			ps.setInt(1, umadexNo);
			ps.executeUpdate();
			c.prepareStatement("SELECT after_delete_a();").executeUpdate();
		} catch (SQLException e) {
			// 正常に削除されたとみなす。
		}

	}

	/**
	 * トレセン学園関係者であるウマ娘、没ウマ娘を除いたウマ娘の総数
	 *
	 * @return トレセン学園関係者であるウマ娘、没ウマ娘を除いたウマ娘の総数
	 */
	public int noMax() {
		try {
			final ResultSet rs = c.prepareStatement("SELECT COUNT(*) AS noMax FROM Umamusume;").executeQuery();
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
