package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.message.*;

/**
 * �����d��敪
 * 
 * @author AIS
 */
public enum AutoJournalDivision {

	/** �ʏ� */
	NORMAL(0),

	/** ���� */
	AUTO(1);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private AutoJournalDivision(int value) {
		this.value = value;
	}

	/**
	 * �����d��敪��Ԃ�
	 * 
	 * @param type �����d��敪
	 * @return �����d��敪
	 */
	public static AutoJournalDivision get(int type) {
		for (AutoJournalDivision em : values()) {
			if (em.value == type) {
				return em;
			}
		}

		return null;
	}

	/**
	 * �����d��敪����Ԃ�
	 * 
	 * @return �����d��敪��
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * �����d��敪����Ԃ�
	 * 
	 * @param type �����d��敪
	 * @return �����d��敪��
	 */
	public static String getName(AutoJournalDivision type) {

		switch (type) {
			case NORMAL: // �ʏ�
				return "C00372";

			case AUTO: // ����
				return "C01107";

			default:
				return null;
		}
	}

	/**
	 * �����d��敪��Enum���̎擾<br>
	 * �R�[�h�F���́A�R�[�h�F���̌`��
	 * 
	 * @param lang ����R�[�h
	 * @return �����d��敪��Enum����
	 */
	public static String getCodeAndNames(String lang) {

		StringBuilder sb = new StringBuilder();

		for (AutoJournalDivision type : AutoJournalDivision.values()) {
			sb.append(type.value);
			sb.append("�F");
			sb.append(MessageUtil.getWord(lang, type.getName()));
			sb.append("�A");
		}

		return sb.substring(0, sb.length() - 1);

	}

}
