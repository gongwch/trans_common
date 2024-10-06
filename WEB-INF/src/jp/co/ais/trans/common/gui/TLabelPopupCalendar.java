package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;

/**
 * Label + TPopupCalendar複合アイテム.<br>
 * TPanelに、タブ順、メッセージIDインターフェイスを追加. <br>
 * <ol>
 * <li>label TLabel
 * <li>calendar TPopupCalendar
 * </ol>
 */
public class TLabelPopupCalendar extends TPanel implements TInterfaceTabControl, ILabelField {

	/** YYYY表示タイプ */
	public static final int TYPE_YYYY = 1;

	/** YY表示タイプ */
	public static final int TYPE_YY = 2;

	/** ラベル */
	protected TLabel label;

	/** カレンダー */
	protected TPopupCalendar calendar;

	/**
	 * デフォルトコンストラクター.<br>
	 * 年月日タイプで構築
	 */
	public TLabelPopupCalendar() {
		this(TPopupCalendar.TYPE_YMD);
	}

	/**
	 * コンストラクタ.Label+Calendar<br>
	 * カレンダータイプ(TPopupCalendar.TYPE_XXX)を指定.
	 * 
	 * @param calendarType カレンダータイプ(TPopupCalendar.TYPE_XXX)
	 */
	public TLabelPopupCalendar(int calendarType) {
		super();
		initComponents(calendarType, TYPE_YYYY);
	}

	/**
	 * コンストラクタ.Label+Calendar<br>
	 * カレンダータイプ(TPopupCalendar.TYPE_XXX)を指定.<br>
	 * 年表示タイプタイプ(TYPE_YYYY or TYPE_YY)を指定.<br>
	 * 
	 * @param calendarType カレンダータイプ(TPopupCalendar.TYPE_XXX)
	 * @param yearType 年タイプ(TYPE_YYYY or TYPE_YY)
	 */
	public TLabelPopupCalendar(int calendarType, int yearType) {
		super();
		initComponents(calendarType, yearType);
	}

	/**
	 * カレンダータイプ設定
	 * 
	 * @param calendarType カレンダータイプ
	 */
	public void setCalendarType(int calendarType) {
		calendar.setCalendarType(calendarType);
		this.setPanelSize();
	}

	/**
	 * カレンダータイプ
	 * 
	 * @return カレンダータイプ
	 */
	public int getCalendarType() {
		return this.calendar.getCalendarType();
	}

	/**
	 * 画面構築
	 * 
	 * @param type カレンダータイプ
	 * @param yearType 年タイプ
	 */
	protected void initComponents(int type, int yearType) {

		label = createLabel();
		calendar = (yearType == TYPE_YY) ? createYYCalendar(type) : createCalendar(type);

		setLayout(new GridBagLayout());

		label.setLabelFor(calendar);
		label.setText("TLabelCalendar");

		TGuiUtil.setComponentSize(label, new Dimension(110, 20));

		label.setHorizontalAlignment(SwingConstants.RIGHT);
		add(label, new GridBagConstraints());

		// カレンダーのサイズは自動
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(calendar, gridBagConstraints);

		setPanelSize();
	}

	/**
	 * ラベルの生成
	 * 
	 * @return ラベル
	 */
	protected TLabel createLabel() {
		return new TLabel();
	}

	/**
	 * カレンダーの生成
	 * 
	 * @param type カレンダータイプ
	 * @return カレンダー
	 */
	protected TPopupCalendar createCalendar(int type) {
		return new TPopupCalendar(type);
	}

	/**
	 * カレンダーの生成
	 * 
	 * @param type カレンダータイプ
	 * @return カレンダー
	 */
	protected TPopupCalendar createYYCalendar(int type) {
		return new TYYCalendar(type);
	}

	// itemの幅 ******************************************

	/**
	 * label 幅の設定.
	 * 
	 * @param size 幅
	 */
	public void setLabelSize(int size) {
		Dimension labelSize = this.label.getPreferredSize();
		labelSize.setSize(size, labelSize.getHeight());
		TGuiUtil.setComponentSize(this.label, labelSize);
		this.setPanelSize();
	}

	/**
	 * label 幅の取得
	 * 
	 * @return 幅
	 */
	public int getLabelSize() {
		return (int) this.label.getPreferredSize().getWidth();
	}

