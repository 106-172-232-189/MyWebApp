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
 * @version 6.0
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
	 * @throws IOException エラーページの表示処理に失敗
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		try (final RacingUmamusumeDAO rudao = new RacingUmamusumeDAO()) {
			request.setCharacterEncoding("UTF-8");
			request.setAttribute("noMax", rudao.noMax()); // 勝負服を得ているウマ娘の総数
			request.setAttribute("racingUmamusumeList", rudao.getList(false));
			request.getRequestDispatcher("./jsp/RacingUmamusumeJSP.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * トップページの「キャラクター一覧(実装順)」ボタンで「キャラクター一覧(実装順)」ページへアクセスした際に実行
	 *
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 * @throws IOException エラーページの表示処理に失敗
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		try (final RacingUmamusumeDAO rudao = new RacingUmamusumeDAO()) {
			request.setCharacterEncoding("UTF-8");

			final String id = request.getParameter("id"); // 検索欄に入力された文字列
			request.setAttribute("noMax", rudao.noMax()); // 勝負服を得ているウマ娘の総数
			request.setAttribute("racingUmamusumeList", getRacingUmamusume(rudao, id));
			request.setAttribute("id", id);
			request.getRequestDispatcher("./jsp/RacingUmamusumeJSP.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 検索欄に入力された文字列で勝負服を得ているウマ娘一覧の中から検索
	 *
	 * @param id 検索欄に入力された文字列
	 * @return idで勝負服を得ているウマ娘一覧の中から検索した結果を格納するArrayListオブジェクト
	 * @throws SQLException データベースに関する処理時に何らかの異常が発生
	 */
	private List<RacingUmamusumeBean> getRacingUmamusume(final RacingUmamusumeDAO rudao, final String id) throws SQLException {
		if (id == null || id.equals("")) {
			return rudao.getList(false);
		}

		try {
			return rudao.getRacingUmamusume(Integer.parseInt(id.trim().replaceAll("[,，_＿　]", "")));
		} catch (NumberFormatException e) {
			return rudao.getRacingUmamusume(false, id);
		}
	}

}
