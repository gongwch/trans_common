package jp.co.ais.trans2.define;

/**
 * トラオペ向けEnum上位I/F
 */
public interface OPEnum {

	/**
	 * コード区分
	 * 
	 * @return コード区分
	 */
	public OPCodeDivision getCodeDivision();

	/**
	 * 識別コード
	 * 
	 * @return 識別コード
	 */
	public String getCode();

}
