package com.umamusumelist.servlet.manager;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.umamusumelist.dao.OTPDAO;
import com.umamusumelist.util.MailSender;
import com.umamusumelist.util.RandomString;

/**
 * 管理者専用ページへのログインに関する処理を行うサーブレット
 *
 * @author Umamusumelist.com
 * @version 8.0
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
	 * GETメソッドで「管理者専用ページ」へアクセスしようとした際に実行<br>
	 * 現在のセッションが無ければ、ログイン画面を表示し、セッションがあれば管理者画面を表示
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * @throws ServletException ページのフォワード処理に失敗①
	 * @throws IOException ページのフォワード処理に失敗②
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(false);

		// 現在のセッションが無ければ、ログイン画面を表示し、セッションがあれば管理者画面を表示する。
		if (session == null) {
			try (final OTPDAO otpdao = new OTPDAO()) {
				if (otpdao.isExist()) {
					request.getRequestDispatcher("../WEB-INF/login/Login.html").forward(request, response);
					return;
				} else {
					request.getRequestDispatcher("../WEB-INF/login/Confirm.html").forward(request, response);
					return;
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
				request.getRequestDispatcher("../WEB-INF/login/Confirm.html").forward(request, response);
				return;
			}
		} else {
			request.getRequestDispatcher("../WEB-INF/manager/Manager.html").forward(request, response);
			return;
		}
	}

	/**
	 * POSTメソッドで「管理者専用ページ」へアクセスしようとした際に実行<br>
	 * 現在のセッションが無ければ、ログイン画面を表示し、セッションがあれば管理者画面を表示<br>
	 * ログイン画面で入力されたパスワードが正しければ、管理者画面を表示<br>
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * @throws ServletException ページのフォワード処理に失敗①
	 * @throws IOException ページのフォワード処理に失敗②
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession(false);
		final String password = request.getParameter("password");

		if (session == null) {
			try (final OTPDAO otpdao = new OTPDAO()) {
				if (otpdao.isExist() && password == null) {
					request.getRequestDispatcher("../WEB-INF/login/Login.html").forward(request, response);
					return;
				} else if (otpdao.isExist() && password != null && password.equals(otpdao.getOTP().get(0).otp())) {
					otpdao.delete();
					session = request.getSession(true);
					request.getRequestDispatcher("../WEB-INF/manager/Manager.html").forward(request, response);
					return;
				} else if (otpdao.isExist() && password != null && !password.equals(otpdao.getOTP().get(0).otp())) {
					request.getRequestDispatcher("../WEB-INF/login/PasswordIsIncorrect.html").forward(request, response);
					return;
				} else {
					otpdao.delete();
					String otp = RandomString.generate(16);
					otpdao.setOTP(otp);
					MailSender.send(otp);
					request.getRequestDispatcher("../WEB-INF/login/Login.html").forward(request, response);
					return;
				}
			} catch (ClassNotFoundException | SQLException e) {
				request.getRequestDispatcher("../WEB-INF/login/Login.html").forward(request, response);
				return;
			}
		} else {
			request.getRequestDispatcher("../WEB-INF/manager/Manager.html").forward(request, response);
			return;
		}
	}

}
