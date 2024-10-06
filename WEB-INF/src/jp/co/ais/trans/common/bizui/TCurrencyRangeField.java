package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.gui.*;

/**
 * 通貨検索範囲フィールド
 */
public class TCurrencyRangeField extends TRangeField {

	/** UID */
	private static final long serialVersionUID = -1355561921215608682L;

	/**
	 * コンストラクタ
	 */
	public TCurrencyRangeField() {
		super.panelMsgId = "C00371";

		super.init();
	}

	/**
	 * 開始フィールドを生成する(Orverride用)
	 * 
	 * @return 開始フィールド
	 */
	protected TButtonField createBeginField() {
		TCurrencyField field = new TCurrencyField();

		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TCurrencyRangeField.super.getBeginCode();
				((TCurrencyField) TCurrencyRangeField.super.getEndField()).setBeginCode(code);
			}
		});

		return field;
	}

	/**
	 * 終了フィールドを生成する(Orverride用)
	 * 
	 * @return 終了フィールド
	 */
	protected TButtonField createEndField() {
		TCurrencyField field = new TCurrencyField();

		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TCurrencyRangeField.super.getEndCode();
				((TCurrencyField) TCurrencyRangeField.super.getBeginField()).setEndCode(code);
			}
		});

		return field;
	}
}
