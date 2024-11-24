package com.umamusumelist.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.umamusumelist.bean.RacingUmamusumeBean;
import com.umamusumelist.dao.RacingUmamusumeDAO;

/**
 * 勝負服を得ているウマ娘の取得に関する処理を行うサーブレット
 *
 * @author Umamusumelist.com
 * @version 5.1
 */
@WebServlet(name = "/RacingUmamusumeList")
public final class RacingUmamusumeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 新規インスタンス作成時のコンストラクター
	 * @see HttpServlet#HttpServlet()
	 */
	public RacingUmamusumeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * URLで直接「キャラクター一覧(実装順)」ページへアクセスした際に実行
	 *
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("UTF-8");

			final RacingUmamusumeDAO rudao = new RacingUmamusumeDAO();
			request.setAttribute("noMax", rudao.noMax()); // 勝負服を得ているウマ娘の総数
			request.setAttribute("racingUmamusumeList", rudao.getList(false)); // 勝負服を得ているウマ娘一覧
			request.setAttribute("racingUmamusumeListExclusive", rudao.getList(true)); // 勝負服を得ている特殊なウマ娘一覧
			request.getRequestDispatcher("./jsp/RacingUmamusumeJSP.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * トップページの「キャラクター一覧(実装順)」ボタンで「キャラクター一覧(実装順)」ページへアクセスした際に実行
	 *
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
			request.setAttribute("racingUmamusumeList", getRacingUmamusume(false, rudao, id)); // 検索結果一覧
			request.setAttribute("racingUmamusumeListExclusive", getRacingUmamusume(true, rudao, id)); // 検索結果一覧(特殊)
			request.setAttribute("id", id);
			request.getRequestDispatcher("./jsp/RacingUmamusumeJSP.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 検索欄に入力された文字列で勝負服を得ているウマ娘一覧の中から検索
	 *
	 * @param isExclusive 排他的な勝負服であるか
	 * @param id 検索欄に入力された文字列
	 * @return idで勝負服を得ているウマ娘一覧の中から検索した結果を格納するArrayListオブジェクト
	 */
	private List<RacingUmamusumeBean> getRacingUmamusume(final boolean isExclusive, final RacingUmamusumeDAO rudao, final String id) throws SQLException {
		if (id == null || id.equals("")) {
			return rudao.getList(isExclusive);
		}

		if (!isExclusive) {
			try {
				return rudao.getRacingUmamusume(Integer.parseInt(id));
			} catch (NumberFormatException e) {
				return rudao.getRacingUmamusume(isExclusive, id);
			}
		}

		return rudao.getRacingUmamusume(isExclusive, id);
	}

}
