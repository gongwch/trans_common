package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.message.*;

/**
 * �t�֋敪
 * 
 * @author AIS
 */
public enum TransferDivision {

	/** �ʏ� */
	NORMAL(0),

	/** �t�� */
	TRANSFER(1);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private TransferDivision(int value) {
		this.value = value;
	}

	/**
	 * �t�֋敪��Ԃ�
	 * 
	 * @param type �t�֋敪
	 * @return �t�֋敪
	 */
	public static TransferDivision get(int type) {
		for (TransferDivision em : values()) {
			if (em.value == type) {
				return em;
			}
		}

		return null;
	}

	/**
	 * �t�֋敪����Ԃ�
	 * 
	 * @return �t�֋敪��
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * �t�֋敪����Ԃ�
	 * 
	 * @param type �t�֋敪
	 * @return �t�֋敪��
	 */
	public static String getName(TransferDivision type) {

		switch (type) {
			case NORMAL: // �ʏ�
				return "C00372";

			case TRANSFER: // �t��
				return "C00375";

			default:
				return null;
		}
	}

	/**
	 * �t�֋敪��Enum���̎擾<br>
	 * �R�[�h�F���́A�R�[�h�F���̌`��
	 * 
	 * @param lang ����R�[�h
	 * @return �t�֋敪��Enum����
	 */
	public static String getCodeAndNames(String lang) {

		StringBuilder sb = new StringBuilder();

		for (TransferDivision type : TransferDivision.values()) {
			sb.append(type.value);
			sb.append("�F");
			sb.append(MessageUtil.getWord(lang, type.getName()));
			sb.append("�A");
		}

		return sb.substring(0, sb.length() - 1);

	}

}
