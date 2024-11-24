package com.umamusumelist.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.umamusumelist.bean.RacingUmamusumeBean;
import com.umamusumelist.util.KatakanaToZenkaku;

/**
 * 勝負服を得たウマ娘を取り扱うDAO
 *
 * @author Umamusumelist.com
 * @version 5.0
 */
public class RacingUmamusumeDAO {

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
	public RacingUmamusumeDAO() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * 勝負服を得ているウマ娘一覧を全件取得
	 *
	 * @param isExclusive 排他的な勝負服であるか
	 * @return 勝負服を得ているウマ娘一覧を全件取得するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private ResultSet select(boolean isExclusive) {
		try {
			return c.prepareStatement(
				(isExclusive ?
				"SELECT A.no AS noA, A.name AS name, A.appeared AS appeared, B.parameter AS parameter, B.no AS noB FROM Racing_Umamusume_Exclusive AS A INNER JOIN Umamusume_Exclusive AS B ON ((A.name IS NULL AND B.name IS NULL) OR (A.name = B.name)) "
				:
				"SELECT A.no AS noA, A.name AS name, A.appeared AS appeared, B.parameter AS parameter, B.no AS noB FROM Racing_Umamusume AS A INNER JOIN Umamusume AS B ON ((A.name IS NULL AND B.name IS NULL) OR (A.name = B.name)) "
				) +
				"ORDER BY A.no;").executeQuery();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 名前もしくは名前の一部で勝負服を得ているウマ娘一覧の中から検索
	 *
	 * @param isExclusive 排他的な勝負服であるか
	 * @return 名前もしくは名前の一部で勝負服を得ているウマ娘一覧の中から検索するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private PreparedStatement searchByName(final boolean isExclusive) {
		try {
			return c.prepareStatement(
				"SELECT noA, name, appeared, parameter, noB FROM (" +
					(isExclusive ?
					"SELECT A.no AS noA, A.name AS name, A.appeared AS appeared, B.parameter AS parameter, B.no AS noB FROM Racing_Umamusume_Exclusive AS A INNER JOIN Umamusume_Exclusive AS B ON ((A.name IS NULL AND B.name IS NULL) OR (A.name = B.name))"
					:
					"SELECT A.no AS noA, A.name AS name, A.appeared AS appeared, B.parameter AS parameter, B.no AS noB FROM Racing_Umamusume AS A INNER JOIN Umamusume AS B ON ((A.name IS NULL AND B.name IS NULL) OR (A.name = B.name))"
					) +
				") AS Racing_Umamusume WHERE name LIKE ? ORDER BY noA;");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 勝負服番号で勝負服を得ているウマ娘一覧(900番台を除く)の中から検索
	 *
	 * @return 勝負服番号で勝負服を得ているウマ娘一覧(900番台を除く)の中から検索するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private PreparedStatement searchByNo() {
		try {
			return c.prepareStatement(
				"SELECT noA, name, appeared, parameter, noB FROM ( " +
					"SELECT A.no AS noA, A.name AS name, A.appeared AS appeared, B.parameter AS parameter, B.no AS noB FROM Racing_Umamusume AS A INNER JOIN Umamusume AS B ON ((A.name IS NULL AND B.name IS NULL) OR (A.name = B.name)) " +
				") AS Racing_Umamusume WHERE noA = ? ORDER BY noA;");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 新たに勝負服を得たウマ娘を一覧に登録
	 *
	 * @param isExclusive 排他的な勝負服であるか
	 * @return 新たに勝負服を得たウマ娘を一覧に登録するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private PreparedStatement insert(final boolean isExclusive) {
		try {
			return c.prepareStatement("INSERT INTO " +
									  (
										   isExclusive ?
										  "Racing_Umamusume_Exclusive VALUES (?, (SELECT name FROM Umamusume_Exclusive WHERE no = ?));" :
										  "Racing_Umamusume (name, appeared) VALUES ((SELECT name FROM Umamusume WHERE no = ?), ?);"
									  ));
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 何らかの事情により登場できなくなったウマ娘を勝負服番号から特定して一覧から削除
	 *
	 * @param isExclusive 排他的な勝負服であるか
	 * @return 何らかの事情により登場できなくなったウマ娘を勝負服番号から特定して一覧から削除するSQL文をデータベースに送るためのPreparedStatementオブジェクト
	 */
	private PreparedStatement delete(final boolean isExclusive) {
		try {
			return c.prepareStatement("DELETE FROM " + (isExclusive ? "Racing_Umamusume_Exclusive " : "Racing_Umamusume ") + "WHERE no = ?;");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 勝負服を得ているウマ娘一覧(テーブル別)を全件取得
	 *
	 * @param isExclusive 排他的な勝負服であるか
	 * @return 勝負服を得ているウマ娘一覧(テーブル別)を全件取得した結果を格納するArrayListオブジェクト
	 */
	public List<RacingUmamusumeBean> getList(boolean isExclusive) {
		final List<RacingUmamusumeBean> rubl = new ArrayList<>();
		final ResultSet rs = select(isExclusive);

		try {
			while (rs.next()) {
				rubl.add(RacingUmamusumeBean.create(isExclusive,
													rs.getInt("noA"),
													rs.getString("name"),
													rs.getString("parameter"),
													rs.getDate("appeared"),
													rs.getInt("noB")));
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return rubl;
	}

	/**
	 * 勝負服を得ているウマ娘一覧を全件取得
	 *
	 * @return 勝負服を得ているウマ娘一覧を全件取得した結果を格納するArrayListオブジェクト
	 */
	public List<RacingUmamusumeBean> getList() {
		final List<RacingUmamusumeBean> rubl = getList(false);
		rubl.addAll(getList(false).size(), getList(true));
		return rubl;
	}

	/**
	 * 名前もしくは名前の一部で勝負服を得ているウマ娘一覧の中から検索
	 *
	 * @param isExclusive 排他的な勝負服であるか
	 * @param name 名前
	 * @return 名前もしくは名前の一部で勝負服を得ているウマ娘一覧の中から検索した結果を格納するArrayListオブジェクト
	 */
	public List<RacingUmamusumeBean> getRacingUmamusume(final boolean isExclusive, final String name) {
		final PreparedStatement ps = searchByName(isExclusive);
		final List<RacingUmamusumeBean> rubl = new ArrayList<>();

		try {
			ps.setString(1, "%" + KatakanaToZenkaku.katakanaToZenkaku(name) + "%");

			final ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rubl.add(RacingUmamusumeBean.create(isExclusive,
													rs.getInt("noA"),
													rs.getString("name"),
													rs.getString("parameter"),
													rs.getDate("appeared"),
													rs.getInt("noB")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rubl;
	}

	/**
	 * 勝負服番号で勝負服を得ているウマ娘一覧(900番台を除く)の中から検索
	 * @param racingSuitno 勝負服番号
	 * @return 勝負服番号で勝負服を得ているウマ娘一覧(900番台を除く)の中から検索した結果を格納するArrayListオブジェクト
	 */
	public List<RacingUmamusumeBean> getRacingUmamusume(final int racingSuitno) {
		final PreparedStatement ps = searchByNo();
		final List<RacingUmamusumeBean> rubl = new ArrayList<>();

		try {
			ps.setInt(1, racingSuitno);

			final ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rubl.add(RacingUmamusumeBean.create(false,
													rs.getInt("noA"),
													rs.getString("name"),
													rs.getString("parameter"),
													rs.getDate("appeared"),
													rs.getInt("noB")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rubl;
	}

	/**
	 * 図鑑番号からウマ娘を特定し、勝負服を得たウマ娘を一覧に登録
	 *
	 * @param isExclusive 排他的な勝負服であるか
	 * @param racingSuitNo 特殊な勝負服番号
	 * @param umadexNo 図鑑番号
	 * @param appeared ウマ娘公式ポータルサイトにて勝負服が登録された日時
	 */
	public void setRacingUmamusume(final boolean isExclusive, final int racingSuitNo, final int umadexNo, final Date appeared) {
		final PreparedStatement ps = insert(isExclusive);

		try {
			if (isExclusive) {
				// 特殊なウマ娘を登録する場合、指定した特殊な勝負服番号(racingSuitNo)を添えて登録する。
				ps.setInt(1, racingSuitNo);
				ps.setInt(2, umadexNo);
			} else {
				// 通常のウマ娘を登録する場合、登録日(appeared)を添えて登録する。
				ps.setInt(1, umadexNo);
				ps.setDate(2, appeared);
			}

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * 何らかの事情により登場できなくなったウマ娘を勝負服番号から特定して一覧から削除
	 *
	 * @param isExclusive 排他的な勝負服であるか
	 * @param racingSuitNo 勝負服番号
	 */
	public void deleteRacingUmamusume(final boolean isExclusive, final int racingSuitNo) {
		final PreparedStatement ps = delete(isExclusive);

		try {
			ps.setInt(1, racingSuitNo);
			ps.executeUpdate();

			if (!isExclusive) {
				c.prepareStatement("SELECT after_delete_x();").executeUpdate();
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
		}
	}

	/**
	 * 勝負服を得ているウマ娘の総数
	 *
	 * @return 勝負服を得ているウマ娘の総数
	 */
	public int noMax() {
		try {
			final ResultSet rs = c.prepareStatement("SELECT COUNT(*) AS noMax FROM Racing_Umamusume").executeQuery();
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
