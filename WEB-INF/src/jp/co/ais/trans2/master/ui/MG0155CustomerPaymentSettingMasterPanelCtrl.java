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
 * 取引先支払条件マスタのコントローラー
 * 
 * @author AIS
 */
public class MG0155CustomerPaymentSettingMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0155CustomerPaymentSettingMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0155CustomerPaymentSettingMasterDialog editView = null;

	/** 振込手数料のデフォルトを先方とするか */
	protected boolean isDefaultOther = ClientConfig.isFlagOn("trans.MG0155.commission.default.other");

	/** 操作モード。現在操作中のモードを把握するために使用する。 */
	protected Mode mode = null;

	/** 操作モード。 */
	protected enum Mode {
		/** 新規 */
		NEW,
		/** 修正 */
		MODIFY,
		/** 複写 */
		COPY
	}

	/**
	 * 画面生成及び初期化
	 */
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

	/**
	 * パネル取得
	 */
	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MG0155CustomerPaymentSettingMasterPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面のイベント定義。
	 */
	protected void addMainViewEvent() {

		// [新規]ボタン押下
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [検索]ボタン押下
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [編集]ボタン押下
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [複写]ボタン押下
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [削除]ボタン押下
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [エクセル]ボタン押下
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
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
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02325") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0155CustomerPaymentSettingMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent();
	}

	/**
	 * 編集画面のイベント定義。
	 */
	protected void addEditViewEvent() {

		// [確定]ボタン押下
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [戻る]ボタン押下
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
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

				if (bank != null) {
					editView.ctrlBranch.clear();
					editView.ctrlBranch.setEditable(true);
					editView.ctrlBranch.getSearchCondition().setBankCode(bank.getCode());
				} else {
					editView.ctrlBranch.clear();
					editView.ctrlBranch.setEditable(false);
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

		switch (editMode) {

			case NEW:
				// 新規s
				editView.setTitle(getWord("C01698"));
				editView.ctrldtBeginDate.setValue(editView.ctrldtBeginDate.getCalendar().getMinimumDate());
				editView.ctrldtEndDate.setValue(editView.ctrldtEndDate.getCalendar().getMaximumDate());
				editView.ctrlBranch.setEditable(false);
				if (isDefaultOther) {
					editView.ctrlcboCashInFee.setSelectedItemValue(CashInFeeType.Other);
				}
				break;

			case MODIFY:
			case COPY:
				// 編集
				if (Mode.MODIFY == editMode) {
					editView.setTitle(getWord("C00977"));
					editView.setEditMode();
					editView.ctrlCustomerReference.setEditable(false);
					editView.ctrlCustomerConditionCode.setEditable(false);

					// 複写
				} else {
					editView.setTitle(getWord("C01738"));
				}

				editView.ctrlCustomerReference.setEntity(paymentSetting.getCustomer());

				editView.ctrlCustomerConditionCode.setValue(paymentSetting.getCode());

				editView.ctrlPaymentMethodReference.setEntity(paymentSetting.getPaymentMethod());

				editView.ctrlpaymentConditionDay.setValue(paymentSetting.getCloseDay());
				editView.ctrlpaymentConditionMonth.setValue(paymentSetting.getNextMonth());
				editView.ctrlpaymentConditionPayDay.setValue(paymentSetting.getCashDay());

				editView.ctrlBankAccountReference.setEntity(paymentSetting.getBankAccount());

				editView.ctrlBank.code.setValue(paymentSetting.getBankCode());
				editView.ctrlBank.name.setValue(paymentSetting.getBankName());
				editView.ctrlBranch.code.setValue(paymentSetting.getBranchCode());
				editView.ctrlBranch.name.setValue(paymentSetting.getBranchName());
				editView.ctrlBranch.getSearchCondition().setBankCode(paymentSetting.getBankCode());

				editView.ctrlRemittanceReference.setEntity(paymentSetting.getRemittancePurpose());

				editView.ctrlcboPaymentDataType.setSelectedItemValue(paymentSetting.getPaymentDateType());
				editView.ctrlcboDepositKind.setSelectedItemValue(paymentSetting.getDepositKind());
				editView.ctrlcboCashInFee.setSelectedItemValue(paymentSetting.getCashInFeeType());
				editView.ctrlcboCommissionPaymentType.setSelectedItemValue(paymentSetting.getCommissionPaymentType());
				editView.ctrlAccountNumber.setValue(paymentSetting.getAccountNo());
				editView.ctrlEnglishBankName.setValue(paymentSetting.getSendBankName());
				editView.ctrlEnglishBranchName.setValue(paymentSetting.getSendBranchName());
				editView.ctrlEnglishBankAddress.setValue(paymentSetting.getAccountName());
				editView.ctrlAccountKana.setValue(paymentSetting.getAccountNameKana());
				editView.ctrlPaymentBankName.setValue(paymentSetting.getPayBankName());
				editView.ctrlPaymentBranchName.setValue(paymentSetting.getPayBranchName());
				editView.ctrlPaymentBankAddress.setValue(paymentSetting.getPayBankAddress());
				editView.ctrlViaBankName.setValue(paymentSetting.getViaBankName());
				editView.ctrlViaBranchName.setValue(paymentSetting.getViaBranchName());
				editView.ctrlViaBankAddress.setValue(paymentSetting.getViaBankAddress());
				editView.ctrlRecipientAddress.setValue(paymentSetting.getReceiverAddress());
				editView.ctrldtBeginDate.setValue(paymentSetting.getDateFrom());
				editView.ctrldtEndDate.setValue(paymentSetting.getDateTo());
		}
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

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entry", paymentSetting);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(paymentSetting));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", paymentSetting);

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(paymentSetting));

			}

			// 編集画面を閉じる
			btnClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 編集画面で入力されたデータを返す
	 * 
	 * @return 編集画面で入力されたデータ
	 */
	protected PaymentSetting getInputtedPaymentSetting() {

		PaymentSetting paymentSetting = createPaymentSetting();
		paymentSetting.setCompanyCode(getCompany().getCode());
		paymentSetting.setCustomer(editView.ctrlCustomerReference.getEntity());
		paymentSetting.setCode(editView.ctrlCustomerConditionCode.getValue());
		paymentSetting.setPaymentMethod(editView.ctrlPaymentMethodReference.getEntity());
		paymentSetting.setPaymentDateType(getSelectedPaymentDataType());
		paymentSetting.setCloseDay(editView.ctrlpaymentConditionDay.getText());
		paymentSetting.setNextMonth(editView.ctrlpaymentConditionMonth.getText());
		paymentSetting.setCashDay(editView.ctrlpaymentConditionPayDay.getText());
		paymentSetting.setBankAccount(editView.ctrlBankAccountReference.getEntity());
		paymentSetting.setBankCode(editView.ctrlBank.getCode());
		paymentSetting.setBankName(editView.ctrlBank.getNames());
		paymentSetting.setBranchCode(editView.ctrlBranch.getCode());
		paymentSetting.setBranchName(editView.ctrlBranch.getNames());
		paymentSetting.setRemittancePurpose(editView.ctrlRemittanceReference.getEntity());
		paymentSetting.setDepositKind(getSelectedDepositKind());
		paymentSetting.setCashInFeeType(getSelectedCashInFeeType());
		paymentSetting.setCommissionPaymentType(getSelectedPayFeeType());
		paymentSetting.setAccountNo(editView.ctrlAccountNumber.getValue());
		paymentSetting.setSendBankName(editView.ctrlEnglishBankName.getValue());
		paymentSetting.setSendBranchName(editView.ctrlEnglishBranchName.getValue());
		paymentSetting.setAccountName(editView.ctrlEnglishBankAddress.getValue());
		paymentSetting.setAccountNameKana(editView.ctrlAccountKana.getText());
		paymentSetting.setPayBankName(editView.ctrlPaymentBankName.getValue());
		paymentSetting.setPayBranchName(editView.ctrlPaymentBranchName.getValue());
		paymentSetting.setPayBankAddress(editView.ctrlPaymentBankAddress.getValue());
		paymentSetting.setViaBankName(editView.ctrlViaBankName.getValue());
		paymentSetting.setViaBranchName(editView.ctrlViaBranchName.getValue());
		paymentSetting.setViaBankAddress(editView.ctrlViaBankAddress.getValue());
		paymentSetting.setReceiverAddress(editView.ctrlRecipientAddress.getValue());
		paymentSetting.setDateFrom(editView.ctrldtBeginDate.getValue());
		paymentSetting.setDateTo(editView.ctrldtEndDate.getValue());

		return paymentSetting;
	}

	/**
	 * @return 支払条件マスタの検索条件
	 */
	protected PaymentSettingSearchCondition createPaymentSettingSearchCondition() {
		return new PaymentSettingSearchCondition();
	}

	/**
	 * @return 支払条件マスタ情報
	 */
	protected PaymentSetting createPaymentSetting() {
		return new PaymentSetting();
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
	@SuppressWarnings("unchecked")
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
			.getSelectedRowValueAt(MG0155CustomerPaymentSettingMasterPanel.SC.customerCode));
		condition.setCode((String) mainView.tbl
			.getSelectedRowValueAt(MG0155CustomerPaymentSettingMasterPanel.SC.customerConditionCord));

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
	 * Servletクラスを返す
	 * 
	 * @return モデル
	 */
	protected Class getModelClass() {
		return PaymentSettingManager.class;
	}

	/**
	 * 支払条件情報を一覧に表示する形式に変換し返す
	 * 
	 * @param paymentSetting 支払条件情報
	 * @return 一覧に表示する形式に変換されたデータ
	 */
	protected List<Object> getRowData(PaymentSetting paymentSetting) {

		List<Object> list = new ArrayList<Object>();
		list.add(paymentSetting.getCustomer().getCode()); // 取引先コード
		list.add(paymentSetting.getCustomer().getNames()); // 取引先略称
		list.add(paymentSetting.getCode()); // 取引先条件コード
		list.add(getWord(CashInFeeType.getCashInFeeTypeName(paymentSetting.getCashInFeeType()))); // 振込手数料区分
		list.add(paymentSetting.getCloseDay()); // 締日
		list.add(paymentSetting.getNextMonth()); // 後月
		list.add(paymentSetting.getCashDay()); // 支払日
		list.add(getWord(PaymentDateType.getPaymentDateTypeName(paymentSetting.getPaymentDateType()))); // 支払区分

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
		list.add(paymentSetting.getAccountNo()); // 口座番号
		list.add(paymentSetting.getAccountNameKana()); // 口座名義カナ

		if (paymentSetting.getRemittancePurpose() != null) {
			list.add(paymentSetting.getRemittancePurpose().getName()); // 送金目的名
		} else {
			list.add("");
		}

		list.add(paymentSetting.getSendBankName()); // 英文銀行名
		list.add(paymentSetting.getSendBranchName()); // 英文支店名
		list.add(paymentSetting.getAccountName()); // 英文銀行住所
		list.add(getWord(CommissionPaymentType.getName(paymentSetting.getCommissionPaymentType()))); // 手数料区分
		list.add(paymentSetting.getPayBankName()); // 支払銀行名称
		list.add(paymentSetting.getPayBranchName()); // 支払支店名称
		list.add(paymentSetting.getPayBankAddress()); // 支払銀行住所
		list.add(paymentSetting.getViaBankName()); // 経由銀行名称
		list.add(paymentSetting.getViaBranchName()); // 経由支店名称
		list.add(paymentSetting.getViaBankAddress()); // 経由銀行住所
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

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// 取引先コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlCustomerReference.getCode())) {
			// 取引先コードを入力してください。
			showMessage(editView, "I00037", "C00786");
			editView.ctrlCustomerReference.requestTextFocus();
			return false;
		}

		// 取引先条件コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlCustomerConditionCode.getValue())) {
			// 取引先条件コードを入力してください。
			showMessage(editView, "I00037", "C00788");
			editView.ctrlCustomerConditionCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一取引先コードかつ、取引先条件コードが存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {

			PaymentSettingSearchCondition condition = createPaymentSettingSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCustomerCode(editView.ctrlCustomerReference.getCode());
			condition.setCode(editView.ctrlCustomerConditionCode.getValue());
			List<PaymentSetting> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				// 指定のデータは既に存在します。
				showMessage(editView, "I00262");
				editView.ctrlCustomerConditionCode.requestTextFocus();
				return false;
			}
		}

		// 支払方法が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlPaymentMethodReference.getCode())) {
			// 支払方法を入力してください。
			showMessage(editView, "I00037", "C00233");
			editView.ctrlPaymentMethodReference.requestTextFocus();
			return false;
		}

		// ～日締めが未入力の場合エラー
		if (editView.ctrlpaymentConditionDay.isEmpty()) {
			// 締め日を入力してください。
			showMessage(editView, "I00037", "C00244");
			editView.ctrlpaymentConditionDay.requestFocus();
			return false;
		}

		// 日付(数値)チェック
		int simDay = editView.ctrlpaymentConditionDay.getInt();
		if (simDay <= 0 || (31 < simDay && simDay != 99)) {
			// 締め日は1～31, 99の範囲で入力してください。
			showMessage(editView, "I00247", "C00244", "1", "31, 99");
			editView.ctrlpaymentConditionDay.requestFocus();
			return false;
		}

		// ～ヵ月後が未入力の場合エラー
		if (editView.ctrlpaymentConditionMonth.isEmpty()) {
			// 締め後月を入力してください。
			showMessage(editView, "I00037", "C11257");
			editView.ctrlpaymentConditionMonth.requestFocus();
			return false;
		}

		// ～日払いが未入力の場合エラー
		if (editView.ctrlpaymentConditionPayDay.isEmpty()) {
			// 支払日を入力してください。
			showMessage(editView, "I00037", "C01098");
			editView.ctrlpaymentConditionPayDay.requestFocus();
			return false;
		}

		// 日付(数値)チェック
		int payDay = editView.ctrlpaymentConditionPayDay.getInt();
		if (payDay <= 0 || (31 < payDay && payDay != 99)) {
			// 支払日は1～31, 99の範囲で入力してください。
			showMessage(editView, "I00247", "C01098", "1", "31, 99");
			editView.ctrlpaymentConditionPayDay.requestFocus();
			return false;
		}

		// 振込振出銀行コードが未入力の場合エラー
		// if (Util.isNullOrEmpty(editView.ctrlBankAccountReference.getCode())) {
		// // 振込振出銀行コードを入力してください。
		// showMessage(editView, "I00037", "C00792");
		// editView.ctrlBankAccountReference.requestTextFocus();
		// return false;
		// }

		// 銀行コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlBank.getCode())) {
			// 銀行コードを入力してください。
			showMessage(editView, "I00037", "C00779");
			editView.ctrlBank.requestTextFocus();
			return false;
		}

		// 支店コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlBranch.getCode())) {
			// 支店コードを入力してください。
			showMessage(editView, "I00037", "C02055");
			editView.ctrlBranch.requestTextFocus();
			return false;
		}

		// 口座番号が未入力の場合エラー
		PaymentMethod paymentMethod = editView.ctrlPaymentMethodReference.getEntity();

		if (paymentMethod != null && PaymentKind.isBankAccountPayment(paymentMethod.getPaymentKind())) {
			if (editView.ctrlAccountNumber.isEmpty()) {
				// 口座番号を入力してください。
				showMessage(editView, "I00037", "C00794");
				editView.ctrlAccountNumber.requestTextFocus();
				return false;
			}
		}

		// 預金種目が外貨時、英字銀行名および英字支店名が未入力の場合エラー
		if (getSelectedDepositKind() == DepositKind.FOREIGN_CURRENCY) {
			if (Util.isNullOrEmpty(editView.ctrlEnglishBankName.getValue())) {
				// 英文銀行名を入力してください。
				showMessage(editView, "I00037", "C00795");
				editView.ctrlEnglishBankName.requestTextFocus();
				return false;
			}

			if (Util.isNullOrEmpty(editView.ctrlEnglishBranchName.getValue())) {
				// 英文支店名を入力してください。
				showMessage(editView, "I00037", "C00796");
				editView.ctrlEnglishBranchName.requestTextFocus();
				return false;
			}
		}

		// 口座名義ｶﾅが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlAccountKana.getValue())) {
			// 口座名義ｶﾅを入力してください。
			showMessage(editView, "I00037", "C00168");
			editView.ctrlAccountKana.requestFocus();
			return false;
		}

		// 開始年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrldtBeginDate.getValue())) {
			// 開始年月日を入力してください。
			showMessage(editView, "I00037", "C00055");
			editView.ctrldtBeginDate.requestTextFocus();
			return false;
		}

		// 終了年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrldtEndDate.getValue())) {
			// 終了年月日を入力してください。
			showMessage(editView, "I00037", "C00261");
			editView.ctrldtEndDate.requestTextFocus();
			return false;
		}

		// 開始日付<=終了日付にしてください。
		if (!Util.isSmallerThenByYMD(editView.ctrldtBeginDate.getValue(), editView.ctrldtEndDate.getValue())) {
			// 開始日付<=終了日付にしてください。
			showMessage(editView, "I00090", getWord("C01013") + getWord("C00446"), getWord("C00260")
				+ getWord("C00446"));
			editView.ctrldtEndDate.requestTextFocus();
			return false;
		}

		return true;
	}

	/**
	 * 選択された振込手数料を返す
	 * 
	 * @return 選択された振込手数料
	 */
	protected CashInFeeType getSelectedCashInFeeType() {

		CashInFeeType selectedIndex = (CashInFeeType) editView.ctrlcboCashInFee.getSelectedItemValue();
		return selectedIndex;
	}

	/**
	 * 選択された支払区分を返す
	 * 
	 * @return 選択された支払区分
	 */
	protected PaymentDateType getSelectedPaymentDataType() {

		PaymentDateType selectedIndex = (PaymentDateType) editView.ctrlcboPaymentDataType.getSelectedItemValue();
		return selectedIndex;
	}

	/**
	 * 選択された支払手数料区分を返す
	 * 
	 * @return 選択された支払手数料区分
	 */
	protected CommissionPaymentType getSelectedPayFeeType() {

		CommissionPaymentType selectedIndex = (CommissionPaymentType) editView.ctrlcboCommissionPaymentType
			.getSelectedItemValue();
		return selectedIndex;
	}

	/**
	 * 選択された預金種目を返す
	 * 
	 * @return 選択された預金種目
	 */
	protected DepositKind getSelectedDepositKind() {

		DepositKind selectedIndex = (DepositKind) editView.ctrlcboDepositKind.getSelectedItemValue();
		return selectedIndex;
	}
}