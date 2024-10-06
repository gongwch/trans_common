package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.tree.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.tree.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.program.*;
import jp.co.ais.trans2.model.security.*;

/**
 * プログラムロールマスタ CTRL
 * 
 * @author AIS
 */
public class MG0260ProgramRoleMasterPanelCtrl extends TController {

	/**
	 * 指示画面
	 */
	protected MG0260ProgramRoleMasterPanel mainView = null;

	/**
	 * 編集画面
	 */
	protected MG0260ProgramRoleMasterDialog editView = null;

	/**
	 * 操作モード。現在操作中のモードを把握するために使用する。
	 */
	protected Mode mode = null;

	/**
	 * 修正前のデータ
	 */
	protected ProgramRole editBean = null;

	/**
	 * プログラムリスト
	 */
	protected List<Program> editPrgList = null;

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
		mainView = new MG0260ProgramRoleMasterPanel();
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
			ProgramRoleSearchCondition condition = getSearchCondition();
			List<ProgramRole> list = getList(condition);

			// 検索条件に該当するプログラムが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// プログラム情報を一覧に表示する
			for (ProgramRole bean : list) {
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
			ProgramRole bean = getSelectedRowData();

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
			ProgramRole bean = getSelectedRowData();

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
			ProgramRole bean = getSelectedRowData();

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
			ProgramRoleSearchCondition condition = getSearchCondition();
			condition.setLang(super.getUser().getLanguage());

			// データ抽出
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11153") + ".xls");// プログラムロールマスタ

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0260ProgramRoleMasterDialog(getCompany(), mainView.getParentFrame(), true);

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

		// [←(プログラム追加)]ボタン押下
		editView.btnAddProgram.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnAddProgram_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [←(プログラム追加)]ボタン押下
		editView.btnDeleteProgram.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDeleteProgram_Click();
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
	protected void initEditView(Mode editMode, ProgramRole bean) throws TException {

		this.mode = editMode;
		editPrgList = getProgramList();

		// 新規の場合
		if (Mode.NEW == editMode) {

			editView.setTitle(getWord("C01698"));// 新規画面
			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());
			editBean = null;// 編集前データを削除

			// プログラムを候補一覧に追加する
			for (Program program : editPrgList) {
				editView.tbl.addRow(getRowData(program));
			}

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

			// 選択プログラム一覧を設定する
			addSelectedPrg(bean.getProgramList());

			// プログラムを候補一覧に追加する
			for (Program program : getCandidatePrgList(bean.getProgramList())) {
				editView.tbl.addRow(getRowData(program));
			}

		}

	}

