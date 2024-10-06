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
 * 	�Ȗڐ���敪�}�X�^�̎w�����
 */
public class MG0431AutomaticJournalizingItemMasterPanel extends TMainPanel {

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

	/** ���������p�l�� */
	public TPanel pnlSearchCondition;

	/** �Ȗڐ���敪�͈͌��� */
	public TAutomaticJournalizingItemReferenceRange AutoJnlItemRange;
	
	/** �Ȗڐ���敪�}�X�^�ꗗ */
	public TTable tbl;

	/**
	 * �ꗗ�̃J������`
	 */
	public enum SC {
		/** �Ȗڐ���敪 */
		autojnlitemkbn, 
		/** �Ȗڐ���敪���� */
		autojnlitemname, 
		/** ����R�[�h */
		depcode, 
		/** ���喼�� */
		depname, 
		/** �ȖڃR�[�h */
		kmkcode, 
		/** �Ȗږ��� */
		kmkname, 
		/** �⏕�R�[�h */
		hkmcode, 
		/** �⏕���� */
		hkmname,
		/** �ȖڃR�[�h */
		ukmcode, 
		/** �Ȗږ��� */
		ukmname
	}
	
	/**
	 * �R���X�g���N�^.
	 */
	public MG0431AutomaticJournalizingItemMasterPanel() {
		super();
	}

	@Override
	public void allocateComponents() {

		// �V�K�{�^��
		int x = HEADER_LEFT_X;
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setSize(25, 110);
		btnNew.setLocation(x, HEADER_Y);
		pnlHeader.add(btnNew);

		// �����{�^��
		x = x + btnNew.getWidth() + HEADER_MARGIN_X;
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 110);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// �ҏW�{�^��
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnModify.setLangMessageID("C00481");
		btnModify.setShortcutKey(KeyEvent.VK_F3);
		btnModify.setSize(25, 110);
		btnModify.setLocation(x, HEADER_Y);
		pnlHeader.add(btnModify);
		tbl.addSpreadSheetSelectChange(btnModify);

		// ���ʃ{�^��
		x = x + btnModify.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 110);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// �폜�{�^��
		x = x + btnCopy.getWidth() + HEADER_MARGIN_X;
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setSize(25, 110);
		btnDelete.setLocation(x, HEADER_Y);
		pnlHeader.add(btnDelete);

		// �G�N�Z���{�^��
		x = x + btnDelete.getWidth() + HEADER_MARGIN_X;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// �㕔
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(null);
		pnlBodyTop.setMaximumSize(new Dimension(0, 85));
		pnlBodyTop.setMinimumSize(new Dimension(0, 85));
		pnlBodyTop.setPreferredSize(new Dimension(0, 85));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// ���������p�l��
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setLangMessageID("C01060"); // ��������
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(480, 75);
		pnlBodyTop.add(pnlSearchCondition);

		// �Ȗڐ���敪�����͈�
		AutoJnlItemRange.setLocation(20, 20);
		pnlSearchCondition.add(AutoJnlItemRange);

		// ����
		TPanel pnlBodyButtom = new TPanel();
		pnlBodyButtom.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyButtom, gc);

		// �ꗗ
		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBodyButtom.add(tbl,gc);

	}

	@Override
	public void initComponents() {
		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlSearchCondition = new TPanel();
		AutoJnlItemRange = new TAutomaticJournalizingItemReferenceRange();

		tbl = new TTable();
		tbl.addColumn(SC.autojnlitemkbn, "C01008", 100, SwingConstants.LEFT);
		tbl.addColumn(SC.autojnlitemname, "C11885", 250, SwingConstants.LEFT);
		tbl.addColumn(SC.depcode, "C00571", 120, SwingConstants.LEFT);
		tbl.addColumn(SC.depname, "C10389", 250, SwingConstants.LEFT);
		tbl.addColumn(SC.kmkcode, "C00572", 120, SwingConstants.LEFT);
		tbl.addColumn(SC.kmkname, "C00700", 150, SwingConstants.LEFT);
		tbl.addColumn(SC.hkmcode, "C00602", 120, SwingConstants.LEFT);
		tbl.addColumn(SC.hkmname, "C00701", 150, SwingConstants.LEFT);
		tbl.addColumn(SC.ukmcode, "C00603", 120, SwingConstants.LEFT);
		tbl.addColumn(SC.ukmname, "C00702", 150, SwingConstants.LEFT);
	}

	@Override
	public void setTabIndex() {
		int i = 0;
		AutoJnlItemRange.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}
