package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;

import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �Ȗڑg�D���x���͈̔̓��j�b�g<br>
 * (�\�����x��+�Ȗڑ̌n+�Ȗڔ͈�+�ȖڔC�ӎw��)
 * 
 * @author AIS
 */
public class TItemOrganizationUnitController extends TController {

	/**
	 * �\�����x���I�����
	 */
	protected enum MODE {
		/** �Ȗ� */
		Item,

		/** �⏕�Ȗ� */
		SubItem,

		/** ����Ȗ� */
		DetailItem;
	}

	/** �I��\�����x�� */
	protected MODE Mode = MODE.Item;

	/** �t�B�[���h */
	protected TItemOrganizationUnit field;

	/** ���Entity */
	protected Company company;

	/** �\�����x���ɂ���ăy�[�W�𕪂��Ȃ� true�F�����Ȃ� */
	protected boolean isItemLevelNoSep = ClientConfig.isFlagOn("trans.item.level.no.separate");

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field
	 */
	public TItemOrganizationUnitController(TItemOrganizationUnit field) {
		this.field = field;
		init();
	}

	/**
	 * ������
	 */
	protected void init() {
		// ��Џ�����
		company = getCompany();

		// �C�x���g�ݒ�
		addEvent();
	}

	/**
	 * �C�x���g��`
	 */
	protected void addEvent() {

		// [�Ȗڑ̌n]�ύX��
		field.ctrlItemOrganization.addCallBackListener(new TCallBackListener() {

			@Override
			public void after() {
				ctrlItemOrganization_after();
			}
		});

		// [�\�����x��]�ύX��
		field.ctrlItemLevelChooser.rdoItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Mode = MODE.Item;
				ctrlItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom, true);
				ctrlItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo, true);
			}
		});
		field.ctrlItemLevelChooser.rdoSubItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MODE preMode = Mode;
				Mode = MODE.SubItem;
				field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom.refleshGroupEntity();
				field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo.refleshGroupEntity();

				if (preMode == MODE.Item) {
					ctrlItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom, true);
					ctrlItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo, true);
				} else {
					ctrlSubItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom, true);
					ctrlSubItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo, true);
				}
			}
		});
		field.ctrlItemLevelChooser.rdoDetailItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				MODE preMode = Mode;
				Mode = MODE.DetailItem;
				field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom.refleshGroupEntity();
				field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo.refleshGroupEntity();

				if (preMode == MODE.Item) {
					ctrlItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom, true);
					ctrlItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo, true);
				} else {
					ctrlSubItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom, true);
					ctrlSubItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo, true);
				}
			}
		});

		// [�Ȗڔ͈́F�Ȗ�]�ύX��
		field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom.ctrlItemReference
			.addCallBackListener(new TCallBackListener() {

				@Override
				public void after(boolean flag) {
					if (!flag
						|| !field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom.ctrlItemReference.isValueChanged()) {
						return;
					}
					ctrlItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom, false);
				}
			});
		field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo.ctrlItemReference
			.addCallBackListener(new TCallBackListener() {

				@Override
				public void after(boolean flag) {
					if (!flag
						|| !field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo.ctrlItemReference.isValueChanged()) {
						return;
					}
					ctrlItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo, false);
				}
			});

		// [�Ȗڔ͈́F�⏕�Ȗ�]�ύX��
		field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom.ctrlSubItemReference
			.addCallBackListener(new TCallBackListener() {

				@Override
				public void after(boolean flag) {
					if (!flag
						|| !field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom.ctrlSubItemReference
							.isValueChanged()) {
						return;
					}
					ctrlSubItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom, false);
				}
			});
		field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo.ctrlSubItemReference
			.addCallBackListener(new TCallBackListener() {

				@Override
				public void after(boolean flag) {
					if (!flag
						|| !field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo.ctrlSubItemReference
							.isValueChanged()) {
						return;
					}
					ctrlSubItemReference_after(field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo, false);
				}
			});
	}

	/**
	 * [�ȖڑI��]���̏���
	 * 
	 * @param fieldGroup �ȖڃO���[�v
	 * @param isLevel �\�����x�����삩�ǂ���
	 */
	protected void ctrlItemReference_after(TItemGroup fieldGroup, boolean isLevel) {
		Item entity = fieldGroup.ctrlItemReference.getEntity();

		if (!isLevel || (isLevel && !isItemLevelNoSep)) {
			fieldGroup.ctrlSubItemReference.clear();
		}

		// �⏕�Ȗڂ����ꍇ�A�⏕�t�B�[���h����͉\�ɂ���
		// �A���A�\�����x�����u�Ȗځv�̏ꍇ�͓��͕s�ɂ���
		if (entity != null) {
			boolean isItemLevelEditable = isItemLevelNoSep || (!isItemLevelNoSep && this.Mode != MODE.Item);

			fieldGroup.ctrlSubItemReference.setEditable(isItemLevelEditable && entity.hasSubItem());
			fieldGroup.ctrlSubItemReference.getSearchCondition().setItemCode(entity.getCode());

			if (!isLevel || (isLevel && !isItemLevelNoSep)) {
				fieldGroup.ctrlDetailItemReference.clear();
				fieldGroup.ctrlDetailItemReference.setEditable(false);
			}
			fieldGroup.ctrlDetailItemReference.getSearchCondition().setItemCode(entity.getCode());
		} else {
			fieldGroup.ctrlSubItemReference.clear();
			fieldGroup.ctrlSubItemReference.setEditable(false);

			fieldGroup.ctrlDetailItemReference.clear();
			fieldGroup.ctrlDetailItemReference.setEditable(false);
		}
	}

	/**
	 * [�⏕�ȖڑI��]���̏���
	 * 
	 * @param fieldGroup �ȖڃO���[�v
	 * @param isLevel �\�����x�����삩�ǂ���
	 */
	protected void ctrlSubItemReference_after(TItemGroup fieldGroup, boolean isLevel) {
		Item entity = fieldGroup.ctrlSubItemReference.getEntity();

		if (!isLevel || (isLevel && !isItemLevelNoSep)) {
			fieldGroup.ctrlDetailItemReference.clear();
		}

		// ����Ȗڂ����ꍇ�A����t�B�[���h����͉\�ɂ���
		// �A���A�\�����x�����u����Ȗځv�łȂ��ꍇ�͓��͕s�ɂ���
		if (entity != null && entity.getSubItem() != null) {
			boolean isItemLevelEditable = isItemLevelNoSep || (!isItemLevelNoSep && this.Mode == MODE.DetailItem);

			fieldGroup.ctrlDetailItemReference.setEditable(isItemLevelEditable && entity.getSubItem().hasDetailItem());
			fieldGroup.ctrlDetailItemReference.getSearchCondition().setItemCode(entity.getCode());
			fieldGroup.ctrlDetailItemReference.getSearchCondition().setSubItemCode(entity.getSubItem().getCode());
		} else {
			fieldGroup.ctrlDetailItemReference.setEditable(false);
		}
	}

	/**
	 * [�Ȗڑ̌n]�ύX���A�Ȗڔ͈̓R���|�[�l���g�֔��f����
	 */
	protected void ctrlItemOrganization_after() {

		// �ȖڃR���|�[�l���g�ɉȖڑ̌n�̏����Z�b�g
		String ioCode = field.ctrlItemOrganization.getCode();
		field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupFrom.getSearchCondition().setItemOrganizationCode(ioCode);
		field.ctrlItemRange.ctrlItemGroupRange.ctrlItemGroupTo.getSearchCondition().setItemOrganizationCode(ioCode);
		field.ctrlItemRange.ctrlItemGroupOptionalSelector.getSearchCondition().setItemOrganizationCode(ioCode);
		field.ctrlItemRange.ctrlItemGroupOptionalSelector.getSubItemSearchCondition().setItemOrganizationCode(ioCode);
		field.ctrlItemRange.ctrlItemGroupOptionalSelector.getDetailItemSearchCondition()
			.setItemOrganizationCode(ioCode);

		// �Ȗڑ̌n���ύX�ɂȂ�����Ȗڔ͈͂��N���A����B
		System.out.println(field.ctrlItemOrganization.isValueChanged());
		if (field.ctrlItemOrganization.getEntity() != null && field.ctrlItemOrganization.isValueChanged()) {
			field.ctrlItemRange.clear();
		}
	}

}
