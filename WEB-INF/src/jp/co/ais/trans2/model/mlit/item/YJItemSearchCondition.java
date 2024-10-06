package jp.co.ais.trans2.model.mlit.item;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * 輸送実績アイテムマスタの検索条件
 * 
 * @author AIS
 */
public class YJItemSearchCondition extends TransferBase implements Cloneable {

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public YJItemSearchCondition clone() {
		try {
			return (YJItemSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/** 会社コード */
	protected String companyCode = null;

	/** アイテムコード */
	protected String itemCode = null;

	/** サブアイテムコード */
	protected String subItemCode = null;

	/** 開始アイテムコード */
	protected String itemCodeFrom = null;

	/** 終了アイテムコード */
	protected String itemCodeTo = null;

	/** 開始サブアイテムコード */
	protected String subItemCodeFrom = null;

	/** 終了サブアイテムコード */
	protected String subItemCodeTo = null;

	/** アイテムコード前方一致 */
	protected String itemCodeLike = null;

	/** アイテム名称like */
	protected String itemNameLike = null;

	/** アイテムコード前方一致 */
	protected String subItemCodeLike = null;

	/** アイテム名称like */
	protected String subItemNameLike = null;

	/**
	 * 会社コードの取得
	 * 
	 * @return companyCode 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * アイテムコードの取得
	 * 
	 * @return itemCode アイテムコード
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * アイテムコードの設定
	 * 
	 * @param itemCode アイテムコード
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * サブアイテムコードの取得
	 * 
	 * @return subItemCode サブアイテムコード
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * サブアイテムコードの設定
	 * 
	 * @param subItemCode サブアイテムコード
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * 開始アイテムコードの取得
	 * 
	 * @return itemCodeFrom 開始アイテムコード
	 */
	public String getItemCodeFrom() {
		return itemCodeFrom;
	}

	/**
	 * 開始アイテムコードの設定
	 * 
	 * @param itemCodeFrom 開始アイテムコード
	 */
	public void setItemCodeFrom(String itemCodeFrom) {
		this.itemCodeFrom = itemCodeFrom;
	}

	/**
	 * 終了アイテムコードの取得
	 * 
	 * @return itemCodeTo 終了アイテムコード
	 */
	public String getItemCodeTo() {
		return itemCodeTo;
	}

	/**
	 * 終了アイテムコードの設定
	 * 
	 * @param itemCodeTo 終了アイテムコード
	 */
	public void setItemCodeTo(String itemCodeTo) {
		this.itemCodeTo = itemCodeTo;
	}

	/**
	 * 開始サブアイテムコードの取得
	 * 
	 * @return subItemCodeFrom 開始サブアイテムコード
	 */
	public String getSubItemCodeFrom() {
		return subItemCodeFrom;
	}

	/**
	 * 開始サブアイテムコードの設定
	 * 
	 * @param subItemCodeFrom 開始サブアイテムコード
	 */
	public void setSubItemCodeFrom(String subItemCodeFrom) {
		this.subItemCodeFrom = subItemCodeFrom;
	}

	/**
	 * 終了サブアイテムコードの取得
	 * 
	 * @return subItemCodeTo 終了サブアイテムコード
	 */
	public String getSubItemCodeTo() {
		return subItemCodeTo;
	}

	/**
	 * 終了サブアイテムコードの設定
	 * 
	 * @param subItemCodeTo 終了サブアイテムコード
	 */
	public void setSubItemCodeTo(String subItemCodeTo) {
		this.subItemCodeTo = subItemCodeTo;
	}

	/**
	 * アイテムコード前方一致の取得
	 * 
	 * @return itemCodeLike アイテムコード前方一致
	 */
	public String getItemCodeLike() {
		return itemCodeLike;
	}

	/**
	 * アイテムコード前方一致の設定
	 * 
	 * @param itemCodeLike アイテムコード前方一致
	 */
	public void setItemCodeLike(String itemCodeLike) {
		this.itemCodeLike = itemCodeLike;
	}

	/**
	 * アイテム名称likeの取得
	 * 
	 * @return itemNameLike アイテム名称like
	 */
	public String getItemNameLike() {
		return itemNameLike;
	}

	/**
	 * アイテム名称likeの設定
	 * 
	 * @param itemNameLike アイテム名称like
	 */
	public void setItemNameLike(String itemNameLike) {
		this.itemNameLike = itemNameLike;
	}

	/**
	 * サブアイテムコード前方一致の取得
	 * 
	 * @return subItemCodeLike サブアイテムコード前方一致
	 */
	public String getSubItemCodeLike() {
		return subItemCodeLike;
	}

	/**
	 * サブアイテムコード前方一致の設定
	 * 
	 * @param subItemCodeLike サブアイテムコード前方一致
	 */
	public void setSubItemCodeLike(String subItemCodeLike) {
		this.subItemCodeLike = subItemCodeLike;
	}

	/**
	 * サブアイテム名称likeの取得
	 * 
	 * @return subItemNameLike サブアイテム名称like
	 */
	public String getSubItemNameLike() {
		return subItemNameLike;
	}

	/**
	 * サブアイテム名称likeの設定
	 * 
	 * @param subItemNameLike サブアイテム名称like
	 */
	public void setSubItemNameLike(String subItemNameLike) {
		this.subItemNameLike = subItemNameLike;
	}
}
