package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.port.*;

/**
 * �`�̔C�ӑI���R���|�[�l���g
 * 
 * @author AIS
 */
public class TPortOptionalSelector extends TOptionalSelector {

	/** �R���g���[�� */
	protected TPortOptionalSelectorController controller;

	/**
	 * �R���X�g���N�^�[
	 */
	public TPortOptionalSelector() {
		createController();
	}

	/**
	 * �R���g���[���쐬
	 */
	protected void createController() {
		controller = new TPortOptionalSelectorController(this);
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public PortSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TPortOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * �I�����ꂽ�DEntity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ�DEntity�ꗗ
	 */
	public List<Port> getEntities() {
		return controller.getEntities();
	}

}
