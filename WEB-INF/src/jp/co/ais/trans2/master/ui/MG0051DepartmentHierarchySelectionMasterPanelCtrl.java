package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.table.*;
import javax.swing.tree.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.master.ui.MG0051DepartmentHierarchySelectionMasterPanel.DS1;
import jp.co.ais.trans2.master.ui.MG0051DepartmentHierarchySelectionMasterPanel.DS2;
import jp.co.ais.trans2.model.department.*;

/**
 * 部門階層マスタのコントローラ
 * 
 * @author AIS
 */
public class MG0051DepartmentHierarchySelectionMasterPanelCtrl extends TController {

	/** 操作モード */
	public enum Mode {
		/** 新規 */
		NEW,
		/** 削除 */
		DELETE,
		/** エクセル */
		EXCEL,
		/** 確定 */
		SETTLE,
		/** 上位部門 */
		UPDEP,
		/** 下位部門 */
		LOWDEP
	}

	/** 指示画面 */
	protected MG0051DepartmentHierarchySelectionMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0051DepartmentHierarchySelectionDialog editView = null;

	/** 部門検索画面 */
	protected MG0051DepartmentHierarchySelectionDepDialog editDepView = null;

	/** 名称編集画面 */
	protected MG0051DepartmentHierarchySelectionNameDialog editNameView = null;

	/** 操作モード。現在操作中のモードを把握するために使用する。 */
	protected Mode mode = null;

	/** 部門のマップ **/
	protected HashMap<String, String> depMap;

	/** orgList **/
	protected List<DepartmentOrganization> orgList = new ArrayList<DepartmentOrganization>();

	/** keyスペース **/
	protected final String SPC = "<>";

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
	public MG0051DepartmentHierarchySelectionMasterPanel getPanel() {
		return mainView;
	}

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MG0051DepartmentHierarchySelectionMasterPanel(this);
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
				changedOrganizationCode(evt.getStateChange(), true);
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

