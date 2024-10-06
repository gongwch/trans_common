package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans2.common.config.*;

/**
 * 管理マスタ検索フィールドコントロール
 */
public class TManagementFieldCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "InformationServlet";

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** フィールド */
	protected TManagementField panel;

	/**
	 * コンストラクタ
	 * 
	 * @param itemField フィールド
	 */
	public TManagementFieldCtrl(TManagementField itemField) {
		this.panel = itemField;
	}

	/**
	 * 管理マスタ検索処理
	 */
	public void managementMouseClicked() {
		try {
			// 検索ダイアログを呼び出す
			REFDialogMasterCtrl dialog = new REFDialogMasterCtrl(panel, REFDialogMasterCtrl.BMN_MST
				+ panel.getManagementType());

			// 会社コード
			dialog.setKaiCode(Util.avoidNull(panel.getCompanyCode()));

			// 開始管理コード
			dialog.setBeginCode(Util.avoidNull(panel.getBeginCode()));

			// 終了管理コード
			dialog.setEndCode(Util.avoidNull(panel.getEndCode()));

			// ｺｰﾄﾞ設定、自動検索
			if (showDefaultCode && !Util.isNullOrEmpty(panel.getField().getValue())) {
				dialog.setCode(String.valueOf(panel.getField().getValue()));
				dialog.searchData(false);
			}

			dialog.show();

			if (dialog.isSettle()) {

				String[] info = dialog.getCurrencyInfo();
				// 管理コード
				panel.setValue(info[0]);
				// 管理略称
				panel.setNoticeValue(info[1]);
			}

			panel.getField().requestFocus();
		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * ロストフォーカス処理<BR>
	 * 
	 * @return true:正常処理
	 */
	public boolean managementLostFocus() {
		try {
			// テキストフィールドに文字列が入力されていたときのみ有効
			String strKnrCode = panel.getValue();
			// 会社コード取得
			TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();
			// 管理名称の取得
			String[] name = compInfo.getManageDivNames();

			if (!Util.isNullOrEmpty(strKnrCode)) {
				// 管理1 ～ 管理6マスタデータを検索
				Map<String, String> map = findKnrMst(strKnrCode, panel.getManagementType());
				if (!"0".equals(map.get("existFlag"))) {

					// 取得したデータの開始日付
					Date strDate = DateUtil.toYMDDate(map.get("STR_DATE"));
					// 取得したデータの終了日付
					Date endDate = DateUtil.toYMDDate(map.get("END_DATE"));
					// 基準となる日付
					Date termDate = DateUtil.toYMDDate(Util.avoidNull(panel.getTermBasisDate()));
					if (!Util.isNullOrEmpty(termDate)) {
						if (termDate.after(endDate) || strDate.after(termDate)) {
							if (!showConfirmMessage(panel, "Q00046", panel.getButton().getText() + getWord("C00174"))) {
								return false;
							}
						}
					}
					// 管理コードの略称を取得する
					panel.setNoticeValue(Util.avoidNull(map.get("KNR_NAME_S_".concat(Util.avoidNull(panel
						.getManagementType())))));

				} else {
					if (panel.isCheckMode()) {
						// 管理コードが存在しません。
						showMessage(panel, "W00081", (name[panel.getManagementType() - 1] + getWord("C00174")));
					}
					// 管理名称ブランクでセットする。
					panel.setNoticeValue("");
					panel.clearOldText();
					panel.requestTextFocus();
					return !panel.isCheckMode();

				}

			} else {
				// 管理名称ブランクでセットする。
				panel.setNoticeValue("");
			}
			return true;
		} catch (Exception ex) {
			errorHandler(panel, ex);
			return false;
		}
	}

	/**
	 * 管理1 ～ 管理6マスタデータを検索
	 * 
	 * @param knrCode 管理ｺｰﾄﾞ
	 * @param type 管理表フラグ
	 * @return 管理1 ～ 管理6マスタデータ
	 */
	protected Map<String, String> findKnrMst(String knrCode, int type) {
		try {
			// 伝票日付
			String strSlipDate = Util.avoidNull(panel.getTermBasisDate());
			// 送信するパラメータを設定
			addSendValues("FLAG", "Knrmst");
			// 管理コードタイプ
			addSendValues("KNR_FLG", Util.avoidNull(type));
			// 伝票日付
			addSendValues("SLIP_DATE", strSlipDate);
			// 管理コード
			addSendValues("KNR_CODE", knrCode);

			// サーブレットの接続先
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
			}
			return getResult();

		} catch (IOException e) {
			// 正常に処理されませんでした
			errorHandler(panel, e, "E00009");
			return null;
		}
	}
}
