package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * 時刻フィールド
 * 
 * @author Yanwei
 */
public class TTimeField extends TTextField {

	/** Constant for the ":" key. */
	protected static final String COLON = ":";

	/** Constant for the "_" key. */
	protected static final String UNDERSCORE = "_";

	/** 時間初期フォーマットHM */
	protected static String INIT_CALENDAR_HM = "__:__";

	/** 初期テキスト */
	protected String initText = INIT_CALENDAR_HM;

	/** ブランク許可 */
	protected boolean isAllowableBlank = true;

	/** 時間フォーマッティング文字列 */
	protected static final String DATE_HM = "HH" + COLON + "mm";

	/** 年月日 */
	protected static final String BASE_YMD = "2000/01/01 ";

	/**
	 * コンストラクタ.<br>
	 */
	public TTimeField() {
		super();
		TUIManager.setLogicalFont(this);
		initComponents();
		initType();
	}

	/**
	 * システム初期化
	 */
	protected void initComponents() {
		setLayout(new GridBagLayout());

		Dimension size = new Dimension(50, 20);
		size = TGuiUtil.correctSize(TGuiUtil.TYPE_CALENDER, size);

		super.setPreferredSize(size);
		super.setMaximumSize(size);
		super.setMinimumSize(size);

		this.setEditable(true);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setImeMode(false);

		// calendar key listener
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {

				if (!TTimeField.this.isFocusOwner() || !TTimeField.this.isEditable()) {
					return;
				}

				// input number allowable
				if (!Character.isDigit(e.getKeyChar()) && !(UNDERSCORE.equals(e.getKeyChar()))) {
					e.consume();
				}

				// インテリジェント入力
				dateFieldActionPerformed(e);
			}

			@Override
			@SuppressWarnings("deprecation")
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == 88) {
					// Ctrl+X
					TTimeField.this.selectAll();
					TTimeField.this.cut();
					TTimeField.this.clearField();
					e.consume();

				} else if (e.getKeyCode() == 86) {
					// Ctrl+V
					TTimeField.this.clear();
					TTimeField.this.paste();

					if (!isAppropriateValue(getPlainText())) {
						// 編集前の有効な値を再設定
						setValue(getOldText());
					}

					e.consume();

				} else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
					// 矢印キーのUpとDownを押し Calendarのフリッパーを実現する
					if (!TTimeField.this.isFocusOwner() || !TTimeField.this.isEditable()) {
						return;
					}

					boolean isSuccess = (e.getKeyCode() == KeyEvent.VK_UP) ? upAction() : downAction();

