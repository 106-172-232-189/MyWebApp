package servlet.manager;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.UmamusumeBean;
import dao.UmamusumeDAO;

@WebServlet(name = "Manager/SetOrDeleteUmamusumeExclusive")
public class SetOrDeleteUmamusumeExclusiveServlet extends HttpServlet {

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SetOrDeleteUmamusumeExclusiveServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("UTF-8");
			HttpSession session = request.getSession(false);

			// 現在のセッションが無ければ、認証失敗画面を表示する。
			if (session == null) {
				request.getRequestDispatcher("../error/401_Unauthorized.html").forward(request, response);
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		final UmamusumeDAO udao = new UmamusumeDAO();

		try {
			request.setCharacterEncoding("UTF-8");
			HttpSession session = request.getSession(false);

			// 現在のセッションが無ければ、認証失敗画面を表示する。
			if (session == null) {
				request.getRequestDispatcher("../error/401_Unauthorized.html").forward(request, response);
				return;
			}

			 // 名前
			final String name = request.getParameter("name") == null ? "" : request.getParameter("name");
			// 特殊な図鑑番号(追加)
			final int no = request.getParameter("no") == null || request.getParameter("no").equals("") ? 0
					: Integer.parseInt(request.getParameter("no"));
			// パラメーター
			final String parameter = request.getParameter("parameter");
			// 追加ボタンもしくは削除ボタン
			final String button = request.getParameter("button");
			// 特殊な図鑑番号(削除)
			final int target = request.getParameter("target") == null || request.getParameter("target").equals("") ? 0
					: Integer.parseInt(request.getParameter("target"));
			forward(request, response, name, no, parameter, button, target, udao,
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

	private void forward(HttpServletRequest request, HttpServletResponse response, String name, int no,
			String parameter, String button, int target, UmamusumeDAO udao, List<UmamusumeBean> ubl)
			throws ServletException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		// 追加ボタン
		if (button != null && button.equals("add")) {
			// 通常はこのif文の中身を処理しません
			if (parameter == null) {
				request.setAttribute("message", "パラメーターを入力してください");
				request.setAttribute("umamusumeList", ubl);
				request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteUmamusumeExclusive.jsp").forward(request,
						response);
				return;
			}

			// 特殊な図鑑番号が入力されていない
			if (no == 0) {
				request.setAttribute("message", "図鑑番号を入力してください");
				request.setAttribute("umamusumeList", ubl);
				request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteUmamusumeExclusive.jsp").forward(request,
						response);
				return;
			}

			// 入力した名前、図鑑番号、パラメーターのいずれかが既存のデータと重複している
			if (isFound(udao.getList(), name, no, parameter)) {
				request.setAttribute("message", "すでに登録している名前、図鑑番号、パラメーターです");
				request.setAttribute("umamusumeList", ubl);
				request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteUmamusumeExclusive.jsp").forward(request,
						response);
				return;
			}

			// 正常に登録される
			udao.setUmamusume(true, no, name, parameter);
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

	// 入力した名前、図鑑番号、パラメーターが既存のデータと重複していないかを判定する
	private boolean isFound(List<UmamusumeBean> ubl, String name, int no, String parameter) {
		for (UmamusumeBean u : ubl) {
			if (no == u.noA()) {
				return true;
			}

			if (u.name() == null || name.equals("") || parameter.equals("")) {
				continue;
			}

			if (name.equals(
					u.name().substring(0, u.name().indexOf("?") != -1 ? u.name().indexOf("?") : u.name().length()))
					|| parameter.equals(u.parameter())) {
				return true;
			}
		}

		return false;
	}

}