		// [組織名称]ボタン押下
		mainView.btnOrganizationName.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnOrganizationName_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * [確定]ボタン押下
	 */
	protected void btnEditDepViewSettle_Click() {

		try {
			String level1 = "";
			String level2 = "";
			String level3 = "";
			String level4 = "";
			String level5 = "";
			String level6 = "";
			String level7 = "";
			String level8 = "";
			String level9 = "";

			// 確認メッセージを表示する
			if (!showConfirmMessage(editDepView, "Q00004")) {
				return;
			}

			List<DepartmentOrganization> list = getSelectedHierarchy(true);
			List<DepartmentOrganization> listDep = new ArrayList<DepartmentOrganization>();

			// 階層編集画面値を取得
			String upDep = editDepView.ctrlDepartmentReferenceUP.getCode();
			String lowDep = editDepView.ctrlDepartmentReferenceLow.getCode();

			// 画面値の入力チェック
			// 入力チェックを行う。
			if (!isEditDepViewInputRight()) {
				return;
			}

			// 上位部門の階層レベル
			int upLevel = -1;

			// 上位部門のレベルを取得
			for (DepartmentOrganization bean : list) {
				String condiDepCode = bean.getDepCode();
				if (condiDepCode.equals(upDep)) {
					upLevel = bean.getLevel();
					break;
				}
			}

			// レベル設定
			for (DepartmentOrganization bean : list) {
				String conditionDepCode = bean.getDepCode();
				if (conditionDepCode.equals(lowDep)) {
					bean.setCompanyCode(getCompanyCode());
					bean.setDepCode(lowDep);
					bean.setLevel(upLevel + 1);

					switch (bean.getLevel()) {
						case 1:
							level1 = conditionDepCode;
							break;
						case 2:
							level1 = bean.getLevel1();
							level2 = conditionDepCode;
							break;
						case 3:
							level1 = bean.getLevel1();
							level2 = bean.getLevel2();
							level3 = conditionDepCode;
							break;
						case 4:
							level1 = bean.getLevel1();
							level2 = bean.getLevel2();
							level3 = bean.getLevel3();
							level4 = conditionDepCode;
							break;
						case 5:
							level1 = bean.getLevel1();
							level2 = bean.getLevel2();
							level3 = bean.getLevel3();
							level4 = bean.getLevel4();
							level5 = conditionDepCode;
							break;
						case 6:
							level1 = bean.getLevel1();
							level2 = bean.getLevel2();
							level3 = bean.getLevel3();
							level4 = bean.getLevel4();
							level5 = bean.getLevel5();
							level6 = conditionDepCode;
							break;
						case 7:
							level1 = bean.getLevel1();
							level2 = bean.getLevel2();
							level3 = bean.getLevel3();
							level4 = bean.getLevel4();
							level5 = bean.getLevel5();
							level6 = bean.getLevel6();
							level7 = conditionDepCode;
							break;
						case 8:
							level1 = bean.getLevel1();
							level2 = bean.getLevel2();
							level3 = bean.getLevel3();
							level4 = bean.getLevel4();
							level5 = bean.getLevel5();
							level6 = bean.getLevel6();
							level7 = bean.getLevel7();
							level8 = conditionDepCode;
							break;
						case 9:
							level1 = bean.getLevel1();
							level2 = bean.getLevel2();
							level3 = bean.getLevel3();
							level4 = bean.getLevel4();
							level5 = bean.getLevel5();
							level6 = bean.getLevel6();
							level7 = bean.getLevel7();
							level8 = bean.getLevel8();
							level9 = conditionDepCode;
							break;
					}

					bean.setCode(bean.getCode());
					bean.setLevel0(bean.getLevel0());
					bean.setLevel1(level1);
					bean.setLevel2(level2);
					bean.setLevel3(level3);
					bean.setLevel4(level4);
					bean.setLevel5(level5);
					bean.setLevel6(level6);
					bean.setLevel7(level7);
					bean.setLevel8(level8);
					bean.setLevel9(level9);

				} else {
					bean.setCompanyCode(getCompanyCode());
					bean.setCode(bean.getCode());
					bean.setDepCode(conditionDepCode);
					bean.setLevel0(bean.getLevel0());
					bean.setLevel(bean.getLevel());
					bean.setLevel1(bean.getLevel1());
					bean.setLevel2(bean.getLevel2());
					bean.setLevel3(bean.getLevel3());
					bean.setLevel4(bean.getLevel4());
					bean.setLevel5(bean.getLevel5());
					bean.setLevel6(bean.getLevel6());
					bean.setLevel7(bean.getLevel7());
					bean.setLevel8(bean.getLevel8());
					bean.setLevel9(bean.getLevel9());

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
		for (DepartmentOrganization condition : list) {
			DepartmentOrganization bean = new DepartmentOrganization();

			bean.setCompanyCode(getCompanyCode());
			bean.setCode(condition.getCode());
			bean.setLevel0(condition.getLevel0());
			bean.setDepCode(condition.getDepCode());
			bean.setLevel(condition.getLevel());
			bean.setLevel1(condition.getLevel1());
			bean.setLevel2(condition.getLevel2());
			bean.setLevel3(condition.getLevel3());
			bean.setLevel4(condition.getLevel4());
			bean.setLevel5(condition.getLevel5());
			bean.setLevel6(condition.getLevel6());
			bean.setLevel7(condition.getLevel7());
			bean.setLevel8(condition.getLevel8());
			bean.setLevel9(condition.getLevel9());
			bean.setLevel1Name(depMap.get(condition.getLevel1()));
			bean.setLevel2Name(depMap.get(condition.getLevel2()));
			bean.setLevel3Name(depMap.get(condition.getLevel3()));
			bean.setLevel4Name(depMap.get(condition.getLevel4()));
			bean.setLevel5Name(depMap.get(condition.getLevel5()));
			bean.setLevel6Name(depMap.get(condition.getLevel6()));
			bean.setLevel7Name(depMap.get(condition.getLevel7()));
			bean.setLevel8Name(depMap.get(condition.getLevel8()));
			bean.setLevel9Name(depMap.get(condition.getLevel9()));

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
		TTable mainBmnSpred = mainView.ssDepartmentList;

		List<DepartmentOrganization> lvl1List = new ArrayList<DepartmentOrganization>();
		List<DepartmentOrganization> lvl2List = new ArrayList<DepartmentOrganization>();
		List<DepartmentOrganization> lvl3List = new ArrayList<DepartmentOrganization>();
		List<DepartmentOrganization> lvl4List = new ArrayList<DepartmentOrganization>();
		List<DepartmentOrganization> lvl5List = new ArrayList<DepartmentOrganization>();
		List<DepartmentOrganization> lvl6List = new ArrayList<DepartmentOrganization>();
		List<DepartmentOrganization> lvl7List = new ArrayList<DepartmentOrganization>();
		List<DepartmentOrganization> lvl8List = new ArrayList<DepartmentOrganization>();
		List<DepartmentOrganization> lvl9List = new ArrayList<DepartmentOrganization>();

		// 一覧をクリアする
		mainHieSpred.removeRow();

		// listの中を部門コード順に並べ替え
		Collections.sort(list, new sortList());

		// レベル事List作成
		for (DepartmentOrganization bean : list) {

			if (bean.getLevel() == 0) {
				continue;
			}

			else if (bean.getLevel() == 1) {
				lvl1List.add(bean);

			}

			else if (bean.getLevel() == 2) {
				lvl2List.add(bean);
			}

			else if (bean.getLevel() == 3) {
				lvl3List.add(bean);
			}

			else if (bean.getLevel() == 4) {
				lvl4List.add(bean);
			}

			else if (bean.getLevel() == 5) {
				lvl5List.add(bean);
			}

			else if (bean.getLevel() == 6) {
				lvl6List.add(bean);
			}

			else if (bean.getLevel() == 7) {
				lvl7List.add(bean);
			}

			else if (bean.getLevel() == 8) {
				lvl8List.add(bean);
			}

			else if (bean.getLevel() == 9) {
				lvl9List.add(bean);
			}
		}

		// sort順にスプレッド並べ替え
		for (DepartmentOrganization bean : lvl1List) {
			mainHieSpred.addRow(getRowData(bean));

			for (DepartmentOrganization bean2 : lvl2List) {
				if (bean.getLevel1().equals(bean2.getLevel1())) {
					mainHieSpred.addRow(getRowData(bean2));

					for (DepartmentOrganization bean3 : lvl3List) {
						if (bean2.getLevel2().equals(bean3.getLevel2())) {
							mainHieSpred.addRow(getRowData(bean3));

							for (DepartmentOrganization bean4 : lvl4List) {
								if (bean3.getLevel3().equals(bean4.getLevel3())) {
									mainHieSpred.addRow(getRowData(bean4));

									for (DepartmentOrganization bean5 : lvl5List) {
										if (bean4.getLevel4().equals(bean5.getLevel4())) {
											mainHieSpred.addRow(getRowData(bean5));

											for (DepartmentOrganization bean6 : lvl6List) {
												if (bean5.getLevel5().equals(bean6.getLevel5())) {
													mainHieSpred.addRow(getRowData(bean6));

													for (DepartmentOrganization bean7 : lvl7List) {
														if (bean6.getLevel6().equals(bean7.getLevel6())) {
															mainHieSpred.addRow(getRowData(bean7));

															for (DepartmentOrganization bean8 : lvl8List) {
																if (bean7.getLevel7().equals(bean8.getLevel7())) {
																	mainHieSpred.addRow(getRowData(bean8));

																	for (DepartmentOrganization bean9 : lvl9List) {
																		if (bean8.getLevel8().equals(bean9.getLevel8())) {
																			mainHieSpred.addRow(getRowData(bean9));
																		}
																	}
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		// 上位が下位になった場合、所属していた下位を左スプレッドへ移動。
		List<DepartmentOrganization> tblList = new ArrayList<DepartmentOrganization>();
		if (mainHieSpred.getRowCount() != list.size()) {
			for (int i = 0; mainHieSpred.getRowCount() > i; i++) {
				DepartmentOrganization depOrg = (DepartmentOrganization) mainHieSpred.getRowValueAt(i, DS2.bean2);
				tblList.add(depOrg);
			}

			for (DepartmentOrganization bean : list) {

				if (!tblList.contains(bean)) {
					String code = bean.getDepCode();
					String depName = depMap.get(code);
					mainBmnSpred.addRow(new Object[] { code, depName });
				}
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
	 * @param isUpdate
	 */
	protected void changedOrganizationCode(int state, boolean isUpdate) {
		try {
			orgList = new ArrayList<DepartmentOrganization>();
			List<Department> depList = new ArrayList<Department>();
			int lvl1 = mainView.ssHierarchyList.table.convertColumnIndexToView(DS2.level1.ordinal());
			int lvl2 = mainView.ssHierarchyList.table.convertColumnIndexToView(DS2.level2.ordinal());
			int lvl3 = mainView.ssHierarchyList.table.convertColumnIndexToView(DS2.level3.ordinal());
			int lvl4 = mainView.ssHierarchyList.table.convertColumnIndexToView(DS2.level4.ordinal());
			int lvl5 = mainView.ssHierarchyList.table.convertColumnIndexToView(DS2.level5.ordinal());
			int lvl6 = mainView.ssHierarchyList.table.convertColumnIndexToView(DS2.level6.ordinal());
			int lvl7 = mainView.ssHierarchyList.table.convertColumnIndexToView(DS2.level7.ordinal());
			int lvl8 = mainView.ssHierarchyList.table.convertColumnIndexToView(DS2.level8.ordinal());
			int lvl9 = mainView.ssHierarchyList.table.convertColumnIndexToView(DS2.level9.ordinal());

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
			orgList = getDepartmentOrganization(orgCondition);

			if (orgList.size() < 2) {
				setDepButtonEnabled(false);
			} else {
				setDepButtonEnabled(true);
			}

			if (orgList != null || !orgList.isEmpty()) {

				String lvl0Name = !Util.isNullOrEmpty(orgList.get(0).getDPK_LVL_0_NAME()) ? orgList.get(0)
					.getDPK_LVL_0_NAME() : getWord("C00991");
				String lvl1Name = !Util.isNullOrEmpty(orgList.get(0).getDPK_LVL_1_NAME()) ? orgList.get(0)
					.getDPK_LVL_1_NAME() : getWord("C02126");
				String lvl2Name = !Util.isNullOrEmpty(orgList.get(0).getDPK_LVL_2_NAME()) ? orgList.get(0)
					.getDPK_LVL_2_NAME() : getWord("C02127");
				String lvl3Name = !Util.isNullOrEmpty(orgList.get(0).getDPK_LVL_3_NAME()) ? orgList.get(0)
					.getDPK_LVL_3_NAME() : getWord("C02128");
				String lvl4Name = !Util.isNullOrEmpty(orgList.get(0).getDPK_LVL_4_NAME()) ? orgList.get(0)
					.getDPK_LVL_4_NAME() : getWord("C02129");
				String lvl5Name = !Util.isNullOrEmpty(orgList.get(0).getDPK_LVL_5_NAME()) ? orgList.get(0)
					.getDPK_LVL_5_NAME() : getWord("C02130");
				String lvl6Name = !Util.isNullOrEmpty(orgList.get(0).getDPK_LVL_6_NAME()) ? orgList.get(0)
					.getDPK_LVL_6_NAME() : getWord("C02131");
				String lvl7Name = !Util.isNullOrEmpty(orgList.get(0).getDPK_LVL_7_NAME()) ? orgList.get(0)
					.getDPK_LVL_7_NAME() : getWord("C02132");
				String lvl8Name = !Util.isNullOrEmpty(orgList.get(0).getDPK_LVL_8_NAME()) ? orgList.get(0)
					.getDPK_LVL_8_NAME() : getWord("C02133");
				String lvl9Name = !Util.isNullOrEmpty(orgList.get(0).getDPK_LVL_9_NAME()) ? orgList.get(0)
					.getDPK_LVL_9_NAME() : getWord("C02134");

				mainView.ctrlOrganizationName.setValue(orgList.get(0).getName());
				mainView.ctrlDepartment.lbl.setText(lvl0Name);
				mainView.ctrlDepartment.lbl.setToolTipText(lvl0Name);

				DefaultTableColumnModel columnModel = (DefaultTableColumnModel) mainView.ssHierarchyList.table
					.getColumnModel();

				columnModel.getColumn(lvl1).setHeaderValue(lvl1Name);
				columnModel.getColumn(lvl2).setHeaderValue(lvl2Name);
				columnModel.getColumn(lvl3).setHeaderValue(lvl3Name);
				columnModel.getColumn(lvl4).setHeaderValue(lvl4Name);
				columnModel.getColumn(lvl5).setHeaderValue(lvl5Name);
				columnModel.getColumn(lvl6).setHeaderValue(lvl6Name);
				columnModel.getColumn(lvl7).setHeaderValue(lvl7Name);
				columnModel.getColumn(lvl8).setHeaderValue(lvl8Name);
				columnModel.getColumn(lvl9).setHeaderValue(lvl9Name);

			}
			// 部門検索条件の定義
			DepartmentSearchCondition depCondition = getSearchCondition();

			// DBから部門情報を取得する
			depList = getDepartment(depCondition);

			// 部門情報をマップへセットする
			depMap = new HashMap<String, String>();

			for (Department dep : depList) {
				// 部門コード
				String code = valueString(dep.getCode());
				// 部門名称
				String text = valueString(dep.getName());
				depMap.put(code, text);
			}

			// レベル０初期化
			mainView.ctrlDepartment.clear();

			// レベル０
			for (DepartmentOrganization org : orgList) {
				if (org.getLevel() == 0) {
					String departmentCode = org.getDepCode();
					mainView.ctrlDepartment.setCode(departmentCode);
					mainView.ctrlDepartment.setNames(org.getDepNames());
					break;
				}
			}

			// フィルター
			if (!Util.isNullOrEmpty(depList)) {
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

			if (isUpdate) {
				// 部門一覧へ反映
				initList(depList);
				initListHierarchy(orgList);

				setMainButtonEnabled(!Util.isNullOrEmpty(mainView.ctrlOrganizationCode.getSelectedItemValue()));
			}

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
		setDepUpLowButtonEnabled(false);
		// 組織名称、レベルテキストクリア
		mainView.ctrlOrganizationName.clear();
		mainView.ctrlDepartment.lbl.setText(getWord("C00991"));
		mainView.ctrlDepartment.lbl.setToolTipText(getWord("C00991"));

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
		mainView.btnOrganizationName.setEnabled(bln);
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
	protected void setDepUpLowButtonEnabled(boolean bln) {
		mainView.btnDepartmentAdd.setEnabled(bln);
		mainView.btnDepartmentCancellation.setEnabled(bln);

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
	 * 指示画面[組織名称設定]ボタン押下
	 */
	protected void btnOrganizationName_Click() {

		try {

			// 編集画面を生成
			createEditNameView();
			// 編集画面を初期化する
			initEditNameView();

			// 編集画面を表示
			editNameView.setLocationRelativeTo(null);
			editNameView.setVisible(true);

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

		if (mainView.ssHierarchyList.getSelectedRow() == -1) {
			// 一覧からデータを選択してください。
			showMessage("I00043");
			return false;
		}

		return true;
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
				depCode = ((String) mainHieSpred.getRowValueAt(row[i], DS2.codeDep));

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

				depCode = ((String) mainHieSpred.getRowValueAt(row[i], DS2.codeDep));
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

	}

	/**
	 * [部門削除]ボタン押下
	 */
	protected void btnDepartmentCancellation_Click() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		TTable mainBmnSpred = mainView.ssDepartmentList;

		int levelDel = 0;
		int selectedRows = mainHieSpred.getRowCount();
		String dep = (String) mainHieSpred.getSelectedRowValueAt(DS2.codeDep);
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

			String code = (String) mainHieSpred.getRowValueAt(rowConst, DS2.codeDep);
			String depName = depMap.get(code);
			// 左スプレッドへ追加
			mainBmnSpred.addRow(new Object[] { code, depName });
			// 選択元から削除
			mainHieSpred.removeRow(rowConst);
			row++;
			if (mainHieSpred.getRowCount() == 0) {
				// 階層スプレッド0行だったら上位下位ボタン押下不可
				setDepButtonEnabled(false);
			}
			if (row >= selectedRows) {
				break;
			}

			while (levelDel < list.get(row).getLevel()) {
				String code2 = (String) mainHieSpred.getRowValueAt(rowConst, DS2.codeDep);
				String depName2 = depMap.get(code2);

				// 左スプレッドへ追加
				mainBmnSpred.addRow(new Object[] { code2, depName2 });
				// 選択元から削除
				mainHieSpred.removeRow(rowConst);
				row++;
				if (mainHieSpred.getRowCount() == 0) {
					// 階層スプレッド0行だったら上位下位ボタン押下不可
					setDepButtonEnabled(false);
				}
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
		TTable mainHieSpred = mainView.ssHierarchyList;
		// 一覧をクリアする
		mainBmnSpred.removeRow();
		mainHieSpred.removeRow();

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
		for (DepartmentOrganization bean : list) {
			if (bean.getLevel() != 0) {// 部門レベル０以外はセットする
				mainHieSpred.addRow(getRowData(bean));
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
		return DepartmentOrganizationManager.class;
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
			byte[] data = (byte[]) request(getModelClass(), "getDepartmentOrganizationNameExcel", condition);

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
	 * 組織名称[確定]ボタン押下
	 */
	protected void btnSettleName_Click() {

		try {

			if (!isEditDepViewInputName()) {
				return;
			}

			// 確認メッセージを表示する
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			DepartmentOrganization list = getSelectedName();

			// 登録処理
			request(getModelClass(), "entryDepartmentNameOrganization", list);

			// 処理完了メッセージ
			showMessage(mainView, "I00008");

			// リフレッシュ
			changedOrganizationCode(ItemEvent.SELECTED, false);

			// ウインドウ閉じる
			btnEditNameViewClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {

			if (!isEditDepViewInput()) {
				return;
			}

			// 確認メッセージを表示する
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			// List<DepartmentOrganization> list = getSelectedHierarchy();
			List<DepartmentOrganization> list = getSelectedDepHie();

			// 登録処理
			request(getModelClass(), "entryDepartmentOrganization",
				mainView.ctrlOrganizationCode.getSelectedOrganizationCode(), mainView.ctrlOrganizationName.getValue(),
				list);

			// 処理完了メッセージ
			showMessage(mainView, "I00008");

			// リフレッシュ
			changedOrganizationCode(ItemEvent.SELECTED, true);

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
	 * @param isConfim 部門設定確定押下時
	 * @return 一覧で選択されている部門階層
	 * @throws TException
	 */
	protected List<DepartmentOrganization> getSelectedHierarchy(boolean isConfim) throws TException {

		DepartmentOrganization bean = new DepartmentOrganization();
		TTable mainHieSpred = mainView.ssHierarchyList;
		String lowDep = editDepView.ctrlDepartmentReferenceLow.getCode();
		String upDep = editDepView.ctrlDepartmentReferenceUP.getCode();
		List<DepartmentOrganization> list = new ArrayList<DepartmentOrganization>();
		Map<String, String> map = new HashMap<String, String>();

		if (isConfim) {
			for (int row = 0; row < mainHieSpred.getRowCount(); row++) {

				// 確定押下時のみ各部門のレベル取得
				bean = (DepartmentOrganization) mainHieSpred.getRowValueAt(row, DS2.bean2);

				// 部門階層は９階層以上設定できません
				if (upDep.equals(bean.getDepCode()) && bean.getLevel() == 9) {
					throw new TException(getMessage("W00153"));
				}

				map.put(bean.getDepCode() + SPC + "1", bean.getLevel1());
				map.put(bean.getDepCode() + SPC + "2", bean.getLevel2());
				map.put(bean.getDepCode() + SPC + "3", bean.getLevel3());
				map.put(bean.getDepCode() + SPC + "4", bean.getLevel4());
				map.put(bean.getDepCode() + SPC + "5", bean.getLevel5());
				map.put(bean.getDepCode() + SPC + "6", bean.getLevel6());
				map.put(bean.getDepCode() + SPC + "7", bean.getLevel7());
				map.put(bean.getDepCode() + SPC + "8", bean.getLevel8());
				map.put(bean.getDepCode() + SPC + "9", bean.getLevel9());

			}
		}

		for (int row = 0; row < mainHieSpred.getRowCount(); row++) {

			bean = (DepartmentOrganization) mainHieSpred.getRowValueAt(row, DS2.bean2);

			String depCode = Util.avoidNull(mainHieSpred.getRowValueAt(row, DS2.codeDep));
			bean.setCompanyCode(getCompanyCode());
			bean.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());
			bean.setLevel0((mainView.ctrlDepartment.getCode()));
			bean.setDepCode(depCode);
			bean.setLevel(valueInt(mainHieSpred.getRowValueAt(row, DS2.level)));

			if (depCode.equals(lowDep)) {
				// 下位部門に上位部門のレベル設定
				bean.setLevel1(map.get(upDep + SPC + "1"));
				bean.setLevel2(map.get(upDep + SPC + "2"));
				bean.setLevel3(map.get(upDep + SPC + "3"));
				bean.setLevel4(map.get(upDep + SPC + "4"));
				bean.setLevel5(map.get(upDep + SPC + "5"));
				bean.setLevel6(map.get(upDep + SPC + "6"));
				bean.setLevel7(map.get(upDep + SPC + "7"));
				bean.setLevel8(map.get(upDep + SPC + "8"));
				bean.setLevel9(map.get(upDep + SPC + "9"));

			} else {

				if (bean.getLevel() == 1) {

					bean.setLevel1(depCode);

				} else if (bean.getLevel() == 2) {

					for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {
						DepartmentOrganization bean2 = (DepartmentOrganization) mainHieSpred.getRowValueAt(row2,
							DS2.bean2);

						if (bean.getLevel1() == null && bean2.getLevel() == 1) {
							bean.setLevel1(bean2.getDepCode());
						}

					}
					bean.setLevel2(depCode);

				} else if (bean.getLevel() == 3) {

					for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {
						DepartmentOrganization bean2 = (DepartmentOrganization) mainHieSpred.getRowValueAt(row2,
							DS2.bean2);

						if (bean.getLevel1() == null && bean2.getLevel() == 1) {
							bean.setLevel1(bean2.getDepCode());
						} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
							bean.setLevel2(bean2.getDepCode());
						}
					}
					bean.setLevel3(depCode);

				} else if (bean.getLevel() == 4) {

					for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {
						DepartmentOrganization bean2 = (DepartmentOrganization) mainHieSpred.getRowValueAt(row,
							DS2.bean2);

						if (bean.getLevel1() == null && bean2.getLevel() == 1) {
							bean.setLevel1(bean2.getDepCode());
						} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
							bean.setLevel2(bean2.getDepCode());
						} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
							bean.setLevel3(bean2.getDepCode());
						}
					}
					bean.setLevel4(depCode);
				} else if (bean.getLevel() == 5) {

					for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {

						DepartmentOrganization bean2 = (DepartmentOrganization) mainHieSpred.getRowValueAt(row,
							DS2.bean2);

						if (bean.getLevel1() == null && bean2.getLevel() == 1) {
							bean.setLevel1(bean2.getDepCode());
						} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
							bean.setLevel2(bean2.getDepCode());
						} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
							bean.setLevel3(bean2.getDepCode());
						} else if (bean.getLevel4() == null && bean2.getLevel() == 4) {
							bean.setLevel4(bean2.getDepCode());
						}
					}
					bean.setLevel5(depCode);

				} else if (bean.getLevel() == 6) {

					for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {
						DepartmentOrganization bean2 = (DepartmentOrganization) mainHieSpred.getRowValueAt(row,
							DS2.bean2);

						if (bean.getLevel1() == null && bean2.getLevel() == 1) {
							bean.setLevel1(bean2.getDepCode());
						} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
							bean.setLevel2(bean2.getDepCode());
						} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
							bean.setLevel3(bean2.getDepCode());
						} else if (bean.getLevel4() == null && bean2.getLevel() == 4) {
							bean.setLevel4(bean2.getDepCode());
						} else if (bean.getLevel5() == null && bean2.getLevel() == 5) {
							bean.setLevel5(bean2.getDepCode());
						}
					}
					bean.setLevel6(depCode);

				} else if (bean.getLevel() == 7) {

					for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {
						DepartmentOrganization bean2 = (DepartmentOrganization) mainHieSpred.getRowValueAt(row,
							DS2.bean2);

						if (bean.getLevel1() == null && bean2.getLevel() == 1) {
							bean.setLevel1(bean2.getDepCode());
						} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
							bean.setLevel2(bean2.getDepCode());
						} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
							bean.setLevel3(bean2.getDepCode());
						} else if (bean.getLevel4() == null && bean2.getLevel() == 4) {
							bean.setLevel4(bean2.getDepCode());
						} else if (bean.getLevel5() == null && bean2.getLevel() == 5) {
							bean.setLevel5(bean2.getDepCode());
						} else if (bean.getLevel6() == null && bean2.getLevel() == 6) {
							bean.setLevel6(bean2.getDepCode());
						}
					}

					bean.setLevel7(depCode);

				} else if (bean.getLevel() == 8) {

					for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {
						DepartmentOrganization bean2 = (DepartmentOrganization) mainHieSpred.getRowValueAt(row,
							DS2.bean2);

						if (bean.getLevel1() == null && bean2.getLevel() == 1) {
							bean.setLevel1(bean2.getDepCode());
						} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
							bean.setLevel2(bean2.getDepCode());
						} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
							bean.setLevel3(bean2.getDepCode());
						} else if (bean.getLevel4() == null && bean2.getLevel() == 4) {
							bean.setLevel4(bean2.getDepCode());
						} else if (bean.getLevel5() == null && bean2.getLevel() == 5) {
							bean.setLevel5(bean2.getDepCode());
						} else if (bean.getLevel6() == null && bean2.getLevel() == 6) {
							bean.setLevel6(bean2.getDepCode());
						} else if (bean.getLevel7() == null && bean2.getLevel() == 7) {
							bean.setLevel7(bean2.getDepCode());
						}
					}

					bean.setLevel8(depCode);

				} else if (bean.getLevel() == 9) {

					for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {
						DepartmentOrganization bean2 = (DepartmentOrganization) mainHieSpred.getRowValueAt(row,
							DS2.bean2);

						if (bean.getLevel1() == null && bean2.getLevel() == 1) {
							bean.setLevel1(bean2.getDepCode());
						} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
							bean.setLevel2(bean2.getDepCode());
						} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
							bean.setLevel3(bean2.getDepCode());
						} else if (bean.getLevel4() == null && bean2.getLevel() == 4) {
							bean.setLevel4(bean2.getDepCode());
						} else if (bean.getLevel5() == null && bean2.getLevel() == 5) {
							bean.setLevel5(bean2.getDepCode());
						} else if (bean.getLevel6() == null && bean2.getLevel() == 6) {
							bean.setLevel6(bean2.getDepCode());
						} else if (bean.getLevel7() == null && bean2.getLevel() == 7) {
							bean.setLevel7(bean2.getDepCode());
						} else if (bean.getLevel8() == null && bean2.getLevel() == 8) {
							bean.setLevel8(bean2.getDepCode());
						}
					}
					bean.setLevel9(depCode);
				}

			}

			list.add(bean);

		}

		return list;
	}

	/**
	 * 名称追加画面の入力値取得
	 * 
	 * @return bean
	 */
	protected DepartmentOrganization getSelectedName() {

		DepartmentOrganization bean = new DepartmentOrganization();
		String lvl0 = !Util.isNullOrEmpty(editNameView.ctrlLevel0.getValue()) ? editNameView.ctrlLevel0.getValue()
			: getWord("C00991");
		String lvl1 = !Util.isNullOrEmpty(editNameView.ctrlLevel1.getValue()) ? editNameView.ctrlLevel1.getValue()
			: getWord("C02126");
		String lvl2 = !Util.isNullOrEmpty(editNameView.ctrlLevel2.getValue()) ? editNameView.ctrlLevel2.getValue()
			: getWord("C02127");
		String lvl3 = !Util.isNullOrEmpty(editNameView.ctrlLevel3.getValue()) ? editNameView.ctrlLevel3.getValue()
			: getWord("C02128");
		String lvl4 = !Util.isNullOrEmpty(editNameView.ctrlLevel4.getValue()) ? editNameView.ctrlLevel4.getValue()
			: getWord("C02129");
		String lvl5 = !Util.isNullOrEmpty(editNameView.ctrlLevel5.getValue()) ? editNameView.ctrlLevel5.getValue()
			: getWord("C02130");
		String lvl6 = !Util.isNullOrEmpty(editNameView.ctrlLevel6.getValue()) ? editNameView.ctrlLevel6.getValue()
			: getWord("C02131");
		String lvl7 = !Util.isNullOrEmpty(editNameView.ctrlLevel7.getValue()) ? editNameView.ctrlLevel7.getValue()
			: getWord("C02132");
		String lvl8 = !Util.isNullOrEmpty(editNameView.ctrlLevel8.getValue()) ? editNameView.ctrlLevel8.getValue()
			: getWord("C02133");
		String lvl9 = !Util.isNullOrEmpty(editNameView.ctrlLevel9.getValue()) ? editNameView.ctrlLevel9.getValue()
			: getWord("C02134");

		bean.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());
		bean.setName(editNameView.ctrlOrganizationName.getValue());
		bean.setDPK_LVL_0_NAME(lvl0);
		bean.setDPK_LVL_1_NAME(lvl1);
		bean.setDPK_LVL_2_NAME(lvl2);
		bean.setDPK_LVL_3_NAME(lvl3);
		bean.setDPK_LVL_4_NAME(lvl4);
		bean.setDPK_LVL_5_NAME(lvl5);
		bean.setDPK_LVL_6_NAME(lvl6);
		bean.setDPK_LVL_7_NAME(lvl7);
		bean.setDPK_LVL_8_NAME(lvl8);
		bean.setDPK_LVL_9_NAME(lvl9);

		return bean;
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
		editView = new MG0051DepartmentHierarchySelectionDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent(0);

	}

	/**
	 * 組織名称編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditNameView() {

		// 編集画面を生成
		editNameView = new MG0051DepartmentHierarchySelectionNameDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent(2);

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 * 
	 * @throws TException
	 */
	protected void createEditDepView() throws TException {
		TTable mainHieSpred = mainView.ssHierarchyList;
		// 編集画面を生成
		editDepView = new MG0051DepartmentHierarchySelectionDepDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent(1);

		// 検索処理に制限をかける
		List<DepartmentOrganization> list = getSelectedHierarchy(false);
		String[] dpCodeList = new String[list.size()];
		for (int row = 0; row < list.size(); row++) {
			String condiDepCode = list.get(row).getDepCode();
			if (!(condiDepCode.equals(mainHieSpred.getSelectedRowValueAt(DS2.codeDep)))) {
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

			// 名称編集
			case 2:
				// [確定]ボタン押下
				editNameView.btnSettle.addActionListener(new ActionListener() {

					public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
						editNameView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						btnSettleName_Click();
						editNameView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				});

				// [戻る]ボタン押下
				editNameView.btnClose.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						editNameView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						btnEditNameViewClose_Click();
						editNameView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
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
	 * 名称編集画面を初期化する
	 */
	protected void initEditNameView() {

		// 画面
		editNameView.setTitle(getWord("C11968"));
		editNameView.ctrlOrganizationName.setValue((String) mainView.ctrlOrganizationName.getValue());
		editNameView.ctrlLevel0.setValue(orgList.get(0).getDPK_LVL_0_NAME());
		editNameView.ctrlLevel1.setValue(orgList.get(0).getDPK_LVL_1_NAME());
		editNameView.ctrlLevel2.setValue(orgList.get(0).getDPK_LVL_2_NAME());
		editNameView.ctrlLevel3.setValue(orgList.get(0).getDPK_LVL_3_NAME());
		editNameView.ctrlLevel4.setValue(orgList.get(0).getDPK_LVL_4_NAME());
		editNameView.ctrlLevel5.setValue(orgList.get(0).getDPK_LVL_5_NAME());
		editNameView.ctrlLevel6.setValue(orgList.get(0).getDPK_LVL_6_NAME());
		editNameView.ctrlLevel7.setValue(orgList.get(0).getDPK_LVL_7_NAME());
		editNameView.ctrlLevel8.setValue(orgList.get(0).getDPK_LVL_8_NAME());
		editNameView.ctrlLevel9.setValue(orgList.get(0).getDPK_LVL_9_NAME());

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
			List<DepartmentOrganization> depOrgList = getDepartmentOrganization(orgCondition);

			// 部門検索条件の定義
			DepartmentSearchCondition depCondition = getSearchCondition();

			// DBから部門情報を取得する
			List<Department> depList = getDepartment(depCondition);

			// フィルター
			if (depList != null) {
				for (DepartmentOrganization org : depOrgList) {
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
		org.setName(editView.ctrlOrganizationName.getValue());
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
	 * 名称編集画面[戻る]ボタン押下
	 */
	protected void btnEditNameViewClose_Click() {
		editNameView.setVisible(false);
	}

	/**
	 * 部門階層設定画面[戻る]ボタン押下
	 */
	protected void btnEditDepViewClose_Click() {
		editDepView.setVisible(false);
	}

	/**
	 * 部門コード順にList内をsort
	 */
	public class sortList implements Comparator<DepartmentOrganization> {

		@Override
		public int compare(DepartmentOrganization detail1, DepartmentOrganization detail2) {

			String depCode1 = detail1.getDepCode();
			String depCode2 = detail2.getDepCode();

			return depCode1.compareTo(depCode2);
		}
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

		// 組織コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlOrganizationName.getValue())) {
			showMessage(editView, "I00037", "C11967"); // 組織名称
			editView.ctrlOrganizationName.requestFocus();
			return false;
		}

		// レベル０が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDepartmentReference.getCode())) {
			showMessage(editView, "I00037", "C00991"); // レベル０
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

	/**
	 * 組織名称設定画面で入力した値が妥当かをチェックする
	 * 
	 * @return 組織名称設定画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 */
	protected boolean isEditDepViewInput() {
		// 組織名称ブランクでエラー
		if (Util.isNullOrEmpty(mainView.ctrlOrganizationName.getValue())) {
			showMessage(editDepView, "I00204", "C11967"); // 組織名称を設定してください。
			mainView.ctrlOrganizationName.requestFocus();
			return false;
		}
		return true;
	}

	/**
	 * 組織名称設定画面で入力した値が妥当かをチェックする
	 * 
	 * @return 組織名称設定画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 */
	protected boolean isEditDepViewInputName() {
		// 組織名称ブランクでエラー
		if (Util.isNullOrEmpty(editNameView.ctrlOrganizationName.getValue())) {
			showMessage(editDepView, "I00037", "C11967"); // 組織名称
			editNameView.ctrlOrganizationName.requestFocus();
			return false;
		}
		return true;
	}

}
