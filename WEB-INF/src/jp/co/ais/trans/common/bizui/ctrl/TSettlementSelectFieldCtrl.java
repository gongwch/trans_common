package jp.co.ais.trans.common.bizui.ctrl;

import java.awt.event.*;
import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;

/**
 * 決算段階フィールドコントロール
 */
public class TSettlementSelectFieldCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "InformationServlet";

	/** パネル */
	private TSettlementSelectField panel;

	/** 設定数値 */
	private String ksdKbn = "";

	/**
	 * コンストラクタ
	 * 
	 * @param panel パネル
	 */
	public TSettlementSelectFieldCtrl(TSettlementSelectField panel) {
		try {
			this.panel = panel;

			initSettlementDiv();

			this.panel.numSettlementDivision.addFocusListener(new FocusAdapter() {

				public void focusLost(FocusEvent evt) {
					if (TSettlementSelectFieldCtrl.this.panel.rdoSettlement.isSelected()
						&& Util.isNullOrEmpty(TSettlementSelectFieldCtrl.this.panel.numSettlementDivision.getValue())) {

						// 段階表示がブランクの場合、設定数値を挿入
						TSettlementSelectFieldCtrl.this.panel.numSettlementDivision.setValue(ksdKbn);
					}
				}
			});

		} catch (IOException ex) {
			errorHandler(this.panel, "E00009");
		}
	}

	/**
	 * 決算時期 初期値：GLｺﾝﾄﾛｰﾙﾏｽﾀ.決算段階区分
	 * 
	 * @return boolean
	 * @throws IOException
	 */
	private boolean initSettlementDiv() throws IOException {

		// FLAG
		addSendValues("FLAG", "KSD_KBN");

		if (!request(TARGET_SERVLET)) {
			errorHandler(this.panel);
			return false;
		}

		Map resultMap = getResult();

		// 決算段階区分を取得する
		ksdKbn = Util.avoidNull(resultMap.get("KSD_KBN"));

		// 決算段階 ブランク/0(未使用)の場合、通常を選択
		panel.rdoNormally.setSelected(Util.isNullOrEmpty(ksdKbn) || "0".equals(ksdKbn));

		radioSettlementChange();

		return true;
	}

	/**
	 * 決算時期 1～9まで入力可能。決算区分が「通常」の場合、入力不可。決算の場合、必須。
	 */
	public void radioSettlementChange() {
		boolean isSetSelect = panel.rdoSettlement.isSelected();
		panel.numSettlementDivision.setEditable(isSetSelect);
		panel.numSettlementDivision.setValue(isSetSelect ? ksdKbn : "");
	}
}
