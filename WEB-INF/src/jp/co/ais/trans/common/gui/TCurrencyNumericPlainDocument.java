package jp.co.ais.trans.common.gui;

import java.math.*;

import javax.swing.text.*;

import jp.co.ais.trans.common.util.*;

/**
 * VT数値ドキュメント制御
 */
public class TCurrencyNumericPlainDocument extends TNumericPlainDocument {

	/** USD */
	protected boolean USD = false;

	/** JPY */
	protected boolean JPY = false;

	/**
	 * コンストラクタ
	 * 
	 * @param field 対象フィールド
	 */
	public TCurrencyNumericPlainDocument(JTextComponent field) {
		super(field);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param field 対象フィールド
	 * @param maxLength 最大入力数
	 */
	public TCurrencyNumericPlainDocument(JTextComponent field, int maxLength) {
		super(field, maxLength);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param field 対象フィールド
	 * @param maxLength 最大入力数
	 * @param digits 小数部桁数
	 */
	public TCurrencyNumericPlainDocument(JTextComponent field, int maxLength, int digits) {
		super(field, maxLength, digits);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param field 対象フィールド
	 * @param maxLength 最大入力数
	 * @param digits 小数部桁数
	 * @param isPositive true:マイナスを許可しない(正数のみ)
	 */
	public TCurrencyNumericPlainDocument(JTextComponent field, int maxLength, int digits, boolean isPositive) {
		super(field, maxLength, digits, isPositive);
	}

	/**
	 * USDの取得
	 * 
	 * @return USD USD
	 */
	public boolean isUSD() {
		return USD;
	}

	/**
	 * USDの設定
	 * 
	 * @param USD USD
	 */
	public void setUSD(boolean USD) {
		this.USD = USD;
	}

	/**
	 * JPYの取得
	 * 
	 * @return JPY JPY
	 */
	public boolean isJPY() {
		return JPY;
	}

	/**
	 * JPYの設定
	 * 
	 * @param JPY JPY
	 */
	public void setJPY(boolean JPY) {
		this.JPY = JPY;
	}

	@Override
	public void remove(int offs, int len) throws BadLocationException {

		String text = field.getText();
		text = new StringBuilder(text).delete(offs, offs + len).toString();

		// 数値が入力されている場合、チェックを行う
		if (!Util.isNullOrEmpty(text.replace("-", "").replace(".", ""))) {
			if (!isDefinedNumber(text)) {
				return;
			}
		}

		super.remove(offs, len);
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

		// 現在のテキスト
		String nowText = field.getText();

		if (str.length() == 1) {

			// 入力値チェック : -.0-9文字以外は入力禁止
			if (!str.matches("[0-9]") && !"-".equals(str) && !".".equals(str)) {
				return;
			}

			if ("-".equals(str)) {
				// 正数のみ、先頭じゃない、既に-がある場合は除外
				if (isPositive || offs != 0 || nowText.contains("-")) {
					return;
				}
			} else if (".".equals(str)) {
				// 先頭、既に.がある、小数点桁数0、-の次の場合は除外
				if (offs == 0 || nowText.contains(".") || decimalPoint == 0
					|| (offs == 1 && "-".equals(nowText.subSequence(0, 1)))) {
					return;
				}
			}

		} else {
			// ","付き文字列が上書きされるとき。
			// 2文字以上pasteされるとき。
			str = checkNumStr(str);
		}

		// 入力後のテキストに変換
		String text = new StringBuilder(nowText).insert(offs, str).toString();

		// 数値が入力されている場合、チェックを行う
		if (!Util.isNullOrEmpty(text.replace("-", "").replace(".", ""))) {
			if (!isDefinedNumber(text)) {
				return;
			}
		}

		super.insertString(offs, str, a);
	}

	/**
	 * 入力可能文字チェック
	 * 
	 * @param s 入力値
	 * @return 入力不可文字除外
	 */
	@Override
	protected String checkNumStr(final String s) {
		StringBuffer sb = new StringBuffer("");

		String sign = isUSD() ? "$" : "\\";

		for (int cnt = 0; cnt < s.length(); cnt++) {

			String str = s.substring(cnt, cnt + 1);

			// 入力値チェック : -.0-9文字以外は入力禁止
			if (!sign.equals(str) && !",".equals(str) && !str.matches("[0-9]") && !"-".equals(str) && !".".equals(str)
				&& !"(".equals(str) && !")".equals(str)) {
				continue;
			}

			// 正数のみモードの場合、- は除外
			if (isPositive && "-".equals(str)) {
				continue;
			}

			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * 入力数値チェック
	 * 
	 * @param num 入力数値
	 * @return 入力数値がプロパティ設定に当てはまる場合はtrue
	 */
	@Override
	protected boolean isDefinedNumber(final String num) {
		// 数値かどうか
		String text = num.replace(",", "");

		if (isUSD()) {
			text = text.replace("$", "");
		} else if (isJPY()) {
			text = text.replace("\\", "");
		}

		boolean isMinus = false;

		if (text.contains("(")) {
			isMinus = true;
		}

		text = text.replace("(", "");
		text = text.replace(")", "");

		if (isMinus) {
			text = "-" + text;
		}

		if (!Util.isNumber(text)) {
			return false;
		}

		// バイト数による入力制御(MaxLengthが設定されている場合)
		if (maxLength >= 0) {
			// 単純バイト数チェック
			if (text.replace("-", "").replace(".", "").getBytes().length > maxLength) {
				return false;
			}

			// 桁数チェック
			String[] splits = StringUtil.split(text.replace("-", ""), ".");

			// 整数部
			if (integralPoint != -1 && splits[0].length() > integralPoint) {
				return false;
			}

			// 小数部
			if (decimalPoint != -1 && (splits.length >= 2 && splits[1].length() > decimalPoint)) {
				return false;
			}

			// 最大値、最小値チェック
			BigDecimal val = DecimalUtil.toBigDecimalNULL(text);

			if (maxValue != null && val != null && maxValue.compareTo(val) == -1) {
				return false;
			}
			if (minValue != null && val != null && minValue.compareTo(val) == 1) {
				return false;
			}
		}

		return true;
	}

}
