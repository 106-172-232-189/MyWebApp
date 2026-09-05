package com.umamusumelist.util;

import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

/**
 * Gmailで所定の内容のメールを自動的に送信
 *
 * @author Umamusumelist.com
 * @version 8.0
 */
public final class MailSender {

	/** Gmailに送信依頼
	 *
	 * @param content
	 *            内容
	 */
	public static void send(String content) {
		// SMTPサーバー設定
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		try {
			// メール作成
			Message message = new MimeMessage(Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("GoogleAccountAddress", "****************");
				}
			}));
			message.setFrom(new InternetAddress("From"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("To"));
			message.setSubject("Title");
			message.setText(content);

			// 送信
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
