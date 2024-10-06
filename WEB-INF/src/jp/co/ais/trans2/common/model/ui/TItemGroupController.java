package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目・補助・内訳のユニットコンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TItemGroupController extends TController {

	/** フィールド */
	protected TItemGroup field;

	/**
	 * 検索条件<br>
	 * 当該条件は、科目、補助、内訳の全てのフィールドに適用される。<br>
	 * 科目、補助、内訳それぞれに個別に検索条件をセットしたい場合、<br>
	 * それぞれをgetしてセットすること。
	 */
	protected ItemGroupSearchCondition condition;

	/**
	 * @param field フィールド
	 */
	public TItemGroupController(TItemGroup field) {
		this.field = field;
		init();
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
	 * 初期化
	 */
	protected void init() {

		// イベント定義
		addEvent();

		// サイズ設定
		field.setSize(field.ctrlItemReference.getWidth(), 60);

		// 内訳を使わない場合、
		if (!getCompany().getAccountConfig().isUseDetailItem()) {
			field.ctrlDetailItemReference.setVisible(false);
			field.setSize(field.ctrlItemReference.getWidth(), 40);
		}

		clear();

		initSearchCondition();

	}

	/**
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setItemCondition(field.ctrlItemReference.getSearchCondition());
		condition.setSubItemCondition(field.ctrlSubItemReference.getSearchCondition());
		condition.setDetailItemCondition(field.ctrlDetailItemReference.getSearchCondition());
	}

	/**
	 * 科目 補助科目のイベント定義。
	 */
	protected void addEvent() {

		// 科目選択
		field.ctrlItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !field.ctrlItemReference.isValueChanged()) {
					return;
				}

				ctrlItemReference_after();
			}
		});

		// 補助科目選択
		field.ctrlSubItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !field.ctrlSubItemReference.isValueChanged()) {
					return;
				}

				ctrlSubItemReference_after();
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
		field.ctrlItemReference.clear();
		field.ctrlSubItemReference.clear();
		field.ctrlDetailItemReference.clear();
		field.ctrlSubItemReference.setEditable(false);
		field.ctrlDetailItemReference.setEditable(false);
	}

	/**
	 * [科目選択]時の処理
	 */
	protected void ctrlItemReference_after() {
		Item entity = field.ctrlItemReference.getEntity();

		// 補助科目を持つ場合、補助フィールドを入力可能にする。
		if (entity != null) {

			String subItemCode = field.ctrlSubItemReference.getCode();

			field.ctrlSubItemReference.clear();
			field.ctrlSubItemReference.setEditable(entity.hasSubItem());
			field.ctrlSubItemReference.getSearchCondition().setItemCode(entity.getCode());

			if (!Util.isNullOrEmpty(subItemCode) && field.ctrlSubItemReference.isEditable()) {
				// 補助科目コード入力あり

				field.ctrlSubItemReference.setCode(subItemCode);
				Item subItem = field.ctrlSubItemReference.controller.getInputtedEntity();
				if (subItem != null) {
					// 当該エンティティを設定する
					field.ctrlSubItemReference.setEntity(subItem);
					// [補助科目選択]時の処理
					ctrlSubItemReference_after();
				} else {
					field.ctrlSubItemReference.clear();
					field.ctrlDetailItemReference.clear();
					field.ctrlDetailItemReference.setEditable(false);
					field.ctrlDetailItemReference.getSearchCondition().setItemCode(entity.getCode());
				}

			} else {
				field.ctrlDetailItemReference.clear();
				field.ctrlDetailItemReference.setEditable(false);
				field.ctrlDetailItemReference.getSearchCondition().setItemCode(entity.getCode());
			}

		} else {
			field.ctrlSubItemReference.clear();
			field.ctrlSubItemReference.setEditable(false);
			field.ctrlDetailItemReference.clear();
			field.ctrlDetailItemReference.setEditable(false);
		}
	}

	/**
	 * [補助科目選択]時の処理
	 */
	protected void ctrlSubItemReference_after() {
		Item entity = field.ctrlSubItemReference.getEntity();

		// 内訳科目を持つ場合、内訳フィールドを入力可能にする。
		if (entity != null && entity.getSubItem() != null) {
			field.ctrlDetailItemReference.clear();
			field.ctrlDetailItemReference.setEditable(entity.getSubItem().hasDetailItem());
			field.ctrlDetailItemReference.getSearchCondition().setItemCode(entity.getCode());
			field.ctrlDetailItemReference.getSearchCondition().setSubItemCode(entity.getSubItem().getCode());
		} else {
			field.ctrlDetailItemReference.clear();
			field.ctrlDetailItemReference.setEditable(false);
		}
	}

	/**
	 * 選択された科目・補助・内訳を返す
	 * 
	 * @return 選択された科目・補助・内訳<br>
	 *         (Itemの中に階層的に科目・補助・内訳を内包して返す)
	 */
	public Item getEntity() {

		// 選択された科目を取得
		Item item = field.ctrlItemReference.getEntity();

		if (item == null) {
			return null;
		}

		// 補助まであれば補助をセット
		if (field.ctrlSubItemReference.getEntity() != null
			&& field.ctrlSubItemReference.getEntity().getSubItem() != null) {

			item.setSubItem(field.ctrlSubItemReference.getEntity().getSubItem());

			// 内訳まであれば内訳をセット
			if (field.ctrlDetailItemReference.getEntity() != null
				&& field.ctrlDetailItemReference.getEntity().getSubItem() != null
				&& field.ctrlDetailItemReference.getEntity().getSubItem().getDetailItem() != null) {

				item.getSubItem().setDetailItem(field.ctrlDetailItemReference.getEntity().getSubItem().getDetailItem());

			} else {
				item.getSubItem().setDetailItem(null);
			}

		} else {
			item.setSubItem(null);
		}

		return item;
	}

	/**
	 * 選択された科目・補助・内訳を返す
	 * 
	 * @return 選択された科目・補助・内訳<br>
	 *         (Itemの中に階層的に科目・補助・内訳を内包して返す)<br>
	 *         <b>※入力途中</b>
	 */
	public Item getInputtedEntity() {

		// 選択された科目を取得
		Item item = field.ctrlItemReference.getEntity();

		if (item == null) {
			return null;
		}

		// 補助まであれば補助をセット
		if (field.ctrlSubItemReference.getEntity() != null
			&& field.ctrlSubItemReference.getEntity().getSubItem() != null) {

			item.setSubItem(field.ctrlSubItemReference.getEntity().getSubItem());

			// 内訳まであれば内訳をセット
			if (field.ctrlDetailItemReference.getEntity() != null
				&& field.ctrlDetailItemReference.getEntity().getSubItem() != null
				&& field.ctrlDetailItemReference.getEntity().getSubItem().getDetailItem() != null) {

				item.getSubItem().setDetailItem(field.ctrlDetailItemReference.getEntity().getSubItem().getDetailItem());

			} else {
				// クリア処理なし
				item.getSubItem().setDetailItem(field.ctrlDetailItemReference.isEditable());
			}

		} else {
			// クリア処理なし
			item.setSubItem(field.ctrlSubItemReference.isEditable());
		}

		return item;
	}

	/**
	 * 科目・補助・内訳を設定する.
	 * 
	 * @param item 科目・補助・内訳
	 */
	public void setEntity(Item item) {
		field.ctrlItemReference.setEntity(item);
		ctrlItemReference_after();

		field.ctrlSubItemReference.setEntity(item);
		ctrlSubItemReference_after();

		field.ctrlDetailItemReference.setEntity(item);

		field.ctrlSubItemReference.setEditable(item != null && item.hasSubItem());
		field.ctrlDetailItemReference.setEditable(item != null && item.hasSubItem() && item.getSubItem() != null
			&& item.getSubItem().hasDetailItem());
	}

	/**
	 * 検索条件のgetter
	 * 
	 * @return 検索条件
	 */
	public ItemGroupSearchCondition getSearchCondition() {
		return condition;
	}

	/**
	 * 科目の検索条件getter
	 * 
	 * @return 科目の検索条件
	 */
	public ItemSearchCondition getItemSearchCondition() {
		return field.ctrlItemReference.getSearchCondition();
	}

	/**
	 * 補助科目の検索条件getter
	 * 
	 * @return 補助科目の検索条件
	 */
	public SubItemSearchCondition getSubItemSearchCondition() {
		return field.ctrlSubItemReference.getSearchCondition();
	}

	/**
	 * 内訳科目の検索条件getter
	 * 
	 * @return 内訳科目の検索条件
	 */
	public DetailItemSearchCondition getDetailItemSearchCondition() {
		return field.ctrlDetailItemReference.getSearchCondition();
	}

	/**
	 * 未入力があるかどうか
	 * 
	 * @return true:未入力あり
	 */
	public boolean isEmpty() {

		Item item = getEntity();

		if (field.ctrlItemReference.isEmpty() || item == null) {
			return true;
		}

		if (item.hasSubItem()) {
			if (field.ctrlSubItemReference.isEmpty() || item.getSubItem() == null) {
				return true;
			}

			if (getCompany().getAccountConfig().isUseDetailItem() && item.getSubItem().hasDetailItem()) {
				if (field.ctrlDetailItemReference.isEmpty() || item.getDetailItem() == null) {
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
		field.ctrlItemReference.refleshEntity();
		Item item = field.ctrlItemReference.getEntity();
		if (item == null) {
			setEntity(null);
		} else {
			setEntity(item);
		}
	}

	/**
	 * 新しい条件で科目再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshGroupEntity() {

		String itemCode = field.ctrlItemReference.code.getText();
		String subItemCode = field.ctrlSubItemReference.code.getText();
		String detailItemCode = field.ctrlDetailItemReference.code.getText();

		String kcompanyCode = field.ctrlItemReference.getSearchCondition().getCompanyCode();
		try {

			// 内訳を検索
			Item item = (Item) request(ItemManager.class, "getItem", kcompanyCode, itemCode, subItemCode,
				detailItemCode);

			if (item == null && !Util.isNullOrEmpty(detailItemCode)) {

				// 補助を検索
				item = (Item) request(ItemManager.class, "getItem", kcompanyCode, itemCode, subItemCode, null);

				if (item == null && !Util.isNullOrEmpty(subItemCode)) {

					// 科目を検索
					item = (Item) request(ItemManager.class, "getItem", kcompanyCode, itemCode, null, null);

					if (item == null) {
						setEntity(null);
						return;
					}
				}
			}

			setEntity(item);

		} catch (TException e) {
			errorHandler(field, e);
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
		if (!checkInputBlank(field.ctrlItemReference.code, field.ctrlItemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(field.ctrlSubItemReference.code, field.ctrlSubItemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(field.ctrlDetailItemReference.code, field.ctrlDetailItemReference.btn.getText())) {
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
	protected boolean checkInputBlank(TTextField field1, String name) {

		if (field1.isVisible() && field1.isEditable() && field1.isEmpty()) {
			showMessage("I00037", name);// {0}を入力してください。
			field1.requestFocusInWindow();
			return false;
		}

		return true;
	}
}
