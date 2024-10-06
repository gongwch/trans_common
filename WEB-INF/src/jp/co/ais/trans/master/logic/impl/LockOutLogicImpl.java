package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * ���b�N�A�E�g�}�X�^���W�b�N
 */
public class LockOutLogicImpl implements LockOutLogic {

	/** ���b�N�A�E�g�}�X�^Dao */
	private LOCK_OUT_TBLDao dao;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao LOCK_OUT_TBL
	 */
	public LockOutLogicImpl(LOCK_OUT_TBLDao dao) {

		// �����}�X�^Dao��Ԃ�
		this.dao = dao;
	}

	/**
	 * ���b�N�A�E�g������
	 * 
	 * @param entityList �G���e�B�e�B���X�g
	 */
	public void delete(List<LOCK_OUT_TBL> entityList) {

		// ���b�N�A�E�g���X�g�폜
		for (LOCK_OUT_TBL row : entityList) {
			dao.delete(row);
		}
	}

	/**
	 * ���b�N�A�E�g�f�[�^�擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param arrCount ���s�񐔐���
	 * @return ���b�N�A�E�g���X�g
	 */
	public List<LOCK_OUT_TBL> findWithUserName(String kaiCode, int arrCount) {
		return dao.findWithUserName(kaiCode, arrCount);
	}

}
