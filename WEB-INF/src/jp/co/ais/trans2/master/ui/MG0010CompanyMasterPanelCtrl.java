package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.close.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 会社コントロールマスタのコントローラ
 * 
 * @author AIS
 */
public class MG0010CompanyMasterPanelCtrl extends TController {

	/** SPCタブ表示可否 */
	protected static boolean isNotShowSpc = ClientConfig.isFlagOn("trans.company.master.not.show.spc");

	/** INVOICE(会社基礎情報英語版)タブ表示可否 */
	protected static boolean isShowCompanyEng = ClientConfig.isFlagOn("trans.company.master.show.company.eng");

	/** AR：英分請求書SIGNER表示可否 */
	protected static boolean isShowARSignerEng = ClientConfig.isFlagOn("trans.company.master.show.ar.signer.eng");

	/** true: 2023INVOICE制度対応を使用する */
	public static boolean isInvoice = ClientConfig.isFlagOn("trans.slip.use.invoice.tax");

	/** 指示画面 */
	protected MG0010CompanyMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0010CompanyMasterDialog editView = null;

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
		mainView = new MG0010CompanyMasterPanel();
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

		// 再検索
		btnSearch_Click();

		// 会社コード開始にフォーカス
		mainView.ctrlCompanyRange.ctrlCompanyReferenceFrom.code.requestFocus();

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

			// 開始コード<=終了コードにしてください。
			if (Util.isSmallerThen(mainView.ctrlCompanyRange.getCodeFrom(), mainView.ctrlCompanyRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlCompanyRange.getFieldFrom().requestFocus();
				return;

			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 会社情報を取得
			CompanySearchCondition condition = getSearchCondition();
			List<Company> list = getCompany(condition);

			// 検索条件に該当する会社が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 会社情報を一覧に表示する
			for (Company company : list) {
				mainView.tbl.addRow(getRowData(company));
			}

			// メインボタンを押下可能にし、1行目を選択する
			setMainButtonEnabled(true);
			mainView.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面[編集]ボタン押下
	 */
	protected void btnModify_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// 編集対象の会社を取得する
			Company company = getSelectedCompany();

			// 編集画面を生成し、編集対象の会社情報をセットする
			createEditView();
			initEditView(Mode.MODIFY, company);

			// 編集画面を表示する
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面[複写]ボタン押下
	 */
	protected void btnCopy_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// 複写対象の会社を取得する
			Company company = getSelectedCompany();

			// 複写画面を生成し、複写対象の会社情報をセットする
			createEditView();
			initEditView(Mode.COPY, company);

			// 複写画面を表示する
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面[削除]ボタン押下
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

			// 削除対象の会社を取得する
			Company company = getSelectedCompany();

			// 削除する
			deleteCompany(company);

			// 削除した会社を一覧から削除
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
	 * 指示画面[エクセル]ボタン押下
	 */
	protected void btnExcel_Click() {

		try {

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// データ抽出
			CompanySearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C00910") + ".xls");// 会社コントロールマスタ

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0010CompanyMasterDialog(getCompany(), mainView.getParentFrame(), true);
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

		// 内訳管理チェック
		editView.chkDetailItem.addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.chkDetailItem.isSelected()) {
					editView.ctrlDetailItemName.setEditable(true);
				} else {
					editView.ctrlDetailItemName.setEditable(false);
					editView.ctrlDetailItemName.setText(null);
				}
			}
		});

