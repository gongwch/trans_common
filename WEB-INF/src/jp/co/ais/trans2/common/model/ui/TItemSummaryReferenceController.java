package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.item.summary.*;

/**
 * 科目集計検索コンポーネントのコントローラー
 */
public class TItemSummaryReferenceController extends TReferenceController {

	/** 検索条件 */
	protected ItemSummarySearchCondition condition;

	/**
	 * @param field 科目集計コンポーネント
	 */
	public TItemSummaryReferenceController(TReference field) {
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
	protected ItemSummarySearchCondition createSearchCondition() {
		return new ItemSummarySearchCondition();
	}

	/**
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
	}

	/**
	 * フィールド[コード]のverify
	 * 
	 * @param comp コンポーネント
	 * @return false:NG
	 */
	@Override
	public boolean code_Verify(@SuppressWarnings("unused")
	JComponent comp) {

//		 値がブランクの場合、名称を初期化
		if (Util.isNullOrEmpty(field.getCode())) {
			entity = null;
			field.name.setText(null);
			return true;
		}

		// 値の変更がない時はデータを取得しない
		if (!field.code.isValueChanged()) {
			return true;
		}

		// 入力されたコードに対し、紐付くデータを取得する
		entity = getInputtedEntity();

		// 値があれば略称をセット
		if (entity != null) {
			// 有効期限チェック
			if (!isInValidTerm()) {
				return false;
			}

			field.name.setText(getEntity().getKmkName());
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
				Date from = getEntity().getDateFrom();
				Date to = getEntity().getDateTo();
				if ((from != null && !Util.isSmallerThenByYMD(from, validTerm))
					|| (to != null && !Util.isSmallerThenByYMD(validTerm, to))) {

					if (!showConfirmMessage("Q00025", getEntity().getKmkCode())) {
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
			ItemSummarySearchCondition condition_ = getCondition().clone();
			// コード前方一致
			condition_.setCodeLike(dialog.code.getText());
			// 略称あいまい
			condition_.setNamesLike(dialog.names.getText());
			// 検索名称あいまい
			condition_.setNamekLike(dialog.namek.getText());

			List<ItemSummary> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (ItemSummary bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getKmkCode(), bean.getKmkName(), bean.getKmkNamek() });
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
			entity = getSelectedEntity();

			// Entityが存在すれば、コードと名称を検索ダイアログ呼び出し元にセット
			if (entity != null) {
				field.code.setText(getEntity().getKmkCode());
				field.name.setText(getEntity().getKmkName());
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
					listener.after(entity != null);
					listener.afterVerify(entity != null);
				}
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * Classを返す
	 * 
	 * @return Class
	 */
	protected Class getModelClass() {
		return ItemSummaryManager.class;
	}

	/**
	 * 入力された科目集計を返す
	 * 
	 * @return Entity
	 */
	protected ItemSummary getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			ItemSummarySearchCondition searchCondition = condition.clone();
			searchCondition.setKmkCode(field.getCode());
			searchCondition.setCodeFrom(null);
			searchCondition.setCodeTo(null);

			// 有効期限切れを任意で選ばせる為に条件からは削除
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				searchCondition.setValidTerm(null);
			}

			List<ItemSummary> list = getList(searchCondition);

			if (list == null || list.isEmpty()) {
				return null;
			}
			return list.get(0);

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * 検索条件に該当する科目集計リストを返す
	 * 
	 * @param condition_ 検索条件
	 * @return 検索条件に該当する科目集計リスト
	 */
	@SuppressWarnings("unchecked")
	protected List<ItemSummary> getList(ItemSummarySearchCondition condition_) {

		try {

			List<ItemSummary> list = (List<ItemSummary>) request(getModelClass(), "getRef", condition_);

			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return 検索条件を戻します。
	 */
	public ItemSummarySearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition 検索条件を設定します。
	 */
	public void setCondition(ItemSummarySearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * 選択された部門を返す
	 * 
	 * @return 選択された科目集計
	 */
	protected ItemSummary getSelectedEntity() {
		return (ItemSummary) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00077"; // 科目
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C00077"; // 科目
	}

	/**
	 * 伝票の参照権限をセットする。<br>
	 * 
	 * @param slipRole 伝票参照権限
	 * @param item 
	 */
	public void setSlipRole(SlipRole slipRole, ItemSummary item) {

		if (SlipRole.ALL == slipRole) {
			field.setEnabled(true);
		} else {
			setEntity(item);
			field.setEditable(false);
		}
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public ItemSummary getEntity() {
		return (ItemSummary) super.getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param item 科目集計
	 */
	public void setEntity(ItemSummary item) {
		if (item == null) {
			clear();
			return;
		}

		field.code.setText(item.getKmkCode());
		field.name.setText(item.getKmkName());
		entity = item;
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		Department entity_ = new Department();
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
		return new Department();
	}
}
