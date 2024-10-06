package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.ui.*;

/**
 * ��s���p��
 */
public class MG0450BankMergePanel extends TMainPanel {

	/** �G�N�Z���捞 */
	public TButton btnImportExcel;

	/** ���s */
	public TButton btnExecute;

	/** �X�V�Ώۋ�s�ꗗ */
	public TTable tblImportList;

	/** �G���[���e�ꗗ */
	public TTable tblErrList;

	/** �����m�F�ꗗ */
	public TTable tblCompList;

	/** �X�V�Ώۋ�s���x�� */
	public TLabel labImportList;

	/** �G���[���e�ꗗ���x�� */
	public TLabel labErrList;

	/** �����m�F�ꗗ */
	public TLabel labCompList;

	/**
	 * �X�V�Ώۋ�s�ꗗ
	 */
	public enum IN {
		/** �`�F�b�N */
		CHK_BOX,
		/** �V��s�� */
		NEW_BANK_NAME,
		/** ����s�� */
		OLD_BANK_NAME,
		/** �x�X�� */
		COUNT_BANK_OFFICE,
		/** Entity */
		ENTITY
	}

	/**
	 * �G���[���e�ꗗ
	 */
	public enum ERR {
		/** �s */
		LINE,
		/** �G���[���e */
		ERROR_DETAIL,
		/** Entity */
		ENTITY
	}

	/**
	 * �����m�F�ꗗ
	 */
	public enum COMP {
		/** �}�X�^�� */
		MASTER_NAME,
		/** �ǉ����� */
		COUNT_ADD,
		/** �X�V���� */
		COUNT_UPDATE,
		/** Entity */
		ENTITY
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#initComponents()
	 */
	@Override
	public void initComponents() {

		btnImportExcel = new TButton();
		btnExecute = new TButton();
		labImportList = new TLabel("C11788"); // �X�V�Ώۋ�s
		labErrList = new TLabel("C11789"); // �G���[���e�ꗗ
		labCompList = new TLabel("C11790"); // �����m�F�ꗗ

		tblImportList = new TTable();
		tblImportList.addColumn(IN.CHK_BOX, "", 30, TCheckBox.class);
		tblImportList.addColumn(IN.NEW_BANK_NAME, "C11791", 200); // �V��s��
		tblImportList.addColumn(IN.OLD_BANK_NAME, "C11792", 200); // ����s��
		tblImportList.addColumn(IN.COUNT_BANK_OFFICE, "C11793", 200, SwingConstants.RIGHT); // �x�X��
		tblImportList.addColumn(IN.ENTITY, "", -1, false);

		tblErrList = new TTable();
		tblErrList.addColumn(ERR.LINE, "C00118", 230, SwingConstants.RIGHT); // �s
		tblErrList.addColumn(ERR.ERROR_DETAIL, "C01589", 400); // �G���[���e
		tblErrList.addColumn(ERR.ENTITY, "", -1, false);

		tblCompList = new TTable();
		tblCompList.addColumn(COMP.MASTER_NAME, "C11794", 230); // �}�X�^��
		tblCompList.addColumn(COMP.COUNT_ADD, "C11795", 200, SwingConstants.RIGHT); // �ǉ�����
		tblCompList.addColumn(COMP.COUNT_UPDATE, "C11796", 200, SwingConstants.RIGHT); // �X�V����
		tblCompList.addColumn(COMP.ENTITY, "", -1, false);
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		// �G�N�Z���捞�{�^��
		int x;
		x = HEADER_LEFT_X;
		btnImportExcel.setLangMessageID("C03259"); // �G�N�Z���捞
		btnImportExcel.setShortcutKey(KeyEvent.VK_F1);
		btnImportExcel.setSize(25, 120);
		btnImportExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnImportExcel);

		// ���s�{�^��
		x = x + btnImportExcel.getWidth() + HEADER_MARGIN_X;
		btnExecute.setLangMessageID("C00218"); // ���s
		btnExecute.setShortcutKey(KeyEvent.VK_F8);
		btnExecute.setSize(25, 120);
		btnExecute.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExecute);

		// �X�V�Ώۋ�s
		gc = new GridBagConstraints();
		gc.gridy = 0;
		gc.weightx = 0;
		gc.weighty = 0;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(5, HEADER_LEFT_X, 0, 10);
		pnlBody.add(labImportList, gc);

		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, HEADER_LEFT_X, 2, 10);
		pnlBody.add(tblImportList, gc);

		// �G���[���e�ꗗ
		gc = new GridBagConstraints();
		gc.gridy = 2;
		gc.weightx = 0;
		gc.weighty = 0;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(5, HEADER_LEFT_X, 0, 10);
		pnlBody.add(labErrList, gc);

		gc.gridy = 3;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, HEADER_LEFT_X, 2, 10);
		pnlBody.add(tblErrList, gc);

		// �����m�F�ꗗ
		gc = new GridBagConstraints();
		gc.gridy = 4;
		gc.weightx = 0;
		gc.weighty = 0;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(5, HEADER_LEFT_X, 0, 10);
		pnlBody.add(labCompList, gc);

		gc.gridy = 5;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, HEADER_LEFT_X, 0, 10);
		pnlBody.add(tblCompList, gc);

	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#setTabIndex()
	 */
	@Override
	public void setTabIndex() {

		int i = 1;

		btnImportExcel.setTabControlNo(i++);
		btnExecute.setTabControlNo(i++);
		tblImportList.setTabControlNo(i++);
		tblErrList.setTabControlNo(i++);
		tblCompList.setTabControlNo(i++);

	}

}
