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
 * ����K�w�}�X�^�̃R���g���[��
 * 
 * @author AIS
 */
public class MG0051DepartmentHierarchySelectionMasterPanelCtrl extends TController {

	/** ���샂�[�h */
	public enum Mode {
		/** �V�K */
		NEW,
		/** �폜 */
		DELETE,
		/** �G�N�Z�� */
		EXCEL,
		/** �m�� */
		SETTLE,
		/** ��ʕ��� */
		UPDEP,
		/** ���ʕ��� */
		LOWDEP
	}

	/** �w����� */
	protected MG0051DepartmentHierarchySelectionMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0051DepartmentHierarchySelectionDialog editView = null;

	/** ���匟����� */
	protected MG0051DepartmentHierarchySelectionDepDialog editDepView = null;

	/** ���̕ҏW��� */
	protected MG0051DepartmentHierarchySelectionNameDialog editNameView = null;

	/** ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B */
	protected Mode mode = null;

	/** ����̃}�b�v **/
	protected HashMap<String, String> depMap;

	/** orgList **/
	protected List<DepartmentOrganization> orgList = new ArrayList<DepartmentOrganization>();

	/** key�X�y�[�X **/
	protected final String SPC = "<>";

	/**
	 * �������J�n����
	 */
	@Override
	public void start() {

		try {

			// �w����ʐ���
			createMainView();

			// �w����ʂ�����������
			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �p�l���̎擾
	 */
	@Override
	public MG0051DepartmentHierarchySelectionMasterPanel getPanel() {
		return mainView;
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0051DepartmentHierarchySelectionMasterPanel(this);
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		// [�V�K]�{�^������
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�폜]�{�^������
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�G�N�Z��]�{�^������
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�m��]�{�^������
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// �g�D�R�[�h�ύX����
		mainView.ctrlOrganizationCode.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent evt) {
				changedOrganizationCode(evt.getStateChange(), true);
			}
		});
		// [��ʕ���]�{�^������
		mainView.btnUpperDepartment.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnUpperDepartment_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [���ʕ���]�{�^������
		mainView.btnLowerDepartment.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnLowerDepartment_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [����ǉ�]�{�^������
		mainView.btnDepartmentAdd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDepartmentAdd_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [����폜]�{�^������
		mainView.btnDepartmentCancellation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDepartmentCancellation_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�g�D����]�{�^������
		mainView.btnOrganizationName.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnOrganizationName_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * [�m��]�{�^������
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

			// �m�F���b�Z�[�W��\������
			if (!showConfirmMessage(editDepView, "Q00004")) {
				return;
			}

			List<DepartmentOrganization> list = getSelectedHierarchy(true);
			List<DepartmentOrganization> listDep = new ArrayList<DepartmentOrganization>();

			// �K�w�ҏW��ʒl���擾
			String upDep = editDepView.ctrlDepartmentReferenceUP.getCode();
			String lowDep = editDepView.ctrlDepartmentReferenceLow.getCode();

			// ��ʒl�̓��̓`�F�b�N
			// ���̓`�F�b�N���s���B
			if (!isEditDepViewInputRight()) {
				return;
			}

			// ��ʕ���̊K�w���x��
			int upLevel = -1;

			// ��ʕ���̃��x�����擾
			for (DepartmentOrganization bean : list) {
				String condiDepCode = bean.getDepCode();
				if (condiDepCode.equals(upDep)) {
					upLevel = bean.getLevel();
					break;
				}
			}

			// ���x���ݒ�
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

			// �����������b�Z�[�W
			showMessage(editDepView, "I00008");

			// ��ʂ����
			btnEditDepViewClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �R�[�h�Ɩ��̂�ϊ�
	 * 
	 * @param list ����K�w
	 * @return list ����K�w
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
	 * �K�w�}�X�^���f�[�^���擾���A�ꗗ�ɐݒ肷��B
	 * 
	 * @param list ����K�w
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

		// �ꗗ���N���A����
		mainHieSpred.removeRow();

		// list�̒��𕔖�R�[�h���ɕ��בւ�
		Collections.sort(list, new sortList());

		// ���x����List�쐬
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

		// sort���ɃX�v���b�h���בւ�
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

		// ��ʂ����ʂɂȂ����ꍇ�A�������Ă������ʂ����X�v���b�h�ֈړ��B
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

		// 1�s�ڂ�I������
		if (mainHieSpred.getRowCount() != 0) {
			mainHieSpred.setRowSelection(0);
		}
	}

	/**
	 * �g�D�R�[�h�ύX����
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

				// ���x���O������
				mainView.ctrlDepartment.clear();

				initMainView();

				return;
			}

			if (ItemEvent.SELECTED != state) {
				return;
			}

			// �g�D���������̒�`
			DepartmentOrganizationSearchCondition orgCondition = getTreeSearchCondition();

			// DB����g�D�����擾����
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
			// ���匟�������̒�`
			DepartmentSearchCondition depCondition = getSearchCondition();

			// DB���畔������擾����
			depList = getDepartment(depCondition);

			// ��������}�b�v�փZ�b�g����
			depMap = new HashMap<String, String>();

			for (Department dep : depList) {
				// ����R�[�h
				String code = valueString(dep.getCode());
				// ���喼��
				String text = valueString(dep.getName());
				depMap.put(code, text);
			}

			// ���x���O������
			mainView.ctrlDepartment.clear();

			// ���x���O
			for (DepartmentOrganization org : orgList) {
				if (org.getLevel() == 0) {
					String departmentCode = org.getDepCode();
					mainView.ctrlDepartment.setCode(departmentCode);
					mainView.ctrlDepartment.setNames(org.getDepNames());
					break;
				}
			}

			// �t�B���^�[
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
				// ����ꗗ�֔��f
				initList(depList);
				initListHierarchy(orgList);

				setMainButtonEnabled(!Util.isNullOrEmpty(mainView.ctrlOrganizationCode.getSelectedItemValue()));
			}

			// ����ǉ��E�폜��L��
			setDepUpLowButtonEnabled(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �w����ʂ�����������
	 */
	protected void initMainView() {

		// ���C���{�^���������s�ɂ���
		setMainButtonEnabled(false);

		// ��ʁA���ʕ���{�^���������s�ɂ���
		setDepButtonEnabled(false);
		setDepUpLowButtonEnabled(false);
		// �g�D���́A���x���e�L�X�g�N���A
		mainView.ctrlOrganizationName.clear();
		mainView.ctrlDepartment.lbl.setText(getWord("C00991"));
		mainView.ctrlDepartment.lbl.setToolTipText(getWord("C00991"));

		// �ꗗ�ݒ菈��
		initList(null);

	}

	/**
	 * ���C���{�^���̉�������
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
	 * ��ʕ���A���ʕ���̉�������
	 * 
	 * @param bln enabled
	 */
	protected void setDepButtonEnabled(boolean bln) {
		mainView.btnUpperDepartment.setEnabled(bln);
		mainView.btnLowerDepartment.setEnabled(bln);
	}

	/**
	 * ��ʕ���A���ʕ���̉�������
	 * 
	 * @param bln enabled
	 */
	protected void setDepUpLowButtonEnabled(boolean bln) {
		mainView.btnDepartmentAdd.setEnabled(bln);
		mainView.btnDepartmentCancellation.setEnabled(bln);

	}

	/**
	 * �w�����[�V�K]�{�^������
	 */
	protected void btnNew_Click() {

		try {

			// �ҏW��ʂ𐶐�
			createEditView();

			// �ҏW��ʂ�����������
			initEditView(Mode.NEW, null, null);

			// �ҏW��ʂ�\��
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �w�����[�g�D���̐ݒ�]�{�^������
	 */
	protected void btnOrganizationName_Click() {

		try {

			// �ҏW��ʂ𐶐�
			createEditNameView();
			// �ҏW��ʂ�����������
			initEditNameView();

			// �ҏW��ʂ�\��
			editNameView.setLocationRelativeTo(null);
			editNameView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �I���s�̃`�F�b�N
	 * 
	 * @return true:�G���[�Ȃ�
	 */
	protected boolean checkMainView() {

		if (mainView.ssHierarchyList.getSelectedRow() == -1) {
			// �ꗗ����f�[�^��I�����Ă��������B
			showMessage("I00043");
			return false;
		}

		return true;
	}

	/**
	 * �w�����[��ʕ���]�{�^������
	 */
	protected void btnUpperDepartment_Click() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		try {

			String depCode = null;
			String depName = null;

			// �ҏW��ʂ𐶐�
			createEditDepView();

			int row[] = mainHieSpred.getSelectedRows();
			if (row.length == 0) {
				showMessage(editDepView, "I00041", "C02473"); // ����K�w
				mainView.btnUpperDepartment.requestFocus();
				return;

			}

			for (int i = 0; i < row.length; i++) {
				depCode = ((String) mainHieSpred.getRowValueAt(row[i], DS2.codeDep));

				depName = depMap.get(depCode);
			}

			// �ҏW��ʂ�����������
			initEditView(Mode.UPDEP, depCode, depName);

			// �ҏW��ʂ�\��
			editDepView.setLocationRelativeTo(null);
			editDepView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �w�����[���ʕ���]�{�^������
	 */
	protected void btnLowerDepartment_Click() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		try {

			String depCode = null;
			String depName = null;

			// �ҏW��ʂ𐶐�
			createEditDepView();

			int row[] = mainHieSpred.getSelectedRows();
			if (row.length == 0) {
				showMessage(editDepView, "I00041", "C02473"); // ����K�w
				mainView.btnUpperDepartment.requestFocus();
				return;

			}
			for (int i = 0; i < row.length; i++) {

				depCode = ((String) mainHieSpred.getRowValueAt(row[i], DS2.codeDep));
				depName = depMap.get(depCode);
			}

			// �ҏW��ʂ�����������
			initEditView(Mode.LOWDEP, depCode, depName);

			// �ҏW��ʂ�\��
			editDepView.setLocationRelativeTo(null);
			editDepView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [����ǉ�]�{�^������
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
				// ����R�[�h
				inner.add(code);
				// ���x��
				inner.add("1");
				// ���喼�̃��x���P
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

		// �I��������폜
		mainBmnSpred.removeSelectedRows();
		setDepButtonEnabled(true);

	}

	/**
	 * [����폜]�{�^������
	 */
	protected void btnDepartmentCancellation_Click() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		TTable mainBmnSpred = mainView.ssDepartmentList;

		int levelDel = 0;
		int selectedRows = mainHieSpred.getRowCount();
		String dep = (String) mainHieSpred.getSelectedRowValueAt(DS2.codeDep);
		if (Util.isNullOrEmpty(dep)) {
			showMessage(mainView, "I00041", "C02473"); // ����K�w��I�����Ă��������B
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
			// ���X�v���b�h�֒ǉ�
			mainBmnSpred.addRow(new Object[] { code, depName });
			// �I��������폜
			mainHieSpred.removeRow(rowConst);
			row++;
			if (mainHieSpred.getRowCount() == 0) {
				// �K�w�X�v���b�h0�s���������ʉ��ʃ{�^�������s��
				setDepButtonEnabled(false);
			}
			if (row >= selectedRows) {
				break;
			}

			while (levelDel < list.get(row).getLevel()) {
				String code2 = (String) mainHieSpred.getRowValueAt(rowConst, DS2.codeDep);
				String depName2 = depMap.get(code2);

				// ���X�v���b�h�֒ǉ�
				mainBmnSpred.addRow(new Object[] { code2, depName2 });
				// �I��������폜
				mainHieSpred.removeRow(rowConst);
				row++;
				if (mainHieSpred.getRowCount() == 0) {
					// �K�w�X�v���b�h0�s���������ʉ��ʃ{�^�������s��
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
	 * ����}�X�^���f�[�^���擾���A�ꗗ�ɐݒ肷��B
	 * 
	 * @param list ����
	 */
	protected void initList(List<Department> list) {
		TTable mainBmnSpred = mainView.ssDepartmentList;
		TTable mainHieSpred = mainView.ssHierarchyList;
		// �ꗗ���N���A����
		mainBmnSpred.removeRow();
		mainHieSpred.removeRow();

		// ����K�w�}�X�^�����݂��Ȃ��ꍇ
		if (list == null || list.isEmpty()) {
			return;
		}

		// ��������ꗗ�ɕ\������
		for (Department department : list) {
			mainBmnSpred.addRow(getDepartmentDataRow(department));
		}

		// 1�s�ڂ�I������
		mainBmnSpred.setRowSelection(0);

	}

	/**
	 * �K�w�}�X�^���f�[�^���擾���A�ꗗ�ɐݒ肷��B
	 * 
	 * @param list ����K�w
	 */
	protected void initListHierarchy(List<DepartmentOrganization> list) {
		TTable mainHieSpred = mainView.ssHierarchyList;
		// �ꗗ���N���A����
		mainHieSpred.removeRow();

		// ����K�w�}�X�^�����݂��Ȃ��ꍇ
		if (list == null || list.isEmpty()) {
			return;
		}
		// ����K�w���ꗗ�ɕ\������
		for (DepartmentOrganization bean : list) {
			if (bean.getLevel() != 0) {// ���僌�x���O�ȊO�̓Z�b�g����
				mainHieSpred.addRow(getRowData(bean));
			}
		}
		// 1�s�ڂ�I������
		if (mainHieSpred.getRowCount() != 0) {
			mainHieSpred.setRowSelection(0);
		}
	}

	/**
	 * ����K�w���ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param organization ����K�w
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ����K�w
	 */
	protected List<Object> getRowData(DepartmentOrganization organization) {
		List<Object> list = new ArrayList<Object>();
		switch (organization.getLevel()) {

			case 2:
				list.add(organization.getDepCode()); // ����R�[�h
				list.add(organization.getLevel()); // ���x��
				list.add(""); // ���x���P
				list.add(organization.getLevel2Name()); // ���x���Q
				list.add(organization.getLevel3Name()); // ���x���R
				list.add(organization.getLevel4Name()); // ���x���S
				list.add(organization.getLevel5Name()); // ���x���T
				list.add(organization.getLevel6Name()); // ���x���U
				list.add(organization.getLevel7Name()); // ���x���V
				list.add(organization.getLevel8Name()); // ���x���W
				list.add(organization.getLevel9Name()); // ���x���X
				list.add(organization);
				break;
			case 3:
				list.add(organization.getDepCode()); // ����R�[�h
				list.add(organization.getLevel()); // ���x��
				list.add(""); // ���x���P
				list.add(""); // ���x���Q
				list.add(organization.getLevel3Name()); // ���x���R
				list.add(organization.getLevel4Name()); // ���x���S
				list.add(organization.getLevel5Name()); // ���x���T
				list.add(organization.getLevel6Name()); // ���x���U
				list.add(organization.getLevel7Name()); // ���x���V
				list.add(organization.getLevel8Name()); // ���x���W
				list.add(organization.getLevel9Name()); // ���x���X
				list.add(organization);
				break;
			case 4:
				list.add(organization.getDepCode()); // ����R�[�h
				list.add(organization.getLevel()); // ���x��
				list.add(""); // ���x���P
				list.add(""); // ���x���Q
				list.add(""); // ���x���R
				list.add(organization.getLevel4Name()); // ���x���S
				list.add(organization.getLevel5Name()); // ���x���T
				list.add(organization.getLevel6Name()); // ���x���U
				list.add(organization.getLevel7Name()); // ���x���V
				list.add(organization.getLevel8Name()); // ���x���W
				list.add(organization.getLevel9Name()); // ���x���X
				list.add(organization);
				break;
			case 5:
				list.add(organization.getDepCode()); // ����R�[�h
				list.add(organization.getLevel()); // ���x��
				list.add(""); // ���x���P
				list.add(""); // ���x���Q
				list.add(""); // ���x���R
				list.add(""); // ���x���S
				list.add(organization.getLevel5Name()); // ���x���T
				list.add(organization.getLevel6Name()); // ���x���U
				list.add(organization.getLevel7Name()); // ���x���V
				list.add(organization.getLevel8Name()); // ���x���W
				list.add(organization.getLevel9Name()); // ���x���X
				list.add(organization);
				break;
			case 6:
				list.add(organization.getDepCode()); // ����R�[�h
				list.add(organization.getLevel()); // ���x��
				list.add(""); // ���x���P
				list.add(""); // ���x���Q
				list.add(""); // ���x���R
				list.add(""); // ���x���S
				list.add(""); // ���x���T
				list.add(organization.getLevel6Name()); // ���x���U
				list.add(organization.getLevel7Name()); // ���x���V
				list.add(organization.getLevel8Name()); // ���x���W
				list.add(organization.getLevel9Name()); // ���x���X
				list.add(organization);
				break;
			case 7:
				list.add(organization.getDepCode()); // ����R�[�h
				list.add(organization.getLevel()); // ���x��
				list.add(""); // ���x���P
				list.add(""); // ���x���Q
				list.add(""); // ���x���R
				list.add(""); // ���x���S
				list.add(""); // ���x���T
				list.add(""); // ���x���U
				list.add(organization.getLevel7Name()); // ���x���V
				list.add(organization.getLevel8Name()); // ���x���W
				list.add(organization.getLevel9Name()); // ���x���X
				list.add(organization);
				break;
			case 8:
				list.add(organization.getDepCode()); // ����R�[�h
				list.add(organization.getLevel()); // ���x��
				list.add(""); // ���x���P
				list.add(""); // ���x���Q
				list.add(""); // ���x���R
				list.add(""); // ���x���S
				list.add(""); // ���x���T
				list.add(""); // ���x���U
				list.add(""); // ���x���V
				list.add(organization.getLevel8Name()); // ���x���W
				list.add(organization.getLevel9Name()); // ���x���X
				list.add(organization);
				break;
			case 9:
				list.add(organization.getDepCode()); // ����R�[�h
				list.add(organization.getLevel()); // ���x��
				list.add(""); // ���x���P
				list.add(""); // ���x���Q
				list.add(""); // ���x���R
				list.add(""); // ���x���S
				list.add(""); // ���x���T
				list.add(""); // ���x���U
				list.add(""); // ���x���V
				list.add(""); // ���x���W
				list.add(organization.getLevel9Name()); // ���x���X
				list.add(organization);
				break;
			default:
				list.add(organization.getDepCode()); // ����R�[�h
				list.add(organization.getLevel()); // ���x��
				list.add(organization.getLevel1Name()); // ���x���P
				list.add(organization.getLevel2Name()); // ���x���Q
				list.add(organization.getLevel3Name()); // ���x���R
				list.add(organization.getLevel4Name()); // ���x���S
				list.add(organization.getLevel5Name()); // ���x���T
				list.add(organization.getLevel6Name()); // ���x���U
				list.add(organization.getLevel7Name()); // ���x���V
				list.add(organization.getLevel8Name()); // ���x���W
				list.add(organization.getLevel9Name()); // ���x���X
				list.add(organization);

				break;

		}

		return list;
	}

	/**
	 * ����K�w���ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param organization ����K�w
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ����K�w
	 */
	protected List<Object> getRowCodeData(DepartmentOrganization organization) {
		List<Object> list = new ArrayList<Object>();

		list.add(organization.getDepCode()); // ����R�[�h
		list.add(organization.getLevel()); // ���x��
		list.add(organization.getLevel1Name()); // ���x���P
		list.add(organization.getLevel2Name()); // ���x���Q
		list.add(organization.getLevel3Name()); // ���x���R
		list.add(organization.getLevel4Name()); // ���x���S
		list.add(organization.getLevel5Name()); // ���x���T
		list.add(organization.getLevel6Name()); // ���x���U
		list.add(organization.getLevel7Name()); // ���x���V
		list.add(organization.getLevel8Name()); // ���x���W
		list.add(organization.getLevel9Name()); // ���x���X
		list.add(organization);

		return list;
	}

	/**
	 * ����̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected DepartmentSearchCondition getSearchCondition() {
		DepartmentSearchCondition condition = new DepartmentSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setSumDepartment(true);
		return condition;
	}

	/**
	 * ����K�w�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected DepartmentOrganizationSearchCondition getOrganizationSearchCondition() {
		DepartmentOrganizationSearchCondition condition = new DepartmentOrganizationSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());

		return condition;
	}

	/**
	 * ���������ɊY�����镔��̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�����镔��̃��X�g
	 * @throws TException
	 */
	protected List<Department> getDepartment(DepartmentSearchCondition condition) throws TException {
		return (List<Department>) request(getModelClass(), "get", condition);
	}

	/**
	 * ���f���N���X��Ԃ�
	 * 
	 * @return ���f���N���X
	 */
	protected Class getModelClass() {
		return DepartmentOrganizationManager.class;
	}

	/**
	 * ��������ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param department ����
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ����
	 */
	protected Object[] getDepartmentDataRow(Department department) {
		return new Object[] { department.getCode(), department.getName(), department };
	}

	/**
	 * ����K�w�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected DepartmentOrganizationSearchCondition getTreeSearchCondition() {

		DepartmentOrganizationSearchCondition condition = new DepartmentOrganizationSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());

		return condition;

	}

	/**
	 * ���������ɊY�����镔��K�w�̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�����镔��K�w�̃��X�g
	 * @throws TException
	 */
	protected List<DepartmentOrganization> getDepartmentOrganization(DepartmentOrganizationSearchCondition condition)
		throws TException {
		return (List<DepartmentOrganization>) request(getModelClass(), "getDepartmentOrganizationData", condition);
	}

	/**
	 * [�폜]�{�^������
	 */
	protected void btnDelete_Click() {
		try {
			// �m�F���b�Z�[�W
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			DepartmentOrganization bean = getSelectedDepOrg();

			// ����K�w�����擾
			request(getModelClass(), "deleteDepartmentOrganization", bean);

			if (bean == null) {
				// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂���
				throw new TException("I00193");
			}

			// �R���{�{�b�N�X���t���b�V��
			mainView.ctrlOrganizationCode.getController().initList();

			// ���x���O������
			mainView.ctrlDepartment.clear();

			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ����K�w��Ԃ�
	 * 
	 * @return ����K�w
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
	 * @return ����K�w���̌�������
	 */
	protected DepartmentOrganizationSearchCondition createDepartmentOrganizationSearchCondition() {
		return new DepartmentOrganizationSearchCondition();
	}

	/**
	 * �w�����[�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {
		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// �f�[�^���o
			DepartmentOrganizationSearchCondition condition = getOrganizationSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getDepartmentOrganizationNameExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02327") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �g�D����[�m��]�{�^������
	 */
	protected void btnSettleName_Click() {

		try {

			if (!isEditDepViewInputName()) {
				return;
			}

			// �m�F���b�Z�[�W��\������
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			DepartmentOrganization list = getSelectedName();

			// �o�^����
			request(getModelClass(), "entryDepartmentNameOrganization", list);

			// �����������b�Z�[�W
			showMessage(mainView, "I00008");

			// ���t���b�V��
			changedOrganizationCode(ItemEvent.SELECTED, false);

			// �E�C���h�E����
			btnEditNameViewClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			if (!isEditDepViewInput()) {
				return;
			}

			// �m�F���b�Z�[�W��\������
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			// List<DepartmentOrganization> list = getSelectedHierarchy();
			List<DepartmentOrganization> list = getSelectedDepHie();

			// �o�^����
			request(getModelClass(), "entryDepartmentOrganization",
				mainView.ctrlOrganizationCode.getSelectedOrganizationCode(), mainView.ctrlOrganizationName.getValue(),
				list);

			// �����������b�Z�[�W
			showMessage(mainView, "I00008");

			// ���t���b�V��
			changedOrganizationCode(ItemEvent.SELECTED, true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ꗗ�őI������Ă��镔���Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă��镔��
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
	 * �ꗗ�őI������Ă��镔��K�w��Ԃ�
	 * 
	 * @param isConfim ����ݒ�m�艟����
	 * @return �ꗗ�őI������Ă��镔��K�w
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

				// �m�艟�����̂݊e����̃��x���擾
				bean = (DepartmentOrganization) mainHieSpred.getRowValueAt(row, DS2.bean2);

				// ����K�w�͂X�K�w�ȏ�ݒ�ł��܂���
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
				// ���ʕ���ɏ�ʕ���̃��x���ݒ�
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
	 * ���̒ǉ���ʂ̓��͒l�擾
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
	 * �ꗗ�őI������Ă��镔��K�w��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă��镔��K�w
	 * @param obj �X�v���b�h�̒l
	 */
	protected static String valueString(Object obj) {
		return (obj == null) ? "" : obj.toString();
	}

	/**
	 * �ꗗ�őI������Ă��镔��K�w��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă��镔��K�w
	 * @param obj �X�v���b�h�̒l
	 */
	protected static int valueInt(Object obj) {
		String objStr = obj.toString();
		int num3 = new Integer(objStr).intValue();
		return num3;

	}

	/**
	 * @param node
	 * @return ����R�[�h
	 */
	protected String getNodeDepartmentCode(TreeNode node) {
		return ((DepartmentTreeNode) node).getDepDisp().getCode();
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0051DepartmentHierarchySelectionDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent(0);

	}

	/**
	 * �g�D���̕ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditNameView() {

		// �ҏW��ʂ𐶐�
		editNameView = new MG0051DepartmentHierarchySelectionNameDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent(2);

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 * 
	 * @throws TException
	 */
	protected void createEditDepView() throws TException {
		TTable mainHieSpred = mainView.ssHierarchyList;
		// �ҏW��ʂ𐶐�
		editDepView = new MG0051DepartmentHierarchySelectionDepDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent(1);

		// ���������ɐ�����������
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
	 * �ҏW��ʂ̃C�x���g��`�B
	 * 
	 * @param flag
	 */
	protected void addEditViewEvent(int flag) {
		switch (flag) {
			case 0:

				// [�m��]�{�^������
				editView.btnSettle.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						btnEditViewSettle_Click();
						editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				});

				// [�߂�]�{�^������
				editView.btnClose.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						btnEditViewClose_Click();
						editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				});
				break;
			case 1:
				// [�m��]�{�^������
				editDepView.btnSettle.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						editDepView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						btnEditDepViewSettle_Click();
						editDepView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				});

				// [�߂�]�{�^������
				editDepView.btnClose.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						editDepView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						btnEditDepViewClose_Click();
						editDepView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				});
				break;

			// ���̕ҏW
			case 2:
				// [�m��]�{�^������
				editNameView.btnSettle.addActionListener(new ActionListener() {

					public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
						editNameView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						btnSettleName_Click();
						editNameView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				});

				// [�߂�]�{�^������
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
	 * �ҏW��ʂ�����������
	 * 
	 * @param mode_ �V�K
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
	 * ���̕ҏW��ʂ�����������
	 */
	protected void initEditNameView() {

		// ���
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
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnEditViewSettle_Click() {

		try {

			// ���̓`�F�b�N���s���B
			if (!isEditViewInputRight()) {
				return;
			}

			// ���͂��ꂽ�g�D�R�[�h�����擾
			DepartmentOrganization departmentOrganization = getInputedOrg();

			if (Mode.NEW == mode) {
				// �V�K�o�^
				request(getModelClass(), "entryDepartmentOrganization", departmentOrganization);
			}

			// �ҏW��ʂ����
			btnEditViewClose_Click();

			// �ǉ������R���{�{�b�N�X�ɔ��f����
			mainView.ctrlOrganizationCode.getController().initList();

			mainView.ctrlOrganizationCode.setSelectedItemValue(departmentOrganization.getCode());
			mainView.ctrlDepartment.setCode(departmentOrganization.getDepCode());
			mainView.ctrlDepartment.setNames(departmentOrganization.getDepNames());

			// �g�D���������̒�`
			DepartmentOrganizationSearchCondition orgCondition = getTreeSearchCondition();

			// DB����g�D�����擾����
			List<DepartmentOrganization> depOrgList = getDepartmentOrganization(orgCondition);

			// ���匟�������̒�`
			DepartmentSearchCondition depCondition = getSearchCondition();

			// DB���畔������擾����
			List<Department> depList = getDepartment(depCondition);

			// �t�B���^�[
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
	 * �ҏW��ʂœ��͂��ꂽ�g�D�R�[�h��Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�g�D�R�[�h���
	 */
	protected DepartmentOrganization getInputedOrg() {

		// �g�D�R�[�h���
		DepartmentOrganization org = new DepartmentOrganization();
		org.setCode(editView.ctrlOrganizationCode.getValue());
		org.setName(editView.ctrlOrganizationName.getValue());
		org.setDepCode(editView.ctrlDepartmentReference.getCode());
		org.setDepNames(editView.ctrlDepartmentReference.getNames());
		return org;

	}

	/**
	 * �ҏW���[�߂�]�{�^������
	 */
	protected void btnEditViewClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * ���̕ҏW���[�߂�]�{�^������
	 */
	protected void btnEditNameViewClose_Click() {
		editNameView.setVisible(false);
	}

	/**
	 * ����K�w�ݒ���[�߂�]�{�^������
	 */
	protected void btnEditDepViewClose_Click() {
		editDepView.setVisible(false);
	}

	/**
	 * ����R�[�h����List����sort
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
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isEditViewInputRight() throws Exception {

		// �g�D�R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlOrganizationCode.getValue())) {
			showMessage(editView, "I00037", "C00335"); // �g�D�R�[�h
			editView.ctrlOrganizationCode.requestFocus();
			return false;
		}

		// �g�D�R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlOrganizationName.getValue())) {
			showMessage(editView, "I00037", "C11967"); // �g�D����
			editView.ctrlOrganizationName.requestFocus();
			return false;
		}

		// ���x���O�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDepartmentReference.getCode())) {
			showMessage(editView, "I00037", "C00991"); // ���x���O
			editView.ctrlDepartmentReference.btn.requestFocus();
			return false;
		}

		// �g�D�R�[�h���d�����Ă�����G���[
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
	 * ����ݒ��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return ����ݒ��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 */
	protected boolean isEditDepViewInputRight() {
		// ��ʕ��傪�����͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editDepView.ctrlDepartmentReferenceUP.getCode())) {
			showMessage(editDepView, "I00037", "C01909"); // ��ʕ���R�[�h
			editDepView.ctrlDepartmentReferenceUP.requestFocus();
			return false;
		}

		// ���ʕ��傪�����͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editDepView.ctrlDepartmentReferenceLow.getCode())) {
			showMessage(editDepView, "I00037", getWord("C00044") + getWord("C00698")); // ���ʕ���R�[�h
			editDepView.ctrlDepartmentReferenceLow.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * �g�D���̐ݒ��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �g�D���̐ݒ��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 */
	protected boolean isEditDepViewInput() {
		// �g�D���̃u�����N�ŃG���[
		if (Util.isNullOrEmpty(mainView.ctrlOrganizationName.getValue())) {
			showMessage(editDepView, "I00204", "C11967"); // �g�D���̂�ݒ肵�Ă��������B
			mainView.ctrlOrganizationName.requestFocus();
			return false;
		}
		return true;
	}

	/**
	 * �g�D���̐ݒ��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �g�D���̐ݒ��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 */
	protected boolean isEditDepViewInputName() {
		// �g�D���̃u�����N�ŃG���[
		if (Util.isNullOrEmpty(editNameView.ctrlOrganizationName.getValue())) {
			showMessage(editDepView, "I00037", "C11967"); // �g�D����
			editNameView.ctrlOrganizationName.requestFocus();
			return false;
		}
		return true;
	}

}
