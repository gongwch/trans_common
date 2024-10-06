package jp.co.ais.trans.common.gui;

import java.awt.*;

import javax.swing.*;

/**
 * TPanelに、タブ順、メッセージIDインターフェイスを追加した複合item. <br>
 * 子item.
 * <ol>
 * <li>label TLabel
 * <li>field TPasswordField
 * </ol>
 */
public class TLabelPasswordField extends TPanel implements TInterfaceLangMessageID, TInterfaceTabControl {

	/** 入力の概要を表示するlabel. */
	protected TLabel label;

	/** テキスト入力item. */
	protected TPasswordField field;

	/** labelのサイズ. */
	private Dimension labelSize = null;

	/** fieldのサイズ. */
	private Dimension fieldSize = null;

	/**
	 * Constructor. <br>
	 * 子itemを初期化する.
	 */
	public TLabelPasswordField() {
		super();
		initComponents();

		initControl();
	}

	/**
	 * アイテムのサイズ保存
	 */
	protected void initControl() {
		field.setEditable(true);
		field.setText("");
	}

	/**
	 * 子itemの初期化.
	 */
	protected void initComponents() {

		label = new TLabel();
		field = createPasswordField();

		setLayout(new java.awt.GridBagLayout());

		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setLabelFor(field);
		label.setText("TLabelField");

		TGuiUtil.setComponentSize(label, new Dimension(100, 20));
		add(label, new GridBagConstraints());

		TGuiUtil.setComponentSize(field, new Dimension(140, 20));

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(field, gridBagConstraints);

		labelSize = this.label.getPreferredSize();
		fieldSize = this.field.getPreferredSize();

		setPanelSize();
	}

	/**
	 * テキストフィールド生成
	 * 
	 * @return テキストフィールド
	 */
	protected TPasswordField createPasswordField() {
		return new TPasswordField();
	}

	// アイテムの幅 ********************************************

