package jp.co.ais.trans.master.common;

/**
 * 
 */
public class TTextValue {

	/**  */
	private String text;

	/**  */
	private String value;

	/**
	 * 
	 */
	public TTextValue() {
		//
	}

	/**
	 * @param text
	 */
	public TTextValue(String text) {
		this.text = text;
		this.value = text;
	}

	/**
	 * @param text
	 * @param value
	 */
	public TTextValue(String text, String value) {
		this.text = text;
		this.value = value;
	}

	/**
	 * @return String
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return String
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return this.text;
	}
}
