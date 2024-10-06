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
 * ���b�N�A�E�g�Ǘ��}�X�^�̉�ʃ��C�A�E�g
 * 
 * @author AIS
 */
public class MG0027LockOutMasterPanel extends TMainPanel {

	/** �����{�^�� */
	public TImageButton btnSearch;

	/** �����{�^�� */
	public TImageButton btnDelete;

	/** ���b�N�A�E�g���� */
	public TCheckBox ctrlUnlockFlg;

	/** ���b�N�A�E�g�Ǘ��}�X�^�ꗗ */
	public TTable tbl;

	/** �ꗗ�̃J������` */
	public enum SC {
		/** Entity **/
		ENTITY

		/** ���b�N�A�E�g���� */
		, UNLOCK

		/** ���[�U�[�R�[�h */
		, USR_CODE

		/** ���[�U�[���� */
		, USR_NAME

		/** ���O�C�����s���� */
		, FAILURE

	}

	@Override
	public void initComponents() {
		btnSearch = new TImageButton(IconType.SEARCH);
		btnDelete = new TImageButton(IconType.DELETE);
		ctrlUnlockFlg = new TCheckBox();
		tbl = new TTable();
		tbl.addColumn(SC.ENTITY, "", -1);
		tbl.addColumn(SC.UNLOCK, getWord(""), 40, ctrlUnlockFlg);
		tbl.addColumn(SC.USR_CODE, getWord("C00589"), 180);
		tbl.addColumn(SC.USR_NAME, getWord("C00691"), 180);
		tbl.addColumn(SC.FAILURE, getWord("C02781"), 180, SwingConstants.CENTER);

	}

	@Override
	public void allocateComponents() {

		// �����{�^��
		int x = HEADER_LEFT_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// �����{�^��
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F7);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// �ꗗ
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBody.add(tbl, gc);

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		tbl.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);

	}
}
