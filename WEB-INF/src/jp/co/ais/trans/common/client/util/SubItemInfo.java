package jp.co.ais.trans.common.client.util;

/**
 * �⏕�Ȗڏ��
 */
public class SubItemInfo extends ItemInfo {

	/** �⏕�敪 0:�Ȃ� 1:���� */
	private static final String SUB_DIVISION = "1";

	/**
	 * �R���X�g���N�^
	 */
	protected SubItemInfo() {
		nameCode = "HKM_NAME";
		shortNameCode = "HKM_NAME_S";
	}

	/**
	 * ����Ȗڂ����݂��邩�ǂ���
	 * 
	 * @return ���݂���ꍇtrue
	 */
	public boolean isExistBreakDownItem() {
		return SUB_DIVISION.equals(data.get("HKM_KBN"));
	}

}
