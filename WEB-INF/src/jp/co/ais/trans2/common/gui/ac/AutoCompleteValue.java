package jp.co.ais.trans2.common.gui.ac;

import jp.co.ais.trans.common.dt.*;

/**
 * インクリメントサーチ結果表示
 */
public class AutoCompleteValue extends TransferBase {

	/** 値 */
	public Object value;

	/** 表示名 */
	public String text;

	/**
	 * コンストラクター
	 * 
	 * @param value
	 */
	public AutoCompleteValue(Object value) {
		if (value != null) {
			if (value instanceof AutoCompletable) {
				// 表示名を呼出す
				this.value = value;
				this.text = ((AutoCompletable) value).getDisplayText();
			} else {
				// toString隠れ呼出す
				this.value = value;
				this.text = value.toString();
			}
		}
	}

	@Override
	public String toString() {
		return text;
	}
}
