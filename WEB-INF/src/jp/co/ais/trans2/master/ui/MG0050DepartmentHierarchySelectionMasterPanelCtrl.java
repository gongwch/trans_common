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
 * ����K�w�}�X�^�̃R���g���[��
 * 
 * @author AIS
 */
public class MG0050DepartmentHierarchySelectionMasterPanelCtrl extends TController {

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
		/** ��ʕ��� */
		, UPDEP
		/** ���ʕ��� */
		, LOWDEP
	}

	/** �w����� */
	protected MG0050DepartmentHierarchySelectionMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0050DepartmentHierarchySelectionDialog editView = null;

	/** ���匟����� */
	protected MG0050DepartmentHierarchySelectionDepDialog editDepView = null;

	/** ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B */
	protected Mode mode = null;

	/** ����̃}�b�v **/
	protected HashMap<String, String> depMap;

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
	public MG0050DepartmentHierarchySelectionMasterPanel getPanel() {
		return mainView;
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0050DepartmentHierarchySelectionMasterPanel(this);
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
				changedOrganizationCode(evt.getStateChange());
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

	}

	/**
	 * [�m��]�{�^������
	 */
	protected void btnEditDepViewSettle_Click() {

		try {

			// �m�F���b�Z�[�W��\������
			if (!showConfirmMessage(editDepView, "Q00004")) {
				return;
			}

			List<DepartmentOrganization> list = getSelectedHierarchy();
			List<DepartmentOrganization> listDep = new ArrayList<DepartmentOrganization>();

			// �K�w�ҏW��ʒl���擾
			String upDep = editDepView.ctrlDepartmentReferenceUP.getCode();
			String lowDep = editDepView.ctrlDepartmentReferenceLow.getCode();

			// ��ʒl�̓��̓`�F�b�N
			// ���̓`�F�b�N���s���B
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

			// ��ʕ���̊K�w���x��
			int upLevel = -1;

			// ��ʕ���̃��x�����擾
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

			// ���x���ݒ�
			for (int row = 0; row < list.size(); row++) {
				DepartmentOrganization bean = new DepartmentOrganization();
				String condiDepCode = list.get(row).getDepCode();
				if (condiDepCode.equals(lowDep)) {
					bean.setCompanyCode(getCompanyCode());
					bean.setDepCode(lowDep);
					bean.setLevel(upLevel + 1);
					// ����K�w�͂X�K�w�ȏ�ݒ�ł��܂���
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
	 * �K�w�}�X�^���f�[�^���擾���A�ꗗ�ɐݒ肷��B
	 * 
	 * @param list ����K�w
	 */
	protected void modifyListHierarchy(List<DepartmentOrganization> list) {

		TTable mainHieSpred = mainView.ssHierarchyList;
		// �ꗗ���N���A����
		mainHieSpred.removeRow();

		// ����K�w���ꗗ�ɕ\������
		for (int k = 0; k < list.size(); k++) {
			if (list.get(k).getLevel() != 0) {// ���僌�x���O�ȊO�̓Z�b�g����
				mainHieSpred.addRow(getRowData(list.get(k)));
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
	 */
	protected void changedOrganizationCode(int state) {
		try {
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
			List<DepartmentOrganization> orgList = getDepartmentOrganization(orgCondition);

			if (orgList.size() < 2) {
				setDepButtonEnabled(false);
			} else {
				setDepButtonEnabled(true);
			}

			// ���匟�������̒�`
			DepartmentSearchCondition depCondition = getSearchCondition();

			// DB���畔������擾����
			List<Department> depList = getDepartment(depCondition);

			// ��������}�b�v�փZ�b�g����
			depMap = new LinkedHashMap();

			for (int j = 0; j < depList.size(); j++) {
				// ����R�[�h
				String code = valueString(depList.get(j).getCode());
				// ���喼��
				String text = valueString(depList.get(j).getName());
				depMap.put(code, text);
			}

			// ���x���O������
			mainView.ctrlDepartment.clear();

			// ���x���O
			for (DepartmentOrganization org : orgList) {
				if (org.getLevel() == 0) {
					String departmentCode = org.getDepCode();
					mainView.ctrlDepartment.setCode(departmentCode);
					mainView.ctrlDepartment.setNames(org.getDepName());
					break;
				}
			}

			// �t�B���^�[
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

			// ����ꗗ�֔��f
			initList(depList);
			initListHierarchy(orgList);

			setMainButtonEnabled(!Util.isNullOrEmpty(mainView.ctrlOrganizationCode.getSelectedItemValue()));

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
		setDepUpLowButtonEnabledDfault(false);
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
	protected void setDepUpLowButtonEnabledDfault(boolean bln) {
		mainView.btnDepartmentAdd.setEnabled(bln);
		mainView.btnDepartmentCancellation.setEnabled(bln);

	}

	/**
	 * ��ʕ���A���ʕ���̉�������
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
				depCode = ((String) mainHieSpred.getRowValueAt(row[i], DS2.codedep));

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

				depCode = ((String) mainHieSpred.getRowValueAt(row[i], DS2.codedep));
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
		setDepUpLowButtonEnabled(true);
		// 1�s�ڂ�I������
		mainHieSpred.setRowSelection(0);

	}

	/**
	 * [����폜]�{�^������
	 */
	protected void btnDepartmentCancellation_Click() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		TTable mainBmnSpred = mainView.ssDepartmentList;

		int levelDel = 0;
		int selectedRows = mainHieSpred.getRowCount();
		String dep = (String) mainHieSpred.getSelectedRowValueAt(DS2.codedep);
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
			String code = (String) mainHieSpred.getRowValueAt(rowConst, DS2.codedep);
			String depName = depMap.get(code);
			// ���X�v���b�h�֒ǉ�
			mainBmnSpred.addRow(new Object[] { code, depName });
			// �I��������폜
			mainHieSpred.removeRow(rowConst);
			row++;
			if (row >= selectedRows) {
				break;
			}

			while (levelDel < list.get(row).getLevel()) {
				String code2 = (String) mainHieSpred.getRowValueAt(rowConst, DS2.codedep);
				String depName2 = depMap.get(code2);

				// ���X�v���b�h�֒ǉ�
				mainBmnSpred.addRow(new Object[] { code2, depName2 });
				// �I��������폜
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
	 * ����}�X�^���f�[�^���擾���A�ꗗ�ɐݒ肷��B
	 * 
	 * @param list ����
	 */
	protected void initList(List<Department> list) {
		TTable mainBmnSpred = mainView.ssDepartmentList;
		// �ꗗ���N���A����
		mainBmnSpred.removeRow();

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
		for (int k = 0; k < list.size(); k++) {
			if (list.get(k).getLevel() != 0) {// ���僌�x���O�ȊO�̓Z�b�g����
				mainHieSpred.addRow(getRowData(list.get(k)));
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
		list.add(organization.getLevel1()); // ���x���P
		list.add(organization.getLevel2()); // ���x���Q
		list.add(organization.getLevel3()); // ���x���R
		list.add(organization.getLevel4()); // ���x���S
		list.add(organization.getLevel5()); // ���x���T
		list.add(organization.getLevel6()); // ���x���U
		list.add(organization.getLevel7()); // ���x���V
		list.add(organization.getLevel8()); // ���x���W
		list.add(organization.getLevel9()); // ���x���X
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
		return DepartmentManager.class;
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
			byte[] data = (byte[]) request(getModelClass(), "getDepartmentOrganizationExcel", condition);

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
	 * [�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			// �m�F���b�Z�[�W��\������
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			// List<DepartmentOrganization> list = getSelectedHierarchy();
			List<DepartmentOrganization> list = getSelectedDepHie();

			// �o�^����
			request(getModelClass(), "entryDepartmentOrganization",
				mainView.ctrlOrganizationCode.getSelectedOrganizationCode(), list);

			// �����������b�Z�[�W
			showMessage(mainView, "I00008");

			// ���t���b�V��
			changedOrganizationCode(ItemEvent.SELECTED);

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
	 * @return �ꗗ�őI������Ă��镔��K�w
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
		editView = new MG0050DepartmentHierarchySelectionDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent(0);

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditDepView() {
		TTable mainHieSpred = mainView.ssHierarchyList;
		// �ҏW��ʂ𐶐�
		editDepView = new MG0050DepartmentHierarchySelectionDepDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent(1);

		// ���������ɐ�����������
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
			List<DepartmentOrganization> orgList = getDepartmentOrganization(orgCondition);

			// ���匟�������̒�`
			DepartmentSearchCondition depCondition = getSearchCondition();

			// DB���畔������擾����
			List<Department> depList = getDepartment(depCondition);

			// �t�B���^�[
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
	 * �ҏW��ʂœ��͂��ꂽ�g�D�R�[�h��Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�g�D�R�[�h���
	 */
	protected DepartmentOrganization getInputedOrg() {

		// �g�D�R�[�h���
		DepartmentOrganization org = new DepartmentOrganization();
		org.setCode(editView.ctrlOrganizationCode.getValue());
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
	 * ����K�w�ݒ���[�߂�]�{�^������
	 */
	protected void btnEditDepViewClose_Click() {
		editDepView.setVisible(false);
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

		// ���x���O�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlDepartmentReference.getCode())) {
			showMessage(editView, "I00037", "C00722"); // ���x���O
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

}
