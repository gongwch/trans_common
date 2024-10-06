package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * 伝票印刷停止チェック
 */
public class TSlipPrintStopCheck extends TCheckBox {

	/**
	 * コンストラクタ.
	 */
	public TSlipPrintStopCheck() {
		super();

		allocateComponents();
	}

	/**
	 * 初期化
	 */
	public void allocateComponents() {
		this.setLangMessageID("C01249");
		this.setEnterFocusable(false);
		this.setOpaque(false);

		TGuiUtil.setComponentSize(this, new Dimension(100, 20));

		if (!TLoginInfo.getCompany().getAccountConfig().isSlipPrint()) {
			this.setVisible(false);
			this.setSelected(true);

			return;
		}

		clear();
	}

	/**
	 * 初期化
	 */
	public void clear() {
		// 印刷時の初期値 PRINT_DEF
		boolean def = TLoginInfo.getCompany().getAccountConfig().getSlipPrintDefault();
		this.setSelected(!def);
	}
}
