package jp.co.ais.trans.common.client;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.server.dao.*;
import jp.co.ais.trans.common.util.Util;

/**
 * ���p�ҏ�񃊃N�G�X�g class.
 */
public class TRequestMenuManager extends TRequestServiceBase {

	private List resList;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param access ���N�G�X�g�N���X
	 */
	public TRequestMenuManager(TRequestBase access) {
		super(access, "MenuManager");
	}

	/**
	 * ���j���[�����擾�B true �����F���ʂ�List��Bean�ɕϊ����Atrue��Ԃ�
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param usrCode ���[�U�R�[�h
	 * @return false ���s
	 * @throws IOException
	 */
	public boolean request(String kaiCode, String usrCode) throws IOException {

		if (Util.isNullOrEmpty(kaiCode) || Util.isNullOrEmpty(usrCode)) {
			throw new IllegalArgumentException("kaiCode or userCode is empty.");
		}

		// ���M����p�����[�^��ݒ�
		access.addSendValues("kaiCode", kaiCode);
		access.addSendValues("usrCode", usrCode);

		// ���ۃ`�F�b�N
		boolean result = request();

		if (result) {
			// ���j���[�������X�g�ɃZ�b�g
			resList = new LinkedList();

			List list = access.getResultList();

			for (Iterator iter = list.iterator(); iter.hasNext();) {
				List element = (List) iter.next();
				if (element.size() == 0) {
					continue;
				}
				MenuBean bean = new MenuBean();
				bean.setSYS_CODE(String.valueOf(element.get(0)));
				bean.setPRG_CODE(String.valueOf(element.get(1)));
				bean.setPRG_NAME(String.valueOf(element.get(2)));
				bean.setPRG_NAME_S(String.valueOf(element.get(3)));
				bean.setKEN(Integer.parseInt(String.valueOf(element.get(4))));
				bean.setCOM(String.valueOf(element.get(5)));
				bean.setLD_NAME(String.valueOf(element.get(6)));
				bean.setPARENT_PRG_CODE(String.valueOf(element.get(7)));
				bean.setMENU_KBN(Boolean.parseBoolean(String.valueOf(element.get(8))));
				bean.setDISP_INDEX(Integer.parseInt(String.valueOf(element.get(9))));
				resList.add(bean);
			}

		}

		return result;
	}

	/**
	 * ���N�G�X�g���ʂ�List���擾
	 * 
	 * @return List��Bean�ɕϊ��������X�g
	 */
	public List getMenuList() {
		if (resList == null) {
			throw new IllegalStateException("List is Null.");
		}
		return resList;
	}

}
