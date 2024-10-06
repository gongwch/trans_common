package jp.co.ais.trans2.common.model.ui.slip;

import java.math.*;

import jp.co.ais.trans.common.util.*;

/**
 * 伝票パターン明細コンポーネントコントローラ
 */
public class TSlipPatternDetailPanelCtrl extends TSlipDetailPanelCtrl {

	/**
	 * コンストラクタ.
	 * 
	 * @param panel
	 */
	public TSlipPatternDetailPanelCtrl(TSlipPatternDetailPanel panel) {
		super(panel);
	}
	
	/**
	 * invoice使用するかどうか(パターンはfalse)
	 */
	@Override
	protected void initInvoiceFlg() {

		isInvoice = false;
	}

	/**
	 * 画面表示初期処理
	 */
	@Override
	protected void initView() {
		super.initView();

		// 債務/債権残高/BS勘定非表示
		panel.btnAP.setVisible(false);
		panel.btnAR.setVisible(false);
		panel.btnBS.setVisible(false);

		// 明細アップ・ダウンロード非表示
		panel.btnDownload.setVisible(false);
		panel.btnUpload.setVisible(false);
	}

	/**
	 * 入力チェック
	 * 
	 * @return false:NG
	 */
	@Override
	protected boolean checkInput() {

		// 入力金額
		BigDecimal inKin = panel.ctrlInputAmount.getBigDecimalValue();

		if (DecimalUtil.isZero(inKin)) {
			return true;
		}

		// 消費税額
		BigDecimal taxInKin = panel.ctrlTaxAmount.getBigDecimalValue();
		if (!panel.ctrlTaxAmount.isEmpty() && !DecimalUtil.isNullOrZero(taxInKin)) {

			if (inKin.signum() != taxInKin.signum()) {
				showMessage("I00126");// 入力金額と消費税額の符号が異なります。
				panel.ctrlTaxAmount.requestFocus();
				return false;
			}

			if (inKin.abs().compareTo(taxInKin.abs()) <= 0) {
				showMessage("I00127");// 消費税額は入力金額未満である必要があります。
				panel.ctrlTaxAmount.requestFocus();
				return false;
			}
		}

		// 邦貨金額
		BigDecimal kin = panel.ctrlKeyAmount.getBigDecimal();
		if (!panel.ctrlKeyAmount.isEmpty() && !DecimalUtil.isNullOrZero(kin)) {

			if (inKin.signum() != kin.signum()) {
				showMessage("I00125");// 入力金額と邦貨金額の符号が異なります。
				panel.ctrlKeyAmount.requestFocus();
				return false;
			}
		}

		return true;
	}

}
