package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * �x�����@�}�X�^�ҏW�_�C�A���O
 * 
 * @author ISFnet China
 */
public class MP0040EditDisplayDialog extends jp.co.ais.trans.common.gui.TDialog {

	/** �V���A��UID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** �R���g���[���N���X */
	private MP0040EditDisplayDialogCtrl ctrl;

	/** �m�肳�ꂽ���ǂ��� */
	boolean isSettle = false;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent �e�t���[��
	 * @param modal ���[�_���_�C�A���O��(true: ���[�_�����[�h)
	 * @param ctrl �R���g���[���N���X
	 * @param titleid
	 */
	public MP0040EditDisplayDialog(Frame parent, boolean modal, MP0040EditDisplayDialogCtrl ctrl, String titleid) {
		// �e�t���[����ݒ�
		super(parent, modal);
		// �R���g���[���N���X��ݒ�
		this.ctrl = ctrl;
		this.setResizable(false);
		// ��ʂ̏�����
		initComponents();
		setLangMessageID(titleid);
		// ��ʂ̐ݒ�
		setSize(650, 480);

		super.initDialog();
	}

	/**
	 * Frame �擾
	 * 
	 * @return Frame
	 */
	@Override
	public Frame getParentFrame() {
		return super.getParentFrame();
	}

	/**
	 * ��ʏ�����
	 */
	protected void initComponents() {

		// GridBagConstraints �錾
		GridBagConstraints gridBagConstraints;

		// GridBagLayout ������
		getContentPane().setLayout(new GridBagLayout());

		// �{�^���p�l��
		pnlButton = new TPanel();
		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMaximumSize(new Dimension(610, 40));
		pnlButton.setMinimumSize(new Dimension(610, 40));
		pnlButton.setPreferredSize(new Dimension(610, 40));

		// �m��{�^��
		btnSettle = new TImageButton(IconType.SETTLE);
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(13);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 350, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		// �m��{�^���C�x���g��`
		btnSettle.addActionListener(new ActionListener() {

			/**
			 * �m��{�^�������̏���
			 */
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent evt) {
				isSettle = true;
				ctrl.disposeDialog();
			}
		});

		// ����{�^��
		btnReturn = new TButton();
		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(14);
		btnReturn.setForClose(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);

		// ����{�^���C�x���g��`
		btnReturn.addActionListener(new ActionListener() {

			/**
			 * �߂�{�^�������̏���
			 */
			public void actionPerformed(@SuppressWarnings("unused") ActionEvent evt) {
				isSettle = false;
				ctrl.disposeDialog();
			}
		});

		// �{�^���p�l�� �z�u
		getContentPane().add(pnlButton, gridBagConstraints);

		// ���C���p�l��1
		pnlDetail1 = new TPanel();
		pnlDetail1.setLayout(new GridBagLayout());
		pnlDetail1.setMaximumSize(new Dimension(610, 80));
		pnlDetail1.setMinimumSize(new Dimension(610, 80));
		pnlDetail1.setPreferredSize(new Dimension(610, 80));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);

		// �x�����@�R�[�h
		ctrlPaymentMethodCode = new TLabelField();
		ctrlPaymentMethodCode.setFieldSize(50);
		ctrlPaymentMethodCode.setLabelSize(150);
		ctrlPaymentMethodCode.setLangMessageID("C00864");
		ctrlPaymentMethodCode.getField().setAllowedSpace(false);
		ctrlPaymentMethodCode.setMaxLength(3);
		ctrlPaymentMethodCode.setTabControlNo(1);
		ctrlPaymentMethodCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlPaymentMethodCode, gridBagConstraints);

		// �x�����@����
		ctrlPaymentMethodName = new TLabelField();
		ctrlPaymentMethodName.setFieldSize(300);
		ctrlPaymentMethodName.setLabelSize(150);
		ctrlPaymentMethodName.setLangMessageID("C00865");
		ctrlPaymentMethodName.setMaxLength(20);
		ctrlPaymentMethodName.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlPaymentMethodName, gridBagConstraints);

		// �x�����@��������
		ctrlPaymentMethodNameForSearch = createForSearchCtrl();
		ctrlPaymentMethodNameForSearch.setFieldSize(300);
		ctrlPaymentMethodNameForSearch.setLabelSize(150);
		ctrlPaymentMethodNameForSearch.setLangMessageID("C00866");
		ctrlPaymentMethodNameForSearch.setMaxLength(40);
		ctrlPaymentMethodNameForSearch.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 5, 150);
		pnlDetail1.add(ctrlPaymentMethodNameForSearch, gridBagConstraints);

		// ���C���p�l��1 �z�u
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		getContentPane().add(pnlDetail1, gridBagConstraints);

		// ���C���p�l��2 (�Ȗ�)
		pnlDetail2 = new TPanel();
		pnlDetail2.setLayout(new GridBagLayout());
		pnlDetail2.setMaximumSize(new Dimension(610, 130));
		pnlDetail2.setMinimumSize(new Dimension(610, 130));
		pnlDetail2.setPreferredSize(new Dimension(610, 130));

		// �ȖڃT�C�Y������
		ctrlItemUnit = new TAccountItemUnit();
		setItemSize();
		ctrlItemUnit.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;

		// �ȖڃR���|�[�l���g�擾
		AccountItemInputParameter inputParameter = new AccountItemInputParameter();
		inputParameter.setSummaryDivision("0");
		ctrlItemUnit.getTItemField().setInputParameter(inputParameter);
		pnlDetail2.add(ctrlItemUnit, gridBagConstraints);

		// �v�㕔��
		ctrlAppropriateDepartment = new TButtonField();
		ctrlAppropriateDepartment.setLangMessageID("C00863");
		ctrlAppropriateDepartment.setMaximumSize(new Dimension(520, 20));
		ctrlAppropriateDepartment.setMinimumSize(new Dimension(520, 20));
		ctrlAppropriateDepartment.setButtonSize(85);
		ctrlAppropriateDepartment.setFieldSize(80);
		ctrlAppropriateDepartment.setNoticeSize(221);
		ctrlAppropriateDepartment.setMaxLength(10);
		ctrlAppropriateDepartment.setTabControlNo(7);
		ctrlAppropriateDepartment.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(15, 0, 0, 82);
		pnlDetail2.add(ctrlAppropriateDepartment, gridBagConstraints);

		// ���C���p�l��2 �z�u
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		getContentPane().add(pnlDetail2, gridBagConstraints);

		// ���C���p�l��3
		pnlDetail3 = new TPanel();
		pnlDetail3.setLayout(new GridBagLayout());
		pnlDetail3.setMaximumSize(new Dimension(560, 150));
		pnlDetail3.setMinimumSize(new Dimension(560, 150));
		pnlDetail3.setPreferredSize(new Dimension(560, 150));

		// �x���Ώۋ敪�p�l��
		pnlPaymentDivision = new TPanel();
		pnlPaymentDivision.setLayout(new GridBagLayout());
		pnlPaymentDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
		pnlPaymentDivision.setLangMessageID("C01096");
		pnlPaymentDivision.setMaximumSize(new Dimension(220, 50));
		pnlPaymentDivision.setMinimumSize(new Dimension(220, 50));
		pnlPaymentDivision.setPreferredSize(new Dimension(220, 50));

		// Radio Button Group - �x���Ώۋ敪
		btngrpPaymentDivision = new ButtonGroup();

		// Radio Button - �Ј��x��
		rdoEmployeePayment = new TRadioButton();
		rdoEmployeePayment.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpPaymentDivision.add(rdoEmployeePayment);
		rdoEmployeePayment.setSelected(true);
		rdoEmployeePayment.setLangMessageID("C01119");
		rdoEmployeePayment.setMargin(new Insets(0, 0, 0, 0));
		rdoEmployeePayment.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlPaymentDivision.add(rdoEmployeePayment, gridBagConstraints);

		// Radio Button - �ЊO�x��
		rdoExternalPayment = new TRadioButton();
		rdoExternalPayment.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpPaymentDivision.add(rdoExternalPayment);
		rdoExternalPayment.setLangMessageID("C01124");
		rdoExternalPayment.setMargin(new Insets(0, 0, 0, 0));
		rdoExternalPayment.setTabControlNo(9);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 20, 5, 0);
		pnlPaymentDivision.add(rdoExternalPayment, gridBagConstraints);

