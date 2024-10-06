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
 * ��ЊK�w�}�X�^�̃R���g���[��
 * 
 * @author AIS
 */
public class OW0140CompanyHierarchicalMasterPanelCtrl extends TController {

	/** ���샂�[�h */
	public enum Mode {
		/** �V�K */
		NEW
		/** �폜 */
		, DELETE
		/** �G�N�Z�� */
		, EXCEL
		/** �m�� */
		, SETTLE
		/** ��ʉ�� */
		, UPCMP
		/** ���ʉ�� */
		, LOWCMP
	}

	/** �w����� */
	protected OW0140CompanyHierarchicalMasterPanel mainView = null;

	/** �ҏW��� */
	protected OW0140CompanyHierarchicalDialog editView = null;

	/** ��Ќ������ */
	protected OW0140CompanyHierarchicalComDialog editCmpView = null;

	/** ���̕ҏW��� */
	protected OW0140CompanyHierarchicalNameDialog editNameView = null;

	/** ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B */
	protected Mode mode = null;

	/** ��Ђ̃}�b�v **/
	protected HashMap<String, String> cmpMap;

	/** orgList **/
	protected List<CompanyOrganization> orgList = new ArrayList<CompanyOrganization>();

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
	public OW0140CompanyHierarchicalMasterPanel getPanel() {
		return mainView;
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new OW0140CompanyHierarchicalMasterPanel(this);
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
		// [��ʉ��]�{�^������
		mainView.btnUpperCompany.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnUpperCompany_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [���ʉ��]�{�^������
		mainView.btnLowerCompany.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnLowerCompany_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [��Вǉ�]�{�^������
		mainView.btnCompanyAdd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCompanyAdd_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [��Ѝ폜]�{�^������
		mainView.btnCompanyCancellation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCompanyCancellation_Click();
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

			// �m�F���b�Z�[�W��\������
			if (!showConfirmMessage(editCmpView, "Q00004")) {
				return;
			}

			List<CompanyOrganization> list = getSelectedHierarchy(true);
			List<CompanyOrganization> listCmp = new ArrayList<CompanyOrganization>();

			// �K�w�ҏW��ʒl���擾
			String upCmp = editCmpView.ctrlCompanyReferenceUP.getCode();
			String lowCmp = editCmpView.ctrlCompanyReferenceLow.getCode();

			// ��ʒl�̓��̓`�F�b�N
			// ���̓`�F�b�N���s���B
			if (!isEditCmpViewInputRight()) {
				return;
			}

			// ��ʉ�Ђ̊K�w���x��
			int upLevel = -1;

			// ��ʉ�Ђ̃��x�����擾
			for (CompanyOrganization bean : list) {
				String condiCmpCode = bean.getCmpCode();
				if (condiCmpCode.equals(upCmp)) {
					upLevel = bean.getLevel();
					break;
				}
			}

			// ���x���ݒ�
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

			// �����������b�Z�[�W
			showMessage(editCmpView, "I00008");

			// ��ʂ����
			btnEditCmpViewClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �R�[�h�Ɩ��̂�ϊ�
	 * 
	 * @param list ��ЊK�w
	 * @return list ��ЊK�w
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
	 * �K�w�}�X�^���f�[�^���擾���A�ꗗ�ɐݒ肷��B
	 * 
	 * @param list ��ЊK�w
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

		// �ꗗ���N���A����
		mainHieSpred.removeRow();

		// list�̒�����ЃR�[�h���ɕ��בւ�
		Collections.sort(list, new sortList());

		// ���x����List�쐬
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

		// sort���ɃX�v���b�h���בւ�
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

		// ��ʂ����ʂɂȂ����ꍇ�A�������Ă������ʂ����X�v���b�h�ֈړ��B
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

				// ���x���O������
				mainView.ctrlCompany.clear();

				initMainView();

				return;
			}

			if (ItemEvent.SELECTED != state) {
				return;
			}

			// �g�D���������̒�`
			CompanyOrganizationSearchCondition orgCondition = getTreeSearchCondition();

			// DB����g�D�����擾����
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
			// ��Ќ��������̒�`
			CompanySearchCondition cmpCondition = getSearchCondition();

			// DB�����Џ����擾����
			cmpList = getCompany(cmpCondition);

			// ��Џ����}�b�v�փZ�b�g����
			cmpMap = new HashMap<String, String>();

			for (Company cmp : cmpList) {
				// ��ЃR�[�h
				String code = valueString(cmp.getCode());
				// ��Ж���
				String text = valueString(cmp.getName());
				cmpMap.put(code, text);
			}

			// ���x���O������
			mainView.ctrlCompany.clear();

			// ���x���O
			for (CompanyOrganization org : orgList) {
				if (org.getLevel() == 0) {
					String cmpCode = org.getCmpCode();
					mainView.ctrlCompany.setCode(cmpCode);
					mainView.ctrlCompany.setNames(org.getLevel0Name());
					break;
				}
			}

			// �t�B���^�[
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
				// ��Јꗗ�֔��f
				initList(cmpList);
				initListHierarchy(orgList);

				setMainButtonEnabled(!Util.isNullOrEmpty(mainView.ctrlOrganizationCode.getSelectedItemValue()));
			}
			// ��Вǉ��E�폜��L��
			setCmpUpLowButtonEnabled(true);

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

		// ��ʁA���ʉ�Ѓ{�^���������s�ɂ���
		setCmpButtonEnabled(false);
		setCmpUpLowButtonEnabled(false);
		// �g�D���́A���x���e�L�X�g�N���A
		mainView.ctrlOrganizationName.clear();
		mainView.ctrlCompany.lbl.setText(getWord("C00991"));
		mainView.ctrlCompany.lbl.setToolTipText(getWord("C00991"));

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
	 * ��ʉ�ЁA���ʉ�Ђ̉�������
	 * 
	 * @param bln enabled
	 */
	protected void setCmpButtonEnabled(boolean bln) {
		mainView.btnUpperCompany.setEnabled(bln);
		mainView.btnLowerCompany.setEnabled(bln);
	}

	/**
	 * ��ʉ�ЁA���ʉ�Ђ̉�������
	 * 
	 * @param bln enabled
	 */
	protected void setCmpUpLowButtonEnabled(boolean bln) {
		mainView.btnCompanyAdd.setEnabled(bln);
		mainView.btnCompanyCancellation.setEnabled(bln);

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
	 * �w�����[��ʉ��]�{�^������
	 */
	protected void btnUpperCompany_Click() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		try {
			String cmpCode = null;
			String cmpName = null;

			// �ҏW��ʂ𐶐�
			createEditCmpView();

			int row[] = mainHieSpred.getSelectedRows();
			if (row.length == 0) {
				showMessage(editCmpView, "I00041", "C11979"); // ��ЊK�w
				mainView.btnUpperCompany.requestFocus();
				return;

			}

			for (int i = 0; i < row.length; i++) {
				cmpCode = ((String) mainHieSpred.getRowValueAt(row[i], CS2.codeCmp));

				cmpName = cmpMap.get(cmpCode);
			}

			// �ҏW��ʂ�����������
			initEditView(Mode.UPCMP, cmpCode, cmpName);

			// �ҏW��ʂ�\��
			editCmpView.setLocationRelativeTo(null);
			editCmpView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �w�����[���ʉ��]�{�^������
	 */
	protected void btnLowerCompany_Click() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		try {

			String cmpCode = null;
			String cmpName = null;

			// �ҏW��ʂ𐶐�
			createEditCmpView();

			int row[] = mainHieSpred.getSelectedRows();
			if (row.length == 0) {
				showMessage(editCmpView, "I00041", "C11979"); // ��ЊK�w
				mainView.btnUpperCompany.requestFocus();
				return;

			}
			for (int i = 0; i < row.length; i++) {

				cmpCode = ((String) mainHieSpred.getRowValueAt(row[i], CS2.codeCmp));
				cmpName = cmpMap.get(cmpCode);
			}

			// �ҏW��ʂ�����������
			initEditView(Mode.LOWCMP, cmpCode, cmpName);

			// �ҏW��ʂ�\��
			editCmpView.setLocationRelativeTo(null);
			editCmpView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [��Вǉ�]�{�^������
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
				// ��ЃR�[�h
				inner.add(code);
				// ���x��
				inner.add("1");
				// ��Ж��̃��x���P
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
		mainCmpSpred.removeSelectedRows();
		setCmpButtonEnabled(true);
		// 1�s�ڂ�I������
		mainHieSpred.setRowSelection(0);

	}

	/**
	 * [��Ѝ폜]�{�^������
	 */
	protected void btnCompanyCancellation_Click() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		TTable mainBmnSpred = mainView.ssCompanyList;

		int levelDel = 0;
		int selectedRows = mainHieSpred.getRowCount();
		String cmp = (String) mainHieSpred.getSelectedRowValueAt(CS2.codeCmp);
		if (Util.isNullOrEmpty(cmp)) {
			showMessage(mainView, "I00041", "C11979"); // ��ЊK�w��I�����Ă��������B
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
			// ���X�v���b�h�֒ǉ�
			mainBmnSpred.addRow(new Object[] { code, name });
			// �I��������폜
			mainHieSpred.removeRow(rowConst);
			row++;
			if (mainHieSpred.getRowCount() == 0) {
				// �K�w�X�v���b�h0�s���������ʉ��ʃ{�^�������s��
				setCmpButtonEnabled(false);
			}
			if (row >= selectedRows) {
				break;
			}

			while (levelDel < list.get(row).getLevel()) {
				String code2 = (String) mainHieSpred.getRowValueAt(rowConst, CS2.codeCmp);
				String name2 = cmpMap.get(code2);

				// ���X�v���b�h�֒ǉ�
				mainBmnSpred.addRow(new Object[] { code2, name2 });
				// �I��������폜
				mainHieSpred.removeRow(rowConst);
				row++;

				if (mainHieSpred.getRowCount() == 0) {
					// �K�w�X�v���b�h0�s���������ʉ��ʃ{�^�������s��
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
	 * ��Ѓ}�X�^���f�[�^���擾���A�ꗗ�ɐݒ肷��B
	 * 
	 * @param list ���
	 */
	protected void initList(List<Company> list) {
		TTable mainBmnSpred = mainView.ssCompanyList;
		TTable mainHieSpred = mainView.ssHierarchyList;
		// �ꗗ���N���A����
		mainBmnSpred.removeRow();
		mainHieSpred.removeRow();

		// ��ЊK�w�}�X�^�����݂��Ȃ��ꍇ
		if (list == null || list.isEmpty()) {
			return;
		}

		// ��Џ����ꗗ�ɕ\������
		for (Company company : list) {
			mainBmnSpred.addRow(getCompanyDataRow(company));
		}

		// 1�s�ڂ�I������
		mainBmnSpred.setRowSelection(0);

	}

	/**
	 * �K�w�}�X�^���f�[�^���擾���A�ꗗ�ɐݒ肷��B
	 * 
	 * @param list ��ЊK�w
	 */
	protected void initListHierarchy(List<CompanyOrganization> list) {
		TTable mainHieSpred = mainView.ssHierarchyList;
		// �ꗗ���N���A����
		mainHieSpred.removeRow();

		// ��ЊK�w�}�X�^�����݂��Ȃ��ꍇ
		if (list == null || list.isEmpty()) {
			return;
		}

		// ��ЊK�w���ꗗ�ɕ\������
		for (CompanyOrganization bean : list) {
			if (bean.getLevel() != 0) {// ��Ѓ��x���O�ȊO�̓Z�b�g����
				mainHieSpred.addRow(getRowData(bean));
			}
		}
		// 1�s�ڂ�I������
		if (mainHieSpred.getRowCount() != 0) {
			mainHieSpred.setRowSelection(0);
		}
	}

	/**
	 * ��ЊK�w���ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param organization ��ЊK�w
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ��ЊK�w
	 */
	protected List<Object> getRowData(CompanyOrganization organization) {
		List<Object> list = new ArrayList<Object>();
		switch (organization.getLevel()) {

			case 2:
				list.add(organization.getCmpCode()); // ��ЃR�[�h
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
				list.add(organization.getCmpCode()); // ��ЃR�[�h
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
				list.add(organization.getCmpCode()); // ��ЃR�[�h
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
				list.add(organization.getCmpCode()); // ��ЃR�[�h
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
				list.add(organization.getCmpCode()); // ��ЃR�[�h
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
				list.add(organization.getCmpCode()); // ��ЃR�[�h
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
				list.add(organization.getCmpCode()); // ��ЃR�[�h
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
				list.add(organization.getCmpCode()); // ��ЃR�[�h
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
				list.add(organization.getCmpCode()); // ��ЃR�[�h
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
	 * ��ЊK�w���ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param organization ��ЊK�w
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ��ЊK�w
	 */
	protected List<Object> getRowCodeData(CompanyOrganization organization) {
		List<Object> list = new ArrayList<Object>();

		list.add(organization.getCmpCode()); // ��ЃR�[�h
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
	 * ��Ђ̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected CompanySearchCondition getSearchCondition() {
		CompanySearchCondition condition = new CompanySearchCondition();
		return condition;
	}

	/**
	 * ��ЊK�w�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected CompanyOrganizationSearchCondition getOrganizationSearchCondition() {
		CompanyOrganizationSearchCondition condition = new CompanyOrganizationSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());

		return condition;
	}

	/**
	 * ���������ɊY�������Ђ̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�������Ђ̃��X�g
	 * @throws TException
	 */
	protected List<Company> getCompany(CompanySearchCondition condition) throws TException {
		return (List<Company>) request(getModelClass(), "get", condition);
	}

	/**
	 * ���f���N���X��Ԃ�
	 * 
	 * @return ���f���N���X
	 */
	protected Class getModelClass() {
		return CompanyOrganizationManager.class;
	}

	/**
	 * ��Џ����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param company ���
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ���
	 */
	protected Object[] getCompanyDataRow(Company company) {
		return new Object[] { company.getCode(), company.getName(), company };
	}

	/**
	 * ��ЊK�w�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected CompanyOrganizationSearchCondition getTreeSearchCondition() {

		CompanyOrganizationSearchCondition condition = new CompanyOrganizationSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());

		return condition;

	}

	/**
	 * ���������ɊY�������ЊK�w�̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�������ЊK�w�̃��X�g
	 * @throws TException
	 */
	protected List<CompanyOrganization> getCompanyOrganization(CompanyOrganizationSearchCondition condition)
		throws TException {
		return (List<CompanyOrganization>) request(getModelClass(), "getCompanyOrganizationData", condition);
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

			CompanyOrganization bean = getSelectedCmpOrg();

			// ��ЊK�w�����擾
			request(getModelClass(), "deleteCompanyOrganization", bean);

			if (bean == null) {
				// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂���
				throw new TException("I00193");
			}

			// �R���{�{�b�N�X���t���b�V��
			mainView.ctrlOrganizationCode.getController().initList();

			// ���x���O������
			mainView.ctrlCompany.clear();

			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ��ЊK�w��Ԃ�
	 * 
	 * @return ��ЊK�w
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
	 * @return ��ЊK�w���̌�������
	 */
	protected CompanyOrganizationSearchCondition createCompanyOrganizationSearchCondition() {
		return new CompanyOrganizationSearchCondition();
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
			CompanyOrganizationSearchCondition condition = getOrganizationSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getCompanyOrganizationNameExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02220") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �g�D����[�m��]�{�^������
	 */
	protected void btnSettleName_Click() {

		try {

			if (!isEditCmpViewInputName()) {
				return;
			}

			// �m�F���b�Z�[�W��\������
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			CompanyOrganization list = getSelectedName();

			// �o�^����
			request(getModelClass(), "entryCompanyNameOrganization", list);

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

			if (!isEditCmpViewInput()) {
				return;
			}

			// �m�F���b�Z�[�W��\������
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			List<CompanyOrganization> list = getSelectedCmpHie();

			// �o�^����
			request(getModelClass(), "entryCompanyOrganization", mainView.ctrlOrganizationCode.getComboBox()
				.getSelectedItem(), mainView.ctrlOrganizationName.getValue(), list);

			// �����������b�Z�[�W
			showMessage(mainView, "I00008");

			// ���t���b�V��
			changedOrganizationCode(ItemEvent.SELECTED, true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ꗗ�őI������Ă����Ђ�Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă�����
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
	 * �ꗗ�őI������Ă����ЊK�w��Ԃ�
	 * 
	 * @param isConfim ��Аݒ�m�艟����
	 * @return �ꗗ�őI������Ă����ЊK�w
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
				// �m�艟�����̂݊e��Ђ̃��x���擾
				bean = (CompanyOrganization) mainHieSpred.getRowValueAt(row, CS2.bean2);

				// ��ЊK�w�͂X�K�w�ȏ�ݒ�ł��܂���
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
	 * ���̒ǉ���ʂ̓��͒l�擾
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
	 * �ꗗ�őI������Ă����ЊK�w��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă����ЊK�w
	 * @param obj �X�v���b�h�̒l
	 */
	protected static String valueString(Object obj) {
		return (obj == null) ? "" : obj.toString();
	}

	/**
	 * �ꗗ�őI������Ă����ЊK�w��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă����ЊK�w
	 * @param obj �X�v���b�h�̒l
	 */
	protected static int valueInt(Object obj) {
		String objStr = obj.toString();
		int num3 = new Integer(objStr).intValue();
		return num3;

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new OW0140CompanyHierarchicalDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent(0);

	}

	/**
	 * �g�D���̕ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditNameView() {

		// �ҏW��ʂ𐶐�
		editNameView = new OW0140CompanyHierarchicalNameDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent(2);

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 * 
	 * @throws TException
	 */
	protected void createEditCmpView() throws TException {
		TTable mainHieSpred = mainView.ssHierarchyList;
		// �ҏW��ʂ𐶐�
		editCmpView = new OW0140CompanyHierarchicalComDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent(1);

		// ���������ɐ�����������
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
				editCmpView.btnSettle.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						editCmpView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						btnEditCmpViewSettle_Click();
						editCmpView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				});

				// [�߂�]�{�^������
				editCmpView.btnClose.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						editCmpView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						btnEditCmpViewClose_Click();
						editCmpView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				});
				break;

			// �g�D����
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
			CompanyOrganization companyOrganization = getInputedOrg();

			if (Mode.NEW == mode) {
				// �V�K�o�^
				request(getModelClass(), "entryCompanyOrganization", companyOrganization);
			}

			// �ҏW��ʂ����
			btnEditViewClose_Click();

			// �ǉ������R���{�{�b�N�X�ɔ��f����
			mainView.ctrlOrganizationCode.getController().initList();

			mainView.ctrlOrganizationCode.setSelectedItemValue(companyOrganization.getCode());
			mainView.ctrlCompany.setCode(companyOrganization.getCmpCode());
			mainView.ctrlCompany.setNames(companyOrganization.getCmpNames());

			// �g�D���������̒�`
			CompanyOrganizationSearchCondition orgCondition = getTreeSearchCondition();

			// DB����g�D�����擾����
			List<CompanyOrganization> comOrgList = getCompanyOrganization(orgCondition);

			// ��Ќ��������̒�`
			CompanySearchCondition cmpCondition = getSearchCondition();

			// DB�����Џ����擾����
			List<Company> cmpList = getCompany(cmpCondition);

			// �t�B���^�[
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
	 * ��ЃR�[�h����List����sort
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
	 * �ҏW��ʂœ��͂��ꂽ�g�D�R�[�h��Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�g�D�R�[�h���
	 */
	protected CompanyOrganization getInputedOrg() {

		// �g�D�R�[�h���
		CompanyOrganization org = new CompanyOrganization();
		org.setCode(editView.ctrlOrganizationCode.getValue());
		org.setName(editView.ctrlOrganizationName.getValue());
		org.setCmpCode(editView.ctrlCompanyReference.getCode());
		org.setCmpNames(editView.ctrlCompanyReference.getNames());
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
	 * ��ЊK�w�ݒ���[�߂�]�{�^������
	 */
	protected void btnEditCmpViewClose_Click() {
		editCmpView.setVisible(false);
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
		if (Util.isNullOrEmpty(editView.ctrlCompanyReference.getCode())) {
			showMessage(editView, "I00037", "C00991"); // ���x���O
			editView.ctrlCompanyReference.btn.requestFocus();
			return false;
		}

		// �g�D�R�[�h���d�����Ă�����G���[
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
	 * ��Аݒ��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return ��Аݒ��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 */
	protected boolean isEditCmpViewInputRight() {
		// ��ʉ�Ђ������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editCmpView.ctrlCompanyReferenceUP.getCode())) {
			showMessage(editCmpView, "I00037", "C01909"); // ��ʉ�ЃR�[�h
			editCmpView.ctrlCompanyReferenceUP.requestFocus();
			return false;
		}

		// ���ʉ�Ђ������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editCmpView.ctrlCompanyReferenceLow.getCode())) {
			showMessage(editCmpView, "I00037", getWord("C00044") + getWord("C00698")); // ���ʉ�ЃR�[�h
			editCmpView.ctrlCompanyReferenceLow.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * �g�D���̐ݒ��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �g�D���̐ݒ��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 */
	protected boolean isEditCmpViewInput() {
		// �g�D���̃u�����N�ŃG���[
		if (Util.isNullOrEmpty(mainView.ctrlOrganizationName.getValue())) {
			showMessage(editCmpView, "I00204", "C11967"); // �g�D���̂�ݒ肵�Ă��������B
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
	protected boolean isEditCmpViewInputName() {
		// �g�D���̃u�����N�ŃG���[
		if (Util.isNullOrEmpty(editNameView.ctrlOrganizationName.getValue())) {
			showMessage(editCmpView, "I00037", "C11967"); // �g�D����
			editNameView.ctrlOrganizationName.requestFocus();
			return false;
		}
		return true;
	}

}
