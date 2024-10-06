package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * カレンダーマスタ
 */
public class MP0020CalendarMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	protected static final String PROGRAM_ID = "MP0020";

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MP0020CalendarMasterServlet";

	/** パネル */
	protected MP0020CalendarMasterPanel panel;

	/**
	 * コンストラクタ.
	 */
	public MP0020CalendarMasterPanelCtrl() {

		try {
			// 一覧画面の実例化
			panel = new MP0020CalendarMasterPanel(this);
			// 一覧画面の初期化
			panel.setInit();
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				//フォーカスをゲットする
				getFocus();
			}
		});
	}

	/**
	 * フォーカスをゲットする
	 */
	public void getFocus() {
		panel.txtObjectYears.requestFocus();		
	}
	
	/**
	 * @return String
	 */
	public String getLangCode() {
		return super.getLoginLanguage();
	}

	/**
	 * パネル取得
	 * 
	 * @return ｶﾚﾝﾀﾞｰマスタパネル
	 */
	public TPanelBusiness getPanel() {
		// パネルを返す
		return this.panel;
	}

	/**
	 * 表示データの取得
	 * 
	 * @param dteFrom 開始日付, dteTo 終了日付
	 * @param dteTo
	 */
	public void reflesh(Date dteFrom, Date dteTo) {
		try {
			// 会社コードの取得
			String kaiCode = getLoginUserCompanyCode();
			// 送信するパラメータを設定
			addSendValues("flag", "find");
			// 会社コードの設定
			addSendValues("kaiCode", kaiCode);
			// 開始日付の設定
			addSendValues("beginCalDate", DateUtil.toYMDHMSString(dteFrom));
			// 終了日付の設定
			addSendValues("endCalDate", DateUtil.toYMDHMSString(dteTo));
			// 送信
			// サーバ側のエラー場合
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
				return;
			}
			// 結果の取得
			List lisRt = getResultList();
			// 結果の設定
			panel.setDte(lisRt);
			panel.btnSettle.setEnabled(true);
		} catch (IOException e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 削除処理
	 * 
	 * @param calMonth 日付, intLastDay 日数
	 * @param intLastDay
	 */
	void delete(String calMonth, int intLastDay) {
		try {
			// 会社コードの取得
			String kaiCode = getLoginUserCompanyCode();
			// 処理種別の設定
			super.addSendValues("flag", "delete");
			// 会社コードの設定
			super.addSendValues("kaiCode", kaiCode);
			// 日付の設定
			super.addSendValues("calMonth", calMonth);
			// 日数の設定
			super.addSendValues("intLastDay", String.valueOf(intLastDay));
			// サーブレットの接続先
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
				return;
			}
		} catch (IOException e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * @return boolean
	 */
	public boolean disposeDialog() {
		boolean isSettle = false;
		// 確定ボタン押下 チェックOKなら閉じる
		if (this.showConfirmMessage(panel.getParentFrame(), "Q00005", "")) {
			isSettle = true;
			// 完了メッセージを表示する。
			showMessage(panel.getParentFrame(), "I00008", (Object) "");
		}
		return isSettle;
	}

	/**
	 * 引数の日付の書式をチェックし、成功の時、元のストリングを戻る
	 * 
	 * @param strDate 日付
	 * @return String
	 */

	public String chkDate(String strDate) {
		// 成功
		return strDate;
	}

	/**
	 * 新規処理
	 * 
	 * @param calDate 日付
	 * @param calHarai 社員支払対象日
	 * @param calBnkKbn 銀行休業日
	 * @param calSha 臨時支払対象日
	 */
	public void insert(String calDate, String calHarai, String calBnkKbn, String calSha) {
		try {
			// 会社コードの取得
			String kaiCode = getLoginUserCompanyCode();
			// 処理種別の設定
			super.addSendValues("flag", "insert");
			// 会社コードの設定
			super.addSendValues("kaiCode", kaiCode);
			// 日付の設定
			super.addSendValues("calDate", calDate);
			// 社員支払対象日の設定
			super.addSendValues("calHarai", calHarai);
			// 銀行休業日の設定
			super.addSendValues("calBnkKbn", calBnkKbn);
			// 臨時支払対象日の設定
			super.addSendValues("calSha", calSha);
			// プログラムＩＤの設定
			super.addSendValues("prgID", PROGRAM_ID);
			// サーブレットの接続先
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
			}
		} catch (IOException e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

}
