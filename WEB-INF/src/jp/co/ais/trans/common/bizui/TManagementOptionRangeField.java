package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * �Ǘ��R���|�[�l���g
 * 
 * @author AIS
 */
public class TManagementOptionRangeField extends TPanel implements TInterfaceLangMessageID {

	/** �V���A��UID */
	private static final long serialVersionUID = 6975993877695662983L;

	/** �����Ȃ� */
	public static final int VALUE_NONE = 0;

	/** �����R�[�h */
	public static final int VALUE_CUSTOMER = 1;

	/** �Ј��R�[�h */
	public static final int VALUE_EMP = 2;

	/** �Ǘ��P�R�[�h */
	public static final int VALUE_MANAGEMENT1 = 3;

	/** �Ǘ��Q�R�[�h */
	public static final int VALUE_MANAGEMENT2 = 4;

	/** �Ǘ��R�R�[�h */
	public static final int VALUE_MANAGEMENT3 = 5;

	/** �Ǘ��S�R�[�h */
	public static final int VALUE_MANAGEMENT4 = 6;

	/** �Ǘ��T�R�[�h */
	public static final int VALUE_MANAGEMENT5 = 7;

	/** �Ǘ��U�R�[�h */
	public static final int VALUE_MANAGEMENT6 = 8;

	/**
	 * �R���X�g���N�^
	 */
	public TManagementOptionRangeField() {

		super();

		initComponents();

		new TManagementOptionRangeFieldCtrl(this);
	}

	/**
	 * ��ʍ\�z
	 */
	protected void initComponents() {

		GridBagConstraints gridBagConstraints;

		basePanel = new TPanel();
		cmbManagement = new TComboBox();
		ctrlBeginManagement = new TManagementEnhancingField();
		ctrlEndManagement = new TManagementEnhancingField();

		// �J�n�R�[�h
		ctrlBeginManagement.setLangMessageID("C01012");
		// �I���R�[�h
		ctrlEndManagement.setLangMessageID("C01143");

		ctrlBeginManagement.addCallControl(new CallBackListener() {

			public void before() {
				// �����Ȃ� �{�^�����b�N
				if ((cmbManagement.getSelectedItemValue()).equals(VALUE_NONE)) {
					setBtnLock();
				}
				// �����
				else if ((cmbManagement.getSelectedItemValue()).equals(VALUE_CUSTOMER)) {
					ctrlBeginManagement.setManagementType(TManagementEnhancingField.TYPE_CUSTOMER);
				}
				// �Ј�
				else if ((cmbManagement.getSelectedItemValue()).equals(VALUE_EMP)) {
					ctrlBeginManagement.setManagementType(TManagementEnhancingField.TYPE_EMP);
				}
				// �Ǘ��R�[�h�P�`�U�I����
				else {
					switch (Util.avoidNullAsInt(cmbManagement.getSelectedItemValue())) {
						case VALUE_MANAGEMENT1:
							ctrlBeginManagement.setManagementType(TManagementField.TYPE_MANAGEMENT1);
							break;
						case VALUE_MANAGEMENT2:
							ctrlBeginManagement.setManagementType(TManagementField.TYPE_MANAGEMENT2);
							break;
						case VALUE_MANAGEMENT3:
							ctrlBeginManagement.setManagementType(TManagementField.TYPE_MANAGEMENT3);
							break;
						case VALUE_MANAGEMENT4:
							ctrlBeginManagement.setManagementType(TManagementField.TYPE_MANAGEMENT4);
							break;
						case VALUE_MANAGEMENT5:
							ctrlBeginManagement.setManagementType(TManagementField.TYPE_MANAGEMENT5);
							break;
						case VALUE_MANAGEMENT6:
							ctrlBeginManagement.setManagementType(TManagementField.TYPE_MANAGEMENT6);
							break;
					}
				}
			}

			public void after() {
				ctrlBeginManagement.setBeginCode("");
				ctrlBeginManagement.setEndCode(ctrlEndManagement.getValue());
				ctrlEndManagement.setBeginCode(ctrlBeginManagement.getValue());
				ctrlEndManagement.setEndCode("");
			}

		});

		ctrlEndManagement.addCallControl(new CallBackListener() {

			public void before() {
				if ((cmbManagement.getSelectedItemValue()).equals(VALUE_NONE)) {
					setBtnLock();
				}
				// �����
				else if ((cmbManagement.getSelectedItemValue()).equals(VALUE_CUSTOMER)) {
					ctrlEndManagement.setManagementType(TManagementEnhancingField.TYPE_CUSTOMER);
				}
				// �Ј�
				else if ((cmbManagement.getSelectedItemValue()).equals(VALUE_EMP)) {
					ctrlEndManagement.setManagementType(TManagementEnhancingField.TYPE_EMP);
				}
				// �Ǘ��R�[�h�P�`�U�I����
				else {
					switch (Util.avoidNullAsInt(cmbManagement.getSelectedItemValue())) {
						case VALUE_MANAGEMENT1:
							ctrlEndManagement.setManagementType(TManagementField.TYPE_MANAGEMENT1);
							break;
						case VALUE_MANAGEMENT2:
							ctrlEndManagement.setManagementType(TManagementField.TYPE_MANAGEMENT2);
							break;
						case VALUE_MANAGEMENT3:
							ctrlEndManagement.setManagementType(TManagementField.TYPE_MANAGEMENT3);
							break;
						case VALUE_MANAGEMENT4:
							ctrlEndManagement.setManagementType(TManagementField.TYPE_MANAGEMENT4);
							break;
						case VALUE_MANAGEMENT5:
							ctrlEndManagement.setManagementType(TManagementField.TYPE_MANAGEMENT5);
							break;
						case VALUE_MANAGEMENT6:
							ctrlEndManagement.setManagementType(TManagementField.TYPE_MANAGEMENT6);
							break;
					}
				}
			}

			public void after() {
				ctrlBeginManagement.setBeginCode("");
				ctrlBeginManagement.setEndCode(ctrlEndManagement.getValue());
				ctrlEndManagement.setBeginCode(ctrlBeginManagement.getValue());
				ctrlEndManagement.setEndCode("");
			}
		});

		setLayout(new GridBagLayout());

		basePanel.setLayout(new GridBagLayout());
		gridBagConstraints = new GridBagConstraints();
		add(basePanel, new GridBagConstraints());

		// �����l�ݒ�
		setBtnLock();
		cmbManagement.setModel(new DefaultComboBoxModel(new String[] { " " }));
		cmbManagement.setMaximumSize(new Dimension(110, 20));
		cmbManagement.setMinimumSize(new Dimension(110, 20));
		cmbManagement.setPreferredSize(new Dimension(110, 20));
		// �Ǘ���ύX����
		cmbManagement.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == 1) {
					if (cmbManagement.getSelectedIndex() == 0) {
						ctrlBeginManagement.setEditMode(false);
						ctrlEndManagement.setEditMode(false);
					} else {
						ctrlBeginManagement.setEditMode(true);
						ctrlEndManagement.setEditMode(true);
					}
				}
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(cmbManagement, gridBagConstraints);

		ctrlBeginManagement.setButtonSize(85);
		ctrlBeginManagement.setFieldSize(75);
		ctrlBeginManagement.setMaxLength(10);
		ctrlBeginManagement.setImeMode(false);
		ctrlBeginManagement.setNoticeSize(135);
		ctrlBeginManagement.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(ctrlBeginManagement, gridBagConstraints);

		ctrlEndManagement.setButtonSize(85);
		ctrlEndManagement.setFieldSize(75);
		ctrlEndManagement.setMaxLength(10);
		ctrlEndManagement.setImeMode(false);
		ctrlEndManagement.setNoticeSize(135);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(ctrlEndManagement, gridBagConstraints);

	}

	/**
	 * �ꊇ���̓t�B�[���h�T�C�Y�ݒ�
	 * 
	 * @param size �T�C�Y
	 */
	public void setFieldSize(int size) {
		ctrlBeginManagement.setFieldSize(size);
		ctrlEndManagement.setFieldSize(size);
	}

	/**
	 * �ꊇ���̃t�B�[���h�T�C�Y�ݒ�
	 * 
	 * @param size �T�C�Y
	 */
	public void setNoticeSize(int size) {
		ctrlBeginManagement.setNoticeSize(size);
		ctrlEndManagement.setNoticeSize(size);
	}

	/**
	 * �u�Ǘ��R���{�{�b�N�X�v�R���|�[�l���g��Ԃ�
	 * 
	 * @return �Ǘ��R���{�{�b�N�X
	 */
	public TComboBox getCmbManagement() {
		return this.cmbManagement;
	}

	/**
	 * �u�J�n�Ǘ��t�B�[���h�v�R���|�[�l���g��Ԃ�
	 * 
	 * @return �J�n�Ǘ��t�B�[���h
	 */
	public TManagementField getBeginTManagementField() {
		return this.ctrlBeginManagement;
	}

	/**
	 * �u�I���Ǘ��t�B�[���h�v�R���|�[�l���g��Ԃ�
	 * 
	 * @return �I���Ǘ��t�B�[���h
	 */
	public TManagementField getEndTManagementField() {
		return this.ctrlEndManagement;
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
	 * �Ǘ��R���{�{�b�N�X����C���f�b�N�X�l���擾
	 * 
	 * @return �I���s�̃C���f�b�N�X
	 */
	public int getSelectIndex() {
		return cmbManagement.getSelectedIndex();
	}

	/**
	 * �J�n�Ǘ��t�B�[���h����l���擾
	 * 
	 * @return �J�n�Ǘ��R�[�h
	 */
	public String getBeginManagementCode() {
		return ctrlBeginManagement.getField().getText();
	}

	/**
	 * �I���Ǘ��R�[�h�t�B�[���h����l���擾
	 * 
	 * @return �I���Ǘ��R�[�h
	 */
	public String getEndManagementCode() {
		return ctrlEndManagement.getField().getText();
	}

	/**
	 * �p�l���S�� �^�u�ړ����ԍ���ݒ肷��.
	 * 
	 * @param no
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		cmbManagement.setTabControlNo(no);
		ctrlBeginManagement.setTabControlNo(no);
		ctrlEndManagement.setTabControlNo(no);
	}

	/**
	 * �R�[�h�̑��݂̃`�F�b�N���s��Ȃ����[�h�֕ύX
	 */
	public void setNonCheckMode() {
		ctrlBeginManagement.setChekcMode(false);
		ctrlEndManagement.setChekcMode(false);
	}

	/**
	 * �{�^�� �����s��
	 */
	public void setBtnLock() {
		ctrlBeginManagement.getButton().setEnabled(false);
		ctrlBeginManagement.getField().setEditable(false);
		ctrlEndManagement.getButton().setEnabled(false);
		ctrlEndManagement.getField().setEditable(false);
	}

	/**
	 * �{�^�� ������
	 */
	public void setBtnUnLock() {
		ctrlBeginManagement.getButton().setEnabled(true);
		ctrlBeginManagement.getField().setEditable(true);
		ctrlEndManagement.getButton().setEnabled(true);
		ctrlEndManagement.getField().setEditable(true);
	}

	/**
	 * �p�l���S�� �����s��
	 */
	public void setLock() {
		cmbManagement.setEnabled(false);
		ctrlBeginManagement.getButton().setEnabled(false);
		ctrlBeginManagement.getField().setEditable(false);
		ctrlEndManagement.getButton().setEnabled(false);
		ctrlEndManagement.getField().setEditable(false);
	}

	/**
	 * �p�l���S�� ������
	 */
	public void setUnLock() {
		cmbManagement.setEnabled(true);
		ctrlBeginManagement.getButton().setEnabled(true);
		ctrlBeginManagement.getField().setEditable(true);
		ctrlEndManagement.getButton().setEnabled(true);
		ctrlEndManagement.getField().setEditable(true);
	}

	protected TComboBox cmbManagement;

	protected TManagementEnhancingField ctrlBeginManagement;

	protected TManagementEnhancingField ctrlEndManagement;

	protected TPanel basePanel;

	/**
	 * �I�����ꂽ�W�v�P�ʂ�Ԃ��B
	 * return �I�����ꂽ�W�v�P��
	 */
	public TransUtil.SumGroup getSelectedSumGroup() {

		int managementType = Integer.parseInt(cmbManagement.getSelectedItemValue().toString());
		// ��������
		if (VALUE_NONE == managementType) {
			return TransUtil.SumGroup.None;
		}
		// �����R�[�h
		else if (VALUE_CUSTOMER == managementType) {
			return TransUtil.SumGroup.Tri;
		}
		// �Ј��R�[�h
		else if (VALUE_EMP == managementType) {
			return TransUtil.SumGroup.Emp;
		}
		// �Ǘ�1�R�[�h
		else if (VALUE_MANAGEMENT1 == managementType) {
			return TransUtil.SumGroup.Knr1;
		}
		// �Ǘ�2�R�[�h
		else if (VALUE_MANAGEMENT2 == managementType) {
			return TransUtil.SumGroup.Knr2;
		}
		// �Ǘ�3�R�[�h
		else if (VALUE_MANAGEMENT3 == managementType) {
			return TransUtil.SumGroup.Knr3;
		}
		// �Ǘ�4�R�[�h
		else if (VALUE_MANAGEMENT4 == managementType) {
			return TransUtil.SumGroup.Knr4;
		}
		// �Ǘ�5�R�[�h
		else if (VALUE_MANAGEMENT5 == managementType) {
			return TransUtil.SumGroup.Knr5;
		}
		// �Ǘ�6�R�[�h
		else if (VALUE_MANAGEMENT6 == managementType) {
			return TransUtil.SumGroup.Knr6;
		}

		return null;
		
	}

}
