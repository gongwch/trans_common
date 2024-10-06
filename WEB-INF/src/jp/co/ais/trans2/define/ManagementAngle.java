package jp.co.ais.trans2.define;

import jp.co.ais.trans2.model.company.Company;

/**
 * 管理として使用できる切り口
 * @author AIS
 *
 */
public enum ManagementAngle {
	/**
	 * 条件なし
	 */
	NONE(0),

	/**
	 * 取引先
	 */
	CUSTOMER(1),

	/**
	 * 社員
	 */
	EMPLOYEE(2),

	/**
	 * 管理1
	 */
	MANAGEMENT1(3),

	/**
	 * 管理2
	 */
	MANAGEMENT2(4),

	/**
	 * 管理3
	 */
	MANAGEMENT3(5),

	/**
	 * 管理4
	 */
	MANAGEMENT4(6),

	/**
	 * 管理5
	 */
	MANAGEMENT5(7),

	/**
	 * 管理6
	 */
	MANAGEMENT6(8),

	/**
	 * 集計取引先
	 */
	SUMCUSTOMER(9),

	/**
	 * 部門
	 */
	DEPARTMENT(10),

	/**
	 * 科目
	 */
	ITEM(11);

	@SuppressWarnings("unused")
	private int value;

	private ManagementAngle(int value) {
		this.value = value;
	}

	/**
	 * 選択された切り口の名称を返す
	 * @param angle
	 * @param company
	 * @return
	 */
	public static String getName(ManagementAngle angle, Company company) {

		switch (angle) {

			case NONE:
				return "C01748";//条件なし

			case CUSTOMER:
				return "C00408";//取引先

			case EMPLOYEE:
				return "C00246";//社員

			case MANAGEMENT1:
				return company.getAccountConfig().getManagement1Name();

			case MANAGEMENT2:
				return company.getAccountConfig().getManagement2Name();

			case MANAGEMENT3:
				return company.getAccountConfig().getManagement3Name();

			case MANAGEMENT4:
				return company.getAccountConfig().getManagement4Name();

			case MANAGEMENT5:
				return company.getAccountConfig().getManagement5Name();

			case MANAGEMENT6:
				return company.getAccountConfig().getManagement6Name();

			case SUMCUSTOMER:
				return "C01991";//取引先(集計)

			case DEPARTMENT:
				return "C00467";//部門

			case ITEM:
				return company.getAccountConfig().getItemName();

			default:
				return null;
		}
	}

}
