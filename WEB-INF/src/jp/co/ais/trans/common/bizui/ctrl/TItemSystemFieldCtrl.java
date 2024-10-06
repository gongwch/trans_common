package jp.co.ais.trans.common.bizui.ctrl;

import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans2.common.config.*;

/**
 * 科目体系フィールドコントロール
 * 
 * @author moriya
 */
public class TItemSystemFieldCtrl extends TAppletClientBase {

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "InformationServlet";

	/** フィールド */
	protected TItemSystemField panel;

	/** ダイアログのコードの初期値表示するか */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/**
	 * コンストラクタ
	 * 
	 * @param itemField フィールド
	 */
	public TItemSystemFieldCtrl(TItemSystemField itemField) {
		this.panel = itemField;
		// 初期の科目体系名称を取得する
		getItemSystemInfo("init");

	}

	/**
	 * 基本科目体系を取得する
	 * 
	 * @param flag 状態を示すフラグ
	 * @return true:正常処理
	 */
	public boolean getItemSystemInfo(String flag) {
		try {

			// 初期画面ロード時の表示
			if (flag.equals("init")) {
				panel.getField().setValue("00");
			}

			// 基本科目体系コード
			String itemSystemCode = flag.equals("init") ? "00" : panel.getField().getText();

			// 科目体系コードがブランクの時のロストフォーカスはエラー
			if (Util.isNullOrEmpty(itemSystemCode)) {
				showMessage(panel, "I00002", "C00609");
				panel.setNoticeValue("");
				panel.clearOldText();
				panel.requestTextFocus();
				return false;
			}

			// フラグ
			addSendValues("FLAG", "GetItemSystem");
			// 基本科目体系コード
			addSendValues("ITEM_SYSTEM_CODE", itemSystemCode);

			// サーブレットの接続先
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
				return false;
			}

			// 結果を取得する
			Map resultMap = getResult();

			// 基本科目体系名を取得する
			String strItemSystemName = Util.avoidNull(resultMap.get("ITEM_SYSTEM_NAME"));
			panel.setNoticeValue(strItemSystemName);

			// 該当コードは存在しません。
			if (Util.isNullOrEmpty(strItemSystemName)) {
				showMessage(panel, "W00081", "C00617");

				panel.setNoticeValue("");
				panel.clearOldText();
				panel.requestTextFocus();

				return false;
			}

			return true;

		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel, e, "E00009");
			return false;
		}
	}

	/**
	 * 科目体系ダイアログの表示
	 */
	public void showItemDialog() {
		try {
			// 科目体系マスタ一覧のダイアログを呼ぶ
			REFDialogMasterCtrl dialog = new REFDialogMasterCtrl(panel, REFDialogMasterCtrl.KMK_TK_MST);

			// ｺｰﾄﾞ設定、自動検索
			if (showDefaultCode && !Util.isNullOrEmpty(panel.getField().getValue())) {
				dialog.setCode(String.valueOf(panel.getField().getValue()));
				dialog.searchData(false);
			}

			// ダイアログオープン
			dialog.show();

			if (dialog.isSettle()) {
				String[] info = dialog.getCurrencyInfo();
				panel.setValue(info[0]);
				panel.setNoticeValue(info[1]);
			}
			// フォーカス取得
			panel.getField().requestFocus();

		} catch (Exception e) {
			errorHandler(panel, e);
		}
	}

}
