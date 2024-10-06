package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * MG0130ConsumptionTaxMaster - 消費税マスタ - Main Controller
 * 
 * @author AIS
 */
public class MG0130ConsumptionTaxMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0130ConsumptionTaxMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0130ConsumptionTaxMasterDialog editView = null;

	/** 現在操作中のモードを把握するために使用する */
	protected Mode mode = null;

	/** 編集前の非適格請求書発行事業者のフラグ保持 */
	protected boolean isBeforeNoInvRegFlg = false;

	/** 非適格請求書発行事業者の設定を表示するかどうか true:表示する */
	protected static boolean isInvoiceTaxProperty = ClientConfig.isFlagOn("trans.slip.use.invoice.tax");

	/** true: 2023INVOICE制度対応を使用する(会社情報含む) */
	public static boolean isInvoice = false;

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

			// インボイス使用するかどうか
			if (isInvoiceTaxProperty) {
				initInvoiceFlg();
			}

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
		mainView = new MG0130ConsumptionTaxMasterPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面初期化
	 */
	protected void initMainView() {
		setMainButtonEnabled(false);
	}

	/**
	 * メインボタンの押下制御
	 * 
	 * @param bln Boolean
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnModify.setEnabled(bln);
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
	}

	/**
	 * invoice使用するかどうか
	 */
	protected void initInvoiceFlg() {

		isInvoice = getCompany().isCMP_INV_CHK_FLG();
	}

	/**
	 * 指示画面のイベント定義
	 */
	protected void addMainViewEvent() {

		// [新規]ボタン
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [検索]ボタン
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [編集]ボタン
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [複写]ボタン
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [削除]ボタン
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [エクセル]ボタン
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * 指示画面[新規]ボタン押下
	 */
	protected void btnNew_Click() {
		try {

			// 編集画面生成
			createEditView();

			// 編集画面初期化
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

			// 検索条件取得
			ConsumptionTaxSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlTaxRefRan.requestFocus();
				return;
			}

			// 一覧をクリア
			mainView.tbList.removeRow();

			// メインボタン制御
			setMainButtonEnabled(false);

			// データ取得 //???List<String> dataTypeList
			List<ConsumptionTax> list = getList(condition);

			// 検索条件に該当するデータが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 検索したデータを一覧に表示する
			for (ConsumptionTax cst : list) {
				mainView.tbList.addRow(getRowData(cst));
			}

			// メインボタン制御
			setMainButtonEnabled(true);

			mainView.tbList.setRowSelectionInterval(0, 0);

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

			// 編集対象のデータ取得
			ConsumptionTax bean = getSelected();

			if (bean == null) {
				return;
			}

			isBeforeNoInvRegFlg = bean.isNO_INV_REG_FLG();

			// 編集画面生成
			createEditView();

			// 編集画面に選択データをセット
			initEditView(Mode.MODIFY, bean);

			// 編集画面表示
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

			// 複写対象のデータ取得
			ConsumptionTax bean = getSelected();

			if (bean == null) {
				return;
			}

			// 複写画面生成
			createEditView();

			// 編集画面に選択データをセット
			initEditView(Mode.COPY, bean);

			// 複写画面表示
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

			// 確認メッセージ表示
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除対象のデータ取得
			ConsumptionTax bean = getSelected();

			if (bean == null) {
				return;
			}

			// 削除実行
			doDelete(bean);

			// 削除した行を一覧から削除
			mainView.tbList.removeSelectedRow();

			// 削除した後、一覧のレコードが0件の場合、メインボタン制御
			if (mainView.tbList.getRowCount() == 0) {
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

		if (mainView.tbList.getSelectedRow() == -1) {
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

			// 検索条件取得
			ConsumptionTaxSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlTaxRefRan.requestFocus();
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// エクセルデータの取得
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			// データが無かった場合、エラーメッセージ出力
			if (Util.isNullOrEmpty(data)) {
				showMessage(mainView, "I00022");
				return;
			}

			// エクセルタイトルセット
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02324") + ".xls");
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 編集画面作成
	 */
	protected void createEditView() {
		editView = new MG0130ConsumptionTaxMasterDialog(mainView.getParentFrame(), true);
		addEditViewEvent();
		addSubViewEvent();
	}

	/**
	 * 編集画面のイベント定義
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

		// 売上仕入区分変更処理
		editView.ctrlcboUsKbn.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					changedUseKbn();
				}
			}
		});

		// 非適格
		editView.ctrlNoInvReg.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				changedNoInvReg();
			}
		});

	}

	/**
	 * 編集画面[確定]ボタン押下
	 */
	protected void btnSettle_Click() {
		try {

			// 入力データチェック
			if (!isInputCorrect()) {
				return;
			}

			// 入力データ取得
			ConsumptionTax bean = getInputtedConsumptionTax();

			// 新規登録の場合
			switch (mode) {

				case NEW:
				case COPY:

					// 新規登録 - 複写登録
					request(getModelClass(), "entry", bean);

					// 追加分を一覧に反映
					mainView.tbList.addRow(getRowData(bean));

					// メインボタン制御
					setMainButtonEnabled(true);

					break;

				case MODIFY:

					boolean isTriMstUpdate = false;

					// 確認メッセージ表示
					if (isInvoice && chkTriMstNoInvRegTaxCode()) {

						if (!showConfirmMessage(FocusButton.NO, "I01108")) {
							return;
						}
						isTriMstUpdate = true;
					}

					// 修正
					request(getModelClass(), "modify", bean, isTriMstUpdate);

					// 修正分を一覧に反映
					mainView.tbList.modifySelectedRow(getRowData(bean));

					break;
			}

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
	 * 編集画面表示区分ラジオボタン押下時のイベント
	 */
	protected void addSubViewEvent() {

		// [消費税計算書使用しない]ボタン押下
		editView.rdoDisUse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (editView.rdoDisUse.isSelected()) {
					btnDU_Click();
				}
			}

		});

		// [消費税計算書使用する]ボタン押下
		editView.rdoUse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnU_Click();
			}
		});

	}

	/**
	 * 編集画面初期化
	 * 
	 * @param editMode
	 * @param bean
	 */
	protected void initEditView(Mode editMode, ConsumptionTax bean) {

		this.mode = editMode;

		switch (editMode) {

			case NEW:
				editView.setTitle(getWord("C01698"));
				editView.ctrlStrDate.setValue(editView.ctrlStrDate.getCalendar().getMinimumDate());
				editView.ctrlEndDate.setValue(editView.ctrlEndDate.getCalendar().getMaximumDate());
				btnDU_Click();

				if (!isInvoice) {
					editView.ctrlNoInvReg.setVisible(false);
					editView.ctrlTransitMeasure.setVisible(false);
					editView.lblTransitMeasure.setVisible(false);
				}
				changedNoInvReg();
				changedUseKbn();
				break;

			case MODIFY:
			case COPY:

				if (Mode.MODIFY == editMode) {

					editView.setTitle(getWord("C00977"));
					editView.ctrlTaxCode.setEditable(false);
					editView.setEditMode();

				} else {

					editView.setTitle(getWord("C01738"));

				}

				editView.ctrlTaxCode.setValue(bean.getCode());
				editView.ctrlTaxName.setValue(bean.getName());
				editView.ctrlTaxNameS.setValue(bean.getNames());
				editView.ctrlTaxNameK.setValue(bean.getNamek());
				// リストボックス（売上仕入区分）
				editView.ctrlcboUsKbn.setSelectedItemValue(bean.getTaxType());
				// ラジオボタン(消費税計算書)
				editView.numOutputOrder.setValue(bean.getOdr());

				if (bean.isTaxConsumption() == false) {
					editView.rdoDisUse.setSelected(true);
				} else if (bean.isTaxConsumption() == true) {
					editView.rdoUse.setSelected(true);
				}

				if (editView.rdoDisUse.isSelected()) {
					btnDU_Click();
				}
				if (editView.rdoUse.isSelected()) {
					btnU_Click();
					editView.numOutputOrder.setValue(bean.getOdr());
				}

				editView.numConsumptionTaxRate.setNumber(bean.getRate());
				editView.ctrlStrDate.setValue(bean.getDateFrom());
				editView.ctrlEndDate.setValue(bean.getDateTo());

				if (isInvoice) {
					editView.ctrlNoInvReg.setSelected(bean.isNO_INV_REG_FLG());
					editView.ctrlTransitMeasure.setNumber(bean.getKEKA_SOTI_RATE());
				} else {
					editView.ctrlNoInvReg.setVisible(false);
					editView.ctrlTransitMeasure.setVisible(false);
					editView.lblTransitMeasure.setVisible(false);
				}
				changedNoInvReg();
				changedUseKbn();
				break;
		}
	}

	/**
	 * 編集画面[使用しない]ボタン押下
	 */
	protected void btnDU_Click() {
		editView.numOutputOrder.setEditable(false);
		editView.numOutputOrder.clear();
	}

	/**
	 * 編集画面[使用する]ボタン押下
	 */
	protected void btnU_Click() {
		editView.numOutputOrder.setEditable(true);

	}

	/**
	 * 編集画面の入力値取得
	 * 
	 * @return Summary
	 */
	protected ConsumptionTax getInputtedConsumptionTax() {

		ConsumptionTax bean = new ConsumptionTax();

		bean.setCompanyCode(getCompanyCode());
		bean.setCode(editView.ctrlTaxCode.getValue());
		bean.setName(editView.ctrlTaxName.getValue());
		bean.setNames(editView.ctrlTaxNameS.getValue());
		bean.setNamek(editView.ctrlTaxNameK.getValue());
		// リストボックス（売上仕入区分）
		bean.setTaxType((TaxType) editView.ctrlcboUsKbn.getSelectedItemValue());
		// ラジオボタン(消費税計算書)
		if (editView.rdoDisUse.isSelected()) {
			bean.setTaxConsumption(false);
		} else if (editView.rdoUse.isSelected()) {
			bean.setTaxConsumption(true);
			bean.setOdr(editView.numOutputOrder.getValue());
		}
		bean.setRate(editView.numConsumptionTaxRate.getBigDecimal());// 消費税率
		bean.setDateFrom(editView.ctrlStrDate.getValue());
		bean.setDateTo(editView.ctrlEndDate.getValue());

		if (isInvoice) {
			bean.setNO_INV_REG_FLG(editView.ctrlNoInvReg.isSelected());
			bean.setKEKA_SOTI_RATE(editView.ctrlTransitMeasure.getBigDecimal()); // 経過措置控除可能率
		}

		return bean;
	}

	/**
	 * 検索条件取得
	 * 
	 * @return SummarySearchCondition 検索条件
	 */
	protected ConsumptionTaxSearchCondition getSearchCondition() {

		ConsumptionTaxSearchCondition condition = new ConsumptionTaxSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.ctrlTaxRefRan.getCodeFrom());
		condition.setCodeTo(mainView.ctrlTaxRefRan.getCodeTo());
		condition.setInvoiceFlg(isInvoice);
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;
	}

	/**
	 * 一覧に表示するデータをセット
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(ConsumptionTax bean) {

		List<Object> list = new ArrayList<Object>();

		list.add(bean.getCode()); // 消費税コード
		list.add(bean.getName()); // 消費税名称
		list.add(bean.getNames()); // 消費税略称
		list.add(bean.getNamek()); // 消費税検索名称
		list.add(getWord(TaxType.getName(bean.getTaxType()))); // 売上仕入区分
		list.add(bean.getRate()); // 消費税率
		list.add(bean.isTaxConsumption() ? bean.getOdr() : getWord("CLM0291")); // 消費税計算出力か（ボタン）
		list.add(bean.isNO_INV_REG_FLG() ? getWord("C12198") : ""); // 非適格請求書発行事業者
		list.add(DecimalUtil.isNullOrZero(bean.getKEKA_SOTI_RATE()) ? ""
			: NumberFormatUtil.formatNumber(bean.getKEKA_SOTI_RATE(), 0) + "%");
		list.add(DateUtil.toYMDString(bean.getDateFrom()));
		list.add(DateUtil.toYMDString(bean.getDateTo()));
		list.add(bean);

		return list;
	}

	/**
	 * @return 検索条件
	 */
	protected ConsumptionTaxSearchCondition createCondition() {
		return new ConsumptionTaxSearchCondition();
	}

	/**
	 * 一覧で選択したデータを取得
	 * 
	 * @return bean
	 * @exception Exception
	 */
	protected ConsumptionTax getSelected() throws Exception {

		ConsumptionTax bean = (ConsumptionTax) mainView.tbList
			.getSelectedRowValueAt(MG0130ConsumptionTaxMasterPanel.SC.ENTITY);

		ConsumptionTaxSearchCondition condition = new ConsumptionTaxSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(bean.getCode());

		List<ConsumptionTax> list = getList(condition);

		// メッセージ追加必要 ##########
		if (list == null || list.isEmpty()) {
			showMessage("I00193");// 選択されたデータは他ユーザーにより削除されました。
			return null;
		}
		return list.get(0);
	}

	/**
	 * 検索条件に該当するデータを返す
	 * 
	 * @param condition
	 * @return List
	 * @throws Exception
	 */
	protected List<ConsumptionTax> getList(ConsumptionTaxSearchCondition condition) throws Exception {
		List<ConsumptionTax> list = (List<ConsumptionTax>) request(getModelClass(), "get", condition);
		return list;
	}

	/**
	 * 指定行データを削除する
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void doDelete(ConsumptionTax bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * @return モデル
	 */
	protected Class getModelClass() {
		return ConsumptionTaxManager.class;
	}

	/**
	 * incoice用取引先マスタに登録されている非適格請求書発行事業者の消費税コードをクリアするかチェック
	 * 
	 * @return boolean
	 */
	protected boolean chkTriMstNoInvRegTaxCode() {

		if (isBeforeNoInvRegFlg && !editView.ctrlNoInvReg.isSelected()) {
			return true;
		}

		return false;
	}

	/**
	 * 売上仕入区分変更処理
	 */
	protected void changedUseKbn() {
		TaxType type = (TaxType) editView.ctrlcboUsKbn.getSelectedItemValue();

		if (TaxType.PURCHAESE == type) {
			editView.ctrlNoInvReg.setEnabled(true);
			editView.ctrlTransitMeasure.setEditable(editView.ctrlNoInvReg.isSelected());
		} else {
			editView.ctrlNoInvReg.setSelected(false);
			editView.ctrlNoInvReg.setEnabled(false);
			editView.ctrlTransitMeasure.clear();
			editView.ctrlTransitMeasure.setEditable(false);
		}
	}

	/**
	 * 非適格の場合に控除可能率入力可能
	 */
	protected void changedNoInvReg() {
		if (editView.ctrlNoInvReg.isSelected()) {
			editView.ctrlTransitMeasure.setEditable(true);

			if (Util.isNullOrEmpty(editView.ctrlTransitMeasure.getValue())) {
				editView.ctrlTransitMeasure.setNumber(100);
			}
		} else {
			editView.ctrlTransitMeasure.clear();
			editView.ctrlTransitMeasure.setEditable(false);
		}
	}

	/**
	 * 編集画面入力値の妥当性をチェック
	 * 
	 * @return bool - true:run, false:stop
	 * @exception Exception
	 */

	protected boolean isInputCorrect() throws Exception {

		if (Util.isNullOrEmpty(editView.ctrlTaxCode.getValue())) {
			showMessage(editView, "I00037", "C00573"); // 消費税コード
			editView.ctrlTaxCode.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlTaxName.getValue())) {
			showMessage(editView, "I00037", "C00774"); // 消費税名称
			editView.ctrlTaxName.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlTaxNameS.getValue())) {
			showMessage(editView, "I00037", "C00775"); // 消費税略称
			editView.ctrlTaxNameS.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlTaxNameK.getValue())) {
			showMessage(editView, "I00037", "C00828"); // 消費税検索名称
			editView.ctrlTaxNameK.requestTextFocus();
			return false;
		}

		if (editView.rdoUse.isSelected()) {

			if (Util.isNullOrEmpty(editView.numOutputOrder.getValue())) {
				showMessage(editView, "I00037", "C00776"); // 消費税計算出力順序
				editView.numOutputOrder.requestTextFocus();
				return false;
			}
		}

		if (!editView.rdoDisUse.isSelected()) {
			String value = editView.numOutputOrder.getValue();
			if (!Util.isNullOrEmpty(value)) {
				if (Integer.parseInt(value) < 1 || 99 < Integer.parseInt(value)) {
					showMessage(editView, "W00065", 1, 99);
					editView.numOutputOrder.requestFocus();
					return false;
				}
			}
		}

		if (Util.isNullOrEmpty(editView.numConsumptionTaxRate.getValue())) {
			showMessage(editView, "I00037", "C00777"); // 消費税率
			editView.numConsumptionTaxRate.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlStrDate.getValue())) {
			showMessage(editView, "I00037", "COP706"); // 開始年月日
			editView.ctrlStrDate.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00037", "COP707"); // 終了年月日
			editView.ctrlEndDate.requestTextFocus();
			return false;
		}

		// 開始年月日,終了年月日チェック
		if ((!Util.isNullOrEmpty(editView.ctrlStrDate.getValue())
			&& !Util.isNullOrEmpty(editView.ctrlEndDate.getValue()))
			&& !Util.isSmallerThenByYMD(editView.ctrlStrDate.getValue(), editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.ctrlStrDate.requestFocus();
			return false;
		}

		// 新規、複写の場合は同一会社コード&&同一社員コードが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			ConsumptionTaxSearchCondition condition = new ConsumptionTaxSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlTaxCode.getValue());

			List<ConsumptionTax> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00084", "C00697");
				editView.ctrlTaxCode.requestTextFocus();
				return false;
			}
		}

		if (isInvoice) {
			if (editView.ctrlTransitMeasure.isEditable()
				&& Util.isNullOrEmpty(editView.ctrlTransitMeasure.getValue())) {
				showMessage(editView, "I00037", "C12228"); // 経過措置可能控除率
				editView.ctrlTransitMeasure.requestTextFocus();
				return false;
			}
		}
		return true;
	}
}