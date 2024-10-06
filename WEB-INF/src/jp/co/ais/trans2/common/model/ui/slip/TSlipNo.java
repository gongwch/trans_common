package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;

/**
 * “`•[”Ô†{C³‰ñ”
 */
public class TSlipNo extends TLabelField {

	/** C³‰ñ” */
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

		// C³‰ñ”
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
	 * C³‰ñ”ƒtƒB[ƒ‹ƒh‚ğ¶¬
	 * 
	 * @return C³‰ñ”ƒtƒB[ƒ‹ƒh
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
	 * C³‰ñ”ƒZƒbƒg
	 * 
	 * @param count C³‰ñ”
	 */
	public void setUpdateCount(int count) {
		txtUpdCount.setNumber(count);
	}

	/**
	 * C³‰ñ”ƒQƒbƒg
	 * 
	 * @return C³‰ñ”
	 */
	public int getUpdateCount() {
		return txtUpdCount.getInt();
	}

	/**
	 * “`•[”Ô†ƒZƒbƒg
	 * 
	 * @param slipNo “`•[”Ô†
	 */
	public void setSlipNo(String slipNo) {
		field.setText(slipNo);
	}

	/**
	 * “`•[”Ô†ƒQƒbƒg
	 * 
	 * @return “`•[”Ô†
	 */
	public String getSlipNo() {
		return field.getText();
	}

	/**
	 * “ü—Í’l‚ğƒNƒŠƒA‚·‚é
	 */
	@Override
	public void clear() {
		this.field.clear();
		this.txtUpdCount.clear();
	}
}
