package jp.co.ais.trans2.common.model.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jp.co.ais.trans.common.gui.TPanelBusiness;
import jp.co.ais.trans.common.util.Util;
import jp.co.ais.trans2.common.client.TController;

/**
 * ���Z�i�K�I���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TSettlementStageSelectorController extends TController {

	/** �t�B�[���h */
	protected TSettlementStageSelector field;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TSettlementStageSelectorController(TSettlementStageSelector field) {
		this.field = field;
		init();
	}

	@Override
	public void start() {
		//
	}

	/**
	 * ������
	 */
	protected void init() {
		addEvent();

		// ���Z���Ȃ��ꍇ
		if (getCompany().getFiscalPeriod().getMaxSettlementStage() == 0) {
			field.rdoNormal.setSelected(true);

			// ���Z����ꍇ
		} else {
			field.rdoSettlement.setSelected(true);
			field.nmSettlementLevel.setNumber(getCompany().getFiscalPeriod().getMaxSettlementStage());
		}

	}

	/**
	 * �C���x�g��`
	 */
	protected void addEvent() {

		/**
		 * �ʏ퉟��
		 */
		field.rdoNormal.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				rdoNormal_Click();
			}
		});

		/**
		 * ���Z����
		 */
		field.rdoSettlement.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				rdoSettlement_Click();
			}
		});

	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * ���Z�i�K��Ԃ�
	 * 
	 * @return ���Z�i�K
	 */
	public int getSettlementStage() {
		if (field.rdoNormal.isSelected()) {
			return 0;
		}
		return field.nmSettlementLevel.getInt();
	}

	/**
	 * ���Z�i�K��Ԃ�
	 * 
	 * @param settlementLevel ���Z�i�K
	 */
	public void setSettlementStage(int settlementLevel) {
		if (settlementLevel == 0) {
			rdoNormal_Click();
			field.rdoNormal.setSelected(true);
		} else {
			rdoSettlement_Click();
			field.rdoSettlement.setSelected(true);
			field.nmSettlementLevel.setNumber(settlementLevel);
		}
	}

	/**
	 * [�ʏ�]����
	 */
	protected void rdoNormal_Click() {
		field.nmSettlementLevel.setEnabled(false);
		field.nmSettlementLevel.setText(null);
	}

	/**
	 * [���Z]����
	 */
	protected void rdoSettlement_Click() {
		field.nmSettlementLevel.setEnabled(true);
	}

	/**
	 * ���͂�����������Ԃ�
	 * 
	 * @return true(����) / false(�G���[)
	 */
	public boolean isCorrect() {

		// ���Z���I������Ă���ꍇ
		if (field.rdoSettlement.isSelected()) {

			// ���Z�i�K�������͂Ȃ�G���[
			if (Util.isNullOrEmpty(field.nmSettlementLevel.getText())) {
				return false;
			}

			// ���Z�i�K��1�`9�łȂ���΃G���[
			if (field.nmSettlementLevel.getInt() < 1 || field.nmSettlementLevel.getInt() > 9) {
				return false;
			}
		}

		return true;
	}

}
