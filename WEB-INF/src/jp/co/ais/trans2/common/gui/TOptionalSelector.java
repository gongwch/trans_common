package jp.co.ais.trans2.common.gui;

import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;

/**
 * �C�ӑI��(�ʎw��)�R���|�[�l���g
 * 
 * @author AIS
 */
public abstract class TOptionalSelector extends TPanel {

	/** �ʑI���{�^�� */
	public TButton btn;

	/** �I���ꗗ */
	public TComboBox cbo;

	/** ��Џo�͒P�� */
	protected TCompanyOrganizationUnit companyOrgUnit = null;

	/**
	 * TTable�̃J������`
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** �R�[�h */
		code,

		/** ���� */
		names
	}

	/**
	 * �R���X�g���N�^.
	 */
	public TOptionalSelector() {

		initComponents();

		allocateComponents();
	}

	/**
	 * ������
	 */
	public void initComponents() {
		btn = new TButton();
		cbo = new TComboBox();
	}

	/**
	 * �z�u
	 */
	public void allocateComponents() {

		setSize(333, 20);
		setLayout(null);
		setOpaque(false);

		// �ʎw��{�^��
		btn.setSize(20, 80);
		btn.setOpaque(false);
		btn.setLocation(0, 0);
		btn.setLangMessageID("C11088"); // �C�ӎw��
		add(btn);

		// �I���ꗓ
		cbo.setSize(249, 20);
		cbo.setLocation(80, 0);
		cbo.setOpaque(false);
		add(cbo);
	}

	/**
	 * �R���g���[����getter
	 * 
	 * @return �R���g���[��
	 */
	public abstract TOptionalSelectorController getController();

	/**
	 * �^�u���ݒ�
	 * 
	 * @param index �^�u��
	 */
	public void setTabControlNo(int index) {
		btn.setTabControlNo(index);
		cbo.setTabControlNo(index);
	}

	/**
	 * �e�[�u���ꗗ�̃��t���b�V��
	 */
	public void refresh() {
		if (getController() != null && getController().getDialog() != null) {
			getController().refresh();
			getController().saveListData();
		}
	}

	/**
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		btn.setEnabled(enabled);
		cbo.setEnabled(enabled);
	}

	/**
	 * �I�����ꂽ�R�[�h���X�g��Ԃ�
	 * 
	 * @return �I�����ꂽ�R�[�h���X�g
	 */
	public List<String> getCodeList() {
		return getController().getCodeList();
	}

	/**
	 * �N���A����
	 */
	public void clear() {
		getController().clear();
	}

	/**
	 * ���������̃R�[�h���X�g���Z�b�g�i�����l�j
	 * 
	 * @param codeList
	 */
	public void setCodeList(List<String> codeList) {
		getController().setCodeList(codeList);
	}

	/**
	 * ��Џo�͒P�ʂ̎擾
	 * 
	 * @return companyOrgUnit ��Џo�͒P��
	 */
	public TCompanyOrganizationUnit getCompanyOrgUnit() {
		return companyOrgUnit;
	}

	/**
	 * ��Џo�͒P�ʂ̐ݒ�
	 * 
	 * @param companyOrgUnit ��Џo�͒P��
	 */
	public void setCompanyOrgUnit(TCompanyOrganizationUnit companyOrgUnit) {
		this.companyOrgUnit = companyOrgUnit;
	}

}
