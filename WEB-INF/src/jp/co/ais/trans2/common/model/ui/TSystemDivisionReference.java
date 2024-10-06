package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.TReference;
import jp.co.ais.trans2.common.gui.TReferenceController;
import jp.co.ais.trans2.model.program.SystemDivision;
import jp.co.ais.trans2.model.program.SystemDivisionSearchCondition;


/**
 * �V�X�e���敪�����R���|�[�l���g
 */
public class TSystemDivisionReference extends TReference {

	/** �R���g���[�� */
	public TSystemDivisionReferenceController controller;

	/**
	 * �R���X�g���N�^
	 *
	 * @param title
	 */
	public TSystemDivisionReference(String title) {
		super(title);
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 */
	public TSystemDivisionReference() {
		super();
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 *
	 * @param type �^�C�v
	 */
	public TSystemDivisionReference(TYPE type) {
		super(type);
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 *
	 * @param type �^�C�v
	 * @param title
	 */
	public TSystemDivisionReference(TYPE type, String title) {
		super(type, title);
		controller = createController();
	}

	/**
	 * �R���g���[������
	 *
	 * @return �R���g���[��
	 */
	public TSystemDivisionReferenceController createController() {
		return new TSystemDivisionReferenceController(this);
	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 *
	 * @return ��������
	 */
	public SystemDivisionSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 *
	 * @return �I�����ꂽEntity
	 */
	public SystemDivision getEntity() {
		return controller.getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 *
	 * @param entity �G���e�B�e�B
	 */
	public void setEntity(SystemDivision entity) {
		controller.setEntity(entity);
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}
}
