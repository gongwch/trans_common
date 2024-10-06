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
 * 消費税マスタ
 */
public class MG0130ConsumptionTaxMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	private static final String PROGRAM_ID = "MG0130";

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0130ConsumptionTaxMasterServlet";

	/** 新規と複写区分 */
	protected static int INSERT_KBN = 0;

	/** 編集区分 */
	protected static int UPD_KBN = 1;

	/** パネル */
	protected MG0130ConsumptionTaxMasterPanel panel;

	protected Map<String, String> usKbnMap;

	private REFDialogCtrl refBeginConsumptionTax;

	private REFDialogCtrl refEndConsumptionTax;

	MG0130EditDisplayDialogCtrl dialog;

	/**
	 * コンストラクタ.
	 */

	public MG0130ConsumptionTaxMasterPanelCtrl() {
		try {
			// 一覧画面の初期化
			panel = new MG0130ConsumptionTaxMasterPanel(this);
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

		// 言語の指定された名称を表示する
		usKbnMap = new LinkedHashMap();
		usKbnMap.put("0", getWord("C02103"));
		usKbnMap.put("1", getWord("C00026"));
		usKbnMap.put("2", getWord("C00201"));

	}

	void init() {
		// 開始コードの設定
		refBeginConsumptionTax = new REFDialogCtrl(panel.ctrlBeginConsumptionTax, panel.getParentFrame());
		refBeginConsumptionTax.setTargetServlet(TARGET_SERVLET);
		refBeginConsumptionTax.setTitleID("C02324");
		refBeginConsumptionTax.setColumnLabels("C00573", "C00775", "C00828");
		refBeginConsumptionTax.setShowsMsgOnError(false);
		refBeginConsumptionTax.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				keys.put("kind", "ConsumptionTax");
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndConsumptionTax.getField().getText());
				// データを返す
				return keys;
			}
		});

		panel.ctrlBeginConsumptionTax.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refBeginConsumptionTax.refreshName();
				return true;
			}
		});

		// 終了コードの設定

		refEndConsumptionTax = new REFDialogCtrl(panel.ctrlEndConsumptionTax, panel.getParentFrame());
		refEndConsumptionTax.setTargetServlet(TARGET_SERVLET);
		refEndConsumptionTax.setTitleID("C02324");
		refEndConsumptionTax.setColumnLabels("C00573", "C00775", "C00828");
		refEndConsumptionTax.setShowsMsgOnError(false);
		refEndConsumptionTax.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				keys.put("kind", "ConsumptionTax");
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginConsumptionTax.getField().getText());
				// データを返す
				return keys;
			}
		});

		panel.ctrlEndConsumptionTax.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refEndConsumptionTax.refreshName();
				return true;
			}
		});

	}

	/**
	 * パネル取得
	 * 
	 * @return 消費税マスタパネル
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
		dialog = new MG0130EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * 新規登録処理
	 */
	void insert() {
		try {
			createEditDisplayDialog("C01698");
			dialog.setUsKbnMap(usKbnMap);
			// 編集画面の表示
			dialog.show(true);
			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), true);
			unlockBtn();
			// 選択行保持する
			modifySpreadRow(dialog.getDataList().get("zeiCode").toString(), INSERT_KBN);
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
			createEditDisplayDialog("C00977");
			dialog.setUsKbnMap(usKbnMap);
			// 当前行を取得する
			int nomRow = panel.ssConsumptionTax.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssConsumptionTax.getDataSource();
			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			// データ集を取得する
			Map<String, Object> map = new TreeMap<String, Object>();
			// 消費税コードの設定
			map.put("zeiCode", model.getTableDataItem(nomRow, 1));
			// 消費税名称の設定
			map.put("zeiName", model.getTableDataItem(nomRow, 2));
			// 消費税略称の設定
			map.put("zeiName_S", model.getTableDataItem(nomRow, 3));
			// 消費税検索名称の設定
			map.put("zeiName_K", model.getTableDataItem(nomRow, 4));
			// 売上仕入区分の設定
			map.put("usKbn", model.getTableDataItem(nomRow, 14));
			// 消費税率の設定
			map.put("zeiRate", model.getTableDataItem(nomRow, 6));
			// 消費税計算書出力順序の設定
			String szeiKeiKbn = String.valueOf(model.getTableDataItem(nomRow, 7));
			if (szeiKeiKbn.equals(this.getWord("C00282"))) {
				szeiKeiKbn = "";
				map.put("szeiKeiKbn", szeiKeiKbn);
			} else {
				map.put("szeiKeiKbn", model.getTableDataItem(nomRow, 7));
			}
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 8)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			// 処理種別の設定ﾞ
			map.put("flag", "update");
			// データを画面に設定するﾞ
			dialog.setValues(map);
			// 編集画面を表示するﾞ
			dialog.show(false);
			// 編集画面が開けていましたﾞ
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), false);
			// スプレッド更新
			modifySpreadRow(dialog.getDataList().get("zeiCode").toString(), UPD_KBN);
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
			createEditDisplayDialog("C01738");
			dialog.setUsKbnMap(usKbnMap);
			// 当前行を取得する
			int nomRow = panel.ssConsumptionTax.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssConsumptionTax.getDataSource();
			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 消費税コードの設定
			map.put("zeiCode", model.getTableDataItem(nomRow, 1));
			// 消費税名称の設定
			map.put("zeiName", model.getTableDataItem(nomRow, 2));
			// 消費税略称の設定
			map.put("zeiName_S", model.getTableDataItem(nomRow, 3));
			// 消費税検索名称の設定
			map.put("zeiName_K", model.getTableDataItem(nomRow, 4));
			// 売上仕入区分の設定
			map.put("usKbn", model.getTableDataItem(nomRow, 14));
			// 消費税率の設定
			map.put("zeiRate", model.getTableDataItem(nomRow, 6));
			// 消費税計算書出力順序の設定
			String szeiKeiKbn = String.valueOf(model.getTableDataItem(nomRow, 7));
			if (szeiKeiKbn.equals(this.getWord("C00282"))) {
				szeiKeiKbn = "";
				map.put("szeiKeiKbn", szeiKeiKbn);
			} else {
				map.put("szeiKeiKbn", model.getTableDataItem(nomRow, 7));
			}
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 8)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			// 処理種別の設定ﾞ
			map.put("flag", "insert");
			// データを画面に設定するﾞ
			dialog.setValues(map);
			dialog.show(true);
			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), true);

			modifySpreadRow(dialog.getDataList().get("zeiCode").toString(), INSERT_KBN);
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
		// 会社コードの設定
		super.addSendValues("kaiCode", getLoginUserCompanyCode());
		// 消費税コードの設定
		super.addSendValues("zeiCode", (String) map.get("zeiCode"));
		// 消費税名称の設定
		super.addSendValues("zeiName", (String) map.get("zeiName"));
		// 消費税略称の設定
		super.addSendValues("zeiName_S", (String) map.get("zeiName_S"));
		// 消費税検索名称の設定
		super.addSendValues("zeiName_K", (String) map.get("zeiName_K"));
		// 売上仕入区分の設定
		super.addSendValues("usKbn", (String) map.get("usKbn"));
		// 消費税率の設定
		super.addSendValues("zeiRate", (String) map.get("zeiRate"));
		// 消費税計算書出力順序の設定
		super.addSendValues("szeiKeiKbn", (String) map.get("szeiKeiKbn"));
		// 開始年月日の設定
		super.addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// 終了年月日の設定
		super.addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// プログラムＩＤの設定の設定
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
	 * @param zeiCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String zeiCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(zeiCode, updKbn);
		TTable ssPanelSpread = panel.ssConsumptionTax;
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
		lockBtn(panel.ssConsumptionTax.getDataSource().getNumRows() != 0);
	}

	/**
	 * 新規、複写、編集の場合、スプレッド取得
	 * 
	 * @param zeiCode
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String zeiCode, int updKbn) throws IOException, TRequestException {
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginZeiCode", Util.avoidNull(zeiCode));
		// 終了コードの設定
		addSendValues("endZeiCode", Util.avoidNull(zeiCode));

		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssConsumptionTax.getDataSource();
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
				// 結果集を追加する
				colum.add(column, (String) dataIte.next());
			}

			String value, text;
			// 売上仕入区分
			value = colum.get(5);
			text = usKbnMap.get(value);
			colum.add(14, value);
			colum.set(5, text);

			String num;
			DecimalFormat format = new DecimalFormat("#0.0");
			num = format.format(Float.valueOf((colum.get(6))));
			colum.set(6, num);
			// 消費税計算書出力順序区分
			value = colum.get(7);
			if (Util.isNullOrEmpty(value)) {
				colum.set(7, this.getWord("C00282"));
			} else {
				colum.set(7, value);
			}
			try {
				colum.set(8, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(8))));
				colum.set(9, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(9))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(8), ex);
			}
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssConsumptionTax.getCurrentRow(), colum);
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
				int nomRow = panel.ssConsumptionTax.getCurrentRow();
				// データ集を取得する
				TableDataModel model = panel.ssConsumptionTax.getDataSource();
				// 会社コードの取得
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// 消費税コードの取得
				String zeiCode = (String) model.getTableDataItem(nomRow, 1);
				// 処理種別の設定
				super.addSendValues("flag", "delete");
				// 会社コードの設定
				super.addSendValues("kaiCode", kaiCode);
				// 消費税コードの設定
				super.addSendValues("zeiCode", zeiCode);

				// サーブレットの接続先
				if (!request(TARGET_SERVLET)) {
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
		int row = panel.ssConsumptionTax.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssConsumptionTax.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssConsumptionTax.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssConsumptionTax.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// 保持しておいた値をスクロールバーにセット
			panel.ssConsumptionTax.getVertSB().setValue(0);
			panel.ssConsumptionTax.getHorizSB().setValue(0);
		}
		// ボタンロック解除
		lockBtn(panel.ssConsumptionTax.getDataSource().getNumRows() != 0);
	}

	/**
	 * マスタ選択行状態の保持
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {
		panel.ssConsumptionTax.setRowSelection(row, row);
		panel.ssConsumptionTax.setCurrentCell(row, 0);
	}

	/**
	 * 検索処理
	 * 
	 * @return boolean
	 */
	boolean find() {
		String beginConsumptionTax = panel.ctrlBeginConsumptionTax.getValue().toString();
		String endConsumptionTax = panel.ctrlEndConsumptionTax.getValue().toString();

		try {
			// 警告メッセージ表示
			if (Util.isSmallerThen(beginConsumptionTax, endConsumptionTax) == false) {
				panel.ctrlBeginConsumptionTax.getField().requestFocus();
				showMessage(panel, "W00036", "C00286");
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
	 * ボタン(新規、削除、編集、複写、リスト出力)ロック
	 * 
	 * @param boo
	 */
	void lockBtn(boolean boo) {
		panel.btnDelete.setEnabled(boo);
		panel.btnEdit.setEnabled(boo);
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
	 * 画面リフレッシュ
	 * 
	 * @return boolean
	 * @throws IOException
	 */
	private boolean reflesh() throws IOException {
		boolean dataExists = true;
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();
		// 開始コードの取得
		String beginZeiCode = panel.ctrlBeginConsumptionTax.getValue();
		// 終了コードの取得
		String endZeiCode = panel.ctrlEndConsumptionTax.getValue();

		beginZeiCode = beginZeiCode.trim();
		endZeiCode = endZeiCode.trim();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginZeiCode", Util.avoidNull(beginZeiCode));
		// 終了コードの設定
		addSendValues("endZeiCode", Util.avoidNull(endZeiCode));
		// 送信
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return false;
		}
		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();
		// 結果を取得する
		Iterator recordIte = getResultList().iterator();

		// 値があるかどうか
		if (!recordIte.hasNext()) {
			panel.ctrlBeginConsumptionTax.getField().requestFocus();
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
				// 結果集を追加する
				colum.add(column, (String) dataIte.next());
			}

			String value, text;
			// 売上仕入区分
			value = colum.get(5);
			text = usKbnMap.get(value);
			colum.add(14, value);
			colum.set(5, text);

			String num;
			DecimalFormat format = new DecimalFormat("#0.0");
			num = format.format(Float.valueOf((colum.get(6))));
			colum.set(6, num);
			// 消費税計算書出力順序区分
			value = colum.get(7);
			if (Util.isNullOrEmpty(value)) {
				colum.set(7, this.getWord("C00282"));
			} else {
				colum.set(7, value);
			}
			try {
				colum.set(8, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(8))));
				colum.set(9, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(9))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(8), ex);
			}
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
			panel.ctrlBeginConsumptionTax.getField().requestFocus();
			// 確認ダイアログ表示
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			// 送信するパラメータを設定
			conds.put("flag", "report");
			// 会社コードの取得
			conds.put("kaiCode", getLoginUserCompanyCode());
			// 開始コードの取得
			conds.put("beginZeiCode", panel.ctrlBeginConsumptionTax.getValue());
			// 終了コードの取得
			conds.put("endZeiCode", panel.ctrlEndConsumptionTax.getValue());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, TARGET_SERVLET, conds);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
