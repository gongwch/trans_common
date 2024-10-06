package jp.co.ais.trans2.common.model.ui;

import static jp.co.ais.trans2.define.DepositKind.*;

import java.util.*;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.*;

/**
 * 預金種目選択コンボボックスコントローラー
 *
 * @author AIS
 */
public class TDepositKindComboBoxController extends TController {

	/** コンボボックス */
	public TDepositKindComboBox cmbo;

	/** 預金種目項目リスト */
	public List<DepositKindElement> list;

	/**
	 * コンストラクタ
	 *
	 * @param cmbo コンボボックス
	 */
	public TDepositKindComboBoxController(TDepositKindComboBox cmbo) {
		super();
		this.cmbo = cmbo;
		setSortList();
	}

	/**
	 * 預金種目項目リストを設定
	 */
	public void setSortList() {

		list = new ArrayList<DepositKindElement>();
		list.add(new DepositKindElement("", null));
		list.add(new DepositKindElement(TModelUIUtil.getShortWord(getDepositKindName(ORDINARY)), ORDINARY));
		list.add(new DepositKindElement(TModelUIUtil.getShortWord(getDepositKindName(CURRENT)), CURRENT));
		list.add(new DepositKindElement(TModelUIUtil.getShortWord(getDepositKindName(FOREIGN_CURRENCY)), FOREIGN_CURRENCY));
		list.add(new DepositKindElement(TModelUIUtil.getShortWord(getDepositKindName(SAVINGS)), SAVINGS));
		list.add(new DepositKindElement(TModelUIUtil.getShortWord(getDepositKindName(FIXED)), FIXED));

		for (DepositKindElement ele : list) {
			cmbo.getComboBox().addItem(ele.getName());
		}
	}

	/**
	 * 選択された預金種目を返す
	 *
	 * @return DepositKind
	 */
	public DepositKind getSelectedDepositKind() {
		return list.get(cmbo.getSelectedIndex()).getDepositKind();
	}

	/**
	 * 預金種目をセットする
	 *
	 * @param depositKind
	 */
	public void setSelectedDepositKind(DepositKind depositKind) {

		if (depositKind == null) {
			return;
		}

		int i = 0;
		for (DepositKindElement ele : list) {
			if (depositKind.equals(ele.getDepositKind())) {
				cmbo.setSelectedIndex(i);
				return;
			}
			i++;
		}
	}

	/** 預金種目項目 */
	protected class DepositKindElement {

		/** 名称 */
		protected String name;

		/** 預金種目 */
		protected DepositKind depositKind;

		/**
		 * コンストラクタ
		 *
		 * @param name 名称
		 * @param depositKind 預金種目
		 */
		public DepositKindElement(String name, DepositKind depositKind) {
			this.name = name;
			this.depositKind = depositKind;
		}

		/**
		 * 預金種目
		 *
		 * @return 預金種目
		 */
		public DepositKind getDepositKind() {
			return depositKind;
		}

		/**
		 * 預金種目
		 *
		 * @param depositKind 預金種目
		 */
		public void setDepositKind(DepositKind depositKind) {
			this.depositKind = depositKind;
		}

		/**
		 * 名称
		 *
		 * @return 名称
		 */
		public String getName() {
			return name;
		}

		/**
		 * 名称
		 *
		 * @param name 名称
		 */
		public void setName(String name) {
			this.name = name;
		}
	}
}
