package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.UmamusumeBean;

public class UmamusumeDAO {

	private static final String URL = "jdbc:postgresql://localhost:5432/my_database";
	private static final String USER = "postgres";
	private static final String PASSWORD = "Nsns";

	private Connection c;

	public UmamusumeDAO() {
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	// 以下のSELECT文でウマ娘一覧を取得する。
	private ResultSet select(final boolean isExclusive) {
		try {
			return c.prepareStatement(
				isExclusive ?
				"SELECT A.no AS noA, A.name, A.parameter, B.no AS noB FROM Umamusume_Exclusive AS A LEFT OUTER JOIN Racing_Umamusume_Exclusive AS B on (A.name IS null AND B.name IS null) OR A.name = B.name ORDER BY A.no;" :
				"SELECT A.no AS noA, A.name, A.parameter, B.no AS noB FROM Umamusume AS A LEFT OUTER JOIN Racing_Umamusume AS B on (A.name IS null AND B.name IS null) OR A.name = B.name ORDER BY A.no;"
			).executeQuery();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}

	// 名前もしくは名前の一部でウマ娘一覧の中から検索する。
	private PreparedStatement searchByName() {
		try {
			return c.prepareStatement(
				"SELECT noA, name, parameter, noB FROM (" +
				"SELECT A.no AS noA, A.name, A.parameter, B.no AS noB FROM Umamusume AS A LEFT OUTER JOIN Racing_Umamusume AS B on (A.name IS null AND B.name IS null) OR A.name = B.name " +
				"UNION " +
				"SELECT A.no AS noA, A.name, A.parameter, B.no AS noB FROM Umamusume_Exclusive AS A LEFT OUTER JOIN Racing_Umamusume_Exclusive AS B on (A.name IS null AND B.name IS null) OR A.name = B.name) " +
				"AS Umamusume WHERE name LIKE ? ORDER BY noA;"
			);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 図鑑番号(mode=true)もしくは勝負服番号(mode=false)でウマ娘一覧の中から検索する。
	private PreparedStatement searchByNo(boolean mode) {
		try {
			return c.prepareStatement(
				"SELECT noA, name, parameter, noB FROM (" +
				"SELECT A.no AS noA, A.name, A.parameter, B.no AS noB FROM Umamusume AS A LEFT OUTER JOIN Racing_Umamusume AS B on (A.name IS null AND B.name IS null) OR A.name = B.name " +
				"UNION " +
				"SELECT A.no AS noA, A.name, A.parameter, B.no AS noB FROM Umamusume_Exclusive AS A LEFT OUTER JOIN Racing_Umamusume_Exclusive AS B on (A.name IS null AND B.name IS null) OR A.name = B.name) " +
				"AS Umamusume WHERE " + (mode ? "noA = ?;" : "noB = ?;")
			);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 以下のSELECT文で勝負服を得ていないウマ娘一覧を取得する。
	private ResultSet selectWhereRacingUmamusumeNoIsNull(final boolean isExclusive) {
		try {
			return c.prepareStatement(
				isExclusive ?
				"SELECT A.no AS noA, A.name, A.parameter, B.no AS noB FROM Umamusume_Exclusive AS A LEFT OUTER JOIN Racing_Umamusume_Exclusive AS B ON ((A.name IS NULL AND B.name IS NULL) OR (A.name = B.name)) WHERE B.no IS NULL AND A.no > 801 ORDER BY A.no;" :
				"SELECT A.no AS noA, A.name, A.parameter, B.no AS noB FROM Umamusume AS A LEFT OUTER JOIN Racing_Umamusume AS B ON ((A.name IS NULL AND B.name IS NULL) OR (A.name = B.name)) WHERE B.no IS NULL ORDER BY A.no;"
			).executeQuery();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}

	// 以下のINSERT文で新たなウマ娘を一覧に登録する。
	private PreparedStatement insert(final boolean isExclusive) {
		try {
			return c.prepareStatement("INSERT INTO " + (isExclusive ? "Umamusume_Exclusive (no, name, parameter) VALUES (?, ?, ?);" : "Umamusume (name, parameter) VALUES (?, ?);"));
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}

	// 以下のUPDATE文でウマ娘の情報を変更する。
	private PreparedStatement update(final boolean isExclusive) {
		try {
			return c.prepareStatement("UPDATE " + (isExclusive ? "Umamusume_Exclusive " : "Umamusume ") + "SET name = ?, parameter = ? WHERE no = ?;");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 以下のDELETE文で何らかの事情により登場できなくなったウマ娘を一覧から削除する。
	private PreparedStatement delete(final boolean isExclusive) {
		try {
			return c.prepareStatement("DELETE FROM " + (isExclusive ? "Umamusume_Exclusive " : "Umamusume ") + "WHERE no = ?;");
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}

	// isExclusiveの値によって、通常のウマ娘一覧を取得するか特殊なウマ娘を取得するかが変わる。
	public List<UmamusumeBean> getList(final boolean isExclusive) {
		final List<UmamusumeBean> ubl = new ArrayList<>();
		final ResultSet rs = select(isExclusive);

		try {
			while (rs.next()) {
				ubl.add(UmamusumeBean.create(rs.getInt("noA"),
											 rs.getString("name"),
											 rs.getString("parameter"),
											 rs.getInt("noB")));
			}
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return ubl;
	}

	// ウマ娘一覧を取得する。
	public List<UmamusumeBean> getList() {
		final List<UmamusumeBean> ubl = getList(false);
		ubl.addAll(ubl.size(), getList(true));
		return ubl;
	}

	// 勝負服を得ていないウマ娘一覧を取得する。
	public List<UmamusumeBean> getListWhereRacingUmamusumeNoIsNull(final boolean isExclusive) {
		final List<UmamusumeBean> ubl = new ArrayList<>();
		final ResultSet rs = selectWhereRacingUmamusumeNoIsNull(isExclusive);

		try {
			while (rs.next()) {
				ubl.add(UmamusumeBean.create(rs.getInt("noA"),
											 rs.getString("name"),
											 rs.getString("parameter"),
											 rs.getInt("noB")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ubl;
	}

	// 名前もしくは名前の一部でウマ娘一覧の中から検索する。
	public List<UmamusumeBean> getUmamusume(final String name) {
		final PreparedStatement ps = searchByName();
		final List<UmamusumeBean> ubl = new ArrayList<>();

		try {
			ps.setString(1, "%" + name + "%");

			final ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ubl.add(UmamusumeBean.create(rs.getInt("noA"),
											 rs.getString("name"),
											 rs.getString("parameter"),
											 rs.getInt("noB")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ubl;
	}

	// 図鑑番号(mode=true)もしくは勝負服番号(mode=false)でウマ娘一覧の中から検索する。
	public List<UmamusumeBean> getUmamusume(boolean mode, final int no) {
		final PreparedStatement ps = searchByNo(mode);
		final List<UmamusumeBean> ubl = new ArrayList<>();

		try {
			ps.setInt(1, no);

			final ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ubl.add(UmamusumeBean.create(rs.getInt("noA"),
											 rs.getString("name"),
											 rs.getString("parameter"),
											 rs.getInt("noB")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ubl;
	}

	// 新たなウマ娘を登録する。
	public void setUmamusume(final boolean isExclusive, final String name, final String parameter) {
		setUmamusume(isExclusive, 0, name, parameter);
	}

	public void setUmamusume(final boolean isExclusive, final int no, final String name, final String parameter) {
		final PreparedStatement ps = insert(isExclusive);

		try {
			if (isExclusive) {
				// 特殊なウマ娘を新規登録する場合、特殊な図鑑番号を添える。
				ps.setInt(1, no);
				ps.setString(2, name.equals("") ? null : name);
				ps.setString(3, parameter.equals("") ? null : parameter);
			} else {
				ps.setString(1, name.equals("") ? null : name);
				ps.setString(2, parameter.equals("") ? null : parameter);
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	// 図鑑番号からすでに登録してあるウマ娘を特定し、名前もしくはパラメーターの変更を行う。
	public void updateName(final boolean isExclusive, final int no, final String newName, final String newParameter) {
		final PreparedStatement ps = update(isExclusive);

		try {
			ps.setString(1, newName);
			ps.setString(2, newParameter);
			ps.setInt(3, no);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 何らかの事情により登場できなくなったウマ娘を図鑑番号から特定し、一覧から削除する。
	public void deleteUmamusume(final boolean isExclusive, final int no) {
		final PreparedStatement ps = delete(isExclusive);

		try {
			ps.setInt(1, no);
			ps.executeUpdate();
			c.prepareStatement("SELECT after_delete_a()").executeUpdate();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
		}
	}

	// トレセン学園関係者であるウマ娘、没ウマ娘を除いたウマ娘の総数を返す。
	public int noMax() {
		try {
			final ResultSet rs = c.prepareStatement("SELECT COUNT(*) AS noMax FROM Umamusume").executeQuery();
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
