package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.bank.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 銀行口座マスタ画面コントロール
 * 
 * @author AIS
 */
public class MP0030BankAccountMasterPanelCtrl extends TController {

	/** パネル */
	protected MP0030BankAccountMasterPanel mainView;

	/** 編集画面 */
	protected MP0030BankAccountMasterDialog editView;

	/** 修正前のデータ */
	protected BankAccount editBean = null;

	/** 操作モード。現在操作中のモードを把握するために使用する。 */
	protected Mode mode = null;

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

			// 指示画面を初期化する
			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MP0030BankAccountMasterPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面のイベント定義。
	 */
	protected void addMainViewEvent() {

		mainView.btnNew.addActionListener(new ActionListener() {

			// 新規ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				btnNew_Click();
			}
		});

		mainView.btnSearch.addActionListener(new ActionListener() {

			// 検索ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				btnSearch_Click();
			}
		});

		mainView.btnModify.addActionListener(new ActionListener() {

			// 編集ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				btnModify_Click();
			}
		});

		mainView.btnCopy.addActionListener(new ActionListener() {

			// 複写ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				btnCopy_Click();
			}
		});

		mainView.btnDelete.addActionListener(new ActionListener() {

			// 削除ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				btnDelete_Click();
			}
		});

		mainView.btnListOutput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// リスト出力ボタンを押下
				btnListOutput();
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
	 * パネル取得
	 * 
	 * @return 通貨マスタパネル
	 */
	@Override
	public TPanelBusiness getPanel() {
		// パネルを返す
		return mainView;
	}

	/**
	 * 新規登録処理
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
	 * 検索処理
	 */
	protected void btnSearch_Click() {

		try {

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 開始コード<=終了コードにしてください。
			if (Util.isSmallerThen(mainView.ctrlRange.getCodeFrom(), mainView.ctrlRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlRange.getFieldFrom().requestFocus();
				return;
			}

			// 銀行口座情報を取得
			BankAccountSearchCondition condition = getSearchCondition();
			List<BankAccount> list = getList(condition);

			// 検索条件に該当する銀行口座が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 銀行口座情報を一覧に表示する
			for (BankAccount bean : list) {
				mainView.tbl.addRow(getRowData(bean));
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

			// 編集対象の銀行口座を取得する
			BankAccount bean = getSelectedRowData();

			// 編集画面を生成し、編集対象の銀行口座情報をセットする
			createEditView();
			initEditView(Mode.MODIFY, bean);

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

			// 複写対象の銀行口座を取得する
			BankAccount bean = getSelectedRowData();

			// 編集前データを削除
			editBean = null;

			// 複写画面を生成し、複写対象の銀行口座情報をセットする
			createEditView();
			initEditView(Mode.COPY, bean);

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

			// 削除対象の銀行口座を取得する
			BankAccount bean = getSelectedRowData();

			// 削除する
			doDelete(bean);

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
	 * エクセルリスト出力
	 */
	protected void btnListOutput() {

		try {

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// エクセルデータの取得
			BankAccountSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			// 銀行口座マスタ
			printer.preview(data, getWord("C02322") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MP0030BankAccountMasterDialog(getCompany(), mainView.getParentFrame(), true);

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

			private void ctrlBankReference_after() {
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
	 * 編集画面[確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {

			// 入力チェックを行う。
			if (!isInputRight()) {
				return;
			}

			// 入力された銀行口座情報を取得
			BankAccount bean = getInputedData();
			BankAccountSearchCondition condition = createBankAccountSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(bean.getCode());

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entry", bean);
				bean = getList(condition).get(0);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(bean));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", bean);
				bean = getList(condition).get(0);

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(bean));

			}

			// 編集画面を閉じる
			btnClose_Click();

			// 完了メッセージ
			showMessage(mainView.getParentFrame(), "I00013");

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
	protected boolean isInputRight() throws Exception {

		// 銀行口座コードチェック
		if (Util.isNullOrEmpty(editView.ctrlBankAccount.getValue())) {
			// エラーメッセージ出力
			showMessage("I00002", "C00857");
			editView.ctrlBankAccount.requestFocus();
			return false;
		}

		// 新規、複写の場合は同一銀行口座コードが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {

			BankAccountSearchCondition condition = createBankAccountSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlBankAccount.getValue());

			List<BankAccount> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00055", "C01879");
				editView.ctrlBankAccount.requestTextFocus();
				return false;
			}
		}

		// 銀行口座名称
		if (Util.isNullOrEmpty(editView.txtBankAccountName.getValue())) {
			// エラーメッセージ出力
			super.showMessage(editView, "I00002", "C02145");
			editView.txtBankAccountName.requestFocus();
			return false;
		}

		// 通貨チェック
		if (Util.isNullOrEmpty(editView.ctrlCurrency.getCode())) {
			// エラーメッセージ出力
			super.showMessage(editView, "I00002", "C00371");
			editView.ctrlCurrency.requestTextFocus();
			return false;
		}

		// 銀行チェック
		if (Util.isNullOrEmpty(editView.ctrlBank.getCode())) {
			// エラーメッセージ出力
			super.showMessage(editView, "I00002", "C00124");
			editView.ctrlBank.requestTextFocus();
			return false;
		}

		// 支店チェック
		if (Util.isNullOrEmpty(editView.ctrlBranch.getCode())) {
			// エラーメッセージ出力
			super.showMessage(editView, "I00002", "C00222");
			editView.ctrlBranch.requestTextFocus();
			return false;
		}

		// 振込依頼人コードチェック
		if (Util.isNullOrEmpty(editView.ctrlTransferRequesterCode.getValue())) {
			// エラーメッセージ出力
			super.showMessage(editView, "I00002", "C00858");
			editView.ctrlTransferRequesterCode.requestFocus();
			return false;
		}

		// 振込依頼人名の設定
		if (Util.isNullOrEmpty(editView.ctrlTransferRequesterKanaName.getValue())) {
			// エラーメッセージ出力
			super.showMessage(editView, "I00002", "C00859");
			editView.ctrlTransferRequesterKanaName.requestFocus();
			return false;
		}

		// 口座番号チェック
		if (Util.isNullOrEmpty(editView.ctrlAccountNumber.getValue())) {
			// エラーメッセージ出力
			super.showMessage(editView, "I00002", "C00794");
			editView.ctrlAccountNumber.requestFocus();
			return false;
		}

		// 預金種目チェック
		if (Util.isNullOrEmpty(editView.ctrlDepositType.getSelectedDepositKind())) {
			// エラーメッセージ出力
			super.showMessage(editView, "I00002", "C01326");
			editView.ctrlDepositType.requestFocus();
			return false;
		}

		// 計上部門チェック
		if (Util.isNullOrEmpty(editView.ctrlAppropriateDepartment.getCode())) {
			// エラーメッセージ出力
			super.showMessage(editView, "I00002", "C00863");
			editView.ctrlAppropriateDepartment.requestTextFocus();
			return false;
		}

		// 科目チェック
		if (Util.isNullOrEmpty(editView.ctrlItemUnit.ctrlItemReference.getCode())) {
			// エラーメッセージ出力
			super.showMessage(editView, "I00002", "C00572");
			editView.ctrlItemUnit.ctrlItemReference.requestTextFocus();
			return false;
		}

		// 補助チェック
		if (editView.ctrlItemUnit.ctrlSubItemReference.isEditable()
			&& Util.isNullOrEmpty(editView.ctrlItemUnit.ctrlSubItemReference.getCode())) {
			// エラーメッセージ出力
			super.showMessage(editView, "I00002", "C00602");
			editView.ctrlItemUnit.ctrlSubItemReference.requestTextFocus();
			return false;
		}

		// 内訳チェック
		if (editView.ctrlItemUnit.ctrlDetailItemReference.isEditable()
			&& Util.isNullOrEmpty(editView.ctrlItemUnit.ctrlDetailItemReference.getCode())) {
			showMessage(editView, "I00002", "C00603");
			editView.ctrlItemUnit.ctrlDetailItemReference.requestTextFocus();
			return false;
		}

		// 開始年月日
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "W00034", "C00055");
			editView.dtBeginDate.getCalendar().requestFocus();
			return false;
		}

		// 終了年月日
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			showMessage(editView, "W00034", "C00261");
			editView.dtEndDate.getCalendar().requestFocus();
			return false;
		}

		// 開始年月日＜＝終了年月日にしてください
		if (Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue()) == false) {
			editView.dtBeginDate.getCalendar().requestFocus();
			showMessage(editView, "I00067", "");
			return false;
		}

		return true;

	}

	/**
	 * 編集画面で入力された銀行口座を返す
	 * 
	 * @return 編集画面で入力された銀行口座
	 */
	protected BankAccount getInputedData() {

		BankAccount bean = createBankAccount();
		bean.setCompanyCode(getCompanyCode());
		// 銀行口座コードの設定
		bean.setCode(editView.ctrlBankAccount.getValue());
		// 銀行口座名称の設定
		bean.setName(editView.txtBankAccountName.getText());
		// 通貨コードの設定
		bean.setCurrencyCode(editView.ctrlCurrency.getCode());
		// 銀行コードの設定
		bean.setBankCode(editView.ctrlBank.getCode());
		// 支店コードの設定
		bean.setBranchCode(editView.ctrlBranch.getCode());
		// 振込依頼人コードの設定
		bean.setClientCode(editView.ctrlTransferRequesterCode.getValue());
		// 振込依頼人名の設定
		bean.setClientName(editView.ctrlTransferRequesterKanaName.getValue());
		// 振込依頼人名（漢字）の設定
		bean.setClientNameJ(editView.ctrlTransferRequesterKanjiName.getValue());
		// 振込依頼人名（英字）の設定
		bean.setClientNameE(editView.ctrlTransferRequesterEnglishName.getValue());
		// 預金種目の設定
		bean.setDepositKind(editView.ctrlDepositType.getSelectedDepositKind());
		// 口座番号の設定
		bean.setAccountNo(editView.ctrlAccountNumber.getValue());
		// 社員ＦＢ区分の設定
		bean.setUseEmployeePayment(editView.rdoEmployeeFBUse.isSelected());
		// 社外ＦＢ区分の設定
		bean.setUseExPayment(editView.rdoExternalFBUse.isSelected());
		// 計上部門コードの設定
		bean.setDepartmentCode(editView.ctrlAppropriateDepartment.getCode());
		// 科目コードの設定
		bean.setItemCode(editView.ctrlItemUnit.ctrlItemReference.getCode());
		// 補助科目コードの設定
		bean.setSubItemCode(editView.ctrlItemUnit.ctrlSubItemReference.getCode());
		// 内訳科目コードの設定
		bean.setDetailItemCode(editView.ctrlItemUnit.ctrlDetailItemReference.getCode());
		// 開始年月日の設定
		bean.setDateFrom(editView.dtBeginDate.getValue());
		// 終了年月日の設定
		bean.setDateTo(editView.dtEndDate.getValue());

		return bean;
	}

	/**
	 * 編集画面[戻る]ボタン押下
	 */
	protected void btnClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param editMode 操作モード。
	 * @param bean
	 */
	protected void initEditView(Mode editMode, BankAccount bean) {

		this.mode = editMode;

		// 新規の場合
		if (Mode.NEW == editMode) {

			// 新規画面
			editView.setTitle(getWord("C01698"));
			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());
			editView.ctrlBranch.setEditable(false);

			editBean = null;// 編集前データを削除

			// 編集、複写の場合
		} else if (Mode.MODIFY == editMode || Mode.COPY == editMode) {

			// 編集
			if (Mode.MODIFY == editMode) {
				// 編集画面
				editView.setTitle(getWord("C00977"));
				editView.ctrlBankAccount.setEditable(false);
				editView.setEditMode();
				editBean = bean;// 編集前データを保持
				// 複写
			} else {
				// 複写画面
				editView.setTitle(getWord("C01738"));
				editBean = null;// 編集前データを削除
			}

			setEditDialog(bean);

		}

	}

	/**
	 * 画面に銀行口座情報をセットする
	 * 
	 * @param bean
	 */
	protected void setEditDialog(BankAccount bean) {
		editView.ctrlBankAccount.setValue(bean.getCode());
		editView.txtBankAccountName.setValue(bean.getName());
		editView.ctrlCurrency.code.setValue(bean.getCurrencyCode());
		editView.ctrlBank.code.setValue(bean.getBankCode());
		editView.ctrlBank.name.setValue(bean.getBankName());
		editView.ctrlBranch.code.setValue(bean.getBranchCode());
		editView.ctrlBranch.name.setValue(bean.getBranchName());
		editView.ctrlBranch.getSearchCondition().setBankCode(bean.getBankCode());
		editView.ctrlTransferRequesterCode.setValue(bean.getClientCode());
		editView.ctrlTransferRequesterKanaName.setValue(bean.getClientName());
		editView.ctrlTransferRequesterKanjiName.setValue(bean.getClientNameJ());
		editView.ctrlTransferRequesterEnglishName.setValue(bean.getClientNameE());
		editView.ctrlAccountNumber.setValue(bean.getAccountNo());
		editView.ctrlDepositType.setSelectedDepositKind(bean.getDepositKind());
		editView.rdoEmployeeFBUse.setSelected(bean.isUseEmployeePayment());
		editView.rdoExternalFBUse.setSelected(bean.isUseExPayment());
		editView.ctrlAppropriateDepartment.code.setValue(bean.getDepartmentCode());
		editView.ctrlAppropriateDepartment.name.setValue(bean.getDepartmentNames());
		Item item = new Item();
		item.setCode(bean.getItemCode());
		item.setNames(bean.getItemNames());
		if (!Util.isNullOrEmpty(bean.getSubItemCode())) {
			SubItem subItem = new SubItem();
			subItem.setCode(bean.getSubItemCode());
			subItem.setNames(bean.getSubItemNames());

			if (!Util.isNullOrEmpty(bean.getDetailItemCode())) {
				DetailItem detailItem = new DetailItem();
				detailItem.setCode(bean.getDetailItemCode());
				detailItem.setNames(bean.getDetailItemNames());
				subItem.setDetailItem(detailItem);
			}
			item.setSubItem(subItem);
		}
		editView.ctrlItemUnit.setEntity(item);

		editView.dtBeginDate.setValue(bean.getDateFrom());
		editView.dtEndDate.setValue(bean.getDateTo());
	}

	/**
	 * 一覧で選択されている銀行口座を返す
	 * 
	 * @return 一覧で選択されている銀行口座
	 * @throws Exception
	 */
	protected BankAccount getSelectedRowData() throws Exception {

		BankAccountSearchCondition condition = createBankAccountSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MP0030BankAccountMasterPanel.SC.code));

		List<BankAccount> list = getList(condition);

		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました。
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * 指定の銀行口座を削除する
	 * 
	 * @param bean マスタ
	 * @throws Exception
	 */
	protected void doDelete(BankAccount bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * ボタン(新規、削除、編集、複写、リスト出力)ロック
	 * 
	 * @param boo
	 */
	protected void lockBtn(boolean boo) {
		mainView.btnDelete.setEnabled(boo);
		mainView.btnModify.setEnabled(boo);
		mainView.btnCopy.setEnabled(boo);
		mainView.btnListOutput.setEnabled(boo);

	}

	/**
	 * 指示画面で入力された銀行口座の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected BankAccountSearchCondition getSearchCondition() {

		BankAccountSearchCondition condition = createBankAccountSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setCodeFrom(mainView.ctrlRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * 検索条件に該当するリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当するリスト
	 * @throws Exception
	 */
	protected List<BankAccount> getList(BankAccountSearchCondition condition) throws Exception {

		List<BankAccount> list = (List<BankAccount>) request(getModelClass(), "get", condition);
		return list;

	}

	/**
	 * Managerクラスを返す
	 * 
	 * @return クラス
	 */
	protected Class getModelClass() {
		return BankAccountManager.class;
	}

	/**
	 * エンティティを一覧に表示する形式に変換し返す
	 * 
	 * @param bean データ
	 * @return 一覧に表示する形式に変換された銀行口座
	 */
	protected String[] getRowData(BankAccount bean) {

		return new String[] { bean.getCode(), bean.getName(), bean.getCurrencyCode(), bean.getBankCode(),
				bean.getBankName(), bean.getBranchCode(), bean.getBranchName(), bean.getClientCode(),
				bean.getClientName(), bean.getClientNameJ(), bean.getClientNameE(),
				getWord(DepositKind.getDepositKindName(bean.getDepositKind())), bean.getAccountNo(),
				bean.isUseEmployeePayment() ? getWord("C02149") : getWord("C02148"),
				bean.isUseExPayment() ? getWord("C02151") : getWord("C02150"), bean.getDepartmentCode(),
				bean.getDepartmentNames(), bean.getItemCode(), bean.getItemNames(), bean.getSubItemCode(),
				bean.getSubItemNames(), bean.getDetailItemCode(), bean.getDetailItemNames(),
				DateUtil.toYMDString(bean.getDateFrom()), DateUtil.toYMDString(bean.getDateTo()) };
	}

	/**
	 * @return 銀行口座
	 */
	protected BankAccount createBankAccount() {
		return new BankAccount();
	}

	/**
	 * @return 銀行口座の検索条件
	 */
	protected BankAccountSearchCondition createBankAccountSearchCondition() {
		return new BankAccountSearchCondition();
	}

}
