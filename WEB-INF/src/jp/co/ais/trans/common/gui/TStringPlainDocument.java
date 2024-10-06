package jp.co.ais.trans.common.gui;

import javax.swing.text.*;

import jp.co.ais.trans.common.util.*;

/**
 * 文字列用のtext document
 */
public class TStringPlainDocument extends PlainDocument {

	/** 対象フィールド */
	protected JTextComponent field;

	/** スペース入力を許可するかどうか */
	protected boolean isAllowedSpace = true;

	/** IMEモード(全角許可) */
	protected boolean isImeMode = true;

	/** 最大長 */
	protected int maxLength = 128;

	/** 禁則文字リスト */
	protected String[] prohibitionWords = new String[0];

	/** 入力可能性正規表現文字 */
	protected String regex;

	/**
	 * コンストラクタ.<br>
	 * デフォルトの最大入力数は128
	 * 
	 * @param field 対象フィールド
	 */
	public TStringPlainDocument(JTextComponent field) {
		this(field, 128, true);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param field 対象フィールド
	 * @param maxLength 最大入力数
	 */
	public TStringPlainDocument(JTextComponent field, int maxLength) {
		this(field, maxLength, true);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param field 対象フィールド
	 * @param maxLength 最大入力数
	 * @param imeFlag IME制御フラグ
	 */
	public TStringPlainDocument(JTextComponent field, int maxLength, boolean imeFlag) {
		this.field = field;
		this.maxLength = maxLength;
		field.enableInputMethods(imeFlag);
	}

	/**
	 * @see PlainDocument#insertString(int, String, AttributeSet)
	 */
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

		// スペース入力不可ならスペースを削除
		if (!isAllowedSpace) {
			str = str.replace(" ", "");
		}

		// タブ文字禁止
		str = str.replace("\t", "");

		// IMEモードOFF
		if (!isImeMode) {
			for (int i = 0; i < str.length(); i++) {
				String s = str.substring(i, i + 1);

				// 全角禁止で全角が含まれている場合はNG
				if (s.getBytes().length > 1) {
					return;
				}

				// 正規表現チェック : 半角カナはNG
				if (s.matches("[｡-ﾟ]")) {
					return;
				}
			}
		}

		if (a == null || !a.isDefined(StyleConstants.ComposedTextAttribute)) {

			// 正規表現指定による入力文字の制御
			if (regex != null && !"".equals(regex)) {
				String replaceStr = str;
				for (int i = 0; i < str.length(); i++) {
					String s = str.substring(i, i + 1);

					// 正規表現チェック : 指定文字以外は削除
					if (!s.matches(regex)) {
						replaceStr = replaceStr.replace(s, "");
					}
				}
				str = replaceStr;
			}

			// 禁則文字が指定されている場合は削除
			for (String word : prohibitionWords) {
				str = str.replace(word, "");
			}
		}

		// バイト数による入力制御
		if (maxLength >= 0) {

			// IMEで入力時に、
			// 未確定(ComposedText)のときは１文字づつ追加。
			// 確定時に未確定が削除され、再度全文字が追加される
			// 文字長さは再度追加されたときにチェックする。

			if ((a != null) && (a.isDefined(StyleConstants.ComposedTextAttribute))) {
				super.insertString(offs, str, a);
				return;
			}

			// 現在のテキスト
			String nowText = field.getText();
			if (nowText.getBytes().length + str.getBytes().length > maxLength) {

				super.insertString(offs, StringUtil.leftBX(str, maxLength - nowText.getBytes().length), a);
				return;
			}
		}

		super.insertString(offs, str, a);
	}

	/**
	 * IMEモード設定.trueなら全角可
	 * 
	 * @return true:全角含む
	 */
	public boolean isImeMode() {
		return this.isImeMode;
	}

	/**
	 * IMEモード設定.trueなら全角可
	 * 
	 * @param imeMode true:全角含む
	 */
	public void setImeMode(boolean imeMode) {
		this.isImeMode = imeMode;
	}

	/**
	 * space入力許可
	 * 
	 * @return true:許可
	 */
	public boolean isAllowedSpace() {
		return this.isAllowedSpace;
	}

	/**
	 * space入力許可
	 * 
	 * @param isAllowedSpace true:許可
	 */
	public void setAllowedSpace(boolean isAllowedSpace) {
		this.isAllowedSpace = isAllowedSpace;
	}

	/**
	 * 最大桁数
	 * 
	 * @return 最大桁数
	 */
	public int getMaxLength() {
		return this.maxLength;
	}

	/**
	 * 最大桁数
	 * 
	 * @param maxLength 最大桁数
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * 禁則文字(入力させない文字)の設定
	 * 
	 * @param words 禁則文字リスト
	 */
	public void setProhibitionWords(String... words) {
		this.prohibitionWords = words;
	}

	/**
	 * 入力可能文字を正規表現で指定する.
	 * 
	 * @param regex 正規表現文字
	 */
	public void setRegex(String regex) {
		this.regex = regex;
	}
}
