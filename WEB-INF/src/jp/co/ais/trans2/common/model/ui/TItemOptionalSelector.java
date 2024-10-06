package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �Ȗڂ̔C�ӑI���R���|�[�l���g
 * 
 * @author AIS
 */
public class TItemOptionalSelector extends TOptionalSelector {

	/** �R���g���[�� */
	protected TItemOptionalSelectorController controller;

	/**
	 * �R���X�g���N�^
	 */
	public TItemOptionalSelector() {
		controller = createController();
	}

	/**
	 * �R���g���[���[�̎擾
	 * 
	 * @return �R���g���[���[
	 */
	public TItemOptionalSelectorController createController() {
		return new TItemOptionalSelectorController(this);
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public ItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TItemOptionalSelectorController getController() {
		return controller;
	}

}
