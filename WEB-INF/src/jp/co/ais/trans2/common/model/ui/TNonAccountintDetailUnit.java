package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.text.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ���v���׃t�B�[���h
 * 
 * @author ais
 */
public class TNonAccountintDetailUnit extends TPanel {

	/** �x�[�X�p�l�� */
	protected TPanel pnlBase;

	/** ���v���א��l */
	protected TLabelNumericField[] ctrlNums = new TLabelNumericField[3];

	/** ���v���ו��� */
	protected TLabelField[] ctrlTexts = new TLabelField[3];

	/** ���v���ד��t */
	protected TLabelPopupCalendar[] ctrlDates = new TLabelPopupCalendar[3];

	/** ���v���׋敪 */
	protected NonAccountingDivision[] divs;

	/** �t�B�[���h�T�C�Y */
	protected static final int SIZE_FIELD = 90;

	/** �t�B�[���h�T�C�Y ����60�p�^�[�� */
	protected static final int SIZE_FIELD_60 = 480;

	/** ���x���T�C�Y */
	protected static final int SIZE_LABEL = 80;

	/** ���͍ő包�� */
	protected static final int LENGTH = 10;

	/** ���͍ő包�� 60�p�^�[�� */
	protected static final int LENGTH_60 = 60;

	/** ���v���׃T�C�Y���f�t�H���g�ݒ�ɂ��� */
	public boolean isDefaultSize = ClientConfig.isFlagOn("trans.use.non.account.detail.default.size");

	/**
	 * �R���X�g���N�^
	 */
	public TNonAccountintDetailUnit() {
		initComponents();
	}

	/**
	 * ��ʍ\�z
	 */
	protected void initComponents() {

		setLayout(new BorderLayout());
		if (isDefaultSize) {
			TGuiUtil.setComponentSize(this, new Dimension(480, 60));
		} else {
			TGuiUtil.setComponentSize(this, new Dimension(600, 60));
		}

		pnlBase = new TPanel();
		pnlBase.setLayout(new GridBagLayout());

		AccountConfig conf = TLoginInfo.getCompany().getAccountConfig();

		if (Util.isNullOrEmpty(conf)) {
			return;
		}

		divs = new NonAccountingDivision[] { conf.getNonAccounting1(), conf.getNonAccounting2(),
				conf.getNonAccounting3() };

		// ���v����1�`3
		setHMField(1, conf.getNonAccounting1Name());
		setHMField(2, conf.getNonAccounting2Name());
		setHMField(3, conf.getNonAccounting3Name());

		add(pnlBase, BorderLayout.NORTH);
	}

	/**
	 * ���v���׃t�B�[���h�\��
	 * 
	 * @param num ���הԍ�
	 * @param name �t�B�[���h����
	 */
	protected void setHMField(int num, String name) {

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = num;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 10);

		switch (divs[num - 1]) {
			case NUMBER: // ���l
				ctrlNums[num - 1] = new TLabelNumericField();
				if (isDefaultSize) {
					ctrlNums[num - 1].setFieldSize(SIZE_FIELD);
				} else {
					ctrlNums[num - 1].setFieldSize(SIZE_FIELD_60);
				}
				ctrlNums[num - 1].setLabelSize(SIZE_LABEL);
				ctrlNums[num - 1].setLabelText(name);
				if (isDefaultSize) {
					ctrlNums[num - 1].setMaxLength(LENGTH);
				} else {
					ctrlNums[num - 1].setMaxLength(LENGTH_60);
				}
				ctrlNums[num - 1].setNumericFormat("#,###.##########");
				ctrlNums[num - 1].setChangeRedOfMinus(true);

				pnlBase.add(ctrlNums[num - 1], gridBagConstraints);
				break;

			case CHAR: // ����
				ctrlTexts[num - 1] = new TLabelField();
				if (isDefaultSize) {
					ctrlTexts[num - 1].setFieldSize(SIZE_FIELD);
				} else {
					ctrlTexts[num - 1].setFieldSize(SIZE_FIELD_60);
				}
				ctrlTexts[num - 1].setLabelSize(SIZE_LABEL);
				ctrlTexts[num - 1].setLabelText(name);
				if (isDefaultSize) {
					ctrlTexts[num - 1].setMaxLength(LENGTH);
				} else {
					ctrlTexts[num - 1].setMaxLength(LENGTH_60);
				}

				pnlBase.add(ctrlTexts[num - 1], gridBagConstraints);
				break;

			case YMD_DATE: // ���t(YYYY/MM/DD)
				ctrlDates[num - 1] = new TLabelPopupCalendar();
				ctrlDates[num - 1].setLabelSize(SIZE_LABEL);
				ctrlDates[num - 1].setLabelText(name);
				ctrlDates[num - 1].setAllowableBlank(true);
				ctrlDates[num - 1].setValue(null);

				gridBagConstraints.gridwidth = 5;
				pnlBase.add(ctrlDates[num - 1], gridBagConstraints);
				break;

			case YMDHM_DATE: // ���t(YYYY/MM/DD HH:MM)
				ctrlDates[num - 1] = new TLabelPopupCalendar(TPopupCalendar.TYPE_YMDHM);
				// ctrlDates[num - 1].setCalendarSize(105);
				ctrlDates[num - 1].setLabelSize(SIZE_LABEL);
				ctrlDates[num - 1].setLabelText(name);
				ctrlDates[num - 1].setAllowableBlank(true);
				ctrlDates[num - 1].setValue(null);

				gridBagConstraints.gridwidth = 5;
				pnlBase.add(ctrlDates[num - 1], gridBagConstraints);
				break;

			default:
				break;
		}
	}

	/**
	 * �l�擾
	 * 
	 * @param num ���v���הԍ�
	 * @return �l
	 */
	public String getValue(int num) {

		switch (divs[num - 1]) {
			case NUMBER:
				return ctrlNums[num - 1].getInputText();

			case CHAR:
				return ctrlTexts[num - 1].getValue();

			case YMD_DATE:
				return DateUtil.toYMDString(ctrlDates[num - 1].getValue());

			case YMDHM_DATE:
				return DateUtil.toYMDHMString(ctrlDates[num - 1].getValue());
		}

		return null;
	}

	/**
	 * �l�ݒ�
	 * 
	 * @param num ���v���הԍ�
	 * @param value �l
	 */
	public void setValue(int num, String value) {
		try {
			switch (divs[num - 1]) {
				case NUMBER:
					ctrlNums[num - 1].setValue(value);
					break;

				case CHAR:
					ctrlTexts[num - 1].setValue(value);
					break;

				case YMD_DATE:
					ctrlDates[num - 1].setValue(DateUtil.toYMDDate(value));
					break;

				case YMDHM_DATE:
					ctrlDates[num - 1].setValue(DateUtil.toYMDHMDate(value));
					break;
			}

		} catch (ParseException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * ���͐���
	 * 
	 * @param num ���v���הԍ�
	 * @param isEdit true:����� false:����s��
	 */
	public void setEditable(int num, boolean isEdit) {

		switch (divs[num - 1]) {
			case NUMBER:
				ctrlNums[num - 1].setEditable(isEdit);
				break;

			case CHAR:
				ctrlTexts[num - 1].setEditable(isEdit);
				break;

			case YMD_DATE:
			case YMDHM_DATE:
				ctrlDates[num - 1].setEnabled(isEdit);
				ctrlDates[num - 1].setEditable(isEdit);
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
	 * �l�ݒ�
	 * 
	 * @param num ���v���הԍ�
	 * @return �t�B�[���h
	 */
	public TPanel getHmField(int num) {
		switch (divs[num - 1]) {
			case NUMBER:
				return ctrlNums[num - 1];

			case CHAR:
				return ctrlTexts[num - 1];

			case YMD_DATE:
			case YMDHM_DATE:
				return ctrlDates[num - 1];

		}
		return null;
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
	 * @param no �^�u�ԍ�
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

	/**
	 * �ő���͌��Z�b�g
	 * 
	 * @param num ���v���הԍ�
	 * @param maxLength ����
	 */
	public void setMaxLength(int num, int maxLength) {
		switch (divs[num - 1]) {
			case NUMBER:
				ctrlNums[num - 1].setMaxLength(maxLength);
				break;

			case CHAR:
				ctrlTexts[num - 1].setMaxLength(maxLength);
				break;
		}
	}

	/**
	 * �N���A
	 */
	public void clear() {
		setValue(1, null);
		setValue(2, null);
		setValue(3, null);
	}

	/**
	 * �N���A
	 * 
	 * @param num ���v���הԍ�
	 */
	public void clear(int num) {
		setValue(num, null);
	}

	/**
	 * ���͐���
	 * 
	 * @param isEdit true:���͉�
	 */
	public void setEditable(boolean isEdit) {
		this.setEditable(1, isEdit);
		this.setEditable(2, isEdit);
		this.setEditable(3, isEdit);
	}

}
