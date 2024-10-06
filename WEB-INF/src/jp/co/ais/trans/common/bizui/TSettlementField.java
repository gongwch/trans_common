package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * ���Z�t�B�[���h
 * 
 * @author moriya
 */
public class TSettlementField extends TPanel {

	private static final long serialVersionUID = 1L;

	/** �R���g���[���N���X */
	private TSettlementFieldCtrl ctrl;

	/** �x�[�X�p�l�� */
	private TPanel pnlBase;

	/** ���Z�d��`�F�b�N�{�b�N�X */
	private TCheckBox chkClosingEntry;

	/** ���Z�d��񐔕\�� */
	private TNumericField ctrlClosingAccountsStage;

	/** �`�[���t�̎擾 */
	private Date slipDate;

	/**
	 * �R���X�g���N�^
	 */
	public TSettlementField() {
		super();
		this.ctrl = new TSettlementFieldCtrl(this);
		initComponents();
	}

	/**
	 * ��ʍ\�z
	 */
	private void initComponents() {

		GridBagConstraints gridBagConstraints;

		pnlBase = new TPanel();
		chkClosingEntry = new TCheckBox();
		ctrlClosingAccountsStage = new TNumericField();

		pnlBase.setLayout(new GridBagLayout());
		pnlBase.setMaximumSize(new Dimension(100, 25));
		pnlBase.setMinimumSize(new Dimension(100, 25));
		pnlBase.setPreferredSize(new Dimension(100, 25));
		gridBagConstraints = new GridBagConstraints();
		add(pnlBase, gridBagConstraints);

		chkClosingEntry.setLangMessageID("C00143");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlBase.add(chkClosingEntry, gridBagConstraints);

		ctrlClosingAccountsStage.setEnabled(true);
		ctrlClosingAccountsStage.setEditable(false);
		ctrlClosingAccountsStage.setMaxLength(1);
		ctrlClosingAccountsStage.setMaximumSize(new Dimension(20, 20));
		ctrlClosingAccountsStage.setMinimumSize(new Dimension(20, 20));
		ctrlClosingAccountsStage.setPreferredSize(new Dimension(20, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		pnlBase.add(ctrlClosingAccountsStage, gridBagConstraints);

		// �u���Z�d��v�`�F�b�N���̏���
		chkClosingEntry.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (!ctrl.chkClosingEntryMouseClicked()) {
					chkClosingEntry.setSelected(false);
				}
			}
		});

	}

	/**
	 * �`�[���tsetter
	 * 
	 * @return Date
	 */
	public Date getSlipDate() {
		return slipDate;
	}

	/**
	 * �`�[���tgetter
	 * 
	 * @param slipDate
	 */
	public void setSlipDate(Date slipDate) {
		this.slipDate = slipDate;
	}

	/**
	 * �p�l��setter
	 * 
	 * @return TPanel
	 */
	public TPanel getBasePanel() {
		return pnlBase;
	}

	/**
	 * �p�l��getter
	 * 
	 * @param pnlBase
	 */
	public void setBasePanel(TPanel pnlBase) {
		this.pnlBase = pnlBase;
	}

	/**
	 * ���Z�d��`�F�b�N�{�b�N�X�擾
	 * 
	 * @return ���Z�d��`�F�b�N�{�b�N�X
	 */
	public TCheckBox getClosingEntry() {
		return chkClosingEntry;
	}

	/**
	 * ���Z�d��񐔕\���t�B�[���h�擾
	 * 
	 * @return ���Z�d��񐔕\���t�B�[���h
	 */
	public TNumericField getClosingAccountsStage() {
		return ctrlClosingAccountsStage;
	}

	/**
	 * �`�F�b�N�{�b�N�X���I������Ă��邩�ǂ���.
	 * 
	 * @return true:�I������Ă���
	 */
	public boolean isSelected() {
		return chkClosingEntry.isSelected();
	}

	/**
	 * �t�B�[���h�֒l��ݒ�
	 * 
	 * @param value
	 */
	public void setValue(Integer value) {
		ctrlClosingAccountsStage.setValue(value);
	}

	/**
	 * �l�̎擾
	 * 
	 * @return �l
	 */
	public int getValue() {
		return ctrlClosingAccountsStage.getInt();
	}

	/**
	 * �^�u�ړ����ԍ���ݒ�
	 * 
	 * @param no �^�u���ԍ�
	 */
	public void setTabControlNo(int no) {
		chkClosingEntry.setTabControlNo(no);
	}
}
