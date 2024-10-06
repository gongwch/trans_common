package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * ��s/�x�X�����t�B�[���h
 */
public class TBankUnitField extends TPanel {

	/** ��s */
	private TBankField bnkField;

	/** �x�X */
	private TBankStnField stnField;

	/** Call�N���X */
	protected List<CallBackListener> callCtrlList = new LinkedList<CallBackListener>();

	/** �x�X���g�p���[�h(�\���͂���) */
	protected boolean stnEnableMode = true;

	/**
	 * �R���X�g���N�^
	 */
	public TBankUnitField() {
		initComponents();
	}

	/**
	 * ��ʏ�����
	 */
	private void initComponents() {

		bnkField = new TBankField();
		stnField = new TBankStnField();

		setLayout(new GridBagLayout());

		bnkField.setTabControlNo(0);
		add(bnkField, new GridBagConstraints());

		stnField.setEnabled(false);
		stnField.setTabControlNo(1);
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		add(stnField, gridBagConstraints);

		bnkField.addCallControl(new CallBackListener() {

			public void after(boolean flag) {
				if (Util.isNullOrEmpty(bnkField.getValue()) || !flag) {
					stnField.clear();
					stnField.setEnabled(false);
				} else {
					stnField.clear();
					stnField.setEnabled(true);
					stnField.setEditable(true);
					stnField.setBnkCode(bnkField.getValue());

					if (!Util.isNullOrEmpty(stnField.getValue())) {
						stnField.chkExist();
					}
				}
			}
		});
	}

	/**
	 * �^�u�ړ����ԍ����e�L�X�g�t�B�[���h�ɐݒ肷��.
	 * 
	 * @param no �ԍ�
	 */
	public void setTabControlNo(int no) {
		bnkField.setTabControlNo(no);
		stnField.setTabControlNo(no);
	}

	/**
	 * �N���A
	 */
	public void clear() {
		bnkField.getField().setEditable(true);
		bnkField.getButton().setEnabled(true);
		stnField.getField().setEditable(false);
		stnField.getButton().setEnabled(false);
		bnkField.clear();
		stnField.clear();
	}

	/**
	 * ��s�t�B�[���h�擾
	 * 
	 * @return bnkField ��s�t�B�[���h
	 */
	public TBankField getBnkField() {
		return bnkField;
	}

	/**
	 * �x�X�t�B�[���h�擾
	 * 
	 * @return stnField �x�X�t�B�[���h
	 */
	public TBankStnField getStnField() {
		return stnField;
	}

	/**
	 * ��s�R�[�h�ݒ�
	 * 
	 * @param code
	 */
	public void setBnkCode(String code) {
		bnkField.setValue(code);
	}

	/**
	 * �x�X�R�[�h�ݒ�
	 * 
	 * @param code
	 */
	public void setStnCode(String code) {
		stnField.setValue(code);

	}

	/**
	 * ���̓t�B�[���h��Editable�ݒ�
	 * 
	 * @param isControl true:����\
	 */
	public void setEditable(boolean isControl) {
		bnkField.setButtonEnabled(isControl);
		bnkField.setEditable(isControl);
		stnField.setButtonEnabled(isControl);
		stnField.setEditable(isControl);
	}

	/**
	 * stnEnableMode�擾
	 * 
	 * @return stnEnableMode
	 */
	public boolean isStnEnableMode() {
		return stnEnableMode;
	}

	/**
	 * stnEnableMode�ݒ�
	 * 
	 * @param stnEnableMode
	 */
	public void setStnEnableMode(boolean stnEnableMode) {
		this.stnEnableMode = stnEnableMode;
	}

}