package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.mlit.region.*;

/**
 * �A�����э��}�X�^�����R���|�[�l���g
 * 
 * @author AIS
 */
public class TMlitRegionReference extends TReference {

	/** �R���g���[�� */
	protected TMlitRegionReferenceController controller;

	/**
	 * �R���X�g���N�^�[
	 */
	public TMlitRegionReference() {
		controller = createController();
	}

	/**
	 * @return �R���g���[��
	 */
	protected TMlitRegionReferenceController createController() {
		return new TMlitRegionReferenceController(this);
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
	public YJRegionSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public YJRegion getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param bean Vessel�}�X�^
	 */
	public void setEntity(YJRegion bean) {
		controller.setEntity(bean);
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}
}
