package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * TPanelに、タブ順、メッセージIDインターフェイスを追加した複合item. <br>
 * 子item.
 * <ol>
 * <li>label TLabel
 * <li>combo TComboBox
 * </ol>
 */
public class TLabelComboBox extends TPanel implements TInterfaceTabControl {

	/** ラベル */
	protected TLabel label;

	/** コンボボックス */
	protected TComboBox combo;

	/** アイテムのサイズ */
	private Dimension labelSize = null;

	/** コンボボックスのサイズ */
	private Dimension comboSize = null;

	/**
	 * コンストラクタ
	 */
	public TLabelComboBox() {
		super();
		initComponents();
	}

	/**
	 * 初期構築
	 */
	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		label = createLabel();
		combo = createComboBox();

		setLayout(new GridBagLayout());

		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setLabelFor(combo);
		label.setText("TLabelComboBox");

		TGuiUtil.setComponentSize(label, new Dimension(120, 20));
		add(label, new GridBagConstraints());

		combo.setModel(new DefaultComboBoxModel());
		TGuiUtil.setComponentSize(combo, new Dimension(160, 20));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(combo, gridBagConstraints);

		labelSize = this.label.getPreferredSize();
		comboSize = this.combo.getPreferredSize();

		setPanelSize();
	}

	/**
	 * ラベルを生成
	 * 
	 * @return ラベル
	 */
	protected TLabel createLabel() {
		return new TLabel();
	}

	/**
	 * コンボボックスを生成
	 * 
	 * @return コンボボックス
	 */
	protected TComboBox createComboBox() {
		return new TComboBox();
	}

	// getter. **************************************************

	/**
	 * label getter.
	 * 
	 * @return label
	 */
	public TLabel getLabel() {
		return this.label;
	}

	/**
	 * combo getter.
	 * 
	 * @return comboBox
	 */
	public TComboBox getComboBox() {
		return this.combo;
	}

	// アイテムの幅 ***************************************************

	/**
	 * panelサイズを、並んだアイテムの横幅の合計にする
	 */
	private void setPanelSize() {
		TGuiUtil.setComponentSize(this, new Dimension(
			(int) (this.labelSize.getWidth() + this.comboSize.getWidth()) + 5, this.getPreferredSize().height));
	}

	/**
	 * label 幅の設定.
	 * 
	 * @param size 幅
	 */
	public void setLabelSize(int size) {
		this.labelSize.setSize(size, this.labelSize.getHeight());
		TGuiUtil.setComponentSize(this.label, this.labelSize);
		this.setPanelSize();
	}

	/**
	 * button 幅の取得
	 * 
	 * @return 幅
	 */
	public int getLabelSize() {
		return (int) this.labelSize.getWidth();
	}

	/**
	 * combobox 幅の設定
	 * 
	 * @param size 幅
	 */
	public void setComboSize(int size) {
		this.comboSize.setSize(size, this.comboSize.getHeight());
		TGuiUtil.setComponentSize(this.combo, this.comboSize);
		this.setPanelSize();
	}

	/**
	 * combobox 幅の取得
	 * 
	 * @return 幅
	 */
	public int getComboSize() {
		return (int) this.comboSize.getWidth();
	}

	// アイテムの寄せ **********************************************

	/**
	 * labelの寄せ
	 * 
	 * @deprecated
	 * @param align 0:左 1:中央 2:右
	 */
	public void setLabelHAlignment(int align) {
		TGuiUtil.setLabelHorizonalAlignment(this.label, align);
	}

	// 文字設定 ***************************************************

	/**
	 * ラベル文字設定
	 * 
	 * @param text ラベル文字
	 */
	public void setLabelText(String text) {
		this.label.setText(text);
	}

	/**
	 * ラベル文字取得
	 * 
	 * @return ラベル文字
	 */
	public String getLabelText() {
		return this.label.getText();
	}

	/**
	 * ラベルフォント設定
	 * 
	 * @param font フォント
	 */
	public void setLabelFont(Font font) {
		this.label.setFont(font);
	}

	/**
	 * ラベルフォント取得
	 * 
	 * @return フォント
	 */
	public Font getLabelFont() {
		return this.label.getFont();
	}

	// interface 実装 ********************************************

	/**
	 * ラベルの表示文字のメッセージIDを取得する.
	 * 
	 * @see TInterfaceLangMessageID#getLangMessageID()
	 */
	@Override
	public String getLangMessageID() {

		return this.label.getLangMessageID();
	}

	/**
	 * ラベルの表示文字のメッセージIDを設定する.
	 * 
	 * @see TInterfaceLangMessageID#setLangMessageID(java.lang.String)
	 */
	@Override
	public void setLangMessageID(String langMessageID) {

		this.label.setLangMessageID(langMessageID);
	}

	/**
	 * @see TInterfaceTabControl#getTabControlNo()
	 */
	public int getTabControlNo() {
		return this.combo.getTabControlNo();
	}

	/**
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		this.combo.setTabControlNo(no);
	}

	/**
	 * 
	 */
	public boolean isTabEnabled() {
		return this.combo.isTabEnabled();
	}

	/**
	 * @see TInterfaceTabControl#setTabEnabled(boolean)
	 */
	public void setTabEnabled(boolean bool) {
		this.combo.setTabEnabled(bool);
	}

	// enable 有効／無効操作制御 ************************************************

	/**
	 * ComboBox enabled property
	 * 
	 * @param bool false:操作不可
	 */
	public void setComboEnabled(boolean bool) {
		this.combo.setEnabled(bool);
	}

	/**
	 * ComboBox enabled property
	 * 
	 * @return false:操作不可
	 */
	public boolean isComboEnabled() {
		return this.combo.isEnabled();
	}

	/**
	 * Label enabled property
	 * 
	 * @param bool false:操作不可
	 */
	public void setLabelEnabled(boolean bool) {
		this.label.setEnabled(bool);
	}

	/**
	 * Label enabled property
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
	@Override
	public void setEnabled(boolean bool) {
		// this.label.setEnabled(bool);
		this.combo.setEnabled(bool);
		super.setEnabled(bool);
	}

	// editable 編集可／不可操作制御 ************************************************

	/**
	 * ComboBox editable property.
	 * 
	 * @param bool false:編集不可
	 */
	public void setEditable(boolean bool) {
		this.combo.setEditable(bool);
	}

	/**
	 * ComboBox editable property.
	 * 
	 * @return false:編集不可
	 */
	public boolean isEditable() {
		return this.combo.isEditable();
	}

	/**
	 * comboにフォーカスを合わせる. field.requestFocus()
	 */
	public void requestTextFocus() {
		this.combo.requestFocus();
	}

	/**
	 * add focus listener
	 * 
	 * @param listener
	 */
	@Override
	public void addFocusListener(FocusListener listener) {

		this.combo.getEditor().getEditorComponent().addFocusListener(listener);
	}

	/**
	 * remove focus listener
	 * 
	 * @param listener
	 */
	@Override
	public void removeFocusListener(FocusListener listener) {

		this.combo.getEditor().getEditorComponent().removeFocusListener(listener);
	}

	/**
	 * input verifier setter.
	 * 
	 * @param iv 設定InputVerifier
	 */
	@Override
	public void setInputVerifier(InputVerifier iv) {

		this.combo.setInputVerifier(iv);
	}

	/**
	 * input verifier getter.
	 * 
	 * @return 取得InputVerifier
	 */
	@Override
	public InputVerifier getInputVerifier() {

		return this.combo.getInputVerifier();
	}

	/**
	 * コンボボックスのインデックス設定
	 * 
	 * @param index インデックス
	 */
	public void setSelectedIndex(int index) {
		this.combo.setSelectedIndex(index);
	}

	/**
	 * 値を指定して選択を変更する
	 * 
	 * @param value 値
	 */
	public void setSelectedItemValue(Object value) {
		this.combo.setSelectedItemValue(value);
	}

	/**
	 * コンボボックスのインデックス取得
	 * 
	 * @return インデックス
	 */
	public int getSelectedIndex() {
		return this.combo.getSelectedIndex();
	}

	/**
	 * 選択されている値を取得
	 * 
	 * @return 値
	 */
	public Object getSelectedItemValue() {
		return this.combo.getSelectedItemValue();
	}

	/**
	 * 値(キー)・表示文字を追加
	 * 
	 * @param text テキスト
	 * @param value 値
	 */
	public void addTextValueItem(Object value, String text) {
		this.combo.addTextValueItem(value, text);
	}
}
