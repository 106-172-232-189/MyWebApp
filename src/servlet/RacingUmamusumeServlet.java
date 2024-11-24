package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.RacingUmamusumeBean;
import dao.RacingUmamusumeDAO;

/**
 * Servlet implementation class RacingUmamusumeServlet
 */
@WebServlet(name = "/RacingUmamusumeList")
public class RacingUmamusumeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RacingUmamusumeServlet() {
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

			final RacingUmamusumeDAO rudao = new RacingUmamusumeDAO();
			request.setAttribute("noMax", rudao.noMax()); // 勝負服を得ているウマ娘の総数
			request.setAttribute("racingUmamusumeList", rudao.getList()); // 勝負服を得ているウマ娘一覧
			request.getRequestDispatcher("./jsp/RacingUmamusumeJSP.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("UTF-8");

			final RacingUmamusumeDAO rudao = new RacingUmamusumeDAO();
			final String id = request.getParameter("id"); // 検索欄に入力された文字列
			request.setAttribute("noMax", rudao.noMax()); // 勝負服を得ているウマ娘の総数
			request.setAttribute("racingUmamusumeList", getRacingUmamusume(rudao, id)); // 検索結果一覧
			request.setAttribute("id", id);
			request.getRequestDispatcher("./jsp/RacingUmamusumeJSP.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 入力された値が数値として解決できるかを確認して検索する。
	private List<RacingUmamusumeBean> getRacingUmamusume(final RacingUmamusumeDAO rudao, final String id) {
		if (id == null || id.equals("")) {
			return rudao.getList();
		}

		try {
			return rudao.getRacingUmamusume(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return rudao.getRacingUmamusume(id);
		}
	}

}
