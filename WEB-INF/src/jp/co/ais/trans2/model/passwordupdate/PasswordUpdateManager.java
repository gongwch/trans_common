package jp.co.ais.trans2.model.passwordupdate;

import jp.co.ais.trans.common.except.*;

/**
 * MG0025 - �p�X���[�h�ύX
 * 
 * @author AIS
 */
public interface PasswordUpdateManager {

	/**
	 * ���C�� (UPDATE)
	 * 
	 * @param bean ���͏��
	 * @throws TException
	 */
	public void modify(PasswordUpdate bean) throws TException;

}