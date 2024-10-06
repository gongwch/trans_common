package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �Ȗڑ̌n�����R���|�[�l���g
 * 
 * @author AIS
 */
public class TItemOrganizationReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = 7346976337342780514L;

	/** �R���g���[�� */
	protected TItemOrganizationReferenceController controller;

	/**
	 * 
	 *
	 */
	public TItemOrganizationReference() {
		controller = createController();
	}

	/**
	 * @return �R���g���[���[
	 */
	protected TItemOrganizationReferenceController createController() {
		return new TItemOrganizationReferenceController(this);
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
	public ItemOrganizationSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public ItemOrganization getEntity() {
		return controller.getEntity();
	}

	/**
	 * ��{�Ȗڑ̌n���Z�b�g����
	 */
	public void setBaseItemOrganization() {
		controller.setBaseItemOrganization();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param itemOrganization �Ȗڑ̌n
	 */
	public void setEntity(ItemOrganization itemOrganization) {
		controller.setEntity(itemOrganization);
	}

	@Override
	protected int getCodeLength() {
		return TransUtil.ITEM_ORGANIZATION_CODE_LENGTH;
	}

	@Override
	protected void allocateComponents() {
		super.allocateComponents();
		code.setMaximumSize(new Dimension(30, 20));
		code.setMinimumSize(new Dimension(30, 20));
		code.setPreferredSize(new Dimension(30, 20));
		resize();
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	@Override
	public void refleshAndShowEntity() {
		controller.refleshEntity();
		controller.setEntity(getEntity());
	}
}
