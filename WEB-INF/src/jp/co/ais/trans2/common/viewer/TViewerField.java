package jp.co.ais.trans2.common.viewer;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;

/**
 * 表示用ラベル・コード・名称
 */
public class TViewerField extends TLabelField {

	/** 名称フィールド */
	public TTextField field2;

	/**
	 * コンストラクター
	 */
	public TViewerField() {
		//
	}

	/**
	 * 子itemの初期化.
	 */
	@Override
	protected void initComponents() {
		field2 = createTextField();

		super.initComponents();

		TGuiUtil.setComponentSize(field2, new Dimension(150, 20));

		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		add(field2, gridBagConstraints);
	}

	/**
	 * アイテムのサイズ保存
	 */
	@Override
	protected void initControl() {
		super.initControl();

		setEditable(false);
		setOpaque(false);
		getField().setOpaque(false);
		field2.setOpaque(false);
		setLabelSize(75);
		setFieldSize(80);
	}

	/**
	 * field editable property.
	 * 
	 * @param bool false:編集不可
	 */
	@Override
	public void setEditable(boolean bool) {
		this.field.setEditable(bool);
		this.field2.setEditable(bool);
	}

	/**
	 * panelサイズを、並んだアイテムの横幅の合計にする.
	 */
	@Override
	protected void setPanelSize() {
		TGuiUtil.setComponentSize(this, getLabelSize() + getFieldSize() + getField2Size() + 5,
			this.getPreferredSize().height);
	}

	/**
	 * @return 名称2
	 */
	public TTextField getField2() {
		return field2;
	}

	/**
	 * @return 名称幅
	 */
	public int getField2Size() {
		return field2.getWidth();
	}

	/**
	 * 名称2幅設定
	 * 
	 * @param width
	 */
	public void setField2Size(int width) {
		TGuiUtil.setComponentSize(field2, width, field2.getHeight());
		setPanelSize();
	}

	/**
	 * コンポーネントのサイズを再設定する
	 */
	@Override
	public void reWidth() {
		setSize(this.field.getWidth() + this.label.getWidth() + this.field2.getWidth() + 5, this.getHeight());
	}

	@Deprecated
	@Override
	public void setValue(String value) {
		super.setValue(value);
	}

	/**
	 * 値設定
	 * 
	 * @param code
	 * @param name
	 */
	public void setValue(String code, String name) {
		field.setText(code);
		field2.setText(name);
	}
}
