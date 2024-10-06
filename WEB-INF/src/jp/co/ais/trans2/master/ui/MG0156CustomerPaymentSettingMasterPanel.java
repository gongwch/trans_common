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
 * �����x�������}�X�^(�C�O�p)�̉�ʃ��C�A�E�g
 * 
 * @author AIS
 */
public class MG0156CustomerPaymentSettingMasterPanel extends TMainPanel {

	/**
	 * �ꗗ�̃J������`
	 * 
	 * @author AIS
	 */
	public enum SC {

		/** Entity */
		entity,

		/** �����R�[�h */
		customerCode,

		/** ����旪�� */
		customerNames,

		/** ���������R�[�h */
		customerConditionCode,

		/** �x���������� */
		paymentConditionDay,

		/** �x���������㌎ */
		paymentConditionMonth,

		/** �x�������x���� */
		paymentConditionPayday,

		/** �x���敪 */
		paymentType,

		/** �x�����@ */
		paymentMethod,

		/** �U���U�o��s�����R�[�h */
		bankAccountCode,

		/** ��s�R�[�h */
		bankCode,

		/** ��s���� */
		bankName,

		/** �x�X�R�[�h */
		branchCode,

		/** �x�X���� */
		branchName,

		/** �a����� */
		depositType,

		/** �o���N�`���[�W�敪 */
		bankChargeType,

		/** �U���萔���敪 */
		cashInFee,

		/** �萔���敪 */
		commissionPaymentType,

		/** �����ԍ� */
		accountNumber,

		/** ��sSWIFT�R�[�h */
		bankSwiftCode,

		/** �p����s�� */
		englishBankName,

		/** �p���x�X�� */
		englishBranchName,

		/** ��s���R�[�h */
		bankCountryCode,

		/** �p����s�Z�� */
		englishBankAddress,

		/** �������`�J�i */
		accountKana,

		/** �����ړI�� */
		remittancePurposeName,

		/** �o�R��sSWIFT�R�[�h */
		viaBankSwiftCode,

		/** �o�R��s���� */
		viaBankName,

		/** �o�R�x�X���� */
		viaBranchName,

		/** �o�R��s���R�[�h */
		viaBankCountryCode,

		/** �o�R��s�Z�� */
		viaBankAddress,

		/** ���l���R�[�h */
		recipientCountryCode,

		/** ���l�Z�� */
		recipientAddress,

		/** �J�n�N���� */
		from,

		/** �I���N���� */
		to
	}

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

	/** �����R�[�h�͈͌����p�l�� */
	public TPanel pnlCustomerReferenceRange;

	/** �����R�[�h�͈͌��� */
	public TCustomerReferenceRange ctrlCustomerReferenceRange;

	/** �L�����Ԑ؂��\�����邩 */
	public TCheckBox chkOutputTermEnd;

	/** �ꗗ */
	public TPanel pnlBodyBottom;

	/** �����x�������}�X�^�ꗗ */
	public TTable tbl;

	/**
	 * �R���X�g���N�^
	 */
	public MG0156CustomerPaymentSettingMasterPanel() {
		super();
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#initComponents()
	 */
	@Override
	public void initComponents() {
		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		pnlCustomerReferenceRange = new TPanel();
		ctrlCustomerReferenceRange = new TCustomerReferenceRange();
		chkOutputTermEnd = new TCheckBox();

		tbl = new TTable();
		tbl.addColumn(SC.entity, "", -1, false);
		tbl.addColumn(SC.customerCode, "C00786", 100);
		tbl.addColumn(SC.customerNames, "C00787", 150);
		tbl.addColumn(SC.customerConditionCode, "C00788", 110);
		tbl.addColumn(SC.paymentConditionDay, "C02057", 100, SwingConstants.RIGHT);
		tbl.addColumn(SC.paymentConditionMonth, "C02058", 100, SwingConstants.RIGHT);
		tbl.addColumn(SC.paymentConditionPayday, "C02059", 100, SwingConstants.RIGHT);
		tbl.addColumn(SC.paymentType, "C00682", 70, SwingConstants.CENTER);
		tbl.addColumn(SC.paymentMethod, "C00233", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.bankAccountCode, "C00792", 120);
		tbl.addColumn(SC.bankCode, "C00779", 100);
		tbl.addColumn(SC.bankName, "C00781", 150);
		tbl.addColumn(SC.branchCode, "C02055", 100);
		tbl.addColumn(SC.branchName, "C02060", 150);
		tbl.addColumn(SC.depositType, "C01326", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.bankChargeType, "C11423", 100, SwingConstants.CENTER); // �o���N�`���[�W�敪
		tbl.addColumn(SC.cashInFee, getWord("C02056"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.commissionPaymentType, getWord("C10224"), 80, SwingConstants.CENTER);
		tbl.addColumn(SC.accountNumber, "C00794", 120);
		tbl.addColumn(SC.bankSwiftCode, "C11418", 120); // ��sSWIFT�R�[�h
		tbl.addColumn(SC.englishBankName, "C00795", 150);
		tbl.addColumn(SC.englishBranchName, "C00796", 150);
		tbl.addColumn(SC.bankCountryCode, "C11417", 100); // ��s���R�[�h
		tbl.addColumn(SC.englishBankAddress, "C00797", 150);
		tbl.addColumn(SC.accountKana, "C01068", 150); // �������`�J�i
		tbl.addColumn(SC.remittancePurposeName, "C02037", 100); // �����ړI��
		tbl.addColumn(SC.viaBankSwiftCode, "C11422", 120); // �o�R��sSWIFT�R�[�h
		tbl.addColumn(SC.viaBankName, "C00802", 150);
		tbl.addColumn(SC.viaBranchName, "C00803", 150);
		tbl.addColumn(SC.viaBankCountryCode, "C11421", 100); // �o�R��s���R�[�h
		tbl.addColumn(SC.viaBankAddress, "C00804", 150);
		tbl.addColumn(SC.recipientCountryCode, "C11416", 100); // ���l���R�[�h
		tbl.addColumn(SC.recipientAddress, "C00805", 150);
		tbl.addColumn(SC.from, "C00055", 100, SwingConstants.CENTER);
		tbl.addColumn(SC.to, "C00261", 100, SwingConstants.CENTER);
	}

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#allocateComponents()
	 */
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

		// �����R�[�h�͈̓p�l��
		pnlCustomerReferenceRange.setLayout(null);
		pnlCustomerReferenceRange.setLangMessageID("C01060"); // ��������
		pnlCustomerReferenceRange.setLocation(30, 10);
		pnlCustomerReferenceRange.setSize(600, 75);
		pnlBodyTop.add(pnlCustomerReferenceRange);

		// �����͈�
		ctrlCustomerReferenceRange.setLocation(20, 20);
		pnlCustomerReferenceRange.add(ctrlCustomerReferenceRange);

		// �L�������؂�\��
		chkOutputTermEnd.setLangMessageID("C11089"); // �L�����Ԑ؂�\��
		chkOutputTermEnd.setSize(140, 20);
		chkOutputTermEnd.setLocation(360, 40);
		pnlCustomerReferenceRange.add(chkOutputTermEnd);

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

	/**
	 * @see jp.co.ais.trans2.common.ui.TMainPanel#setTabIndex()
	 */
	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlCustomerReferenceRange.setTabControlNo(i++);
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
