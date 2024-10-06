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
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.aprvrole.*;
import jp.co.ais.trans2.model.user.*;

/**
 * 承認権限ロールマスタコントローラ
 */
public class MG0500AprvRoleMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0500AprvRoleMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0500AprvRoleMasterDialog editView = null;

	/**
	 * ユーザーリスト
	 */
	protected List<User> editUserList = null;

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
		mainView = new MG0500AprvRoleMasterPanel(getCompany());
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
			editView.setLocationRelativeTo(null);// 中央に表示させる
			editView.setVisible(true);// 表示を適用

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
			if (Util.isSmallerThen(mainView.ctrlSearch.getCodeFrom(), mainView.ctrlSearch.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlSearch.getFieldFrom().requestFocus();
				return;
			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 検索条件を取得し、それをもとに承認権限ロール情報を取得
			AprvRoleSearchCondition condition = getSearchCondition();
			List<AprvRole> list = get(condition);

			// 検索条件に該当する承認権限ロールが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage("I00022");
				return;
			}

			// 承認権限ロール情報を一覧に表示する
			for (AprvRole bean : list) {
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

			// 編集対象のコードを取得する
			AprvRole bean = getSelected();

			// 編集画面を生成し、編集対象の承認権限ロール情報をセットする
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

			// 複写対象の承認権限ロールを取得する
			AprvRole bean = getSelected();
			// 複写画面を生成し、複写対象の承認権限ロール情報をセットする
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
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {
				return;
			}

			// 承認権限ロール情報を取得
			AprvRole bean = getSelected();

			// 削除
			request(getModelClass(), "delete", bean);

			// 削除した積荷品を一覧から削除
			mainView.tbl.removeSelectedRow();

			// 完了メッセージ
			showMessage(mainView, "I00013");

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
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// データ抽出
			AprvRoleSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage("I00022");
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("承認権限ロールマスタ") + ".xls"); // 承認権限ロールマスタ

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0500AprvRoleMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent();

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

		// [←(プログラム追加)]ボタン押下
		editView.btnAdd.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnAdd_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [←(プログラム追加)]ボタン押下
		editView.btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCancel_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * [一覧から選択]ボタン押下
	 */
	protected void btnAdd_Click() {

		int selectedRows[] = editView.tblRight.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String userCode = (String) editView.tblRight.getRowValueAt(selectedRows[i],
				MG0500AprvRoleMasterDialog.SC.userCode);
			String userNames = (String) editView.tblRight.getRowValueAt(selectedRows[i],
				MG0500AprvRoleMasterDialog.SC.userName);
			String depNames = (String) editView.tblRight.getRowValueAt(selectedRows[i],
				MG0500AprvRoleMasterDialog.SC.depName);

			editView.tblLeft.addRow(new Object[] { userCode, userNames, depNames });
		}

		// 選択元から削除
		editView.tblRight.removeSelectedRows();

	}

	/**
	 * [一覧から選択を取消]ボタン押下
	 */
	protected void btnCancel_Click() {

		int selectedRows[] = editView.tblLeft.getSelectedRows();
		for (int i = 0; i < selectedRows.length; i++) {

			String userCode = (String) editView.tblLeft.getRowValueAt(selectedRows[i],
				MG0500AprvRoleMasterDialog.SC.userCode);
			String userNames = (String) editView.tblLeft.getRowValueAt(selectedRows[i],
				MG0500AprvRoleMasterDialog.SC.userName);
			String depNames = (String) editView.tblLeft.getRowValueAt(selectedRows[i],
				MG0500AprvRoleMasterDialog.SC.depName);

			editView.tblRight.addRow(new Object[] { userCode, userNames, depNames });
		}

		// 選択先から削除
		editView.tblLeft.removeSelectedRows();

	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param mode_ 操作モード。
	 * @param bean 承認権限ロール。修正、複写の場合は当該承認権限ロール情報を編集画面にセットする。
	 * @throws TException
	 */
	protected void initEditView(Mode mode_, AprvRole bean) throws TException {
		this.mode = mode_;
		editUserList = getUserList();
		// 新規
		if (Mode.NEW == mode) {

			editView.setTitle(getWord("C01698"));
			editView.ctrlBeginDate.setValue(editView.ctrlBeginDate.getCalendar().getMinimumDate());
			editView.ctrlEndDate.setValue(editView.ctrlEndDate.getCalendar().getMaximumDate());
			// ユーザーを候補一覧に追加する
			for (User user : editUserList) {
				editView.tblRight.addRow(getRowData(user));
			}

		}

		else if (Mode.MODIFY == mode || Mode.COPY == mode) {

			// 編集
			if (Mode.MODIFY == mode) {
				editView.setTitle(getWord("C00977")); // 編集画面
				// 編集画面で、編集不可の設定を行う。
				editView.ctrlRoleCode.setEditable(false);
				editView.setEditMode();

			} else {
				// 複写
				editView.setTitle(getWord("C01738")); // 複写画面

			}
			editView.ctrlRoleCode.setValue(bean.getAPRV_ROLE_CODE());
			editView.ctrlRoleName.setValue(bean.getAPRV_ROLE_NAME());
			editView.ctrlRoleNames.setValue(bean.getAPRV_ROLE_NAME_S());
			editView.ctrlRoleNamek.setValue(bean.getAPRV_ROLE_NAME_K());
			editView.ctrlBeginDate.setValue(bean.getSTR_DATE());
			editView.ctrlEndDate.setValue(bean.getEND_DATE());

			// 承認権限ロールマスタに登録されえているユーザーを一覧に表示
			for (User user : bean.getUserList()) {
				editView.tblLeft.addRow(getRowDataRole(user));
			}

			// ユーザーを候補一覧に追加する
			for (User editUser : editUserList) {
				boolean isExist = false;
				for (User user : bean.getUserList()) {
					if (Util.equals(editUser.getCode(), user.getCode())) {
						isExist = true;
						break;
					}
				}
				if (!isExist) {
					editView.tblRight.addRow(getRowData(editUser));
				}

			}
		}

	}

	/**
	 * 編集画面[確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {

			// 入力チェックを行う。
			if (!isDialogInputCheck()) {
				return;
			}

			// 入力された承認権限ロール情報を取得
			List<AprvRole> usrList = getInputed();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entry", usrList);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(usrList.get(0)));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);
				mainView.tbl.setRowSelection(0);
				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", usrList);

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(usrList.get(0)));

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
	 * @return AprvRoleManager
	 */
	protected Class getModelClass() {
		return AprvRoleManager.class;
	}

	/**
	 * 編集画面で入力された承認権限ロール情報を返す
	 * 
	 * @return 編集画面で入力された承認権限ロール情報
	 */
	protected List<AprvRole> getInputed() {
		List<AprvRole> usrList = new ArrayList<AprvRole>();
		for (int row = 0; row < editView.tblLeft.getRowCount(); row++) {
			AprvRole bean = new AprvRole();
			bean.setCompanyCode(super.getCompanyCode());// 会社コード
			bean.setAPRV_ROLE_CODE(editView.ctrlRoleCode.getValue());// ロールコード
			bean.setAPRV_ROLE_NAME(editView.ctrlRoleName.getValue());// ロール名称
			bean.setAPRV_ROLE_NAME_S(editView.ctrlRoleNames.getValue());// ロール略称
			bean.setAPRV_ROLE_NAME_K(editView.ctrlRoleNamek.getValue());// ロール検索名称
			bean.setUSR_CODE((String) editView.tblLeft.getRowValueAt(row, MG0500AprvRoleMasterDialog.SC.userCode));// 経理承認
			bean.setSTR_DATE(editView.ctrlBeginDate.getValue()); // 開始年月日
			bean.setEND_DATE(editView.ctrlEndDate.getValue()); // 終了年月日

			usrList.add(bean);
		}
		return usrList;
	}

	/**
	 * @param bean
	 * @return データ取得
	 */
	protected List<Object> getRowData(AprvRole bean) {
		List<Object> list = new ArrayList<Object>();

		list.add(bean);
		list.add(bean.getAPRV_ROLE_CODE()); // ロールコード
		list.add(bean.getAPRV_ROLE_NAME()); // ロール名称
		list.add(bean.getAPRV_ROLE_NAME_S()); // ロール略称
		list.add(bean.getAPRV_ROLE_NAME_K()); // ロール検索名称
		list.add(DateUtil.toYMDString(bean.getSTR_DATE())); // 開始年月日
		list.add(DateUtil.toYMDString(bean.getEND_DATE())); // 終了年月日

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
	 * 検索条件に該当するコードのリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当するコードリスト
	 * @throws Exception
	 */
	protected List<AprvRole> get(AprvRoleSearchCondition condition) throws Exception {
		condition.setCompanyCode(getCompanyCode());
		List<AprvRole> list = (List<AprvRole>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * ユーザー覧を取得する
	 * 
	 * @return ユーザー
	 * @throws TException
	 */
	protected List<User> getUserList() throws TException {

		UserSearchCondition condition = new UserSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());

		List<User> list = (List<User>) request(UserManager.class, "get", condition);

		return list;

	}

	/**
	 * 新規用ユーザー情報を一覧に表示する形式に変換し返す
	 * 
	 * @param user ユーザー
	 * @return 一覧に表示する形式に変換されたプログラム
	 */
	protected String[] getRowData(User user) {
		return new String[] { user.getCode(), user.getNames(), user.getDepartment().getNames() };
	}

	/**
	 * 編集用ユーザー情報を一覧に表示する形式に変換し返す
	 * 
	 * @param role
	 * @return 一覧に表示する形式に変換されたプログラム
	 */
	protected String[] getRowDataRole(User role) {
		return new String[] { role.getCode(), role.getNames(), role.getDepartment().getNames() };
	}

	/**
	 * 指示画面で入力された承認権限ロールマスタの検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected AprvRoleSearchCondition getSearchCondition() {

		AprvRoleSearchCondition condition = new AprvRoleSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.ctrlSearch.getCodeFrom());
		condition.setCodeTo(mainView.ctrlSearch.getCodeTo());

		if (!mainView.ctrlOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;
	}

	/**
	 * 一覧で選択されている承認権限ロールコードを返す
	 * 
	 * @return 一覧で選択されている承認権限ロールコード
	 * @throws Exception
	 */
	protected AprvRole getSelected() throws Exception {

		AprvRoleSearchCondition condition = new AprvRoleSearchCondition();
		AprvRole role = (AprvRole) mainView.tbl.getSelectedRowValueAt(MG0500AprvRoleMasterPanel.SC.bean);
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(role.getAPRV_ROLE_CODE());

		List<AprvRole> list = get(condition);

		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました
			throw new TException("I00193");
		}
		return list.get(0);
	}

	/**
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isDialogInputCheck() throws Exception {

		// 承認権限ロールコードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlRoleCode.getValue())) {
			showMessage(editView, "I00037", "C11154"); // ロールコードを入力してください。
			editView.ctrlRoleCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一承認権限ロールコードが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			AprvRoleSearchCondition condition = new AprvRoleSearchCondition();
			condition.setCompanyCode(getCompany().getCode());
			condition.setCode(editView.ctrlRoleCode.getValue());

			List<AprvRole> list = get(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00148", "C11154"); // 指定のロールコードは既に存在します。
				editView.ctrlRoleCode.requestTextFocus();
				return false;
			}
		}

		// 承認権限ロール名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlRoleName.getValue())) {
			showMessage(editView, "I00037", "C11155"); // ロール名称を入力してください。
			editView.ctrlRoleName.requestTextFocus();
			return false;
		}

		// 承認権限ロール略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlRoleNames.getValue())) {
			showMessage(editView, "I00037", "C11156"); // 承認権限ロール略称を入力してください。
			editView.ctrlRoleNames.requestTextFocus();
			return false;
		}

		// 承認権限ロール検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlRoleNamek.getValue())) {
			showMessage(editView, "I00037", "C11157"); // 承認権限ロール検索名称を入力してください。
			editView.ctrlRoleNamek.requestTextFocus();
			return false;
		}

		// 開始年月日
		if (Util.isNullOrEmpty(editView.ctrlBeginDate.getValue())) {
			showMessage(editView, "I00037", editView.ctrlBeginDate.getLabelText()); // 開始年月日を入力してください
			editView.ctrlBeginDate.requestFocus();
			return false;
		}

		// 終了年月日
		if (Util.isNullOrEmpty(editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00037", editView.ctrlEndDate.getLabelText()); // 終了年月日を入力してください
			editView.ctrlEndDate.requestFocus();
			return false;
		}

		// 開始年月日＞終了年月日の場合エラー
		if (!Util.isSmallerThenByYMD(editView.ctrlBeginDate.getValue(), editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00067"); // 開始年月日 <= 終了年月日にしてください。
			editView.ctrlBeginDate.requestTextFocus();
			return false;
		}

		// ユーザーが未選択の場合エラー
		if (Util.isNullOrEmpty(editView.tblLeft.getRowCount()) || editView.tblLeft.getRowCount() == 0) {
			showMessage(editView, "I00041", "C00528"); // ユーザーを選択してください。
			editView.ctrlRoleNamek.requestTextFocus();
			return false;
		}
		return true;
	}

}
