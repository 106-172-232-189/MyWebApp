package com.umamusumelist.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.umamusumelist.bean.NotUmamusumeBean;
import com.umamusumelist.bean.UmamusumeBean;
import com.umamusumelist.dao.NotUmamusumeDAO;
import com.umamusumelist.dao.RacingUmamusumeDAO;
import com.umamusumelist.dao.UmamusumeDAO;

/**
 * ウマ娘、ウマ娘でないトレセン学園関係者の取得に関する処理を行うサーブレット
 *
 * @author Umamusumelist.com
 * @version 5.0
 */
@WebServlet(name = "/UmamusumeList")
public class UmamusumeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * 新規インスタンス作成時のコンストラクター
     * @see HttpServlet#HttpServlet()
     */
    public UmamusumeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * URLで直接「キャラクター一覧」ページへアクセスした際に実行
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("UTF-8");

			UmamusumeDAO udao = new UmamusumeDAO();
			NotUmamusumeDAO nudao = new NotUmamusumeDAO();
			request.setAttribute("mode", true);
			request.setAttribute("noMaxA", udao.noMax()); // 特殊なウマ娘を除いたウマ娘の総数
			request.setAttribute("noMaxB", new RacingUmamusumeDAO().noMax()); // 勝負服が登録された(育成ウマ娘として実装された)ウマ娘の総数
			request.setAttribute("noMaxC", nudao.noMax()); // ウマ娘でないトレセン学園関係者の総数
			request.setAttribute("umamusumeList", udao.getList()); // ウマ娘一覧
			request.setAttribute("notUmamusumeList", nudao.getList()); // ウマ娘でないトレセン学園関係者一覧
			request.getRequestDispatcher("./jsp/UmamusumeJSP.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * トップページの「キャラクター一覧」ボタンで「キャラクター一覧」ページへアクセスした際に実行
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("UTF-8");

			final String id = request.getParameter("id"); // 検索欄に入力された文字列
			UmamusumeDAO udao = new UmamusumeDAO();
			NotUmamusumeDAO nudao = new NotUmamusumeDAO();
			request.setAttribute("id", id);
			request.setAttribute("noMaxA", udao.noMax()); // 特殊なウマ娘を除いたウマ娘の総数
			request.setAttribute("noMaxB", new RacingUmamusumeDAO().noMax()); // 勝負服が登録された(育成ウマ娘として実装された)ウマ娘の総数
			request.setAttribute("noMaxC", nudao.noMax()); // ウマ娘でないトレセン学園関係者の総数
			request.setAttribute("umamusumeList", getList(udao, id)); // 検索結果一覧
			request.setAttribute("notUmamusumeList", getList(nudao, id)); // ウマ娘でないトレセン学園関係者一覧
			request.getRequestDispatcher("./jsp/UmamusumeJSP.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 検索欄に入力された文字列でウマ娘一覧の中から検索
	 *
	 * @param id 検索欄に入力された文字列
	 * @return idでウマ娘一覧の中から検索した結果を格納するArrayListオブジェクト
	 */
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

	/**
	 * 検索欄に入力された文字列でウマ娘でないトレセン学園関係者一覧の中から検索
	 *
	 * @param id 検索欄に入力された文字列
	 * @return idでウマ娘でないトレセン学園関係者一覧の中から検索した結果を格納するArrayListオブジェクト
	 */
	private List<NotUmamusumeBean> getList(NotUmamusumeDAO nudao, String id) {
		// TODO 自動生成されたメソッド・スタブ
		if (id == null || id.equals("")) {
			return nudao.getList();
		}

		return nudao.getNotUmamusume(id);
	}

}
