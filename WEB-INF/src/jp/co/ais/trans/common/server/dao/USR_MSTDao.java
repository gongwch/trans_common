package jp.co.ais.trans.common.server.dao;

/**
 * ���[�U�[�V�X�e���敪�擾�p Dao
 */
public interface USR_MSTDao {

	/** Entity */
	public Class BEAN = USR_MST.class;

	/** getUSR_MSTByKaicodeUsrcode ARGS */
	public String getUSR_MSTByKaicodeUsrcode_ARGS = "KAI_CODE,USR_CODE";

	/**
	 * �w�肳�ꂽ�f�[�^�̎擾(���[�U��)
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param usrCode ���[�U�R�[�h
	 * @return ���[�U
	 */
	public USR_MST getUSR_MSTByKaicodeUsrcode(String kaiCode, String usrCode);

}