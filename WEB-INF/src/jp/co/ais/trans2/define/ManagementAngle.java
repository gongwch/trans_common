package jp.co.ais.trans2.define;

import jp.co.ais.trans2.model.company.Company;

/**
 * �Ǘ��Ƃ��Ďg�p�ł���؂��
 * @author AIS
 *
 */
public enum ManagementAngle {
	/**
	 * �����Ȃ�
	 */
	NONE(0),

	/**
	 * �����
	 */
	CUSTOMER(1),

	/**
	 * �Ј�
	 */
	EMPLOYEE(2),

	/**
	 * �Ǘ�1
	 */
	MANAGEMENT1(3),

	/**
	 * �Ǘ�2
	 */
	MANAGEMENT2(4),

	/**
	 * �Ǘ�3
	 */
	MANAGEMENT3(5),

	/**
	 * �Ǘ�4
	 */
	MANAGEMENT4(6),

	/**
	 * �Ǘ�5
	 */
	MANAGEMENT5(7),

	/**
	 * �Ǘ�6
	 */
	MANAGEMENT6(8),

	/**
	 * �W�v�����
	 */
	SUMCUSTOMER(9),

	/**
	 * ����
	 */
	DEPARTMENT(10),

	/**
	 * �Ȗ�
	 */
	ITEM(11);

	@SuppressWarnings("unused")
	private int value;

	private ManagementAngle(int value) {
		this.value = value;
	}

	/**
	 * �I�����ꂽ�؂���̖��̂�Ԃ�
	 * @param angle
	 * @param company
	 * @return
	 */
	public static String getName(ManagementAngle angle, Company company) {

		switch (angle) {

			case NONE:
				return "C01748";//�����Ȃ�

			case CUSTOMER:
				return "C00408";//�����

			case EMPLOYEE:
				return "C00246";//�Ј�

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
				return "C01991";//�����(�W�v)

			case DEPARTMENT:
				return "C00467";//����

			case ITEM:
				return company.getAccountConfig().getItemName();

			default:
				return null;
		}
	}

}
