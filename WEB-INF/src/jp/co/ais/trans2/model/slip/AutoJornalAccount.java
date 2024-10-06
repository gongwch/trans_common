package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * 自動仕訳科目
 */
public class AutoJornalAccount extends TransferBase {

	/** 会社コード */
	protected String companyCode;

	/** 科目制御区分 */
	protected int kind;

	/** 科目制御区分名称 */
	protected String kindName;

	/** 部門コード */
	protected String depertmentCode;

	/** 部門名称 */
	protected String depertmentName;

	/** 部門略称 */
	protected String depertmentNames;

	/** 科目コード */
	protected String itemCode;

	/** 科目名称 */
	protected String itemName;

	/** 科目略称 */
	protected String itemNames;

	/** 補助科目コード */
	protected String subItemCode;

	/** 補助科目名称 */
	protected String subItemName;

	/** 補助科目略称 */
	protected String subItemNames;

	/** 内訳科目コード */
	protected String detailItemCode;

	/** 内訳科目名称 */
	protected String detailItemName;

	/** 内訳科目略称 */
	protected String detailItemNames;

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コード
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 部門コード
	 * 
	 * @return 部門コード
	 */
	public String getDepertmentCode() {
		return depertmentCode;
	}

	/**
	 * 部門コード
	 * 
	 * @param depertmentCode 部門コード
	 */
	public void setDepertmentCode(String depertmentCode) {
		this.depertmentCode = depertmentCode;
	}

	/**
	 * 部門名称
	 * 
	 * @return 部門名称
	 */
	public String getDepertmentName() {
		return depertmentName;
	}

	/**
	 * 部門名称
	 * 
	 * @param depertmentName 部門名称
	 */
	public void setDepertmentName(String depertmentName) {
		this.depertmentName = depertmentName;
	}

	/**
	 * 部門略称
	 * 
	 * @return 部門略称
	 */
	public String getDepertmentNames() {
		return depertmentNames;
	}

	/**
	 * 部門略称
	 * 
	 * @param depertmentNames 部門略称
	 */
	public void setDepertmentNames(String depertmentNames) {
		this.depertmentNames = depertmentNames;
	}

	/**
	 * 内訳科目コード
	 * 
	 * @return 内訳科目コード
	 */
	public String getDetailItemCode() {
		return detailItemCode;
	}

	/**
	 * 内訳科目コード
	 * 
	 * @param detailItemCode 内訳科目コード
	 */
	public void setDetailItemCode(String detailItemCode) {
		this.detailItemCode = detailItemCode;
	}

	/**
	 * 内訳科目名称
	 * 
	 * @return 内訳科目名称
	 */
	public String getDetailItemName() {
		return detailItemName;
	}

	/**
	 * 内訳科目名称
	 * 
	 * @param detailItemName 内訳科目名称
	 */
	public void setDetailItemName(String detailItemName) {
		this.detailItemName = detailItemName;
	}

	/**
	 * 内訳科目略称
	 * 
	 * @return 内訳科目略称
	 */
	public String getDetailItemNames() {
		return detailItemNames;
	}

	/**
	 * 内訳科目略称
	 * 
	 * @param detailItemNames 内訳科目略称
	 */
	public void setDetailItemNames(String detailItemNames) {
		this.detailItemNames = detailItemNames;
	}

	/**
	 * 科目コード
	 * 
	 * @return 科目コード
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * 科目コード
	 * 
	 * @param itemCode 科目コード
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * 科目名称
	 * 
	 * @return 科目名称
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * 科目名称
	 * 
	 * @param itemName 科目名称
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 科目略称
	 * 
	 * @return 科目略称
	 */
	public String getItemNames() {
		return itemNames;
	}

	/**
	 * 科目略称
	 * 
	 * @param itemNames 科目略称
	 */
	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}

	/**
	 * 補助科目コード
	 * 
	 * @return 補助科目コード
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * 補助科目コード
	 * 
	 * @param subItemCode 補助科目コード
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * 補助科目名称
	 * 
	 * @return 補助科目名称
	 */
	public String getSubItemName() {
		return subItemName;
	}

	/**
	 * 補助科目名称
	 * 
	 * @param subItemName 補助科目名称
	 */
	public void setSubItemName(String subItemName) {
		this.subItemName = subItemName;
	}

	/**
	 * 補助科目略称
	 * 
	 * @return 補助科目略称
	 */
	public String getSubItemNames() {
		return subItemNames;
	}

	/**
	 * 補助科目略称
	 * 
	 * @param subItemNames 補助科目略称
	 */
	public void setSubItemNames(String subItemNames) {
		this.subItemNames = subItemNames;
	}

	/**
	 * 科目制御区分
	 * 
	 * @return 科目制御区分
	 */
	public int getKind() {
		return kind;
	}

	/**
	 * 科目制御区分
	 * 
	 * @param kind 科目制御区分
	 */
	public void setKind(int kind) {
		this.kind = kind;
	}

	/**
	 * 科目制御区分名称
	 * 
	 * @return 科目制御区分名称
	 */
	public String getKindName() {
		return kindName;
	}

	/**
	 * 科目制御区分名称
	 * 
	 * @param kindName 科目制御区分名称
	 */
	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	/**
	 * 科目が同一かどうかを判定する.
	 * 
	 * @param item 科目コード
	 * @param subItem 補助科目コード
	 * @param detailItem 内訳科目コード
	 * @return true:同一
	 */
	public boolean equalsItem(String item, String subItem, String detailItem) {
		boolean isEqual = Util.avoidNull(itemCode).equals(Util.avoidNull(item));
		isEqual &= Util.avoidNull(subItemCode).equals(Util.avoidNull(subItem));
		isEqual &= Util.avoidNull(detailItemCode).equals(Util.avoidNull(detailItem));

		return isEqual;
	}
}
