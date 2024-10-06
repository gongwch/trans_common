package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.tree.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.master.ui.MG0050DepartmentHierarchySelectionMasterPanel.DS1;
import jp.co.ais.trans2.master.ui.MG0050DepartmentHierarchySelectionMasterPanel.DS2;
import jp.co.ais.trans2.model.department.*;

/**
 * 部門階層マスタのコントローラ
 * 
 * @author AIS
 */
public class MG0050DepartmentHierarchySelectionMasterPanelCtrl extends TController {

	/** 操作モード */
	public enum Mode {
		/** 新規 */
		NEW
		/** 削除 */
		, DELETE
		/** エクセル */
		, EXCEL
		/** 確定 */
		, SETTLE
		/** 上位部門 */
		, UPDEP
		/** 下位部門 */
		, LOWDEP
	}

	/** 指示画面 */
	protected MG0050DepartmentHierarchySelectionMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0050DepartmentHierarchySelectionDialog editView = null;

	/** 部門検索画面 */
	protected MG0050DepartmentHierarchySelectionDepDialog editDepView = null;

	/** 操作モード。現在操作中のモードを把握するために使用する。 */
	protected Mode mode = null;

	/** 部門のマップ **/
	protected HashMap<String, String> depMap;

	/**
	 * 処理を開始する
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
	 * パネルの取得
	 */
	@Override
	public MG0050DepartmentHierarchySelectionMasterPanel getPanel() {
		return mainView;
	}

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MG0050DepartmentHierarchySelectionMasterPanel(this);
		addMainViewEvent();
	}

	/**
	 * 指示画面のイベント定義。
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

		// [確定]ボタン押下
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 組織コード変更処理
		mainView.ctrlOrganizationCode.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent evt) {
				changedOrganizationCode(evt.getStateChange());
			}
		});
		// [上位部門]ボタン押下
		mainView.btnUpperDepartment.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnUpperDepartment_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [下位部門]ボタン押下
		mainView.btnLowerDepartment.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnLowerDepartment_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [部門追加]ボタン押下
		mainView.btnDepartmentAdd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDepartmentAdd_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [部門削除]ボタン押下
		mainView.btnDepartmentCancellation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDepartmentCancellation_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * [確定]ボタン押下
	 */
	protected void btnEditDepViewSettle_Click() {

		try {

			// 確認メッセージを表示する
			if (!showConfirmMessage(editDepView, "Q00004")) {
				return;
			}

			List<DepartmentOrganization> list = getSelectedHierarchy();
			List<DepartmentOrganization> listDep = new ArrayList<DepartmentOrganization>();

			// 階層編集画面値を取得
			String upDep = editDepView.ctrlDepartmentReferenceUP.getCode();
			String lowDep = editDepView.ctrlDepartmentReferenceLow.getCode();

			// 画面値の入力チェック
			// 入力チェックを行う。
			if (!isEditDepViewInputRight()) {
				return;
			}

			String level1 = null;
			String level2 = null;
			String level3 = null;
			String level4 = null;
			String level5 = null;
			String level6 = null;
			String level7 = null;
			String level8 = null;
			String level9 = null;

			// 上位部門の階層レベル
			int upLevel = -1;

			// 上位部門のレベルを取得
			for (int upper = 0; upper < list.size(); upper++) {
				String condiDepCode = list.get(upper).getDepCode();
				if (condiDepCode.equals(upDep)) {
					upLevel = list.get(upper).getLevel();
					level1 = list.get(upper).getLevel1();
					level2 = list.get(upper).getLevel2();
					level3 = list.get(upper).getLevel3();
					level4 = list.get(upper).getLevel4();
					level5 = list.get(upper).getLevel5();
					level6 = list.get(upper).getLevel6();
					level7 = list.get(upper).getLevel7();
					level8 = list.get(upper).getLevel8();
					level9 = list.get(upper).getLevel9();
				}
			}

			// レベル設定
			for (int row = 0; row < list.size(); row++) {
				DepartmentOrganization bean = new DepartmentOrganization();
				String condiDepCode = list.get(row).getDepCode();
				if (condiDepCode.equals(lowDep)) {
					bean.setCompanyCode(getCompanyCode());
					bean.setDepCode(lowDep);
					bean.setLevel(upLevel + 1);
					// 部門階層は９階層以上設定できません
					if (bean.getLevel() > 9) {
						showMessage(mainView, "W00153");
						return;
					}
					switch (bean.getLevel()) {
						case 2:
							bean.setCode(list.get(row).getCode());
							bean.setLevel0(list.get(row).getLevel0());
							bean.setLevel1(level1);
							bean.setLevel2(condiDepCode);
							bean.setLevel3(level3);
							bean.setLevel4(level4);
							bean.setLevel5(level5);
							bean.setLevel6(level6);
							bean.setLevel7(level7);
							bean.setLevel8(level8);
							bean.setLevel9(level9);

							break;
						case 3:
							bean.setCode(list.get(row).getCode());
							bean.setLevel0(list.get(row).getLevel0());
							bean.setLevel1(level1);
							bean.setLevel2(level2);
							bean.setLevel3(condiDepCode);
							bean.setLevel4(level4);
							bean.setLevel5(level5);
							bean.setLevel6(level6);
							bean.setLevel7(level7);
							bean.setLevel8(level8);
							bean.setLevel9(level9);

							break;
						case 4:
							bean.setCode(list.get(row).getCode());
							bean.setLevel0(list.get(row).getLevel0());
							bean.setLevel1(level1);
							bean.setLevel2(level2);
							bean.setLevel3(level3);
							bean.setLevel4(condiDepCode);
							bean.setLevel5(level5);
							bean.setLevel6(level6);
							bean.setLevel7(level7);
							bean.setLevel8(level8);
							bean.setLevel9(level9);

							break;
						case 5:
							bean.setCode(list.get(row).getCode());
							bean.setLevel0(list.get(row).getLevel0());
							bean.setLevel1(level1);
							bean.setLevel2(level2);
							bean.setLevel3(level3);
							bean.setLevel4(level4);
							bean.setLevel5(condiDepCode);
							bean.setLevel6(level6);
							bean.setLevel7(level7);
							bean.setLevel8(level8);
							bean.setLevel9(level9);

							break;
						case 6:
							bean.setCode(list.get(row).getCode());
							bean.setLevel0(list.get(row).getLevel0());
							bean.setLevel1(level1);
							bean.setLevel2(level2);
							bean.setLevel3(level3);
							bean.setLevel4(level4);
							bean.setLevel5(level5);
							bean.setLevel6(condiDepCode);
							bean.setLevel7(level7);
							bean.setLevel8(level8);
							bean.setLevel9(level9);

							break;
						case 7:
							bean.setCode(list.get(row).getCode());
							bean.setLevel0(list.get(row).getLevel0());
							bean.setLevel1(level1);
							bean.setLevel2(level2);
							bean.setLevel3(level3);
							bean.setLevel4(level4);
							bean.setLevel5(level5);
							bean.setLevel6(level6);
							bean.setLevel7(condiDepCode);
							bean.setLevel8(level8);
							bean.setLevel9(level9);

							break;
						case 8:
							bean.setCode(list.get(row).getCode());
							bean.setLevel0(list.get(row).getLevel0());
							bean.setLevel1(level1);
							bean.setLevel2(level2);
							bean.setLevel3(level3);
							bean.setLevel4(level4);
							bean.setLevel5(level5);
							bean.setLevel6(level6);
							bean.setLevel7(level7);
							bean.setLevel8(condiDepCode);
							bean.setLevel9(level9);

							break;
						case 9:
							bean.setCode(list.get(row).getCode());
							bean.setLevel0(list.get(row).getLevel0());
							bean.setLevel1(level1);
							bean.setLevel2(level2);
							bean.setLevel3(level3);
							bean.setLevel4(level4);
							bean.setLevel5(level5);
							bean.setLevel6(level6);
							bean.setLevel7(level7);
							bean.setLevel8(level8);
							bean.setLevel9(condiDepCode);

							break;
					}

				} else {
					bean.setCompanyCode(getCompanyCode());
					bean.setCode(list.get(row).getCode());
					bean.setDepCode(condiDepCode);
					bean.setLevel0(list.get(row).getLevel0());
					bean.setLevel(list.get(row).getLevel());
					bean.setLevel1(list.get(row).getLevel1());
					bean.setLevel2(list.get(row).getLevel2());
					bean.setLevel3(list.get(row).getLevel3());
					bean.setLevel4(list.get(row).getLevel4());
					bean.setLevel5(list.get(row).getLevel5());
					bean.setLevel6(list.get(row).getLevel6());
					bean.setLevel7(list.get(row).getLevel7());
					bean.setLevel8(list.get(row).getLevel8());
					bean.setLevel9(list.get(row).getLevel9());

				}
				listDep.add(bean);
			}
			List<DepartmentOrganization> listConv = modifyNameCode(listDep);
			modifyListHierarchy(listConv);

			// 処理完了メッセージ
			showMessage(editDepView, "I00008");

			// 画面を閉じる
			btnEditDepViewClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * コードと名称を変換
	 * 
	 * @param list 部門階層
	 * @return list 部門階層
	 */
	protected List<DepartmentOrganization> modifyNameCode(List<DepartmentOrganization> list) {

		List<DepartmentOrganization> listConv = new ArrayList<DepartmentOrganization>();
		for (int i = 0; i < list.size(); i++) {
			DepartmentOrganization bean = new DepartmentOrganization();

			bean.setCompanyCode(getCompanyCode());
			bean.setCode(list.get(i).getCode());
			bean.setLevel0(list.get(i).getLevel0());
			bean.setDepCode(list.get(i).getDepCode());
			bean.setLevel(list.get(i).getLevel());
			bean.setLevel1(list.get(i).getLevel1());
			bean.setLevel2(list.get(i).getLevel2());
			bean.setLevel3(list.get(i).getLevel3());
			bean.setLevel4(list.get(i).getLevel4());
			bean.setLevel5(list.get(i).getLevel5());
			bean.setLevel6(list.get(i).getLevel6());
			bean.setLevel7(list.get(i).getLevel7());
			bean.setLevel8(list.get(i).getLevel8());
			bean.setLevel9(list.get(i).getLevel9());
			bean.setLevel1Name(depMap.get(list.get(i).getLevel1()));
			bean.setLevel2Name(depMap.get(list.get(i).getLevel2()));
			bean.setLevel3Name(depMap.get(list.get(i).getLevel3()));
			bean.setLevel4Name(depMap.get(list.get(i).getLevel4()));
			bean.setLevel5Name(depMap.get(list.get(i).getLevel5()));
			bean.setLevel6Name(depMap.get(list.get(i).getLevel6()));
			bean.setLevel7Name(depMap.get(list.get(i).getLevel7()));
			bean.setLevel8Name(depMap.get(list.get(i).getLevel8()));
			bean.setLevel9Name(depMap.get(list.get(i).getLevel9()));

			listConv.add(bean);
		}

		return listConv;
	}

	/**
	 * 階層マスタよりデータを取得し、一覧に設定する。
	 * 
	 * @param list 部門階層
	 */
	protected void modifyListHierarchy(List<DepartmentOrganization> list) {

		TTable mainHieSpred = mainView.ssHierarchyList;
		// 一覧をクリアする
		mainHieSpred.removeRow();

		// 部門階層を一覧に表示する
		for (int k = 0; k < list.size(); k++) {
			if (list.get(k).getLevel() != 0) {// 部門レベル０以外はセットする
				mainHieSpred.addRow(getRowData(list.get(k)));
			}
		}
		// 1行目を選択する
		if (mainHieSpred.getRowCount() != 0) {
			mainHieSpred.setRowSelection(0);
		}
	}

	/**
	 * 組織コード変更処理
	 * 
	 * @param state
	 */
	protected void changedOrganizationCode(int state) {
		try {
			if (Util.isNullOrEmpty(mainView.ctrlOrganizationCode.getSelectedOrganizationCode())) {

				// レベル０初期化
				mainView.ctrlDepartment.clear();

				initMainView();

				return;
			}

			if (ItemEvent.SELECTED != state) {
				return;
			}

			// 組織検索条件の定義
			DepartmentOrganizationSearchCondition orgCondition = getTreeSearchCondition();

			// DBから組織情報を取得する
			List<DepartmentOrganization> orgList = getDepartmentOrganization(orgCondition);

			if (orgList.size() < 2) {
				setDepButtonEnabled(false);
			} else {
				setDepButtonEnabled(true);
			}

			// 部門検索条件の定義
			DepartmentSearchCondition depCondition = getSearchCondition();

			// DBから部門情報を取得する
			List<Department> depList = getDepartment(depCondition);

			// 部門情報をマップへセットする
			depMap = new LinkedHashMap();

			for (int j = 0; j < depList.size(); j++) {
				// 部門コード
				String code = valueString(depList.get(j).getCode());
				// 部門名称
				String text = valueString(depList.get(j).getName());
				depMap.put(code, text);
			}

			// レベル０初期化
			mainView.ctrlDepartment.clear();

			// レベル０
			for (DepartmentOrganization org : orgList) {
				if (org.getLevel() == 0) {
					String departmentCode = org.getDepCode();
					mainView.ctrlDepartment.setCode(departmentCode);
					mainView.ctrlDepartment.setNames(org.getDepName());
					break;
				}
			}

			// フィルター
			if (depList != null) {
				for (DepartmentOrganization org : orgList) {
					String departmentCode = org.getDepCode();

					for (int i = depList.size() - 1; i >= 0; i--) {
						if (departmentCode.equals(depList.get(i).getCode())) {
							depList.remove(i);
							break;
						}
					}
				}
			}

			// 部門一覧へ反映
			initList(depList);
			initListHierarchy(orgList);

			setMainButtonEnabled(!Util.isNullOrEmpty(mainView.ctrlOrganizationCode.getSelectedItemValue()));

			// 部門追加・削除を有効
			setDepUpLowButtonEnabled(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 指示画面を初期化する
	 */
	protected void initMainView() {

		// メインボタンを押下不可にする
		setMainButtonEnabled(false);

		// 上位、下位部門ボタンを押下不可にする
		setDepButtonEnabled(false);
		setDepUpLowButtonEnabledDfault(false);
		// 一覧設定処理
		initList(null);

	}

	/**
	 * メインボタンの押下制御
	 * 
	 * @param bln enabled
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnDelete.setEnabled(bln);
		mainView.btnExcel.setEnabled(bln);
		mainView.btnSettle.setEnabled(bln);
	}

	/**
	 * 上位部門、下位部門の押下制御
	 * 
	 * @param bln enabled
	 */
	protected void setDepButtonEnabled(boolean bln) {
		mainView.btnUpperDepartment.setEnabled(bln);
		mainView.btnLowerDepartment.setEnabled(bln);
	}

	/**
	 * 上位部門、下位部門の押下制御
	 * 
	 * @param bln enabled
	 */
	protected void setDepUpLowButtonEnabledDfault(boolean bln) {
		mainView.btnDepartmentAdd.setEnabled(bln);
		mainView.btnDepartmentCancellation.setEnabled(bln);

	}

	/**
	 * 上位部門、下位部門の押下制御
	 * 
	 * @param bln enabled
	 */
	protected void setDepUpLowButtonEnabled(boolean bln) {
		mainView.btnDepartmentAdd.setEnabled(bln);
		if (mainView.ssHierarchyList.getRowCount() != 0) {
			mainView.btnDepartmentCancellation.setEnabled(bln);
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
			initEditView(Mode.NEW, null, null);

			// 編集画面を表示
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 指示画面[上位部門]ボタン押下
	 */
	protected void btnUpperDepartment_Click() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		try {
			String depCode = null;
			String depName = null;

			// 編集画面を生成
			createEditDepView();

			int row[] = mainHieSpred.getSelectedRows();
			if (row.length == 0) {
				showMessage(editDepView, "I00041", "C02473"); // 部門階層
				mainView.btnUpperDepartment.requestFocus();
				return;

			}

			for (int i = 0; i < row.length; i++) {
				depCode = ((String) mainHieSpred.getRowValueAt(row[i], DS2.codedep));

				depName = depMap.get(depCode);
			}

			// 編集画面を初期化する
			initEditView(Mode.UPDEP, depCode, depName);

			// 編集画面を表示
			editDepView.setLocationRelativeTo(null);
			editDepView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 指示画面[下位部門]ボタン押下
	 */
	protected void btnLowerDepartment_Click() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		try {

			String depCode = null;
			String depName = null;

			// 編集画面を生成
			createEditDepView();

			int row[] = mainHieSpred.getSelectedRows();
			if (row.length == 0) {
				showMessage(editDepView, "I00041", "C02473"); // 部門階層
				mainView.btnUpperDepartment.requestFocus();
				return;

			}
			for (int i = 0; i < row.length; i++) {

				depCode = ((String) mainHieSpred.getRowValueAt(row[i], DS2.codedep));
				depName = depMap.get(depCode);
			}

			// 編集画面を初期化する
			initEditView(Mode.LOWDEP, depCode, depName);

			// 編集画面を表示
			editDepView.setLocationRelativeTo(null);
			editDepView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [部門追加]ボタン押下
	 */
	protected void btnDepartmentAdd_Click() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		TTable mainBmnSpred = mainView.ssDepartmentList;
		int selectedRows[] = mainBmnSpred.getSelectedRows();

		try {

			for (int i = 0; i < selectedRows.length; i++) {
				DepartmentOrganization bean = new DepartmentOrganization();

				String code = (String) mainBmnSpred.getRowValueAt(selectedRows[i], DS1.code);
				String name = (String) mainBmnSpred.getRowValueAt(selectedRows[i], DS1.name);

				bean.setCompanyCode(getCompanyCode());
				bean.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());
				bean.setLevel0(mainView.ctrlDepartment.getCode());
				bean.setDepCode(code);
				bean.setLevel(1);
				bean.setLevel1(code);
				bean.setLevel1Name(name);

				List inner = new ArrayList();
				// 部門コード
				inner.add(code);
				// レベル
				inner.add("1");
				// 部門名称レベル１
				inner.add(name);
				inner.add("");
				inner.add("");
				inner.add("");
				inner.add("");
				inner.add("");
				inner.add("");
				inner.add("");
				inner.add("");
				inner.add(bean);

				mainHieSpred.addRow(inner);

			}

		} catch (Exception e) {
			errorHandler(e);
		}

		// 選択元から削除
		mainBmnSpred.removeSelectedRows();
		setDepButtonEnabled(true);
		setDepUpLowButtonEnabled(true);
		// 1行目を選択する
		mainHieSpred.setRowSelection(0);

	}

	/**
	 * [部門削除]ボタン押下
	 */
	protected void btnDepartmentCancellation_Click() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		TTable mainBmnSpred = mainView.ssDepartmentList;

		int levelDel = 0;
		int selectedRows = mainHieSpred.getRowCount();
		String dep = (String) mainHieSpred.getSelectedRowValueAt(DS2.codedep);
		if (Util.isNullOrEmpty(dep)) {
			showMessage(mainView, "I00041", "C02473"); // 部門階層を選択してください。
			return;
		}

		int selectedRows2[] = mainHieSpred.getSelectedRows();
		int row = selectedRows2[0];
		int rowConst = row;

		List<DepartmentOrganization> list = new ArrayList<DepartmentOrganization>();
		DepartmentOrganization bean = new DepartmentOrganization();

		for (int i = 0; i < selectedRows; i++) {
			bean = (DepartmentOrganization) mainHieSpred.getRowValueAt(i, DS2.bean2);
			list.add(bean);
		}

		levelDel = Integer.parseInt((mainHieSpred.getRowValueAt(row, DS2.level).toString()));

		while (levelDel == list.get(row).getLevel()) {
			String code = (String) mainHieSpred.getRowValueAt(rowConst, DS2.codedep);
			String depName = depMap.get(code);
			// 左スプレッドへ追加
			mainBmnSpred.addRow(new Object[] { code, depName });
			// 選択元から削除
			mainHieSpred.removeRow(rowConst);
			row++;
			if (row >= selectedRows) {
				break;
			}

			while (levelDel < list.get(row).getLevel()) {
				String code2 = (String) mainHieSpred.getRowValueAt(rowConst, DS2.codedep);
				String depName2 = depMap.get(code2);

				// 左スプレッドへ追加
				mainBmnSpred.addRow(new Object[] { code2, depName2 });
				// 選択元から削除
				mainHieSpred.removeRow(rowConst);
				row++;
				if (row >= selectedRows) {
					break;
				}
			}
			break;
		}
	}

	/**
	 * 部門マスタよりデータを取得し、一覧に設定する。
	 * 
	 * @param list 部門
	 */
	protected void initList(List<Department> list) {
		TTable mainBmnSpred = mainView.ssDepartmentList;
		// 一覧をクリアする
		mainBmnSpred.removeRow();

		// 部門階層マスタが存在しない場合
		if (list == null || list.isEmpty()) {
			return;
		}

		// 部門情報を一覧に表示する
		for (Department department : list) {
			mainBmnSpred.addRow(getDepartmentDataRow(department));
		}

		// 1行目を選択する
		mainBmnSpred.setRowSelection(0);

	}

	/**
	 * 階層マスタよりデータを取得し、一覧に設定する。
	 * 
	 * @param list 部門階層
	 */
	protected void initListHierarchy(List<DepartmentOrganization> list) {
		TTable mainHieSpred = mainView.ssHierarchyList;
		// 一覧をクリアする
		mainHieSpred.removeRow();

		// 部門階層マスタが存在しない場合
		if (list == null || list.isEmpty()) {
			return;
		}

		// 部門階層を一覧に表示する
		for (int k = 0; k < list.size(); k++) {
			if (list.get(k).getLevel() != 0) {// 部門レベル０以外はセットする
				mainHieSpred.addRow(getRowData(list.get(k)));
			}
		}
		// 1行目を選択する
		if (mainHieSpred.getRowCount() != 0) {
			mainHieSpred.setRowSelection(0);
		}
	}

	/**
	 * 部門階層を一覧に表示する形式に変換し返す
	 * 
	 * @param organization 部門階層
	 * @return 一覧に表示する形式に変換された部門階層
	 */
	protected List<Object> getRowData(DepartmentOrganization organization) {
		List<Object> list = new ArrayList<Object>();
		switch (organization.getLevel()) {

			case 2:
				list.add(organization.getDepCode()); // 部門コード
				list.add(organization.getLevel()); // レベル
				list.add(""); // レベル１
				list.add(organization.getLevel2Name()); // レベル２
				list.add(organization.getLevel3Name()); // レベル３
				list.add(organization.getLevel4Name()); // レベル４
				list.add(organization.getLevel5Name()); // レベル５
				list.add(organization.getLevel6Name()); // レベル６
				list.add(organization.getLevel7Name()); // レベル７
				list.add(organization.getLevel8Name()); // レベル８
				list.add(organization.getLevel9Name()); // レベル９
				list.add(organization);
				break;
			case 3:
				list.add(organization.getDepCode()); // 部門コード
				list.add(organization.getLevel()); // レベル
				list.add(""); // レベル１
				list.add(""); // レベル２
				list.add(organization.getLevel3Name()); // レベル３
				list.add(organization.getLevel4Name()); // レベル４
				list.add(organization.getLevel5Name()); // レベル５
				list.add(organization.getLevel6Name()); // レベル６
				list.add(organization.getLevel7Name()); // レベル７
				list.add(organization.getLevel8Name()); // レベル８
				list.add(organization.getLevel9Name()); // レベル９
				list.add(organization);
				break;
			case 4:
				list.add(organization.getDepCode()); // 部門コード
				list.add(organization.getLevel()); // レベル
				list.add(""); // レベル１
				list.add(""); // レベル２
				list.add(""); // レベル３
				list.add(organization.getLevel4Name()); // レベル４
				list.add(organization.getLevel5Name()); // レベル５
				list.add(organization.getLevel6Name()); // レベル６
				list.add(organization.getLevel7Name()); // レベル７
				list.add(organization.getLevel8Name()); // レベル８
				list.add(organization.getLevel9Name()); // レベル９
				list.add(organization);
				break;
			case 5:
				list.add(organization.getDepCode()); // 部門コード
				list.add(organization.getLevel()); // レベル
				list.add(""); // レベル１
				list.add(""); // レベル２
				list.add(""); // レベル３
				list.add(""); // レベル４
				list.add(organization.getLevel5Name()); // レベル５
				list.add(organization.getLevel6Name()); // レベル６
				list.add(organization.getLevel7Name()); // レベル７
				list.add(organization.getLevel8Name()); // レベル８
				list.add(organization.getLevel9Name()); // レベル９
				list.add(organization);
				break;
			case 6:
				list.add(organization.getDepCode()); // 部門コード
				list.add(organization.getLevel()); // レベル
				list.add(""); // レベル１
				list.add(""); // レベル２
				list.add(""); // レベル３
				list.add(""); // レベル４
				list.add(""); // レベル５
				list.add(organization.getLevel6Name()); // レベル６
				list.add(organization.getLevel7Name()); // レベル７
				list.add(organization.getLevel8Name()); // レベル８
				list.add(organization.getLevel9Name()); // レベル９
				list.add(organization);
				break;
			case 7:
				list.add(organization.getDepCode()); // 部門コード
				list.add(organization.getLevel()); // レベル
				list.add(""); // レベル１
				list.add(""); // レベル２
				list.add(""); // レベル３
				list.add(""); // レベル４
				list.add(""); // レベル５
				list.add(""); // レベル６
				list.add(organization.getLevel7Name()); // レベル７
				list.add(organization.getLevel8Name()); // レベル８
				list.add(organization.getLevel9Name()); // レベル９
				list.add(organization);
				break;
			case 8:
				list.add(organization.getDepCode()); // 部門コード
				list.add(organization.getLevel()); // レベル
				list.add(""); // レベル１
				list.add(""); // レベル２
				list.add(""); // レベル３
				list.add(""); // レベル４
				list.add(""); // レベル５
				list.add(""); // レベル６
				list.add(""); // レベル７
				list.add(organization.getLevel8Name()); // レベル８
				list.add(organization.getLevel9Name()); // レベル９
				list.add(organization);
				break;
			case 9:
				list.add(organization.getDepCode()); // 部門コード
				list.add(organization.getLevel()); // レベル
				list.add(""); // レベル１
				list.add(""); // レベル２
				list.add(""); // レベル３
				list.add(""); // レベル４
				list.add(""); // レベル５
				list.add(""); // レベル６
				list.add(""); // レベル７
				list.add(""); // レベル８
				list.add(organization.getLevel9Name()); // レベル９
				list.add(organization);
				break;
			default:
				list.add(organization.getDepCode()); // 部門コード
				list.add(organization.getLevel()); // レベル
				list.add(organization.getLevel1Name()); // レベル１
				list.add(organization.getLevel2Name()); // レベル２
				list.add(organization.getLevel3Name()); // レベル３
				list.add(organization.getLevel4Name()); // レベル４
				list.add(organization.getLevel5Name()); // レベル５
				list.add(organization.getLevel6Name()); // レベル６
				list.add(organization.getLevel7Name()); // レベル７
				list.add(organization.getLevel8Name()); // レベル８
				list.add(organization.getLevel9Name()); // レベル９
				list.add(organization);

				break;

		}

		return list;
	}

	/**
	 * 部門階層を一覧に表示する形式に変換し返す
	 * 
	 * @param organization 部門階層
	 * @return 一覧に表示する形式に変換された部門階層
	 */
	protected List<Object> getRowCodeData(DepartmentOrganization organization) {
		List<Object> list = new ArrayList<Object>();

		list.add(organization.getDepCode()); // 部門コード
		list.add(organization.getLevel()); // レベル
		list.add(organization.getLevel1()); // レベル１
		list.add(organization.getLevel2()); // レベル２
		list.add(organization.getLevel3()); // レベル３
		list.add(organization.getLevel4()); // レベル４
		list.add(organization.getLevel5()); // レベル５
		list.add(organization.getLevel6()); // レベル６
		list.add(organization.getLevel7()); // レベル７
		list.add(organization.getLevel8()); // レベル８
		list.add(organization.getLevel9()); // レベル９
		list.add(organization);

		return list;
	}

	/**
	 * 部門の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected DepartmentSearchCondition getSearchCondition() {
		DepartmentSearchCondition condition = new DepartmentSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setSumDepartment(true);

		return condition;
	}

	/**
	 * 部門階層の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected DepartmentOrganizationSearchCondition getOrganizationSearchCondition() {
		DepartmentOrganizationSearchCondition condition = new DepartmentOrganizationSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());

		return condition;
	}

	/**
	 * 検索条件に該当する部門のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する部門のリスト
	 * @throws TException
	 */
	protected List<Department> getDepartment(DepartmentSearchCondition condition) throws TException {
		return (List<Department>) request(getModelClass(), "get", condition);
	}

	/**
	 * モデルクラスを返す
	 * 
	 * @return モデルクラス
	 */
	protected Class getModelClass() {
		return DepartmentManager.class;
	}

	/**
	 * 部門情報を一覧に表示する形式に変換し返す
	 * 
	 * @param department 部門
	 * @return 一覧に表示する形式に変換された部門
	 */
	protected Object[] getDepartmentDataRow(Department department) {
		return new Object[] { department.getCode(), department.getName(), department };
	}

	/**
	 * 部門階層の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected DepartmentOrganizationSearchCondition getTreeSearchCondition() {

		DepartmentOrganizationSearchCondition condition = new DepartmentOrganizationSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());

		return condition;

	}

	/**
	 * 検索条件に該当する部門階層のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する部門階層のリスト
	 * @throws TException
	 */
	protected List<DepartmentOrganization> getDepartmentOrganization(DepartmentOrganizationSearchCondition condition)
		throws TException {
		return (List<DepartmentOrganization>) request(getModelClass(), "getDepartmentOrganizationData", condition);
	}

	/**
	 * [削除]ボタン押下
	 */
	protected void btnDelete_Click() {
		try {
			// 確認メッセージ
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			DepartmentOrganization bean = getSelectedDepOrg();

			// 部門階層情報を取得
			request(getModelClass(), "deleteDepartmentOrganization", bean);

			if (bean == null) {
				// 選択されたデータは他ユーザーにより削除されました
				throw new TException("I00193");
			}

			// コンボボックスリフレッシュ
			mainView.ctrlOrganizationCode.getController().initList();

			// レベル０初期化
			mainView.ctrlDepartment.clear();

			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 部門階層を返す
	 * 
	 * @return 部門階層
	 * @throws Exception
	 */
	protected DepartmentOrganization getSelectedDepOrg() throws Exception {

		DepartmentOrganizationSearchCondition condition = createDepartmentOrganizationSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());
		List<DepartmentOrganization> list = getDepartmentOrganization(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * @return 部門階層情報の検索条件
	 */
	protected DepartmentOrganizationSearchCondition createDepartmentOrganizationSearchCondition() {
		return new DepartmentOrganizationSearchCondition();
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
			DepartmentOrganizationSearchCondition condition = getOrganizationSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getDepartmentOrganizationExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02327") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {

			// 確認メッセージを表示する
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			// List<DepartmentOrganization> list = getSelectedHierarchy();
			List<DepartmentOrganization> list = getSelectedDepHie();

			// 登録処理
			request(getModelClass(), "entryDepartmentOrganization",
				mainView.ctrlOrganizationCode.getSelectedOrganizationCode(), list);

			// 処理完了メッセージ
			showMessage(mainView, "I00008");

			// リフレッシュ
			changedOrganizationCode(ItemEvent.SELECTED);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 一覧で選択されている部門を返す
	 * 
	 * @return 一覧で選択されている部門
	 */
	protected List<DepartmentOrganization> getSelectedDepHie() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		List<DepartmentOrganization> list = new ArrayList<DepartmentOrganization>();
		for (int i = 0; i < mainHieSpred.getRowCount(); i++) {
			list.add((DepartmentOrganization) mainHieSpred.getRowValueAt(i, DS2.bean2));
		}

		return list;
	}

	/**
	 * 一覧で選択されている部門階層を返す
	 * 
	 * @return 一覧で選択されている部門階層
	 */
	protected List<DepartmentOrganization> getSelectedHierarchy() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		List<DepartmentOrganization> list = new ArrayList<DepartmentOrganization>();

		for (int row = 0; row < mainHieSpred.getRowCount(); row++) {
			DepartmentOrganization bean = new DepartmentOrganization();
			bean.setCompanyCode(getCompanyCode());
			bean.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());
			bean.setLevel0((mainView.ctrlDepartment.getCode()));
			bean.setDepCode(Util.avoidNull(mainHieSpred.getRowValueAt(row, DS2.codedep)));
			bean.setLevel(valueInt(mainHieSpred.getRowValueAt(row, DS2.level)));

			if (bean.getLevel() == 1) {
				bean.setLevel1(Util.avoidNull(mainHieSpred.getRowValueAt(row, DS2.codedep)));
			}
			if (bean.getLevel() == 2) {

				for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {
					DepartmentOrganization bean2 = new DepartmentOrganization();
					bean2.setLevel(valueInt(mainHieSpred.getRowValueAt(row2, DS2.level)));
					if (bean.getLevel1() == null && bean2.getLevel() == 1) {
						bean.setLevel1(Util.avoidNull(mainHieSpred.getRowValueAt(row2, DS2.codedep)));
					}

				}

				bean.setLevel2(Util.avoidNull(mainHieSpred.getRowValueAt(row, DS2.codedep)));
			}
			if (bean.getLevel() == 3) {

				for (int row3 = 0; row3 < mainHieSpred.getRowCount(); row3++) {
					DepartmentOrganization bean2 = new DepartmentOrganization();
					bean2.setLevel(valueInt(mainHieSpred.getRowValueAt(row3, DS2.level)));
					if (bean.getLevel1() == null && bean2.getLevel() == 1) {
						bean.setLevel1(Util.avoidNull(mainHieSpred.getRowValueAt(row3, DS2.codedep)));
					} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
						bean.setLevel2(Util.avoidNull(mainHieSpred.getRowValueAt(row3, DS2.codedep)));
					}
				}
				bean.setLevel3(Util.avoidNull(mainHieSpred.getRowValueAt(row, DS2.codedep)));

			}
			if (bean.getLevel() == 4) {

				for (int row4 = 0; row4 < mainHieSpred.getRowCount(); row4++) {
					DepartmentOrganization bean2 = new DepartmentOrganization();
					bean2.setLevel(valueInt(mainHieSpred.getRowValueAt(row4, DS2.level)));
					if (bean.getLevel1() == null && bean2.getLevel() == 1) {
						bean.setLevel1(Util.avoidNull(mainHieSpred.getRowValueAt(row4, DS2.codedep)));
					} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
						bean.setLevel2(Util.avoidNull(mainHieSpred.getRowValueAt(row4, DS2.codedep)));
					} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
						bean.setLevel3(Util.avoidNull(mainHieSpred.getRowValueAt(row4, DS2.codedep)));
					}
				}
				bean.setLevel4(Util.avoidNull(mainHieSpred.getRowValueAt(row, DS2.codedep)));
			}
			if (bean.getLevel() == 5) {

				for (int row5 = 0; row5 < mainHieSpred.getRowCount(); row5++) {
					DepartmentOrganization bean2 = new DepartmentOrganization();
					bean2.setLevel(valueInt(mainHieSpred.getRowValueAt(row5, DS2.level)));
					if (bean.getLevel1() == null && bean2.getLevel() == 1) {
						bean.setLevel1(Util.avoidNull(mainHieSpred.getRowValueAt(row5, DS2.codedep)));
					} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
						bean.setLevel2(Util.avoidNull(mainHieSpred.getRowValueAt(row5, DS2.codedep)));
					} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
						bean.setLevel3(Util.avoidNull(mainHieSpred.getRowValueAt(row5, DS2.codedep)));
					} else if (bean.getLevel4() == null && bean2.getLevel() == 4) {
						bean.setLevel4(Util.avoidNull(mainHieSpred.getRowValueAt(row5, DS2.codedep)));
					}
				}

				bean.setLevel5(Util.avoidNull(mainHieSpred.getRowValueAt(row, DS2.codedep)));
			}
			if (bean.getLevel() == 6) {

				for (int row6 = 0; row6 < mainHieSpred.getRowCount(); row6++) {
					DepartmentOrganization bean2 = new DepartmentOrganization();
					bean2.setLevel(valueInt(mainHieSpred.getRowValueAt(row6, DS2.level)));
					if (bean.getLevel1() == null && bean2.getLevel() == 1) {
						bean.setLevel1(Util.avoidNull(mainHieSpred.getRowValueAt(row6, DS2.codedep)));
					} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
						bean.setLevel2(Util.avoidNull(mainHieSpred.getRowValueAt(row6, DS2.codedep)));
					} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
						bean.setLevel3(Util.avoidNull(mainHieSpred.getRowValueAt(row6, DS2.codedep)));
					} else if (bean.getLevel4() == null && bean2.getLevel() == 4) {
						bean.setLevel4(Util.avoidNull(mainHieSpred.getRowValueAt(row6, DS2.codedep)));
					} else if (bean.getLevel5() == null && bean2.getLevel() == 5) {
						bean.setLevel5(Util.avoidNull(mainHieSpred.getRowValueAt(row6, DS2.codedep)));
					}
				}
				bean.setLevel6(Util.avoidNull(mainHieSpred.getRowValueAt(row, DS2.codedep)));
			}

			if (bean.getLevel() == 7) {

				for (int row7 = 0; row7 < mainHieSpred.getRowCount(); row7++) {
					DepartmentOrganization bean2 = new DepartmentOrganization();
					bean2.setLevel(valueInt(mainHieSpred.getRowValueAt(row7, DS2.level)));
					if (bean.getLevel1() == null && bean2.getLevel() == 1) {
						bean.setLevel1(Util.avoidNull(mainHieSpred.getRowValueAt(row7, DS2.codedep)));
					} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
						bean.setLevel2(Util.avoidNull(mainHieSpred.getRowValueAt(row7, DS2.codedep)));
					} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
						bean.setLevel3(Util.avoidNull(mainHieSpred.getRowValueAt(row7, DS2.codedep)));
					} else if (bean.getLevel4() == null && bean2.getLevel() == 4) {
						bean.setLevel4(Util.avoidNull(mainHieSpred.getRowValueAt(row7, DS2.codedep)));
					} else if (bean.getLevel5() == null && bean2.getLevel() == 5) {
						bean.setLevel5(Util.avoidNull(mainHieSpred.getRowValueAt(row7, DS2.codedep)));
					} else if (bean.getLevel6() == null && bean2.getLevel() == 6) {
						bean.setLevel6(Util.avoidNull(mainHieSpred.getRowValueAt(row7, DS2.codedep)));
					}
				}
				bean.setLevel7(Util.avoidNull(mainHieSpred.getRowValueAt(row, DS2.codedep)));
			}

			if (bean.getLevel() == 8) {

				for (int row8 = 0; row8 < mainHieSpred.getRowCount(); row8++) {
					DepartmentOrganization bean2 = new DepartmentOrganization();
					bean2.setLevel(valueInt(mainHieSpred.getRowValueAt(row8, DS2.level)));
					if (bean.getLevel1() == null && bean2.getLevel() == 1) {
						bean.setLevel1(Util.avoidNull(mainHieSpred.getRowValueAt(row8, DS2.codedep)));
					} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
						bean.setLevel2(Util.avoidNull(mainHieSpred.getRowValueAt(row8, DS2.codedep)));
					} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
						bean.setLevel3(Util.avoidNull(mainHieSpred.getRowValueAt(row8, DS2.codedep)));
					} else if (bean.getLevel4() == null && bean2.getLevel() == 4) {
						bean.setLevel4(Util.avoidNull(mainHieSpred.getRowValueAt(row8, DS2.codedep)));
					} else if (bean.getLevel5() == null && bean2.getLevel() == 5) {
						bean.setLevel5(Util.avoidNull(mainHieSpred.getRowValueAt(row8, DS2.codedep)));
					} else if (bean.getLevel6() == null && bean2.getLevel() == 6) {
						bean.setLevel6(Util.avoidNull(mainHieSpred.getRowValueAt(row8, DS2.codedep)));
					} else if (bean.getLevel7() == null && bean2.getLevel() == 7) {
						bean.setLevel7(Util.avoidNull(mainHieSpred.getRowValueAt(row8, DS2.codedep)));
					}
				}

				bean.setLevel8(Util.avoidNull(mainHieSpred.getRowValueAt(row, DS2.codedep)));
			}

			if (bean.getLevel() == 9) {

				for (int row9 = 0; row9 < mainHieSpred.getRowCount(); row9++) {
					DepartmentOrganization bean2 = new DepartmentOrganization();
					bean2.setLevel(valueInt(mainHieSpred.getRowValueAt(row9, DS2.level)));
					if (bean.getLevel1() == null && bean2.getLevel() == 1) {
						bean.setLevel1(Util.avoidNull(mainHieSpred.getRowValueAt(row9, DS2.codedep)));
					} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
						bean.setLevel2(Util.avoidNull(mainHieSpred.getRowValueAt(row9, DS2.codedep)));
					} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
						bean.setLevel3(Util.avoidNull(mainHieSpred.getRowValueAt(row9, DS2.codedep)));
					} else if (bean.getLevel4() == null && bean2.getLevel() == 4) {
						bean.setLevel4(Util.avoidNull(mainHieSpred.getRowValueAt(row9, DS2.codedep)));
					} else if (bean.getLevel5() == null && bean2.getLevel() == 5) {
						bean.setLevel5(Util.avoidNull(mainHieSpred.getRowValueAt(row9, DS2.codedep)));
					} else if (bean.getLevel6() == null && bean2.getLevel() == 6) {
						bean.setLevel6(Util.avoidNull(mainHieSpred.getRowValueAt(row9, DS2.codedep)));
					} else if (bean.getLevel7() == null && bean2.getLevel() == 7) {
						bean.setLevel7(Util.avoidNull(mainHieSpred.getRowValueAt(row9, DS2.codedep)));
					} else if (bean.getLevel8() == null && bean2.getLevel() == 8) {
						bean.setLevel8(Util.avoidNull(mainHieSpred.getRowValueAt(row9, DS2.codedep)));
					}
				}
				bean.setLevel9(Util.avoidNull(mainHieSpred.getRowValueAt(row, DS2.codedep)));
			}
			list.add(bean);
		}

		return list;
	}

	/**
	 * 一覧で選択されている部門階層を返す
	 * 
	 * @return 一覧で選択されている部門階層
	 * @param obj スプレッドの値
	 */
	protected static String valueString(Object obj) {
		return (obj == null) ? "" : obj.toString();
	}

	/**
	 * 一覧で選択されている部門階層を返す
	 * 
	 * @return 一覧で選択されている部門階層
	 * @param obj スプレッドの値
	 */
	protected static int valueInt(Object obj) {
		String objStr = obj.toString();
		int num3 = new Integer(objStr).intValue();
		return num3;

	}

	/**
	 * @param node
	 * @return 部門コード
	 */
	protected String getNodeDepartmentCode(TreeNode node) {
		return ((DepartmentTreeNode) node).getDepDisp().getCode();
	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0050DepartmentHierarchySelectionDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent(0);

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditDepView() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		// 編集画面を生成
		editDepView = new MG0050DepartmentHierarchySelectionDepDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent(1);

		// 検索処理に制限をかける
		List<DepartmentOrganization> list = getSelectedHierarchy();
		String[] dpCodeList = new String[getSelectedHierarchy().size()];
		for (int row = 0; row < list.size(); row++) {
			String condiDepCode = list.get(row).getDepCode();
			if (!(condiDepCode.equals(mainHieSpred.getSelectedRowValueAt(DS2.codedep)))) {
				dpCodeList[row] = condiDepCode;
			}
		}
		editDepView.ctrlDepartmentReferenceUP.getSearchCondition().setDepartmentCodeList(dpCodeList);
		editDepView.ctrlDepartmentReferenceLow.getSearchCondition().setDepartmentCodeList(dpCodeList);
	}

	/**
	 * 編集画面のイベント定義。
	 * 
	 * @param flag
	 */
	protected void addEditViewEvent(int flag) {
		switch (flag) {
			case 0:

				// [確定]ボタン押下
				editView.btnSettle.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						btnEditViewSettle_Click();
						editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				});

				// [戻る]ボタン押下
				editView.btnClose.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						btnEditViewClose_Click();
						editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				});
				break;
			case 1:
				// [確定]ボタン押下
				editDepView.btnSettle.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						editDepView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						btnEditDepViewSettle_Click();
						editDepView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				});

				// [戻る]ボタン押下
				editDepView.btnClose.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						editDepView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						btnEditDepViewClose_Click();
						editDepView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				});
				break;
		}
	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param mode_ 新規
	 * @param depCode
	 * @param depName
	 */
	protected void initEditView(Mode mode_, String depCode, String depName) {

		this.mode = mode_;
		switch (mode) {
			case NEW:
				editView.setTitle(getWord("C01698"));
				break;
			case UPDEP:
				editDepView.setTitle(getWord("C02404"));
				editDepView.ctrlDepartmentReferenceLow.setCode(depCode);
				editDepView.ctrlDepartmentReferenceLow.setNames(depName);
				editDepView.ctrlDepartmentReferenceLow.btn.setEnabled(false);
				editDepView.ctrlDepartmentReferenceLow.code.setEnabled(false);

				break;
			case LOWDEP:
				editDepView.setTitle(getWord("C02405"));
				editDepView.ctrlDepartmentReferenceUP.setCode(depCode);
				editDepView.ctrlDepartmentReferenceUP.setNames(depName);
				editDepView.ctrlDepartmentReferenceUP.btn.setEnabled(false);
				editDepView.ctrlDepartmentReferenceUP.code.setEnabled(false);
				break;

		}

	}

	/**
	 * 編集画面[確定]ボタン押下
	 */
	protected void btnEditViewSettle_Click() {

		try {

			// 入力チェックを行う。
			if (!isEditViewInputRight()) {
				return;
			}

			// 入力された組織コード情報を取得
			DepartmentOrganization departmentOrganization = getInputedOrg();

			if (Mode.NEW == mode) {
				// 新規登録
				request(getModelClass(), "entryDepartmentOrganization", departmentOrganization);
			}

			// 編集画面を閉じる
			btnEditViewClose_Click();

			// 追加分をコンボボックスに反映する
			mainView.ctrlOrganizationCode.getController().initList();

			mainView.ctrlOrganizationCode.setSelectedItemValue(departmentOrganization.getCode());
			mainView.ctrlDepartment.setCode(departmentOrganization.getDepCode());
			mainView.ctrlDepartment.setNames(departmentOrganization.getDepNames());

			// 組織検索条件の定義
			DepartmentOrganizationSearchCondition orgCondition = getTreeSearchCondition();

			// DBから組織情報を取得する
			List<DepartmentOrganization> orgList = getDepartmentOrganization(orgCondition);

			// 部門検索条件の定義
			DepartmentSearchCondition depCondition = getSearchCondition();

			// DBから部門情報を取得する
			List<Department> depList = getDepartment(depCondition);

			// フィルター
			if (depList != null) {
				for (DepartmentOrganization org : orgList) {
					String departmentCode = org.getDepCode();

					for (int i = depList.size() - 1; i >= 0; i--) {
						if (departmentCode.equals(depList.get(i).getCode())) {
							depList.remove(i);
							break;
						}
					}
				}
			}

			initList(depList);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 編集画面で入力された組織コードを返す
	 * 
	 * @return 編集画面で入力された組織コード情報
	 */
	protected DepartmentOrganization getInputedOrg() {

		// 組織コード情報
		DepartmentOrganization org = new DepartmentOrganization();
		org.setCode(editView.ctrlOrganizationCode.getValue());
		org.setDepCode(editView.ctrlDepartmentReference.getCode());
		org.setDepNames(editView.ctrlDepartmentReference.getNames());
		return org;

	}

	/**
	 * 編集画面[戻る]ボタン押下
	 */
	protected void btnEditViewClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * 部門階層設定画面[戻る]ボタン押下
	 */
	protected void btnEditDepViewClose_Click() {
		editDepView.setVisible(false);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 * @throws Exception
	 */
	protected boolean isEditViewInputRight() throws Exception {

		// 組織コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlOrganizationCode.getValue())) {
			showMessage(editView, "I00037", "C00335"); // 組織コード
			editView.ctrlOrganizationCode.requestFocus();
			return false;
		}

		// レベル０が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDepartmentReference.getCode())) {
			showMessage(editView, "I00037", "C00722"); // レベル０
			editView.ctrlDepartmentReference.btn.requestFocus();
			return false;
		}

		// 組織コードが重複していたらエラー
		DepartmentOrganizationSearchCondition condition = new DepartmentOrganizationSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(editView.ctrlOrganizationCode.getValue());

		List<DepartmentOrganization> list = getDepartmentOrganization(condition);

		if (list != null && !list.isEmpty()) {
			showMessage(editView, "I00084", "C00335");
			editView.ctrlOrganizationCode.requestTextFocus();
			return false;
		}
		return true;
	}

	/**
	 * 部門設定画面で入力した値が妥当かをチェックする
	 * 
	 * @return 部門設定画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 */
	protected boolean isEditDepViewInputRight() {
		// 上位部門が未注力の場合エラー
		if (Util.isNullOrEmpty(editDepView.ctrlDepartmentReferenceUP.getCode())) {
			showMessage(editDepView, "I00037", "C01909"); // 上位部門コード
			editDepView.ctrlDepartmentReferenceUP.requestFocus();
			return false;
		}

		// 下位部門が未注力の場合エラー
		if (Util.isNullOrEmpty(editDepView.ctrlDepartmentReferenceLow.getCode())) {
			showMessage(editDepView, "I00037", getWord("C00044") + getWord("C00698")); // 下位部門コード
			editDepView.ctrlDepartmentReferenceLow.requestFocus();
			return false;
		}

		return true;
	}

}
