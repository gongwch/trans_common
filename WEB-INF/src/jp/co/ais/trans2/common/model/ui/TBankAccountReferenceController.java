package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 銀行口座検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TBankAccountReferenceController extends TReferenceController {

	/** 検索条件 */
	protected BankAccountSearchCondition condition;

	/**
	 * @param field 銀行口座コンポーネント
	 */
	public TBankAccountReferenceController(TReference field) {
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
	protected BankAccountSearchCondition createSearchCondition() {
		return new BankAccountSearchCondition();
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
	public boolean code_Verify(JComponent comp) {

		// 値がブランクの場合、名称を初期化
		if (Util.isNullOrEmpty(field.getCode())) {
			entity = null;
			field.name.setText(null);
			getField().accountNo.setText(null);
			return true;
		}

		// 入力されたコードに対し、紐付くデータを取得する
		entity = getInputtedEntity();

		// 値があれば略称をセット
		if (entity != null) {

			// 検索条件に該当するかをチェック
			BankAccountSearchCondition condition_ = getCondition();

			// 社員支払チェック
			if (condition_.isUseEmployeePayment() && !getEntity().isUseEmployeePayment()) {
				if (!showConfirmMessage(field, "Q00052", "C01117")) {
					field.code.clearOldText();
					field.code.requestFocus();
					return false;
				}
			}

			// 社外支払チェック
			if (condition_.isUseExPayment() && !getEntity().isUseExPayment()) {
				if (!showConfirmMessage(field, "Q00052", "C01122")) {
					field.code.clearOldText();
					field.code.requestFocus();
					return false;
				}
			}

			// 有効期限チェック
			if (!isInValidTerm()) {
				return false;
			}

			field.code.setText(getEntity().getCode());
			field.name.setText(getName(getEntity()));
			getField().accountNo.setText(getDepositKindAndAcountNoNo(getEntity()));
			return true;

		}

		field.name.setText(null);
		getField().accountNo.setText(null);

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
			BankAccountSearchCondition condition_ = getCondition().clone();
			// コード前方一致
			condition_.setCodeLike(dialog.code.getText());
			// 略称あいまい
			condition_.setNameLike(dialog.names.getText());
			// 預金金種
			condition_.setDepositKind(((TBankAccountReferenceDialog) dialog).cmbDepositKind.getSelectedDepositKind());
			// 検索名称あいまい
			condition_.setAccountNokLike(dialog.namek.getText());

			List<BankAccount> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (BankAccount bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCode(), getName(bean),
						getWord(bean.getDepositKindName()), bean.getAccountNo() });
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
				field.name.setText(getName(getEntity()));
				getField().accountNo.setText(getDepositKindAndAcountNoNo(getEntity()));
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
		return BankAccountManager.class;
	}

	/**
	 * 入力された銀行口座を返す
	 * 
	 * @return Entity
	 */
	protected BankAccount getInputtedEntity() {

		try {
			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			BankAccountSearchCondition searchCondition = condition.clone();
			searchCondition.setCode(field.getCode());
			searchCondition.setUseEmployeePayment(false);
			searchCondition.setUseExPayment(false);
			searchCondition.setCodeFrom(null);
			searchCondition.setCodeTo(null);

			// 有効期限切れを任意で選ばせる為に条件からは削除
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				searchCondition.setValidTerm(null);
			}

			List<BankAccount> list = getList(searchCondition);

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
	protected List<BankAccount> getList(BankAccountSearchCondition condition_) {

		try {

			String method = "get";
			if (getField().isAllCompanyMode()) {
				method = "getRef";
			}

			List<BankAccount> list = (List<BankAccount>) request(getModelClass(), method, condition_);

			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return 検索条件を戻します。
	 */
	public BankAccountSearchCondition getCondition() {
		return condition;
	}

	/**
	 * 選択された銀行口座を返す
	 * 
	 * @return 選択された銀行口座
	 */
	protected BankAccount getSelectedEntity() {
		return (BankAccount) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00857";// 銀行口座
	}

	/**
	 * 略称のキャプションを取得します
	 * 
	 * @return 略称のキャプション
	 */
	@Override
	public String getNamesCaption() {
		return "C10361";// 銀行名
	}

	/**
	 * 検索名称のキャプションを取得します
	 * 
	 * @return 検索名称のキャプション
	 */
	@Override
	public String getNamekCaption() {
		return "C00794";// 口座番号
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C00857";// 銀行口座
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
	public BankAccount getEntity() {
		return (BankAccount) super.getEntity();
	}

	/**
	 * フィールドを返す
	 * 
	 * @return フィールド
	 */
	public TBankAccountReference getField() {
		return (TBankAccountReference) field;
	}

	/**
	 * 初期化処理
	 * 
	 * @see jp.co.ais.trans2.common.gui.TReferenceController#clear()
	 */
	@Override
	public void clear() {
		super.clear();
		getField().accountNo.setText("");
		getField().setDepCode("");
	}

	/**
	 * Entityをセットする
	 * 
	 * @param bankAccount 銀行口座
	 */
	public void setEntity(BankAccount bankAccount) {
		if (bankAccount == null) {
			clear();
			return;
		}

		field.code.setText(bankAccount.getCode());
		field.name.setText(getName(bankAccount));
		getField().accountNo.setText(getDepositKindAndAcountNoNo(bankAccount));
		entity = bankAccount;
	}

	/**
	 * 名称の表示値を取得する
	 * 
	 * @param bean
	 * @return 名称の表示値
	 */
	protected String getName(BankAccount bean) {

		if (bean == null) {
			return null;
		}

		if (ClientConfig.isFlagOn("trans.bankaccount.ref.name")) {
			return bean.getName();
		}

		return bean.getBankAndBranchName();
	}

	/**
	 * 口座種別＋口座番号を返す
	 * 
	 * @param bankAccount
	 * @return String
	 */
	protected String getDepositKindAndAcountNoNo(BankAccount bankAccount) {

		String account = getWord(DepositKind.getDepositKindName(bankAccount.getDepositKind())) + " "
			+ Util.avoidNull(bankAccount.getAccountNo());

		return account;
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		BankAccount bean = getInputtedEntity();
		if (bean == null) {
			this.clear();
			return;
		}

		setEntity(bean);
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		BankAccount entity_ = new BankAccount();
		entity_.setCode(field.code.getText());
		entity_.setName(field.name.getText());
		return entity_;
	}

	/**
	 * 検索ダイアログ(銀行口座用)のインスタンスを生成し返す
	 * 
	 * @return 検索ダイアログ
	 */
	@Override
	protected TBankAccountReferenceDialog createDialog() {
		return new TBankAccountReferenceDialog(this);
	}

	/**
	 * 計上部門コードの条件セット アンカーカスタマイズ
	 * 
	 * @param code 銀行口座
	 */
	public void setDepCode(String code) {
		condition.setDeptCode(code);
	}

}
