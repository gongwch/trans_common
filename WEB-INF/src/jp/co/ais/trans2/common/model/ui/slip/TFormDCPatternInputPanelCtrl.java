package jp.co.ais.trans2.common.model.ui.slip;

import java.math.*;

import jp.co.ais.trans.common.util.*;

/**
 * �d�󖾍ד��̓R���g���[��
 * 
 * @author AIS
 */
public class TFormDCPatternInputPanelCtrl extends TFormDCInputPanelCtrl {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param panel �d�󖾍ד��̓p�l��
	 */
	protected TFormDCPatternInputPanelCtrl(TFormDCInputPanel panel) {
		super(panel);
	}

	/**
	 * ���̓`�F�b�N
	 * 
	 * @return false:NG
	 */
	@Override
	protected boolean checkInput() {

		int dc = DR;

		if (!panel.ctrlItem[DR].ctrlItemReference.code.isEmpty()) {
			dc = DR;

		} else if (!panel.ctrlItem[CR].ctrlItemReference.code.isEmpty()) {
			dc = CR;

		} else {
			return true;
		}

		// ���͋��z
		BigDecimal inKin = panel.ctrlInputAmount[dc].getBigDecimal();

		if (DecimalUtil.isZero(inKin)) {
			return true;
		}

		// ����Ŋz
		BigDecimal taxInKin = panel.ctrlTaxAmount[dc].getBigDecimal();
		if (!panel.ctrlTaxAmount[dc].isEmpty() && !DecimalUtil.isNullOrZero(taxInKin)) {

			if (inKin.signum() != taxInKin.signum()) {
				showMessage("I00126");// ���͋��z�Ə���Ŋz�̕������قȂ�܂��B
				panel.ctrlTaxAmount[dc].requestFocus();
				return false;
			}

			if (inKin.abs().compareTo(taxInKin.abs()) <= 0) {
				showMessage("I00127");// ����Ŋz�͓��͋��z�����ł���K�v������܂��B
				panel.ctrlTaxAmount[dc].requestFocus();
				return false;
			}
		}

		// �M�݋��z
		BigDecimal kin = panel.ctrlKeyAmount[dc].getBigDecimal();
		if (!panel.ctrlKeyAmount[dc].isEmpty() && !DecimalUtil.isNullOrZero(kin)) {

			if (inKin.signum() != kin.signum()) {
				showMessage("I00125");// ���͋��z�ƖM�݋��z�̕������قȂ�܂��B
				panel.ctrlKeyAmount[dc].requestFocus();
				return false;
			}
		}

		return true;
	}

}