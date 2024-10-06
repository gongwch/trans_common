package jp.co.ais.trans2.common.model.ui.slip;

import java.math.*;

import jp.co.ais.trans.common.util.*;

/**
 * �`�[�p�^�[�����׃R���|�[�l���g�R���g���[��
 */
public class TSlipPatternDetailPanelCtrl extends TSlipDetailPanelCtrl {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param panel
	 */
	public TSlipPatternDetailPanelCtrl(TSlipPatternDetailPanel panel) {
		super(panel);
	}
	
	/**
	 * invoice�g�p���邩�ǂ���(�p�^�[����false)
	 */
	@Override
	protected void initInvoiceFlg() {

		isInvoice = false;
	}

	/**
	 * ��ʕ\����������
	 */
	@Override
	protected void initView() {
		super.initView();

		// ��/���c��/BS�����\��
		panel.btnAP.setVisible(false);
		panel.btnAR.setVisible(false);
		panel.btnBS.setVisible(false);

		// ���׃A�b�v�E�_�E�����[�h��\��
		panel.btnDownload.setVisible(false);
		panel.btnUpload.setVisible(false);
	}

	/**
	 * ���̓`�F�b�N
	 * 
	 * @return false:NG
	 */
	@Override
	protected boolean checkInput() {

		// ���͋��z
		BigDecimal inKin = panel.ctrlInputAmount.getBigDecimalValue();

		if (DecimalUtil.isZero(inKin)) {
			return true;
		}

		// ����Ŋz
		BigDecimal taxInKin = panel.ctrlTaxAmount.getBigDecimalValue();
		if (!panel.ctrlTaxAmount.isEmpty() && !DecimalUtil.isNullOrZero(taxInKin)) {

			if (inKin.signum() != taxInKin.signum()) {
				showMessage("I00126");// ���͋��z�Ə���Ŋz�̕������قȂ�܂��B
				panel.ctrlTaxAmount.requestFocus();
				return false;
			}

			if (inKin.abs().compareTo(taxInKin.abs()) <= 0) {
				showMessage("I00127");// ����Ŋz�͓��͋��z�����ł���K�v������܂��B
				panel.ctrlTaxAmount.requestFocus();
				return false;
			}
		}

		// �M�݋��z
		BigDecimal kin = panel.ctrlKeyAmount.getBigDecimal();
		if (!panel.ctrlKeyAmount.isEmpty() && !DecimalUtil.isNullOrZero(kin)) {

			if (inKin.signum() != kin.signum()) {
				showMessage("I00125");// ���͋��z�ƖM�݋��z�̕������قȂ�܂��B
				panel.ctrlKeyAmount.requestFocus();
				return false;
			}
		}

		return true;
	}

}