	/**
	 * panelサイズを、並んだアイテムの横幅の合計にする.
	 */
	private void setPanelSize() {
		TGuiUtil.setComponentSize(this, new Dimension(
			(int) (this.labelSize.getWidth() + this.fieldSize.getWidth()) + 5, this.getPreferredSize().height));
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
	 * label 幅の取得
	 * 
	 * @return 幅
	 */
	public int getLabelSize() {
		return (int) this.labelSize.getWidth();
	}

	/**
	 * field 幅の設定
	 * 
	 * @param size 幅
	 */
	public void setFieldSize(int size) {
		this.fieldSize.setSize(size, this.fieldSize.getHeight());
		TGuiUtil.setComponentSize(this.field, this.fieldSize);
		this.setPanelSize();
	}

	/**
	 * field 幅の取得
	 * 
	 * @return 幅
	 */
	public int getFieldSize() {
		return (int) this.fieldSize.getWidth();
	}

	// アイテムの寄せ ***********************

	/**
	 * labelの文字の寄せを設定する.
	 * 
	 * @deprecated
	 * @param align 0:左 1:中央 2:右
	 */
	public void setLabelHAlignment(int align) {
		TGuiUtil.setLabelHorizonalAlignment(this.label, align);
	}

	/**
	 * fieldの文字の寄せを設定する.
	 * 
	 * @deprecated
	 * @param align 0:左 1:中央 2:右
	 */
	public void setFieldHAlignment(int align) {
		TGuiUtil.setTextFieldHorizonalAlignment(this.field, align);
	}

	// item getter. ********************************************

	/**
	 * パネル上のTLabel labelのインスタンスを返す.
	 * 
	 * @return label
	 */
	public TLabel getLabel() {
		return this.label;
	}

	/**
	 * パネル上のTPasswordField fieldインスタンスを返す.
	 * 
	 * @return field
	 */
	public TPasswordField getField() {
		return this.field;
	}

	// 子itemの文字設定. **********************************************

	/**
	 * 表示するTextをlabelに設定する.
	 * 
	 * @param text 設定文字列
	 */
	public void setLabelText(String text) {
		this.label.setText(text);
	}

	/**
	 * setText()で設定された文字列を返す.
	 * 
	 * @return ラベル文字
	 */
	public String getLabelText() {
		return label.getText();
	}

	/**
	 * fieldに文字列を設定する.
	 * 
	 * @param value 設定文字列
	 */
	public void setValue(String value) {
		this.field.setText(value);
	}

	/**
	 * fieldから文字列を取得する.
	 * 
	 * @return field文字列
	 */
	public String getValue() {
		return this.field.getValue();
	}

	/**
	 * テキストフィールドに入力値が有るかどうか
	 * 
	 * @return true:入力値有り
	 */
	public boolean isEmpty() {
		return this.field.isEmpty();
	}

	/**
	 * labelのFontを設定する.
	 * 
	 * @param font 設定フォント
	 */
	public void setLabelFont(Font font) {
		this.label.setFont(font);
	}

	/**
	 * labelのFontを取得する.
	 * 
	 * @return フォント
	 */
	public Font getLabelFont() {
		return this.label.getFont();
	}

	// interface 実装 **************************************************

	/**
	 * タブ移動順番号をテキストフィールドから取得する.
	 */
	public int getTabControlNo() {
		return this.field.getTabControlNo();
	}

	/**
	 * タブ移動順番号をテキストフィールドに設定する.
	 */
	public void setTabControlNo(int no) {
		this.field.setTabControlNo(no);
	}

	/**
	 * タブ移動可・不可取得.
	 * 
	 * @return true: 可、false: 不可
	 */
	public boolean isTabEnabled() {
		return this.field.isTabEnabled();
	}

	/**
	 * タブ移動可・不可設定.
	 * 
	 * @param bool true: 可、false: 不可
	 */
	public void setTabEnabled(boolean bool) {
		this.field.setTabEnabled(bool);
	}

	/**
	 * ラベルの表示文字のメッセージIDを取得する.
	 */
	public String getLangMessageID() {
		return this.label.getLangMessageID();
	}

	/**
	 * ラベルの表示文字のメッセージIDを設定する.
	 * 
	 * @param langMessageID
	 */
	public void setLangMessageID(String langMessageID) {
		this.label.setLangMessageID(langMessageID);
	}

	// 最大桁. ****************************************************

	/**
	 * 最大桁数
	 * 
	 * @return 最大桁数
	 */
	public int getMaxLength() {
		return this.field.getMaxLength();
	}

	/**
	 * 最大桁数
	 * 
	 * @param maxLength 最大桁数
	 */
	public void setMaxLength(int maxLength) {
		this.field.setMaxLength(maxLength);
	}

	// enable 有効／無効操作制御 ************************************************

	/**
	 * field enabled property.
	 * 
	 * @param bool false:操作不可
	 */
	public void setFieldEnabled(boolean bool) {
		this.field.setEnabled(bool);
	}

	/**
	 * field enabled property.
	 * 
	 * @return bool
	 */
	public boolean isFieldEnabled() {
		return this.field.isEnabled();
	}

	/**
	 * label enabled property.
	 * 
	 * @param bool
	 */
	public void setLabelEnabled(boolean bool) {
		this.label.setEnabled(bool);
	}

	/**
	 * label enabled property.
	 * 
	 * @return label enabled property
	 */
	public boolean isLabelEnabled() {
		return this.label.isEnabled();
	}

	/**
	 * panel enabled property.
	 * 
	 * @param bool false:操作不可
	 */
	public void setEnabled(boolean bool) {
		// this.label.setEnabled(bool);
		this.field.setEnabled(bool);
		super.setEnabled(bool);
	}

	// editable 編集可／不可操作制御 ************************************************

	/**
	 * field editable property.
	 * 
	 * @param bool false:編集不可
	 */
	public void setEditable(boolean bool) {
		this.field.setEditable(bool);
	}

	/**
	 * field editable property.
	 * 
	 * @return false:編集不可
	 */
	public boolean isEditable() {
		return this.field.isEditable();
	}

	/*******************************************************************************************************************
	 * fieldにフォーカスを合わせる.
	 */
	public void requestTextFocus() {
		this.field.requestFocus();
	}

	/*******************************************************************************************************************
	 * fieldにフォーカスを合わせる.
	 */
	@Override
	public void requestFocus() {
		this.field.requestFocus();
	}

}
