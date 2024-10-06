package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.item.*;

/**
 * ����Ȗڔ͈͌����R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TDetailItemRangeController extends TController {

	/** �������� */
	protected ItemGroupSearchCondition condition;

	/** �t�B�[���h */
	protected TDetailItemRange field;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TDetailItemRangeController(TDetailItemRange field) {
		this.field = field;
		init();
	}

	/**
	 * ������
	 */
	public void init() {

		// �C�x���g��`
		addEvent();

		// �T�C�Y�ݒ�
		field.setSize(field.detailItemRange.getWidth(), 60);

		// ������g��Ȃ��ꍇ�A
		if (!getCompany().getAccountConfig().isUseDetailItem()) {
			field.detailItemRange.setVisible(false);
			field.setSize(field.detailItemRange.getWidth(), 40);
		}

		clear();

		// ��������������
		initSearchCondition();
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
	 * ��������������������
	 */
	protected void initSearchCondition() {
		condition = createSearchCondition();
		condition.setItemCondition(field.itemReference.getSearchCondition());
		condition.setSubItemCondition(field.subItemReference.getSearchCondition());
		condition.setDetailItemCondition(field.detailItemRange.ctrlDetailItemReferenceFrom.getSearchCondition());
		condition.setDetailItemCondition(field.detailItemRange.ctrlDetailItemReferenceTo.getSearchCondition());
	}

	/**
	 * �Ȗ� �⏕�Ȗڂ̃C�x���g��`�B
	 */
	protected void addEvent() {

		// �ȖڑI��
		field.itemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !field.itemReference.isValueChanged()) {
					return;
				}

				ctrlItemReference_after();
			}
		});

		// �⏕�ȖڑI��
		field.subItemReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !field.subItemReference.isValueChanged()) {
					return;
				}

				ctrlSubItemReference_after();
			}
		});

		// ����ȖڊJ�n�I��
		field.detailItemRange.ctrlDetailItemReferenceFrom.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !field.detailItemRange.ctrlDetailItemReferenceFrom.isValueChanged()) {
					return;
				}

				ctrlDetailItemRangeFrom_after();
			}
		});

		// ����ȖڏI���I��
		field.detailItemRange.ctrlDetailItemReferenceTo.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag || !field.detailItemRange.ctrlDetailItemReferenceTo.isValueChanged()) {
					return;
				}

				ctrlDetailItemRangeTo_after();
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
		field.itemReference.clear();
		field.subItemReference.clear();
		field.detailItemRange.ctrlDetailItemReferenceFrom.clear();
		field.detailItemRange.ctrlDetailItemReferenceTo.clear();
		field.subItemReference.setEditable(false);
		field.detailItemRange.ctrlDetailItemReferenceFrom.setEditable(false);
		field.detailItemRange.ctrlDetailItemReferenceTo.setEditable(false);
	}

	/**
	 * [�ȖڑI��]���̏���
	 */
	public void ctrlItemReference_after() {
		Item entity = field.itemReference.getEntity();

		// �⏕�Ȗڂ����ꍇ�A�⏕�t�B�[���h����͉\�ɂ���B
		if (entity != null) {
			field.subItemReference.clear();
			field.subItemReference.setEditable(entity.hasSubItem());
			field.subItemReference.getSearchCondition().setItemCode(entity.getCode());
			field.detailItemRange.clear();
			field.detailItemRange.setEditable(false);
			field.detailItemRange.ctrlDetailItemReferenceFrom.getSearchCondition().setItemCode(entity.getCode());
			field.detailItemRange.ctrlDetailItemReferenceTo.getSearchCondition().setItemCode(entity.getCode());
		} else {
			field.subItemReference.clear();
			field.subItemReference.setEditable(false);
			field.detailItemRange.clear();
			field.detailItemRange.setEditable(false);
		}
	}

	/**
	 * [�⏕�ȖڑI��]���̏���
	 */
	protected void ctrlSubItemReference_after() {
		Item entity = field.subItemReference.getEntity();

		// �⏕�Ȗڂ����ꍇ�A�⏕�t�B�[���h����͉\�ɂ���B
		if (entity != null) {
			field.detailItemRange.clear();
			field.detailItemRange.setEditable(entity.getSubItem().hasDetailItem());
			field.detailItemRange.ctrlDetailItemReferenceFrom.getSearchCondition().setItemCode(entity.getCode());
			field.detailItemRange.ctrlDetailItemReferenceTo.getSearchCondition().setItemCode(entity.getCode());
			field.detailItemRange.ctrlDetailItemReferenceFrom.getSearchCondition().setSubItemCode(
				entity.getSubItem().getCode());
			field.detailItemRange.ctrlDetailItemReferenceTo.getSearchCondition().setSubItemCode(
				entity.getSubItem().getCode());
		} else {
			field.detailItemRange.clear();
			field.detailItemRange.setEditable(false);
		}
	}

	/**
	 * [����ȖڊJ�n�I��]���̏���
	 */
	@SuppressWarnings("unused")
	public void ctrlDetailItemRangeFrom_after() {
		Item entity = field.detailItemRange.ctrlDetailItemReferenceFrom.getEntity();
	}

	/**
	 * [����ȖڏI���I��]���̏���
	 */
	@SuppressWarnings("unused")
	public void ctrlDetailItemRangeTo_after() {
		Item entity = field.detailItemRange.ctrlDetailItemReferenceTo.getEntity();
	}

	/**
	 * �I�����ꂽ�ȖځE�⏕�E�����Ԃ�
	 * 
	 * @return �I�����ꂽ�ȖځE�⏕�E����<br>
	 *         (Item�̒��ɊK�w�I�ɉȖځE�⏕�E��������ĕԂ�)
	 */
	public Item getEntity() {

		// �I�����ꂽ�Ȗڂ��擾
		Item item = field.itemReference.getEntity();

		if (item == null) {
			return null;
		}

		// �⏕�܂ł���Ε⏕���Z�b�g
		if (field.subItemReference.getEntity() != null && field.subItemReference.getEntity().getSubItem() != null) {

			item.setSubItem(field.subItemReference.getEntity().getSubItem());

			// ����܂ł���Γ�����Z�b�g
			if (field.detailItemRange.ctrlDetailItemReferenceFrom.getEntity() != null
				&& field.detailItemRange.ctrlDetailItemReferenceFrom.getEntity().getSubItem() != null
				&& field.detailItemRange.ctrlDetailItemReferenceTo.getEntity().getSubItem().getDetailItem() != null) {
				item.getSubItem().setDetailItem(
					field.detailItemRange.ctrlDetailItemReferenceFrom.getEntity().getSubItem().getDetailItem());
				item.getSubItem().setDetailItem(
					field.detailItemRange.ctrlDetailItemReferenceTo.getEntity().getSubItem().getDetailItem());
			} else {
				item.getSubItem().setDetailItem(null);
			}

		} else {
			item.setSubItem(null);
		}

		return item;
	}

	/**
	 * �ȖځE�⏕�E�����ݒ肷��.
	 * 
	 * @param item �ȖځE�⏕�E����
	 */
	public void setEntity(Item item) {
		field.itemReference.setEntity(item);
		ctrlItemReference_after();

		field.subItemReference.setEntity(item);
		ctrlSubItemReference_after();

		field.detailItemRange.ctrlDetailItemReferenceFrom.setEntity(item);
		ctrlDetailItemRangeFrom_after();

		field.detailItemRange.ctrlDetailItemReferenceTo.setEntity(item);
		ctrlDetailItemRangeTo_after();

		field.subItemReference.setEditable(item != null && item.hasSubItem());
		field.detailItemRange.ctrlDetailItemReferenceFrom.setEditable(item != null && item.hasSubItem()
			&& item.getSubItem() != null && item.getSubItem() != null && item.getSubItem().hasDetailItem());
		field.detailItemRange.ctrlDetailItemReferenceTo.setEditable(item != null && item.hasSubItem()
			&& item.getSubItem() != null && item.getSubItem() != null && item.getSubItem().hasDetailItem());
	}

	/**
	 * ����������getter
	 * 
	 * @return ��������
	 */
	public ItemSearchCondition getSearchCondition() {
		return getSearchCondition();
	}

	/**
	 * �Ȗڂ̌�������getter
	 * 
	 * @return �Ȗڂ̌�������
	 */
	public ItemSearchCondition getItemSearchCondition() {
		return field.itemReference.getSearchCondition();
	}

	/**
	 * �⏕�Ȗڂ̌�������getter
	 * 
	 * @return �⏕�Ȗڂ̌�������
	 */
	public SubItemSearchCondition getSubItemSearchCondition() {
		return field.subItemReference.getSearchCondition();
	}

	/**
	 * ����Ȗڂ̌�������getter
	 * 
	 * @return ����Ȗڂ̌�������
	 */
	public DetailItemSearchCondition getDetailItemSearchCondition() {
		return field.getSearchCondition();
	}

	/**
	 * �����͂����邩�ǂ���
	 * 
	 * @return true:�����͂���
	 */
	public boolean isEmpty() {

		Item item = getEntity();

		if (field.itemReference.isEmpty() || item == null) {
			return true;
		}

		if (item.hasSubItem()) {
			if (field.subItemReference.isEmpty() || item.getSubItem() == null) {
				return true;
			}

			if (getCompany().getAccountConfig().isUseDetailItem() && item.getSubItem().hasDetailItem()) {
				if (field.detailItemRange.ctrlDetailItemReferenceFrom.isEmpty()
					|| field.detailItemRange.ctrlDetailItemReferenceTo.isEmpty() || item.getDetailItem() == null) {
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
		field.itemReference.refleshEntity();
		Item item = field.itemReference.getEntity();
		if (item == null) {
			setEntity(null);
		} else {
			setEntity(item);
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
		if (!checkInputBlank(field.itemReference.code, field.itemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(field.subItemReference.code, field.subItemReference.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(field.detailItemRange.ctrlDetailItemReferenceFrom.code,
			field.detailItemRange.ctrlDetailItemReferenceFrom.btn.getText())) {
			return false;
		}
		if (!checkInputBlank(field.detailItemRange.ctrlDetailItemReferenceTo.code,
			field.detailItemRange.ctrlDetailItemReferenceTo.btn.getText())) {
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
	private boolean checkInputBlank(TTextField field1, String name) {

		if (field1.isVisible() && field1.isEditable() && field1.isEmpty()) {
			showMessage("I00037", name);// {0}����͂��Ă��������B
			field1.requestFocusInWindow();
			return false;
		}

		return true;
	}
}