	/**
	 * カレンダーは自動でサイズ調整する為、特殊ケースを除き利用しないこと.<br>
	 * calendar 幅の設定
	 * 
	 * @deprecated 利用しない
	 * @param width 幅
	 */
	public void setCalendarSize(int width) {
		Dimension calendarSize = this.calendar.getPreferredSize();
		calendarSize.setSize(width, calendarSize.getHeight());

		this.calendar.setPreferredSize(calendarSize);
		this.calendar.setMaximumSize(calendarSize);
		this.setPanelSize();
	}

	/**
	 * calendar 幅の取得
	 * 
	 * @return 幅
	 */
	public int getCalendarSize() {
		return (int) this.calendar.getPreferredSize().getWidth();
	}

	/**
	 * panelサイズを、並んだアイテムの横幅の合計にする
	 */
	public void setPanelSize() {
		Dimension size = this.getPreferredSize();
		size.setSize(getLabelSize() + getCalendarSize() + 5, size.getHeight());
		TGuiUtil.setComponentSize(this, size);
	}

	// アイテムの寄せ *********************************************

	/**
	 * labelの寄せ
	 * 
	 * @deprecated
	 * @param align 左:0 中央:1 右:2
	 */
	public void setLabelHAlignment(int align) {
		TGuiUtil.setLabelHorizonalAlignment(this.label, align);
	}

	// property *********************************************

	/**
	 * Label getter
	 * 
	 * @return ラベル
	 */
	public TLabel getLabel() {
		return this.label;
	}

	/**
	 * popupCarendar getter.
	 * 
	 * @return カレンダー
	 */
	public TPopupCalendar getCalendar() {
		return this.calendar;
	}

	/**
	 * ブランク入力許可状態
	 * 
	 * @param b true:許可
	 */
	public void setAllowableBlank(boolean b) {

		this.calendar.setAllowableBlank(b);
	}

	/**
	 * ブランク入力許可状態
	 * 
	 * @return true:許可
	 */
	public boolean isAllowableBlank() {

		return this.calendar.isAllowableBlank();
	}

	// 文字設定. *********************************************

	/**
	 * labelの表示Textを取得する.
	 * 
	 * @return String
	 */
	public String getLabelText() {
		return this.label.getText();
	}

	/**
	 * 表示するTextをlabelに設定する.
	 * 
	 * @param text 設定文字列
	 */
	public void setLabelText(String text) {
		this.label.setText(text);
	}

	/**
	 * labelのフォントを設定する.
	 * 
	 * @param font フォント
	 */
	public void setLabelFont(Font font) {
		this.label.setFont(font);
	}

	/**
	 * labelのフォントを取得する.
	 * 
	 * @return フォント
	 */
	public Font getLabelFont() {
		return this.label.getFont();
	}

	/**
	 * カレンダーフィールドに日付を設定する.
	 * 
	 * @param value 設定日付
	 */
	public void setValue(Date value) {
		this.calendar.setValue(value);
	}

	/**
	 * テキストフィールドから日付を取得する.
	 * 
	 * @return 日付
	 */
	public Date getValue() {
		return this.calendar.getValue();
	}

	/**
	 * 指定月の末日を返す(年、年月表示用)
	 * 
	 * @return 日付
	 */
	public Date getLastDate() {
		return DateUtil.getLastDate(getValue());
	}

	/**
	 * 入力されている年を取得
	 * 
	 * @return 年
	 */
	public int getYear() {
		return this.calendar.getYear();
	}

	/**
	 * テキストフィールドに入力値が有るかどうか
	 * 
	 * @return true:入力値有り
	 */
	public boolean isEmpty() {
		return this.calendar.isEmpty();
	}

	// interface 実装 ***************************************************

	/**
	 * ラベルの表示文字のメッセージIDを取得する.
	 * 
	 * @see TInterfaceLangMessageID#getLangMessageID()
	 */
	public String getLangMessageID() {
		return this.label.getLangMessageID();
	}

	/**
	 * ラベルの表示文字のメッセージIDを設定する.
	 * 
	 * @see TInterfaceLangMessageID#setLangMessageID(java.lang.String)
	 */
	public void setLangMessageID(String langMessageID) {
		this.label.setLangMessageID(langMessageID);
	}

