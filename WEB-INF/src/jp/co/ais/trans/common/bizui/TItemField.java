package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;

/**
 * �Ȗڃt�B�[���h
 */
public class TItemField extends TButtonField {

	/** �R���g���[�� */
	protected TItemFieldCtrl ctrl;

	/** Call�N���X */
	private List<CallBackListener> callCtrlList;

	/** �����p�p�����[�^ */
	private AccountItemInputParameter inputParameter;

	/** ���ʗp�p�����[�^ */
	protected AccountItemOutputParameter outputParameter;

	/** ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N */
	private boolean chekcMode = true;

	/**
	 * Constructor.
	 */
	public TItemField() {
		super();

		// �T�C�Y�����l
		this.setButtonSize(85);
		this.setFieldSize(75);

		// ���͌��������l
		this.setMaxLength(10);

		// IME����
		this.setImeMode(false);

		ctrl = new TItemFieldCtrl(this);

		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());

		inputParameter = new AccountItemInputParameter();

		outputParameter = new AccountItemOutputParameter();

		initComponents();
	}

	/**
	 * ��ʍ\�z
	 */
	private void initComponents() {
		// ��ЃR�[�h�擾
		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();

		String name = compInfo.getItemName();
		if (!Util.isNullOrEmpty(name)) {
			this.getButton().setText(name);
		} else {
			// ���̂��擾�ł��Ȃ��ꍇ�A�f�t�H���g�u�Ȗځv��ݒ�
			this.setLangMessageID("C00077");
		}

		// �u�Ȗځv�{�^��������
		this.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnItemActionPerformed();
			}
		});

		// �ȖڃR�[�h���X�g�t�H�[�J�X
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				if (!isValueChanged2()) {
					return true;
				}
				return itemLostFocus();
			}
		});
	}

	/**
	 * �{�^���������̏���
	 */
	private void btnItemActionPerformed() {
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean isChange = ctrl.search();

		if (isChange) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			for (CallBackListener listener : callCtrlList) {
				listener.after();
				listener.after(isChange);
			}

			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	 * ���X�g�t�H�[�J�X���̏���
	 * 
	 * @return true:���폈��
	 */
	public boolean itemLostFocus() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean sts = ctrl.setupInfo();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(sts);
		}

		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		return sts;
	}

	/**
	 * CallBackLisneter�̓o�^
	 * 
	 * @param callCtrl CallControl�N��
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * �����p�����[�^���Z�b�g����B
	 * 
	 * @param inputParameter �����p�����[�^
	 */
	public void setInputParameter(AccountItemInputParameter inputParameter) {
		this.inputParameter = inputParameter;
	}

	/**
	 * �����p�����[�^���擾����B
	 * 
	 * @return inputParameter �����p�����[�^
	 */
	public AccountItemInputParameter getInputParameter() {
		return this.inputParameter;
	}

	/**
	 * ���ʃp�����[�^���Z�b�g����B
	 * 
	 * @param outputParameter ���ʃp�����[�^
	 */
	public void setOutputParameter(AccountItemOutputParameter outputParameter) {
		this.outputParameter = outputParameter;
	}

	/**
	 * ���ʃp�����[�^���擾����B
	 * 
	 * @return outputParameter ���ʃp�����[�^
	 */
	public AccountItemOutputParameter getOutputParameter() {
		return this.outputParameter;
	}

	/**
	 * ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N���s�����ǂ���
	 * 
	 * @return true: �`�F�b�N����
	 */
	public boolean isCheckMode() {
		return chekcMode;
	}

	/**
	 * ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N���s�����ǂ���
	 * 
	 * @param chekcMode true: �`�F�b�N����
	 */
	public void setCheckMode(boolean chekcMode) {
		this.chekcMode = chekcMode;
	}

	/**
	 * field �e�L�X�g�t�B�[���h�ɕ������ݒ肷��
	 * 
	 * @param value �ݒ蕶����
	 */
	@Override
	public void setValue(String value) {
		super.setValue(value);

		// �����pBEAN�ɉȖں��ނ��Z�b�g
		inputParameter.setItemCode(value);

		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		// �������������s
		boolean sts = ctrl.setupInfo();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(sts);
		}
	}

	/**
	 * �l�̓������Z�b�g. ���ʃZ�b�g���X�V�����.
	 */
	public void resetValue() {
		this.setValue(this.getValue());
	}

	/**
	 * ����R�[�h�̐ݒ�.<br>
	 * ���ݓ��͂���Ă���Ȗڂ��w�蕔��ɊY�����Ȃ�<br>
	 * �i�ʂ̕���R�[�h���Œ蕔��Ƃ��ēo�^����Ă���j�ꍇ�A �ȖڃR�[�h���N���A����.
	 * 
	 * @param depCode ����R�[�h
	 */
	public void setDepartmentCode(String depCode) {
		this.inputParameter.setDepartmentCode(depCode);

		boolean isCheck = this.chekcMode;

		this.chekcMode = false;
		this.ctrl.setupInfo();
		if (!this.outputParameter.isExist()) {
			this.clear();
		}

		this.chekcMode = isCheck;
	}
}
