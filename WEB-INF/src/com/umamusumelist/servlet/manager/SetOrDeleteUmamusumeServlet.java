package com.umamusumelist.servlet.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.umamusumelist.bean.UmamusumeBean;
import com.umamusumelist.dao.UmamusumeDAO;

/**
 * ウマ娘の登録・削除処理を行うサーブレット
 *
 * @author Umamusumelist.com
 * @version 5.5
 *
 */
@WebServlet(name = "Manager/SetOrDeleteUmamusume")
public final class SetOrDeleteUmamusumeServlet extends HttpServlet {

	/**
	 * 新規インスタンス作成時のコンストラクター
	 *
	 * @see HttpServlet#HttpServlet()
	 */
	public SetOrDeleteUmamusumeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * URLで直接登録・削除画面へアクセスしようとした際に実行<br>
	 * 現在のセッションが無ければ、セッション有効期限切れ画面を表示し、セッションがあれば登録・削除画面を表示
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * @throws IOException エラーページの表示処理に失敗
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("UTF-8");

			// 現在のセッションが無ければ、セッション有効期限切れ画面を表示する。
			if (request.getSession(false) == null) {
				request.getRequestDispatcher("../WEB-INF/login/SessionTimeout.html").forward(request, response);
				return;
			}

			final UmamusumeDAO udao = new UmamusumeDAO();
			request.setAttribute("umamusumeList", udao.getListWhereRacingUmamusumeNoIsNull(false)); // 勝負服を得ていない通常のウマ娘
			request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteUmamusume.jsp").forward(request, response);
			udao.close();
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 管理者画面の「登録/削除(図鑑番号)」ボタンで登録・削除画面へアクセスしようとした際に実行<br>
	 * 現在のセッションが無ければ、セッション有効期限切れ画面を表示し、セッションがあれば登録・削除画面を表示
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * @throws IOException エラーページの表示処理に失敗
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("UTF-8");

			// 現在のセッションが無ければ、セッション有効期限切れ画面を表示する。
			if (request.getSession(false) == null) {
				request.getRequestDispatcher("../WEB-INF/login/SessionTimeout.html").forward(request, response);
				return;
			}

			final UmamusumeDAO udao = new UmamusumeDAO();
			// 名前
			final String name = request.getParameter("name") == null ? "" : request.getParameter("name");
			// 図鑑番号(登録)
			final int umadexNo = request.getParameter("umadexNo") == null || request.getParameter("umadexNo").equals("") ? 0
					: Integer.parseInt(request.getParameter("umadexNo"));
			// パラメーター
			final String parameter = request.getParameter("parameter") == null ? "" : request.getParameter("parameter");
			// 追加ボタンもしくは変更ボタンもしくは削除ボタン
			final String button = request.getParameter("button");
			// 図鑑番号(変更もしくは削除)
			final int target = request.getParameter("target") == null || request.getParameter("target").equals("") ? 0
					: Integer.parseInt(request.getParameter("target"));
			forward(request, response, name, umadexNo, parameter, button, target, udao);
			udao.close();
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 登録が行われる条件:<br>
	 * &nbsp;&nbsp;①名前、ウマ娘公式ポータルサイトにおける識別子が両方とも入力されている<br>
	 * &nbsp;&nbsp;②名前、ウマ娘公式ポータルサイトにおける識別子のいずれも既存のデータと重複していない<br>
	 * <br>
	 * 変更が行われる条件:<br>
	 * &nbsp;&nbsp;①変更対象の図鑑番号、変更後の名前、ウマ娘公式ポータルサイトにおける識別子が全て入力されている<br>
	 * &nbsp;&nbsp;②変更後の名前、ウマ娘公式ポータルサイトにおける識別子のいずれも既存のデータと重複していない<br>
	 * <br>
	 * 削除が行われる条件: 削除対象が選択されている
	 *
	 * @param name
	 *            名前
	 * @param umadexNo 登録時に割り当てる図鑑番号
	 * @param parameter
	 *            ウマ娘公式ポータルサイトにおける識別子
	 * @param button
	 *            ボタンの種類
	 * @param target
	 *            変更対象もしくは削除対象の図鑑番号
	 * @throws ServletException ページのフォワード処理に失敗①
	 * @throws IOException ページのフォワード処理に失敗②
	 * @throws SQLException データベースに関する処理時に何らかの異常が発生
	 */
	private void forward(final HttpServletRequest request, final HttpServletResponse response, final String name,
			final int umadexNo, final String parameter, final String button, final int target, final UmamusumeDAO udao)
			throws ServletException, IOException, SQLException {
		// TODO 自動生成されたメソッド・スタブ
		if (button != null && button.equals("add")) { // 追加ボタン
			if (name.equals("") || umadexNo == 0 || parameter.equals("")) { // 名前、図鑑番号、もしくはパラメーターが入力されていない
				request.setAttribute("message", "Please enter the name, Umadex No. and parameter");
			} else if (isFound(udao.getList(), name, parameter, umadexNo)) { // 入力した名前、図鑑番号、パラメーターのいずれかが既存のデータと重複している
				request.setAttribute("message", "This name, Umadex No. or parameter is already registered");
			} else { // 正常に登録される
				udao.setUmamusume(false, umadexNo, name, parameter);
				request.setAttribute("message", "Registered successfully");
			}
		} else if (button != null && button.equals("update")) { // 変更ボタン
			// 変更対象が選択されていないか、変更後の名前もしくはパラメーターが入力されていない
			if (target == 0 || name.equals("") || parameter.equals("")) {
				request.setAttribute("message", "Please Select the target and enter the updated name and parameter");
			} else if (isFound(udao.getList(), name, parameter, 0)) { // 入力した名前、パラメーターのいずれかが既存のデータと重複している
				request.setAttribute("message", "This name or parameter is already registered");
			} else { // 正常に変更される
				udao.updateName(false, target, name, parameter);
				request.setAttribute("message", "Updated successfully");
			}
		} else if (button != null && button.equals("delete")) { // 削除ボタン
			if (target == 0) { // 削除対象が選択されていない
				request.setAttribute("message", "Please Select the target");
			} else { // 正常に削除される
				udao.deleteUmamusume(false, target);
				request.setAttribute("message", "Deleted successfully");
			}
		}

		request.setAttribute("umamusumeList", udao.getListWhereRacingUmamusumeNoIsNull(false));
		request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteUmamusume.jsp").forward(request, response);
	}

	/**
	 * 入力した名前、ウマ娘公式ポータルサイトにおける識別子が既存のデータと重複しているかの判定
	 *
	 * @param name
	 *            名前
	 * @param parameter
	 *            ウマ娘公式ポータルサイトにおける識別子
	 * @param umadexNo 図鑑番号
	 * @return 判定結果
	 */
	private boolean isFound(final List<UmamusumeBean> ubl, final String name, final String parameter, final int umadexNo) {
		for (UmamusumeBean u : ubl) {
			if (name.equals("") || parameter.equals("")) {
				continue;
			}

			if (name.equals(
					u.name().substring(0, u.name().indexOf("?") != -1 ? u.name().indexOf("?") : u.name().length()))) {
				return true;
			}

			if (umadexNo == u.umadexNo()) {
				return true;
			}

			if (parameter.equals(u.parameter())) {
				return true;
			}
		}

		return false;
	}

}
