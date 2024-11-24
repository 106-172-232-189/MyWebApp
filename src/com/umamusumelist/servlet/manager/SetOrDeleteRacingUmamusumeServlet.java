package com.umamusumelist.servlet.manager;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.umamusumelist.bean.RacingUmamusumeBean;
import com.umamusumelist.dao.RacingUmamusumeDAO;
import com.umamusumelist.dao.UmamusumeDAO;

/**
 * 勝負服を得たウマ娘の登録・削除処理を行うサーブレット
 *
 * @author Umamusumelist.com
 * @version 5.1
 *
 */
@WebServlet(name = "Manager/SetOrDeleteRacingUmamusume")
public final class SetOrDeleteRacingUmamusumeServlet extends HttpServlet {

	/**
	 * 新規インスタンス作成時のコンストラクター
	 *
	 * @see HttpServlet#HttpServlet()
	 */
	public SetOrDeleteRacingUmamusumeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * URLで直接登録・削除画面へアクセスしようとした際に実行<br>
	 * 現在のセッションが無ければ、セッション有効期限切れ画面を表示し、セッションがあれば登録・削除画面を表示
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("UTF-8");

			// 現在のセッションが無ければ、セッション有効期限切れ画面を表示する。
			if (request.getSession(false) == null) {
				request.getRequestDispatcher("../WEB-INF/login/SessionTimeout.html").forward(request, response);
				return;
			}

			final UmamusumeDAO udao = new UmamusumeDAO();
			final RacingUmamusumeDAO rudao = new RacingUmamusumeDAO();
			request.setAttribute("umamusumeListNotExclusive", udao.getListWhereRacingUmamusumeNoIsNull(false)); // 勝負服を得ていない通常のウマ娘
			request.setAttribute("umamusumeListExclusive", udao.getListWhereRacingUmamusumeNoIsNull(true)); // 勝負服を得ていない特殊なウマ娘
			request.setAttribute("racingUmamusumeListNotExclusive", rudao.getList(false)); // 勝負服を得ている通常のウマ娘
			request.setAttribute("racingUmamusumeListExclusive", rudao.getList(true)); // 勝負服を得ている特殊なウマ娘
			request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteRacingUmamusume.jsp").forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 管理者画面の「登録/削除(勝負服)」ボタンで登録・削除画面へアクセスしようとした際に実行<br>
	 * 現在のセッションが無ければ、セッション有効期限切れ画面を表示し、セッションがあれば登録・削除画面を表示
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("UTF-8");

			final UmamusumeDAO udao = new UmamusumeDAO();
			final RacingUmamusumeDAO rudao = new RacingUmamusumeDAO();

			// 現在のセッションが無ければ、有効期限切れ画面を表示する。
			if (request.getSession(false) == null) {
				request.getRequestDispatcher("../WEB-INF/login/SessionTimeout.html").forward(request, response);
				return;
			}

