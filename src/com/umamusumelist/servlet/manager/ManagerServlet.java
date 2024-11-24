package com.umamusumelist.servlet.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 管理者専用ページへのログインに関する処理を行うサーブレット
 *
 * @author Umamusumelist.com
 * @version 5.1
 */
@WebServlet(name = "/Manager/")
public final class ManagerServlet extends HttpServlet {

	/**
	 * 新規インスタンス作成時のコンストラクター
	 *
	 * @see HttpServlet#HttpServlet()
	 */
	public ManagerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * URLで直接管理者画面へアクセスしようとした際に実行<br>
	 * 現在のセッションが無ければ、ログイン画面を表示し、セッションがあれば管理者画面を表示
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);

		// 現在のセッションが無ければ、ログイン画面を表示し、セッションがあれば管理者画面を表示する。
		if (session == null) {
			request.getRequestDispatcher("../WEB-INF/login/Login.html").forward(request, response);
			return;
		}

		request.getRequestDispatcher("../WEB-INF/manager/Manager.html").forward(request, response);
	}

	/**
	 * トップページの「管理者専用ページ」ボタンで管理者画面へアクセスしようとした際に実行<br>
	 * 現在のセッションが無ければ、ログイン画面を表示し、セッションがあれば管理者画面を表示<br>
	 * ログイン画面で入力されたパスワードが正しければ、管理者画面を表示<br>
	 * ログインするためのパスワードや入力したパスワードはフロントエンドにてSHA-512で暗号化
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		final String password = request.getParameter("password");

		// 現在のセッションが無ければ、ログイン画面を表示し、セッションがあれば管理者画面を表示する。
		// ログインするためのパスワードや入力したパスワードはフロントエンドにてSHA-512で暗号化される。
		if (session == null && password == null) {
			request.getRequestDispatcher("../WEB-INF/login/Login.html").forward(request, response);
		} else if (session == null && password != null) {
			if (!password.equals("36034a9cae9c6dad0ca7fc15ad92f0fb8b109be53168ec903bca430ccd1c61885efe3af20a6e49d27d5434ff7e363463f9cbca0db742c850b6b32477b88389de")) {
				request.getRequestDispatcher("../WEB-INF/login/PasswordIsIncorrect.html").forward(request, response);
				return;
			}

			// パスワードが正しければ、管理者画面を表示する。
			session = request.getSession(true);
			request.getRequestDispatcher("../WEB-INF/manager/Manager.html").forward(request, response);
		} else if (session != null) {
			request.getRequestDispatcher("../WEB-INF/manager/Manager.html").forward(request, response);
		} else {
			request.getRequestDispatcher("../WEB-INF/login/Login.html").forward(request, response);
		}
	}

}
