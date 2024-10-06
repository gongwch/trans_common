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
import jp.co.ais.trans2.model.bank.*;
import jp.co.ais.trans2.model.employee.*;

/**
 * MG0160EmployeeMaster - 社員マスタ - Main Controller
 * 
 * @author AIS
 */
public class MG0160EmployeeMasterPanelCtrl extends TController {

	/** 新規画面 */
	protected MG0160EmployeeMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0160EmployeeMasterDialog editView = null;

	/** 現在操作中のモードを把握するために使用する */
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
		mainView = new MG0160EmployeeMasterPanel();
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

			// 編集編集画面初期化
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
			EmployeeSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlEmpRefRan.requestFocus();
				return;
			}

			// 一覧をクリア
			mainView.tbList.removeRow();

			// メインボタン制御
			setMainButtonEnabled(false);

			// データ取得
			List<Employee> list = getList(condition);

			// 検索条件に該当するデータが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 検索したデータを一覧に表示する
			for (Employee emp : list) {
				mainView.tbList.addRow(getRowData(emp));
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
			Employee bean = getSelected();

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
			Employee bean = getSelected();

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
			Employee bean = getSelected();

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

			// 検索条件の取得
			EmployeeSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlEmpRefRan.requestFocus();
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
			printer.preview(data, getWord("C00913") + ".xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 編集画面作成
	 */
	protected void createEditView() {
		editView = new MG0160EmployeeMasterDialog(mainView.getParentFrame(), true);
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
		// 銀行
		editView.ctrlBnkCode.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !editView.ctrlBnkCode.isValueChanged()) {
					return;
				}

				ctrlBankReference_after();
			}

			protected void ctrlBankReference_after() {
				Bank bank = editView.ctrlBnkCode.getEntity();

				if (bank != null) {
					editView.ctrlStnCode.clear();
					editView.ctrlStnCode.setEditable(true);
					editView.ctrlStnCode.getSearchCondition().setBankCode(bank.getCode());
				} else {
					editView.ctrlStnCode.clear();
					editView.ctrlStnCode.setEditable(false);
				}
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
			Employee bean = getInputtedEmployee();

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

					// 修正
					request(getModelClass(), "modify", bean);

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
	 * 編集画面初期化
	 * 
	 * @param editMode
	 * @param bean
	 */
	protected void initEditView(Mode editMode, Employee bean) {

		this.mode = editMode;

		switch (editMode) {

			case NEW:

				editView.setTitle(getWord("C01698"));
				editView.ctrlStnCode.setEditable(false);
				editView.ctrlDateFrom.setValue(editView.ctrlDateFrom.getCalendar().getMinimumDate());
				editView.ctrlDateTo.setValue(editView.ctrlDateTo.getCalendar().getMaximumDate());
				editView.bgKozaKbn.setSelected(editView.ctrlKozaKbnOrd.getModel(), true);

				break;

			case MODIFY:
			case COPY:

				if (Mode.MODIFY == editMode) {

					editView.setTitle(getWord("C00977"));
					editView.ctrlCode.setEnabled(false);

				} else {

					editView.setTitle(getWord("C01738"));

				}

				if (bean.getBnkCode() == null && bean.getStnCode() == null) {
					editView.ctrlStnCode.setEditable(false);
				}

				editView.ctrlCode.setValue(bean.getCode());
				editView.ctrlName.setValue(bean.getName());
				editView.ctrlNameS.setValue(bean.getNames());
				editView.ctrlNameK.setValue(bean.getNamek());
				editView.ctrlCbkCode.code.setValue(bean.getCbkCode());
				editView.ctrlCbkCode.name.setValue(bean.getCbkName());
				editView.ctrlBnkCode.code.setValue(bean.getBnkCode());
				editView.ctrlBnkCode.name.setValue(bean.getBnkName());
				editView.ctrlStnCode.code.setValue(bean.getStnCode());
				editView.ctrlStnCode.name.setValue(bean.getStnName());
				editView.ctrlStnCode.getSearchCondition().setBankCode(bean.getBnkCode());
				editView.bgKozaKbn.setSelected(editView.ctrlKozaKbnOrd.getModel(), true);
				editView.ctrlKozaKbnCur.setSelected(DepositKind.CURRENT == bean.getKozaKbn());
				editView.ctrlYknNo.setValue(bean.getYknNo());
				editView.ctrlYknKana.setValue(bean.getYknKana());
				editView.ctrlDateFrom.setValue(bean.getDateFrom());
				editView.ctrlDateTo.setValue(bean.getDateTo());

				break;
		}
	}

	/**
	 * 編集画面の入力値取得
	 * 
	 * @return Employee
	 */
	protected Employee getInputtedEmployee() {

		Employee bean = new Employee();

		bean.setCode(editView.ctrlCode.getValue());
		bean.setName(editView.ctrlName.getValue());
		bean.setNames(editView.ctrlNameS.getValue());
		bean.setNamek(editView.ctrlNameK.getValue());
		bean.setCbkCode(editView.ctrlCbkCode.getCode());
		bean.setCbkName(editView.ctrlCbkCode.getNames());
		bean.setBnkCode(editView.ctrlBnkCode.getCode());
		bean.setBnkName(editView.ctrlBnkCode.getNames());
		bean.setStnCode(editView.ctrlStnCode.getCode());
		bean.setStnName(editView.ctrlStnCode.getNames());

		if (editView.ctrlKozaKbnCur.isSelected()) {
			bean.setKozaKbn(DepositKind.CURRENT);
		} else {
			bean.setKozaKbn(DepositKind.ORDINARY);
		}

		bean.setYknKana(editView.ctrlYknKana.getValue());
		bean.setYknNo(editView.ctrlYknNo.getValue());
		bean.setDateFrom(editView.ctrlDateFrom.getValue());
		bean.setDateTo(editView.ctrlDateTo.getValue());

		return bean;
	}

	/**
	 * 検索条件取得
	 * 
	 * @return EmployeeSearchCondition 検索条件
	 */
	protected EmployeeSearchCondition getSearchCondition() {

		EmployeeSearchCondition condition = new EmployeeSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.ctrlEmpRefRan.getCodeFrom());
		condition.setCodeTo(mainView.ctrlEmpRefRan.getCodeTo());
		if (!mainView.chkTerm.isSelected()) {
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
	protected List<Object> getRowData(Employee bean) {

		List<Object> list = new ArrayList<Object>();

		list.add(bean.getCode());
		list.add(bean.getName());
		list.add(bean.getNames());
		list.add(bean.getNamek());
		list.add(bean.getCbkCode());
		list.add(bean.getCbkName());
		list.add(bean.getBnkCode());
		list.add(bean.getBnkName());
		list.add(bean.getStnCode());
		list.add(bean.getStnName());
		list.add(getWord(bean.getKozaKbnText()));
		list.add(bean.getYknNo());
		list.add(bean.getYknKana());
		list.add(DateUtil.toYMDString(bean.getDateFrom()));
		list.add(DateUtil.toYMDString(bean.getDateTo()));
		list.add(bean);

		return list;
	}

	/**
	 * 一覧で選択したデータを取得
	 * 
	 * @return bean
	 * @exception Exception
	 */
	protected Employee getSelected() throws Exception {

		Employee bean = (Employee) mainView.tbList.getSelectedRowValueAt(MG0160EmployeeMasterPanel.SC.ENTITY);

		EmployeeSearchCondition condition = new EmployeeSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(bean.getCode());

		List<Employee> list = getList(condition);

		// メッセージ追加必要 ##########
		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました。
			throw new TException(getMessage("I00193"));
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
	protected List<Employee> getList(EmployeeSearchCondition condition) throws Exception {
		List<Employee> list = (List<Employee>) request(getModelClass(), "get", condition);
		return list;
	}

	/**
	 * 指定行データを削除する
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void doDelete(Employee bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * @return モデル
	 */
	protected Class getModelClass() {
		return EmployeeManager.class;
	}

	/**
	 * 編集画面入力値の妥当性をチェック *
	 * 
	 * @return bool - true:run, false:stop
	 * @exception Exception
	 */

	protected boolean isInputCorrect() throws Exception {
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			showMessage(editView, "I00037", "C00697");
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlName.getValue())) {
			showMessage(editView, "I00037", "C00807");
			editView.ctrlName.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlNameS.getValue())) {
			showMessage(editView, "I00037", "C00808");
			editView.ctrlNameS.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlNameK.getValue())) {
			showMessage(editView, "I00037", "C00809");
			editView.ctrlNameK.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlDateFrom.getValue())) {
			showMessage(editView, "I00037", "C00055");
			editView.ctrlDateFrom.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlDateTo.getValue())) {
			showMessage(editView, "I00037", "C00261");
			editView.ctrlDateTo.requestTextFocus();
			return false;
		}

		if (editView.ctrlStnCode.isEditable() && Util.isNullOrEmpty(editView.ctrlStnCode.getCode())) {
			showMessage(editView, "I00037", editView.ctrlStnCode.btn.getText());
			editView.ctrlStnCode.requestTextFocus();
			return false;
		}

		// 開始年月日,終了年月日チェック
		if ((!Util.isNullOrEmpty(editView.ctrlDateFrom.getValue()) && !Util.isNullOrEmpty(editView.ctrlDateTo
			.getValue())) && !Util.isSmallerThenByYMD(editView.ctrlDateFrom.getValue(), editView.ctrlDateTo.getValue())) {
			showMessage(editView, "I00067");
			editView.ctrlDateFrom.requestFocus();
			return false;
		}

		// 新規、複写の場合は同一会社コード&&同一社員コードが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			EmployeeSearchCondition condition = new EmployeeSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlCode.getValue());

			List<Employee> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00084", "C00697");
				editView.ctrlCode.requestTextFocus();
				return false;
			}
		}
		return true;
	}
}