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
 * ����Ȗڃt�B�[���h
 */
public class TBreakDownItemField extends TButtonField {

	/** �R���g���[�� */
	protected TBreakDownItemFieldCtrl ctrl;

	/** �����pBEAN�N���X */
	private AccountItemInputParameter inputParameter;

	/** ���ʗpBEAN�N���X */
	protected AccountItemOutputParameter outputParameter;

	/** Call�N���X */
	private List<CallBackListener> callCtrlList;

	/** ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N */
	private boolean chekcMode = true;

	/**
	 * Constructor.
	 */
	public TBreakDownItemField() {
		super();

		// �T�C�Y�����l
		this.setButtonSize(85);
		this.setFieldSize(75);

		// ���͌��������l
		this.setMaxLength(10);

		ctrl = new TBreakDownItemFieldCtrl(this);

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

		String name = compInfo.getBreakDownItemName();
		if (!Util.isNullOrEmpty(name)) {

			this.getButton().setText(name);
		} else {
			// ���̂��擾�ł��Ȃ��ꍇ�A�f�t�H���g�u����Ȗځv��ݒ�
			setLangMessageID("C00025");
		}

		// �u����Ȗځv�{�^��������
		this.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnBreakDownItemActionPerformed();
			}
		});

		// ����ȖڃR�[�h���X�g�t�H�[�J�X
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				if (!isValueChanged2()) {
					return true;
				}
				return breakDownItemLostFocus();
			}
		});
	}

	/**
	 * �{�^���������̏���
	 */
	private void btnBreakDownItemActionPerformed() {

		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean isChange = ctrl.search();

		if (isChange) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			for (CallBackListener callCtrl : callCtrlList) {
				callCtrl.after();
			}

			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	/**
	 * ���X�g�t�H�[�J�X���̏���
	 * 
	 * @return boolean
	 */
	protected boolean breakDownItemLostFocus() {
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean sts = ctrl.setupInfo();

		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.after();
			callCtrl.after(sts);
		}
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		return sts;
	}

	/**
	 * Call�N���X���Z�b�g����B<BR>
	 * 
	 * @param callCtrl CallControl�N���X
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N���s�����ǂ���
	 * 
	 * @return true: �`�F�b�N����
	 */
	public boolean isChekcMode() {
		return chekcMode;
	}

	/**
	 * ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N���s�����ǂ���
	 * 
	 * @param chekcMode true: �`�F�b�N����
	 */
	public void setChekcMode(boolean chekcMode) {
		this.chekcMode = chekcMode;
	}

	/**
	 * �����pBEAN�N���X���Z�b�g����B<BR>
	 * 
	 * @param inputParameter AccountItemInputParameter�N���X
	 */
	public void setInputParameter(AccountItemInputParameter inputParameter) {
		this.inputParameter = inputParameter;
	}

	/**
	 * �����pBEAN�N���X���擾����B<BR>
	 * 
	 * @return inputParameter AccountItemInputParameter�N���X
	 */
	public AccountItemInputParameter getInputParameter() {
		return this.inputParameter;
	}

	/**
	 * �����pBEAN�N���X���Z�b�g����B<BR>
	 * 
	 * @param outputParameter AccountItemOutputParameter�N���X
	 */
	public void setOutputParameter(AccountItemOutputParameter outputParameter) {
		this.outputParameter = outputParameter;
	}

	/**
	 * �����pBEAN�N���X���擾����B<BR>
	 * 
	 * @return outputParameter AccountItemOutputParameter�N���X
	 */
	public AccountItemOutputParameter getOutputParameter() {
		return this.outputParameter;
	}

	/**
	 * field �e�L�X�g�t�B�[���h�ɕ������ݒ肷��.
	 * 
	 * @param value �ݒ蕶����
	 */
	@Override
	public void setValue(String value) {
		super.setValue(value);

		// �����pBEAN�ɕ⏕�Ȗں��ނ��Z�b�g
		inputParameter.setBreakDownItemCode(value);

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
}
