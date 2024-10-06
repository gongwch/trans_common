package jp.co.ais.trans2.master.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �Ȗڃ}�X�^�`�F�b�N�{�b�N�X�̃R���|�[�l���g<br>
 * �q�C���x�v�Z�t���O�ǉ���
 */
public class TItemStatusVoyageUnit extends TItemStatusUnit {

	/** �q�C���x�v�Z�t���O */
	public TCheckBox chkVoyage;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param company
	 */
	public TItemStatusVoyageUnit(Company company) {
		super(company);
	}

	/**
	 * �R���|�[�l���g�̏������B��ɃC���X�^���X�̐������s���܂��B
	 */
	@Override
	public void initComponents() {
		super.initComponents();

		chkVoyage = new TCheckBox();
	}

	/**
	 * �R���|�[�l���g�̔z�u���s���܂��B
	 */
	@Override
	public void allocateComponents() {
		super.allocateComponents();

		setLayout(null);
		setSize(700, 540);

		// �q�C���x�v�Z�t���O
		chkVoyage.setLangMessageID("C11602"); // �q�C���x�v�Z�t���O
		chkVoyage.setSize(180, 20);
		if (chkOccurDate.isVisible()) {
			chkVoyage.setLocation(0, chkOccurDate.getY() + 20);
		} else {
			chkVoyage.setLocation(0, chksirzeiflg.getY() + 20);
		}
		add(chkVoyage);
	}

	/**
	 * �R���|�[�l���g�̃^�u���̐ݒ���s���܂��B
	 * 
	 * @param tabControlNo
	 */
	@Override
	public void setTabControlNo(int tabControlNo) {
		super.setTabControlNo(tabControlNo);

		chkVoyage.setTabControlNo(tabControlNo);
	}
}
