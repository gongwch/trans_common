package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.model.history.*;

/**
 * ���F�����_�C�A���O
 */
public class TApproveHistoryDialog extends TDialog {

	/** ����{�^�� */
	public TButton btnReturn;

	/** �X�v���b�h */
	public TTable tbl;

	/** �R���g���[���[ */
	public TApproveHistoryDialogCtrl controller;

	/**
	 * @param parent
	 * @param mordal
	 */
	public TApproveHistoryDialog(Frame parent, boolean mordal) {
		super(parent, mordal);

		controller = createController();
	}

	/**
	 * @return �R���g���[���[
	 */
	public TApproveHistoryDialogCtrl createController() {
		return new TApproveHistoryDialogCtrl(this);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#initComponents()
	 */
	@Override
	public void initComponents() {
		btnReturn = new TButton();
		tbl = new TTable();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		setResizable(true);

		setPreferredSize(new Dimension(800, 520));

		setTitle(getWord("C11755")); // ���F����

		int x = getPreferredSize().width - 160;
		int y = 10;

		// ����{�^��
		btnReturn.setText(getWord("C02374")); // ����
		btnReturn.setSize(25, 120);
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setLocation(x, y);
		pnlHeader.add(btnReturn);

		// �ꗗ
		gc = new GridBagConstraints();
		gc.insets = new Insets(10, 10, 0, 10);
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTHWEST;
		pnlBody.add(tbl, gc);

		initSpread();

		pack();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#setTabIndex()
	 */
	@Override
	public void setTabIndex() {
		int i = 1;
		tbl.setTabControlNo(i++);
		btnReturn.setTabControlNo(i++);
	}

	/**
	 * �X�v���b�h������
	 */
	public void initSpread() {
		tbl.addColumn(SC.companyCode, getWord("C00596"), 75); // ��ЃR�[�h
		tbl.addColumn(SC.slipDate, getWord("C00599"), 75, SwingConstants.CENTER); // �`�[���t
		tbl.addColumn(SC.slipNo, getWord("C00605"), 110); // �`�[�ԍ�
		tbl.addColumn(SC.slipState, getWord("C01069"), 75, SwingConstants.CENTER); // �X�V�敪
		tbl.addColumn(SC.employeeCode, getWord("C00363"), 75); // �S����
		tbl.addColumn(SC.employeeName, getWord("C11296"), 95); // �S���Җ���
		tbl.addColumn(SC.employeeNameS, getWord("C11756"), 0); // �S���җ���
		tbl.addColumn(SC.approveFlag, getWord("C02829"), 75, SwingConstants.CENTER); // �����敪
		tbl.addColumn(SC.approveDate, getWord("C11757"), 115, SwingConstants.CENTER); // ��������
	}

	/**
	 * SC
	 */
	public enum SC {
		/** ��ЃR�[�h */
		companyCode,

		/** �`�[���t */
		slipDate,

		/** �`�[�ԍ� */
		slipNo,

		/** �X�V�敪 */
		slipState,

		/** �S���� */
		employeeCode,

		/** �S���Җ��� */
		employeeName,

		/** �S���җ��� */
		employeeNameS,

		/** �����敪 */
		approveFlag,

		/** �������� */
		approveDate
	}

	/**
	 * �f�[�^�ݒ�
	 * 
	 * @param list
	 */
	public void setData(List<ApproveHistory> list) {
		controller.setData(list);
	}

}
