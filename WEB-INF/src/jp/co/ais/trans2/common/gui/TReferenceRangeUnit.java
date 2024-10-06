package jp.co.ais.trans2.common.gui;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.model.ui.*;

/**
 * �͈͑I���t�B�[���h�ƌʑI���t�B�[���h�̃Z�b�g
 */
public abstract class TReferenceRangeUnit extends TTitlePanel {

	/** �{�[�_�[��\�����邩 */
	protected boolean border = false;

	/** ��Џo�͒P�� */
	protected TCompanyOrganizationUnit companyOrgUnit = null;

	/** */
	public TReferenceRangeUnit() {
		this(false);
	}

	/**
	 * @param border
	 */
	public TReferenceRangeUnit(boolean border) {

		this(border, false);

	}

	/**
	 * @param border
	 * @param title �^�C�g���\�����ǂ���
	 */
	public TReferenceRangeUnit(boolean border, boolean title) {

		this.border = border;

		initComponents();

		allocateComponents(title);

	}

	/**
	 * �R���|�[�l���g�̏�����
	 */
	public abstract void initComponents();

	/**
	 * �R���|�[�l���g�̔z�u
	 * 
	 * @param title �^�C�g���\�����ǂ���
	 */
	public void allocateComponents(boolean title) {

		setOpaque(false);

		TReferenceRange referenceRange = getReferenceRange();
		referenceRange.setLocation(0, 0);
		add(referenceRange);

		TOptionalSelector optionalSelector = getOptionalSelector();
		optionalSelector.setLocation(0, referenceRange.getHeight());
		add(optionalSelector);

		setSize(optionalSelector.getWidth() + 5, referenceRange.getHeight() + optionalSelector.getHeight() + 5);

		if (border) {
			setSize(365, 95);
			if (title) {
				setLangMessageID(getBorderTitle());
				referenceRange.setLocation(15, 5);
			} else {
				setBorder(TBorderFactory.createTitledBorder(getBorderTitle()));
				this.titlePanel.setVisible(false);
				referenceRange.setLocation(15, 0);
			}
			optionalSelector.setLocation(15, referenceRange.getHeight() + referenceRange.getY());
		} else {
			this.titlePanel.setVisible(false);
			this.setBorder(null);
		}

	}

	/**
	 * �͈͎w��t�B�[���h��getter
	 * 
	 * @return �͈͎w��t�B�[���h
	 */
	public abstract TReferenceRange getReferenceRange();

	/**
	 * �C�ӑI��(�ʎw��)�R���|�[�l���g��getter
	 * 
	 * @return �C�ӑI��(�ʎw��)�R���|�[�l���g
	 */
	public abstract TOptionalSelector getOptionalSelector();

	/**
	 * �{�[�_�[�̃^�C�g����Ԃ�
	 * 
	 * @return �{�[�^�[�̃^�C�g��
	 */
	public abstract String getBorderTitle();

	/**
	 * �R�[�h�Ɨ��̂�����������
	 */
	public void clear() {
		getReferenceRange().getFieldFrom().clear();
		getReferenceRange().getFieldTo().clear();
		// TODO
		// getOptionalSelector().clear;
	}

	/**
	 * �ҏW�E�s����
	 * 
	 * @param edit
	 */
	public void setEditable(boolean edit) {
		getReferenceRange().getFieldFrom().setEditable(edit);
		getReferenceRange().getFieldTo().setEditable(edit);
		// TODO
		// getOptionalSelector().setEditable(edit);
	}

	/**
	 * Tab���ݒ�
	 * 
	 * @param no �^�u��
	 */
	public void setTabControlNo(int no) {
		getOptionalSelector().setTabControlNo(no);
		getReferenceRange().getFieldFrom().setTabControlNo(no);
		getReferenceRange().getFieldTo().setTabControlNo(no);
	}

	/**
	 * �召�`�F�b�N
	 * 
	 * @return true(��薳��) / false(�G���[����)
	 */
	public boolean isSmallerFrom() {
		return (Util.isSmallerThen(getReferenceRange().getFieldFrom().getCode(), getReferenceRange().getFieldTo()
			.getCode()));
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

		if (getReferenceRange() != null) {
			if (getReferenceRange().getFieldFrom() != null) {
				getReferenceRange().getFieldFrom().setCompanyOrgUnit(companyOrgUnit);
			}
			if (getReferenceRange().getFieldTo() != null) {
				getReferenceRange().getFieldTo().setCompanyOrgUnit(companyOrgUnit);
			}
		}
		if (getOptionalSelector() != null) {
			getOptionalSelector().setCompanyOrgUnit(companyOrgUnit);
		}
	}

}
