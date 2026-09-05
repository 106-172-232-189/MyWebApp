package com.umamusumelist.listener;

import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.umamusumelist.dao.OTPDAO;

/**
 * サーバーが稼働している間、1分間に一度の間隔で有効期限切れのワンタイムパスワードが無いかを確認し、あったら削除
 *
 * @author Umamusumelist.com
 * @version 8.0
 */
@WebListener
public final class AppBackgroundConfigListener implements ServletContextListener {

	/** 定期実行機 */
	private ScheduledExecutorService scheduler;

	/**
	 * サーバーを起動した際、定期実行機に「有効期限切れのワンタイムパスワードを削除する処理」を伝える
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(() -> { deleteExpiredOTP(); }, 0, 60, TimeUnit.SECONDS);
	}

	/**
	 * サーバーを停止した際、定期実行機も停止する
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if (scheduler != null) {
			scheduler.shutdownNow(); // タスクの新規受付を停止
		}
	}

	/**
	 * 有効期限切れのワンタイムパスワードが無いかを確認し、あったら削除する
	 */
	private void deleteExpiredOTP() {
		try (final OTPDAO otpdao = new OTPDAO()) {
			otpdao.deleteExpiredOTP();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
