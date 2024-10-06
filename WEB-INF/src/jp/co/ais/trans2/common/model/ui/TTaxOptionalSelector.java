package jp.co.ais.trans2.common.model.ui;

import java.util.List;
import jp.co.ais.trans2.common.gui.TOptionalSelector;
import jp.co.ais.trans2.model.tax.ConsumptionTax;
import jp.co.ais.trans2.model.tax.ConsumptionTaxSearchCondition;

/**
 * 消費税の任意選択コンポーネント
 * @author AIS
 *
 */
public class TTaxOptionalSelector extends TOptionalSelector {

	/** コントローラ */
	protected TTaxOptionalSelectorController controller;

	/**
	 *
	 *
	 */
	public TTaxOptionalSelector() {
		controller = new TTaxOptionalSelectorController(this);
	}

	/**
	 * 検索条件を返す
	 * @return 検索条件
	 */
	public ConsumptionTaxSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TTaxOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * 消費税一覧にEntityをセットする
	 *
	 */
	public void setEntities(List<ConsumptionTax> consumptionTax) {
		controller.setEntities(consumptionTax);
	}

	/**
	 * 選択された消費税Entity一覧を返す
	 * @return 選択された消費税Entity一覧
	 */
	public List<ConsumptionTax> getEntities() {
		return controller.getEntities();
	}

}
