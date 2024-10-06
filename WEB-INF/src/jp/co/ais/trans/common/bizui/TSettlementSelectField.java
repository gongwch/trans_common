package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���Z�i�K�t�B�[���h
 * 
 * @author moriya
 */
public class TSettlementSelectField extends TPanel {

	/** �V���A��UID */
	private static final long serialVersionUID = 1L;

	/** �{�^�����C�A�E�g */
	private int layoutType = HORIZONTAL;

	/** �� */
	public static final int HORIZONTAL = 1;

	/** �c */
	public static final int VERTICAL = 2;

	/** �R���g���[���N���X */
	protected TSettlementSelectFieldCtrl ctrl;

	/** �x�[�X�p�l�� */
	protected TPanel pnlBase;

	/** �ʏ� */
	public TRadioButton rdoNormally;

	/** ���Z */
	public TRadioButton rdoSettlement;

	/** ���x���i�����j */
	public TLabel lblSettlementDivision;

	/** ���Z�i�K�\�� */
	public TNumericField numSettlementDivision;

	/** �{�^���O���[�v */
	public ButtonGroup btngrpSettlementDivision;

	/**
	 * �R���X�g���N�^
	 */
	public TSettlementSelectField() {
		super();
		initComponents();
		this.ctrl = new TSettlementSelectFieldCtrl(this);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type
	 */
	public TSettlementSelectField(int type) {
		super();
		layoutType = type;
		initComponents();
		this.ctrl = new TSettlementSelectFieldCtrl(this);
	}

	/**
	 * ��ʍ\�z
	 */
	protected void initComponents() {

		GridBagConstraints gridBagConstraints;

		pnlBase = new TPanel();
		rdoNormally = new TRadioButton();
		rdoSettlement = new TRadioButton();
		lblSettlementDivision = new TLabel();
		numSettlementDivision = new TNumericField();
		btngrpSettlementDivision = new ButtonGroup();

		pnlBase.setLangMessageID("C00610");
		rdoNormally.setLangMessageID("C00372");
		btngrpSettlementDivision.add(rdoNormally);
		btngrpSettlementDivision.add(rdoSettlement);
		rdoSettlement.setSelected(true);
		rdoSettlement.setLangMessageID("C00142");
		lblSettlementDivision.setLangMessageID("C00374");
		numSettlementDivision.setMaxLength(1);
		numSettlementDivision.setPositiveOnly(true);

		switch (layoutType) {
			// ���^�C�v
			case HORIZONTAL:

				pnlBase.setLayout(new GridBagLayout());

				pnlBase.setMaximumSize(new Dimension(210, 45));
				pnlBase.setMinimumSize(new Dimension(210, 45));
				pnlBase.setPreferredSize(new Dimension(210, 45));

				rdoNormally.setMargin(new Insets(0, 0, 0, 0));
				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(0, 0, 5, 0);
				pnlBase.add(rdoNormally, gridBagConstraints);

				rdoSettlement.setMargin(new Insets(0, 0, 0, 0));

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 1;
				gridBagConstraints.gridy = 0;
				gridBagConstraints.insets = new Insets(0, 15, 5, 0);
				pnlBase.add(rdoSettlement, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 3;
				gridBagConstraints.gridy = 0;
				gridBagConstraints.insets = new Insets(0, 5, 5, 0);
				pnlBase.add(lblSettlementDivision, gridBagConstraints);

				numSettlementDivision.setMaximumSize(new Dimension(20, 20));
				numSettlementDivision.setMinimumSize(new Dimension(20, 20));
				numSettlementDivision.setPreferredSize(new Dimension(20, 20));
				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 2;
				gridBagConstraints.gridy = 0;
				gridBagConstraints.insets = new Insets(0, 5, 5, 0);
				pnlBase.add(numSettlementDivision, gridBagConstraints);
				break;
			case VERTICAL:

				pnlBase.setLayout(new GridBagLayout());

				pnlBase.setMaximumSize(new Dimension(150, 75));
				pnlBase.setMinimumSize(new Dimension(150, 75));
				pnlBase.setPreferredSize(new Dimension(150, 75));

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.insets = new Insets(5, 0, 0, 3);
				pnlBase.add(rdoNormally, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 0;
				gridBagConstraints.gridy = 1;
				gridBagConstraints.insets = new Insets(5, 0, 0, 3);
				pnlBase.add(rdoSettlement, gridBagConstraints);

				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 2;
				gridBagConstraints.gridy = 1;
				gridBagConstraints.insets = new Insets(5, 5, 1, 5);
				pnlBase.add(lblSettlementDivision, gridBagConstraints);

				numSettlementDivision.setMaximumSize(new Dimension(20, 20));
				numSettlementDivision.setMinimumSize(new Dimension(20, 20));
				numSettlementDivision.setPreferredSize(new Dimension(20, 20));
				gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.gridx = 1;
				gridBagConstraints.gridy = 1;
				pnlBase.add(numSettlementDivision, gridBagConstraints);
				break;
		}

		gridBagConstraints = new GridBagConstraints();
		add(pnlBase, gridBagConstraints);

		// �X�V�敪��ύX����
		rdoNormally.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				// CTRL���C���X�^���X�������O�ɌĂяo���ꂽ�ꍇ�͏������Ȃ�
				if (Util.isNullOrEmpty(ctrl)) {
					return;
				}

				ctrl.radioSettlementChange();
			}
		});

		// ���Z�敪��ύX����
		rdoSettlement.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				// CTRL���C���X�^���X�������O�ɌĂяo���ꂽ�ꍇ�͏������Ȃ�
				if (Util.isNullOrEmpty(ctrl)) {
					return;
				}
				ctrl.radioSettlementChange();
			}
		});
	}

	/**
	 * �^�u�ړ����ԍ���ݒ�
	 * 
	 * @param no �^�u���ԍ�
	 */
	public void setTabControlNo(int no) {
		rdoNormally.setTabControlNo(no);
		rdoSettlement.setTabControlNo(no);
		numSettlementDivision.setTabControlNo(no);
	}

	/**
	 * �p�l���̎擾
	 * 
	 * @return TPanel
	 */
	public TPanel getPnlBase() {
		return pnlBase;
	}

	/**
	 * ���W�I�{�^���i�ʏ�j�̎擾
	 * 
	 * @return TRadioButton
	 */
	public TRadioButton getRdoNormally() {
		return rdoNormally;
	}

	/**
	 * ���W�I�{�^���i���Z�j�̎擾
	 * 
	 * @return TRadioButton
	 */
	public TRadioButton getRdoSettlement() {
		return rdoSettlement;
	}

	/**
	 * ���Z�i�K�t�B�[���h�̎擾
	 * 
	 * @return ���Z�i�K�t�B�[���h
	 */
	public TNumericField getNumSettlementDivision() {
		return numSettlementDivision;
	}

	/**
	 * �ʏ킪�I������Ă��邩�ǂ���
	 * 
	 * @return true:�ʏ�Afalse:���Z
	 */
	public boolean isNormallySelected() {
		return rdoNormally.isSelected();
	}

	/**
	 * ���Z�i�K���擾
	 * 
	 * @return ���Z�i�K
	 */
	public int getSettlementDivision() {
		return numSettlementDivision.getInt();
	}

	/**
	 * ���x�����̎擾
	 * 
	 * @return ����
	 */
	public TLabel getLblSettlementDivision() {
		return lblSettlementDivision;
	}

}
