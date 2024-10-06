package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.mlit.item.*;

/**
 * �A�����уT�u�A�C�e���}�X�^�����R���|�[�l���g
 * 
 * @author AIS
 */
public class TMlitSubItemReference extends TReference {

	/** �R���g���[�� */
	protected TMlitSubItemReferenceController controller;

	/**
	 * �R���X�g���N�^�[
	 */
	public TMlitSubItemReference() {
		controller = createController();
	}

	/**
	 * @return �R���g���[��
	 */
	protected TMlitSubItemReferenceController createController() {
		return new TMlitSubItemReferenceController(this);
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
