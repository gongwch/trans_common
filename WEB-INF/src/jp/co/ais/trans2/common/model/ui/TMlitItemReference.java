package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.mlit.item.*;

/**
 * �A�����уA�C�e���}�X�^�����R���|�[�l���g
 * 
 * @author AIS
 */
public class TMlitItemReference extends TReference {

	/** �R���g���[�� */
	protected TMlitItemReferenceController controller;

	/**
	 * �R���X�g���N�^�[
	 */
	public TMlitItemReference() {
		controller = createController();
	}

	/**
	 * @return �R���g���[��
	 */
	protected TMlitItemReferenceController createController() {
		return new TMlitItemReferenceController(this);
	}

	@Override
	protected TReferenceController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public YJItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public YJItem getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param bean Vessel�}�X�^
	 */
	public void setEntity(YJItem bean) {
		controller.setEntity(bean);
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}
}
