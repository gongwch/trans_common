package jp.co.ais.trans2.common.gui;

import java.awt.event.*;
import java.math.*;
import java.text.*;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * 時刻フィールド
 * 
 * @author AIS
 */
public class TTimezoneField extends TTextField {

	/** 小数点以下単位 */
	public static final BigDecimal DEFAULT_UNIT = new BigDecimal("0.15");

	/** デフォルト値 */
	public static final String DEFAULT_VALUE = "+00:00";

	/** 小数点基準ポイント */
	protected static final String DECIMAL_POINT = ":";

	/** 指定小数点桁数 */
	protected static final int DIGITS = 2;

	/** 最大値 */
	protected BigDecimal MAX_VALUE = new BigDecimal("23.45");

	/** 最小値 */
	protected BigDecimal MIN_VALUE = new BigDecimal("-23.45");

	/** 小数点以下単位 */
	protected BigDecimal unit = DEFAULT_UNIT;

	/** 指定フォーマット */
	protected DecimalFormat formatter = null;

	/** スプレッドのフォントを使う */
	protected boolean useTableFont = false;

	/** 15分単位 */
	protected boolean minus15Only = true;

	/**
	 * Constructor.
	 */
	public TTimezoneField() {
		super();

		// 初期化
		init();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param i インデックス番号
	 */
	public TTimezoneField(int i) {
		this();

		setRowIndex(i);
	}

	/**
	 * 初期化
	 */
	public void init() {

		TUIManager.setLogicalFont(this);

		super.setImeMode(false);
		super.setHorizontalAlignment(SwingConstants.CENTER);
		this.formatter = new DecimalFormat("00.00");
		this.formatter.setPositivePrefix("+");
		this.formatter.setNegativePrefix("-");

		this.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				String text = getText();
				setText(text);
				return true;
			}
		});

		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent evt) {
				if (isActive()) {
					String text = TTimezoneField.this.getText();

					TTimezoneField.this.setTextNonFormat(text.replace(",", ""));
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
					if (tableCellEditor || !TTimezoneField.this.isFocusOwner() || !TTimezoneField.this.isEditable()) {
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
				if (!TTimezoneField.this.isFocusOwner() || !TTimezoneField.this.isEditable()) {
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
	 * @see jp.co.ais.trans.common.gui.TTextField#createPlainDocument()
	 */
	@Override
	protected TStringPlainDocument createPlainDocument() {
		TStringPlainDocument doc = (TStringPlainDocument) super.createPlainDocument();

		doc.setRegex("[\\+\\-\\.0-9:]");

		return doc;
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
	 * @return true:自分が処理中
	 */
	protected boolean isActive() {
		return TGuiUtil.isActive(TTimezoneField.this);
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
		return 6;
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
	 * フォーマット表示から数値表現へ修正してテキスト値を取得する
	 * 
	 * @return 数値表現文字列
	 */
	@Override
	public String getText() {
		return super.getInputText();
	}

	/**
	 * BigDecimal型で表示数値を取得する
	 * 
	 * @return 数値(時差:10進法)
	 */
	public BigDecimal getNumber() {
		return getNumber(getText());
	}

	/**
	 * BigDecimal型で表示数値を取得する
	 * 
	 * @return 数値(60進法)
	 */
	protected BigDecimal getNativeBigDecimal() {
		String txt = getText();
		return getNativeBigDecimal(txt);
	}

	/**
	 * BigDecimal型で表示数値を取得する
	 * 
	 * @param tz
	 * @return 数値(時差:10進法)
	 */
	public static BigDecimal getNumber(String tz) {
		return getNumber(tz, DEFAULT_UNIT);
	}

	/**
	 * BigDecimal型で表示数値を取得する
	 * 
	 * @param tz
	 * @param unit 最小単位指定
	 * @return 数値(時差:10進法)
	 */
	public static BigDecimal getNumber(String tz, BigDecimal unit) {
		BigDecimal value = getNativeBigDecimal(tz);
		BigDecimal num = remainder(value, unit);
		return DecimalUtil.convert60to10(num);
	}

	/**
	 * BigDecimal型で表示数値を取得する
	 * 
	 * @param txt
	 * @return 数値(60進法)
	 */
	public static BigDecimal getNativeBigDecimal(String txt) {

		if (Util.isNullOrEmpty(txt) || txt.trim().equals("-") || txt.trim().equals("+")) {
			return BigDecimal.ZERO;
		}

		return new BigDecimal(txt.replaceAll(":", "."));
	}

	/**
	 * テキスト設定. フォーマット変換が発生
	 * 
	 * @param text テキスト
	 */
	@Override
	public void setText(String text) {
		// 空白または記号のみの場合はフォーマットせずにテキストをクリア
		if (text == null || "".equals(text.replace("-", "").replace("+", "").replace(".", ""))) {
			super.setText(DEFAULT_VALUE);
			return;
		}

		String ftext = getFormattedString(text);
		super.setText(ftext);
	}

	/**
	 * Number値設定. フォーマット変換が発生<br>
	 * 10進法→60進法変換含み
	 * 
	 * @param value 数値(10進法)
	 */
	public void setNumber(Number value) {
		if (value == null) {
			super.setText(DEFAULT_VALUE);
			return;
		}

		// 10進法→60進法
		BigDecimal num = DecimalUtil.convert10to60(value);
		super.setText(getFormattedString(num));
	}

	/**
	 * @param text
	 * @return フォーマット文字列
	 */
	protected String getFormattedString(String text) {
		try {

			String sign = text.length() >= 1 ? text.substring(0, 1) : "+";

			BigDecimal dec = new BigDecimal(text.replaceAll("[\\+\\-,]", "").replaceAll(":", "."));
			if (sign.equals("-")) {
				dec = dec.negate(); // 符号反転
			}
			return getFormattedString(dec);

		} catch (NumberFormatException ex) {
			// エラーの場合、初期値を返す(無効値とする)
			return DEFAULT_VALUE;
		}
	}

	/**
	 * @param num
	 * @return フォーマット文字列
	 */
	public String getFormattedString(Number num) {

		if (num instanceof BigDecimal) {
			// BigDecimalの場合、60余計部を外す
			num = remainder((BigDecimal) num);

			BigDecimal chk = (BigDecimal) num;

			if (chk.compareTo(MAX_VALUE) == 1 //
				|| chk.compareTo(MIN_VALUE) == -1) {
				return DEFAULT_VALUE;
			}
		}

		String ftext = this.formatter.format(num);
		return ftext.replace(".", ":");
	}

	/**
	 * @param value
	 * @return 余計外す数値
	 */
	public BigDecimal remainder(BigDecimal value) {
		return remainder(value, unit);
	}

	/**
	 * @param value
	 * @param unit
	 * @return 余計外す数値
	 */
	public static BigDecimal remainder(BigDecimal value, BigDecimal unit) {
		BigDecimal num[] = DecimalUtil.separateDecimal(value);
		BigDecimal b = value.subtract(num[1].remainder(unit));

		switch (b.signum()) {
			case 1:
				return calculateUpper(b, unit);
			case -1:
				return calculateLower(b, unit);
		}

		return b;
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
	 * UPとDownイベント
	 * 
	 * @param actionFlg 操作フラグ
	 * @return true:処理した
	 */
	protected boolean upOrDownAction(int actionFlg) {
		// 「.」だけ残した状態でスピンさせると内部でエラーを避ける
		if (this.getText().equals(".") || this.getText().equals(":")) {
			return false;
		}
		// 整数部フラグ
		boolean flgPositive = true;
		// テストの値
		BigDecimal value = this.getNativeBigDecimal();

		// テストの改変以前の値
		BigDecimal oldValue = this.getNativeBigDecimal();

		// JTextField中カレントカーソルの位置：
		int position = this.getCaretPosition();

		this.setTextNonFormat(getFormattedString(value));

		// 小数点位置
		int pointIndex = DIGITS;

		if (position <= pointIndex) {
			position = pointIndex;
			flgPositive = true;
		} else {
			position = getText().length();
			flgPositive = false;
		}

		// 小数桁
		int digit_ = DIGITS;

		// UPイベント
		if (actionFlg == KeyEvent.VK_UP) {
			if (pointIndex == -1 || position <= pointIndex) {
				// 整数部を計算

				value = value.add(BigDecimal.ONE);

			} else {
				// 小数部を計算

				value = value.add(unit);

				// 計算結果の６０進調整
				value = calculateUpper(value);
			}

		} else if (actionFlg == KeyEvent.VK_DOWN) {
			if (pointIndex == -1 || position <= pointIndex) {
				// 整数部を計算

				value = value.subtract(BigDecimal.ONE);

			} else {
				// 小数部を計算

				value = value.subtract(unit);

				// 計算結果の６０進調整
				value = calculateLower(value);
			}
		}

		BigDecimal num = DecimalUtil.roundNum(value, digit_);

		if (num.compareTo(MAX_VALUE) == 1) {
			num = MAX_VALUE;
		} else if (num.compareTo(MIN_VALUE) == -1) {
			num = MIN_VALUE;
		}

		// Number値設定
		this.setTextNonFormat(getFormattedString(num));

		// ブランク場合、改変以前の値設定
		if (this.getValue().equals("")) {
			BigDecimal oldNum = DecimalUtil.roundNum(oldValue, digit_);
			this.setTextNonFormat(getFormattedString(oldNum));
		}

		// 小数点位置
		position = flgPositive ? DIGITS : getText().length();

		// フォーカス位置を持ちつづける
		this.setCaretPosition(position);

		return true;
	}

	/**
	 * @param value
	 * @return ６０進調整
	 */
	protected BigDecimal calculateLower(BigDecimal value) {
		return calculateLower(value, unit);
	}

	/**
	 * @param value
	 * @param unit
	 * @return ６０進調整
	 */
	public static BigDecimal calculateLower(BigDecimal value, BigDecimal unit) {
		// 計算結果の６０進調整
		BigDecimal num[] = DecimalUtil.separateDecimal(value);
		if (num[1].abs().compareTo(DecimalUtil.NUM_60) >= 0) {
			if (num[1].signum() == -1) {
				// マイナス
				num[0] = num[0].subtract(BigDecimal.ONE);
				value = num[0];
			} else {
				// プラス
				num[0] = num[0].add(DecimalUtil.NUM_60).subtract(unit);
				value = num[0];
			}
		}
		return value;
	}

	/**
	 * @param value
	 * @return ６０進調整
	 */
	protected BigDecimal calculateUpper(BigDecimal value) {
		return calculateUpper(value, unit);
	}

	/**
	 * @param value
	 * @param unit
	 * @return ６０進調整
	 */
	public static BigDecimal calculateUpper(BigDecimal value, BigDecimal unit) {
		// 計算結果の６０進調整
		BigDecimal num[] = DecimalUtil.separateDecimal(value);
		if (num[1].abs().compareTo(DecimalUtil.NUM_60) >= 0) {

			if (num[1].signum() == -1) {
				// マイナス
				num[0] = num[0].subtract(DecimalUtil.NUM_60).add(unit);
				value = num[0];
			} else {
				// プラス
				num[0] = num[0].add(BigDecimal.ONE);
				value = num[0];
			}
		}
		return value;
	}

	/**
	 * テキストフィールドの文字をそのまま返す.
	 * 
	 * @return Plainテキスト
	 */
	public String getPlainText() {
		return getText();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellRenderer(jp.co.ais.trans2.common.gui.TTable)
	 */
	@Override
	public TableCellRenderer getCellRenderer(TTable tbl) {
		return new TTimezoneRenderer(this, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	@Override
	public TableCellEditor getCellEditor(TTable tbl) {

		tableCellEditor = true;
		return new TTimezoneEditor(this, tbl);
	}

	/**
	 * 水平方向のalign getter
	 * 
	 * @return 水平方向のalign
	 */
	@Override
	public int getDefaultCellRendererHorizontalAlignment() {
		return SwingConstants.CENTER;
	}

	/**
	 * 15分単位の取得
	 * 
	 * @return minus15Only 15分単位
	 */
	public boolean isMinus15Only() {
		return minus15Only;
	}

	/**
	 * 15分単位の設定
	 * 
	 * @param minus15Only 15分単位
	 */
	public void setMinus15Only(boolean minus15Only) {
		this.minus15Only = minus15Only;

		if (this.minus15Only) {
			unit = new BigDecimal("0.15");
			MAX_VALUE = new BigDecimal("23.45");
			MIN_VALUE = new BigDecimal("-23.45");
		} else {
			unit = new BigDecimal("0.01");
			MAX_VALUE = new BigDecimal("23.59");
			MIN_VALUE = new BigDecimal("-23.59");
		}
	}

}
