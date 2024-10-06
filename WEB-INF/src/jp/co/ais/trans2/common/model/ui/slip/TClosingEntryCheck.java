package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;

/**
 * ���Z�d��`�F�b�N
 */
public class TClosingEntryCheck extends TPanel {

	/** �`�F�b�N */
	public TCheckBox chk;

	/** ���Z�i�K */
	public TNumericField num;

	/** �� */
	public TLabel lblStage;

	/** �i�K */
	public int stage;

	/** �`�[���t�R���|�[�l���g */
	public TSlipDate ctrlSlipDate;

	/** �R���g���[�� */
	public TClosingEntryCheckController controller;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param tSlipDate �`�[���t�R���|�[�l���g
	 */
	public TClosingEntryCheck(TSlipDate tSlipDate) {
		super();

		this.ctrlSlipDate = tSlipDate;

		initComponents();
		allocateComponents();

		controller = createController();
	}

	/**
	 * @return �R���g���[��
	 */
	protected TClosingEntryCheckController createController() {
		return new TClosingEntryCheckController(this);
	}

	/**
	 * ������
	 */
	protected void initComponents() {
		chk = new TCheckBox();
		num = new TNumericField();
		lblStage = new TLabel();
	}

	/**
	 * �z�u
	 */
	protected void allocateComponents() {
		this.setLayout(null);

		TGuiUtil.setComponentSize(this, new Dimension(125, 20));

		// �`�F�b�N
		chk.setLangMessageID("C00142");
		TGuiUtil.setComponentSize(chk, new Dimension(50, 20));
		chk.setLocation(0, 0);
		add(chk);

		// �i�K
		num.setEditable(false);
		num.setNumericFormat("#");
		num.setMaxLength(1);
		num.setPositiveOnly(true);
		TGuiUtil.setComponentSize(num, new Dimension(30, 20));
		num.setLocation(chk.getX() + chk.getWidth(), 0);
		add(num);

		// �i�K
		lblStage.setLangMessageID("C00373");
		lblStage.setHorizontalAlignment(SwingConstants.LEFT);
		TGuiUtil.setComponentSize(lblStage, new Dimension(45, 20));
		lblStage.setLocation(num.getX() + num.getWidth() + 2, 0);
		add(lblStage);
	}

	/**
	 * �^�u���ݒ�
	 * 
	 * @param no �^�u��
	 */
	public void setTabControlNo(int no) {
		chk.setTabControlNo(no);
		num.setTabControlNo(no);
	}

	/**
	 * ���Z�i�K�����͉\���ǂ���(�`�F�b�N��)
	 * 
	 * @param isEdit true:�\
	 */
	public void setEditMode(boolean isEdit) {
		controller.setEditMode(isEdit);
	}

	/**
	 * �`�F�b�N��\�����Ȃ�
	 */
	public void setNotVisibleCheckBox() {
		chk.setVisible(false);

		num.setLocation(0, 0);
		lblStage.setLocation(num.getX() + num.getWidth() + 2, 0);
		TGuiUtil.setComponentSize(this, new Dimension(num.getWidth() + lblStage.getWidth(), 20));
	}

	/**
	 * �i�K��\�����Ȃ�
	 */
	public void setNotVisibleStage() {
		num.setVisible(false);
		lblStage.setVisible(false);

		TGuiUtil.setComponentSize(this, new Dimension(chk.getWidth(), 20));
	}

	/**
	 * ����������
	 */
	public void clear() {
		chk.setSelected(false);
	}

	/**
	 * �`�F�b�N�{�b�N�X���I������Ă��邩�ǂ���
	 * 
	 * @return true:�I��
	 */
	public boolean isSelected() {
		return chk.isSelected();
	}

	/**
	 * �`�F�b�N�{�b�N�X��ON/OFF
	 * 
	 * @param isSelected true:ON
	 */
	public void setSelected(boolean isSelected) {
		chk.setSelected(isSelected);
	}

	/**
	 * ���Z�i�K�̎擾
	 * 
	 * @return ���Z�i�K
	 */
	public int getStage() {
		return num.getInt();
	}

	/**
	 * ���Z�i�K�̍Đݒ�
	 */
	public void resetStage() {
		controller.resetStage();
	}

	/**
	 * �`�F�b�N�{�b�N�X�ւ̃��X�i�[�ݒ�
	 * 
	 * @param listener ���X�i�[
	 */
	public void addItemListener(ItemListener listener) {
		chk.addItemListener(listener);
	}
}
