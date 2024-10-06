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
 * �����}�X�^ �w�����
 */
public class MG0150CustomerMasterPanel extends TMainPanel {

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

	/** �����͈͌��� */
	public TCustomerReferenceRange TCustomerReferenceRange;

	/** �L�����Ԑ؂��\�����邩 */
	public TCheckBox chkOutputTermEnd;

	/** �ꗗ */
	public TTable tbl;

	/** �S���Ґݒ�{�^�� */
	public TImageButton btnUsr;

	/** �S���҈ꗗ�o�̓{�^�� */
	public TImageButton btnUsrOut;

	/**
	 * �ꗗ�̃J������`
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** �����R�[�h */
		triCode,
		/** ����於�� */
		triName,
		/** ����旪�� */
		triNames,
		/** ����挟������ */
		triNamek,
		/** �����敪 */
		triKbn,
		/** �O���[�v��Ћ敪 */
		groupCompanyKbn,
		/** ���Ə����� */
		officeName,
		/** �X�֔ԍ� */
		postNum,
		/** ���R�[�h */
		countryCode,
		/** ���� */
		countryName,
		/** �Z���J�i */
		addressKana,
		/** �Z���P */
		address1,
		/** �Z���Q */
		address2,
		/** EMail Address */
		emailaddress,
		/** �����h�� */
		cusTitle,
		/** �S���ҏ������� */
		departmentInCharge,
		/** �S���� */
		chargeName,
		/** �S���Ҍh�� */
		chargeTitle,
		/** �d�b�ԍ� */
		telNum,
		/** FAX�ԍ� */
		faxNum,
		/** �W�v�����R�[�h */
		sumAiteCode,
		/** �W�v����於�� */
		sumAiteName,
		/** �d���敪 */
		siireKbn,
		/** ���Ӑ�敪 */
		tokuiKbn,
		/** �����������ߓ� */
		nyukinConditionSimeDate,
		/** �����������ߌ㌎ */
		nyukinConditionSimeMonth,
		/** �������������� */
		nyukinConditionNyukinDate,
		/** ������s�����R�[�h */
		nyukinBankKouzaCode,
		/** ������s�������� */
		nyukinBankKouzaName,
		/** �U���˗��l�� */
		hurikomiIraiName,
		/** �����萔���敪 */
		nyukinTesuryoKbn,
		/** ��K�i���������s���Ǝ� */
		noInvReg,
		/** ����Ŗ��� */
		taxName,
		/** �K�i���������s���ƎҔԍ� */
		invRegNo,
		/** �J�n�N���� */
		dateFrom,
		/** �I���N���� */
		dateTo
	}

	@Override
	public void initComponents() {

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnModify = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnExcel = new TImageButton(IconType.EXCEL);
		btnUsr = new TImageButton();
		btnUsrOut = new TImageButton();
		pnlSearchCondition = new TPanel();
		TCustomerReferenceRange = new TCustomerReferenceRange();
		chkOutputTermEnd = new TCheckBox();

		tbl = new TTable();
		tbl.addColumn(SC.triCode, getWord("C00786"), 100); // �����R�[�h
		tbl.addColumn(SC.triName, getWord("C00830"), 200); // ����於��
		tbl.addColumn(SC.triNames, getWord("C00787"), 200); // ����旪��
		tbl.addColumn(SC.triNamek, getWord("C00836"), 200); // ����挟������
		int divWidth = !MG0150CustomerMasterPanelCtrl.isNoVisibleTriDivison ? 200 : -1;
		tbl.addColumn(SC.triKbn, getWord("C03344"), divWidth); // �����敪
		int groupWidth = MG0150CustomerMasterPanelCtrl.isUseTRI_TYPE_GRP_FLG ? 100 : -1;
		tbl.addColumn(SC.groupCompanyKbn, getWord("C04294"), groupWidth, SwingConstants.CENTER); // �O���[�v��Ћ敪
		tbl.addColumn(SC.officeName, getWord("C02487"), 200); // ���Ə���
		tbl.addColumn(SC.postNum, getWord("C00527"), 100); // �X�֔ԍ�
		tbl.addColumn(SC.countryCode, getWord("C11889"), 50);// ���R�[�h
		tbl.addColumn(SC.countryName, getWord("C11890"), 80);// ����
		tbl.addColumn(SC.addressKana, getWord("C01152"), 200); // �Z���J�i
		tbl.addColumn(SC.address1, getWord("C01150"), 200); // �Z��1
		tbl.addColumn(SC.address2, getWord("C01151"), 200); // �Z��2
		tbl.addColumn(SC.emailaddress, getWord("CBL401"), 150); // EMail Address

		int titleWidth = MG0150CustomerMasterPanelCtrl.isUseCustomerManagementSet ? 100 : -1;
		tbl.addColumn(SC.cusTitle, getWord("C12184"), titleWidth, SwingConstants.CENTER); // �����h��
		tbl.addColumn(SC.chargeTitle, getWord("C12187"), titleWidth, SwingConstants.CENTER); // �S���Ҍh��
		int nameWidth = MG0150CustomerMasterPanelCtrl.isUseCustomerManagementSet ? 200 : -1;

		tbl.addColumn(SC.departmentInCharge, getWord("C12185"), nameWidth); // �S���ҏ�������
		tbl.addColumn(SC.chargeName, getWord("C12186"), nameWidth); // �S���Җ���
		tbl.addColumn(SC.telNum, getWord("C00393"), 100); // �d�b�ԍ�
		tbl.addColumn(SC.faxNum, getWord("C00690"), 100); // FAX�ԍ�
		tbl.addColumn(SC.sumAiteCode, getWord("C00871"), 100); // �W�v�����R�[�h
		tbl.addColumn(SC.sumAiteName, getWord("C11085"), 100); // �W�v����於��
		tbl.addColumn(SC.siireKbn, getWord("C01089"), 100, SwingConstants.CENTER); // �d����敪
		tbl.addColumn(SC.tokuiKbn, getWord("C01261"), 100, SwingConstants.CENTER); // ���Ӑ�敪
		tbl.addColumn(SC.nyukinConditionSimeDate, getWord("C02038"), 100); // �����������ߓ�
		tbl.addColumn(SC.nyukinConditionSimeMonth, getWord("C02039"), 100); // �����������ߌ㌎
		tbl.addColumn(SC.nyukinConditionNyukinDate, getWord("C00870"), 100); // ��������������
		tbl.addColumn(SC.nyukinBankKouzaCode, getWord("C02040"), 200); // ������s�����R�[�h
		tbl.addColumn(SC.nyukinBankKouzaName, getWord("C11087"), 200); // ������s��������
		tbl.addColumn(SC.hurikomiIraiName, getWord("C10133"), 200); // �U���˗��l��
		tbl.addColumn(SC.nyukinTesuryoKbn, getWord("C02042"), 100, SwingConstants.CENTER); // �����萔���敪

		int invoiceWidth = MG0150CustomerMasterPanelCtrl.isInvoice ? 150 : -1;
		int invoiceNoWidth = MG0150CustomerMasterPanelCtrl.isInvoice ? 180 : -1;
		tbl.addColumn(SC.noInvReg, getWord("C12197"), invoiceWidth, SwingConstants.CENTER); // ��K�i���������s���Ǝ�
		tbl.addColumn(SC.taxName, getWord("C00286"), invoiceWidth); // �����
		tbl.addColumn(SC.invRegNo, getWord("C12171"), invoiceNoWidth); // �K�i���������s���Ǝғo�^�ԍ�

		tbl.addColumn(SC.dateFrom, getWord("C00055"), 100, SwingConstants.CENTER); // �J�n�N����
		tbl.addColumn(SC.dateTo, getWord("C00261"), 100, SwingConstants.CENTER); // �I���N����

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

		// �S���Ґݒ�{�^��
		x = x + btnExcel.getWidth() + HEADER_MARGIN_X;
		btnUsr.setLangMessageID(getWord("C00363") + getWord("C00319"));
		btnUsr.setShortcutKey(KeyEvent.VK_F7);
		btnUsr.setSize(25, 130);
		btnUsr.setLocation(x, HEADER_Y);
		btnUsr.setVisible(MG0150CustomerMasterPanelCtrl.isVisibleTriUsrSetting);
		pnlHeader.add(btnUsr);

		// �S���҈ꗗ�o�̓{�^��
		x = x + btnUsr.getWidth() + HEADER_MARGIN_X;
		btnUsrOut.setLangMessageID(getWord("C00363") + getWord("C00010") + getWord("C00266"));
		btnUsrOut.setShortcutKey(KeyEvent.VK_F8);
		btnUsrOut.setSize(25, 130);
		btnUsrOut.setLocation(x, HEADER_Y);
		btnUsrOut.setVisible(MG0150CustomerMasterPanelCtrl.isVisibleTriUsrSetting);
		pnlHeader.add(btnUsrOut);

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
		pnlSearchCondition.setBorder(TBorderFactory.createTitledBorder(getWord("C01060")));
		pnlSearchCondition.setLocation(30, 10);
		pnlSearchCondition.setSize(600, 75);
		pnlBodyTop.add(pnlSearchCondition);

		// ����挟���͈�
		TCustomerReferenceRange.setLocation(20, 20);
		pnlSearchCondition.add(TCustomerReferenceRange);

		// �L�����Ԑ؂�\��
		chkOutputTermEnd.setLangMessageID("C11089");
		chkOutputTermEnd.setSize(140, 20);
		chkOutputTermEnd.setLocation(360, 40);
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
	public void setTabIndex() {
		int i = 1;
		TCustomerReferenceRange.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);

		tbl.setTabControlNo(i++);
		btnNew.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnModify.setTabControlNo(i++);
		btnCopy.setTabControlNo(i++);
		btnDelete.setTabControlNo(i++);
		btnExcel.setTabControlNo(i++);
		btnUsr.setTabControlNo(i++);
		btnUsrOut.setTabControlNo(i++);
	}
}
