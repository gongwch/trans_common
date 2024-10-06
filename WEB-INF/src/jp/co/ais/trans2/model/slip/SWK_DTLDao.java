package jp.co.ais.trans2.model.slip;

import java.util.*;

/**
 * �d�󖾍�Dao
 */
public interface SWK_DTLDao {

	/** Entity */
	public Class BEAN = SWK_DTL.class;

	/**
	 * �ǉ�
	 * 
	 * @param list �f�[�^���X�g
	 */
	public void insert(List<SWK_DTL> list);

	/**
	 * �����ɂ��폜
	 * 
	 * @param param ����
	 */
	public void deleteByCondition(SlipCondition param);

	/**
	 * �����ɂ�錟��
	 * 
	 * @param param ����
	 * @return ���׃��X�g
	 */
	public List<SWK_DTL> findByCondition(SlipCondition param);

	/**
	 * �����ɂ�������ԍX�V
	 * 
	 * @param entity �ΏۃG���e�B�e�B
	 * @return �X�V����
	 */
	public int updateEraseState(SWK_DTL entity);

	/**
	 * �����ɂ�������ԍX�V
	 * 
	 * @param param ����
	 * @return �X�V����
	 */
	public int updateEraseState2(SlipCondition param);

	/** �p�����[�^ */
	public String getDtl_QUERY = "KAI_CODE = ? AND SWK_DEN_NO = ? AND SWK_GYO_NO = ? AND SWK_ADJ_KBN = 0";

	/**
	 * @param companyCode
	 * @param slipNo
	 * @param rowNo
	 * @return SWK_DTL
	 */
	public SWK_DTL getDtl(String companyCode, String slipNo, int rowNo);

}
