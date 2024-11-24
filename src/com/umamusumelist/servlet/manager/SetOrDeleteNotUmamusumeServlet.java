package com.umamusumelist.servlet.manager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.umamusumelist.bean.NotUmamusumeBean;
import com.umamusumelist.dao.NotUmamusumeDAO;

/**
 * ウマ娘でないトレセン学園関係者の登録・削除処理を行うサーブレット
 *
 * @author Umamusumelist.com
 * @version 5.1
 *
 */
@WebServlet(name = "Manager/SetOrDeleteNotUmamusume")
public final class SetOrDeleteNotUmamusumeServlet extends HttpServlet {

	/**
	 * 新規インスタンス作成時のコンストラクター
	 *
	 * @see HttpServlet#HttpServlet()
	 */
	public SetOrDeleteNotUmamusumeServlet() {
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

			request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteNotUmamusume.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 管理者画面の「登録/削除(ｳﾏ娘でないﾄﾚｾﾝ学園関係者)」ボタンで登録・削除画面へアクセスしようとした際に実行<br>
	 * 現在のセッションが無ければ、セッション有効期限切れ画面を表示し、セッションがあれば登録・削除画面を表示
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("UTF-8");

			final NotUmamusumeDAO nudao = new NotUmamusumeDAO();

			// 現在のセッションが無ければ、セッション有効期限切れ画面を表示する。
			if (request.getSession(false) == null) {
				request.getRequestDispatcher("../WEB-INF/login/SessionTimeout.html").forward(request, response);
				return;
			}

			// 名前
			final String name = request.getParameter("name") == null ? "" : request.getParameter("name");
			// パラメーター
			final String parameter = request.getParameter("parameter") == null ? "" : request.getParameter("parameter");
			// 追加ボタンもしくは変更ボタンもしくは削除ボタン
			final String button = request.getParameter("button");
			// 変更元
			final String target = request.getParameter("target") == null ? "" : request.getParameter("target");
			forward(request, response, name, parameter, button, target, nudao);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 登録が行われる条件:<br>
	 * &nbsp;&nbsp;①名前、ウマ娘公式ポータルサイトにおける識別子が両方とも入力されている<br>
	 * &nbsp;&nbsp;②名前、ウマ娘公式ポータルサイトにおける識別子のいずれも既存のデータと重複していない<br>
	 * <br>
	 * 変更が行われる条件:<br>
	 * &nbsp;&nbsp;①変更対象の名前、変更後の名前、ウマ娘公式ポータルサイトにおける識別子が全て入力されている<br>
	 * &nbsp;&nbsp;②変更後の名前、ウマ娘公式ポータルサイトにおける識別子のいずれも既存のデータと重複していない<br>
	 * <br>
	 * 削除が行われる条件: 削除対象が選択されている
	 *
	 * @param name
	 *            名前
	 * @param parameter
	 *            ウマ娘公式ポータルサイトにおける識別子
	 * @param button
	 *            ボタンの種類
	 * @param target
	 *            変更前の名前
	 */
	private void forward(final HttpServletRequest request, final HttpServletResponse response, final String name,
			final String parameter, final String button, final String target, final NotUmamusumeDAO nudao)
			throws ServletException, SQLException, IOException {
		// TODO 自動生成されたメソッド・スタブ
		if (button != null && button.equals("add")) { // 追加ボタン
			if (name.equals("") || parameter.equals("")) { // 名前もしくはパラメーターが入力されていない
				request.setAttribute("message", "名前とパラメーターを入力してください");
			} else if (isFound(nudao.getList(), name, parameter)) { // 入力した名前、パラメーターのいずれかが既存のデータと重複している
				request.setAttribute("message", "すでに登録している名前もしくはパラメーターです");
			} else { // 正常に登録される
				nudao.setNotUmamusume(name, parameter);
				request.setAttribute("message", "登録しました");
			}
		} else if (button != null && button.equals("update")) { // 変更ボタン
			if (target.equals("") || name.equals("") || parameter.equals("")) { // 変更対象が選択されていないか、変更後の名前もしくはパラメーターが入力されていない
				request.setAttribute("message", "変更対象を選択し、変更後の名前とパラメーターを入力してください");
			} else if (isFound(nudao.getList(), name, parameter)) { // 入力した名前、パラメーターのいずれかが既存のデータと重複している
				request.setAttribute("message", "すでに登録している名前もしくはパラメーターです");
			} else { // 正常に変更される
				nudao.updateName(target, name, parameter);
				request.setAttribute("message", "変更しました");
			}
		} else if (button != null && button.equals("delete")) { // 削除ボタン
			if (target.equals("")) { // 削除対象が選択されていない
				request.setAttribute("message", "削除対象を選択してください");
			} else { // 正常に削除される
				nudao.deleteNotUmamusume(target);
				request.setAttribute("message", "削除しました");
			}
		}

		request.setAttribute("notUmamusumeList", nudao.getList());
		request.getRequestDispatcher("../WEB-INF/manager/SetOrDeleteNotUmamusume.jsp").forward(request, response);
	}

	/**
	 * 入力した名前、ウマ娘公式ポータルサイトにおける識別子が既存のデータと重複しているかの判定
	 *
	 * @param name
	 *            名前
	 * @param parameter
	 *            ウマ娘公式ポータルサイトにおける識別子
	 * @return 判定結果
	 */
	private boolean isFound(final List<NotUmamusumeBean> nub, final String name, final String parameter) {
		for (NotUmamusumeBean nu : nub) {
			if (name.equals("") || parameter.equals("")) {
				continue;
			}

			if (name.equals(nu.name().substring(0,
					nu.name().indexOf("?") != -1 ? nu.name().indexOf("?") : nu.name().length()))) {
				return true;
			}

			if (parameter.equals(nu.parameter())) {
				return true;
			}
		}

		return false;
	}

}
