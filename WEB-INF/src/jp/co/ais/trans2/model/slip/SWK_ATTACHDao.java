package jp.co.ais.trans2.model.slip;

/**
 * �`�[�Y�tDao<br>
 * SQL�p�~
 */
@Deprecated
public interface SWK_ATTACHDao {

	/** BEAN */
	public Class BEAN = SWK_ATTACH.class;

	/**
	 * �V�K�쐬
	 * 
	 * @param entity
	 * @return ����
	 */
	public int insert(SWK_ATTACH entity);

	/** ���� */
	public String delete_ARGS = "kAI_CODE, sWK_DEN_NO, sEQ";

	/**
	 * ����
	 * 
	 * @param kAI_CODE ��ЃR�[�h
	 * @param sWK_DEN_NO �`�[�ԍ�
	 * @param sEQ �s�ԍ�
	 * @return ����
	 */
	public int delete(String kAI_CODE, String sWK_DEN_NO, Integer sEQ);

	/** ���� */
	public String updateSeq_ARGS = "kAI_CODE, sWK_DEN_NO";

	/**
	 * SEQ�X�V
	 * 
	 * @param kAI_CODE ��ЃR�[�h
	 * @param sWK_DEN_NO �`�[�ԍ�
	 * @return ����
	 */
	public int updateSeq(String kAI_CODE, String sWK_DEN_NO);
}
