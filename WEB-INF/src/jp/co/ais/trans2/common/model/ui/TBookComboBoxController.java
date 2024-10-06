package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.*;

/**
 * �䒠�I���R���{�{�b�N�X�̃R���g���[��
 * 
 * @author AIS
 */
public class TBookComboBoxController extends TController {

	/** �R���{�{�b�N�X */
	protected TBookComboBox comboBox;

	/**
	 * @param comboBox �t�B�[���h
	 */
	public TBookComboBoxController(TBookComboBox comboBox) {

		this.comboBox = comboBox;

		init();
		initEvent();
	}

	/**
	 * ������
	 */
	protected void init() {

		// �R���{�{�b�N�X����
		getList();
	}

	/**
	 * �䒠����
	 */
	protected void getList() {
		comboBox.getComboBox().addTextValueItem(AccountBook.OWN, getWord(AccountBook.getAccountBookName(AccountBook.OWN)));
		comboBox.getComboBox().addTextValueItem(AccountBook.IFRS, getWord(AccountBook.getAccountBookName(AccountBook.IFRS)));
	}

	/**
	 * �C�x���g�\��
	 */
	public void initEvent() {
		comboBox.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					setCurrencySelector();
				}
			}
		});
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * �I������Ă���䒠��Ԃ�
	 * 
	 * @return �I������Ă���䒠
	 */
	public AccountBook getAccountBook() {
		return (AccountBook) comboBox.getSelectedItemValue();
	}

	/**
	 * �w��̑䒠��I������
	 * 
	 * @param book
	 */
	public void selectAccountBook(AccountBook book) {
		comboBox.setSelectedItemValue(book);
	}

	/**
	 * �䒠�ʉݑI����ݒ肷��B
	 */
	public void setCurrencySelector() {

		TBookCurrencyComboBox cmbBookCurrency = comboBox.cmbBookCurrency;

		if (cmbBookCurrency != null) {

			switch (getAccountBook()) {
				case OWN: // ������v����
					cmbBookCurrency.setSelectedItemValue(CurrencyType.KEY); // �����ʉ�ONLY
					cmbBookCurrency.setEnabled(false);
					break;

				default:
					cmbBookCurrency.setEnabled(true);
					break;
			}
		}
	}

}
