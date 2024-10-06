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
 * 通貨マスタ画面コントロール
 */
public class MG0300CurrencyMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	protected static final String PROGRAM_ID = "MG0300";

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
		return "MG0300CurrencyMasterServlet";
	}

	/** パネル */
	protected MG0300CurrencyMasterPanel panel;

	protected REFDialogCtrl ref1;

	protected REFDialogCtrl ref2;

	/**
	 * コンストラクタ.
	 */
	public MG0300CurrencyMasterPanelCtrl() {
		try {
			panel = new MG0300CurrencyMasterPanel(this);
		} catch (Exception e) {
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
	void init() {
		ref1 = new REFDialogCtrl(panel.ctrlBeginCurrency, panel.getParentFrame());
		ref1.setColumnLabels("C00665", "C00881", "C00882");
		ref1.setTargetServlet("MG0300CurrencyMasterServlet");
		ref1.setTitleID(getWord("C01985"));
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new LinkedHashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndCurrency.getField().getText());
				keys.put("kind", "Currency");
				return keys;
			}
		});

		panel.ctrlBeginCurrency.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}
		});

		ref2 = new REFDialogCtrl(panel.ctrlEndCurrency, panel.getParentFrame());
		ref2.setColumnLabels("C00665", "C00881", "C00882");
		ref2.setTargetServlet("MG0300CurrencyMasterServlet");
		ref2.setTitleID(getWord("C01985"));
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new LinkedHashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginCurrency.getField().getText());
				keys.put("kind", "Currency");
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
	 * 新規登録処理
	 */
	void insert() {
		try {
			MG0300EditDisplayDialogCtrl dialog = new MG0300EditDisplayDialogCtrl(panel.getParentFrame(), "C01698");

			dialog.show();

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), true);
			// 選択行保持する
			modifySpreadRow(dialog.getDataList().get("curCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 編集処理
	 */
	void update() {
		try {
			MG0300EditDisplayDialogCtrl dialog = new MG0300EditDisplayDialogCtrl(panel.getParentFrame(), "C00977");

			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();

			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("curCode", model.getTableDataItem(nomRow, 1));
			map.put("curName", model.getTableDataItem(nomRow, 2));
			map.put("curName_S", model.getTableDataItem(nomRow, 3));
			map.put("curName_K", model.getTableDataItem(nomRow, 4));
			map.put("decKeta", model.getTableDataItem(nomRow, 7));
			map.put("ratePow", model.getTableDataItem(nomRow, 5));
			map.put("convKbn", model.getTableDataItem(nomRow, 10));
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 8)));
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			map.put("flag", "update");

			dialog.setValues(map);
			dialog.show();

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), false);

			// スプレッド更新
			modifySpreadRow(dialog.getDataList().get("curCode").toString(), UPD_KBN);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 複写処理
	 */
	public void copy() {
		try {
			MG0300EditDisplayDialogCtrl dialog = new MG0300EditDisplayDialogCtrl(panel.getParentFrame(), "C01738");
			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();

			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("curCode", model.getTableDataItem(nomRow, 1));
			map.put("curName", model.getTableDataItem(nomRow, 2));
			map.put("curName_S", model.getTableDataItem(nomRow, 3));
			map.put("curName_K", model.getTableDataItem(nomRow, 4));
			map.put("decKeta", model.getTableDataItem(nomRow, 7));
			map.put("ratePow", model.getTableDataItem(nomRow, 5));
			map.put("convKbn", model.getTableDataItem(nomRow, 10));
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 8)));
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			map.put("flag", "insert");

			dialog.setValues(map);
			dialog.show();

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), true);

			// スプレッド更新
			modifySpreadRow(dialog.getDataList().get("curCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param curCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String curCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(curCode, updKbn);
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
	 * @param curCode
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected Vector<Vector> setModifyCell(String curCode, int updKbn) throws IOException, TRequestException {
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginCurCode", Util.avoidNull(curCode));
		addSendValues("endCurCode", Util.avoidNull(curCode));

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
			Iterator dataIte = ((List) recordIte.next()).iterator();

			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}

			String rate, dec;
			dec = colum.get(7);
			rate = colum.get(5);

			// 摘要区分
			String value = colum.get(6);
			String text = ("0".equals(value) ? this.getWord("C00065") : this.getWord("C00563"));

			// レート係数
			colum.set(5, rate);
			// 小数点以下桁数
			colum.set(7, dec);

			try {
				colum.set(8, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(8))));
				colum.set(9, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(9))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(8), ex);
			}
			colum.add(10, value);
			colum.set(6, text);

		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssJournal.getCurrentRow(), colum);
		}
		return cells;
	}

	/**
	 * 変更処理
	 * 
	 * @param map データ
	 * @param isInsert 新規(true) or 更新(false)
	 * @throws IOException
	 */
	protected void modify(Map map, boolean isInsert) throws IOException {
		addSendValues("flag", isInsert ? "insert" : "update");
		addSendValues("kaiCode", getLoginUserCompanyCode());
		addSendValues("curCode", (String) map.get("curCode"));
		addSendValues("curName", (String) map.get("curName"));
		addSendValues("curName_S", (String) map.get("curName_S"));
		addSendValues("curName_K", (String) map.get("curName_K"));
		addSendValues("decKeta", (String) map.get("decKeta"));
		addSendValues("ratePow", (String) map.get("ratePow"));
		addSendValues("convKbn", (String) map.get("convKbn"));
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		addSendValues("prgID", PROGRAM_ID);

		if (!request(getServletName())) {
			// サーバ側のエラー場合
			errorHandler(panel);
		}
	}

	/**
	 * 削除処理
	 */
	void delete() {
		if (this.showConfirmMessage(panel, FocusButton.NO, "Q00007")) {
			try {
				// 選択されている行の1つ目と2つ目のカラムを取得
				int nomRow = panel.ssJournal.getCurrentRow();
				TableDataModel model = panel.ssJournal.getDataSource();

				String kaiCode = (String) model.getTableDataItem(nomRow, 0); // 会社コード
				String curCode = (String) model.getTableDataItem(nomRow, 1); // 通貨コード
				addSendValues("flag", "delete");
				addSendValues("kaiCode", kaiCode);
				addSendValues("curCode", curCode);

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

		// 開始コードの取得
		String strBegin = panel.ctrlBeginCurrency.getValue();
		// 終了コードの取得
		String strEnd = panel.ctrlEndCurrency.getValue();
		try {
			// 開始コードが終了コードより大きい
			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				// 警告メッセージ表示
				panel.ctrlBeginCurrency.getField().requestFocus();
				showMessage(panel, "W00036", "C00371");
				return false;
			}
			return reflesh();
		} catch (IOException e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}

		return false;
	}

	/**
	 * 画面リフレッシュ
	 * 
	 * @return boolean
	 * @throws IOException
	 */
	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		String kaiCode = getLoginUserCompanyCode();

		String beginCurCode = panel.ctrlBeginCurrency.getValue();
		String endCurCode = panel.ctrlEndCurrency.getValue();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginCurCode", Util.avoidNull(beginCurCode));
		addSendValues("endCurCode", Util.avoidNull(endCurCode));

		// サーバ側のエラー場合
		if (!request(getServletName())) {
			errorHandler(panel);
			return false;
		}
		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();

		Iterator recordIte = getResultList().iterator();
		if (!recordIte.hasNext()) {
			panel.ctrlBeginCurrency.getField().requestFocus();
			// 警告メッセージ表示
			showMessage(panel, "W00100");
			dataExists = false;
		}
		for (int row = 0; recordIte.hasNext(); row++) {
			Iterator dataIte = ((List) recordIte.next()).iterator();

			Vector<String> colum = new Vector<String>();

			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}

			String rate, dec;
			dec = colum.get(7);
			rate = colum.get(5);

			// 摘要区分
			String value = colum.get(6);
			String text = ("0".equals(value) ? this.getWord("C00065") : this.getWord("C00563"));

			// レート係数
			colum.set(5, rate);
			// 小数点以下桁数
			colum.set(7, dec);

			try {
				colum.set(8, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(8))));
				colum.set(9, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(9))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(8), ex);
			}
			colum.add(10, value);
			colum.set(6, text);

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
			panel.ctrlBeginCurrency.getField().requestFocus();
			// 確認ダイアログ表示
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("langCode", getLoginLanguage());
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginCurCode", panel.ctrlBeginCurrency.getValue());
			conds.put("endCurCode", panel.ctrlEndCurrency.getValue());
			conds.put("langCode", getLoginLanguage());
			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
