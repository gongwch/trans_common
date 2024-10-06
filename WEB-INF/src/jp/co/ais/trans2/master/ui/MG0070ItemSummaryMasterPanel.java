package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * MG0070ItemSummaryMaster - �ȖڏW�v�}�X�^ - Main Panel Class
 * 
 * @author AIS
 */
public class MG0070ItemSummaryMasterPanel extends TMainPanel{

	/** �����Œ�l */
	protected final int ITEM_WIDTH = 110;

	/** �c���Œ�l */
	protected final int ITEM_HEIGHT = 25;

	/** �V�K�{�^�� */
	public TImageButton btnNew;
	
	/** �����{�^�� */
	public TImageButton btnSearch;

	/** �ҏW�{�^�� */
	public TImageButton btnModify;

	/** ���ʃ{�^�� */
	public TImageButton btnCopy;

	/** �폜�{�^�� */
	public TImageButton btnDelete;

	/** �G�N�Z���{�^�� */
	public TImageButton btnExcel;

	/** �͈͎w�茟���p�l�� */
	public TPanel pnlRefRange;

	/** �Ȗڑ̌n�������i */
	public TItemSummaryRangeUnit ctrlAccSumRef; 

	/** �L�����Ԑ؂� */
	public TCheckBox chkOutputTermEnd;

	/** �ꗗ�p�l�� */
	public TPanel pnlBodyBottom;

	/** �ꗗ */
	public TTable tbl;

	/** �ꗗ�̃J������` */
	public enum SC{

		/** �Ȗڑ̌n�R�[�h */
		KMT_CODE,

		/** �Ȗڑ̌n���� */
		KMT_NAME,

		/** �ȖڃR�[�h */
		KMK_CODE,

		/** �Ȗڗ��� */
		KMK_NAME,

		/** ���\�Ȗږ��� */
		KOK_NAME,

		/** �W�v�����ȖڃR�[�h */
		SUM_CODE,

		/** �W�v�����Ȗڗ��� */
		SUM_NAME,

		/** �Ȗڏo�͏��� */
		ODR,

		/** �\���敪 */
		HYJ_KBN,

		/** �J�n�N���� */
		STR_DATE,

		/** �I���N���� */
		END_DATE,

		/** Entity */
		ENTITY
	}

	@Override
	public void initComponents() {

		btnNew     = new TImageButton(IconType.NEW);
		btnSearch  = new TImageButton(IconType.SEARCH);
		btnModify  = new TImageButton(IconType.EDIT);
		btnCopy    = new TImageButton(IconType.COPY);
		btnDelete  = new TImageButton(IconType.DELETE);
		btnExcel   = new TImageButton(IconType.EXCEL);
		chkOutputTermEnd = new TCheckBox();
		pnlBodyBottom = new TPanel();
		pnlRefRange  = new TPanel();
		ctrlAccSumRef = new TItemSummaryRangeUnit();

		// �ꗗ�i���X�g�j�^�C�g����`
		tbl = new TTable();
		tbl.addColumn(SC.KMT_CODE, getWord("C00617"), 120);
		tbl.addColumn(SC.KMT_NAME, getWord("C00619"), 240);
		tbl.addColumn(SC.KMK_CODE, getWord("C00572"), 120);
		tbl.addColumn(SC.KMK_NAME, getWord("C00730"), 180);
		tbl.addColumn(SC.KOK_NAME, getWord("C00624"), 280);
		tbl.addColumn(SC.SUM_CODE, getWord("C01609"), 150);
		tbl.addColumn(SC.SUM_NAME, getWord("C00625"), 240);
		tbl.addColumn(SC.ODR, getWord("C00626"), 120, SwingConstants.RIGHT);
		tbl.addColumn(SC.HYJ_KBN, getWord("C01300"), 120, SwingConstants.CENTER);
		tbl.addColumn(SC.STR_DATE, getWord("C00055"), 120, SwingConstants.CENTER);
		tbl.addColumn(SC.END_DATE, getWord("C00261"), 120, SwingConstants.CENTER);
		tbl.addColumn(SC.ENTITY, "", -1, false);
	}

	@Override
	public void allocateComponents() {

		// �V�K�{�^��
		int x = HEADER_LEFT_X;
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setSize(ITEM_HEIGHT, ITEM_WIDTH);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// �����{�^��
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(ITEM_HEIGHT, ITEM_WIDTH);
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// �ҏW�{�^��
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(ITEM_HEIGHT, ITEM_WIDTH);
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);
		tbl.addSpreadSheetSelectChange(btnModify);

		// ���ʃ{�^��
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(ITEM_HEIGHT, ITEM_WIDTH);
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// �폜�{�^��
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(ITEM_HEIGHT, ITEM_WIDTH);
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// �G�N�Z���{�^��
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(ITEM_HEIGHT, ITEM_WIDTH + 20);
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// �㕔
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMinimumSize(new Dimension(0, 145));
		pnlBodyTop.setPreferredSize(new Dimension(0, 145));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// �͈̓p�l��
		pnlRefRange.setLangMessageID("C01060");
		pnlRefRange.setLayout(null);
		pnlRefRange.setSize(650, 110);
		pnlRefRange.setLocation(20, 20);
		pnlBodyTop.add(pnlRefRange);

		// �Ȗڑ̌n
		ctrlAccSumRef.setSize(510, 105);
		ctrlAccSumRef.setLocation(20, 25);
		pnlRefRange.add(ctrlAccSumRef);

		// ���b�Z�[�W�ǉ��K�v ##########
		chkOutputTermEnd.setLangMessageID("C11089");
		chkOutputTermEnd.setSize(140, 20);
		chkOutputTermEnd.setLocation(490, 77);
		pnlRefRange.add(chkOutputTermEnd);
		
		// ����
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.insets = new Insets(-5, 20, 10, 20);
		pnlBody.add(tbl, gc);
	}

	@Override
	/**
	 * Tab����`
	 */
	public void setTabIndex() {
		int i = 1;
		ctrlAccSumRef.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}