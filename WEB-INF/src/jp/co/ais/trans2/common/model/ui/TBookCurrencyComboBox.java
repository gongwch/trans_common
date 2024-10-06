package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.define.CurrencyType;

/**
 * �\���ʉݑI���R���{�{�b�N�X
 */
public class TBookCurrencyComboBox extends TLabelComboBox {

	/** �R���g���[�� */
	protected TBookCurrencyComboBoxController controller;

	/**
	 * 
	 */
	public TBookCurrencyComboBox() {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

		controller = new TBookCurrencyComboBoxController(this);

	}

	/**
	 * �R���|�[�l���g������
	 */
	protected void initComponents() {
		// 
	}

	/**
	 * �R���|�[�l���g�̔z�u
	 */
	protected void allocateComponents() {

		// �\���ʉ�
		label.setLangMessageID("C10549");
		setLabelSize(50);
		setComboSize(150);
		setSize(245, 20);

	}

	/**
	 * @return
	 */
	public TBookCurrencyComboBoxController getController() {
		return controller;
	}

	/**
	 * �I������Ă���\���ʉ݂�Ԃ�
	 * 
	 * @return �I������Ă���\���ʉ�
	 */
	public CurrencyType getCurrencyType() {
		return controller.getCurrencyType();
	}

}
