package jp.co.ais.trans2.common.model.ui;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.department.*;

/**
 * �g�D(����K�w)�̏o�͒P�ʃ��j�b�g�R���|�[�l���g TODO �ʑI���͖��Ή�
 * 
 * @author AIS
 */
public class TDepartmentOrganizationUnit extends TTitlePanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -1175300671844325183L;

	/** �g�D���̕\�� */
	public static int DISPLAY_NAME_FLG = 0;

	/** �g�D�R���{�{�b�N�X */
	public TDepartmentOrganizationComboBox cboDepartmentOrganization;

	/** �K�w���x�����̕\���R���{�{�b�N�X */
	public TLabelComboBox cboLevel;

	/** �z������p�l�� */
	public TPanel pnlLowDepartment;

	/** �z�����僉�x�� */
	public TLabel lblLowDepartment;

	/** �܂� */
	public TRadioButton rdoInclude;

	/** �܂܂Ȃ� */
	public TRadioButton rdoNotInclude;

	/** �{�^���O���[�v */
	public ButtonGroup btnGroup;

	/** ��ʕ��� */
	public TDepartmentReference ctrlSuperiorDepartment;

	/** ���� */
	public TDepartmentReferenceRangeUnit ctrlDepartment;

	/** �R���g���[�� */
	public TDepartmentOrganizationUnitController controller;

	/** �ێ��L�[ */
	protected String saveKey = null;

	/**
	 * 
	 *
	 */
	public TDepartmentOrganizationUnit() {
		this(true);
	}

	/**
	 * @param title �^�C�g���\�����ǂ���
	 */
	public TDepartmentOrganizationUnit(boolean title) {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents(title);

		// �R���g���[������
		controller = createController();

	}

	/**
	 * �R���g���[������
	 * 
	 * @return �R���g���[��
	 */
	public TDepartmentOrganizationUnitController createController() {
		return new TDepartmentOrganizationUnitController(this);
	}

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	protected void initComponents() {
		cboDepartmentOrganization = new TDepartmentOrganizationComboBox();
		cboLevel = new TLabelComboBox();
		pnlLowDepartment = new TPanel();
		lblLowDepartment = new TLabel();
		rdoInclude = new TRadioButton();
		rdoNotInclude = new TRadioButton();
		ctrlSuperiorDepartment = new TDepartmentReference();
		ctrlDepartment = new TDepartmentReferenceRangeUnit();
		btnGroup = new ButtonGroup();
	}

	static {
		try {
			DISPLAY_NAME_FLG = Util.avoidNullAsInt(ClientConfig.getProperty("trans.output.unit.display.name.flg"));
		} catch (Exception ex) {
			// �G���[�Ȃ�
		}
	}

	/**
	 * �R���|�[�l���g��z�u����
	 * 
	 * @param title �^�C�g���\�����ǂ���
	 */
	protected void allocateComponents(boolean title) {
		setSize(365, 165);

		if (title) {
			setLangMessageID("C01159");
		} else {
			setLangMessageID("C01159");
			this.titlePanel.setVisible(false);
		}
		switch (DISPLAY_NAME_FLG) {
			case 0:
				// �g�D�R���{�{�b�N�X
				if (title) {
					cboDepartmentOrganization.setLocation(30, 7);
				} else {
					cboDepartmentOrganization.setLocation(30, 0);
				}
				add(cboDepartmentOrganization);

				// �K�w���x���R���{�{�b�N�X
				cboLevel.setLangMessageID("C00060");
				cboLevel.setLabelSize(60);
				cboLevel.setSize(165, 20);
				cboLevel.setComboSize(100);
				if (title) {
					cboLevel.setLocation(30, 32);
				} else {
					cboLevel.setLocation(30, 25);
				}
				add(cboLevel);

				// �z������p�l��
				pnlLowDepartment.setLayout(null);
				pnlLowDepartment.setLangMessageID("C00904");
				pnlLowDepartment.setSize(150, 50);
				if (title) {
					pnlLowDepartment.setLocation(200, 5);
				} else {
					pnlLowDepartment.setLocation(200, -2);
				}
				add(pnlLowDepartment);

				// �܂�
				rdoInclude.setLangMessageID("C00461");
				rdoInclude.setSize(65, 20);
				rdoInclude.setLocation(10, 20);
				rdoInclude.setOpaque(false);
				pnlLowDepartment.add(rdoInclude);

				// �܂܂Ȃ�
				rdoNotInclude.setLangMessageID("C00460");
				rdoNotInclude.setSize(80, 20);
				rdoNotInclude.setLocation(75, 20);
				rdoNotInclude.setOpaque(false);
				pnlLowDepartment.add(rdoNotInclude);

				btnGroup.add(rdoInclude);
				btnGroup.add(rdoNotInclude);
				break;

			// �V���C�A�E�g
			default:
				// �g�D�R���{�{�b�N�X
				if (title) {
					cboDepartmentOrganization.setLocation(4, 7);
				} else {
					cboDepartmentOrganization.setLocation(4, 0);
				}
				cboDepartmentOrganization.setLabelSize(25);
				cboDepartmentOrganization.setComboSize(160);
				cboDepartmentOrganization.setLangMessageID("C00334");
				add(cboDepartmentOrganization);

				// �K�w���x���R���{�{�b�N�X
				cboLevel.setLabelSize(0);
				cboLevel.setSize(165, 20);
				cboLevel.setComboSize(160);
				if (title) {
					cboLevel.setLocation(194, 7);
				} else {
					cboLevel.setLocation(194, 0);
				}
				add(cboLevel);

				// ���x��
				lblLowDepartment.setSize(50, 20);
				lblLowDepartment.setLangMessageID("C00904");

				// �܂�
				rdoInclude.setLangMessageID("C00461");
				rdoInclude.setSize(65, 20);
				rdoInclude.setLocation(70, 33);
				rdoInclude.setOpaque(false);

				// �܂܂Ȃ�
				rdoNotInclude.setLangMessageID("C00460");
				rdoNotInclude.setSize(80, 20);
				rdoNotInclude.setOpaque(false);

				if (title) {
					rdoInclude.setLocation(70, 33);
					rdoNotInclude.setLocation(145, 33);
					lblLowDepartment.setLocation(6, 33);
				} else {
					lblLowDepartment.setLocation(6, 25);
					rdoInclude.setLocation(70, 25);
					rdoNotInclude.setLocation(145, 25);
				}
				add(lblLowDepartment);
				add(rdoInclude);
				add(rdoNotInclude);

				btnGroup.add(rdoInclude);
				btnGroup.add(rdoNotInclude);
				break;
		}

		// ��ʕ���
		ctrlSuperiorDepartment.btn.setLangMessageID("C00719");
		if (title) {
			ctrlSuperiorDepartment.setLocation(15, 60);
		} else {
			ctrlSuperiorDepartment.setLocation(15, 50);
		}
		add(ctrlSuperiorDepartment);

		// ����
		if (title) {
			ctrlDepartment.setLocation(15, 80);
		} else {
			ctrlDepartment.setLocation(15, 70);
		}
		ctrlDepartment.getReferenceRange().getFieldFrom().btn.setLangMessageID("C10347");
		ctrlDepartment.getReferenceRange().getFieldTo().btn.setLangMessageID("C10169");
		add(ctrlDepartment);

	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {

		cboDepartmentOrganization.setTabControlNo(tabControlNo);
		cboDepartmentOrganization.setTabControlNo(tabControlNo);
		cboLevel.setTabControlNo(tabControlNo);
		rdoInclude.setTabControlNo(tabControlNo);
		rdoNotInclude.setTabControlNo(tabControlNo);
		ctrlSuperiorDepartment.setTabControlNo(tabControlNo);
		ctrlDepartment.setTabControlNo(tabControlNo);
	}

	/**
	 * �o�͒P�ʂ�Ԃ�
	 * 
	 * @return �o�͒P��
	 */
	public DepartmentOutputCondition getDepartmentOutputCondition() {
		return controller.getDepartmentOutputCondition();
	}

	/**
	 * �o�͒P�ʂ�ݒ肷��
	 * 
	 * @param condition �o�͒P��
	 */
	public void setDepartmentOutputCondition(DepartmentOutputCondition condition) {
		controller.setDepartmentOutputCondition(condition);
	}

	/**
	 * �召�`�F�b�N
	 * 
	 * @return true(��薳��) / false(�G���[����)
	 */
	public boolean isDepartmentSmallerFrom() {
		return controller.isDepartmentSmallerFrom();
	}

	/**
	 * �ێ��L�[�̎擾
	 * 
	 * @return saveKey �ێ��L�[
	 */
	public String getSaveKey() {
		return saveKey;
	}

	/**
	 * �ێ��L�[�̐ݒ�
	 * 
	 * @param saveKey �ێ��L�[
	 */
	public void setSaveKey(String saveKey) {
		this.saveKey = saveKey;
	}

	/**
	 * �o�͒P�ʏ����ێ��̐ݒ�ɂ�蕜��
	 */
	public void restoreDepartmentSetting() {
		controller.restoreSetting();
	}

	/**
	 * �o�͒P�ʏ��� �ݒ�ێ�
	 */
	public void saveDepartmentSetting() {
		// ��������鎞�ɁA�ێ��L�[������΁A���Y�������N���C�A���g�Ɏ���
		controller.saveSetting();
	}
}
