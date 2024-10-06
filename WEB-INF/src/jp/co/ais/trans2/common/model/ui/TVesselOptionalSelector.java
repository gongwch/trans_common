package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * �D�̔C�ӑI���R���|�[�l���g
 * 
 * @author AIS
 */
public class TVesselOptionalSelector extends TOptionalSelector {

	/** �R���g���[�� */
	protected TVesselOptionalSelectorController controller;

	/**
	 * �R���X�g���N�^�[
	 */
	public TVesselOptionalSelector() {
		createController();
	}

	/**
	 * �R���g���[���쐬
	 */
	protected void createController() {
		controller = new TVesselOptionalSelectorController(this);
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public VesselSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TVesselOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * �I�����ꂽ�DEntity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ�DEntity�ꗗ
	 */
	public List<Vessel> getEntities() {
		return controller.getEntities();
	}

}
