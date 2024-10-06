package jp.co.ais.trans2.common.model.ui.slip;

import java.math.*;

import jp.co.ais.trans.common.util.*;

/**
 * 仕訳明細入力コントローラ
 * 
 * @author AIS
 */
public class TFormDCPatternInputPanelCtrl extends TFormDCInputPanelCtrl {

	/**
	 * コンストラクタ.
	 * 
	 * @param panel 仕訳明細入力パネル
	 */
	protected TFormDCPatternInputPanelCtrl(TFormDCInputPanel panel) {
		super(panel);
	}

	/**
	 * 入力チェック
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

		// 入力金額
		BigDecimal inKin = panel.ctrlInputAmount[dc].getBigDecimal();

		if (DecimalUtil.isZero(inKin)) {
			return true;
		}

		// 消費税額
		BigDecimal taxInKin = panel.ctrlTaxAmount[dc].getBigDecimal();
		if (!panel.ctrlTaxAmount[dc].isEmpty() && !DecimalUtil.isNullOrZero(taxInKin)) {

			if (inKin.signum() != taxInKin.signum()) {
				showMessage("I00126");// 入力金額と消費税額の符号が異なります。
				panel.ctrlTaxAmount[dc].requestFocus();
				return false;
			}

			if (inKin.abs().compareTo(taxInKin.abs()) <= 0) {
				showMessage("I00127");// 消費税額は入力金額未満である必要があります。
				panel.ctrlTaxAmount[dc].requestFocus();
				return false;
			}
		}

		// 邦貨金額
		BigDecimal kin = panel.ctrlKeyAmount[dc].getBigDecimal();
		if (!panel.ctrlKeyAmount[dc].isEmpty() && !DecimalUtil.isNullOrZero(kin)) {

			if (inKin.signum() != kin.signum()) {
				showMessage("I00125");// 入力金額と邦貨金額の符号が異なります。
				panel.ctrlKeyAmount[dc].requestFocus();
				return false;
			}
		}

		return true;
	}

}