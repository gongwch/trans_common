package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 銀行口座の任意選択コンポーネント
 * 
 * @author AIS
 */
public class TBankAccountOptionalSelector extends TOptionalSelector {

	/** コントローラ */
	protected TBankAccountOptionalSelectorController controller;

	/** true:全SPCモード */
	protected boolean allCompanyMode = false;

	/**
	 * 
	 */
	public TBankAccountOptionalSelector() {
		controller = createController();
	}

	/**
	 * @return TBankAccountOptionalSelectorController
	 */
	protected TBankAccountOptionalSelectorController createController() {
		return new TBankAccountOptionalSelectorController(this);
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public BankAccountSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	@Override
	public TBankAccountOptionalSelectorController getController() {
		return controller;
	}

	/**
	 * 選択された銀行口座Entity一覧を返す
	 * 
	 * @return 選択された銀行口座Entity一覧
	 */
	public List<BankAccount> getEntities() {
		return controller.getEntities();
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
