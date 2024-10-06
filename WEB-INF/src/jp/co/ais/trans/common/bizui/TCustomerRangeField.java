package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.gui.*;

/**
 * 取引先範囲指定フィールド
 */
public class TCustomerRangeField extends TRangeField {

	/** UID */
	private static final long serialVersionUID = -1448740112699566917L;

	/**
	 * コンストラクタ
	 */
	public TCustomerRangeField() {
		super.panelMsgId = "C10338";

		super.init();
	}

	/**
	 * 開始フィールドを生成する(Orverride用)
	 * 
	 * @return 開始フィールド
	 */
	protected TButtonField createBeginField() {
		TCustomerField field = new TCustomerField();

		// 終了科目に開始コードを設定
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TCustomerRangeField.super.getBeginCode();
				((TCustomerField) TCustomerRangeField.super.getEndField()).setBeginCode(code);
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
		TCustomerField field = new TCustomerField();

		// 終了科目に開始コードを設定
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TCustomerRangeField.super.getEndCode();
				((TCustomerField) TCustomerRangeField.super.getBeginField()).setEndCode(code);
			}
		});

		return field;
	}
}
