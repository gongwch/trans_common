package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.bank.*;

/**
 * 銀行検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TBankReferenceController extends TReferenceController {

	/** 検索条件 */
	protected BankSearchCondition condition;

	/**
	 * @param field 銀行コンポーネント
	 */
	public TBankReferenceController(TReference field) {
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
	protected BankSearchCondition createSearchCondition() {
		return new BankSearchCondition();
	}

	/**
	 * 検索条件を初期化する
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
	}

	/**
	 * フィールド[コード]のverify
	 * 
	 * @param comp コンポーネント
	 * @return false:NG
	 */
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
		if (entity != null) {

			// 有効期限チェック
			if (!isInValidTerm()) {
				return false;
			}

			field.code.setText(getEntity().getCode());
			field.name.setText(getEntity().getNames());
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

					if (!showConfirmMessage("Q00025", getEntity().getCode())) {
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
			BankSearchCondition condition1 = getCondition().clone();
			// コード前方一致
			condition1.setCodeLike(dialog.code.getText());
			// 略称あいまい
			condition1.setNamesLike(dialog.names.getText());
			// 検索名称あいまい
			condition1.setNamekLike(dialog.namek.getText());

			List<Bank> list = getList(condition1);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (Bank bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCode(), bean.getNames(), bean.getNamek() });
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
				field.code.setText(getEntity().getCode());
				field.name.setText(getEntity().getNames());
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

			field.code.pushOldText();

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
		return BankManager.class;
	}

	/**
	 * 入力された銀行口座を返す
	 * 
	 * @return Entity
	 */
	protected Bank getInputtedEntity() {

		try {
			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			BankSearchCondition searchCondition = condition.clone();
			searchCondition.setCode(field.getCode());
			searchCondition.setCodeFrom(null);
			searchCondition.setCodeTo(null);

			// 有効期限切れを任意で選ばせる為に条件からは削除
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				searchCondition.setValidTerm(null);
			}

			List<Bank> list = getList(searchCondition);

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
	 * 検索条件に該当する銀行口座リストを返す
	 * 
	 * @param condition_ 検索条件
	 * @return 検索条件に該当する銀行口座リスト
	 */
	@SuppressWarnings("unchecked")
	protected List<Bank> getList(BankSearchCondition condition_) {

		try {

			List<Bank> list = (List<Bank>) request(getModelClass(), "get", condition_);

			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return 検索条件を戻します。
	 */
	public BankSearchCondition getCondition() {
		return condition;
	}

	/**
	 * 選択された銀行口座を返す
	 * 
	 * @return 選択された銀行口座
	 */
	protected Bank getSelectedEntity() {
		return (Bank) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00124";// 銀行
	}

	/**
	 * 略称のキャプションを取得します
	 * 
	 * @return 略称のキャプション
	 */
	@Override
	public String getNamesCaption() {
		return "C00781";// 銀行名称
	}

	/**
	 * 検索名称のキャプションを取得します
	 * 
	 * @return 検索名称のキャプション
	 */
	@Override
	public String getNamekCaption() {
		return "C00829";// 銀行検索名称
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C00124";// 銀行
	}

	/**
	 * 略称のカラムサイズを取得します
	 * 
	 * @return 略称のカラムサイズ
	 */
	@Override
	public int getNamesColumnSize() {
		return 300;
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public Bank getEntity() {
		return (Bank) super.getEntity();
	}

	/**
	 * フィールドを返す
	 * 
	 * @return フィールド
	 */
	public TBankReference getField() {
		return (TBankReference) field;
	}

	/**
	 * 初期化処理
	 * 
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#clear()
	 */
	@Override
	public void clear() {
		super.clear();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param bank 銀行
	 */
	public void setEntity(Bank bank) {
		if (bank == null) {
			clear();
			return;
		}

		field.code.setText(bank.getCode());
		field.name.setText(bank.getNames());
		entity = bank;
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		Bank entity1 = new Bank();
		entity1.setCode(field.code.getText());
		entity1.setNames(field.name.getText());
		return entity1;
	}

	/**
	 * Entityのインスタンスファクトリ
	 * 
	 * @return 新規TReferable
	 */
	@Override
	public TReferable createEntity() {
		return new Bank();
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		Bank bean = getInputtedEntity();
		if (bean == null) {
			this.clear();
		} else {
			entity = bean;
		}
	}

}
