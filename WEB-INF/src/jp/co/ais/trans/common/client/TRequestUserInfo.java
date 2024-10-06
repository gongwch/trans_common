package jp.co.ais.trans.common.client;

import java.io.*;
import java.net.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.log.*;

/**
 * ���p�ҏ�񃊃N�G�X�g class.
 */
public final class TRequestUserInfo extends TAppletClientBase {

	/**
	 * ���p�ҏ�� ���N�G�X�g
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param usrCode ���[�U�R�[�h
	 * @throws TException
	 */
	public void request(String kaiCode, String usrCode) throws TException {
		try {

			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "base");
			addSendValues("kaiCode", kaiCode);
			addSendValues("usrCode", usrCode);
			addSendValues("ipadd", InetAddress.getLocalHost().getHostAddress());

			// ���M
			if (!request("common/userInfo")) {
				throw new TRequestException();
			}

			// �T�[�u���b�g���瑗���Ă������ʂ����ێ��N���X�ɃZ�b�g
			TUserInfo userInfo = TClientLoginInfo.getInstance();

			Map<String, String> map = getResult();

			// ���[�U���
			userInfo.reflect(map);

			// ��Џ��
			TCompanyInfo compInfo = userInfo.getCompanyInfo();
			compInfo.reflect(map);

			// �ʉݏ��̎擾 --
			addSendValues("flag", "currency");
			addSendValues("kaiCode", userInfo.getCompanyCode());

			if (!request("common/userInfo")) {
				throw new TRequestException();
			}

			compInfo.setCurrencyDigits(getResult());

			ClientLogger.debug(userInfo.toString());
			ClientLogger.debug(compInfo.toString());

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}
}
