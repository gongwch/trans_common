package jp.co.ais.trans.common.gui;

import javax.swing.text.*;

import jp.co.ais.trans.common.util.*;

/**
 * 半角変換テキストフィールド（テキストフィールド拡張）
 */
public class THarfSizeCharConvertField extends TTextField {

	/** 全角カタカナ */
	public static final char[] ZENKAKU_KATAKANA = { 'ァ', 'ア', 'ィ', 'イ', 'ゥ', 'ウ', 'ェ', 'エ', 'ォ', 'オ', 'カ', 'ガ', 'キ',
			'ギ', 'ク', 'グ', 'ケ', 'ゲ', 'コ', 'ゴ', 'サ', 'ザ', 'シ', 'ジ', 'ス', 'ズ', 'セ', 'ゼ', 'ソ', 'ゾ', 'タ', 'ダ', 'チ', 'ヂ',
			'ッ', 'ツ', 'ヅ', 'テ', 'デ', 'ト', 'ド', 'ナ', 'ニ', 'ヌ', 'ネ', 'ノ', 'ハ', 'バ', 'パ', 'ヒ', 'ビ', 'ピ', 'フ', 'ブ', 'プ',
			'ヘ', 'ベ', 'ペ', 'ホ', 'ボ', 'ポ', 'マ', 'ミ', 'ム', 'メ', 'モ', 'ャ', 'ヤ', 'ュ', 'ユ', 'ョ', 'ヨ', 'ラ', 'リ', 'ル', 'レ',
			'ロ', 'ヮ', 'ワ', 'ヰ', 'ヱ', 'ヲ', 'ン', 'ヴ', 'ヵ', 'ヶ' };

	/** 半角カタカナ */
	public static final String[] HANKAKU_KATAKANA = { "ｧ", "ｱ", "ｨ", "ｲ", "ｩ", "ｳ", "ｪ", "ｴ", "ｫ", "ｵ", "ｶ", "ｶﾞ", "ｷ",
			"ｷﾞ", "ｸ", "ｸﾞ", "ｹ", "ｹﾞ", "ｺ", "ｺﾞ", "ｻ", "ｻﾞ", "ｼ", "ｼﾞ", "ｽ", "ｽﾞ", "ｾ", "ｾﾞ", "ｿ", "ｿﾞ", "ﾀ", "ﾀﾞ",
			"ﾁ", "ﾁﾞ", "ｯ", "ﾂ", "ﾂﾞ", "ﾃ", "ﾃﾞ", "ﾄ", "ﾄﾞ", "ﾅ", "ﾆ", "ﾇ", "ﾈ", "ﾉ", "ﾊ", "ﾊﾞ", "ﾊﾟ", "ﾋ", "ﾋﾞ", "ﾋﾟ",
			"ﾌ", "ﾌﾞ", "ﾌﾟ", "ﾍ", "ﾍﾞ", "ﾍﾟ", "ﾎ", "ﾎﾞ", "ﾎﾟ", "ﾏ", "ﾐ", "ﾑ", "ﾒ", "ﾓ", "ｬ", "ﾔ", "ｭ", "ﾕ", "ｮ", "ﾖ",
			"ﾗ", "ﾘ", "ﾙ", "ﾚ", "ﾛ", "ﾜ", "ﾜ", "ｲ", "ｴ", "ｦ", "ﾝ", "ｳﾞ", "ｶ", "ｹ" };

	/** 全角カタカナ一つ目 */
	public static final char ZENKAKU_KATAKANA_FIRST_CHAR = ZENKAKU_KATAKANA[0];

	/** 全角カタカナ最終目 */
	public static final char ZENKAKU_KATAKANA_LAST_CHAR = ZENKAKU_KATAKANA[ZENKAKU_KATAKANA.length - 1];

	/**
	 * コンストラクタ.
	 */
	public THarfSizeCharConvertField() {
		super();
		// 正規表現での入力制限
		super.setRegex("[ -/:-@\\[-\\`\\{-\\~ｰｦ-ﾟァ-ヶ]");
	}

	/**
	 * 入力制御
	 * 
	 * @return PlainDocument
	 */
	@Override
	protected PlainDocument createPlainDocument() {
		return new TStringPlainDocument(this) {

			/**
			 * @see PlainDocument#insertString(int, String, AttributeSet)
			 */
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

				// バイト数による入力制御
				if (maxLength >= 0) {

					if (a == null || !a.isDefined(StyleConstants.ComposedTextAttribute)) {

						// 現在のテキスト
						String nowText = field.getText();
						if (nowText.getBytes().length + str.getBytes().length > maxLength) {

							if (str.getBytes().length > 2) {
								str = zenkakuToHankaku(str);
							}

							super.insertString(offs, StringUtil.leftBX(str, maxLength - nowText.getBytes().length), a);
							return;
						}

						str = zenkakuToHankaku(str);
					}
				}

				super.insertString(offs, str, a);
			}
		};
	}

	/**
	 * 半角変換
	 * 
	 * @param c
	 * @return 半角カタカナ
	 */
	public static String zenkakuKatakanaToHankakuKatakana(char c) {
		if (c >= ZENKAKU_KATAKANA_FIRST_CHAR && c <= ZENKAKU_KATAKANA_LAST_CHAR) {
			return HANKAKU_KATAKANA[c - ZENKAKU_KATAKANA_FIRST_CHAR];
		} else {
			return String.valueOf(c);
		}
	}

	/**
	 * 全角カタカナをに半角カタカナ変換するメソッド
	 * 
	 * @param s 入力の文字
	 * @return 半角カタカナ
	 */
	public static String zenkakuToHankaku(String s) {
		StringBuffer sb = new StringBuffer(s);
		StringBuffer sb2 = new StringBuffer();
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);

			if (c >= 'ａ' && c <= 'ｚ') {
				sb.setCharAt(i, (char) (c - 'ａ' + 'a'));
			} else if (c >= 'Ａ' && c <= 'Ｚ') {
				sb.setCharAt(i, (char) (c - 'Ａ' + 'A'));
			} else if (c >= '０' && c <= '９') {
				sb.setCharAt(i, (char) (c - '０' + '0'));
			} else if (c == 'ー') {
				sb.setCharAt(i, (char) (c - 'ー' + 'ｰ'));
			}
		}

		for (int i = 0; i < sb.length(); i++) {
			char originalChar = sb.charAt(i);
			String convertedChar = zenkakuKatakanaToHankakuKatakana(originalChar);
			sb2.append(convertedChar);
		}

		return sb2.toString();
	}
}
