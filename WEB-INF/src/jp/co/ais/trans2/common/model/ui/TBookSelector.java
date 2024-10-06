package jp.co.ais.trans2.common.model.ui;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * �䒠�I���R���|�[�l���g
 * 
 * @author AIS
 */
public class TBookSelector extends TTitlePanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 3687486371935677293L;

	/** ������v���� */
	public TRadioButton rdoOwn;

	/** IFRS��v���� */
	public TRadioButton rdoIfrs;

	/** �R���g���[�� */
	protected TBookSelectorController controller;

	/** �䒠�ʉݑI���R���|�[�l���g */
	public TBookCurrencySelector tBookCurrencySelector;

	/**
	 * �R���|�[�l���g
	 */
	public TBookSelector() {
		this(true);
	}

	/**
	 * �R���|�[�l���g
	 * 
	 * @param title �^�C�g���\�����ǂ���
	 */
	public TBookSelector(boolean title) {
		initComponents();
		allocateComponents(title);

		controller = new TBookSelectorController(this);
	}

	/**
	 * �R���|�[�l���g
	 * 
	 * @param tBookCurrencySelector �䒠�ʉݑI���R���|�[�l���g
	 */
	public TBookSelector(TBookCurrencySelector tBookCurrencySelector) {
		this(tBookCurrencySelector, true);
	}

	/**
	 * �R���|�[�l���g
	 * 
	 * @param title �^�C�g���\�����ǂ���
	 * @param tBookCurrencySelector �䒠�ʉݑI���R���|�[�l���g
	 */
	public TBookSelector(TBookCurrencySelector tBookCurrencySelector, boolean title) {
		this(title);

		this.tBookCurrencySelector = tBookCurrencySelector;
		controller.setCurrencySelector();
	}

	/**
	 * �R���|�[�l���g�̏�����
	 */
	public void initComponents() {
		rdoOwn = new TRadioButton();
		rdoIfrs = new TRadioButton();
	}

	/**
	 * �R���|�[�l���g�̔z�u
	 * 
	 * @param title �^�C�g���\�����ǂ���
	 */
	public void allocateComponents(boolean title) {

		setSize(135, 75);

		if (title) {
			setLangMessageID("C10961"); // ����敪

		} else {
			setBorder(TBorderFactory.createTitledBorder(TModelUIUtil.getWord("C10961"))); // ����敪
			this.titlePanel.setVisible(false);
			mainPanel.setOpaque(false);
		}

		// ������v����
		rdoOwn.setLangMessageID("C10982"); // ������v����
		rdoOwn.setSize(120, 20);
		rdoOwn.setLocation(15, title ? 5 : 0);
		add(rdoOwn);

		// IFRS��v����
		rdoIfrs.setLangMessageID("C10983"); // IFRS��v����
		rdoIfrs.setSize(120, 20);
		rdoIfrs.setLocation(15, title ? 30 : 25);
		add(rdoIfrs);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdoOwn);
		bg.add(rdoIfrs);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		rdoOwn.setTabControlNo(tabControlNo);
		rdoIfrs.setTabControlNo(tabControlNo);
	}

	/**
	 * �I�����ꂽ�����Ԃ�
	 * 
	 * @return �I�����ꂽ����
	 */
	public AccountBook getAccountBook() {
		return controller.getAccountBook();
	}

	/**
	 * �����ݒ�
	 * 
	 * @param accountBook �I�����ꂽ����
	 */
	public void setAccountBook(AccountBook accountBook) {
		controller.setAccountBook(accountBook);
	}

	/**
	 * �䒠�ʉݑI���R���|�[�l���g
	 * 
	 * @return �䒠�ʉݑI���R���|�[�l���g
	 */
	public TBookCurrencySelector getTBookCurrencySelector() {
		return tBookCurrencySelector;
	}

	/**
	 * �䒠�ʉݑI���R���|�[�l���g
	 * 
	 * @param bookCurrencySelector �䒠�ʉݑI���R���|�[�l���g
	 */
	public void setTBookCurrencySelector(TBookCurrencySelector bookCurrencySelector) {
		tBookCurrencySelector = bookCurrencySelector;
	}

}
