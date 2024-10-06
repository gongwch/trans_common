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
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * 補助科目マスタコントローラ
 */
public class MG0090SubItemMasterPanelCtrl extends TController {

	/** 補助科目マスタ検索条件 */
	protected static final SubItemSearchCondition ItemSearchCondition = null;

	/** 指示画面 */
	protected MG0090SubItemMasterPanel mainView;

	/** 編集画面 */
	protected MG0090SubItemMasterDialog editView = null;

	/**
	 * 操作モード。現在操作中のモードを把握するために使用する。
	 */
	protected Mode mode = null;

	/**
	 * 操作モード。
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
		mainView = new MG0090SubItemMasterPanel(getCompany());
		addMainViewEvent();
	}

	/**
	 * 指示画面のボタン押下時のイベント
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

		// 科目コード検索欄に入力された場合に入力可となる。
		mainView.ctrlSerch.itemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !mainView.ctrlSerch.itemReference.isValueChanged()) {
					return;
				}

				itemReference_after();
			}

			private void itemReference_after() {
				Item item = mainView.ctrlSerch.itemReference.getEntity();

				if (item != null) {
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceFrom.clear();
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceTo.clear();
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceFrom.setEditable(true);
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceTo.setEditable(true);
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceFrom.getSearchCondition().setItemCode(
						item.getCode());
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceTo.getSearchCondition().setItemCode(
						item.getCode());
				} else {
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceFrom.clear();
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceTo.clear();
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceFrom.setEditable(false);
					mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceTo.setEditable(false);
				}
			}
		});
	}

	/**
	 * 指示画面を初期化する
	 */
	protected void initMainView() {
		// 見出し、集計にセット
		mainView.ctrlSerch.itemReference.getSearchCondition().setSumItem(true);
		mainView.ctrlSerch.itemReference.getSearchCondition().setTitleItem(true);
		// 補助科目ありかどうかを判別
		mainView.ctrlSerch.itemReference.getSearchCondition().setSubItem(true);

		mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceFrom.setEditable(false);
		mainView.ctrlSerch.subItemRange.ctrlSubItemReferenceTo.setEditable(false);
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

			// 開始コード<=終了コードにしてください。
			if (Util.isSmallerThen(mainView.ctrlSerch.subItemRange.getCodeFrom(),
				mainView.ctrlSerch.subItemRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlSerch.subItemRange.getFieldFrom().requestFocus();
				return;
			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 補助科目情報を取得
			SubItemSearchCondition condition = getSearchCondition();
			List<Item> list = getSubItem(condition);

			// 検索条件に該当する補助科目が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 補助科目情報を一覧に表示する
			for (Item subItem : list) {
				mainView.tbl.addRow(getRowData(subItem));
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

			// 編集対象の補助科目を取得する
			Item subItem = getSelectedSubItem();

			// 編集画面を生成し、編集対象の補助科目情報をセットする
			createEditView();
			initEditView(Mode.MODIFY, subItem);

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

			// 複写対象の補助科目を取得する
			Item subItem = getSelectedSubItem();

			// 複写画面を生成し、複写対象の補助科目情報をセットする
			createEditView();
			initEditView(Mode.COPY, subItem);

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

			// 削除対象の補助科目を取得する
			Item item = getSelectedSubItem();

			// 確認メッセージ
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除する
			deleteSubItem(item);

			// 削除した補助科目を一覧から削除
			mainView.tbl.removeSelectedRow();

			// 削除した結果0件になったらメインボタンを押下不可能にする
			if (mainView.tbl.getRowCount() == 0) {
				setMainButtonEnabled(false);
			}

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
			SubItemSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getSubItemExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C00488") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0090SubItemMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent();

		// 見出し、集計にセット
		editView.itemReference.getSearchCondition().setSumItem(true);
		editView.itemReference.getSearchCondition().setTitleItem(true);

		// 補助科目ありかどうかを判別
		editView.itemReference.getSearchCondition().setSubItem(true);
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
	 * @param bean 補助科目。修正、複写の場合は当該補助科目情報を編集画面にセットする。
	 */
	protected void initEditView(Mode mode_, Item bean) {

		this.mode = mode_;
		// 新規
		switch (mode) {
		// 新規
			case NEW:
				editView.setTitle(getWord("C01698"));
				editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
				editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());

				break;

			case COPY:
			case MODIFY:
				// 編集
				if (Mode.MODIFY == mode_) {
					editView.setTitle(getWord("C00977"));
					editView.itemReference.setEditable(false);
					editView.ctrlSubItemCode.setEditable(false);
					editView.setEditMode();
					// 複写
				} else {
					editView.setTitle(getWord("C01738"));
				}
				editView.itemReference.setCode(bean.getCode());
				editView.itemReference.name.setValue(bean.getNames());
				editView.itemReference.setEntity(bean);
				editView.ctrlSubItemCode.setValue(bean.getSubItem().getCode());
				editView.ctrlSubItemName.setValue(bean.getSubItemName());
				editView.ctrlSubItemNames.setValue(bean.getSubItemNames());
				editView.ctrlSubItemNamek.setValue(bean.getSubItemNamek());

				editView.ctrlFixDepartment.setCode(bean.getSubItem().getFixedDepartmentCode());
				editView.ctrlFixDepartment.name.setValue(bean.getSubItem().getFixedDepartmentName());

				editView.TTaxReference.setCode(bean.getSubItem().getConsumptionTax().getCode());
				editView.TTaxReference.name.setValue(bean.getSubItem().getConsumptionTax().getName());
				if (bean.getSubItem().hasDetailItem() == true) {
					editView.rdoYes.setSelected(true);
				} else {
					editView.rdoNo.setSelected(true);
				}
				editView.cmbtricodeflg.setSelectedItemValue(bean.getSubItem().getClientType());
				editView.chk.chkglflg1.setSelected(bean.getSubItem().isUseInputCashFlowSlip());
				editView.chk.chkglflg2.setSelected(bean.getSubItem().isUseOutputCashFlowSlip());
				editView.chk.chkglflg3.setSelected(bean.getSubItem().isUseTransferSlip());
				editView.chk.chkapflg1.setSelected(bean.getSubItem().isUseExpenseSettlementSlip());
				editView.chk.chkapflg2.setSelected(bean.getSubItem().isUsePaymentAppropriateSlip());
				editView.chk.chkarflg1.setSelected(bean.getSubItem().isUseReceivableAppropriateSlip());
				editView.chk.chkarflg2.setSelected(bean.getSubItem().isUseReceivableErasingSlip());
				editView.chk.chkfaflg1.setSelected(bean.getSubItem().isUseAssetsEntrySlip());
				editView.chk.chkfaflg2.setSelected(bean.getSubItem().isUsePaymentRequestSlip());
				editView.chk.chkempcodeflg.setSelected(bean.getSubItem().isUseEmployee());
				editView.chk.chkknrflg1.setSelected(bean.getSubItem().isUseManagement1());
				editView.chk.chkknrflg2.setSelected(bean.getSubItem().isUseManagement2());
				editView.chk.chkknrflg3.setSelected(bean.getSubItem().isUseManagement3());
				editView.chk.chkknrflg4.setSelected(bean.getSubItem().isUseManagement4());
				editView.chk.chkknrflg5.setSelected(bean.getSubItem().isUseManagement5());
				editView.chk.chkknrflg6.setSelected(bean.getSubItem().isUseManagement6());
				editView.chk.chkhmflg1.setSelected(bean.getSubItem().isUseNonAccount1());
				editView.chk.chkhmflg2.setSelected(bean.getSubItem().isUseNonAccount2());
				editView.chk.chkhmflg3.setSelected(bean.getSubItem().isUseNonAccount3());
				editView.chk.chkurizeiflg.setSelected(bean.getSubItem().isUseSalesTaxation());
				editView.chk.chksirzeiflg.setSelected(bean.getSubItem().isUsePurchaseTaxation());
				editView.chk.chkmcrflg.setSelected(bean.getSubItem().isUseForeignCurrency());
				editView.chk.chkexcflg.setSelected(bean.getSubItem().isDoesCurrencyEvaluate());
				editView.dtBeginDate.setValue(bean.getSubItem().getDateFrom());
				editView.dtEndDate.setValue(bean.getSubItem().getDateTo());

				// 発生日フラグ
				if (editView.chk.chkOccurDate.isVisible()) {
					editView.chk.chkOccurDate.setSelected(bean.getSubItem().isUseOccurDate());
				}
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

			// 入力された補助科目情報を取得
			Item item = getInputedSubItem();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entrySubItem", item);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(item));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modifySubItem", item);

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(item));

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
	 * Servletクラスを返す
	 * 
	 * @return ItemManager
	 */
	protected Class getModelClass() {
		return ItemManager.class;
	}

	/**
	 * 編集画面で入力された補助科目を返す
	 * 
	 * @return 編集画面で入力された補助科目
	 */
	protected Item getInputedSubItem() {
		Item item = createItem();
		SubItem subItem = createSubItem();
		subItem.setCompanyCode(getCompany().getCode());
		item.setCode(editView.itemReference.getCode());
		item.setName(editView.itemReference.getName());
		item.setNames(editView.itemReference.getNames());
		subItem.setCode(editView.ctrlSubItemCode.getValue());
		subItem.setName(editView.ctrlSubItemName.getValue());
		subItem.setNames(editView.ctrlSubItemNames.getValue());
		subItem.setNamek(editView.ctrlSubItemNamek.getValue());

		subItem.setFixedDepartmentCode(editView.ctrlFixDepartment.getCode());

		ConsumptionTax consumptionTax = createConsumptionTax();
		consumptionTax.setCode(editView.TTaxReference.getCode());
		subItem.setConsumptionTax(consumptionTax);
		if (editView.rdoNo.isSelected()) {
			subItem.setDetailItem(false);
		} else {
			subItem.setDetailItem(true);
		}
		subItem.setClientType(getCustomerType());
		subItem.setUseInputCashFlowSlip(editView.chk.chkglflg1.isSelected());
		subItem.setUseOutputCashFlowSlip(editView.chk.chkglflg2.isSelected());
		subItem.setUseTransferSlip(editView.chk.chkglflg3.isSelected());
		subItem.setUseExpenseSettlementSlip(editView.chk.chkapflg1.isSelected());
		subItem.setUsePaymentAppropriateSlip(editView.chk.chkapflg2.isSelected());
		subItem.setUseReceivableAppropriateSlip(editView.chk.chkarflg1.isSelected());
		subItem.setUseReceivableErasingSlip(editView.chk.chkarflg2.isSelected());
		subItem.setUseAssetsEntrySlip(editView.chk.chkfaflg1.isSelected());
		subItem.setUsePaymentRequestSlip(editView.chk.chkfaflg2.isSelected());
		subItem.setUseEmployee(editView.chk.chkempcodeflg.isSelected());
		subItem.setUseManagement1(editView.chk.chkknrflg1.isSelected());
		subItem.setUseManagement2(editView.chk.chkknrflg2.isSelected());
		subItem.setUseManagement3(editView.chk.chkknrflg3.isSelected());
		subItem.setUseManagement4(editView.chk.chkknrflg4.isSelected());
		subItem.setUseManagement5(editView.chk.chkknrflg5.isSelected());
		subItem.setUseManagement6(editView.chk.chkknrflg6.isSelected());
		subItem.setUseNonAccount1(editView.chk.chkhmflg1.isSelected());
		subItem.setUseNonAccount2(editView.chk.chkhmflg2.isSelected());
		subItem.setUseNonAccount3(editView.chk.chkhmflg3.isSelected());
		subItem.setUseSalesTaxation(editView.chk.chkurizeiflg.isSelected());
		subItem.setUsePurchaseTaxation(editView.chk.chksirzeiflg.isSelected());
		subItem.setUseForeignCurrency(editView.chk.chkmcrflg.isSelected());
		subItem.setDoesCurrencyEvaluate(editView.chk.chkexcflg.isSelected());
		subItem.setDateFrom(editView.dtBeginDate.getValue());
		subItem.setDateTo(editView.dtEndDate.getValue());

		// 発生日フラグ
		if (editView.chk.chkOccurDate.isVisible()) {
			subItem.setUseOccurDate(editView.chk.chkOccurDate.isSelected());
		} else {
			subItem.setUseOccurDate(true);
		}

		item.setSubItem(subItem);

		return item;

	}

	/**
	 * @return 科目
	 */
	protected Item createItem() {
		return new Item();
	}

	/**
	 * @return 補助科目
	 */
	protected SubItem createSubItem() {
		return new SubItem();
	}

	/**
	 * @return 消費税
	 */
	protected ConsumptionTax createConsumptionTax() {
		return new ConsumptionTax();
	}

	/**
	 * 取引先入力フラグを返す
	 * 
	 * @return selectedData
	 */
	protected CustomerType getCustomerType() {
		CustomerType selectedData = (CustomerType) editView.cmbtricodeflg.getSelectedItemValue();
		return selectedData;
	}

	/**
	 * 補助科目情報を一覧に表示する形式に変換し返す
	 * 
	 * @param bean 補助科目情報
	 * @return 一覧に表示する形式に変換された補助科目情報
	 */
	protected List<Object> getRowData(Item bean) {
		List<Object> list = new ArrayList<Object>();
		list.add(bean.getCode()); // 科目コード
		list.add(bean.getNames()); // 科目名称
		list.add(bean.getSubItem().getCode()); // 補助科目コード
		list.add(bean.getSubItemName()); // 補助科目名称
		list.add(bean.getSubItemNames()); // 補助科目略称
		list.add(bean.getSubItemNamek()); // 補助科目検索名称
		list.add(getUtiName(bean.getSubItem().hasDetailItem()));// 内訳区分
		list.add(bean.getSubItem().getFixedDepartmentCode()); // 固定部門ｺｰﾄﾞ
		list.add(bean.getSubItem().getConsumptionTax().getCode()); // 消費税コード
		list.add(getBoo(bean.getSubItem().isUseInputCashFlowSlip())); // 入金伝票入力フラグ
		list.add(getBoo(bean.getSubItem().isUseOutputCashFlowSlip())); // 出金伝票入力フラグ
		list.add(getBoo(bean.getSubItem().isUseTransferSlip())); // 振替伝票入力フラグ
		list.add(getBoo(bean.getSubItem().isUseExpenseSettlementSlip())); // 経費精算伝票入力フラグ
		list.add(getBoo(bean.getSubItem().isUsePaymentAppropriateSlip())); // 債務計上伝票入力フラグ
		list.add(getBoo(bean.getSubItem().isUseReceivableAppropriateSlip())); // 債権計上伝票入力フラグ
		list.add(getBoo(bean.getSubItem().isUseReceivableErasingSlip())); // 債権消込伝票入力フラグ
		list.add(getBoo(bean.getSubItem().isUseAssetsEntrySlip())); // 資産計上伝票入力フラグ
		list.add(getBoo(bean.getSubItem().isUsePaymentRequestSlip())); // 支払依頼伝票入力フラグ
		list.add(getWord(getName(bean.getSubItem().getClientType()))); // 取引先入力フラグ
		list.add(getBoo(bean.getSubItem().isUseEmployee())); // 社員入力フラグ
		list.add(getBoo(bean.getSubItem().isUseManagement1())); // 管理１入力フラグ
		list.add(getBoo(bean.getSubItem().isUseManagement2())); // 管理2入力フラグ
		list.add(getBoo(bean.getSubItem().isUseManagement3())); // 管理3入力フラグ
		list.add(getBoo(bean.getSubItem().isUseManagement4())); // 管理4入力フラグ
		list.add(getBoo(bean.getSubItem().isUseManagement5())); // 管理5入力フラグ
		list.add(getBoo(bean.getSubItem().isUseManagement6())); // 管理6入力フラグ
		list.add(getBoo(bean.getSubItem().isUseNonAccount1())); // 非会計１入力フラグ
		list.add(getBoo(bean.getSubItem().isUseNonAccount2())); // 非会計2入力フラグ
		list.add(getBoo(bean.getSubItem().isUseNonAccount3())); // 非会計3入力フラグ
		list.add(getBoo(bean.getSubItem().isUseSalesTaxation())); // 売上課税入力フラグ
		list.add(getBoo(bean.getSubItem().isUsePurchaseTaxation())); // 仕入課税入力フラグ
		list.add(getBoo(bean.getSubItem().isUseForeignCurrency())); // 多通貨入力フラグ
		list.add(getBoo2(bean.getSubItem().isDoesCurrencyEvaluate())); // 評価替対象フラグ
		list.add(getBoo(bean.getSubItem().isUseOccurDate())); // 発生日フラグ
		list.add(DateUtil.toYMDString(bean.getSubItem().getDateFrom())); // 開始年月日
		list.add(DateUtil.toYMDString(bean.getSubItem().getDateTo())); // 終了年月日

		return list;
	}

	/**
	 * BOOLEANをStringでを返す。管理科目を除くフラグの表示用。
	 * 
	 * @param castString
	 * @return string
	 */
	public String getBoo(boolean castString) {

		if (castString) {
			return getWord("C01276");// 入力可
		} else {
			return getWord("C01279");// 入力不可

		}
	}

	/**
	 * BOOLEANをStringでを返す。管理科目表示用。
	 * 
	 * @param castStringKnr
	 * @return string
	 */
	public String getBoo1(boolean castStringKnr) {

		if (castStringKnr) {
			return getWord("C02371");// 入力必須
		} else {
			return getWord("C01279");// 入力不可

		}
	}

	/**
	 * BOOLEANをStringでを返す。評価替表示用。
	 * 
	 * @param castStringExc
	 * @return string
	 */
	public String getBoo2(boolean castStringExc) {

		if (castStringExc) {
			return getWord("C02100");// する
		} else {
			return getWord("C02099");// しない

		}
	}

	/**
	 * @param customerType
	 * @return 得意先区分名称
	 */
	public static String getName(CustomerType customerType) {

		if (customerType == null) {
			return "";
		}

		switch (customerType) {
			case NONE:
				return "C01279";// 入力不可
			case CUSTOMER:
				return "C00401";// 得意先
			case VENDOR:
				return "C00203";// 仕入先
			case BOTH:
				return "C02122";// 得意先＆仕入先
			default:
				return "";
		}

	}

	/**
	 * @param hasDetailItem
	 * @return 内訳
	 */
	public String getUtiName(boolean hasDetailItem) {

		if (hasDetailItem) {
			return getWord("C02156");// 有
		} else {
			return getWord("C02155");// 無
		}
	}

	/**
	 * メインボタンの押下制御
	 * 
	 * @param mainEnabled enabled
	 */
	protected void setMainButtonEnabled(boolean mainEnabled) {
		mainView.btnModify.setEnabled(mainEnabled);
		mainView.btnCopy.setEnabled(mainEnabled);
		mainView.btnDelete.setEnabled(mainEnabled);
	}

	/**
	 * 検索条件に該当する補助科目のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する補助科目のリスト
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Item> getSubItem(SubItemSearchCondition condition) throws Exception {

		List<Item> list = (List<Item>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * 指示画面で入力された補助科目の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected SubItemSearchCondition getSearchCondition() {

		SubItemSearchCondition condition = new SubItemSearchCondition();
		condition.setSumItem(true);
		condition.setTitleItem(true);
		condition.setCompanyCode(getCompanyCode());
		condition.setItemCode(mainView.ctrlSerch.itemReference.getCode());
		condition.setCodeFrom(mainView.ctrlSerch.subItemRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlSerch.subItemRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}
		return condition;
	}

	/**
	 * 一覧で選択されている補助科目を返す
	 * 
	 * @return 一覧で選択されている補助科目
	 * @throws Exception
	 */
	protected Item getSelectedSubItem() throws Exception {
		SubItemSearchCondition condition = new SubItemSearchCondition();
		condition.setSumItem(true);
		condition.setTitleItem(true);
		condition.setCompanyCode(getCompany().getCode());
		condition.setItemCode((String) mainView.tbl.getSelectedRowValueAt(MG0090SubItemMasterPanel.SC.kmkcode));
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0090SubItemMasterPanel.SC.code));
		List<Item> list = getSubItem(condition);

		if (list == null || list.isEmpty()) {
			throw new TException("I00193");
		}
		return list.get(0);
	}

	/**
	 * 指定の補助科目を削除する
	 * 
	 * @param item
	 * @throws TException
	 */
	protected void deleteSubItem(Item item) throws TException {
		request(getModelClass(), "deleteSubItem", item);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// 科目コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.itemReference.getCode())) {
			showMessage(editView, "I00037", "C00572");
			editView.itemReference.requestTextFocus();
			return false;
		}

		// 補助科目コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlSubItemCode.getValue())) {
			showMessage(editView, "I00037", "C00602");
			editView.ctrlSubItemCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一補助科目が既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			SubItemSearchCondition condition = new SubItemSearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlSubItemCode.getValue());
			condition.setItemCode(editView.itemReference.getCode()); // 科目コードも条件

