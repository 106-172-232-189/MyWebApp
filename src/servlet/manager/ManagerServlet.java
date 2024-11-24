package servlet.manager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "/Manager/")
public class ManagerServlet extends HttpServlet {

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ManagerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
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
			if (!password.equals("3570d2cb3425d9420f64111ee5aab65fcb679a7f69774e605ce00cfebc5be45df1d1cccd9603a7f04626e47dd18c80ae566143affae4639d3a3bbd9b6a841dca")) {
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
