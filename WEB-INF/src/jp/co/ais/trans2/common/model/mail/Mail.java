package jp.co.ais.trans2.common.model.mail;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

import jakarta.activation.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;

/**
 * ���[�����M�p
 */
public class Mail implements Serializable {

	/** ���[���T�[�o�z�X�g��� */
	public HOST host;

	/** �G���[�ʒm�p���[�����M�ҁE���M����(�f�t�H���g) */
	public HEADER header;

	/** �^�C�g��(����) */
	public String title;

	/** ���� */
	public String text;

	/** �Y�t�t�@�C�� */
	public List<File> fileList = new ArrayList<File>();

	/** true:���[��DEBUG���[�h */
	public boolean debug = false;

	/** SMTP���M�pPROTOCOL */
	public String smtpProtocol = null;

	/**
	 * �R���X�g���N�^.
	 */
	public Mail() {
		this("");
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param title ����
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
	 * ����
	 * 
	 * @param title ����
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * �{��
	 * 
	 * @param text �{��
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * �Y�t�t�@�C���ǉ�
	 * 
	 * @param file �t�@�C��
	 */
	public void addFile(File file) {
		fileList.add(file);
	}

	/**
	 * �Y�t�t�@�C���ǉ�
	 * 
	 * @param filePath �t�@�C���p�X
	 */
	public void addFile(String filePath) {
		fileList.add(new File(filePath));
	}

	/**
	 * true:���[��DEBUG���[�h�̎擾
	 * 
	 * @return debug true:���[��DEBUG���[�h
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * true:���[��DEBUG���[�h�̐ݒ�
	 * 
	 * @param debug true:���[��DEBUG���[�h
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	/**
	 * SMTP���M�pPROTOCOL�̎擾
	 * 
	 * @return smtpProtocol SMTP���M�pPROTOCOL
	 */
	public String getSmtpProtocol() {
		return smtpProtocol;
	}

	/**
	 * SMTP���M�pPROTOCOL�̐ݒ�
	 * 
	 * @param smtpProtocol SMTP���M�pPROTOCOL
	 */
	public void setSmtpProtocol(String smtpProtocol) {
		this.smtpProtocol = smtpProtocol;
	}

	/**
	 * �x�[�X�w�b�_�[�Ń��[���𑗐M����.
	 */
	public void send() {
		try {
			if (host == null || header == null) {

				// �z�X�g�A�܂��́A���M��񂪐ݒ肳��Ă��܂���.
				throw new TRuntimeException("I11520");
			}

			Properties props = new Properties();

			// �ڑ�����z�X�g��
			props.put("mail.host", host.hostIP);

			// SMTP�T�[�o�[�̃A�h���X���w��
			props.put("mail.smtp.host", host.smtpHostIP);

			// �|�[�g�̐ݒ�
			props.put("mail.smtp.port", host.smtpPort);

			// SMTP before POP3�Ή�
			props.put("mail.smtp.auth", host.smtpAuth);

			// SMTP START TLS
			props.put("mail.smtp.starttls.enable", host.smtpUseStartTLS);

			if ("true".equals(host.smtpUseStartTLS)) {
				props.put("mail.smtps.host", host.smtpHostIP);
				props.put("mail.smtps.port", host.smtpPort);
				props.put("mail.smtps.auth", host.smtpAuth);
			}

			// JavaMail�̃f�o�b�O���[�h
			props.put("mail.debug", Boolean.toString(debug));

			Session session = Session.getInstance(props, new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(header.from, header.fromPassword);
				}
			});

			// Session session = Session.getDefaultInstance(props);
			MimeMessage mimeMessage = new MimeMessage(session);

			// ���[���̌`�����w��
			mimeMessage.setHeader("Content-Type", "text/html");

			// ���M�����[���A�h���X�Ƒ��M�Җ����w��
			mimeMessage.setFrom(new InternetAddress(header.from, header.fromName, "UTF-8"));

			// ���M�惁�[���A�h���X���w��
			for (String to : header.toList) {
				mimeMessage.addRecipients(Message.RecipientType.TO, to);
			}

			for (String cc : header.ccList) {
				mimeMessage.addRecipients(Message.RecipientType.CC, cc);
			}

			for (String bcc : header.bccList) {
				mimeMessage.addRecipients(Message.RecipientType.BCC, bcc);
			}

			// ���[���̃^�C�g�����w��
			mimeMessage.setSubject(title, "UTF-8");

			// ���M���t���w��
			mimeMessage.setSentDate(new Date());

			// ���e��ݒ�
			Multipart multipart = new MimeMultipart();

			// �{��
			MimeBodyPart part = new MimeBodyPart();
			part.setText(text, "UTF-8");
			multipart.addBodyPart(part);

			for (File file : fileList) {
				MimeBodyPart filePart = new MimeBodyPart();

				filePart.setDataHandler(
						new DataHandler(new FileDataSource(file))); // �t�@�C��
				filePart.setFileName(MimeUtility.encodeWord(file.getName(), "UTF-8", null)); // �t�@�C����

				multipart.addBodyPart(filePart);
			}

			mimeMessage.setContent(multipart);

			if (!Util.isNullOrEmpty(smtpProtocol)) {
				// �w��PROTOCOL�ő��M

				Transport transport = null;

				try {
					// transport�̐ݒ�
					transport = session.getTransport(smtpProtocol);

					// �F�ؗp���[�U���ƃp�X���[�h��ݒ肵�R�l�N�g
					transport.connect(null, header.from, header.fromPassword);

					// ���M
					transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());

				} finally {
					// transport�����
					if (transport != null) {
						transport.close();
					}
				}
			} else {
				// �ʏ푗�M
				Transport.send(mimeMessage);
			}

		} catch (Exception ex) {
			throw new TRuntimeException(ex, "mail send faild.");
		}
	}

