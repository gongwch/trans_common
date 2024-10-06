package jp.co.ais.trans.common.gui;

import java.math.*;

/**
 * TPanelに、タブ順、メッセージIDインターフェイスを追加した複合item. 数値入力用. <br>
 * 子item.
 * <ol>
 * <li>label TLabel
 * <li>field TTextField
 * </ol>
 */
public class TLabelNumericField extends TLabelField {

	/**
	 * Creates a new instance of TLabelNumericField
	 */
	public TLabelNumericField() {
		super();

		super.setImeMode(false);
	}

	/**
	 * テキストフィールド生成
	 * 
	 * @return テキストフィールド
	 */
	@Override
	protected TTextField createTextField() {
		return new TNumericField();
	}

	/**
	 * 最大桁数設定.<br>
	 * setMaxLength(17,4)→ #,###,###,###.##0.0000
	 * 
	 * @param maxLength 最大桁数(正数部、小数部合わせて)
	 * @param decimalPoint 小数点桁数
	 */
	public void setMaxLength(int maxLength, int decimalPoint) {
		((TNumericField) field).setMaxLength(maxLength, decimalPoint);
	}

	/**
	 * 数値型のフォーマット
	 * 
	 * @param format フォーマット
	 */
	public void setNumericFormat(String format) {
		((TNumericField) field).setNumericFormat(format);
	}

	/**
	 * フォーマット(数値型)
	 * 
	 * @return 数値型のフォーマット
	 */
	public String getNumericFormat() {
		return ((TNumericField) field).getNumericFormat();
	}

	/**
	 * 常に非IMEモード.
	 * 
	 * @deprecated 常に非IMEモード.
	 */
	@Override
	public void setImeMode(boolean flag) {
		super.setImeMode(false);
	}

	/**
	 * 正数のみモード設定
	 */
	public void setPositiveOnly() {
		setPositiveOnly(true);
	}

	/**
	 * 正数のみモード設定
	 * 
	 * @param isPositive trueの場合、正数のみ
	 */
	public void setPositiveOnly(boolean isPositive) {
		((TNumericField) field).setPositiveOnly(isPositive);
	}

	/**
	 * パネル上のTTextField fieldインスタンスを返す.
	 * 
	 * @return field
	 */
	@Override
	public TNumericField getField() {
		return (TNumericField) super.getField();
	}

	/**
	 * 変更されたかチェックする
	 * 
	 * @return 変更されている場合、true
	 */
	@Override
	public boolean isValueChanged() {

		return this.field.isValueChanged();
	}

	/**
	 * 変更されたかチェックする(null含む)
	 * 
	 * @return 変更されている場合、true
	 */
	public boolean isValueChanged2() {

		return this.field.isValueChanged2();
	}

	/**
	 * int型で表示数値を取得する
	 * 
	 * @return 数値
	 */
	public int getIntValue() {
		return getField().getInt();
	}

	/**
	 * double型で表示数値を取得する<br>
	 * doubleではなくBigDecimalで.
	 * 
	 * @deprecated double使うな
	 * @return 数値
	 */
	public double getDoubleValue() {
		return getField().getDouble();
	}

	/**
	 * BigDecimal型で表示数値を取得する
	 * 
	 * @return 数値
	 */
	public BigDecimal getBigDecimalValue() {
		return getField().getBigDecimal();
	}

	/**
	 * BigDecimal型で表示数値を取得する
	 * 
	 * @return 数値
	 */
	public BigDecimal getBigDecimal() {
		return getField().getBigDecimal();
	}

	/**
	 * 表示文字をそのまま取得
	 * 
	 * @return 表示文字
	 */
	public String getInputText() {
		return getField().getInputText();
	}

	/**
	 * Double値設定. フォーマット変換が発生<br>
	 * doubleではなくBigDecimalで.
	 * 
	 * @deprecated double使うな
	 * @param value 数値
	 */
	public void setDoubleValue(Double value) {
		getField().setDouble(value);
	}

	/**
	 * Number値設定. フォーマット変換が発生
	 * 
	 * @param value 数値
	 */
	public void setNumberValue(Number value) {
		getField().setNumber(value);
	}

	/**
	 * Number値設定. フォーマット変換が発生
	 * 
	 * @param value 数値
	 */
	public void setNumber(Number value) {
		getField().setNumber(value);
	}

	/**
	 * フォーマットが設定されているかどうか
	 * 
	 * @return true : フォマット有り false : フォーマットなし
	 */
	public boolean isFormatterExist() {
		return ((TNumericField) field).isFormatterExist();
	}

	/**
	 * 小数点桁数を0フォーマット(ex:#.0000)で変更する.
	 * 
	 * @param digit 小数点桁数
	 * @see TNumericField#setDecimalPoint(int)
	 */
	public void setDecimalPoint(int digit) {
		((TNumericField) field).setDecimalPoint(digit);
	}

	/**
	 * 小数点桁数
	 * 
	 * @return 小数点桁数
	 * @see TNumericField#getDecimalPoint()
	 */
	public int getDecimalPoint() {
		return ((TNumericField) field).getDecimalPoint();
	}

	/**
	 * 小数点桁数を0フォーマット(ex:#.0000)で変更する.<br>
	 * TRANS1.0対応
	 * 
	 * @param digit 小数点桁数
	 */
	@Deprecated
	public void setFractionDegits(int digit) {
		setDecimalPoint(digit);
	}

	/**
	 * 小数点桁数<br>
	 * TRANS1.0対応
	 * 
	 * @return 小数点桁数
	 */
	@Deprecated
	public int getFractionDegits() {
		return getDecimalPoint();
	}

	/**
	 * マイナス値を入力された際、赤文字に変更するかどうか.
	 * 
	 * @param isChangeRedOfMinus true:変更する
	 * @see TNumericField#setChangeRedOfMinus(boolean)
	 */
	public void setChangeRedOfMinus(boolean isChangeRedOfMinus) {
		((TNumericField) field).setChangeRedOfMinus(isChangeRedOfMinus);
	}

	/**
	 * マイナス値を入力された際、赤文字に変更するかどうか.
	 * 
	 * @return true:変更する
	 * @see TNumericField#isChangeRedOfMinus()
	 */
	public boolean isChangeRedOfMinus() {
		return ((TNumericField) field).isChangeRedOfMinus();
	}

	/**
	 * 入力値をクリアする
	 */
	@Override
	public void clear() {
		this.field.clear();
	}

	/**
	 * 入力値がゼロかどうか.
	 * 
	 * @return true:ゼロ
	 */
	public boolean isZero() {
		return ((TNumericField) field).isZero();
	}

	/**
	 * 最大値の取得
	 * 
	 * @return maxValue 最大値
	 */
	public BigDecimal getMaxValue() {
		return getField().getMaxValue();
	}

	/**
	 * 最大値の設定
	 * 
	 * @param maxValue 最大値
	 */
	public void setMaxValue(BigDecimal maxValue) {
		getField().setMaxValue(maxValue);
	}

	/**
	 * 最小値の取得
	 * 
	 * @return minValue 最小値
	 */
	public BigDecimal getMinValue() {
		return getField().getMinValue();
	}

	/**
	 * 最小値の設定
	 * 
	 * @param minValue 最小値
	 */
	public void setMinValue(BigDecimal minValue) {
		getField().setMinValue(minValue);
	}
}
