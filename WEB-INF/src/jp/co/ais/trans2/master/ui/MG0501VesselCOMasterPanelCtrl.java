package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * ���q�O�q�敪�}�X�^�R���g���[��
 * 
 * @author AIS
 */
public class MG0501VesselCOMasterPanelCtrl extends TController {

	/**
	 * �w�����
	 */
	protected MG0501VesselCOMasterPanel mainView = null;

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

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0501VesselCOMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		// [����]�{�^������
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�m��]�{�^������
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�G�N�Z��]�{�^������
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [��(���q�D�ǉ�)]�{�^������
		mainView.btnCoastalAddVessel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCoastalAddVessel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [��(���q�D�폜)]�{�^������
		mainView.btnCoastalDeleteVessel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCoastalDeleteVessel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [��(�O�q�D�ǉ�)]�{�^������
		mainView.btnOceanAddVessel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnOceanAddVessel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [��(�O�q�D�폜)]�{�^������
		mainView.btnOceanDeleteVessel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnOceanDeleteVessel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * �w����ʂ�����������
	 */
	protected void initMainView() {

		// �����������Ɠ��l�̐���
		btnSearch_Click();

	}

	/**
	 * �w�����[����]�{�^������
	 */
	protected void btnSearch_Click() {

		try {

			// �ꗗ���N���A
			mainView.tblRight.removeRow();
			mainView.tblUpper.removeRow();
			mainView.tblLower.removeRow();

			// �f�[�^�擾
			List<VesselCO> list = (List<VesselCO>) request(getModelClass(), "get", false);

			// ���������ɊY������D��񂪑��݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			for (VesselCO VesselCO : list) {
				if (VesselCO.getCOKbn().equals("1")) {

					// ���ナ�X�g���ꗗ�ɕ\������
					mainView.tblUpper.addRow(addSelectedCoastl(VesselCO));

				} else if (VesselCO.getCOKbn().equals("2")) {

					// �������X�g���ꗗ�ɕ\������
					mainView.tblLower.addRow(addSelectedOcean(VesselCO));
				} else {

					// �E���X�g���ꗗ�ɕ\������
					mainView.tblRight.addRow(addCandidateVessel(VesselCO));
				}
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {

		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// �f�[�^���o
			byte[] data = (byte[]) request(getModelClass(), "getExcel");

			// �f�[�^�����������ꍇ�A�G���[���b�Z�[�W�o��
			if (Util.isNullOrEmpty(data)) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11985") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [��(���q�D�ǉ�)]�{�^������
	 */
	protected void btnCoastalAddVessel_Click() {

		try {

			// �E���X�g����I�����ꂽ�f�[�^���擾
			List<VesselCO> list = getSelectedVesselList();

			// ���ナ�X�g���Đݒ�
			for (VesselCO VesselCO : list) {
				mainView.tblUpper.addRow(addSelectedCoastl(VesselCO));
			}

			// �E���X�g�̑I���s���폜
			deleteVesselRow();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [��(���q�D����)]�{�^������
	 */
	protected void btnCoastalDeleteVessel_Click() {

		try {

			// ���ナ�X�g����I�����ꂽ�f�[�^���擾
			List<VesselCO> list = getSelectedCoastlList();

			// �E���X�g���Đݒ�
			for (VesselCO VesselCO : list) {
				mainView.tblRight.addRow(addCandidateVessel(VesselCO));
			}

			// ���ナ�X�g�̑I���s���폜
			deleteCoastlRow();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [��(�O�q�D�ǉ�)]�{�^������
	 */
	protected void btnOceanAddVessel_Click() {

		try {

			// �E���X�g����I�����ꂽ�f�[�^���擾
			List<VesselCO> list = getSelectedVesselList();

			// �������X�g���Đݒ�
			for (VesselCO VesselCO : list) {
				mainView.tblLower.addRow(addSelectedOcean(VesselCO));
			}

			// �E���X�g�̑I���s���폜
			deleteVesselRow();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [��(�O�q�D����)]�{�^������
	 */
	protected void btnOceanDeleteVessel_Click() {

		try {

			// �������X�g����I�����ꂽ�f�[�^���擾
			List<VesselCO> list = getSelectedOceanList();

			// �E���X�g���Đݒ�
			for (VesselCO VesselCO : list) {
				mainView.tblRight.addRow(addSelectedOcean(VesselCO));
			}

			// �������X�g�̑I���s���폜
			deleteOceanRow();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ���ナ�X�g����I�����ꂽ�f�[�^���擾
	 * 
	 * @return �I���s���X�g
	 */
	protected List<VesselCO> getSelectedCoastlList() {

		List<VesselCO> list = new ArrayList<VesselCO>();

		int[] rows = mainView.tblUpper.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			VesselCO vesselCO = new VesselCO();
			vesselCO.setVesselCode((String) mainView.tblUpper.getRowValueAt(rows[i],
				MG0501VesselCOMasterPanel.SC.vesselCode));
			vesselCO.setVesselNames((String) mainView.tblUpper.getRowValueAt(rows[i],
				MG0501VesselCOMasterPanel.SC.vesselNames));
			list.add(vesselCO);
		}
		return list;
	}

	/**
	 * �������X�g����I�����ꂽ�f�[�^���擾
	 * 
	 * @return �I���s���X�g
	 */
	protected List<VesselCO> getSelectedOceanList() {

		List<VesselCO> list = new ArrayList<VesselCO>();

		int[] rows = mainView.tblLower.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			VesselCO vesselCO = new VesselCO();
			vesselCO.setVesselCode((String) mainView.tblLower.getRowValueAt(rows[i],
				MG0501VesselCOMasterPanel.SC.vesselCode));
			vesselCO.setVesselNames((String) mainView.tblLower.getRowValueAt(rows[i],
				MG0501VesselCOMasterPanel.SC.vesselNames));
			list.add(vesselCO);
		}
		return list;
	}

	/**
	 * �E���X�g����I�����ꂽ�f�[�^���擾
	 * 
	 * @return �I���s���X�g
	 */
	protected List<VesselCO> getSelectedVesselList() {

		List<VesselCO> list = new ArrayList<VesselCO>();

		int[] rows = mainView.tblRight.getSelectedRows();
		for (int i = 0; i < rows.length; i++) {
			VesselCO vesselCO = new VesselCO();
			vesselCO.setVesselCode((String) mainView.tblRight.getRowValueAt(rows[i],
				MG0501VesselCOMasterPanel.SC.vesselCode));
			vesselCO.setVesselNames((String) mainView.tblRight.getRowValueAt(rows[i],
				MG0501VesselCOMasterPanel.SC.vesselNames));
			list.add(vesselCO);
		}
		return list;
	}

	/**
	 * ���ナ�X�g����I�����ꂽ�f�[�^���폜
	 */
	protected void deleteCoastlRow() {

		int[] rows = mainView.tblUpper.getSelectedRows();
		for (int i = rows.length - 1; i >= 0; i--) {
			mainView.tblUpper.removeRow(rows[i]);
		}
	}

	/**
	 * �������X�g����I�����ꂽ�f�[�^���폜
	 */
	protected void deleteOceanRow() {

		int[] rows = mainView.tblLower.getSelectedRows();
		for (int i = rows.length - 1; i >= 0; i--) {
			mainView.tblLower.removeRow(rows[i]);
		}
	}

	/**
	 * �E���X�g����I�����ꂽ�f�[�^���폜
	 */
	protected void deleteVesselRow() {

		int[] rows = mainView.tblRight.getSelectedRows();
		for (int i = rows.length - 1; i >= 0; i--) {
			mainView.tblRight.removeRow(rows[i]);
		}
	}

	/**
	 * ���ナ�X�g�Ƀf�[�^��ǉ�����
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> addSelectedCoastl(VesselCO bean) {

		List<Object> list = new ArrayList<Object>();
		list.add(bean.getVesselCode());
		list.add(bean.getVesselNames());

		return list;
	}

	/**
	 * �������X�g�Ƀf�[�^��ǉ�����
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> addSelectedOcean(VesselCO bean) {

		List<Object> list = new ArrayList<Object>();
		list.add(bean.getVesselCode());
		list.add(bean.getVesselNames());

		return list;
	}

	/**
	 * �E���X�g�Ƀf�[�^��ǉ�����
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> addCandidateVessel(VesselCO bean) {

		List<Object> list = new ArrayList<Object>();
		list.add(bean.getVesselCode());
		list.add(bean.getVesselNames());

		return list;
	}

	/**
	 * �w�����[�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			// ���㉺���X�g�ꗗ�̎擾
			List<VesselCO> list = getSelectedVesselCOList();

			// DB�ꊇ�폜
			request(getModelClass(), "delete");

			// �V�K�o�^
			request(getModelClass(), "entry", list);

			// �������b�Z�[�W
			showMessage(mainView, "I00008");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ���㉺���X�g�ꗗ�̎擾
	 * 
	 * @return �I���s���X�g
	 */
	protected List<VesselCO> getSelectedVesselCOList() {

		List<VesselCO> list = new ArrayList<VesselCO>();

		// ���ナ�X�g�ꗗ�̎擾
		int rows = mainView.tblUpper.getRowCount();
		for (int i = 0; i < rows; i++) {
			VesselCO vesselCO = new VesselCO();
			vesselCO
				.setVesselCode((String) mainView.tblUpper.getRowValueAt(i, MG0501VesselCOMasterPanel.SC.vesselCode));
			vesselCO.setCOKbn("1");
			list.add(vesselCO);
		}

		// �������X�g�ꗗ�̎擾
		int rows_ = mainView.tblLower.getRowCount();
		for (int j = 0; j < rows_; j++) {
			VesselCO vesselCO = new VesselCO();
			vesselCO
				.setVesselCode((String) mainView.tblLower.getRowValueAt(j, MG0501VesselCOMasterPanel.SC.vesselCode));
			vesselCO.setCOKbn("2");
			list.add(vesselCO);
		}
		return list;
	}

	/**
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return �N���X
	 */
	protected Class getModelClass() {
		return VesselCOManager.class;
	}
}
