package jp.co.ais.trans.master.ui;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 実行ログ参照コントロール
 * 
 * @author roh
 */
public class MG0029ReleasedFilePanelCtrl extends TPanelCtrlBase {

	/** パネル */
	private MG0029ReleasedFilePanel panel;

	/** 処理サーブレット(プログラム） */
	private static final String TARGET_SERVLET = "MG0029ReleasedFileServlet";

	/**
	 * パネル取得
	 * 
	 * @return パネル
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * コンストラクタ
	 */
	public MG0029ReleasedFilePanelCtrl() {
		panel = new MG0029ReleasedFilePanel(this);
	}

	/**
	 * エクセル出力
	 */
	protected void exportToExcel() {

		// 処理の続行を問う
		if (showConfirmMessage(panel.getParentFrame(), "Q00011")) {
			try {
				// エクセルで表示する
				this.download(panel, TARGET_SERVLET);

			} catch (Exception ex) {
				errorHandler(panel, ex);
			}
		}

	}
}
