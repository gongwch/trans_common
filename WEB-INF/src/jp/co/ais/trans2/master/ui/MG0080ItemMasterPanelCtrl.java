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
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * 科目マスタコントローラ
 */
public class MG0080ItemMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0080ItemMasterPanel mainView;

	/** 編集画面 */
	protected MG0080ItemMasterDialog editView = null;

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

	/**
	 * @see jp.co.ais.trans2.common.client.TController#start()
	 */
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

	/**
	 * @see jp.co.ais.trans2.common.client.TController#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MG0080ItemMasterPanel(getCompany());
		addMainViewEvent();
	}

	/**
	 * 指示画面ボタン押下時イベント
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
	 * 編集画面集計区分ラジオボタン押下時のイベント
	 */
	protected void addSubViewEvent() {
		// [入力]ボタン押下
		editView.rdoImput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (editView.rdoImput.isSelected()) {
					btnImput_Click();
				}
			}
		});

		// [集計]ボタン押下
		editView.rdoSum.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnSum_Click();
			}
		});

		// [見出]ボタン押下
		editView.rdoTitle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnSum_Click();
			}
		});

	}

	/**
	 * 指示画面を初期化する
	 */
	protected void initMainView() {
		// メインボタンを押下不可能にする
		setMainButtonEnabled(false);

		// 見出し、集計にセット
		mainView.itemRange.ctrlItemReferenceFrom.getSearchCondition().setSumItem(true);
		mainView.itemRange.ctrlItemReferenceFrom.getSearchCondition().setTitleItem(true);
		mainView.itemRange.ctrlItemReferenceTo.getSearchCondition().setSumItem(true);
		mainView.itemRange.ctrlItemReferenceTo.getSearchCondition().setTitleItem(true);

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
			if (Util.isSmallerThen(mainView.itemRange.getCodeFrom(), mainView.itemRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.itemRange.getFieldFrom().requestFocus();
				return;
			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 科目情報を取得
			ItemSearchCondition condition = getSearchCondition();
			List<Item> list = getItem(condition);

			// 検索条件に該当する科目が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 科目情報を一覧に表示する
			for (Item item : list) {
				mainView.tbl.addRow(getRowData(item));
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

			// 編集対象の科目を取得する
			Item item = getSelectedItem();

			// 編集画面を生成し、編集対象の科目情報をセットする
			createEditView();
			initEditView(Mode.MODIFY, item);

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

			// 複写対象の科目を取得する
			Item item = getSelectedItem();

			// 複写画面を生成し、複写対象の科目情報をセットする
			createEditView();
			initEditView(Mode.COPY, item);

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

			// 削除対象の科目を取得する
			Item item = getSelectedItem();

			// 確認メッセージ
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除する
			deleteItem(item);

			// 削除した科目を一覧から削除
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
			ItemSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02342") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 編集画面[集計][見出]ボタン押下
	 */
	protected void btnSum_Click() {

		editView.comHjokbn.setComboEnabled(false);
		editView.comHjokbn.setSelectedIndex(0);
		editView.ctrlFixDepartment.setEditable(false);
		editView.ctrlFixDepartment.clear();
		editView.ctrlTax.setEditable(false);
		editView.ctrlTax.clear();
		editView.cmbcntgl.setComboEnabled(false);
		editView.cmbcntgl.setSelectedIndex(0);
		editView.cmbcntap.setComboEnabled(false);
		editView.cmbcntap.setSelectedIndex(0);
		editView.cmbcntar.setComboEnabled(false);
		editView.cmbcntar.setSelectedIndex(0);
		editView.cmbcntbg.setComboEnabled(false);
		editView.cmbcntbg.setSelectedIndex(0);
		editView.cmbtricodeflg.setComboEnabled(false);
		editView.cmbtricodeflg.setSelectedIndex(0);
		editView.cmbcntsousai.setComboEnabled(false);
		editView.cmbcntsousai.setSelectedIndex(0);
		editView.cmbkesikbn.setComboEnabled(false);
		editView.cmbkesikbn.setSelectedIndex(0);
		editView.cmbexcflg.setComboEnabled(false);
		editView.cmbexcflg.setSelectedIndex(0);
		editView.chk.chkglflg1.setEnabled(false);
		editView.chk.chkglflg1.setSelected(false);
		editView.chk.chkglflg2.setEnabled(false);
		editView.chk.chkglflg2.setSelected(false);
		editView.chk.chkglflg3.setEnabled(false);
		editView.chk.chkglflg3.setSelected(false);
		editView.chk.chkapflg1.setEnabled(false);
		editView.chk.chkapflg1.setSelected(false);
		editView.chk.chkapflg2.setEnabled(false);
		editView.chk.chkapflg2.setSelected(false);
		editView.chk.chkarflg1.setEnabled(false);
		editView.chk.chkarflg1.setSelected(false);
		editView.chk.chkarflg2.setEnabled(false);
		editView.chk.chkarflg2.setSelected(false);
		editView.chk.chkfaflg1.setEnabled(false);
		editView.chk.chkfaflg1.setSelected(false);
		editView.chk.chkfaflg2.setEnabled(false);
		editView.chk.chkfaflg2.setSelected(false);
		editView.chk.chkmcrflg.setEnabled(false);
		editView.chk.chkmcrflg.setSelected(false);
		editView.chk.chkempcodeflg.setEnabled(false);
		editView.chk.chkempcodeflg.setSelected(false);
		editView.chk.chkknrflg1.setEnabled(false);
		editView.chk.chkknrflg1.setSelected(false);
		editView.chk.chkknrflg2.setEnabled(false);
		editView.chk.chkknrflg2.setSelected(false);
		editView.chk.chkknrflg3.setEnabled(false);
		editView.chk.chkknrflg3.setSelected(false);
		editView.chk.chkknrflg4.setEnabled(false);
		editView.chk.chkknrflg4.setSelected(false);
		editView.chk.chkknrflg5.setEnabled(false);
		editView.chk.chkknrflg5.setSelected(false);
		editView.chk.chkknrflg6.setEnabled(false);
		editView.chk.chkknrflg6.setSelected(false);
		editView.chk.chkhmflg1.setEnabled(false);
		editView.chk.chkhmflg1.setSelected(false);
		editView.chk.chkhmflg2.setEnabled(false);
		editView.chk.chkhmflg2.setSelected(false);
		editView.chk.chkhmflg3.setEnabled(false);
		editView.chk.chkhmflg3.setSelected(false);
		editView.chk.chkurizeiflg.setEnabled(false);
		editView.chk.chkurizeiflg.setSelected(false);
		editView.chk.chksirzeiflg.setEnabled(false);
		editView.chk.chksirzeiflg.setSelected(false);
		editView.chk.chkOccurDate.setEnabled(false);

	}

	/**
	 * 編集画面[入力]ボタン押下
	 */
	protected void btnImput_Click() {

		editView.comHjokbn.setComboEnabled(true);
		editView.comHjokbn.setSelectedIndex(1);
		editView.ctrlFixDepartment.setEditable(true);
		editView.ctrlTax.setEditable(true);
		editView.cmbcntgl.setComboEnabled(true);
		editView.cmbcntgl.setSelectedIndex(1);
		editView.cmbcntap.setComboEnabled(true);
		editView.cmbcntap.setSelectedIndex(1);
		editView.cmbcntar.setComboEnabled(true);
		editView.cmbcntar.setSelectedIndex(1);
		editView.cmbcntbg.setComboEnabled(true);
		editView.cmbcntbg.setSelectedIndex(1);
		editView.cmbtricodeflg.setComboEnabled(true);
		editView.cmbtricodeflg.setSelectedIndex(1);
		editView.cmbcntsousai.setComboEnabled(true);
		editView.cmbcntsousai.setSelectedIndex(1);
		editView.cmbkesikbn.setComboEnabled(true);
		editView.cmbkesikbn.setSelectedIndex(1);
		editView.cmbexcflg.setComboEnabled(true);
		editView.cmbexcflg.setSelectedIndex(1);
		editView.chk.chkglflg1.setEnabled(true);
		editView.chk.chkglflg2.setEnabled(true);
		editView.chk.chkglflg3.setEnabled(true);
		editView.chk.chkapflg1.setEnabled(true);
		editView.chk.chkapflg2.setEnabled(true);
		editView.chk.chkarflg1.setEnabled(true);
		editView.chk.chkarflg2.setEnabled(true);
		editView.chk.chkfaflg1.setEnabled(true);
		editView.chk.chkfaflg2.setEnabled(true);
		editView.chk.chkmcrflg.setEnabled(true);
		editView.chk.chkempcodeflg.setEnabled(true);
		editView.chk.chkknrflg1.setEnabled(true);
		editView.chk.chkknrflg2.setEnabled(true);
		editView.chk.chkknrflg3.setEnabled(true);
		editView.chk.chkknrflg4.setEnabled(true);
		editView.chk.chkknrflg5.setEnabled(true);
		editView.chk.chkknrflg6.setEnabled(true);
		editView.chk.chkhmflg1.setEnabled(true);
		editView.chk.chkhmflg2.setEnabled(true);
		editView.chk.chkhmflg3.setEnabled(true);
		editView.chk.chkurizeiflg.setEnabled(true);
		editView.chk.chksirzeiflg.setEnabled(true);
		editView.chk.chkOccurDate.setEnabled(true);

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0080ItemMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent();
		addSubViewEvent();
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

	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param mode_ 操作モード。
	 * @param bean 科目。修正、複写の場合は当該科目情報を編集画面にセットする。
	 */
	protected void initEditView(Mode mode_, Item bean) {

		this.mode = mode_;

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
					editView.ctrlItemCode.setEditable(false);
					editView.setEditMode();
					// 複写
				} else {
					editView.setTitle(getWord("C01738"));
				}
				if (ItemSumType.INPUT != bean.getItemSumType()) {
					btnSum_Click();
				}

				editView.ctrlItemCode.setValue(bean.getCode());
				editView.ctrlItemName.setValue(bean.getName());
				editView.ctrlItemNames.setValue(bean.getNames());
				editView.ctrlItemNamek.setValue(bean.getNamek());

				if (ItemSumType.INPUT == bean.getItemSumType()) {
					editView.rdoImput.setSelected(true);
				} else if (ItemSumType.SUM == bean.getItemSumType()) {
					editView.rdoSum.setSelected(true);
				} else {
					editView.rdoTitle.setSelected(true);
				}

				if (Dc.DEBIT == bean.getDc()) {
					editView.rdoDebit.setSelected(true);
				} else {
					editView.rdoCredit.setSelected(true);
				}

				editView.comKmkshu.setSelectedItemValue(bean.getItemType());

				if (bean.getItemSumType() == ItemSumType.INPUT) {
					editView.comHjokbn.setSelectedItemValue(bean.hasSubItem());
					editView.ctrlFixDepartment.setCode(bean.getFixedDepartmentCode());
					editView.ctrlFixDepartment.name.setValue(bean.getFixedDepartmentName());
					editView.ctrlTax.setCode(bean.getConsumptionTax().getCode());
					editView.ctrlTax.name.setValue(bean.getConsumptionTax().getName());
					editView.cmbcntgl.setSelectedItemValue(bean.getGlType());
					editView.cmbcntap.setSelectedItemValue(bean.getApType());
					editView.cmbcntar.setSelectedItemValue(bean.getArType());
					editView.cmbcntbg.setSelectedItemValue(bean.getBgType());
					editView.cmbtricodeflg.setSelectedItemValue(bean.getClientType());
					editView.cmbcntsousai.setSelectedItemValue(bean.isDoesOffsetItem());
					editView.cmbkesikbn.setSelectedItemValue(bean.isDoesBsOffset());
					editView.cmbexcflg.setSelectedItemValue(bean.getEvaluationMethod());
					editView.chk.chkglflg1.setSelected(bean.isUseInputCashFlowSlip());
					editView.chk.chkglflg2.setSelected(bean.isUseOutputCashFlowSlip());
					editView.chk.chkglflg3.setSelected(bean.isUseTransferSlip());
					editView.chk.chkapflg1.setSelected(bean.isUseExpenseSettlementSlip());
					editView.chk.chkapflg2.setSelected(bean.isUsePaymentAppropriateSlip());
					editView.chk.chkarflg1.setSelected(bean.isUseReceivableAppropriateSlip());
					editView.chk.chkarflg2.setSelected(bean.isUseReceivableErasingSlip());
					editView.chk.chkfaflg1.setSelected(bean.isUseAssetsEntrySlip());
					editView.chk.chkfaflg2.setSelected(bean.isUsePaymentRequestSlip());
					editView.chk.chkmcrflg.setSelected(bean.isUseForeignCurrency());
					editView.chk.chkempcodeflg.setSelected(bean.isUseEmployee());
					editView.chk.chkknrflg1.setSelected(bean.isUseManagement1());
					editView.chk.chkknrflg2.setSelected(bean.isUseManagement2());
					editView.chk.chkknrflg3.setSelected(bean.isUseManagement3());
					editView.chk.chkknrflg4.setSelected(bean.isUseManagement4());
					editView.chk.chkknrflg5.setSelected(bean.isUseManagement5());
					editView.chk.chkknrflg6.setSelected(bean.isUseManagement6());
					editView.chk.chkhmflg1.setSelected(bean.isUseNonAccount1());
					editView.chk.chkhmflg2.setSelected(bean.isUseNonAccount2());
					editView.chk.chkhmflg3.setSelected(bean.isUseNonAccount3());
					editView.chk.chkurizeiflg.setSelected(bean.isUseSalesTaxation());
					editView.chk.chksirzeiflg.setSelected(bean.isUsePurchaseTaxation());

					// 発生日フラグ
					if (editView.chk.chkOccurDate.isVisible()) {
						editView.chk.chkOccurDate.setSelected(bean.isUseOccurDate());
					}

				}

				editView.dtBeginDate.setValue(bean.getDateFrom());
				editView.dtEndDate.setValue(bean.getDateTo());
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

			// 入力された科目情報を取得
			Item item = getInputedItem();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entry", item);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(item));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", item);

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
	 * 編集画面で入力された科目を返す
	 * 
	 * @return 編集画面で入力された科目
	 */
	protected Item getInputedItem() {

		Item item = createItem();

		item.setCompanyCode(getCompany().getCode());
		item.setCode(editView.ctrlItemCode.getValue());
		item.setName(editView.ctrlItemName.getValue());
		item.setNames(editView.ctrlItemNames.getValue());
		item.setNamek(editView.ctrlItemNamek.getValue());
		item.setDateFrom(editView.dtBeginDate.getValue());
		item.setDateTo(editView.dtEndDate.getValue());

		if (editView.rdoImput.isSelected()) {
			item.setItemSumType(ItemSumType.INPUT);
		} else if (editView.rdoSum.isSelected()) {
			item.setItemSumType(ItemSumType.SUM);
		} else {
			item.setItemSumType(ItemSumType.TITLE);
		}

		item.setItemType(getKmkShu());

		if (editView.rdoCredit.isSelected()) {
			item.setDc(Dc.CREDIT);
		} else {
			item.setDc(Dc.DEBIT);
		}

		if (item.getItemSumType() == ItemSumType.INPUT) {

			if (editView.comHjokbn.getSelectedIndex() == 1) {
				item.setSubItem(false);
			} else {
				item.setSubItem(true);
			}
			ConsumptionTax consumptionTax = createConsumptionTax();
			consumptionTax.setCode(editView.ctrlTax.getCode());
			item.setConsumptionTax(consumptionTax);
			item.setUseEmployee(editView.chk.chkempcodeflg.isSelected());
			item.setUseManagement1(editView.chk.chkknrflg1.isSelected());
			item.setUseManagement2(editView.chk.chkknrflg2.isSelected());
			item.setUseManagement3(editView.chk.chkknrflg3.isSelected());
			item.setUseManagement4(editView.chk.chkknrflg4.isSelected());
			item.setUseManagement5(editView.chk.chkknrflg5.isSelected());
			item.setUseManagement6(editView.chk.chkknrflg6.isSelected());
			item.setUseNonAccount1(editView.chk.chkhmflg1.isSelected());
			item.setUseNonAccount2(editView.chk.chkhmflg2.isSelected());
			item.setUseNonAccount3(editView.chk.chkhmflg3.isSelected());
			item.setUseSalesTaxation(editView.chk.chkurizeiflg.isSelected());
			item.setUsePurchaseTaxation(editView.chk.chksirzeiflg.isSelected());
			item.setUseForeignCurrency(editView.chk.chkmcrflg.isSelected());
			item.setEvaluationMethod(getEvaluationMethod());
			item.setFixedDepartmentCode(editView.ctrlFixDepartment.getCode());
			item.setGlType(getGl());
			item.setApType(getAp());
			item.setArType(getAr());
			item.setBgType(getBg());
			item.setDoesOffsetItem(isDoesOffsetItem());
			item.setDoesBsOffset(isDoesBsOffset());
			item.setClientType(getCustomerType());
			item.setUseInputCashFlowSlip(editView.chk.chkglflg1.isSelected());
			item.setUseOutputCashFlowSlip(editView.chk.chkglflg2.isSelected());
			item.setUseTransferSlip(editView.chk.chkglflg3.isSelected());
			item.setUseExpenseSettlementSlip(editView.chk.chkapflg1.isSelected());
			item.setUsePaymentAppropriateSlip(editView.chk.chkapflg2.isSelected());
			item.setUseReceivableAppropriateSlip(editView.chk.chkarflg1.isSelected());
			item.setUseReceivableErasingSlip(editView.chk.chkarflg2.isSelected());
			item.setUseAssetsEntrySlip(editView.chk.chkfaflg1.isSelected());
			item.setUsePaymentRequestSlip(editView.chk.chkfaflg2.isSelected());

			// 発生日フラグ
			if (editView.chk.chkOccurDate.isVisible()) {
				item.setUseOccurDate(editView.chk.chkOccurDate.isSelected());
			} else {
				item.setUseOccurDate(true);
			}

		}

		return item;

	}

	/**
	 * @return 消費税マスタ検索条件
	 */
	protected ConsumptionTaxSearchCondition createConsumptionTaxSearchCondition() {
		return new ConsumptionTaxSearchCondition();
	}

	/**
	 * @return 科目マスタ検索条件
	 */
	protected ItemSearchCondition createItemSearchCondition() {
		return new ItemSearchCondition();
	}

	/**
	 * @return 消費税
	 */
	protected ConsumptionTax createConsumptionTax() {
		return new ConsumptionTax();
	}

	/**
	 * @return 科目
	 */
	protected Item createItem() {
		return new Item();
	}

	/**
	 * 科目種別を返す
	 * 
	 * @return selectedData
	 */
	protected ItemType getKmkShu() {
		ItemType selectedData = (ItemType) editView.comKmkshu.getSelectedItemValue();

		return selectedData;
	}

	/**
	 * GL区分を返す
	 * 
	 * @return selectedData
	 */
	protected GLType getGl() {
		GLType selectedData = (GLType) editView.cmbcntgl.getSelectedItemValue();

		return selectedData;
	}

	/**
	 * AP区分を返す
	 * 
	 * @return selectedData
	 */
	protected APType getAp() {
		if (Util.isNullOrEmpty(editView.cmbcntap.getSelectedItemValue())) {
			return APType.NOMAL;
		}
		APType selectedData = (APType) editView.cmbcntap.getSelectedItemValue();
		return selectedData;
	}

	/**
	 * AR区分を返す
	 * 
	 * @return selectedData
	 */
	protected ARType getAr() {
		if (Util.isNullOrEmpty(editView.cmbcntar.getSelectedItemValue())) {
			return ARType.NOMAl;
		}
		ARType selectedData = (ARType) editView.cmbcntar.getSelectedItemValue();
		return selectedData;
	}

	/**
	 * BG区分を返す
	 * 
	 * @return selectedData
	 */
	protected BGType getBg() {
		BGType selectedData = (BGType) editView.cmbcntbg.getSelectedItemValue();
		return selectedData;
	}

	/**
	 * 取引先入力フラグを返す
	 * 
	 * @return selectedData
	 */
	protected CustomerType getCustomerType() {
		if (Util.isNullOrEmpty(editView.cmbtricodeflg.getSelectedItemValue())) {
			return CustomerType.NONE;
		}
		CustomerType selectedData = (CustomerType) editView.cmbtricodeflg.getSelectedItemValue();
		return selectedData;
	}

	/**
	 * 評価替対象フラグを返す
	 * 
	 * @return selectedData
	 */
	protected EvaluationMethod getEvaluationMethod() {
		EvaluationMethod selectedData = (EvaluationMethod) editView.cmbexcflg.getSelectedItemValue();
		return selectedData;
	}

	/**
	 * 相殺科目制御区分を返す
	 * 
	 * @return selectedData
	 */
	protected Boolean isDoesOffsetItem() {
		Boolean selectedData = (Boolean) editView.cmbcntsousai.getComboBox().getSelectedItemValue();

		return selectedData;
	}

	/**
	 * BS勘定消込区分を返す
	 * 
	 * @return selectedData
	 */
	protected Boolean isDoesBsOffset() {
		Boolean selectedData = (Boolean) editView.cmbkesikbn.getComboBox().getSelectedItemValue();

		return selectedData;
	}

	/**
	 * 科目情報を一覧に表示する形式に変換し返す
	 * 
	 * @param item 科目情報
	 * @return 一覧に表示する形式に変換された科目情報
	 */
	protected List<Object> getRowData(Item item) {
		List<Object> list = new ArrayList<Object>();

		list.add(item.getCode()); // 科目コード
		list.add(item.getName()); // 科目名称
		list.add(item.getNames()); // 科目略称
		list.add(item.getNamek()); // 科目検索名称
		list.add(getWord(ItemSumType.getName(item.getItemSumType()))); // 集計区分
		list.add(getWord(ItemType.getName(item.getItemType()))); // 科目種別
		list.add(getWord(Dc.getName(item.getDc()))); // 貸借区分

		if (item.getItemSumType() == ItemSumType.INPUT) {
			list.add(item.hasSubItem() ? getWord("C00006") : getWord("C00412")); // 補助区分
			list.add(item.getFixedDepartmentCode()); // 固定部門ｺｰﾄﾞ
			list.add(item.getConsumptionTax().getCode());// 消費税コード
			list.add(getWord(GLType.getName(item.getGlType()))); // GL科目制御区分
			list.add(getWord(APType.getName(item.getApType()))); // AP科目制御区分
			list.add(getWord(ARType.getName(item.getArType()))); // AR科目制御区分
			list.add(getWord(BGType.getName(item.getBgType()))); // BG科目制御区分
			list.add(getWord((CustomerType.getName(item.getClientType())))); // 取引先入力フラグ
			list.add(getWord(getDivisionName(item.isDoesOffsetItem()))); // 相殺科目制御区分
			list.add(getWord(getDivisionName(item.isDoesBsOffset()))); // BS勘定消込区分
			list.add(getWord(EvaluationMethod.getName(item.getEvaluationMethod()))); // 評価替対象フラグ
			list.add(getWord(getBoo(item.isUseInputCashFlowSlip()))); // 入金伝票入力フラグ
			list.add(getWord(getBoo(item.isUseOutputCashFlowSlip()))); // 出金伝票入力フラグ
			list.add(getWord(getBoo(item.isUseTransferSlip()))); // 振替伝票入力フラグ
			list.add(getWord(getBoo(item.isUseExpenseSettlementSlip()))); // 経費精算伝票入力フラグ
			list.add(getWord(getBoo(item.isUsePaymentAppropriateSlip()))); // 債務計上伝票入力フラグ
			list.add(getWord(getBoo(item.isUseReceivableAppropriateSlip()))); // 債権計上伝票入力フラグ
			list.add(getWord(getBoo(item.isUseReceivableErasingSlip()))); // 債権消込伝票入力フラグ
			list.add(getWord(getBoo(item.isUseAssetsEntrySlip()))); // 資産計上伝票入力フラグ
			list.add(getWord(getBoo(item.isUsePaymentRequestSlip()))); // 支払依頼伝票入力フラグ
			list.add(getWord(getBoo(item.isUseForeignCurrency()))); // 多通貨入力フラグ
			list.add(getWord(getBoo1(item.isUseEmployee()))); // 社員入力フラグ
			list.add(getWord(getBoo1(item.isUseManagement1()))); // 管理１入力フラグ
			list.add(getWord(getBoo1(item.isUseManagement2()))); // 管理2入力フラグ
			list.add(getWord(getBoo1(item.isUseManagement3()))); // 管理3入力フラグ
			list.add(getWord(getBoo1(item.isUseManagement4()))); // 管理4入力フラグ
			list.add(getWord(getBoo1(item.isUseManagement5()))); // 管理5入力フラグ
			list.add(getWord(getBoo1(item.isUseManagement6()))); // 管理6入力フラグ
			list.add(getWord(getBoo(item.isUseNonAccount1()))); // 非会計１入力フラグ
			list.add(getWord(getBoo(item.isUseNonAccount2()))); // 非会計2入力フラグ
			list.add(getWord(getBoo(item.isUseNonAccount3()))); // 非会計3入力フラグ
			list.add(getWord(getBoo(item.isUseSalesTaxation()))); // 売上課税入力フラグ
			list.add(getWord(getBoo(item.isUsePurchaseTaxation()))); // 仕入課税入力フラグ
			list.add(getWord(getBoo(item.isUseOccurDate()))); // 発生日フラグ
			list.add(DateUtil.toYMDString(item.getDateFrom())); // 開始年月日
			list.add(DateUtil.toYMDString(item.getDateTo())); // 終了年月日
		} else {
			list.add(""); // 補助区分
			list.add(""); // 固定部門ｺｰﾄﾞ
			list.add(""); // 消費税コード
			list.add(""); // GL科目制御区分
			list.add(""); // AP科目制御区分
			list.add(""); // AR科目制御区分
			list.add(""); // BG科目制御区分
			list.add(""); // 取引先入力フラグ
			list.add(""); // 相殺科目制御区分
			list.add(""); // BS勘定消込区分
			list.add(""); // 評価替対象フラグ
			list.add(""); // 入金伝票入力フラグ
			list.add(""); // 出金伝票入力フラグ
			list.add(""); // 振替伝票入力フラグ
			list.add(""); // 経費精算伝票入力フラグ
			list.add(""); // 債務計上伝票入力フラグ
			list.add(""); // 債権計上伝票入力フラグ
			list.add(""); // 債権消込伝票入力フラグ
			list.add(""); // 資産計上伝票入力フラグ
			list.add(""); // 支払依頼伝票入力フラグ
			list.add(""); // 多通貨入力フラグ
			list.add(""); // 社員入力フラグ
			list.add(""); // 管理１入力フラグ
			list.add(""); // 管理2入力フラグ
			list.add(""); // 管理3入力フラグ
			list.add(""); // 管理4入力フラグ
			list.add(""); // 管理5入力フラグ
			list.add(""); // 管理6入力フラグ
			list.add(""); // 非会計１入力フラグ
			list.add(""); // 非会計2入力フラグ
			list.add(""); // 非会計3入力フラグ
			list.add(""); // 売上課税入力フラグ
			list.add(""); // 仕入課税入力フラグ
			list.add(""); // 発生日フラグ
			list.add(DateUtil.toYMDString(item.getDateFrom())); // 開始年月日
			list.add(DateUtil.toYMDString(item.getDateTo())); // 終了年月日

		}

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
			return "C01276"; // 入力可
		} else {
			return "C01279"; // 入力不可
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
			return "C02371"; // 入力必須
		} else {
			return "C01279"; // 入力不可
		}
	}

	/**
	 * 名称を返す
	 * 
	 * @param bool
	 * @return String
	 */
	public static String getDivisionName(boolean bool) {

		if (bool) {
			return "C02100"; // する
		} else {
			return "C02099"; // しない
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
	 * 検索条件に該当する科目のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する科目のリスト
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Item> getItem(ItemSearchCondition condition) throws Exception {

		List<Item> list = (List<Item>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * 指示画面で入力された科目の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected ItemSearchCondition getSearchCondition() {

		ItemSearchCondition condition = createItemSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.itemRange.getCodeFrom());
		condition.setCodeTo(mainView.itemRange.getCodeTo());
		condition.setSumItem(true);
		condition.setTitleItem(true);
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}
		return condition;

	}

	/**
	 * 一覧で選択されている科目を返す
	 * 
	 * @return 一覧で選択されている科目
	 * @throws Exception
	 */
	protected Item getSelectedItem() throws Exception {

		ItemSearchCondition condition = createItemSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0080ItemMasterPanel.SC.code));
		condition.setSumItem(true);
		condition.setTitleItem(true);
		List<Item> list = getItem(condition);

		if (list == null || list.isEmpty()) {
			throw new TException("I00193"); // 選択されたデータは他ユーザーにより削除されました。
		}
		return list.get(0);
	}

	/**
	 * 指定の科目を削除する
	 * 
	 * @param item 科目
	 * @throws TException
	 */
	protected void deleteItem(Item item) throws TException {
		request(getModelClass(), "delete", item);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// 科目コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlItemCode.getValue())) {
			showMessage(editView, "I00037", "C00572"); // 科目コードを入力してください。
			editView.ctrlItemCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一科目が既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			ItemSearchCondition condition = createItemSearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlItemCode.getValue());
			condition.setSumItem(true);
			condition.setTitleItem(true);

			List<Item> list = getItem(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00077"); // 指定の科目は既に存在します。
				editView.ctrlItemCode.requestTextFocus();
				return false;
			}
		}

		// 科目名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlItemName.getValue())) {
			showMessage(editView, "I00037", "C00700"); // 科目名称を入力してください。
			editView.ctrlItemName.requestTextFocus();
			return false;
		}

		// 科目略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlItemNames.getValue())) {
			showMessage(editView, "I00037", "C00730"); // 科目略称を入力してください。
			editView.ctrlItemNames.requestTextFocus();
			return false;
		}

		// 科目検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlItemNamek.getValue())) {
			showMessage(editView, "I00037", "C00731"); // 科目検索名称を入力してください。
			editView.ctrlItemNamek.requestTextFocus();
			return false;
		}

		// 科目種別が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.comKmkshu.getSelectedItemValue())) {
			showMessage(editView, "I00037", "C01007"); // 科目種別を入力してください。
			editView.comKmkshu.requestTextFocus();
			return false;
		}

		// 集計区分の入力ボタンが入力されている場合
		if (editView.rdoImput.isSelected()) {

			// 補助区分が未入力の場合エラー
			if (Util.isNullOrEmpty(editView.comHjokbn.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C01314"); // 補助区分を入力してください。
				editView.comHjokbn.requestTextFocus();
				return false;
			}

			// GLが未入力の場合エラー
			if (Util.isNullOrEmpty(editView.cmbcntgl.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C00968"); // GL科目制御区分を入力してください。
				editView.cmbcntgl.requestTextFocus();
				return false;
			}

			// APが未入力の場合エラー
			if (Util.isNullOrEmpty(editView.cmbcntap.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C00959"); // AP科目制御区分を入力してください。
				editView.cmbcntap.requestTextFocus();
				return false;
			}

			// ARが未入力の場合エラー
			if (Util.isNullOrEmpty(editView.cmbcntar.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C00960"); // AR科目制御区分を入力してください。
				editView.cmbcntar.requestTextFocus();
				return false;
			}

			// BGが未入力の場合エラー
			if (Util.isNullOrEmpty(editView.cmbcntbg.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C00962"); // BG科目制御区分を入力してください。
				editView.cmbcntbg.requestTextFocus();
				return false;
			}

			// 取引先入力フラグが未入力の場合エラー
			if (Util.isNullOrEmpty(editView.cmbtricodeflg.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C01134"); // 取引先入力フラグを入力してください。
				editView.cmbtricodeflg.requestTextFocus();
				return false;
			}

			// 相殺科目制御区分が未入力の場合エラー
			if (Util.isNullOrEmpty(editView.cmbcntsousai.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C01217"); // 相殺科目制御区分を入力してください。
				editView.cmbcntsousai.requestTextFocus();
				return false;
			}

			// BS勘定消込区分が未入力の場合エラー
			if (Util.isNullOrEmpty(editView.cmbkesikbn.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C02078"); // BS勘定消込区分を入力してください。
				editView.cmbkesikbn.requestTextFocus();
				return false;
			}

			// 評価替対象フラグが未入力の場合エラー
			if (Util.isNullOrEmpty(editView.cmbexcflg.getSelectedItemValue())) {
				showMessage(editView, "I00037", "C01301"); // 評価替対象フラグを入力してください。
				editView.cmbexcflg.requestTextFocus();
				return false;
			}
		}

		// 開始年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055"); // 開始年月日を入力してください。
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// 終了年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261"); // 終了年月日を入力してください。
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		// 開始年月日,終了年月日チェック
		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}

		// 債務管理科目の場合、取引先の入力は必須
		if (APType.DEBIT == getAp()) {
			if (CustomerType.NONE == getCustomerType() || CustomerType.CUSTOMER == getCustomerType()) {
				// 債務管理科目を設定する場合、取引先区分には仕入先もしくは得意先&仕入先を設定してください。
				showMessage(editView, "I00613");
				editView.cmbtricodeflg.requestFocus();
				return false;
			}
		}
		// 債権管理科目の場合、取引先の入力は必須
		if (ARType.NOMAl != getAr()) {
			if (CustomerType.NONE == getCustomerType() || CustomerType.VENDOR == getCustomerType()) {
				// 債務管理科目を設定する場合、取引先区分には得意先もしくは得意先&仕入先を設定してください。
				showMessage(editView, "I00614");
				editView.cmbtricodeflg.requestFocus();
				return false;
			}
		}

		if (!Util.isNullOrEmpty(editView.ctrlTax.getCode())) {
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
	 * 消費税情報を取得
	 * 
	 * @return tax 消費税情報
	 * @throws Exception
	 */
	protected ConsumptionTax getConsumptionTax() throws Exception {

		ConsumptionTaxSearchCondition condition = createConsumptionTaxSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setCode(editView.ctrlTax.getCode());

		List<ConsumptionTax> taxList = (List<ConsumptionTax>) request(ConsumptionTaxManager.class, "get", condition);

		return taxList.get(0);

	}

}