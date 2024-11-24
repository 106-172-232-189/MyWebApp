package com.umamusumelist.servlet.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.umamusumelist.bean.UmamusumeBean;
import com.umamusumelist.dao.UmamusumeDAO;

/**
 * トレセン学園関係者であるウマ娘の登録・削除処理を行うサーブレット
 *
 * @author Umamusumelist.com
 * @version 5.0
 *
 */
@WebServlet(name = "Manager/SetOrDeleteUmamusumeExclusive")
public class SetOrDeleteUmamusumeExclusiveServlet extends HttpServlet {

	/**
	 * 新規インスタンス作成時のコンストラクター
	 *
	 * @see HttpServlet#HttpServlet()
	 */
	public SetOrDeleteUmamusumeExclusiveServlet() {
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
			HttpSession session = request.getSession(false);

			// 現在のセッションが無ければ、セッション有効期限切れ画面を表示する。
			if (session == null) {
				request.getRequestDispatcher("../WEB-INF/login/SessionTimeout.html").forward(request, response);
				return;
			}

			final UmamusumeDAO udao = new UmamusumeDAO();
			request.setAttribute("umamusumeList", udao.getListWhereRacingUmamusumeNoIsNull(true)); // 勝負服を得ていない特殊なウマ娘
			request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteUmamusumeExclusive.jsp").forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 管理者画面の「登録/削除(図鑑番号(800番台、900番台))」ボタンで登録・削除画面へアクセスしようとした際に実行<br>
	 * 現在のセッションが無ければ、セッション有効期限切れ画面を表示し、セッションがあれば登録・削除画面を表示
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		final UmamusumeDAO udao = new UmamusumeDAO();

		try {
			request.setCharacterEncoding("UTF-8");
			HttpSession session = request.getSession(false);

			// 現在のセッションが無ければ、セッション有効期限切れ画面を表示する。
			if (session == null) {
				request.getRequestDispatcher("../WEB-INF/login/SessionTimeout.html").forward(request, response);
				return;
			}

			 // 名前
			final String name = request.getParameter("name") == null ? "" : request.getParameter("name");
			// 特殊な図鑑番号(追加)
			final int umadexNo = request.getParameter("no") == null || request.getParameter("no").equals("") ? 0
					: Integer.parseInt(request.getParameter("no"));
			// パラメーター
			final String parameter = request.getParameter("parameter") == null ? "" : request.getParameter("parameter");
			// 追加ボタンもしくは削除ボタン
			final String button = request.getParameter("button");
			// 特殊な図鑑番号(削除)
			final int target = request.getParameter("target") == null || request.getParameter("target").equals("") ? 0
					: Integer.parseInt(request.getParameter("target"));
			forward(request, response, name, umadexNo, parameter, button, target, udao,
					udao.getListWhereRacingUmamusumeNoIsNull(true));
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "エラーが発生しました");
			request.setAttribute("umamusumeList", udao.getListWhereRacingUmamusumeNoIsNull(true)); // 勝負服を得ていない特殊なウマ娘
			try {
				request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteUmamusumeExclusive.jsp").forward(request,
						response);
			} catch (ServletException | IOException e2) {
				// TODO 自動生成された catch ブロック
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 登録が行われる条件:<br>
	 * &nbsp;&nbsp;①名前、特殊な図鑑番号が両方とも入力されている<br>
	 * &nbsp;&nbsp;②名前、特殊な図鑑番号、ウマ娘公式ポータルサイトにおける識別子のいずれも既存のデータと重複していない<br>
	 * <br>
	 * 削除が行われる条件: 削除対象が選択されている
	 *
	 * @param name 名前
	 * @param umadexNo 特殊な図鑑番号
	 * @param parameter ウマ娘公式ポータルサイトにおける識別子
	 * @param button ボタンの種類
	 * @param target 削除対象の図鑑番号
	 * @throws ServletException
	 * @throws IOException
	 */
	private void forward(HttpServletRequest request, HttpServletResponse response, String name, int umadexNo,
			String parameter, String button, int target, UmamusumeDAO udao, List<UmamusumeBean> ubl)
			throws ServletException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		// 追加ボタン
		if (button != null && button.equals("add")) {
			// 名前もしくは特殊な図鑑番号が入力されていない
			if (name.equals("") || umadexNo == 0) {
				request.setAttribute("message", "名前と図鑑番号を入力してください");
				request.setAttribute("umamusumeList", ubl);
				request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteUmamusumeExclusive.jsp").forward(request, response);
				return;
			}

			// 入力した名前、図鑑番号、パラメーターのいずれかが既存のデータと重複している
			if (isFound(udao.getList(), name, umadexNo, parameter)) {
				request.setAttribute("message", "すでに登録している名前、図鑑番号、パラメーターです");
				request.setAttribute("umamusumeList", ubl);
				request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteUmamusumeExclusive.jsp").forward(request,
						response);
				return;
			}

			// 正常に登録される
			udao.setUmamusume(true, umadexNo, name, parameter);
			request.setAttribute("message", "登録しました");
			request.setAttribute("umamusumeList", udao.getListWhereRacingUmamusumeNoIsNull(true));
			request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteUmamusumeExclusive.jsp").forward(request,
					response);
			return;
		// 削除ボタン
		} else if (button != null && button.equals("delete")) {
			// 削除対象が選択されていない
			if (target == 0) {
				request.setAttribute("message", "削除対象を選択してください");
				request.setAttribute("umamusumeList", ubl);
				request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteUmamusumeExclusive.jsp").forward(request,
						response);
				return;
			}

			// 正常に削除される
			udao.deleteUmamusume(true, target);
			request.setAttribute("message", "削除しました");
			request.setAttribute("umamusumeList", udao.getListWhereRacingUmamusumeNoIsNull(true));
			request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteUmamusumeExclusive.jsp").forward(request,
					response);
			return;
		} else {
			request.setAttribute("umamusumeList", udao.getListWhereRacingUmamusumeNoIsNull(true));
			request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteUmamusumeExclusive.jsp").forward(request,
					response);
		}
	}

	/**
	 * 入力した名前、特殊な図鑑番号、ウマ娘公式ポータルサイトにおける識別子が既存のデータと重複しているかの判定
	 *
	 * @param name 名前
	 * @param umadexNo 特殊な図鑑番号
	 * @param parameter ウマ娘公式ポータルサイトにおける識別子
	 * @return 判定結果
	 */
	private boolean isFound(List<UmamusumeBean> ubl, String name, int umadexNo, String parameter) {
		for (UmamusumeBean u : ubl) {
			if (name.equals(u.name().substring(0, u.name().indexOf("?") != -1 ? u.name().indexOf("?") : u.name().length()))) {
				return true;
			}

			if (umadexNo == u.umadexNo()) {
				return true;
			}

			if (parameter.equals("")) {
				continue;
			}

			if (parameter.equals(u.parameter())) {
				return true;
			}
		}

		return false;
	}

}
