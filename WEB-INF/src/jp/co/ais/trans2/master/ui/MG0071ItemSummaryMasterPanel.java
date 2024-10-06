package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTabbedPane;
import jp.co.ais.trans2.common.gui.dnd.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * �ȖڏW�v�}�X�^�̎w����ʃ��C�A�E�g
 * 
 * @author AIS
 */
public class MG0071ItemSummaryMasterPanel extends TMainPanel {

	/** �R���g���[���N���X */
	public MG0071ItemSummaryMasterPanelCtrl ctrl;

	/** �����{�^�� */
	public TImageButton btnSearch;

	/** ���ʃ{�^�� */
	public TImageButton btnCopy;

	/** �G�N�Z���{�^�� */
	public TImageButton btnExcel;

	/** �m�� */
	public TImageButton btnSettle;

	/** �V�K�쐬�p�l�� */
	public TPanel pnlNew;

	/** �ꗗ�p�l�� */
	public TPanel pnlTable;

	/** �ꗗ */
	public TDnDTable dndTable;

	/** ���������p�l�� */
	public TPanel pnlSearchCondition;

	/** �Ȗڑ̌n */
	public TItemOrganizationReference ctrlItemOrganizationReference;

	/** �L�������؂�`�F�b�N�{�b�N�X */
	public TCheckBox chkOutputTermEnd;

	/** �R�����g�p */
	public TLabel ctrlComment;

	/** ���X�g�^�u */
	public TTabbedPane listTab;

	/** �c���[ */
	public TDnDTree tree;

	/** �c���[�p�l�� */
	public JScrollPane sp;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param panelCtrl �R���g���[��
	 */
	public MG0071ItemSummaryMasterPanel(MG0071ItemSummaryMasterPanelCtrl panelCtrl) {
		ctrl = panelCtrl;
	}

	/**
	 * �ꗗ�̃J������`
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** �ȖڃR�[�h */
		code,
		/** �Ȗڗ��� */
		name,
		/** �W�v�敪 */
		sumkbn,
		/** �Ȗڎ�� */
		kmkshu,
		/** �ݎ؋敪 */
		dckbn,
		/** �ȖڃG���e�B�e�B */
		bean,
	}

	@Override
	public void initComponents() {
		btnSearch = new TImageButton(IconType.SEARCH);
		btnCopy = new TImageButton(IconType.COPY);
		btnExcel = new TImageButton(IconType.EXCEL);
		btnSettle = new TImageButton(IconType.SETTLE);
		pnlNew = new TPanel();
		pnlSearchCondition = new TPanel();
		ctrlItemOrganizationReference = new TItemOrganizationReference();
		chkOutputTermEnd = new TCheckBox();
		ctrlComment = new TLabel();
		listTab = new TTabbedPane();
		tree = new TDnDTree();
		sp = new JScrollPane();
		pnlTable = new TPanel();

		// TTable�̋@�\�g���N���X
		dndTable = new TDnDTable();
		dndTable.addColumn(SC.code, "C00572", 70); // �ȖڃR�[�h
		dndTable.addColumn(SC.name, "C00730", 155); // �Ȗڗ���
		dndTable.addColumn(SC.sumkbn, "C01148", 60, SwingConstants.CENTER); // �W�v�敪
		dndTable.addColumn(SC.kmkshu, "C01007", 60, SwingConstants.CENTER); // �Ȗڎ��
		dndTable.addColumn(SC.dckbn, "C01226", 60, SwingConstants.CENTER); // �ݎ؋敪
		dndTable.addColumn(SC.bean, "", 0);
	}

	@Override
	public void allocateComponents() {

		int x = 5;

		// �����{�^��
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 130);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// ���ʃ{�^��
		x = x + btnSearch.getWidth() + HEADER_MARGIN_X;
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setSize(25, 130);
		btnCopy.setLocation(x, HEADER_Y);
		pnlHeader.add(btnCopy);

		// �G�N�Z���{�^��
		x = btnCopy.getX() + btnCopy.getWidth() + 5;
		btnExcel.setLangMessageID("C01545");
		btnExcel.setShortcutKey(KeyEvent.VK_F6);
		btnExcel.setSize(25, 130);
		btnExcel.setLocation(x, HEADER_Y);
		pnlHeader.add(btnExcel);

		// �m��{�^��
		x = btnExcel.getX() + btnExcel.getWidth() + 5;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 130);
		btnSettle.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �㕔
		TPanel pnlBodyTop = new TPanel();
		pnlBodyTop.setLayout(new GridBagLayout());
		gc = new GridBagConstraints();
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		pnlBody.add(pnlBodyTop, gc);

		// �V�K�쐬�p�l��
		pnlNew.setLayout(null);

		TGuiUtil.setComponentSize(pnlNew, 0, 80);
		gc = new GridBagConstraints();
		gc.weightx = 1.0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 2;

		pnlBodyTop.add(pnlNew, gc);

		// ���������p�l��
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setLangMessageID("C01060");
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(450, 55);
		pnlNew.add(pnlSearchCondition);

		// �Ȗڑ̌n
		ctrlItemOrganizationReference.setLocation(10, 5);
		TGuiUtil.setComponentSize(ctrlItemOrganizationReference, 300, 50);
		pnlSearchCondition.add(ctrlItemOrganizationReference);

		// �L�����Ԑ؂�\��
		chkOutputTermEnd.setLangMessageID("C11089");
		chkOutputTermEnd.setLocation(0, 0);
		chkOutputTermEnd.setSize(0, 0);
		pnlSearchCondition.add(chkOutputTermEnd);

		// �R�����g
		ctrlComment.setSize(450, 15);
		ctrlComment.setLangMessageID("C11607");
		ctrlComment.setForeground(Color.blue);
		ctrlComment.setLocation(180, 65);
		pnlNew.add(ctrlComment);

		// ���X�g�^�u
		listTab.switchMode();
		listTab.setOpaque(false);

		TGuiUtil.setComponentSize(sp, 270, 400);
		sp.getViewport().add(tree, null);
		listTab.addTab(getWord("C02421"), sp);
		listTab.setSelectedIndex(0);

		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(0, 30, 10, 30);

		pnlBodyTop.add(listTab, gc);

		// �ꗗ
		dndTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		TGuiUtil.setComponentSize(pnlTable, 274, 400);
		pnlTable.setLayout(new BoxLayout(pnlTable, BoxLayout.X_AXIS));

		pnlTable.add(dndTable);

		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.gridx = 1;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);

		pnlBodyTop.add(pnlTable, gc);
	}

	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlItemOrganizationReference.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		dndTable.setTabControlNo(i++);
	}

}
