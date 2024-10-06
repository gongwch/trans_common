package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;

/**
 * �ȖڃR���|�[�l���g
 * 
 * @author ookawara
 */
public class TAccountItemUnit extends TPanel implements TInterfaceLangMessageID {

	/** �⏕�Ȗږ��g�p���[�h(�\���͂���) */
	protected boolean subItemEnableMode = true;

	/** ����Ȗږ��g�p���[�h(�\���͂���) */
	protected boolean breakDownItemEnableMode = true;

	/** �x�[�X�p�l�� */
	protected TPanel basePanel;

	/** �Ȗڃt�B�[���h */
	protected TItemField ctrlItem;

	/** �⏕�Ȗڃt�B�[���h */
	protected TSubItemField ctrlSubItem;

	/** ����Ȗڃt�B�[���h */
	protected TBreakDownItemField ctrlBreakDownItem;

	/** ���������Z�b�g */
	private UnitInputParameter inputParameter;

	/** �������ʃZ�b�g */
	protected AccountItemOutputParameter outputParameter;

	/** ��InputParameter���X�g */
	private List<AccountItemInputParameter> paramList;

	/**
	 * �R���X�g���N�^
	 */
	public TAccountItemUnit() {
		super();

		paramList = Collections.synchronizedList(new LinkedList<AccountItemInputParameter>());

		initComponents();

		// ��������
		setEnableSubItem(false);

		// ��ЃR���g���[�����
		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();

		// �Ȗڔ͈͓���Ȗڔ�\��
		getTBreakDownItemField().setVisible(compInfo.isUseBreakDownItem());
	}

	/**
	 * ��ʍ\�z
	 */
	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		basePanel = new TPanel();
		ctrlItem = new TItemField();
		ctrlSubItem = new TSubItemField();
		ctrlBreakDownItem = new TBreakDownItemField();

		// �����pBean�̃Z�b�g
		paramList.add(ctrlItem.getInputParameter());
		paramList.add(ctrlSubItem.getInputParameter());
		paramList.add(ctrlBreakDownItem.getInputParameter());
		inputParameter = new UnitInputParameter(paramList);

		// �l�擾�pBean�̐���
		outputParameter = new AccountItemOutputParameter();

		ctrlItem.addCallControl(new CallBackListener() {

			public void after() {
				if (!subItemEnableMode) {
					return;
				}

				if (ctrlItem.getOutputParameter().isIncludeSubItem()) {
					setEnableSubItem(true);
					inputParameter.setItemCode(getItemCode());
				} else {
					setEnableSubItem(false);
				}
			}
		});

		ctrlSubItem.addCallControl(new CallBackListener() {

			public void after() {
				if (!breakDownItemEnableMode) {
					return;
				}

				// �󕶎��w��̏ꍇ�A�ȖڃR�[�h�̍Đݒ�
				if (ctrlSubItem.isEmpty() && ctrlSubItem.isValueChanged()) {
					ctrlItem.resetValue();
					setEnableBreakDownItem(false);
					return;
				}

				if (ctrlSubItem.getOutputParameter().isIncludeBreakDownItem()) {
					setEnableBreakDownItem(true);
					inputParameter.setItemCode(getItemCode());
					inputParameter.setSubItemCode(getSubItemCode());
				} else {
					setEnableBreakDownItem(false);
				}
			}
		});

		ctrlBreakDownItem.addCallControl(new CallBackListener() {

			public void after() {

				// �󕶎��w��̏ꍇ�A�⏕�ȖڃR�[�h�̍Đݒ�
				if (ctrlBreakDownItem.isEmpty() && ctrlBreakDownItem.isValueChanged()) {
					ctrlSubItem.resetValue();
				}
			}
		});

		// �l�擾�pBEAN��ݒ�
		ctrlItem.setOutputParameter(outputParameter);
		ctrlSubItem.setOutputParameter(outputParameter);
		ctrlBreakDownItem.setOutputParameter(outputParameter);

		setLayout(new GridBagLayout());

		basePanel.setLayout(new GridBagLayout());
		gridBagConstraints = new GridBagConstraints();
		add(basePanel, new GridBagConstraints());

		ctrlItem.setButtonSize(85);
		ctrlItem.setFieldSize(75);
		ctrlItem.setMaxLength(10);
		ctrlItem.setImeMode(false);
		ctrlItem.setNoticeSize(135);
		ctrlItem.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(ctrlItem, gridBagConstraints);

		ctrlSubItem.setButtonSize(85);
		ctrlSubItem.setFieldSize(75);
		ctrlSubItem.setMaxLength(10);
		ctrlSubItem.setImeMode(false);
		ctrlSubItem.setNoticeSize(135);
		ctrlSubItem.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(ctrlSubItem, gridBagConstraints);

