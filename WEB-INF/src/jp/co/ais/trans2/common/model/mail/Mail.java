package jp.co.ais.trans2.common.model.mail;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

import jakarta.activation.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;

/**
 * メール送信用
 */
public class Mail implements Serializable {

	/** メールサーバホスト情報 */
	public HOST host;

	/** エラー通知用メール送信者・送信先情報(デフォルト) */
	public HEADER header;

	/** タイトル(件名) */
	public String title;

	/** 文章 */
	public String text;

	/** 添付ファイル */
	public List<File> fileList = new ArrayList<File>();

	/** true:メールDEBUGモード */
	public boolean debug = false;

	/** SMTP送信用PROTOCOL */
	public String smtpProtocol = null;

	/**
	 * コンストラクタ.
	 */
	public Mail() {
		this("");
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param title 件名
	 */
	public Mail(String title) {
		this.title = title;
	}

	/**
	 * @param host
	 */
	public void setHost(HOST host) {
		this.host = host;
	}

	/**
	 * @param header
	 */
	public void setHeader(HEADER header) {
		this.header = header;
	}

	/**
	 * 件名
	 * 
	 * @param title 件名
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 本文
	 * 
	 * @param text 本文
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 添付ファイル追加
	 * 
	 * @param file ファイル
	 */
	public void addFile(File file) {
		fileList.add(file);
	}

	/**
	 * 添付ファイル追加
	 * 
	 * @param filePath ファイルパス
	 */
	public void addFile(String filePath) {
		fileList.add(new File(filePath));
	}

	/**
	 * true:メールDEBUGモードの取得
	 * 
	 * @return debug true:メールDEBUGモード
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * true:メールDEBUGモードの設定
	 * 
	 * @param debug true:メールDEBUGモード
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * SMTP送信用PROTOCOLの取得
	 * 
	 * @return smtpProtocol SMTP送信用PROTOCOL
	 */
	public String getSmtpProtocol() {
		return smtpProtocol;
	}

	/**
	 * SMTP送信用PROTOCOLの設定
	 * 
	 * @param smtpProtocol SMTP送信用PROTOCOL
	 */
	public void setSmtpProtocol(String smtpProtocol) {
		this.smtpProtocol = smtpProtocol;
	}

	/**
	 * ベースヘッダーでメールを送信する.
	 */
	public void send() {
		try {
			if (host == null || header == null) {

				// ホスト、または、送信情報が設定されていません.
				throw new TRuntimeException("I11520");
			}

			Properties props = new Properties();

			// 接続するホスト名
			props.put("mail.host", host.hostIP);

			// SMTPサーバーのアドレスを指定
			props.put("mail.smtp.host", host.smtpHostIP);

			// ポートの設定
			props.put("mail.smtp.port", host.smtpPort);

			// SMTP before POP3対応
			props.put("mail.smtp.auth", host.smtpAuth);

			// SMTP START TLS
			props.put("mail.smtp.starttls.enable", host.smtpUseStartTLS);

			if ("true".equals(host.smtpUseStartTLS)) {
				props.put("mail.smtps.host", host.smtpHostIP);
				props.put("mail.smtps.port", host.smtpPort);
				props.put("mail.smtps.auth", host.smtpAuth);
			}

			// JavaMailのデバッグモード
			props.put("mail.debug", Boolean.toString(debug));

			Session session = Session.getInstance(props, new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(header.from, header.fromPassword);
				}
			});

			// Session session = Session.getDefaultInstance(props);
			MimeMessage mimeMessage = new MimeMessage(session);

			// メールの形式を指定
			mimeMessage.setHeader("Content-Type", "text/html");

			// 送信元メールアドレスと送信者名を指定
			mimeMessage.setFrom(new InternetAddress(header.from, header.fromName, "UTF-8"));

			// 送信先メールアドレスを指定
			for (String to : header.toList) {
				mimeMessage.addRecipients(Message.RecipientType.TO, to);
			}

			for (String cc : header.ccList) {
				mimeMessage.addRecipients(Message.RecipientType.CC, cc);
			}

			for (String bcc : header.bccList) {
				mimeMessage.addRecipients(Message.RecipientType.BCC, bcc);
			}

			// メールのタイトルを指定
			mimeMessage.setSubject(title, "UTF-8");

			// 送信日付を指定
			mimeMessage.setSentDate(new Date());

			// 内容を設定
			Multipart multipart = new MimeMultipart();

			// 本文
			MimeBodyPart part = new MimeBodyPart();
			part.setText(text, "UTF-8");
			multipart.addBodyPart(part);

			for (File file : fileList) {
				MimeBodyPart filePart = new MimeBodyPart();

				filePart.setDataHandler(
						new DataHandler(new FileDataSource(file))); // ファイル
				filePart.setFileName(MimeUtility.encodeWord(file.getName(), "UTF-8", null)); // ファイル名

				multipart.addBodyPart(filePart);
			}

			mimeMessage.setContent(multipart);

			if (!Util.isNullOrEmpty(smtpProtocol)) {
				// 指定PROTOCOLで送信

				Transport transport = null;

				try {
					// transportの設定
					transport = session.getTransport(smtpProtocol);

					// 認証用ユーザ名とパスワードを設定しコネクト
					transport.connect(null, header.from, header.fromPassword);

					// 送信
					transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

				} finally {
					// transportを閉じる
					if (transport != null) {
						transport.close();
					}
				}
			} else {
				// 通常送信
				Transport.send(mimeMessage);
			}

		} catch (Exception ex) {
			throw new TRuntimeException(ex, "mail send faild.");
		}
	}

	/**
	 * メールホスト情報
	 */
	public static class HOST implements Serializable {

		/** ホストIPアドレス */
		protected String hostIP;

		/** SMTPホストIPアドレス */
		protected String smtpHostIP;

		/** SMTPポート */
		protected String smtpPort;

		/** SMTP認証が必要かどうか(true or false) */
		protected String smtpAuth = "false";

		/** true:START_TLS使用 */
		protected String smtpUseStartTLS = "false";

		/**
		 * ホストIPアドレス
		 * 
		 * @param hostIP ホストIPアドレス
		 */
		public void setHostIP(String hostIP) {
			this.hostIP = hostIP;
		}

		/**
		 * SMTPホストIPアドレス
		 * 
		 * @param smtpHostIP SMTPホスト IPアドレス
		 */
		public void setSmtpHostIP(String smtpHostIP) {
			this.smtpHostIP = smtpHostIP;
		}

		/**
		 * SMTPポート
		 * 
		 * @param smtpPort ポート番号
		 */
		public void setSmtpPort(String smtpPort) {
			this.smtpPort = smtpPort;
		}

		/**
		 * SMTP認証が必要かどうか
		 * 
		 * @param smtpAuth true:必要
		 */
		public void setSmtpAuth(boolean smtpAuth) {
			this.smtpAuth = smtpAuth ? "true" : "false";
		}

		/**
		 * SMTP認証が必要かどうか
		 * 
		 * @param smtpAuth true:必要
		 */
		public void setSmtpAuth(String smtpAuth) {
			this.smtpAuth = smtpAuth;
		}

		/**
		 * true:START_TLS使用の設定
		 * 
		 * @param smtpUseStartTLS true:START_TLS使用
		 */
		public void setSmtpUseStartTLS(boolean smtpUseStartTLS) {
			this.smtpUseStartTLS = smtpUseStartTLS ? "true" : "false";
		}

		/**
		 * true:START_TLS使用の設定
		 * 
		 * @param smtpUseStartTLS true:START_TLS使用
		 */
		public void setSmtpUseStartTLS(String smtpUseStartTLS) {
			this.smtpUseStartTLS = smtpUseStartTLS;
		}

	}

	/**
	 * メール ヘッダ(送信先・送信元)情報
	 */
	public static class HEADER implements Serializable {

		/** 送信者メールアドレス */
		protected String from;

		/** 送信者名 */
		protected String fromName;

		/** 送信者メールパスワード */
		protected String fromPassword;

		/** TO宛先リスト */
		protected List<String> toList = new ArrayList<String>();

		/** CC宛先リスト */
		protected List<String> ccList = new ArrayList<String>();

		/** BCC宛先リスト */
		protected List<String> bccList = new ArrayList<String>();

		/**
		 * 送信者メールアドレス
		 * 
		 * @param from メールアドレス
		 */
		public void setFrom(String from) {
			this.from = from;
		}

		/**
		 * 送信者名
		 * 
		 * @param fromName 送信者名
		 */
		public void setFromName(String fromName) {
			this.fromName = fromName;
		}

		/**
		 * 送信者メールパスワード
		 * 
		 * @param fromPassword メールパスワード
		 */
		public void setFromPassword(String fromPassword) {
			this.fromPassword = fromPassword;
		}

		/**
		 * 送信者メールアドレス
		 * 
		 * @return 送信者メールアドレス
		 */
		public String getFrom() {
			return from;
		}

		/**
		 * 送信者名
		 * 
		 * @return 送信者名
		 */
		public String getFromName() {
			return fromName;
		}

		/**
		 * 送信者メールパスワード
		 * 
		 * @return 送信者メールパスワード
		 */
		public String getFromPassword() {
			return fromPassword;
		}

		/**
		 * 送信先メールアドレス(TO)
		 * 
		 * @param to メールアドレス
		 */
		public void addToList(String to) {
			toList.add(to);
		}

		/**
		 * 送信先メールアドレス(CC)
		 * 
		 * @param cc メールアドレス
		 */
		public void addCcList(String cc) {
			ccList.add(cc);
		}

		/**
		 * 送信先メールアドレス(BCC)
		 * 
		 * @param bcc メールアドレス
		 */
		public void addBccList(String bcc) {
			bccList.add(bcc);
		}
	}

}
