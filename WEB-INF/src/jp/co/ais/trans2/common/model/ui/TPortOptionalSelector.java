package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.port.*;

/**
 * 港の任意選択コンポーネント
 * 
 * @author AIS
 */
public class TPortOptionalSelector extends TOptionalSelector {

	/** コントローラ */
	protected TPortOptionalSelectorController controller;

	/**
	 * コンストラクター
	 */
	public TPortOptionalSelector() {
		createController();
	}

	/**
	 * コントローラ作成
	 */
	protected void createController() {
		controller = new TPortOptionalSelectorController(this);
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public PortSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TPortOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * 選択された船Entity一覧を返す
	 * 
	 * @return 選択された船Entity一覧
	 */
	public List<Port> getEntities() {
		return controller.getEntities();
	}

}
