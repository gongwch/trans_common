package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
import java.text.*;
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
 * 摘要マスタ
 */
public class MG0120MemoMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	protected static final String PROGRAM_ID = "MG0120";

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
		return "MG0120MemoMasterServlet";
	}

	/** パネル */
	protected MG0120MemoMasterPanel panel;

	protected Map dataKbnMap;

	private REFDialogCtrl refBeginMemo;

	private REFDialogCtrl refEndMemo;

	/**
	 * コンストラクタ.
	 */

	public MG0120MemoMasterPanelCtrl() {
		try {
			// 一覧画面の初期化
			panel = new MG0120MemoMasterPanel(this);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
		// 画面初期化
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				init();
			}
		});

	}

	void init() {
		// 開始コードの設定
		refBeginMemo = new REFDialogCtrl(panel.ctrlBeginMemo, panel.getParentFrame());
		refBeginMemo.setColumnLabelIDs("C00564", "C01741", "C00566");
		refBeginMemo.setTargetServlet(getServletName());
		refBeginMemo.setTitleID("C02349");
		refBeginMemo.setShowsMsgOnError(false);
		refBeginMemo.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndMemo.getField().getText());
				keys.put("kind", "Memo");
				return keys;
			}
		});

		panel.ctrlBeginMemo.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refBeginMemo.refreshName();

				return true;
			}
		});

		// 終了コードの設定
		refEndMemo = new REFDialogCtrl(panel.ctrlEndMemo, panel.getParentFrame());
		refEndMemo.setColumnLabelIDs("C00564", "C01741", "C00566");
		refEndMemo.setTargetServlet(getServletName());
		refEndMemo.setTitleID("C02349");
		refEndMemo.setShowsMsgOnError(false);
		refEndMemo.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginMemo.getField().getText());
				keys.put("kind", "Memo");
				return keys;
			}
		});

		panel.ctrlEndMemo.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refEndMemo.refreshName();
				return true;
			}
		});

		// 言語の指定された名称を表示する
		dataKbnMap = new LinkedHashMap();
		dataKbnMap.put("11", getWord("C00419"));
		dataKbnMap.put("12", getWord("C00264"));
		dataKbnMap.put("13", getWord("C00469"));
		dataKbnMap.put("14", getWord("C02079"));
		dataKbnMap.put("15", getWord("C02080"));
		dataKbnMap.put("21", getWord("C02081"));
		dataKbnMap.put("22", getWord("C02082"));
		dataKbnMap.put("23", getWord("C00314"));
		dataKbnMap.put("24", getWord("C02083"));
		dataKbnMap.put("25", getWord("C02084"));
		dataKbnMap.put("26", getWord("C02085"));
		dataKbnMap.put("27", getWord("C02086"));
		dataKbnMap.put("28", getWord("C04290"));
		dataKbnMap.put("2E", getWord("C02088"));
		dataKbnMap.put("2T", getWord("C02089"));
		dataKbnMap.put("31", getWord("C01978"));
		dataKbnMap.put("32", getWord("C01979"));
		dataKbnMap.put("33", getWord("C02090"));
		dataKbnMap.put("40", getWord("C00096"));
		dataKbnMap.put("41", getWord("C02091"));
		dataKbnMap.put("42", getWord("C02092"));
		dataKbnMap.put("51", getWord("C02093"));
		dataKbnMap.put("52", getWord("C02094"));
		dataKbnMap.put("61", getWord("C02095"));
		dataKbnMap.put("62", getWord("C02096"));

	}

	/**
	 * パネル取得
	 * 
	 * @return 通貨マスタパネル
	 */
	public TPanelBusiness getPanel() {
		// パネルを返す
		return this.panel;
	}

	/**
	 * 新規登録処理
	 */
	void insert() {
		try {
			// 編集画面の初期化
			MG0120EditDisplayDialogCtrl dialog = new MG0120EditDisplayDialogCtrl(panel.getParentFrame(), "C01698");
			dialog.setDataKbnMap(dataKbnMap);
			// 編集画面の表示
			dialog.show();
			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), true);
			// 選択行保持する
			String tekCode = dialog.getDataList().get("tekCode").toString();
			String tekKbn = dialog.getDataList().get("tekKbn").toString();
			modifySpreadRow(tekCode, tekKbn, INSERT_KBN);
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
			MG0120EditDisplayDialogCtrl dialog = new MG0120EditDisplayDialogCtrl(panel.getParentFrame(), "C00977");
			dialog.setDataKbnMap(dataKbnMap);
			// 当前行を取得する
			int nomRow = panel.ssMemo.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssMemo.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 摘要区分の設定
			map.put("tekKbn", model.getTableDataItem(nomRow, 8));
			// ﾃﾞｰﾀ区分の設定
			map.put("dataKbn", model.getTableDataItem(nomRow, 9));
			// 摘要コードの設定
			map.put("tekCode", model.getTableDataItem(nomRow, 3));
			// 摘要名称の設定
			map.put("tekName", model.getTableDataItem(nomRow, 4));
			// 摘要名称の設定
			map.put("tekName_K", model.getTableDataItem(nomRow, 5));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 6)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 7)));
			// 処理種別の設定
			map.put("flag", "update");
			// データを画面に設定する
			dialog.setValues(map);
			// 編集画面を表示する
			dialog.show();
			// 編集画面が開けていましたﾞ
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), false);
			// スプレッド更新
			String tekCode = dialog.getDataList().get("tekCode").toString();
			String tekKbn = dialog.getDataList().get("tekKbn").toString();
			modifySpreadRow(tekCode, tekKbn, UPD_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 複写処理
	 */
	public void copy() {
		try {
			MG0120EditDisplayDialogCtrl dialog = new MG0120EditDisplayDialogCtrl(panel.getParentFrame(), "C01738");
			dialog.setDataKbnMap(dataKbnMap);
			// 当前行を取得する
			int nomRow = panel.ssMemo.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssMemo.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 摘要区分の設定
			map.put("tekKbn", model.getTableDataItem(nomRow, 8));
			// ﾃﾞｰﾀ区分の設定
			map.put("dataKbn", model.getTableDataItem(nomRow, 9));
			// 摘要コードの設定
			map.put("tekCode", model.getTableDataItem(nomRow, 3));
			// 摘要名称の設定
			map.put("tekName", model.getTableDataItem(nomRow, 4));
			// 摘要検索名称の設定
			map.put("tekName_K", model.getTableDataItem(nomRow, 5));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 6)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 7)));
			// 処理種別の設定
			map.put("flag", "insert");
			// データを画面に設定する
			dialog.setValues(map);
			// 編集画面を表示する
			dialog.show();
			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), true);

			String tekCode = dialog.getDataList().get("tekCode").toString();
			String tekKbn = dialog.getDataList().get("tekKbn").toString();
			modifySpreadRow(tekCode, tekKbn, INSERT_KBN);

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
		addSendValues("flag", isInsert ? "insert" : "update");
		// 会社コードの設定
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// ﾃﾞｰﾀ区分の設定
		addSendValues("dataKbn", (String) map.get("dataKbn"));
		// 摘要区分の設定
		addSendValues("tekKbn", (String) map.get("tekKbn"));
		// 摘要コードの設定
		addSendValues("tekCode", (String) map.get("tekCode"));
		// 摘要名称の設定
		addSendValues("tekName", (String) map.get("tekName"));
		// 摘要検索名称の設定
		addSendValues("tekName_K", (String) map.get("tekName_K"));
		// 開始年月日の設定
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// 終了年月日の設定
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// プログラムＩＤの設定
		addSendValues("prgID", PROGRAM_ID);
		// サーブレットの接続先
		if (!request(getServletName())) {
			errorHandler(panel);
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param tekCode
	 * @param tekKbn
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String tekCode, String tekKbn, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(tekCode, tekKbn, updKbn);
		TTable ssPanelSpread = panel.ssMemo;
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
		lockBtn(panel.ssMemo.getDataSource().getNumRows() != 0);
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
	 * 新規、複写、編集の場合、スプレッド取得
	 * 
	 * @param tekCode
	 * @param tekKbn
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected Vector<Vector> setModifyCell(String tekCode, String tekKbn, int updKbn) throws IOException,
		TRequestException {
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginTekCode", Util.avoidNull(tekCode));
		// 終了コードの設定
		addSendValues("endTekCode", Util.avoidNull(tekCode));
		// 摘要区分の設定
		addSendValues("tekKbn", Util.avoidNull(tekKbn));

		// サーブレットの接続先
		if (!request(getServletName())) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssMemo.getDataSource();
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

			try {
				colum.set(6, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(6))));
				colum.set(7, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(7))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(33), ex);
			}
			String value, text;
			// 摘要区分
			value = colum.get(1);
			text = ("0".equals(value) ? this.getWord("C00569") : this.getWord("C00119"));
			colum.add(8, value);
			colum.set(1, text);

			// ﾃﾞｰﾀ区分
			colum.add(9, "");
			value = colum.get(2);
			text = (String) dataKbnMap.get(value);
			colum.set(9, value);
			colum.set(2, text);

		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssMemo.getCurrentRow(), colum);
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
				int nomRow = panel.ssMemo.getCurrentRow();
				// データ集を取得する
				TableDataModel model = panel.ssMemo.getDataSource();
				// 会社コードの取得
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// 摘要区分の取得
				String tekKbn = (String) model.getTableDataItem(nomRow, 8);
				// 摘要コードの取得
				String tekCode = (String) model.getTableDataItem(nomRow, 3);
				// 処理種別の設定
				addSendValues("flag", "delete");
				// 会社コードの設定
				addSendValues("kaiCode", kaiCode);
				// 摘要区分の設定
				addSendValues("tekKbn", tekKbn);
				// 摘要コードの設定
				addSendValues("tekCode", tekCode);

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
	 * 検索処理
	 * 
	 * @return boolean
	 */
	boolean find() {

		String beginTekCode = panel.ctrlBeginMemo.getValue().toString();
		String endTekCode = panel.ctrlEndMemo.getValue().toString();
		try {
			if (!Util.isSmallerThen(beginTekCode, endTekCode)) {
				// 警告メッセージ表示
				panel.ctrlBeginMemo.getField().requestFocus();
				showMessage(panel, "W00036", "C00384");
				return false;
			}
			// 表示データの取得
			return reflesh();
		} catch (IOException e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
		return false;
	}

	/**
	 * 削除場合、スプレッド更新
	 */
	private void deleteSpreadRow() {
		int row = panel.ssMemo.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssMemo.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssMemo.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssMemo.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// 保持しておいた値をスクロールバーにセット
			panel.ssMemo.getVertSB().setValue(0);
			panel.ssMemo.getHorizSB().setValue(0);
		}
		// ボタンロック解除
		lockBtn(panel.ssMemo.getDataSource().getNumRows() != 0);
	}

	/**
	 * マスタ選択行状態の保持
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {
		panel.ssMemo.setRowSelection(row, row);
		panel.ssMemo.setCurrentCell(row, 0);
	}

	/**
	 * 画面リフレッシュ
	 * 
	 * @return boolean
	 * @throws IOException
	 */
	protected boolean reflesh() throws IOException {

		boolean dataExists = true;
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();
		// 開始コードの設定
		String beginTekCode = panel.ctrlBeginMemo.getValue();
		// 終了コードの取得
		String endTekCode = panel.ctrlEndMemo.getValue();
		// 摘要区分の取得
		String tekKbn = "";
		if (panel.rdoSlipMemo.isSelected()) {
			tekKbn = "0";
		} else if (panel.rdoRowMemo.isSelected()) {
			tekKbn = "1";
		}
		beginTekCode = beginTekCode.trim();
		endTekCode = endTekCode.trim();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginTekCode", Util.avoidNull(beginTekCode));
		// 終了コードの設定
		addSendValues("endTekCode", Util.avoidNull(endTekCode));
		// 摘要区分の設定
		addSendValues("tekKbn", Util.avoidNull(tekKbn));

		// サーブレットの接続先
		if (!request(getServletName())) {
			errorHandler(panel);
			return false;
		}

		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();

		Iterator recordIte = getResultList().iterator();
		// 値があるかどうか
		if (!recordIte.hasNext()) {
			panel.ctrlBeginMemo.getField().requestFocus();
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

			try {
				colum.set(6, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(6))));
				colum.set(7, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(7))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(33), ex);
			}
			String value, text;
			// 摘要区分
			value = colum.get(1);
			text = ("0".equals(value) ? this.getWord("C00569") : this.getWord("C00119"));
			colum.add(8, value);
			colum.set(1, text);

			// ﾃﾞｰﾀ区分
			colum.add(9, "");
			value = colum.get(2);
			text = (String) dataKbnMap.get(value);
			colum.set(9, value);
			colum.set(2, text);

			cells.add(row, colum);
		}

		panel.setDataList(cells);
		return dataExists;
	}

	/**
	 * エクセルリスト出力
	 */
	void outptExcel() {

		try {
			panel.ctrlBeginMemo.getField().requestFocus();
			// 確認ダイアログ表示
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			// 摘要区分の取得
			String tekKbn = "";
			if (panel.rdoSlipMemo.isSelected()) {
				tekKbn = "0";
			} else if (panel.rdoRowMemo.isSelected()) {
				tekKbn = "1";
			}

			Map conds = new HashMap();
			// 送信するパラメータを設定
			conds.put("flag", "report");
			// 会社コードの取得
			conds.put("kaiCode", getLoginUserCompanyCode());
			// 開始コードの取得
			conds.put("beginTekCode", panel.ctrlBeginMemo.getField().getText());
			// 終了コードの取得
			conds.put("endTekCode", panel.ctrlEndMemo.getField().getText());
			// 言語コードの取得
			conds.put("langCode", getLoginLanguage());
			// 摘要区分の素y特
			conds.put("tekKbn", tekKbn);
			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
