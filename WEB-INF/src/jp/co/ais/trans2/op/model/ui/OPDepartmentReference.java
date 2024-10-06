package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.model.department.*;

/**
 * ���匟���R���|�[�l���g
 * 
 * @author AIS
 */
public class OPDepartmentReference extends TDepartmentReference {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title
	 */
	public OPDepartmentReference(String title) {
		super(title);
	}

	/**
	 * �R���X�g���N�^
	 */
	public OPDepartmentReference() {
		super();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public OPDepartmentReference(TYPE type) {
		super(type);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 * @param title
	 */
	public OPDepartmentReference(TYPE type, String title) {
		super(type, title);
	}

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	@Override
	protected void initComponents() {

		// �g���I�y���ʑΉ�
		if (isLabelMode()) {
			// �����I�Ƀ��x���ɂ���
			this.type = TYPE.LABEL;
		}

		super.initComponents();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	@Override
	protected void allocateComponents() {

		// �g���I�y���ʑΉ�
		if (isLabelMode()) {
			OPGuiUtil.allocateComponents(this);
		} else {
			super.allocateComponents();
		}
	}

	/**
	 * @return true:���x�����[�h
	 */
	protected boolean isLabelMode() {
		return OPGuiUtil.isLabelMode();
	}

	/**
	 * �R���g���[������
	 * 
	 * @return �R���g���[��
	 */
	@Override
	protected OPDepartmentReferenceController createController() {
		return new OPDepartmentReferenceController(this);
	}

	@Override
	protected OPDepartmentReferenceController getController() {
		return (OPDepartmentReferenceController) controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	@Override
	public DepartmentSearchCondition getSearchCondition() {
		return getController().getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	@Override
	public Department getEntity() {
		return getController().getEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param department ����
	 */
	@Override
	public void setEntity(Department department) {
		getController().setEntity(department);
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	@Override
	public void refleshEntity() {
		getController().refleshEntity();
	}

	/**
	 * ����ݒ�
	 * 
	 * @param termDate
	 */
	public void setTermDate(Date termDate) {
		getController().setTermDate(termDate);
	}

}
