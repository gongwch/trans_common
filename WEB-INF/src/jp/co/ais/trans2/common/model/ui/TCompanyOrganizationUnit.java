package jp.co.ais.trans2.common.model.ui;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �g�D(��ЊK�w)�̏o�͒P�ʃ��j�b�g�R���|�[�l���g
 * 
 * @author AIS
 */
public class TCompanyOrganizationUnit extends TTitlePanel {

	/** �g�D���̕\�� */
	public static int DISPLAY_NAME_FLG = 0;

	/** �g�D�R���{�{�b�N�X */
	public TCompanyOrganizationComboBox cboCompanyOrganization;

	/** �K�w���x�� */
	public TLabelComboBox cboLevel;

	/** �z����Ѓp�l�� */
	public TPanel pnlLowCompany;

	/** �z�����僉�x�� */
	public TLabel lblLowCompany;

	/** �܂� */
	public TRadioButton rdoInclude;

	/** �܂܂Ȃ� */
	public TRadioButton rdoNotInclude;

	/** �{�^���O���[�v */
	public ButtonGroup btnGroup;

	/** ��ʉ�� */
	public TCompanyReference ctrlSuperiorCompany;

	/** ��� */
	public TCompanyReferenceRangeUnit ctrlCompany;

	/** �R���g���[�� */
	public TCompanyOrganizationUnitController controller;

	/**
	 * 
	 *
	 */
	public TCompanyOrganizationUnit() {
		this(true);
	}

	/**
	 * @param title �^�C�g���\�����ǂ���
	 */
	public TCompanyOrganizationUnit(boolean title) {

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
	public TCompanyOrganizationUnitController createController() {
		return new TCompanyOrganizationUnitController(this);
	}

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	protected void initComponents() {
		cboCompanyOrganization = new TCompanyOrganizationComboBox();
		cboLevel = new TLabelComboBox();
		pnlLowCompany = new TPanel();
		lblLowCompany = new TLabel();
		rdoInclude = new TRadioButton();
		rdoNotInclude = new TRadioButton();
		ctrlSuperiorCompany = new TCompanyReference();
		ctrlCompany = new TCompanyReferenceRangeUnit();
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
			setLangMessageID("C11922"); // �o�͒P��(��ЊK�w)
		} else {
			setLangMessageID("C11922"); // �o�͒P��(��ЊK�w)
			this.titlePanel.setVisible(false);
		}
		switch (DISPLAY_NAME_FLG) {
			case 0:
				// �g�D�R���{�{�b�N�X
				if (title) {
					cboCompanyOrganization.setLocation(30, 7);
				} else {
					cboCompanyOrganization.setLocation(30, 0);
				}
				add(cboCompanyOrganization);

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

				// �z����Ѓp�l��
				pnlLowCompany.setLayout(null);
				pnlLowCompany.setLangMessageID("C01281"); // �z�����
				pnlLowCompany.setSize(150, 50);
				if (title) {
					pnlLowCompany.setLocation(200, 5);
				} else {
					pnlLowCompany.setLocation(200, -2);
				}
				add(pnlLowCompany);

				// �܂�
				rdoInclude.setLangMessageID("C00461");
				rdoInclude.setSize(65, 20);
				rdoInclude.setLocation(10, 20);
				rdoInclude.setOpaque(false);
				pnlLowCompany.add(rdoInclude);

				// �܂܂Ȃ�
				rdoNotInclude.setLangMessageID("C00460");
				rdoNotInclude.setSize(80, 20);
				rdoNotInclude.setLocation(75, 20);
				rdoNotInclude.setOpaque(false);
				pnlLowCompany.add(rdoNotInclude);

				btnGroup.add(rdoInclude);
				btnGroup.add(rdoNotInclude);
				break;
			// �V���C�A�E�g
			default:
				// �g�D�R���{�{�b�N�X
				if (title) {
					cboCompanyOrganization.setLocation(4, 7);
				} else {
					cboCompanyOrganization.setLocation(4, 0);
				}
				cboCompanyOrganization.setLabelSize(25);
				cboCompanyOrganization.setComboSize(160);
				cboCompanyOrganization.setLangMessageID("C00334");
				add(cboCompanyOrganization);

				// �K�w���x���R���{�{�b�N�X
				cboLevel.setLangMessageID("");
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
				lblLowCompany.setSize(50, 20);
				lblLowCompany.setLangMessageID("C00904");

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
					lblLowCompany.setLocation(6, 33);
				} else {
					lblLowCompany.setLocation(6, 25);
					rdoInclude.setLocation(70, 25);
					rdoNotInclude.setLocation(145, 25);
				}
				add(lblLowCompany);
				add(rdoInclude);
				add(rdoNotInclude);

				btnGroup.add(rdoInclude);
				btnGroup.add(rdoNotInclude);
				break;

		}

		// ��ʉ��
		ctrlSuperiorCompany.btn.setLangMessageID("C01487"); // ��ʉ��
		if (title) {
			ctrlSuperiorCompany.setLocation(15, 60);
		} else {
			ctrlSuperiorCompany.setLocation(15, 50);
		}
		add(ctrlSuperiorCompany);

		// ���
		if (title) {
			ctrlCompany.setLocation(15, 80);
		} else {
			ctrlCompany.setLocation(15, 70);
		}
		ctrlCompany.getReferenceRange().getFieldFrom().btn.setLangMessageID("C11366"); // �J�n���
		ctrlCompany.getReferenceRange().getFieldTo().btn.setLangMessageID("C11367"); // �I�����
		add(ctrlCompany);

	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		cboCompanyOrganization.setTabControlNo(tabControlNo);
		cboLevel.setTabControlNo(tabControlNo);
		rdoInclude.setTabControlNo(tabControlNo);
		rdoNotInclude.setTabControlNo(tabControlNo);
		ctrlSuperiorCompany.setTabControlNo(tabControlNo);
		ctrlCompany.setTabControlNo(tabControlNo);
	}

	/**
	 * �o�͒P�ʂ�Ԃ�
	 * 
	 * @return �o�͒P��
	 */
	public CompanyOutputCondition getCompanyOutputCondition() {
		return controller.getCompanyOutputCondition();
	}

	/**
	 * �o�͒P�ʂ�ݒ肷��
	 * 
	 * @param condition �o�͒P��
	 */
	public void setCompanyOutputCondition(CompanyOutputCondition condition) {
		controller.setCompanyOutputCondition(condition);
	}

	/**
	 * �召�`�F�b�N
	 * 
	 * @return true(��薳��) / false(�G���[����)
	 */
	public boolean isCompanySmallerFrom() {
		return controller.isCompanySmallerFrom();
	}

}
