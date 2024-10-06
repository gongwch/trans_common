package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �⏕�Ȗڃ}�X�^�͈͌����̃R���|�[�l���g
 */
public class TSubItemRange extends TPanel {

	/** �Ȗڌ��� */
	public TItemReference itemReference;

	/** �⏕�Ȗڔ͈͌��� */
	public TSubItemReferenceRange subItemRange;

	/**
	 * �R���X�g���N�^.
	 */
	public TSubItemRange() {
		super();
		initComponents();
		allocateComponents();
	}
	
	/**
	 * �R���|�[�l���g�̏������B��ɃC���X�^���X�̐������s���܂��B
	 */
	public void initComponents() {
		itemReference = new TItemReference();
		subItemRange = new TSubItemReferenceRange();
	}

	/**
	 * �R���|�[�l���g�̔z�u���s���܂��B
	 */
	public void allocateComponents() {

		setLayout(null);
		setSize(400, 100);

		// �Ȗڌ���
		itemReference.setLocation(0, 0);
		add(itemReference);

		// �⏕�Ȗڔ͈͌���
		subItemRange.setLocation(0, 30);
		add(subItemRange);
	}

	/**
	 * �R���|�[�l���g�̃^�u���̐ݒ���s���܂��B
	 * 
	 * @param tabControlNo
	 */
	public void setTabControlNo(int tabControlNo) {
		itemReference.setTabControlNo(tabControlNo);
		subItemRange.setTabControlNo(tabControlNo);
	}
}