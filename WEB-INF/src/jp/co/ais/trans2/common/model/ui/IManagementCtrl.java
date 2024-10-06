package jp.co.ais.trans2.common.model.ui;

/**
 * 管理項目単位があるプログラムIF
 */
public interface IManagementCtrl {

	/**
	 * @return 管理項目キー
	 */
	public String getManagementSaveKey();

	/**
	 * 管理項目単位条件保持の設定により復旧
	 */
	public void restoreManagementSetting();

	/**
	 * 管理項目単位条件 設定保持
	 */
	public void saveManagementSetting();

	/**
	 * @return 出力単位キー
	 */
	public String getDepartmentSaveKey();

	/**
	 * 出力単位条件保持の設定により復旧
	 */
	public void restoreDepartmentSetting();

	/**
	 * 出力単位単位条件 設定保持
	 */
	public void saveDepartmentSetting();
}
