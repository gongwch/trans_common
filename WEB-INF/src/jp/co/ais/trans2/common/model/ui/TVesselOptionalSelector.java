package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * 船の任意選択コンポーネント
 * 
 * @author AIS
 */
public class TVesselOptionalSelector extends TOptionalSelector {

	/** コントローラ */
	protected TVesselOptionalSelectorController controller;

	/**
	 * コンストラクター
	 */
	public TVesselOptionalSelector() {
		createController();
	}

	/**
	 * コントローラ作成
	 */
	protected void createController() {
		controller = new TVesselOptionalSelectorController(this);
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public VesselSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TVesselOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * 選択された船Entity一覧を返す
	 * 
	 * @return 選択された船Entity一覧
	 */
	public List<Vessel> getEntities() {
		return controller.getEntities();
	}

}
