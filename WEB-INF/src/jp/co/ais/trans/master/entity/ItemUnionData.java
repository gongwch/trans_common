package jp.co.ais.trans.master.entity;


/**
 * 科目統合情報データ
 */
public class ItemUnionData extends MasterBase {

	/** 会社コード */
	private String kAI_CODE = "";

	/** 科目情報 */
	private KMK_MST kMK_MST;

	/** 補助科目情報 */
	private HKM_MST hKM_MST;

	/** 内訳科目情報 */
	private UKM_MST uKM_MST;

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * 会社コード
	 * 
	 * @param kai_code
	 */
	public void setKAI_CODE(String kai_code) {
		kAI_CODE = kai_code;
	}

	/**
	 * 科目情報
	 * 
	 * @return 科目情報
	 */
	public KMK_MST getKMK_MST() {
		return kMK_MST;
	}

	/**
	 * 科目情報
	 * 
	 * @param kmk_mst
	 */
	public void setKMK_MST(KMK_MST kmk_mst) {
		kMK_MST = kmk_mst;
	}

	/**
	 * 補助科目情報
	 * 
	 * @return 補助科目情報
	 */
	public HKM_MST getHKM_MST() {
		return hKM_MST;
	}

	/**
	 * 補助科目情報
	 * 
	 * @param hkm_mst
	 */
	public void setHKM_MST(HKM_MST hkm_mst) {
		hKM_MST = hkm_mst;
	}

	/**
	 * 内訳科目情報
	 * 
	 * @return 内訳科目情報
	 */
	public UKM_MST getUKM_MST() {
		return uKM_MST;
	}

	/**
	 * 内訳科目情報
	 * 
	 * @param ukm_mst
	 */
	public void setUKM_MST(UKM_MST ukm_mst) {
		uKM_MST = ukm_mst;
	}

}
