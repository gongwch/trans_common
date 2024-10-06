package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * �`�[�̃X�e�[�^�X�I���R���|�[�l���g
 * 
 * @author AIS
 */
public class TSlipStateSelector extends TTitlePanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 5229926607981299214L;

	/** �R���g���[�� */
	protected TSlipStateSelectorController controller;

	/** �X�V�敪[�o�^] */
	public TCheckBox chkEntry;

	/** �X�V�敪[���F] */
	public TCheckBox chkApprove;

	/**
	 * 
	 *
	 */
	public TSlipStateSelector() {

		initComponents();

		allocateComponents();

		// �R���g���[������
		controller = new TSlipStateSelectorController(this);

	}

	/**
	 * �R���|�[�l���g�̏�����
	 */
	public void initComponents() {
		chkEntry = new TCheckBox();
		chkApprove = new TCheckBox();
	}

	/**
	 * �R���|�[�l���g�̔z�u
	 */
	public void allocateComponents() {

		setLangMessageID("C01069"); // �X�V�敪
		setSize(90, 75);

		// �o�^
		chkEntry.setLangMessageID("C01258"); // �o�^
		chkEntry.setSize(50, 20);
		chkEntry.setLocation(15, 5);
		add(chkEntry);

		// ���F
		chkApprove.setLangMessageID("C01168"); // ���F
		chkApprove.setSize(50, 20);
		chkApprove.setLocation(15, 30);
		add(chkApprove);

	}

	/**
	 * �o�^���܂ނ�
	 * 
	 * @return �o�^���܂ނ�
	 */
	public boolean isEntry() {
		return controller.isEntry();
	}

	/**
	 * �o�^���܂ނ��ݒ�
	 * 
	 * @param isEntry �o�^���܂ނ�
	 */
	public void setEntry(boolean isEntry) {
		controller.setEntry(isEntry);
	}

	/**
	 * ���F���܂ނ�
	 * 
	 * @return ���F���܂ނ�
	 */
	public boolean isApprove() {
		return controller.isApprove();
	}

	/**
	 * ���F���܂ނ��ݒ�
	 * 
	 * @param isApprove ���F���܂ނ�
	 */
	public void setApprove(boolean isApprove) {
		controller.setApprove(isApprove);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		chkEntry.setTabControlNo(tabControlNo);
		chkApprove.setTabControlNo(tabControlNo);
	}

}
