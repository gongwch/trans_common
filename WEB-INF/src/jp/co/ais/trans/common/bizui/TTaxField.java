package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * ����Ńt�B�[���h<br>
 * 
 * @author roh
 */
public class TTaxField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = 152234308781802217L;

	/** �R���g���[�� */
	protected TTaxFieldCtrl ctrl;

	/** �L������ */
	private String termBasisDate;

	/** ��ЃR�[�h */
	private String companyCode;

	/** ����グ�敪 */
	private boolean sales;

	/** �d����敪 */
	private boolean purchase;

	/** �ΏۊO�敪 */
	private boolean elseTax;

	/** �Ń��[�g */
	private Float rate;

	/** ����d���敪�F�d�� */
	private boolean uskbnPurchase;

	/** ����d���敪�F���� */
	private boolean uskbnSales;

	/** Call�N���X */
	private List<CallBackListener> callCtrlList;

	/**
	 * ��ЃR�[�h�擾
	 * 
	 * @return companyCode <br>
	 *         ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�ݒ�
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �ΏۊO�敪�擾
	 * 
	 * @return �ΏۊO�敪
	 */
	public boolean isElseTax() {
		return elseTax;
	}

	/**
	 * �ΏۊO�敪�擾
	 * 
	 * @param elseTax
	 */
	public void setElseTax(boolean elseTax) {
		this.elseTax = elseTax;
	}

	/**
	 * �d����敪�擾
	 * 
	 * @return �d����敪
	 */
	public boolean isPurchase() {
		return purchase;
	}

	/**
	 * �d����敪�ݒ�
	 * 
	 * @param purchase
	 */
	public void setPurchase(boolean purchase) {
		this.purchase = purchase;
	}

	/**
	 * ����グ�敪�擾
	 * 
	 * @return ����グ�敪
	 */
	public boolean isSales() {
		return sales;
	}

	/**
	 * ����グ�敪�ݒ�
	 * 
	 * @param sales
	 */
	public void setSales(boolean sales) {
		this.sales = sales;
	}

	/**
	 * �L�����Ԏ擾
	 * 
	 * @return �L������
	 */
	public String getTermBasisDate() {
		return termBasisDate;
	}

	/**
	 * �L�����Ԑݒ�
	 * 
	 * @param termBasisDate �L������
	 */
	public void setTermBasisDate(String termBasisDate) {
		this.termBasisDate = termBasisDate;
	}

	/**
	 * ����d���敪�F�d�� �擾
	 * 
	 * @return ����d���敪�F�d��
	 */
	public boolean isUsKbnPurchase() {
		return uskbnPurchase;
	}

	/**
	 * ����d���敪�F�d�� �ݒ�
	 * 
	 * @param uskbnPurchase
	 */
	public void setUsKbnPurchase(boolean uskbnPurchase) {
		this.uskbnPurchase = uskbnPurchase;
	}

	/**
	 * ����d���敪�F���� �擾
	 * 
	 * @return ����d���敪�F����
	 */
	public boolean isUsKbnSales() {
		return uskbnSales;
	}

	/**
	 * ����d���敪�F���� �ݒ�
	 * 
	 * @param uskbnSales
	 */
	public void setUsKbnSales(boolean uskbnSales) {
		this.uskbnSales = uskbnSales;
	}

	/**
	 * �Ń��[�g�擾
	 * 
	 * @return companyCode <br>
	 *         �Ń��[�g
	 */
	public Float getRate() {
		return rate;
	}

	/**
	 * �Ń��[�g�ݒ�
	 * 
	 * @param rate
	 */
	public void setRate(Float rate) {
		this.rate = rate;
	}

	/**
	 * CallBackListener��ǉ�����
	 * 
	 * @param callCtrl CallBackListener
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * ����Ńt�B�[���h
	 * 
	 * @author roh
	 */
	public TTaxField() {
		super();
		ctrl = new TTaxFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
		initComponents();

	}

	/** ��ʍ\�z */
	private void initComponents() {

		// �{�^���T�C�Y�����l
		this.setButtonSize(85);
		// �t�B�[���h�T�C�Y�����l
		this.setFieldSize(25);
		// Notice�T�C�Y�����l
		this.setNoticeSize(135);
		// ���͌����̐ݒ�
		this.setMaxLength(10);
		// IME����̐ݒ�
		this.setImeMode(false);
		// ���b�Z�[�WID�̐ݒ�
		this.setLangMessageID("C00673");

		// ���X�g�t�H�[�J�X���C�x���g
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				if (!getField().isEditable()) {
					return true;
				}
				if (!isValueChanged()) {
					return true;
				}

				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}
				
				ctrl.termBasisDateFlag = true;
				boolean sts = taxlostFocus();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}

				return sts;

			}
		});

		// �ŋ敪�{�^���������C�x���g
		addButtonActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				ctrl.btnActionPerformed();
			}
		});
	}

	/**
	 * ���[�X�g�t�H�[�J�X
	 * 
	 * @return boolean
	 */
	public boolean taxlostFocus() {
		return ctrl.taxLostFocus();
	}

	/**
	 * field �e�L�X�g�t�B�[���h�ɕ������ݒ肷��
	 * 
	 * @param value �ݒ蕶����
	 */
	@Override
	public void setValue(String value) {
		super.setValue(value);
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}
		ctrl.termBasisDateFlag = false;
		ctrl.taxLostFocus();

		for (CallBackListener listener : callCtrlList) {
 			listener.after();
		}
	}
}