			List<Item> list = getSubItem(condition);
			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00488");
				editView.ctrlSubItemCode.requestTextFocus();
				return false;
			}
		}

		// 補助名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlSubItemName.getValue())) {
			showMessage(editView, "I00037", "C00934");
			editView.ctrlSubItemName.requestTextFocus();
			return false;
		}

		// 補助略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlSubItemNames.getValue())) {
			showMessage(editView, "I00037", "C00933");
			editView.ctrlSubItemNames.requestTextFocus();
			return false;
		}

		// 補助検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlSubItemNamek.getValue())) {
			showMessage(editView, "I00037", "C00935");
			editView.ctrlSubItemNamek.requestTextFocus();
			return false;
		}

		// 開始年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055");
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// 終了年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
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

		// 債務管理科目の場合、取引先区分は設定
		if (getItem() != null && getItem().getApType() == APType.DEBIT) {
			if (CustomerType.NONE == getCustomerType() || CustomerType.CUSTOMER == getCustomerType()) {
				// 債務管理科目を設定する場合、取引先区分には仕入先もしくは得意先&仕入先を設定してください。
				showMessage(editView, "I00613");
				editView.cmbtricodeflg.requestFocus();
				return false;
			}
		}
		// 債権管理科目の場合、取引先の入力は必須
		if (ARType.NOMAl != getItem().getArType()) {
			if (CustomerType.NONE == getCustomerType() || CustomerType.VENDOR == getCustomerType()) {
				// 債務管理科目を設定する場合、取引先区分には得意先もしくは得意先&仕入先を設定してください。
				showMessage(editView, "I00614");
				editView.cmbtricodeflg.requestFocus();
				return false;
			}
		}

		if (!Util.isNullOrEmpty(editView.TTaxReference.getCode())) {
			// 消費税情報を取得
			ConsumptionTax tax = getConsumptionTax();

			// 消費税コードと課税フラグの関連チェック
			if (tax.getTaxType() == TaxType.SALES && !editView.chk.chkurizeiflg.isSelected()
				|| tax.getTaxType() == TaxType.PURCHAESE && !editView.chk.chksirzeiflg.isSelected()) {
				showMessage(editView, "W00220");
				return false;
			}
		}
		return true;
	}

	/**
	 * 編集画面内部の科目情報を取得
	 * 
	 * @return 科目情報
	 */
	protected Item getItem() {
		Item bean = editView.itemReference.getEntity();
		if (bean == null) {
			editView.itemReference.refleshEntity();
			bean = editView.itemReference.getEntity();
		}
		return bean;
	}

	/**
	 * 消費税情報を取得
	 * 
	 * @return tax 消費税情報
	 * @throws Exception
	 */
	protected ConsumptionTax getConsumptionTax() throws Exception {

		ConsumptionTaxSearchCondition condition = new ConsumptionTaxSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setCode(editView.TTaxReference.getCode());

		List<ConsumptionTax> taxList = (List<ConsumptionTax>) request(ConsumptionTaxManager.class, "get", condition);

		return taxList.get(0);

	}
}
