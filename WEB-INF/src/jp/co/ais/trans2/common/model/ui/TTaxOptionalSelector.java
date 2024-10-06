package jp.co.ais.trans2.common.model.ui;

import java.util.List;
import jp.co.ais.trans2.common.gui.TOptionalSelector;
import jp.co.ais.trans2.model.tax.ConsumptionTax;
import jp.co.ais.trans2.model.tax.ConsumptionTaxSearchCondition;

/**
 * ����ł̔C�ӑI���R���|�[�l���g
 * @author AIS
 *
 */
public class TTaxOptionalSelector extends TOptionalSelector {

	/** �R���g���[�� */
	protected TTaxOptionalSelectorController controller;

	/**
	 *
	 *
	 */
	public TTaxOptionalSelector() {
		controller = new TTaxOptionalSelectorController(this);
	}

	/**
	 * ����������Ԃ�
	 * @return ��������
	 */
	public ConsumptionTaxSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TTaxOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * ����ňꗗ��Entity���Z�b�g����
	 *
	 */
	public void setEntities(List<ConsumptionTax> consumptionTax) {
		controller.setEntities(consumptionTax);
	}

	/**
	 * �I�����ꂽ�����Entity�ꗗ��Ԃ�
	 * @return �I�����ꂽ�����Entity�ꗗ
	 */
	public List<ConsumptionTax> getEntities() {
		return controller.getEntities();
	}

}
