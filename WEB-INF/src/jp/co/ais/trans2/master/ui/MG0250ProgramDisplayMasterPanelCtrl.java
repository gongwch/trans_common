package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.tree.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.tree.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.master.ui.MG0250ProgramDisplayMasterPanel.TProgramMenuPanel;
import jp.co.ais.trans2.model.program.*;

/**
 * プログラム表示設定マスタのコントローラ
 * 
 * @author AIS
 */
public class MG0250ProgramDisplayMasterPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0250ProgramDisplayMasterPanel mainView = null;

	/** 新規タブ登録画面 */
	protected MG0250ProgramDisplayMasterTabDialog tabDialog = null;

	/**
	 * タブ編集モード
	 * 
	 * @author AIS
	 */
	protected enum TabMode {
		/** NEW */
		NEW,
		/** MODIFY */
		MODIFY
	}

	/** タブ編集のモード */
	protected TabMode mode = null;

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
		mainView = new MG0250ProgramDisplayMasterPanel();
		addMainViewEvent();
	}

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createProgramDisplayMasterTabDialog() {
		tabDialog = new MG0250ProgramDisplayMasterTabDialog(getCompany(), mainView.getParentFrame(), true);
		addNewTabDialogEvent();
	}

	/**
	 * 指示画面のイベント定義。
	 */
	protected void addMainViewEvent() {

		// [タブ追加]ボタン押下
		mainView.btnAddTab.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnAddTab_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [タブ修正]ボタン押下
		mainView.btnModifyTab.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModifyTab_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [タブ削除]ボタン押下
		mainView.btnDeleteTab.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDeleteTab_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [確定]ボタン押下
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [←(プログラム追加)]ボタン押下
		mainView.btnAddProgram.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnAddProgram_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * 指示画面を初期化する
	 * 
	 * @throws TException
	 */
	protected void initMainView() throws TException {

		mainView.menuTab.setTabPlacement(SwingConstants.LEFT);
		mainView.menuTab.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		mainView.tbl.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// プログラム情報を取得
		ProgramSearchCondition condition = getSearchCondition();
		List<Program> list = getProgram(condition);

		// 検索条件に該当するプログラムが存在しない場合、メッセージを表示して終了
		if (list == null || list.isEmpty()) {
			showMessage(mainView.getParentFrame(), "I00022");
			return;
		}

		// プログラム情報を一覧に表示する
		for (Program program : list) {
			mainView.tbl.addRow(getRowData(program));
		}

	}

	/**
	 * 新規タブ登録画面のイベント定義
	 */
	protected void addNewTabDialogEvent() {

		// [確定]ボタン押下
		tabDialog.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				newTabDialog_btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [戻る]ボタン押下
		tabDialog.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				newTabDialog_btnClose_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * 指示画面[タブ追加]ボタン押下
	 */
	protected void btnAddTab_Click() {

		try {

			mode = TabMode.NEW;
			createProgramDisplayMasterTabDialog();
			tabDialog.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面[タブ修正]ボタン押下
	 */
	protected void btnModifyTab_Click() {

		try {

			mode = TabMode.MODIFY;
			createProgramDisplayMasterTabDialog();
			tabDialog.ctrlProgramGroupName.setValue(getSelectedTabPanel().getTitle());

			tabDialog.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面[タブ削除]ボタン押下
	 */
	protected void btnDeleteTab_Click() {

		try {

			TProgramMenuPanel pnl = getSelectedTabPanel();
			pnl.setTitle(tabDialog.ctrlProgramGroupName.getValue());
			int index = mainView.menuTab.indexOfComponent(pnl);
			mainView.menuTab.removeTabAt(index);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 指示画面[確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00004")) {
				return;
			}

			// 登録
			List<SystemizedProgram> list = getInputedSystemizedProgram();
			entrySystemizedProgram(list);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [←(プログラム追加)]ボタン押下
	 */
	protected void btnAddProgram_Click() {

		try {

			// 選択されたプログラム一覧を取得
			List<Program> list = getSelectedPrograms();

			// 左側のメニューに追加
			TProgramMenuPanel pnl = (TProgramMenuPanel) mainView.menuTab.getSelectedComponent();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) pnl.tree.getLastSelectedPathComponent();
			if (node == null) {
				node = pnl.getRoot();
			}

			for (Program program : list) {
				TTreeItem item = new TTreeItem(program.getNames(), program);
				DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(item);
				node.add(treeNode);

			}
			pnl.tree.expandAll();
			pnl.tree.reload();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * タブ設定ダイアログ[確定]ボタン押下
	 */
	protected void newTabDialog_btnSettle_Click() {

		// 新規の場合
		if (TabMode.NEW == mode) {
			mainView.menuTab.add(getTabPanel());

			// 修正の場合
		} else {
			TProgramMenuPanel pnl = getSelectedTabPanel();
			pnl.setTitle(tabDialog.ctrlProgramGroupName.getValue());
			int index = mainView.menuTab.indexOfComponent(pnl);
			mainView.menuTab.removeTabAt(index);
			mainView.menuTab.add(pnl, index);
			mainView.menuTab.setSelectedIndex(index);
		}
		tabDialog.setVisible(false);
		tabDialog.dispose();
	}

	/**
	 * タブ設定ダイアログ[戻る]ボタン押下
	 */
	protected void newTabDialog_btnClose_Click() {
		tabDialog.setVisible(false);
		tabDialog.dispose();
	}

	/**
	 * 新規タブを返す
	 * 
	 * @return 新規タブ
	 */
	protected TProgramMenuPanel getTabPanel() {

		TProgramMenuPanel panel = mainView.new TProgramMenuPanel();
		panel.setTitle(tabDialog.ctrlProgramGroupName.getValue());

		return panel;
	}

	/**
	 * プログラム一覧を返す
	 * 
	 * @param condition
	 * @return List<Program>
	 * @throws TException
	 */
	@SuppressWarnings("unchecked")
	protected List<Program> getProgram(ProgramSearchCondition condition) throws TException {

		List<Program> list = (List<Program>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * プログラムの検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected ProgramSearchCondition getSearchCondition() {

		ProgramSearchCondition condition = new ProgramSearchCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setExecutable(true);
		condition.setNotExecutable(false);

		return condition;

	}

	/**
	 * Servletクラスを返す
	 * 
	 * @return Class
	 */
	protected Class getModelClass() {
		return ProgramManager.class;
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

	/**
	 * 選択されたタブパネルを返す
	 * 
	 * @return 選択されたタブパネル
	 */
	protected TProgramMenuPanel getSelectedTabPanel() {
		TProgramMenuPanel pnl = (TProgramMenuPanel) mainView.menuTab.getSelectedComponent();
		return pnl;
	}

	/**
	 * 選択されたプログラムの一覧を返す
	 * 
	 * @return 選択されたプログラムの一覧
	 */
	protected List<Program> getSelectedPrograms() {

		List<Program> list = new ArrayList<Program>();

		int[] rows = mainView.tbl.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			Program program = new Program();
			program.setCode((String) mainView.tbl
				.getRowValueAt(rows[i], MG0250ProgramDisplayMasterPanel.SC.programCode));
			program.setNames((String) mainView.tbl.getRowValueAt(rows[i],
				MG0250ProgramDisplayMasterPanel.SC.programNames));
			list.add(program);
		}

		return list;
	}

	/**
	 * 入力されたプログラム体系を返す
	 * 
	 * @return 入力されたプログラム体系
	 */
	protected List<SystemizedProgram> getInputedSystemizedProgram() {

		List<SystemizedProgram> list = new ArrayList<SystemizedProgram>();

		// タブの数だけプログラム体系を持つ
		for (int tabCount = 0; tabCount < mainView.menuTab.getTabCount(); tabCount++) {
			SystemizedProgram systemizedProgram = new SystemizedProgram();
			systemizedProgram.setCompanyCode(TLoginInfo.getCompany().getCode());
			systemizedProgram.setProgramGroupCode(Integer.toString(tabCount));
			TProgramMenuPanel panel = (TProgramMenuPanel) mainView.menuTab.getComponentAt(tabCount);
			systemizedProgram.setProgramGroupName(panel.getTitle());
			list.add(systemizedProgram);
		}
		return list;
	}

	/**
	 * プログラム体系を登録する
	 * 
	 * @param list プログラム体系のリスト
	 * @throws TException
	 */
	protected void entrySystemizedProgram(List<SystemizedProgram> list) throws TException {
		request(getModelClass(), "entrySystemizedProgram", list);
	}

}
