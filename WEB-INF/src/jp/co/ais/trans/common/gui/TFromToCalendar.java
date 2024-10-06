package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.util.*;

import javax.swing.*;

/**
 * From～Toのカレンダー
 */
public class TFromToCalendar extends TPanel {

	/** ラベル */
	protected TLabel lblLabel;

	/** From */
	protected TPopupCalendar dtFrom;

	/** ～ラベル */
	protected TLabel lblKARA;

	/** To */
	protected TPopupCalendar dtTo;

	/** */
	protected int type = TPopupCalendar.TYPE_YMD;

	/**
	 * コンストラクタ
	 */
	public TFromToCalendar() {
		super();

		initComponents();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param type カレンダータイプ
	 */
	public TFromToCalendar(int type) {
		this.type = type;
	}

	/**
	 * 初期化.
	 */
	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		lblLabel = createLabelField();
		dtFrom = createCalendarField();
		lblKARA = createLabelField();
		dtTo = createCalendarField();

		setLayout(new GridBagLayout());

		// ラベル
		lblLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLabel.setLabelFor(dtFrom);
		lblLabel.setText("Label"); // ダミー文字

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(lblLabel, gridBagConstraints);

		// FROM
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 3, 0, 0);
		add(dtFrom, gridBagConstraints);

		// ～ラベル
		lblKARA.setHorizontalAlignment(SwingConstants.CENTER);
		lblKARA.setLabelFor(dtTo);
		lblKARA.setLangMessageID("～");

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 2, 0, 2);
		add(lblKARA, gridBagConstraints);

		// TO
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		add(dtTo, gridBagConstraints);
	}

	/**
	 * ラベルフィールド生成
	 * 
	 * @return Fromフィールド
	 */
	protected TLabel createLabelField() {
		return new TLabel();
	}

	/**
	 * カレンダーフィールド生成
	 * 
	 * @return Fromフィールド
	 */
	protected TPopupCalendar createCalendarField() {
		return new TPopupCalendar(type);
	}

	/**
	 * コントロール番号設定
	 * 
	 * @param number 番号
	 */
	public void setTabControlNo(int number) {
		dtFrom.setTabControlNo(number);
		dtTo.setTabControlNo(number);
	}

	/**
	 * ラベル文字設定
	 * 
	 * @param text ラベル文字
	 */
	public void setLabelText(String text) {
		lblLabel.setLangMessageID(text);
		lblLabel.setText(text);
	}

	/**
	 * 編集状態変更
	 * 
	 * @param isEdit true:編集可、false:編集不可
	 */
	public void setEditable(boolean isEdit) {
		dtFrom.setEditable(isEdit);
		dtTo.setEditable(isEdit);
	}

	/**
	 * 操作状態変更
	 * 
	 * @param isEnabled true:操作可、false:操作不可
	 */
	public void setEnabled(boolean isEnabled) {
		dtFrom.setEnabled(isEnabled);
		dtTo.setEnabled(isEnabled);
	}

	/**
	 * ラベルの表示/非表示切替
	 * 
	 * @param isVisible true:表示、false:非表示
	 */
	public void setLabelVisible(boolean isVisible) {
		lblLabel.setVisible(isVisible);
	}

	/**
	 * ラベルの内容の X 軸に沿った配置方法を設定します。
	 * 
	 * @param alignment SwingConstants定数
	 */
	public void setLabelHAlignment(int alignment) {
		lblLabel.setHorizontalAlignment(alignment);
	}

	/**
	 * ラベルコンポーネント取得
	 * 
	 * @return ラベルコンポーネント
	 */
	public TLabel getLabel() {
		return lblLabel;
	}

	/**
	 * FROMカレンダーコンポーネント取得
	 * 
	 * @return FROMカレンダーコンポーネント
	 */
	public TPopupCalendar getFromCalendar() {
		return dtFrom;
	}

	/**
	 * TOカレンダーコンポーネント取得
	 * 
	 * @return TOカレンダーコンポーネント
	 */
	public TCalendar getToCalendar() {
		return dtTo;
	}

	/**
	 * From値取得
	 * 
	 * @return Date
	 */
	public Date getFromDate() {
		return dtFrom.getValue();
	}

	/**
	 * From値設定
	 * 
	 * @param date Date
	 */
	public void setFromDate(Date date) {
		dtFrom.setValue(date);
	}

	/**
	 * To値取得
	 * 
	 * @return Date
	 */
	public Date getToDate() {
		return dtTo.getValue();
	}

	/**
	 * To値設定
	 * 
	 * @param date Date
	 */
	public void setToDate(Date date) {
		dtTo.setValue(date);
	}

	/**
	 * 入力日付の「FROM <= TO」を比較する.
	 * 
	 * @return 「FROM <= TO」または、どちらかがブランクならばtrue.
	 */
	public boolean isSmallerFrom() {
		Date fromDate = dtFrom.getValue();
		Date toDate = dtTo.getValue();

		if (fromDate == null || toDate == null) {
			return true;
		}

		return fromDate.compareTo(toDate) <= 0;
	}

	/**
	 * 入力値をクリアする
	 */
	public void clear() {
		this.dtFrom.setValue(null);
		this.dtTo.setValue(null);
	}

	/**
	 * @see javax.swing.JComponent#requestFocus()
	 */
	@Override
	public void requestFocus() {
		this.dtFrom.requestFocus();
	}

	/**
	 * @see javax.swing.JComponent#requestFocusInWindow()
	 */
	@Override
	public boolean requestFocusInWindow() {
		return this.dtFrom.requestFocusInWindow();
	}
}
