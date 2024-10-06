package jp.co.ais.trans2.model.item;

/**
 * 内訳科目マスタ検索条件
 * 
 * @author AIS
 */
public class DetailItemSearchCondition extends SubItemSearchCondition {

	/** 補助科目コード */
	protected String subItemCode = null;

	/** 内訳科目が存在しない科目も抽出するか */
	protected boolean getNotExistDetailItem = false;

	/**
	 * 内訳科目が存在しない科目も抽出するかを返す。<br>
	 * trueの場合、内訳科目が無くとも科目だけのEntityを返す。
	 * 
	 * @return 内訳科目が存在しない科目も抽出するか
	 */
	public boolean isGetNotExistDetailItem() {
		return getNotExistDetailItem;
	}

	/**
	 * 内訳科目が存在しない科目も抽出するかを設定する。<br>
	 * trueの場合、内訳科目が無くとも科目だけのEntityを返す。
	 * 
	 * @param getNotExistDetailItem 内訳科目が存在しない科目も抽出するか
	 */
	public void setGetNotExistDetailItem(boolean getNotExistDetailItem) {
		this.getNotExistDetailItem = getNotExistDetailItem;
	}

	/**
	 * 補助科目コードgetter
	 * 
	 * @return 補助科目コード
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * 補助科目コードsetter
	 * 
	 * @param subItemCode 補助科目コード
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public DetailItemSearchCondition clone() {
		return (DetailItemSearchCondition) super.clone();
	}

}
