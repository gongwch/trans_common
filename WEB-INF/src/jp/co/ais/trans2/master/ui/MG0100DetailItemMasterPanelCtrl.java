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
 * 内訳科目マスタコントローラ
 */
public class MG0100DetailItemMasterPanelCtrl extends TController {

	/** 内訳科目マスタ検索条件 */
	protected static final DetailItemSearchCondition ItemSearchCondition = null;

	/** 指示画面 */
	protected MG0100DetailItemMasterPanel mainView;

	/** 編集画面 */
	protected MG0100DetailItemMasterDialog editView = null;

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
		mainView = new MG0100DetailItemMasterPanel(getCompany());
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

	}

	/**
	 * 指示画面を初期化する
	 */
	protected void initMainView() {
		// 補助科目、内訳科目を含むかの取得
		mainView.ctrlSerch.itemReference.getSearchCondition().setSubItem(true);
		mainView.ctrlSerch.subItemReference.getSearchCondition().setDetailItem(true);

		// 補助科目、内訳科目の使用可否
		mainView.ctrlSerch.subItemReference.setEditable(false);
		mainView.ctrlSerch.detailItemRange.ctrlDetailItemReferenceFrom.setEditable(false);
		mainView.ctrlSerch.detailItemRange.ctrlDetailItemReferenceTo.setEditable(false);

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
			if (Util.isSmallerThen(mainView.ctrlSerch.detailItemRange.getCodeFrom(),
				mainView.ctrlSerch.detailItemRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlSerch.detailItemRange.getFieldFrom().requestFocus();
				return;
			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 内訳科目情報を取得
			DetailItemSearchCondition condition = getSearchCondition();
			List<Item> list = getDetailItem(condition);

			// 検索条件に該当する内訳科目が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 内訳科目情報を一覧に表示する
			for (Item detailItem : list) {
				mainView.tbl.addRow(getRowData(detailItem));
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

			// 編集対象の内訳科目を取得する
			Item detailItem = getSelectedDetailItem();

			// 編集画面を生成し、編集対象の内訳科目情報をセットする
			createEditView();
			initEditView(Mode.MODIFY, detailItem);

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

			// 複写対象の内訳科目を取得する
			Item detailItem = getSelectedDetailItem();

			// 複写画面を生成し、複写対象の内訳科目情報をセットする
			createEditView();
			initEditView(Mode.COPY, detailItem);

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

			// 削除対象の内訳科目を取得する
			Item item = getSelectedDetailItem();

			// 確認メッセージ
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除する
			deleteDetailItem(item);

			// 削除した内訳科目を一覧から削除
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
			DetailItemSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getDetailItemExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C00025") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0100DetailItemMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent();

		// 見出し、集計にセット
		editView.ctrlItem.getSearchCondition().setSumItem(true);
		editView.ctrlItem.getSearchCondition().setTitleItem(true);

		editView.ctrlSubItem.getSearchCondition().setSumItem(true);
		editView.ctrlSubItem.getSearchCondition().setTitleItem(true);

		// 補助、内訳科目ありかどうかを判別
		editView.ctrlItem.getSearchCondition().setSubItem(true);
		editView.ctrlSubItem.getSearchCondition().setDetailItem(true);
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

		// 科目選択
		editView.ctrlItem.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !editView.ctrlItem.isValueChanged()) {
					return;
				}

				ctrlItemReference_after();
			}
		});

	}

	/**
	 * [科目選択]時の処理
	 */
	protected void ctrlItemReference_after() {
		Item entity = editView.ctrlItem.getEntity();
		if (entity != null) {
			editView.ctrlSubItem.clear();
			editView.ctrlSubItem.getSearchCondition().setItemCode(entity.getCode());
			editView.ctrlSubItem.setEditable(true);
		} else {
			editView.ctrlSubItem.clear();
			editView.ctrlSubItem.setEditable(false);
		}
	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param mode_ 操作モード。
	 * @param bean 内訳科目。修正、複写の場合は当該内訳科目情報を編集画面にセットする。
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
				editView.ctrlSubItem.setEditable(false);

				break;

			case COPY:
			case MODIFY:
				// 編集
				if (Mode.MODIFY == mode_) {
					editView.setTitle(getWord("C00977"));
					editView.ctrlItem.setEditable(false);
					editView.ctrlSubItem.setEditable(false);
					editView.ctrlDetailItemCode.setEditable(false);
					editView.setEditMode();
					// 複写
				} else {
					editView.setTitle(getWord("C01738"));
				}
				editView.ctrlItem.setCode(bean.getCode());
				editView.ctrlItem.name.setValue(bean.getNames());
				editView.ctrlItem.setEntity(bean);
				editView.ctrlSubItem.setCode(bean.getSubItemCode());
				editView.ctrlSubItem.getSearchCondition().setItemCode(bean.getCode());
				editView.ctrlSubItem.name.setValue(bean.getSubItemNames());
				editView.ctrlDetailItemCode.setValue(bean.getDetailItem().getCode());
				editView.ctrlDetailItemName.setValue(bean.getDetailItemName());
				editView.ctrlDetailItemNames.setValue(bean.getDetailItemNames());
				editView.ctrlDetailItemNamek.setValue(bean.getDetailItemNamek());
				editView.TTaxReference.setCode(bean.getDetailItem().getConsumptionTax().getCode());
				editView.TTaxReference.name.setValue(bean.getDetailItem().getConsumptionTax().getName());
				editView.cmbtricodeflg.setSelectedItemValue(bean.getDetailItem().getClientType());
				editView.chk.chkglflg1.setSelected(bean.getDetailItem().isUseInputCashFlowSlip());
				editView.chk.chkglflg2.setSelected(bean.getDetailItem().isUseOutputCashFlowSlip());
				editView.chk.chkglflg3.setSelected(bean.getDetailItem().isUseTransferSlip());
				editView.chk.chkapflg1.setSelected(bean.getDetailItem().isUseExpenseSettlementSlip());
				editView.chk.chkapflg2.setSelected(bean.getDetailItem().isUsePaymentAppropriateSlip());
				editView.chk.chkarflg1.setSelected(bean.getDetailItem().isUseReceivableAppropriateSlip());
				editView.chk.chkarflg2.setSelected(bean.getDetailItem().isUseReceivableErasingSlip());
				editView.chk.chkfaflg1.setSelected(bean.getDetailItem().isUseAssetsEntrySlip());
				editView.chk.chkfaflg2.setSelected(bean.getDetailItem().isUsePaymentRequestSlip());
				editView.chk.chkempcodeflg.setSelected(bean.getDetailItem().isUseEmployee());
				editView.chk.chkknrflg1.setSelected(bean.getDetailItem().isUseManagement1());
				editView.chk.chkknrflg2.setSelected(bean.getDetailItem().isUseManagement2());
				editView.chk.chkknrflg3.setSelected(bean.getDetailItem().isUseManagement3());
				editView.chk.chkknrflg4.setSelected(bean.getDetailItem().isUseManagement4());
				editView.chk.chkknrflg5.setSelected(bean.getDetailItem().isUseManagement5());
				editView.chk.chkknrflg6.setSelected(bean.getDetailItem().isUseManagement6());
				editView.chk.chkhmflg1.setSelected(bean.getDetailItem().isUseNonAccount1());
				editView.chk.chkhmflg2.setSelected(bean.getDetailItem().isUseNonAccount2());
				editView.chk.chkhmflg3.setSelected(bean.getDetailItem().isUseNonAccount3());
				editView.chk.chkurizeiflg.setSelected(bean.getDetailItem().isUseSalesTaxation());
				editView.chk.chksirzeiflg.setSelected(bean.getDetailItem().isUsePurchaseTaxation());
				editView.chk.chkmcrflg.setSelected(bean.getDetailItem().isUseForeignCurrency());
				editView.chk.chkexcflg.setSelected(bean.getDetailItem().isDoesCurrencyEvaluate());
				editView.dtBeginDate.setValue(bean.getDetailItem().getDateFrom());
				editView.dtEndDate.setValue(bean.getDetailItem().getDateTo());

				// 発生日フラグ
				if (editView.chk.chkOccurDate.isVisible()) {
					editView.chk.chkOccurDate.setSelected(bean.getDetailItem().isUseOccurDate());
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

			// 入力された内訳科目情報を取得
			Item item = getInputedDetailItem();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entryDetailItem", item);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(item));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modifyDetailItem", item);

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
	 * 編集画面で入力された内訳科目を返す
	 * 
	 * @return 編集画面で入力された内訳科目
	 */
	protected Item getInputedDetailItem() {
		Item item = new Item();
		SubItem subItem = new SubItem();
		DetailItem detailItem = new DetailItem();
		detailItem.setCompanyCode(getCompany().getCode());
		item.setCode(editView.ctrlItem.getCode());
		item.setName(editView.ctrlItem.getName());
		item.setNames(editView.ctrlItem.getNames());
		subItem.setCode(editView.ctrlSubItem.getCode());
		subItem.setName(editView.ctrlSubItem.getName());
		subItem.setNames(editView.ctrlSubItem.getNames());

		item.setSubItem(subItem);

		detailItem.setCode(editView.ctrlDetailItemCode.getValue());
		detailItem.setName(editView.ctrlDetailItemName.getValue());
		detailItem.setNames(editView.ctrlDetailItemNames.getValue());
		detailItem.setNamek(editView.ctrlDetailItemNamek.getValue());
		ConsumptionTax consumptionTax = new ConsumptionTax();
		consumptionTax.setCode(editView.TTaxReference.getCode());
		detailItem.setConsumptionTax(consumptionTax);
		detailItem.setClientType(getCustomerType());
		detailItem.setUseInputCashFlowSlip(editView.chk.chkglflg1.isSelected());
		detailItem.setUseOutputCashFlowSlip(editView.chk.chkglflg2.isSelected());
		detailItem.setUseTransferSlip(editView.chk.chkglflg3.isSelected());
		detailItem.setUseExpenseSettlementSlip(editView.chk.chkapflg1.isSelected());
		detailItem.setUsePaymentAppropriateSlip(editView.chk.chkapflg2.isSelected());
		detailItem.setUseReceivableAppropriateSlip(editView.chk.chkarflg1.isSelected());
		detailItem.setUseReceivableErasingSlip(editView.chk.chkarflg2.isSelected());
		detailItem.setUseAssetsEntrySlip(editView.chk.chkfaflg1.isSelected());
		detailItem.setUsePaymentRequestSlip(editView.chk.chkfaflg2.isSelected());
		detailItem.setUseEmployee(editView.chk.chkempcodeflg.isSelected());
		detailItem.setUseManagement1(editView.chk.chkknrflg1.isSelected());
		detailItem.setUseManagement2(editView.chk.chkknrflg2.isSelected());
		detailItem.setUseManagement3(editView.chk.chkknrflg3.isSelected());
		detailItem.setUseManagement4(editView.chk.chkknrflg4.isSelected());
		detailItem.setUseManagement5(editView.chk.chkknrflg5.isSelected());
		detailItem.setUseManagement6(editView.chk.chkknrflg6.isSelected());
		detailItem.setUseNonAccount1(editView.chk.chkhmflg1.isSelected());
		detailItem.setUseNonAccount2(editView.chk.chkhmflg2.isSelected());
		detailItem.setUseNonAccount3(editView.chk.chkhmflg3.isSelected());
		detailItem.setUseSalesTaxation(editView.chk.chkurizeiflg.isSelected());
		detailItem.setUsePurchaseTaxation(editView.chk.chksirzeiflg.isSelected());
		detailItem.setUseForeignCurrency(editView.chk.chkmcrflg.isSelected());
		detailItem.setDoesCurrencyEvaluate(editView.chk.chkexcflg.isSelected());
		detailItem.setDateFrom(editView.dtBeginDate.getValue());
		detailItem.setDateTo(editView.dtEndDate.getValue());

		// 発生日フラグ
		if (editView.chk.chkOccurDate.isVisible()) {
			detailItem.setUseOccurDate(editView.chk.chkOccurDate.isSelected());
		} else {
			detailItem.setUseOccurDate(true);
		}

		item.getSubItem().setDetailItem(detailItem);

		return item;

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
	 * 内訳科目情報を一覧に表示する形式に変換し返す
	 * 
	 * @param bean 内訳科目情報
	 * @return 一覧に表示する形式に変換された内訳科目情報
	 */
	protected List<Object> getRowData(Item bean) {
		List<Object> list = new ArrayList<Object>();
		list.add(bean.getCode()); // 科目コード
		list.add(bean.getNames()); // 科目名称
		list.add(bean.getSubItemCode()); // 補助科目コード
		list.add(bean.getSubItemNames()); // 補助科目名称
		list.add(bean.getDetailItem().getCode()); // 内訳科目コード
		list.add(bean.getDetailItemName()); // 内訳科目名称
		list.add(bean.getDetailItemNames()); // 内訳科目略称
		list.add(bean.getDetailItemNamek()); // 内訳科目検索名称
		list.add(bean.getDetailItem().getConsumptionTax().getCode()); // 消費税コード
		list.add(getBoo(bean.getDetailItem().isUseInputCashFlowSlip())); // 入金伝票入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseOutputCashFlowSlip())); // 出金伝票入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseTransferSlip())); // 振替伝票入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseExpenseSettlementSlip())); // 経費精算伝票入力フラグ
		list.add(getBoo(bean.getDetailItem().isUsePaymentAppropriateSlip())); // 債務計上伝票入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseReceivableAppropriateSlip())); // 債権計上伝票入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseReceivableErasingSlip())); // 債権消込伝票入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseAssetsEntrySlip())); // 資産計上伝票入力フラグ
		list.add(getBoo(bean.getDetailItem().isUsePaymentRequestSlip())); // 支払依頼伝票入力フラグ
		list.add(getWord(getName(bean.getDetailItem().getClientType()))); // 取引先入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseEmployee())); // 社員入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseManagement1())); // 管理１入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseManagement2())); // 管理2入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseManagement3())); // 管理3入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseManagement4())); // 管理4入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseManagement5())); // 管理5入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseManagement6())); // 管理6入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseNonAccount1())); // 非会計１入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseNonAccount2())); // 非会計2入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseNonAccount3())); // 非会計3入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseSalesTaxation())); // 売上課税入力フラグ
		list.add(getBoo(bean.getDetailItem().isUsePurchaseTaxation())); // 仕入課税入力フラグ
		list.add(getBoo(bean.getDetailItem().isUseForeignCurrency())); // 多通貨入力フラグ
		list.add(getBoo2(bean.getDetailItem().isDoesCurrencyEvaluate())); // 評価替対象フラグ
		list.add(getBoo(bean.getDetailItem().isUseOccurDate())); // 発生日フラグ
		list.add(DateUtil.toYMDString(bean.getDetailItem().getDateFrom())); // 開始年月日
		list.add(DateUtil.toYMDString(bean.getDetailItem().getDateTo())); // 終了年月日

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
	 * 検索条件に該当する内訳科目のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する内訳科目のリスト
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Item> getDetailItem(DetailItemSearchCondition condition) throws Exception {

		List<Item> list = (List<Item>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * 指示画面で入力された内訳科目の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected DetailItemSearchCondition getSearchCondition() {

		DetailItemSearchCondition condition = new DetailItemSearchCondition();
		condition.setSubItem(true);
		condition.setDetailItem(true);
		condition.setCompanyCode(getCompanyCode());
		condition.setItemCode(mainView.ctrlSerch.itemReference.getCode());
		condition.setSubItemCode(mainView.ctrlSerch.subItemReference.getCode());
		condition.setCodeFrom(mainView.ctrlSerch.detailItemRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlSerch.detailItemRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}
		return condition;
	}

	/**
	 * 一覧で選択されている内訳科目を返す
	 * 
	 * @return 一覧で選択されている内訳科目
	 * @throws Exception
	 */
	protected Item getSelectedDetailItem() throws Exception {
		DetailItemSearchCondition condition = new DetailItemSearchCondition();
		condition.setSumItem(true);
		condition.setTitleItem(true);
		condition.setCompanyCode(getCompany().getCode());
		condition.setItemCode((String) mainView.tbl.getSelectedRowValueAt(MG0100DetailItemMasterPanel.SC.kmkcode));
		condition.setSubItemCode((String) mainView.tbl.getSelectedRowValueAt(MG0100DetailItemMasterPanel.SC.hkmcode));
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0100DetailItemMasterPanel.SC.code));
		List<Item> list = getDetailItem(condition);

		if (list == null || list.isEmpty()) {
			throw new TException("I00193");
		}
		return list.get(0);
	}

	/**
	 * 指定の内訳科目を削除する
	 * 
	 * @param item
	 * @throws TException
	 */
	protected void deleteDetailItem(Item item) throws TException {
		request(getModelClass(), "deleteDetailItem", item);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// 科目コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlItem.getCode())) {
			showMessage(editView, "I00037", "C00572");
			editView.ctrlItem.requestTextFocus();
			return false;
		}

		// 補助科目コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlSubItem.getCode())) {
			showMessage(editView, "I00037", "C00602");
			editView.ctrlSubItem.requestTextFocus();
			return false;
		}

		// 内訳科目コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDetailItemCode.getValue())) {
			showMessage(editView, "I00037", "C00603");
			editView.ctrlDetailItemCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一内訳科目が既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			DetailItemSearchCondition condition = new DetailItemSearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlDetailItemCode.getValue());
			condition.setItemCode(editView.ctrlItem.getCode()); // 科目コードも条件
			condition.setSubItemCode(editView.ctrlSubItem.getCode()); // 補助科目コードも条件

			List<Item> list = getDetailItem(condition);
			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00025");
				editView.ctrlDetailItemCode.requestTextFocus();
				return false;
			}
		}

		// 内訳名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDetailItemName.getValue())) {
			showMessage(editView, "I00037", "C00877");
			editView.ctrlDetailItemName.requestTextFocus();
			return false;
		}

		// 内訳略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDetailItemNames.getValue())) {
			showMessage(editView, "I00037", "C00878");
			editView.ctrlDetailItemNames.requestTextFocus();
			return false;
		}

		// 内訳検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDetailItemNamek.getValue())) {
			showMessage(editView, "I00037", "C00879");
			editView.ctrlDetailItemNamek.requestTextFocus();
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

		// 債務管理科目の場合、取引先区分は設定必須
		if (getItem() != null && getItem().getApType() == APType.DEBIT) {
			if (CustomerType.NONE == getCustomerType() || CustomerType.CUSTOMER == getCustomerType()) {
				// 債務管理科目を設定する場合、取引先区分には仕入先もしくは得意先&仕入先を設定してください。
				showMessage(editView, "I00613");
				editView.cmbtricodeflg.requestFocus();
				return false;
			}
		}
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
		Item bean = editView.ctrlItem.getEntity();
		if (bean == null) {
			editView.ctrlItem.refleshEntity();
			bean = editView.ctrlItem.getEntity();
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
