package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.mlit.region.*;

/**
 * �A�����ђn��}�X�^�����R���|�[�l���g
 * 
 * @author AIS
 */
public class TMlitCountryReference extends TReference {

	/** �R���g���[�� */
	protected TMlitCountryReferenceController controller;

	/**
	 * �R���X�g���N�^�[
	 */
	public TMlitCountryReference() {
		controller = createController();
	}

	/**
	 * @return �R���g���[��
	 */
	protected TMlitCountryReferenceController createController() {
		return new TMlitCountryReferenceController(this);
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
