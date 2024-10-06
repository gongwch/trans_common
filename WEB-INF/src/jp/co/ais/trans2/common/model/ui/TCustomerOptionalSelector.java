package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.customer.*;

/**
 * 取引先：任意選択：メイン
 * 
 * @author AIS
 */
public class TCustomerOptionalSelector extends TOptionalSelector {

	/** コントローラ */
	protected TCustomerOptionalSelectorController controller;

	/** true:全SPCモード */
	protected boolean allCompanyMode = false;

	/**
	 * コンストラクタ
	 */
	public TCustomerOptionalSelector() {
		controller = createController();
	}

	/**
	 * コントローラ生成
	 * 
	 * @return controller
	 */
	public TCustomerOptionalSelectorController createController() {
		return new TCustomerOptionalSelectorController(this);
	}

	/**
	 * 検索条件取得
	 * 
	 * @return 検索条件
	 */
	public CustomerSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * コントローラ取得
	 * 
	 * @return controller
	 */
	@Override
	public TCustomerOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * エンティティ取得
	 * 
	 * @return エンティティリスト
	 */
	public List<Customer> getEntities() {
		return controller.getEntities();
	}

	/**
	 * コードリストセット
	 * 
	 * @param codeList
	 */
	@Override
	public void setCodeList(List<String> codeList) {
		controller.setCodeList(codeList);
	}

	/**
	 * true:全SPCモードの取得
	 * 
	 * @return allCompanyMode true:全SPCモード
	 */
	public boolean isAllCompanyMode() {
		return allCompanyMode;
	}

	/**
	 * true:全SPCモードの設定
	 * 
	 * @param allCompanyMode true:全SPCモード
	 */
	public void setAllCompanyMode(boolean allCompanyMode) {
		this.allCompanyMode = allCompanyMode;

		if (allCompanyMode) {
			getSearchCondition().setCompanyCode(null);
		} else {
			getSearchCondition().setCompanyCode(TLoginInfo.getCompany().getCode());
		}
	}

}