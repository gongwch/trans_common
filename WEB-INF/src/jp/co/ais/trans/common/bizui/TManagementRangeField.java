package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.gui.*;

/**
 * 管理1～6範囲指定フィールド
 */
public class TManagementRangeField extends TRangeField {

	/** 管理1～6 */
	protected int type;

	/**
	 * コンストラクタ
	 * 
	 * @param type 管理タイプ（TManagement）
	 */
	public TManagementRangeField(int type) {

		this.type = type;
		super.panelMsgId = getWord("C00094") + this.type + getWord("C03186");

		init();
	}

	/**
	 * 開始フィールドを生成する(Orverride用)
	 * 
	 * @return 開始フィールド
	 */
	protected TButtonField createBeginField() {
		TManagementField field = new TManagementField(type);

		// 終了科目に開始コードを設定
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TManagementRangeField.super.getBeginCode();
				((TManagementField) TManagementRangeField.super.getEndField()).setBeginCode(code);
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
		TManagementField field = new TManagementField(type);

		// 終了科目に開始コードを設定
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TManagementRangeField.super.getEndCode();
				((TManagementField) TManagementRangeField.super.getBeginField()).setEndCode(code);
			}
		});

		return field;
	}
}
