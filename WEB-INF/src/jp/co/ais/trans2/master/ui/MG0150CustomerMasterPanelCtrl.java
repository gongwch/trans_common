package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.ui.MG0150CustomerUsrMasterDialog.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.program.*;

/**
 * 取引先マスタ コントローラー
 */
public class MG0150CustomerMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0150CustomerMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0150CustomerMasterDialog editView = null;

	/** 担当者設定画面 */
	protected MG0150CustomerUsrMasterDialog usrView = null;

	/** 国コード必須チェックするかどうか true：する */
	protected boolean isUseCountryCheck = ClientConfig.isFlagOn("trans.MG0150.use.country.required.check");

	/** true:グループ会社区分有効 */
	public static boolean isUseTRI_TYPE_GRP_FLG = ClientConfig.isFlagOn("trans.kt.MG0150.group.comp.flag");

	/** 取引先区分を表示しないかどうか true:表示しない */
	protected static boolean isNoVisibleTriDivison = ClientConfig.isFlagOn("trans.MG0150.no.visible.tri.division");

	/** 取引先情報をチェックするかどうか true:チェックする */
	protected static boolean isRequiredCustomerAccountNo = ClientConfig
		.isFlagOn("trans.MG0150.required.customer.account.no");

	/** 取引先担当者設定を表示するかどうか true:表示する */
	protected static boolean isVisibleTriUsrSetting = ClientConfig.isFlagOn("trans.MG0150.visible.tri.usr.setting");

	/** 取引先の敬称/担当部署/担当者などの設定を表示するかどうか true:表示する */
	protected static boolean isUseCustomerManagementSet = ClientConfig
		.isFlagOn("trans.usr.customer.managementi.setting");
	
	/** 取引先がが個人か否かを表示するかどうか true:表示する */
	protected static boolean isUseCustomerIsPerson = ClientConfig.isFlagOn("trans.kt.MG0150.tri.person.flag");

	/** 非適格請求書発行事業者の設定を表示するかどうか true:表示する */
	protected static boolean isInvoiceTaxProperty = ClientConfig.isFlagOn("trans.slip.use.invoice.tax");

	/** true: 2023INVOICE制度対応を使用する(会社情報含む) */
	public static boolean isInvoice = false;

	/**
	 * 操作モード。現在操作中のモードを把握するために使用する。
	 */
	protected Mode mode = null;

	/**
	 * 操作モード。
	 * 
	 * @author AIS
	 */
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

			// インボイス使用するかどうか
			if (isInvoiceTaxProperty) {
				initInvoiceFlg();
			}

			// 指示画面生成
			createMainView();

			// 指示画面を初期化する
			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MG0150CustomerMasterPanel();
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

		// [担当者設定]ボタン押下
		mainView.btnUsr.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnUsr_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [担当者一覧出力]ボタン押下
		mainView.btnUsrOut.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnUsrOut_Click();
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
	 * invoice使用するかどうか
	 */
	protected void initInvoiceFlg() {

		isInvoice = getCompany().isCMP_INV_CHK_FLG();
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

			// 検索条件
			CustomerSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				// 警告メッセージ表示
				showMessage(mainView, "I00068");
				mainView.TCustomerReferenceRange.ctrlCustomerReferenceFrom.code.requestFocus();
				return;
			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 取引先情報を取得
			List<Customer> list = getCustomer(condition);

			// 検索条件に該当する取引先が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 取引先情報を一覧に表示する
			for (Customer customer : list) {
				mainView.tbl.addRow(getRowData(customer));
			}

			// メインボタンを押下可能にし、1行目を選択する
			setMainButtonEnabled(true);
			mainView.tbl.setRowSelectionInterval(0, 0);

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

			// 編集対象の取引先を取得する
			Customer customer = getSelectedCustomer();

			// 編集画面を生成し、編集対象の取引先情報をセットする
			createEditView();
			initEditView(Mode.MODIFY, customer);

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

			// 複写対象の取引先を取得する
			Customer customer = getSelectedCustomer();

			// 複写画面を生成し、複写対象の取引先情報をセットする
			createEditView();
			initEditView(Mode.COPY, customer);

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

			// 削除対象の取引先を取得する
			Customer customer = getSelectedCustomer();

			// 削除する
			deleteCustomer(customer);

			// 削除した取引先を一覧から削除
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
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			CustomerSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02326") + ".xls");// 取引先マスタ

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [担当者設定]ボタン押下
	 */
	protected void btnUsr_Click() {

		try {

			// 担当者設定の取引先を取得する
			Customer customer = getSelectedCustomer();

			// 取引先担当者設定情報を取得
			List<CustomerUser> list = getTRI_USR_MST(customer.getCode());

			// 担当者設定画面を生成し、編集対象の取引先情報をセットする
			createUsrView();
			initUsrView(customer);

			// 取引先担当者設定を未登録の場合
			if (list.isEmpty()) {
				SystemClassificationSearchCondition condition = new SystemClassificationSearchCondition();
				condition.setCompanyCode(getCompanyCode());
				List<SystemClassification> listSYS = getSYS_KBN(condition);
				// 取引先担当者設定情報を一覧に表示する
				for (SystemClassification sys : listSYS) {
					usrView.tbl.addRow(getRowDataSYS(sys));
				}
			} else {
				// 取引先担当者設定情報を一覧に表示する
				for (CustomerUser customerUsr : list) {
					usrView.tbl.addRow(getRowDataUsr(customerUsr));
				}
			}

			// 担当者設定画面を表示する
			usrView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [担当者一覧出力]ボタン押下
	 */
	protected void btnUsrOut_Click() {

		try {

			// 確認メッセージ
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			CustomerSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getUsrExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C00363") + getWord("C00010") + ".xls");// 担当者一覧

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0150CustomerMasterDialog(mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent();

	}

	/**
	 * 担当者設定画面のファクトリ。新規に担当者設定画面を生成し、イベントを定義する。
	 */
	protected void createUsrView() {

		// 担当者設定画面を生成
		usrView = new MG0150CustomerUsrMasterDialog(mainView.getParentFrame(), true);

		// 担当者設定画面のイベント定義
		addUsrViewEvent();

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

		// [非得意先]選択
		editView.rdoNotTokui.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				changedCustomer(e.getStateChange() != ItemEvent.SELECTED);
			}
		});

		// [非適格請求書発行事業者]選択
		editView.chkNoInvReg.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				chkNoInvReg(editView.chkNoInvReg.isSelected());
			}
		});

		// 仕入先区分非仕入先選択
		editView.rdoNotSiire.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (editView.rdoNotSiire.isSelected() && isInvoice) {
					btnSiire_Click(false);
				}
			}

		});

		// 仕入先区分仕入先選択
		editView.rdoSiire.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (isInvoice) {
					btnSiire_Click(true);
				}
			}
		});
	}

	/**
	 * 担当者設定画面のイベント定義。
	 */
	protected void addUsrViewEvent() {

		// [確定]ボタン押下
		usrView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				usrView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettleUsr_Click();
				usrView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [戻る]ボタン押下
		usrView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				usrView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCloseUsr_Click();
				usrView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * 非得意先/得意先切替
	 * 
	 * @param isCustomer
	 */
	protected void changedCustomer(boolean isCustomer) {
		editView.ctrlBankAccount.setEditable(isCustomer);
		editView.ctrlClientName.setEditable(isCustomer);
		editView.ctrlCustomerClosedDaySetting.setEditable(isCustomer);
		editView.rdoTosha.setEnabled(isCustomer);
		editView.rdoSenpo.setEnabled(isCustomer);

		if (!isCustomer) {
			editView.ctrlBankAccount.clear();
			editView.ctrlClientName.clear();
			editView.ctrlCustomerClosedDaySetting.clear();
		}

	}

	/**
	 * 非適格請求書発行事業者チェックボックス押下
	 * 
	 * @param flg
	 */
	protected void chkNoInvReg(boolean flg) {
		editView.ctrlInvRegNo.clear();
		editView.ctrlInvRegNo.setEditable(!flg);
		editView.ctrlTaxReference.clear();
		editView.ctrlTaxReference.setEditable(flg);

	}

	/**
	 * 編集画面[仕入れ区分]ボタン押下 インボイス消費税制御
	 * 
	 * @param flg 非仕入:false
	 */
	protected void btnSiire_Click(boolean flg) {
		editView.chkNoInvReg.setEnabled(flg);
		if (!flg) {
			editView.chkNoInvReg.setSelected(flg);
			editView.ctrlInvRegNo.clear();
			editView.ctrlInvRegNo.setEditable(flg);
			editView.ctrlTaxReference.clear();
			editView.ctrlTaxReference.setEditable(flg);
		} else {
			chkNoInvReg(false);
		}
	}

	/**
	 * 担当者設定画面を初期化する
	 * 
	 * @param customer 。修正、複写の場合は当該情報を編集画面にセットする。
	 */
	protected void initUsrView(Customer customer) {
		usrView.setTitle(getWord("C00363") + getWord("C00319") + getWord("C00075"));
		usrView.ctrlTriCode.setValue(customer.getCode());
		usrView.ctrlTriName.setValue(customer.getName());

	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param mode_ 操作モード。
	 * @param customer 。修正、複写の場合は当該情報を編集画面にセットする。
	 */
	protected void initEditView(Mode mode_, Customer customer) {

		this.mode = mode_;

		// 消費税リファレンス：インボイス非適格請求書発行事業者フラグ設定
		editView.ctrlTaxReference.getSearchCondition().setNoInvRegFlg(true);
		// 仕入のみ表示
		editView.ctrlTaxReference.getSearchCondition().setPurcharseOnliy(true);

		// 新規の場合
		if (Mode.NEW == mode_) {
			editView.setTitle(getWord("C01698"));
			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());
			editView.ctrlTaxReference.setEditable(false);

			if (!isUseTRI_TYPE_GRP_FLG) {
				editView.chkGrp.setVisible(false);
			}

			// プロパティにより非表示設定
			if (isNoVisibleTriDivison) {

				editView.pnlTri.setVisible(false);

				editView.chkChtr.setVisible(false);
				editView.chkOnr.setVisible(false);
				editView.chkAgnt.setVisible(false);
				editView.chkSplr.setVisible(false);
				editView.chkBrkr.setVisible(false);
				editView.chkBank.setVisible(false);
				editView.chkOzr.setVisible(false);

				editView.chkShpp.setVisible(false);
				editView.chkCons.setVisible(false);
				editView.chkNtpt.setVisible(false);
				editView.chkFwd.setVisible(false);
				editView.chkBktr.setVisible(false);
				editView.chkBksp.setVisible(false);
			}

			if (!isUseCustomerManagementSet) {
				editView.pnlCusTitle.setVisible(false);
				editView.cboCusTitle.setVisible(false);
				editView.cboChargeTitle.setVisible(false);
				editView.ctrlDepInCharge.setVisible(false);
				editView.ctrlChargeName.setVisible(false);
			}
			if (!isUseCustomerIsPerson) {
				editView.chkHideBankNo.setVisible(false);
			}
			if (isInvoice) {
				btnSiire_Click(false);
			} else {
				editView.chkNoInvReg.setVisible(false);
				editView.ctrlTaxReference.setVisible(false);
				editView.ctrlInvRegNo.setVisible(false);
			}

			return;
		}

		// 編集
		if (Mode.MODIFY == mode_) {
			editView.setTitle(getWord("C00977"));
			editView.ctrlTriCode.setEditable(false);
			editView.setEditMode();
		}
		// 複写
		else {
			editView.setTitle(getWord("C01738"));
		}

		editView.ctrlTriCode.setValue(customer.getCode());
		editView.ctrlTriName.setValue(customer.getName());
		editView.ctrlTriNames.setValue(customer.getNames());
		editView.ctrlTriNamek.setValue(customer.getNamek());
		editView.ctrlPostNum.setValue(customer.getZipCode());
		editView.ctrlCountry.setEntity(customer.getCountry());
		editView.ctrlOfficeName.setValue(customer.getOfficeName());
		editView.ctrlTelNum.setValue(customer.getTel());
		editView.ctrlFaxNum.setValue(customer.getFax());
		editView.ctrlAddressNum1.setValue(customer.getAddress());
		editView.ctrlAddressNum2.setValue(customer.getAddress2());
		editView.ctrlAddressNumKana.setValue(customer.getAddressKana());
		editView.ctrlEMailAddress.setValue(customer.getEmailAddress());

		if (CustomerType.CUSTOMER == customer.getCustomerType() || CustomerType.BOTH == customer.getCustomerType()) {
			editView.ctrlBankAccount.setEditable(true);
			editView.ctrlClientName.setEditable(true);
			editView.ctrlCustomerClosedDaySetting.setEditable(true);
			editView.rdoTosha.setEnabled(true);
			editView.rdoSenpo.setEnabled(true);

			editView.ctrlBankAccount.setEntity(customer.getBankAccount());
			editView.ctrlClientName.setValue(customer.getClientName());
			editView.ctrlCustomerClosedDaySetting.setValue(customer);
		}

		if (CashInFeeType.Other == customer.getCashInFeeType()) {
			editView.rdoSenpo.setSelected(true);
		} else {
			editView.rdoTosha.setSelected(true);
		}

		boolean isSiire = false;
		boolean isTokui = false;

		switch (customer.getCustomerType()) {
			case BOTH:
				isSiire = true;
				isTokui = true;
				break;

			case VENDOR:
				isSiire = true;
				break;

			case CUSTOMER:
				isTokui = true;
				break;

			case NONE:
			default:
				break;
		}

		editView.rdoSiire.setSelected(isSiire);
		editView.rdoTokui.setSelected(isTokui);

		editView.TCustomerReference.setCode(customer.getSumCode());
		editView.TCustomerReference.setNames(customer.getSumName());

		editView.dtBeginDate.setValue(customer.getDateFrom());
		editView.dtEndDate.setValue(customer.getDateTo());

		// 非適格請求書発行事業者 onかoffか
		chkNoInvReg(editView.chkNoInvReg.isSelected());

		// プロパティにより非表示設定
		if (isNoVisibleTriDivison) {

			editView.pnlTri.setVisible(false);

			editView.chkChtr.setVisible(false);
			editView.chkOnr.setVisible(false);
			editView.chkAgnt.setVisible(false);
			editView.chkSplr.setVisible(false);
			editView.chkBrkr.setVisible(false);
			editView.chkBank.setVisible(false);
			editView.chkOzr.setVisible(false);

			editView.chkShpp.setVisible(false);
			editView.chkCons.setVisible(false);
			editView.chkNtpt.setVisible(false);
			editView.chkFwd.setVisible(false);
			editView.chkBktr.setVisible(false);
			editView.chkBksp.setVisible(false);
		} else {
			editView.chkChtr.setSelected(customer.isCharterer());
			editView.chkOnr.setSelected(customer.isOwner());
			editView.chkAgnt.setSelected(customer.isPortAgent());
			editView.chkSplr.setSelected(customer.isSupplier());
			editView.chkBrkr.setSelected(customer.isBroker());
			editView.chkBank.setSelected(customer.isBank());
			editView.chkOzr.setSelected(customer.isOther());

			editView.chkShpp.setSelected(customer.isShipper());
			editView.chkCons.setSelected(customer.isConsignee());
			editView.chkNtpt.setSelected(customer.isNotifyParty());
			editView.chkFwd.setSelected(customer.isFowarder());
			editView.chkBktr.setSelected(customer.isBunkerTrader());
			editView.chkBksp.setSelected(customer.isBunkerSupplier());
		}
		editView.chkHideBankNo.setSelected(customer.isPersonalCustomer());

		if (isUseTRI_TYPE_GRP_FLG) {
			editView.chkGrp.setSelected(customer.isGroupCompany());
		} else {
			editView.chkGrp.setVisible(false);
		}

		if (isUseCustomerManagementSet) {
			editView.cboCusTitle.setSelectedItemValue(customer.getTRI_TITLE_TYPE());
			editView.cboChargeTitle.setSelectedItemValue(customer.getEMP_TITLE_TYPE());
			editView.ctrlDepInCharge.setValue(customer.getCHARGE_DEP_NAME());
			editView.ctrlChargeName.setValue(customer.getCHARGE_EMP_NAME());
		} else {
			editView.pnlCusTitle.setVisible(false);
			editView.cboCusTitle.setVisible(false);
			editView.cboChargeTitle.setVisible(false);
			editView.ctrlDepInCharge.setVisible(false);
			editView.ctrlChargeName.setVisible(false);
		}

		if (isInvoice) {
			if (!customer.isPurchase()) {
				btnSiire_Click(false);
			} else {
				editView.chkNoInvReg.setSelected(customer.isNO_INV_REG_FLG());
				editView.ctrlInvRegNo.setValue(customer.getINV_REG_NO());
				editView.ctrlTaxReference.setCode(Util.avoidNull(customer.getNO_INV_REG_ZEI_CODE()));
				editView.ctrlTaxReference.refleshAndShowEntity();
			}
		} else {
			editView.chkNoInvReg.setVisible(false);
			editView.ctrlInvRegNo.setVisible(false);
			editView.ctrlTaxReference.setVisible(false);
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

			// 入力された取引先情報を取得
			Customer customer = getInputedCustomer();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entry", customer);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(customer));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", customer);

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(customer));

			}

			// 編集画面を閉じる
			btnClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 担当者設定画面[確定]ボタン押下
	 */
	protected void btnSettleUsr_Click() {

		try {

			// 入力された取引先担当者設定情報を取得
			List<CustomerUser> list = getInputedCustomerUsr();

			// 新規登録
			request(getModelClass(), "entryTRI_USR_MST", list);

			// メインボタンを押下可能にする
			setMainButtonEnabled(true);

			// 担当者設定画面を閉じる
			btnCloseUsr_Click();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 編集画面[戻る]ボタン押下
	 */
	protected void btnClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * 担当者設定画面[戻る]ボタン押下
	 */
	protected void btnCloseUsr_Click() {
		usrView.setVisible(false);
	}

	/**
	 * 指示画面で入力された取引先の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected CustomerSearchCondition getSearchCondition() {

		CustomerSearchCondition condition = new CustomerSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCodeFrom(mainView.TCustomerReferenceRange.getCodeFrom());
		condition.setCodeTo(mainView.TCustomerReferenceRange.getCodeTo());
		condition.setInvoiceFlg(isInvoice);
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * Servletクラスを返す
	 * 
	 * @return CustomerManager
	 */
	protected Class getModelClass() {
		return CustomerManager.class;
	}

	/**
	 * Servletクラスを返す
	 * 
	 * @return CustomerManager
	 */
	protected Class getModelClassSYS() {
		return ProgramManager.class;
	}

	/**
	 * 編集画面で入力された情報を返す
	 * 
	 * @return 編集画面で入力された取引先
	 */
	protected Customer getInputedCustomer() {

		Customer customer = createCustomer();
		customer.setCompanyCode(getCompany().getCode());
		customer.setCode(editView.ctrlTriCode.getValue());
		customer.setName(editView.ctrlTriName.getValue());
		customer.setNames(editView.ctrlTriNames.getValue());
		customer.setNamek(editView.ctrlTriNamek.getValue());
		customer.setOfficeName(editView.ctrlOfficeName.getValue());
		customer.setZipCode(editView.ctrlPostNum.getValue());
		customer.setTel(editView.ctrlTelNum.getValue());
		customer.setFax(editView.ctrlFaxNum.getValue());
		customer.setCountry(editView.ctrlCountry.getEntity()); // 国エンティティ
		customer.setCountryCode(editView.ctrlCountry.getCode()); // 国コード
		customer.setAddress(editView.ctrlAddressNum1.getValue());
		customer.setAddress2(editView.ctrlAddressNum2.getValue());
		customer.setAddressKana(editView.ctrlAddressNumKana.getValue());
		customer.setEmailAddress(editView.ctrlEMailAddress.getValue());
		customer.setBankAccount(editView.ctrlBankAccount.getEntity());
		customer.setBankAccountCode(editView.ctrlBankAccount.getCode());
		customer.setClientName(editView.ctrlClientName.getValue());

		if (editView.rdoTokui.isSelected()) {
			customer.setCloseDay(editView.ctrlCustomerClosedDaySetting.getCloseDay());
			customer.setNextMonth(editView.ctrlCustomerClosedDaySetting.getNextMonth());
			customer.setCashDay(editView.ctrlCustomerClosedDaySetting.getCashDay());
		}

		if (editView.rdoTosha.isSelected()) {
			customer.setCashInFeeType(CashInFeeType.Our);
		} else {
			customer.setCashInFeeType(CashInFeeType.Other);
		}

		customer.setPurchase(editView.rdoSiire.isSelected());
		customer.setClient(editView.rdoTokui.isSelected());
		customer.setSumCode(editView.TCustomerReference.getCode());
		customer.setSumName(editView.TCustomerReference.getNames());

		if (editView.rdoSiire.isSelected() && editView.rdoTokui.isSelected()) {
			customer.setCustomerType(CustomerType.BOTH);
		} else if (!editView.rdoSiire.isSelected() && editView.rdoTokui.isSelected()) {
			customer.setCustomerType(CustomerType.CUSTOMER);
		} else if (editView.rdoSiire.isSelected() && !editView.rdoTokui.isSelected()) {
			customer.setCustomerType(CustomerType.VENDOR);
		} else {
			customer.setCustomerType(CustomerType.NONE);
		}

		customer.setDateFrom(editView.dtBeginDate.getValue());
		customer.setDateTo(editView.dtEndDate.getValue());

		customer.setCharterer(editView.chkChtr.isSelected());
		customer.setOwner(editView.chkOnr.isSelected());
		customer.setPortAgent(editView.chkAgnt.isSelected());
		customer.setSupplier(editView.chkSplr.isSelected());
		customer.setBroker(editView.chkBrkr.isSelected());
		customer.setBank(editView.chkBank.isSelected());
		customer.setOther(editView.chkOzr.isSelected());

		customer.setShipper(editView.chkShpp.isSelected());
		customer.setConsignee(editView.chkCons.isSelected());
		customer.setNotifyParty(editView.chkNtpt.isSelected());
		customer.setFowarder(editView.chkFwd.isSelected());
		customer.setBunkerTrader(editView.chkBktr.isSelected());
		customer.setBunkerSupplier(editView.chkBksp.isSelected());
		customer.setPersonalCustomer(editView.chkHideBankNo.isSelected());

		if (isUseTRI_TYPE_GRP_FLG) {
			customer.setGroupCompany(editView.chkGrp.isSelected());
		}
		if (isUseCustomerManagementSet) {
			customer.setTRI_TITLE_TYPE((HonorType) editView.cboCusTitle.getSelectedItemValue());
			customer.setEMP_TITLE_TYPE((HonorType) editView.cboChargeTitle.getSelectedItemValue());
			customer.setCHARGE_DEP_NAME(editView.ctrlDepInCharge.getValue());
			customer.setCHARGE_EMP_NAME(editView.ctrlChargeName.getValue());
		}

		if (isInvoice) {
			customer.setNO_INV_REG_FLG(editView.chkNoInvReg.isSelected());
			customer.setINV_REG_NO(editView.ctrlInvRegNo.getValue());
			customer.setNO_INV_REG_ZEI_CODE(Util.isNullOrEmpty(editView.ctrlTaxReference.getCode()) ? null
				: editView.ctrlTaxReference.getCode());
			customer.setNO_INV_REG_ZEI_NAME(Util.isNullOrEmpty(editView.ctrlTaxReference.getNames()) ? null
				: editView.ctrlTaxReference.getNames());
		}

		return customer;

	}

	/**
	 * 担当者設定画面で入力された情報を返す
	 * 
	 * @return 担当者設定画面で入力された取引先
	 */
	protected List<CustomerUser> getInputedCustomerUsr() {

		List<CustomerUser> list = new ArrayList<CustomerUser>();
		for (int row = 0; row < usrView.tbl.getRowCount(); row++) {
			CustomerUser bean = new CustomerUser();
			bean.setKAI_CODE(getCompanyCode());
			bean.setTRI_CODE(usrView.ctrlTriCode.getValue());
			bean.setSYS_KBN(Util.avoidNull(usrView.tbl.getRowValueAt(row, TriColum.SYS_KBN)));
			bean.setUSR_NAME(Util.avoidNull(usrView.tbl.getRowValueAt(row, TriColum.USR_NAME)));
			bean.setDEP_NAME(Util.avoidNull(usrView.tbl.getRowValueAt(row, TriColum.DEP_NAME)));
			bean.setPOSITION(Util.avoidNull(usrView.tbl.getRowValueAt(row, TriColum.POSITION)));
			list.add(bean);
		}

		return list;

	}

	/**
	 * 取引先ファクトリ
	 * 
	 * @return Customer
	 */
	protected Customer createCustomer() {
		return new Customer();
	}

	/**
	 * 取引先情報を一覧に表示する形式に変換し返す
	 * 
	 * @param customer 取引先
	 * @return 一覧に表示する形式に変換された取引先
	 */
	protected Object[] getRowData(Customer customer) {

		return new Object[] {
				customer.getCode(), //
				customer.getName(), //
				customer.getNames(), //
				customer.getNamek(), //
				getTriType(customer),
				getWord(customer.isGroupCompany() ? "C10583" : ""), // グループ会社区分
				customer.getOfficeName(), //
				customer.getZipCode(), //
				customer.getCountryCode(), (customer.getCountry() == null) ? null : customer.getCountry().getName(),
				customer.getAddressKana(), //
				customer.getAddress(), //
				customer.getAddress2(), //
				customer.getEmailAddress(), //
				getWord(HonorType.getName(customer.getTRI_TITLE_TYPE())), //
				getWord(HonorType.getName(customer.getEMP_TITLE_TYPE())), //
				customer.getCHARGE_DEP_NAME(), //
				customer.getCHARGE_EMP_NAME(), //
				customer.getTel(), //
				customer.getFax(), //
				customer.getSumCode(), //
				customer.getSumName(), //
				getWord(CustomerType.getVenderName(customer.getCustomerType())), //
				getWord(CustomerType.getCustomerName(customer.getCustomerType())), //
				customer.getCloseDay(), //
				customer.getNextMonth(), //
				customer.getCashDay(), //
				customer.getBankAccountCode(), //
				(customer.getBankAccount() == null) ? null : customer.getBankAccount().getName(), //
				customer.getClientName(), //
				getWord(CashInFeeType.getCashInFeeTypeName(customer.getCashInFeeType())), //
				customer.isNO_INV_REG_FLG() ? getWord("C12198") : "", //
				customer.getNO_INV_REG_ZEI_NAME(), //
				customer.getINV_REG_NO(), //
				DateUtil.toYMDString(customer.getDateFrom()), //
				DateUtil.toYMDString(customer.getDateTo()) //
		};
	}

	/**
	 * 取引先担当者設定情報を一覧に表示する形式に変換し返す
	 * 
	 * @param sys システム情報
	 * @return 一覧に表示する形式に変換された取引先
	 */
	protected Object[] getRowDataSYS(SystemClassification sys) {

		return new Object[] { sys, sys.getCode(), null, null, null };
	}

	/**
	 * 取引先担当者設定情報を一覧に表示する形式に変換し返す
	 * 
	 * @param customerUsr システム情報
	 * @return 一覧に表示する形式に変換された取引先
	 */
	protected Object[] getRowDataUsr(CustomerUser customerUsr) {

		return new Object[] { customerUsr, customerUsr.getSYS_KBN(), customerUsr.getUSR_NAME(),
				customerUsr.getDEP_NAME(), customerUsr.getPOSITION() };
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
		mainView.btnUsr.setEnabled(bln);
	}

	/**
	 * 検索条件に該当する取引先のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する取引先のリスト
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Customer> getCustomer(CustomerSearchCondition condition) throws Exception {

		List<Customer> list = (List<Customer>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * 検索条件に該当する取引先のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する取引先のリスト
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<SystemClassification> getSYS_KBN(SystemClassificationSearchCondition condition) throws Exception {

		List<SystemClassification> list = (List<SystemClassification>) request(getModelClassSYS(), "getSystem",
			condition);

		return list;
	}

	/**
	 * 検索条件に該当する取引先のリストを返す
	 * 
	 * @param triCode 取引先コード
	 * @return 検索条件に該当する取引先のリスト
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<CustomerUser> getTRI_USR_MST(String triCode) throws Exception {

		List<CustomerUser> list = (List<CustomerUser>) request(getModelClass(), "getTRI_USR_MST", triCode);

		return list;
	}

	/**
	 * 一覧で選択されている取引先を返す
	 * 
	 * @return 一覧で選択されている取引先
	 * @throws Exception
	 */
	protected Customer getSelectedCustomer() throws Exception {

		CustomerSearchCondition condition = new CustomerSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0150CustomerMasterPanel.SC.triCode));

		List<Customer> list = getCustomer(condition);

		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました
			throw new TException("I00193");
		}
		return list.get(0);
	}

	/**
	 * 指定の取引先を削除する
	 * 
	 * @param customer
	 * @throws Exception
	 */
	protected void deleteCustomer(Customer customer) throws Exception {
		request(getModelClass(), "delete", customer);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// 取引先コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlTriCode.getValue())) {
			// 取引先コードを入力してください。
			showMessage(editView, "I00037", "C00786");
			editView.ctrlTriCode.requestTextFocus();
			return false;
		}

		// 取引先名称が未入力の場合エラー if
		if (Util.isNullOrEmpty(editView.ctrlTriName.getValue())) {
			// 取引先名称を入力してください。
			showMessage(editView, "I00037", "C00830");
			editView.ctrlTriName.requestTextFocus();
			return false;
		}

		// 取引先略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlTriNames.getValue())) {
			// 取引先略称を入力してください。
			showMessage(editView, "I00037", "C00787");
			editView.ctrlTriNames.requestTextFocus();
			return false;
		}

		// 取引先検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlTriNamek.getValue())) {
			// 取引先検索名称を入力してください。
			showMessage(editView, "I00037", "C00836");
			editView.ctrlTriNamek.requestTextFocus();
			return false;
		}

		// 国が未入力の場合エラー
		if (isUseCountryCheck && Util.isNullOrEmpty(editView.ctrlCountry.getCode())) {
			// 国を入力してください。
			showMessage(editView, "I00037", "C11415");
			editView.ctrlCountry.requestTextFocus();
			return false;

		}

		// EMail Addressがメールアドレス形式(@.*)以外の場合エラー
		if (!Util.isNullOrEmpty(editView.ctrlEMailAddress.getValue())) {
			String email = editView.ctrlEMailAddress.getValue();
			if (!checkMailAddressByRegularExpression(email)) {
				// 正しいメールアドレスを入力してください。
				showMessage(editView, "I00789");
				editView.ctrlEMailAddress.requestTextFocus();
				return false;
			}

		}

		// 得意先の場合
		if (isRequiredCustomerAccountNo && editView.rdoTokui.isSelected()) {

			// 銀行口座が未入力の場合エラー
			if (Util.isNullOrEmpty(editView.ctrlBankAccount.getCode())) {
				// 入金銀行口座を入力してください。
				showMessage(editView, "I00037", "C01268");
				editView.ctrlBankAccount.requestTextFocus();
				return false;

			}

			// 入金条件(締め)が未入力の場合エラー
			if (editView.rdoTokui.isSelected()
				&& Util.isNullOrEmpty(editView.ctrlCustomerClosedDaySetting.closeDay.getValue())) {
				// 入金条件(日締め)を入力してください。
				showMessage(editView, "I00037", "C11092");
				editView.ctrlCustomerClosedDaySetting.closeDay.requestFocus();
				return false;
			}

			// 入金条件(ヵ月後)が未入力の場合エラー
			if (editView.rdoTokui.isSelected()
				&& Util.isNullOrEmpty(editView.ctrlCustomerClosedDaySetting.nextMonth.getValue())) {
				// 入金条件(ヶ月後)を入力してください。
				showMessage(editView, "I00037", "C11090");
				editView.ctrlCustomerClosedDaySetting.nextMonth.requestFocus();
				return false;
			}

			// 入金条件(締め日)が未入力の場合エラー
			if (editView.rdoTokui.isSelected()
				&& Util.isNullOrEmpty(editView.ctrlCustomerClosedDaySetting.cashDay.getValue())) {
				// 入金条件(日払い)を入力してください。
				showMessage(editView, "I00037", "C11091");
				editView.ctrlCustomerClosedDaySetting.cashDay.requestFocus();
				return false;
			}

			int close = editView.ctrlCustomerClosedDaySetting.getCloseDay();
			if (close < 1 || (31 < close && close != 99)) {
				// 締日には、1～31、または、99(月末)を入力してください。
				showMessage(editView, "I00209", "C03552");
				editView.ctrlCustomerClosedDaySetting.closeDay.requestFocus();
				return false;
			}

			int cash = editView.ctrlCustomerClosedDaySetting.getCashDay();

			if (cash < 1 || (31 < cash && cash != 99)) {
				// 支払日には、1～31、または、99(月末)を入力してください。
				showMessage(editView, "I00209", "C03552");
				editView.ctrlCustomerClosedDaySetting.cashDay.requestFocus();
				return false;
			}

		}

		// 開始年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			// 開始年月日を入力してください
			showMessage(editView, "I00037", "C00055");
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// 終了年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			// 終了年月日を入力してください
			showMessage(editView, "I00037", "C00261");
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		// 開始年月日,終了年月日チェック
		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}

		// 新規、複写の場合は同一取引先が既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			CustomerSearchCondition condition = new CustomerSearchCondition();
			condition.setCode(editView.ctrlTriCode.getValue());
			condition.setCompanyCode(getCompanyCode());
			List<Customer> list = getCustomer(condition);
			if (list != null && !list.isEmpty()) {
				// 指定の取引先は既に存在します。
				showMessage(editView, "I00148", "C00408");
				editView.ctrlTriCode.requestTextFocus();
				return false;
			}
		}

		return true;
	}

	/**
	 * 選択されている取引先区分を連結
	 * 
	 * @param customer
	 * @return 選択された取引先区分
	 */
	protected String getTriType(Customer customer) {

		// 取引先区分
		StringBuilder sb = new StringBuilder();
		if (customer.isCharterer()) {
			sb.append(getWord("CTC0058"));
		}
		if (customer.isOwner()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0059"));
		}
		if (customer.isPortAgent()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0115"));
		}
		if (customer.isSupplier()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0116"));
		}
		if (customer.isBroker()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0078"));
		}
		// BANK
		if (customer.isBank()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("COP532"));
		}
		if (customer.isOther()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0117"));
		}
		if (customer.isShipper()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL206"));
		}
		if (customer.isConsignee()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL209"));
		}
		if (customer.isNotifyParty()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL320"));
		}
		if (customer.isFowarder()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL833"));
		}
		if (customer.isBunkerTrader()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL834"));
		}
		if (customer.isBunkerSupplier()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL835"));
		}

		return sb.toString();
	}

	/**
	 * メールアドレスチェック
	 * 
	 * @param address チェック対象のアドレス
	 * @return 正しいメールアドレスはtrue *@*.*形式以外はfalse
	 */
	public static boolean checkMailAddressByRegularExpression(String address) {

		String aText = "[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^\\$\\-\\|]";
		String dotAtom = aText + "+" + "(\\." + aText + "+)*";
		String regularExpression = "^" + dotAtom + "@" + dotAtom + "$";
		Pattern pattern = Pattern.compile(regularExpression);
		Matcher matcher = pattern.matcher(address);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

}
