package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * ����Ȗڃ}�X�^�͈͌����̃R���|�[�l���g
 */
public class TDetailItemRange extends TPanel {

	/** �Ȗڌ��� */
	public TItemReference itemReference;

	/** �⏕�Ȗڌ��� */
	public TSubItemReference subItemReference;

	/** ����Ȗڔ͈͌��� */
	public TDetailItemReferenceRange detailItemRange;

	/** �R���g���[�� */
	public TDetailItemRangeController controller;

	/**
	 * �R���X�g���N�^.
	 */
	public TDetailItemRange() {
		super();

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();
		controller = new TDetailItemRangeController(this);
	}

	/**
	 * �R���|�[�l���g�̏������B��ɃC���X�^���X�̐������s���܂��B
	 */
	public void initComponents() {
		itemReference = new TItemReference();
		subItemReference = new TSubItemReference();
		detailItemRange = new TDetailItemReferenceRange();
	}

	/**
	 * �R���g���[���[�̎擾
	 * 
	 * @return TReference
	 */
	public TDetailItemRangeController createController() {
		// �R���g���[������
		return new TDetailItemRangeController(this);
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public DetailItemSearchCondition getSearchCondition() {
		return controller.getDetailItemSearchCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public Item getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entity���Z�b�g
	 * 
	 * @param item Entity
	 */
	public void setEntity(Item item) {
		controller.setEntity(item);
	}

	/**
	 * �R���|�[�l���g�̔z�u���s���܂��B
	 */
	public void allocateComponents() {

		setLayout(null);
		setSize(400, 135);

		// �Ȗڌ���
		itemReference.setLocation(0, 0);
		add(itemReference);

		// �⏕�Ȗڌ���
		subItemReference.setLocation(0, 30);
		add(subItemReference);

		// ����Ȗڔ͈͌���
		detailItemRange.setLocation(0, 60);
		add(detailItemRange);
	}

	/**
	 * �R���|�[�l���g�̃^�u���̐ݒ���s���܂��B
	 * 
	 * @param tabControlNo
	 */
	public void setTabControlNo(int tabControlNo) {
		itemReference.setTabControlNo(tabControlNo);
		subItemReference.setTabControlNo(tabControlNo);
		detailItemRange.setTabControlNo(tabControlNo);
	}
}