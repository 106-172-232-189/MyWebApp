package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.RacingUmamusumeBean;
import util.KatakanaToZenkaku;

public class RacingUmamusumeDAO {

	private static final String URL = "jdbc:postgresql://localhost:5432/my_database";
	private static final String USER = "postgres";
	private static final String PASSWORD = "Nsns";

	private Connection c;

	public RacingUmamusumeDAO() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	// 以下のSELECT文で勝負服を得ているウマ娘の一覧を取得する。
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

	// 名前もしくは名前の一部で勝負服を得ているウマ娘一覧の中から検索する。
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

	// 勝負服番号で勝負服を得ているウマ娘一覧の中から検索する。
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

	// 以下のINSERT文で新たに勝負服を得たウマ娘を一覧に登録する。
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

	// 以下のDELETE文で何らかの事情により登場できなくなったウマ娘を一覧から削除する。
	private PreparedStatement delete(final boolean isExclusive) {
		try {
			return c.prepareStatement("DELETE FROM " + (isExclusive ? "Racing_Umamusume_Exclusive " : "Racing_Umamusume ") + "WHERE no = ?;");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}

	/// isExclusiveの値によって、勝負服を得た通常のウマ娘一覧を取得するか勝負服を得た特殊なウマ娘を取得するかが変わる。
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

	// 勝負服を得ているウマ娘一覧を取得する。
	public List<RacingUmamusumeBean> getList() {
		final List<RacingUmamusumeBean> rubl = getList(false);
		rubl.addAll(getList(false).size(), getList(true));
		return rubl;
	}

	// 名前もしくは名前の一部で勝負服を得ているウマ娘一覧の中から検索する。
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

	// 勝負服番号で勝負服を得ているウマ娘一覧の中から検索する。
	public List<RacingUmamusumeBean> getRacingUmamusume(final int no) {
		final PreparedStatement ps = searchByNo();
		final List<RacingUmamusumeBean> rubl = new ArrayList<>();

		try {
			ps.setInt(1, no);

			final ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				rubl.add(RacingUmamusumeBean.create(rs.getInt("noB") >= 800,
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

	// 図鑑番号(noB)からウマ娘を特定し、勝負服を得たウマ娘を一覧に登録する。
	public void setRacingUmamusume(final boolean isExclusive, final int noA, final int noB, final Date appeared) {
		final PreparedStatement ps = insert(isExclusive);

		try {
			if (isExclusive) {
				// 特殊なウマ娘を登録する場合、指定した特殊な勝負服番号(noA)を添えて登録する。
				ps.setInt(1, noA);
				ps.setInt(2, noB);
			} else {
				// 通常のウマ娘を登録する場合、登録日(appeared)を添えて登録する。
				ps.setInt(1, noB);
				ps.setDate(2, appeared);
			}

			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	// 勝負服番号からウマ娘を特定して勝負服を得ているウマ娘一覧から削除する。
	public void deleteRacingUmamusume(final boolean isExclusive, final int no) {
		final PreparedStatement ps = delete(isExclusive);

		try {
			ps.setInt(1, no);
			ps.executeUpdate();

			if (!isExclusive) {
				c.prepareStatement("SELECT after_delete_x();").executeUpdate();
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
		}
	}

	// 勝負服を得ているウマ娘の総数を返す。
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
