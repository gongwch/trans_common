package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ��I���F����\���F���C��
 * 
 * @author AIS
 */
public class TManagementComboMultiUnit extends TPanel {

	/** �Ǘ��I���R���{�{�b�N�X */
	public List<TComboBox> combos;

	/** �e�R���{�{�b�N�X�̏����l */
	public List<ManagementAngle> angles;

	/** ����\�� */
	public TButton btn;

	/** �R���g���[�� */
	public TManagementComboMultiUnitController controller;

	/**
	 * �R���X�g���N�^
	 */
	public TManagementComboMultiUnit() {
		this(null);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param angles
	 */
	public TManagementComboMultiUnit(List<ManagementAngle> angles) {

		if (angles == null) {
			this.angles = new ArrayList<ManagementAngle>();
			this.angles.add(ManagementAngle.NONE);
			this.angles.add(ManagementAngle.NONE);
			this.angles.add(ManagementAngle.NONE);
		} else {
			this.angles = angles;
		}

		// �R���|�[�l���g������
		initComponents();

		// �R���|�[�l���g�z�u
		allocateComponents();

		// �R���g���[������
		controller = createController();
	}

	/**
	 * �R���|�[�l���g������
	 */
	public void initComponents() {
		combos = new ArrayList<TComboBox>();
		for (int i = 0; i < angles.size(); i++) {
			combos.add(new TComboBox());
		}

		// ����\��
		btn = new TButton();
	}

	/**
	 * �R���|�[�l���g�z�u
	 */
	public void allocateComponents() {

		setLayout(null);

		int x = 0;
		for (TComboBox cbo : combos) {
			TGuiUtil.setComponentSize(cbo, 90, 20);
			cbo.setLocation(x, 5);
			add(cbo);
			x += cbo.getWidth() + 5;
		}

		btn.setLangMessageID("C11884"); // ����\��
		btn.setSize(20, 70);
		btn.setLocation(x, 5);
		add(btn);

		x += btn.getWidth();

		setSize(x + 5, 30);
	}

	/**
	 * �R���g���[������
	 * 
	 * @return controller
	 */
	protected TManagementComboMultiUnitController createController() {
		return new TManagementComboMultiUnitController(this);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		for (TComboBox cbo : combos) {
			cbo.setTabControlNo(tabControlNo);
		}
		btn.setTabControlNo(tabControlNo);
	}

	/**
	 * �Ǘ�������Z�߂ĕԂ�
	 * 
	 * @return �Ǘ�������Z�߂ĕԂ�
	 */
	public List<ManagementAngle> getSelectedAngles() {
		return controller.getSelectedAngles();
	}

	/**
	 * �Ǘ�������ݒ肷��
	 * 
	 * @param list �Ǘ�����
	 */
	public void setSelectedAngles(List<ManagementAngle> list) {
		controller.setSelectedAngles(list);
	}

	/**
	 * �Ǘ�������ݒ肷��
	 * 
	 * @param conditions �Ǘ�����
	 */
	public void setSelectedAngleConditions(List<ManagementAngleSearchCondition> conditions) {
		controller.setSelectedAngleConditions(conditions);
	}

	/**
	 * @return true:�g�p�\�̊Ǘ����ڂ��I�����ꂽ����
	 */
	public boolean isLastSelected() {
		return controller.isLastSelected();
	}

	/**
	 * �g�p�\�̊Ǘ����ڂɃt�H�[�J�X�𓖂Ă�
	 */
	public void requestLastFocus() {
		controller.requestLastFocus();
	}

}