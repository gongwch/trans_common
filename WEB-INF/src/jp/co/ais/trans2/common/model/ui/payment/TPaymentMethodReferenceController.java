package jp.co.ais.trans2.common.model.ui.payment;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 支払方法検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TPaymentMethodReferenceController extends TReferenceController {

	/** 検索条件 */
	protected PaymentMethodSearchCondition condition;

	/**
	 * @param field 支払方法コンポーネント
	 */
	public TPaymentMethodReferenceController(TReference field) {
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
	protected PaymentMethodSearchCondition createSearchCondition() {
		return new PaymentMethodSearchCondition();
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

			// 銀行口座連動チェック
			checkPaymentKind();
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
			field.name.setText(getEntity().getName());

			// 銀行口座連動チェック
			checkPaymentKind();
			return true;

		}

		field.name.setText(null);
		if (checkExist) {
			showMessage(field, "I00123");// 該当コードは存在しません
			field.code.requestFocus();
			field.code.clearOldText();
			return false;
		}

		// 銀行口座連動チェック
		checkPaymentKind();
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
			PaymentMethodSearchCondition condition_ = getCondition().clone();
			// コード前方一致
			condition_.setCodeLike(dialog.code.getText());
			// 略称あいまい
			condition_.setNameLike(dialog.names.getText());
			// 検索名称あいまい
			condition_.setNamekLike(dialog.namek.getText());

			List<PaymentMethod> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			}

			// 一覧にセット
			for (PaymentMethod bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCode(), bean.getName(), bean.getNamek() });
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
				field.name.setText(getEntity().getName());
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

			// 銀行口座連動チェック
			checkPaymentKind();

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
		return PaymentMethodManager.class;
	}

	/**
	 * 入力された支払方法を返す
	 * 
	 * @return Entity
	 */
	protected PaymentMethod getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			PaymentMethodSearchCondition searchCondition = condition.clone();
			searchCondition.setCode(field.getCode());
			searchCondition.setCodeFrom(null);
			searchCondition.setCodeTo(null);

			// 有効期限切れを任意で選ばせる為に条件からは削除
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				searchCondition.setValidTerm(null);
			}

			List<PaymentMethod> list = getList(searchCondition);

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
	 * 検索条件に該当する支払方法リストを返す
	 * 
	 * @param condition_ 検索条件
	 * @return 検索条件に該当する支払方法リスト
	 */
	protected List<PaymentMethod> getList(PaymentMethodSearchCondition condition_) {

		try {

			List<PaymentMethod> list = (List<PaymentMethod>) request(getModelClass(), "get", condition_);

			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return 検索条件を戻します。
	 */
	public PaymentMethodSearchCondition getCondition() {
		return condition;
	}

	/**
	 * 選択された支払方法を返す
	 * 
	 * @return 選択された支払方法
	 */
	protected PaymentMethod getSelectedEntity() {
		return (PaymentMethod) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C00233";// 支払方法
	}

	/**
	 * 略称のキャプションを取得します
	 * 
	 * @return 略称のキャプション
	 */
	@Override
	public String getNamesCaption() {
		return "C00865";// 支払方法名
	}

	@Override
	public String getTableKeyName() {
		return field.getClass().getSimpleName() + getUser().getCode();
	}

	@Override
	public String getButtonCaption() {
		return "C00233";// 支払方法
	}

	/**
	 * 銀行口座連動チェック
	 */
	protected void checkPaymentKind() {

		// 銀行口座フィールド
		TBankAccountReference ref = ((TPaymentMethodReference) field).ctrlBankAccount;
		// エラーの支払方法内部コード
		PaymentKind[] errlist = ((TPaymentMethodReference) field).errPaymentKinds;

		// 連動の設定がない場合は処理終了
		if (ref == null || errlist == null) {
			return;
		}

		// 銀行口座フィールド 初期化
		ref.clear();
		ref.setEditable(true);

		// エンティティがない場合は処理終了
		if (getEntity() == null) {
			ref.setEditable(false);
			return;
		}

		// 保持している内部コードと入力した内部コードを比較
		PaymentKind kind = getEntity().getPaymentKind();
		for (int i = 0; i < errlist.length; i++) {

			// 一致する場合は銀行口座フィールドを編集不可
			if (kind.equals(errlist[i])) {
				ref.setEditable(false);
				break;
			}
		}

		// 支払方法内部コードにより、銀行口座の検索条件を変更する
		switch (kind) {
			case EX_PAY_FB:
				ref.getSearchCondition().setUseEmployeePayment(false);
				ref.getSearchCondition().setUseExPayment(true);
				break;

			case EMP_PAY_ACCRUED:
			case EMP_PAY_UNPAID:
				ref.getSearchCondition().setUseEmployeePayment(true);
				ref.getSearchCondition().setUseExPayment(false);
				break;

			default:
				ref.getSearchCondition().setUseEmployeePayment(false);
				ref.getSearchCondition().setUseExPayment(false);
				break;
		}
	}

	/**
	 * 銀行口座の入力が必要な支払方法かどうか
	 * 
	 * @return 銀行口座の入力が必要な支払方法かどうか
	 */
	protected boolean isNeedInputAccount() {

		// 銀行口座フィールド
		TBankAccountReference ref = ((TPaymentMethodReference) field).ctrlBankAccount;
		// エラーの支払方法内部コード
		PaymentKind[] errlist = ((TPaymentMethodReference) field).errPaymentKinds;

		// 連動の設定がない場合はtrue
		if (ref == null || errlist == null) {
			return true;
		}

		// エンティティがない場合はfalse
		if (getEntity() == null) {
			return false;
		}

		// 保持している内部コードと入力した内部コードを比較
		PaymentKind kind = getEntity().getPaymentKind();
		for (int i = 0; i < errlist.length; i++) {

			// 一致する場合は銀行口座フィールドを編集不可
			if (kind.equals(errlist[i])) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 選択されたEntityを返す
	 * 
	 * @return 選択されたEntity
	 */
	@Override
	public PaymentMethod getEntity() {
		return (PaymentMethod) super.getEntity();
	}

	/**
	 * Entityをセットする
	 * 
	 * @param PaymentMethod 支払方法
	 */
	public void setEntity(PaymentMethod PaymentMethod) {
		if (PaymentMethod == null) {
			clear();
			return;
		}

		field.code.setText(PaymentMethod.getCode());
		field.name.setText(PaymentMethod.getName());
		entity = PaymentMethod;
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		PaymentMethod entity_ = new PaymentMethod();
		entity_.setCode(field.code.getText());
		entity_.setName(field.name.getText());
		return entity_;
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		PaymentMethod entity_ = getInputtedEntity();
		if (entity_ == null) {
			this.clear();
		} else {
			entity = entity_;
		}
	}

}
