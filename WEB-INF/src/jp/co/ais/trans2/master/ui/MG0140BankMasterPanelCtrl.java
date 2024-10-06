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
import jp.co.ais.trans2.master.ui.MG0140BankMasterPanel.SC;
import jp.co.ais.trans2.model.bank.*;

/**
 * 銀行マスタのコントローラ
 */

public class MG0140BankMasterPanelCtrl extends TController {

	/**
	 * 指示画面
	 */
	protected MG0140BankMasterPanel mainView = null;

	/**
	 * 編集画面
	 */
	protected MG0140BankMasterDialog editView = null;

	/**
	 * 操作モードを把握
	 */
	protected Mode mode = null;

	/**
	 * 操作モード
	 */
	protected enum Mode {
		/** 新規 */
		New,
		/** 修正 */
		Copy,
		/** 複写 */
		Modify
	}

	@Override
	public void start() {

		try {
			// 指示画面生成
			createMainView();

			// 指示画面を初期化
			initMainView();

			// 指示画面指示
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
	 * 指示画面のファクトリ
	 */
	protected void createMainView() {
		mainView = new MG0140BankMasterPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面イベント定義
	 */
	protected void addMainViewEvent() {

		// 新規ボタン押下
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// 検索ボタン押下
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// 編集ボタン押下
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// 複写ボタン押下
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// 削除ボタン押下
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// エクセルボタンを押下
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 銀行コード入力イベント
		mainView.ctrlBank.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				verifyBankCode();
			}
		});
	}

	/**
	 * 入力した銀行コードを支店検索条件に渡す
	 */
	protected void verifyBankCode() {

		// 入力した銀行コード
		Bank bank = mainView.ctrlBank.getEntity();

		// 入力されていない、またはマスタに存在しない値であれば、支店をブランクにする
		if (bank == null) {
			mainView.ctrlBranch.getFieldFrom().getSearchCondition().setBankCode(null);
			mainView.ctrlBranch.getFieldTo().getSearchCondition().setBankCode(null);
			mainView.ctrlBranch.clear();
		} else {
			mainView.ctrlBranch.getFieldFrom().getSearchCondition().setBankCode(bank.getCode());
			mainView.ctrlBranch.getFieldTo().getSearchCondition().setBankCode(bank.getCode());
		}
		setBranchEnabled(bank != null);

	}

	/**
	 * 指示画面を初期化
	 */
	protected void initMainView() {

		// メインボタンを押下不可能にする
		setMainButtonEnabled(false);

		// 支店フィールドは使用不可
		setBranchEnabled(false);

	}

