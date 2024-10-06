package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * �ʉݕʊO�ݏo�͑I���R���|�[�l���g
 * 
 * @author AIS
 */
public class TCurrencySelector extends TTitlePanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -3116302500758346513L;

	/** �R���g���[�� */
	protected TCurrencySelectorController controller;

	/** ���� */
	public TRadioButton rdoTrue;

	/** ���Ȃ� */
	public TRadioButton rdoFalse;

	/** �ʉ݃t�B�[���h */
	public TCurrencyReference currencyReference;

	/**
	 * 
	 *
	 */
	public TCurrencySelector() {

		initComponents();

		allocateComponents();

		// �R���g���[������
		controller = new TCurrencySelectorController(this);

	}

	/**
	 * �R���|�[�l���g�̏�����
	 */
	public void initComponents() {
		rdoTrue = new TRadioButton();
		rdoFalse = new TRadioButton();
		currencyReference = new TCurrencyReference();
	}

	/**
	 * �R���|�[�l���g�̔z�u
	 */
	public void allocateComponents() {

		setLangMessageID("C11086"); // �ʉݕʊO�ݕ\��
		setSize(175, 75);

		// ����
		rdoTrue.setLangMessageID("C02100"); // ����
		rdoTrue.setSize(50, 20);
		rdoTrue.setLocation(15, 5);
		add(rdoTrue);

		// �ʉݑI��
		currencyReference.btn.setMargin(new Insets(0, 0, 0, 0));
		currencyReference.btn.setLangMessageID("C00371"); // �ʉ�
		currencyReference.btn.setMaximumSize(new Dimension(50, 20));
		currencyReference.btn.setMinimumSize(new Dimension(50, 20));
		currencyReference.btn.setPreferredSize(new Dimension(50, 20));
		currencyReference.name.setVisible(false);
		currencyReference.resize();
		currencyReference.setLocation(70, 7);
		add(currencyReference);

		// ���Ȃ�
		rdoFalse.setLangMessageID("C02099"); // ���Ȃ�
		rdoFalse.setSize(80, 20);
		rdoFalse.setLocation(15, 30);
		add(rdoFalse);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdoTrue);
		bg.add(rdoFalse);

	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		rdoTrue.setTabControlNo(tabControlNo);
		currencyReference.setTabControlNo(tabControlNo);
		rdoFalse.setTabControlNo(tabControlNo);
	}

}
