package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 会社の任意選択コンポーネント
 * 
 * @author AIS
 */
public class TCompanyOptionalSelector extends TOptionalSelector {

	/** コントローラ */
	protected TCompanyOptionalSelectorController controller;

	/**
	 * コンストラクター
	 */
	public TCompanyOptionalSelector() {
		createController();
	}

	/**
	 * コントローラ作成
	 */
	protected void createController() {
		controller = new TCompanyOptionalSelectorController(this);
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public CompanySearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TCompanyOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * 選択された会社Entity一覧を返す
	 * 
	 * @return 選択された会社Entity一覧
	 */
	public List<Company> getEntities() {
		return controller.getEntities();
	}

	/**
	 * 部門Entity一覧を設定する
	 * 
	 * @param list 部門Entity一覧
	 */
	@SuppressWarnings("unused")
	public void setEntities(List<Company> list) {
		// TODO
	}

}
