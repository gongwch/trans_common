package jp.co.ais.trans.master.entity;


/**
 * �Ȗړ������f�[�^
 */
public class ItemUnionData extends MasterBase {

	/** ��ЃR�[�h */
	private String kAI_CODE = "";

	/** �Ȗڏ�� */
	private KMK_MST kMK_MST;

	/** �⏕�Ȗڏ�� */
	private HKM_MST hKM_MST;

	/** ����Ȗڏ�� */
	private UKM_MST uKM_MST;

	/**
	 * ��ЃR�[�h
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @param kai_code
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * �Ȗڏ��
	 * 
	 * @return �Ȗڏ��
	 */
	public KMK_MST getKMK_MST() {
		return kMK_MST;
	}

	/**
	 * �Ȗڏ��
	 * 
	 * @param kmk_mst
	 */
	public void setKMK_MST(KMK_MST kmk_mst) {
		kMK_MST = kmk_mst;
	}

	/**
	 * �⏕�Ȗڏ��
	 * 
	 * @return �⏕�Ȗڏ��
	 */
	public HKM_MST getHKM_MST() {
		return hKM_MST;
	}

	/**
	 * �⏕�Ȗڏ��
	 * 
	 * @param hkm_mst
	 */
	public void setHKM_MST(HKM_MST hkm_mst) {
		hKM_MST = hkm_mst;
	}

	/**
	 * ����Ȗڏ��
	 * 
	 * @return ����Ȗڏ��
	 */
	public UKM_MST getUKM_MST() {
		return uKM_MST;
	}

	/**
	 * ����Ȗڏ��
	 * 
	 * @param ukm_mst
	 */
	public void setUKM_MST(UKM_MST ukm_mst) {
		uKM_MST = ukm_mst;
	}

}
