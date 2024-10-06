package jp.co.ais.trans.common.bizui;

import java.awt.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;

/**
 * �x�����t�B�[���h
 * 
 * @author ookawara
 */
public class TPaymentInformationField extends TPanel implements TInterfaceLangMessageID {

	/** �V���A��UID */
	private static final long serialVersionUID = 6975993877695662983L;

	/** �R���g���[���N���X */
	private TPaymentInformationFieldCtrl ctrl;

	/** ��ЃR�[�h */
	private String companyCode;

	/** �L�����Ԃ̃`�F�b�N */
	private String termBasisDate;

	/** �f�t�H���g */
	public static final int TYPE_NORMAL = 0;

	/** �x���\����Ȃ� */
	public static final int TYPE_CALENDER_NONE = 1;

	/**
	 * �R���X�g���N�^
	 */
	public TPaymentInformationField() {
		this(TYPE_NORMAL);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type TYPE_NORMAL�F�J�����_�[�L�� TYPE_CALENDER_NONE�F�J�����_�[����
	 */
	public TPaymentInformationField(int type) {
		super();

		// ���O�C����ЃR�[�h���Z�b�g
		this.companyCode = TClientLoginInfo.getInstance().getCompanyCode();

		ctrl = new TPaymentInformationFieldCtrl(this);

		switch (type) {
			case TYPE_CALENDER_NONE:
				initVariableComponents();
				break;

			default:
				initComponents();
				break;
		}

	}

	/**
	 * ��ʍ\�z
	 */
	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		basePanel = new TPanel();
		paymentConditionField = new TPaymentConditionEnhancingField();
		paymentMethodField = new TPaymentMethodField();
		bankAccountField = new TBankAccountEnhancingField();
		calendar = new TLabelPopupCalendar();
		divisonComboBox = new TPaymentDivisionComboBox();
		// �l�擾�pBEAN
		parameter = new PaymentInformationParameter();

		paymentConditionField.setParameter(parameter);

		paymentConditionField.addCallControl(new CallBackListener() {

			public void after() {
				ctrl.paymentConditionScreenCtrl();
			}
		});

		paymentMethodField.addCallControl(new CallBackListener() {

			public void after() {
				ctrl.paymentMethodScreenCtrl();
			}
		});

		setLayout(new GridBagLayout());

		basePanel.setLayout(new GridBagLayout());
		gridBagConstraints = new GridBagConstraints();
		add(basePanel, new GridBagConstraints());

		// �����l�ݒ�
		paymentConditionField.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(paymentConditionField, gridBagConstraints);

		paymentMethodField.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(paymentMethodField, gridBagConstraints);

		bankAccountField.getButton().setLangMessageID("C01634");
		bankAccountField.getButton().setEnabled(false);
		bankAccountField.getField().setEditableEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(bankAccountField, gridBagConstraints);

		calendar.setLabelHAlignment(1);
		calendar.setLabelSize(85);
		calendar.setLangMessageID("C01100");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(calendar, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 180, 0, 0);
		basePanel.add(divisonComboBox, gridBagConstraints);

	}

	/**
	 * ��ʍ\�z �x���\����Ȃ�
	 */
	private void initVariableComponents() {
		GridBagConstraints gridBagConstraints;

		basePanel = new TPanel();
		paymentConditionField = new TPaymentConditionEnhancingField();
		paymentMethodField = new TPaymentMethodField();
		bankAccountField = new TBankAccountEnhancingField();
		calendar = new TLabelPopupCalendar();
		divisonComboBox = new TPaymentDivisionComboBox();
		// Label�EField�T�C�Y��ύX
		divisonComboBox.setLabelSize(85);
		divisonComboBox.setComboSize(75);
		// �l�擾�pBEAN
		parameter = new PaymentInformationParameter();

		paymentConditionField.setParameter(parameter);

		paymentConditionField.addCallControl(new CallBackListener() {

			public void after() {
				ctrl.paymentConditionScreenCtrl();
			}
		});

		paymentMethodField.addCallControl(new CallBackListener() {

			public void after() {
				ctrl.paymentMethodScreenCtrl();
			}
		});

		setLayout(new GridBagLayout());

		basePanel.setLayout(new GridBagLayout());
		gridBagConstraints = new GridBagConstraints();
		add(basePanel, new GridBagConstraints());

		// �����l�ݒ�
		paymentConditionField.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(paymentConditionField, gridBagConstraints);

		paymentMethodField.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(paymentMethodField, gridBagConstraints);

		bankAccountField.getButton().setLangMessageID("C01634");
		bankAccountField.getButton().setEnabled(false);
		bankAccountField.getField().setEditableEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(bankAccountField, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(divisonComboBox, gridBagConstraints);

	}

	/**
	 * �p�l���S�� �^�u�ړ����ԍ���ݒ肷��.
	 * 
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 * @param no �ԍ�
	 */
	public void setTabControlNo(int no) {
		paymentConditionField.setTabControlNo(no);
		paymentMethodField.setTabControlNo(no);
		bankAccountField.setTabControlNo(no);
		calendar.setTabControlNo(no);
		divisonComboBox.setTabControlNo(no);
	}

	/**
	 * ��ЃR�[�h��Ԃ�
	 * 
	 * @return companyCode ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		paymentConditionField.setCompanyCode(companyCode);
		paymentMethodField.setCompanyCode(companyCode);
		bankAccountField.setCompanyCode(companyCode);
	}

	/**
	 * ����̎擾
	 * 
	 * @return termBasisDate ���
	 */
	public String getTermBasisDate() {
		return termBasisDate;
	}

	/**
	 * ����̐ݒ�
	 * 
	 * @param termBasisDate
	 */
	public void setTermBasisDate(String termBasisDate) {
		this.termBasisDate = termBasisDate;
	}

	/**
	 * �����R�[�h�̎擾
	 * 
	 * @return customerCode �����R�[�h
	 */
	public String getCustomerCode() {
		return this.paymentConditionField.getCustomerCode();
	}

	/**
	 * �����R�[�h�̐ݒ�
	 * 
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.paymentConditionField.setCustomerCode(customerCode);
	}

	/**
	 * �ʉ݃R�[�h�̎擾
	 * 
	 * @return curCode �ʉ݃R�[�h
	 */
	public String getCurCode() {
		return this.paymentConditionField.getCurrencyCode();
	}

	/**
	 * �ʉ݃R�[�h�̃Z�b�g
	 * 
	 * @param curCode
	 */
	public void setCurCode(String curCode) {
		this.paymentConditionField.setCurrencyCode(curCode);
	}

	/**
	 * �x��������R���|�[�l���g�̎擾
	 * 
	 * @return paymentConditionField �x��������R���|�[�l���g
	 */
	public TPaymentConditionEnhancingField getPaymentConditionField() {
		return paymentConditionField;
	}

	/**
	 * �x�����@�R���|�[�l���g�̎擾
	 * 
	 * @return paymentMethodField �x�����@�R���|�[�l���g
	 */
	public TPaymentMethodField getPaymentMethodField() {
		return paymentMethodField;
	}

	/**
	 * �U�o��s�R���|�[�l���g�̎擾
	 * 
	 * @return bankAccountField �U�o��s�R���|�[�l���g
	 */
	public TBankAccountEnhancingField getBankAccountField() {
		return bankAccountField;
	}

	/**
	 * �x���敪�R���|�[�l���g�̎擾
	 * 
	 * @return divisonComboBox �x���敪�R���|�[�l���g
	 */
	public TPaymentDivisionComboBox getDivisonComboBox() {
		return divisonComboBox;
	}

	/**
	 * �x�����R���|�[�l���g�̎擾
	 * 
	 * @return divisonComboBox �x�����R���|�[�l���g
	 */
	public TLabelPopupCalendar getCalendar() {
		return calendar;
	}

	/**
	 * �f�t�H���g�̎����������Z�b�g����
	 */
	public void setDefaultPaymentInformation() {
		this.paymentConditionField.setDefaultPaymentCondition();
		ctrl.paymentConditionScreenCtrl();
	}

	/**
	 * �l���N���A����
	 */
	public void clear() {
		paymentConditionField.setValue("");
		paymentMethodField.setValue("");
		bankAccountField.setValue("");
		calendar.setValue(null);
		divisonComboBox.getComboBox().setSelectedIndex(0);
	}

	private TPaymentConditionEnhancingField paymentConditionField;

	private TPaymentMethodField paymentMethodField;

	private TBankAccountEnhancingField bankAccountField;

	private TLabelPopupCalendar calendar;

	private TPaymentDivisionComboBox divisonComboBox;

	private TPanel basePanel;

	private PaymentInformationParameter parameter;

}
