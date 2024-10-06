package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �ȖځE�⏕�E����̃��j�b�g�R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TItemGroupController extends TController {

	/** �t�B�[���h */
	protected TItemGroup field;

	/**
	 * ��������<br>
	 * ���Y�����́A�ȖځA�⏕�A����̑S�Ẵt�B�[���h�ɓK�p�����B<br>
	 * �ȖځA�⏕�A���󂻂ꂼ��ɌʂɌ����������Z�b�g�������ꍇ�A<br>
	 * ���ꂼ���get���ăZ�b�g���邱�ƁB
	 */
	protected ItemGroupSearchCondition condition;

	/**
	 * @param field �t�B�[���h
	 */
	public TItemGroupController(TItemGroup field) {
		this.field = field;
		init();
	}

	/**
	 * ���������̃C���X�^���X�𐶐����Ԃ�
	 * 
	 * @return �V�K���������̃C���X�^���X
	 */
	protected ItemGroupSearchCondition createSearchCondition() {
		return new ItemGroupSearchCondition();
	}

	/**
	 * ������
	 */
	protected void init() {

		// �C�x���g��`
		addEvent();

		// �T�C�Y�ݒ�
		field.setSize(field.ctrlItemReference.getWidth(), 60);

		// ������g��Ȃ��ꍇ�A
		if (!getCompany().getAccountConfig().isUseDetailItem()) {
			field.ctrlDetailItemReference.setVisible(false);
			field.setSize(field.ctrlItemReference.getWidth(), 40);
		}

		clear();

		initSearchCondition();

	}

	/**
	 * ��������������������
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setItemCondition(field.ctrlItemReference.getSearchCondition());
		condition.setSubItemCondition(field.ctrlSubItemReference.getSearchCondition());
		condition.setDetailItemCondition(field.ctrlDetailItemReference.getSearchCondition());
	}

	/**
	 * �Ȗ� �⏕�Ȗڂ̃C�x���g��`�B
	 */
	protected void addEvent() {

		// �ȖڑI��
		field.ctrlItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !field.ctrlItemReference.isValueChanged()) {
					return;
				}

				ctrlItemReference_after();
			}
		});

		// �⏕�ȖڑI��
		field.ctrlSubItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !field.ctrlSubItemReference.isValueChanged()) {
					return;
				}

				ctrlSubItemReference_after();
			}
		});
	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * �t�B�[���h���N���A����
	 */
	public void clear() {
		field.ctrlItemReference.clear();
		field.ctrlSubItemReference.clear();
		field.ctrlDetailItemReference.clear();
		field.ctrlSubItemReference.setEditable(false);
		field.ctrlDetailItemReference.setEditable(false);
	}

	/**
	 * [�ȖڑI��]���̏���
	 */
	protected void ctrlItemReference_after() {
		Item entity = field.ctrlItemReference.getEntity();

		// �⏕�Ȗڂ����ꍇ�A�⏕�t�B�[���h����͉\�ɂ���B
		if (entity != null) {

			String subItemCode = field.ctrlSubItemReference.getCode();

			field.ctrlSubItemReference.clear();
			field.ctrlSubItemReference.setEditable(entity.hasSubItem());
			field.ctrlSubItemReference.getSearchCondition().setItemCode(entity.getCode());

			if (!Util.isNullOrEmpty(subItemCode) && field.ctrlSubItemReference.isEditable()) {
				// �⏕�ȖڃR�[�h���͂���

				field.ctrlSubItemReference.setCode(subItemCode);
				Item subItem = field.ctrlSubItemReference.controller.getInputtedEntity();
				if (subItem != null) {
					// ���Y�G���e�B�e�B��ݒ肷��
					field.ctrlSubItemReference.setEntity(subItem);
					// [�⏕�ȖڑI��]���̏���
					ctrlSubItemReference_after();
				} else {
					field.ctrlSubItemReference.clear();
					field.ctrlDetailItemReference.clear();
					field.ctrlDetailItemReference.setEditable(false);
					field.ctrlDetailItemReference.getSearchCondition().setItemCode(entity.getCode());
				}

			} else {
				field.ctrlDetailItemReference.clear();
				field.ctrlDetailItemReference.setEditable(false);
				field.ctrlDetailItemReference.getSearchCondition().setItemCode(entity.getCode());
			}

		} else {
			field.ctrlSubItemReference.clear();
			field.ctrlSubItemReference.setEditable(false);
			field.ctrlDetailItemReference.clear();
			field.ctrlDetailItemReference.setEditable(false);
		}
	}

	/**
	 * [�⏕�ȖڑI��]���̏���
	 */
	protected void ctrlSubItemReference_after() {
		Item entity = field.ctrlSubItemReference.getEntity();

		// ����Ȗڂ����ꍇ�A����t�B�[���h����͉\�ɂ���B
		if (entity != null && entity.getSubItem() != null) {
			field.ctrlDetailItemReference.clear();
			field.ctrlDetailItemReference.setEditable(entity.getSubItem().hasDetailItem());
			field.ctrlDetailItemReference.getSearchCondition().setItemCode(entity.getCode());
			field.ctrlDetailItemReference.getSearchCondition().setSubItemCode(entity.getSubItem().getCode());
		} else {
			field.ctrlDetailItemReference.clear();
			field.ctrlDetailItemReference.setEditable(false);
		}
	}

	/**
	 * �I�����ꂽ�ȖځE�⏕�E�����Ԃ�
	 * 
	 * @return �I�����ꂽ�ȖځE�⏕�E����<br>
	 *         (Item�̒��ɊK�w�I�ɉȖځE�⏕�E��������ĕԂ�)
	 */
	public Item getEntity() {

		// �I�����ꂽ�Ȗڂ��擾
		Item item = field.ctrlItemReference.getEntity();

		if (item == null) {
			return null;
		}

		// �⏕�܂ł���Ε⏕���Z�b�g
		if (field.ctrlSubItemReference.getEntity() != null
			&& field.ctrlSubItemReference.getEntity().getSubItem() != null) {

			item.setSubItem(field.ctrlSubItemReference.getEntity().getSubItem());

			// ����܂ł���Γ�����Z�b�g
			if (field.ctrlDetailItemReference.getEntity() != null
				&& field.ctrlDetailItemReference.getEntity().getSubItem() != null
				&& field.ctrlDetailItemReference.getEntity().getSubItem().getDetailItem() != null) {

				item.getSubItem().setDetailItem(field.ctrlDetailItemReference.getEntity().getSubItem().getDetailItem());

			} else {
				item.getSubItem().setDetailItem(null);
			}

		} else {
			item.setSubItem(null);
		}

		return item;
	}

	/**
	 * �I�����ꂽ�ȖځE�⏕�E�����Ԃ�
	 * 
	 * @return �I�����ꂽ�ȖځE�⏕�E����<br>
	 *         (Item�̒��ɊK�w�I�ɉȖځE�⏕�E��������ĕԂ�)<br>
	 *         <b>�����͓r��</b>
	 */
	public Item getInputtedEntity() {

		// �I�����ꂽ�Ȗڂ��擾
		Item item = field.ctrlItemReference.getEntity();

		if (item == null) {
			return null;
		}

		// �⏕�܂ł���Ε⏕���Z�b�g
		if (field.ctrlSubItemReference.getEntity() != null
			&& field.ctrlSubItemReference.getEntity().getSubItem() != null) {

			item.setSubItem(field.ctrlSubItemReference.getEntity().getSubItem());

			// ����܂ł���Γ�����Z�b�g
			if (field.ctrlDetailItemReference.getEntity() != null
				&& field.ctrlDetailItemReference.getEntity().getSubItem() != null
				&& field.ctrlDetailItemReference.getEntity().getSubItem().getDetailItem() != null) {

				item.getSubItem().setDetailItem(field.ctrlDetailItemReference.getEntity().getSubItem().getDetailItem());

			} else {
				// �N���A�����Ȃ�
				item.getSubItem().setDetailItem(field.ctrlDetailItemReference.isEditable());
			}

		} else {
			// �N���A�����Ȃ�
			item.setSubItem(field.ctrlSubItemReference.isEditable());
		}

		return item;
	}

	/**
	 * �ȖځE�⏕�E�����ݒ肷��.
	 * 
	 * @param item �ȖځE�⏕�E����
	 */
	public void setEntity(Item item) {
		field.ctrlItemReference.setEntity(item);
		ctrlItemReference_after();

		field.ctrlSubItemReference.setEntity(item);
		ctrlSubItemReference_after();

		field.ctrlDetailItemReference.setEntity(item);

		field.ctrlSubItemReference.setEditable(item != null && item.hasSubItem());
		field.ctrlDetailItemReference.setEditable(item != null && item.hasSubItem() && item.getSubItem() != null
			&& item.getSubItem().hasDetailItem());
	}

	/**
	 * ����������getter
	 * 
	 * @return ��������
	 */
	public ItemGroupSearchCondition getSearchCondition() {
		return condition;
	}

	/**
	 * �Ȗڂ̌�������getter
	 * 
	 * @return �Ȗڂ̌�������
	 */
	public ItemSearchCondition getItemSearchCondition() {
		return field.ctrlItemReference.getSearchCondition();
	}

	/**
	 * �⏕�Ȗڂ̌�������getter
	 * 
	 * @return �⏕�Ȗڂ̌�������
	 */
	public SubItemSearchCondition getSubItemSearchCondition() {
		return field.ctrlSubItemReference.getSearchCondition();
	}

	/**
	 * ����Ȗڂ̌�������getter
	 * 
	 * @return ����Ȗڂ̌�������
	 */
	public DetailItemSearchCondition getDetailItemSearchCondition() {
		return field.ctrlDetailItemReference.getSearchCondition();
	}

	/**
	 * �����͂����邩�ǂ���
	 * 
	 * @return true:�����͂���
	 */
	public boolean isEmpty() {

		Item item = getEntity();

		if (field.ctrlItemReference.isEmpty() || item == null) {
			return true;
		}

		if (item.hasSubItem()) {
			if (field.ctrlSubItemReference.isEmpty() || item.getSubItem() == null) {
				return true;
			}

			if (getCompany().getAccountConfig().isUseDetailItem() && item.getSubItem().hasDetailItem()) {
				if (field.ctrlDetailItemReference.isEmpty() || item.getDetailItem() == null) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * �V���������ŉȖڍČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		field.ctrlItemReference.refleshEntity();
		Item item = field.ctrlItemReference.getEntity();
		if (item == null) {
			setEntity(null);
		} else {
			setEntity(item);
		}
	}

	/**
	 * �V���������ŉȖڍČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshGroupEntity() {

		String itemCode = field.ctrlItemReference.code.getText();
		String subItemCode = field.ctrlSubItemReference.code.getText();
		String detailItemCode = field.ctrlDetailItemReference.code.getText();

		String kcompanyCode = field.ctrlItemReference.getSearchCondition().getCompanyCode();
		try {

			// ���������
			Item item = (Item) request(ItemManager.class, "getItem", kcompanyCode, itemCode, subItemCode,
				detailItemCode);

			if (item == null && !Util.isNullOrEmpty(detailItemCode)) {

				// �⏕������
				item = (Item) request(ItemManager.class, "getItem", kcompanyCode, itemCode, subItemCode, null);

				if (item == null && !Util.isNullOrEmpty(subItemCode)) {

					// �Ȗڂ�����
					item = (Item) request(ItemManager.class, "getItem", kcompanyCode, itemCode, null, null);

					if (item == null) {
						setEntity(null);
						return;
					}
				}
			}

			setEntity(item);

		} catch (TException e) {
			errorHandler(field, e);
		}

	}

	/**
	 * ���̓u�����N�`�F�b�N<br>
	 * �`�[���ׂ̃`�F�b�N���W�b�N��]�p
	 * 
	 * @see jp.co.ais.trans2.common.model.ui.slip.TSlipDetailPanelCtrl
	 * @return false:NG
	 */
	protected boolean checkInput() {
		// �Ȗ�
		if (!checkInputBlank(field.ctrlItemReference.code, field.ctrlItemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(field.ctrlSubItemReference.code, field.ctrlSubItemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(field.ctrlDetailItemReference.code, field.ctrlDetailItemReference.btn.getText())) {
			return false;
		}

		return true;
	}

	/**
	 * ���̓u�����N�`�F�b�N
	 * 
	 * @param field1 �Ώۃt�B�[���h
	 * @param name �G���[���̕\����
	 * @return false:NG
	 */
	protected boolean checkInputBlank(TTextField field1, String name) {

		if (field1.isVisible() && field1.isEditable() && field1.isEmpty()) {
			showMessage("I00037", name);// {0}����͂��Ă��������B
			field1.requestFocusInWindow();
			return false;
		}

		return true;
	}
}
