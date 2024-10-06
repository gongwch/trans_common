package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.*;

/**
 * �䒠�I���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TBookSelectorController extends TController {

	/** �䒠�I���R���|�[�l���g */
	protected TBookSelector field;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TBookSelectorController(TBookSelector field) {
		this.field = field;
		init();
		initEvent();
	}

	/**
	 * ������
	 */
	public void init() {
		field.rdoOwn.setSelected(true);
	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * �I�����ꂽ�����Ԃ�
	 * 
	 * @return �I�����ꂽ����
	 */
	public AccountBook getAccountBook() {
		if (field.rdoOwn.isSelected()) {
			return AccountBook.OWN;
		} else if (field.rdoIfrs.isSelected()) {
			return AccountBook.IFRS;
		}
		return null;
	}

	/**
	 * �����ݒ�
	 * 
	 * @param accountBook �I�����ꂽ����
	 */
	public void setAccountBook(AccountBook accountBook) {
		if (AccountBook.IFRS == accountBook) {
			field.rdoIfrs.setSelected(true);
		} else {
			field.rdoOwn.setSelected(true);
		}
	}

	/**
	 * �C�x���g�\��
	 */
	public void initEvent() {
		field.rdoOwn.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					setCurrencySelector();
				}
			}
		});

		field.rdoIfrs.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					setCurrencySelector();
				}
			}

		});
	}

	/**
	 * �䒠�ʉݑI����ݒ肷��B
	 */
	public void setCurrencySelector() {

		TBookCurrencySelector tBookCurrencySelector = field.tBookCurrencySelector;

		if (tBookCurrencySelector != null) {
			// ������v����
			if (field.rdoOwn.isSelected()) {
				tBookCurrencySelector.rdoKey.setSelected(true);// �����ʉ�
				tBookCurrencySelector.rdoKey.setEnabled(false);// �����ʉ�
				tBookCurrencySelector.rdoFunctional.setEnabled(false);// �@�\�ʉ�
			} else {
				tBookCurrencySelector.rdoKey.setEnabled(true);// �����ʉ�
				tBookCurrencySelector.rdoFunctional.setEnabled(true);// �@�\�ʉ�
			}
		}
	}

}
