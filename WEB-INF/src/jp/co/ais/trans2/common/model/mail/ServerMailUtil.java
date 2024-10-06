package jp.co.ais.trans2.common.model.mail;

import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.model.mail.Mail.HEADER;

/**
 * サーバ用メール送信ユーティリティ
 */
public class ServerMailUtil {

	/** 通常メール(ホスト設定済) */
	public static Mail.HOST baseHost = new Mail.HOST();

	/** エラー用メール(ホスト・ヘッダ設定済) */
	public static Mail.HEADER errorHeader = new Mail.HEADER();

	/** 通常メール送信アドレス */
	public static String baseFrom;

	/** 通常メール送信者 */
	public static String baseName;

	/** 通常メール送信時パスワード */
	public static String basePw;

	static {
		// デフォルトホスト情報を取得する.
		baseHost.setHostIP(ServerConfig.getProperty("mail.host"));
		baseHost.setSmtpHostIP(ServerConfig.getProperty("mail.smtp.host"));
		baseHost.setSmtpPort(ServerConfig.getProperty("mail.smtp.port"));

		try {
			baseHost.setSmtpAuth(ServerConfig.getProperty("mail.smtp.auth"));

		} catch (MissingResourceException ex) {
			// デフォルトFalse
		}

		try {
			baseHost.setSmtpUseStartTLS(ServerConfig.getProperty("mail.smtp.starttls.enable"));

		} catch (MissingResourceException ex) {
			// デフォルトFalse
		}

		// 送信用デフォルトメールヘッダ情報を取得する.
		baseFrom = ServerConfig.getProperty("mail.base.from");
		baseName = ServerConfig.getProperty("mail.base.from.name");
		basePw = ServerConfig.getProperty("mail.base.from.pwd");

		// エラー送信用デフォルトメールヘッダ情報を取得する.
		try {
			errorHeader.setFrom(ServerConfig.getProperty("mail.err.from"));
			errorHeader.setFromName(ServerConfig.getProperty("mail.err.from.name"));
			errorHeader.setFromPassword(ServerConfig.getProperty("mail.err.from.pwd"));

		} catch (MissingResourceException ex) {
			// エラー用の設定が無い場合はデフォルトを設定
			errorHeader.setFrom(baseFrom);
			errorHeader.setFromName(baseName);
			errorHeader.setFromPassword(basePw);
		}

		String toList = ServerConfig.getProperty("mail.err.to");
		for (String to : StringUtil.toArrayByComma(toList)) {
			errorHeader.addToList(to);
		}

		try {
			String ccList = ServerConfig.getProperty("mail.err.cc");
			for (String cc : StringUtil.toArrayByComma(ccList)) {
				errorHeader.addCcList(cc);
			}
		} catch (MissingResourceException ex) {
			// CCとBCCは設定がなくてもエラーにしない
		}

		try {
			String bccList = ServerConfig.getProperty("mail.err.bcc");
			for (String bcc : StringUtil.toArrayByComma(bccList)) {
				errorHeader.addBccList(bcc);
			}
		} catch (MissingResourceException ex) {
			// CCとBCCは設定がなくてもエラーにしない
		}
	}

	/**
	 * メールを送信する<br>
	 * mail.HOST<br>
	 * mail.HEADER<br>
	 * 事前に設定する(この関数は発送のみ、デフォルトホスト以外の場合利用)
	 * 
	 * @param mail メール
	 */
	public synchronized static void send(Mail mail) {
		mail.send();
	}

	/**
	 * ヘッダを指定してメールを送信する
	 * 
	 * @param hdr ヘッダ
	 * @param mail メール
	 */
	public synchronized static void send(HEADER hdr, Mail mail) {
		mail.setHost(baseHost);
		mail.setHeader(hdr);
		mail.send();
	}

	/**
	 * ヘッダを指定してメールを送信する
	 * 
	 * @param to 宛先
	 * @param mail メール
	 */
	public synchronized static void send(String to, Mail mail) {
		send(to, null, null, mail);
	}

	/**
	 * ヘッダを指定してメールを送信する
	 * 
	 * @param to 宛先
	 * @param cc CC
	 * @param mail メール
	 */
	public synchronized static void send(String to, String cc, Mail mail) {
		send(to, cc, null, mail);
	}

	/**
	 * ヘッダを指定してメールを送信する
	 * 
	 * @param to 宛先
	 * @param cc CC
	 * @param bcc BCC
	 * @param mail メール
	 */
	public synchronized static void send(String to, String cc, String bcc, Mail mail) {
		Mail.HEADER header = new Mail.HEADER();
		header.addToList(to);

		if (!Util.isNullOrEmpty(cc)) {
			header.addCcList(cc);
		}
		if (!Util.isNullOrEmpty(bcc)) {
			header.addBccList(bcc);
		}
		header.setFrom(baseFrom);
		header.setFromName(baseName);
		header.setFromPassword(basePw);

		send(header, mail);
	}

	/**
	 * デフォルトエラーメール送信先へメールを送信する.
	 * 
	 * @param mail メール
	 */
	public synchronized static void sendError(Mail mail) {
		send(errorHeader, mail);
	}
}
