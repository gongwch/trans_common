package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.employee.*;

/**
 * 社員の任意選択コンポーネント
 * 
 * @author AIS
 */
public class TEmployeeOptionalSelector extends TOptionalSelector {

	/** コントローラ */
	protected TEmployeeOptionalSelectorController controller;

	/** true:全SPCモード */
	protected boolean allCompanyMode = false;

	/**
	 * コンストラクター
	 */
	public TEmployeeOptionalSelector() {
		controller = createController();
	}

	/**
	 * @return コントローラー
	 */
	protected TEmployeeOptionalSelectorController createController() {
		return new TEmployeeOptionalSelectorController(this);
	}

	@Override
	public TEmployeeOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public EmployeeSearchCondition getSearchCondition() {
		return controller.getCondition();
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
