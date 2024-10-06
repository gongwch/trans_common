package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.language.*;

/**
 * ����R�[�h�R���{�{�b�N�X
 */
public class TLanguageComboBox extends TLabelComboBox {

	/** �R���g���[�� */
	protected TLanguageComboBoxController controller;

	/**
	 * 
	 */
	public TLanguageComboBox() {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

		controller = new TLanguageComboBoxController(this);

	}

	protected void initComponents() {
		// 
	}

	protected void allocateComponents() {

		// ����R�[�h
		label.setLangMessageID("C00699");
		setLabelSize(60);
		setComboSize(100);
		setSize(165, 20);

	}

	/**
	 * @return controller
	 */
	public TLanguageComboBoxController getController() {
		return controller;
	}

	/**
	 * �I������Ă���l���擾
	 * 
	 * @return �l
	 */
	@Override
	public String getSelectedItemValue() {
		return (String) this.combo.getSelectedItemValue();
	}

}
