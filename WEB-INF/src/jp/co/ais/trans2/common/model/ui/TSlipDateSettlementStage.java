package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.model.ui.slip.*;

/**
 * �`�[���t�ƌ��Z�i�K�R���|�[�l���g
 * 
 * @author AIS
 */
public class TSlipDateSettlementStage extends TPanel {

	/** �`�[���t */
	public TSlipDate slipDate;

	/** �`�[���t�\���ݒ� */
	public int dateType = TSlipDate.TYPE_YMD;

	/** ���Z�`�[�� */
	public TClosingEntryCheck chkSettlementStage;

	/** �R���g���[�� */
	protected TSlipDateSettlementStageController controller = null;

	/**
	 * �R���X�g���N�^
	 */
	public TSlipDateSettlementStage() {
		initComponents();
		allocateComponents();
		controller = new TSlipDateSettlementStageController(this);
	}

	/**
	 * ���tType�ݒ肠��̏ꍇ �R���X�g���N�^
	 * 
	 * @param type
	 */
	public TSlipDateSettlementStage(int type) {
		dateType = type;
		initComponents();
		allocateComponents();
		controller = new TSlipDateSettlementStageController(this);
	}

	/**
	 * �R���|�[�l���g������
	 */
	public void initComponents() {
		slipDate = new TSlipDate(dateType);
		chkSettlementStage = new TClosingEntryCheck(slipDate);
	}

	/**
	 * �R���|�[�l���g�z�u
	 */
	public void allocateComponents() {

		setLayout(null);

		// �`�[���t
		slipDate.setLocation(0, 0);
		add(slipDate);

		// ���Z�`�[��
		chkSettlementStage.setSize(120, 20);
		chkSettlementStage.setLocation(slipDate.getWidth() + 20, 0);
		chkSettlementStage.setOpaque(false);
		add(chkSettlementStage);

		resize();

	}

	/**
	 * �T�C�Y�ύX
	 */
	public void resize() {
		setSize(slipDate.getWidth() + chkSettlementStage.getWidth() + 20, slipDate.getHeight());
	}

	/**
	 * tab���w��
	 * 
	 * @param index tab��
	 */
	public void setTabControlNo(int index) {
		slipDate.setTabControlNo(index);
		chkSettlementStage.setTabControlNo(index);
	}

	/**
	 * ���Z�`�F�b�N
	 * 
	 * @return true�F����Afalse�F�ُ�
	 */
	public boolean canCreateSlip() {
		return controller.canCreateSlip();
	}

	/**
	 * ���Z�i�K��Ԃ�
	 * 
	 * @return ���Z�i�K
	 */
	public int getSettlementStage() {
		return controller.getSettlementStage();
	}

}
