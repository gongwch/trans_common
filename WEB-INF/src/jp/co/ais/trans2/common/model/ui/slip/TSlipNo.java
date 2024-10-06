package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;

/**
 * �`�[�ԍ��{�C����
 */
public class TSlipNo extends TLabelField {

	/** �C���� */
	protected TNumericField txtUpdCount;

	/**
	 * @see jp.co.ais.trans.common.gui.TLabelField#initComponents()
	 */
	@Override
	protected void initComponents() {
		super.initComponents();

		TGuiUtil.setComponentSize(label, new Dimension(75, 20));
		label.setLangMessageID("C00605");

		field.setImeMode(false);
		field.setMaxLength(20);

		// �C����
		txtUpdCount = createUpdateCountField();
		txtUpdCount.setEditable(false);
		txtUpdCount.setNumericFormat("#,###");
		TGuiUtil.setComponentSize(txtUpdCount, new Dimension(30, 20));

		add(txtUpdCount, new GridBagConstraints());

		setPanelSize();
	}

	/**
	 * init
	 */
	@Override
	protected void initControl() {
		field.setEditable(false);
	}

	/**
	 * �C���񐔃t�B�[���h�𐶐�
	 * 
	 * @return �C���񐔃t�B�[���h
	 */
	protected TNumericField createUpdateCountField() {
		return new TNumericField();
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TLabelField#setPanelSize()
	 */
	@Override
	protected void setPanelSize() {
		if (txtUpdCount == null) {
			return;
		}

		TGuiUtil.setComponentSize(this, new Dimension(this.label.getWidth() + this.field.getWidth()
			+ txtUpdCount.getWidth() + 5, this.getPreferredSize().height));
	}

	/**
	 * �C���񐔃Z�b�g
	 * 
	 * @param count �C����
	 */
	public void setUpdateCount(int count) {
		txtUpdCount.setNumber(count);
	}

	/**
	 * �C���񐔃Q�b�g
	 * 
	 * @return �C����
	 */
	public int getUpdateCount() {
		return txtUpdCount.getInt();
	}

	/**
	 * �`�[�ԍ��Z�b�g
	 * 
	 * @param slipNo �`�[�ԍ�
	 */
	public void setSlipNo(String slipNo) {
		field.setText(slipNo);
	}

	/**
	 * �`�[�ԍ��Q�b�g
	 * 
	 * @return �`�[�ԍ�
	 */
	public String getSlipNo() {
		return field.getText();
	}

	/**
	 * ���͒l���N���A����
	 */
	@Override
	public void clear() {
		this.field.clear();
		this.txtUpdCount.clear();
	}
}
