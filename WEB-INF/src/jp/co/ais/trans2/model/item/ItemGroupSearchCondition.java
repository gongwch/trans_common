package jp.co.ais.trans2.model.item;

import java.util.*;

import jp.co.ais.trans.common.dt.TransferBase;

/**
 * 科目・補助・内訳の集合条件クラス。<br>
 * 各条件のsetterが科目、補助、内訳全てに適用される。
 */
public class ItemGroupSearchCondition extends TransferBase {

	/** 科目条件 */
	protected ItemSearchCondition itemCondition;

	/** 補助科目条件 */
	protected SubItemSearchCondition subItemCondition;

	/** 内訳科目条件 */
	protected DetailItemSearchCondition detailItemCondition;

	/**
	 * 科目条件
	 * 
	 * @return 科目条件
	 */
	public ItemSearchCondition getItemCondition() {
		return itemCondition;
	}

	/**
	 * 科目条件
	 * 
	 * @param itemCondition 科目条件
	 */
	public void setItemCondition(ItemSearchCondition itemCondition) {
		this.itemCondition = itemCondition;
	}

	/**
	 * 補助科目条件
	 * 
	 * @return 補助科目条件
	 */
	public SubItemSearchCondition getSubItemCondition() {
		return subItemCondition;
	}

	/**
	 * 補助科目条件
	 * 
	 * @param subItemCondition 補助科目条件
	 */
	public void setSubItemCondition(SubItemSearchCondition subItemCondition) {
		this.subItemCondition = subItemCondition;
	}

	/**
	 * 内訳科目条件
	 * 
	 * @return 内訳科目条件
	 */
	public DetailItemSearchCondition getDetailItemCondition() {
		return detailItemCondition;
	}

	/**
	 * 内訳科目条件
	 * 
	 * @param detailItemCondition 内訳科目条件
	 */
	public void setDetailItemCondition(DetailItemSearchCondition detailItemCondition) {
		this.detailItemCondition = detailItemCondition;
	}

	/**
	 * 会社コード設定
	 * 
	 * @param code 会社コード
	 */
	public void setCompanyCode(String code) {
		itemCondition.setCompanyCode(code);

		if (subItemCondition != null) {
			subItemCondition.setCompanyCode(code);

			if (detailItemCondition != null) {
				detailItemCondition.setCompanyCode(code);
			}
		}
	}

	/**
	 * 部門コード設定
	 * 
	 * @param code 部門コード
	 */
	public void setDepartmentCode(String code) {
		itemCondition.setDepartmentCode(code);
	}

	/**
	 * 部門コード設定
	 * 
	 * @return 部門コード
	 */
	public String getDepartmentCode() {
		return itemCondition.getDepartmentCode();
	}

	/**
	 * 有効日設定
	 * 
	 * @param date 有効日
	 */
	public void setValidTerm(Date date) {
		itemCondition.setValidTerm(date);

		if (subItemCondition != null) {
			subItemCondition.setValidTerm(date);

			if (detailItemCondition != null) {
				detailItemCondition.setValidTerm(date);
			}
		}
	}

	/**
	 * 資金科目だけを対象にするか
	 * @param cash
	 */
	public void setCash(boolean cash) {
		itemCondition.setCash(cash);
		if (subItemCondition != null) {
			subItemCondition.setCash(cash);
			if (detailItemCondition != null) {
				detailItemCondition.setCash(cash);
			}
		}
	}

	/**
	 * 科目体系コードsetter
	 * @param itemOrganizationCode 科目体系コード
	 */
	public void setItemOrganizationCode(String itemOrganizationCode) {
		itemCondition.setItemOrganizationCode(itemOrganizationCode);
		if (subItemCondition != null) {
			subItemCondition.setItemOrganizationCode(itemOrganizationCode);
			if (detailItemCondition != null) {
				detailItemCondition.setItemOrganizationCode(itemOrganizationCode);
			}
		}
	}

}
