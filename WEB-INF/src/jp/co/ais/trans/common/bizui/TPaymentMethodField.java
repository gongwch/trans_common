package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �x�������@�t�B�[���h
 * 
 * @author roh
 */
public class TPaymentMethodField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = 197997362164770458L;

	/** Call�N���X */
	private List<CallBackListener> callCtrlList;

	/** �R���g���[�� */
	protected TPaymentMethodFieldCtrl ctrl;

	/** �L������ */
	private String termBasisDate;

	/** ��ЃR�[�h */
	private String companyCode;

	/** �x�������R�[�h */
	private String naiCode;

	/** ���������F�x���Ώۋ敪 */
	private String serchSihKbn;

	/** ���������F�x�������R�[�h */
	private String serchNaiCode;

	/** ���������F�x�������R�[�h(NOT����) */
	private String serchNotNaiCode;

	/** �x�����@���X�g */
	private List<String> hohCodes;

	/** �x�����@�}�X�^ */
	private AP_HOH_MST bean;

	/**
	 * �R���X�g���N�^
	 */
	public TPaymentMethodField() {
		super();
		ctrl = new TPaymentMethodFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
		initComponents();
	}

	/**
	 * �\�z
	 */
	private void initComponents() {

		// �{�^���T�C�Y�����l
		this.setButtonSize(85);
		// �t�B�[���h�T�C�Y�����l
		this.setFieldSize(75);
		// ���͌��������l
		this.setMaxLength(10);
		// IME����̐ݒ�
		this.setImeMode(false);
		// ���b�Z�[�WID�̐ݒ�
		this.setLangMessageID("C00233");

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
				boolean sts = ctrl.setPaymentNotice();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}
				return sts;
			}
		});

		// �x�������@�{�^���������C�x���g
		addButtonActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}

				ctrl.showSearchDialog();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}
			}
		});

	}

	/**
	 * Call�N���X���Z�b�g����B
	 * 
	 * @param callCtrl CallControl�N���X
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * ��ЃR�[�h�K��
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
	 * �x�������R�[�h��ݒ�
	 * 
	 * @param naiCode �x�������R�[�h
	 */
	public void setNaiCode(String naiCode) {
		this.naiCode = naiCode;
	}

	/**
	 * �x�������R�[�h���擾
	 * 
	 * @return naiCode �x�������R�[�h
	 */
	public String getNaiCode() {
		return this.naiCode;
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
		ctrl.setPaymentNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
		}
	}

	/**
	 * �x�����@�}�X�^���擾����
	 * 
	 * @return �x�����@�}�X�^
	 */
	public AP_HOH_MST getBean() {
		return bean;
	}

	/**
	 * �x�����@�}�X�^��ݒ肷��
	 * 
	 * @param bean
	 */
	public void setBean(AP_HOH_MST bean) {
		this.bean = bean;
	}

	/**
	 * ���������F�x�������R�[�h���擾����
	 * 
	 * @return ���������F�x�������R�[�h
	 */
	public String getSerchNaiCode() {
		return serchNaiCode;
	}

	/**
	 * ���������F�x�������R�[�h��ݒ肷��
	 * 
	 * @param serchNaiCode
	 */
	public void setSerchNaiCode(String serchNaiCode) {
		this.serchNaiCode = serchNaiCode;
	}

	/**
	 * ���������F�x���Ώۋ敪���擾����
	 * 
	 * @return ���������F�x���Ώۋ敪
	 */
	public String getSerchSihKbn() {
		return serchSihKbn;
	}

	/**
	 * ���������F�x���Ώۋ敪��ݒ肷��
	 * 
	 * @param serchSihKbn
	 */
	public void setSerchSihKbn(String serchSihKbn) {
		this.serchSihKbn = serchSihKbn;
	}

	/**
	 * ���������F�x�������R�[�h(NOT����)���擾����
	 * 
	 * @return ���������F�x�������R�[�h(NOT����)
	 */
	public String getSerchNotNaiCode() {
		return serchNotNaiCode;
	}

	/**
	 * ���������F�x�������R�[�h(NOT����)��ݒ肷��
	 * 
	 * @param serchNotNaiCode
	 */
	public void setSerchNotNaiCode(String serchNotNaiCode) {
		this.serchNotNaiCode = serchNotNaiCode;
	}

	/**
	 * �x�����@�R�[�h���X�g
	 * 
	 * @return hohCodes
	 */
	public List<String> getHohCodes() {
		return hohCodes;
	}

	/**
	 * �x�����@�R�[�h���X�g
	 * 
	 * @param hohCodes
	 */
	public void setHohCodes(List<String> hohCodes) {
		this.hohCodes = hohCodes;
	}

}
