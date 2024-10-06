package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ���b�N�A�E�g�r�W�l�X���W�b�N
 * 
 * @author roh
 */
public interface LockOutLogic {

	/**
	 * ���b�N�A�E�g�f�[�^�擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param arrCount ���s�񐔐���
	 * @return ���b�N�A�E�g���X�g
	 */
	public abstract List<LOCK_OUT_TBL> findWithUserName(String kaiCode, int arrCount);

	/**
	 * ���b�N�A�E�g������
	 * 
	 * @param entityList �G���e�B�e�B���X�g
	 */
	public abstract void delete(List<LOCK_OUT_TBL> entityList);

}
