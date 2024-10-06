package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * �ʉ݃t�B�[���h
 * 
 * @author roh
 */
public class TCurrencyField extends TButtonField {

	/** �R���g���[�� */
	protected TCurrencyFieldCtrl ctrl;

	/** ��ЃR�[�h */
	protected String companyCode;

	/** �L������ */
	protected String termBasisDate;

	/** �ʉ݃I�u�W�F�N�g */
	private int decKeta;

	/** Call�N���X */
	protected List<CallBackListener> callCtrlList;

	/** �J�n�R�[�h */
	private String beginCode;

	/** �I���R�[�h */
	private String endCode;

	/** �s���l�����͂ł��邩�ǂ��� */
	private boolean isFocusOut;

	/**
	 * �R���X�g���N�^
	 */
	public TCurrencyField() {
		this(false);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param isShowNotice ���̃t�B�[���h��\�����邩�ǂ���
	 */
	public TCurrencyField(boolean isShowNotice) {
		super();

		initComponents(isShowNotice);

		ctrl = new TCurrencyFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
	}

	/**
	 * �\�z
	 * 
	 * @param isShowNotice ���̃t�B�[���h��\�����邩�ǂ���
	 */
	protected void initComponents(boolean isShowNotice) {

		// �{�^���T�C�Y�����l
		this.setButtonSize(85);
		// �t�B�[���h�T�C�Y�����l
		this.setFieldSize(40);

		// Notice�̓f�t�H���g�\�����Ȃ�
		this.setNoticeVisible(isShowNotice);
		this.setNoticeSize(isShowNotice ? 120 : 0);

		// ���͌��������l
		this.setMaxLength(3);
		// IME����̐ݒ�
		this.setImeMode(false);
		// ���b�Z�[�WID�̐ݒ�
		this.setLangMessageID("C00371");

		// �u�ʉ݁v�{�^��������
		this.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnCurrencyActionPerformed();
			}
		});

		// �ʉ݃��X�g�t�H�[�J�X
		setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
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

		boolean bol = ctrl.setCurrencyInfo();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(bol);
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
				listener.after();
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
	 * ��ЃR�[�h�K��
	 * 
	 * @return companyCode ��ЃR�[�h
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

		boolean bol = ctrl.setCurrencyInfo();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(bol);
		}
	}

	/**
	 * �J�n�R�[�h�擾
	 * 
	 * @return �J�n�R�[�h
	 */
	public String getBeginCode() {
		return beginCode;
	}

	/**
	 * �J�n�R�[�h�ݒ�
	 * 
	 * @param beginCode
	 */
	public void setBeginCode(String beginCode) {
		this.beginCode = beginCode;
	}

	/**
	 * �I���R�[�h�擾
	 * 
	 * @return �I���R�[�h
	 */
	public String getEndCode() {
		return endCode;
	}

	/**
	 * �I���R�[�h�ݒ�
	 * 
	 * @param endCode
	 */
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	/**
	 * @return �����_�ȉ��̌���
	 */
	public int getDecKeta() {
		return decKeta;
	}

	/**
	 * @param decKeta
	 */
	public void setDecKeta(int decKeta) {
		this.decKeta = decKeta;
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

}
