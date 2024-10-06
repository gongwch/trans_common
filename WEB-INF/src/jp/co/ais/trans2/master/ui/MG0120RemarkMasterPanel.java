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
 * �E�v�}�X�^�̎w�����
 * 
 * @author AIS
 */
public class MG0120RemarkMasterPanel extends TMainPanel {

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

	/** �㕔 */
	public TPanel pnlBodyTop;

	/** ���������p�l�� */
	public TPanel pnlSearchCondition;

	/** �E�v�͈͌����J�n�I�� */
	public TRemarkReferenceRange ctrlRemRefRan;

	/** �L�������؂�`�F�b�N�{�b�N�X */
	public TCheckBox chkOutputTermEnd;

	/** �E�v�敪�p�l�� */
	public TPanel pnlTek;

	/** �E�v�敪 ButtonGroup */
	public ButtonGroup bgBmn;

	/** �`�[�E�v */
	public TRadioButton rdoDnp;

	/** �s�E�v */
	public TRadioButton rdoGyo;

	/** �S�� */
	public TRadioButton rdoZen;

	/** ���� */
	public TPanel pnlBodyBottom;

	/** �E�v�}�X�^�ꗗ */
	public TTable tbList;

	/** �ꗗ�̃J������` */
	public enum SC {

		/** �E�v�敪 */
		TEK_KBN,

		/** �f�[�^�敪 */
		DATA_KBN,

		/** �E�v�R�[�h */
		TEK_CODE,

		/** �E�v���� */
		TEK_NAME,

		/** �E�v�������� */
		TEK_NAME_K,

		/** �J�n�N���� */
		STR_DATE,

		/** �I���N���� */
		END_DATE,

		/** Entity */
		ENTITY
	}

	@SuppressWarnings("static-access")
	@Override
	public void initComponents() {

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlBodyTop = new TPanel();
		pnlSearchCondition = new TPanel();
		chkOutputTermEnd = new TCheckBox();
		ctrlRemRefRan = new TRemarkReferenceRange();
		pnlTek = new TPanel();
		rdoDnp = new TRadioButton();
		rdoGyo = new TRadioButton();
		rdoZen = new TRadioButton();
		bgBmn = new ButtonGroup();
		pnlBodyBottom = new TPanel();

		tbList = new TTable();
		tbList.addColumn(SC.TEK_KBN, getWord("C00568"), 100, SwingConstants.CENTER);
		tbList.addColumn(SC.DATA_KBN, getWord("C00567"), 100, SwingConstants.CENTER);
		tbList.addColumn(SC.TEK_CODE, getWord("C00564"), 100);
		tbList.addColumn(SC.TEK_NAME, getWord("C00565"), 200);
		tbList.addColumn(SC.TEK_NAME_K, getWord("C00566"), 200);
		tbList.addColumn(SC.STR_DATE, getWord("COP706"), 100, 0);
		tbList.addColumn(SC.END_DATE, getWord("COP707"), 100, 0);
		tbList.addColumn(SC.ENTITY, "", -1, false);
	}

	@Override
	public void allocateComponents() {

		// �V�K�{�^��
		int x = HEADER_LEFT_X;
		btnNew.setSize(25, 110);
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// �����{�^��
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setSize(25, 110);
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// �ҏW�{�^��
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setSize(25, 110);
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);
		tbList.addSpreadSheetSelectChange(btnModify);

		// ���ʃ{�^��
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setSize(25, 110);
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// �폜�{�^��
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setSize(25, 110);
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// �G�N�Z���{�^��
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnExcel.setSize(25, 130);
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// �㕔
		pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 115));
		pnlBodyTop.setMinimumSize(new Dimension(0, 115));
		pnlBodyTop.setPreferredSize(new Dimension(0, 115));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// ���������p�l��
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setLangMessageID("C01060");
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(700, 105);
		pnlBodyTop.add(pnlSearchCondition);

		// �L�����Ԑ؂�\��
		chkOutputTermEnd.setLangMessageID("C11089"); // �L�����Ԑ؂�\��
		chkOutputTermEnd.setLocation(560, 70);
		chkOutputTermEnd.setSize(180, 20);
		pnlSearchCondition.add(chkOutputTermEnd);

		// �E�v�i�͈́j�i�J�n�A�I���j
		ctrlRemRefRan.setLocation(20, 20);
		pnlSearchCondition.add(ctrlRemRefRan);

		// �E�v�敪�p�l��
		pnlTek.setLayout(null);
		pnlTek.setLangMessageID("C00568");
		pnlTek.setLocation(750, 10);
		pnlTek.setSize(170, 105);
		pnlBodyTop.add(pnlTek);

		x = 30;
		// �`�[�E�v
		rdoDnp.setLangMessageID("C00569");
		rdoDnp.setSize(100, 20);
		rdoDnp.setLocation(20, 20);
		rdoDnp.setIndex(0);
		pnlTek.add(rdoDnp);

		x += rdoDnp.getWidth();
		// �s�E�v
		rdoGyo.setLangMessageID("C00119");
		rdoGyo.setSize(100, 20);
		rdoGyo.setLocation(20, 45);
		rdoGyo.setIndex(1);
		pnlTek.add(rdoGyo);

		// �S��
		rdoZen.setLangMessageID("C01212");
		rdoZen.setSize(100, 20);
		rdoZen.setLocation(20, 70);
		rdoZen.setIndex(2);
		rdoZen.setSelected(true);
		pnlTek.add(rdoZen);

		bgBmn = new ButtonGroup();
		bgBmn.add(rdoDnp);
		bgBmn.add(rdoGyo);
		bgBmn.add(rdoZen);

		// ����
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBody.add(tbList, gc);
	}

	@Override
	// Tab����`
	public void setTabIndex() {
		int i = 0;
		ctrlRemRefRan.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		rdoDnp.setTabControlNo(i++);
		rdoGyo.setTabControlNo(i++);
		rdoZen.setTabControlNo(i++);
		tbList.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}