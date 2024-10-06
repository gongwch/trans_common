package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;

/**
 * �Ǘ��}�X�^�����t�B�[���h
 * 
 * @author moriya
 */
public class TManagementField extends TButtonField {

	/** �V���A��UID */
	private static final long serialVersionUID = 1L;

	/** �R���g���[���N���X */
	protected TManagementFieldCtrl ctrl;

	/** ��ЃR�[�h */
	private String companyCode;

	/** �J�n�R�[�h */
	private String beginCode;

	/** �I���R�[�h */
	private String endCode;

	/** �L�����Ԃ̃`�F�b�N */
	private String termBasisDate;

	/** �Ǘ��R�[�h�P */
	public static final int TYPE_MANAGEMENT1 = 1;

	/** �Ǘ��R�[�h�Q */
	public static final int TYPE_MANAGEMENT2 = 2;

	/** �Ǘ��R�[�h�R */
	public static final int TYPE_MANAGEMENT3 = 3;

	/** �Ǘ��R�[�h�S */
	public static final int TYPE_MANAGEMENT4 = 4;

	/** �Ǘ��R�[�h�T */
	public static final int TYPE_MANAGEMENT5 = 5;

	/** �Ǘ��R�[�h�U */
	public static final int TYPE_MANAGEMENT6 = 6;

	/** �R���g���[���^�C�v���Z�b�g */
	private int managementType;

	/** ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N */
	private boolean checkMode = true;

	/** Call�N���X */
	protected List<CallBackListener> callCtrlList = new LinkedList<CallBackListener>();

	/**
	 * �R���X�g���N�^<br>
	 * �Ǘ��R�[�h1�`6�̐ݒ�͈����ɓn���p�����[�^�̐��l�ɂ���Č���<br>
	 * �Ǘ�1�`6�FTYPE_MANAGEMENT1�`6
	 * 
	 * @param type �Ǘ��^�C�v
	 */
	public TManagementField(int type) {
		super();

		this.ctrl = new TManagementFieldCtrl(this);

		this.managementType = type;

		initComponents();
	}

	/**
	 * ��ʍ\�z
	 */
	private void initComponents() {

		// �T�C�Y�����l
		this.setButtonSize(85);
		this.setFieldSize(75);
		this.setNoticeSize(135);
		this.setImeMode(false);
		this.setMaxLength(10);

		// ��ЃR�[�h�擾
		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();
		// �Ǘ��敪�̎擾
		boolean[] mngViews = compInfo.isUseManageDivs();
		// �Ǘ����̂̎擾
		String[] name = compInfo.getManageDivNames();

		switch (managementType) {

			// �Ǘ�1(�\���敪�ŕ\��/��\�������߂�)
			case TYPE_MANAGEMENT1:
				if (mngViews[0]) {
					this.button.setText(name[0]);
				} else {
					this.button.setVisible(false);
					this.field.setVisible(false);
					this.notice.setVisible(false);
				}
				break;
			// �Ǘ�2(�\���敪�ŕ\��/��\�������߂�)
			case TYPE_MANAGEMENT2:
				if (mngViews[1]) {
					this.button.setText(name[1]);
				} else {
					this.button.setVisible(false);
					this.field.setVisible(false);
					this.notice.setVisible(false);
				}
				break;

			// �Ǘ�3(�\���敪�ŕ\��/��\�������߂�)
			case TYPE_MANAGEMENT3:
				if (mngViews[2]) {
					this.button.setText(name[2]);
				} else {
					this.button.setVisible(false);
					this.field.setVisible(false);
					this.notice.setVisible(false);
				}
				break;

			// �Ǘ�4(�\���敪�ŕ\��/��\�������߂�)
			case TYPE_MANAGEMENT4:
				if (mngViews[3]) {
					this.button.setText(name[3]);
				} else {
					this.button.setVisible(false);
					this.field.setVisible(false);
					this.notice.setVisible(false);
				}
				break;

			// �Ǘ�5(�\���敪�ŕ\��/��\�������߂�)
			case TYPE_MANAGEMENT5:
				if (mngViews[4]) {
					this.button.setText(name[4]);
				} else {
					this.button.setVisible(false);
					this.field.setVisible(false);
					this.notice.setVisible(false);
				}
				break;

			// �Ǘ�6(�\���敪�ŕ\��/��\�������߂�)
			case TYPE_MANAGEMENT6:
				if (mngViews[5]) {
					this.button.setText(name[5]);
				} else {
					this.button.setVisible(false);
					this.field.setVisible(false);
					this.notice.setVisible(false);
				}
				break;
		}

		// �{�^�����������C�x���g
		this.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}

				ctrl.managementMouseClicked();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}
			}
		});

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

				boolean sts = ctrl.managementLostFocus();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
					listener.after(sts);
				}
				return sts;
			}
		});

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
		boolean sts = ctrl.managementLostFocus();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(sts);
		}
	}

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �J�n�R�[�h�̎擾
	 * 
	 * @return beginCode �J�n�R�[�h
	 */
	public String getBeginCode() {
		return beginCode;
	}

	/**
	 * �J�n�R�[�h�̐ݒ�
	 * 
	 * @param beginCode
	 */
	public void setBeginCode(String beginCode) {
		this.beginCode = beginCode;
	}

	/**
	 * �I���R�[�h�̎擾
	 * 
	 * @return endCode �I���R�[�h
	 */
	public String getEndCode() {
		return endCode;
	}

	/**
	 * �I���R�[�h�̐ݒ�
	 * 
	 * @param endCode
	 */
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	/**
	 * ����̎擾
	 * 
	 * @return ���
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
	 * ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N���s�����ǂ�
	 * 
	 * @param checkMode true:�`�F�b�N���� false�F�`�F�b�N���Ȃ�
	 */
	public void setChekcMode(boolean checkMode) {
		this.checkMode = checkMode;
	}

	/**
	 * ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N���s�����ǂ�
	 * 
	 * @return true: �`�F�b�N����
	 */
	public boolean isCheckMode() {
		return checkMode;
	}

	/**
	 * �R���g���[���^�C�v���Z�b�g
	 * 
	 * @param managementType
	 */
	public void setManagementType(int managementType) {
		this.managementType = managementType;
	}

	/**
	 * �R���g���[���^�C�v���Z�b�g
	 * 
	 * @return �^�C�v
	 */
	public int getManagementType() {
		return this.managementType;
	}

	/**
	 * Call�N���X���Z�b�g����B
	 * 
	 * @param callCtrl CallControl�N��
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

}
