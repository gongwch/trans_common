package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TReference.TYPE;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �ȖځE�⏕�E����̃��j�b�g�R���|�[�l���g
 * 
 * @author AIS
 */
public class TItemGroup extends TPanel {

	/** �Ȗڃt�B�[���h */
	public TItemReference ctrlItemReference;

	/** �⏕�Ȗڃt�B�[���h */
	public TSubItemReference ctrlSubItemReference;

	/** ����Ȗڃt�B�[���h */
	public TDetailItemReference ctrlDetailItemReference;

	/** �R���g���[�� */
	public TItemGroupController controller;

	/**
	 * �R���X�g���N�^
	 */
	public TItemGroup() {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();
		controller = createController();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public TItemGroup(TYPE type) {

		// �R���|�[�l���g������������
		initComponents(type);

		// �R���|�[�l���g��z�u����
		allocateComponents();
		controller = createController();
	}

	/**
	 * �R���g���[���[�̎擾
	 * 
	 * @return TItemGroupController
	 */
	public TItemGroupController createController() {
		// �R���g���[������
		return new TItemGroupController(this);
	}

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	protected void initComponents() {
		initComponents(TYPE.BUTTON);
	}

	/**
	 * �R���|�[�l���g������������<BR>
	 * 
	 * @param type �^�C�v
	 */
	protected void initComponents(TYPE type) {
		ctrlItemReference = new TItemReference(type);
		ctrlSubItemReference = new TSubItemReference(type);
		ctrlDetailItemReference = new TDetailItemReference(type);
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	protected void allocateComponents() {

		setSize(ctrlItemReference.getWidth(), 60);

		setLayout(null);

		// �Ȗ�
		ctrlItemReference.setLocation(0, 0);
		add(ctrlItemReference);

		// �⏕�Ȗ�
		ctrlSubItemReference.setLocation(0, 20);
		add(ctrlSubItemReference);

		// ����Ȗ�
		ctrlDetailItemReference.setLocation(0, 40);
		add(ctrlDetailItemReference);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		ctrlItemReference.setTabControlNo(tabControlNo);
		ctrlSubItemReference.setTabControlNo(tabControlNo);
		ctrlDetailItemReference.setTabControlNo(tabControlNo);
	}

	/**
	 * �I�����ꂽ�ȖځE�⏕�E�����Ԃ�
	 * 
	 * @return �I�����ꂽ�ȖځE�⏕�E����<br>
	 *         (Item�̒��ɊK�w�I�ɉȖځE�⏕�E��������ĕԂ�)
	 */
	public Item getEntity() {
		return controller.getEntity();
	}

	/**
	 * �I�����ꂽ�ȖځE�⏕�E�����Ԃ�
	 * 
	 * @return �I�����ꂽ�ȖځE�⏕�E����<br>
	 *         (Item�̒��ɊK�w�I�ɉȖځE�⏕�E��������ĕԂ�)<br>
	 *         <b>�����͓r��</b>
	 */
	public Item getInputtedEntity() {
		return controller.getInputtedEntity();
	}

	/**
	 * �ȖځE�⏕�E�����ݒ肷��.
	 * 
	 * @param item �ȖځE�⏕�E����
	 */
	public void setEntity(Item item) {
		controller.setEntity(item);
	}

	/**
	 * ����������getter
	 * 
	 * @return ��������
	 */
	public ItemGroupSearchCondition getSearchCondition() {
		return controller.getSearchCondition();
	}

	/**
	 * �Ȗڂ̌�������getter
	 * 
	 * @return �Ȗڂ̌�������
	 */
	public ItemSearchCondition getItemSearchCondition() {
		return controller.getItemSearchCondition();
	}

	/**
	 * �⏕�Ȗڂ̌�������getter
	 * 
	 * @return �⏕�Ȗڂ̌�������
	 */
	public SubItemSearchCondition getSubItemSearchCondition() {
		return controller.getSubItemSearchCondition();
	}

	/**
	 * ����Ȗڂ̌�������getter
	 * 
	 * @return ����Ȗڂ̌�������
	 */
	public DetailItemSearchCondition getDetailItemSearchCondition() {
		return controller.getDetailItemSearchCondition();
	}

	/**
	 * �N���A
	 */
	public void clear() {
		controller.clear();
	}

	/**
	 * �R�[���o�b�N���X�i�[�ݒ�
	 * 
	 * @param listener �R�[���o�b�N���X�i�[
	 */
	public void addCallBackListener(TCallBackListener listener) {
		ctrlItemReference.addCallBackListener(listener);
		ctrlSubItemReference.addCallBackListener(listener);
		ctrlDetailItemReference.addCallBackListener(listener);
	}

	/**
	 * �����͂����邩�ǂ���
	 * 
	 * @return true:�����͂���
	 */
	public boolean isEmpty() {
		return controller.isEmpty();
	}

	@Override
	public void requestFocus() {
		ctrlItemReference.requestTextFocus();
	}

	/**
	 * �V���������ŉȖڍČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

	/**
	 * �V���������ŉȖڍČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshGroupEntity() {
		controller.refleshGroupEntity();
	}

	/**
	 * ���̓`�F�b�N
	 * 
	 * @return false:NG
	 */
	public boolean checkInput() {
		return controller.checkInput();
	}

}