			// 特殊な勝負服番号(追加)
			final int racingSuitNo = request.getParameter("no") == null || request.getParameter("no").equals("") ? 0
					: Integer.parseInt(request.getParameter("no"));
			// 追加ボタンもしくは削除ボタン
			final String button = request.getParameter("button");
			// 通常のウマ娘か特殊なウマ娘か
			final boolean isExclusive = Boolean.parseBoolean(request.getParameter("type"));
			// 通常の図鑑番号
			final int target = request.getParameter("target") == null || request.getParameter("target").equals("") ? 0
					: Integer.parseInt(request.getParameter("target"));
			// 特殊な図鑑番号
			final int target2 = request.getParameter("target2") == null || request.getParameter("target2").equals("")
					? 0 : Integer.parseInt(request.getParameter("target2"));
			// 通常の勝負服番号
			final int target3 = request.getParameter("target3") == null || request.getParameter("target3").equals("")
					? 0 : Integer.parseInt(request.getParameter("target3"));
			// 特殊な勝負服番号(削除)
			final int target4 = request.getParameter("target4") == null || request.getParameter("target4").equals("")
					? 0 : Integer.parseInt(request.getParameter("target4"));
			// 勝負服登録日
			Date appeared;
			try {
				appeared = request.getParameter("appeared") == null ? Date.valueOf("2021-02-24")
						: Date.valueOf(request.getParameter("appeared"));
			} catch (IllegalArgumentException e) {
				appeared = Date.valueOf("2021-02-24");
			}
			forward(request, response, racingSuitNo, button, isExclusive, target, target2, target3, target4, appeared,
					udao, rudao);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 登録が行われる条件:<br>
	 * &nbsp;&nbsp;①登録対象が選択されている<br>
	 * &nbsp;&nbsp;②特殊なウマ娘の場合は、特殊な勝負服番号も入力されている<br>
	 * &nbsp;&nbsp;③特殊なウマ娘の場合は、入力した特殊な勝負服番号が既存のデータと重複していない<br>
	 * <br>
	 * 削除が行われる条件: 削除対象が選択されている
	 *
	 * @param racingSuitNo
	 *            特殊な勝負服番号
	 * @param button
	 *            ボタンの種類
	 * @param isExclusive
	 *            トレセン学園関係者であるか
	 * @param target
	 *            登録対象の図鑑番号
	 * @param target2
	 *            登録対象の図鑑番号(トレセン学園関係者)
	 * @param target3
	 *            削除対象の勝負服番号
	 * @param target4
	 *            削除対象の勝負服番号(トレセン学園関係者)
	 * @param appeared
	 *            ウマ娘公式ポータルサイトにて勝負服が登録された日時
	 */
	private void forward(final HttpServletRequest request, final HttpServletResponse response, final int racingSuitNo,
			final String button, final boolean isExclusive, final int target, final int target2, final int target3,
			final int target4, final Date appeared, final UmamusumeDAO udao, final RacingUmamusumeDAO rudao)
			throws ServletException, SQLException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		if (button != null && button.equals("add")) { // 追加ボタン
			if ((isExclusive ? target2 : target) == 0 || (isExclusive && racingSuitNo == 0)) { // 勝負服を得たウマ娘を新たに追加する際、登録対象が選択されていない、また、特殊なウマ娘の場合は、勝負服番号が入力されていない
				request.setAttribute("message", "登録対象を選択し、特殊なウマ娘の場合は勝負服番号を入力してください");
			} else if (isFound(rudao.getList(true), racingSuitNo)) { // 入力した特殊な勝負服番号が既存のデータと重複している
				request.setAttribute("message", "既に登録してある勝負服番号です");
			} else { // 正常に登録される
				rudao.setRacingUmamusume(isExclusive, racingSuitNo, isExclusive ? target2 : target,
						isExclusive ? null : appeared);
				request.setAttribute("message", "登録しました");
			}
		} else if (button != null && button.equals("delete")) { // 削除ボタン
			if ((isExclusive ? target4 : target3) == 0) { // 削除対象が選択されていない
				request.setAttribute("message", "削除対象を選択してください");
			} else { // 正常に削除される
				rudao.deleteRacingUmamusume(isExclusive, isExclusive ? target4 : target3);
				request.setAttribute("message", "削除しました");
			}
		}

		request.setAttribute("umamusumeListNotExclusive", udao.getListWhereRacingUmamusumeNoIsNull(false));
		request.setAttribute("umamusumeListExclusive", udao.getListWhereRacingUmamusumeNoIsNull(true));
		request.setAttribute("racingUmamusumeListNotExclusive", rudao.getList(false));
		request.setAttribute("racingUmamusumeListExclusive", rudao.getList(true));
		request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteRacingUmamusume.jsp").forward(request,
				response);
	}

	/**
	 * 入力した特殊な勝負服番号が既存のデータと重複しているかの判定
	 *
	 * @param racingSuitNo
	 *            特殊な勝負服番号
	 * @return 判定結果
	 */
	private boolean isFound(final List<RacingUmamusumeBean> rubl, final int racingSuitNo) {
		// TODO 自動生成されたメソッド・スタブ
		for (RacingUmamusumeBean rub : rubl) {
			if (racingSuitNo == rub.racingSuitNo()) {
				return true;
			}
		}

		return false;
	}

}