	/**
	 * タブ移動順番号をテキストフィールドから取得する.
	 * 
	 * @see TInterfaceTabControl#getTabControlNo()
	 */
	public int getTabControlNo() {
		return this.calendar.getTabControlNo();
	}

	/**
	 * タブ移動順番号をテキストフィールドに設定する.
	 * 
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		this.calendar.setTabControlNo(no);
	}

	/**
	 * タブ移動可 不可
	 * 
	 * @return true: 可、false: 不可
	 */
	public boolean isTabEnabled() {
		return this.calendar.isTabEnabled();
	}

	/**
	 * タブ移動順実装
	 * 
	 * @see TInterfaceTabControl#setTabEnabled(boolean)
	 */
	public void setTabEnabled(boolean bool) {
		this.calendar.setTabEnabled(bool);
	}

	// enable 有効／無効操作制御 ************************************************

	/**
	 * PopupCalendar enabled property.
	 * 
	 * @param bool false:操作不可
	 */
	public void setCalendarEnabled(boolean bool) {
		this.calendar.setEnabled(bool);
	}

	/**
	 * PopupCalendar enabled property.
	 * 
	 * @return false:操作不可
	 */
	public boolean isCalendarEnabled() {
		return this.calendar.isEnabled();
	}

	/**
	 * Label enabled property.
	 * 
	 * @param bool false:操作不可
	 */
	public void setLabelEnabled(boolean bool) {
		this.label.setEnabled(bool);
	}

	/**
	 * Label enabled property.
	 * 
	 * @return false:操作不可
	 */
	public boolean isLabelEnabled() {
		return this.label.isEnabled();
	}

	/**
	 * panel enabled
	 * 
	 * @param bool false:操作不可
	 */
	public void setEnabled(boolean bool) {
		this.calendar.setEnabled(bool);
		super.setEnabled(bool);
	}

	// editable 編集可／不可操作制御 ************************************************

	/**
	 * calendar editable property.
	 * 
	 * @param bool false:編集不可
	 */
	public void setEditable(boolean bool) {
		this.calendar.setEditable(bool);
	}

	/**
	 * calendar editable property.
	 * 
	 * @return false:編集不可
	 */
	public boolean isEditable() {
		return this.calendar.isEditable();
	}

	/**
	 * calendarにフォーカスを合わせる. calendar.requestFocus()
	 */
	public void requestTextFocus() {
		this.calendar.requestFocus();
	}

	/**
	 * calendarにフォーカスを合わせる. calendar.requestFocus()
	 */
	@Override
	public void requestFocus() {
		this.calendar.requestFocus();
	}

	/**
	 * popup calendarへFocusListenerを登録する.
	 * 
	 * @param l リスナ
	 */
	public synchronized void addFocusListener(FocusListener l) {
		super.addFocusListener(l);

		this.calendar.addFocusListener(l);
	}

	/**
	 * popup calendarからFocusListenerを削除する.
	 * 
	 * @param l リスナ
	 */
	public synchronized void removeFocusListener(FocusListener l) {
		this.calendar.removeFocusListener(l);

		super.removeFocusListener(l);
	}

	/**
	 * 入力最小日付
	 * 
	 * @param dt 最小日付
	 */
	public void setMinimumDate(Date dt) {
		this.calendar.setMinimumDate(dt);
	}

	/**
	 * 入力最大日付
	 * 
	 * @param dt 最大日付
	 */
	public void setMaximumDate(Date dt) {
		this.calendar.setMaximumDate(dt);
	}

	/**
	 * @see TPopupCalendar#setInputVerifier(InputVerifier)
	 */
	public void setInputVerifier(InputVerifier inputVerifier) {
		this.calendar.setInputVerifier(inputVerifier);
	}

	/**
	 * @return true:変更されている
	 * @see TPopupCalendar#isValueChanged()
	 */
	public boolean isValueChanged() {
		return this.calendar.isValueChanged();
	}

	/**
	 * @return true:変更されている
	 * @see TPopupCalendar#isValueChanged2()
	 */
	public boolean isValueChanged2() {

		return this.calendar.isValueChanged2();
	}

	/**
	 * 入力値をクリアする
	 */
	public void clear() {
		this.calendar.setValue(null);
	}
}
