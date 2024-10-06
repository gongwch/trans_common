package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.message.*;

/**
 * �ۗ��敪
 * 
 * @author AIS
 */
public enum ReservationDivsion {

	/** �ۗ����Ȃ� */
	NORMAL(0),

	/** �ۗ����� */
	RESERVATION(1);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private ReservationDivsion(int value) {
		this.value = value;
	}

	/**
	 * �ۗ��敪��Ԃ�
	 * 
	 * @param type �ۗ��敪
	 * @return �ۗ��敪
	 */
	public static ReservationDivsion get(int type) {
		for (ReservationDivsion em : values()) {
			if (em.value == type) {
				return em;
			}
		}

		return null;
	}

	/**
	 * �ۗ��敪����Ԃ�
	 * 
	 * @return �ۗ��敪��
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * �ۗ��敪����Ԃ�
	 * 
	 * @param type �ۗ��敪
	 * @return �ۗ��敪��
	 */
	public static String getName(ReservationDivsion type) {

		switch (type) {
			case NORMAL: // �ۗ����Ȃ�
				return "C01981";

			case RESERVATION: // �ۗ�����
				return "C01982";

			default:
				return null;
		}
	}

	/**
	 * �ۗ��敪��Enum���̎擾<br>
	 * �R�[�h�F���́A�R�[�h�F���̌`��
	 * 
	 * @param lang ����R�[�h
	 * @return �ۗ��敪��Enum����
	 */
	public static String getCodeAndNames(String lang) {

		StringBuilder sb = new StringBuilder();

		for (ReservationDivsion type : ReservationDivsion.values()) {
			sb.append(type.value);
			sb.append("�F");
			sb.append(MessageUtil.getWord(lang, type.getName()));
			sb.append("�A");
		}

		return sb.substring(0, sb.length() - 1);

	}
}
