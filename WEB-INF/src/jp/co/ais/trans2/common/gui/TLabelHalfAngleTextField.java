package jp.co.ais.trans2.common.gui;

import jp.co.ais.trans.common.gui.*;

/**
 * ラベル付き半角ｶﾅ制御テキストフィールド
 */
public class TLabelHalfAngleTextField extends TLabelField {
	
	/**
	 * 半角ｶﾅ制御テキストフィールド生成
	 * 
	 * @return 半角ｶﾅ制御テキストフィールド
	 */
	@Override
	protected TTextField createTextField() {
		return new THalfAngleTextField();
	}
}
