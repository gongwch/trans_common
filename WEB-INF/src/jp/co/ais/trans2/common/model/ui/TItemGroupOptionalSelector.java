package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �Ȗ�+�⏕�Ȗ�+����Ȗڂ̔C�ӑI���R���|�[�l���g
 * 
 * @author AIS
 */
public class TItemGroupOptionalSelector extends TOptionalSelector {

	/** �R���g���[�� */
	protected TItemGroupOptionalSelectorController controller;

	/**
	 * �R���X�g���N�^.
	 */
	public TItemGroupOptionalSelector() {
		controller = createController();
	}

	/**
	 * �R���g���[���𐶐�����
	 * 
	 * @return �R���g���[��
	 */
	protected TItemGroupOptionalSelectorController createController() {
		return new TItemGroupOptionalSelectorController(this);
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public ItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �⏕�Ȗڌ���������Ԃ�
	 * 
	 * @return ��������
	 */
	public SubItemSearchCondition getSubItemSearchCondition() {
		return controller.getSubItemCondition();
	}

	/**
	 * ����Ȗڌ���������Ԃ�
	 * 
	 * @return ��������
	 */
	public DetailItemSearchCondition getDetailItemSearchCondition() {
		return controller.getDetailItemCondition();
	}

	@Override
	public TItemGroupOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * �I�����ꂽ�Ȗ�Entity�ꗗ��Ԃ�
	 * 
	 * @return �I�����ꂽ�Ȗ�Entity�ꗗ
	 */
	public List<Item> getEntities() {
		return controller.getEntities();
	}

	/**
	 * �Ȗڈꗗ��Entity���Z�b�g����
	 * @param item 
	 */
	public void setEntities(List<Item> item) {
		controller.setEntities(item);
	}

	/**
	 * �⏕�A����ʂɏo�͂����ۂɁA�⏕�A���󂪖������̂ł��o�͂��邩��ݒ肷��B
	 * 
	 * @param bln
	 */
	public void setGetAllItems(boolean bln) {
		controller.setGetAllItems(bln);
	}

	/**
	 * �Ȗڃ��x�����Œ肷��B
	 * 
	 * @param itemLevel �Ȗڃ��x��
	 */
	public void setItemLevel(ItemLevel itemLevel) {
		controller.setItemLevel(itemLevel);
	}

	/**
	 * �Ȗڃ��x�����ő�ŌŒ肷��B<br>
	 * ����Ȗڂ��g�p����ꍇ�͓���ȖځA�g�p���Ȃ��ꍇ�͕⏕�ȖڌŒ�B
	 * 
	 * @param ac ��v�ݒ�
	 */
	public void setItemLevelMax(AccountConfig ac) {
		controller.setItemLevelMax(ac);
	}

}
