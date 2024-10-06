package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.gui.*;

/**
 * 社員検索範囲フィールド
 */
public class TEmployeeRangeField extends TRangeField {

	/** UID */
	private static final long serialVersionUID = -1355561921215608682L;

	/**
	 * コンストラクタ
	 */
	public TEmployeeRangeField() {
		super.panelMsgId = "C00246";

		super.init();
	}

	/**
	 * 開始フィールドを生成する(Orverride用)
	 * 
	 * @return 開始フィールド
	 */
	protected TButtonField createBeginField() {
		TEmployeeField field = new TEmployeeField();

		// 終了科目に開始コードを設定
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TEmployeeRangeField.super.getBeginCode();
				((TEmployeeField) TEmployeeRangeField.super.getEndField()).setBeginCode(code);
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
		TEmployeeField field = new TEmployeeField();

		// 終了科目に開始コードを設定
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TEmployeeRangeField.super.getEndCode();
				((TEmployeeField) TEmployeeRangeField.super.getBeginField()).setEndCode(code);
			}
		});

		return field;
	}
}
