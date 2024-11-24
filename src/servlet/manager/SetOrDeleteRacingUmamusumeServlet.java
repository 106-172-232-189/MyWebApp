package servlet.manager;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.RacingUmamusumeBean;
import bean.UmamusumeBean;
import dao.RacingUmamusumeDAO;
import dao.UmamusumeDAO;

@WebServlet(name = "Manager/SetOrDeleteRacingUmamusume")
public class SetOrDeleteRacingUmamusumeServlet extends HttpServlet {

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SetOrDeleteRacingUmamusumeServlet() {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		final UmamusumeDAO udao = new UmamusumeDAO();
		final RacingUmamusumeDAO rudao = new RacingUmamusumeDAO();
		final List<UmamusumeBean> ublNex = udao.getListWhereRacingUmamusumeNoIsNull(false); // 勝負服を得ていない通常のウマ娘
		final List<UmamusumeBean> ublEx = udao.getListWhereRacingUmamusumeNoIsNull(true); // 勝負服を得ていない特殊なウマ娘

		try {
			request.setCharacterEncoding("UTF-8");
			HttpSession session = request.getSession(false);

			// 現在のセッションが無ければ、認証失敗画面を表示する。
			if (session == null) {
				request.getRequestDispatcher("../error/401_Unauthorized.html").forward(request, response);
				return;
			}

			// 特殊な勝負服番号(追加)
			final int no = request.getParameter("no") == null || request.getParameter("no").equals("") ? 0
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
			forward(request, response, no, button, isExclusive, target, target2, target3, target4, appeared, udao,
					ublNex, ublEx, rudao);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "エラーが発生しました");
			request.setAttribute("umamusumeListNotExclusive", ublNex);
			request.setAttribute("umamusumeListExclusive", ublEx);
			request.setAttribute("racingUmamusumeListNotExclusive", rudao.getList(false)); // 勝負服を得ている通常のウマ娘
			request.setAttribute("racingUmamusumeListExclusive", rudao.getList(true)); // 勝負服を得ている特殊なウマ娘
			try {
				request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteRacingUmamusume.jsp").forward(request,
						response);
			} catch (ServletException | IOException e2) {
				// TODO 自動生成された catch ブロック
				e2.printStackTrace();
			}
		}
	}

	private void forward(HttpServletRequest request, HttpServletResponse response, int no, String button,
			boolean isExclusive, int target, int target2, int target3, int target4, Date appeared, UmamusumeDAO udao,
			List<UmamusumeBean> ublNex, List<UmamusumeBean> ublEx, RacingUmamusumeDAO rudao)
			throws ServletException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		// 追加ボタン
		if (button != null && button.equals("add")) {
			// 勝負服を得た特殊なウマ娘を新たに追加する際、勝負服番号が入力されていない
			if (isExclusive && no == 0) {
				request.setAttribute("message", "勝負服番号を入力してください");
				request.setAttribute("umamusumeListNotExclusive", ublNex);
				request.setAttribute("umamusumeListExclusive", ublEx);
				request.setAttribute("racingUmamusumeListNotExclusive", rudao.getList(false));
				request.setAttribute("racingUmamusumeListExclusive", rudao.getList(true));
				request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteRacingUmamusume.jsp").forward(request,
						response);
				return;
			}

			// 登録対象が選択されていない
			if ((isExclusive ? target2 : target) == 0) {
				request.setAttribute("message", "登録対象を選択してください");
				request.setAttribute("umamusumeListNotExclusive", ublNex);
				request.setAttribute("umamusumeListExclusive", ublEx);
				request.setAttribute("racingUmamusumeListNotExclusive", rudao.getList(false));
				request.setAttribute("racingUmamusumeListExclusive", rudao.getList(true));
				request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteRacingUmamusume.jsp").forward(request,
						response);
				return;
			}

			if (isFound(rudao.getList(true), no)) {
				request.setAttribute("message", "既に登録してある勝負服番号です");
				request.setAttribute("umamusumeListNotExclusive", ublNex);
				request.setAttribute("umamusumeListExclusive", ublEx);
				request.setAttribute("racingUmamusumeListNotExclusive", rudao.getList(false));
				request.setAttribute("racingUmamusumeListExclusive", rudao.getList(true));
				request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteRacingUmamusume.jsp").forward(request,
						response);
				return;
			}

			// 正常に登録される
			rudao.setRacingUmamusume(isExclusive, no, isExclusive ? target2 : target, isExclusive ? null : appeared);
			request.setAttribute("message", "登録しました");
			request.setAttribute("umamusumeListNotExclusive", udao.getListWhereRacingUmamusumeNoIsNull(false));
			request.setAttribute("umamusumeListExclusive", udao.getListWhereRacingUmamusumeNoIsNull(true));
			request.setAttribute("racingUmamusumeListNotExclusive", rudao.getList(false));
			request.setAttribute("racingUmamusumeListExclusive", rudao.getList(true));
			request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteRacingUmamusume.jsp").forward(request,
					response);
			return;
		// 削除ボタン
		} else if (button != null && button.equals("delete")) {
			// 削除対象が選択されていない
			if ((isExclusive ? target4 : target3) == 0) {
				request.setAttribute("message", "削除対象を選択してください");
				request.setAttribute("umamusumeListNotExclusive", ublNex);
				request.setAttribute("umamusumeListExclusive", ublEx);
				request.setAttribute("racingUmamusumeListNotExclusive", rudao.getList(false));
				request.setAttribute("racingUmamusumeListExclusive", rudao.getList(true));
				request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteRacingUmamusume.jsp").forward(request,
						response);
				return;
			}

			// 正常に削除される
			rudao.deleteRacingUmamusume(isExclusive, isExclusive ? target4 : target3);
			request.setAttribute("message", "削除しました");
			request.setAttribute("umamusumeListNotExclusive", udao.getListWhereRacingUmamusumeNoIsNull(false));
			request.setAttribute("umamusumeListExclusive", udao.getListWhereRacingUmamusumeNoIsNull(true));
			request.setAttribute("racingUmamusumeListNotExclusive", rudao.getList(false));
			request.setAttribute("racingUmamusumeListExclusive", rudao.getList(true));
			request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteRacingUmamusume.jsp").forward(request,
					response);
			return;
		} else {
			request.setAttribute("umamusumeListNotExclusive", udao.getListWhereRacingUmamusumeNoIsNull(false));
			request.setAttribute("umamusumeListExclusive", udao.getListWhereRacingUmamusumeNoIsNull(true));
			request.setAttribute("racingUmamusumeListNotExclusive", rudao.getList(false));
			request.setAttribute("racingUmamusumeListExclusive", rudao.getList(true));
			request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteRacingUmamusume.jsp").forward(request,
					response);
		}
	}

	private boolean isFound(List<RacingUmamusumeBean> rubl, int no) {
		// TODO 自動生成されたメソッド・スタブ
		for (RacingUmamusumeBean rub : rubl) {
			if (no == rub.no()) {
				return true;
			}
		}

		return false;
	}

}
