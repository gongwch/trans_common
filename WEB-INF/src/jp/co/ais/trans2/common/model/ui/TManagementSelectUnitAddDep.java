package jp.co.ais.trans2.common.model.ui;


/**
 * 管理選択：管理選択+範囲検索+任意選択：メイン （部門追加版）
 * 
 * @author AIS
 */
public class TManagementSelectUnitAddDep extends TManagementSelectUnit {

	/**
	 * コンストラクタ
	 */
	public TManagementSelectUnitAddDep() {
		super(false);
	}

	/**
	 * コントローラ生成
	 * 
	 * @return controller
	 */
	@Override
	public TManagementSelectUnitAddDepController createController() {
		return new TManagementSelectUnitAddDepController(this);
	}

}