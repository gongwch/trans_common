package jp.co.ais.trans.common.bizui;

import java.awt.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���v���׃t�B�[���h
 * 
 * @author ais
 */
public class TNonAccountDtlField extends TPanel {

	private static final long serialVersionUID = 1L;

	/** �R���g���[���N���X */
	protected TNonAccountDtlFieldCtrl ctrl;

	/** �x�[�X�p�l�� */
	protected TPanel pnlBase;

	/** ���v���א��l */
	protected TLabelNumericField[] ctrlNums = new TLabelNumericField[3];

	/** ���v���ו��� */
	protected TLabelField[] ctrlTexts = new TLabelField[3];

	/** ���v���ד��t */
	protected TLabelPopupCalendar[] ctrlDates = new TLabelPopupCalendar[3];

	/** ��ЃR�[�h */
	private String companyCode;

	/** ���v���ׂP�敪 */
	private int hmDivision1;

	/** ���v���ׂQ�敪 */
	private int hmDivision2;

	/** ���v���ׂR�敪 */
	private int hmDivision3;

	/** �t�B�[���h�T�C�Y */
	private final static int fieldSize = 90;

	/** ���x���T�C�Y */
	private final static int labelSize = 65;

	/** ���͍ő包�� */
	private final static int maxLength = 10;

	/**
	 * �R���X�g���N�^
	 */
	public TNonAccountDtlField() {
		super();
		this.ctrl = new TNonAccountDtlFieldCtrl(this);
		initComponents();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param code ��ЃR�[�h
	 */
	public TNonAccountDtlField(String code) {
		super();
		this.companyCode = code;
		this.ctrl = new TNonAccountDtlFieldCtrl(this);
		initComponents();
	}

	/**
	 * ��ʍ\�z
	 */
	protected void initComponents() {

		GridBagConstraints gridBagConstraints;

		pnlBase = new TPanel();
		pnlBase.setLayout(new GridBagLayout());
		TGuiUtil.setComponentSize(pnlBase, new Dimension(180, 80));

		CMP_MST cmp = ctrl.getCmpData();

		if (Util.isNullOrEmpty(cmp)) {
			return;
		}

		setHmDivision1(cmp.getCMP_HM_KBN_1() == 4 ? 3 : cmp.getCMP_HM_KBN_1());
		setHmDivision2(cmp.getCMP_HM_KBN_2() == 4 ? 3 : cmp.getCMP_HM_KBN_2());
		setHmDivision3(cmp.getCMP_HM_KBN_3() == 4 ? 3 : cmp.getCMP_HM_KBN_3());

		// ���v���ׂP-------------------------------------------------------------------
		setHMField(1, cmp.getCMP_HM_KBN_1(), cmp.getCMP_HM_NAME_1());

		// ���v���ׂQ-------------------------------------------------------------------
		setHMField(2, cmp.getCMP_HM_KBN_2(), cmp.getCMP_HM_NAME_2());

		// ���v���ׂR-------------------------------------------------------------------
		setHMField(3, cmp.getCMP_HM_KBN_3(), cmp.getCMP_HM_NAME_3());

		gridBagConstraints = new GridBagConstraints();
		add(pnlBase, gridBagConstraints);

	}

	/**
	 * ���v���׃t�B�[���h�\��
	 * 
	 * @param num ���הԍ�
	 * @param hmKbn �t�B�[���h�敪
	 * @param hmName �t�B�[���h����
	 */
	protected void setHMField(int num, int hmKbn, String hmName) {

		switch (hmKbn) {
			case 1: // ���l
				ctrlNums[num - 1] = new TLabelNumericField();

				ctrlNums[num - 1].setFieldSize(fieldSize);
				ctrlNums[num - 1].setLabelSize(labelSize);
				ctrlNums[num - 1].setLabelText(hmName);
				ctrlNums[num - 1].setMaxLength(maxLength);

				GridBagConstraints gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = num;
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.insets = new Insets(0, 5, 0, 10);
				pnlBase.add(ctrlNums[num - 1], gridBagConstraints);
				break;

			case 2: // ����
				ctrlTexts[num - 1] = new TLabelField();
				ctrlTexts[num - 1].setFieldSize(fieldSize);
				ctrlTexts[num - 1].setLabelSize(labelSize);
				ctrlTexts[num - 1].setLabelText(hmName);
				ctrlTexts[num - 1].setMaxLength(maxLength);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = num;
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.insets = new Insets(0, 5, 0, 10);
				pnlBase.add(ctrlTexts[num - 1], gridBagConstraints);
				break;

			case 3: // ���t(YYYY/MM/DD)
				ctrlDates[num - 1] = new TLabelPopupCalendar();

				ctrlDates[num - 1].setLabelHAlignment(3);
				ctrlDates[num - 1].setLabelSize(labelSize);
				ctrlDates[num - 1].setLabelText(hmName);
				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = num;
				gridBagConstraints.gridwidth = 5;
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.insets = new Insets(0, 5, 0, 10);
				pnlBase.add(ctrlDates[num - 1], gridBagConstraints);
				break;

			case 4: // ���t(YYYY/MM/DD HH:MM)
				ctrlDates[num - 1] = new TLabelPopupCalendar(TPopupCalendar.TYPE_YMDHM);
				ctrlDates[num - 1].setCalendarSize(105);
				ctrlDates[num - 1].setLabelHAlignment(3);
				ctrlDates[num - 1].setLabelSize(labelSize);
				ctrlDates[num - 1].setLabelText(hmName);
				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = num;
				gridBagConstraints.gridwidth = 5;
				gridBagConstraints.anchor = GridBagConstraints.WEST;
				gridBagConstraints.insets = new Insets(0, 5, 0, 10);
				pnlBase.add(ctrlDates[num - 1], gridBagConstraints);
				break;

			default:
				break;
		}
	}

	/**
	 * ��ЃR�[�h�擾
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�Z�b�g
	 * 
	 * @param value
	 */
	public void setCompanyCode(String value) {
		companyCode = value;
	}

	/**
	 * ���v���ׂP�敪�擾
	 * 
	 * @return ���v���ׂP�敪
	 */
	public int getHmDivision1() {
		return hmDivision1;
	}

	/**
	 * ���v���ׂP�敪�Z�b�g
	 * 
	 * @param value
	 */
	public void setHmDivision1(int value) {
		hmDivision1 = value;
	}

	/**
	 * ���v���ׂQ�敪�擾
	 * 
	 * @return ���v���ׂQ�敪
	 */
	public int getHmDivision2() {
		return hmDivision2;
	}

	/**
	 * ���v���ׂQ�敪�Z�b�g
	 * 
	 * @param value
	 */
	public void setHmDivision2(int value) {
		hmDivision2 = value;
	}

	/**
	 * ���v���ׂR�敪�擾
	 * 
	 * @return ���v���ׂR�敪
	 */
	public int getHmDivision3() {
		return hmDivision3;
	}

	/**
	 * ���v���ׂR�敪�Z�b�g
	 * 
	 * @param value
	 */
	public void setHmDivision3(int value) {
		hmDivision3 = value;
	}

	/**
	 * ���v���ׂP�̒l�擾
	 * 
	 * @return ���v���ׂP
	 */
	public String getHm1Value() {
		switch (getHmDivision1()) {
			case 1:
				return ctrlNums[0].getValue();
			case 2:
				return ctrlTexts[0].getValue();
			case 3:
				return Util.avoidNull(ctrlDates[0].getValue());
			default:
				return null;
		}
	}

	/**
	 * ���v���ׂP�Z�b�g
	 * 
	 * @param value
	 */
	public void setHm1Value(String value) {
		switch (getHmDivision1()) {
			case 1:
				ctrlNums[0].setValue(value);
				break;
			case 2:
				ctrlTexts[0].setValue(value);
				break;
			case 3:
				ctrl.setToDateComp(ctrlDates[0], value);
				break;
		}
	}

	/**
	 * ���v���ׂQ�̒l�擾
	 * 
	 * @return ���v���ׂQ
	 */
	public String getHm2Value() {
		switch (getHmDivision2()) {
			case 1:
				return ctrlNums[1].getValue();
			case 2:
				return ctrlTexts[1].getValue();
			case 3:
				return Util.avoidNull(ctrlDates[1].getValue());
			default:
				return null;
		}
	}

	/**
	 * ���v���ׂQ�Z�b�g
	 * 
	 * @param value
	 */
	public void setHm2Value(String value) {
		switch (getHmDivision2()) {
			case 1:
				ctrlNums[1].setValue(value);
				break;
			case 2:
				ctrlTexts[1].setValue(value);
				break;
			case 3:
				ctrl.setToDateComp(ctrlDates[1], value);
				break;
		}
	}

	/**
	 * ���v���ׂR�̒l�擾
	 * 
	 * @return ���v���ׂR
	 */
	public String getHm3Value() {
		switch (getHmDivision3()) {
			case 1:
				return ctrlNums[2].getValue();
			case 2:
				return ctrlTexts[2].getValue();
			case 3:
				return Util.avoidNull(ctrlDates[2].getValue());
			default:
				return null;
		}
	}

	/**
	 * ���v���ׂR�Z�b�g
	 * 
	 * @param value
	 */
	public void setHm3Value(String value) {
		switch (getHmDivision3()) {
			case 1:
				ctrlNums[2].setValue(value);
				break;
			case 2:
				ctrlTexts[2].setValue(value);
				break;
			case 3:
				ctrl.setToDateComp(ctrlDates[2], value);
				break;
		}
	}

	/**
	 * ���v����1�̉�ʐ���
	 * 
	 * @param editableHm1
	 */
	public void setEditableHm1(boolean editableHm1) {
		switch (getHmDivision1()) {
			case 1:
				ctrlNums[0].setEditable(editableHm1);
				break;
			case 2:
				ctrlTexts[0].setEditable(editableHm1);
				break;
			case 3:
				ctrlDates[0].setEnabled(editableHm1);
				ctrlDates[0].setEditable(editableHm1);
				break;
		}
	}

	/**
	 * ���v����2�̉�ʐ���
	 * 
	 * @param editableHm2
	 */
	public void setEditableHm2(boolean editableHm2) {
		switch (getHmDivision2()) {
			case 1:
				ctrlNums[1].setEditable(editableHm2);
				break;
			case 2:
				ctrlTexts[1].setEditable(editableHm2);
				break;
			case 3:
				ctrlDates[1].setEnabled(editableHm2);
				ctrlDates[1].setEditable(editableHm2);
				break;
		}
	}

	/**
	 * ���v����3�̉�ʐ���
	 * 
	 * @param editableHm3
	 */
	public void setEditableHm3(boolean editableHm3) {
		switch (getHmDivision3()) {
			case 1:
				ctrlNums[2].setEditable(editableHm3);
				break;
			case 2:
				ctrlTexts[2].setEditable(editableHm3);
				break;
			case 3:
				ctrlDates[2].setEnabled(editableHm3);
				ctrlDates[2].setEditable(editableHm3);
				break;
		}
	}

	// �ȉ��R���|�[�l���g�̎擾---------------------------------------------------------
	/**
	 * �p�l���擾
	 * 
	 * @return �p�l��
	 */
	public TPanel getPanel() {
		return pnlBase;
	}

	/**
	 * ���v���ׂP�R���|�[�l���g�擾
	 * 
	 * @return ���v���ׂP�R���|�[�l���g
	 */
	public TLabelNumericField getHm1Num() {
		return ctrlNums[0];
	}

	/**
	 * ���v���ׂP�R���|�[�l���g�擾
	 * 
	 * @return ���v���ׂP�R���|�[�l���g
	 */
	public TLabelField getHm1Text() {
		return ctrlTexts[0];
	}

	/**
	 * ���v����1�R���|�[�l���g�擾
	 * 
	 * @return ���v����1�R���|�[�l���g
	 */
	public TLabelPopupCalendar getHm1Date() {
		return ctrlDates[0];
	}

	/**
	 * ���v���ׂQ�R���|�[�l���g�擾
	 * 
	 * @return ���v���ׂQ�R���|�[�l���g
	 */
	public TLabelNumericField getHm2Num() {
		return ctrlNums[1];
	}

	/**
	 * ���v���ׂQ�R���|�[�l���g�擾
	 * 
	 * @return ���v���ׂQ�R���|�[�l���g
	 */
	public TLabelField getHm2Text() {
		return ctrlTexts[1];
	}

	/**
	 * ���v���ׂQ�R���|�[�l���g�擾
	 * 
	 * @return ���v���ׂQ�R���|�[�l���g
	 */
	public TLabelPopupCalendar getHm2Date() {
		return ctrlDates[1];
	}

	/**
	 * ���v���ׂR�R���|�[�l���g�擾
	 * 
	 * @return ���v���ׂR�R���|�[�l���g
	 */
	public TLabelNumericField getHm3Num() {
		return ctrlNums[2];
	}

	/**
	 * ���v���ׂR�R���|�[�l���g�擾
	 * 
	 * @return ���v���ׂR�R���|�[�l���g
	 */
	public TLabelField getHm3Text() {
		return ctrlTexts[2];
	}

	/**
	 * ���v���ׂR�R���|�[�l���g�擾
	 * 
	 * @return ���v���ׂR�R���|�[�l���g
	 */
	public TLabelPopupCalendar getHm3Date() {
		return ctrlDates[2];
	}

	/**
	 * �^�u�ړ����ԍ�����v���ׂɐݒ肷��.
	 * 
	 * @param no
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		for (int i = 0; i <= 2; i++) {
			if (ctrlNums[i] != null) {
				ctrlNums[i].setTabControlNo(no);
			}

			if (ctrlTexts[i] != null) {
				ctrlTexts[i].setTabControlNo(no);
			}

			if (ctrlDates[i] != null) {
				ctrlDates[i].setTabControlNo(no);
			}
		}
	}
}
