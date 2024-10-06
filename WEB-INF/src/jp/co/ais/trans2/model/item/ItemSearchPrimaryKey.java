package jp.co.ais.trans2.model.item;

import jp.co.ais.trans.common.dt.*;

/**
 * 科目・補助・内訳マスタ共通<br>
 * 検索プライマリキー
 */
public class ItemSearchPrimaryKey extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String KAI_CODE;

	/** 科目コード */
	protected String KMK_CODE;

	/** 補助コード */
	protected String HKM_CODE;

	/** 内訳コード */
	protected String UKM_CODE;

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected ItemSearchPrimaryKey clone() throws CloneNotSupportedException {
		return (ItemSearchPrimaryKey) super.clone();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param kAI_CODE
	 * @param kMK_CODE
	 * @param hKM_CODE
	 * @param uKM_CODE
	 */
	public ItemSearchPrimaryKey(String kAI_CODE, String kMK_CODE, String hKM_CODE, String uKM_CODE) {
		super();
		KAI_CODE = kAI_CODE;
		KMK_CODE = kMK_CODE;
		HKM_CODE = hKM_CODE;
		UKM_CODE = uKM_CODE;
	}

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * 会社コード
	 * 
	 * @param kAI_CODE
	 */
	public void setKAI_CODE(String kAI_CODE) {
		KAI_CODE = kAI_CODE;
	}

	/**
	 * 科目コード
	 * 
	 * @return 科目コード
	 */
	public String getKMK_CODE() {
		return KMK_CODE;
	}

	/**
	 * 科目コード
	 * 
	 * @param kMK_CODE
	 */
	public void setKMK_CODE(String kMK_CODE) {
		KMK_CODE = kMK_CODE;
	}

	/**
	 * 補助コード
	 * 
	 * @return 補助コード
	 */
	public String getHKM_CODE() {
		return HKM_CODE;
	}

	/**
	 * 補助コード
	 * 
	 * @param hKM_CODE
	 */
	public void setHKM_CODE(String hKM_CODE) {
		HKM_CODE = hKM_CODE;
	}

	/**
	 * 内訳コード
	 * 
	 * @return 内訳コード
	 */
	public String getUKM_CODE() {
		return UKM_CODE;
	}

	/**
	 * 内訳コード
	 * 
	 * @param uKM_CODE
	 */
	public void setUKM_CODE(String uKM_CODE) {
		UKM_CODE = uKM_CODE;
	}

}
