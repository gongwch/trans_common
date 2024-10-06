package jp.co.ais.trans2.common.model.ui;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.*;

/**
 * 科目の表示レベル選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TItemLevelChooserController extends TController {

	/** フィールド */
	protected TItemLevelChooser field;

	/**
	 * @param field フィールド
	 */
	public TItemLevelChooserController(TItemLevelChooser field) {
		this.field = field;
		init();
	}

	/**
	 * 初期化
	 */
	protected void init() {

		// イベント定義
		addEvent();

		// サイズ設定
		if (field.getDirection() == SwingConstants.VERTICAL) {
			field.setSize(130, 85);
		} else {
			field.setSize(310, 50);
		}

		// caption
		field.rdoItem.setLangMessageID(getCompany().getAccountConfig().getItemName());
		field.rdoSubItem.setLangMessageID(getCompany().getAccountConfig().getSubItemName());
		field.rdoDetailItem.setLangMessageID(getCompany().getAccountConfig().getDetailItemName());

		// 内訳を使わない場合、
		if (!getCompany().getAccountConfig().isUseDetailItem()) {
			field.rdoDetailItem.setVisible(false);
			field.setSize(220, 50);
			if (field.getDirection() == SwingConstants.VERTICAL) {
				field.setSize(130, 75);
				if (field.getTitle()) {
					field.rdoItem.setLocation(field.rdoItem.getX(), 5);
					field.rdoSubItem.setLocation(field.rdoSubItem.getX(), 30);
				} else {
					field.rdoItem.setLocation(field.rdoItem.getX(), 18);
					field.rdoSubItem.setLocation(field.rdoSubItem.getX(), 43);
				}
			}
		}

		field.rdoItem.setSelected(true);

	}

	/**
	 * イベント処理
	 */
	protected void addEvent() {
		//
	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * 選択された科目レベルを返す
	 * 
	 * @return 選択された科目レベル
	 */
	public ItemLevel getItemLevel() {
		if (field.rdoItem.isSelected()) {
			return ItemLevel.ITEM;
		} else if (field.rdoSubItem.isSelected()) {
			return ItemLevel.SUBITEM;
		} else if (field.rdoDetailItem.isSelected()) {
			return ItemLevel.DETAILITEM;
		}
		return null;
	}

	/**
	 * 科目レベルをセットする
	 * 
	 * @param itemLevel 科目レベル
	 */
	public void setItemLevel(ItemLevel itemLevel) {
		if (itemLevel == null) {
			field.rdoItem.setSelected(true);
		} else if (ItemLevel.ITEM == itemLevel) {
			field.rdoItem.setSelected(true);
		} else if (ItemLevel.SUBITEM == itemLevel) {
			field.rdoSubItem.setSelected(true);
		} else if (ItemLevel.DETAILITEM == itemLevel) {
			field.rdoDetailItem.setSelected(true);
		}
	}
}
