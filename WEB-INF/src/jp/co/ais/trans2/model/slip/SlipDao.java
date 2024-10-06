package jp.co.ais.trans2.model.slip;

import java.util.*;

/**
 * �`�[�pDao
 */
public interface SlipDao {

	/** Entity */
	public Class BEAN = SWK_HDR.class;

	/**
	 * �����ɂ�錟��
	 * 
	 * @param param ����
	 * @return ���ʃ��X�g
	 */
	List<SWK_HDR> findByCondition(SlipCondition param);

	/**
	 * �����ɂ��p�^�[������
	 * 
	 * @param param ����
	 * @return ���ʃ��X�g
	 */
	List<SWK_HDR> findPatternByCondition(SlipCondition param);

	/**
	 * �����ɂ�闚������
	 * 
	 * @param param ����
	 * @return ���ʃ��X�g
	 */
	List<SWK_HDR> findHistoryByCondition(SlipCondition param);

	/**
	 * �����ɂ�錟��
	 * 
	 * @param param ����
	 * @return ���ʃ��X�g
	 */
	List<Slip> findSlipByCondition(SlipCondition param);

	/**
	 * �����ɂ��p�^�[������
	 * 
	 * @param param ����
	 * @return ���ʃ��X�g
	 */
	List<Slip> findSlipPatternByCondition(SlipCondition param);

	/**
	 * �����ɂ�闚������
	 * 
	 * @param param ����
	 * @return ���ʃ��X�g
	 */
	List<Slip> findSlipHistoryByCondition(SlipCondition param);

}
