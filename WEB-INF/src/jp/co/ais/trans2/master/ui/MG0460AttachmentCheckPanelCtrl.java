package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.attach.verify.*;

/**
 * 添付検証画面コントローラ
 */
public class MG0460AttachmentCheckPanelCtrl extends TController {

	/** 指示画面 */
	protected MG0460AttachmentCheckPanel mainView;

	@Override
	public void start() {
		try {
			// メイン画面生成
			createMainView();
			// メイン画面初期化
			initMainView();
			// メイン画面表示
			mainView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 指示画面初期化
	 */
	protected void createMainView() {
		this.mainView = new MG0460AttachmentCheckPanel();
	}

	/**
	 * 指示画面初期化
	 */
	protected void initMainView() {
		addMainViewEvent();
	}

	/**
	 * 画面イベント定義
	 */
	protected void addMainViewEvent() {
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Clicked();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Clicked();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * 検索ボタン押下時の処理
	 */
	protected void btnSearch_Clicked() {
		try {
			// 結果一覧を全行除去
			mainView.tblResult.removeRow();

			List<AttachmentVerifyResult> list = (List<AttachmentVerifyResult>) request(getModelClass(), "get");
			if (list == null || list.isEmpty()) {
				return;
			}
			// 結果一覧に反映
			for (AttachmentVerifyResult bean : list) {
				List row = new ArrayList();
				row.add(bean.getKAI_CODE());
				row.add(bean.getTYPE().toString());
				row.add(bean.getKEY1());
				row.add(bean.getKEY2());
				row.add(bean.getKEY3());
				row.add(bean.getKEY4());
				row.add(bean.getFILE_NAME());
				row.add(bean.getSRV_FILE_NAME());
				row.add(bean.getMESSAGE());
				mainView.tblResult.addRow(row);
			}
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * エクセルボタン押下時の処理
	 */
	protected void btnExcel_Clicked() {
		try {

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// エクセルデータの取得
			byte[] data = (byte[]) request(getModelClass(), "getExcel");

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// プレビュー
			TPrinter printer = new TPrinter();
			// 銀行口座マスタ
			printer.preview(data, getWord("添付検証結果") + "_" + DateUtil.toYMDHMSPlainString(new Date()) + ".xls");
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * @return マネージャクラス
	 */
	protected Class getModelClass() {
		return AttachmentVerifyManager.class;
	}

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

}
