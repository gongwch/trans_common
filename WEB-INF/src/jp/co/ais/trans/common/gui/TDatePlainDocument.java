package jp.co.ais.trans.common.gui;

import javax.swing.text.*;

import jp.co.ais.trans.common.util.*;

/**
 * 日付用のtext document
 */
public class TDatePlainDocument extends PlainDocument {

	/** 年(YYYY) */
	public static final int TYPE_Y = 1;

	/** 年月(YYYY/MM) */
	public static final int TYPE_YM = 2;

	/** 年月日(YYYY/MM/DD) */
	public static final int TYPE_YMD = 3;

	/** 最大桁数 */
	protected int maxLength;

	/** 対象フィールド */
	protected JTextComponent field;

	/**
	 * コンストラクタ
	 * 
	 * @param field 対象フィールド
	 */
	public TDatePlainDocument(JTextComponent field) {
		this(field, TYPE_YMD);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param field 対象フィールド
	 * @param type 日付タイプ(TYPE_XX)
	 */
	public TDatePlainDocument(JTextComponent field, int type) {
		this.field = field;

		switch (type) {
			case TYPE_Y:
				maxLength = 4;
				break;

			case TYPE_YM:
				maxLength = 7;
				break;

			default:
				maxLength = 10;
				break;
		}
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

		// 現在のテキスト
		String nowText = field.getText();

		if (str.length() == 1) {

			// 入力値チェック : /.1-9文字以外は入力禁止
			if (!str.matches("[0-9]") && !"/".equals(str)) {
				return;
			}

		} else {
			// 2文字以上pasteされるとき。
			str = checkDateStr(str);
		}

		// 入力後のテキストに変換
		String text = new StringBuilder(nowText).insert(offs, str).toString();

		if (!isDefinedNumber(text)) {
			return;
		}

		super.insertString(offs, str, a);
	}

	/**
	 * 入力可能文字チェック
	 * 
	 * @param s 入力値
	 * @return 入力不可文字除外
	 */
	protected String checkDateStr(final String s) {
		StringBuffer sb = new StringBuffer("");

		for (int cnt = 0; cnt < s.length(); cnt++) {

			String str = s.substring(cnt, cnt + 1);

			// 入力値チェック : /,0-9文字以外は入力禁止
			if (!str.matches("[0-9]") && !"/".equals(str)) {
				continue;
			}
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * 入力数値チェック
	 * 
	 * @param text 入力数値
	 * @return 入力数値がプロパティ設定に当てはまる場合はtrue
	 */
	protected boolean isDefinedNumber(final String text) {

		// 単純バイト数チェック
		if (text.getBytes().length > maxLength) {
			return false;

		} else if (text.getBytes().length == maxLength) {
			return DateUtil.isDate(text);
		}

		return true;
	}

}