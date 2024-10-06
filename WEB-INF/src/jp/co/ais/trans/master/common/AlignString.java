package jp.co.ais.trans.master.common;

/**
 * 文字列と配置を保持するクラス
 */
public class AlignString {

	/** 左寄せ */
	public static final int LEFT = 1;

	/** 中央 */
	public static final int CENTER = 2;

	/** 右寄せ */
	public static final int RIGHT = 3;

	/** 位置 */
	private int align = 0;

	/** 文字値 */
	private String str;

	/**
	 * コンストラクタ
	 * 
	 * @param str
	 * @param align
	 */
	public AlignString(String str, int align) {
		this.str = str;
		this.align = align;
	}

	/**
	 * 配置
	 * 
	 * @return 配置
	 */
	public int getAlign() {
		return align;
	}

	/**
	 * 文字列
	 * 
	 * @return 文字列
	 */
	public String getString() {
		return str;
	}
}
