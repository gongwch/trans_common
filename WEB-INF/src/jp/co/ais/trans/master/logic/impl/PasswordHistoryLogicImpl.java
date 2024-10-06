package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �p�X���[�h�����r�W�l�X���W�b�N
 * 
 * @author roh
 */
public class PasswordHistoryLogicImpl implements PasswordHistoryLogic {

	/** �p�X���[�h�Ǘ��}�X�^Dao */
	private PWD_HST_TBLDao dao;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao PWD_HST_TBLDao
	 */
	public PasswordHistoryLogicImpl(PWD_HST_TBLDao dao) {
		this.dao = dao;
	}

	/**
	 * �����f�[�^����.<br>
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param usrCode ���[�U�R�[�h
	 * @return �����f�[�^
	 */
	public List<PWD_HST_TBL> findPassword(String kaiCode, String usrCode) {

		return dao.findPassword(kaiCode, usrCode);
	}

	/**
	 * ����V�K
	 * 
	 * @param pwdDto
	 */
	public void insert(PWD_HST_TBL pwdDto) {
		dao.insert(pwdDto);
	}

}
