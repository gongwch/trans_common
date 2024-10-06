package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * ����I���t�B�[���h
 * 
 * @author AIS
 */
public class TCheckBookField extends TTitlePanel {

	/** �������� */
	public TCheckBox chkNationalBook;

	/** IFRS���� */
	public TCheckBox chkIFRSBook;

	/** ���� */
	public static final int height = 20;

	/**
	 * �R���X�g���N�^.
	 */
	public TCheckBookField() {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

	}

	/**
	 * �R���|�[�l���g
	 */
	protected void initComponents() {
		chkNationalBook = new TCheckBox();
		chkIFRSBook = new TCheckBox();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	protected void allocateComponents() {

		setSize(130, 75);

		// ����I��
		setLangMessageID("C11103");

		chkNationalBook.setSelected(true);
		chkNationalBook.setSize(100, height);
		// ��������
		chkNationalBook.setLangMessageID("C11104");
		chkNationalBook.setLocation(15, 5);
		add(chkNationalBook);

		chkIFRSBook.setSelected(true);
		chkIFRSBook.setSize(100, height);
		// IFRS����
		chkIFRSBook.setLangMessageID("C11105");
		chkIFRSBook.setLocation(15, 30);
		add(chkIFRSBook);

	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		chkNationalBook.setTabControlNo(tabControlNo);
		chkIFRSBook.setTabControlNo(tabControlNo);
	}

	/**
	 * @return
	 */
	public TCheckBox getChkNationalBook() {
		return chkNationalBook;
	}

	/**
	 * @return
	 */
	public TCheckBox getChkIFRSBook() {
		return chkIFRSBook;
	}

	/**
	 * �ҏW��Ԃ�ݒ肷��
	 * 
	 * @param isEditable
	 */
	public void setEditable(boolean isEditable) {
		chkNationalBook.setEnabled(isEditable);
		chkIFRSBook.setEnabled(isEditable);
	}

	/**
	 * �N���A
	 */
	public void clear() {
		chkNationalBook.setSelected(false);
		chkIFRSBook.setSelected(false);
	}

	/**
	 * �����ꂩ�̒��낪�I������Ă��邩��Ԃ�
	 * 
	 * @return �����ꂩ�̒��낪�I������Ă��邩
	 */
	public boolean isSelected() {
		return chkNationalBook.isSelected() || chkIFRSBook.isSelected();
	}

	/**
	 * �I����Ԃ̒����Ԃ�
	 * 
	 * @return
	 */
	public AccountBook getAccountBook() {
		if (chkIFRSBook.isSelected() && chkNationalBook.isSelected()) {
			return AccountBook.BOTH;
		} else if (chkNationalBook.isSelected()) {
			return AccountBook.OWN;
		} else if (chkIFRSBook.isSelected()) {
			return AccountBook.IFRS;
		} else {
			return null;
		}

	}
}
