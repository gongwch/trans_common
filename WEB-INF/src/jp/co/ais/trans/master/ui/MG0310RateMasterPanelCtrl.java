package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * レートマスタ画面コントロール
 */
public class MG0310RateMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	private static final String PROGRAM_ID = "MG0310";

	/** 新規と複写区分 */
	private static int INSERT_KBN = 0;

	/** 編集区分 */
	private static int UPD_KBN = 1;

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "MG0310RateMasterServlet";

	/** パネル */
	protected MG0310RateMasterPanel panel;

	protected REFDialogCtrl ref1;

	protected REFDialogCtrl ref2;

	MG0310EditDisplayDialogCtrl dialog;

	/**
	 * コンストラクタ.
	 */
	public MG0310RateMasterPanelCtrl() {
		try {
			// 一覧画面の初期化
			panel = new MG0310RateMasterPanel(this);
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

	/**
	 * 画面初期化
	 */
	protected void init() {
		// 開始コードの設定
		ref1 = new REFDialogCtrl(panel.ctrlBeginCurrency, panel.getParentFrame());
		ref1.setColumnLabels("C00665", "C00881", "C00882");
		ref1.setTargetServlet("MG0300CurrencyMasterServlet");
		ref1.setTitleID(getWord("C01985"));
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				// mapの初期化
				Map keys = new HashMap();
				// 会社コードの設定
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndCurrency.getField().getText());
				keys.put("kind", "Currency");
				// データを返す
				return keys;
			}
		});

		panel.ctrlBeginCurrency.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}
		});

		// 終了コードの設定
		ref2 = new REFDialogCtrl(panel.ctrlEndCurrency, panel.getParentFrame());
		ref2.setColumnLabels("C00665", "C00881", "C00882");
		ref2.setTargetServlet("MG0300CurrencyMasterServlet");
		ref2.setTitleID(getWord("C01985"));
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				// mapの初期化
				Map keys = new HashMap();
				// 会社コードの設定
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginCurrency.getField().getText());
				keys.put("kind", "Currency");
				// データを返す
				return keys;
			}
		});

		panel.ctrlEndCurrency.setInputVerifier(new TInputVerifier() {

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
		dialog = new MG0310EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * 新規登録処理
	 */
	void insert() {
		try {
			// 編集画面の初期化
			createEditDisplayDialog("C01698");

			// 編集画面の表示
			dialog.show(true);
			// 編集画面が開けていました
			if (!dialog.isSettle()) return;
			// データが編集する
			modify(dialog.getDataList(), true);
			// ボタンロックの解除
			lockBtn(true);

			// 選択行保持する
			String curCode = dialog.getDataList().get("curCode").toString();
			String strDate = DateUtil.toYMDHMSString((Date) dialog.getDataList().get("strDate"));
			modifySpreadRow(curCode, strDate, INSERT_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel);
		}
	}

	/**
	 * 編集処理
	 */
	void update() {
		try {
			createEditDisplayDialog("C00977");
			// 当前行を取得する
			int nomRow = panel.ssRateList.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssRateList.getDataSource();
			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) return;
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 通貨コードの設定
			map.put("curCode", model.getTableDataItem(nomRow, 1));
			// 摘要開始日付の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 2)));
			// レートの設定
			map.put("curRate", model.getTableDataItem(nomRow, 3));
			// 処理種別の設定
			map.put("flag", "update");
			// データを画面に設定する
			dialog.setValues(map);
			// 編集画面の表示
			dialog.show(false);
			// 編集画面が開けていました
			if (!dialog.isSettle()) return;
			// データを編集する
			modify(dialog.getDataList(), false);
			// スプレッド更新
			String curCode = dialog.getDataList().get("curCode").toString();
			String strDate = DateUtil.toYMDHMSString((Date) dialog.getDataList().get("strDate"));
			modifySpreadRow(curCode, strDate, UPD_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel);
		}
	}

	/**
	 * 複写処理
	 */
	public void copy() {
		try {
			createEditDisplayDialog("C01738");
			// 当前行を取得する
			int nomRow = panel.ssRateList.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssRateList.getDataSource();
			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) return;
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 通貨コードの設定
			map.put("curCode", model.getTableDataItem(nomRow, 1));
			// 摘要開始日付の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 2)));
			// レートの設定
			map.put("curRate", model.getTableDataItem(nomRow, 3));
			// 処理種別の設定
			map.put("flag", "insert");
			// データを画面に設定する
			dialog.setValues(map);
			// 編集画面の表示
			dialog.show(true);
			// 編集画面が開けていました
			if (!dialog.isSettle()) return;
			// データを編集する
			modify(dialog.getDataList(), true);
			// スプレッド更新
			String curCode = dialog.getDataList().get("curCode").toString();
			String strDate = DateUtil.toYMDHMSString((Date) dialog.getDataList().get("strDate"));
			modifySpreadRow(curCode, strDate, INSERT_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel);
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
		// 会社コードの設定
		super.addSendValues("kaiCode", (String) map.get("kaiCode"));
		// 通貨コードの設定
		super.addSendValues("curCode", (String) map.get("curCode"));
		// 摘要開始日付の設定
		super.addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// レートの設定
		super.addSendValues("curRate", (String) map.get("curRate"));
		// プログラムＩＤの設定
		super.addSendValues("prgID", PROGRAM_ID);
		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
			// サーバ側のエラー場合
			errorHandler(panel);
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param curCode
	 * @param strDate
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 * @throws ParseException
	 */
	private void modifySpreadRow(String curCode, String strDate, int updKbn) throws TRequestException, IOException,
		ParseException {
		Vector<Vector> cells = setModifyCell(curCode, strDate, updKbn);
		TTable ssPanelSpread = panel.ssRateList;
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
		lockBtn(panel.ssRateList.getDataSource().getNumRows() != 0);
	}

	/**
	 * 新規、複写、編集の場合、スプレッド取得
	 * 
	 * @param curCode
	 * @param strDate
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 * @throws ParseException
	 */
	private Vector<Vector> setModifyCell(String curCode, String strDate, int updKbn) throws IOException,
		TRequestException, ParseException {
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		// 処理種別の設定
		addSendValues("flag", "findOne");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("curCode", Util.avoidNull(curCode));
		// 終了コードの取得
		addSendValues("strDate", strDate);

		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssRateList.getDataSource();
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
			// columの初期化
			for (int column = 0; dataIte.hasNext(); column++) {
				// 結果を追加する
				colum.add(column, (String) dataIte.next());
			}

			colum.set(2, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(2))));

			String num = NumberFormatUtil.formatNumber(new BigDecimal(colum.get(3)), 4);
			colum.set(3, num);

		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssRateList.getCurrentRow(), colum);
		}
		return cells;
	}

	/**
	 * 削除処理
	 */
	void delete() {
		try {
			if (!this.showConfirmMessage(panel, "Q00007", "")) {
				return;
			}

			// 選択されている行の1つ目と2つ目のカラムを取得
			int nomRow = panel.ssRateList.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssRateList.getDataSource();
			// 会社コードの取得
			String kaiCode = (String) model.getTableDataItem(nomRow, 0);
			// 通貨コードの取得
			String curCode = (String) model.getTableDataItem(nomRow, 1);
			// 適用開始日付の取得
			String strDate = (String) model.getTableDataItem(nomRow, 2);
			// 処理種別の設定
			super.addSendValues("flag", "delete");
			// 会社コードの設定
			super.addSendValues("kaiCode", kaiCode);
			// 通貨コードの設定
			super.addSendValues("curCode", curCode);
			// 適用開始日付の設定
			super.addSendValues("strDate", strDate);

			// サーブレットの接続先
			if (!request(TARGET_SERVLET)) {
				// サーバ側のエラー場合
				errorHandler(panel);
				return;
			}

			deleteSpreadRow();
		} catch (IOException e) {
			// 正常に処理されませんでした
			errorHandler(panel);
		}
	}

	/**
	 * 削除場合、スプレッド更新
	 */
	private void deleteSpreadRow() {
		int row = panel.ssRateList.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssRateList.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssRateList.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssRateList.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// 保持しておいた値をスクロールバーにセット
			panel.ssRateList.getVertSB().setValue(0);
			panel.ssRateList.getHorizSB().setValue(0);
		}
		// ボタンロック解除
		lockBtn(panel.ssRateList.getDataSource().getNumRows() != 0);
	}

	/**
	 * マスタ選択行状態の保持
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {
		panel.ssRateList.setRowSelection(row, row);
		panel.ssRateList.setCurrentCell(row, 0);
	}

	/**
	 * 検索処理
	 * 
	 * @return boolean
	 */
	boolean find() {
		try {
			// 開始コードの取得
			String strBegin = panel.ctrlBeginCurrency.getValue();
			// 終了コードの取得
			String strEnd = panel.ctrlEndCurrency.getValue();
			// 開始コードが終了コードより大きい
			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				// 警告メッセージ表示
				panel.ctrlBeginCurrency.getField().requestFocus();
				super.showMessage(panel, "W00036", "C00371");
				return false;
			}
			// 表示データの取得
			return reflesh();
			// ボタンロック解除

		} catch (IOException e) {
			// ボタンロックの設定
			lockBtn(false);
			// 正常に処理されませんでした
			errorHandler(panel);
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
	 * 表示データの取得
	 * 
	 * @return データ
	 * @throws IOException
	 */
	protected boolean reflesh() throws IOException {
		try {
			boolean dataExists = true;
			// 会社コードの取得
			String kaiCode = getLoginUserCompanyCode();
			// 開始コードの取得
			String beginCurCode = panel.ctrlBeginCurrency.getValue();
			String endCurCode = panel.ctrlEndCurrency.getValue();
			// 送信するパラメータを設定
			addSendValues("flag", "find");
			// 会社コードの設定
			addSendValues("kaiCode", kaiCode);
			// 開始コードの設定
			addSendValues("beginCurCode", Util.avoidNull(beginCurCode));
			// 終了コードの取得
			addSendValues("endCurCode", Util.avoidNull(endCurCode));

			// サーブレットの接続先
			if (!request(TARGET_SERVLET)) {
				// サーバ側のエラー場合
				errorHandler(panel);
				return false;
			}

			// サーブレットから送られてきた結果を配列にセット
			Vector<Vector> cells = new Vector<Vector>();
			// 結果を取得する
			Iterator recordIte = getResultList().iterator();
			// 値があるかどうか
			if (!recordIte.hasNext()) {
				panel.ctrlBeginCurrency.getField().requestFocus();
				showMessage(panel, "W00100");
				dataExists = false;
			}
			for (int row = 0; recordIte.hasNext(); row++) {
				// 結果の内容を取得する
				Iterator dataIte = ((List) recordIte.next()).iterator();
				// columの初期化
				Vector<String> colum = new Vector<String>();
				for (int column = 0; dataIte.hasNext(); column++) {
					// 結果を追加する
					colum.add(column, (String) dataIte.next());
				}

				colum.set(2, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(2))));

				String num = NumberFormatUtil.formatNumber(new BigDecimal(colum.get(3)), 4);
				colum.set(3, num);

				// 結果集を追加する
				cells.add(row, colum);
			}
			// 結果を表示する
			panel.setDataList(cells);
			return dataExists;
		} catch (ParseException ex) {
			errorHandler(panel);
			return false;
		}
	}

	/**
	 * エクセルリスト出力
	 */
	void outptExcel() {

		try {
			panel.ctrlBeginCurrency.getField().requestFocus();
			// 確認ダイアログ表示
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginCurCode", panel.ctrlBeginCurrency.getValue());
			conds.put("endCurCode", panel.ctrlEndCurrency.getValue());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, TARGET_SERVLET, conds);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel);
		}
	}
}
