package jp.co.ais.trans2.model.item;

/**
 * 補助科目マスタ検索条件
 * 
 * @author AIS
 */
public class SubItemSearchCondition extends ItemSearchCondition {

	/** 科目コード */
	protected String itemCode = null;

	/** 補助科目が存在しない科目も抽出するか */
	protected boolean getNotExistSubItem = false;

	/** 内訳科目を含むか */
	protected boolean detailItem = false;

	/**
	 * 補助科目が存在しない科目も抽出するかを返す。<br>
	 * trueの場合、補助科目が無くとも科目だけのEntityを返す。
	 * 
	 * @return 補助科目が存在しない科目も抽出するか
	 */
	public boolean isGetNotExistSubItem() {
		return getNotExistSubItem;
	}

	/**
	 * 補助科目が存在しない科目も抽出するかを設定する。<br>
	 * trueの場合、補助科目が無くとも科目だけのEntityを返す。
	 * 
	 * @param getNotExistSubItem 補助科目が存在しない科目も抽出するか
	 */
	public void setGetNotExistSubItem(boolean getNotExistSubItem) {
		this.getNotExistSubItem = getNotExistSubItem;
	}

	/**
	 * 科目コードgetter
	 * 
	 * @return 科目コード
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * 科目コードsetter
	 * 
	 * @param itemCode 科目コード
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public SubItemSearchCondition clone() {
		return (SubItemSearchCondition) super.clone();
	}

	/**
	 * 内訳科目を含むか
	 * 
	 * @return detailItem
	 */
	public boolean isDetailItem() {
		return detailItem;
	}

	/**
	 * 内訳科目を含むか
	 * 
	 * @param detailItem
	 */
	public void setDetailItem(boolean detailItem) {
		this.detailItem = detailItem;
	}

}
