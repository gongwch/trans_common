package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * �䒠�I���R���|�[�l���g(�`�F�b�N�{�b�N�X��)
 * 
 * @author AIS
 */
public class TBookCheckBoxSelector extends TPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 3687486371935677293L;

	/** ������v���� */
	public TCheckBox chkOwn;

	/** IFRS��v���� */
	public TCheckBox chkIfrs;

	/** �R���g���[�� */
	protected TBookCheckBoxSelectorController controller;

	/**
	 * �R���|�[�l���g
	 */
	public TBookCheckBoxSelector() {

		initComponents();

		allocateComponents();

		controller = new TBookCheckBoxSelectorController(this);

	}

	/**
	 * �R���|�[�l���g�̏�����
	 */
	public void initComponents() {
		chkOwn = new TCheckBox();
		chkIfrs = new TCheckBox();
	}

	/**
	 * �R���|�[�l���g�̔z�u
	 */
	public void allocateComponents() {

		setLayout(null);
		// ����敪
		setLangMessageID("C10961");
		setSize(135, 75);

		// ������v����
		chkOwn.setLangMessageID("C10982");
		chkOwn.setSize(100, 20);
		chkOwn.setLocation(15, 18);
		add(chkOwn);

		// IFRS��v����
		chkIfrs.setLangMessageID("C10983");
		chkIfrs.setSize(100, 20);
		chkIfrs.setLocation(15, 43);
		add(chkIfrs);

	}

	/**
	 * �t�H�[�J�X�����i������v����Ƀt�H�[�J�X�j
	 * 
	 * @see javax.swing.JComponent#requestFocus()
	 */
	@Override
	public void requestFocus() {
		chkOwn.requestFocus();
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		chkOwn.setTabControlNo(tabControlNo);
		chkIfrs.setTabControlNo(tabControlNo);
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
	 * �䒠���I������Ă��邩�ǂ���
	 * 
	 * @return false�F����I������Ă��Ȃ�
	 */
	public boolean isSelected() {
		return controller.isSelected();
	}

}
