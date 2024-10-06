package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans.common.dt.*;

/**
 * �e���|�����[�e�[�u�����ݒ�N���X
 */
public interface TempTableNameGetterSetting {

	/**
	 * ���[�N�e�[�u�����̎擾
	 * 
	 * @param bean
	 * @return T_BALANCE_WORK�e�[�u����
	 */
	public String getBalanceWorkTableName(TransferBase bean);

	/**
	 * �ꎞ�e�[�u�����̎擾
	 * 
	 * @param bean
	 * @param defaultTableName �f�t�H���g�ꎞ�e�[�u��ID
	 * @return �e�v���O�����Ŏg���ꎞ�e�[�u����
	 */
	public String getTemporaryWorkTableName(TransferBase bean, String defaultTableName);

}