		// 管理1チェック
		editView.chkManagement1.addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.chkManagement1.isSelected()) {
					editView.ctrlManagement1Name.setEditable(true);
				} else {
					editView.ctrlManagement1Name.setEditable(false);
					editView.ctrlManagement1Name.setText(null);
				}
			}
		});

		// 管理2チェック
		editView.chkManagement2.addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.chkManagement2.isSelected()) {
					editView.ctrlManagement2Name.setEditable(true);
				} else {
					editView.ctrlManagement2Name.setEditable(false);
					editView.ctrlManagement2Name.setText(null);
				}
			}
		});

		// 管理3チェック
		editView.chkManagement3.addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.chkManagement3.isSelected()) {
					editView.ctrlManagement3Name.setEditable(true);
				} else {
					editView.ctrlManagement3Name.setEditable(false);
					editView.ctrlManagement3Name.setText(null);
				}
			}
		});

		// 管理4チェック
		editView.chkManagement4.addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.chkManagement4.isSelected()) {
					editView.ctrlManagement4Name.setEditable(true);
				} else {
					editView.ctrlManagement4Name.setEditable(false);
					editView.ctrlManagement4Name.setText(null);
				}
			}
		});

		// 管理5チェック
		editView.chkManagement5.addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.chkManagement5.isSelected()) {
					editView.ctrlManagement5Name.setEditable(true);
				} else {
					editView.ctrlManagement5Name.setEditable(false);
					editView.ctrlManagement5Name.setText(null);
				}
			}
		});

		// 管理6チェック
		editView.chkManagement6.addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.chkManagement6.isSelected()) {
					editView.ctrlManagement6Name.setEditable(true);
				} else {
					editView.ctrlManagement6Name.setEditable(false);
					editView.ctrlManagement6Name.setText(null);
				}
			}
		});

		// 非会計明細区分1
		editView.cboNotAccount1.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.cboNotAccount1.getSelectedIndex() == 0) {
					editView.ctrlNotAccount1Name.setEditable(false);
					editView.ctrlNotAccount1Name.setText(null);
				} else {
					editView.ctrlNotAccount1Name.setEditable(true);
				}
			}
		});
		// 非会計明細区分2
		editView.cboNotAccount2.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.cboNotAccount2.getSelectedIndex() == 0) {
					editView.ctrlNotAccount2Name.setEditable(false);
					editView.ctrlNotAccount2Name.setText(null);
				} else {
					editView.ctrlNotAccount2Name.setEditable(true);
				}
			}
		});

		// 非会計明細区分3
		editView.cboNotAccount3.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.cboNotAccount3.getSelectedIndex() == 0) {
					editView.ctrlNotAccount3Name.setEditable(false);
					editView.ctrlNotAccount3Name.setText(null);
				} else {
					editView.ctrlNotAccount3Name.setEditable(true);
				}
			}
		});

		// 決算を行う／行わない
		editView.cboSettlement.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.cboSettlement.getSelectedIndex() == 0) {
					editView.ctrlSettlementStage.setEditable(true);
				} else {
					editView.ctrlSettlementStage.setEditable(false);
					editView.ctrlSettlementStage.setText(null);
				}
			}
		});
		// ワークフロー承認
		editView.cboWorkFlowApprove.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				afterWorkflowChanged();
			}
		});

	}

	/**
	 * ワークフロー承認チェックボックス変更時
	 */
	protected void afterWorkflowChanged() {
		boolean isWorkflow = editView.cboWorkFlowApprove.getSelectedItemValue() == ApproveType.WORKFLOW;
		if (isWorkflow) {
			// ワークフロー承認利用時は現場 / 経理承認も利用必須
			editView.chkFieldApprove.setSelected(true);
			editView.chkAccountantApprove.setSelected(true);
		} else {
			// ワークフロー承認非利用時は選択肢リセット
			editView.cboDenyAction.setSelectedItemValue(DenyAction.BACK_ONE);
		}
		switchApproveCheckEnable();
	}

	/**
	 * 承認系チェックボックス編集可否を切替
	 */
	protected void switchApproveCheckEnable() {
		boolean isWorkflow = editView.cboWorkFlowApprove.getSelectedItemValue() == ApproveType.WORKFLOW;
		editView.chkFieldApprove.setEnabled(!isWorkflow);
		editView.chkAccountantApprove.setEnabled(!isWorkflow);
		editView.cboDenyAction.setEnabled(isWorkflow);
	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param mode 操作モード。
	 * @param bean 会社。修正、複写の場合は当該会社情報を編集画面にセットする。
	 */
	protected void initEditView(@SuppressWarnings("hiding") Mode mode, Company bean) {

		this.mode = mode;

		editView.ctrlDetailItemName.setEditable(false);
		editView.ctrlManagement1Name.setEditable(false);
		editView.ctrlManagement2Name.setEditable(false);
		editView.ctrlManagement3Name.setEditable(false);
		editView.ctrlManagement4Name.setEditable(false);
		editView.ctrlManagement5Name.setEditable(false);
		editView.ctrlManagement6Name.setEditable(false);
		editView.ctrlNotAccount1Name.setEditable(false);
		editView.ctrlNotAccount2Name.setEditable(false);
		editView.ctrlNotAccount3Name.setEditable(false);
		// 新規の場合
		if (Mode.NEW == mode) {

			editView.setTitle(getWord("C01698"));// 新規画面

			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());
			switchApproveCheckEnable();
			return;

			// 編集、複写の場合
		} else if (Mode.MODIFY == mode || Mode.COPY == mode) {

			// 編集
			if (Mode.MODIFY == mode) {
				editView.setTitle(getWord("C00977"));// 編集画面
				editView.ctrlCode.setEditable(false);
				editView.setEditMode();
				// 複写
			} else {
				editView.setTitle(getWord("C01738"));// 複写画面
			}

			FiscalPeriod fp = bean.getFiscalPeriod();

			// 会社情報
			editView.ctrlCode.setValue(bean.getCode());
			editView.ctrlName.setValue(bean.getName());
			editView.ctrlNames.setValue(bean.getNames());
			editView.ctrlAddress1.setValue(bean.getAddress1());
			editView.ctrlAddress2.setValue(bean.getAddress2());
			editView.ctrlKana.setValue(bean.getAddressKana());
			editView.ctrlZipCode.setValue(bean.getZip());
			editView.ctrlPhoneNo.setValue(bean.getPhoneNo());
			editView.ctrlFaxNo.setValue(bean.getFaxNo());
			editView.ctrlInvRegNo.setValue(bean.getInvRegNo());
			editView.dtBeginDate.setValue(bean.getDateFrom());
			editView.dtEndDate.setValue(bean.getDateTo());

			// 科目と管理
			AccountConfig ac = bean.getAccountConfig();
			editView.ctrlItemName.setValue(ac.getItemName());
			editView.ctrlSubItemName.setValue(ac.getSubItemName());
			editView.chkDetailItem.setSelected(ac.isUseDetailItem());
			editView.ctrlDetailItemName.setValue(ac.getDetailItemName());
			editView.chkManagement1.setSelected(ac.isUseManagement1());
			editView.chkManagement2.setSelected(ac.isUseManagement2());
			editView.chkManagement3.setSelected(ac.isUseManagement3());
			editView.chkManagement4.setSelected(ac.isUseManagement4());
			editView.chkManagement5.setSelected(ac.isUseManagement5());
			editView.chkManagement6.setSelected(ac.isUseManagement6());
			editView.ctrlManagement1Name.setValue(ac.getManagement1Name());
			editView.ctrlManagement2Name.setValue(ac.getManagement2Name());
			editView.ctrlManagement3Name.setValue(ac.getManagement3Name());
			editView.ctrlManagement4Name.setValue(ac.getManagement4Name());
			editView.ctrlManagement5Name.setValue(ac.getManagement5Name());
			editView.ctrlManagement6Name.setValue(ac.getManagement6Name());
			editView.cboNotAccount1.setSelectedItemValue(ac.getNonAccounting1());
			editView.ctrlNotAccount1Name.setValue(ac.getNonAccounting1Name());
			editView.cboNotAccount2.setSelectedItemValue(ac.getNonAccounting2());
			editView.ctrlNotAccount2Name.setValue(ac.getNonAccounting2Name());
			editView.cboNotAccount3.setSelectedItemValue(ac.getNonAccounting3());
			editView.ctrlNotAccount3Name.setValue(ac.getNonAccounting3Name());

			// 伝票
			editView.ctrlSlipNoDigit.setNumber(ac.getSlipNoDigit());
			editView.cboSlipNoAdopt1.setSelectedItemValue(ac.getSlipNoAdopt1());
			editView.cboSlipNoAdopt2.setSelectedItemValue(ac.getSlipNoAdopt2());
			editView.cboSlipNoAdopt3.setSelectedItemValue(ac.getSlipNoAdopt3());
			editView.cboExchangeFraction.setSelectedItemValue(ac.getExchangeFraction());
			editView.cboTemporaryGetTaxFraction.setSelectedItemValue(ac.getReceivingFunction());
			editView.cboTemporaryPaymentTaxFraction.setSelectedItemValue(ac.getPaymentFunction());
			editView.chkSlipPrint.setSelected(ac.isSlipPrint());
			editView.chkSlipPrintInitial.setSelected(ac.getSlipPrintDefault());
			editView.cboConvertType.setSelectedItemValue(ac.getConvertType());

			// 決算
			if (fp.getMaxSettlementStage() > 0) {
				editView.cboSettlement.setSelectedIndex(0);
				editView.ctrlSettlementStage.setValue(fp.getMaxSettlementStage());
			} else {
				editView.cboSettlement.setSelectedIndex(1);
			}

			editView.cboSettlementPer.setSelectedItemValue(fp.getSettlementTerm());

			editView.cboForeignCurrencyExchangeRate.setSelectedItemValue(ac.getEvaluationRateDate());

			if (ac.isCarryJournalOfMidtermClosingForward()) {
				editView.cboCarryJournalOfMidtermClosingForward.setSelectedIndex(0);
			} else {
				editView.cboCarryJournalOfMidtermClosingForward.setSelectedIndex(1);
			}

			// その他
			editView.ctrlInitialMonth.setNumber(fp.getMonthBeginningOfPeriod());
			editView.cboWorkFlowApprove.setSelectedItemValue(ac.isUseWorkflowApprove() ? ApproveType.WORKFLOW
				: ApproveType.REGULAR);
			editView.cboDenyAction.setSelectedItemValue(ac.isBackFirstWhenWorkflowDeny() ? DenyAction.BACK_FIRST
				: DenyAction.BACK_ONE);
			editView.chkFieldApprove.setSelected(ac.isUseFieldApprove());
			editView.chkAccountantApprove.setSelected(ac.isUseApprove());
			editView.ctrlKeyCurrency.setEntity(ac.getKeyCurrency());
			editView.ctrlFunctionCurrency.setEntity(ac.getFunctionalCurrency());
			editView.chkGrpKbn.setSelected(ac.isUseGroupAccount());
			editView.chkIfrsKbn.setSelected(ac.isUseIfrs());
			editView.chkHasDateKbn.setSelected(ac.isUseHasDateCheck());
			editView.ctrlEnSignerText.setText(bean.getAR_SIGN_NAME());

			if (isInvoice) {
				editView.ctrlInvoiceChk.setSelected(bean.isCMP_INV_CHK_FLG());
			} else {
				editView.ctrlInvoiceChk.setVisible(false);
			}

			// 環境
			editView.ctrlCompanyColor.setColor(bean.getColor());

			// INVOICE使用(会社基礎情報英語版)--追加
			editView.ctrlEngName.setValue(bean.getKAI_NAME_ENG());
			editView.ctrlEngNames.setValue(bean.getKAI_NAME_S_ENG());
			editView.ctrlEngAddress1.setValue(bean.getJYU_1_ENG());
			editView.ctrlEngAddress2.setValue(bean.getJYU_2_ENG());

			// SPC会社情報--追加
			editView.ctrlConnCompanyCode.setValue(bean.getConnCompanyCode());
			editView.ctrlSignerPosition.setValue(bean.getSignerPosition());
			editView.ctrlRemitterName.setValue(bean.getRemitterName());
			editView.ctrlConnPhoneNo.setValue(bean.getConnPhoneNo());
			editView.ctrlDebitNoteAddress1.setValue(bean.getDebitNoteAddress1());
			editView.ctrlDebitNoteAddress2.setValue(bean.getDebitNoteAddress2());
			editView.ctrlDebitNoteAddress3.setValue(bean.getDebitNoteAddress3());
			editView.ctrlDebitNoteInfo.setText(bean.getDebitNoteInfo());

			// 承認チェックボックス変更 途中で機能追加を想定しswitchではなくafter処理
			afterWorkflowChanged();

		}

	}

	/**
	 * 編集画面[確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {

			// 入力チェックを行う。
			if (!isInputRight()) {
				return;
			}

			// 入力された会社情報を取得
			Company company = getInputedCompany();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entry", company);

				// 編集画面を閉じる
				btnClose_Click();

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(company));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", company);

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(company));

				// 編集画面を閉じる
				btnClose_Click();

			}

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
	 * 指示画面で入力された会社の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected CompanySearchCondition getSearchCondition() {

		CompanySearchCondition condition = createCompanySearchCondition();
		condition.setCodeFrom(mainView.ctrlCompanyRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlCompanyRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}
		condition.setNotShowSpc(isNotShowSpc); // SPC非表示フラグ持つ
		condition.setShowInvoice(isShowCompanyEng);// INVOICE(会社基礎情報英語版)
		condition.setShowARSignerEng(isShowARSignerEng); // AR：英分請求書SIGNER表示可否
		condition.setInvoiceFlg(isInvoice); // インボイス使用

		return condition;

	}

	/**
	 * モデルインターフェースを返す
	 * 
	 * @return モデルインターフェース
	 */
	protected Class getModelClass() {
		return CompanyManager.class;
	}

	/**
	 * 編集画面で入力された会社を返す
	 * 
	 * @return 編集画面で入力された会社
	 */
	protected Company getInputedCompany() {

		// 会社情報
		Company company = createCompany();
		company.setCode(editView.ctrlCode.getValue());
		company.setName(editView.ctrlName.getValue());
		company.setNames(editView.ctrlNames.getValue());
		company.setAddress1(editView.ctrlAddress1.getValue());
		company.setAddress2(editView.ctrlAddress2.getValue());
		company.setAddressKana(editView.ctrlKana.getValue());
		company.setZip(editView.ctrlZipCode.getValue());
		company.setPhoneNo(editView.ctrlPhoneNo.getValue());
		company.setFaxNo(editView.ctrlFaxNo.getValue());
		company.setInvRegNo(editView.ctrlInvRegNo.getValue());
		company.setDateFrom(editView.dtBeginDate.getValue());
		company.setDateTo(editView.dtEndDate.getValue());

		// 科目と管理
		AccountConfig ac = createAccountConfig();
		ac.setItemName(editView.ctrlItemName.getValue());
		ac.setSubItemName(editView.ctrlSubItemName.getValue());
		ac.setUseDetailItem(editView.chkDetailItem.isSelected());
		ac.setDetailItemName(editView.ctrlDetailItemName.getText());
		ac.setUseManagement1(editView.chkManagement1.isSelected());
		ac.setUseManagement2(editView.chkManagement2.isSelected());
		ac.setUseManagement3(editView.chkManagement3.isSelected());
		ac.setUseManagement4(editView.chkManagement4.isSelected());
		ac.setUseManagement5(editView.chkManagement5.isSelected());
		ac.setUseManagement6(editView.chkManagement6.isSelected());
		ac.setManagement1Name(editView.ctrlManagement1Name.getText());
		ac.setManagement2Name(editView.ctrlManagement2Name.getText());
		ac.setManagement3Name(editView.ctrlManagement3Name.getText());
		ac.setManagement4Name(editView.ctrlManagement4Name.getText());
		ac.setManagement5Name(editView.ctrlManagement5Name.getText());
		ac.setManagement6Name(editView.ctrlManagement6Name.getText());
		ac.setNonAccounting1(((NonAccountingDivision) editView.cboNotAccount1.getSelectedItemValue()).getValue());
		ac.setNonAccounting1Name(editView.ctrlNotAccount1Name.getText());
		ac.setNonAccounting2(((NonAccountingDivision) editView.cboNotAccount2.getSelectedItemValue()).getValue());
		ac.setNonAccounting2Name(editView.ctrlNotAccount2Name.getText());
		ac.setNonAccounting3(((NonAccountingDivision) editView.cboNotAccount3.getSelectedItemValue()).getValue());
		ac.setNonAccounting3Name(editView.ctrlNotAccount3Name.getText());

		// 伝票
		ac.setSlipNoDigit(editView.ctrlSlipNoDigit.getIntValue());
		ac.setSlipNoAdopt1((SlipNoAdopt) editView.cboSlipNoAdopt1.getSelectedItemValue());
		ac.setSlipNoAdopt2((SlipNoAdopt) editView.cboSlipNoAdopt2.getSelectedItemValue());
		ac.setSlipNoAdopt3((SlipNoAdopt) editView.cboSlipNoAdopt3.getSelectedItemValue());
		ac.setExchangeFraction((ExchangeFraction) editView.cboExchangeFraction.getSelectedItemValue());
		ac.setReceivingFunction((ExchangeFraction) editView.cboTemporaryGetTaxFraction.getSelectedItemValue());
		ac.setPaymentFunction((ExchangeFraction) editView.cboTemporaryPaymentTaxFraction.getSelectedItemValue());
		ac.setSlipPrint(editView.chkSlipPrint.isSelected());
		ac.setSlipPrintDefault(editView.chkSlipPrintInitial.isSelected());
		ac.setConvertType((ConvertType) editView.cboConvertType.getSelectedItemValue());

		// 決算
		FiscalPeriod fp = createFiscalPeriod();
		if (editView.cboSettlement.getComboBox().getSelectedIndex() == 0) {
			fp.setMaxSettlementStage(editView.ctrlSettlementStage.getInt());
		} else if (editView.cboSettlement.getComboBox().getSelectedIndex() == 1) {
			fp.setMaxSettlementStage(0);
		}
		fp.setSettlementTerm((SettlementTerm) editView.cboSettlementPer.getSelectedItemValue());

		ac.setEvaluationRateDate((EvaluationRateDate) editView.cboForeignCurrencyExchangeRate.getSelectedItemValue());

		ac.setCarryJournalOfMidtermClosingForward(editView.cboCarryJournalOfMidtermClosingForward.getSelectedIndex() == 0);

		// その他
		fp.setMonthBeginningOfPeriod(editView.ctrlInitialMonth.getIntValue());
		ac.setUseWorkflowApprove(editView.cboWorkFlowApprove.getSelectedItemValue() == ApproveType.WORKFLOW);
		ac.setBackFirstWhenWorkflowDeny(editView.cboDenyAction.getSelectedItemValue() == DenyAction.BACK_FIRST);
		ac.setUseFieldApprove(editView.chkFieldApprove.isSelected());
		ac.setUseApprove(editView.chkAccountantApprove.isSelected());
		ac.setKeyCurrency(editView.ctrlKeyCurrency.getEntity());
		ac.setFunctionalCurrency(editView.ctrlFunctionCurrency.getEntity());
		ac.setUseGroupAccount(editView.chkGrpKbn.isSelected());
		ac.setUseIfrs(editView.chkIfrsKbn.isSelected());
		ac.setUseHasDateCheck(editView.chkHasDateKbn.isSelected());
		company.setCMP_INV_CHK_FLG(editView.ctrlInvoiceChk.isSelected());
		company.setAR_SIGN_NAME(editView.ctrlEnSignerText.getText());

		company.setAccountConfig(ac);
		company.setFiscalPeriod(fp);

		// 環境
		company.setColor(editView.ctrlCompanyColor.getColor());

		// INVOICE使用(会社基礎情報英語版)
		company.setKAI_NAME_ENG(editView.ctrlEngName.getValue());
		company.setKAI_NAME_S_ENG(editView.ctrlEngNames.getValue());
		company.setJYU_1_ENG(editView.ctrlEngAddress1.getValue());
		company.setJYU_2_ENG(editView.ctrlEngAddress2.getValue());

		// SPC会社情報
		company.setConnCompanyCode(editView.ctrlConnCompanyCode.getValue());
		company.setSignerPosition(editView.ctrlSignerPosition.getValue());
		company.setRemitterName(editView.ctrlRemitterName.getValue());
		company.setConnPhoneNo(editView.ctrlConnPhoneNo.getValue());
		company.setDebitNoteAddress1(editView.ctrlDebitNoteAddress1.getValue());
		company.setDebitNoteAddress2(editView.ctrlDebitNoteAddress2.getValue());
		company.setDebitNoteAddress3(editView.ctrlDebitNoteAddress3.getValue());
		company.setDebitNoteInfo(editView.ctrlDebitNoteInfo.getText());

		return company;

	}

	/**
	 * @return 会社の検索条件
	 */
	protected CompanySearchCondition createCompanySearchCondition() {
		return new CompanySearchCondition();
	}

	/**
	 * @return 会計期間
	 */
	protected FiscalPeriod createFiscalPeriod() {
		return new FiscalPeriod();
	}

	/**
	 * @return 会計の設定情報
	 */
	protected AccountConfig createAccountConfig() {
		return new AccountConfig();
	}

	/**
	 * @return 会社
	 */
	protected Company createCompany() {
		return new Company();
	}

	/**
	 * 会社情報を一覧に表示する形式に変換し返す
	 * 
	 * @param company 会社
	 * @return 一覧に表示する形式に変換された会社
	 */
	protected String[] getRowData(Company company) {
		return new String[] { company.getCode(), company.getName(), company.getNames(),
				DateUtil.toYMDString(company.getDateFrom()), DateUtil.toYMDString(company.getDateTo()) };
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
	 * 検索条件に該当する会社のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する会社のリスト
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Company> getCompany(CompanySearchCondition condition) throws Exception {

		List<Company> list = (List<Company>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * 一覧で選択されている会社を返す
	 * 
	 * @return 一覧で選択されている会社
	 * @throws Exception
	 */
	protected Company getSelectedCompany() throws Exception {

		CompanySearchCondition condition = createCompanySearchCondition();
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0010CompanyMasterPanel.SC.code));
		condition.setShowInvoice(isShowCompanyEng);// INVOICE(会社基礎情報英語版)
		List<Company> list = getCompany(condition);

		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * 指定の会社を削除する
	 * 
	 * @param company 会社
	 * @throws Exception
	 */
	protected void deleteCompany(Company company) throws Exception {
		request(getModelClass(), "delete", company);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {
		int tabIndex = 0;
		// -- 会社情報タブ --
		// 会社コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			showMessage(editView, "I00037", "C00596");// 会社コード
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一会社が既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			CompanySearchCondition condition = createCompanySearchCondition();
			condition.setCode(editView.ctrlCode.getValue());
			condition.setShowInvoice(isShowCompanyEng);// INVOICE(会社基礎情報英語版)

			List<Company> list = getCompany(condition);

			if (list != null && !list.isEmpty()) {
				// 指定の会社は既に存在します
				showMessage(editView, "I00148", "C00053");
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlCode.requestTextFocus();
				return false;
			}
		}

		// 会社名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlName.getValue())) {
			showMessage(editView, "I00037", "C00685");// 会社名称
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlName.requestTextFocus();
			return false;
		}

		// 会社略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlNames.getValue())) {
			showMessage(editView, "I00037", "C00686");// 会社略称
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlNames.requestTextFocus();
			return false;
		}

		// 開始年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055");// 開始年月日
			editView.tab.setSelectedIndex(tabIndex);
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// 終了年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261");// 終了年月日
			editView.tab.setSelectedIndex(tabIndex);
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		// 開始年月日,終了年月日チェック
		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}

		// -- 科目と管理タブ --
		tabIndex++;
		// 科目名称
		if (Util.isNullOrEmpty(editView.ctrlItemName.getValue())) {
			showMessage(editView, "I00037", "C00700");// 科目名称
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlItemName.requestFocus();
			return false;
		}

		// 補助科目名称
		if (Util.isNullOrEmpty(editView.ctrlSubItemName.getValue())) {
			showMessage(editView, "I00037", "C00701");// 補助科目名称
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlSubItemName.requestFocus();
			return false;
		}

		// 内訳科目名称
		if (editView.ctrlDetailItemName.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlDetailItemName.getValue())) {
				showMessage(editView, "I00037", "C00702");// 内訳科目名称
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlDetailItemName.requestFocus();
				return false;
			}
		}

		// 管理区分1名称
		if (editView.ctrlManagement1Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlManagement1Name.getValue())) {
				editView.tab.setSelectedIndex(1);
				showMessage(editView, "I00037", "C11204");// 管理区分1の名称
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlManagement1Name.requestFocus();
				return false;
			}
		}

		// 管理区分2名称
		if (editView.ctrlManagement2Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlManagement2Name.getValue())) {
				showMessage(editView, "I00037", "C11205");// 管理区分2の名称
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlManagement2Name.requestFocus();
				return false;
			}
		}

		// 管理区分3名称
		if (editView.ctrlManagement3Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlManagement3Name.getValue())) {
				showMessage(editView, "I00037", "C11206");// 管理区分3の名称
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlManagement3Name.requestFocus();
				return false;
			}
		}

		// 管理区分4名称
		if (editView.ctrlManagement4Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlManagement4Name.getValue())) {
				showMessage(editView, "I00037", "C11207");// 管理区分4の名称
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlManagement4Name.requestFocus();
				return false;
			}
		}

		// 管理区分5名称
		if (editView.ctrlManagement5Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlManagement5Name.getValue())) {
				showMessage(editView, "I00037", "C11208");// 管理区分5の名称
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlManagement5Name.requestFocus();
				return false;
			}
		}

		// 管理区分6名称
		if (editView.ctrlManagement6Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlManagement6Name.getValue())) {
				showMessage(editView, "I00037", "C11209");// 管理区分6の名称
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlManagement6Name.requestFocus();
				return false;
			}
		}

		// 非会計明細区分名称1
		if (editView.ctrlNotAccount1Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlNotAccount1Name.getValue())) {
				showMessage(editView, "I00037", "C11210");// 非会計明細区分1の名称
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlNotAccount1Name.requestFocus();
				return false;
			}
		}

		// 非会計明細区分名称2
		if (editView.ctrlNotAccount2Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlNotAccount2Name.getValue())) {
				showMessage(editView, "I00037", "C11211");// 非会計明細区分2の名称
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlNotAccount2Name.requestFocus();
				return false;
			}
		}

		// 非会計明細区分名称3
		if (editView.ctrlNotAccount3Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlNotAccount3Name.getValue())) {
				showMessage(editView, "I00037", "C11212");// 非会計明細区分3の名称
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlNotAccount3Name.requestFocus();
				return false;
			}
		}

		// -- 伝票タブ --
		tabIndex++;
		// 自動採番部
		if (Util.isNullOrEmpty(editView.ctrlSlipNoDigit.getValue())) {
			showMessage(editView, "I00037", "C00224");// 自動採番部桁数
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlSlipNoDigit.requestFocus();
			return false;
		}

		// -- 決算タブ --
		tabIndex++;
		// 決算段階数
		if (editView.ctrlSettlementStage.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlSettlementStage.getValue())) {
				showMessage(editView, "I00037", "C11189");// 決算段階数
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlSettlementStage.requestFocus();
				return false;
			}
		}

		if (editView.ctrlSettlementStage.isEditable() && editView.ctrlSettlementStage.getInt() == 0) {
			// 決算段階数は1～9に指定してください。
			showMessage(editView, "I00226");
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlSettlementStage.requestFocus();
			return false;
		}

		// グループ会計を使用する場合、決算段階を合わせる
		if (editView.chkGrpKbn.isSelected()) {
			int settlementStage = 0;
			if (editView.ctrlSettlementStage.isEditable()) {
				settlementStage = editView.ctrlSettlementStage.getInt();
			}
			List<Company> list = getDifferentSettlementStageCompany(settlementStage);
			if (list != null && !list.isEmpty()) {
				// グループ会計を使用する場合、グループ会社と決算段階を合わせて下さい
				showMessage(editView, "I00227");
				editView.tab.setSelectedIndex(tabIndex);
				editView.chkGrpKbn.requestFocus();
				return false;
			}
		}

		// -- その他タブ --
		tabIndex++;
		// 会計期間 期首月
		if (Util.isNullOrEmpty(editView.ctrlInitialMonth.getValue())) {
			showMessage(editView, "I00037", "C00105");// 期首月
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlInitialMonth.requestFocus();
			return false;
		}

		if (Util.avoidNullAsInt(editView.ctrlInitialMonth.getValue()) > 12
			|| Util.avoidNullAsInt(editView.ctrlInitialMonth.getValue()) == 0) {
			// 期首月は1～12に指定してください
			showMessage(editView, "I00228");
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlInitialMonth.requestFocus();
			return false;
		}

		// 基軸通貨
		if (editView.ctrlKeyCurrency.isEmpty()) {
			showMessage(editView, "I00037", "C00907");// 基軸通貨
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlKeyCurrency.requestTextFocus();
			return false;
		}

		// 機能通貨
		if (editView.ctrlFunctionCurrency.isEmpty()) {
			showMessage(editView, "I00037", "C11084");// 機能通貨
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlFunctionCurrency.requestTextFocus();
			return false;
		}

		// -- 環境タブ --
		// なし
		return true;

	}

	/**
	 * 指定決算段階以外のグループ会社を使用する会社を返す
	 * 
	 * @param settlementStage 決算段階
	 * @return List
	 * @throws Exception
	 */
	protected List<Company> getDifferentSettlementStageCompany(int settlementStage) throws Exception {

		CompanySearchCondition condition = createCompanySearchCondition();
		condition.setGroupAccountDivision(1);
		condition.setSettlementStageOtherThan(settlementStage);
		condition.setShowInvoice(isShowCompanyEng);// INVOICE(会社基礎情報英語版)

		List<Company> list = getCompany(condition);

		return list;

	}
}
