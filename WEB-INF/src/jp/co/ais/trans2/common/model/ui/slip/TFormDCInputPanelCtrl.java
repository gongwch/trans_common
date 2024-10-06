package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.event.*;
import java.math.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.remark.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * �d�󖾍ד��̓R���g���[��
 * 
 * @author AIS
 */
public class TFormDCInputPanelCtrl extends TController {

	/** 0:�ؕ� */
	public static final int DR = Dc.DEBIT.value;

	/** 1:�ݕ� */
	public static final int CR = Dc.CREDIT.value;

	/** �d�󖾍ד��̓p�l�� */
	protected TFormDCInputPanel panel;

	/** ��v�n�ݒ� */
	protected AccountConfig conf = getCompany().getAccountConfig();

	/** ��ʉ� */
	protected Currency keyCurrency = conf.getKeyCurrency();

	/** ��ʉݏ����_���� */
	protected int keyDigit = keyCurrency.getDecimalPoint();

	/** �v�Z���W�b�N */
	protected TCalculator calculator = TCalculatorFactory.getCalculator();

	/** �f�t�H���g������ */
	protected boolean defaultTaxInnerDivision = false;

	/** ����Bean */
	protected SWK_DTL entity;

	/** �O���w������ */
	protected Customer customer;

	/** �O���w��Ј� */
	protected Employee employee;

	/** ���(�`�[���t) */
	protected Date baseDate;

	/** ���Z�d��(true:���Z�d��) */
	protected boolean isClosing;

	/** ���v�v�Z��CallBackListener */
	protected List<TCallBackListener> callBackListenerList = null;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param panel �d�󖾍ד��̓p�l��
	 */
	protected TFormDCInputPanelCtrl(TFormDCInputPanel panel) {

		this.panel = panel;

		// ������ʐ���
		initView();

		// �C�x���g�o�^
		addViewEvent();

		// �����\��
		init();
	}

	/**
	 * ��ʕ\����������
	 */
	protected void initView() {

		String companyCode = getCompany().getCode();

		// �ؕ��Ȗ�
		if (!conf.isUseDetailItem()) {

			panel.ctrlItemDebit.ctrlDetailItemReference.setVisible(false);
		}
		panel.ctrlItemDebit.getSearchCondition().setCompanyCode(companyCode);
		panel.ctrlItemDebit.getSearchCondition().setDepartmentCode(getUser().getDepartment().getCode());
		panel.ctrlItemDebit.ctrlItemReference.refleshEntity();

		// �ؕ������
		panel.ctrlTaxDebit.getSearchCondition().setCompanyCode(companyCode);

		// �Ǘ�1
		if (!conf.isUseManagement1()) {

			panel.ctrlManage1.setVisible(false);
		}
		panel.ctrlManage1.getSearchCondition().setCompanyCode(companyCode);

		// �����
		panel.ctrlCustomer.getSearchCondition().setCompanyCode(companyCode);

		// �E�v
		panel.ctrlRemark.getSearchCondition().setSlipRemark(false);
		panel.ctrlRemark.getSearchCondition().setSlipRowRemark(true);
		panel.ctrlRemark.getSearchCondition().setCompanyCode(companyCode);

		// �ݕ��Ȗ�
		if (!conf.isUseDetailItem()) {

			panel.ctrlItemCredit.ctrlDetailItemReference.setVisible(false);
		}
		panel.ctrlItemCredit.getSearchCondition().setCompanyCode(companyCode);
		panel.ctrlItemCredit.getSearchCondition().setDepartmentCode(getUser().getDepartment().getCode());
		panel.ctrlItemCredit.ctrlItemReference.refleshEntity();

		// �ݕ������
		panel.ctrlTaxCredit.getSearchCondition().setCompanyCode(companyCode);

		// ��ʉ݂̏����_�ݒ�
		panel.ctrlInputAmountDebit.setDecimalPoint(keyDigit);
		panel.ctrlTaxAmountDebit.setDecimalPoint(keyDigit);
		panel.ctrlKeyAmountDebit.setDecimalPoint(keyDigit);
		panel.ctrlInputAmountCredit.setDecimalPoint(keyDigit);
		panel.ctrlTaxAmountCredit.setDecimalPoint(keyDigit);
		panel.ctrlKeyAmountCredit.setDecimalPoint(keyDigit);

		// �ʉ݃��X�g
		initCurrencyList();
	}

	/**
	 * �ʉ݃��X�g������
	 */
	protected void initCurrencyList() {
		panel.ctrlCurrencyDebit.removeAllItems();
		panel.ctrlCurrencyCredit.removeAllItems();

		List<Currency> list = null;

		try {
			CurrencySearchCondition condition = new CurrencySearchCondition();
			condition.setCompanyCode(getCompanyCode());
			list = (List<Currency>) request(CurrencyManager.class, "get", condition);
		} catch (Exception ex) {
			errorHandler(ex);
		}

		if (list == null) {
			panel.ctrlCurrencyDebit.addTextValueItem(keyCurrency, keyCurrency.getCode());
			panel.ctrlCurrencyCredit.addTextValueItem(keyCurrency, keyCurrency.getCode());
		} else {
			for (Currency bean : list) {
				panel.ctrlCurrencyDebit.addTextValueItem(bean, bean.getCode());
				panel.ctrlCurrencyCredit.addTextValueItem(bean, bean.getCode());
			}
		}

		panel.ctrlCurrencyDebit.setSelectedText(keyCurrency.getCode());
		panel.ctrlCurrencyCredit.setSelectedText(keyCurrency.getCode());
	}

	/**
	 * �C�x���g�o�^
	 */
	protected void addViewEvent() {

		for (int i = DR; i <= CR; i++) {

			final int dc = i;

			// �v����
			panel.ctrlKCompany[dc].addCallBackListener(new TCallBackListener() {

				@Override
				public boolean afterVerify(boolean flag) {
					if (!flag) {
						return false;
					}
					changedCompany(dc);
					doAfterEvent();

					return true;
				}
			});

			// �v�㕔��
			panel.ctrlKDepartment[dc].addCallBackListener(new TCallBackListener() {

				@Override
				public boolean afterVerify(boolean flag) {
					if (!flag) {
						return false;
					}
					changedDepartment(dc);
					doAfterEvent();

					return true;
				}
			});

			// �Ȗ�
			panel.ctrlItem[dc].addCallBackListener(new TCallBackListener() {

				@Override
				public boolean afterVerify(boolean flag) {
					if (!flag) {
						return false;
					}
					changedItem(dc);
					doAfterEvent();

					return true;
				}
			});

			// �ʉ�
			panel.ctrlCurrency[dc].addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						changedCurrency(dc);
						doAfterEvent();
					}
				}
			});

			// �ʉ݃��[�g
			panel.ctrlRate[dc].setInputVerifier(new TInputVerifier() {

				@Override
				public boolean verifyCommit(JComponent comp) {
					if (!panel.ctrlRate[dc].isValueChanged2()) {
						return true;
					}

					changedRate(dc);
					doAfterEvent();
					return true;
				}
			});

			// �����
			panel.ctrlTax[dc].addCallBackListener(new TCallBackListener() {

				@Override
				public void after(boolean flag) {
					if (!flag) {
						return;
					}
					changedTax(dc);
					doAfterEvent();
				}
			});

			// �ŋ敪
			panel.ctrlTaxDivision[dc].addItemListener(new ItemListener() {

				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						changedTaxDivision(dc);
						doAfterEvent();
					}
				}
			});

			// ���͋��z
			panel.ctrlInputAmount[dc].setInputVerifier(new TInputVerifier() {

				@Override
				public boolean verifyCommit(JComponent comp) {
					if (!panel.ctrlInputAmount[dc].isValueChanged2()) {
						return true;
					}

					changedInputAmount(dc);
					doAfterEvent();
					return true;
				}
			});

			// ����ŋ��z
			panel.ctrlTaxAmount[dc].setInputVerifier(new TInputVerifier() {

				@Override
				public boolean verifyCommit(JComponent comp) {
					if (!panel.ctrlTaxAmount[dc].isValueChanged2()) {
						return true;
					}

					changedTaxAmount(dc);
					doAfterEvent();
					return true;
				}
			});

			// ����z
			panel.ctrlKeyAmount[dc].setInputVerifier(new TInputVerifier() {

				@Override
				public boolean verifyCommit(JComponent comp) {
					if (!panel.ctrlKeyAmount[dc].isValueChanged2()) {
						return true;
					}

					changedKeyAmount(dc);
					doAfterEvent();
					return true;
				}
			});

		}

		// ������
		panel.ctrlOccurDate.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!panel.ctrlOccurDate.isValueChanged2()) {
					return true;
				}

				changedOccurDate();
				doAfterEvent();
				return true;
			}
		});

	}

	/**
	 * �������
	 */
	protected void init() {

		this.entity = null;
		panel.lblTitle.setText("");

		// �N���A
		clearInput(DR);
		clearInput(CR);
	}

	// _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/

	/**
	 * ���͕��̂ݏ������
	 * 
	 * @param dc �ݎ�
	 */
	public void clearInput(int dc) {
		// �N���A
		panel.ctrlKCompany[dc].clear();
		panel.ctrlKDepartment[dc].clear();
		panel.ctrlItem[dc].clear();
		panel.ctrlOccurDate.clear();
		panel.ctrlTaxDivision[dc].setSelectedItemValue(defaultTaxInnerDivision ? TaxCalcType.IN : TaxCalcType.OUT);
		panel.ctrlRemark.clear();
		panel.ctrlInputAmount[dc].clear();

		// �����l
		setCompany(getCompany(), dc);

		if (isDefaultBlankDepartment()) {
			// �v�㕔��̏����l��null�̏ꍇ�A���[�U���̐ݒ�s�v
			setDepartment(null, dc);
		} else {
			// �f�t�H���g�v�㕔��
			setDepartment(getUser().getDepartment(), dc);
		}

		// ���͐���
		panel.ctrlKCompany[dc].setEditable(conf.isUseGroupAccount()); // �O���[�v��vOFF���͓��͕s��
		panel.ctrlKDepartment[dc].setEditable(true);
		panel.ctrlItem[dc].ctrlItemReference.setEditable(!isDefaultBlankDepartment());
		panel.ctrlOccurDate.setEditable(true);
		panel.ctrlTaxDivision[dc].setEnabled(true);
		panel.ctrlRemark.setEditable(true);
		panel.ctrlInputAmount[dc].setEditable(true);

		// �ȖژA���n
		clearInputForItem(dc);

		// �������f�t�H���g�l�ݒ�
		if (TSlipDetailPanelCtrl.allowDefaultOccurDate && panel.ctrlOccurDate.isVisible()
			&& panel.ctrlOccurDate.isEditable()) {
			panel.ctrlOccurDate.setValue(baseDate);
			changedOccurDate();
		}

	}

	/**
	 * @return true:�v�㕔�叉���u�����N
	 */
	protected boolean isDefaultBlankDepartment() {
		return TSlipDetailPanelCtrl.departmentDefaultBlank;
	}

	/**
	 * �Ȗڊ֘A���͕��̂ݏ������
	 * 
	 * @param dc �ݎ�
	 */
	public void clearInputForItem(int dc) {
		// �N���A
		panel.ctrlCurrency[dc].setSelectedText(keyCurrency.getCode());
		panel.ctrlRate[dc].clear();
		panel.ctrlTax[dc].clear();
		panel.ctrlTaxAmount[dc].clear();
		panel.ctrlInputAmount[dc].clear();
		panel.ctrlKeyAmount[dc].clear();
		panel.ctrlCustomer.clear();
		panel.ctrlEmployee.clear();
		panel.ctrlManage1.clear();
		panel.ctrlManage2.clear();
		panel.ctrlManage3.clear();
		panel.ctrlManage4.clear();
		panel.ctrlManage5.clear();
		panel.ctrlManage6.clear();
		panel.ctrlNonAcDetails.clear();

		// ���͐���
		panel.ctrlCurrency[dc].setEnabled(false);
		panel.ctrlRate[dc].setEditable(false);
		panel.ctrlTaxDivision[dc].setEnabled(false); // �ŕύX�s��
		panel.ctrlTax[dc].setEditable(false);
		panel.ctrlTaxAmount[dc].setEditable(false);
		panel.ctrlInputAmount[dc].setEditable(false);
		panel.ctrlKeyAmount[dc].setEditable(false);
		panel.ctrlCustomer.setEditable(false);
		panel.ctrlEmployee.setEditable(false);
		panel.ctrlManage1.setEditable(false);
		panel.ctrlManage2.setEditable(false);
		panel.ctrlManage3.setEditable(false);
		panel.ctrlManage4.setEditable(false);
		panel.ctrlManage5.setEditable(false);
		panel.ctrlManage6.setEditable(false);
		panel.ctrlNonAcDetails.setEditable(false);

		// �����l
		setCurrecy(keyCurrency, dc);

	}

	/**
	 * �v���ЕύX
	 * 
	 * @param dc �ݎ�
	 * @return false:�t�֐ݒ�NG
	 */
	protected boolean changedCompany(int dc) {
		try {
			if (panel.ctrlKCompany[dc].isEmpty()) {
				panel.ctrlKCompany[dc].setEntity(getCompany());
			}

			Company company = panel.ctrlKCompany[dc].getEntity();

			if (company == null) {
				return false;
			}

			String code = company.getCode();

			// ��Њԕt�փ}�X�^���ݒ肳��Ă��܂���B
			if (!getCompanyCode().equals(code) && !isAppropriateCompanyReplace(dc)) {
				showMessage("I00054");// ��Њԕt�փ}�X�^���ݒ肳��Ă��܂���B
				panel.ctrlKCompany[dc].code.requestFocus();
				panel.ctrlKCompany[dc].code.clearOldText();

				return false;
			}

			// ���t���b�V���̏ꍇ�A�N���A�s�v
			if (!isNotClearByCompany()) {

				// �N���A
				clearInput(dc);

				setCompany(company, dc);

				// �v�㕔��̓��O�C����ЈȊO�̓u�����N
				if (!getCompanyCode().equals(code)) {
					panel.ctrlKDepartment[dc].clear();
				}

				changedDepartment(dc);
			} else {

				// ��Аݒ�̂�
				setCompany(company, dc);
			}

			return true;

		} catch (TException ex) {
			errorHandler(ex);
			return true;
		}
	}

	/**
	 * @return true:�v���ЕύX��薾�׍��ڍŐV�擾���s���A���݂��Ă��Ȃ��ꍇ�̂݃N���A
	 */
	protected boolean isNotClearByCompany() {
		return true;
	}

	/**
	 * �v��ȖڃR�[�h�̃`�F�b�N
	 * 
	 * @param dc �ݎ�
	 * @return false:NG
	 * @throws TException
	 */
	protected boolean isAppropriateCompanyReplace(int dc) throws TException {
		String kcompany = panel.ctrlKCompany[dc].getCode();

		List<TransferConfig> list = (List<TransferConfig>) request(CompanyManager.class, "getTransferConfig",
			getCompanyCode(), kcompany);

		if (list == null) {
			return false;
		}

		return list.size() == 2;
	}

	/**
	 * �v�㕔��ύX
	 * 
	 * @param dc �ݎ�
	 */
	protected void changedDepartment(int dc) {
		Department dept = panel.ctrlKDepartment[dc].getEntity();

		// �ύX�O�ȖڃR�[�h
		String itemCode = panel.ctrlItem[dc].ctrlItemReference.getCode();

		setDepartment(dept, dc);

		// �ύX����������ȖڕύX�ʒm
		if (!itemCode.equals(panel.ctrlItem[dc].ctrlItemReference.getCode())) {
			changedItem(dc);
		}
	}

	/**
	 * �ȖڕύX
	 * 
	 * @param dc �ݎ�
	 * @return false:NG
	 */
	protected boolean changedItem(int dc) {
		Item item = panel.ctrlItem[dc].getEntity();

		// ���萧��
		int ait = CR;

		if (dc == DR) {
			ait = CR;
		} else {
			ait = DR;
		}

		clearInput(ait);

		if (item == null) {
			clearInputForItem(dc);
			changedCurrency(dc);
			return true;
		}

		// ���ʏ���
		{
			panel.ctrlKCompany[ait].code.setEditable(false);
			panel.ctrlKDepartment[ait].code.setEditable(false);
			panel.ctrlItem[ait].ctrlItemReference.code.setEditable(false);
			panel.ctrlKCompany[ait].clear();
			panel.ctrlKDepartment[ait].clear();
			panel.ctrlItem[ait].clear();
			panel.ctrlRate[ait].clear();
			panel.ctrlKeyAmount[ait].clear();
		}

		// �⏕�A���󂪂���ꍇ�́A�������𔽉f
		if (item.getSubItem() != null) {
			item = item.getSubItem();
		}

		if (item.getDetailItem() != null) {
			item = item.getDetailItem();
		}

		panel.ctrlInputAmount[dc].setEditable(true);

		// ���ʉݓ��̓t���O
		panel.ctrlCurrency[dc].setEnabled(item.isUseForeignCurrency());
		if (!item.isUseForeignCurrency()) {
			setCurrecy(keyCurrency, dc);
			changedCurrency(dc);
		}

		// �����
		if (item.isUseSalesTaxation() || item.isUsePurchaseTaxation()) {
			// ����ېœ��́A�܂��́A�d���ېœ��͂̏ꍇ�A���͉�
			panel.ctrlTaxDivision[dc].setEnabled(true);
			panel.ctrlTax[dc].setEditable(true);
			panel.ctrlTax[dc].setEntity(item.getConsumptionTax());
			panel.ctrlTax[dc].getSearchCondition().setHasSales(item.isUseSalesTaxation());
			panel.ctrlTax[dc].getSearchCondition().setHasPurcharse(item.isUsePurchaseTaxation());

		} else {
			panel.ctrlTaxDivision[dc].setEnabled(false);
			panel.ctrlTax[dc].getSearchCondition().setHasSales(false);
			panel.ctrlTax[dc].getSearchCondition().setHasPurcharse(false);
			panel.ctrlTax[dc].setEditable(false);
			panel.ctrlTax[dc].setEntity(null);
		}

		// �������ς���Ă�̂Ő������`�F�b�N
		panel.ctrlTax[dc].refleshEntity();
		changedTax(dc);

		// �����
		CustomerType customerType = item.getClientType();
		panel.ctrlCustomer.setEditable(customerType != CustomerType.NONE);
		panel.ctrlCustomer.getSearchCondition().setType(customerType);

		if (customerType != CustomerType.NONE) {

			// ����斢�ݒ�̏ꍇ�͏����l�Ƃ��āA�O���ݒ�̎������w��
			if (Util.isNullOrEmpty(panel.ctrlCustomer.getCode()) && customer != null) {
				panel.ctrlCustomer.setEntity(customer);
			}

			panel.ctrlCustomer.refleshEntity();

		} else {
			panel.ctrlCustomer.clear();
		}

		// �Ј�
		panel.ctrlEmployee.setEditable(item.isUseEmployee());

		if (!item.isUseEmployee()) {
			panel.ctrlEmployee.clear();

		} else if (panel.ctrlEmployee.isEmpty() && !TSlipDetailPanelCtrl.employeeDefaultBlank) {
			Company kcompany = panel.ctrlKCompany[dc].getEntity();

			if (kcompany.getCode().equals(getCompanyCode())) {
				Employee refEmployee = employee == null ? getUser().getEmployee() : employee;
				panel.ctrlEmployee.setEntity(refEmployee);
			}
		}

		// �Ǘ��P�`6
		panel.ctrlManage1.setEditable(item.isUseManagement1());
		panel.ctrlManage2.setEditable(item.isUseManagement2());
		panel.ctrlManage3.setEditable(item.isUseManagement3());
		panel.ctrlManage4.setEditable(item.isUseManagement4());
		panel.ctrlManage5.setEditable(item.isUseManagement5());
		panel.ctrlManage6.setEditable(item.isUseManagement6());

		if (!item.isUseManagement1()) panel.ctrlManage1.clear();
		if (!item.isUseManagement2()) panel.ctrlManage2.clear();
		if (!item.isUseManagement3()) panel.ctrlManage3.clear();
		if (!item.isUseManagement4()) panel.ctrlManage4.clear();
		if (!item.isUseManagement5()) panel.ctrlManage5.clear();
		if (!item.isUseManagement6()) panel.ctrlManage6.clear();

		// ���v����1�`3
		panel.ctrlNonAcDetails.setEditable(1, item.isUseNonAccount1());
		panel.ctrlNonAcDetails.setEditable(2, item.isUseNonAccount2());
		panel.ctrlNonAcDetails.setEditable(3, item.isUseNonAccount3());

		if (!item.isUseNonAccount1()) panel.ctrlNonAcDetails.clear(1);
		if (!item.isUseNonAccount2()) panel.ctrlNonAcDetails.clear(2);
		if (!item.isUseNonAccount3()) panel.ctrlNonAcDetails.clear(3);

		if (isAllowOccurDateBlank()) {
			// �������u�����N�\�̏ꍇ�A�Ȗڃt���O�ɂ�萧��
			if (item.isUseOccurDate()) {
				panel.ctrlOccurDate.setEditable(true);
			} else {
				panel.ctrlOccurDate.setEditable(false);
				panel.ctrlOccurDate.clear();
			}
		}

		return true;
	}

	/**
	 * �ʉݕύX
	 * 
	 * @param dc �ݎ�
	 */
	protected void changedCurrency(int dc) {
		Currency currency = (Currency) panel.ctrlCurrency[dc].getSelectedItemValue();

		setCurrecy(currency, dc);

		// ���[�g
		panel.ctrlRate[dc].setNumberValue(getCurrencyRate(dc));

		changedRate(dc);
	}

	/**
	 * �������̕ύX
	 */
	protected void changedOccurDate() {

		int dc = DR;

		if (!Util.isNullOrEmpty(panel.ctrlItem[CR].ctrlItemReference.getCode())) {
			dc = CR;
		}

		// ���[�g�ύX
		BigDecimal old = panel.ctrlRate[dc].getBigDecimal();
		BigDecimal nuw = getCurrencyRate(dc);

		// �擾���[�g������
		if (nuw == null) {
			panel.ctrlRate[dc].clear();
			return;
		}

		// ���[�g�l�ɕύX���Ȃ�
		if (old.compareTo(nuw) == 0) {
			return;
		}

		panel.ctrlRate[dc].setNumberValue(nuw);
		changedRate(dc);
	}

	/**
	 * �ʉ݃��[�g �ύX
	 * 
	 * @param dc �ݎ�
	 */
	protected void changedRate(int dc) {
		// �M�݊��Z
		Currency currency = (Currency) panel.ctrlCurrency[dc].getSelectedItemValue();

		if (currency == null || panel.ctrlInputAmount[dc].isEmpty()) {
			return;
		}

		BigDecimal inAmount = panel.ctrlInputAmount[dc].getBigDecimal();
		BigDecimal rate = panel.ctrlRate[dc].getBigDecimal();
		panel.ctrlKeyAmount[dc].setNumber(convertKeyAmount(inAmount, currency, rate));

		// �����
		changedTax(dc);

	}

	/**
	 * ���͋��z�̕ύX
	 * 
	 * @param dc �ݎ�
	 */
	protected void changedInputAmount(int dc) {

		// �����
		changedTax(dc);
	}

	/**
	 * ����Ŋz�̕ύX
	 * 
	 * @param dc �ݎ�
	 */
	@SuppressWarnings("unused")
	protected void changedTaxAmount(int dc) {
		//
	}

	/**
	 * ����z�̕ύX
	 * 
	 * @param dc �ݎ�
	 */
	@SuppressWarnings("unused")
	protected void changedKeyAmount(int dc) {
		//
	}

	/**
	 * ����/�O�� �ؑ�
	 * 
	 * @param dc �ݎ�
	 */
	protected void changedTaxDivision(int dc) {
		changedTax(dc);
	}

	/**
	 * ����ŕύX
	 * 
	 * @param dc �ݎ�
	 */
	protected void changedTax(int dc) {

		Currency currency = null;

		try {
			// ����Ōv�Z
			Company kcompany = panel.ctrlKCompany[dc].getEntity();
			currency = (Currency) panel.ctrlCurrency[dc].getSelectedItemValue();
			ConsumptionTax tax = panel.ctrlTax[dc].getEntity();
			BigDecimal rate = panel.ctrlRate[dc].getBigDecimalValue();

			// ���[�g0�Ȃ����łȂ�
			if (kcompany == null || tax == null || DecimalUtil.isNullOrZero(tax.getRate())
				|| DecimalUtil.isNullOrZero(rate)) {
				panel.ctrlTaxAmount[dc].clear();
				panel.ctrlTaxAmount[dc].setEditable(false);
				return;
			}

			// �ʉ݂Ȃ�
			if (currency == null) {
				return;
			}

			AccountConfig kconf = kcompany.getAccountConfig();

			// ����A�d���̏ꍇ�͓��͉�
			switch (tax.getTaxType()) {
				case NOT:
					panel.ctrlTaxAmount[dc].clear();
					panel.ctrlTaxAmount[dc].setEditable(false);
					break;

				case SALES:
				case PURCHAESE:
					panel.ctrlTaxAmount[dc].setEditable(true);

					BigDecimal inAmount = panel.ctrlInputAmount[dc].getBigDecimal();

					if (DecimalUtil.isZero(inAmount)) {
						panel.ctrlTaxAmount[dc].clear();
						break;
					}

					TTaxCalculation param = TCalculatorFactory.createTaxCalculation();
					param.setInside(panel.ctrlTaxDivision[dc].getSelectedItemValue() == TaxCalcType.IN); // ����or�O��
					param.setAmount(inAmount); // �Ώۋ��z
					param.setTax(tax); // ����ŏ��
					param.setDigit(currency.getDecimalPoint()); // �����_����
					param.setReceivingFunction(kconf.getReceivingFunction()); // �؎�
					param.setPaymentFunction(kconf.getPaymentFunction()); // ����
					panel.ctrlTaxAmount[dc].setNumber(calculator.calculateTax(param));
					break;
			}

		} finally {
			if (currency != null) {
				// �M�݊��Z
				BigDecimal inAmount = panel.ctrlInputAmount[dc].getBigDecimal();
				BigDecimal rate = panel.ctrlRate[dc].getBigDecimal();
				panel.ctrlKeyAmount[dc].setNumber(convertKeyAmount(inAmount, currency, rate));
			}
		}
	}

	/**
	 * ����ݒ�(�L��������)
	 * 
	 * @param baseDate ���
	 */
	protected void setBaseDate(Date baseDate) {
		this.baseDate = baseDate;

		// �L������;
		panel.ctrlItemDebit.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlItemCredit.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlTaxDebit.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlTaxCredit.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlRemark.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlCustomer.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlEmployee.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage1.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage2.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage3.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage4.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage5.getSearchCondition().setValidTerm(baseDate);
		panel.ctrlManage6.getSearchCondition().setValidTerm(baseDate);

	}

	/**
	 * ���Z�d��ݒ�
	 * 
	 * @param isClosing true:���Z�d��Afalse:�ʏ�d��
	 */
	public void setClosingEntry(boolean isClosing) {
		this.isClosing = isClosing;
	}

	/**
	 * �O���w�������ݒ�(�Œ�\���p)
	 * 
	 * @param customer �O���w������
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * �O���w��Ј���ݒ�(�����\���p)
	 * 
	 * @param employee �Ј�
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @return true:�O���[�v��v(��ʉ݈قȂ�v���Љ�)
	 */
	public static boolean isGroupAccounting() {
		return TSlipDetailPanelCtrl.groupAccounting;
	}

	/**
	 * @return true:�������u�����N�\
	 */
	public boolean isAllowOccurDateBlank() {
		return TSlipDetailPanelCtrl.allowOccurDateBlank && !conf.isUseIfrs();
	}

	/**
	 * @param dtl
	 * @return true:�������u�����N�̏ꍇ�A�`�[���t��o�^����
	 */
	public boolean isAllowEntryDefaultOccurDate(SWK_DTL dtl) {
		if (dtl.getHAS_DATE() != null) {
			return false;
		}

		if (!dtl.isUseItemOccurDate()) {
			return false;
		}
		return TSlipDetailPanelCtrl.allowEntryDefaultOccurDate;
	}

	/**
	 * ��Аݒ�
	 * 
	 * @param kcompany ��ЃR�[�h
	 * @param dc �ݎ�
	 */
	protected void setCompany(Company kcompany, int dc) {
		panel.ctrlKCompany[dc].setEntity(kcompany);

		String code = kcompany.getCode();

		// �����ύX
		panel.ctrlKDepartment[dc].getSearchCondition().setCompanyCode(code);
		panel.ctrlItem[dc].getSearchCondition().setCompanyCode(code);

		panel.ctrlTax[dc].getSearchCondition().setCompanyCode(code);
		panel.ctrlRemark.getSearchCondition().setCompanyCode(code);
		panel.ctrlCustomer.getSearchCondition().setCompanyCode(code);
		panel.ctrlEmployee.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage1.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage2.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage3.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage4.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage5.getSearchCondition().setCompanyCode(code);
		panel.ctrlManage6.getSearchCondition().setCompanyCode(code);

		AccountConfig kconf = kcompany.getAccountConfig();

		// ���̃Z�b�g
		panel.ctrlItem[dc].ctrlItemReference.btn.setText(kconf.getItemName());
		panel.ctrlItem[dc].ctrlSubItemReference.btn.setText(kconf.getSubItemName());
		panel.ctrlItem[dc].ctrlDetailItemReference.btn.setText(kconf.getDetailItemName());
		panel.ctrlManage1.btn.setText(kconf.getManagement1Name());
		panel.ctrlManage2.btn.setText(kconf.getManagement2Name());
		panel.ctrlManage3.btn.setText(kconf.getManagement3Name());
		panel.ctrlManage4.btn.setText(kconf.getManagement4Name());
		panel.ctrlManage5.btn.setText(kconf.getManagement5Name());
		panel.ctrlManage6.btn.setText(kconf.getManagement6Name());

		// ���p���邩
		panel.ctrlItem[dc].ctrlDetailItemReference.code.setVisible(kconf.isUseDetailItem());
		panel.ctrlItem[dc].ctrlDetailItemReference.name.setVisible(kconf.isUseDetailItem());
		panel.ctrlManage1.setVisible(kconf.isUseManagement1());
		panel.ctrlManage2.setVisible(kconf.isUseManagement2());
		panel.ctrlManage3.setVisible(kconf.isUseManagement3());
		panel.ctrlManage4.setVisible(kconf.isUseManagement4());
		panel.ctrlManage5.setVisible(kconf.isUseManagement5());
		panel.ctrlManage6.setVisible(kconf.isUseManagement6());

		// �^�C�g���ݒ�
		if (dc == DR) {
			String title = createItemTitle(kconf, DR);
			panel.lblDebitItemTitle.setText(title);
			panel.lblDebitItemTitle.setToolTipText(title);
		} else {
			String title = createItemTitle(kconf, CR);
			panel.lblCreditItemTitle.setText(title);
			panel.lblCreditItemTitle.setToolTipText(title);
		}

		// ���t���b�V���̏ꍇ�A�e�l�Ď擾
		if (isNotClearByCompany()) {

			boolean isBreak = false;

			panel.ctrlKDepartment[dc].refleshEntity();
			if (panel.ctrlKDepartment[dc].getEntity() == null) {
				changedDepartment(dc);

				isBreak = true;
			}

			if (!isBreak) {
				panel.ctrlItem[dc].refleshGroupEntity();

				if (panel.ctrlItem[dc].getEntity() == null) {
					isBreak = true;
				}

				changedItem(dc);
			}

			if (!isBreak) {
				Remark oldRemark = (Remark) panel.ctrlRemark.getController().getUnspecifiedEntity();

				panel.ctrlRemark.refleshEntity();
				panel.ctrlCustomer.refleshEntity();
				panel.ctrlEmployee.refleshEntity();
				panel.ctrlManage1.refleshEntity();
				panel.ctrlManage2.refleshEntity();
				panel.ctrlManage3.refleshEntity();
				panel.ctrlManage4.refleshEntity();
				panel.ctrlManage5.refleshEntity();
				panel.ctrlManage6.refleshEntity();
				panel.ctrlTax[dc].refleshEntity();

				if (panel.ctrlRemark.getEntity() != null) {
					panel.ctrlRemark.setNames(panel.ctrlRemark.getEntity().getName());
				} else if (oldRemark != null && Util.isNullOrEmpty(oldRemark.getCode())) {
					panel.ctrlRemark.setEntity(oldRemark);
				}
				if (panel.ctrlCustomer.getEntity() != null) {
					panel.ctrlCustomer.setNames(panel.ctrlCustomer.getEntity().getNames());
				}
				if (panel.ctrlEmployee.getEntity() != null) {
					panel.ctrlEmployee.setNames(panel.ctrlEmployee.getEntity().getNames());
				}
				if (panel.ctrlManage1.getEntity() != null) {
					panel.ctrlManage1.setNames(panel.ctrlManage1.getEntity().getNames());
				}
				if (panel.ctrlManage2.getEntity() != null) {
					panel.ctrlManage2.setNames(panel.ctrlManage2.getEntity().getNames());
				}
				if (panel.ctrlManage3.getEntity() != null) {
					panel.ctrlManage3.setNames(panel.ctrlManage3.getEntity().getNames());
				}
				if (panel.ctrlManage4.getEntity() != null) {
					panel.ctrlManage4.setNames(panel.ctrlManage4.getEntity().getNames());
				}
				if (panel.ctrlManage5.getEntity() != null) {
					panel.ctrlManage5.setNames(panel.ctrlManage5.getEntity().getNames());
				}
				if (panel.ctrlManage6.getEntity() != null) {
					panel.ctrlManage6.setNames(panel.ctrlManage6.getEntity().getNames());
				}
				if (panel.ctrlTax[dc].getEntity() != null) {
					panel.ctrlTax[dc].setNames(panel.ctrlTax[dc].getEntity().getNames());
				}
			}
		}

	}

	/**
	 * �Ȗڃ^�C�g����������
	 * 
	 * @param kconf
	 * @param dc
	 * @return �^�C�g���ݒ�
	 */
	protected String createItemTitle(AccountConfig kconf, int dc) {
		// �^�C�g���ݒ�
		// �u�ؕ� ���/����/�Ȗ�/�⏕/����v
		// �u�ݕ� ���/����/�Ȗ�/�⏕/����v
		StringBuilder sb = new StringBuilder();

		if (dc == DR) {
			sb.append(getWord("C00080")); // �ؕ�
		} else {
			sb.append(getWord("C00068")); // �ݕ�
		}
		sb.append("  "); // �]��
		sb.append(getWord("C00053")); // ���
		sb.append("/"); // sep
		sb.append(getWord("C00467")); // ����
		sb.append("/"); // sep
		sb.append(kconf.getItemName()); // �Ȗ�
		sb.append("/"); // sep
		sb.append(kconf.getSubItemName()); // �⏕
		if (kconf.isUseDetailItem()) {
			sb.append("/"); // sep
			sb.append(kconf.getDetailItemName()); // ����
		}
		return sb.toString();
	}

	/**
	 * �v�㕔��ݒ�
	 * 
	 * @param dept �v�㕔��
	 * @param dc �ݎ�
	 */
	protected void setDepartment(Department dept, int dc) {
		panel.ctrlKDepartment[dc].setEntity(dept);

		if (dept == null) {
			panel.ctrlItem[dc].setEntity(null);
			panel.ctrlItem[dc].ctrlItemReference.setEditable(false);
			return;
		}

		// �Ȗڏ�����
		panel.ctrlItem[dc].ctrlItemReference.setEditable(true);

		String code = dept.getCode();

		String oldDeptCode = panel.ctrlItem[dc].getSearchCondition().getDepartmentCode();

		if (!code.equals(Util.avoidNull(oldDeptCode))) {
			// ����R�[�h���ύX�ɂȂ����ꍇ�A������ύX
			panel.ctrlItem[dc].getSearchCondition().setDepartmentCode(code);

			// �����ύX�ɂ��A�������`�F�b�N��OK�Ȃ�c��
			if (!panel.ctrlItem[dc].isEmpty()) {
				if (!isNotClearByCompany()) {
					panel.ctrlItem[dc].refleshEntity();
				} else {
					panel.ctrlItem[dc].refleshGroupEntity();
				}
			}
		}
	}

	/**
	 * �ʉݐݒ�
	 * 
	 * @param currency �ʉ�
	 * @param dc �ݎ�
	 */
	protected void setCurrecy(Currency currency, int dc) {

		if (currency == null) {
			panel.ctrlCurrency[dc].setSelectedText(keyCurrency.getCode());
			panel.ctrlRate[dc].clear();
			panel.ctrlRate[dc].setEditable(false);
			panel.ctrlKeyAmount[dc].clear();
			panel.ctrlKeyAmount[dc].setEditable(false);
			return;
		}

		panel.ctrlCurrency[dc].setSelectedText(currency.getCode());

		String currencyCode = currency.getCode();
		boolean isKey = keyCurrency.getCode().equals(currencyCode);

		// ���[�g
		panel.ctrlRate[dc].setEditable(!isKey);
		panel.ctrlRate[dc].setNumberValue(isKey ? BigDecimal.ONE : null);
		changedRate(dc);

		// ����z
		panel.ctrlKeyAmount[dc].setEditable(!isKey);

		// �����_�ύX
		int digit = currency.getDecimalPoint();

		// �e�R���|�[�l���g
		panel.ctrlInputAmount[dc].setDecimalPoint(digit);
		panel.ctrlTaxAmount[dc].setDecimalPoint(digit);
		changedTax(dc);
	}

	// _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/

	/**
	 * ���ׂ�Entity�`���ŃZ�b�g����.
	 * 
	 * @param dtl �f�[�^
	 */
	protected void setEntity(SWK_DTL dtl) {

		// �N���A
		init();

		if (dtl == null) {
			return;
		}

		int dc = dtl.isDR() ? DR : CR;

		setCompany(dtl.getAppropriateCompany(), dc); // �v����
		setDepartment(dtl.getDepartment(), dc); // �v�㕔��

		Item item = dtl.getItem();
		panel.ctrlItem[dc].setEntity(item != null ? item.clone() : null); // �Ȗ�
		changedItem(dc);

		setCurrecy(dtl.getCurrency(), dc); // �ʉ݃R�[�h

		panel.ctrlTax[dc].setEntity(dtl.getTax()); // ��
		changedTax(dc);

		if (dtl.isErasing()) {

			// �s���ʕs��
			panel.btnRowCopy.setEnabled(false);
			panel.btnRowCopyReverse.setEnabled(false);

			// ���͕s��
			panel.ctrlKCompany[dc].setEditable(false);
			panel.ctrlKDepartment[dc].setEditable(false);
			panel.ctrlItem[dc].ctrlItemReference.setEditable(false);
			panel.ctrlItem[dc].ctrlSubItemReference.setEditable(false);
			panel.ctrlItem[dc].ctrlDetailItemReference.setEditable(false);
			panel.ctrlOccurDate.setEditable(false);
			panel.ctrlTaxDivision[dc].setEnabled(false);

			panel.ctrlCurrency[dc].setEditable(false);
			panel.ctrlRate[dc].setEditable(false);
			panel.ctrlTax[dc].setEditable(false);
			panel.ctrlTaxAmount[dc].setEditable(false);
			panel.ctrlCustomer.setEditable(false);
			panel.ctrlEmployee.setEditable(false);
			panel.ctrlManage1.setEditable(false);
			panel.ctrlManage2.setEditable(false);
			panel.ctrlManage3.setEditable(false);
			panel.ctrlManage4.setEditable(false);
			panel.ctrlManage5.setEditable(false);
			panel.ctrlManage6.setEditable(false);
			panel.ctrlNonAcDetails.setEditable(false);

			if (dtl.isAPErasing() || (TSlipDetailPanelCtrl.isUseBS && dtl.isBSErasing())) {
				// AP�ABS�̏ꍇ�͋��z���͕s��
				panel.ctrlInputAmount[dc].setEditable(false);
				panel.ctrlKeyAmount[dc].setEditable(false);
			}
		}

		// �ؕ��ŋ敪
		if (dtl.getSWK_ZEI_KBN() == SWK_DTL.ZEI_KBN.TAX_OUT) {
			panel.ctrlTaxDivision[dc].setSelectedItemValue(TaxCalcType.OUT);

		} else if (dtl.getSWK_ZEI_KBN() == SWK_DTL.ZEI_KBN.TAX_IN) {
			panel.ctrlTaxDivision[dc].setSelectedItemValue(TaxCalcType.IN);

		} else {
			panel.ctrlTaxDivision[dc].setSelectedItemValue(defaultTaxInnerDivision ? TaxCalcType.IN : TaxCalcType.OUT);
		}

		panel.ctrlKeyAmount[dc].setNumber(dtl.getSWK_KIN()); // ���z
		panel.ctrlRate[dc].setNumberValue(dtl.getSWK_CUR_RATE()); // �ʉ݃��[�g
		panel.ctrlInputAmount[dc].setNumber(dtl.getSWK_IN_KIN()); // ���͋��z

		// ���͏���Ŋz
		if (dtl.getTax() == null || DecimalUtil.isNullOrZero(dtl.getTax().getRate())) {
			panel.ctrlTaxAmount[dc].clear();

		} else {
			panel.ctrlTaxAmount[dc].setNumber(dtl.getSWK_IN_ZEI_KIN());
		}

		panel.ctrlKeyAmount[dc].setNumber(dtl.getSWK_KIN()); // ���z
		panel.ctrlRemark.setCode(dtl.getSWK_GYO_TEK_CODE()); // �s�E�v�R�[�h
		panel.ctrlRemark.setNames(dtl.getSWK_GYO_TEK()); // �s�E�v
		panel.ctrlCustomer.setCode(dtl.getSWK_TRI_CODE()); // �����R�[�h
		panel.ctrlCustomer.setNames(dtl.getSWK_TRI_NAME_S()); // �����
		panel.ctrlEmployee.setCode(dtl.getSWK_EMP_CODE()); // �Ј��R�[�h
		panel.ctrlEmployee.setNames(dtl.getSWK_EMP_NAME_S()); // �Ј�
		panel.ctrlManage1.setCode(dtl.getSWK_KNR_CODE_1()); // �Ǘ�1�R�[�h
		panel.ctrlManage1.setNames(dtl.getSWK_KNR_NAME_S_1()); // �Ǘ�1
		panel.ctrlManage2.setCode(dtl.getSWK_KNR_CODE_2()); // �Ǘ�2�R�[�h
		panel.ctrlManage2.setNames(dtl.getSWK_KNR_NAME_S_2()); // �Ǘ�2
		panel.ctrlManage3.setCode(dtl.getSWK_KNR_CODE_3()); // �Ǘ�3�R�[�h
		panel.ctrlManage3.setNames(dtl.getSWK_KNR_NAME_S_3()); // �Ǘ�3
		panel.ctrlManage4.setCode(dtl.getSWK_KNR_CODE_4()); // �Ǘ�4�R�[�h
		panel.ctrlManage4.setNames(dtl.getSWK_KNR_NAME_S_4()); // �Ǘ�4
		panel.ctrlManage5.setCode(dtl.getSWK_KNR_CODE_5()); // �Ǘ�5�R�[�h
		panel.ctrlManage5.setNames(dtl.getSWK_KNR_NAME_S_5()); // �Ǘ�5
		panel.ctrlManage6.setCode(dtl.getSWK_KNR_CODE_6()); // �Ǘ�6�R�[�h
		panel.ctrlManage6.setNames(dtl.getSWK_KNR_NAME_S_6()); // �Ǘ�6
		panel.ctrlOccurDate.setValue(getOccurDate(dtl)); // ������
		panel.ctrlNonAcDetails.setValue(1, dtl.getSWK_HM_1()); // ���v����1
		panel.ctrlNonAcDetails.setValue(2, dtl.getSWK_HM_2()); // ���v����2
		panel.ctrlNonAcDetails.setValue(3, dtl.getSWK_HM_3()); // ���v����3

		this.entity = dtl;
		if (dtl.getAR_ZAN() != null) {
			panel.lblTitle.setText("������");
		} else if (dtl.isAPErasing()) {
			panel.lblTitle.setText("������");
		} else if (dtl.isBSErasing()) {
			panel.lblTitle.setText("BS�������");
		}
	}

	/**
	 * ��ʂ֓\��t���鎞�������̒l
	 * 
	 * @param dtl
	 * @return ������
	 */
	protected Date getOccurDate(SWK_DTL dtl) {

		if (isAllowOccurDateBlank()) {

			if (dtl.getItem() != null && !dtl.isUseItemOccurDate()) {
				// �Ȗڂ����������g�p�̏ꍇ�A�������̓u�����N�ɂ���
				return null;
			}
		}

		return dtl.getHAS_DATE();
	}

	/**
	 * @return ���׎d��
	 */
	protected SWK_DTL createSlipDetail() {
		return new SWK_DTL();
	}

	/**
	 * ���ׂ�Entity�`���ŕԂ�.
	 * 
	 * @return Entity
	 */
	protected SWK_DTL getEntity() {

		int dc = DR;

		if (!panel.ctrlItemDebit.ctrlItemReference.code.isEmpty()) {
			// ��
			dc = DR;

		} else if (!panel.ctrlItemCredit.ctrlItemReference.code.isEmpty()) {
			// ��
			dc = CR;

		} else {
			// �Ȃ�
			return null;
		}

		SWK_DTL dtl = this.entity;
		if (dtl == null) {
			dtl = createSlipDetail();
		}

		dtl.setDC(Dc.getDc(dc));

		dtl.setAppropriateCompany(panel.ctrlKCompany[dc].getEntity()); // �v����
		dtl.setDepartment(panel.ctrlKDepartment[dc].getEntity()); // �v�㕔��
		dtl.setItem(panel.ctrlItem[dc].getInputtedEntity()); // �Ȗ�
		dtl.setTax(panel.ctrlTax[dc].getEntity()); // ��
		dtl.setCurrency((Currency) panel.ctrlCurrency[dc].getSelectedItemValue()); // �ʉ�

		// ��
		ConsumptionTax tax = dtl.getTax();
		if (tax == null || tax.getTaxType() == TaxType.NOT || DecimalUtil.isNullOrZero(tax.getRate())) {
			dtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE);

		} else {
			dtl.setSWK_ZEI_KBN(((TaxCalcType) panel.ctrlTaxDivision[dc].getSelectedItemValue()).value); // ��
		}

		dtl.setSWK_CUR_RATE(panel.ctrlRate[dc].getBigDecimal()); // �ʉ݃��[�g
		dtl.setSWK_IN_KIN(panel.ctrlInputAmount[dc].getBigDecimal()); // ���͋��z

		BigDecimal inTaxAmount = panel.ctrlTaxAmount[dc].getBigDecimal(); // ���͏����
		dtl.setSWK_IN_ZEI_KIN(inTaxAmount); // ���͏���Ŋz

		dtl.setSWK_ZEI_KIN(convertKeyTaxAmount(inTaxAmount, dc)); // ����Ŋz(�M��)
		dtl.setSWK_KIN(panel.ctrlKeyAmount[dc].getBigDecimal()); // ����z

		dtl.setSWK_GYO_TEK_CODE(panel.ctrlRemark.getCode()); // �s�E�v�R�[�h
		dtl.setSWK_GYO_TEK(panel.ctrlRemark.getNames()); // �s�E�v
		dtl.setSWK_TRI_CODE(panel.ctrlCustomer.getCode()); // �����R�[�h
		dtl.setSWK_TRI_NAME(panel.ctrlCustomer.getEntity() == null ? "" : panel.ctrlCustomer.getEntity().getName());
		dtl.setSWK_TRI_NAME_S(panel.ctrlCustomer.getNames());
		dtl.setSWK_EMP_CODE(panel.ctrlEmployee.getCode()); // �Ј��R�[�h
		dtl.setSWK_EMP_NAME(panel.ctrlEmployee.getEntity() == null ? "" : panel.ctrlEmployee.getEntity().getName());
		dtl.setSWK_EMP_NAME_S(panel.ctrlEmployee.getNames());
		dtl.setSWK_KNR_CODE_1(panel.ctrlManage1.getCode()); // �Ǘ�1�R�[�h
		dtl.setSWK_KNR_NAME_1(panel.ctrlManage1.getEntity() == null ? "" : panel.ctrlManage1.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_1(panel.ctrlManage1.getNames());
		dtl.setSWK_KNR_CODE_2(panel.ctrlManage2.getCode()); // �Ǘ�2�R�[�h
		dtl.setSWK_KNR_NAME_2(panel.ctrlManage2.getEntity() == null ? "" : panel.ctrlManage2.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_2(panel.ctrlManage2.getNames());
		dtl.setSWK_KNR_CODE_3(panel.ctrlManage3.getCode()); // �Ǘ�3�R�[�h
		dtl.setSWK_KNR_NAME_3(panel.ctrlManage3.getEntity() == null ? "" : panel.ctrlManage3.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_3(panel.ctrlManage3.getNames());
		dtl.setSWK_KNR_CODE_4(panel.ctrlManage4.getCode()); // �Ǘ�4�R�[�h
		dtl.setSWK_KNR_NAME_4(panel.ctrlManage4.getEntity() == null ? "" : panel.ctrlManage4.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_4(panel.ctrlManage4.getNames());
		dtl.setSWK_KNR_CODE_5(panel.ctrlManage5.getCode()); // �Ǘ�5�R�[�h
		dtl.setSWK_KNR_NAME_5(panel.ctrlManage5.getEntity() == null ? "" : panel.ctrlManage5.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_5(panel.ctrlManage5.getNames());
		dtl.setSWK_KNR_CODE_6(panel.ctrlManage6.getCode()); // �Ǘ�6�R�[�h
		dtl.setSWK_KNR_NAME_6(panel.ctrlManage6.getEntity() == null ? "" : panel.ctrlManage6.getEntity().getName());
		dtl.setSWK_KNR_NAME_S_6(panel.ctrlManage6.getNames());
		dtl.setSWK_HM_1(panel.ctrlNonAcDetails.getValue(1)); // ���v����1
		dtl.setSWK_HM_2(panel.ctrlNonAcDetails.getValue(2)); // ���v����2
		dtl.setSWK_HM_3(panel.ctrlNonAcDetails.getValue(3)); // ���v����3

		// ������
		Date occurDate = panel.ctrlOccurDate.getValue();
		dtl.setHAS_DATE(occurDate); // ������

		return dtl;
	}

	/**
	 * ���͒l�`�F�b�N
	 * 
	 * @return false:NG\
	 */
	protected boolean checkInput() {

		int dc = DR;

		if (!panel.ctrlItem[DR].ctrlItemReference.code.isEmpty()) {
			dc = DR;

		} else if (!panel.ctrlItem[CR].ctrlItemReference.code.isEmpty()) {
			dc = CR;

		} else {
			return true;
		}

		// �v�㕔��
		if (!checkInputBlank(panel.ctrlKDepartment[dc].code, panel.ctrlKDepartment[dc].btn.getText())) {
			return false;
		}

		// �Ȗ�
		TItemGroup item = panel.ctrlItem[dc];
		if (!checkInputBlank(item.ctrlItemReference.code, item.ctrlItemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(item.ctrlSubItemReference.code, item.ctrlSubItemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(item.ctrlDetailItemReference.code, item.ctrlDetailItemReference.btn.getText())) {
			return false;
		}

		Currency currency = (Currency) panel.ctrlCurrency[dc].getSelectedItemValue();
		if (currency == null) {
			currency = keyCurrency;
		}

		// ������(�u�����N�\�̏ꍇ�A�`�F�b�N�s�v)
		boolean checkOccurDate = !isAllowOccurDateBlank();
		if (item.getEntity() != null) {
			Item itemBean = item.getEntity().getPreferedItem();
			if (itemBean.isUseOccurDate() && !Util.isNullOrEmpty(currency.getCode())
				&& !keyCurrency.getCode().equals(currency.getCode())) {
				// �u�����N�\�A����ʉ݈ȊO�̏ꍇ�͉Ȗڂ̃t���O�ɏ]���ă`�F�b�N
				checkOccurDate = true;
			}
		}

		if (checkOccurDate
			&& !checkInputBlank(panel.ctrlOccurDate.getCalendar(), panel.ctrlOccurDate.getLabel().getText())) {
			return false;
		}

		// ���[�g
		if (!checkInputBlank(panel.ctrlRate[dc].getField(), panel.ctrlRate[dc].getLabel().getText())) {
			return false;
		}

		// �ŋ敪
		if (!checkInputBlank(panel.ctrlTax[dc].code, panel.ctrlTax[dc].btn.getText())) {
			return false;
		}

		// ���͋��z
		BigDecimal inKin = panel.ctrlInputAmount[dc].getBigDecimal();

		// ��ʉ݂ƈقȂ�ʉ݂̏ꍇ�́A0��F�߂�.
		if (keyCurrency.getCode().equals(currency.getCode())) {
			if (DecimalUtil.isZero(inKin)) {
				showMessage("I00037", "C00574");
				panel.ctrlInputAmount[dc].requestFocus();
				return false;
			}
		}

		// ����Ŋz (0�ł�OK)
		BigDecimal taxInKin = panel.ctrlTaxAmount[dc].getBigDecimal();
		if (!DecimalUtil.isZero(taxInKin)) {

			if (!DecimalUtil.isZero(inKin) && inKin.signum() != taxInKin.signum()) {
				showMessage("I00126");// ���͋��z�Ə���Ŋz�̕������قȂ�܂��B
				panel.ctrlTaxAmount[dc].requestFocus();
				return false;
			}

			if (inKin.abs().compareTo(taxInKin.abs()) <= 0) {
				showMessage("I00127");// ����Ŋz�͓��͋��z�����ł���K�v������܂��B
				panel.ctrlTaxAmount[dc].requestFocus();
				return false;
			}
		}

		// �M�݋��z
		BigDecimal kin = panel.ctrlKeyAmount[dc].getBigDecimal();
		if (DecimalUtil.isZero(kin)) {
			showMessage("I00037", "C00576");// {0}����͂��Ă��������B
			panel.ctrlKeyAmount[dc].requestFocus();

			return false;
		}

		if (!DecimalUtil.isZero(inKin) && inKin.signum() != kin.signum()) {
			showMessage("I00125");// ���͋��z�ƖM�݋��z�̕������قȂ�܂��B
			panel.ctrlKeyAmount[dc].requestFocus();
			return false;
		}

		// �����
		if (!checkInputBlank(panel.ctrlCustomer.code, panel.ctrlCustomer.btn.getText())) {
			return false;
		}
		// �Ј�
		if (!checkInputBlank(panel.ctrlEmployee.code, panel.ctrlEmployee.btn.getText())) {
			return false;
		}
		// �Ǘ�1�`6
		if (!checkInputBlank(panel.ctrlManage1.code, panel.ctrlManage1.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage2.code, panel.ctrlManage2.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage3.code, panel.ctrlManage3.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage4.code, panel.ctrlManage4.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage5.code, panel.ctrlManage5.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(panel.ctrlManage6.code, panel.ctrlManage6.btn.getText())) {
			return false;
		}

		return true;
	}

	/**
	 * ���̓u�����N�`�F�b�N
	 * 
	 * @param field �Ώۃt�B�[���h
	 * @param name �G���[���̕\����
	 * @return false:NG
	 */
	protected boolean checkInputBlank(TTextField field, String name) {

		if (field.isVisible() && field.isEditable() && field.isEmpty()) {
			showMessage("{0}����͂��Ă��������B", name);
			field.requestFocusInWindow();
			return false;
		}

		return true;
	}

	/**
	 * ��ʓ��͏������Ɋ����Ŋz�Ɋ��Z
	 * 
	 * @param taxAmount ���͏���Ŋz
	 * @param dc �ݎ�
	 * @return ��ʉݏ���Ŋz
	 */
	protected BigDecimal convertKeyTaxAmount(BigDecimal taxAmount, int dc) {

		if (DecimalUtil.isNullOrZero(taxAmount)) {
			return BigDecimal.ZERO;
		}

		Company kcompany = getCompany();

		Currency currency;
		ConsumptionTax tax;
		BigDecimal rate;

		currency = (Currency) panel.ctrlCurrency[dc].getSelectedItemValue();
		tax = panel.ctrlTax[dc].getEntity();
		rate = panel.ctrlRate[dc].getBigDecimal();

		if (kcompany == null || currency == null || tax == null) {
			return BigDecimal.ZERO;
		}

		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(kcompany.getAccountConfig().getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(keyCurrency.getDecimalPoint());
		param.setForeignAmount(taxAmount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
	}

	/**
	 * ��ʓ��͏������Ɋ���z�Ɋ��Z
	 * 
	 * @param amount ���͋��z
	 * @param currency �ʉ�
	 * @param rate �ʉ݃��[�g
	 * @return ��ʉ݋��z
	 */
	protected BigDecimal convertKeyAmount(BigDecimal amount, Currency currency, BigDecimal rate) {

		if (DecimalUtil.isNullOrZero(amount)) {
			return BigDecimal.ZERO;
		}

		Company kcompany = getCompany();

		if ((kcompany == null) || (currency == null)) {
			return BigDecimal.ZERO;
		}

		// ���Z
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(kcompany.getAccountConfig().getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(keyCurrency.getDecimalPoint());
		param.setForeignAmount(amount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
	}

	/**
	 * �f�t�H���g�����ł�
	 * 
	 * @param defaultTaxInnerDivision true:����
	 */
	protected void setDefaultTaxInnerDivision(boolean defaultTaxInnerDivision) {

		this.defaultTaxInnerDivision = defaultTaxInnerDivision;

		panel.ctrlTaxDivisionDebit.setSelectedItemValue(defaultTaxInnerDivision ? TaxCalcType.IN : TaxCalcType.OUT);
		panel.ctrlTaxDivisionCredit.setSelectedItemValue(defaultTaxInnerDivision ? TaxCalcType.IN : TaxCalcType.OUT);

		changedTaxDivision(DR);
		changedTaxDivision(CR);
	}

	/**
	 * �ʉ݃��[�g�擾
	 * 
	 * @param dc �ݎ�
	 * @return ���[�g
	 */
	protected BigDecimal getCurrencyRate(int dc) {
		try {

			Currency currency = (Currency) panel.ctrlCurrency[dc].getSelectedItemValue();

			if (currency == null) {
				return null;
			}

			if (currency.getCode().equals(keyCurrency.getCode())) {
				return BigDecimal.ONE;
			}

			Date date = panel.ctrlOccurDate.getValue();

			if (date == null) {
				date = baseDate;
			}

			if (date == null) {
				return null;
			}

			return (BigDecimal) request(RateManager.class, isClosing ? "getSettlementRate" : "getRate", currency, date);

		} catch (TException ex) {
			errorHandler(ex);
		}
		return null;
	}

	/**
	 * ���v�\��
	 */
	protected void doAfterEvent() {

		if (callBackListenerList != null && !callBackListenerList.isEmpty()) {

			for (TCallBackListener listener : callBackListenerList) {

				listener.after();
			}
		}
	}

	/**
	 * �ؕ��M�݋��z���擾����
	 * 
	 * @return �ؕ��M�݋��z
	 */
	protected BigDecimal getDebitKeyAmount() {
		return panel.ctrlKeyAmountDebit.getBigDecimal();
	}

	/**
	 * �ݕ��M�݋��z���擾����
	 * 
	 * @return �ݕ��M�݋��z
	 */
	protected BigDecimal getCreditKeyAmount() {
		return panel.ctrlKeyAmountCredit.getBigDecimal();
	}

	/**
	 * �ؕ��M�ݏ���Ŋz���擾����
	 * 
	 * @return �ؕ��M�ݏ���Ŋz
	 */
	protected BigDecimal getDebitTaxAmount() {
		return convertKeyTaxAmount(panel.ctrlTaxAmountDebit.getBigDecimal(), DR);
	}

	/**
	 * �ݕ��M�ݏ���Ŋz���擾����
	 * 
	 * @return �ݕ��M�ݏ���Ŋz
	 */
	protected BigDecimal getCreditTaxAmount() {
		return convertKeyTaxAmount(panel.ctrlTaxAmountCredit.getBigDecimal(), CR);
	}

	/**
	 * �ؕ����͏���Ŋz���擾����
	 * 
	 * @return �ؕ��M�ݏ���Ŋz
	 */
	protected BigDecimal getDebitTaxInputAmount() {
		return panel.ctrlTaxAmountDebit.getBigDecimal();
	}

	/**
	 * �ݕ����͏���Ŋz���擾����
	 * 
	 * @return �ݕ��M�ݏ���Ŋz
	 */
	protected BigDecimal getCreditTaxInputAmount() {
		return panel.ctrlTaxAmountCredit.getBigDecimal();
	}

	/**
	 * �ؕ����͋��z���擾����
	 * 
	 * @return �ؕ����͋��z
	 */
	protected BigDecimal getDebitInputAmount() {
		return panel.ctrlInputAmountDebit.getBigDecimal();
	}

	/**
	 * �ݕ����͋��z���擾����
	 * 
	 * @return �ݕ����͋��z
	 */
	protected BigDecimal getCreditInputAmount() {
		return panel.ctrlInputAmountCredit.getBigDecimal();
	}

	/**
	 * �ؕ��ʉ݃R�[�h���擾����
	 * 
	 * @return �ؕ��ʉ݃R�[�h
	 */
	protected Currency getDebitCurrency() {
		return (Currency) panel.ctrlCurrencyDebit.getSelectedItemValue();
	}

	/**
	 * �ݕ��ʉ݃R�[�h���擾����
	 * 
	 * @return �ݕ��ʉ݃R�[�h
	 */
	protected Currency getCreditCurrency() {
		return (Currency) panel.ctrlCurrencyCredit.getSelectedItemValue();
	}

	/**
	 * �ؕ�����Ŋz���܂߂邩
	 * 
	 * @return �܂߂�:true
	 */
	protected boolean isDebitTaxInclusive() {
		return panel.ctrlTaxDivisionDebit.getSelectedItemValue().equals(TaxCalcType.IN);
	}

	/**
	 * �ݕ�����Ŋz���܂߂邩
	 * 
	 * @return �܂߂�:true
	 */
	protected boolean isCreditTaxInclusive() {
		return panel.ctrlTaxDivisionCredit.getSelectedItemValue().equals(TaxCalcType.IN);
	}

	/**
	 * callBackListener��ǉ�����
	 * 
	 * @param callBackListener callBackListener
	 */
	protected void addCallBackListener(TCallBackListener callBackListener) {

		if (callBackListenerList == null) {

			callBackListenerList = new ArrayList<TCallBackListener>();
		}

		callBackListenerList.add(callBackListener);
	}

	/**
	 * ���׃f�[�^�̒ʉ݃��X�g�쐬
	 * 
	 * @param list �ʉ݃��X�g
	 */
	public void setCurrecyList(List<Currency> list) {

		if (list == null) {
			return;
		}

		panel.ctrlCurrencyDebit.removeAllItems();
		panel.ctrlCurrencyCredit.removeAllItems();

		for (Currency bean : list) {

			panel.ctrlCurrencyDebit.addTextValueItem(bean, bean.getCode());
			panel.ctrlCurrencyCredit.addTextValueItem(bean, bean.getCode());
		}

		panel.ctrlCurrencyDebit.setSelectedText(keyCurrency.getCode());
		panel.ctrlCurrencyCredit.setSelectedText(keyCurrency.getCode());
	}
}