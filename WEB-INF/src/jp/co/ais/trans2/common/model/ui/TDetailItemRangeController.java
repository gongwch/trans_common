package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 内訳科目範囲検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TDetailItemRangeController extends TController {

	/** 検索条件 */
	protected ItemGroupSearchCondition condition;

	/** フィールド */
	protected TDetailItemRange field;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TDetailItemRangeController(TDetailItemRange field) {
		this.field = field;
		init();
	}

	/**
	 * 初期化
	 */
	public void init() {

		// イベント定義
		addEvent();

		// サイズ設定
		field.setSize(field.detailItemRange.getWidth(), 60);

		// 内訳を使わない場合、
		if (!getCompany().getAccountConfig().isUseDetailItem()) {
			field.detailItemRange.setVisible(false);
			field.setSize(field.detailItemRange.getWidth(), 40);
		}

		clear();

		// 検索条件初期化
		initSearchCondition();
	}

	/**
	 * 検索条件のインスタンスを生成し返す
	 * 
	 * @return 新規検索条件のインスタンス
	 */
	protected ItemGroupSearchCondition createSearchCondition() {
		return new ItemGroupSearchCondition();
	}

	/**
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setItemCondition(field.itemReference.getSearchCondition());
		condition.setSubItemCondition(field.subItemReference.getSearchCondition());
		condition.setDetailItemCondition(field.detailItemRange.ctrlDetailItemReferenceFrom.getSearchCondition());
		condition.setDetailItemCondition(field.detailItemRange.ctrlDetailItemReferenceTo.getSearchCondition());
	}

	/**
	 * 科目 補助科目のイベント定義。
	 */
	protected void addEvent() {

		// 科目選択
		field.itemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !field.itemReference.isValueChanged()) {
					return;
				}

				ctrlItemReference_after();
			}
		});

		// 補助科目選択
		field.subItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !field.subItemReference.isValueChanged()) {
					return;
				}

				ctrlSubItemReference_after();
			}
		});

		// 内訳科目開始選択
		field.detailItemRange.ctrlDetailItemReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !field.detailItemRange.ctrlDetailItemReferenceFrom.isValueChanged()) {
					return;
				}

				ctrlDetailItemRangeFrom_after();
			}
		});

		// 内訳科目終了選択
		field.detailItemRange.ctrlDetailItemReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !field.detailItemRange.ctrlDetailItemReferenceTo.isValueChanged()) {
					return;
				}

				ctrlDetailItemRangeTo_after();
			}
		});
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
	 * フィールドをクリアする
	 */
	public void clear() {
		field.itemReference.clear();
		field.subItemReference.clear();
		field.detailItemRange.ctrlDetailItemReferenceFrom.clear();
		field.detailItemRange.ctrlDetailItemReferenceTo.clear();
		field.subItemReference.setEditable(false);
		field.detailItemRange.ctrlDetailItemReferenceFrom.setEditable(false);
		field.detailItemRange.ctrlDetailItemReferenceTo.setEditable(false);
	}

	/**
	 * [科目選択]時の処理
	 */
	public void ctrlItemReference_after() {
		Item entity = field.itemReference.getEntity();

		// 補助科目を持つ場合、補助フィールドを入力可能にする。
		if (entity != null) {
			field.subItemReference.clear();
			field.subItemReference.setEditable(entity.hasSubItem());
			field.subItemReference.getSearchCondition().setItemCode(entity.getCode());
			field.detailItemRange.clear();
			field.detailItemRange.setEditable(false);
			field.detailItemRange.ctrlDetailItemReferenceFrom.getSearchCondition().setItemCode(entity.getCode());
			field.detailItemRange.ctrlDetailItemReferenceTo.getSearchCondition().setItemCode(entity.getCode());
		} else {
			field.subItemReference.clear();
			field.subItemReference.setEditable(false);
			field.detailItemRange.clear();
			field.detailItemRange.setEditable(false);
		}
	}

	/**
	 * [補助科目選択]時の処理
	 */
	protected void ctrlSubItemReference_after() {
		Item entity = field.subItemReference.getEntity();

		// 補助科目を持つ場合、補助フィールドを入力可能にする。
		if (entity != null) {
			field.detailItemRange.clear();
			field.detailItemRange.setEditable(entity.getSubItem().hasDetailItem());
			field.detailItemRange.ctrlDetailItemReferenceFrom.getSearchCondition().setItemCode(entity.getCode());
			field.detailItemRange.ctrlDetailItemReferenceTo.getSearchCondition().setItemCode(entity.getCode());
			field.detailItemRange.ctrlDetailItemReferenceFrom.getSearchCondition().setSubItemCode(
				entity.getSubItem().getCode());
			field.detailItemRange.ctrlDetailItemReferenceTo.getSearchCondition().setSubItemCode(
				entity.getSubItem().getCode());
		} else {
			field.detailItemRange.clear();
			field.detailItemRange.setEditable(false);
		}
	}

	/**
	 * [内訳科目開始選択]時の処理
	 */
	@SuppressWarnings("unused")
	public void ctrlDetailItemRangeFrom_after() {
		Item entity = field.detailItemRange.ctrlDetailItemReferenceFrom.getEntity();
	}

	/**
	 * [内訳科目終了選択]時の処理
	 */
	@SuppressWarnings("unused")
	public void ctrlDetailItemRangeTo_after() {
		Item entity = field.detailItemRange.ctrlDetailItemReferenceTo.getEntity();
	}

	/**
	 * 選択された科目・補助・内訳を返す
	 * 
	 * @return 選択された科目・補助・内訳<br>
	 *         (Itemの中に階層的に科目・補助・内訳を内包して返す)
	 */
	public Item getEntity() {

		// 選択された科目を取得
		Item item = field.itemReference.getEntity();

		if (item == null) {
			return null;
		}

		// 補助まであれば補助をセット
		if (field.subItemReference.getEntity() != null && field.subItemReference.getEntity().getSubItem() != null) {

			item.setSubItem(field.subItemReference.getEntity().getSubItem());

			// 内訳まであれば内訳をセット
			if (field.detailItemRange.ctrlDetailItemReferenceFrom.getEntity() != null
				&& field.detailItemRange.ctrlDetailItemReferenceFrom.getEntity().getSubItem() != null
				&& field.detailItemRange.ctrlDetailItemReferenceTo.getEntity().getSubItem().getDetailItem() != null) {
				item.getSubItem().setDetailItem(
					field.detailItemRange.ctrlDetailItemReferenceFrom.getEntity().getSubItem().getDetailItem());
				item.getSubItem().setDetailItem(
					field.detailItemRange.ctrlDetailItemReferenceTo.getEntity().getSubItem().getDetailItem());
			} else {
				item.getSubItem().setDetailItem(null);
			}

		} else {
			item.setSubItem(null);
		}

		return item;
	}

	/**
	 * 科目・補助・内訳を設定する.
	 * 
	 * @param item 科目・補助・内訳
	 */
	public void setEntity(Item item) {
		field.itemReference.setEntity(item);
		ctrlItemReference_after();

		field.subItemReference.setEntity(item);
		ctrlSubItemReference_after();

		field.detailItemRange.ctrlDetailItemReferenceFrom.setEntity(item);
		ctrlDetailItemRangeFrom_after();

		field.detailItemRange.ctrlDetailItemReferenceTo.setEntity(item);
		ctrlDetailItemRangeTo_after();

		field.subItemReference.setEditable(item != null && item.hasSubItem());
		field.detailItemRange.ctrlDetailItemReferenceFrom.setEditable(item != null && item.hasSubItem()
			&& item.getSubItem() != null && item.getSubItem() != null && item.getSubItem().hasDetailItem());
		field.detailItemRange.ctrlDetailItemReferenceTo.setEditable(item != null && item.hasSubItem()
			&& item.getSubItem() != null && item.getSubItem() != null && item.getSubItem().hasDetailItem());
	}

	/**
	 * 検索条件のgetter
	 * 
	 * @return 検索条件
	 */
	public ItemSearchCondition getSearchCondition() {
		return getSearchCondition();
	}

	/**
	 * 科目の検索条件getter
	 * 
	 * @return 科目の検索条件
	 */
	public ItemSearchCondition getItemSearchCondition() {
		return field.itemReference.getSearchCondition();
	}

	/**
	 * 補助科目の検索条件getter
	 * 
	 * @return 補助科目の検索条件
	 */
	public SubItemSearchCondition getSubItemSearchCondition() {
		return field.subItemReference.getSearchCondition();
	}

	/**
	 * 内訳科目の検索条件getter
	 * 
	 * @return 内訳科目の検索条件
	 */
	public DetailItemSearchCondition getDetailItemSearchCondition() {
		return field.getSearchCondition();
	}

	/**
	 * 未入力があるかどうか
	 * 
	 * @return true:未入力あり
	 */
	public boolean isEmpty() {

		Item item = getEntity();

		if (field.itemReference.isEmpty() || item == null) {
			return true;
		}

		if (item.hasSubItem()) {
			if (field.subItemReference.isEmpty() || item.getSubItem() == null) {
				return true;
			}

			if (getCompany().getAccountConfig().isUseDetailItem() && item.getSubItem().hasDetailItem()) {
				if (field.detailItemRange.ctrlDetailItemReferenceFrom.isEmpty()
					|| field.detailItemRange.ctrlDetailItemReferenceTo.isEmpty() || item.getDetailItem() == null) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 新しい条件で科目再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		field.itemReference.refleshEntity();
		Item item = field.itemReference.getEntity();
		if (item == null) {
			setEntity(null);
		} else {
			setEntity(item);
		}
	}

	/**
	 * 入力ブランクチェック<br>
	 * 伝票明細のチェックロジックを転用
	 * 
	 * @see jp.co.ais.trans2.common.model.ui.slip.TSlipDetailPanelCtrl
	 * @return false:NG
	 */
	protected boolean checkInput() {
		// 科目
		if (!checkInputBlank(field.itemReference.code, field.itemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(field.subItemReference.code, field.subItemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(field.detailItemRange.ctrlDetailItemReferenceFrom.code,
			field.detailItemRange.ctrlDetailItemReferenceFrom.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(field.detailItemRange.ctrlDetailItemReferenceTo.code,
			field.detailItemRange.ctrlDetailItemReferenceTo.btn.getText())) {
			return false;
		}

		return true;
	}

	/**
	 * 入力ブランクチェック
	 * 
	 * @param field1 対象フィールド
	 * @param name エラー時の表示名
	 * @return false:NG
	 */
	private boolean checkInputBlank(TTextField field1, String name) {

		if (field1.isVisible() && field1.isEditable() && field1.isEmpty()) {
			showMessage("I00037", name);// {0}を入力してください。
			field1.requestFocusInWindow();
			return false;
		}

		return true;
	}
}