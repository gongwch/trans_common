package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.math.*;
import java.text.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * VT数値フィールド
 */
public class TCurrencyNumericField extends TTextField {

	/** 小数点基準ポイント */
	protected static final String DECIMAL_POINT = ".";

	/** 指定フォーマット */
	protected DecimalFormat formatter = null;

	/** マイナス値を入力された際、赤文字に変更するかどうか. */
	protected boolean isChangeRedOfMinus = true;

	/** 指定小数点桁数 */
	protected int digit;

	/** デフォルト文字色 */
	protected Color defaultForeground;

	/** スプレッドのフォントを使う */
	protected boolean useTableFont = false;

	/** 最大値 */
	protected BigDecimal maxValue = null;

	/** 最小値 */
	protected BigDecimal minValue = null;

	/**
	 * Constructor.
	 */
	public TCurrencyNumericField() {
		super();

		defaultForeground = TUIManager.getTextDefaultForeground();

		TUIManager.setLogicalFont(this);

		super.setImeMode(false);
		super.setHorizontalAlignment(SwingConstants.RIGHT);

		this.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				String text = TCurrencyNumericField.this.getText();
				TCurrencyNumericField.this.setText(text);

				return true;
			}
		});

		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent evt) {
				if (TGuiUtil.isActive(TCurrencyNumericField.this)) {
					String text = TCurrencyNumericField.this.getText();

					TCurrencyNumericField.this.setTextNonFormat(getTextForNonFormat(text));
					selectAll();
				}
			}
		});

		// numeric key listener
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
					// 矢印キーのUpとDownを押し フリッパーを実現する
					if (tableCellEditor || !TCurrencyNumericField.this.isFocusOwner()
						|| !TCurrencyNumericField.this.isEditable()) {
						return;
					}

					if (!upOrDownAction(e.getKeyCode())) {
						e.consume();
					}
				}
			}
		});

		// mouse wheel listener
		this.addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {
				if (!TCurrencyNumericField.this.isFocusOwner() || !TCurrencyNumericField.this.isEditable()) {
					return;
				}

				boolean isDown = true;
				int rotation = e.getWheelRotation();

				// Downが正、Upが負
				if (rotation < 0) {
					isDown = false;
					rotation *= -1;
				}

				for (int i = 0; i < rotation; i++) {
					if (!upOrDownAction(isDown ? KeyEvent.VK_DOWN : KeyEvent.VK_UP)) {
						e.consume();
						break;
					}
				}
			}
		});
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param format
	 */
	public TCurrencyNumericField(String format) {
		this();

		setNumericFormat(format);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param i インデックス番号
	 */
	public TCurrencyNumericField(int i) {
		this();

		setRowIndex(i);
	}

	/**
	 * テキスト補助機能
	 */
	@Override
	protected void initTextHelper() {
		// UNDO/REDO は動作が怪しいので無し

		// カットアンドペースト
		addMouseListener(TGuiUtil.createCnPListener(this));
	}

	/**
	 * 入力制御
	 * 
	 * @return PlainDocument
	 */
	@Override
	protected PlainDocument createPlainDocument() {
		return new TCurrencyNumericPlainDocument(this);
	}

	/**
	 * space入力許可
	 * 
	 * @return true:許可
	 */
	@Override
	public boolean isAllowedSpace() {
		return false;
	}

	/**
	 * space入力許可
	 * 
	 * @param b true:許可
	 */
	@Override
	public void setAllowedSpace(boolean b) {
		// スペースは入力不可
	}

	/**
	 * 最大桁数
	 * 
	 * @return 最大桁数
	 */
	@Override
	public int getMaxLength() {
		return ((TCurrencyNumericPlainDocument) document).getMaxLength();
	}

	/**
	 * 最大桁数設定.<br>
	 * setMaxLength(17,4)→ #,###,###,###,##0.0000
	 * 
	 * @param maxLength 最大桁数(正数部、小数部合わせて)
	 * @param decimalPoint 小数点桁数
	 */
	public void setMaxLength(int maxLength, int decimalPoint) {
		setMaxLength(maxLength);
		setNumericFormat("#,##0");
		setDecimalPoint(decimalPoint);
	}

	/**
	 * 最大桁数
	 * 
	 * @param maxLength 最大桁数
	 */
	@Override
	public void setMaxLength(int maxLength) {
		((TCurrencyNumericPlainDocument) document).setMaxLength(maxLength);

		if (maxLength == 1) {
			setNumericFormat("#");
		}
	}

	/**
	 * USDの取得
	 * 
	 * @return USD USD
	 */
	public boolean isUSD() {
		return ((TCurrencyNumericPlainDocument) document).isUSD();
	}

	/**
	 * USDの設定
	 * 
	 * @param USD USD
	 */
	public void setUSD(boolean USD) {
		((TCurrencyNumericPlainDocument) document).setUSD(USD);
		if (USD) {
			setMaxLength(14, 2);
		}
	}

	/**
	 * JPYの取得
	 * 
	 * @return JPY JPY
	 */
	public boolean isJPY() {
		return ((TCurrencyNumericPlainDocument) document).isJPY();
	}

	/**
	 * JPYの設定
	 * 
	 * @param JPY JPY
	 */
	public void setJPY(boolean JPY) {
		((TCurrencyNumericPlainDocument) document).setJPY(JPY);
		if (JPY) {
			setMaxLength(12, 0);
		}
	}

	/**
	 * 数値型のフォーマット.<br>
	 * 指定フォーマットに小数点が含まれている場合、整数部最大桁数は<br>
	 * MaxLengthから小数点桁数を引いたものに自動的に割り当てられる.<br>
	 * MaxLength = 整数部桁数 + 小数点桁数
	 * 
	 * @param format フォーマット
	 */
	public void setNumericFormat(String format) {
		// 空文字を指定されたら設定しない
		if (Util.isNullOrEmpty(format)) {
			return;
		}

		this.formatter = new DecimalFormat(format);
		// if (isUSD()) {
		// formatter.setCurrency(Currency.getInstance("USD"));
		// } else {
		// formatter.setCurrency(Currency.getInstance("JPY"));
		// }

		// 最大小数点桁数
		this.digit = formatter.getMaximumFractionDigits();
		((TCurrencyNumericPlainDocument) document).setDecimalPoint(digit);

		if (!isEmpty()) {
			this.setNumber(this.getBigDecimal());
		}
	}

	/**
	 * 小数点桁数を0フォーマット(ex:#.0000)で変更する.
	 * 
	 * @param digit 小数点桁数
	 */
	public void setDecimalPoint(int digit) {
		this.digit = digit;

		if (this.formatter == null) {
			((TCurrencyNumericPlainDocument) document).setDecimalPoint(digit);
			return;
		}

		String format = this.formatter.toPattern();
		String[] splitFormat = StringUtil.split(format, ".");

		StringBuilder buff = new StringBuilder();

		if (digit > 0) {
			buff.append(".");
			for (int i = 1; i <= digit; i++) {
				buff.append("0");
			}
		}

		this.setNumericFormat(splitFormat[0] + buff.toString());
	}

	/**
	 * 小数点桁数
	 * 
	 * @return 小数点桁数
	 */
	public int getDecimalPoint() {
		return this.digit;
	}

	/**
	 * マイナス値を入力された際、赤文字に変更するかどうか.
	 * 
	 * @param isChangeRedOfMinus true:変更する
	 */
	public void setChangeRedOfMinus(boolean isChangeRedOfMinus) {
		this.isChangeRedOfMinus = isChangeRedOfMinus;
	}

	/**
	 * マイナス値を入力された際、赤文字に変更するかどうか.
	 * 
	 * @return true:変更する
	 */
	public boolean isChangeRedOfMinus() {
		return this.isChangeRedOfMinus;
	}

	/**
	 * 数値型のフォーマット
	 * 
	 * @return 数値型のフォーマット
	 */
	public String getNumericFormat() {
		if (formatter == null) {
			return "";
		}
		return formatter.toPattern();
	}

	/**
	 * 常にIMEモードです
	 * 
	 * @deprecated 常にIMEモードです
	 */
	@Override
	public void setImeMode(boolean flag) {
		super.setImeMode(false);
	}

	/**
	 * 正数のみモード設定
	 * 
	 * @param isPositive trueの場合、正数のみ
	 */
	public void setPositiveOnly(boolean isPositive) {
		((TCurrencyNumericPlainDocument) document).setPositiveOnly(isPositive);
	}

	/**
	 * 正数のみモード
	 * 
	 * @return trueの場合、正数のみ
	 */
	public boolean isPositiveOnly() {
		return ((TCurrencyNumericPlainDocument) document).isPositive();
	}

	/**
	 * フォーマット表示から数値表現へ修正してテキスト値を取得する
	 * 
	 * @return 数値表現文字列
	 */
	@Override
	public String getText() {
		return super.getInputText().replace(",", "");
	}

	/**
	 * int型で表示数値を取得する
	 * 
	 * @return 数値
	 */
	public int getInt() {
		String txt = getText();

		if (Util.isNullOrEmpty(txt)) {
			return 0;
		}

		return Integer.parseInt(txt);
	}

	/**
	 * double型で表示数値を取得する<br>
	 * doubleではなくBigDecimalで.
	 * 
	 * @deprecated double使うな
	 * @return 数値
	 */
	public double getDouble() {
		String txt = getText();

		if (Util.isNullOrEmpty(txt)) {
			return 0d;
		}

		return Double.parseDouble(txt);
	}

	/**
	 * BigDecimal型で表示数値を取得する
	 * 
	 * @return 数値
	 */
	public BigDecimal getBigDecimal() {
		String txt = getTextForNonFormat(getText());

		if (Util.isNullOrEmpty(txt) || txt.trim().equals("-")) {
			return BigDecimal.ZERO;
		}

		return new BigDecimal(txt);
	}

	/**
	 * @param text
	 * @return テキスト
	 */
	protected String getTextForNonFormat(String text) {

		text = text.replace(",", "");
		text = text.replace("$", "");
		text = text.replace("\\", "");

		boolean isMinus = false;

		if (text.indexOf("(") != -1) {
			isMinus = true;
		}

		text = text.replace("(", "");
		text = text.replace(")", "");

		return (isMinus ? "-" : "") + text;
	}

	/**
	 * テキスト設定. フォーマット変換が発生
	 * 
	 * @param text テキスト
	 */
	@Override
	public void setText(String text) {
		try {
			// 空白または記号のみの場合はフォーマットせずにテキストをクリア
			if (text == null || "".equals(text.replace("-", "").replace(".", ""))) {
				super.setText("");
				return;
			}

			String cur = "";
			if (isUSD()) {
				cur = "$";
			} else if (isJPY()) {
				cur = "\\";
			}

			if (!Util.isNullOrEmpty(cur)) {
				text = text.replace(cur, "");
			}

			if (formatter != null) {
				try {
					BigDecimal dec = new BigDecimal(text.replaceAll(",", ""));

					String ftext = this.formatter.format(dec);
					// -0表示を防ぐ
					if ("-0".equals(ftext)) {
						ftext = "0";
					}

					ftext = cur + ftext;

					super.setText(ftext);

				} catch (NumberFormatException ex) {
					throw new TRuntimeException(ex);
				}
			} else {
				super.setText(text);
			}

		} finally {

			if (this.isChangeRedOfMinus) {
				// 値がマイナス時に赤字変換
				super.setForeground(this.isMinus() ? Color.RED : TUIManager.getTextDefaultForeground());
			}
		}
	}

	/**
	 * Double値設定. フォーマット変換が発生<br>
	 * doubleではなくBigDecimalで.
	 * 
	 * @deprecated double使うな
	 * @param value 数値
	 */
	public void setDouble(Double value) {
		if (value == -0d) {
			// -0表示を防ぐ
			value = 0d;
		}

		this.setNumber(value);
	}

	/**
	 * Number値設定. フォーマット変換が発生
	 * 
	 * @param value 数値
	 */
	public void setNumber(Number value) {
		try {
			if (value == null) {
				super.setText("");
				return;
			}

			String cur = "";
			if (isUSD()) {
				cur = "$";
			} else if (isJPY()) {
				cur = "\\";
			}

			if (formatter != null) {
				String ftext = this.formatter.format(value);
				// -0表示を防ぐ
				if ("-0".equals(ftext)) {
					ftext = "0";
				}

				boolean isMinus = ftext.contains("-");

				ftext = cur + ftext;
				if (isMinus) {
					ftext = ftext.replace("-", "");
					ftext = "(" + ftext + ")";
				}
				super.setText(ftext);

			} else {
				super.setText(String.valueOf(value));
			}

		} finally {
			if (this.isChangeRedOfMinus) {
				// 値がマイナス時に赤字変換
				super.setForeground(this.isMinus() ? Color.RED : defaultForeground);
			}
		}
	}

	/**
	 * スプレッド表示用テキストの取得
	 * 
	 * @param num
	 * @param value
	 * @return スプレッド表示用テキスト
	 */
	public static String getCellText(TCurrencyNumericField num, Object value) {
		if (Util.isNullOrEmpty(value)) {
			return "";
		}

		BigDecimal n = null;

		if (Util.isNumber(Util.avoidNull(value))) {
			n = DecimalUtil.toBigDecimalNVL(value);
		}

		if (num.formatter != null) {

			String cur = "";
			if (num.isUSD()) {
				cur = "$";
			} else if (num.isJPY()) {
				cur = "\\";
			}

			String ftext = num.formatter.format(n);
			// -0表示を防ぐ
			if ("-0".equals(ftext)) {
				ftext = "0";
			}

			boolean isMinus = ftext.contains("-");

			ftext = cur + ftext;
			if (isMinus) {
				ftext = ftext.replace("-", "");
				ftext = "(" + ftext + ")";
			}
			return ftext;

		} else {

			return String.valueOf(value);
		}

	}

	/**
	 * @see com.klg.jclass.field.JCTextField#setForeground(java.awt.Color)
	 */
	@Override
	public void setForeground(Color color) {
		super.setForeground(color);

		this.defaultForeground = color;
	}

	/**
	 * フォーマットが設定されているかどうか
	 * 
	 * @return true : フォマット有り false : フォーマットなし
	 */
	public boolean isFormatterExist() {
		return this.formatter != null;
	}

	/**
	 * フォーマット変換無しのテキスト設定
	 * 
	 * @param text テキスト
	 */
	protected void setTextNonFormat(String text) {
		super.setText(text);
	}

	/**
	 * 数値がマイナスかどうか
	 * 
	 * @return true:マイナス
	 */
	protected boolean isMinus() {
		return getBigDecimal().compareTo(BigDecimal.ZERO) < 0;
	}

	/**
	 * UPとDownイベント
	 * 
	 * @param actionFlg 操作フラグ
	 * @return true:処理した
	 */
	protected boolean upOrDownAction(int actionFlg) {
		// 「.」だけ残した状態でスピンさせると内部でエラーを避ける
		if (this.getText().equals(".")) {
			return false;
		}
		// 整数部フラグ
		boolean flgPositive = true;
		// テストの値
		BigDecimal value = this.getBigDecimal();

		// テストの改変以前の値
		BigDecimal oldValue = this.getBigDecimal();

		// JTextField中カレントカーソルの位置：
		int position = this.getCaretPosition();

		this.setTextNonFormat(getBigDecimal().toString());

		// 小数点位置
		int pointIndex = getPointIndex();

		if (position <= pointIndex) {
			position = pointIndex;
			flgPositive = true;
		} else {
			position = getText().length();
			flgPositive = false;
		}

		// 小数桁
		int digit_ = getDigit();

		// UPイベント
		if (actionFlg == KeyEvent.VK_UP) {

			// 整数区分を計算
			if (pointIndex == -1 || position <= pointIndex) {

				BigDecimal positive = DecimalUtil.separateDecimal(value)[0];

				if (value.compareTo(BigDecimal.ZERO) < 0 && positive.compareTo(BigDecimal.ZERO) == 0) {
					value = value.abs().add(BigDecimal.ONE);

				} else {
					value = value.add(BigDecimal.ONE);
				}

			} else {
				value = value.add(new BigDecimal(DecimalUtil.calculatePower(10, -(digit_))));
			}

		} else if (actionFlg == KeyEvent.VK_DOWN) {
			BigDecimal positive = DecimalUtil.separateDecimal(value)[0];

			// 整数区分を計算
			if (pointIndex == -1 || position <= pointIndex) {

				if (((TCurrencyNumericPlainDocument) document).isPositive() && positive.compareTo(BigDecimal.ZERO) == 0) {
					value = oldValue;
					return true;
				}

				if (positive.compareTo(BigDecimal.ZERO) <= 0) {
					value = value.abs().add(BigDecimal.ONE).multiply(new BigDecimal(-1));

				} else {
					value = value.subtract(BigDecimal.ONE);
				}

			} else {

				if (((TCurrencyNumericPlainDocument) document).isPositive() && value.compareTo(BigDecimal.ZERO) == 0) {
					value = oldValue;
					return true;
				}

				value = value.subtract(new BigDecimal(DecimalUtil.calculatePower(10, -(digit_))));
			}
		}

		// Number値設定
		this.setTextNonFormat(String.valueOf(DecimalUtil.roundNum(value, digit_)));

		// ブランク場合、改変以前の値設定
		if (this.getValue().equals("")) {
			this.setTextNonFormat(String.valueOf(DecimalUtil.roundNum(oldValue, digit_)));
		}

		// 小数点位置
		pointIndex = getPointIndex();

		position = flgPositive ? pointIndex : getText().length();

		// フォーカス位置を持ちつづける
		this.setCaretPosition(position);

		return true;
	}

	/**
	 * 小数点位置取得
	 * 
	 * @return 小数点位置
	 */
	protected int getPointIndex() {
		// 小数点位置
		return getBigDecimal().toString().indexOf(DECIMAL_POINT);
	}

	/**
	 * 小数桁数取得
	 * 
	 * @return 小数桁数
	 */
	protected int getDigit() {
		// 小数点位置
		int pointIndex = getPointIndex();
		// テキスト値桁数－小数点位置－1
		return (pointIndex != -1) ? getBigDecimal().toString().length() - pointIndex - 1 : 0;
	}

	/**
	 * テキストフィールドの文字をそのまま返す.
	 * 
	 * @return Plainテキスト
	 */
	public String getPlainText() {
		return getBigDecimal().toString();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellRenderer(jp.co.ais.trans2.common.gui.TTable)
	 */
	@Override
	public TableCellRenderer getCellRenderer(TTable tbl) {
		return new TCurrencyNumricRenderer(this, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	@Override
	public TableCellEditor getCellEditor(TTable tbl) {

		tableCellEditor = true;
		return new TCurrencyNumricEditor(this, tbl);
	}

	/**
	 * スプレッドのフォントを使うの取得
	 * 
	 * @return useTableFont スプレッドのフォントを使う
	 */
	public boolean isUseTableFont() {
		return useTableFont;
	}

	/**
	 * スプレッドのフォントを使うの設定
	 * 
	 * @param useTableFont スプレッドのフォントを使う
	 */
	public void setUseTableFont(boolean useTableFont) {
		this.useTableFont = useTableFont;
	}

	/**
	 * 入力値がゼロかどうか.
	 * 
	 * @return true:ゼロ
	 */
	public boolean isZero() {
		return DecimalUtil.isZero(getBigDecimal());
	}

	/**
	 * 水平方向のalign getter
	 * 
	 * @return 水平方向のalign
	 */
	@Override
	public int getDefaultCellRendererHorizontalAlignment() {
		return SwingConstants.RIGHT;
	}

	/**
	 * 最大値の取得
	 * 
	 * @return maxValue 最大値
	 */
	public BigDecimal getMaxValue() {
		return maxValue;
	}

	/**
	 * 最大値の設定
	 * 
	 * @param maxValue 最大値
	 */
	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;

		if (getDocument() != null) {
			((TCurrencyNumericPlainDocument) getDocument()).maxValue = maxValue;
		}
	}

	/**
	 * 最小値の取得
	 * 
	 * @return minValue 最小値
	 */
	public BigDecimal getMinValue() {
		return minValue;
	}

	/**
	 * 最小値の設定
	 * 
	 * @param minValue 最小値
	 */
	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;

		if (getDocument() != null) {
			((TCurrencyNumericPlainDocument) getDocument()).minValue = minValue;
		}
	}
}
