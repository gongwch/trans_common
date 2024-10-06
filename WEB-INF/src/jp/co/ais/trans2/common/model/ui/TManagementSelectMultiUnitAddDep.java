package jp.co.ais.trans2.common.model.ui;

import java.util.*;

/**
 * 管理選択：管理選択+範囲検索+任意選択 * 3：メイン
 * 
 * @author AIS
 */
public class TManagementSelectMultiUnitAddDep extends TManagementSelectMultiUnit {

	/** 管理選択フィールド */
	public List<TManagementSelectUnitAddDep> ctrlManagementSelectUnits;

	/**
	 * コンストラクタ
	 */
	public TManagementSelectMultiUnitAddDep() {
		super(FIELD_COUNT);
	}

	/**
	 * コントローラ生成
	 * 
	 * @return controller
	 */
	@Override
	protected TManagementSelectMultiUnitAddDepController createController() {
		return new TManagementSelectMultiUnitAddDepController(this);
	}

	/**
	 * コンポーネント初期化
	 * 
	 * @param fieldCount
	 */
	@Override
	public void initComponents(int fieldCount) {
		ctrlManagementSelectUnits = new ArrayList<TManagementSelectUnitAddDep>();
		for (int i = 0; i < fieldCount; i++) {
			ctrlManagementSelectUnits.add(new TManagementSelectUnitAddDep());
		}
	}

	/**
	 * コンポーネント配置
	 */
	@Override
	public void allocateComponents() {

		setLangMessageID("C10834"); // 管理選択

		int y = 5;
		for (TManagementSelectUnitAddDep unit : ctrlManagementSelectUnits) {
			unit.setLocation(15, y);
			add(unit);
			y = y + unit.getHeight() + 5;
		}

		setSize(360, y + 25);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	@Override
	public void setTabControlNo(int tabControlNo) {
		for (TManagementSelectUnitAddDep unit : ctrlManagementSelectUnits) {
			unit.setTabControlNo(tabControlNo);
		}
	}

}