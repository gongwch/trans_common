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
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.program.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ユーザーロールマスタ CTRL
 * 
 * @author AIS
 */
public class MG0270UserRoleMasterPanelCtrl extends TController {

	/**
	 * 指示画面
	 */
	protected MG0270UserRoleMasterPanel mainView = null;

	/**
	 * 編集画面
	 */
	protected MG0270UserRoleMasterDialog editView = null;

	/**
	 * 操作モード。現在操作中のモードを把握するために使用する。
	 */
	protected Mode mode = null;

	/**
	 * 修正前のデータ
	 */
	protected UserRole editBean = null;

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

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MG0270UserRoleMasterPanel();
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

			// 一覧をクリアし、メインボタンを押下不可能にする
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// 開始コード<=終了コードにしてください。
			if (Util.isSmallerThen(mainView.ctrlRange.getCodeFrom(), mainView.ctrlRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlRange.getFieldFrom().requestFocus();
				return;
			}

			// プログラム情報を取得
			UserSearchCondition condition = getSearchCondition();
			List<UserRole> list = getList(condition);

			// 検索条件に該当するプログラムが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// プログラム情報を一覧に表示する
			for (UserRole bean : list) {
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

			// 編集対象のプログラムを取得する
			UserRole bean = getSelectedRowData();

			// 編集画面を生成し、編集対象のプログラム情報をセットする
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

			// 複写対象のプログラムを取得する
			UserRole bean = getSelectedRowData();

			// 編集前データを削除
			editBean = null;

			// 複写画面を生成し、複写対象のプログラム情報をセットする
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

			// 確認メッセージ
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			// 削除対象のプログラムを取得する
			UserRole bean = getSelectedRowData();

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
	 * [エクセル]ボタン押下
	 */
	protected void btnExcel_Click() {

		try {

			// 確認メッセージ
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// プログラム情報を取得
			UserSearchCondition condition = getSearchCondition();
			condition.setLang(super.getUser().getLanguage());

			// データ抽出
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11164") + ".xls");// ユーザーロールマスタ

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0270UserRoleMasterDialog(getCompany(), mainView.getParentFrame(), true);

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
	 * @param editMode 操作モード。
	 * @param bean プログラム。修正、複写の場合は当該プログラム情報を編集画面にセットする。
	 * @throws TException
	 */
	protected void initEditView(Mode editMode, UserRole bean) throws TException {

		this.mode = editMode;

		// 新規の場合
		if (Mode.NEW == editMode) {

			editView.setTitle(getWord("C01698"));// 新規画面
			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());
			editBean = null;// 編集前データを削除

			// 編集、複写の場合
		} else if (Mode.MODIFY == editMode || Mode.COPY == editMode) {

			// 編集
			if (Mode.MODIFY == editMode) {
				editView.setTitle(getWord("C00977"));// 編集画面
				editView.ctrlProgramCode.setEditable(false);
				editView.setEditMode();
				editBean = bean;// 編集前データを保持
				// 複写
			} else {
				editView.setTitle(getWord("C01738"));// 複写画面
				editBean = null;// 編集前データを削除
			}

			editView.ctrlProgramCode.setValue(bean.getCode());
			editView.ctrlProgramName.setValue(bean.getName());
			editView.ctrlProgramNames.setValue(bean.getNames());
			editView.ctrlProgramNamek.setValue(bean.getNamek());
			editView.dtBeginDate.setValue(bean.getTermFrom());
			editView.dtEndDate.setValue(bean.getTermTo());

		}

		// 一覧に表示するデータを取得
		List<Item> itemList = getItemList();
		List<Department> depList = getDepartmentList();

		// 科目開示レベル一覧の設定
		for (Item data : itemList) {
			editView.itemTbl.addRow(getItemRow(data, bean));
		}

		// 部門開示レベル一覧の設定
		for (Department data : depList) {
			editView.depTbl.addRow(getDepRow(data, bean));
		}

	}

	/**
	 * 科目一覧に設定するデータを取得
	 * 
	 * @param bean
	 * @param usrBean
	 * @return 設定データ
	 */
	protected String[] getItemRow(Item bean, UserRole usrBean) {

		String div = "0";

		// データが存在する場合は一致データを検索
		if (usrBean != null) {

			// 一致する科目のデータが存在する場合、その値を優先
			for (RoleItemLevel lvl : usrBean.getItemLvlList()) {
				if (lvl.getItemCode().equals(bean.getCode())) {
					div = String.valueOf(lvl.getDepDivision());
					break;
				}
			}
		}

		return new String[] { div, bean.getCode(), bean.getNames() };
	}

	/**
	 * 部門一覧に設定するデータを取得
	 * 
	 * @param bean
	 * @param usrBean
	 * @return 設定データ
	 */
	protected List<Object> getDepRow(Department bean, UserRole usrBean) {

		boolean div = false;
		List<Object> addList = new ArrayList<Object>();

		// データが存在する場合は一致データを検索
		if (usrBean != null) {

			// 一致する科目のデータが存在する場合、その値を優先
			for (RoleDepartmentLevel lvl : usrBean.getDepLvlList()) {
				if (lvl.getDepCode().equals(bean.getCode())) {
					div = BooleanUtil.toBoolean(lvl.getDepDivision());
					break;
				}
			}
		}
		addList.add(div);
		addList.add(bean.getCode());
		addList.add(bean.getNames());

		return addList;
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

			// 入力されたプログラム情報を取得
			UserRole bean = getInputedData();

			// 新規登録の場合
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// 新規登録
				request(getModelClass(), "entry", bean);

				// 追加分を一覧に反映する
				mainView.tbl.addRow(getRowData(bean));

				// メインボタンを押下可能にする
				setMainButtonEnabled(true);

				// 修正の場合
			} else if (Mode.MODIFY == mode) {

				// 修正
				request(getModelClass(), "modify", bean);

				// 修正内容を一覧に反映する
				mainView.tbl.modifySelectedRow(getRowData(bean));

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
	 * 指示画面で入力されたプログラムの検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected UserSearchCondition getSearchCondition() {

		UserSearchCondition condition = new UserSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setCodeFrom(mainView.ctrlRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * Servletクラスを返す
	 * 
	 * @return クラス
	 */
	protected Class getModelClass() {
		return UserRoleManager.class;
	}

	/**
	 * 編集画面で入力されたプログラムを返す
	 * 
	 * @return 編集画面で入力されたプログラム
	 */
	protected UserRole getInputedData() {

		UserRole bean = new UserRole();
		bean.setCompanyCode(TLoginInfo.getCompany().getCode());
		bean.setCode(editView.ctrlProgramCode.getValue());
		bean.setName(editView.ctrlProgramName.getValue());
		bean.setNames(editView.ctrlProgramNames.getValue());
		bean.setNamek(editView.ctrlProgramNamek.getValue());
		bean.setTermFrom(editView.dtBeginDate.getValue());
		bean.setTermTo(editView.dtEndDate.getValue());
		if (editBean != null) {
			bean.setInpDate(editBean.getInpDate());
		}
		bean.setUsrId(getUser().getCode());

		// 科目開示レベルの取得
		for (int i = 0; i < editView.itemTbl.getRowCount(); i++) {

			// 科目コードの設定
			RoleItemLevel lvl = new RoleItemLevel();
			lvl.setItemCode(Util.avoidNull(editView.itemTbl.getRowValueAt(i, MG0270UserRoleMasterDialog.SC.CODE)));

			// 開示レベルの設定
			String div = Util.avoidNull(editView.itemTbl.getRowValueAt(i, MG0270UserRoleMasterDialog.SC.DIVISION));
			lvl.setDepDivision(Integer.valueOf(div));

			bean.addItemLvlList(lvl);
		}

		// 部門開示レベルの取得
		for (int i = 0; i < editView.depTbl.getRowCount(); i++) {

			// 部門コードの設定
			RoleDepartmentLevel lvl = new RoleDepartmentLevel();
			lvl.setDepCode(Util.avoidNull(editView.depTbl.getRowValueAt(i, MG0270UserRoleMasterDialog.SC.CODE)));

			// 開示レベルの設定
			boolean div = (Boolean) editView.depTbl.getRowValueAt(i, MG0270UserRoleMasterDialog.SC.DIVISION);
			lvl.setDepDivision(BooleanUtil.toInt(div));

			bean.addDepLvlList(lvl);
		}

		return bean;

	}

	/**
	 * エンティティを一覧に表示する形式に変換し返す
	 * 
	 * @param bean データ
	 * @return 一覧に表示する形式に変換されたプログラム
	 */
	protected String[] getRowData(UserRole bean) {

		return new String[] { bean.getCode(), bean.getName(), bean.getNames(), bean.getNamek(),
				DateUtil.toYMDString(bean.getTermFrom()), DateUtil.toYMDString(bean.getTermTo()) };
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
	 * 検索条件に該当するリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当するリスト
	 * @throws Exception
	 */
	protected List<UserRole> getList(UserSearchCondition condition) throws Exception {

		List<UserRole> list = (List<UserRole>) request(getModelClass(), "get", condition);
		return list;

	}

	/**
	 * 一覧で選択されているプログラムを返す
	 * 
	 * @return 一覧で選択されているプログラム
	 * @throws Exception
	 */
	protected UserRole getSelectedRowData() throws Exception {

		UserSearchCondition condition = new UserSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0270UserRoleMasterPanel.SC.code));

		List<UserRole> list = getList(condition);

		if (list == null || list.isEmpty()) {
			// 選択されたデータは他ユーザーにより削除されました
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * 指定のプログラムを削除する
	 * 
	 * @param bean マスタ
	 * @throws Exception
	 */
	protected void doDelete(UserRole bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlProgramCode.getValue())) {
			showMessage(editView, "I00037", "C11154");// ロールコード
			editView.ctrlProgramCode.requestTextFocus();
			return false;
		}

		// 新規、複写の場合は同一プログラムが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {

			UserSearchCondition condition = new UserSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlProgramCode.getValue());

			List<UserRole> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00055", "C11154");// ロールコード
				editView.ctrlProgramCode.requestTextFocus();
				return false;
			}
		}

		// 名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlProgramName.getValue())) {
			showMessage(editView, "I00037", "C11155");// ロール名称
			editView.ctrlProgramName.requestTextFocus();
			return false;
		}

		// 略称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlProgramNames.getValue())) {
			showMessage(editView, "I00037", "C11156");// ロール略称
			editView.ctrlProgramNames.requestTextFocus();
			return false;
		}

		// 検索名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlProgramNamek.getValue())) {
			showMessage(editView, "I00037", "C11157");// ロール検索名称
			editView.ctrlProgramNamek.requestFocus();
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

		return true;

	}

	/**
	 * 科目コード一覧を取得する
	 * 
	 * @return プログラム一覧
	 * @throws TException
	 */
	protected List<Item> getItemList() throws TException {

		ItemSearchCondition condition = new ItemSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setSumItem(false);
		condition.setTitleItem(false);
		List<Item> list = (List<Item>) request(ItemManager.class, "get", condition);

		return list;

	}

	/**
	 * 部門一覧を取得する
	 * 
	 * @return プログラム一覧
	 * @throws TException
	 */
	protected List<Department> getDepartmentList() throws TException {

		DepartmentSearchCondition condition = new DepartmentSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());

		List<Department> list = (List<Department>) request(DepartmentManager.class, "get", condition);

		return list;

	}

	/**
	 * プログラム情報を一覧に表示する形式に変換し返す
	 * 
	 * @param program プログラム
	 * @return 一覧に表示する形式に変換されたプログラム
	 */
	protected String[] getRowData(Program program) {
		return new String[] { program.getSystemClassification().getCode(), program.getCode(), program.getNames() };
	}

}
