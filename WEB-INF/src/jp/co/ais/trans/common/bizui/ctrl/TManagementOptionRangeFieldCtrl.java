package jp.co.ais.trans.common.bizui.ctrl;

import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.info.*;

/**
 * 管理コンポーネントコントロール
 */
public class TManagementOptionRangeFieldCtrl extends TAppletClientBase {

	/** フィールド */
	protected TManagementOptionRangeField managementField;

	/**
	 * コンストラクタ
	 * 
	 * @param managementField フィールド
	 */
	public TManagementOptionRangeFieldCtrl(TManagementOptionRangeField managementField) {
		this.managementField = managementField;
		// 管理DROPを作成する
		createManagementDrop();
	}

	/**
	 * 管理名を作成する
	 */
	protected void createManagementDrop() {
		// 会社情報取得
		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();

		// 管理区分表示
		boolean[] mngViews = compInfo.isUseManageDivs();
		String[] mngNames = compInfo.getManageDivNames();

		Map<Object, String> map = new TreeMap<Object, String>();

		// 条件なし
		map.put(0, String.valueOf(getWord("C01748")));
		// 取引先
		map.put(1, String.valueOf(getWord("C00408")));
		// 社員
		map.put(2, String.valueOf(getWord("C00246")));
		// 管理1
		if (mngViews[0]) {
			map.put(3, mngNames[0]);
		}
		// 管理2
		if (mngViews[1]) {
			map.put(4, mngNames[1]);
		}
		// 管理3
		if (mngViews[2]) {
			map.put(5, mngNames[2]);
		}
		// 管理4
		if (mngViews[3]) {
			map.put(6, mngNames[3]);
		}
		// 管理5
		if (mngViews[4]) {
			map.put(7, mngNames[4]);
		}
		// 管理6
		if (mngViews[5]) {
			map.put(8, mngNames[5]);
		}

		managementField.getCmbManagement().setModel(map);

		// 条件なしを選択する
		managementField.getCmbManagement().setSelectedIndex(0);
	}
}
