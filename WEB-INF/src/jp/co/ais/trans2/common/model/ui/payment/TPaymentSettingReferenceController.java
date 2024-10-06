package jp.co.ais.trans2.common.model.ui.payment;

import java.awt.event.*;
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
 * 支払条件検索コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TPaymentSettingReferenceController extends TReferenceController {

	/** 口座名義カナを使う */
	public static boolean IS_USE_ACCOUNT_KANA = ClientConfig.isFlagOn("trans.tri.sj.ref.use.account.kana");

	/** 検索条件 */
	protected PaymentSettingSearchCondition condition;

	/**
	 * @param field 支払条件コンポーネント
	 */
	public TPaymentSettingReferenceController(TReference field) {
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
	protected PaymentSettingSearchCondition createSearchCondition() {
		return new PaymentSettingSearchCondition();
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
	public boolean code_Verify(@SuppressWarnings("unused") JComponent comp) {

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

			// 有効期限チェック
			if (!isInValidTerm()) {
				return false;
			}

			field.code.setText(getEntity().getCode());
			field.name.setText(getBankAndBranchName(getEntity()));

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
			PaymentSettingSearchCondition condition_ = getCondition().clone();
			// コード前方一致
			condition_.setCodeLike(dialog.code.getText());
			// 略称あいまい
			condition_.setNameLike(dialog.names.getText());

			List<PaymentSetting> list = getList(condition_);

			if (list == null || list.isEmpty()) {
				if (isCheckExist()) {
					showMessage(dialog, "I00051");
				}
				return;
			} else {
				// フィルターを行う

				List<PaymentSetting> newList = new ArrayList<PaymentSetting>();

				String namek = dialog.namek.getText();
				String accountKana = "";

				if (IS_USE_ACCOUNT_KANA && dialog instanceof TPaymentSettingReferenceDialog) {
					accountKana = ((TPaymentSettingReferenceDialog) dialog).accountKana.getText();
				}

				for (PaymentSetting bean : list) {

					// 口座番号不一致
					if (!Util.isNullOrEmpty(namek) && !getDepositKindAndAcountNoNo(bean).contains(namek)) {
						continue;
					}

					// 口座名義カナ不一致
					if (IS_USE_ACCOUNT_KANA) {
						if (!Util.isNullOrEmpty(accountKana)
								&& !Util.avoidNullNT(bean.getAccountNameKana()).contains(accountKana)) {
							continue;
						}
					}

					newList.add(bean);
				}

				list = newList;

				if (list.isEmpty()) {
					if (isCheckExist()) {
						showMessage(dialog, "I00051");
					}
					return;
				}
			}

			// 一覧にセット
			for (PaymentSetting bean : list) {
				dialog.tbl.addRow(new Object[] { bean, bean.getCode(), getBankAndBranchName(bean),
						getDepositKindAndAcountNoNo(bean), bean.getAccountNameKana() });
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
	 * 「銀行名＋支店名」の取得<br>
	 * 預金種目：外貨の場合は、「被仕向銀行名＋被仕向支店名」
	 * 
	 * @param bean 支払条件
	 * @return 銀行名と支店名
	 */
	protected String getBankAndBranchName(PaymentSetting bean) {

		if (bean == null) {
			return "";
		}
		if (bean.getDepositKind() == DepositKind.FOREIGN_CURRENCY) {
			if (Util.isNullOrEmpty(bean.getSendBankName()) && Util.isNullOrEmpty(bean.getSendBranchName())) {
				// 英文銀行名、支店名未設定の場合は銀行名と支店名を使う
				return bean.getBankAndBranchName();
			}
			return Util.avoidNullNT(bean.getSendBankName()) + " " + Util.avoidNullNT(bean.getSendBranchName());
		}

		return bean.getBankAndBranchName();
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
				field.name.setText(getBankAndBranchName(getEntity()));
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
	 * 口座種別＋口座番号を返す
	 * 
	 * @param entity_f
	 * @return String
	 */
	protected String getDepositKindAndAcountNoNo(PaymentSetting entity_) {
		boolean isHide = false;
		try {
			isHide = entity_.getCustomer().isPersonalCustomer();
		} catch (Exception e) {
			//
			e.printStackTrace();
		}
		String accountNo = Util.avoidNull(entity_.getAccountNo());
		if (isHide) {
			accountNo = "*********";
		}
		String account = getWord(DepositKind.getDepositKindName(entity_.getDepositKind())) + " "
				+ accountNo;

		return account;
	}

	/**
	 * Classを返す
	 * 
	 * @return Class
	 */
	protected Class getModelClass() {
		return PaymentSettingManager.class;
	}

	/**
	 * 入力された支払条件を返す
	 * 
	 * @return Entity
	 */
	protected PaymentSetting getInputtedEntity() {

		try {

			if (Util.isNullOrEmpty(field.getCode())) {
				return null;
			}

			PaymentSettingSearchCondition searchCondition = condition.clone();
			searchCondition.setCode(field.getCode());
			searchCondition.setCodeFrom(null);
			searchCondition.setCodeTo(null);

			// 有効期限切れを任意で選ばせる為に条件からは削除
			if (getCompany().getAccountConfig().isSlipTermCheck()) {
				searchCondition.setValidTerm(null);
			}

			List<PaymentSetting> list = getList(searchCondition);

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
	 * 検索条件に該当する支払条件リストを返す
	 * 
	 * @param condition_ 検索条件
	 * @return 検索条件に該当する支払条件リスト
	 */
	protected List<PaymentSetting> getList(PaymentSettingSearchCondition condition_) {

		try {

			List<PaymentSetting> list = (List<PaymentSetting>) request(getModelClass(), "get", condition_);

			return list;

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * @return 検索条件を戻します。
	 */
	public PaymentSettingSearchCondition getCondition() {
		return condition;
	}

	/**
	 * 選択された支払条件を返す
	 * 
	 * @return 選択された支払条件
	 */
	protected PaymentSetting getSelectedEntity() {
		return (PaymentSetting) dialog.tbl.getSelectedRowValueAt(TReferenceDialog.SC.bean);
	}

	@Override
	public String getDialogCaption() {
		return "C10756";// 取引先支払条件
	}

	/**
	 * コードのキャプションを取得します
	 * 
	 * @return コードのキャプション
	 */
	@Override
	public String getCodeCaption() {
		return "C10757";// 取引先条件
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
		return "C01185";// 振込先口座
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
	public PaymentSetting getEntity() {
		return (PaymentSetting) super.getEntity();
	}

	/**
	 * フィールドを返す
	 * 
	 * @return フィールド
	 */
	public TPaymentSettingReference getField() {
		return (TPaymentSettingReference) field;
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
	}

	/**
	 * Entityをセットする
	 * 
	 * @param paymentSetting 支払条件
	 */
	public void setEntity(PaymentSetting paymentSetting) {
		if (paymentSetting == null) {
			clear();
			return;
		}

		field.code.setText(paymentSetting.getCode());
		field.name.setText(getBankAndBranchName(paymentSetting));
		getField().accountNo.setText(getDepositKindAndAcountNoNo(paymentSetting));
		entity = paymentSetting;
	}

	/**
	 * 存在チェックをしない場合の入力途中未確定Entityを返す
	 * 
	 * @return 存在チェックをしない場合の入力途中未確定Entity
	 */
	@Override
	public Object getUnspecifiedEntity() {
		PaymentSetting entity_ = new PaymentSetting();
		entity_.setCode(field.code.getText());
		return entity_;
	}

	/**
	 * 新しい条件で再検索を行い、適正値でない場合はクリアする.
	 */
	public void refleshEntity() {
		PaymentSetting bean = getInputtedEntity();
		setEntity(bean);
	}

	/**
	 * 検索ダイアログのインスタンスを生成し返す
	 * 
	 * @return 検索ダイアログ
	 */
	@Override
	protected TReferenceDialog createDialog() {
		if (IS_USE_ACCOUNT_KANA) {
			return new TPaymentSettingReferenceDialog(this);
		} else {
			return super.createDialog();
		}
	}

	/**
	 * 検索ダイアログで発生するイベントを追加する
	 */
	@Override
	protected void addDialogEvent() {
		super.addDialogEvent();

		if (IS_USE_ACCOUNT_KANA && dialog instanceof TPaymentSettingReferenceDialog) {
			// 口座名義カナでEnterで検索実行
			((TPaymentSettingReferenceDialog) dialog).accountKana.addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent e) {
					dialog_code_keyPressed(e);
				}
			});
		}
	}

}
