package jp.co.ais.trans.common.server.dao;

/**
 * �Ј��}�X�^Dao(���o�[�W����).<br>
 * KT�̕��𗘗p���邱�ƁB
 */
public interface EMP_MSTDao {

	/** Entity */
	public static final Class BEAN = EMP_MST.class;

	/** getEMP_MST ARGS */
	public static final String getEMP_MST_ARGS = "kai_code, emp_code";

	/**
	 * �Ј����
	 * 
	 * @param kai_code ��ЃR�[�h
	 * @param emp_code �Ј��R�[�h
	 * @return �Ј����
	 */
	public EMP_MST getEMP_MST(String kai_code, String emp_code);
}
