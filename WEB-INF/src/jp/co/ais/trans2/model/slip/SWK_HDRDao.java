package jp.co.ais.trans2.model.slip;

import java.util.*;

/**
 * �d��w�b�_�[Dao(����)
 */
public interface SWK_HDRDao {

	/**
	 * �o�^
	 * 
	 * @param entity �ΏۃG���e�B�e�B
	 */
	void insert(SWK_HDR entity);

	/**
	 * �����ɂ��폜
	 * 
	 * @param param ����
	 */
	void deleteByCondition(SlipCondition param);

	/**
	 * �����ɂ�錟��
	 * 
	 * @param param ����
	 * @return ���ʃ��X�g
	 */
	List<SWK_HDR> findByCondition(SlipCondition param);

	/**
	 * �r����ԕύX
	 * 
	 * @param param ����
	 */
	void updateLockState(SlipCondition param);

	/** LOCK���m�F�p */
	public String getLockInfo_ARGS = "companyCode, slipNo";

	/**
	 * LOCK���m�F�pSWK_SHR_KBN��SWK_UPD_KBN��Ԃ�
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @return LOCK���m�F�pSWK_SHR_KBN��SWK_UPD_KBN
	 */
	SWK_HDR getLockInfo(String companyCode, String slipNo);
}
