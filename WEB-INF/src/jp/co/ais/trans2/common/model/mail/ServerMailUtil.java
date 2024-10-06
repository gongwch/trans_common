package jp.co.ais.trans2.common.model.mail;

import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.model.mail.Mail.HEADER;

/**
 * �T�[�o�p���[�����M���[�e�B���e�B
 */
public class ServerMailUtil {

	/** �ʏ탁�[��(�z�X�g�ݒ��) */
	public static Mail.HOST baseHost = new Mail.HOST();

	/** �G���[�p���[��(�z�X�g�E�w�b�_�ݒ��) */
	public static Mail.HEADER errorHeader = new Mail.HEADER();

	/** �ʏ탁�[�����M�A�h���X */
	public static String baseFrom;

	/** �ʏ탁�[�����M�� */
	public static String baseName;

	/** �ʏ탁�[�����M���p�X���[�h */
	public static String basePw;

	static {
		// �f�t�H���g�z�X�g�����擾����.
		baseHost.setHostIP(ServerConfig.getProperty("mail.host"));
		baseHost.setSmtpHostIP(ServerConfig.getProperty("mail.smtp.host"));
		baseHost.setSmtpPort(ServerConfig.getProperty("mail.smtp.port"));

		try {
			baseHost.setSmtpAuth(ServerConfig.getProperty("mail.smtp.auth"));

		} catch (MissingResourceException ex) {
			// �f�t�H���gFalse
		}

		try {
			baseHost.setSmtpUseStartTLS(ServerConfig.getProperty("mail.smtp.starttls.enable"));

		} catch (MissingResourceException ex) {
			// �f�t�H���gFalse
		}

		// ���M�p�f�t�H���g���[���w�b�_�����擾����.
		baseFrom = ServerConfig.getProperty("mail.base.from");
		baseName = ServerConfig.getProperty("mail.base.from.name");
		basePw = ServerConfig.getProperty("mail.base.from.pwd");

		// �G���[���M�p�f�t�H���g���[���w�b�_�����擾����.
		try {
			errorHeader.setFrom(ServerConfig.getProperty("mail.err.from"));
			errorHeader.setFromName(ServerConfig.getProperty("mail.err.from.name"));
			errorHeader.setFromPassword(ServerConfig.getProperty("mail.err.from.pwd"));

		} catch (MissingResourceException ex) {
			// �G���[�p�̐ݒ肪�����ꍇ�̓f�t�H���g��ݒ�
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
			// CC��BCC�͐ݒ肪�Ȃ��Ă��G���[�ɂ��Ȃ�
		}

		try {
			String bccList = ServerConfig.getProperty("mail.err.bcc");
			for (String bcc : StringUtil.toArrayByComma(bccList)) {
				errorHeader.addBccList(bcc);
			}
		} catch (MissingResourceException ex) {
			// CC��BCC�͐ݒ肪�Ȃ��Ă��G���[�ɂ��Ȃ�
		}
	}

	/**
	 * ���[���𑗐M����<br>
	 * mail.HOST<br>
	 * mail.HEADER<br>
	 * ���O�ɐݒ肷��(���̊֐��͔����̂݁A�f�t�H���g�z�X�g�ȊO�̏ꍇ���p)
	 * 
	 * @param mail ���[��
	 */
	public synchronized static void send(Mail mail) {
		mail.send();
	}

	/**
	 * �w�b�_���w�肵�ă��[���𑗐M����
	 * 
	 * @param hdr �w�b�_
	 * @param mail ���[��
	 */
	public synchronized static void send(HEADER hdr, Mail mail) {
		mail.setHost(baseHost);
		mail.setHeader(hdr);
		mail.send();
	}

	/**
	 * �w�b�_���w�肵�ă��[���𑗐M����
	 * 
	 * @param to ����
	 * @param mail ���[��
	 */
	public synchronized static void send(String to, Mail mail) {
		send(to, null, null, mail);
	}

	/**
	 * �w�b�_���w�肵�ă��[���𑗐M����
	 * 
	 * @param to ����
	 * @param cc CC
	 * @param mail ���[��
	 */
	public synchronized static void send(String to, String cc, Mail mail) {
		send(to, cc, null, mail);
	}

	/**
	 * �w�b�_���w�肵�ă��[���𑗐M����
	 * 
	 * @param to ����
	 * @param cc CC
	 * @param bcc BCC
	 * @param mail ���[��
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
	 * �f�t�H���g�G���[���[�����M��փ��[���𑗐M����.
	 * 
	 * @param mail ���[��
	 */
	public synchronized static void sendError(Mail mail) {
		send(errorHeader, mail);
	}
}
