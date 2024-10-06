package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 伝票種別マスタ コントローラー
 */
public class MG0330SlipTypeMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0330SlipTypeMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0330SlipTypeMasterDialog editView = null;

	/** インボイス制度チェックするかどうか true:表示する */
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

	/**
	 * invoice使用するかどうか
	 */
	protected void initInvoiceFlg() {

		isInvoice = getCompany().isCMP_INV_CHK_FLG();
	}

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MG0330SlipTypeMasterPanel();
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

			// 検索条件
			SlipTypeSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				// 警告メッセージ表示
				showMessage(mainView, "I00068");
				mainView.TSlipTypeReferenceRange.ctrlSlipTypeReferenceFrom.code.requestFocus();
				return;
			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 伝票種別情報を取得
			List<SlipType> list = getSlipType(condition);

			// 検索条件に該当する伝票種別が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 伝票種別情報を一覧に表示する
			for (SlipType sliptype : list) {
				mainView.tbl.addRow(getRowData(sliptype));
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

			// 編集対象の伝票種別を取得する
			SlipType sliptype = getSelectedSlipType();

			// 編集画面を生成し、編集対象の伝票種別情報をセットする
			createEditView();
			initEditView(Mode.MODIFY, sliptype);

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

			// 複写対象の伝票種別を取得する
			SlipType sliptype = getSelectedSlipType();

			// 複写画面を生成し、複写対象の伝票種別情報をセットする
			createEditView();
			initEditView(Mode.COPY, sliptype);

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
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除対象の伝票種別を取得する
			SlipType sliptype = getSelectedSlipType();

			// 削除する
			deleteSlipType(sliptype);

			// 削除した伝票種別を一覧から削除
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

			SlipTypeSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			// 伝票種別マスタ
			printer.preview(data, getWord("C00912") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0330SlipTypeMasterDialog(mainView.getParentFrame(), true);

		// コンボボックスの中身
		initDataDivComboBox(editView.cboDataDiv.getComboBox());
		// しない
		editView.cboDenNoFlg.getComboBox().addTextValueItem(false, getWord("C02099"));
		// "する"
		editView.cboDenNoFlg.getComboBox().addTextValueItem(true, getWord("C02100"));
		// 他シス受入対象外
		editView.cboAnoSysKbn.getComboBox().addTextValueItem(false, getWord("C02097"));
		// 他シス受入対象
		editView.cboAnoSysKbn.getComboBox().addTextValueItem(true, getWord("C02098"));
		initAcceptUnitComboBox(editView.cboAcceptUnit.getComboBox());
		initTaxCulDivComboBox(editView.cboTaxCulDiv.getComboBox());
		initJnlIfComboBox(editView.cboJnlIfDiv.getComboBox());

		// しない
		editView.cboInvoiceCheck.getComboBox().addTextValueItem(false, getWord("C02099"));
		// する
		editView.cboInvoiceCheck.getComboBox().addTextValueItem(true, getWord("C02100"));

		// 編集画面のイベント定義
		addEditViewEvent();

	}

	/**
	 * データ区分コンボボックスの初期化
	 * 
	 * @param comboBox
	 */
	protected void initDataDivComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(DataDivision.INPUT, getWord(DataDivision.getDataDivisionName(DataDivision.INPUT)));
		comboBox.addTextValueItem(DataDivision.OUTPUT, getWord(DataDivision.getDataDivisionName(DataDivision.OUTPUT)));
		comboBox.addTextValueItem(DataDivision.TRANSFER,
			getWord(DataDivision.getDataDivisionName(DataDivision.TRANSFER)));
		comboBox.addTextValueItem(DataDivision.ESTIMATE,
			getWord(DataDivision.getDataDivisionName(DataDivision.ESTIMATE)));
		comboBox.addTextValueItem(DataDivision.ESTIMATE_DEL,
			getWord(DataDivision.getDataDivisionName(DataDivision.ESTIMATE_DEL)));
		comboBox.addTextValueItem(DataDivision.LEDGER_IFRS,
			getWord(DataDivision.getDataDivisionName(DataDivision.LEDGER_IFRS)));
		comboBox.addTextValueItem(DataDivision.LEDGER_SELF,
			getWord(DataDivision.getDataDivisionName(DataDivision.LEDGER_SELF)));
		comboBox.addTextValueItem(DataDivision.EMP_KARI,
			getWord(DataDivision.getDataDivisionName(DataDivision.EMP_KARI)));
		comboBox
			.addTextValueItem(DataDivision.EXPENCE, getWord(DataDivision.getDataDivisionName(DataDivision.EXPENCE)));
		comboBox
			.addTextValueItem(DataDivision.ACCOUNT, getWord(DataDivision.getDataDivisionName(DataDivision.ACCOUNT)));
		comboBox.addTextValueItem(DataDivision.PAYMENT_EMP,
			getWord(DataDivision.getDataDivisionName(DataDivision.PAYMENT_EMP)));
		comboBox.addTextValueItem(DataDivision.ABROAD, getWord(DataDivision.getDataDivisionName(DataDivision.ABROAD)));
		comboBox.addTextValueItem(DataDivision.PAYMENT_COM,
			getWord(DataDivision.getDataDivisionName(DataDivision.PAYMENT_COM)));
		comboBox.addTextValueItem(DataDivision.CREDIT_SLIP,
			getWord(DataDivision.getDataDivisionName(DataDivision.CREDIT_SLIP)));
		comboBox.addTextValueItem(DataDivision.CREDIT_DEL,
			getWord(DataDivision.getDataDivisionName(DataDivision.CREDIT_DEL)));
	}

	/**
	 * 受入単位コンボボックスの初期化
	 * 
	 * @param comboBox
	 */
	protected void initAcceptUnitComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(AcceptUnit.SLIPTYPE, getWord(AcceptUnit.getAcceptUnitName(AcceptUnit.SLIPTYPE)));
		comboBox.addTextValueItem(AcceptUnit.SLIPNO, getWord(AcceptUnit.getAcceptUnitName(AcceptUnit.SLIPNO)));
	}

	/**
	 * 消費税計算区分コンボボックスの初期化
	 * 
	 * @param comboBox
	 */
	protected void initTaxCulDivComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(TaxCalcType.OUT, getWord(TaxCalcType.getTaxCalcTypeName(TaxCalcType.OUT)));
		comboBox.addTextValueItem(TaxCalcType.IN, getWord(TaxCalcType.getTaxCalcTypeName(TaxCalcType.IN)));
	}

	/**
	 * 仕訳インターフェース区分コンボボックスの初期化
	 * 
	 * @param comboBox
	 */
	protected void initJnlIfComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(SlipState.ENTRY, getWord(SlipState.getSlipStateName(SlipState.ENTRY)));
		comboBox.addTextValueItem(SlipState.APPROVE, getWord(SlipState.getSlipStateName(SlipState.APPROVE)));
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

	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param mode_ 操作モード。
	 * @param sliptype 。修正、複写の場合は当該情報を編集画面にセットする。
	 */
	protected void initEditView(Mode mode_, SlipType sliptype) {

		this.mode = mode_;

		// 新規の場合
		if (Mode.NEW == mode_) {
			// 新規画面
			editView.setTitle(getWord("C01698"));

			if (!isInvoice) {
				editView.cboInvoiceCheck.setVisible(false);
			}

			return;
		}

		// 編集
		if (Mode.MODIFY == mode_) {
			// 編集画面
			editView.setTitle(getWord("C00977"));
			editView.ctrlSlipTypeCode.setEditable(false);
			editView.setEditMode();
		}
		// 複写
		else {
			// 複写画面
			editView.setTitle(getWord("C01738"));
		}

		// dialogの初期値
		editView.ctrlSlipTypeCode.setValue(sliptype.getCode());
		editView.ctrlSysKbn.setEntity(sliptype.getSystemDivision());
		editView.ctrlSlipTypeName.setValue(sliptype.getName());
		editView.ctrlSlipTypeNames.setValue(sliptype.getNames());
		editView.ctrlSlipTitle.setValue(sliptype.getNamek());
		if (DataDivision.getDataDivision(sliptype.getDataType()) != null) {
			editView.cboDataDiv.setSelectedIndex(DataDivision.getDataDivision(sliptype.getDataType()).value);
		}
		editView.cboAnoSysKbn.setSelectedItemValue(sliptype.isAnotherSystemDivision());
		editView.cboDenNoFlg.setSelectedItemValue(sliptype.isTakeNewSlipNo());
		editView.cboAcceptUnit.setSelectedItemValue(sliptype.isAcceptUnit());
		editView.cboTaxCulDiv
			.setSelectedIndex(TaxCalcType.getTaxCulKbn(sliptype.isInnerConsumptionTaxCalculation()).value);
		editView.cboJnlIfDiv.setSelectedItemValue(sliptype.getJounalIfDivision());
		editView.ctrlRevSlipTypeReference.setEntity(sliptype.getReversingSlipType());

		if (isInvoice) {
			editView.cboInvoiceCheck.setSelectedItemValue(sliptype.isINV_SYS_FLG() ? true : false);
		} else {
			editView.cboInvoiceCheck.setVisible(false);
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

			// 入力された伝票種別情報を取得
			SlipType sliptype = getInputedSlipType();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entry", sliptype);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(sliptype));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", sliptype);

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(sliptype));

			}

			// 編集画面を閉じる
			btnClose_Click();

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
	 * 指示画面で入力された伝票種別の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected SlipTypeSearchCondition getSearchCondition() {

		SlipTypeSearchCondition condition = new SlipTypeSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCodeFrom(mainView.TSlipTypeReferenceRange.getCodeFrom());
		condition.setCodeTo(mainView.TSlipTypeReferenceRange.getCodeTo());
		condition.setInvoiceFlg(isInvoice);

		return condition;

	}

	/**
	 * Servletクラスを返す
	 * 
	 * @return CustomerManager
	 */
	protected Class getModelClass() {
		return SlipTypeManager.class;
	}

	/**
	 * 編集画面で入力された情報を返す ※伝票番号採番ﾌﾗｸﾞはSlipTypeManagerImplで取得
	 * 
	 * @return 編集画面で入力された伝票種別
	 */
	protected SlipType getInputedSlipType() {

		SlipType sliptype = createSlipType();
		sliptype.setCompanyCode(getCompany().getCode());

		sliptype.setCode(editView.ctrlSlipTypeCode.getValue());
		sliptype.setSystemDiv(editView.ctrlSysKbn.getCode());
		sliptype.setName(editView.ctrlSlipTypeName.getValue());
		sliptype.setNames(editView.ctrlSlipTypeNames.getValue());
		sliptype.setNamek(editView.ctrlSlipTitle.getValue());
		sliptype
			.setDataType(DataDivision.getDataDivisionCode((DataDivision) editView.cboDataDiv.getSelectedItemValue()));
		sliptype.setAnotherSystemDivision(BooleanUtil.toBoolean(editView.cboAnoSysKbn.getSelectedIndex()));
		if (editView.cboTaxCulDiv.getSelectedIndex() == 0) {
			sliptype.setInnerConsumptionTaxCalculation(false);
		} else {
			sliptype.setInnerConsumptionTaxCalculation(true);
		}
		if (editView.cboDenNoFlg.getSelectedIndex() == 0) {
			sliptype.setTakeNewSlipNo(false);
		} else {
			sliptype.setTakeNewSlipNo(true);
		}
		sliptype.setAcceptUnit((AcceptUnit) editView.cboAcceptUnit.getSelectedItemValue());
		sliptype.setJounalIfDivision((SlipState) editView.cboJnlIfDiv.getSelectedItemValue());
		sliptype.setReversingSlipType(editView.ctrlRevSlipTypeReference.getEntity());

		if (isInvoice) {
			sliptype.setINV_SYS_FLG(BooleanUtil.toBoolean(editView.cboInvoiceCheck.getSelectedIndex()));
		}

		return sliptype;

	}

	/**
	 * 伝票種別ファクトリ
	 * 
	 * @return SlipType
	 */
	protected SlipType createSlipType() {
		return new SlipType();
	}

	/**
	 * 伝票種別情報を一覧に表示する形式に変換し返す
	 * 
	 * @param slipType 伝票種別
	 * @return 一覧に表示する形式に変換された伝票種別
	 */
	protected List<String> getRowData(SlipType slipType) {

		List<String> list = new ArrayList<String>();

		String slipTypeCode = null;
		String slipTypeName = null;

		list.add(slipType.getCode());
		list.add(slipType.getSystemDiv());
		list.add(slipType.getName());
		list.add(slipType.getNames());
		list.add(slipType.getNamek());
		list.add(getWord(DataDivision.getDataDivisionName(DataDivision.getDataDivision(slipType.getDataType()))));
		list.add(getWord(slipType.getAnotherSystemDivisionName()));
		list.add(getWord(slipType.getSlipIndexDivisionName()));
		list.add(getWord(AcceptUnit.getAcceptUnitName(slipType.isAcceptUnit())));
		list.add(getWord(TaxCalcType.getTaxCalcTypeName(TaxCalcType.getTaxCulKbn(slipType
			.isInnerConsumptionTaxCalculation()))));
		list.add(getWord(SlipState.getSlipStateName(slipType.getJounalIfDivision())));

		if (slipType.getReversingSlipType() != null) {
			slipTypeCode = slipType.getReversingSlipType().getCode();
			slipTypeName = slipType.getReversingSlipType().getName();
		}
		list.add(slipTypeCode);
		list.add(slipTypeName);

		list.add(slipType.isINV_SYS_FLG() ? getWord("C02100") : getWord("C02099"));

		return list;
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
	 * 一覧で選択されている伝票種別を返す
	 * 
	 * @return 一覧で選択されている伝票種別
	 * @throws Exception
	 */
	protected SlipType getSelectedSlipType() throws Exception {

		SlipTypeSearchCondition condition = new SlipTypeSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0330SlipTypeMasterPanel.SC.denSyuCode));

		List<SlipType> list = getSlipType(condition);

		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました。
			throw new TException(getMessage("I00193"));
		}
		return list.get(0);
	}

	/**
	 * 検索条件に該当する伝票種別のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する伝票種別のリスト
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<SlipType> getSlipType(SlipTypeSearchCondition condition) throws Exception {

		List<SlipType> list = (List<SlipType>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * 指定の伝票種別を削除する
	 * 
	 * @param sliptype
	 * @throws Exception
	 */
	protected void deleteSlipType(SlipType sliptype) throws Exception {
		request(getModelClass(), "delete", sliptype);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// 伝票種別コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlSlipTypeCode.getValue())) {
			// 伝票種別コードを入力してください。
			showMessage(editView, "I00037", "C00837");
			editView.ctrlSlipTypeCode.requestTextFocus();
			return false;
		}

		// システム区分が未入力の場合エラー if
		if (Util.isNullOrEmpty(editView.ctrlSysKbn.code.getValue())) {
			// "システム区分コードを入力してください。
			showMessage(editView, "I00037", "C11223");
			editView.ctrlSysKbn.requestTextFocus();
			return false;
		}

		// 伝票種別名称が未入力の場合エラー if
		if (Util.isNullOrEmpty(editView.ctrlSlipTypeName.getValue())) {
			// 伝票種別名称を入力してください。
			showMessage(editView, "I00037", "C00838");
			editView.ctrlSlipTypeName.requestTextFocus();
			return false;
		}

		// 伝票種別略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlSlipTypeNames.getValue())) {
			// 伝票種別略称を入力してください。"
			showMessage(editView, "I00037", "C00839");
			editView.ctrlSlipTypeNames.requestTextFocus();
			return false;
		}

		// 帳票タイトルが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlSlipTitle.getValue())) {
			// 帳票タイトルを入力してください。
			showMessage(editView, "I00037", "C02757");
			editView.ctrlSlipTitle.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一伝票種別が既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			SlipTypeSearchCondition condition = new SlipTypeSearchCondition();
			condition.setCode(editView.ctrlSlipTypeCode.getValue());
			condition.setCompanyCode(getCompanyCode());
			List<SlipType> list = getSlipType(condition);
			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00261");
				editView.ctrlSlipTypeCode.requestTextFocus();
				return false;
			}
		}

		return true;
	}

}