		ctrlBreakDownItem.setButtonSize(85);
		ctrlBreakDownItem.setFieldSize(75);
		ctrlBreakDownItem.setMaxLength(10);
		ctrlBreakDownItem.setImeMode(false);
		ctrlBreakDownItem.setNoticeSize(135);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		basePanel.add(ctrlBreakDownItem, gridBagConstraints);
	}

	/**
	 * �T�C�Y���Z�b�g(Small:�`�[�T�C�Y)
	 */
	public void setSizeSmall() {
		this.setMaximumSize(new Dimension(310, 60));
		this.setMinimumSize(new Dimension(310, 60));
		this.setPreferredSize(new Dimension(310, 60));

		GridBagConstraints gridBagConstraints;

		basePanel.setLayout(new GridBagLayout());

		basePanel.setMaximumSize(new Dimension(310, 60));
		basePanel.setMinimumSize(new Dimension(310, 60));
		basePanel.setPreferredSize(new Dimension(310, 60));
		ctrlItem.setButtonSize(85);
		ctrlItem.setFieldSize(75);
		ctrlItem.setNoticeSize(135);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 10, 0, 20);
		basePanel.add(ctrlItem, gridBagConstraints);

		ctrlSubItem.setButtonSize(85);
		ctrlSubItem.setFieldSize(75);
		ctrlSubItem.setNoticeSize(135);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 10, 0, 20);
		basePanel.add(ctrlSubItem, gridBagConstraints);

		ctrlBreakDownItem.setButtonSize(85);
		ctrlBreakDownItem.setFieldSize(75);
		ctrlBreakDownItem.setNoticeSize(135);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 10, 0, 20);
		basePanel.add(ctrlBreakDownItem, gridBagConstraints);
	}

	/**
	 * �p�l���T�C�Y�ݒ�
	 * 
	 * @param size �T�C�Y
	 */
	public void setPanelSize(Dimension size) {

		TGuiUtil.setComponentSize(basePanel, size);
	}

	/**
	 * �ꊇ�{�^���T�C�Y�ݒ�
	 * 
	 * @param size �T�C�Y
	 */
	public void setButtonSize(int size) {
		ctrlItem.setButtonSize(size);
		ctrlSubItem.setButtonSize(size);
		ctrlBreakDownItem.setButtonSize(size);
	}

	/**
	 * �ꊇ���̓t�B�[���h�T�C�Y�ݒ�
	 * 
	 * @param size �T�C�Y
	 */
	public void setFieldSize(int size) {
		ctrlItem.setFieldSize(size);
		ctrlSubItem.setFieldSize(size);
		ctrlBreakDownItem.setFieldSize(size);
	}

	/**
	 * �ꊇ���̃t�B�[���h�T�C�Y�ݒ�
	 * 
	 * @param size �T�C�Y
	 */
	public void setNoticeSize(int size) {
		ctrlItem.setNoticeSize(size);
		ctrlSubItem.setNoticeSize(size);
		ctrlBreakDownItem.setNoticeSize(size);
	}

	/**
	 * �u�Ȗځv�R���|�[�l���g��Ԃ�
	 * 
	 * @return �Ȗڃt�B�[���h
	 */
	public TItemField getTItemField() {
		return this.ctrlItem;
	}

	/**
	 * �u�⏕�Ȗځv�R���|�[�l���g��Ԃ�
	 * 
	 * @return �⏕�Ȗڃt�B�[���h
	 */
	public TSubItemField getTSubItemField() {
		return this.ctrlSubItem;
	}

	/**
	 * �u����Ȗځv�R���|�[�l���g��Ԃ�
	 * 
	 * @return ����Ȗڃt�B�[���h
	 */
	public TBreakDownItemField getTBreakDownItemField() {
		return this.ctrlBreakDownItem;
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
	 * �����pBEAN�R���|�[�l���g��Ԃ�
	 * 
	 * @return inputParameter �����pBEAN
	 */
	public AccountItemInputParameter getInputParameter() {
		return this.inputParameter;
	}

	/**
	 * �l�擾�pBEAN�R���|�[�l���g��Ԃ�
	 * 
	 * @return outputParameter �l�擾�pBEAN
	 */
	public AccountItemOutputParameter getOutputParameter() {
		return this.outputParameter;
	}

	/**
	 * �Ȗڃt�B�[���h����l���擾
	 * 
	 * @return �ȖڃR�[�h
	 */
	public String getItemCode() {
		return ctrlItem.getField().getText();
	}

	/**
	 * �Ȗڃt�B�[���h�֒l�Z�b�g.<br>
	 * �Ȗڂ̗L���ŕ⏕�Ȗڂ̓��͐�����s��.
	 * 
	 * @param code �ȖڃR�[�h
	 */
	public void setItemCode(String code) {
		ctrlItem.setValue(code);

		ctrlSubItem.getButton().setEnabled(this.outputParameter.isIncludeSubItem());
		ctrlSubItem.getField().setEditable(this.outputParameter.isIncludeSubItem());
	}

	/**
	 * �⏕�Ȗڃt�B�[���h����l���擾
	 * 
	 * @return �⏕�ȖڃR�[�h
	 */
	public String getSubItemCode() {
		return ctrlSubItem.getField().getText();
	}

	/**
	 * �⏕�Ȗڃt�B�[���h�֒l�Z�b�g.<br>
	 * �⏕�Ȗڂ̗L���œ���Ȗڂ̓��͐�����s��.
	 * 
	 * @param code �⏕�ȖڃR�[�h
	 */
	public void setSubItemCode(String code) {
		ctrlSubItem.setValue(code);

		ctrlBreakDownItem.getButton().setEnabled(this.outputParameter.isIncludeBreakDownItem());
		ctrlBreakDownItem.getField().setEditable(this.outputParameter.isIncludeBreakDownItem());
	}

	/**
	 * ����Ȗڃt�B�[���h����l���擾
	 * 
	 * @return ����ȖڃR�[�h
	 */
	public String getBreakDownItemCode() {
		return ctrlBreakDownItem.getField().getText();
	}

	/**
	 * ����Ȗڃt�B�[���h�֒l�Z�b�g.
	 * 
	 * @param code ����ȖڃR�[�h
	 */
	public void setBreakDownItemCode(String code) {

		ctrlBreakDownItem.setValue(code);
	}

	/**
	 * �l���N���A����.<br>
	 * InputParameter�́A���̂܂�
	 */
	public void clearCode() {
		ctrlItem.clear();
	}

	/**
	 * �p�l���S�� �^�u�ړ����ԍ���ݒ肷��.
	 * 
	 * @param no �^�u��
	 * @see TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		ctrlItem.setTabControlNo(no);
		ctrlSubItem.setTabControlNo(no);
		ctrlBreakDownItem.setTabControlNo(no);
	}

	/**
	 * �ȖڑI�����̉�ʐ���
	 * 
	 * @param enableSubItem true:�⏕�Ȗڂ���, false:�⏕�Ȗږ���
	 */
	protected void setEnableSubItem(boolean enableSubItem) {
		ctrlSubItem.getField().setText("");
		ctrlSubItem.getField().setEditable(enableSubItem);
		ctrlSubItem.getNotice().setText("");
		ctrlSubItem.getButton().setEnabled(enableSubItem);

		setEnableBreakDownItem(false);
	}

	/**
	 * �⏕�ȖڑI�����̉�ʐ���
	 * 
	 * @param enableBDItem true:����Ȗڂ���, false:����Ȗږ���
	 */
	protected void setEnableBreakDownItem(boolean enableBDItem) {
		ctrlBreakDownItem.getField().setText("");
		ctrlBreakDownItem.getField().setEditable(enableBDItem);
		ctrlBreakDownItem.getNotice().setText("");
		ctrlBreakDownItem.getButton().setEnabled(enableBDItem);
	}

	/**
	 * �R�[�h�̑��݂̃`�F�b�N���s��Ȃ����[�h�֕ύX
	 */
	public void setNonCheckMode() {
		ctrlItem.setCheckMode(false);
		ctrlSubItem.setCheckMode(false);
		ctrlBreakDownItem.setChekcMode(false);
	}

	/**
	 * �⏕�Ȗږ��g�p���[�h
	 * 
	 * @return true:�g�p
	 */
	public boolean isSubItemEnableMode() {
		return subItemEnableMode;
	}

	/**
	 * �⏕�Ȗږ��g�p���[�h
	 * 
	 * @param subItemEnableMode true:�g�p
	 */
	public void setSubItemEnableMode(boolean subItemEnableMode) {
		this.subItemEnableMode = subItemEnableMode;
	}

	/**
	 * ����Ȗږ��g�p���[�h
	 * 
	 * @return true:�g�p
	 */
	public boolean isBreakDownItemEnableMode() {
		return breakDownItemEnableMode;
	}

	/**
	 * ����Ȗږ��g�p���[�h
	 * 
	 * @param breakDownItemEnableMode true:�g�p
	 */
	public void setBreakDownItemEnableMode(boolean breakDownItemEnableMode) {
		this.breakDownItemEnableMode = breakDownItemEnableMode;
	}

	/**
	 * ����R�[�h��ݒ肷��
	 * 
	 * @param depCode ����R�[�h
	 */
	public void setDepartmentCode(String depCode) {
		this.ctrlItem.setDepartmentCode(depCode);
	}

	/**
	 * @param isControll true:����\
	 * @see jp.co.ais.trans.common.gui.TButtonField#setEditMode(boolean)
	 */
	public void setEditMode(boolean isControll) {
		this.ctrlItem.setEditMode(isControll);
	}

	/**
	 * ���͉\�ȏ�Ԃ��ǂ���
	 * 
	 * @return true:���͉\
	 */
	public boolean isEditable() {
		return this.ctrlItem.isEditable();
	}

}
