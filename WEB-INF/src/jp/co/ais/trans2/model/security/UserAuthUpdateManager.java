package jp.co.ais.trans2.model.security;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ���[�U�F�؃}�X�^�}�l�[�W�� �C���^�[�t�F�[�X
 */

public interface UserAuthUpdateManager {

	/**
	 * �m��
	 * 
	 * @param �X�V
	 */
	public void modify(USR_AUTH_MST userauthupdate) throws TException;

	/**
	 * �m��
	 * 
	 * @param �����f�[�^�̑��݃`�F�b�N
	 */
	public USR_AUTH_MST get() throws TException;
}
