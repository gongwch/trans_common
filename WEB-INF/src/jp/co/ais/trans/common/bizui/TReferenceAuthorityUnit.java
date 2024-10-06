package jp.co.ais.trans.common.bizui;

import java.awt.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.Util;

/**
 * �Q�ƌ����t�B�[���h�R���|�[�l���g
 * 
 * @author ookawara
 */
public class TReferenceAuthorityUnit extends TPanel implements TInterfaceLangMessageID {

	/** �V���A��UID */
	private static final long serialVersionUID = 6975993877695662983L;

	/**
	 * �R���X�g���N�^
	 */
	public TReferenceAuthorityUnit() {
		super();

		initComponents();
	}

	/**
	 * ��ʍ\�z
	 */
	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		basePanel = new TPanel();
		ctrlInputDepartment = new TInputDepartmentField();
		ctrlInputPerson = new TInputPersonField();

		// �����l���Z�b�g
		ctrlInputPerson.setInputDepartmentCode(Util.avoidNull(ctrlInputDepartment.getField().getText()));

		ctrlInputDepartment.addCallControl(new CallBackListener() {

			public void after() {
				ctrlInputPerson.setInputDepartmentCode(Util.avoidNull(ctrlInputDepartment.getField().getText()));
			}
		});

		setLayout(new GridBagLayout());

		basePanel.setLayout(new GridBagLayout());
		gridBagConstraints = new GridBagConstraints();
		add(basePanel, new GridBagConstraints());

		ctrlInputDepartment.setButtonSize(85);
		ctrlInputDepartment.setFieldSize(75);
		ctrlInputDepartment.setMaxLength(10);
		ctrlInputDepartment.setImeMode(false);
		ctrlInputDepartment.setNoticeSize(135);
		ctrlInputDepartment.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(ctrlInputDepartment, gridBagConstraints);

		ctrlInputPerson.setButtonSize(85);
		ctrlInputPerson.setFieldSize(75);
		ctrlInputPerson.setMaxLength(10);
		ctrlInputPerson.setImeMode(false);
		ctrlInputPerson.setNoticeSize(135);
		ctrlInputPerson.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(ctrlInputPerson, gridBagConstraints);
	}

	/**
	 * �T�C�Y���Z�b�g(Small:�`�[�T�C�Y)
	 */
	public void setSizeSmall() {
		this.setMaximumSize(new Dimension(310, 40));
		this.setMinimumSize(new Dimension(310, 40));
		this.setPreferredSize(new Dimension(310, 40));

		GridBagConstraints gridBagConstraints;

		basePanel.setLayout(new GridBagLayout());

		basePanel.setMaximumSize(new Dimension(310, 40));
		basePanel.setMinimumSize(new Dimension(310, 40));
		basePanel.setPreferredSize(new Dimension(310, 40));
		ctrlInputDepartment.setButtonSize(85);
		ctrlInputDepartment.setFieldSize(75);
		ctrlInputDepartment.setNoticeSize(135);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 10, 0, 20);
		basePanel.add(ctrlInputDepartment, gridBagConstraints);

		ctrlInputPerson.setButtonSize(85);
		ctrlInputPerson.setFieldSize(75);
		ctrlInputPerson.setNoticeSize(135);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 10, 0, 20);
		basePanel.add(ctrlInputPerson, gridBagConstraints);
	}

	/**
	 * �ꊇ���̓t�B�[���h�T�C�Y�ݒ�
	 * 
	 * @param size �T�C�Y
	 */
	public void setFieldSize(int size) {
		ctrlInputDepartment.setFieldSize(size);
		ctrlInputPerson.setFieldSize(size);
	}

	/**
	 * �ꊇ���̃t�B�[���h�T�C�Y�ݒ�
	 * 
	 * @param size �T�C�Y
	 */
	public void setNoticeSize(int size) {
		ctrlInputDepartment.setNoticeSize(size);
		ctrlInputPerson.setNoticeSize(size);
	}

	/**
	 * �u���͕���v�R���|�[�l���g��Ԃ�
	 * 
	 * @return ���͕���t�B�[���h
	 */
	public TInputDepartmentField getTInputDepartmentField() {
		return this.ctrlInputDepartment;
	}

	/**
	 * �u���͎ҁv�R���|�[�l���g��Ԃ�
	 * 
	 * @return ���͎҃t�B�[���h
	 */
	public TInputPersonField getTInputPersonField() {
		return this.ctrlInputPerson;
	}

	/**
	 * �u�p�l���v�R���|�[�l���g��Ԃ�
	 * 
	 * @return �p�l��
	 */
	public TPanel getTBasePanel() {
		return this.basePanel;
	}

	/**
	 * �Ȗڃt�B�[���h����l���擾
	 * 
	 * @return �ȖڃR�[�h
	 */
	public String getItemCode() {
		return ctrlInputDepartment.getField().getText();
	}

	/**
	 * �⏕�Ȗڃt�B�[���h����l���擾
	 * 
	 * @return �⏕�ȖڃR�[�h
	 */
	public String getSubItemCode() {
		return ctrlInputPerson.getField().getText();
	}

	/**
	 * �p�l���S�� �^�u�ړ����ԍ���ݒ肷��.
	 * 
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 * @param no
	 */
	public void setTabControlNo(int no) {
		ctrlInputDepartment.setTabControlNo(no);
		ctrlInputPerson.setTabControlNo(no);
	}

	/**
	 * �R�[�h�̑��݂̃`�F�b�N���s��Ȃ����[�h�֕ύX
	 */
	public void setNonCheckMode() {
		ctrlInputDepartment.setChekcMode(false);
		ctrlInputPerson.setChekcMode(false);
	}

	private TInputDepartmentField ctrlInputDepartment;

	private TInputPersonField ctrlInputPerson;

	private TPanel basePanel;

}
