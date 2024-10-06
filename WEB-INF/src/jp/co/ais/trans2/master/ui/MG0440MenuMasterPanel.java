package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.dnd.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * ���j���[�\���}�X�^�̎w����ʃ��C�A�E�g
 * 
 * @author AIS
 */
public class MG0440MenuMasterPanel extends TMainPanel {

	/** �R���g���[���N���X */
	public MG0440MenuMasterPanelCtrl ctrl;

	/** ���� */
	public TImageButton btnSearch;

	/** �m�� */
	public TImageButton btnSettle;

	/** �^�u�V�K�쐬�p�l�� */
	public TPanel pnlTabNew;

	/** �^�u�V�K�쐬 */
	public TButton btnNewTab;

	/** ���j���[�^�u */
	public TLeftColorTabbedPane menuTab;

	/** �ꗗ�p�l�� */
	public TPanel pnlTable;

	/** �ꗗ */
	public TDnDTable dndTable;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param panelCtrl �R���g���[��
	 */
	public MG0440MenuMasterPanel(MG0440MenuMasterPanelCtrl panelCtrl) {
		ctrl = panelCtrl;
	}

	/**
	 * �ꗗ�̃J������`
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** �v���O�����R�[�h */
		code,
		/** �v���O�������� */
		name,
		/** �v���O�����G���e�B�e�B */
		bean,
	}

	@Override
	public void initComponents() {
		btnSearch = new TImageButton(IconType.SEARCH);
		btnSettle = new TImageButton(IconType.SETTLE);
		pnlTabNew = new TPanel();
		btnNewTab = new TButton();
		menuTab = new TLeftColorTabbedPane();
		pnlTable = new TPanel();

		// TTable�̋@�\�g���N���X
		dndTable = new TDnDTable();
		dndTable.addColumn(SC.code, "C11403", 120); // ���j���[�R�[�h
		dndTable.addColumn(SC.name, "C00820", 260); // �v���O��������
		dndTable.addColumn(SC.bean, "", 0);
	}

	@Override
	public void allocateComponents() {

		int x = HEADER_LEFT_X;

		// �����{�^��
		btnSearch.setLangMessageID("C00155"); // ����
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setSize(25, 130);
		btnSearch.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSearch);

		// �m��{�^��
		btnSettle.setLangMessageID("C01019"); // �m��
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 130);
		btnSettle.setLocation(x + btnSearch.getWidth() + 5, HEADER_Y);
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

		// �^�u�V�K�쐬�p�l��
		pnlTabNew.setLayout(null);

		TGuiUtil.setComponentSize(pnlTabNew, 200, 40);
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 0;

		pnlBodyTop.add(pnlTabNew, gc);

		// �^�u�V�K�쐬
		btnNewTab.setLangMessageID("C11176"); // �^�u�ǉ�
		btnNewTab.setSize(25, 100);
		btnNewTab.setLocation(30, 10);
		pnlTabNew.add(btnNewTab);

		// ���j���[�^�u
		menuTab.switchMode();
		menuTab.setOpaque(false);

		gc = new GridBagConstraints();
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 30, 10, 30);

		pnlBodyTop.add(menuTab, gc);

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
		btnSearch.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnNewTab.setTabControlNo(i++);
		dndTable.setTabControlNo(i++);
	}

}
