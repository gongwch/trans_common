package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 補助科目検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TSubItemReferenceController extends TReferenceController {

	/** 検索条件 */
	protected SubItemSearchCondition condition;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TSubItemReferenceController(TReference field) {
		super(field);
	}

	@Override
	public void init() {

		super.init();

		// 検索条件初期化
		initSearchCondition();

	}

	/**
	 * 検索条件のインスタンスを生成し返す
	 * 
	 * @return 新規検索条件のインスタンス
	 */
	protected SubItemSearchCondition createSearchCondition() {
		return new SubItemSearchCondition();
	}

	/**
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	@Override
	public boolean code_Verify(@SuppressWarnings("unused") JComponent comp) {

		// 値がブランクの場合、名称を初期化
		if (Util.isNullOrEmpty(field.getCode())) {
			entity = null;
			field.name.setText(null);
			return true;
		}

		// 入力されたコードに対し、紐付くデータを取得する
		entity = getInputtedEntity();

		// 値があれば略称をセット
		if (entity != null && getEntity().getSubItem() != null) {

			// 有効期限チェック
			if (!isInValidTerm()) {
				return false;
			}

			field.code.setText(getEntity().getSubItem().getCode());
			field.name.setText(getEntity().getSubItem().getNames());
			return true;
		}

		field.name.setText(null);
		if (checkExist) {
			showMessage(field, "I00123");// 該当コードは存在しません
			field.code.requestFocus();
			field.code.clearOldText();
			return false;
		}
		return true;

	}

	/**
	 * 有効期限チェック
	 * 
	 * @return true:有効期間内、false:有効期間外
	 */
	protected boolean isInValidTerm() {
		if (getCompany().getAccountConfig().isSlipTermCheck()) {
			Date validTerm = condition.getValidTerm();

			if (validTerm != null) {
				Date from = getEntity().getSubItem().getDateFrom();
				Date to = getEntity().getSubItem().getDateTo();
				if ((from != null && !Util.isSmallerThenByYMD(from, validTerm))
					|| (to != null && !Util.isSmallerThenByYMD(validTerm, to))) {

					if (!showConfirmMessage("Q00025", getEntity().getSubItem().getCode())) {
						field.code.requestFocus();
						field.code.clearOldText();
						return false;
					}

					field.code.requestFocus();
				}
			}
		}

		return true;
	}

	/**
	 * 検索ダイアログ[検索]ボタン押下
	 */
	@Override
	public void btnSearch_Click() {

		try {

			// 確定ボタンを押下不可にする
			dialog.btnSettle.setEnabled(false);

			// 一覧をクリアする
			dialog.tbl.removeRow();

			// データを抽出する
			SubItemSearchCondition condition_ = getCondition().clone();
			// コード前方一致
			condition_.setCodeLike(dialog.code.getText());
			// 略称あいまい
			condition_.setNamesLike(dialog.names.getText());
			// 検索名称あいまい
			condition_.setNamekLike(dialog.namek.getText());

			List<Item> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (Item bean : list) {
				if (bean.getSubItem() != null) {
					dialog.tbl.addRow(new Object[] { bean, bean.getSubItem().getCode(), bean.getSubItem().getNames(),
							bean.getSubItem().getNamek() });
				}
			}

			// 確定ボタンを押下可能にする
			dialog.btnSettle.setEnabled(true);

			// 1行目を選択
			dialog.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 検索ダイアログ[確定]ボタン押下
	 */
	@Override
	public void btnSettle_Click() {

		try {
			// 一覧で選択されたEntityを取得する
			Item newEntity = getSelectedEntity();

			// 同一のコードが選択された場合は、処理なし
			if (field.code.getText().equals(newEntity.getSubItemCode())) {
				dialog.setVisible(false);
				dialog.dispose();
				field.code.requestFocus();
				return;
			}

			entity = newEntity;

			// Entityが存在すれば、コードと名称を検索ダイアログ呼び出し元にセット
			if (entity != null && getEntity().getSubItem() != null) {
				field.code.setText(getEntity().getSubItem().getCode());
				field.name.setText(getEntity().getSubItem().getNames());
				field.code.pushOldText();
			}

			// ダイアログを閉じる
			dialog.setVisible(false);
			dialog.dispose();

			// 呼び出し元のコードフィールドにフォーカス
			field.code.requestFocus();

			if (callBackListenerList != null && !callBackListenerList.isEmpty()) {
				for (TCallBackListener listener : callBackListenerList) {
					listener.after();
					listener.after(true);
					listener.afterVerify(true);
				}
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * フィールドからコードを直接入力した場合の検索
	 * 
	 * @return Entity
	 */
	protected Item getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			SubItemSearchCondition condition_ = condition.clone();
			condition_.setItemCode(this.condition.getItemCode());
			condition_.setCode(field.getCode());
			condition_.setCodeFrom(null);
			condition_.setCodeTo(null);

			// 有効期限切れを任意で選ばせる為に条件からは削除
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				condition_.setValidTerm(null);
			}

			List<Item> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				return null;
			}
			return list.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * @return ダイアログ画面での検索条件を設定
	 */
	protected SubItemSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition_
	 * @return 補助科目情報
	 */
	protected List<Item> getList(SubItemSearchCondition condition_) {

		try {
			List<Item> list = (List<Item>) request(getModelClass(), "get", condition_);
			return list;
		} catch (Exception ex) {
			errorHandler(ex);
		}

		return null;
	}

	/**
	 * モデルクラス
	 * 
	 * @return モデルクラス
	 */
	protected Class getModelClass() {
		return ItemManager.class;
	}

	/**
	 * 検索ダイアログの一覧から選択された行のEntityを返す
	 * 
	 * @return Entity
	 */
	protected Item getSelectedEntity() {
		return (Item) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return getCompany().getAccountConfig().getSubItemName();
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return getCompany().getAccountConfig().getSubItemName();
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public Item getEntity() {
		return (Item) super.getEntity();
	}

	/**
	 * Entityをセット
	 * 
	 * @param item Entity
	 */
	public void setEntity(Item item) {
		if (item == null) {
			clear();
			return;
		}

		field.code.setText(item.getSubItemCode());
		field.name.setText(item.getSubItemNames());
		entity = item;
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		Item entity_ = new Item();
		entity_.setCode(field.code.getText());
		entity_.setNames(field.name.getText());
		return entity_;
	}

	/**
	 * Entityのインスタンスファクトリ
	 * 
	 * @return 新規TReferable
	 */
	@Override
	public TReferable createEntity() {
		return new SubItem();
	}

	/**
	 * (補助科目コードを見る)
	 * 
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#isValueChanged()
	 */
	@Override
	public boolean isValueChanged() {

		if ((oldEntity != null && entity == null) || (oldEntity == null && entity != null)) {
			return true;
		}

		Item oldItem = (Item) oldEntity;
		Item newItem = (Item) entity;

		// 旧コード
		String oldCode = "";
		if (oldItem != null && oldItem.getSubItem() != null) {
			oldCode = Util.avoidNull(oldItem.getSubItem().getCode());
		}

		String newCode = "";
		if (newItem != null && newItem.getSubItem() != null) {
			newCode = Util.avoidNull(newItem.getSubItem().getCode());
		}

		return !oldCode.equals(newCode);
	}
}