	/**
	 * ���[���z�X�g���
	 */
	public static class HOST implements Serializable {

		/** �z�X�gIP�A�h���X */
		protected String hostIP;

		/** SMTP�z�X�gIP�A�h���X */
		protected String smtpHostIP;

		/** SMTP�|�[�g */
		protected String smtpPort;

		/** SMTP�F�؂��K�v���ǂ���(true or false) */
		protected String smtpAuth = "false";

		/** true:START_TLS�g�p */
		protected String smtpUseStartTLS = "false";

		/**
		 * �z�X�gIP�A�h���X
		 * 
		 * @param hostIP �z�X�gIP�A�h���X
		 */
		public void setHostIP(String hostIP) {
			this.hostIP = hostIP;
		}

		/**
		 * SMTP�z�X�gIP�A�h���X
		 * 
		 * @param smtpHostIP SMTP�z�X�g IP�A�h���X
		 */
		public void setSmtpHostIP(String smtpHostIP) {
			this.smtpHostIP = smtpHostIP;
		}

		/**
		 * SMTP�|�[�g
		 * 
		 * @param smtpPort �|�[�g�ԍ�
		 */
		public void setSmtpPort(String smtpPort) {
			this.smtpPort = smtpPort;
		}

		/**
		 * SMTP�F�؂��K�v���ǂ���
		 * 
		 * @param smtpAuth true:�K�v
		 */
		public void setSmtpAuth(boolean smtpAuth) {
			this.smtpAuth = smtpAuth ? "true" : "false";
		}

		/**
		 * SMTP�F�؂��K�v���ǂ���
		 * 
		 * @param smtpAuth true:�K�v
		 */
		public void setSmtpAuth(String smtpAuth) {
			this.smtpAuth = smtpAuth;
		}

		/**
		 * true:START_TLS�g�p�̐ݒ�
		 * 
		 * @param smtpUseStartTLS true:START_TLS�g�p
		 */
		public void setSmtpUseStartTLS(boolean smtpUseStartTLS) {
			this.smtpUseStartTLS = smtpUseStartTLS ? "true" : "false";
		}

		/**
		 * true:START_TLS�g�p�̐ݒ�
		 * 
		 * @param smtpUseStartTLS true:START_TLS�g�p
		 */
		public void setSmtpUseStartTLS(String smtpUseStartTLS) {
			this.smtpUseStartTLS = smtpUseStartTLS;
		}

	}

	/**
	 * ���[�� �w�b�_(���M��E���M��)���
	 */
	public static class HEADER implements Serializable {

		/** ���M�҃��[���A�h���X */
		protected String from;

		/** ���M�Җ� */
		protected String fromName;

		/** ���M�҃��[���p�X���[�h */
		protected String fromPassword;

		/** TO���惊�X�g */
		protected List<String> toList = new ArrayList<String>();

		/** CC���惊�X�g */
		protected List<String> ccList = new ArrayList<String>();

		/** BCC���惊�X�g */
		protected List<String> bccList = new ArrayList<String>();

		/**
		 * ���M�҃��[���A�h���X
		 * 
		 * @param from ���[���A�h���X
		 */
		public void setFrom(String from) {
			this.from = from;
		}

		/**
		 * ���M�Җ�
		 * 
		 * @param fromName ���M�Җ�
		 */
		public void setFromName(String fromName) {
			this.fromName = fromName;
		}

		/**
		 * ���M�҃��[���p�X���[�h
		 * 
		 * @param fromPassword ���[���p�X���[�h
		 */
		public void setFromPassword(String fromPassword) {
			this.fromPassword = fromPassword;
		}

		/**
		 * ���M�҃��[���A�h���X
		 * 
		 * @return ���M�҃��[���A�h���X
		 */
		public String getFrom() {
			return from;
		}

		/**
		 * ���M�Җ�
		 * 
		 * @return ���M�Җ�
		 */
		public String getFromName() {
			return fromName;
		}

		/**
		 * ���M�҃��[���p�X���[�h
		 * 
		 * @return ���M�҃��[���p�X���[�h
		 */
		public String getFromPassword() {
			return fromPassword;
		}

		/**
		 * ���M�惁�[���A�h���X(TO)
		 * 
		 * @param to ���[���A�h���X
		 */
		public void addToList(String to) {
			toList.add(to);
		}

		/**
		 * ���M�惁�[���A�h���X(CC)
		 * 
		 * @param cc ���[���A�h���X
		 */
		public void addCcList(String cc) {
			ccList.add(cc);
		}

		/**
		 * ���M�惁�[���A�h���X(BCC)
		 * 
		 * @param bcc ���[���A�h���X
		 */
		public void addBccList(String bcc) {
			bccList.add(bcc);
		}
	}

}
