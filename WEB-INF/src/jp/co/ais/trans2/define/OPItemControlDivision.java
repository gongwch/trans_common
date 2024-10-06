package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * OP Item Control Division
 */
public enum OPItemControlDivision implements TEnumRadio {

	/** FRT : FREIGHT **/
	FREIGHT("FRT"),

	/** BEX : BUNKER EXPENSE **/
	BUNKER_EXPENSE("BEX"),

	/** BSK : BUNKER STOCK **/
	BUNKER_STOCK("BSK"),

	/** BSE : BUNKER STOCK ESTIMATE **/
	BUNKER_STOCK_ESTIMATE("BSE"),

	/** BSR : BUNKER STOCK TI-BOR/TO-BOR **/
	BUNKER_STOCK_BOR("BSR"),

	/** BSP : BUNKER SUPPLY **/
	BUNKER_SUPPLY("BSP"),

	/** BSP : BUNKER SUPPLY(�b�D�җ���) */
	BUNKER_SUPPLY_CH("BSP-CH"),

	/** BSP : BUNKER SUPPLY(�D�嗧��) */
	BUNKER_SUPPLY_OW("BSP-OW"),

	/** BKR : BUNKER **/
	BUNKER("BKR"),

	/** OH1 : SOBC���쐬�AOH���쐬�̏ꍇ��������ITEM�p **/
	OH_1_NO_SOBC("OH1"),

	/** OH2 : SOBC�쐬�ς݁AOH���쐬�̏ꍇ��������ITEM�p **/
	OH_2_DONE_SOBC("OH2"),

	/** ADV : ADVANCE **/
	ADVANCE("ADV"),

	/** PCG : PORT CHARGE **/
	PORT_CHARGE("PCG"),

	/** BRO : BROKERAGE **/
	BROKERAGE("BRO"),

	/** OEX : OTHER EXPENSE **/
	OTHER_EXPENSE("OEX"),

	/** HIR : HIRE **/
	HIRE("HIR"),

	/** OPF : �^�q�ϑ��� */
	OPERATION_FEE("OPF");

	/** �l */
	public String value;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param value
	 */
	OPItemControlDivision(String value) {
		this.value = value;
	}

	/**
	 * ���̎擾
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * ���̂�Ԃ�
	 * 
	 * @param type
	 * @return ����
	 */
	public static String getName(OPItemControlDivision type) {
		if (type == null) {
			return "";
		}

		return type.value;
	}

	/**
	 * Enum��Ԃ�
	 * 
	 * @param value
	 * @return Enum
	 */
	public static OPItemControlDivision get(String value) {
		for (OPItemControlDivision em : values()) {
			if (em.value.equals(value)) {
				return em;
			}
		}

		return null;
	}

}
