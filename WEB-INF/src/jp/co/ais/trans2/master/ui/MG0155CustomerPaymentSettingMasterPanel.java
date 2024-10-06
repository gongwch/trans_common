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
 * �����x�������}�X�^�̉�ʃ��C�A�E�g
 * 
 * @author AIS
 */
public class MG0155CustomerPaymentSettingMasterPanel extends TMainPanel {

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
	 * �ꗗ�̃J������`
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** �����R�[�h */
		customerCode,
		/** ����旪�� */
		customerNames,
		/** ���������R�[�h */
		customerConditionCord,
		/** �U���萔���敪 */
		wireTransferCommissionType,
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
		/** �����ԍ� */
		accountNumber,
		/** �������`�J�i */
		accountKana,
		/** �����ړI�� */
		remittancePurposeName,
		/** �p����s�� */
		englishBankName,
		/** �p���x�X�� */
		englishBranchName,
		/** �p����s�Z�� */
		englishBankAddress,
		/** �萔���敪 */
		commissionType,
		/** �x����s���� */
		paymentBankName,
		/** �x���x�X���� */
		paymentBranchName,
		/** �x����s�Z�� */
		paymentBankAddress,
		/** �o�R��s���� */
		viaBankName,
		/** �o�R�x�X���� */
		viaBranchName,
		/** �o�R��s�Z�� */
		viaBankAddress,
		/** ���l�Z�� */
		recipientAddress,
		/** �J�n�N���� */
		from,
		/** �I���N���� */
		to,
		/** Entity */
		entity
	}

	/**
	 * �R���X�g���N�^
	 */
	public MG0155CustomerPaymentSettingMasterPanel() {
		super();
	}

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
		tbl.addColumn(SC.customerCode, getWord("C00786"), 100);
		tbl.addColumn(SC.customerNames, getWord("C00787"), 150);
		tbl.addColumn(SC.customerConditionCord, getWord("C00788"), 110);
		tbl.addColumn(SC.wireTransferCommissionType, getWord("C01340") + getWord("C02056"), 120, SwingConstants.CENTER);
		tbl.addColumn(SC.paymentConditionDay, getWord("C02057"), 100);
		tbl.addColumn(SC.paymentConditionMonth, getWord("C02058"), 100);
		tbl.addColumn(SC.paymentConditionPayday, getWord("C02059"), 100);
		tbl.addColumn(SC.paymentType, getWord("C00682"), 70, SwingConstants.CENTER);
		tbl.addColumn(SC.paymentMethod, getWord("C00233"), 80, SwingConstants.CENTER);
		tbl.addColumn(SC.bankAccountCode, getWord("C00792"), 120);
		tbl.addColumn(SC.bankCode, getWord("C00779"), 100);
		tbl.addColumn(SC.bankName, getWord("C00781"), 150);
		tbl.addColumn(SC.branchCode, getWord("C02055"), 100);
		tbl.addColumn(SC.branchName, getWord("C02060"), 150);
		tbl.addColumn(SC.depositType, getWord("C01326"), 70, SwingConstants.CENTER);
		tbl.addColumn(SC.accountNumber, getWord("C00794"), 120);
		tbl.addColumn(SC.accountKana, getWord("C01068"), 150);
		tbl.addColumn(SC.remittancePurposeName, getWord("C02037"), 100);
		tbl.addColumn(SC.englishBankName, getWord("C00795"), 150);
		tbl.addColumn(SC.englishBranchName, getWord("C00796"), 150);
		tbl.addColumn(SC.englishBankAddress, getWord("C00797"), 150);
		tbl.addColumn(SC.commissionType, getWord("C01334") + getWord("C10224"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.paymentBankName, getWord("C00799"), 150);
		tbl.addColumn(SC.paymentBranchName, getWord("C00800"), 150);
		tbl.addColumn(SC.paymentBankAddress, getWord("C00801"), 150);
		tbl.addColumn(SC.viaBankName, getWord("C00802"), 150);
		tbl.addColumn(SC.viaBranchName, getWord("C00803"), 150);
		tbl.addColumn(SC.viaBankAddress, getWord("C00804"), 150);
		tbl.addColumn(SC.recipientAddress, getWord("C00805"), 150);
		tbl.addColumn(SC.from, getWord("C00055"), 100, SwingConstants.CENTER);
		tbl.addColumn(SC.to, getWord("C00261"), 100, SwingConstants.CENTER);
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

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlCustomerReferenceRange.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
	}

}
