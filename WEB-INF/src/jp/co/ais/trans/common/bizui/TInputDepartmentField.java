package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * ���͕���t�B�[���h
 */
public class TInputDepartmentField extends TButtonField {

	/** �V���A��UID */
	private static final long serialVersionUID = -2656930051806872824L;

	private TInputDepartmentFieldCtrl ctrl;

	/** ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N */
	private boolean chekcMode = true;

	/** Call�N���X */
	private List<CallBackListener> callCtrlList;

	/**
	 * Constructor.
	 */
	public TInputDepartmentField() {
		super();

		// �T�C�Y�����l
		this.setButtonSize(85);
		this.setFieldSize(75);

		// ���͌��������l
		this.setMaxLength(10);

		// IME����
		this.setImeMode(false);

		ctrl = new TInputDepartmentFieldCtrl(this);

		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());

		initComponents();

	}

	/**
	 * ��ʍ\�z
	 */
	private void initComponents() {
		// ���p�ҏ��擾���A�l���Z�b�g
		ctrl.setInit();

		// �{�^�����Z�b�g
		this.setLangMessageID("C01280");

		// �u���͕���v�{�^��������
		this.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnDepartmentActionPerformed();
			}
		});

		// ���͕���R�[�h���X�g�t�H�[�J�X
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				if (!isValueChanged()) {
					return true;
				}

				return departmentLostFocus();
			}
		});
	}

	/**
	 * �{�^���������̏���
	 */
	private void btnDepartmentActionPerformed() {
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean isChange = ctrl.search();

		if (isChange) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			for (CallBackListener listener : callCtrlList) {
				listener.after();
			}

			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	 * ���X�g�t�H�[�J�X���̏���
	 * 
	 * @return boolean
	 */
	protected boolean departmentLostFocus() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean sts = ctrl.setupInfo();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
		}

		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		return sts;
	}

	/**
	 * Call�N���X���Z�b�g����B<BR>
	 * 
	 * @param callCtrl CallControl�N��
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N���s�����ǂ�
	 * 
	 * @return true: �`�F�b�N����
	 */
	public boolean isChekcMode() {
		return chekcMode;
	}

	/**
	 * ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N���s�����ǂ�
	 * 
	 * @param chekcMode true: �`�F�b�N����
	 */
	public void setChekcMode(boolean chekcMode) {
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

		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		// �������������s
		ctrl.setupInfo();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
		}
	}
}
