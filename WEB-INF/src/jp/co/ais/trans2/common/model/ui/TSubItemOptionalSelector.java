package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 補助科目の任意選択コンポーネント
 * 
 * @author AIS
 */
public class TSubItemOptionalSelector extends TOptionalSelector {

	/** serialVersionUID */
	private static final long serialVersionUID = 6720856141179813605L;

	/** コントローラ */
	protected TSubItemOptionalSelectorController controller;

	/**
	 * 
	 *
	 */
	public TSubItemOptionalSelector() {
		createController();
	}

	/**
	 * コントローラーの取得
	 */
	public void createController() {
		controller = new TSubItemOptionalSelectorController(this);
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public SubItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TSubItemOptionalSelectorController getController() {
		return controller;
	}

}
