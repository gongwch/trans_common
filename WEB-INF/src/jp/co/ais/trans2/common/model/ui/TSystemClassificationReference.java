package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.program.SystemClassification;
import jp.co.ais.trans2.model.program.SystemClassificationSearchCondition;

/**
 * �V�X�e���̌����R���|�[�l���g
 * @author AIS
 *
 */
public class TSystemClassificationReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 8817226532784567130L;

	/** �R���g���[�� */
	protected TSystemClassificationReferenceController controller;

	/**
	 * 
	 *
	 */
	public TSystemClassificationReference() {

		// �R���g���[������
		controller = new TSystemClassificationReferenceController(this);
		
	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 * @return ��������
	 */
	public SystemClassificationSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * Entity���Z�b�g����
	 * @param bean Entity
	 */
	public void setEntity(SystemClassification bean) {
		controller.setEntity(bean);
	}

	/**
	 * Entity��Ԃ�
	 * @return Entity
	 */
	public SystemClassification getEntity() {
		return controller.getEntity();
	}

}
