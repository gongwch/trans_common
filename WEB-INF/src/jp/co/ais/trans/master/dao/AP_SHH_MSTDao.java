package jp.co.ais.trans.master.dao;

import java.util.List;

import jp.co.ais.trans.master.entity.AP_SHH_MST;

/**
 * �x�����j�}�X�^Dao
 */
public interface AP_SHH_MSTDao {

	/** */
	public Class BEAN = AP_SHH_MST.class;

	/**
	 * �S�̏K��
	 * 
	 * @return List
	 */
	public List getAllAP_SHH_MST();

	/** �p�����[�^ */
	public String getAP_SHH_MST_QUERY = "KAI_CODE = ?";

	/**
	 * �x�����j�擾
	 * 
	 * @param KAI_CODE
	 * @return �x�����jBean
	 */
	public AP_SHH_MST getAP_SHH_MST(String KAI_CODE);

	/** �p�����[�^ */
	public String getAP_SHH_MSTByIds_ARGS = "KAI_CODEs";

	/** �p�����[�^ */
	public String getAP_SHH_MSTByIds_QUERY = "KAI_CODE in /*kAI_CODEs*/(1)";

	/**
	 * ��ЃR�[�h���X�g�Ŏx�����j���X�g�擾
	 * 
	 * @param kAI_CODEs
	 * @return List
	 */
	public List getAP_SHH_MSTByIds(List kAI_CODEs);

	/**
	 * �o�^
	 * 
	 * @param dto
	 */
	public void insert(AP_SHH_MST dto);

	/**
	 * �X�V
	 * 
	 * @param dto
	 */
	public void update(AP_SHH_MST dto);

	/**
	 * �폜
	 * 
	 * @param dto
	 */
	public void delete(AP_SHH_MST dto);

	// ���L�� ISFnet China �ǉ���

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllAP_SHH_MST2(String kaiCode);

}
