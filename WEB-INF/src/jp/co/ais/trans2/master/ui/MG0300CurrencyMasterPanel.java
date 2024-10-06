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
 * �ʉ݃}�X�^�̎w�����
 */
public class MG0300CurrencyMasterPanel extends TMainPanel {

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

	/** �ʉݔ͈͌��� */
	public TCurrencyReferenceRange currencyRange;

	/** �L�������؂�`�F�b�N�{�b�N�X */
	public TCheckBox chkOutputTermEnd;

	/** �ʉ݃}�X�^�ꗗ */
	public TTable tbl;

	/**
	 * �ꗗ�̃J������`
	 */
	public enum SC {
		/** �ʉ݃R�[�h */
		code,
		/** �ʉݖ��� */
		name,
		/** �ʉݗ��� */
		names,
		/** �ʉ݌������� */
		namek,
		/** ���[�g�W�� */
		rate,
		/** �����_�ȉ����� */
		decimalPoint,
		/** �J�n�N���� */
		dateFrom,
		/** �I���N���� */
		dateTo
	}

	/**
	 * �R���X�g���N�^.
	 */
	public MG0300CurrencyMasterPanel() {
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

		// �ʉ݌����͈�
		currencyRange.setLocation(20, 20);
		pnlSearchCondition.add(currencyRange);

		// �L�����Ԑ؂�\��
		chkOutputTermEnd.setLangMessageID("C11089"); // �L�����Ԑ؂�\��
		chkOutputTermEnd.setLocation(340, 40);
		chkOutputTermEnd.setSize(180, 20);
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
		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlSearchCondition = new TPanel();
		chkOutputTermEnd = new TCheckBox();
		currencyRange = new TCurrencyReferenceRange();

		tbl = new TTable();
		tbl.addColumn(SC.code, "C00665", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.name, "C00880", 320, SwingConstants.LEFT);
		tbl.addColumn(SC.names, "C00881", 250, SwingConstants.LEFT);
		tbl.addColumn(SC.namek, "C00882", 200, SwingConstants.LEFT);
		tbl.addColumn(SC.rate, "C00896", 120, SwingConstants.RIGHT);
		tbl.addColumn(SC.decimalPoint, "C00884", 120, SwingConstants.RIGHT);
		tbl.addColumn(SC.dateFrom, "C00055", 120, SwingConstants.CENTER);
		tbl.addColumn(SC.dateTo, "C00261", 120, SwingConstants.CENTER);
	}

	@Override
	public void setTabIndex() {
		int i = 0;
		currencyRange.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		tbl.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}
}
