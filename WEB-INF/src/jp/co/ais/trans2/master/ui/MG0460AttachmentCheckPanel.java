package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * �Y�t���؉��
 */
public class MG0460AttachmentCheckPanel extends TMainPanel {

	// ���C���{�^����

	/** �����{�^�� */
	protected TImageButton btnSearch;

	/** �G�N�Z�� */
	protected TImageButton btnExcel;

	/** ���ʈꗗ */
	protected TTable tblResult;

	/** �ꗗ���` */
	protected enum SC {
		/** ��ЃR�[�h */
		KAI_CODE,
		/** �f�[�^�敪(�`�[�E�D��EOP�Ȃ�) */
		DATA_TYPE,
		/** �L�[���1 */
		KEY1,
		/** �L�[���2 */
		KEY2,
		/** �L�[���3 */
		KEY3,
		/** �L�[���4 */
		KEY4,
		/** �t�@�C���� */
		FILE_NAME,
		/** �T�[�o�[��t�@�C���� */
		SRV_FILE_NAME,
		/** ���b�Z�[�W */
		MESSAGE
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#initComponents()
	 */
	@Override
	public void initComponents() {
		btnSearch = new TImageButton(IconType.SEARCH);
		btnExcel = new TImageButton(IconType.EXCEL);

		initTable();
	}

	/**
	 * ���ʈꗗ������
	 */
	protected void initTable() {
		tblResult = new TTable();

		tblResult.addColumn(SC.KAI_CODE, "��ЃR�[�h", 80, SwingConstants.LEFT);
		tblResult.addColumn(SC.DATA_TYPE, "�f�[�^���", 80, SwingConstants.CENTER);
		tblResult.addColumn(SC.KEY1, "�L�[���1", 120, SwingConstants.LEFT);
		tblResult.addColumn(SC.KEY2, "�L�[���2", 120, SwingConstants.LEFT);
		tblResult.addColumn(SC.KEY3, "�L�[���3", 120, SwingConstants.LEFT);
		tblResult.addColumn(SC.KEY4, "�L�[���4", 120, SwingConstants.LEFT);
		tblResult.addColumn(SC.FILE_NAME, "�t�@�C����", 120, SwingConstants.LEFT);
		tblResult.addColumn(SC.SRV_FILE_NAME, "�T�[�o�[�t�@�C����", 120, SwingConstants.LEFT);
		tblResult.addColumn(SC.MESSAGE, "���b�Z�[�W", 120, SwingConstants.LEFT);
	}

	@Override
	public void allocateComponents() {
		// ���C���{�^����������
		allocateMainButton();
		// �{�f�B��������
		allocateBody();
	}

	/**
	 * ���C���{�^�����z�u
	 */
	protected void allocateMainButton() {
		// �����{�^��
		int x = HEADER_LEFT_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// �G�N�Z���{�^��
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

	}

	/**
	 * �{�f�B���z�u
	 */
	protected void allocateBody() {
		pnlBody.setLayout(new GridBagLayout());

		// �ꗗ
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBody.add(tblResult, gc);
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#setTabIndex()
	 */
	@Override
	public void setTabIndex() {
		int i = 0;
		btnSearch.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
		tblResult.setTabControlNo(i++);
	}

}
