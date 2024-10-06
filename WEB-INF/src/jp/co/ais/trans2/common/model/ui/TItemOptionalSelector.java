package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 科目の任意選択コンポーネント
 * 
 * @author AIS
 */
public class TItemOptionalSelector extends TOptionalSelector {

	/** コントローラ */
	protected TItemOptionalSelectorController controller;

	/**
	 * コンストラクタ
	 */
	public TItemOptionalSelector() {
		controller = createController();
	}

	/**
	 * コントローラーの取得
	 * 
	 * @return コントローラー
	 */
	public TItemOptionalSelectorController createController() {
		return new TItemOptionalSelectorController(this);
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public ItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TItemOptionalSelectorController getController() {
		return controller;
	}

}
