package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * ��v����
 * 
 * @author AIS
 */
public enum AccountBook implements TEnumRadio {

	/** ���� */
	BOTH(0),

	/** �������� */
	OWN(1),

	/** IFRS���� */
	IFRS(2);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private AccountBook(int value) {
		this.value = value;
	}

	/**
	 * ���������\�����ǂ���
	 * 
	 * @return true:��������
	 */
	public boolean isOWN() {
		return isOWN(value);
	}

	/**
	 * IFRS�����\�����ǂ���
	 * 
	 * @return true:IFRS����
	 */
	public boolean isIFRS() {
		return isIFRS(value);
	}

	/**
	 * �^�C�v�擾
	 * 
	 * @param ak ���l
	 * @return �^�C�v
	 */
	public static AccountBook get(int ak) {
		for (AccountBook em : values()) {
			if (em.value == ak) {
				return em;
			}
		}
		return null;
	}

	/**
	 * �w��l�����������\�����ǂ���
	 * 
	 * @param value �l
	 * @return true:��������
	 */
	public static boolean isOWN(int value) {
		AccountBook book = get(value);

		return book.equals(BOTH) || book.equals(OWN);
	}

	/**
	 * �w��l��IFRS�����\�����ǂ���
	 * 
	 * @param value �l
	 * @return true:IFRS����
	 */
	public static boolean isIFRS(int value) {
		AccountBook book = get(value);

		return book.equals(BOTH) || book.equals(IFRS);
	}

	/**
	 * ���떼�̂�Ԃ�
	 * 
	 * @param accountBook ��v����
	 * @return �X�V�敪����
	 */
	public static String getAccountBookName(AccountBook accountBook) {

		switch (accountBook) {
			case OWN:
				return "C10982"; // ������v����
			case IFRS:
				return "C10983"; // IFRS��v����
			default:
				return null;
		}
	}

	/**
	 * �\�����̎擾
	 */
	@Override
	public String getName() {
		return getAccountBookName(this);
	}

}
