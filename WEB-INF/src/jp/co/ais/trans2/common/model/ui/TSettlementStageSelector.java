package jp.co.ais.trans2.common.model.ui;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * ���Z�i�K�I���R���|�[�l���g
 * 
 * @author AIS
 */
public class TSettlementStageSelector extends TTitlePanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 5437867894826776661L;

	/** �R���g���[�� */
	protected TSettlementStageSelectorController controller;

	/** �ʏ� */
	public TRadioButton rdoNormal;

	/** ���Z */
	public TRadioButton rdoSettlement;

	/** ���Z�i�K */
	public TNumericField nmSettlementLevel;

	/** ���Z�i�K */
	public TLabel lblSettlementLevel;

	/**
	 * 
	 *
	 */
	public TSettlementStageSelector() {

		initComponents();

		allocateComponents();

		// �R���g���[������
		controller = new TSettlementStageSelectorController(this);

	}

	/**
	 * �R���|�[�l���g�̏�����
	 */
	public void initComponents() {
		rdoNormal = new TRadioButton();
		rdoSettlement = new TRadioButton();
		nmSettlementLevel = new TNumericField();
		lblSettlementLevel = new TLabel();
	}

	/**
	 * �R���|�[�l���g�̔z�u
	 */
	public void allocateComponents() {

		setLangMessageID("C00610"); // ���Z�敪
		setSize(150, 75);

		// �ʏ�
		rdoNormal.setLangMessageID("C00372"); // �ʏ�
		rdoNormal.setSize(100, 20);
		rdoNormal.setLocation(15, 5);
		add(rdoNormal);

		// ���Z
		rdoSettlement.setLangMessageID("C00142"); // ���Z
		rdoSettlement.setSize(50, 20);
		rdoSettlement.setLocation(15, 30);
		add(rdoSettlement);

		// ���Z�i�K
		nmSettlementLevel.setSize(20, 20);
		nmSettlementLevel.setLocation(70, 30);
		nmSettlementLevel.setMaxLength(1);
		nmSettlementLevel.setPositiveOnly(true);
		add(nmSettlementLevel);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdoNormal);
		bg.add(rdoSettlement);

		lblSettlementLevel.setLangMessageID("C00374"); // ����
		lblSettlementLevel.setSize(50, 20);
		lblSettlementLevel.setLocation(95, 30);
		add(lblSettlementLevel);

	}

	/**
	 * ���Z�i�K��Ԃ�
	 * 
	 * @return ���Z�i�K
	 */
	public int getSettlementStage() {
		return controller.getSettlementStage();
	}

	/**
	 * ���Z�i�K��Ԃ�
	 * 
	 * @param settlementLevel ���Z�i�K
	 */
	public void setSettlementStage(int settlementLevel) {
		controller.setSettlementStage(settlementLevel);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		rdoNormal.setTabControlNo(tabControlNo);
		rdoSettlement.setTabControlNo(tabControlNo);
		nmSettlementLevel.setTabControlNo(tabControlNo);
	}

	/**
	 * ���͂�����������Ԃ�
	 * 
	 * @return true(����) / false(�G���[)
	 */
	public boolean isCorrect() {
		return controller.isCorrect();
	}

}