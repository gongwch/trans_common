package jp.co.ais.trans2.model.balance;

import java.util.*;

/**
 * ���c��Dao
 * 
 * @author AIS
 */
public interface AP_ZANDao {

	/** Bean */
	public Class BEAN = AP_ZAN.class;

	/** �L�[���� */
	public String findByPrimaryKey_ARGS = "KAI_CODE, ZAN_DEN_NO, ZAN_GYO_NO";

	/**
	 * �L�[����
	 * 
	 * @param compCode ��ЃR�[�h
	 * @param slipNo �`�[�ԍ�
	 * @param slipLineNo �s�ԍ�
	 * @return ���c��
	 */
	public AP_ZAN findByPrimaryKey(String compCode, String slipNo, int slipLineNo);

	/**
	 * ����
	 * 
	 * @param param �����p�����[�^
	 * @return ���ʃ��X�g
	 */
	public List<AP_ZAN> findByCondition(BalanceCondition param);

	/**
	 * ����(�������)�`�F�b�N�p����
	 * 
	 * @param param �����p�����[�^
	 * @return ���ʃ��X�g
	 */
	public List<AP_ZAN> findForExists(BalanceCondition param);

	/**
	 * �x���ς݂ɂ���
	 * 
	 * @param param ����
	 */
	public void updateBalance(BalanceCondition param);

	/**
	 * �x���ς݂�������
	 * 
	 * @param param ����
	 */
	public void updateCancelBalance(BalanceCondition param);

	/**
	 * �o�^
	 * 
	 * @param entity �G���e�B�e�B
	 */
	public void insert(AP_ZAN entity);

	/**
	 * �o�^
	 * 
	 * @param entity �G���e�B�e�B
	 */
	public void insertByUpdateDate(AP_ZAN entity);

	/**
	 * �X�V
	 * 
	 * @param entity �G���e�B�e�B
	 * @param sysDate �V�X�e�����t
	 * @return int
	 */
	public int updateByUpdateDate(AP_ZAN entity, Date sysDate);

	/** �X�V */
	public String updateByUpdateDate_ARGS = "entity,sysDate";

	/**
	 * �폜
	 * 
	 * @param entity �c�����X�g
	 * @return int
	 */
	public int deleteByUpdateDate(AP_ZAN entity);

}
