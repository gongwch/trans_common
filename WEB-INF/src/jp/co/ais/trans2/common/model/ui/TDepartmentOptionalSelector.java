package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.department.*;

/**
 * 部門の任意選択コンポーネント
 * 
 * @author AIS
 */
public class TDepartmentOptionalSelector extends TOptionalSelector {

	/** コントローラ */
	protected TDepartmentOptionalSelectorController controller;

	/** true:全SPCモード */
	protected boolean allCompanyMode = false;

	/**
	 * コンストラクター
	 */
	public TDepartmentOptionalSelector() {
		createController();
	}

	/**
	 * コントローラ作成
	 */
	protected void createController() {
		controller = new TDepartmentOptionalSelectorController(this);
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public DepartmentSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TDepartmentOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * 選択された部門Entity一覧を返す
	 * 
	 * @return 選択された部門Entity一覧
	 */
	public List<Department> getDepartmentEntities() {
		return controller.getDeptratmentEntities();
	}

	/**
	 * 部門Entity一覧を設定する
	 * 
	 * @param list 部門Entity一覧
	 */
	public void setDepartmentEntities(List<Department> list) {
		controller.setDeptratmentEntities(list);
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
