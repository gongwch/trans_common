package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.bank.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 取引先支払条件マスタ(海外用)のコントローラー
 * 
 * @author AIS
 */
public class MG0156CustomerPaymentSettingMasterPanelCtrl extends TController {

	/** 操作モード。 */
	protected enum Mode {
		/** 新規 */
		NEW,
		/** 修正 */
		MODIFY,
		/** 複写 */
		COPY
	}

	/** 指示画面 */
	protected MG0156CustomerPaymentSettingMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0156CustomerPaymentSettingMasterDialog editView = null;

	/** 振込手数料のデフォルトを先方とするか */
	protected boolean isDefaultOther = ClientConfig.isFlagOn("trans.MG0155.commission.default.other");

	/** 操作モード。現在操作中のモードを把握するために使用する。 */
	protected Mode mode = null;

	@Override
	public void start() {

		try {
			// 指示画面生成
			createMainView();

			// 指示画面を初期化する
			initMainView();

			// 指示画面表示
			mainView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * Servletクラスを返す
	 * 
	 * @return モデル
	 */
	protected Class getModelClass() {
		return PaymentSettingManager.class;
	}

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MG0156CustomerPaymentSettingMasterPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面のイベント定義。
	 */
	protected void addMainViewEvent() {

		// [新規]ボタン押下
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [検索]ボタン押下
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [編集]ボタン押下
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [複写]ボタン押下
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [削除]ボタン押下
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [エクセル]ボタン押下
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * 指示画面を初期化する
	 */
	protected void initMainView() {

		// メインボタンを押下不可能にする
		setMainButtonEnabled(false);
	}

	/**
	 * 指示画面[新規]ボタン押下
	 */
	protected void btnNew_Click() {

		try {
			// 編集画面を生成
			createEditView();

			// 編集画面を初期化する
			initEditView(Mode.NEW, null);

			// 編集画面を表示
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 指示画面[検索]ボタン押下
	 */
	protected void btnSearch_Click() {

		try {
			// 開始コード、終了コードの入力チェック
			if (!Util.isSmallerThen(mainView.ctrlCustomerReferenceRange.getCodeFrom(),
				mainView.ctrlCustomerReferenceRange.getCodeTo())) {
				showMessage(editView, "I00068");
				mainView.ctrlCustomerReferenceRange.getFieldFrom().requestFocus();
				return;
			}

			// 検索条件を取得
			PaymentSettingSearchCondition condition = getSearchCondition();
			List<PaymentSetting> list = getList(condition);

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 検索条件に該当するデータが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 検索したデータを一覧に表示する
			for (PaymentSetting paymentSetting : list) {
				mainView.tbl.addRow(getRowData(paymentSetting));
			}

			// メインボタンを押下可能にし、1行目を選択する
			setMainButtonEnabled(true);
			mainView.tbl.setRowSelection(0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [編集]ボタン押下
	 */
	protected void btnModify_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// 編集対象のデータを取得する
			PaymentSetting paymentSetting = getSelectedPaymentSetting();

			// 編集画面を生成し、編集対象のデータをセットする
			createEditView();
			initEditView(Mode.MODIFY, paymentSetting);

			// 編集画面を表示する
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [複写]ボタン押下
	 */
	protected void btnCopy_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// 複写対象のレートを取得する
			PaymentSetting paymentSetting = getSelectedPaymentSetting();

			// 複写画面を生成し、複写対象のデータをセットする
			createEditView();
			initEditView(Mode.COPY, paymentSetting);

			// 複写画面を表示する
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [削除]ボタン押下
	 */
	protected void btnDelete_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除対象のデータを取得する
			PaymentSetting paymentSetting = getSelectedPaymentSetting();

			// 削除する
			doDelete(paymentSetting);

			// 削除した行を一覧から削除
			mainView.tbl.removeSelectedRow();

			// 削除した結果0件になったらメインボタンを押下不可能にする
			if (mainView.tbl.getRowCount() == 0) {
				setMainButtonEnabled(false);
			}

			// 完了メッセージ
			showMessage(mainView.getParentFrame(), "I00013");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 選択行のチェック
	 * 
	 * @return true:エラーなし
	 */
	protected boolean checkMainView() {

		if (mainView.tbl.getSelectedRow() == -1) {
			// 一覧からデータを選択してください。
			showMessage("I00043");
			return false;
		}

		return true;
	}

	/**
	 * [エクセル]ボタン押下
	 */
	protected void btnExcel_Click() {

		try {
			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// エクセルデータの取得
			PaymentSettingSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcelForGlobal", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02325") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0156CustomerPaymentSettingMasterDialog(mainView.getParentFrame());
		editView.lblViaCaution.setText(getWord("C01584")); // ※経由銀行は仕向銀行の非コルレス先

		// 編集画面のイベント定義
		addEditViewEvent();
	}

	/**
	 * 編集画面のイベント定義。
	 */
	protected void addEditViewEvent() {

		// [確定]ボタン押下
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [戻る]ボタン押下
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnClose_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 銀行
		editView.ctrlBank.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !editView.ctrlBank.isValueChanged()) {
					return;
				}

				ctrlBankReference_after();
			}

			protected void ctrlBankReference_after() {
				Bank bank = editView.ctrlBank.getEntity();

				editView.ctrlBranch.clear();
				editView.ctrlBranch.setEditable(bank != null);

				if (bank != null) {
					editView.ctrlBranch.getSearchCondition().setBankCode(bank.getCode());
				}
			}
		});
	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param editMode 操作モード。
	 * @param paymentSetting データ。修正、複写の場合は当該データを編集画面にセットする。
	 */
	protected void initEditView(Mode editMode, PaymentSetting paymentSetting) {

		this.mode = editMode;

		// コンボボックスの中身構築

		// 支払区分
		editView.cbxPaymentDataType.addTextValueItem(PaymentDateType.TEMPORARY,
			getWord(PaymentDateType.TEMPORARY.getName()));
		editView.cbxPaymentDataType.addTextValueItem(PaymentDateType.REGULAR,
			getWord(PaymentDateType.REGULAR.getName()));

		// 預金種目
		editView.cbxDepositKind.addTextValueItem(DepositKind.ORDINARY, getWord(DepositKind.ORDINARY.getName()));
		editView.cbxDepositKind.addTextValueItem(DepositKind.CURRENT, getWord(DepositKind.CURRENT.getName()));
		editView.cbxDepositKind.addTextValueItem(DepositKind.FOREIGN_CURRENCY,
			getWord(DepositKind.FOREIGN_CURRENCY.getName()));
		editView.cbxDepositKind.addTextValueItem(DepositKind.SAVINGS, getWord(DepositKind.SAVINGS.getName()));
		editView.cbxDepositKind.addTextValueItem(DepositKind.OTHERS, getWord(DepositKind.OTHERS.getName()));

		// バンクチャージ区分
		editView.cbxBankChargeType.addTextValueItem(BankChargeType.SHA, getShortWord(BankChargeType.SHA.toString()));
		editView.cbxBankChargeType.addTextValueItem(BankChargeType.BEN, getShortWord(BankChargeType.BEN.toString()));
		editView.cbxBankChargeType.addTextValueItem(BankChargeType.OUR, getShortWord(BankChargeType.OUR.toString()));

		// 振込手数料
		editView.cbxCashInFee.addTextValueItem(CashInFeeType.Our, getWord(CashInFeeType.Our.getName()));
		editView.cbxCashInFee.addTextValueItem(CashInFeeType.Other, getWord(CashInFeeType.Other.getName()));

		// 手数料支払区分
		String receiverName = getWord("C00330") + getWord(CommissionPaymentType.Receiver.getName());
		editView.cbxCommissionPaymentType.addTextValueItem(CommissionPaymentType.Receiver, receiverName);
		String payerName = getWord("C00330") + getWord(CommissionPaymentType.Payer.getName());
		editView.cbxCommissionPaymentType.addTextValueItem(CommissionPaymentType.Payer, payerName);

		// 編集画面反映
		switch (editMode) {
			case NEW:
				// 新規
				editView.setTitle(getWord("C01698"));
				editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
				editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());
				editView.ctrlBranch.setEditable(false);
				if (isDefaultOther) {
					editView.cbxCashInFee.setSelectedItemValue(CashInFeeType.Other);
				}
				return;

			case MODIFY:
				editView.setTitle(getWord("C00977"));
				editView.setEditMode();
				editView.ctrlCustomer.setEditable(false);
				editView.txtCustomerConditionCode.setEditable(false);
				break;

			case COPY:
				editView.setTitle(getWord("C01738"));
				break;
		}

		editView.ctrlCustomer.setEntity(paymentSetting.getCustomer());
		editView.txtCustomerConditionCode.setValue(paymentSetting.getCode());
		editView.ctrlPaymentMethod.setEntity(paymentSetting.getPaymentMethod());

		editView.numPaymentDay.setValue(paymentSetting.getCloseDay());
		editView.numPaymentMonth.setValue(paymentSetting.getNextMonth());
		editView.numPaymentPayDay.setValue(paymentSetting.getCashDay());

		editView.ctrlBankAccount.setEntity(paymentSetting.getBankAccount());
		editView.ctrlBank.code.setValue(paymentSetting.getBankCode());
		editView.ctrlBank.name.setValue(paymentSetting.getBankName());
		editView.ctrlBranch.code.setValue(paymentSetting.getBranchCode());
		editView.ctrlBranch.name.setValue(paymentSetting.getBranchName());
		editView.ctrlBranch.getSearchCondition().setBankCode(paymentSetting.getBankCode());

		editView.ctrlRemittanceReference.setEntity(paymentSetting.getRemittancePurpose());

		editView.cbxDepositKind.setSelectedItemValue(paymentSetting.getDepositKind());
		editView.cbxPaymentDataType.setSelectedItemValue(paymentSetting.getPaymentDateType());
		editView.cbxBankChargeType.setSelectedItemValue(paymentSetting.getBankChargeType());

		editView.cbxCashInFee.setSelectedItemValue(paymentSetting.getCashInFeeType());
		editView.cbxCommissionPaymentType.setSelectedItemValue(paymentSetting.getCommissionPaymentType());

		editView.txtAccountNumber.setValue(paymentSetting.getAccountNo());
		editView.txtBankSwiftCode.setValue(paymentSetting.getBankSwiftCode());
		editView.ctrlBankCountry.setEntity(paymentSetting.getBankCountry());
		editView.txtEnBankName.setValue(paymentSetting.getSendBankName());
		editView.txtEnBranchName.setValue(paymentSetting.getSendBranchName());
		editView.txtEnBankAddress.setValue(paymentSetting.getAccountName());
		editView.txtAccountKana.setValue(paymentSetting.getAccountNameKana());

		editView.txtViaBankSwiftCode.setValue(paymentSetting.getViaBankSwiftCode());
		editView.ctrlViaBankCountry.setEntity(paymentSetting.getViaBankCountry());
		editView.txtViaBankName.setValue(paymentSetting.getViaBankName());
		editView.txtViaBranchName.setValue(paymentSetting.getViaBranchName());
		editView.txtViaBankAddress.setValue(paymentSetting.getViaBankAddress());

		editView.ctrlRecipientCountry.setEntity(paymentSetting.getRecipientCountry());
		editView.txtRecipientAddress.setValue(paymentSetting.getReceiverAddress());

		editView.dtBeginDate.setValue(paymentSetting.getDateFrom());
		editView.dtEndDate.setValue(paymentSetting.getDateTo());
	}

	/**
	 * 編集画面[確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {
			// 入力チェックを行う。
			if (!isInputCorrect()) {
				return;
			}

			// 入力されたデータを取得
			PaymentSetting paymentSetting = getInputtedPaymentSetting();

			switch (mode) {
				case NEW: // 新規
				case COPY:
					request(getModelClass(), "entry", paymentSetting);

					// 追加分を一覧に反映する
					mainView.tbl.addRow(getRowData(paymentSetting));

					// メインボタンを押下可能にする
					setMainButtonEnabled(true);

					break;

				case MODIFY: // 修正
					request(getModelClass(), "modify", paymentSetting);

					// 修正内容を一覧に反映する
					mainView.tbl.modifySelectedRow(getRowData(paymentSetting));

					break;
			}

			// 編集画面を閉じる
			btnClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// 取引先コードが未入力の場合エラー
		if (editView.ctrlCustomer.isEmpty()) {
			// 取引先コードを入力してください。
			showMessage(editView, "I00037", "C00786");
			editView.ctrlCustomer.requestTextFocus();
			return false;
		}

		// 取引先条件コードが未入力の場合エラー
		if (editView.txtCustomerConditionCode.isEmpty()) {
			// 取引先条件コードを入力してください。
			showMessage(editView, "I00037", "C00788");
			editView.txtCustomerConditionCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一取引先コードかつ、取引先条件コードが存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			PaymentSettingSearchCondition condition = createPaymentSettingSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCustomerCode(editView.ctrlCustomer.getCode());
			condition.setCode(editView.txtCustomerConditionCode.getValue());
			List<PaymentSetting> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				// 指定のデータは既に存在します。
				showMessage(editView, "I00262");
				editView.txtCustomerConditionCode.requestTextFocus();
				return false;
			}
		}

		// 支払方法が未入力の場合エラー
		if (editView.ctrlPaymentMethod.isEmpty()) {
			// 支払方法を入力してください。
			showMessage(editView, "I00037", "C00233");
			editView.ctrlPaymentMethod.requestTextFocus();
			return false;
		}

		// ～日締めが未入力の場合エラー
		if (editView.numPaymentDay.isEmpty()) {
			// 締め日を入力してください。
			showMessage(editView, "I00037", "C00244");
			editView.numPaymentDay.requestFocus();
			return false;
		}

		// 日付(数値)チェック
		int simDay = editView.numPaymentDay.getInt();
		if (simDay <= 0 || (31 < simDay && simDay != 99)) {
			// 締め日は1～31, 99の範囲で入力してください。
			showMessage(editView, "I00247", "C00244", "1", "31, 99");
			editView.numPaymentDay.requestFocus();
			return false;
		}

		// ～ヵ月後が未入力の場合エラー
		if (editView.numPaymentMonth.isEmpty()) {
			// 締め後月を入力してください。
			showMessage(editView, "I00037", "C11257");
			editView.numPaymentMonth.requestFocus();
			return false;
		}

		// ～日払いが未入力の場合エラー
		if (editView.numPaymentPayDay.isEmpty()) {
			// 支払日を入力してください。
			showMessage(editView, "I00037", "C01098");
			editView.numPaymentPayDay.requestFocus();
			return false;
		}

		// 日付(数値)チェック
		int payDay = editView.numPaymentPayDay.getInt();
		if (payDay <= 0 || (31 < payDay && payDay != 99)) {
			// 支払日は1～31, 99の範囲で入力してください。
			showMessage(editView, "I00247", "C01098", "1", "31, 99");
			editView.numPaymentPayDay.requestFocus();
			return false;
		}

		// 振込振出銀行コードが未入力の場合エラー
		// if (editView.ctrlBankAccount.isEmpty()) {
		// // 振込振出銀行コードを入力してください。
		// showMessage(editView, "I00037", "C00792");
		// editView.ctrlBankAccount.requestTextFocus();
		// return false;
		// }

		// 銀行コードが未入力の場合エラー
		if (editView.ctrlBank.isEmpty()) {
			// 銀行コードを入力してください。
			showMessage(editView, "I00037", "C00779");
			editView.ctrlBank.requestTextFocus();
			return false;
		}

		// 支店コードが未入力の場合エラー
		if (editView.ctrlBranch.isEmpty()) {
			// 支店コードを入力してください。
			showMessage(editView, "I00037", "C02055");
			editView.ctrlBranch.requestTextFocus();
			return false;
		}

		// 口座番号が未入力の場合エラー
		PaymentMethod paymentMethod = editView.ctrlPaymentMethod.getEntity();

		if (paymentMethod != null && PaymentKind.isBankAccountPayment(paymentMethod.getPaymentKind())) {
			if (editView.txtAccountNumber.isEmpty()) {
				// 口座番号を入力してください。
				showMessage(editView, "I00037", "C00794");
				editView.txtAccountNumber.requestTextFocus();
				return false;
			}
		}

		// 口座名義ｶﾅが未入力の場合エラー
		if (editView.txtAccountKana.isEmpty()) {
			// 口座名義ｶﾅを入力してください。
			showMessage(editView, "I00037", "C00168");
			editView.txtAccountKana.requestFocus();
			return false;
		}

		// 開始年月日が未入力の場合エラー
		if (editView.dtBeginDate.isEmpty()) {
			// 開始年月日を入力してください。
			showMessage(editView, "I00037", "C00055");
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// 終了年月日が未入力の場合エラー
		if (editView.dtEndDate.isEmpty()) {
			// 終了年月日を入力してください。
			showMessage(editView, "I00037", "C00261");
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		// 開始日付<=終了日付にしてください。
		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			// 開始日付<=終了日付にしてください。
			showMessage(editView, "I00090", "C00055", "C00261");
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		return true;
	}

	/**
	 * 編集画面で入力されたデータを返す
	 * 
	 * @return 編集画面で入力されたデータ
	 */
	protected PaymentSetting getInputtedPaymentSetting() {
		PaymentSetting paymentSetting = createPaymentSetting();
		paymentSetting.setCompanyCode(getCompany().getCode());
		paymentSetting.setCustomer(editView.ctrlCustomer.getEntity());
		paymentSetting.setCode(editView.txtCustomerConditionCode.getValue());
		paymentSetting.setPaymentMethod(editView.ctrlPaymentMethod.getEntity());
		paymentSetting.setPaymentDateType(getSelectedPaymentDataType());
		paymentSetting.setCloseDay(editView.numPaymentDay.getText());
		paymentSetting.setNextMonth(editView.numPaymentMonth.getText());
		paymentSetting.setCashDay(editView.numPaymentPayDay.getText());
		paymentSetting.setBankAccount(editView.ctrlBankAccount.getEntity());
		paymentSetting.setBankCode(editView.ctrlBank.getCode());
		paymentSetting.setBankName(editView.ctrlBank.getNames());
		paymentSetting.setBranchCode(editView.ctrlBranch.getCode());
		paymentSetting.setBranchName(editView.ctrlBranch.getNames());
		paymentSetting.setRemittancePurpose(editView.ctrlRemittanceReference.getEntity());
		paymentSetting.setDepositKind(getSelectedDepositKind());
		paymentSetting.setAccountNo(editView.txtAccountNumber.getValue());
		paymentSetting.setSendBankName(editView.txtEnBankName.getValue());
		paymentSetting.setSendBranchName(editView.txtEnBranchName.getValue());
		paymentSetting.setAccountName(editView.txtEnBankAddress.getValue());
		paymentSetting.setAccountNameKana(editView.txtAccountKana.getText());
		paymentSetting.setViaBankName(editView.txtViaBankName.getValue());
		paymentSetting.setViaBranchName(editView.txtViaBranchName.getValue());
		paymentSetting.setViaBankAddress(editView.txtViaBankAddress.getValue());
		paymentSetting.setReceiverAddress(editView.txtRecipientAddress.getValue());
		paymentSetting.setDateFrom(editView.dtBeginDate.getValue());
		paymentSetting.setDateTo(editView.dtEndDate.getValue());
		paymentSetting.setBankCountry(editView.ctrlBankCountry.getEntity()); // 銀行国コード
		paymentSetting.setBankSwiftCode(editView.txtBankSwiftCode.getValue()); // 銀行SWIFTコード
		paymentSetting.setViaBankCountry(editView.ctrlViaBankCountry.getEntity()); // 経由銀行国コード
		paymentSetting.setViaBankSwiftCode(editView.txtViaBankSwiftCode.getValue()); // 経由銀行SWIFTコード
		paymentSetting.setRecipientCountry(editView.ctrlRecipientCountry.getEntity()); // 受取人国コード
		paymentSetting.setBankChargeType((BankChargeType) editView.cbxBankChargeType.getSelectedItemValue()); // バンクチャージ区分
		paymentSetting.setCashInFeeType(getSelectedCashInFeeType());
		paymentSetting.setCommissionPaymentType(getSelectedPayFeeType());

		return paymentSetting;
	}

	/**
	 * @return 支払条件マスタの検索条件
	 */
	protected PaymentSettingSearchCondition createPaymentSettingSearchCondition() {
		return new PaymentSettingSearchCondition();
	}

	/**
	 * エンティティ生成
	 * 
	 * @return PaymentSetting
	 */
	protected PaymentSetting createPaymentSetting() {
		return new PaymentSetting();
	}

	/**
	 * 選択された振込手数料を返す
	 * 
	 * @return 選択された振込手数料
	 */
	protected CashInFeeType getSelectedCashInFeeType() {
		CashInFeeType selectedIndex = (CashInFeeType) editView.cbxCashInFee.getSelectedItemValue();
		return selectedIndex;
	}

	/**
	 * 選択された支払区分を返す
	 * 
	 * @return 選択された支払区分
	 */
	protected PaymentDateType getSelectedPaymentDataType() {
		PaymentDateType selectedIndex = (PaymentDateType) editView.cbxPaymentDataType.getSelectedItemValue();
		return selectedIndex;
	}

	/**
	 * 選択された支払手数料区分を返す
	 * 
	 * @return 選択された支払手数料区分
	 */
	protected CommissionPaymentType getSelectedPayFeeType() {
		CommissionPaymentType selectedIndex = (CommissionPaymentType) editView.cbxCommissionPaymentType
			.getSelectedItemValue();
		return selectedIndex;
	}

	/**
	 * 選択された預金種目を返す
	 * 
	 * @return 選択された預金種目
	 */
	protected DepositKind getSelectedDepositKind() {
		DepositKind selectedIndex = (DepositKind) editView.cbxDepositKind.getSelectedItemValue();
		return selectedIndex;
	}

	/**
	 * 編集画面[戻る]ボタン押下
	 */
	protected void btnClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * メインボタンの押下制御
	 * 
	 * @param bln enabled
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnModify.setEnabled(bln);
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
	}

	/**
	 * 検索条件に該当するリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当するリスト
	 * @throws Exception
	 */
	protected List<PaymentSetting> getList(PaymentSettingSearchCondition condition) throws Exception {
		List<PaymentSetting> list = (List<PaymentSetting>) request(getModelClass(), "get", condition);
		return list;
	}

	/**
	 * 一覧で選択されているデータを返す
	 * 
	 * @return 一覧で選択されているデータ
	 * @throws Exception
	 */
	protected PaymentSetting getSelectedPaymentSetting() throws Exception {
		PaymentSettingSearchCondition condition = createPaymentSettingSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCustomerCode((String) mainView.tbl
			.getSelectedRowValueAt(MG0156CustomerPaymentSettingMasterPanel.SC.customerCode));
		condition.setCode((String) mainView.tbl
			.getSelectedRowValueAt(MG0156CustomerPaymentSettingMasterPanel.SC.customerConditionCode));

		List<PaymentSetting> list = getList(condition);

		if (list == null || list.isEmpty()) {
			throw new TException("I00038");
		}

		return list.get(0);
	}

	/**
	 * 指示画面で入力されたデータの検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected PaymentSettingSearchCondition getSearchCondition() {
		PaymentSettingSearchCondition condition = createPaymentSettingSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCustomerCodeFrom(mainView.ctrlCustomerReferenceRange.getCodeFrom());
		condition.setCustomerCodeTo(mainView.ctrlCustomerReferenceRange.getCodeTo());

		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;
	}

	/**
	 * 支払条件情報を一覧に表示する形式に変換し返す
	 * 
	 * @param paymentSetting 支払条件情報
	 * @return 一覧に表示する形式に変換されたデータ
	 */
	protected List<Object> getRowData(PaymentSetting paymentSetting) {
		List<Object> list = new ArrayList<Object>();
		list.add(paymentSetting);
		list.add(paymentSetting.getCustomer().getCode()); // 取引先コード
		list.add(paymentSetting.getCustomer().getNames()); // 取引先略称
		list.add(paymentSetting.getCode()); // 取引先条件コード
		list.add(paymentSetting.getCloseDay()); // 締日
		list.add(paymentSetting.getNextMonth()); // 後月
		list.add(paymentSetting.getCashDay()); // 支払日
		list.add(getWord(paymentSetting.getPaymentDateType().getName())); // 支払区分

		// 支払方法
		if (paymentSetting.getPaymentMethod() != null) {
			list.add(paymentSetting.getPaymentMethod().getName());
		} else {
			list.add("");
		}

		// 振込振出銀行口座コード
		if (paymentSetting.getBankAccount() != null) {
			list.add(paymentSetting.getBankAccount().getCode());
		} else {
			list.add("");
		}

		list.add(paymentSetting.getBankCode()); // 銀行コード
		list.add(paymentSetting.getBankName()); // 銀行名称
		list.add(paymentSetting.getBranchCode()); // 支店コード
		list.add(paymentSetting.getBranchName()); // 支店名称
		list.add(getWord(DepositKind.getDepositKindName(paymentSetting.getDepositKind()))); // 預金種目
		list.add(paymentSetting.getBankChargeType() != null ? paymentSetting.getBankChargeType().toString() : ""); // バンクチャージ区分
		list.add(getWord(CashInFeeType.getCashInFeeTypeName(paymentSetting.getCashInFeeType()))); // 振込手数料区分
		list.add(getWord(CommissionPaymentType.getName(paymentSetting.getCommissionPaymentType()))); // 手数料区分
		list.add(paymentSetting.getAccountNo()); // 口座番号
		list.add(paymentSetting.getBankSwiftCode()); // 銀行SWIFTコード
		list.add(paymentSetting.getSendBankName()); // 英文銀行名
		list.add(paymentSetting.getSendBranchName()); // 英文支店名

		// 銀行国コード
		if (paymentSetting.getBankCountry() != null) {
			list.add(paymentSetting.getBankCountry().getCode());
		} else {
			list.add("");
		}

		list.add(paymentSetting.getAccountName()); // 英文銀行住所
		list.add(paymentSetting.getAccountNameKana()); // 口座名義カナ

		if (paymentSetting.getRemittancePurpose() != null) {
			list.add(paymentSetting.getRemittancePurpose().getName()); // 送金目的名
		} else {
			list.add("");
		}

		list.add(paymentSetting.getViaBankSwiftCode()); // 経由銀行SWIFTコード
		list.add(paymentSetting.getViaBankName()); // 経由銀行名称
		list.add(paymentSetting.getViaBranchName()); // 経由支店名称

		// 経由銀行国コード
		if (paymentSetting.getViaBankCountry() != null) {
			list.add(paymentSetting.getViaBankCountry().getCode());
		} else {
			list.add("");
		}

		list.add(paymentSetting.getViaBankAddress()); // 経由銀行住所

		// 受取人国コード
		if (paymentSetting.getRecipientCountry() != null) {
			list.add(paymentSetting.getRecipientCountry().getCode());
		} else {
			list.add("");
		}

		list.add(paymentSetting.getReceiverAddress()); // 受取人住所

		list.add(DateUtil.toYMDString(paymentSetting.getDateFrom())); // 開始年月日
		list.add(DateUtil.toYMDString(paymentSetting.getDateTo())); // 終了年月日

		return list;
	}

	/**
	 * 指定の行データを削除する
	 * 
	 * @param paymentSetting データ
	 * @throws Exception
	 */
	protected void doDelete(PaymentSetting paymentSetting) throws Exception {
		request(getModelClass(), "delete", paymentSetting);
	}

}
