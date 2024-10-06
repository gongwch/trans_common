package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;

/**
 * �ʉݕʊO�ݏo�͑I���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TCurrencySelectorController extends TController {

	/** �t�B�[���h */
	protected TCurrencySelector field;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TCurrencySelectorController(TCurrencySelector field) {
		this.field = field;
		init();
	}

	@Override
	public void start() {
		//
	}

	/**
	 * ������
	 */
	protected void init() {
		addEvent();
		field.rdoTrue.setSelected(true);
	}

	/**
	 * �C�x���g��`
	 */
	protected void addEvent() {
		field.rdoTrue.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					rdoTrue_Click();
				}
			}

		});

		field.rdoFalse.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					rdoFalse_Click();
				}
			}

		});

	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * [����]����
	 */
	public void rdoTrue_Click() {
		field.currencyReference.setEditable(true);
	}

	/**
	 * [���Ȃ�]����
	 */
	public void rdoFalse_Click() {
		field.currencyReference.clear();
		field.currencyReference.setEditable(false);
	}

}
