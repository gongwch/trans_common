package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �⏕�Ȗڂ̔C�ӑI���R���|�[�l���g
 * 
 * @author AIS
 */
public class TSubItemOptionalSelector extends TOptionalSelector {

	/** serialVersionUID */
	private static final long serialVersionUID = 6720856141179813605L;

	/** �R���g���[�� */
	protected TSubItemOptionalSelectorController controller;

	/**
	 * 
	 *
	 */
	public TSubItemOptionalSelector() {
		createController();
	}

	/**
	 * �R���g���[���[�̎擾
	 */
	public void createController() {
		controller = new TSubItemOptionalSelectorController(this);
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public SubItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TSubItemOptionalSelectorController getController() {
		return controller;
	}

}
