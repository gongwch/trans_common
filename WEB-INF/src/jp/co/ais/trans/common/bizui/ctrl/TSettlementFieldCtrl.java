package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;

/**
 * 決算フィールドコントロール
 */
public class TSettlementFieldCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "InformationServlet";

	/** パネル */
	private TSettlementField panel;

	/**
	 * コンストラクタ
	 * 
	 * @param panel パネル
	 */
	public TSettlementFieldCtrl(TSettlementField panel) {
		this.panel = panel;
	}

	/**
	 * 「決算仕訳」チェック時の処理
	 * 
	 * @return false:チェック不備
	 */
	public boolean chkClosingEntryMouseClicked() {
		try {
			// チェック時
			if (panel.isSelected()) {
				// 伝票日付が入力されていない場合

				if ("".equals(DateUtil.toYMDString(panel.getSlipDate()))) {
					super.showMessage(panel, "W00034", "C00599");
					return false;
				}

				// 決算段階フラグ
				addSendValues("FLAG", "ACCOUNT_STAGE");
				// 会社コード
				addSendValues("KAI_CODE", super.getLoginUserCompanyCode());
				// 伝票日付
				String strSlipDate = DateUtil.toYMDString(DateUtil.toYMDDate(panel.getSlipDate()));
				addSendValues("SLIP_DATE", strSlipDate);

				// サーブレットの接続先
				if (!request(TARGET_SERVLET)) {
					errorHandler(panel);
				}

				// データ取得
				Map<String, String> map = getResult();
				int strAccountStage = Util.avoidNullAsInt(map.get("KSN_KBN"));
				int strMaxAccountStage = Util.avoidNullAsInt(map.get("KSD_KBN"));

				if (strMaxAccountStage < strAccountStage) {
					showMessage(panel, "W00139");
				} else {
					// 伝票日付の月度における決算段階を取得し指示画面に表示する。
					panel.setValue(strAccountStage);
				}
			} else {// 決算段階非表示
				panel.setValue(null);
			}

		} catch (IOException e) {
			// 正常に処理されませんでした
			errorHandler(panel, e, "E00009");
		}

		return true;
	}
}