	/**
	 * [←(プログラム追加)]ボタン押下
	 */
	protected void btnAddProgram_Click() {

		try {

			// 選択されたプログラム一覧を取得
			addSelectedPrg(getSelectedCandidatePrgList());

			// プログラム候補一覧を初期化
			editView.tbl.removeRow();

			// プログラム候補一覧を再設定
			for (Program bean : getCandidatePrgList(getSelectedPrgList(false))) {
				editView.tbl.addRow(getRowData(bean));
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [→(プログラム除去)]ボタン押下
	 */
	protected void btnDeleteProgram_Click() {

		try {

			int[] selectedRow = editView.prgPnl.tree.getSelectionRows();

			if (selectedRow == null) {
				return;
			}

			List<Program> list = new ArrayList<Program>();
			DefaultMutableTreeNode node = editView.prgPnl.getRoot();

			// 各ノードから選択行以外のデータを取得
			for (int i = 0; i < node.getChildCount(); i++) {

				boolean isSelected = false;
				for (int j = 0; j < selectedRow.length; j++) {

					if (selectedRow[j] == i) {
						isSelected = true;
					}

				}

				// 選択されたデータは追加しない
				if (isSelected) {
					continue;
				}

				DefaultMutableTreeNode cNode = (DefaultMutableTreeNode) node.getChildAt(i);
				TTreeItem item = (TTreeItem) cNode.getUserObject();
				list.add((Program) item.getObj());
			}

			// プログラム選択一覧を初期化
			editView.prgPnl.getRoot().removeAllChildren();

			// プログラム選択一覧を再設定
			addSelectedPrg(list);

			// プログラム候補一覧を初期化
			editView.tbl.removeRow();

			// プログラム候補一覧を再設定
			for (Program bean : getCandidatePrgList(getSelectedPrgList(false))) {
				editView.tbl.addRow(getRowData(bean));
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 編集画面 選択PG一覧にデータを追加する
	 * 
	 * @param list
	 */
	protected void addSelectedPrg(List<Program> list) {

		DefaultMutableTreeNode node = editView.prgPnl.getRoot();

		for (Program program : list) {
			TTreeItem item = new TTreeItem(program.getNames(), program);
			DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(item);
			node.add(treeNode);
		}

		editView.prgPnl.tree.expandAll();
		editView.prgPnl.tree.reload();

	}

	/**
	 * 選択されたプログラム候補一覧を返す
	 * 
	 * @return 選択行リスト
	 */
	protected List<Program> getSelectedCandidatePrgList() {

		List<Program> list = new ArrayList<Program>();

		int[] rows = editView.tbl.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			Program program = new Program();
			program.setCode((String) editView.tbl
				.getRowValueAt(rows[i], MG0250ProgramDisplayMasterPanel.SC.programCode));
			program.setNames((String) editView.tbl.getRowValueAt(rows[i],
				MG0250ProgramDisplayMasterPanel.SC.programNames));
			list.add(program);
		}

		return list;
	}

	/**
	 * 選択プログラム一覧のデータを取得する
	 * 
	 * @param isDistinct 重複データを除去するかどうか
	 * @return 選択PRGリスト
	 */
	protected List<Program> getSelectedPrgList(boolean isDistinct) {

		List<Program> list = new ArrayList<Program>();
		DefaultMutableTreeNode node = editView.prgPnl.getRoot();

		// 各ノードからデータを取得
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode cNode = (DefaultMutableTreeNode) node.getChildAt(i);
			TTreeItem item = (TTreeItem) cNode.getUserObject();
			Program addPrg = (Program) item.getObj();
			if (isDistinct) {

				boolean isExist = false;
				for (Program prg : list) {
					if (addPrg.getCode().equals(prg.getCode())) {
						isExist = true;
						break;
					}
				}

				if (isExist) {
					continue;
				}
			}
			list.add(addPrg);
		}

		return list;
	}

	/**
	 * プログラム候補一覧を取得する
	 * 
	 * @param selectedlist
	 * @return 選択行リスト
	 */
	protected List<Program> getCandidatePrgList(List<Program> selectedlist) {

		List<Program> list = new ArrayList<Program>();

		for (Program bean : editPrgList) {

			boolean isExist = false;
			for (Program tmpBean : selectedlist) {
				if (bean.getCode().equals(tmpBean.getCode())) {
					isExist = true;
				}
			}

			if (isExist) {
				continue;
			}

			list.add(bean);

		}

		return list;
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
			ProgramRole bean = getInputedData();

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
	protected ProgramRoleSearchCondition getSearchCondition() {

		ProgramRoleSearchCondition condition = new ProgramRoleSearchCondition();
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
		return ProgramRoleManager.class;
	}

	/**
	 * 編集画面で入力されたプログラムを返す
	 * 
	 * @return 編集画面で入力されたプログラム
	 */
	protected ProgramRole getInputedData() {

		ProgramRole bean = new ProgramRole();
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
		bean.setPrgCode(getProgramCode());
		bean.setUsrId(getUser().getCode());
		bean.setProgramList(getSelectedPrgList(true));
		return bean;

	}

	/**
	 * エンティティを一覧に表示する形式に変換し返す
	 * 
	 * @param bean データ
	 * @return 一覧に表示する形式に変換されたプログラム
	 */
	protected String[] getRowData(ProgramRole bean) {

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
	protected List<ProgramRole> getList(ProgramRoleSearchCondition condition) throws Exception {

		List<ProgramRole> list = (List<ProgramRole>) request(getModelClass(), "get", condition);
		return list;

	}

	/**
	 * 一覧で選択されているプログラムを返す
	 * 
	 * @return 一覧で選択されているプログラム
	 * @throws Exception
	 */
	protected ProgramRole getSelectedRowData() throws Exception {

		ProgramRoleSearchCondition condition = new ProgramRoleSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0260ProgramRoleMasterPanel.SC.code));

		List<ProgramRole> list = getList(condition);

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
	protected void doDelete(ProgramRole bean) throws Exception {
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
			editView.ctrlProgramCode.requestFocus();
			return false;
		}

		// 新規、複写の場合は同一プログラムが既に存在したらエラー
		if (Mode.NEW == mode || Mode.COPY == mode) {

			ProgramRoleSearchCondition condition = new ProgramRoleSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlProgramCode.getValue());

			List<ProgramRole> list = getList(condition);

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
			editView.ctrlProgramNamek.requestTextFocus();
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

		DefaultMutableTreeNode node = editView.prgPnl.getRoot();
		if (node.getChildCount() == 0) {
			// 一覧から使用プログラムを選択してください
			showMessage(editView, "I00254");
			return false;
		}

		return true;

	}

	/**
	 * プログラム一覧を取得する
	 * 
	 * @return プログラム一覧
	 * @throws TException
	 */
	protected List<Program> getProgramList() throws TException {

		ProgramSearchCondition condition = new ProgramSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setExecutable(true);
		condition.setNotExecutable(false);

		List<Program> list = (List<Program>) request(ProgramManager.class, "get", condition);

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
