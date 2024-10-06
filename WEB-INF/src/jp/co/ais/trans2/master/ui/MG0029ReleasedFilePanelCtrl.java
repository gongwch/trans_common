package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.releasedfile.*;

/**
 * リリースファイル一覧
 * 
 * @author AIS
 */
public class MG0029ReleasedFilePanelCtrl extends TController {

	/** 新規画面 */
	protected MG0029ReleasedFilePanel mainView = null;

	@Override
	public void start() {
		try {

			// 指示画面生成
			createMainView();

			// 指示画面表示
			mainView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * 指示画面生成, イベント定義
	 */
	protected void createMainView() {
		mainView = new MG0029ReleasedFilePanel();
		addMainViewEvent();

	}

	/**
	 * 指示画面のイベント定義
	 */
	protected void addMainViewEvent() {
		// [エクセル]ボタン
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * [エクセル]ボタン押下
	 */
	protected void btnExcel_Click() {
		try {

			// 確認メッセージ
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// エクセルデータの取得
			byte[] data = (byte[]) request(getModelClass(), "getExcel");

			// データが無かった場合、エラーメッセージ出力
			if (Util.isNullOrEmpty(data)) {
				showMessage(mainView, "I00022");
				return;
			}

			// エクセルタイトルセット
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02914") + ".xls");
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * @return モデル
	 */
	protected Class getModelClass() {
		return ReleasedFileManager.class;
	}
}
