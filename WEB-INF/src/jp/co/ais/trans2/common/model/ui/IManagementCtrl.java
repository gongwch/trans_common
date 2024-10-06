package jp.co.ais.trans2.common.model.ui;

/**
 * �Ǘ����ڒP�ʂ�����v���O����IF
 */
public interface IManagementCtrl {

	/**
	 * @return �Ǘ����ڃL�[
	 */
	public String getManagementSaveKey();

	/**
	 * �Ǘ����ڒP�ʏ����ێ��̐ݒ�ɂ�蕜��
	 */
	public void restoreManagementSetting();

	/**
	 * �Ǘ����ڒP�ʏ��� �ݒ�ێ�
	 */
	public void saveManagementSetting();

	/**
	 * @return �o�͒P�ʃL�[
	 */
	public String getDepartmentSaveKey();

	/**
	 * �o�͒P�ʏ����ێ��̐ݒ�ɂ�蕜��
	 */
	public void restoreDepartmentSetting();

	/**
	 * �o�͒P�ʒP�ʏ��� �ݒ�ێ�
	 */
	public void saveDepartmentSetting();
}
