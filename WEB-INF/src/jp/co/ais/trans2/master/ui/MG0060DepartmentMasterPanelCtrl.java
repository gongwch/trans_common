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
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.department.*;

/**
 * 部門マスタ 画面コントロール
 */
public class MG0060DepartmentMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0060DepartmentMasterPanel mainView;

	/** 編集画面 */
	protected MG0060DepartmentMasterDialog editView = null;

	/** 操作モード。現在操作中のモードを把握するために使用する。 */
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

	/** 連携コード1使用 */
	public boolean isUseIf1 = false;

	/** 連携コード2使用 */
	public boolean isUseIf2 = false;

	/** 連携コード3使用 */
	public boolean isUseIf3 = false;

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
		mainView = new MG0060DepartmentMasterPanel();
		addMainViewEvent();
	}

	/**
	 * メイン画面ボタン押下時のイベント
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
	 * メイン画面の初期化
	 */
	protected void initMainView() {
		// メインボタンを押下不可能にする
		setMainButtonEnabled(false);

		// 部門範囲の検索条件をセットする
		mainView.departmentRange.ctrlDepartmentReferenceFrom.getSearchCondition().setSumDepartment(true);
		mainView.departmentRange.ctrlDepartmentReferenceTo.getSearchCondition().setSumDepartment(true);

		// 丹羽汽船カスタマイズ：連携桁数ゲット
		try {
			String if1Code = ClientConfig.getProperty("trans.MG0060.if.code.1.length");
			String if1Name = ClientConfig.getProperty("trans.MG0060.if.name.1.length");

			int if1CodeLength = DecimalUtil.toInt(if1Code);
			int if1NameLength = DecimalUtil.toInt(if1Name);

			isUseIf1 = if1CodeLength > 0 && if1NameLength > 0;

		} catch (Exception e) {
			// 処理なし
		}
		try {
			String if2Code = ClientConfig.getProperty("trans.MG0060.if.code.2.length");
			String if2Name = ClientConfig.getProperty("trans.MG0060.if.name.2.length");

			int if2CodeLength = DecimalUtil.toInt(if2Code);
			int if2NameLength = DecimalUtil.toInt(if2Name);

			isUseIf2 = if2CodeLength > 0 && if2NameLength > 0;

		} catch (Exception e) {
			// 処理なし
		}
		try {
			String if3Code = ClientConfig.getProperty("trans.MG0060.if.code.3.length");
			String if3Name = ClientConfig.getProperty("trans.MG0060.if.name.3.length");

			int if3CodeLength = DecimalUtil.toInt(if3Code);
			int if3NameLength = DecimalUtil.toInt(if3Name);

			isUseIf3 = if3CodeLength > 0 && if3NameLength > 0;

		} catch (Exception e) {
			// 処理なし
		}
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
			if (Util.isSmallerThen(mainView.departmentRange.getCodeFrom(), mainView.departmentRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.departmentRange.getFieldFrom().requestFocus();
				return;
			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 部門情報を取得
			DepartmentSearchCondition condition = getSearchCondition();
			List<Department> list = getDepartment(condition);

			// 検索条件に該当する部門が存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {

				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// 部門情報を一覧に表示する
			for (Department department : list) {
				mainView.tbl.addRow(getRowData(department));
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
			// 編集対象の部門を取得する
			Department department = getSelectedDepartment();

			// 編集画面を生成し、編集対象の部門情報をセットする
			createEditView();
			initEditView(Mode.MODIFY, department);

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

			// 複写対象の部門を取得する
			Department department = getSelectedDepartment();

			// 複写画面を生成し、複写対象の部門情報をセットする
			createEditView();
			initEditView(Mode.COPY, department);

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

			// 削除対象の部門を取得する
			Department department = getSelectedDepartment();

			// 確認メッセージ
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除する
			deleteDepartment(department);

			// 削除した部門を一覧から削除
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
			// 開始コード<=終了コードにしてください。
			if (Util.isSmallerThen(mainView.departmentRange.getCodeFrom(), mainView.departmentRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.departmentRange.getFieldFrom().requestFocus();
				return;
			}

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// データ抽出
			DepartmentSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02338") + ".xls");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0060DepartmentMasterDialog(getCompany(), mainView.getParentFrame(), true);

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
	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param mode_ 操作モード。
	 * @param bean 部門。修正、複写の場合は当該部門情報を編集画面にセットする。
	 */
	protected void initEditView(Mode mode_, Department bean) {

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
					editView.ctrlDepartmentCode.setEditable(false);
					editView.setEditMode();
					// 複写
				} else {
					editView.setTitle(getWord("C01738"));
				}

				editView.ctrlDepartmentCode.setValue(bean.getCode());
				editView.ctrlDepartmentName.setValue(bean.getName());
				editView.ctrlDepartmentNames.setValue(bean.getNames());
				editView.ctrlDepartmentNamek.setValue(bean.getNamek());
				editView.ctrlDepartmentMen1.setNumber(bean.getPersonnel1());
				editView.ctrlDepartmentMen2.setNumber(bean.getPersonnel2());
				editView.ctrlDepartmentArea.setNumber(bean.getFloorSpace());
				editView.rdoTotal.setSelected(bean.isSumDepartment());
				editView.rdoImput.setSelected(!bean.isSumDepartment());
				editView.dtBeginDate.setValue(bean.getDateFrom());
				editView.dtEndDate.setValue(bean.getDateTo());

				editView.ctrlIf1Code.setValue(bean.getIfCode1());
				editView.ctrlIf1Name.setValue(bean.getIfName1());
				editView.ctrlIf2Code.setValue(bean.getIfCode2());
				editView.ctrlIf2Name.setValue(bean.getIfName2());
				editView.ctrlIf3Code.setValue(bean.getIfCode3());
				editView.ctrlIf3Name.setValue(bean.getIfName3());
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

			// 入力された部門情報を取得
			Department department = getInputedDepartment();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entry", department);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(department));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", department);

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(department));

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
	 * @return DepartmentManager
	 */
	protected Class getModelClass() {
		return DepartmentManager.class;
	}

	/**
	 * 編集画面で入力された部門を返す
	 * 
	 * @return 編集画面で入力された部門
	 */
	protected Department getInputedDepartment() {

		Department department = createDepartment();
		department.setCompanyCode(getCompany().getCode());
		department.setCode(editView.ctrlDepartmentCode.getValue());
		department.setName(editView.ctrlDepartmentName.getValue());
		department.setNames(editView.ctrlDepartmentNames.getValue());
		department.setNamek(editView.ctrlDepartmentNamek.getValue());
		department.setPersonnel1(editView.ctrlDepartmentMen1.getIntValue());
		department.setPersonnel2(editView.ctrlDepartmentMen2.getIntValue());
		department.setFloorSpace(editView.ctrlDepartmentArea.getBigDecimalValue());
		department.setSumDepartment(editView.rdoImput.isSelected());
		department.setSumDepartment(editView.rdoTotal.isSelected());
		department.setDateFrom(editView.dtBeginDate.getValue());
		department.setDateTo(editView.dtEndDate.getValue());

		department.setIfCode1(editView.ctrlIf1Code.getValue());
		department.setIfName1(editView.ctrlIf1Name.getValue());
		department.setIfCode2(editView.ctrlIf2Code.getValue());
		department.setIfName2(editView.ctrlIf2Name.getValue());
		department.setIfCode3(editView.ctrlIf3Code.getValue());
		department.setIfName3(editView.ctrlIf3Name.getValue());

		department.setUseIf1(isUseIf1);
		department.setUseIf2(isUseIf2);
		department.setUseIf3(isUseIf3);

		return department;

	}

	/**
	 * @return 部門
	 */
	protected Department createDepartment() {
		return new Department();
	}

	/**
	 * 部門情報を一覧に表示する形式に変換し返す
	 * 
	 * @param department 部門情報
	 * @return 一覧に表示する形式に変換された部門情報
	 */
	protected List<Object> getRowData(Department department) {
		List<Object> list = new ArrayList<Object>();

		list.add(department.getCode()); // 部門コード
		list.add(department.getName()); // 部門名称
		list.add(department.getNames()); // 部門略称
		list.add(department.getNamek()); // 部門検索名称
		list.add(NumberFormatUtil.formatNumber(department.getPersonnel1())); // 人員1
		list.add(NumberFormatUtil.formatNumber(department.getPersonnel2())); // 人員2
		list.add(NumberFormatUtil.formatNumber(department.getFloorSpace(), 2)); // 床面積
		list.add(department.isSumDepartment() ? getWord("C00255") : getWord("C01275")); // 部門区分
		list.add(DateUtil.toYMDString(department.getDateFrom())); // 開始年月日
		list.add(DateUtil.toYMDString(department.getDateTo())); // 終了年月日

		list.add(department.getIfCode1()); // 連携コード1
		list.add(department.getIfName1()); // 連携名称1
		list.add(department.getIfCode2()); // 連携コード2
		list.add(department.getIfName2()); // 連携名称2
		list.add(department.getIfCode3()); // 連携コード3
		list.add(department.getIfName3()); // 連携名称3

		return list;
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
	 * 検索条件に該当する部門のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する部門のリスト
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Department> getDepartment(DepartmentSearchCondition condition) throws Exception {

		List<Department> list = (List<Department>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * 指示画面で入力された部門の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected DepartmentSearchCondition getSearchCondition() {

		DepartmentSearchCondition condition = createCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.departmentRange.getCodeFrom());
		condition.setCodeTo(mainView.departmentRange.getCodeTo());
		condition.setSumDepartment(true);
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		condition.setUseIf1(isUseIf1);
		condition.setUseIf2(isUseIf2);
		condition.setUseIf3(isUseIf3);

		return condition;

	}

	/**
	 * @return 検索条件
	 */
	protected DepartmentSearchCondition createCondition() {
		return new DepartmentSearchCondition();
	}

	/**
	 * 一覧で選択されている部門を返す
	 * 
	 * @return 一覧で選択されている部門
	 * @throws Exception
	 */
	protected Department getSelectedDepartment() throws Exception {

		DepartmentSearchCondition condition = createCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0060DepartmentMasterPanel.SC.code));
		condition.setSumDepartment(true);

		condition.setUseIf1(isUseIf1);
		condition.setUseIf2(isUseIf2);
		condition.setUseIf3(isUseIf3);

		List<Department> list = getDepartment(condition);

		if (list == null || list.isEmpty()) {
			throw new TException("I00193"); // 選択されたデータは他ユーザーにより削除されました。
		}
		return list.get(0);
	}

	/**
	 * 指定の部門を削除する
	 * 
	 * @param department 部門
	 * @throws TException
	 */
	protected void deleteDepartment(Department department) throws TException {
		request(getModelClass(), "delete", department);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// 部門コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDepartmentCode.getValue())) {
			showMessage(editView, "I00037", "C00698"); // 部門コードを入力してください。
			editView.ctrlDepartmentCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一部門が既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			DepartmentSearchCondition condition = createCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlDepartmentCode.getValue());
			condition.setSumDepartment(true);

			List<Department> list = getDepartment(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C00467"); // 指定の部門は既に存在します。
				editView.ctrlDepartmentCode.requestTextFocus();
				return false;
			}
		}

		// 部門名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDepartmentName.getValue())) {
			showMessage(editView, "I00037", "C00723"); // 部門名称を入力してください。
			editView.ctrlDepartmentName.requestTextFocus();
			return false;
		}

		// 部門略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDepartmentNames.getValue())) {
			showMessage(editView, "I00037", "C00724"); // 部門略称を入力してください。
			editView.ctrlDepartmentNames.requestTextFocus();
			return false;
		}

		// 部門検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDepartmentNamek.getValue())) {
			showMessage(editView, "I00037", "C00725"); // 部門検索名称を入力してください。
			editView.ctrlDepartmentNamek.requestTextFocus();
			return false;
		}

		// 人員数1が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDepartmentMen1.getValue())) {
			showMessage(editView, "I00037", "C00726"); // 人員数1を入力してください。
			editView.ctrlDepartmentMen1.requestTextFocus();
			return false;
		}

		// 人員数2が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDepartmentMen2.getValue())) {
			showMessage(editView, "I00037", "C00727"); // 人員数2を入力してください。
			editView.ctrlDepartmentMen2.requestTextFocus();
			return false;
		}

		// 床面積が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDepartmentArea.getValue())) {
			showMessage(editView, "I00037", "C00728"); // 床面積を入力してください。
			editView.ctrlDepartmentArea.requestTextFocus();
			return false;
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

		if (isUseIf1 && !Util.isNullOrEmpty(editView.ctrlIf1Code.getValue())) {
			String txt = StringUtil.leftBX(editView.ctrlIf1Code.getValue(), 1);
			if (Util.equals(txt, "0")) {
				showMessage(editView, "I00259", "C12060", editView.ctrlIf1Code.getValue());
				editView.ctrlIf1Code.requestFocus();
				return false;
			}
		}
		if (isUseIf2 && !Util.isNullOrEmpty(editView.ctrlIf2Code.getValue())) {
			String txt = StringUtil.leftBX(editView.ctrlIf2Code.getValue(), 1);
			if (Util.equals(txt, "0")) {
				showMessage(editView, "I00259", "C12062", editView.ctrlIf2Code.getValue());
				editView.ctrlIf2Code.requestFocus();
				return false;
			}
		}
		if (isUseIf3 && !Util.isNullOrEmpty(editView.ctrlIf3Code.getValue())) {
			String txt = StringUtil.leftBX(editView.ctrlIf3Code.getValue(), 1);
			if (Util.equals(txt, "0")) {
				showMessage(editView, "I00259", "C12064", editView.ctrlIf3Code.getValue());
				editView.ctrlIf3Code.requestFocus();
				return false;
			}
		}

		return true;

	}
}
