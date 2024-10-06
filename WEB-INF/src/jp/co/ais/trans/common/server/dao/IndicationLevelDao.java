package jp.co.ais.trans.common.server.dao;

/**
 * �J�����x�����Dao
 */
public interface IndicationLevelDao {

	/** �Ώ�Entity */
	public Class BEAN = IndicationLevel.class;

	/** �J�����x�����擾 */
	public String getIndicationLevel_ARGS = "kaiCode, usrId, kmtCode, dpkSsk";

	/**
	 * �J�����x�����擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param usrId ���[�U�R�[�h
	 * @param kmtCode �Ȗڑ̌n�R�[�h
	 * @param dpkSsk �g�D�R�[�h
	 * @return �J�����x��
	 */
	public IndicationLevel getIndicationLevel(String kaiCode, String usrId, String kmtCode, String dpkSsk);

}