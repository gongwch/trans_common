package jp.co.ais.trans2.common.model.ui;

import javax.swing.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * �Ȗڑg�D���x���͈̔̓��j�b�g<br>
 * (�\�����x��+�Ȗڑ̌n+�Ȗڔ͈�+�ȖڔC�ӎw��)
 * 
 * @author AIS
 */
public class TItemOrganizationUnit extends TTitlePanel {

	/** �\�����x�� */
	public TItemLevelChooser ctrlItemLevelChooser;

	/** �Ȗڑ̌n */
	public TItemOrganizationReference ctrlItemOrganization;

	/** �Ȗڔ͈� */
	public TItemGroupRangeUnit ctrlItemRange;

	/** �R���g���[�� */
	public TItemOrganizationUnitController controller;

	/** �^�C�g�� default:�Ȗڔ͈� */
	protected String title = "C01009";

	/**
	 * �R���X�g���N�^
	 */
	public TItemOrganizationUnit() {
		this(true);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param isTitle �^�C�g���\�����ǂ���
	 */
	public TItemOrganizationUnit(boolean isTitle) {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents(isTitle);

		// �R���g���[������
		controller = createController();
	}

	/**
	 * �R���g���[������
	 * 
	 * @return �R���g���[��
	 */
	public TItemOrganizationUnitController createController() {
		return new TItemOrganizationUnitController(this);
	}

	/**
	 * �R���|�[�l���g������������
	 */
	protected void initComponents() {
		ctrlItemLevelChooser = new TItemLevelChooser(SwingConstants.HORIZONTAL);
		ctrlItemOrganization = new TItemOrganizationReference();
		ctrlItemRange = new TItemGroupRangeUnit();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 * 
	 * @param isTitle �^�C�g���\�����ǂ���
	 */
	protected void allocateComponents(boolean isTitle) {

		int x = 15;
		int y = 5;
		int yy = 0;

		setSize(365, isTitle ? 260 : 240);

		if (isTitle) {
			setLangMessageID(title);
		} else {
			setLangMessageID(title);
			this.titlePanel.setVisible(false);
		}

		// �\�����x��
		if (isTitle) {
			ctrlItemLevelChooser.setLocation(x, y);
		} else {
			ctrlItemLevelChooser.setLocation(x, yy + y);
		}
		add(ctrlItemLevelChooser);

		yy = ctrlItemLevelChooser.getY() + ctrlItemLevelChooser.getHeight() + y;

		// �Ȗڑ̌n
		if (isTitle) {
			ctrlItemOrganization.setLocation(x, yy);
		} else {
			ctrlItemOrganization.setLocation(x, yy);
		}
		add(ctrlItemOrganization);

		yy = ctrlItemOrganization.getY() + ctrlItemOrganization.getHeight() + y;

		// �Ȗڔ͈�
		if (isTitle) {
			ctrlItemRange.setLocation(x, yy);
		} else {
			ctrlItemRange.setLocation(x, yy);
		}
		add(ctrlItemRange);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		ctrlItemLevelChooser.setTabControlNo(tabControlNo);
		ctrlItemOrganization.setTabControlNo(tabControlNo);
		ctrlItemRange.setTabControlNo(tabControlNo);
	}

	/**
	 * �^�C�g����ݒ肷��
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * �^�C�g�����擾����
	 * 
	 * @return title
	 */
	public String getTitle() {
		return this.title;
	}

}
