package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.message.*;

/**
 * �ŋ敪
 * 
 * @author AIS
 */
public enum TaxCalcType {

	/** �O�� */
	OUT(0),

	/** ���� */
	IN(1),

	/** ��ې� */
	NONE(2);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private TaxCalcType(int value) {
		this.value = value;
	}

	/**
	 * TaxCalcType��Ԃ�
	 * 
	 * @param type TaxCalcType
	 * @return TaxCalcType
	 */
	public static TaxCalcType get(int type) {
		for (TaxCalcType em : values()) {
			if (em.value == type) {
				return em;
			}
		}

		return null;
	}

	/**
	 * TaxCalcType����Ԃ�
	 * 
	 * @return TaxCalcType��
	 */
	public String getName() {
		return getTaxCalcTypeName(this);
	}

	/**
	 * TaxCalcType����Ԃ�
	 * 
	 * @param type TaxCalcType
	 * @return TaxCalcType��
	 */
	public static String getTaxCalcTypeName(TaxCalcType type) {
		if (type == null) {
			return null;
		}
		
		switch (type) {
			case OUT: // �O��
				return "C00337";

			case IN: // ����
				return "C00023";

			case NONE: // ��ې�
				return "C01971";

			default:
				return null;
		}
	}

	/**
	 * ���ŁE�O�ł�enum
	 * 
	 * @param taxCulKbnRole
	 * @return ���ŁE�O��
	 */
	public static TaxCalcType getTaxCulKbn(boolean taxCulKbnRole) {

		if (taxCulKbnRole == false) {
			return OUT;
		} else {
			return IN;
		}
	}

	/**
	 * �ŋ敪��Enum���̎擾<br>
	 * �R�[�h�F���́A�R�[�h�F���̌`��
	 * 
	 * @param lang ����R�[�h
	 * @return �ŋ敪��Enum����
	 */
	public static String getCodeAndNames(String lang) {

		StringBuilder sb = new StringBuilder();

		for (TaxCalcType type : TaxCalcType.values()) {
			sb.append(type.value);
			sb.append("�F");
			sb.append(MessageUtil.getWord(lang, type.getName()));
			sb.append("�A");
		}

		return sb.substring(0, sb.length() - 1);

	}

	/**
	 * �ŋ敪���̂���R�[�h��Ԃ�<BR>
	 * �R�[�h����������Ȃ��A�܂��͖��̂�{@code null}�̏ꍇ-1��Ԃ�
	 * 
	 * @param name �ŋ敪����
	 * @param lang ����R�[�h
	 * @return �ŋ敪�R�[�h
	 */
	public static int getCode4Name(String name, String lang) {

		int result = -1;

		if (name == null) {
			return result;
		}

		if (name.equals(MessageUtil.getWord(lang, "C00337"))) {
			result = OUT.value;
		} else if (name.equals(MessageUtil.getWord(lang, "C00023"))) {
			result = IN.value;
		} else if (name.equals(MessageUtil.getWord(lang, "C01971"))) {
			result = NONE.value;
		}

		return result;
	}
}
