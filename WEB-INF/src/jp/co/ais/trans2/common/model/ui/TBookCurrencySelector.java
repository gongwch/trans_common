package jp.co.ais.trans2.common.model.ui;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * �䒠�ʉݑI���R���|�[�l���g
 * 
 * @author AIS
 */
public class TBookCurrencySelector extends TTitlePanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -8446725749481416463L;

	/** �����ʉ� */
	public TRadioButton rdoKey;

	/** �@�\�ʉ� */
	public TRadioButton rdoFunctional;

	/** �R���g���[�� */
	protected TBookCurrencySelectorController controller;

	/**
	 * �R���X�g���N�^
	 */
	public TBookCurrencySelector() {
		this(true);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param title �^�C�g���\�����ǂ���
	 */
	public TBookCurrencySelector(boolean title) {
		initComponents();
		allocateComponents(title);

		controller = new TBookCurrencySelectorController(this);
	}

	/**
	 * �R���|�[�l���g�̏�����
	 */
	public void initComponents() {
		rdoKey = new TRadioButton();
		rdoFunctional = new TRadioButton();
	}

	/**
	 * �R���|�[�l���g�̔z�u
	 * 
	 * @param title �^�C�g���\�����ǂ���
	 */
	public void allocateComponents(boolean title) {

		if (title) {
			setLangMessageID("C10549"); // �\���ʉ�

		} else {
			setBorder(TBorderFactory.createTitledBorder(TModelUIUtil.getWord("C10549"))); // �\���ʉ�
			this.titlePanel.setVisible(false);
			mainPanel.setOpaque(false);
		}

		setSize(110, 75);
		TGuiUtil.setComponentSize(this, this.getSize());

		// �����ʉ�
		rdoKey.setLangMessageID("C11083"); // �����ʉ�
		rdoKey.setSize(80, 20);
		rdoKey.setLocation(15, title ? 5 : 0);
		add(rdoKey);

		// �@�\�ʉ�
		rdoFunctional.setLangMessageID("C11084"); // �@�\�ʉ�
		rdoFunctional.setSize(80, 20);
		rdoFunctional.setLocation(15, title ? 30 : 25);
		add(rdoFunctional);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdoKey);
		bg.add(rdoFunctional);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		rdoKey.setTabControlNo(tabControlNo);
		rdoFunctional.setTabControlNo(tabControlNo);
	}

	/**
	 * �\���ʉ݂�Ԃ�
	 * 
	 * @return �\���ʉ�
	 */
	public CurrencyType getCurrencyType() {
		return controller.getCurrencyType();
	}

	/**
	 * �\���ʉ݂̐ݒ�
	 * 
	 * @param currencyType �\���ʉ�
	 */
	public void setCurrencyType(CurrencyType currencyType) {
		controller.setCurrencyType(currencyType);
	}

}
