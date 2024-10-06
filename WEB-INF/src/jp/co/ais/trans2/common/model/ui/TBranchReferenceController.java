package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.bank.*;

/**
 * 支店検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TBranchReferenceController extends TReferenceController {

	/** 検索条件 */
	protected BranchSearchCondition condition;

	/**
	 * @param field 支店コンポーネント
	 */
	public TBranchReferenceController(TReference field) {
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
	protected BranchSearchCondition createSearchCondition() {
		return new BranchSearchCondition();
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

			field.code.setText(getEntity().getBranch().getCode());
			field.name.setText(getEntity().getBranch().getNames());
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
			BranchSearchCondition condition_ = getCondition().clone();
			// コード前方一致
			condition_.setCodeLike(dialog.code.getText());
			// 略称あいまい
			condition_.setNamesLike(dialog.names.getText());
			// 検索名称あいまい
			condition_.setNamekLike(dialog.namek.getText());

			List<Bank> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (Bank bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getBranch().getCode(), bean.getBranch().getNames(),
						bean.getBranch().getNamek() });
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
				field.code.setText(getEntity().getBranch().getCode());
				field.name.setText(getEntity().getBranch().getNames());
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

			BranchSearchCondition searchCondition = condition.clone();
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
	 * 検索条件に該当する支店リストを返す
	 * 
	 * @param condition_ 検索条件
	 * @return 検索条件に該当する支店リスト
	 */
	@SuppressWarnings("unchecked")
	protected List<Bank> getList(BranchSearchCondition condition_) {

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
	public BranchSearchCondition getCondition() {
		return condition;
	}

	/**
	 * 選択された銀行支店を返す
	 * 
	 * @return 選択された銀行支店
	 */
	protected Bank getSelectedEntity() {
		return (Bank) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00778";// 銀行支店
	}

	/**
	 * 略称のキャプションを取得します
	 * 
	 * @return 略称のキャプション
	 */
	@Override
	public String getNamesCaption() {
		return "C00783";// 銀行支店名称
	}

	/**
	 * 検索名称のキャプションを取得します
	 * 
	 * @return 検索名称のキャプション
	 */
	@Override
	public String getNamekCaption() {
		return "C00785";// 銀行支店検索名称
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C00222";// 支店
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
	public TBranchReference getField() {
		return (TBranchReference) field;
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
	 * @param bank 支店
	 */
	public void setEntity(Bank bank) {
		if (bank == null) {
			clear();
			return;
		}

		field.code.setText(bank.getBranch().getCode());
		field.name.setText(bank.getBranch().getNames());
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
		entity1.getBranch().setCode(field.code.getText());
		entity1.getBranch().setNames(field.name.getText());
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

}
