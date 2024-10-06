package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * ����}�X�^�̎w�����
 */
public class MG0060DepartmentMasterPanel extends TMainPanel {

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

	/** ����͈͌��� */
	public TDepartmentReferenceRange departmentRange;

	/** �L�������؂�`�F�b�N�{�b�N�X */
	public TCheckBox chkOutputTermEnd;

	/** ����}�X�^�ꗗ */
	public TTable tbl;

	/**
	 * �ꗗ�̃J������`
	 */
	public enum SC {
		/** ����R�[�h */
		code,
		/** ���喼�� */
		name,
		/** ���嗪�� */
		names,
		/** ���匟������ */
		namek,
		/** �l����1 */
		men1,
		/** �l����2 */
		men2,
		/** ���ʐ� */
		area,
		/** ����敪 */
		kbn,
		/** �J�n�N���� */
		dateFrom,
		/** �I���N���� */
		dateTo,
		/** �A�g1�R�[�h */
		ifCode1,
		/** �A�g1���� */
		ifName1,
		/** �A�g2�R�[�h */
		ifCode2,
		/** �A�g2���� */
		ifName2,
		/** �A�g3�R�[�h */
		ifCode3,
		/** �A�g3���� */
		ifName3
	}

	/**
	 * �R���X�g���N�^.
	 */
	public MG0060DepartmentMasterPanel() {
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

		// ���匟���͈�
		departmentRange.setLocation(20, 20);
		pnlSearchCondition.add(departmentRange);

		// �L�����Ԑ؂�\��
		chkOutputTermEnd.setLangMessageID("C11089"); // �L�����Ԑ؂�\��
		chkOutputTermEnd.setLocation(340, 40);
		chkOutputTermEnd.setSize(140, 20);
		pnlSearchCondition.add(chkOutputTermEnd);

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
		pnlBodyButtom.add(tbl, gc);

	}

	@Override
	public void initComponents() {

		boolean isUseIf1 = false;
		try {
			String if1Code = ClientConfig.getProperty("trans.MG0060.if.code.1.length");
			String if1Name = ClientConfig.getProperty("trans.MG0060.if.name.1.length");

			isUseIf1 = DecimalUtil.toInt(if1Code) > 0 && DecimalUtil.toInt(if1Name) > 0;

		} catch (Exception e) {
			// �����Ȃ�
		}
		boolean isUseIf2 = false;
		try {
			String if2Code = ClientConfig.getProperty("trans.MG0060.if.code.2.length");
			String if2Name = ClientConfig.getProperty("trans.MG0060.if.name.2.length");

			isUseIf2 = DecimalUtil.toInt(if2Code) > 0 && DecimalUtil.toInt(if2Name) > 0;

		} catch (Exception e) {
			// �����Ȃ�
		}
		boolean isUseIf3 = false;
		try {
			String if3Code = ClientConfig.getProperty("trans.MG0060.if.code.3.length");
			String if3Name = ClientConfig.getProperty("trans.MG0060.if.name.3.length");

			isUseIf3 = DecimalUtil.toInt(if3Code) > 0 && DecimalUtil.toInt(if3Name) > 0;

		} catch (Exception e) {
			// �����Ȃ�
		}

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlSearchCondition = new TPanel();
		chkOutputTermEnd = new TCheckBox();
		departmentRange = new TDepartmentReferenceRange();

		tbl = new TTable();
		tbl.addColumn(SC.code, "C00698", 100, SwingConstants.LEFT);
		tbl.addColumn(SC.name, "C00723", 450, SwingConstants.LEFT);
		tbl.addColumn(SC.names, "C00724", 250, SwingConstants.LEFT);
		tbl.addColumn(SC.namek, "C00725", 450, SwingConstants.LEFT);
		tbl.addColumn(SC.men1, "C00726", 120, SwingConstants.RIGHT);
		tbl.addColumn(SC.men2, "C00727", 120, SwingConstants.RIGHT);
		tbl.addColumn(SC.area, "C00728", 120, SwingConstants.RIGHT);
		tbl.addColumn(SC.kbn, "C01303", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.dateFrom, "C00055", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.dateTo, "C00261", 100, SwingConstants.CENTER);

		tbl.addColumn(SC.ifCode1, "C12060", isUseIf1 ? 100 : -1, SwingConstants.LEFT);
		tbl.addColumn(SC.ifName1, "C12061", isUseIf1 ? 450 : -1, SwingConstants.LEFT);
		tbl.addColumn(SC.ifCode2, "C12062", isUseIf2 ? 100 : -1, SwingConstants.LEFT);
		tbl.addColumn(SC.ifName2, "C12063", isUseIf2 ? 450 : -1, SwingConstants.LEFT);
		tbl.addColumn(SC.ifCode3, "C12064", isUseIf3 ? 100 : -1, SwingConstants.LEFT);
		tbl.addColumn(SC.ifName3, "C12065", isUseIf3 ? 450 : -1, SwingConstants.LEFT);
	}

	@Override
	public void setTabIndex() {
		int i = 0;
		departmentRange.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}
