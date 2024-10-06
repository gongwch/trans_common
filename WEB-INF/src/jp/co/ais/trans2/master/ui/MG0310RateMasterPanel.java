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
 * �ʉ݃��[�g�}�X�^�̎w����ʃ��C�A�E�g
 * 
 * @author AIS
 */
public class MG0310RateMasterPanel extends TMainPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -6170021596968496269L;

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

	/** �ʉݔ͈͌����p�l�� */
	public TPanel pnlCurrencyReferenceRange;

	/** �ʉݔ͈͌��� */
	public TCurrencyReferenceRange ctrlCurrencyReferenceRange;

	/** ���[�g�敪�p�l�� */
	public TPanel pnlRateType;

	/** �В背�[�g */
	public TCheckBox chkCompanyRate;

	/** ���Z�����[�g */
	public TCheckBox chkSettlementRate;

	/** �В背�[�g�i�@�\�ʉ݁j */
	public TCheckBox chkFuncCompanyRate;

	/** ���Z�����[�g�i�@�\�ʉ݁j */
	public TCheckBox chkFuncSettlementRate;

	/** �K�p�J�n���t�J�n */
	public TLabelPopupCalendar dateFrom;

	/** �K�p�J�n���t�I�� */
	public TLabelPopupCalendar dateTo;

	/** �ꗗ */
	public TPanel pnlBodyBottom;

	/** ���[�g�}�X�^�ꗗ */
	public TTable tbl;

	/**
	 * �ꗗ�̃J������`
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** ���[�g�敪 */
		type,
		/** �ʉ݃R�[�h */
		currencyCode,
		/** �K�p�J�n���t */
		termFrom,
		/** ���[�g */
		rate,
		/** Entity */
		entity
	}

	/**
	 * 
	 * 
	 */
	public MG0310RateMasterPanel() {
		//
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
		pnlSearchCondition = new TPanel();
		pnlCurrencyReferenceRange = new TPanel();
		ctrlCurrencyReferenceRange = new TCurrencyReferenceRange();
		pnlRateType = new TPanel();
		chkCompanyRate = new TCheckBox();
		chkSettlementRate = new TCheckBox();
		chkFuncCompanyRate = new TCheckBox();
		chkFuncSettlementRate = new TCheckBox();
		dateFrom = new TLabelPopupCalendar();
		dateTo = new TLabelPopupCalendar();
		pnlBodyBottom = new TPanel();
		tbl = new TTable();
		tbl.addColumn(SC.type, getWord("C00883"), 150);// ���[�g�敪
		tbl.addColumn(SC.currencyCode, getWord("C00665"), 100, SwingConstants.CENTER);// �ʉ݃R�[�h
		tbl.addColumn(SC.termFrom, getWord("C02046"), 100, SwingConstants.CENTER);// �K�p�J�n���t
		tbl.addColumn(SC.rate, getWord("C00556"), 100, SwingConstants.RIGHT);// ���[�g
		tbl.addColumn(SC.entity, "", -1, false);
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
		pnlBodyTop.setMinimumSize(new Dimension(0, 145));
		pnlBodyTop.setPreferredSize(new Dimension(0, 145));
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		pnlBody.add(pnlBodyTop, gc);

		// ���������p�l��
		pnlSearchCondition.setLayout(null);
		pnlSearchCondition.setBorder(TBorderFactory.createTitledBorder(getWord("C01060")));// ��������
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(720, 135);
		pnlBodyTop.add(pnlSearchCondition);

		// �ʉݔ͈̓p�l��
		pnlCurrencyReferenceRange.setLayout(null);
		pnlCurrencyReferenceRange.setBorder(TBorderFactory.createTitledBorder(getWord("C00371")));// �ʉ�
		pnlCurrencyReferenceRange.setSize(350, 75);
		pnlCurrencyReferenceRange.setLocation(20, 20);
		pnlSearchCondition.add(pnlCurrencyReferenceRange);

		// �ʉݔ͈�
		ctrlCurrencyReferenceRange.setLocation(20, 20);
		pnlCurrencyReferenceRange.add(ctrlCurrencyReferenceRange);

		// ���[�g�敪�p�l��
		pnlRateType.setLayout(null);
		pnlRateType.setBorder(TBorderFactory.createTitledBorder(getWord("C00883")));// ���[�g�敪
		pnlRateType.setSize(320, 75);
		pnlRateType.setLocation(375, 20);
		pnlSearchCondition.add(pnlRateType);

		// �В背�[�g
		chkCompanyRate.setSize(110, 20);
		chkCompanyRate.setLocation(20, 20);
		chkCompanyRate.setLangMessageID("C11167");
		pnlRateType.add(chkCompanyRate);

		// ���Z�����[�g
		chkSettlementRate.setSize(110, 20);
		chkSettlementRate.setLocation(20, 45);
		chkSettlementRate.setLangMessageID("C02820");
		pnlRateType.add(chkSettlementRate);

		// �В背�[�g�i�@�\�ʉ݁j
		chkFuncCompanyRate.setSize(180, 20);
		chkFuncCompanyRate.setLocation(130, 20);
		chkFuncCompanyRate.setLangMessageID("C11216");
		pnlRateType.add(chkFuncCompanyRate);

		// ���Z�����[�g�i�@�\�ʉ݁j
		chkFuncSettlementRate.setSize(180, 20);
		chkFuncSettlementRate.setLocation(130, 45);
		chkFuncSettlementRate.setLangMessageID("C11217");
		pnlRateType.add(chkFuncSettlementRate);

		// �K�p�J�n���t�J�n
		dateFrom.setLabelSize(115);
		dateFrom.setLabelText(getWord("C02046"));// �K�p�J�n���t
		dateFrom.setSize(new Dimension(dateFrom.getCalendarSize() + 120, 20));
		dateFrom.setLocation(5, 100);
		pnlSearchCondition.add(dateFrom);

		// �K�p�J�n���t�I��
		dateTo.setLabelSize(40);
		dateTo.setLabelText(getWord("C01333"));// �`
		dateTo.setSize(new Dimension(dateFrom.getCalendarSize() + 45, 20));
		dateTo.setLocation(180, 100);
		pnlSearchCondition.add(dateTo);

		// ����
		gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.gridy = 1;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.insets = new Insets(10, 30, 10, 30);
		pnlBody.add(tbl, gc);

	}

	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlCurrencyReferenceRange.setTabControlNo(i++);
		chkCompanyRate.setTabControlNo(i++);
		chkSettlementRate.setTabControlNo(i++);
		chkFuncCompanyRate.setTabControlNo(i++);
		chkFuncSettlementRate.setTabControlNo(i++);
		dateFrom.setTabControlNo(i++);
		dateTo.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}

}
