package jp.co.ais.trans.common.server.dao;

/**
 * ���ꏳ�FFLAG�擾�pDao(���o�[�W����).<br>
 * KT�̕��𗘗p���邱�ƁB
 */
public interface CMP_MSTDao {

	/** �Ώ�Bean */
	public Class BEAN = CMP_MST.class;

	/** ARGS */
	public static final String getCMP_MSTByKaicode_ARGS = "kaiCode";

	/**
	 * �f�[�^�擾�i�w��j
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @return 0 or 1 (CMP_G_SHONIN_FLG)
	 */
	public int getCMP_MSTByKaicode(String kaiCode);

	/**
	 * �f�[�^�擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @return CMP_MST
	 */
	public CMP_MST getCMP_MSTData(String kaiCode);

}