	/**
	 * 指示画面新規ボタンを押下
	 */
	protected void btnNew_Click() {

		try {

			// 編集画面を生成
			createEditView();

			// 編集画面を初期化する
			initEditView(Mode.New, null);

			// 編集画面を表示
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面検索ボタンを押下
	 */
	protected void btnSearch_Click() {

		try {

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 開始コード<=終了コードにしてください。
			if (Util.isSmallerThen(mainView.ctrlBranch.getCodeFrom(), mainView.ctrlBranch.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlBranch.getFieldFrom().requestFocus();
				return;
			}

			// 銀行情報を取得
			BranchSearchCondition branchcondition = getSearchCondition();

			List<Bank> list = getBranch(branchcondition);

			// 検索条件に該当する銀行が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "W01118", "C00124");
				return;
			}

			// 銀行情報を一覧に表示する
			for (Bank bank : list) {
				mainView.tbl.addRow(getRowData(bank));
			}
			// メインボタンを押下可能にし、1行目を選択する
			setMainButtonEnabled(true);
			mainView.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 指示画面編集を押下
	 */
	protected void btnModify_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// 編集対象の銀行情報を取得する
			Bank bank = getSelectedRowData();

			// 編集画面を生成し、編集対象の銀行情報をセットする
			createEditView();
			initEditView(Mode.Modify, bank);

			// 編集画面を表示する
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面複写ボタンを押下
	 */
	protected void btnCopy_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// 複写対象の銀行情報を取得する
			Bank bank = getSelectedRowData();

			// 複写画面を生成し、複写対象の銀行情報をセットする
			createEditView();
			initEditView(Mode.Copy, bank);

			// 複写画面を表示する
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面削除を押下
	 */
	protected void btnDelete_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// 削除対象の銀行情報を取得する
			Bank bank = getSelectedRowData();

			// 確認メッセージ
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除する
			deleteBank(bank);

			// 削除した銀行情報を一覧から削除
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
	 * 指示画面エクセルボタンを押下
	 */
	protected void btnExcel_Click() {

		try {

			// 確認メッセージ
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// データ抽出
			BranchSearchCondition condition = getSearchCondition();

			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02323") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 編集画面のファクトリ
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0140BankMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent();

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

	}

	/**
	 * 編集画面を初期化
	 * 
	 * @param mode_
	 * @param bank
	 */
	protected void initEditView(Mode mode_, Bank bank) {

		this.mode = mode_;

		// 新規の場合
		if (Mode.New == mode_) {
			editView.setTitle(getWord("C01698"));

			editView.dateFrom.setValue(editView.dateFrom.getCalendar().getMinimumDate());
			editView.dateTo.setValue(editView.dateTo.getCalendar().getMaximumDate());

			return;

			// 編集、複写の場合
		} else if (Mode.Modify == mode_ || Mode.Copy == mode_) {

			// 編集
			if (Mode.Modify == mode_) {
				editView.setTitle(getWord("C00977"));
				editView.ctrlCode.setEditable(false);
				editView.ctrlBranchCode.setEditable(false);
				editView.setEditMode();

				// 複写
			} else {
				editView.setTitle(getWord("C01738"));

			}

			editView.ctrlCode.setValue(bank.getCode());
			editView.ctrlBranchCode.setValue(bank.getBranch().getCode());
			editView.ctrlName.setValue(bank.getNames());
			editView.ctrlKana.setValue(bank.getKana());
			editView.ctrlNamek.setValue(bank.getNamek());
			editView.ctrlBranchName.setValue(bank.getBranch().getNames());
			editView.ctrlBranchKana.setValue(bank.getBranch().getKana());
			editView.ctrlBranchNamek.setValue(bank.getBranch().getNamek());
			editView.dateFrom.setValue(bank.getDateFrom());
			editView.dateTo.setValue(bank.getDateTo());

		}
	}

	/**
	 * 編集画面確定を押下
	 */

	protected void btnSettle_Click() {

		try {

			// 入力チェックを行う
			if (!isInputRight()) {
				return;
			}

			// 入力された銀行情報を取得
			Bank bank = getInputedBank();

			// 新規登録の場合
			if (Mode.New == mode || Mode.Copy == mode) {

				// 新規登録
				request(getModelClass(), "entry", bank);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(bank));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.Modify == mode) {

				// 修正
				request(getModelClass(), "modify", bank);

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(bank));

			}

			// 修正行を反映する
			dispatchRow(bank);

			// 編集画面を閉じる
			btnClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指定する情報を反映する
	 * 
	 * @param bank
	 */
	protected void dispatchRow(Bank bank) {

		for (int i = 0; i < mainView.tbl.getRowCount(); i++) {
			String bankCode = (String) mainView.tbl.getRowValueAt(i, SC.BnkCode);
			if (bankCode.equals(bank.getCode())) {
				mainView.tbl.setRowValueAt(bank.getNames(), i, SC.BnkName);
				mainView.tbl.setRowValueAt(bank.getKana(), i, SC.BnkKana);
				mainView.tbl.setRowValueAt(bank.getNamek(), i, SC.BnkNamek);

			}
		}
	}

	/**
	 * 編集画面戻るを押下
	 */
	protected void btnClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * 指示画面で入力された銀行の検索条件を返す。
	 * 
	 * @return 銀行の検索条件
	 */
	protected BranchSearchCondition getSearchCondition() {
		BranchSearchCondition condition = new BranchSearchCondition();
		condition.setBankCode(mainView.ctrlBank.getCode());
		condition.setCodeFrom(mainView.ctrlBranch.getCodeFrom());
		condition.setCodeTo(mainView.ctrlBranch.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;
	}

	/**
	 * クラスを返す
	 * 
	 * @return クラス
	 */
	protected Class getModelClass() {
		return BankManager.class;
	}

	/**
	 * 編集画面で入力した銀行情報を返す。
	 * 
	 * @return 銀行情報
	 */
	protected Bank getInputedBank() {

		Bank bank = new Bank();
		bank.setCode(editView.ctrlCode.getValue());
		Branch branch = new Branch();
		bank.setBranch(branch);
		bank.getBranch().setCode(editView.ctrlBranchCode.getValue());
		bank.setNames(editView.ctrlName.getValue());
		bank.setKana(editView.ctrlKana.getValue());
		bank.setNamek(editView.ctrlNamek.getValue());
		bank.getBranch().setNames(editView.ctrlBranchName.getValue());
		bank.getBranch().setKana(editView.ctrlBranchKana.getValue());
		bank.getBranch().setNamek(editView.ctrlBranchNamek.getValue());
		bank.setDateFrom(editView.dateFrom.getValue());
		bank.setDateTo(editView.dateTo.getValue());

		return bank;
	}

	/**
	 * 銀行情報を一覧に表示する形式で返す
	 * 
	 * @param bank
	 * @return 銀行情報
	 */
	protected String[] getRowData(Bank bank) {
		return new String[] { bank.getCode(), bank.getBranch().getCode(), bank.getNames(), bank.getKana(),
				bank.getNamek(), bank.getBranch().getNames(), bank.getBranch().getKana(), bank.getBranch().getNamek(),
				DateUtil.toYMDString(bank.getDateFrom()), DateUtil.toYMDString(bank.getDateTo()) };
	}

	/**
	 * メインボタンの押下制御
	 * 
	 * @param bln
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnModify.setEnabled(bln);
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
	}

	/**
	 * 検索条件に該当する銀行リストを返す
	 * 
	 * @param condition
	 * @return 銀行リスト
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Bank> getBranch(BranchSearchCondition condition) throws Exception {

		List<Bank> list = (List<Bank>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * 一覧で選択されてる情報を返す。
	 * 
	 * @return 選択情報
	 * @throws Exception
	 */
	protected Bank getSelectedRowData() throws Exception {

		BranchSearchCondition condition = new BranchSearchCondition();
		condition.setBankCode((String) mainView.tbl.getSelectedRowValueAt(MG0140BankMasterPanel.SC.BnkCode));
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0140BankMasterPanel.SC.BranchCode));

		List<Bank> list = getBranch(condition);

		if (list == null || list.isEmpty()) {
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * 指定の銀行情報を削除する
	 * 
	 * @param bank
	 * @throws Exception
	 */
	protected void deleteBank(Bank bank) throws Exception {
		request(getModelClass(), "delete", bank);
	}

	/**
	 * 編集画面で入力した値が妥当か調べる
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// 銀行コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			showMessage(editView, "I00037", "C00779");
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		// 銀行支店コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlBranchCode.getValue())) {
			showMessage(editView, "I00037", "C00780");
			editView.ctrlBranchCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一銀行コードと支店コードが既に存在したらエラー
		if (Mode.New == mode || Mode.Copy == mode) {
			BranchSearchCondition condition = new BranchSearchCondition();
			condition.setCode(editView.ctrlBranchCode.getValue());
			condition.setBankCode(editView.ctrlCode.getValue());
			List<Bank> list = getBranch(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00124");
				editView.ctrlBranchCode.requestTextFocus();
				return false;
			}
		}

		// 銀行名が未入力の場合エラー

		if (Util.isNullOrEmpty(editView.ctrlName.getValue())) {
			showMessage(editView, "I00037", "C00781");
			editView.ctrlName.requestTextFocus();
			return false;
		}

		// 銀行カナ名称が未入力の場合エラー

		if (Util.isNullOrEmpty(editView.ctrlKana.getValue())) {
			showMessage(editView, "I00037", "C00782");
			editView.ctrlKana.requestTextFocus();
			return false;

		}

		// 銀行検索名称が未入力の場合エラー

		if (Util.isNullOrEmpty(editView.ctrlNamek.getValue())) {
			showMessage(editView, "I00037", "C00829");
			editView.ctrlNamek.requestTextFocus();
			return false;

		}

		// 銀行支店名が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlBranchName.getValue())) {
			showMessage(editView, "I00037", "C00783");
			editView.ctrlBranchName.requestTextFocus();
			return false;

		}

		// 銀行支店カナ名称が未入力の場合エラー

		if (Util.isNullOrEmpty(editView.ctrlBranchKana.getValue())) {
			showMessage(editView, "I00037", "C00784");
			editView.ctrlBranchKana.requestTextFocus();
			return false;

		}

		// 銀行支店検索名称が未入力の場合エラー

		if (Util.isNullOrEmpty(editView.ctrlBranchNamek.getValue())) {
			showMessage(editView, "I00037", "C00785");
			editView.ctrlBranchNamek.requestTextFocus();
			return false;

		}

		// 開始年月日が未入力の場合エラー

		if (Util.isNullOrEmpty(editView.dateFrom.getValue())) {
			showMessage(editView, "I00037", "C00055");
			editView.dateFrom.requestTextFocus();
			return false;
		}

		// 終了年月日が未入力の場合エラー

		if (Util.isNullOrEmpty(editView.dateTo.getValue())) {
			showMessage(editView, "I00037", "C00261");
			editView.dateTo.requestTextFocus();
			return false;
		}

		// 開始年月日,終了年月日チェック
		if ((!Util.isNullOrEmpty(editView.dateFrom.getValue()) && !Util.isNullOrEmpty(editView.dateTo.getValue()))
			&& !Util.isSmallerThenByYMD(editView.dateFrom.getValue(), editView.dateTo.getValue())) {
			showMessage(editView, "I00067");
			editView.dateFrom.requestFocus();
			return false;
		}

		return true;

	}

	/**
	 * 支店フィールドの入力制御
	 * 
	 * @param bln true(入力可能にする)/false(入力不可能にする)
	 */
	protected void setBranchEnabled(boolean bln) {
		mainView.ctrlBranch.setEditable(bln);
	}

}