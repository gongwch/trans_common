package jp.co.ais.trans2.common.model.ui;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.*;

/**
 * �Ȗڂ̕\�����x���I���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TItemLevelChooserController extends TController {

	/** �t�B�[���h */
	protected TItemLevelChooser field;

	/**
	 * @param field �t�B�[���h
	 */
	public TItemLevelChooserController(TItemLevelChooser field) {
		this.field = field;
		init();
	}

	/**
	 * ������
	 */
	protected void init() {

		// �C�x���g��`
		addEvent();

		// �T�C�Y�ݒ�
		if (field.getDirection() == SwingConstants.VERTICAL) {
			field.setSize(130, 85);
		} else {
			field.setSize(310, 50);
		}

		// caption
		field.rdoItem.setLangMessageID(getCompany().getAccountConfig().getItemName());
		field.rdoSubItem.setLangMessageID(getCompany().getAccountConfig().getSubItemName());
		field.rdoDetailItem.setLangMessageID(getCompany().getAccountConfig().getDetailItemName());

		// ������g��Ȃ��ꍇ�A
		if (!getCompany().getAccountConfig().isUseDetailItem()) {
			field.rdoDetailItem.setVisible(false);
			field.setSize(220, 50);
			if (field.getDirection() == SwingConstants.VERTICAL) {
				field.setSize(130, 75);
				if (field.getTitle()) {
					field.rdoItem.setLocation(field.rdoItem.getX(), 5);
					field.rdoSubItem.setLocation(field.rdoSubItem.getX(), 30);
				} else {
					field.rdoItem.setLocation(field.rdoItem.getX(), 18);
					field.rdoSubItem.setLocation(field.rdoSubItem.getX(), 43);
				}
			}
		}

		field.rdoItem.setSelected(true);

	}

	/**
	 * �C�x���g����
	 */
	protected void addEvent() {
		//
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
	 * �I�����ꂽ�Ȗڃ��x����Ԃ�
	 * 
	 * @return �I�����ꂽ�Ȗڃ��x��
	 */
	public ItemLevel getItemLevel() {
		if (field.rdoItem.isSelected()) {
			return ItemLevel.ITEM;
		} else if (field.rdoSubItem.isSelected()) {
			return ItemLevel.SUBITEM;
		} else if (field.rdoDetailItem.isSelected()) {
			return ItemLevel.DETAILITEM;
		}
		return null;
	}

	/**
	 * �Ȗڃ��x�����Z�b�g����
	 * 
	 * @param itemLevel �Ȗڃ��x��
	 */
	public void setItemLevel(ItemLevel itemLevel) {
		if (itemLevel == null) {
			field.rdoItem.setSelected(true);
		} else if (ItemLevel.ITEM == itemLevel) {
			field.rdoItem.setSelected(true);
		} else if (ItemLevel.SUBITEM == itemLevel) {
			field.rdoSubItem.setSelected(true);
		} else if (ItemLevel.DETAILITEM == itemLevel) {
			field.rdoDetailItem.setSelected(true);
		}
	}
}
