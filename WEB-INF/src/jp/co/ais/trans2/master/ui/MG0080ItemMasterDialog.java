package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.model.ui.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �Ȗڃ}�X�^�̕ҏW���
 */
public class MG0080ItemMasterDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �ȖڃR�[�h */
	public TLabelField ctrlItemCode;

	/** �Ȗږ��� */
	public TLabelField ctrlItemName;

	/** �Ȗڗ��� */
	public TLabelField ctrlItemNames;

	/** �Ȗڌ������� */
	public TLabelField ctrlItemNamek;

	/** �Ȗڎ�� */
	public TLabelComboBox comKmkshu;

	/** �⏕�敪 */
	public TLabelComboBox comHjokbn;

	/** ���� */
	public TRadioButton rdoImput;

	/** �W�v */
	public TRadioButton rdoSum;

	/** ���o */
	public TRadioButton rdoTitle;

	/** �W�v�敪 ButtonGroup */
	public ButtonGroup bgSum;

	/** �W�v�敪 */
	public TPanel pnlSumkbn;

	/** �ݕ� */
	public TRadioButton rdoCredit;

	/** �ؕ� */
	public TRadioButton rdoDebit;

	/** �ݎ؋敪 ButtonGroup */
	public ButtonGroup bgDc;

	/** �ݎ؋敪 */
	public TPanel pnlDckbn;

	/** �`�F�b�N */
	public TItemStatusUnit chk;

	/** �Œ蕔�� */
	public TDepartmentReference ctrlFixDepartment;

	/** ����� */
	public TTaxReference ctrlTax;

	/** GL�Ȗڐ���敪 */
	public TLabelComboBox cmbcntgl;

	/** AP�Ȗڐ���敪 */
	public TLabelComboBox cmbcntap;

	/** AR�Ȗڐ���敪 */
	public TLabelComboBox cmbcntar;

	/** BG�Ȗڐ���敪 */
	public TLabelComboBox cmbcntbg;

	/** �������̓t���O */
	public TLabelComboBox cmbtricodeflg;

	/** ���E�Ȗڐ���敪 */
	public TLabelComboBox cmbcntsousai;

	/** BS��������敪 */
	public TLabelComboBox cmbkesikbn;

	/** �]���֑Ώۃt���O */
	public TLabelComboBox cmbexcflg;

	/** �J�n�N���� */
	public TLabelPopupCalendar dtBeginDate;

	/** �I���N���� */
	public TLabelPopupCalendar dtEndDate;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0080ItemMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#initComponents()
	 */
	@Override
	public void initComponents() {

		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlItemCode = new TLabelField();
		ctrlItemName = new TLabelField();
		ctrlItemNames = new TLabelField();
		ctrlItemNamek = new TLabelField();
		comKmkshu = new TLabelComboBox();
		comHjokbn = new TLabelComboBox();
		rdoImput = new TRadioButton();
		rdoSum = new TRadioButton();
		rdoTitle = new TRadioButton();
		pnlSumkbn = new TPanel();
		rdoCredit = new TRadioButton();
		rdoDebit = new TRadioButton();
		pnlDckbn = new TPanel();
		chk = new TItemStatusUnit(company);
		ctrlFixDepartment = new TDepartmentReference();
		ctrlTax = new TTaxReference();
		cmbcntgl = new TLabelComboBox();
		cmbcntap = new TLabelComboBox();
		cmbcntar = new TLabelComboBox();
		cmbcntbg = new TLabelComboBox();
		cmbtricodeflg = new TLabelComboBox();
		cmbcntsousai = new TLabelComboBox();
		cmbkesikbn = new TLabelComboBox();
		cmbexcflg = new TLabelComboBox();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		bgSum = new ButtonGroup();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		setSize(790, 610);

		// �m��{�^��
		int x = 355 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x + 300, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		x = 355;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x + 300, HEADER_Y);
		pnlHeader.add(btnClose);
		pnlBody.setLayout(null);

		x = 20;
		int y = 20;

		// ����
		rdoImput.setLangMessageID("C01275");
		rdoImput.setSize(60, 15);
		rdoImput.setSelected(true);
		rdoImput.setLocation(x, y);
		rdoImput.setIndex(0);

		x += rdoImput.getWidth();

		// �W�v
		rdoSum.setLangMessageID("C00255");
		rdoSum.setSize(60, 15);
		rdoSum.setLocation(x, y);
		rdoSum.setIndex(1);

		x += rdoSum.getWidth();

		// ���o
		rdoTitle.setLangMessageID("C00506");
		rdoTitle.setSize(60, 15);
		rdoTitle.setLocation(x, y);
		rdoTitle.setIndex(2);

		bgSum.add(rdoImput);
		bgSum.add(rdoSum);
		bgSum.add(rdoTitle);

		pnlSumkbn.add(rdoImput);
		pnlSumkbn.add(rdoSum);
		pnlSumkbn.add(rdoTitle);

		// �W�v�敪�p�l��
		pnlSumkbn.setLayout(null);
		pnlSumkbn.setLangMessageID("C01148");
		pnlSumkbn.setSize(220, 48);
		pnlSumkbn.setLocation(30, 2);
		pnlBody.add(pnlSumkbn);

		// �ؕ�
		rdoDebit.setLangMessageID("C00080");
		rdoDebit.setSize(60, 15);
		rdoDebit.setLocation(24, 20);
		rdoDebit.setSelected(true);
		rdoDebit.setIndex(0);
		pnlBody.add(rdoDebit);

		// �ݕ�
		rdoCredit.setLangMessageID("C00068");
		rdoCredit.setSize(60, 15);
		rdoCredit.setLocation(rdoDebit.getX() + 60, rdoDebit.getY());
		rdoCredit.setIndex(1);
		pnlBody.add(rdoCredit);

		bgDc = new ButtonGroup();
		bgDc.add(rdoCredit);
		bgDc.add(rdoDebit);

		pnlDckbn.add(rdoDebit);
		pnlDckbn.add(rdoCredit);

		// �ݎ؋敪�p�l��
		pnlDckbn.setLayout(null);
		pnlDckbn.setLangMessageID("C01226");
		pnlDckbn.setSize(150, 48);
		pnlDckbn.setLocation(260, 2);
		pnlBody.add(pnlDckbn);

		// �Ȗڎ��
		comKmkshu.setLabelSize(100);
		comKmkshu.setComboSize(160);
		comKmkshu.setSize(265, 20);
		comKmkshu.setLocation(370, 5);
		comKmkshu.setLangMessageID("C01007");
		KmkshuComboBox(comKmkshu.getComboBox());
		pnlBody.add(comKmkshu);

		// �⏕�敪
		comHjokbn.setLabelSize(100);
		comHjokbn.setComboSize(160);
		comHjokbn.setSize(265, 20);
		comHjokbn.setLocation(370, 30);
		comHjokbn.setLangMessageID("C01314");
		HjokbnComboBox(comHjokbn.getComboBox());
		pnlBody.add(comHjokbn);

		// �ȖڃR�[�h
		ctrlItemCode.setLabelSize(110);
		ctrlItemCode.setFieldSize(100);
		ctrlItemCode.setSize(215, 20);
		ctrlItemCode.setLocation(10, 65);
		ctrlItemCode.setLangMessageID("C00572");
		ctrlItemCode.setMaxLength(10);
		ctrlItemCode.setImeMode(false);
		ctrlItemCode.setAllowedSpace(false);
		pnlBody.add(ctrlItemCode);

		// �Ȗږ���
		ctrlItemName.setLabelSize(110);
		ctrlItemName.setFieldSize(380);
		ctrlItemName.setSize(495, 20);
		ctrlItemName.setLocation(10, 90);
		ctrlItemName.setLangMessageID("C00700");
		ctrlItemName.setMaxLength(40);
		pnlBody.add(ctrlItemName);

		// �Ȗڗ���
		ctrlItemNames.setLabelSize(110);
		ctrlItemNames.setFieldSize(150);
		ctrlItemNames.setSize(265, 20);
		ctrlItemNames.setLocation(10, 115);
		ctrlItemNames.setLangMessageID("C00730");
		ctrlItemNames.setMaxLength(20);
		pnlBody.add(ctrlItemNames);

		// �Ȗڌ�������
		ctrlItemNamek.setLabelSize(110);
		ctrlItemNamek.setFieldSize(380);
		ctrlItemNamek.setSize(495, 20);
		ctrlItemNamek.setLocation(10, 140);
		ctrlItemNamek.setLangMessageID("C00731");
		ctrlItemNamek.setMaxLength(40);
		pnlBody.add(ctrlItemNamek);

		// �Œ蕔��
		ctrlFixDepartment.setLocation(35, 165);
		ctrlFixDepartment.btn.setLangMessageID("C00732");
		TGuiUtil.setComponentWidth(ctrlFixDepartment.name, 230);
		ctrlFixDepartment.setSize(new Dimension(410, 20));
		pnlBody.add(ctrlFixDepartment);

		// �����
		ctrlTax.setLocation(10, 190);
		ctrlTax.btn.setLangMessageID("C00286");
		TGuiUtil.setComponentWidth(ctrlTax.name, 230);
		ctrlTax.setSize(new Dimension(410, 20));
		pnlBody.add(ctrlTax);

		// GL�Ȗڐ���敪
		cmbcntgl.setLabelSize(100);
		cmbcntgl.setComboSize(160);
		cmbcntgl.setSize(265, 20);
		cmbcntgl.setLocation(20, 230);
		cmbcntgl.setLangMessageID("C00968");
		GlComboBox(cmbcntgl.getComboBox());
		pnlBody.add(cmbcntgl);

		// AP�Ȗڐ���敪
		cmbcntap.setLabelSize(100);
		cmbcntap.setComboSize(160);
		cmbcntap.setSize(265, 20);
		cmbcntap.setLocation(20, 255);
		cmbcntap.setLangMessageID("C00959");
		ApComboBox(cmbcntap.getComboBox());
		pnlBody.add(cmbcntap);

		// AR�Ȗڐ���敪
		cmbcntar.setLabelSize(100);
		cmbcntar.setComboSize(160);
		cmbcntar.setSize(265, 20);
		cmbcntar.setLocation(20, 280);
		cmbcntar.setLangMessageID("C00960");
		ArComboBox(cmbcntar.getComboBox());
		pnlBody.add(cmbcntar);

		// BG�Ȗڐ���敪
		cmbcntbg.setLabelSize(100);
		cmbcntbg.setComboSize(160);
		cmbcntbg.setSize(265, 20);
		cmbcntbg.setLocation(20, 305);
		cmbcntbg.setLangMessageID("C00962");
		BgComboBox(cmbcntbg.getComboBox());
		pnlBody.add(cmbcntbg);

		// �������̓t���O
		cmbtricodeflg.setLabelSize(100);
		cmbtricodeflg.setComboSize(160);
		cmbtricodeflg.setSize(265, 20);
		cmbtricodeflg.setLocation(20, 330);
		cmbtricodeflg.setLangMessageID("C01134");
		TriComboBox(cmbtricodeflg.getComboBox());
		pnlBody.add(cmbtricodeflg);

		// ���E�Ȗڐ���敪
		cmbcntsousai.setLabelSize(120);
		cmbcntsousai.setComboSize(160);
		cmbcntsousai.setSize(285, 20);
		cmbcntsousai.setLocation(0, 355);
		cmbcntsousai.setLangMessageID("C01217");
		CntsousaiComboBox(cmbcntsousai.getComboBox());
		pnlBody.add(cmbcntsousai);

		// BS��������敪
		cmbkesikbn.setLabelSize(100);
		cmbkesikbn.setComboSize(160);
		cmbkesikbn.setSize(265, 20);
		cmbkesikbn.setLocation(20, 380);
		cmbkesikbn.setLangMessageID("C02078");
		BsKesiComboBox(cmbkesikbn.getComboBox());
		pnlBody.add(cmbkesikbn);

		// �]���֑Ώۃt���O
		cmbexcflg.setLabelSize(100);
		cmbexcflg.setComboSize(160);
		cmbexcflg.setSize(265, 20);
		cmbexcflg.setLocation(20, 405);
		cmbexcflg.setLangMessageID("C01301");
		ExcComboBox(cmbexcflg.getComboBox());
		pnlBody.add(cmbexcflg);

		// �����ޯ��
		chk.setLocation(550, 65);
		chk.setSize(700, 500);
		pnlBody.add(chk);

		// �J�n�N����
		dtBeginDate.setLabelSize(110);
		dtBeginDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtBeginDate.setLocation(10, 450);
		dtBeginDate.setLangMessageID("C00055");
		pnlBody.add(dtBeginDate);

		// �I���N����
		dtEndDate.setLabelSize(110);
		dtEndDate.setSize(110 + dtBeginDate.getCalendarSize() + 5, 20);
		dtEndDate.setLocation(10, 475);
		dtEndDate.setLangMessageID("C00261");
		pnlBody.add(dtEndDate);
	}

	/**
	 * �Ȗڎ�ʃR���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox �������Ώۂ̃R���{�{�b�N�X
	 */
	protected void KmkshuComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(ItemType.DC, getShortWord(ItemType.getName(ItemType.DC)));
		comboBox.addTextValueItem(ItemType.PL, getShortWord(ItemType.getName(ItemType.PL)));
		comboBox.addTextValueItem(ItemType.APPROPRIATION, getWord(ItemType.getName(ItemType.APPROPRIATION)));
		comboBox.addTextValueItem(ItemType.MANUFACTURING_COSTS,
			getShortWord(ItemType.getName(ItemType.MANUFACTURING_COSTS)));
		comboBox.addTextValueItem(ItemType.NON_ACCOUNT, getWord(ItemType.getName(ItemType.NON_ACCOUNT)));
	}

	/**
	 * �⏕�敪�R���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox �������Ώۂ̃R���{�{�b�N�X
	 */
	protected void HjokbnComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(null, "");
		comboBox.addTextValueItem(false, getWord("C00412")); // �Ȃ�
		comboBox.addTextValueItem(true, getWord("C00006")); // ����
		comboBox.setSelectedItemValue(false);
	}

	/**
	 * GL�Ȗڐ���敪�R���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox �������Ώۂ̃R���{�{�b�N�X
	 */
	protected void GlComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(GLType.NOMAL, getWord(GLType.getName(GLType.NOMAL)));
		comboBox.addTextValueItem(GLType.FUND, getWord(GLType.getName(GLType.FUND)));
		comboBox.addTextValueItem(GLType.SALES, getWord(GLType.getName(GLType.SALES)));
		comboBox.addTextValueItem(GLType.TEMPORARY, getWord(GLType.getName(GLType.TEMPORARY)));
		comboBox.setSelectedItemValue(GLType.NOMAL);
	}

	/**
	 * AP�Ȗڐ���敪�R���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox �������Ώۂ̃R���{�{�b�N�X
	 */
	protected void ApComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(APType.NOMAL, getWord(APType.getName(APType.NOMAL)));
		comboBox.addTextValueItem(APType.DEBIT, getWord(APType.getName(APType.DEBIT)));
		comboBox.addTextValueItem(APType.EMPLOYEE, getShortWord(APType.getName(APType.EMPLOYEE)));
		comboBox.setSelectedItemValue(APType.NOMAL);
	}

	/**
	 * AR�Ȗڐ���敪�R���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox �������Ώۂ̃R���{�{�b�N�X
	 */
	protected void ArComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(ARType.NOMAl, getWord(ARType.getName(ARType.NOMAl)));
		comboBox.addTextValueItem(ARType.AR, getWord(ARType.getName(ARType.AR)));
		comboBox.addTextValueItem(ARType.AR_COLLECT, getWord(ARType.getName(ARType.AR_COLLECT)));
		comboBox.setSelectedItemValue(ARType.NOMAl);
	}

	/**
	 * BG�Ȗڐ���敪�R���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox �������Ώۂ̃R���{�{�b�N�X
	 */
	protected void BgComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(BGType.NOMAL, getWord(BGType.getName(BGType.NOMAL)));
		comboBox.addTextValueItem(BGType.BG, getShortWord(BGType.getName(BGType.BG)));
		comboBox.setSelectedItemValue(BGType.NOMAL);
	}

	/**
	 * �������̓t���O�R���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox �������Ώۂ̃R���{�{�b�N�X
	 */
	protected void TriComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(CustomerType.NONE, getWord(CustomerType.getName(CustomerType.NONE)));
		comboBox.addTextValueItem(CustomerType.CUSTOMER, getWord(CustomerType.getName(CustomerType.CUSTOMER)));
		comboBox.addTextValueItem(CustomerType.VENDOR, getWord(CustomerType.getName(CustomerType.VENDOR)));
		comboBox.addTextValueItem(CustomerType.BOTH, getWord(CustomerType.getName(CustomerType.BOTH)));
		comboBox.setSelectedItemValue(CustomerType.NONE);
	}

	/**
	 * ���E�Ȗڐ���敪�R���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox �������Ώۂ̃R���{�{�b�N�X
	 */
	protected void CntsousaiComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(false, getWord("C02099")); // ���Ȃ�
		comboBox.addTextValueItem(true, getWord("C02100")); // ����
		comboBox.setSelectedItemValue(false);
	}

	/**
	 * BS��������敪�R���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox �������Ώۂ̃R���{�{�b�N�X
	 */
	protected void BsKesiComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(false, getWord("C02099")); // ���Ȃ�
		comboBox.addTextValueItem(true, getWord("C02100")); // ����
		comboBox.setSelectedItemValue(false);
	}

	/**
	 * �]���֑Ώۃt���O�R���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox �������Ώۂ̃R���{�{�b�N�X
	 */
	protected void ExcComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(EvaluationMethod.NOT_ISSUE,
			getWord(EvaluationMethod.getName(EvaluationMethod.NOT_ISSUE)));
		comboBox.addTextValueItem(EvaluationMethod.CANCEL, getWord(EvaluationMethod.getName(EvaluationMethod.CANCEL)));
		comboBox.addTextValueItem(EvaluationMethod.NOT_CANCEL,
			getWord(EvaluationMethod.getName(EvaluationMethod.NOT_CANCEL)));
		comboBox.setSelectedItemValue(EvaluationMethod.NOT_ISSUE);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#setTabIndex()
	 */
	@Override
	public void setTabIndex() {

		int i = 0;

		rdoImput.setTabControlNo(i++);
		rdoSum.setTabControlNo(i++);
		rdoTitle.setTabControlNo(i++);
		rdoDebit.setTabControlNo(i++);
		rdoCredit.setTabControlNo(i++);
		comKmkshu.setTabControlNo(i++);
		comHjokbn.setTabControlNo(i++);
		ctrlItemCode.setTabControlNo(i++);
		ctrlItemName.setTabControlNo(i++);
		ctrlItemNames.setTabControlNo(i++);
		ctrlItemNamek.setTabControlNo(i++);
		ctrlFixDepartment.setTabControlNo(i++);
		ctrlTax.setTabControlNo(i++);
		cmbcntgl.setTabControlNo(i++);
		cmbcntap.setTabControlNo(i++);
		cmbcntar.setTabControlNo(i++);
		cmbcntbg.setTabControlNo(i++);
		cmbtricodeflg.setTabControlNo(i++);
		cmbcntsousai.setTabControlNo(i++);
		cmbkesikbn.setTabControlNo(i++);
		cmbexcflg.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);
		chk.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
