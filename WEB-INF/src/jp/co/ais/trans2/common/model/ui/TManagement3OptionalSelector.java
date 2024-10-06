package jp.co.ais.trans2.common.model.ui;

import java.util.List;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.management.Management3;
import jp.co.ais.trans2.model.management.Management3SearchCondition;

/**
 * 管理3の任意選択コンポーネント
 * 
 * @author AIS
 */
public class TManagement3OptionalSelector extends TOptionalSelector {

	/** コントローラ */
	protected TManagement3OptionalSelectorController controller;

	/** true:全SPCモード */
	protected boolean allCompanyMode = false;

	/**
	 * コンストラクター
	 */
	public TManagement3OptionalSelector() {
		controller = createController();
	}

	/**
	 * @return コントローラー
	 */
	protected TManagement3OptionalSelectorController createController() {
		return new TManagement3OptionalSelectorController(this);
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public Management3SearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TManagement3OptionalSelectorController getController() {
		return controller;
	}

	/**
	 * 選択された管理3Entity一覧を返す
	 * 
	 * @return 選択された管理3Entity一覧
	 */
	public List<Management3> getEntities() {
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