		// ���C���p�l��3�Ɏx���Ώۋ敪�p�l���ǉ�
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 110, 0, 0);
		pnlDetail3.add(pnlPaymentDivision, gridBagConstraints);

		// �x�������R�[�h
		ctrlPaymentInternalCode = new TLabelComboBox();
		ctrlPaymentInternalCode.setComboSize(215);
		ctrlPaymentInternalCode.setLabelSize(100);
		ctrlPaymentInternalCode.setLangMessageID("C01097");
		ctrlPaymentInternalCode.setTabControlNo(10);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(15, 5, 0, 0);
		pnlDetail3.add(ctrlPaymentInternalCode, gridBagConstraints);

		// �J�n�N����
		dtBeginDate = new TLabelPopupCalendar();
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		dtBeginDate.setMaximumSize(new Dimension(100, 30));
		dtBeginDate.setMinimumSize(new Dimension(100, 30));
		dtBeginDate.setLabelSize(90);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(11);
		dtBeginDate.getCalendar().setMaximumDate(PanelAndDialogCtrlBase.maxInputDate);
		dtBeginDate.getCalendar().setMinimumDate(PanelAndDialogCtrlBase.minInputDate);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(20, 0, 0, 128);
		pnlDetail3.add(dtBeginDate, gridBagConstraints);

		// �I���N����
		dtEndDate = new TLabelPopupCalendar();
		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		dtEndDate.setMaximumSize(new Dimension(100, 30));
		dtEndDate.setMinimumSize(new Dimension(100, 30));
		dtEndDate.setLabelSize(90);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(12);
		dtEndDate.getCalendar().setMaximumDate(PanelAndDialogCtrlBase.maxInputDate);
		dtEndDate.getCalendar().setMinimumDate(PanelAndDialogCtrlBase.minInputDate);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new Insets(-20, 180, 0, 0);
		pnlDetail3.add(dtEndDate, gridBagConstraints);

		// ���C���p�l��3 �z�u
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new Insets(15, 0, 0, 290);
		getContentPane().add(pnlDetail3, gridBagConstraints);

		pack();
	}

	/**
	 * �ȖڃT�C�Y������
	 */
	public void setItemSize() {

		this.setMaximumSize(new Dimension(520, 80));
		this.setMinimumSize(new Dimension(520, 80));
		this.setPreferredSize(new Dimension(520, 80));

		GridBagConstraints gridBagConstraints;

		ctrlItemUnit.getTBasePanel().setLayout(new GridBagLayout());
		ctrlItemUnit.getTBasePanel().setMaximumSize(new Dimension(520, 80));
		ctrlItemUnit.getTBasePanel().setMinimumSize(new Dimension(520, 80));
		ctrlItemUnit.getTBasePanel().setPreferredSize(new Dimension(520, 80));

		// �Ȗ�
		ctrlItemUnit.getTItemField().setButtonSize(85);
		ctrlItemUnit.getTItemField().setFieldSize(80);
		ctrlItemUnit.getTItemField().setNoticeSize(221);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(10, 0, 0, 82);
		ctrlItemUnit.getTBasePanel().add(ctrlItemUnit.getTItemField(), gridBagConstraints);

		// �⏕�Ȗ�
		ctrlItemUnit.getTSubItemField().setButtonSize(85);
		ctrlItemUnit.getTSubItemField().setFieldSize(80);
		ctrlItemUnit.getTSubItemField().setNoticeSize(221);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(5, 0, 0, 82);
		ctrlItemUnit.getTBasePanel().add(ctrlItemUnit.getTSubItemField(), gridBagConstraints);

		// ����Ȗ�
		ctrlItemUnit.getTBreakDownItemField().setButtonSize(85);
		ctrlItemUnit.getTBreakDownItemField().setFieldSize(80);
		ctrlItemUnit.getTBreakDownItemField().setNoticeSize(221);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(5, 0, 0, 82);
		ctrlItemUnit.getTBasePanel().add(ctrlItemUnit.getTBreakDownItemField(), gridBagConstraints);
	}

	/** �{�^���p�l�� */
	protected TPanel pnlButton;

	/** ���C���p�l��1 */
	protected TPanel pnlDetail1;

	/** ���C���p�l��2 */
	protected TPanel pnlDetail2;

	/** ���C���p�l��3 */
	protected TPanel pnlDetail3;

	/** ����{�^�� */
	protected TButton btnReturn;

	/** �m��{�^�� */
	protected TImageButton btnSettle;

	/** �x�����@�R�[�h */
	protected TLabelField ctrlPaymentMethodCode;

	/** �x�����@���� */
	protected TLabelField ctrlPaymentMethodName;

	/** �x�����@�������� */
	protected TLabelField ctrlPaymentMethodNameForSearch;

	/** �Ȗ� */
	protected TAccountItemUnit ctrlItemUnit;

	/** �v�㕔�� */
	protected TButtonField ctrlAppropriateDepartment;

	/** �x���Ώۋ敪 �p�l�� */
	protected TPanel pnlPaymentDivision;

	/** �x���Ώۋ敪 Button Group */
	protected ButtonGroup btngrpPaymentDivision;

	/** �x���Ώۋ敪 Button �Ј��x�� */
	protected TRadioButton rdoEmployeePayment;

	/** �x���Ώۋ敪 Button �ЊO�x�� */
	protected TRadioButton rdoExternalPayment;

	/** �x�������R�[�h */
	protected TLabelComboBox ctrlPaymentInternalCode;

	/** �J�n�N���� */
	protected TLabelPopupCalendar dtBeginDate;

	/** �I���N���� */
	protected TLabelPopupCalendar dtEndDate;

	/**
	 * �������̃t�B�[���h�쐬
	 * 
	 * @return �������̃t�B�[���h
	 */
	protected TLabelField createForSearchCtrl() {
		return new TLabelField();
	}
}