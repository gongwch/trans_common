package jp.co.ais.trans.master.ui;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * 消費税、入力フラグ組合せチェックのためのクラス
 */
public class MG0080InputCheckerShouhiZei extends PanelAndDialogCtrlBase {

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0130ConsumptionTaxMasterServlet";

	private TDialog dialog;

	/**
	 * constructor. <br>
	 * エラーメッセージ出力のための親dialogを渡す
	 * 
	 * @param dialog
	 */
	public MG0080InputCheckerShouhiZei(TDialog dialog) {
		super();
		this.dialog = dialog;
	}

	/**
	 * 入力組合せチェック
	 * 
	 * @param code 消費税コード
	 * @param flagUriage 売上課税入力フラグ
	 * @param flagShiire 仕入課税入力フラグ
	 * @return boolean
	 */
	public boolean isValid(String code, boolean flagUriage, boolean flagShiire) {

		// 消費税コード未入力
		if (Util.isNullOrEmpty(code)) {
			return true;
		}

		// 両方ともONならチェックの必要なし
		if (flagUriage && flagShiire) {
			return true;
		}

		// ********************
		// request to server.
		// ********************
		try {
			// 処理種別の設定
			addSendValues("flag", "findone");
			// 会社コードの設定
			addSendValues("kaiCode", TClientLoginInfo.getInstance().getCompanyCode());
			// 消費税コードの設定
			addSendValues("zeiCode", code);

			// 送信
			if (!request(TARGET_SERVLET)) {
				errorHandler(dialog);
				return false;
			}

			// 結果を取得
			List<List<String>> result = super.getResultList();
			// 結果を返す

			if (result.size() <= 0) {
				// 正常に処理されませんでした
				errorHandler(dialog, "E00009");
				return false;
			}

			List<String> list = result.get(0);

			// list[5]:売上仕入区分
			if (list.size() < 6) {
				// 正常に処理されませんでした
				errorHandler(dialog, "E00009");
				return false;
			}

			int usKbn = Util.avoidNullAsInt(list.get(5));

			// ***************************
			// 消費税コードと課税フラグの関連チェック
			// ***************************

			// 売上/仕入区分 1:売上 2:仕入
			if (usKbn == 1 && !flagUriage || usKbn == 2 && !flagShiire) {
				// 消費税コードと課税フラグの関連が誤っています。
				errorHandler(dialog, "W00220");
				return false;
			}
			return true;
		} catch (IOException ex) {
			// 正常に処理されませんでした
			errorHandler(dialog, ex, "E00009");
			return false;
		}
	}
}
