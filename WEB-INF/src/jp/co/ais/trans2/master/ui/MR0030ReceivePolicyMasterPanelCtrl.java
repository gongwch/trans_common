package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.bill.*;

/**
 * MR0030ReceivePolicyMaster - 入金方針マスタ - Main Controller
 * 
 * @author AIS
 */
public class MR0030ReceivePolicyMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MR0030ReceivePolicyMasterPanel mainView = null;

	/** 現在操作中のモードを把握するために使用する */
	protected Mode mode = null;

	/** 入金方針 */
	protected ReceivePolicy receivePolicy = null;

	/** AR：請求書番号自動採番を使用する */
	protected static boolean isUseARInvAutoNumbering = ClientConfig.isFlagOn("trans.use.ar.inv.auto.numbering");

	/** 操作モード */
	protected enum Mode {

		/** 新規 */
		NEW,
		/** 修正 */
		MODIFY,
		/** 複写 */
		COPY

	}

	@Override
	public void start() {
		try {

			// 指示画面生成
			createMainView();

			// 指示画面初期化
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
	 * 指示画面生成, イベント定義
	 */
	protected void createMainView() {
		mainView = new MR0030ReceivePolicyMasterPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面のイベント定義
	 */
	protected void addMainViewEvent() {

		// [確定]ボタン押下
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [取消]ボタン押下
		mainView.btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCancel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 自動採番を使用するチェックをOFFチェック
		mainView.chkInvoiceNo.addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				setInvoiceNoEditable(false);
			}
		});
		// 自動設定項目1
		mainView.cboInvoiceNo1.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {

				InvoiceNoAdopt adopt = (InvoiceNoAdopt) mainView.cboInvoiceNo1.getSelectedItemValue();
				if (InvoiceNoAdopt.FIXED_CHAR != adopt) {
					mainView.cboInvoiceNo1.setEditable(false);
					mainView.ctrlInvoiceNo1Name.setText(null);
					mainView.ctrlInvoiceNo1Name.setEnabled(false);
				} else {
					mainView.ctrlInvoiceNo1Name.setEnabled(true);
				}
			}
		});
		// 自動設定項目2
		mainView.cboInvoiceNo2.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {

				InvoiceNoAdopt adopt = (InvoiceNoAdopt) mainView.cboInvoiceNo2.getSelectedItemValue();
				if (InvoiceNoAdopt.FIXED_CHAR != adopt) {
					mainView.cboInvoiceNo2.setEditable(false);
					mainView.ctrlInvoiceNo2Name.setText(null);
					mainView.ctrlInvoiceNo2Name.setEnabled(false);
				} else {
					mainView.ctrlInvoiceNo2Name.setEnabled(true);
				}
			}
		});
		// 自動設定項目3
		mainView.cboInvoiceNo3.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {

				InvoiceNoAdopt adopt = (InvoiceNoAdopt) mainView.cboInvoiceNo3.getSelectedItemValue();
				if (InvoiceNoAdopt.FIXED_CHAR != adopt) {
					mainView.cboInvoiceNo3.setEditable(false);
					mainView.ctrlInvoiceNo3Name.setText(null);
					mainView.ctrlInvoiceNo3Name.setEnabled(false);
				} else {
					mainView.ctrlInvoiceNo3Name.setEnabled(true);
				}
			}
		});

	}

	/**
	 * 自動採番を使用する チェックボックス変更処理
	 * 
	 * @param isStart true:初期起動
	 */
	protected void setInvoiceNoEditable(boolean isStart) {
		// 全ての項目を使用可
		if (mainView.chkInvoiceNo.isSelected()) {

			mainView.cboInvoiceNo1.setEnabled(true);
			mainView.cboInvoiceNo2.setEnabled(true);
			mainView.cboInvoiceNo3.setEnabled(true);
			mainView.ctrlInvoiceNoDigit.setEnabled(true);
			if (!isStart) {
				mainView.ctrlInvoiceNoDigit.setNumber(3);
			}
		} else {

			mainView.cboInvoiceNo1.setEnabled(false);
			mainView.cboInvoiceNo2.setEnabled(false);
			mainView.cboInvoiceNo3.setEnabled(false);

			mainView.ctrlInvoiceNo1Name.clear();
			mainView.ctrlInvoiceNo2Name.clear();
			mainView.ctrlInvoiceNo3Name.clear();

			mainView.ctrlInvoiceNo1Name.setEnabled(false);
			mainView.ctrlInvoiceNo2Name.setEnabled(false);
			mainView.ctrlInvoiceNo3Name.setEnabled(false);

			mainView.ctrlInvoiceNoDigit.clear();
			mainView.ctrlInvoiceNoDigit.setEnabled(false);

		}
		if (!isStart) {
			// 初期値：なし は共通
			mainView.cboInvoiceNo1.setSelectedItemValue(InvoiceNoAdopt.NONE);
			mainView.cboInvoiceNo2.setSelectedItemValue(InvoiceNoAdopt.NONE);
			mainView.cboInvoiceNo3.setSelectedItemValue(InvoiceNoAdopt.NONE);
		}
	}

	/**
	 * 指示画面[確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {
			// 入力チェックを行う。
			if (!isInputCheck()) {
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			ReceivePolicy bean = getInputedReceivePolicy();
			request(getModelClass(), "modify", bean);

			// 完了メッセージ
			showMessage(mainView, "I00008");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 指示画面[取消]ボタン押下
	 */
	protected void btnCancel_Click() {

		// 確認メッセージ
		if (!showConfirmMessage(mainView, "Q00002")) {
			return;
		}
		initMainView();
	}

	/**
	 * 指示画面初期化
	 */
	protected void initMainView() {

		ReceivePolicy bean = null;

		try {

			bean = (ReceivePolicy) request(getModelClass(), "get");

		} catch (TException e) {

			errorHandler(e);
		}

		if (bean == null) {
			// 検索条件に該当するデータが存在しない場合、初期値を表示
			mainView.ctrlRecordLength.setNumber(200);
			mainView.ctrlCRLF.setSelected(false);
			mainView.rdoFormatA.setSelected(true);
			mainView.ctrlDifferenceToleranceStr.setValue(0);
			mainView.ctrlDifferenceToleranceEnd.setValue(0);
			mainView.chkBillNoInputFlg.setSelected(false);
			mainView.chkBillCreateFlg.setSelected(false);
		} else {
			// 該当データが存在する場合、取得値を表示
			mainView.ctrlRecordLength.setNumber(bean.getLength());
			// CR/LF付
			if (bean.getLineType().equals("1")) {
				mainView.ctrlCRLF.setSelected(true);
			} else {
				mainView.ctrlCRLF.setSelected(false);
			}
			// フォーマット
			if (bean.getFormat().equals("1")) {
				mainView.rdoFormatB.setSelected(true);
			} else {
				mainView.rdoFormatA.setSelected(true);
			}

			mainView.ctrlDifferenceToleranceStr.setNumber(bean.getFeeFrom().intValue());
			mainView.ctrlDifferenceToleranceEnd.setNumber(bean.getFeeTo().intValue());

			// 科目コード
			mainView.ctrlItemGroup.ctrlItemReference.setCode(bean.getItemCode());
			// 補助コード
			mainView.ctrlItemGroup.ctrlSubItemReference.setCode(bean.getSubItemCode());
			// 内訳コード
			mainView.ctrlItemGroup.ctrlDetailItemReference.setCode(bean.getDetailCode());
			// 科目、補助、内訳の略称
			mainView.ctrlItemGroup.refleshGroupEntity();

			// 計上部門
			mainView.ctrlDepartment.setCode(bean.getDepartmentCode());
			mainView.ctrlDepartment.refleshEntity();

			// 消費税
			mainView.ctrlConsumptionTax.setCode(bean.getTaxCode());
			mainView.ctrlConsumptionTax.refleshEntity();

			mainView.chkBillNoInputFlg.setSelected(bean.isCompulsoryInputBillNo());
			mainView.chkBillCreateFlg.setSelected(bean.isCheckBillMake());

			// 請求番号 採番管理
			mainView.chkInvoiceNo.setSelected(bean.isInvNumbering());
			mainView.cboInvoiceNo1.setSelectedItemValue(bean.getInvoiceNoAdopt1());
			mainView.cboInvoiceNo2.setSelectedItemValue(bean.getInvoiceNoAdopt2());
			mainView.cboInvoiceNo3.setSelectedItemValue(bean.getInvoiceNoAdopt3());
			mainView.ctrlInvoiceNo1Name.setText(bean.getInvNo1Name());
			mainView.ctrlInvoiceNo2Name.setText(bean.getInvNo2Name());
			mainView.ctrlInvoiceNo3Name.setText(bean.getInvNo3Name());
			mainView.ctrlInvoiceNoDigit.setNumber(bean.getInvNoDigit());

		}

		if (isUseARInvAutoNumbering) {
			// 自動採番を使用する処理
			setInvoiceNoEditable(true);
		} else {
			// 画面非表示
			mainView.pnlInvoiceNo.setVisible(false);
			mainView.chkInvoiceNo.setVisible(false);
			mainView.cboInvoiceNo1.setVisible(false);
			mainView.cboInvoiceNo2.setVisible(false);
			mainView.cboInvoiceNo3.setVisible(false);
			mainView.ctrlInvoiceNo1Name.setVisible(false);
			mainView.ctrlInvoiceNo2Name.setVisible(false);
			mainView.ctrlInvoiceNo3Name.setVisible(false);
			mainView.ctrlInvoiceNoDigit.setVisible(false);
		}
	}

	/**
	 * 画面で入力されたデータを返す
	 * 
	 * @return 画面で入力されたデータ
	 */
	protected ReceivePolicy getInputedReceivePolicy() {

		ReceivePolicy bean = new ReceivePolicy();

		bean.setCompanyCode(getCompanyCode());
		bean.setLength(mainView.ctrlRecordLength.getIntValue());

		if (mainView.ctrlCRLF.isSelected() == true) {
			bean.setLineType("1");
		} else {
			bean.setLineType("0");
		}

		if (mainView.rdoFormatA.isSelected() == true) {
			bean.setFormat("0");
		} else {
			bean.setFormat("1");
		}

		bean.setFeeFrom(mainView.ctrlDifferenceToleranceStr.getBigDecimal());
		bean.setFeeTo(mainView.ctrlDifferenceToleranceEnd.getBigDecimal());

		bean.setItemCode(mainView.ctrlItemGroup.ctrlItemReference.getCode());
		bean.setSubItemCode(mainView.ctrlItemGroup.ctrlSubItemReference.getCode());
		bean.setDetailCode(mainView.ctrlItemGroup.ctrlDetailItemReference.getCode());

		bean.setDepartmentCode(mainView.ctrlDepartment.getCode());
		bean.setTaxCode(mainView.ctrlConsumptionTax.getCode());

		bean.setCompulsoryInputBillNo(mainView.chkBillNoInputFlg.isSelected());
		bean.setCheckBillMake(mainView.chkBillCreateFlg.isSelected());

		// 請求番号 採番管理
		bean.setInvNumbering(mainView.chkInvoiceNo.isSelected());
		bean.setInvoiceNoAdopt1((InvoiceNoAdopt) mainView.cboInvoiceNo1.getSelectedItemValue());
		bean.setInvoiceNoAdopt2((InvoiceNoAdopt) mainView.cboInvoiceNo2.getSelectedItemValue());
		bean.setInvoiceNoAdopt3((InvoiceNoAdopt) mainView.cboInvoiceNo3.getSelectedItemValue());
		bean.setInvNo1Name(mainView.ctrlInvoiceNo1Name.getText());
		bean.setInvNo2Name(mainView.ctrlInvoiceNo2Name.getText());
		bean.setInvNo3Name(mainView.ctrlInvoiceNo3Name.getText());
		bean.setInvNoDigit(mainView.ctrlInvoiceNoDigit.getIntValue());

		return bean;
	}

	/**
	 * @return モデル
	 */
	protected Class getModelClass() {
		return ReceivePolicyManager.class;
	}

	/**
	 * 編集画面入力値の妥当性をチェック *
	 * 
	 * @return bool - true:run, false:stop
	 */
	protected boolean isInputCheck() {

		// レコード長が未入力の場合エラー
		if (Util.isNullOrEmpty(mainView.ctrlRecordLength.getValue())) {
			showMessage(mainView, "I00037", "C10020");
			mainView.ctrlRecordLength.requestTextFocus();
			return false;
		}

		// 許容差額範囲 開始金額が未入力の場合エラー
		if (Util.isNullOrEmpty(mainView.ctrlDifferenceToleranceStr.getValue())) {
			showMessage(mainView, "I00037", "C10156");
			mainView.ctrlDifferenceToleranceStr.requestFocus();
			return false;
		}

		// 許容差額範囲 終了金額が未入力の場合エラー
		if (Util.isNullOrEmpty(mainView.ctrlDifferenceToleranceEnd.getValue())) {
			showMessage(mainView, "I00037", "C10197");
			mainView.ctrlDifferenceToleranceEnd.requestFocus();
			return false;
		}

		// 開始金額 > 終了金額の場合エラー
		if (mainView.ctrlDifferenceToleranceStr.getInt() > mainView.ctrlDifferenceToleranceEnd.getInt()) {
			showMessage(mainView, "W00066");
			mainView.ctrlDifferenceToleranceStr.requestFocus();
			return false;
		}

		// 科目コードが未入力の場合エラー
		if (Util.isNullOrEmpty(mainView.ctrlItemGroup.ctrlItemReference.getCode())) {
			showMessage(mainView, "I00037", "C00077");
			mainView.ctrlItemGroup.ctrlItemReference.requestTextFocus();
			return false;
		}

		// 補助科目コードが未入力の場合エラー
		if (mainView.ctrlItemGroup.ctrlSubItemReference.code.isEditable()) {
			if (Util.isNullOrEmpty(mainView.ctrlItemGroup.ctrlSubItemReference.getCode())) {
				showMessage(mainView, "I00037", "C00488");
				mainView.ctrlItemGroup.ctrlSubItemReference.requestTextFocus();
				return false;
			}
		}

		// 内訳科目コードが未入力の場合エラー
		if (mainView.ctrlItemGroup.ctrlDetailItemReference.code.isEditable()) {
			if (Util.isNullOrEmpty(mainView.ctrlItemGroup.ctrlDetailItemReference.getCode())) {
				showMessage(mainView, "I00037", "C00025");
				mainView.ctrlItemGroup.ctrlDetailItemReference.requestTextFocus();
				return false;
			}
		}

		// 計上部門コードが未入力の場合エラー
		if (Util.isNullOrEmpty(mainView.ctrlDepartment.getCode())) {
			showMessage(mainView, "I00037", "C00571");
			mainView.ctrlDepartment.requestTextFocus();
			return false;
		}

		// 消費税コードが未入力の場合エラー
		if (Util.isNullOrEmpty(mainView.ctrlConsumptionTax.getCode())) {
			showMessage(mainView, "I00037", "C00573");
			mainView.ctrlConsumptionTax.requestTextFocus();
			return false;
		}

		// 自動採番を使用する場合のみチェック
		if (isUseARInvAutoNumbering && mainView.chkInvoiceNo.isSelected()) {
			// 自動設定項目1
			InvoiceNoAdopt adopt = (InvoiceNoAdopt) mainView.cboInvoiceNo1.getSelectedItemValue();
			if (InvoiceNoAdopt.FIXED_CHAR == adopt && //
				Util.isNullOrEmpty(mainView.ctrlInvoiceNo1Name.getValue())) {
				showMessage(mainView, "I00037", "C00713");
				mainView.ctrlInvoiceNo1Name.requestFocus();
				return false;
			}
			// 自動設定項目2
			adopt = (InvoiceNoAdopt) mainView.cboInvoiceNo2.getSelectedItemValue();
			if (InvoiceNoAdopt.FIXED_CHAR == adopt && //
				Util.isNullOrEmpty(mainView.ctrlInvoiceNo2Name.getValue())) {
				showMessage(mainView, "I00037", "C00714");
				mainView.ctrlInvoiceNo2Name.requestFocus();
				return false;
			}
			// 自動設定項目3
			adopt = (InvoiceNoAdopt) mainView.cboInvoiceNo3.getSelectedItemValue();
			if (InvoiceNoAdopt.FIXED_CHAR == adopt && //
				Util.isNullOrEmpty(mainView.ctrlInvoiceNo3Name.getValue())) {
				showMessage(mainView, "I00037", "C00715");
				mainView.ctrlInvoiceNo3Name.requestFocus();
				return false;
			}
			// 自動採番部桁数
			if (Util.isNullOrEmpty(mainView.ctrlInvoiceNoDigit.getValue())) {
				showMessage(mainView, "I00037", "C00224");
				mainView.ctrlInvoiceNoDigit.requestFocus();
				return false;
			}

		}

		return true;
	}
}