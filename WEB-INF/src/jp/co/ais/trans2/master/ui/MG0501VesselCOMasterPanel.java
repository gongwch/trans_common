package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * ���q�O�q�敪�}�X�^�̎w�����
 * 
 * @author AIS
 */
public class MG0501VesselCOMasterPanel extends TMainPanel {

	/** �����{�^�� */
	public TImageButton btnSearch;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �G�N�Z���{�^�� */
	public TImageButton btnExcel;

	/** ���q�D���x�� */
	public TLabel lblUpper;

	/** ���q�D�ꗗ */
	public TTable tblUpper;

	/** �O�q�D���x�� */
	public TLabel lblLower;

	/** �O�q�D�ꗗ */
	public TTable tblLower;

	/** �{�^���p�l�� */
	public TPanel pnlBodyButton;

	/** ���q�D�ǉ��{�^�� */
	public TButton btnCoastalAddVessel;

	/** ���q�D�폜�{�^�� */
	public TButton btnCoastalDeleteVessel;

	/** �O�q�D�ǉ��{�^�� */
	public TButton btnOceanAddVessel;

	/** �O�q�D�폜�{�^�� */
	public TButton btnOceanDeleteVessel;

	/** �D���ꗗ */
	public TTable tblRight;

	/**
	 * �e�[�u���񖼗񋓑�
	 */
	public enum SC {
		/** �D�R�[�h */
		vesselCode,
		/** �D���� */
		vesselNames
	}

	/**
	 * �R���|�[�l���g������
	 */
	@Override
	public void initComponents() {

		btnSearch = new TImageButton(IconType.SEARCH);
		btnSettle = new TImageButton(IconType.SETTLE);
		btnExcel = new TImageButton(IconType.EXCEL);
		lblUpper = new TLabel();
		tblUpper = new TTable();
		tblUpper.addColumn(SC.vesselCode, getWord("C11758"), 200);// �D�R�[�h
		tblUpper.addColumn(SC.vesselNames, getWord("C11759"), 300);// �D����
		lblLower = new TLabel();
		tblLower = new TTable();
		tblLower.addColumn(SC.vesselCode, getWord("C11758"), 200);// �D�R�[�h
		tblLower.addColumn(SC.vesselNames, getWord("C11759"), 300);// �D����
		pnlBodyButton = new TPanel();
		btnCoastalAddVessel = new TButton();
		btnCoastalDeleteVessel = new TButton();
		btnOceanAddVessel = new TButton();
		btnOceanDeleteVessel = new TButton();
		tblRight = new TTable();
		tblRight.addColumn(SC.vesselCode, getWord("C11758"), 200);// �D�R�[�h
		tblRight.addColumn(SC.vesselNames, getWord("C11759"), 300);// �D����
	}

	/**
	 * �R���|�[�l���g�z�u
	 */
	@Override
	public void allocateComponents() {

		setSize(850, 600);

		// �����{�^��
		int x = HEADER_LEFT_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// �m��{�^��
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		btnSettle.setEnterFocusable(true);
		pnlHeader.add(btnSettle);

		// �G�N�Z���{�^��
		x = x + btnSettle.getWidth() + HEADER_MARGIN_X;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x + 10, HEADER_Y);
		pnlHeader.add(btnExcel);

		// ���q�D���x��
		lblUpper.setLangMessageID("C11983");
		TGuiUtil.setComponentSize(lblUpper, 550, 60);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 30, 0, 0);
		gc.gridx = 0;
		gc.gridy = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(lblUpper, gc);

		// ���q�D�ꗗ
		tblUpper.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		TGuiUtil.setComponentSize(tblUpper, 550, 350);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 30, 0, 0);
		gc.weightx = 0.9d;
		gc.weighty = 0.5d;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(tblUpper, gc);

		// �O�q�D���x��
		lblLower.setLangMessageID("C11984");
		TGuiUtil.setComponentSize(lblLower, 550, 60);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 30, 0, 0);
		gc.gridx = 0;
		gc.gridy = 2;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(lblLower, gc);

		// �O�q�D�ꗗ
		tblLower.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		TGuiUtil.setComponentSize(tblLower, 550, 350);
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 30, 0, 0);
		gc.weightx = 0.9d;
		gc.weighty = 0.5d;
		gc.gridx = 0;
		gc.gridy = 3;
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(tblLower, gc);

		// �{�^���p�l��
		pnlBodyButton.setLayout(new GridBagLayout());
		pnlBodyButton.setMaximumSize(new Dimension(100, 820));
		pnlBodyButton.setMinimumSize(new Dimension(100, 820));
		pnlBodyButton.setPreferredSize(new Dimension(100, 820));
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridheight = 4;
		pnlBody.add(pnlBodyButton, gc);

		// ���q�D�ǉ��{�^��
		btnCoastalAddVessel.setLangMessageID("C10140");// ��
		btnCoastalAddVessel.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.insets = new Insets(70, 0, 50, 0);
		pnlBodyButton.add(btnCoastalAddVessel, gc);

		// ���q�D�폜�{�^��
		btnCoastalDeleteVessel.setLangMessageID("C10139");// ��
		btnCoastalDeleteVessel.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.insets = new Insets(0, 0, 150, 0);
		pnlBodyButton.add(btnCoastalDeleteVessel, gc);

		// �O�q�D�ǉ��{�^��
		btnOceanAddVessel.setLangMessageID("C10140");// ��
		btnOceanAddVessel.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.gridy = 2;
		gc.insets = new Insets(160, 0, 0, 0);
		pnlBodyButton.add(btnOceanAddVessel, gc);

		// �O�q�D�폜�{�^��
		btnOceanDeleteVessel.setLangMessageID("C10139");// ��
		btnOceanDeleteVessel.setSize(20, 20);
		gc = new GridBagConstraints();
		gc.gridy = 3;
		gc.insets = new Insets(50, 0, 0, 0);
		pnlBodyButton.add(btnOceanDeleteVessel, gc);

		// �D�ꗗ
		tblRight.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		TGuiUtil.setComponentSize(tblRight, 550, 770);
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.gridx = 2;
		gc.gridy = 0;
		gc.gridheight = 4;
		gc.insets = new Insets(60, 0, 0, 30);
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(tblRight, gc);
	}

	/**
	 * Tab Index Setting
	 */
	@Override
	public void setTabIndex() {
		int i = 0;
		tblUpper.setTabControlNo(i++);
		tblLower.setTabControlNo(i++);
		btnCoastalAddVessel.setTabControlNo(i++);
		btnCoastalDeleteVessel.setTabControlNo(i++);
		btnOceanAddVessel.setTabControlNo(i++);
		btnOceanDeleteVessel.setTabControlNo(i++);
		tblRight.setTabControlNo(i++);

		btnSearch.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}