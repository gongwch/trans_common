package jp.co.ais.trans.common.client;

import javax.print.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;

/**
 * Applet�ŋ��L���郍�O�C�����擾.
 */
public final class TClientLoginInfo {

	/** server session ID */
	private static String sessionID = "";

	/** ���[�U��� */
	private static TUserInfo userInfo;

	/** ���[�U���A�N�Z�X */
	private static TRequestUserInfo reqUserInfo;

	/** ��ʃL���v�`��������p�v�����^ */
	private static PrintService printService;

	static {
		userInfo = new TUserInfo();
		reqUserInfo = new TRequestUserInfo();

		// �����O�C�����[�U(�J���p)
		userInfo.setCompanyCode(Util.avoidNull(ClientConfig.getDummyComp()));
		userInfo.setUserCode(Util.avoidNull(ClientConfig.getDummyUser()));
		userInfo.setUserLanguage(System.getProperty("user.language"));
	}

	/**
	 * ���[�U���̎擾
	 * 
	 * @return ���[�U���
	 */
	public static TUserInfo getInstance() {

		return userInfo;
	}

	/**
	 * ��Џ��̎擾
	 * 
	 * @return ��Џ��
	 */
	public static TCompanyInfo getCompanyInfo() {
		return userInfo.getCompanyInfo();
	}

	/**
	 * �Z�b�V����ID�擾
	 * 
	 * @return �Z�b�V����ID
	 */
	public static String getSessionID() {
		return TClientLoginInfo.sessionID;
	}

	/**
	 * �Z�b�V����ID�擾setter
	 * 
	 * @param sessionID �Z�b�V����ID
	 */
	public static void setSessionID(String sessionID) {
		TClientLoginInfo.sessionID = sessionID;
	}

	/**
	 * ���O�C�����Ď擾
	 * 
	 * @throws TException
	 */
	public static void reflesh() throws TException {

		String compCode = userInfo.getCompanyCode();
		String userCode = userInfo.getUserCode();

		reqUserInfo.request(compCode, userCode);
	}

	/**
	 * PrintService���擾����
	 * 
	 * @return PrintService
	 */
	public static PrintService getPrintService() {
		return printService;
	}

	/**
	 * PrintService��ݒ肷��.
	 * 
	 * @param service PrintService
	 */
	public static void setPrintService(PrintService service) {
		printService = service;
	}
}
