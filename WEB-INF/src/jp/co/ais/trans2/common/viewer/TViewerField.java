package jp.co.ais.trans2.common.viewer;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;

/**
 * �\���p���x���E�R�[�h�E����
 */
public class TViewerField extends TLabelField {

	/** ���̃t�B�[���h */
	public TTextField field2;

	/**
	 * �R���X�g���N�^�[
	 */
	public TViewerField() {
		//
	}

	/**
	 * �qitem�̏�����.
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
	 * �A�C�e���̃T�C�Y�ۑ�
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
	 * @param bool false:�ҏW�s��
	 */
	@Override
	public void setEditable(boolean bool) {
		this.field.setEditable(bool);
		this.field2.setEditable(bool);
	}

	/**
	 * panel�T�C�Y���A���񂾃A�C�e���̉����̍��v�ɂ���.
	 */
	@Override
	protected void setPanelSize() {
		TGuiUtil.setComponentSize(this, getLabelSize() + getFieldSize() + getField2Size() + 5,
			this.getPreferredSize().height);
	}

	/**
	 * @return ����2
	 */
	public TTextField getField2() {
		return field2;
	}

	/**
	 * @return ���̕�
	 */
	public int getField2Size() {
		return field2.getWidth();
	}

	/**
	 * ����2���ݒ�
	 * 
	 * @param width
	 */
	public void setField2Size(int width) {
		TGuiUtil.setComponentSize(field2, width, field2.getHeight());
		setPanelSize();
	}

	/**
	 * �R���|�[�l���g�̃T�C�Y���Đݒ肷��
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
	 * �l�ݒ�
	 * 
	 * @param code
	 * @param name
	 */
	public void setValue(String code, String name) {
		field.setText(code);
		field2.setText(name);
	}
}
