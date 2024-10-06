package jp.co.ais.trans2.common.gui;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * �͈͌����t�B�[���h(�O��)
 * 
 * @author AIS
 */
public abstract class TReferenceTripleRange extends TPanel {

	/**
	 * �R���X�g���N�^
	 */
	public TReferenceTripleRange() {
		init();
		initComponents();
		allocateComponents();
	}

	/**
	 * ������
	 */
	protected void init() {
		// for override
	}

	/**
	 * ������
	 */
	public abstract void initComponents();

	/**
	 * �R���|�[�l���g�z�u
	 */
	public void allocateComponents() {
		setLayout(null);
		setOpaque(false);

		setSize(500, 100);

		getFieldUp().setSize(450, 20);
		getFieldUp().btn.setLangMessageID("C00609");
		getFieldUp().code.setMaximumSize(new Dimension(50, 20));
		getFieldUp().code.setMinimumSize(new Dimension(50, 20));
		getFieldUp().code.setPreferredSize(new Dimension(50, 20));
		getFieldUp().name.setMaximumSize(new Dimension(300, 20));
		getFieldUp().name.setMinimumSize(new Dimension(300, 20));
		getFieldUp().name.setPreferredSize(new Dimension(300, 20));
		getFieldUp().setLocation(0, 0);
		add(getFieldUp());

		getFieldFrom().setSize(450, 20);
		getFieldFrom().btn.setLangMessageID("C01012");
		getFieldFrom().code.setMaximumSize(new Dimension(100, 20));
		getFieldFrom().code.setMinimumSize(new Dimension(100, 20));
		getFieldFrom().code.setPreferredSize(new Dimension(100, 20));
		getFieldFrom().name.setMaximumSize(new Dimension(250, 20));
		getFieldFrom().name.setMinimumSize(new Dimension(250, 20));
		getFieldFrom().name.setPreferredSize(new Dimension(250, 20));
		getFieldFrom().setLocation(0, 25);
		add(getFieldFrom());

		getFieldTo().setSize(450, 20);
		getFieldTo().btn.setLangMessageID("C01143");
		getFieldTo().code.setMaximumSize(new Dimension(100, 20));
		getFieldTo().code.setMinimumSize(new Dimension(100, 20));
		getFieldTo().code.setPreferredSize(new Dimension(100, 20));
		getFieldTo().name.setMaximumSize(new Dimension(250, 20));
		getFieldTo().name.setMinimumSize(new Dimension(250, 20));
		getFieldTo().name.setPreferredSize(new Dimension(250, 20));
		getFieldTo().setLocation(0, 50);
		add(getFieldTo());

		getFieldUp().setCheckExist(false);
		getFieldFrom().setCheckExist(false);
		getFieldTo().setCheckExist(false);
	}

	/**
	 * ��ʏ����t�B�[���h��getter
	 * 
	 * @return ��ʏ����t�B�[���h
	 */
	public abstract TReference getFieldUp();

	/**
	 * �J�n�t�B�[���h��getter
	 * 
	 * @return �J�n�t�B�[���h
	 */
	public abstract TReference getFieldFrom();

	/**
	 * �I���t�B�[���h��getter
	 * 
	 * @return �I���t�B�[���h
	 */
	public abstract TReference getFieldTo();

	/**
	 * �N���A
	 */
	public void clear() {
		getFieldUp().clear();
		getFieldFrom().clear();
		getFieldTo().clear();
	}

	/**
	 * Editable�̐ݒ�
	 * 
	 * @param editable
	 */
	public void setEditable(boolean editable) {
		getFieldUp().setEditable(editable);
		getFieldFrom().setEditable(editable);
		getFieldTo().setEditable(editable);
	}

	/**
	 * �召�`�F�b�N
	 * 
	 * @return true(��薳��) / false(�G���[����)
	 */
	public boolean isSmallerFrom() {
		return (Util.isSmallerThen(getFieldFrom().getCode(), getFieldTo().getCode()));
	}

	/**
	 * �R�[�h�̑��݃`�F�b�N�����邩�ݒ�
	 * 
	 * @param checkExist true:�`�F�b�N����
	 */
	public void setCheckExist(boolean checkExist) {
		getFieldUp().setCheckExist(checkExist);
		getFieldFrom().setCheckExist(checkExist);
		getFieldTo().setCheckExist(checkExist);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		getFieldUp().setTabControlNo(tabControlNo);
		getFieldFrom().setTabControlNo(tabControlNo);
		getFieldTo().setTabControlNo(tabControlNo);
	}

	/**
	 * ���͂��ꂽ��ʏ����R�[�h��Ԃ�
	 * 
	 * @return ���͂��ꂽ�J�n�R�[�h
	 */
	public String getCodeUp() {
		return getFieldUp().code.getText();
	}

	/**
	 * ���͂��ꂽ�J�n�R�[�h��Ԃ�
	 * 
	 * @return ���͂��ꂽ�J�n�R�[�h
	 */
	public String getCodeFrom() {
		return getFieldFrom().code.getText();
	}

	/**
	 * ���͂��ꂽ�I���R�[�h��Ԃ�
	 * 
	 * @return ���͂��ꂽ�I���R�[�h
	 */
	public String getCodeTo() {
		return getFieldTo().code.getText();
	}

	/**
	 * ��ʏ����R�[�h�̃Z�b�g
	 * 
	 * @param txt ��ʏ����R�[�h
	 */
	public void setCodeUp(String txt) {
		getFieldUp().code.setText(txt);
	}

	/**
	 * �J�n�R�[�h�̃Z�b�g
	 * 
	 * @param txt �J�n�R�[�h
	 */
	public void setCodeFrom(String txt) {
		getFieldFrom().code.setText(txt);
	}

	/**
	 * �I���R�[�h�̃Z�b�g
	 * 
	 * @param txt �I���R�[�h
	 */
	public void setCodeTo(String txt) {
		getFieldTo().code.setText(txt);
	}
}