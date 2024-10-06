package jp.co.ais.trans2.model.balance;

import java.util.*;

/**
 * ���c��Dao
 */
public interface AR_ZANDao {

	/** Bean */
	public Class BEAN = AR_ZAN.class;

	/** �L�[���� */
	public String findByPrimaryKey_QUERY = "KAI_CODE = ? AND ZAN_SEI_DEN_NO = ? AND ZAN_SEI_GYO_NO = ? AND ZAN_KESI_DEN_NO IS NULL";

	/**
	 * �L�[����
	 * �����s�ł͂Ȃ����̃f�[�^���擾
	 * 
	 * @param compCode ��ЃR�[�h
	 * @param slipNo �`�[�ԍ�
	 * @param slipLineNo �s�ԍ�
	 * @return ���c��
	 */
	public AR_ZAN findByPrimaryKey(String compCode, String slipNo, int slipLineNo);

	/**
	 * �W�v����
	 * 
	 * @param param �����p�����[�^
	 * @return ���ʃ��X�g
	 */
	public List<AR_ZAN> findSummry(BalanceCondition param);

	/**
	 * ����
	 * 
	 * @param param �����p�����[�^
	 * @return ���ʃ��X�g
	 */
	public List<AR_ZAN> findByCondition(BalanceCondition param);

	/**
	 * ����(�������)�`�F�b�N�p����
	 * 
	 * @param param �����p�����[�^
	 * @return ���ʃ��X�g
	 */
	public List<AR_ZAN> findForExists(BalanceCondition param);

	/** �폜 */
	public String deleteBySlipNo_QUERY = "KAI_CODE = ? AND ZAN_KESI_DEN_NO = ?";

	/**
	 * �폜
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param eraseSlipNo �����`�[�ԍ�
	 */
	public void deleteBySlipNo(String companyCode, String eraseSlipNo);

	/**
	 * �o�^
	 * 
	 * @param entity �G���e�B�e�B
	 */
	public void insert(AR_ZAN entity);

}
