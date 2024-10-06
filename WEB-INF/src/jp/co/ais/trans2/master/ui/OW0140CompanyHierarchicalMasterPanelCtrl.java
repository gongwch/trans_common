package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.table.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.master.ui.OW0140CompanyHierarchicalMasterPanel.CS1;
import jp.co.ais.trans2.master.ui.OW0140CompanyHierarchicalMasterPanel.CS2;
import jp.co.ais.trans2.model.company.*;

/**
 * 会社階層マスタのコントローラ
 * 
 * @author AIS
 */
public class OW0140CompanyHierarchicalMasterPanelCtrl extends TController {

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
		/** 上位会社 */
		, UPCMP
		/** 下位会社 */
		, LOWCMP
	}

	/** 指示画面 */
	protected OW0140CompanyHierarchicalMasterPanel mainView = null;

	/** 編集画面 */
	protected OW0140CompanyHierarchicalDialog editView = null;

	/** 会社検索画面 */
	protected OW0140CompanyHierarchicalComDialog editCmpView = null;

	/** 名称編集画面 */
	protected OW0140CompanyHierarchicalNameDialog editNameView = null;

	/** 操作モード。現在操作中のモードを把握するために使用する。 */
	protected Mode mode = null;

	/** 会社のマップ **/
	protected HashMap<String, String> cmpMap;

	/** orgList **/
	protected List<CompanyOrganization> orgList = new ArrayList<CompanyOrganization>();

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
	public OW0140CompanyHierarchicalMasterPanel getPanel() {
		return mainView;
	}

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new OW0140CompanyHierarchicalMasterPanel(this);
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
		// [上位会社]ボタン押下
		mainView.btnUpperCompany.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnUpperCompany_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [下位会社]ボタン押下
		mainView.btnLowerCompany.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnLowerCompany_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [会社追加]ボタン押下
		mainView.btnCompanyAdd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCompanyAdd_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [会社削除]ボタン押下
		mainView.btnCompanyCancellation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCompanyCancellation_Click();
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
	protected void btnEditCmpViewSettle_Click() {

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
			if (!showConfirmMessage(editCmpView, "Q00004")) {
				return;
			}

			List<CompanyOrganization> list = getSelectedHierarchy(true);
			List<CompanyOrganization> listCmp = new ArrayList<CompanyOrganization>();

			// 階層編集画面値を取得
			String upCmp = editCmpView.ctrlCompanyReferenceUP.getCode();
			String lowCmp = editCmpView.ctrlCompanyReferenceLow.getCode();

			// 画面値の入力チェック
			// 入力チェックを行う。
			if (!isEditCmpViewInputRight()) {
				return;
			}

			// 上位会社の階層レベル
			int upLevel = -1;

			// 上位会社のレベルを取得
			for (CompanyOrganization bean : list) {
				String condiCmpCode = bean.getCmpCode();
				if (condiCmpCode.equals(upCmp)) {
					upLevel = bean.getLevel();
					break;
				}
			}

			// レベル設定
			for (CompanyOrganization bean : list) {
				String conditionCmpCode = bean.getCmpCode();
				if (conditionCmpCode.equals(lowCmp)) {
					bean.setCompanyCode(getCompanyCode());
					bean.setCmpCode(lowCmp);
					bean.setLevel(upLevel + 1);

					switch (bean.getLevel()) {
						case 1:
							level1 = conditionCmpCode;
							break;
						case 2:
							level1 = bean.getLevel1();
							level2 = conditionCmpCode;
							break;
						case 3:
							level1 = bean.getLevel1();
							level2 = bean.getLevel2();
							level3 = conditionCmpCode;
							break;
						case 4:
							level1 = bean.getLevel1();
							level2 = bean.getLevel2();
							level3 = bean.getLevel3();
							level4 = conditionCmpCode;
							break;
						case 5:
							level1 = bean.getLevel1();
							level2 = bean.getLevel2();
							level3 = bean.getLevel3();
							level4 = bean.getLevel4();
							level5 = conditionCmpCode;
							break;
						case 6:
							level1 = bean.getLevel1();
							level2 = bean.getLevel2();
							level3 = bean.getLevel3();
							level4 = bean.getLevel4();
							level5 = bean.getLevel5();
							level6 = conditionCmpCode;
							break;
						case 7:
							level1 = bean.getLevel1();
							level2 = bean.getLevel2();
							level3 = bean.getLevel3();
							level4 = bean.getLevel4();
							level5 = bean.getLevel5();
							level6 = bean.getLevel6();
							level7 = conditionCmpCode;
							break;
						case 8:
							level1 = bean.getLevel1();
							level2 = bean.getLevel2();
							level3 = bean.getLevel3();
							level4 = bean.getLevel4();
							level5 = bean.getLevel5();
							level6 = bean.getLevel6();
							level7 = bean.getLevel7();
							level8 = conditionCmpCode;
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
							level9 = conditionCmpCode;
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
					bean.setCmpCode(conditionCmpCode);
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
				listCmp.add(bean);
			}
			List<CompanyOrganization> listOrg = modifyNameCode(listCmp);
			modifyListHierarchy(listOrg);

			// 処理完了メッセージ
			showMessage(editCmpView, "I00008");

			// 画面を閉じる
			btnEditCmpViewClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * コードと名称を変換
	 * 
	 * @param list 会社階層
	 * @return list 会社階層
	 */
	protected List<CompanyOrganization> modifyNameCode(List<CompanyOrganization> list) {

		List<CompanyOrganization> listConv = new ArrayList<CompanyOrganization>();
		for (CompanyOrganization condition : list) {
			CompanyOrganization bean = new CompanyOrganization();

			bean.setCompanyCode(getCompanyCode());
			bean.setCode(condition.getCode());
			bean.setLevel0(condition.getLevel0());
			bean.setCmpCode(condition.getCmpCode());
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
			bean.setLevel1Name(cmpMap.get(condition.getLevel1()));
			bean.setLevel2Name(cmpMap.get(condition.getLevel2()));
			bean.setLevel3Name(cmpMap.get(condition.getLevel3()));
			bean.setLevel4Name(cmpMap.get(condition.getLevel4()));
			bean.setLevel5Name(cmpMap.get(condition.getLevel5()));
			bean.setLevel6Name(cmpMap.get(condition.getLevel6()));
			bean.setLevel7Name(cmpMap.get(condition.getLevel7()));
			bean.setLevel8Name(cmpMap.get(condition.getLevel8()));
			bean.setLevel9Name(cmpMap.get(condition.getLevel9()));

			listConv.add(bean);
		}

		return listConv;
	}

	/**
	 * 階層マスタよりデータを取得し、一覧に設定する。
	 * 
	 * @param list 会社階層
	 */
	protected void modifyListHierarchy(List<CompanyOrganization> list) {

		TTable mainHieSpred = mainView.ssHierarchyList;
		TTable mainCmpSpred = mainView.ssCompanyList;

		List<CompanyOrganization> lvl1List = new ArrayList<CompanyOrganization>();
		List<CompanyOrganization> lvl2List = new ArrayList<CompanyOrganization>();
		List<CompanyOrganization> lvl3List = new ArrayList<CompanyOrganization>();
		List<CompanyOrganization> lvl4List = new ArrayList<CompanyOrganization>();
		List<CompanyOrganization> lvl5List = new ArrayList<CompanyOrganization>();
		List<CompanyOrganization> lvl6List = new ArrayList<CompanyOrganization>();
		List<CompanyOrganization> lvl7List = new ArrayList<CompanyOrganization>();
		List<CompanyOrganization> lvl8List = new ArrayList<CompanyOrganization>();
		List<CompanyOrganization> lvl9List = new ArrayList<CompanyOrganization>();

		// 一覧をクリアする
		mainHieSpred.removeRow();

		// listの中を会社コード順に並べ替え
		Collections.sort(list, new sortList());

		// レベル事List作成
		for (CompanyOrganization bean : list) {

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
		for (CompanyOrganization bean : lvl1List) {
			mainHieSpred.addRow(getRowData(bean));

			for (CompanyOrganization bean2 : lvl2List) {
				if (bean.getLevel1().equals(bean2.getLevel1())) {
					mainHieSpred.addRow(getRowData(bean2));

					for (CompanyOrganization bean3 : lvl3List) {
						if (bean2.getLevel2().equals(bean3.getLevel2())) {
							mainHieSpred.addRow(getRowData(bean3));

							for (CompanyOrganization bean4 : lvl4List) {
								if (bean3.getLevel3().equals(bean4.getLevel3())) {
									mainHieSpred.addRow(getRowData(bean4));

									for (CompanyOrganization bean5 : lvl5List) {
										if (bean4.getLevel4().equals(bean5.getLevel4())) {
											mainHieSpred.addRow(getRowData(bean5));

											for (CompanyOrganization bean6 : lvl6List) {
												if (bean5.getLevel5().equals(bean6.getLevel5())) {
													mainHieSpred.addRow(getRowData(bean6));

													for (CompanyOrganization bean7 : lvl7List) {
														if (bean6.getLevel6().equals(bean7.getLevel6())) {
															mainHieSpred.addRow(getRowData(bean7));

															for (CompanyOrganization bean8 : lvl8List) {
																if (bean7.getLevel7().equals(bean8.getLevel7())) {
																	mainHieSpred.addRow(getRowData(bean8));

																	for (CompanyOrganization bean9 : lvl9List) {
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
		List<CompanyOrganization> tblList = new ArrayList<CompanyOrganization>();
		if (mainHieSpred.getRowCount() != list.size()) {
			for (int i = 0; mainHieSpred.getRowCount() > i; i++) {
				CompanyOrganization cmpOrg = (CompanyOrganization) mainHieSpred.getRowValueAt(i, CS2.bean2);
				tblList.add(cmpOrg);
			}

			for (CompanyOrganization bean : list) {

				if (!tblList.contains(bean)) {
					String code = bean.getCmpCode();
					String cmpName = cmpMap.get(code);
					mainCmpSpred.addRow(new Object[] { code, cmpName });
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
			orgList = new ArrayList<CompanyOrganization>();
			List<Company> cmpList = new ArrayList<Company>();
			int lvl1 = mainView.ssHierarchyList.table.convertColumnIndexToView(CS2.level1.ordinal());
			int lvl2 = mainView.ssHierarchyList.table.convertColumnIndexToView(CS2.level2.ordinal());
			int lvl3 = mainView.ssHierarchyList.table.convertColumnIndexToView(CS2.level3.ordinal());
			int lvl4 = mainView.ssHierarchyList.table.convertColumnIndexToView(CS2.level4.ordinal());
			int lvl5 = mainView.ssHierarchyList.table.convertColumnIndexToView(CS2.level5.ordinal());
			int lvl6 = mainView.ssHierarchyList.table.convertColumnIndexToView(CS2.level6.ordinal());
			int lvl7 = mainView.ssHierarchyList.table.convertColumnIndexToView(CS2.level7.ordinal());
			int lvl8 = mainView.ssHierarchyList.table.convertColumnIndexToView(CS2.level8.ordinal());
			int lvl9 = mainView.ssHierarchyList.table.convertColumnIndexToView(CS2.level9.ordinal());
			if (Util.isNullOrEmpty(mainView.ctrlOrganizationCode.getSelectedOrganizationCode())) {

				// レベル０初期化
				mainView.ctrlCompany.clear();

				initMainView();

				return;
			}

			if (ItemEvent.SELECTED != state) {
				return;
			}

			// 組織検索条件の定義
			CompanyOrganizationSearchCondition orgCondition = getTreeSearchCondition();

			// DBから組織情報を取得する
			orgList = getCompanyOrganization(orgCondition);

			if (orgList.size() < 2) {
				setCmpButtonEnabled(false);
			} else {
				setCmpButtonEnabled(true);
			}
			if (orgList != null || orgList.isEmpty()) {

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

				mainView.ctrlCompany.lbl.setText(lvl0Name);
				mainView.ctrlCompany.lbl.setToolTipText(lvl0Name);

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
			// 会社検索条件の定義
			CompanySearchCondition cmpCondition = getSearchCondition();

			// DBから会社情報を取得する
			cmpList = getCompany(cmpCondition);

			// 会社情報をマップへセットする
			cmpMap = new HashMap<String, String>();

			for (Company cmp : cmpList) {
				// 会社コード
				String code = valueString(cmp.getCode());
				// 会社名称
				String text = valueString(cmp.getName());
				cmpMap.put(code, text);
			}

			// レベル０初期化
			mainView.ctrlCompany.clear();

			// レベル０
			for (CompanyOrganization org : orgList) {
				if (org.getLevel() == 0) {
					String cmpCode = org.getCmpCode();
					mainView.ctrlCompany.setCode(cmpCode);
					mainView.ctrlCompany.setNames(org.getLevel0Name());
					break;
				}
			}

			// フィルター
			if (!Util.isNullOrEmpty(cmpList)) {
				for (CompanyOrganization org : orgList) {
					String companyCode = org.getCmpCode();

					for (int i = cmpList.size() - 1; i >= 0; i--) {
						if (companyCode.equals(cmpList.get(i).getCode())) {
							cmpList.remove(i);
							break;
						}
					}
				}
			}

			if (isUpdate) {
				// 会社一覧へ反映
				initList(cmpList);
				initListHierarchy(orgList);

				setMainButtonEnabled(!Util.isNullOrEmpty(mainView.ctrlOrganizationCode.getSelectedItemValue()));
			}
			// 会社追加・削除を有効
			setCmpUpLowButtonEnabled(true);

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

		// 上位、下位会社ボタンを押下不可にする
		setCmpButtonEnabled(false);
		setCmpUpLowButtonEnabled(false);
		// 組織名称、レベルテキストクリア
		mainView.ctrlOrganizationName.clear();
		mainView.ctrlCompany.lbl.setText(getWord("C00991"));
		mainView.ctrlCompany.lbl.setToolTipText(getWord("C00991"));

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
	 * 上位会社、下位会社の押下制御
	 * 
	 * @param bln enabled
	 */
	protected void setCmpButtonEnabled(boolean bln) {
		mainView.btnUpperCompany.setEnabled(bln);
		mainView.btnLowerCompany.setEnabled(bln);
	}

	/**
	 * 上位会社、下位会社の押下制御
	 * 
	 * @param bln enabled
	 */
	protected void setCmpUpLowButtonEnabled(boolean bln) {
		mainView.btnCompanyAdd.setEnabled(bln);
		mainView.btnCompanyCancellation.setEnabled(bln);

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
	 * 指示画面[上位会社]ボタン押下
	 */
	protected void btnUpperCompany_Click() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		try {
			String cmpCode = null;
			String cmpName = null;

			// 編集画面を生成
			createEditCmpView();

			int row[] = mainHieSpred.getSelectedRows();
			if (row.length == 0) {
				showMessage(editCmpView, "I00041", "C11979"); // 会社階層
				mainView.btnUpperCompany.requestFocus();
				return;

			}

			for (int i = 0; i < row.length; i++) {
				cmpCode = ((String) mainHieSpred.getRowValueAt(row[i], CS2.codeCmp));

				cmpName = cmpMap.get(cmpCode);
			}

			// 編集画面を初期化する
			initEditView(Mode.UPCMP, cmpCode, cmpName);

			// 編集画面を表示
			editCmpView.setLocationRelativeTo(null);
			editCmpView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 指示画面[下位会社]ボタン押下
	 */
	protected void btnLowerCompany_Click() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		try {

			String cmpCode = null;
			String cmpName = null;

			// 編集画面を生成
			createEditCmpView();

			int row[] = mainHieSpred.getSelectedRows();
			if (row.length == 0) {
				showMessage(editCmpView, "I00041", "C11979"); // 会社階層
				mainView.btnUpperCompany.requestFocus();
				return;

			}
			for (int i = 0; i < row.length; i++) {

				cmpCode = ((String) mainHieSpred.getRowValueAt(row[i], CS2.codeCmp));
				cmpName = cmpMap.get(cmpCode);
			}

			// 編集画面を初期化する
			initEditView(Mode.LOWCMP, cmpCode, cmpName);

			// 編集画面を表示
			editCmpView.setLocationRelativeTo(null);
			editCmpView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [会社追加]ボタン押下
	 */
	protected void btnCompanyAdd_Click() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		TTable mainCmpSpred = mainView.ssCompanyList;
		int selectedRows[] = mainCmpSpred.getSelectedRows();

		try {

			for (int i = 0; i < selectedRows.length; i++) {
				CompanyOrganization bean = new CompanyOrganization();

				String code = (String) mainCmpSpred.getRowValueAt(selectedRows[i], CS1.code);
				String name = (String) mainCmpSpred.getRowValueAt(selectedRows[i], CS1.name);

				bean.setCompanyCode(getCompanyCode());
				bean.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());
				bean.setLevel0(mainView.ctrlCompany.getCode());
				bean.setCmpCode(code);
				bean.setLevel(1);
				bean.setLevel1(code);
				bean.setLevel1Name(name);

				List inner = new ArrayList();
				// 会社コード
				inner.add(code);
				// レベル
				inner.add("1");
				// 会社名称レベル１
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
		mainCmpSpred.removeSelectedRows();
		setCmpButtonEnabled(true);
		// 1行目を選択する
		mainHieSpred.setRowSelection(0);

	}

	/**
	 * [会社削除]ボタン押下
	 */
	protected void btnCompanyCancellation_Click() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		TTable mainBmnSpred = mainView.ssCompanyList;

		int levelDel = 0;
		int selectedRows = mainHieSpred.getRowCount();
		String cmp = (String) mainHieSpred.getSelectedRowValueAt(CS2.codeCmp);
		if (Util.isNullOrEmpty(cmp)) {
			showMessage(mainView, "I00041", "C11979"); // 会社階層を選択してください。
			return;
		}

		int selectedRows2[] = mainHieSpred.getSelectedRows();
		int row = selectedRows2[0];
		int rowConst = row;

		List<CompanyOrganization> list = new ArrayList<CompanyOrganization>();
		CompanyOrganization bean = new CompanyOrganization();

		for (int i = 0; i < selectedRows; i++) {
			bean = (CompanyOrganization) mainHieSpred.getRowValueAt(i, CS2.bean2);
			list.add(bean);
		}

		levelDel = Integer.parseInt((mainHieSpred.getRowValueAt(row, CS2.level).toString()));

		while (levelDel == list.get(row).getLevel()) {
			String code = (String) mainHieSpred.getRowValueAt(rowConst, CS2.codeCmp);
			String name = cmpMap.get(code);
			// 左スプレッドへ追加
			mainBmnSpred.addRow(new Object[] { code, name });
			// 選択元から削除
			mainHieSpred.removeRow(rowConst);
			row++;
			if (mainHieSpred.getRowCount() == 0) {
				// 階層スプレッド0行だったら上位下位ボタン押下不可
				setCmpButtonEnabled(false);
			}
			if (row >= selectedRows) {
				break;
			}

			while (levelDel < list.get(row).getLevel()) {
				String code2 = (String) mainHieSpred.getRowValueAt(rowConst, CS2.codeCmp);
				String name2 = cmpMap.get(code2);

				// 左スプレッドへ追加
				mainBmnSpred.addRow(new Object[] { code2, name2 });
				// 選択元から削除
				mainHieSpred.removeRow(rowConst);
				row++;

				if (mainHieSpred.getRowCount() == 0) {
					// 階層スプレッド0行だったら上位下位ボタン押下不可
					setCmpButtonEnabled(false);
				}
				if (row >= selectedRows) {
					break;
				}

			}
			break;
		}

	}

	/**
	 * 会社マスタよりデータを取得し、一覧に設定する。
	 * 
	 * @param list 会社
	 */
	protected void initList(List<Company> list) {
		TTable mainBmnSpred = mainView.ssCompanyList;
		TTable mainHieSpred = mainView.ssHierarchyList;
		// 一覧をクリアする
		mainBmnSpred.removeRow();
		mainHieSpred.removeRow();

		// 会社階層マスタが存在しない場合
		if (list == null || list.isEmpty()) {
			return;
		}

		// 会社情報を一覧に表示する
		for (Company company : list) {
			mainBmnSpred.addRow(getCompanyDataRow(company));
		}

		// 1行目を選択する
		mainBmnSpred.setRowSelection(0);

	}

	/**
	 * 階層マスタよりデータを取得し、一覧に設定する。
	 * 
	 * @param list 会社階層
	 */
	protected void initListHierarchy(List<CompanyOrganization> list) {
		TTable mainHieSpred = mainView.ssHierarchyList;
		// 一覧をクリアする
		mainHieSpred.removeRow();

		// 会社階層マスタが存在しない場合
		if (list == null || list.isEmpty()) {
			return;
		}

		// 会社階層を一覧に表示する
		for (CompanyOrganization bean : list) {
			if (bean.getLevel() != 0) {// 会社レベル０以外はセットする
				mainHieSpred.addRow(getRowData(bean));
			}
		}
		// 1行目を選択する
		if (mainHieSpred.getRowCount() != 0) {
			mainHieSpred.setRowSelection(0);
		}
	}

	/**
	 * 会社階層を一覧に表示する形式に変換し返す
	 * 
	 * @param organization 会社階層
	 * @return 一覧に表示する形式に変換された会社階層
	 */
	protected List<Object> getRowData(CompanyOrganization organization) {
		List<Object> list = new ArrayList<Object>();
		switch (organization.getLevel()) {

			case 2:
				list.add(organization.getCmpCode()); // 会社コード
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
				list.add(organization.getCmpCode()); // 会社コード
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
				list.add(organization.getCmpCode()); // 会社コード
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
				list.add(organization.getCmpCode()); // 会社コード
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
				list.add(organization.getCmpCode()); // 会社コード
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
				list.add(organization.getCmpCode()); // 会社コード
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
				list.add(organization.getCmpCode()); // 会社コード
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
				list.add(organization.getCmpCode()); // 会社コード
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
				list.add(organization.getCmpCode()); // 会社コード
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
	 * 会社階層を一覧に表示する形式に変換し返す
	 * 
	 * @param organization 会社階層
	 * @return 一覧に表示する形式に変換された会社階層
	 */
	protected List<Object> getRowCodeData(CompanyOrganization organization) {
		List<Object> list = new ArrayList<Object>();

		list.add(organization.getCmpCode()); // 会社コード
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
	 * 会社の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected CompanySearchCondition getSearchCondition() {
		CompanySearchCondition condition = new CompanySearchCondition();
		return condition;
	}

	/**
	 * 会社階層の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected CompanyOrganizationSearchCondition getOrganizationSearchCondition() {
		CompanyOrganizationSearchCondition condition = new CompanyOrganizationSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());

		return condition;
	}

	/**
	 * 検索条件に該当する会社のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する会社のリスト
	 * @throws TException
	 */
	protected List<Company> getCompany(CompanySearchCondition condition) throws TException {
		return (List<Company>) request(getModelClass(), "get", condition);
	}

	/**
	 * モデルクラスを返す
	 * 
	 * @return モデルクラス
	 */
	protected Class getModelClass() {
		return CompanyOrganizationManager.class;
	}

	/**
	 * 会社情報を一覧に表示する形式に変換し返す
	 * 
	 * @param company 会社
	 * @return 一覧に表示する形式に変換された会社
	 */
	protected Object[] getCompanyDataRow(Company company) {
		return new Object[] { company.getCode(), company.getName(), company };
	}

	/**
	 * 会社階層の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected CompanyOrganizationSearchCondition getTreeSearchCondition() {

		CompanyOrganizationSearchCondition condition = new CompanyOrganizationSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());

		return condition;

	}

	/**
	 * 検索条件に該当する会社階層のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する会社階層のリスト
	 * @throws TException
	 */
	protected List<CompanyOrganization> getCompanyOrganization(CompanyOrganizationSearchCondition condition)
		throws TException {
		return (List<CompanyOrganization>) request(getModelClass(), "getCompanyOrganizationData", condition);
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

			CompanyOrganization bean = getSelectedCmpOrg();

			// 会社階層情報を取得
			request(getModelClass(), "deleteCompanyOrganization", bean);

			if (bean == null) {
				// 選択されたデータは他ユーザーにより削除されました
				throw new TException("I00193");
			}

			// コンボボックスリフレッシュ
			mainView.ctrlOrganizationCode.getController().initList();

			// レベル０初期化
			mainView.ctrlCompany.clear();

			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 会社階層を返す
	 * 
	 * @return 会社階層
	 * @throws Exception
	 */
	protected CompanyOrganization getSelectedCmpOrg() throws Exception {

		CompanyOrganizationSearchCondition condition = createCompanyOrganizationSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());
		List<CompanyOrganization> list = getCompanyOrganization(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * @return 会社階層情報の検索条件
	 */
	protected CompanyOrganizationSearchCondition createCompanyOrganizationSearchCondition() {
		return new CompanyOrganizationSearchCondition();
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
			CompanyOrganizationSearchCondition condition = getOrganizationSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getCompanyOrganizationNameExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02220") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 組織名称[確定]ボタン押下
	 */
	protected void btnSettleName_Click() {

		try {

			if (!isEditCmpViewInputName()) {
				return;
			}

			// 確認メッセージを表示する
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			CompanyOrganization list = getSelectedName();

			// 登録処理
			request(getModelClass(), "entryCompanyNameOrganization", list);

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

			if (!isEditCmpViewInput()) {
				return;
			}

			// 確認メッセージを表示する
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			List<CompanyOrganization> list = getSelectedCmpHie();

			// 登録処理
			request(getModelClass(), "entryCompanyOrganization", mainView.ctrlOrganizationCode.getComboBox()
				.getSelectedItem(), mainView.ctrlOrganizationName.getValue(), list);

			// 処理完了メッセージ
			showMessage(mainView, "I00008");

			// リフレッシュ
			changedOrganizationCode(ItemEvent.SELECTED, true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 一覧で選択されている会社を返す
	 * 
	 * @return 一覧で選択されている会社
	 */
	protected List<CompanyOrganization> getSelectedCmpHie() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		List<CompanyOrganization> list = new ArrayList<CompanyOrganization>();
		for (int i = 0; i < mainHieSpred.getRowCount(); i++) {
			list.add((CompanyOrganization) mainHieSpred.getRowValueAt(i, CS2.bean2));
		}

		return list;
	}

	/**
	 * 一覧で選択されている会社階層を返す
	 * 
	 * @param isConfim 会社設定確定押下時
	 * @return 一覧で選択されている会社階層
	 * @throws TException
	 */
	protected List<CompanyOrganization> getSelectedHierarchy(boolean isConfim) throws TException {

		CompanyOrganization bean = new CompanyOrganization();
		TTable mainHieSpred = mainView.ssHierarchyList;
		String lowCmp = editCmpView.ctrlCompanyReferenceLow.getCode();
		String upCmp = editCmpView.ctrlCompanyReferenceUP.getCode();
		List<CompanyOrganization> list = new ArrayList<CompanyOrganization>();
		Map<String, String> map = new HashMap<String, String>();

		if (isConfim) {
			for (int row = 0; row < mainHieSpred.getRowCount(); row++) {
				// 確定押下時のみ各会社のレベル取得
				bean = (CompanyOrganization) mainHieSpred.getRowValueAt(row, CS2.bean2);

				// 会社階層は９階層以上設定できません
				if (upCmp.equals(bean.getCmpCode()) && bean.getLevel() == 9) {
					throw new TException(getMessage("W00153"));
				}

				map.put(bean.getCmpCode() + SPC + "1", bean.getLevel1());
				map.put(bean.getCmpCode() + SPC + "2", bean.getLevel2());
				map.put(bean.getCmpCode() + SPC + "3", bean.getLevel3());
				map.put(bean.getCmpCode() + SPC + "4", bean.getLevel4());
				map.put(bean.getCmpCode() + SPC + "5", bean.getLevel5());
				map.put(bean.getCmpCode() + SPC + "6", bean.getLevel6());
				map.put(bean.getCmpCode() + SPC + "7", bean.getLevel7());
				map.put(bean.getCmpCode() + SPC + "8", bean.getLevel8());
				map.put(bean.getCmpCode() + SPC + "9", bean.getLevel9());

			}
		}

		for (int row = 0; row < mainHieSpred.getRowCount(); row++) {

			bean = (CompanyOrganization) mainHieSpred.getRowValueAt(row, CS2.bean2);

			String cmpCode = Util.avoidNull(mainHieSpred.getRowValueAt(row, CS2.codeCmp));
			bean.setCompanyCode(getCompanyCode());
			bean.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());
			bean.setLevel0((mainView.ctrlCompany.getCode()));
			bean.setCmpCode(cmpCode);
			bean.setLevel(valueInt(mainHieSpred.getRowValueAt(row, CS2.level)));

			if (cmpCode.equals(lowCmp)) {

				bean.setLevel1(map.get(upCmp + SPC + "1"));
				bean.setLevel2(map.get(upCmp + SPC + "2"));
				bean.setLevel3(map.get(upCmp + SPC + "3"));
				bean.setLevel4(map.get(upCmp + SPC + "4"));
				bean.setLevel5(map.get(upCmp + SPC + "5"));
				bean.setLevel6(map.get(upCmp + SPC + "6"));
				bean.setLevel7(map.get(upCmp + SPC + "7"));
				bean.setLevel8(map.get(upCmp + SPC + "8"));
				bean.setLevel9(map.get(upCmp + SPC + "9"));

			} else {

				if (bean.getLevel() == 1) {

					bean.setLevel1(cmpCode);

				} else if (bean.getLevel() == 2) {

					for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {
						CompanyOrganization bean2 = (CompanyOrganization) mainHieSpred.getRowValueAt(row2, CS2.bean2);

						if (bean.getLevel1() == null && bean2.getLevel() == 1) {
							bean.setLevel1(bean2.getCmpCode());
						}

					}
					bean.setLevel2(cmpCode);

				} else if (bean.getLevel() == 3) {

					for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {
						CompanyOrganization bean2 = (CompanyOrganization) mainHieSpred.getRowValueAt(row2, CS2.bean2);

						if (bean.getLevel1() == null && bean2.getLevel() == 1) {
							bean.setLevel1(bean2.getCmpCode());
						} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
							bean.setLevel2(bean2.getCmpCode());
						}
					}
					bean.setLevel3(cmpCode);

				} else if (bean.getLevel() == 4) {

					for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {
						CompanyOrganization bean2 = (CompanyOrganization) mainHieSpred.getRowValueAt(row, CS2.bean2);

						if (bean.getLevel1() == null && bean2.getLevel() == 1) {
							bean.setLevel1(bean2.getCmpCode());
						} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
							bean.setLevel2(bean2.getCmpCode());
						} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
							bean.setLevel3(bean2.getCmpCode());
						}
					}
					bean.setLevel4(cmpCode);

				} else if (bean.getLevel() == 5) {

					for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {

						CompanyOrganization bean2 = (CompanyOrganization) mainHieSpred.getRowValueAt(row, CS2.bean2);

						if (bean.getLevel1() == null && bean2.getLevel() == 1) {
							bean.setLevel1(bean2.getCmpCode());
						} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
							bean.setLevel2(bean2.getCmpCode());
						} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
							bean.setLevel3(bean2.getCmpCode());
						} else if (bean.getLevel4() == null && bean2.getLevel() == 4) {
							bean.setLevel4(bean2.getCmpCode());
						}
					}
					bean.setLevel5(cmpCode);

				} else if (bean.getLevel() == 6) {

					for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {
						CompanyOrganization bean2 = (CompanyOrganization) mainHieSpred.getRowValueAt(row, CS2.bean2);

						if (bean.getLevel1() == null && bean2.getLevel() == 1) {
							bean.setLevel1(bean2.getCmpCode());
						} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
							bean.setLevel2(bean2.getCmpCode());
						} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
							bean.setLevel3(bean2.getCmpCode());
						} else if (bean.getLevel4() == null && bean2.getLevel() == 4) {
							bean.setLevel4(bean2.getCmpCode());
						} else if (bean.getLevel5() == null && bean2.getLevel() == 5) {
							bean.setLevel5(bean2.getCmpCode());
						}
					}
					bean.setLevel6(cmpCode);

				} else if (bean.getLevel() == 7) {

					for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {
						CompanyOrganization bean2 = (CompanyOrganization) mainHieSpred.getRowValueAt(row, CS2.bean2);

						if (bean.getLevel1() == null && bean2.getLevel() == 1) {
							bean.setLevel1(bean2.getCmpCode());
						} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
							bean.setLevel2(bean2.getCmpCode());
						} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
							bean.setLevel3(bean2.getCmpCode());
						} else if (bean.getLevel4() == null && bean2.getLevel() == 4) {
							bean.setLevel4(bean2.getCmpCode());
						} else if (bean.getLevel5() == null && bean2.getLevel() == 5) {
							bean.setLevel5(bean2.getCmpCode());
						} else if (bean.getLevel6() == null && bean2.getLevel() == 6) {
							bean.setLevel6(bean2.getCmpCode());
						}
					}

					bean.setLevel7(cmpCode);

				} else if (bean.getLevel() == 8) {

					for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {
						CompanyOrganization bean2 = (CompanyOrganization) mainHieSpred.getRowValueAt(row, CS2.bean2);

						if (bean.getLevel1() == null && bean2.getLevel() == 1) {
							bean.setLevel1(bean2.getCmpCode());
						} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
							bean.setLevel2(bean2.getCmpCode());
						} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
							bean.setLevel3(bean2.getCmpCode());
						} else if (bean.getLevel4() == null && bean2.getLevel() == 4) {
							bean.setLevel4(bean2.getCmpCode());
						} else if (bean.getLevel5() == null && bean2.getLevel() == 5) {
							bean.setLevel5(bean2.getCmpCode());
						} else if (bean.getLevel6() == null && bean2.getLevel() == 6) {
							bean.setLevel6(bean2.getCmpCode());
						} else if (bean.getLevel7() == null && bean2.getLevel() == 7) {
							bean.setLevel7(bean2.getCmpCode());
						}
					}

					bean.setLevel8(cmpCode);

				} else if (bean.getLevel() == 9) {

					for (int row2 = 0; row2 < mainHieSpred.getRowCount(); row2++) {
						CompanyOrganization bean2 = (CompanyOrganization) mainHieSpred.getRowValueAt(row, CS2.bean2);

						if (bean.getLevel1() == null && bean2.getLevel() == 1) {
							bean.setLevel1(bean2.getCmpCode());
						} else if (bean.getLevel2() == null && bean2.getLevel() == 2) {
							bean.setLevel2(bean2.getCmpCode());
						} else if (bean.getLevel3() == null && bean2.getLevel() == 3) {
							bean.setLevel3(bean2.getCmpCode());
						} else if (bean.getLevel4() == null && bean2.getLevel() == 4) {
							bean.setLevel4(bean2.getCmpCode());
						} else if (bean.getLevel5() == null && bean2.getLevel() == 5) {
							bean.setLevel5(bean2.getCmpCode());
						} else if (bean.getLevel6() == null && bean2.getLevel() == 6) {
							bean.setLevel6(bean2.getCmpCode());
						} else if (bean.getLevel7() == null && bean2.getLevel() == 7) {
							bean.setLevel7(bean2.getCmpCode());
						} else if (bean.getLevel8() == null && bean2.getLevel() == 8) {
							bean.setLevel8(bean2.getCmpCode());
						}
					}
					bean.setLevel9(cmpCode);
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
	protected CompanyOrganization getSelectedName() {

		CompanyOrganization bean = new CompanyOrganization();
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
	 * 一覧で選択されている会社階層を返す
	 * 
	 * @return 一覧で選択されている会社階層
	 * @param obj スプレッドの値
	 */
	protected static String valueString(Object obj) {
		return (obj == null) ? "" : obj.toString();
	}

	/**
	 * 一覧で選択されている会社階層を返す
	 * 
	 * @return 一覧で選択されている会社階層
	 * @param obj スプレッドの値
	 */
	protected static int valueInt(Object obj) {
		String objStr = obj.toString();
		int num3 = new Integer(objStr).intValue();
		return num3;

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new OW0140CompanyHierarchicalDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent(0);

	}

	/**
	 * 組織名称編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditNameView() {

		// 編集画面を生成
		editNameView = new OW0140CompanyHierarchicalNameDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent(2);

	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 * 
	 * @throws TException
	 */
	protected void createEditCmpView() throws TException {
		TTable mainHieSpred = mainView.ssHierarchyList;
		// 編集画面を生成
		editCmpView = new OW0140CompanyHierarchicalComDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent(1);

		// 検索処理に制限をかける
		List<CompanyOrganization> list = getSelectedHierarchy(false);
		Set<CompanyOrganization> set = new HashSet<CompanyOrganization>(list);
		Set<String> setString = new HashSet<String>();
		for (CompanyOrganization listCode : set) {
			if (!(listCode.getCmpCode().equals(mainHieSpred.getSelectedRowValueAt(CS2.codeCmp)))) {
				setString.add(listCode.getCmpCode());
			}
		}
		editCmpView.ctrlCompanyReferenceUP.getSearchCondition().setCodeList(setString);
		editCmpView.ctrlCompanyReferenceLow.getSearchCondition().setCodeList(setString);
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
				editCmpView.btnSettle.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						editCmpView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						btnEditCmpViewSettle_Click();
						editCmpView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				});

				// [戻る]ボタン押下
				editCmpView.btnClose.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						editCmpView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						btnEditCmpViewClose_Click();
						editCmpView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				});
				break;

			// 組織名称
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
	 * @param cmpCode
	 * @param cmpName
	 */
	protected void initEditView(Mode mode_, String cmpCode, String cmpName) {

		this.mode = mode_;
		switch (mode) {
			case NEW:
				editView.setTitle(getWord("C01698"));
				break;
			case UPCMP:
				editCmpView.setTitle(getWord("C02395"));
				editCmpView.ctrlCompanyReferenceLow.setCode(cmpCode);
				editCmpView.ctrlCompanyReferenceLow.setNames(cmpName);
				editCmpView.ctrlCompanyReferenceLow.btn.setEnabled(false);
				editCmpView.ctrlCompanyReferenceLow.code.setEnabled(false);

				break;
			case LOWCMP:
				editCmpView.setTitle(getWord("C02396"));
				editCmpView.ctrlCompanyReferenceUP.setCode(cmpCode);
				editCmpView.ctrlCompanyReferenceUP.setNames(cmpName);
				editCmpView.ctrlCompanyReferenceUP.btn.setEnabled(false);
				editCmpView.ctrlCompanyReferenceUP.code.setEnabled(false);
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
			CompanyOrganization companyOrganization = getInputedOrg();

			if (Mode.NEW == mode) {
				// 新規登録
				request(getModelClass(), "entryCompanyOrganization", companyOrganization);
			}

			// 編集画面を閉じる
			btnEditViewClose_Click();

			// 追加分をコンボボックスに反映する
			mainView.ctrlOrganizationCode.getController().initList();

			mainView.ctrlOrganizationCode.setSelectedItemValue(companyOrganization.getCode());
			mainView.ctrlCompany.setCode(companyOrganization.getCmpCode());
			mainView.ctrlCompany.setNames(companyOrganization.getCmpNames());

			// 組織検索条件の定義
			CompanyOrganizationSearchCondition orgCondition = getTreeSearchCondition();

			// DBから組織情報を取得する
			List<CompanyOrganization> comOrgList = getCompanyOrganization(orgCondition);

			// 会社検索条件の定義
			CompanySearchCondition cmpCondition = getSearchCondition();

			// DBから会社情報を取得する
			List<Company> cmpList = getCompany(cmpCondition);

			// フィルター
			if (cmpList != null) {
				for (CompanyOrganization org : comOrgList) {
					String companyCode = org.getCmpCode();

					for (int i = cmpList.size() - 1; i >= 0; i--) {
						if (companyCode.equals(cmpList.get(i).getCode())) {
							cmpList.remove(i);
							break;
						}
					}
				}
			}

			initList(cmpList);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 会社コード順にList内をsort
	 */
	public class sortList implements Comparator<CompanyOrganization> {

		@Override
		public int compare(CompanyOrganization detail1, CompanyOrganization detail2) {

			String cmpCode1 = detail1.getCmpCode();
			String cmpCode2 = detail2.getCmpCode();

			return cmpCode1.compareTo(cmpCode2);
		}
	}

	/**
	 * 編集画面で入力された組織コードを返す
	 * 
	 * @return 編集画面で入力された組織コード情報
	 */
	protected CompanyOrganization getInputedOrg() {

		// 組織コード情報
		CompanyOrganization org = new CompanyOrganization();
		org.setCode(editView.ctrlOrganizationCode.getValue());
		org.setName(editView.ctrlOrganizationName.getValue());
		org.setCmpCode(editView.ctrlCompanyReference.getCode());
		org.setCmpNames(editView.ctrlCompanyReference.getNames());
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
	 * 会社階層設定画面[戻る]ボタン押下
	 */
	protected void btnEditCmpViewClose_Click() {
		editCmpView.setVisible(false);
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
		if (Util.isNullOrEmpty(editView.ctrlCompanyReference.getCode())) {
			showMessage(editView, "I00037", "C00991"); // レベル０
			editView.ctrlCompanyReference.btn.requestFocus();
			return false;
		}

		// 組織コードが重複していたらエラー
		CompanyOrganizationSearchCondition condition = new CompanyOrganizationSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(editView.ctrlOrganizationCode.getValue());

		List<CompanyOrganization> list = getCompanyOrganization(condition);

		if (list != null && !list.isEmpty()) {
			showMessage(editView, "I00084", "C00335");
			editView.ctrlOrganizationCode.requestTextFocus();
			return false;
		}
		return true;
	}

	/**
	 * 会社設定画面で入力した値が妥当かをチェックする
	 * 
	 * @return 会社設定画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 */
	protected boolean isEditCmpViewInputRight() {
		// 上位会社が未注力の場合エラー
		if (Util.isNullOrEmpty(editCmpView.ctrlCompanyReferenceUP.getCode())) {
			showMessage(editCmpView, "I00037", "C01909"); // 上位会社コード
			editCmpView.ctrlCompanyReferenceUP.requestFocus();
			return false;
		}

		// 下位会社が未注力の場合エラー
		if (Util.isNullOrEmpty(editCmpView.ctrlCompanyReferenceLow.getCode())) {
			showMessage(editCmpView, "I00037", getWord("C00044") + getWord("C00698")); // 下位会社コード
			editCmpView.ctrlCompanyReferenceLow.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * 組織名称設定画面で入力した値が妥当かをチェックする
	 * 
	 * @return 組織名称設定画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 */
	protected boolean isEditCmpViewInput() {
		// 組織名称ブランクでエラー
		if (Util.isNullOrEmpty(mainView.ctrlOrganizationName.getValue())) {
			showMessage(editCmpView, "I00204", "C11967"); // 組織名称を設定してください。
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
	protected boolean isEditCmpViewInputName() {
		// 組織名称ブランクでエラー
		if (Util.isNullOrEmpty(editNameView.ctrlOrganizationName.getValue())) {
			showMessage(editCmpView, "I00037", "C11967"); // 組織名称
			editNameView.ctrlOrganizationName.requestFocus();
			return false;
		}
		return true;
	}

}
