package jp.co.ais.trans2.model.item.summary;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目集計Entity
 * 
 * @author AIS
 */
public class ItemSummaryDisp extends TransferBase implements Cloneable {

	/** 会社コード */
	protected String companyCode = null;

	/** 親プログラムコード */
	protected String parentCode = null;

	/** コード */
	protected String code = null;

	/** 科目グループに属するプログラム群 */
	protected Item item = null;

	/** 科目集計グループに属するプログラム群 */
	protected ItemSummary itemSummary = null;

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ItemSummaryDisp clone() {
		try {
			ItemSummaryDisp bean = (ItemSummaryDisp) super.clone();
			bean.setItem(this.item);
			bean.setItemSummary(this.itemSummary);
			return bean;
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * ノードに表示する名称を取得する
	 * 
	 * @return ノードに表示する名称
	 */
	public String getViewName() {
		return this.toString();
	}

	/**
	 * オブジェクトのオリジナル文字表現を返す.
	 * 
	 * @return 名称
	 */
	@Override
	public String toString() {
		if (itemSummary == null) {
			return "";
		}
		return itemSummary.getKmkCode() + " " + itemSummary.getKokName() + " ( " + itemSummary.getOdr() + " ) ";
	}

	/**
	 * エンティティの比較に使用する。
	 * 
	 * @param obj 比較
	 * @return boolean
	 */
	public boolean equals(ItemSummaryDisp obj) {
		if (super.equals(obj)) {
			return true;
		} else {
			return this.code.equals(obj.getCode());
		}
	}

	/**
	 * 会社コードを取得する。
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードを設定する。
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 親プログラムコードを取得する。
	 * 
	 * @return 親プログラムコード
	 */
	public String getParentCode() {
		return parentCode;
	}

	/**
	 * 親プログラムコードを設定する。
	 * 
	 * @param parentCode
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	/**
	 * 科目コードを取得する。
	 * 
	 * @return 科目コード
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 科目コードを設定する。
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 科目グループ情報を取得する。
	 * 
	 * @return 科目グループ
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * 科目グループを設定する。
	 * 
	 * @param item
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * 科目集計グループ情報を取得する。
	 * 
	 * @return 科目集計グループ
	 */
	public ItemSummary getItemSummary() {
		return itemSummary;
	}

	/**
	 * 科目集計グループを設定する。
	 * 
	 * @param itemSummary
	 */
	public void setItemSummary(ItemSummary itemSummary) {
		this.itemSummary = itemSummary;
	}
}
