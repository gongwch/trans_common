package jp.co.ais.trans.common.gui;

import java.math.*;

import javax.swing.text.*;

import jp.co.ais.trans.common.util.*;

/**
 * 数値用のtext document
 */
public class TNumericPlainDocument extends PlainDocument {

	/** UID */
	protected final static long serialVersionUID = 0L;

	/** 対象フィールド */
	protected JTextComponent field;

	/** 最大入力数 */
	protected int maxLength;

	/** 小数点桁数 */
	protected int decimalPoint = 0;

	/** 整数部桁数 */
	protected int integralPoint = 0;

	/** 正数のみ */
	protected boolean isPositive = false;

	/** 最大値 */
	protected BigDecimal maxValue = null;

	/** 最小値 */
	protected BigDecimal minValue = null;

	/**
	 * コンストラクタ
	 * 
	 * @param field 対象フィールド
	 */
	public TNumericPlainDocument(JTextComponent field) {
		this(field, 17);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param field 対象フィールド
	 * @param maxLength 最大入力数
	 */
	public TNumericPlainDocument(JTextComponent field, int maxLength) {
		this(field, maxLength, -1);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param field 対象フィールド
	 * @param maxLength 最大入力数
	 * @param digits 小数部桁数
	 */
	public TNumericPlainDocument(JTextComponent field, int maxLength, int digits) {
		this.field = field;
		this.maxLength = maxLength;

		this.setDecimalPoint(digits);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param field 対象フィールド
	 * @param maxLength 最大入力数
	 * @param digits 小数部桁数
	 * @param isPositive true:マイナスを許可しない(正数のみ)
	 */
	public TNumericPlainDocument(JTextComponent field, int maxLength, int digits, boolean isPositive) {
		this(field, maxLength, digits);
		this.isPositive = isPositive;
	}

	/**
	 * 最大入力数
	 * 
	 * @return 最大入力数
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * 最大入力数
	 * 
	 * @param maxLength 最大入力数
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;

		// maxLengthが小さい場合、整数部の制限は無し
		if (decimalPoint <= 0) {
			this.integralPoint = maxLength;

		} else if (maxLength - decimalPoint > 0) {
			this.integralPoint = maxLength - decimalPoint;
		}
	}

	/**
	 * 小数点桁数
	 * 
	 * @return 小数点桁数
	 */
	public int getDecimalPoint() {
		return decimalPoint;
	}

	/**
	 * 小数点桁数
	 * 
	 * @param digits 小数点桁数
	 */
	public void setDecimalPoint(int digits) {
		this.decimalPoint = digits;

		if (decimalPoint <= 0) {
			this.integralPoint = maxLength;

		} else if (maxLength - decimalPoint > 0) {
			this.integralPoint = maxLength - decimalPoint;
		}
	}

	/**
	 * 正数のみモード設定
	 * 
	 * @param isPositive trueの場合、正数のみ
	 */
	public void setPositiveOnly(boolean isPositive) {
		this.isPositive = isPositive;
	}

	/**
	 * 正数のみモード取得
	 * 
	 * @return isPositive trueの場合、正数のみ
	 */
	public boolean isPositive() {
		return isPositive;
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
	protected String checkNumStr(final String s) {
		StringBuffer sb = new StringBuffer("");

		for (int cnt = 0; cnt < s.length(); cnt++) {

			String str = s.substring(cnt, cnt + 1);

			// 入力値チェック : -.0-9文字以外は入力禁止
			if (!",".equals(str) && !str.matches("[0-9]") && !"-".equals(str) && !".".equals(str)) {
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
	protected boolean isDefinedNumber(final String num) {
		// 数値かどうか
		String text = num.replace(",", "");
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
