package jp.co.ais.trans2.common.model.format;

import jp.co.ais.trans.common.message.*;

/**
 * ���Z�i�K�̃t�H�[�}�b�^
 * 
 * @author AIS
 */
public class SettlementLevelFormat {

	/**
	 * ���Z�i�K���t�H�[�}�b�g����
	 * 
	 * @param settlementLevel ���x��
	 * @param lang ����R�[�h
	 * @return �t�H�[�}�b�g�㕶��
	 */
	public String format(int settlementLevel, String lang) {
		if (settlementLevel == 0) {
			return MessageUtil.getShortWord(lang, "C00372"); // �ʏ�
		}

		return Integer.toString(settlementLevel) + MessageUtil.getShortWord(lang, "C00373"); // ��
	}

	/**
	 * ���Z�i�K�̏o��<br>
	 * �ʏ�̏ꍇ�̓u�����N<br>
	 * �ȊO�Fn�����Z
	 * 
	 * @param settlementLevel ���x��
	 * @param lang ����R�[�h
	 * @return ���Z�i�K
	 */
	public String formatSettlement(int settlementLevel, String lang) {
		if (settlementLevel == 0) {
			return ""; // �ʏ�̏ꍇ�̓u�����N
		}

		// n�����Z
		return format(settlementLevel, lang) + MessageUtil.getShortWord(lang, "C00142");
	}

}
