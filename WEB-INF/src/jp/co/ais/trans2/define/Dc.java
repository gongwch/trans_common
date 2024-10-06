package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.message.*;

/**
 * �ݎ�
 * 
 * @author AIS
 */
public enum Dc {

	/** �ؕ� */
	DEBIT(0),

	/** �ݕ� */
	CREDIT(1);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private Dc(int value) {
		this.value = value;
	}

	/**
	 * �ݎ؂�Ԃ�
	 * 
	 * @param dc �l
	 * @return �ݎ�
	 */
	public static Dc getDc(int dc) {
		for (Dc em : values()) {
			if (em.value == dc) {
				return em;
			}
		}

		return null;
	}
	
	/**
	 * �ؕ��ł��邩
	 * 
	 * @return �ؕ�:true �ݕ�:false
	 */
	public boolean isDebit() {
		return DEBIT == this;
	}
	
	/**
	 * �ݕ��ł��邩
	 * 
	 * @return �ؕ�:false �ݕ�:true
	 */
	public boolean isCredit() {
		return CREDIT == this;
	}

	/**
	 * �e�L�X�g
	 * 
	 * @return �ݎ؃e�L�X�g
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * �e�L�X�g
	 * 
	 * @param dc �^�C�v
	 * @return �ݎ؃e�L�X�g
	 */
	public static String getName(Dc dc) {

		switch (dc) {
			case DEBIT:
				return "C00080";// �ؕ�

			default:
				return "C00068";// �ݕ�
		}
	}

	/**
	 * �ݎ؋敪��Enum���̎擾<br>
	 * �R�[�h�F���́A�R�[�h�F���̌`��
	 * 
	 * @param lang ����R�[�h
	 * @return �ݎ؋敪��Enum����
	 */
	public static String getCodeAndNames(String lang) {

		StringBuilder sb = new StringBuilder();

		for (Dc dc : Dc.values()) {
			sb.append(dc.value);
			sb.append("�F");
			sb.append(MessageUtil.getWord(lang, dc.getName()));
			sb.append("�A");
		}

		return sb.substring(0, sb.length() - 1);

	}
}
