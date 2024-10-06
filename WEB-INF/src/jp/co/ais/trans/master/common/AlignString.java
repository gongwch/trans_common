package jp.co.ais.trans.master.common;

/**
 * ������Ɣz�u��ێ�����N���X
 */
public class AlignString {

	/** ���� */
	public static final int LEFT = 1;

	/** ���� */
	public static final int CENTER = 2;

	/** �E�� */
	public static final int RIGHT = 3;

	/** �ʒu */
	private int align = 0;

	/** �����l */
	private String str;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param str
	 * @param align
	 */
	public AlignString(String str, int align) {
		this.str = str;
		this.align = align;
	}

	/**
	 * �z�u
	 * 
	 * @return �z�u
	 */
	public int getAlign() {
		return align;
	}

	/**
	 * ������
	 * 
	 * @return ������
	 */
	public String getString() {
		return str;
	}
}
