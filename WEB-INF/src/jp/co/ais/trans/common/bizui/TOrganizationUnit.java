package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * �g�D�R���|�[�l���g
 */
public class TOrganizationUnit extends TTitlePanel {

	/** �V���A��UID */
	private static final long serialVersionUID = -3240004347898566637L;

	/** ��Ѓ^�C�v */
	public static final int TYPE_COMPANY = 1;

	/** ����^�C�v */
	public static final int TYPE_DEPARTMENT = 2;

	/** �A������^�C�v */
	public static final int TYPE_CONSOLIDATED_DEPARTMENT = 2;

	/** �R���g���[���N���X */
	private TOrganizationUnitCtrl ctrl;

	/** �o�͒P�ʃp�l�� */
	protected TPanel pnlOutputUnit;

	/** �g�D�R�[�h */
	protected TLabelComboBox ctrlOrganizationCode;

	/** �K�w���x�� */
	protected TLabelComboBox ctrlHierarchicalLevel;

	/** ��ʃR�[�h */
	protected TButtonField ctrlUpperCode;

	/** �R�[�h */
	protected TButtonField ctrlCode;

	/** �z���p�l�����W�I�{�^���i�܂ށj */
	protected TRadioButton rdoInclude;

	/** �z���p�l�����W�I�{�^���i�܂܂Ȃ��j */
	protected TRadioButton rdoExclude;

	/** �z���p�l�����W�I�{�^���O���[�v */
	protected ButtonGroup btngrpSubordinateSection;

	/** �z���p�l�� */
	protected TPanel pnlSubordinate;

	/**
	 * �R���X�g���N�^
	 */
	public TOrganizationUnit() {
		this(TYPE_DEPARTMENT);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �R���g���[���^�C�v
	 */
	public TOrganizationUnit(int type) {
		super();

		initComponents();

		// TODO ��ЁA�A����������H
		switch (type) {
			// ����^�C�v
			case TYPE_DEPARTMENT:

				this.ctrl = new DepartmentUnitCtrl(this);
				this.ctrl.initLangMessageID();
				this.ctrl.initPanel();

				break;
			// ��Ѓ^�C�v
			case TYPE_COMPANY:

				this.ctrl = new CompanyUnitCtrl(this);
				this.ctrl.initLangMessageID();
				this.ctrl.initPanel();

				break;

			default:
				return;
		}
	}

	/**
	 * ��ʍ\�z
	 */
	private void initComponents() {
		GridBagConstraints gc;

		pnlOutputUnit = new TPanel();
		ctrlOrganizationCode = new TLabelComboBox();
		ctrlHierarchicalLevel = new TLabelComboBox();
		ctrlUpperCode = new TButtonField();
		ctrlCode = new TButtonField();
		rdoInclude = new TRadioButton();
		rdoExclude = new TRadioButton();
		btngrpSubordinateSection = new ButtonGroup();
		pnlSubordinate = new TPanel();

		// �o�͒P��
		pnlOutputUnit.setLayout(new GridBagLayout());
		TGuiUtil.setComponentSize(pnlOutputUnit, new Dimension(440, 125));
		pnlOutputUnit.setLocation(0, 0);
		add(pnlOutputUnit);

		// �g�D�R�[�h
		ctrlOrganizationCode.setComboSize(90);
		ctrlOrganizationCode.setLabelSize(60);
		ctrlOrganizationCode.getComboBox().addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent evt) {
				if (ItemEvent.SELECTED == evt.getStateChange()) {
					ctrl.changeOrgCode();
				}
			}
		});

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = new Insets(2, 6, 2, 0);// 52-0
		pnlOutputUnit.add(ctrlOrganizationCode, gc);

		// �K�w����
		ctrlHierarchicalLevel.setComboSize(75);
		ctrlHierarchicalLevel.setLabelSize(60);
		ctrlHierarchicalLevel.getComboBox().addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent evt) {
				if (ItemEvent.SELECTED == evt.getStateChange()) {
					ctrl.changeHierarchicalLevelItem();
				}
			}
		});

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 1;
		gc.anchor = GridBagConstraints.WEST;
		gc.insets = new Insets(0, 6, 3, 0);// 60-0
		pnlOutputUnit.add(ctrlHierarchicalLevel, gc);

		// ���W�I�{�^��(�܂�)
		btngrpSubordinateSection.add(rdoInclude);
		rdoInclude.setSelected(true);
		rdoInclude.setSize(65, 20);
		rdoInclude.setLocation(10, 20);
		rdoInclude.setOpaque(false);
		pnlSubordinate.add(rdoInclude, gc);

		// ���W�I�{�^���i�܂܂Ȃ��j
		btngrpSubordinateSection.add(rdoExclude);
		rdoExclude.setSize(80, 20);
		rdoExclude.setLocation(75, 20);
		rdoExclude.setOpaque(false);
		pnlSubordinate.add(rdoExclude, gc);

		// �z���p�l��
		pnlSubordinate.setLayout(null);
		TGuiUtil.setComponentSize(pnlSubordinate, new Dimension(200, 50));
		gc = new GridBagConstraints();
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridheight = 2;
		gc.insets = new Insets(0, 50, 3, 0);
		pnlOutputUnit.add(pnlSubordinate, gc);

		// ��ʃR�[�h
		ctrlUpperCode.setButtonSize(85);
		ctrlUpperCode.setFieldSize(120);
		ctrlUpperCode.setMaxLength(10);
		ctrlUpperCode.setNoticeSize(200);
		ctrlUpperCode.setImeMode(false);
		ctrlUpperCode.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!ctrlUpperCode.isValueChanged()) {
					return true;
				}
				if (!ctrlUpperCode.getField().isEditable()) {
					return true;
				}

				boolean b = ctrl.setupName(ctrlUpperCode, ctrl.getUpperOrgFlag());

				ctrl.changeUpper();

				return b;
			}
		});

		ctrlUpperCode.addButtonActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				ctrl.showRefDialog(ctrlUpperCode, ctrl.getUpperOrgFlag());

				if (ctrlUpperCode.getField().isValueChanged()) {
					ctrl.changeUpper();
				}
			}
		});

		// �f�t�H���g�ݒ�
		ctrlUpperCode.getButton().setEnabled(false);
		ctrlUpperCode.getField().setEditable(false);

		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 2;
		gc.gridwidth = 3;
		gc.insets = new Insets(0, 0, 5, 0);
		pnlOutputUnit.add(ctrlUpperCode, gc);

		// �R�[�h
		ctrlCode.setButtonSize(85);
		ctrlCode.setFieldSize(120);
		ctrlCode.setMaxLength(10);
		ctrlCode.setNoticeSize(200);
		ctrlCode.setImeMode(false);
		ctrlCode.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!ctrlCode.isValueChanged()) {
					return true;
				}
				if (!ctrlCode.getField().isEditable()) {
					return true;
				}

				return ctrl.setupName(ctrlCode, ctrl.getOrgFlag());
			}
		});

		ctrlCode.getButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				ctrl.showRefDialog(ctrlCode, ctrl.getOrgFlag());
			}
		});
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 3;
		gc.gridwidth = 2;
		gc.insets = new Insets(0, 0, 16, 0);
		pnlOutputUnit.add(ctrlCode, gc);
	}

	/**
	 * �T�C�Y���Z�b�g(S�T�C�Y)
	 */
	public void setSizeSmall() {
		this.setMaximumSize(new Dimension(350, 130));
		this.setMinimumSize(new Dimension(350, 130));
		this.setPreferredSize(new Dimension(350, 130));

		GridBagConstraints gridBagConstraints;

		pnlOutputUnit.setLayout(new GridBagLayout());
		TGuiUtil.setComponentSize(pnlOutputUnit, new Dimension(350, 125));
		pnlOutputUnit.setLocation(0, 0);

		// �g�D�R�[�h
		ctrlOrganizationCode.setComboSize(73);
		ctrlOrganizationCode.setLabelSize(65);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(6, 2, 2, 0);
		pnlOutputUnit.add(ctrlOrganizationCode, gridBagConstraints);

		// �K�w���x��
		ctrlHierarchicalLevel.setComboSize(73);
		ctrlHierarchicalLevel.setLabelSize(65);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 2, 0, 0);
		pnlOutputUnit.add(ctrlHierarchicalLevel, gridBagConstraints);

		// �z������I��
		TGuiUtil.setComponentSize(pnlSubordinate, new Dimension(170, 50));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.insets = new Insets(2, 2, 3, 0);
		pnlOutputUnit.add(pnlSubordinate, gridBagConstraints);

		// ��ʕ���
		ctrlUpperCode.setButtonSize(85);
		ctrlUpperCode.setFieldSize(85);
		ctrlUpperCode.setNoticeSize(125);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(2, 0, 5, 0);
		pnlOutputUnit.add(ctrlUpperCode, gridBagConstraints);

		// ����
		ctrlCode.setButtonSize(85);
		ctrlCode.setFieldSize(85);
		ctrlCode.setNoticeSize(125);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 0, 16, 0);
		pnlOutputUnit.add(ctrlCode, gridBagConstraints);
	}

	/**
	 * �T�C�Y���Z�b�g(M�T�C�Y)
	 */
	public void setSizeMiddle() {
		this.setMaximumSize(new Dimension(385, 130));
		this.setMinimumSize(new Dimension(385, 130));
		this.setPreferredSize(new Dimension(385, 130));

		GridBagConstraints gridBagConstraints;

		pnlOutputUnit.setLayout(new GridBagLayout());
		TGuiUtil.setComponentSize(pnlOutputUnit, new Dimension(385, 125));
		pnlOutputUnit.setLocation(0, 0);

		// �g�D�R�[�h
		ctrlOrganizationCode.setComboSize(90);
		ctrlOrganizationCode.setLabelSize(60);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(4, 27, 1, 0);
		pnlOutputUnit.add(ctrlOrganizationCode, gridBagConstraints);

		// �K�w�R�[�h
		ctrlHierarchicalLevel.setComboSize(75);
		ctrlHierarchicalLevel.setLabelSize(60);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 27, 4, 10);
		pnlOutputUnit.add(ctrlHierarchicalLevel, gridBagConstraints);

		// �z������I��
		TGuiUtil.setComponentSize(pnlSubordinate, new Dimension(170, 50));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.insets = new Insets(0, 6, 3, 0);
		pnlOutputUnit.add(pnlSubordinate, gridBagConstraints);

		// ��ʕ���
		ctrlUpperCode.setButtonSize(85);
		ctrlUpperCode.setFieldSize(85);
		ctrlUpperCode.setMaxLength(10);
		ctrlUpperCode.setNoticeSize(180);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlOutputUnit.add(ctrlUpperCode, gridBagConstraints);

		// ����
		ctrlCode.setButtonSize(85);
		ctrlCode.setFieldSize(85);
		ctrlCode.setMaxLength(10);
		ctrlCode.setNoticeSize(180);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 0, 16, 0);
		pnlOutputUnit.add(ctrlCode, gridBagConstraints);

	}

	/**
	 * �T�C�Y���Z�b�g(L�T�C�Y)
	 */
	public void setSizeLarge() {
		this.setMaximumSize(new Dimension(440, 125));
		this.setMinimumSize(new Dimension(440, 125));
		this.setPreferredSize(new Dimension(440, 125));

		GridBagConstraints gridBagConstraints;

		pnlOutputUnit.setLayout(new GridBagLayout());
		TGuiUtil.setComponentSize(pnlOutputUnit, new Dimension(440, 120));
		pnlOutputUnit.setLocation(0, 0);

		// �g�D�R�[�h
		ctrlOrganizationCode.setComboSize(75);
		ctrlOrganizationCode.setLabelSize(85);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(6, 0, 2, 0);
		pnlOutputUnit.add(ctrlOrganizationCode, gridBagConstraints);

		// �K�w�R�[�h
		ctrlHierarchicalLevel.setComboSize(75);
		ctrlHierarchicalLevel.setLabelSize(85);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlOutputUnit.add(ctrlHierarchicalLevel, gridBagConstraints);

		// �z������I��
		TGuiUtil.setComponentSize(pnlSubordinate, new Dimension(170, 50));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.insets = new Insets(2, 10, 3, 0);
		pnlOutputUnit.add(pnlSubordinate, gridBagConstraints);

		// ��ʕ���
		ctrlUpperCode.setButtonSize(85);
		ctrlUpperCode.setFieldSize(120);
		ctrlUpperCode.setMaxLength(10);
		ctrlUpperCode.setNoticeSize(200);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlOutputUnit.add(ctrlUpperCode, gridBagConstraints);

		// ����
		ctrlCode.setButtonSize(85);
		ctrlCode.setFieldSize(120);
		ctrlCode.setMaxLength(10);
		ctrlCode.setNoticeSize(200);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 16, 0);
		pnlOutputUnit.add(ctrlCode, gridBagConstraints);
	}

	/**
	 * �T�C�Y���Z�b�g(��Зp)
	 */
	public void setSizeCompany() {
		this.setMaximumSize(new Dimension(455, 135));
		this.setMinimumSize(new Dimension(455, 135));
		this.setPreferredSize(new Dimension(455, 135));

		GridBagConstraints gridBagConstraints;

		pnlOutputUnit.setMaximumSize(new Dimension(455, 130));
		pnlOutputUnit.setMinimumSize(new Dimension(455, 130));
		pnlOutputUnit.setPreferredSize(new Dimension(455, 130));

		// �g�D�R�[�h
		ctrlOrganizationCode.setComboSize(75);
		ctrlOrganizationCode.setLabelSize(85);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(12, 25, 3, 0);
		pnlOutputUnit.add(ctrlOrganizationCode, gridBagConstraints);

		// �K�w�R�[�h
		ctrlHierarchicalLevel.setComboSize(75);
		ctrlHierarchicalLevel.setLabelSize(85);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 25, 0, 0);
		pnlOutputUnit.add(ctrlHierarchicalLevel, gridBagConstraints);

		// �z����БI��
		TGuiUtil.setComponentSize(pnlSubordinate, new Dimension(160, 50));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.insets = new Insets(2, 10, 3, 0);
		pnlOutputUnit.add(pnlSubordinate, gridBagConstraints);

		// ��ʉ��
		ctrlUpperCode.setButtonSize(110);
		ctrlUpperCode.setFieldSize(120);
		ctrlUpperCode.setMaxLength(10);
		ctrlUpperCode.setNoticeSize(190);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(2, 0, 3, 0);
		pnlOutputUnit.add(ctrlUpperCode, gridBagConstraints);

		// ���
		ctrlCode.setButtonSize(110);
		ctrlCode.setFieldSize(120);
		ctrlCode.setMaxLength(10);
		ctrlCode.setNoticeSize(190);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 16, 0);
		pnlOutputUnit.add(ctrlCode, gridBagConstraints);
	}

	/**
	 * �^�u�ړ����ԍ���g�D�R���|�[�l���g�S�̂ɐݒ肷��.
	 * 
	 * @param no
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		ctrlOrganizationCode.setTabControlNo(no);
		ctrlHierarchicalLevel.setTabControlNo(no);
		rdoInclude.setTabControlNo(no);
		rdoExclude.setTabControlNo(no);
		ctrlUpperCode.setTabControlNo(no);
		ctrlCode.setTabControlNo(no);
	}

	/**
	 * �Ȗڑ̌n�R�[�h�̃Z�b�g
	 * 
	 * @param code �Ȗڑ̌n�R�[�h
	 */
	public void setItemSystemCode(String code) {
		ctrl.setItemSystemCode(code);
	}

	/**
	 * �o�͒P�ʃp�l���擾
	 * 
	 * @return �o�͒P�ʃp�l��
	 */
	public TPanel getOutputUnitPanel() {
		return this;
	}

	/**
	 * �g�D�R�[�h�擾
	 * 
	 * @return �g�D�R�[�h
	 */
	public TLabelComboBox getOrganizationComboBox() {
		return ctrlOrganizationCode;
	}

	/**
	 * �K�w���x���擾
	 * 
	 * @return �K�w���x��
	 */
	public TLabelComboBox getHierarchicalLevelComboBox() {
		return ctrlHierarchicalLevel;
	}

	/**
	 * ��ʃR�[�h�擾
	 * 
	 * @return ��ʃR�[�h
	 */
	public TButtonField getUpperCodeField() {
		return ctrlUpperCode;
	}

	/**
	 * �R�[�h�擾
	 * 
	 * @return �R�[�h
	 */
	public TButtonField getCodeField() {
		return ctrlCode;
	}

	/**
	 * �z���p�l���擾
	 * 
	 * @return ����p�l��
	 */
	public TPanel getSubordinate() {
		return pnlSubordinate;
	}

	/**
	 * �z�����W�I�{�^���i�܂ށj�擾
	 * 
	 * @return ���W�I�{�^���i�܂ށj
	 */
	public TRadioButton getRdoInclude() {
		return rdoInclude;
	}

	/**
	 * �z�����W�I�{�^���i�܂܂Ȃ�)�擾
	 * 
	 * @return ���W�I�{�^���i�܂܂Ȃ��j
	 */
	public TRadioButton getRdoExclude() {
		return rdoExclude;
	}

	/**
	 * �g�D�R�[�h�擾
	 * 
	 * @return �g�D�R�[�h
	 */
	public String getOrganizationCode() {
		return (String) ctrlOrganizationCode.getComboBox().getSelectedItemValue();
	}

	/**
	 * �K�w���x���擾
	 * 
	 * @return �K�w���x��
	 */
	public int getHierarchicalLevel() {
		return ctrlHierarchicalLevel.getComboBox().getSelectedIndex();
	}

	/**
	 * ��ʃR�[�h�擾
	 * 
	 * @return ��ʃR�[�h
	 */
	public String getUpperCode() {
		return ctrlUpperCode.getValue();
	}

	/**
	 * �R�[�h�擾
	 * 
	 * @return �R�[�h
	 */
	public String getCode() {
		return ctrlCode.getValue();
	}

	/**
	 * �z�����W�I�{�^���u�܂ށv��I�����Ă��邩�ǂ���
	 * 
	 * @return true:�u�܂ށv��I���Afalse:�u�܂܂Ȃ��v��I��
	 */
	public boolean isIncludeSelected() {
		return rdoInclude.isSelected();
	}

	/**
	 * �������\�ɑ΂���J��������Ԃ��܂��B<br>
	 * �g�D�A��ʕ���A����A�z��������܂�/�܂܂Ȃ��A�K�w���x�����܂Ƃ߂ĕԂ��܂��B
	 * 
	 * @return �������\�ɑ΂���J������
	 */
	public DisclosureRegulationOfFinancialStatement getDisclosureRegulationOfFinancialStatement() {

		DisclosureRegulationOfFinancialStatement drfs = new DisclosureRegulationOfFinancialStatement();
		drfs.setOrganizationCode(getOrganizationCode());
		drfs.setDepartmentLevel(getHierarchicalLevel());
		drfs.setUpperDepartmentCode(getUpperCode());
		drfs.setDepartmentCode(getCode());
		drfs.setIncludeLowerDepartment(isIncludeSelected());

		return drfs;
	}

}
