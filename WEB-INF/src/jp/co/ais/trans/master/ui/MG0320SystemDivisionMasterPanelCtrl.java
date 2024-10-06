package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 管理マスタ画面コントロール
 */
public class MG0320SystemDivisionMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	private static final String PROGRAM_ID = "MG0320";

	/** 新規と複写区分 */
	protected static int INSERT_KBN = 0;

	/** 編集区分 */
	protected static int UPD_KBN = 1;

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0320SystemDivisionMasterServlet";
	}

	/** パネル */
	protected MG0320SystemDivisionMasterPanel panel;

	protected Map<String, String> dataKbnMap;

	private REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	MG0320EditDisplayDialogCtrl dialog;

	/**
	 * コンストラクタ.
	 */
	public MG0320SystemDivisionMasterPanelCtrl() {
		try {
			// 一覧画面の初期化
			panel = new MG0320SystemDivisionMasterPanel(this);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		dataKbnMap = new HashMap<String, String>();
		dataKbnMap.put("0", getWord("C02104"));
		dataKbnMap.put("1", getWord("C02105"));
		dataKbnMap.put("2", getWord("C02106"));

		// 画面初期化
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				init();
			}
		});
	}

	/**
	 * 画面初期化
	 */
	void init() {
		// 開始コードの設定
		ref1 = new REFDialogCtrl(panel.ctrlBeginSystemDivision, panel.getParentFrame());
		ref1.setColumnLabels("C00980", "C00833", "C00834");
		ref1.setTargetServlet("MG0320SystemDivisionMasterServlet");
		ref1.setTitleID(getWord("C02354"));
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndSystemDivision.getField().getText());
				keys.put("kind", "SystemDivision");
				return keys;
			}
		});

		panel.ctrlBeginSystemDivision.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}
		});

		// 終了コードの設定
		ref2 = new REFDialogCtrl(panel.ctrlEndSystemDivision, panel.getParentFrame());
		ref2.setColumnLabels("C00980", "C00833", "C00834");
		ref2.setTargetServlet("MG0320SystemDivisionMasterServlet");
		ref2.setTitleID(getWord("C02354"));
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginSystemDivision.getField().getText());
				keys.put("kind", "SystemDivision");
				return keys;
			}
		});

		panel.ctrlEndSystemDivision.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				return true;
			}
		});
	}

	/**
	 * パネル取得
	 * 
	 * @return 管理マスタパネル
	 */
	public TPanelBusiness getPanel() {
		// パネルを返す
		return this.panel;
	}

	/**
	 * Dialog生成
	 * 
	 * @param titleId
	 */
	protected void createEditDisplayDialog(String titleId) {
		dialog = new MG0320EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * 新規登録処理
	 */
	void insert() {
		try {
			// 編集画面の初期化
			createEditDisplayDialog("C01698");
			dialog.setDataKbnMap(dataKbnMap);
			// 編集画面の表示
			dialog.show(true);
			// 編集画面が開けていました
			if (!dialog.isSettle()) return;
			// データが編集する
			modify(dialog.getDataList(), true);
			// ボタンロックの解除
			unlockBtn();

			// 選択行保持する
			modifySpreadRow(dialog.getDataList().get("sysKbn").toString(), INSERT_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 編集処理
	 */
	void update() {
		try {
			// 編集画面の初期化
			createEditDisplayDialog("C00977");
			dialog.setDataKbnMap(dataKbnMap);
			// 当前行を取得する
			int nomRow = panel.ssJournal.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssJournal.getDataSource();
			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("sysKbn", model.getTableDataItem(nomRow, 1));
			map.put("sysName", model.getTableDataItem(nomRow, 2));
			map.put("sysName_S", model.getTableDataItem(nomRow, 3));
			map.put("sysName_K", model.getTableDataItem(nomRow, 4));
			map.put("osyKbn", model.getTableDataItem(nomRow, 10));
			// 処理種別の設定
			map.put("flag", "update");
			// データを画面に設定する
			dialog.setValues(map);
			// 編集画面を表示する
			dialog.show(false);

			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), false);
			// スプレッド更新
			modifySpreadRow(dialog.getDataList().get("sysKbn").toString(), UPD_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * データ処理
	 */
	void enter() {
		try {
			// 当前行を取得する
			int nomRow = panel.ssJournal.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssJournal.getDataSource();
			// ボタンロックの処理
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				lockBtn(false);
			} else {
				unlockBtn();
			}
		} catch (Exception e) {
			// 正常に処理されませんでした
			ClientLogger.error(this.getClass().getName(), e);
		}
	}

	/**
	 * 複写処理
	 */
	public void copy() {
		try {
			createEditDisplayDialog("C01738");
			// 当前行を取得する
			dialog.setDataKbnMap(dataKbnMap);
			int nomRow = panel.ssJournal.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssJournal.getDataSource();
			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("sysKbn", model.getTableDataItem(nomRow, 1));
			map.put("sysName", model.getTableDataItem(nomRow, 2));
			map.put("sysName_S", model.getTableDataItem(nomRow, 3));
			map.put("sysName_K", model.getTableDataItem(nomRow, 4));
			map.put("osyKbn", model.getTableDataItem(nomRow, 10));
			map.put("flag", "insert");
			// データを画面に設定する
			dialog.setValues(map);
			dialog.show(true);
			// 編集画面の表示
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), true);
			// スプレッド更新
			modifySpreadRow(dialog.getDataList().get("sysKbn").toString(), INSERT_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 変更処理
	 * 
	 * @param map データ
	 * @param isInsert 新規(true) or 更新(false)
	 * @throws IOException
	 */
	protected void modify(Map map, boolean isInsert) throws IOException {
		// 処理種別の設定
		super.addSendValues("flag", isInsert ? "insert" : "update");
		super.addSendValues("kaiCode", (String) map.get("kaiCode"));
		super.addSendValues("sysKbn", (String) map.get("sysKbn"));
		super.addSendValues("sysName", (String) map.get("sysName"));
		super.addSendValues("sysName_S", (String) map.get("sysName_S"));
		super.addSendValues("sysName_K", (String) map.get("sysName_K"));
		super.addSendValues("osyKbn", (String) map.get("osyKbn"));
		super.addSendValues("prgID", PROGRAM_ID);
		// サーブレットの接続先
		if (!request(getServletName())) {
			// サーバ側のエラー場合
			errorHandler(panel);
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param sysKbn
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String sysKbn, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(sysKbn, updKbn);
		TTable ssPanelSpread = panel.ssJournal;
		int row = ssPanelSpread.getCurrentRow();
		panel.setDataList(cells);
		if (cells.size() == 0) {
			ssPanelSpread.setRowSelection(-999, 0);
		} else {
			// 新規と複写場合
			if (updKbn == INSERT_KBN) {
				if (cells.size() != 1) {
					int lastRow = cells.size() - 1;
					selectSpreadRow(lastRow);
					// 保持しておいた値をスクロールバーにセット
					ssPanelSpread.getVertSB().setValue(lastRow * ssPanelSpread.getPixelHeight(row));
				} else {
					selectSpreadRow(0);
				}
			} else if (updKbn == UPD_KBN) {
				// 編集場合
				selectSpreadRow(row);
				// 保持しておいた値をスクロールバーにセット
				ssPanelSpread.getVertSB().setValue(row * ssPanelSpread.getPixelHeight(row));
			}
			ssPanelSpread.getHorizSB().setValue(0);
		}
		// ボタンロック解除
		lockBtn(panel.ssJournal.getDataSource().getNumRows() != 0);
	}

	/**
	 * 新規、複写、編集の場合、スプレッド取得
	 * 
	 * @param sysKbn
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String sysKbn, int updKbn) throws IOException, TRequestException {
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginSysKbn", Util.avoidNull(sysKbn));
		addSendValues("endSysKbn", Util.avoidNull(sysKbn));

		// サーブレットの接続先
		if (!request(getServletName())) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssJournal.getDataSource();
		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = modifyDs.getCells();

		Iterator recordIte = getResultList().iterator();

		// 値があるかどうか
		if (!recordIte.hasNext()) {
			showMessage(panel, "W00100", "");
		}

		Vector<String> colum = new Vector<String>();

		for (int row = 0; recordIte.hasNext(); row++) {
			// 結果の内容を取得する
			Iterator dataIte = ((List) recordIte.next()).iterator();
			// 結果を追加する
			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}

			String value, text;
			// ﾃﾞｰﾀ区分
			value = colum.get(5);
			text = dataKbnMap.get(value);
			colum.add(10, value);
			colum.set(5, text);
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssJournal.getCurrentRow(), colum);
		}
		return cells;
	}

	/**
	 * 削除処理
	 */
	void delete() {
		if (this.showConfirmMessage(panel, FocusButton.NO, "Q00007")) {
			try {
				// 選択されている行の1つ目と2つ目のカラムを取得
				int nomRow = panel.ssJournal.getCurrentRow();
				// データ集を取得する
				TableDataModel model = panel.ssJournal.getDataSource();

				String kaiCode = (String) model.getTableDataItem(nomRow, 0); // 会社コード
				String sysKbn = (String) model.getTableDataItem(nomRow, 1); // 管理コード
				super.addSendValues("flag", "delete");
				super.addSendValues("kaiCode", kaiCode);
				super.addSendValues("sysKbn", sysKbn);

				// サーブレットの接続先
				if (!request(getServletName())) {
					errorHandler(panel);
					return;
				}
				deleteSpreadRow();
			} catch (IOException e) {
				// 正常に処理されませんでした
				errorHandler(panel.getParentFrame(), e, "E00009");
			}
		}
	}

	/**
	 * 削除場合、スプレッド更新
	 */
	private void deleteSpreadRow() {
		int row = panel.ssJournal.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssJournal.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssJournal.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssJournal.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// 保持しておいた値をスクロールバーにセット
			panel.ssJournal.getVertSB().setValue(0);
			panel.ssJournal.getHorizSB().setValue(0);
		}
		// ボタンロック解除
		lockBtn(panel.ssJournal.getDataSource().getNumRows() != 0);
	}

	/**
	 * マスタ選択行状態の保持
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {

		panel.ssJournal.setRowSelection(row, row);
		panel.ssJournal.setCurrentCell(row, 0);

	}

	/**
	 * 検索処理
	 * 
	 * @return boolean
	 */
	boolean find() {

		try {
			// 開始コードの取得
			String strBegin = panel.ctrlBeginSystemDivision.getValue();
			// 終了コードの取得
			String strEnd = panel.ctrlEndSystemDivision.getValue();
			// 開始コードが終了コードより大きい
			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				// 警告メッセージ表示
				panel.ctrlBeginSystemDivision.getField().requestFocus();
				showMessage(panel, "W00036", "C00980");
				return false;
			}
			return reflesh();
			// ボタンロック解除

		} catch (IOException e) {
			lockBtn(false);

			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
		return false;
	}

	/**
	 * ボタン(新規、削除、編集、複写、リスト出力)ロック
	 * 
	 * @param boo
	 */
	void lockBtn(boolean boo) {
		// 削除ボタンロックの処理
		panel.btnDelete.setEnabled(boo);
		// 編集ボタンロックの処理
		panel.btnEdit.setEnabled(boo);
		// 複写ボタンロックの処理
		panel.btnCopy.setEnabled(boo);

	}

	/**
	 * ボタンロックの解除
	 */
	void unlockBtn() {
		panel.btnDelete.setEnabled(true);
		panel.btnEdit.setEnabled(true);
		panel.btnCopy.setEnabled(true);
		panel.btnListOutput.setEnabled(true);
	}

	/**
	 * 表示データの取得
	 * 
	 * @return データ
	 * @throws IOException
	 */
	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();
		// 開始コードの取得
		String beginSysKbn = panel.ctrlBeginSystemDivision.getValue();
		// 終了コードの取得
		String endSysKbn = panel.ctrlEndSystemDivision.getValue();
		// 送信するパラメータを設定
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginSysKbn", Util.avoidNull(beginSysKbn));
		addSendValues("endSysKbn", Util.avoidNull(endSysKbn));

		// サーバ側のエラー場合
		if (!request(getServletName())) {
			errorHandler(panel);
			return false;
		}
		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();
		// 結果を取得する
		Iterator recordIte = getResultList().iterator();
		if (!recordIte.hasNext()) {
			panel.ctrlBeginSystemDivision.getField().requestFocus();
			// 警告メッセージ表示
			showMessage(panel, "W00100");
			dataExists = false;
		}
		for (int row = 0; recordIte.hasNext(); row++) {
			// 結果の内容を取得する
			Iterator dataIte = ((List) recordIte.next()).iterator();
			// columの初期化
			Vector<String> colum = new Vector<String>();
			// 結果を追加する
			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}

			String value, text;
			// ﾃﾞｰﾀ区分
			value = colum.get(5);
			text = dataKbnMap.get(value);
			colum.add(10, value);
			colum.set(5, text);

			cells.add(row, colum);
		}
		// 結果を表示する
		panel.setDataList(cells);
		return dataExists;
	}

	/**
	 * エクセルリスト出力
	 */

	void outptExcel() {

		try {
			panel.ctrlBeginSystemDivision.getField().requestFocus();
			// 確認ダイアログ表示
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginSysKbn", panel.ctrlBeginSystemDivision.getValue());
			conds.put("endSysKbn", panel.ctrlEndSystemDivision.getValue());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

}