					if (!isSuccess) {
						e.consume();
					}

				} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE && Util.isNullOrEmpty(getPlainText())) {
					TTimeField.this.clearField();
				}
			}
		});

		// mouse wheel listener
		this.addMouseWheelListener(new MouseWheelListener() {

			public void mouseWheelMoved(MouseWheelEvent e) {
				if (!TTimeField.this.isFocusOwner() || !TTimeField.this.isEditable()) {
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
					boolean isSuccess = !isDown ? upAction() : downAction();

					if (!isSuccess) {
						e.consume();
						break;
					}
				}
			}
		});

		// mouse listener
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getButton() == MouseEvent.BUTTON3) {

					if (!TTimeField.this.isEnabled() || !TTimeField.this.isEditable()) {
						return;
					}

				}
			}
		});

		// calendar focus listener
		this.setInputVerifier(new TInputVerifier() {

			@Override
			@SuppressWarnings("deprecation")
			public boolean verifyCommit(JComponent comp) {

				if (!TTimeField.this.isEnabled() || !TTimeField.this.isEditable()) {
					return true;
				}

				if (!isAppropriateValue(getPlainText())) {
					if (isAllowableBlank) {
						clearField();
					} else {
						// 編集前の有効な値を再設定
						if (Util.isNullOrEmpty(oldText)) {
							setValue(getNowTime());

						} else {
							setValue(oldText);
						}
					}
				}

				return true;
			}
		});

		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				if (Util.isNullOrEmpty(getText())) {
					TTimeField.this.setCaretPosition(0);
					return;
				}

				focusAdjustment();

				selectAll();
			}
		});

		initType();
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TTextField#initTextHelper()
	 */
	@Override
	protected void initTextHelper() {
		// なし
	}

	/**
	 * フォーカス調整
	 */
	protected void focusAdjustment() {
		this.setCaretPosition(5);
	}

	/**
	 * 日付タイプの設定
	 */
	protected void initType() {

		setPlainText(getNowTime());
		this.pushOldText();

	}

	/**
	 * フィールドを未入力状態にする
	 */
	protected void clearField() {
		setPlainText(initText);
	}

	/**
	 * CTRL+V 操作時に、貼り付け文字列は要求に合うかどうかを判断する
	 * 
	 * @param inputValue 対象文字
	 * @return true:貼り付け可能、false:貼り付け不可(無効)
	 */
	protected boolean isAppropriateValue(String inputValue) {
		try {

			if (inputValue.indexOf(UNDERSCORE) != -1) {
				return false;
			}

			Date inputDate = null;

			if (inputValue.length() != 5) {
				return false;
			}
			if (inputValue.indexOf(COLON) != 2) {
				return false;
			}

			inputDate = DateUtil.toYMDHMDate(BASE_YMD + inputValue);

			if (inputDate == null) {
				return false;
			}

			return true;

		} catch (ParseException e) {
			// 入力日付文字が不正な場合は、貼り付け不可
			return false;
		}
	}

	/**
	 * インテリジェント入力
	 * 
	 * @param e KeyEvent
	 */
	protected void dateFieldActionPerformed(KeyEvent e) {

		// JTextField中カレントカーソルの位置：フォーカスの位置取得
		int position = this.getCaretPosition();

		// 入力テキスト
		String subInputText = "0";
		if (position != 0) {
			// フォーカスが当ってる箇所までの文字を取得
			subInputText = getPlainText().substring(0, position);
		}

		if (!isNum(subInputText) && isInputNumber(e)) {
			this.setCaretPosition(0);
		}

		// 入力テキスト
		String inputText = getPlainText();

		// カレントカーソルの位置 最後の1位の処理
		if (isInputNumber(e) && position == initText.length()) {
			if (this.getSelectedText() == null) {
				e.consume();
				return;
			}
		}

		// DELETEキー押下時
		if (!isInputNumber(e) && e.getKeyChar() == KeyEvent.VK_DELETE) {
			setPlainText(inputText.substring(0, position) + initText.substring(position, initText.length()));
			this.setCaretPosition(position);
		}

		// スペース(SPACE)キーを押して、ポップアップ表示は無し
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			e.consume();
			return;
		}

		// BakcSpace key press
		if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
			setPlainText(inputText.substring(0, position) + initText.substring(position, initText.length()));
			this.setCaretPosition(position);
			return;
		}

		// Enter key press
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			return;
		}

		// Tab key press
		if (e.getKeyChar() == KeyEvent.VK_TAB) {
			return;
		}

		// 範囲選択時
		if (getSelectedText() != null) {
			position = getSelectionStart();
		}

		// 前に_が含まれるなら、そのそも無効
		if (position != 0 && inputText.substring(0, position).indexOf(UNDERSCORE) != -1) {
			e.consume();
			return;
		}

		// 入力日付をチェックする
		if (e.getKeyChar() < KeyEvent.VK_0 || e.getKeyChar() > KeyEvent.VK_9) {
			e.consume();
		}

		// Hをチェックする
		if (position == 0) {
			if (e.getKeyChar() > KeyEvent.VK_2) {
				e.consume();
				return;
			}
		}

		if (position == 1) {
			if (inputText.substring(0, 1).equals("2")) {
				if (e.getKeyChar() > KeyEvent.VK_3) {
					e.consume();
					return;
				}
			} else {
				if (e.getKeyChar() > KeyEvent.VK_9) {
					e.consume();
					return;
				}
			}
		}

		// :
		if (position == 2) {
			this.select(position + 1, position + 1);
			e.consume();
			return;
		}

		// Mをチェックする
		if (position == 3) {
			if (e.getKeyChar() > KeyEvent.VK_5) {
				e.consume();
				return;
			}
		}
		if (position == 4) {
			if (e.getKeyChar() > KeyEvent.VK_9) {
				e.consume();
				return;
			}
		}

		if (position == 5) {
			e.consume();
			return;
		}

		// input number allowable
		if (!Character.isDigit(e.getKeyChar())) {
			e.consume();
			return;
		}

		setPlainText(inputText.substring(0, position) + e.getKeyChar()
			+ initText.substring(position + 1, initText.length()));
		e.consume();

		if (position == 1) {
			this.select(position + 2, position + 2);
		} else {
			this.select(position + 1, position + 1);
		}

		return;
	}

	/**
	 * 指定された文字列が数値に変換かどうかの判断を行う
	 * 
	 * @param subInputText
	 * @return true:変換可能
	 */
	protected boolean isNum(String subInputText) {
		return Character.isDigit(subInputText.charAt(0));
	}

	/**
	 * 入力したのが数字であるか否かを判断する
	 * 
	 * @param e
	 * @return true:変換可能
	 */
	protected boolean isInputNumber(KeyEvent e) {
		return Character.isDigit(e.getKeyChar());
	}

	/**
	 * DOWNイベント
	 * 
	 * @return true:処理した
	 */
	protected boolean downAction() {

		String inputText = setupSpinText();

		Date dt = getDateValue();
		if (dt == null) {
			return false;
		}

		int hour = DateUtil.getHour(dt);

		// JTextField中カレントカーソルの位置：
		int position = this.getCaretPosition();

		int intHour = 0;
		int intMinute = 0;

		// DOWN key press
		// 時
		if (position >= 0 && position <= 2) {
			// 時を選ぶ
			this.select(0, 2);

			intHour = Integer.parseInt(this.getSelectedText()) - 1;

			if (intHour == -1) {
				setPlainText("23" + inputText.substring(2, inputText.length()));
				this.select(0, 2);
				return false;
			}

			if (intHour < 10) {
				setPlainText("0" + intHour + inputText.substring(2, inputText.length()));
			} else {
				setPlainText(intHour + inputText.substring(2, inputText.length()));
			}

			this.select(0, 2);
		}

		// 分
		if (position > 2 && position <= 5) {
			// 分を選ぶ
			this.select(3, 5);

			intMinute = Integer.parseInt(this.getSelectedText()) - 1;

			if (intMinute == -1) {
				if (hour == 0) {
					setPlainText(getHM("23", "59"));
				} else {
					setPlainText(getHM((hour - 1), "59"));
				}

				this.select(3, 5);

				return false;
			}

			setPlainText(getHM(hour, intMinute));

			this.select(3, 5);
		}

		return true;
	}

	/**
	 * UPイベント
	 * 
	 * @return true:処理した
	 */
	protected boolean upAction() {

		String inputText = setupSpinText();

		Date dt = getDateValue();
		if (dt == null) {
			return false;
		}

		int hour = DateUtil.getHour(dt);

		// JTextField中カレントカーソルの位置：
		int position = this.getCaretPosition();

		int intHour = 0;
		int intMinute = 0;

		// 時
		if (position >= 0 && position <= 2) {
			// 時を選ぶ
			this.select(0, 2);

			intHour = Integer.parseInt(this.getSelectedText()) + 1;

			// 時間の最大値は24
			if (intHour == 24) {
				setPlainText("00" + inputText.substring(2, inputText.length()));
				this.select(0, 2);
				return false;
			}

			if (intHour < 10) {
				setPlainText("0" + intHour + inputText.substring(2, inputText.length()));
			} else {
				setPlainText(intHour + inputText.substring(2, inputText.length()));
			}

			this.select(0, 2);
		}

		// 分
		if (position > 2 && position <= 5) {
			// 分を選ぶ
			this.select(3, 5);

			intMinute = Integer.parseInt(this.getSelectedText()) + 1;

			// 時間の最大値は24
			if (intMinute == 60) {

				if (hour == 23) {
					setPlainText(getHM("00", "00"));
				} else {
					setPlainText(getHM((hour + 1), "00"));
				}

				this.select(3, 5);

				return false;
			}

			setPlainText(getHM(hour, intMinute));

			this.select(3, 5);
		}

		return true;
	}

	/**
	 * 時分秒のテキストを取得する(タイプ別)
	 * 
	 * @param hour 時
	 * @param min 分
	 * @return 時分秒テキスト
	 */
	protected String getHM(Object hour, Object min) {

		String strHour = String.valueOf(hour);
		String strMin = String.valueOf(min);

		if (strHour.length() == 1) {
			strHour = "0" + strHour;
		}

		if (strMin.length() == 1) {
			strMin = "0" + strMin;
		}

		return strHour + COLON + strMin;
	}

	/**
	 * スピン用のテキストを取得する
	 * 
	 * @return テキスト
	 */
	protected String setupSpinText() {

		String inputText = getPlainText();

		// _ 含まれるかどうかチェック
		int unscIndex = inputText.indexOf(UNDERSCORE);

		if (unscIndex != -1) {
			// 途中まで入力されていれば、それを利用
			int hour = 0;
			int minute = 0;

			// JTextField中カレントカーソルの位置：
			int position = this.getCaretPosition();

			if (unscIndex <= 2) {
				hour = 0;
				minute = 0;
				position = 2;

			} else {
				hour = Integer.parseInt(inputText.substring(0, 2));
				minute = 0;
				position = 5;
			}

			setHMValue(hour, minute);
			setCaretPosition(position);

			inputText = this.getText();
		}

		return inputText;
	}

	/**
	 * キーボード操作を実行する
	 * 
	 * @param key Key
	 */
	protected void keyPress(int key) {
		try {
			Robot robot = new Robot();
			robot.keyPress(key);
			robot.keyRelease(key);
		} catch (AWTException e1) {
			ClientErrorHandler.handledException(e1);
		}
	}

	/**
	 * タイプに依存した現在時間文字列を取得する（フォーマッティング）
	 * 
	 * @return 現在時刻文字列
	 */
	protected String getNowTime() {
		return DateUtil.toHMString(new Date());
	}

	/**
	 * ブランク入力許可状態
	 * 
	 * @param b true:許可
	 */
	public void setAllowableBlank(boolean b) {

		this.isAllowableBlank = b;

		if (getDateValue() == null) {

			if (this.isAllowableBlank) {
				clearField();
			} else {
				setPlainText(getNowTime());
			}

			this.pushOldText();
		}
	}

	/**
	 * ブランク入力許可状態
	 * 
	 * @return true:許可
	 */
	public boolean isAllowableBlank() {

		return this.isAllowableBlank;
	}

	/**
	 * 日付コントロール中の値を取得する
	 * 
	 * @return String
	 */
	@Override
	public String getText() {
		String txt = getPlainText();
		if (Util.isNullOrEmpty(txt) || !isAppropriateValue(txt)) {
			return "";
		}

		return txt;
	}

	/**
	 * テキストフィールドの文字をそのまま返す.
	 * 
	 * @return Plainテキスト
	 */
	public String getPlainText() {
		return super.getText();
	}

	/**
	 * テキスト設定
	 * 
	 * @param text 時分(HH:MM)
	 */
	@Override
	public void setText(String text) {

		if (isAppropriateValue(text)) {
			setPlainText(text);

		} else if (isAllowableBlank) {
			clearField();
		}
	}

	/**
	 * テキストにそのままの文字を反映する.
	 * 
	 * @param text テキスト
	 */
	public void setPlainText(String text) {
		super.setText(text);
	}

	/**
	 * 値の設定
	 * 
	 * @param hour 時
	 * @param minute 分
	 */
	protected void setHMValue(int hour, int minute) {

		String strHour = "";
		String strMinute = "";

		// 時
		if (hour < 10) {
			strHour = "0" + String.valueOf(hour);
		} else {
			strHour = String.valueOf(hour);
		}

		// 分
		if (minute < 10) {
			strMinute = "0" + String.valueOf(minute);
		} else {
			strMinute = String.valueOf(minute);
		}

		setPlainText(strHour + COLON + strMinute);
	}

	/**
	 * 値の設定
	 */
	public void setEmptyValue() {

		if (this.isAllowableBlank) {
			clearField();
			return;
		}
	}

	/**
	 * テキストフィールドから日付を取得する.
	 * 
	 * @return 日付
	 */
	protected Date getDateValue() {
		return getDateValue(getPlainText());
	}

	/**
	 * 指定テキストフィールドから日付を取得する.
	 * 
	 * @param text 日付テキスト
	 * @return 日付
	 */
	protected Date getDateValue(String text) {
		try {

			return DateUtil.toYMDHMDate(BASE_YMD + text);

		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 値を設定する。
	 * 
	 * @param hm 値(HH:mm)
	 */
	public void setValue(String hm) {
		try {
			// HH:mm 形式以外はエラーとする。
			if (hm.length() != 5) {
				return;
			}

			Date date = null;
			date = DateUtil.toYMDHMDate(BASE_YMD + hm);

			this.setText(DateUtil.toHMString(date));

		} catch (ParseException e) {
			return;
		}
	}

	/**
	 * 値を取得する。（表示文字）
	 */
	@Override
	public String getValue() {
		return this.getText();
	}

	@Override
	public int getDefaultCellRendererHorizontalAlignment() {
		return SwingConstants.CENTER;
	}

	@Override
	public TableCellRenderer getCellRenderer(TTable tbl) {
		return new TTimefieldRenderer(this, tbl);
	}

	@Override
	public TableCellEditor getCellEditor(TTable tbl) {
		tableCellEditor = true;
		return new TTimefieldEditor(this, tbl);
	}

}
