package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.UmamusumeBean;
import dao.RacingUmamusumeDAO;
import dao.UmamusumeDAO;

/**
 * Servlet implementation class UmamusumeServlet
 */
@WebServlet(name = "/UmamusumeList")
public class UmamusumeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UmamusumeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("UTF-8");

			UmamusumeDAO udao = new UmamusumeDAO();
			request.setAttribute("mode", true);
			request.setAttribute("noMaxA", udao.noMax()); // 特殊なウマ娘を除いたウマ娘の総数
			request.setAttribute("noMaxB", new RacingUmamusumeDAO().noMax()); // 勝負服が登録された(育成ウマ娘として実装された)ウマ娘の総数
			request.setAttribute("umamusumeList", udao.getList()); // ウマ娘一覧
			request.getRequestDispatcher("./jsp/UmamusumeJSP.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("UTF-8");

			final String id = request.getParameter("id"); // 検索欄に入力された文字列
			UmamusumeDAO udao = new UmamusumeDAO();
			request.setAttribute("id", id);
			request.setAttribute("noMaxA", udao.noMax()); // 特殊なウマ娘を除いたウマ娘の総数
			request.setAttribute("noMaxB", new RacingUmamusumeDAO().noMax()); // 勝負服が登録された(育成ウマ娘として実装された)ウマ娘の総数
			request.setAttribute("umamusumeList", getList(udao, id)); // 検索結果一覧
			request.getRequestDispatcher("./jsp/UmamusumeJSP.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 入力された値が数値として解決できるかを確認して検索する。
	private List<UmamusumeBean> getList(final UmamusumeDAO udao, final String id) {
		if (id == null || id.equals("")) {
			return udao.getList();
		}

		try {
			return udao.getUmamusume(Integer.parseInt(id));
		} catch (NumberFormatException e) {
			return udao.getUmamusume(id);
		}
	}

}
