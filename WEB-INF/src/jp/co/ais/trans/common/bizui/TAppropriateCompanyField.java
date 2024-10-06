package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * �v���Ѓt�B�[���h
 * 
 * @author zhangbo
 */
public class TAppropriateCompanyField extends TButtonField {

	/** �R���g���[�� */
	protected TAppropriateCompanyFieldCtrl ctrl;

	/** �L������ */
	protected String termBasisDate;

	/** �J�n��ЃR�[�h */
	protected String strCompanyCode;

	/** �I����ЃR�[�h */
	protected String endCompanyCode;

	/** Call�N���X */
	private List<CallBackListener> callCtrlList;

	/** �s���l�����͂ł��邩�ǂ��� */
	private boolean isFocusOut;

	/** ���݁iafter�p�j */
	private boolean isBln = true;

	/** �ʉ݋敪 */
	private boolean curKbn = true;

	/**
	 * �R���X�g���N�^
	 */
	public TAppropriateCompanyField() {
		super();

		initComponents();

		ctrl = new TAppropriateCompanyFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
	}

	/**
	 * �\�z
	 */
	protected void initComponents() {

		// �{�^���T�C�Y�����l
		this.setButtonSize(85);
		// �t�B�[���h�T�C�Y�����l
		this.setFieldSize(75);

		this.setNoticeSize(130);

		// ���͌��������l
		this.setMaxLength(10);
		// IME����̐ݒ�
		this.setImeMode(false);
		// ���b�Z�[�WID�̐ݒ�
		this.setLangMessageID("C01052");

		// �u�v���Ёv�{�^��������
		this.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				isBln = true;
				btnCurrencyActionPerformed();
			}
		});

		// �v���Ѓ��X�g�t�H�[�J�X
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				isBln = true;
				if (!getField().isEditable()) {
					return true;
				}
				if (!isValueChanged()) {
					return true;
				}
				return currencyLostFocus();

			}
		});
	}

	/**
	 * �t�H�[�J�X�Ή�
	 * 
	 * @return boolean
	 */
	protected boolean currencyLostFocus() {
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean bol = ctrl.setCompanyInfo();

		for (CallBackListener listener : callCtrlList) {
			if (isBln) {
				listener.after();
			}

			if (!isBln) {
				return false;
			}
		}

		return bol;
	}

	/**
	 * �_�C�A���O����
	 */
	protected void btnCurrencyActionPerformed() {
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}
		boolean isChange = ctrl.showSearchDialog();
		if (isChange) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			for (CallBackListener listener : callCtrlList) {
				if (isBln) {
					listener.after();
				}

			}
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	 * Call�N���X���Z�b�g����B<BR
	 * 
	 * @param callCtrl CallControl�N���X
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
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

		ctrl.setCompanyInfo();

		for (CallBackListener listener : callCtrlList) {
			if (isBln) {
				listener.after();
			}
			if (!isBln) {
				return;
			}
		}
	}

	/**
	 * �s���l����͂��Ă��s�����b�Z�[�W���o���Ȃ����B
	 * 
	 * @return boolean
	 */
	public boolean isFocusOut() {
		return isFocusOut;
	}

	/**
	 * �s���l���̓t���O�ݒ�
	 * 
	 * @param isFocusOut true : �s���l���b�Z�[�W�������Ȃ��B
	 */
	public void setFocusOut(boolean isFocusOut) {
		this.isFocusOut = isFocusOut;
	}

	/**
	 * ���݁iafter�p�j
	 * 
	 * @param isBln
	 */
	public void setBln(boolean isBln) {
		this.isBln = isBln;
	}

	/**
	 * �ʉ݋敪 true:��ʉ݃R�[�h�̉�Ђ̂ݑΏ� false:�S��БΏ�
	 * 
	 * @param curKbn
	 */
	public void setCurKbn(boolean curKbn) {
		this.curKbn = curKbn;
	}

	/**
	 * �ʉ݋敪�̎擾
	 * 
	 * @return curKbn
	 */
	public boolean isCurKbn() {
		return curKbn;
	}

	
	/**
	 * �I����ЃR�[�h���擾����B
	 * @return String �I����ЃR�[�h
	 */
	public String getEndCompanyCode() {
		return endCompanyCode;
	}

	
	/**
	 * �I����ЃR�[�h��ݒ肷��B
	 * @param endCompanyCode
	 */
	public void setEndCompanyCode(String endCompanyCode) {
		this.endCompanyCode = endCompanyCode;
	}

	
	/**
	 * �J�n��ЃR�[�h���擾����B
	 * @return String �J�n��ЃR�[�h
	 */
	public String getStrCompanyCode() {
		return strCompanyCode;
	}

	
	/**
	 * �J�n��ЃR�[�h��ݒ肷��B
	 * @param strCompanyCode
	 */
	public void setStrCompanyCode(String strCompanyCode) {
		this.strCompanyCode = strCompanyCode;
	}

}
