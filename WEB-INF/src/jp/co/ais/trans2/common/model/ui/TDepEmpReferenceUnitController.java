package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.client.TController;

/**
 * ���͕���/���͎҃t�B�[���h�R���|�[�l���g�̃R���g���[��
 * @author AIS
 *
 */
public class TDepEmpReferenceUnitController extends TController {

	/** ���͕���/���͎҃R���|�[�l���g */
	protected TDepEmpReferenceUnit field;

	/**
	 * �R���X�g���N�^
	 * @param field �t�B�[���h
	 */
	public TDepEmpReferenceUnitController(TDepEmpReferenceUnit field) {
		this.field = field;
		init();
	}

	/**
	 * ������
	 *
	 */
	protected void init() {

		// ���͕���ɓ`�[�Q�ƌ����t�^
		field.ctrlDepartmentReference.setSlipRole(
				getUser().getSlipRole(),
				getUser().getDepartment());

		// ���͎҂ɓ`�[�Q�ƌ����t�^
		field.ctrlEmployeeReference.setSlipRole(
				getUser().getSlipRole(),
				getUser().getEmployee());

	}

}
