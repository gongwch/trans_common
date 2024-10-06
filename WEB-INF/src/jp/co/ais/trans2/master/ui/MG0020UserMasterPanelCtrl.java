package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.slip.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.ui.MG0020UserMasterPanel.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ユーザーマスタのコントローラ
 * 
 * @author AIS
 */
public class MG0020UserMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0020UserMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0020UserMasterDialog editView = null;

	/** 添付ファイル */
	protected USR_SIGN attachments = null;

	/** 最大ファイルサイズ(MB) */
	public static int maxFileSize = 4;

	/** 拡張子 */
	public static String[] supportFileExts = null;

	/** システム区分一覧 */
	public static String[] sysKbnList = null;

	static {
		try {
			maxFileSize = Util.avoidNullAsInt(ClientConfig.getProperty("trans.usr.sign.use.attachment.max.size"));
		} catch (Throwable e) {
			// 処理なし
		}

		try {
			supportFileExts = ClientConfig.getProperties("trans.usr.sign.attachment.support.file.exts");
		} catch (Throwable e) {
			supportFileExts = new String[] { "gif", "jpg", "jpeg", "png" };
		}
		try {
			sysKbnList = ClientConfig.getProperties("trans.user.master.syskbn.code.list");
		} catch (Throwable e) {
			// 処理なし
		}
	}

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
		mainView = new MG0020UserMasterPanel();
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
		
		initMainVisibleSetting();
	}

	/**
	 * 指示画面 表示設定の切替
	 */
	protected void initMainVisibleSetting() {
		if (!getCompany().getAccountConfig().isUseWorkflowApprove()) {
			mainView.tbl.setColumnWidth(SC.aprv_roleCode, -1);
			mainView.tbl.setColumnWidth(SC.aprv_roleNames, -1);
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

			// 検索条件
			UserSearchCondition condition = getSearchCondition();

			// 開始 <= 終了チェック
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				// 警告メッセージ表示
				showMessage(mainView, "I00068");
				mainView.ctrlUserRange.ctrlUserReferenceFrom.code.requestFocus();
				return;
			}

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// ユーザー情報を取得
			List<User> list = getUser(condition);

			// 検索条件に該当するユーザーが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ユーザー情報を一覧に表示する
			for (User user : list) {
				mainView.tbl.addRow(getRowData(user));
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

			// 編集対象のユーザーを取得する
			User user = getSelectedUser();

			// 編集画面を生成し、編集対象のユーザー情報をセットする
			createEditView();
			initEditView(Mode.MODIFY, user);

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

			// 複写対象のユーザーを取得する
			User user = getSelectedUser();

			// 複写画面を生成し、複写対象のユーザー情報をセットする
			createEditView();
			initEditView(Mode.COPY, user);

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
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除対象のユーザーを取得する
			User user = getSelectedUser();

			// 削除する
			deleteUser(user);

			// 削除したユーザーを一覧から削除
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

			// データ抽出
			UserSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			// ユーザーマスタ
			printer.preview(data, getWord("C02355") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0020UserMasterDialog(mainView.getParentFrame(), true);

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

		// SIGNボタン
		editView.btnSignAttach.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// SIGN添付追加
				try {
					attachFile();
				} catch (IOException e1) {
					//
				}
			}
		});

		// SIGNフィールドのverifyイベント
		editView.ctrlSignAttach.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				try {

					// 値の変更がない
					if (!editView.ctrlSignAttach.isValueChanged()) {
						return true;
					}

					return checkFileName();

				} finally {
					editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}
			}

		});

	}

	/**
	 * @return 変更されたか
	 */
	protected boolean checkFileName() {
		String fileName = editView.ctrlSignAttach.getValue();
		if (Util.isNullOrEmpty(fileName)) {
			attachments = null;
			return true;
		}
		if (!Util.equals(attachments.getFILE_NAME(), editView.ctrlSignAttach.getValue())) {
			editView.ctrlSignAttach.setValue(null);
			attachments = null;
			return false;
		}
		return true;

	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param mode_ 操作モード。
	 * @param user ユーザー。修正、複写の場合は当該ユーザー情報を編集画面にセットする。
	 * @throws TException
	 */
	protected void initEditView(Mode mode_, User user) throws TException {

		this.mode = mode_;

		// システム区分表示
		if (MG0020UserMasterDialog.DASH_BOARD_CONDITION) {
			systemKbnTbl(mode_);
		}
		switchEditViewVisible();

		// 新規の場合
		if (Mode.NEW == mode_) {

			// 新規画面
			editView.setTitle(getWord("C01698"));

			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());

			return;

			// 編集、複写の場合
		} else if (Mode.MODIFY == mode_ || Mode.COPY == mode_) {

			// 編集
			if (Mode.MODIFY == mode_) {
				// 編集画面
				editView.setTitle(getWord("C00977"));
				editView.ctrlCode.setEditable(false);
				editView.setEditMode();
				// 複写
			} else {
				// 複写画面
				editView.setTitle(getWord("C01738"));
			}

			editView.ctrlCode.setValue(user.getCode());
			editView.ctrlName.setValue(user.getName());
			editView.ctrlNames.setValue(user.getNames());
			editView.ctrlNamek.setValue(user.getNamek());
			editView.ctrlPassword.setValue(user.getPassword());
			editView.ctrlProgramRole.setEntity(user.getProgramRole());
			editView.ctrlUserRole.setEntity(user.getUserRole());
			if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
				editView.ctrlAprvGroup.setEntity(user.getAprvRoleGroup());
			}
			if (MG0020UserMasterDialog.USE_BL_SIGNER) {
				editView.ctrlSignerDept.setValue(user.getSignerDept());
				editView.ctrlSignerTitle.setValue(user.getSignerTitle());
				editView.ctrlSignerName.setValue(user.getSignerName());
				editView.ctrlSignAttach.setValue(user.getSignFileName());
				attachments = user.getSignFile();
			}
			editView.ctrlEMailAddress.setValue(user.getEMailAddress());
			editView.cboUpdateAuthorityLevel.setSelectedItemValue(user.getSlipRole());
			editView.cboAccountingPerson.setSelectedItemValue(user.getUserAccountRole());
			editView.ctrlEmployeeReference.setEntity(user.getEmployee());
			editView.ctrlDepartmentReference.setEntity(user.getDepartment());
			editView.ctrlLangcode.setSelectedItemValue(user.getLanguage());
			editView.dtBeginDate.setValue(user.getDateFrom());
			editView.dtEndDate.setValue(user.getDateTo());

			// アクセス権限
			if (MG0020UserMasterDialog.USE_ACCESS_FLAG) {
				for (int i = 0; i < User.ACCESS_FLAG_COUNT; i++) {
					UserAccessFlag e = UserAccessFlag.get(user, i);
					editView.ctrlAccessPermission[i].setSelectedItemValue(e);
				}
			}
		}

	}

	/**
	 * 編集画面コンポーネント表示切替
	 */
	protected void switchEditViewVisible() {
		editView.ctrlAprvGroup.setVisible(getCompany().getAccountConfig().isUseWorkflowApprove());
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

			// 入力されたユーザー情報を取得
			User user = getInputedUser();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entry", user);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(user));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", user);

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(user));

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
	 * 指示画面で入力されたユーザーの検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected UserSearchCondition getSearchCondition() {

		UserSearchCondition condition = createUserSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCodeFrom(mainView.ctrlUserRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlUserRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * Servletクラスを返す
	 * 
	 * @return UserManager
	 */
	protected Class getModelClass() {
		return UserManager.class;
	}

	/**
	 * 編集画面で入力されたユーザーを返す
	 * 
	 * @return 編集画面で入力されたユーザー
	 */
	protected User getInputedUser() {

		User user = createUser();
		user.setCompanyCode(getCompany().getCode());
		user.setCode(editView.ctrlCode.getValue());
		user.setName(editView.ctrlName.getValue());
		user.setNames(editView.ctrlNames.getValue());
		user.setNamek(editView.ctrlNamek.getValue());
		user.setPassword(editView.ctrlPassword.getValue());
		user.setProgramRole(editView.ctrlProgramRole.getEntity());
		user.setUserRole(editView.ctrlUserRole.getEntity() == null ? null : editView.ctrlUserRole.getEntity());
		user.setAprvRoleGroup(editView.ctrlAprvGroup.getEntity() == null ? null : editView.ctrlAprvGroup.getEntity());
		if (MG0020UserMasterDialog.USE_BL_SIGNER) {
			user.setSignerDept(editView.ctrlSignerDept.getValue());
			user.setSignerTitle(editView.ctrlSignerTitle.getValue());
			user.setSignerName(editView.ctrlSignerName.getValue());
			user.setSignFileName(editView.ctrlSignAttach.getValue());
			user.setSignFile(attachments);
		}
		user.setEMailAddress(editView.ctrlEMailAddress.getValue());
		user.setSlipRole((SlipRole) editView.cboUpdateAuthorityLevel.getSelectedItemValue());
		user.setUserAccountRole((UserAccountRole) editView.cboAccountingPerson.getSelectedItemValue());
		user.setEmployee(editView.ctrlEmployeeReference.getEntity());
		user.setDepartment(editView.ctrlDepartmentReference.getEntity());
		user.setLanguage(editView.ctrlLangcode.getSelectedItemValue());
		user.setLanguageName((String) editView.ctrlLangcode.getComboBox().getSelectedItem());
		user.setDateFrom(editView.dtBeginDate.getValue());
		user.setDateTo(editView.dtEndDate.getValue());

		if (MG0020UserMasterDialog.USE_ACCESS_FLAG) {
			int i = 0;
			user.setAccessPermissionFlag1(getAccessPermission(i++));
			user.setAccessPermissionFlag2(getAccessPermission(i++));
			user.setAccessPermissionFlag3(getAccessPermission(i++));
			user.setAccessPermissionFlag4(getAccessPermission(i++));
			user.setAccessPermissionFlag5(getAccessPermission(i++));
			user.setAccessPermissionFlag6(getAccessPermission(i++));
			user.setAccessPermissionFlag7(getAccessPermission(i++));
			user.setAccessPermissionFlag8(getAccessPermission(i++));
			user.setAccessPermissionFlag9(getAccessPermission(i++));
			user.setAccessPermissionFlag10(getAccessPermission(i++));
		}

		if (MG0020UserMasterDialog.DASH_BOARD_CONDITION) {

			List<USR_DASH_CTL> list = new ArrayList<USR_DASH_CTL>();
			for (int i = 0; editView.tblSysState.getRowCount() > i; i++) {

				USR_DASH_CTL bean = new USR_DASH_CTL();
				boolean usrKbn = (Boolean) editView.tblSysState.getRowValueAt(i, MG0020UserMasterDialog.SC.chk);
				String sysKbn = (String) editView.tblSysState.getRowValueAt(i, MG0020UserMasterDialog.SC.sysKbn);

				bean.setKAI_CODE(getCompany().getCode());
				bean.setUSR_CODE(editView.ctrlCode.getValue());
				bean.setSYS_KBN_CODE(sysKbn);
				bean.setUSE_KBN(usrKbn);
				list.add(bean);
			}
			user.setUSR_DASH_CTLList(list);
		}

		return user;

	}

	/**
	 * アクセス権限の登録値を返す
	 * 
	 * @param index
	 * @return アクセス権限の登録値
	 */
	protected int getAccessPermission(int index) {
		UserAccessFlag e = (UserAccessFlag) editView.ctrlAccessPermission[index].getSelectedItemValue();
		if (e == null) {
			return UserAccessFlag.NONE.value;
		}

		return e.value;
	}

	/**
	 * @return ユーザ検索条件
	 */
	protected UserSearchCondition createUserSearchCondition() {
		return new UserSearchCondition();
	}

	/**
	 * @return ユーザ
	 */
	protected User createUser() {
		return new User();
	}

	/**
	 * ユーザー情報を一覧に表示する形式に変換し返す
	 * 
	 * @param user ユーザー
	 * @return 一覧に表示する形式に変換されたユーザー
	 */
	protected Object[] getRowData(User user) {

		Object[] row = new Object[24];
		row[SC.code.ordinal()] = user.getCode();
		row[SC.name.ordinal()] = user.getName();
		row[SC.names.ordinal()] = user.getNames();
		row[SC.namek.ordinal()] = user.getNamek();
		row[SC.program_roleCode.ordinal()] = user.getProgramRole().getCode();
		row[SC.program_roleNames.ordinal()] = user.getProgramRole().getNames();
		row[SC.user_roleCode.ordinal()] = (Util.isNullOrEmpty(user.getUserRole()) ? "" : user.getUserRole().getCode());
		row[SC.user_roleNames.ordinal()] = (Util.isNullOrEmpty(user.getUserRole()) ? ""
			: user.getUserRole().getNames());
		row[SC.aprv_roleCode.ordinal()] = (Util.isNullOrEmpty(user.getAprvRoleGroup()) ? ""
			: user.getAprvRoleGroup().getAPRV_ROLE_GRP_CODE());
		row[SC.aprv_roleNames.ordinal()] = (Util.isNullOrEmpty(user.getAprvRoleGroup()) ? ""
			: user.getAprvRoleGroup().getAPRV_ROLE_GRP_NAME_S());
		row[SC.signerDept.ordinal()] = user.getSignerDept();
		row[SC.signerTitle.ordinal()] = user.getSignerTitle();
		row[SC.signerName.ordinal()] = user.getSignerName();
		row[SC.signFileName.ordinal()] = user.getSignFileName();
		row[SC.eMailAddress.ordinal()] = user.getEMailAddress();
		row[SC.updateAuthority.ordinal()] = getWord(SlipRole.getSlipRoleName(user.getSlipRole()));
		row[SC.isAccountant.ordinal()] = getWord(UserAccountRole.getUserAccountRoleName(user.getUserAccountRole()));
		row[SC.employeeCode.ordinal()] = user.getEmployee().getCode();
		row[SC.employeeNames.ordinal()] = user.getEmployee().getNames();
		row[SC.departmentCode.ordinal()] = user.getDepartment().getCode();
		row[SC.departmentNames.ordinal()] = user.getDepartment().getNames();
		row[SC.language.ordinal()] = user.getLanguageName();
		row[SC.dateFrom.ordinal()] = DateUtil.toYMDString(user.getDateFrom());
		row[SC.dateTo.ordinal()] = DateUtil.toYMDString(user.getDateTo());

		return row;
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
	 * 検索条件に該当するユーザーのリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当するユーザーのリスト
	 * @throws Exception
	 */
	protected List<User> getUser(UserSearchCondition condition) throws Exception {

		List<User> list = (List<User>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * 一覧で選択されているユーザーを返す
	 * 
	 * @return 一覧で選択されているユーザー
	 * @throws Exception
	 */
	protected User getSelectedUser() throws Exception {

		UserSearchCondition condition = createUserSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0020UserMasterPanel.SC.code));

		List<User> list = getUser(condition);

		if (list == null || list.isEmpty()) {
			throw new TException(getMessage("I00193"));
		}
		return list.get(0);

	}

	/**
	 * 指定のユーザーを削除する
	 * 
	 * @param user ユーザー
	 * @throws Exception
	 */
	protected void deleteUser(User user) throws Exception {
		request(getModelClass(), "delete", user);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// ユーザーコードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			// ユーザーコードを入力してください。
			showMessage(editView, "I00037", "C00589");
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		String replacedCode = StringUtil.replaceToUnderscore(editView.ctrlCode.getValue());
		if ((Mode.NEW == mode || Mode.COPY == mode) && !Util.equals(editView.ctrlCode.getValue(), replacedCode)) {
			// {ユーザーコード}は英数字(0-9)、小文字(a-z)、大文字(A-Z)、記号($, _)以外は使用できません。
			showMessage(editView, "I00900", editView.ctrlCode.getLabelText());
			return false;
		}

		// ユーザー名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlName.getValue())) {
			// ユーザー名称を入力してください。
			showMessage(editView, "I00037", "C00691");
			editView.ctrlName.requestTextFocus();
			return false;
		}

		// ユーザー略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlNames.getValue())) {
			// ユーザー略称を入力してください。
			showMessage(editView, "I00037", "C00692");
			editView.ctrlNames.requestTextFocus();
			return false;
		}

		// ユーザー検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlNamek.getValue())) {
			// ユーザー検索名称を入力してください。
			showMessage(editView, "I00037", "C00693");
			editView.ctrlNamek.requestTextFocus();
			return false;
		}

		// パスワードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlPassword.getValue())) {
			showMessage(editView, "I00139");
			editView.ctrlPassword.requestTextFocus();
			return false;
		}

		// プログラムロールコードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlProgramRole.getCode())) {
			// プログラムロールコードを入力してください。
			showMessage(editView, "I00037", "C11159");
			editView.ctrlProgramRole.requestFocus();
			return false;
		}
		// 表示時、承認グループが未入力の場合エラー
		if (editView.ctrlAprvGroup.isVisible() && editView.ctrlAprvGroup.isEmpty()) {
			showMessage(editView, "I00037", editView.ctrlAprvGroup.btn.getText());
			editView.ctrlAprvGroup.requestFocus();
			return false;
		}
		// EMail Addressがメールアドレス形式(@.*)以外の場合エラー
		if (!Util.isNullOrEmpty(editView.ctrlEMailAddress.getValue())) {
			String email = editView.ctrlEMailAddress.getValue();
			if (!checkMailAddressByRegularExpression(email)) {
				// 正しいメールアドレスを入力してください。
				showMessage(editView, "I00789");
				editView.ctrlEMailAddress.requestTextFocus();
				return false;
			}
		}

		// 社員コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlEmployeeReference.getCode())) {
			// 社員コードを入力してください。
			showMessage(editView, "I00037", "C00697");
			editView.ctrlEmployeeReference.requestFocus();
			return false;
		}

		// 部門コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDepartmentReference.getCode())) {
			// 部門コードを入力してください。
			showMessage(editView, "I00037", "C00698");
			editView.ctrlDepartmentReference.requestFocus();
			return false;
		}

		// 開始年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			// 開始年月日を入力してください。
			showMessage(editView, "I00037", "C00055");
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// 終了年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			// 終了年月日を入力してください。
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
		// 承認権限設定時必須
		if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
			if (editView.ctrlAprvGroup.isEmpty()) {
				showMessage(editView, "I00037", editView.ctrlAprvGroup.btn.getLangMessageID());
				editView.ctrlAprvGroup.requestTextFocus();
				return false;
			}
		}

		// 新規、複写の場合は同一ユーザーが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {
			UserSearchCondition condition = createUserSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlCode.getValue());

			List<User> list = getUser(condition);

			if (list != null && !list.isEmpty()) {
				// 指定のユーザーは既に存在します。
				showMessage(editView, "I00229");
				editView.ctrlCode.requestTextFocus();
				return false;
			}
		}

		return true;

	}

	/**
	 * メールアドレスチェック
	 * 
	 * @param address チェック対象のアドレス
	 * @return 正しいメールアドレスはtrue *@*.*形式以外はfalse
	 */
	public static boolean checkMailAddressByRegularExpression(String address) {

		String aText = "[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^\\$\\-\\|]";
		String dotAtom = aText + "+" + "(\\." + aText + "+)*";
		String regularExpression = "^" + dotAtom + "@" + dotAtom + "$";
		Pattern pattern = Pattern.compile(regularExpression);
		Matcher matcher = pattern.matcher(address);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	/**
	 * 添付エンティティの作成
	 * 
	 * @param filename
	 * @param file
	 * @return 添付
	 */
	protected USR_SIGN createEntity(String filename, File file) {
		USR_SIGN bean = createBean();
		if (!Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			bean.setUSR_CODE(editView.ctrlCode.getValue());
		}
		bean.setINP_DATE(Util.getCurrentDate()); // 新規
		bean.setFILE_NAME(filename);
		bean.setFile(file);

		// 必須情報
		bean.setKAI_CODE(getCompanyCode());
		bean.setPRG_ID(getProgramCode());
		bean.setUSR_ID(getUserCode());

		return bean;
	}

	/**
	 * @return エンティティ作成
	 */
	protected USR_SIGN createBean() {
		return new USR_SIGN();
	}

	/**
	 * ファイル追加
	 * 
	 * @throws IOException
	 */
	protected void attachFile() throws IOException {
		// ファイル選択ダイアログを開く
		TFileChooser fc = new TFileChooser();
		fc.setAcceptAllFileFilterUsed(false); // 全てのファイルは選択不可
		fc.setMultiSelectionEnabled(true); // 複数選択可能

		File dir = getPreviousFolder(".chooser");

		// 前回のフォルダを復元
		if (dir != null) {
			fc.setCurrentDirectory(dir);
		}

		// 添付ファイルフィルター
		TFileFilter filter = new TFileFilter(getWord("C11613"), supportFileExts);

		fc.setFileFilter(filter);
		if (TFileChooser.FileStatus.Selected != fc.show(editView)) {
			return;
		}

		TFile[] tFiles = fc.getSelectedTFiles();

		// 指定ファイルを添付する
		addAllFiles(tFiles);
	}

	/**
	 * @return FolderKeyNameを戻します。
	 */
	protected String getFolderKeyName() {
		return "usr.sign.attachment";
	}

	/**
	 * 前回保存したフォルダ情報を返す。
	 * 
	 * @param type 種類
	 * @return 前回保存したフォルダ情報
	 */
	protected File getPreviousFolder(String type) {

		String path = SystemUtil.getAisSettingDir();
		File dir = (File) FileUtil.getObject(path + File.separator + getFolderKeyName() + type);

		return dir;
	}

	/**
	 * 指定のフォルダ情報を保存する
	 * 
	 * @param dir フォルダ情報
	 */
	protected void saveFolder(File dir) {
		String path = SystemUtil.getAisSettingDir();
		FileUtil.saveObject(dir, path + File.separator + getFolderKeyName() + ".chooser");
	}

	/**
	 * 指定ファイルを添付する
	 * 
	 * @param tFiles
	 * @return true 追加成功
	 * @throws IOException
	 */
	protected boolean addAllFiles(TFile[] tFiles) throws IOException {
		if (tFiles == null || tFiles.length == 0) {
			return false;
		}

		File[] files = new File[tFiles.length];
		for (int i = 0; i < tFiles.length; i++) {
			files[i] = tFiles[i].getFile();
		}

		return addAllFiles(files);
	}

	/**
	 * 指定ファイルを添付する
	 * 
	 * @param tFiles
	 * @return true 追加成功
	 * @throws IOException
	 */
	protected boolean addAllFiles(File[] tFiles) throws IOException {
		if (tFiles == null || tFiles.length == 0) {
			return false;
		}

		for (int i = 0; i < tFiles.length; i++) {
			File file = tFiles[i];

			String path = file.getPath();
			String filename = file.getName();

			byte[] data = null;

			try {
				data = ResourceUtil.zipBinary(filename, ResourceUtil.readBinarry(path));
			} catch (FileNotFoundException e) {
				showMessage(e.getMessage());
				return false;
			}

			if (data != null && data.length > 1024 * 1024 * TAttachButtonCtrl.maxFileSize) {
				// ファイルサイズが4MBを超えています。
				showMessage("I00446", TAttachButtonCtrl.maxFileSize);
				return false;
			}

			if (data == null) {
				// ファイルが存在してない場合、処理不要
				return false;
			}

            // 特殊符号チェック
            String chk = new String(filename.getBytes(), "SHIFT-JIS");
            if (chk.contains("?")) { 
                String c = chk.replaceAll("\\?", "<b><font color=red>?</font></b>");
                // "<html>ファイル名に特殊文字が含まれています。<br>" + c + "<html>"
                showMessage("I01135", c);
                return false;
            }

			addFile(filename, file);

			if (i == 0) {
				// フォルダ記憶
				saveFolder(file);
			}
		}

		return true;
	}

	/**
	 * @param filename
	 * @param file
	 */
	protected void addFile(String filename, File file) {

		attachments = createEntity(filename, file);
		editView.ctrlSignAttach.setValue(filename);
		setData(attachments);

	}

	/**
	 * データ設定
	 * 
	 * @param bean
	 */
	public void setData(USR_SIGN bean) {

		// ローカルに保存する
		try {
			TPrinter printer = new TPrinter(true);

			if (bean.getFileData() != null) {
				byte[] data = (byte[]) bean.getFileData();

				String tempFileName = printer.createResultFile(bean.getFILE_NAME(), data);
				File file = new File(tempFileName);
				bean.setFile(file);
				bean.setFileData(null); // バイナリをクリア
			}
		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * システム区分のテーブル表示
	 * 
	 * @param mode_
	 * @throws TException
	 */
	public void systemKbnTbl(Mode mode_) throws TException {
		List<String> sysList = Arrays.asList(sysKbnList);
		UserSearchCondition condition = createUserSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode("");
		if (Mode.MODIFY == mode_ || Mode.COPY == mode_) {
			condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0020UserMasterPanel.SC.code));
		}
		condition.setSysKbnList(sysList);

		List<USR_DASH_CTL> list = (List<USR_DASH_CTL>) request(getModelClass(), "getDashBoardSysKbn", condition);

		for (USR_DASH_CTL bean : list) {
			editView.tblSysState.addRow(systemListAdd(bean));
		}

	}

	/**
	 * システム区分一覧テーブルにadd
	 * 
	 * @param bean
	 * @return list
	 */
	public List<Object> systemListAdd(USR_DASH_CTL bean) {

		List<Object> list = new ArrayList<Object>();

		list.add(bean.getSYS_KBN_CODE());
		list.add(bean.isUSE_KBN());
		list.add(bean.getSYS_KBN_NAME());

		return list;

	}
}
