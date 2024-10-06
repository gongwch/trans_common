package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.define.AccountBook;

/**
 * ����I���R���{�{�b�N�X
 */
public class TBookComboBox extends TLabelComboBox {

	/** �R���g���[�� */
	protected TBookComboBoxController controller;

	/** �䒠�ʉݑI���R���|�[�l���g */
	public TBookCurrencyComboBox cmbBookCurrency;

	/**
	 * �R���X�g���N�^
	 */
	public TBookComboBox() {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

		controller = new TBookComboBoxController(this);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param cmbBookCurrency �\���ʉݑI���R���{�{�b�N�X
	 */
	public TBookComboBox(TBookCurrencyComboBox cmbBookCurrency) {
		this();

		this.cmbBookCurrency = cmbBookCurrency;
		controller.setCurrencySelector();
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

		// ����敪
		label.setLangMessageID("C10961");
		setLabelSize(60);
		setComboSize(150);
		setSize(245, 20);
	}

	/**
	 * �I������Ă���䒠��Ԃ�
	 * 
	 * @return �I������Ă���䒠
	 */
	public AccountBook getAccountBook() {
		return controller.getAccountBook();
	}

	/**
	 * �w��̑䒠��I������
	 * 
	 * @param book
	 */
	public void selectAccountBook(AccountBook book) {
		controller.selectAccountBook(book);
	}

}
