package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans.common.gui.*;

/**
 * �o�b�`�r�������}�X�^�̎w����ʃ��C�A�E�g
 * 
 * @author AIS
 */
public class MG0380BatchUnLockPanel extends TMainPanel {

	/** �����{�^�� */
	public TImageButton btnSearch;

	/** �����{�^�� */
	public TImageButton btnRelieve;

	/** �r���ꗗ */
	public TTable tbl;

	/** �r���`�F�b�N�{�b�N�X */
	public TCheckBox checkBox;

	/**
	 * �ꗗ�̃J������`
	 */
	public enum SC {

		/** �e�[�u����� */
		bean,
		/** �`�F�b�N�{�b�N�X */
		CHECK_BOX,
		/** �v���O�����R�[�h */
		BAT_ID,
		/** �v���O�������� */
		PRG_NAME,
		/** �r�����[�U */
		USR_ID,
		/** �r�����[�U���� */
		USR_NAME,
		/** �r�����s���� */
		BAT_STR_DATE

	}

	@Override
	public void initComponents() {

		btnSearch = new TImageButton(IconType.SEARCH);
		btnRelieve = new TImageButton();
		checkBox = new TCheckBox();

		tbl = new TTable();
		tbl.addColumn(SC.bean, "BEAN", -1);
		tbl.addColumn(SC.CHECK_BOX, "", 40, checkBox);
		tbl.addColumn(SC.BAT_ID, "C00818", 120, SwingConstants.LEFT);
		tbl.addColumn(SC.PRG_NAME, "C00819", 180, SwingConstants.LEFT);
		tbl.addColumn(SC.USR_ID, "C03423", 100, SwingConstants.LEFT);
		tbl.addColumn(SC.USR_NAME, "C03424", 140, SwingConstants.LEFT);
		tbl.addColumn(SC.BAT_STR_DATE, "C03425", 140, SwingConstants.CENTER);

	}

	@Override
	public void allocateComponents() {

		// �����{�^��
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(HEADER_LEFT_X, HEADER_Y);
		pnlHeader.add(btnSearch);

		// �����{�^��
		btnRelieve.setLangMessageID("C00056");
		btnRelieve.setShortcutKey(KeyEvent.VK_F7);
		btnRelieve.setSize(25, 110);
		btnRelieve.setLocation(150, HEADER_Y);
		pnlHeader.add(btnRelieve);

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
		int i = 0;
		btnSearch.setTabControlNo(i++);
		btnRelieve.setTabControlNo(i++);
		tbl.setTabControlNo(i++);

	}

}
