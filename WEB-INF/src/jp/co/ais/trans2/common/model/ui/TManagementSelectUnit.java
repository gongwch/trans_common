package jp.co.ais.trans2.common.model.ui;

import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.management.*;

/**
 * �Ǘ��I���F�Ǘ��I��+�͈͌���+�C�ӑI���F���C��
 * 
 * @author AIS
 */
public class TManagementSelectUnit extends TTitlePanel {

	/** �Ǘ��I���R���{�{�b�N�X */
	public TComboBox cbo;

	/** �Ǘ��͈͑I���t�B�[���h */
	public List<TReferenceRangeUnit> ctrlReferenceRangeUnits;

	/** �R���g���[�� */
	protected TManagementSelectUnitController controller;

	/** �{�[�_�[��\�����邩 */
	protected boolean border = false;

	/**
	 * �R���X�g���N�^
	 */
	public TManagementSelectUnit() {
		this(false);
	}

	/**
	 * �R���X�g���N�^(�{�[�_�[�\��)
	 * 
	 * @param border
	 */
	public TManagementSelectUnit(boolean border) {

		this.border = border;

		// �R���|�[�l���g������
		initComponents();

		// �R���|�[�l���g�z�u
		allocateComponents();

		// �R���g���[������
		controller = createController();

	}

	/**
	 * �R���g���[������
	 * 
	 * @return controller
	 */
	public TManagementSelectUnitController createController() {
		return new TManagementSelectUnitController(this);
	}

	/**
	 * �R���|�[�l���g������
	 */
	public void initComponents() {
		cbo = new TComboBox();
		ctrlReferenceRangeUnits = new ArrayList<TReferenceRangeUnit>();
	}

	/**
	 * �R���|�[�l���g�z�u
	 */
	public void allocateComponents() {

		setSize(330, 80);

		// �R���{�{�b�N�X
		cbo.setLocation(0, 0);
		cbo.setSize(100, 20);
		add(cbo);

		if (border) {
			setLangMessageID("C10834"); // �Ǘ��I��
			setSize(365, 115);
			cbo.setLocation(15, 5);
		} else {
			this.titlePanel.setVisible(false);
			this.setBorder(null);
		}
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		cbo.setTabControlNo(tabControlNo);
		for (TReferenceRangeUnit unit : ctrlReferenceRangeUnits) {
			unit.setTabControlNo(tabControlNo);
		}
	}

	/**
	 * �I������Ă���Ǘ���Ԃ�
	 * 
	 * @return �I������Ă���Ǘ�
	 */
	public ManagementAngle getManagementAngle() {
		return controller.getManagementAngle();
	}

	/**
	 * �w�肳�ꂽ������Z�߂ĕԂ��B<br>
	 * �E�Ǘ��Ƃ��ĉ����I������Ă��邩<br>
	 * �E�J�n�R�[�h�A�I���R�[�h<br>
	 * �E�ʑI���R�[�h<br>
	 * ��Z�߂ĕԂ��B
	 * 
	 * @return searchConditino
	 */
	public ManagementAngleSearchCondition getManagementAngleSearchCondition() {
		return controller.getManagementAngleSearchCondition();
	}

	/**
	 * �I������Ă���͈͌����t�B�[���h��Ԃ�
	 * 
	 * @return �I������Ă���͈͌����t�B�[���h
	 */
	public TReferenceRangeUnit getReferenceRangeUnit() {
		return controller.getReferenceRangeUnit();
	}

	/**
	 * �w�肳�ꂽ������ݒ肷��
	 * 
	 * @param condition �w�肳�ꂽ����
	 */
	public void setManagementAngleSearchCondition(ManagementAngleSearchCondition condition) {
		controller.setManagementAngleSearchCondition(condition);
	}

	/**
	 * �召�`�F�b�N
	 * 
	 * @return true(��薳��) / false(�G���[����)
	 */
	public boolean isSmallerFrom() {
		return (Util.isSmallerThen(getReferenceRangeUnit().getReferenceRange().getCodeFrom(), getReferenceRangeUnit()
			.getReferenceRange().getCodeTo()));
	}

	/**
	 * �{�[�_�[�m�F
	 * 
	 * @return booean
	 */
	public boolean isBorder() {
		return border;
	}

	/**
	 * �{�[�_�[�Z�b�g
	 * 
	 * @param border
	 */
	public void setBorder(boolean border) {
		this.border = border;
	}
}