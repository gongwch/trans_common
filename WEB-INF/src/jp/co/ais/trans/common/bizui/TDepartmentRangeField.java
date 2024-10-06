package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.gui.*;

/**
 * 部門範囲指定フィールド
 */
public class TDepartmentRangeField extends TRangeField {

	/** UID */
	private static final long serialVersionUID = -4842044557647905736L;

	/**
	 * コンストラクタ
	 */
	public TDepartmentRangeField() {
		super.panelMsgId = getWord("C00467") + getWord("C03186");

		super.init();
	}

	/**
	 * 開始フィールドを生成する(Orverride用)
	 * 
	 * @return 開始フィールド
	 */
	protected TButtonField createBeginField() {
		TDepartmentField field = new TDepartmentField();

		// 終了科目に開始コードを設定
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TDepartmentRangeField.super.getBeginCode();
				((TDepartmentField) TDepartmentRangeField.super.getEndField()).setBeginCode(code);
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
		TDepartmentField field = new TDepartmentField();

		// 終了科目に開始コードを設定
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TDepartmentRangeField.super.getEndCode();
				((TDepartmentField) TDepartmentRangeField.super.getBeginField()).setEndCode(code);
			}
		});

		return field;
	}

	/**
	 * 集計区分設定
	 * 
	 * @param sumDepartment true:集計部門のみ
	 */
	public void setSumDepartment(boolean sumDepartment) {
		((TDepartmentField) TDepartmentRangeField.super.getBeginField()).setSumDepartment(sumDepartment);
		((TDepartmentField) TDepartmentRangeField.super.getEndField()).setSumDepartment(sumDepartment);
	}
}
