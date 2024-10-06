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
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 伝票種別マスタ画面コントロール
 */
public class MG0330SlipTypeMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	protected static final String PROGRAM_ID = "MG0330";

	/** 新規と複写区分 */
	protected static int INSERT_KBN = 0;

	/** 編集区分 */
	protected static int UPD_KBN = 1;

	MG0330EditDisplayDialogCtrl dialog;

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0330SlipTypeMasterServlet";
	}

	/** パネル */
	protected MG0330SlipTypeMasterPanel panel;

	protected Map dataKbnMap;

	protected Map taSysKbnMap;

	protected Map datSaiBanFlgMap;

	protected Map taniMap;

	protected Map zeiKbnMap;

	protected Map swkInKbnMap;

	private REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	/**
	 * コンストラクタ.
	 */

	public MG0330SlipTypeMasterPanelCtrl() {
		// 一覧画面の初期化
		try {
			panel = new MG0330SlipTypeMasterPanel(this);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

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
		dataKbnMap.put("41", getWord("C02091"));
		dataKbnMap.put("42", getWord("C02092"));
		dataKbnMap.put("51", getWord("C02093"));
		dataKbnMap.put("52", getWord("C02094"));
		dataKbnMap.put("61", getWord("C02095"));
		dataKbnMap.put("62", getWord("C02096"));
		taSysKbnMap = new LinkedHashMap();
		taSysKbnMap.put("0", getWord("C02097"));
		taSysKbnMap.put("1", getWord("C02098"));
		datSaiBanFlgMap = new LinkedHashMap();
		datSaiBanFlgMap.put("0", getWord("C02099"));
		datSaiBanFlgMap.put("1", getWord("C02100"));
		taniMap = new LinkedHashMap();
		taniMap.put("0", getWord("C02101"));
		taniMap.put("1", getWord("C02102"));
		zeiKbnMap = new LinkedHashMap();
		zeiKbnMap.put("0", getWord("C00337"));
		zeiKbnMap.put("1", getWord("C00023"));
		swkInKbnMap = new LinkedHashMap();
		swkInKbnMap.put("0", getWord("C01258"));
		swkInKbnMap.put("1", getWord("C01168"));

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
		ref1 = new REFDialogCtrl(panel.ctrlBeginSlipType, panel.getParentFrame());
		ref1.setColumnLabels("C00837", "C00839", "C02757");
		ref1.setTargetServlet("MG0330SlipTypeMasterServlet");
		ref1.setTitleID(getWord("C00912"));
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndSlipType.getField().getText());
				keys.put("kind", "SlipType");
				return keys;
			}
		});

		panel.ctrlBeginSlipType.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}
		});

		// 終了コードの設定
		ref2 = new REFDialogCtrl(panel.ctrlEndSlipType, panel.getParentFrame());
		ref2.setColumnLabels("C00837", "C00839", "C02757");
		ref2.setTargetServlet("MG0330SlipTypeMasterServlet");
		ref2.setTitleID(getWord("C00912"));
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginSlipType.getField().getText());
				keys.put("kind", "SlipType");
				return keys;
			}
		});

		panel.ctrlEndSlipType.setInputVerifier(new TInputVerifier() {

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
		dialog = new MG0330EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * 新規登録処理
	 */
	void insert() {
		try {
			createEditDisplayDialog("C01698");
			dialog.setDataKbnMap(dataKbnMap);
			dialog.setDatSaiBanFlgMap(datSaiBanFlgMap);
			dialog.setSwkInKbnMap(swkInKbnMap);
			dialog.setTaniMap(taniMap);
			dialog.setTaSysKbnMap(taSysKbnMap);
			dialog.setZeiKbnMap(zeiKbnMap);
			dialog.show();

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), true);
			// 選択行保持する
			modifySpreadRow(dialog.getDataList().get("denSyuCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 編集処理
	 */
	void update() {
		try {
			createEditDisplayDialog("C00977");

			dialog.setDataKbnMap(dataKbnMap);
			dialog.setDatSaiBanFlgMap(datSaiBanFlgMap);
			dialog.setSwkInKbnMap(swkInKbnMap);
			dialog.setTaniMap(taniMap);
			dialog.setTaSysKbnMap(taSysKbnMap);
			dialog.setZeiKbnMap(zeiKbnMap);

			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();
			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("denSyuCode", model.getTableDataItem(nomRow, 1));
			map.put("sysKbn", model.getTableDataItem(nomRow, 2));
			map.put("denSyuName", model.getTableDataItem(nomRow, 3));
			map.put("denSyuName_S", model.getTableDataItem(nomRow, 4));
			map.put("denSyuName_K", model.getTableDataItem(nomRow, 5));
			map.put("dataKbn", model.getTableDataItem(nomRow, 12));
			map.put("taSysKbn", model.getTableDataItem(nomRow, 13));
			map.put("datSaiBanFlg", model.getTableDataItem(nomRow, 14));
			map.put("tani", model.getTableDataItem(nomRow, 15));
			map.put("zeiKbn", model.getTableDataItem(nomRow, 16));
			map.put("swkInKbn", model.getTableDataItem(nomRow, 17));
			map.put("flag", "update");

			dialog.setValues(map);
			dialog.show();

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), false);

			// スプレッド更新
			modifySpreadRow(dialog.getDataList().get("denSyuCode").toString(), UPD_KBN);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * データ処理
	 */
	void enter() {
		try {
			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();

			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				lockBtn(false);
			} else {
				unlockBtn();
			}
		} catch (Exception e) {
			ClientLogger.error(this.getClass().getName(), e);
		}
	}

	/**
	 * 複写処理
	 */
	public void copy() {
		try {
			createEditDisplayDialog("C01738");

			dialog.setDataKbnMap(dataKbnMap);
			dialog.setDatSaiBanFlgMap(datSaiBanFlgMap);
			dialog.setSwkInKbnMap(swkInKbnMap);
			dialog.setTaniMap(taniMap);
			dialog.setTaSysKbnMap(taSysKbnMap);
			dialog.setZeiKbnMap(zeiKbnMap);

			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();
			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("denSyuCode", model.getTableDataItem(nomRow, 1));
			map.put("sysKbn", model.getTableDataItem(nomRow, 2));
			map.put("denSyuName", model.getTableDataItem(nomRow, 3));
			map.put("denSyuName_S", model.getTableDataItem(nomRow, 4));
			map.put("denSyuName_K", model.getTableDataItem(nomRow, 5));
			map.put("dataKbn", model.getTableDataItem(nomRow, 12));
			map.put("taSysKbn", model.getTableDataItem(nomRow, 13));
			map.put("datSaiBanFlg", model.getTableDataItem(nomRow, 14));
			map.put("tani", model.getTableDataItem(nomRow, 15));
			map.put("zeiKbn", model.getTableDataItem(nomRow, 16));
			map.put("swkInKbn", model.getTableDataItem(nomRow, 17));
			map.put("flag", "insert");

			dialog.setValues(map);
			dialog.show();

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), true);

			// スプレッド更新
			modifySpreadRow(dialog.getDataList().get("denSyuCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param denSyuCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String denSyuCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(denSyuCode, updKbn);
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
	 * @param denSyuCode
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String denSyuCode, int updKbn) throws IOException, TRequestException {
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginDenSyuCode", Util.avoidNull(denSyuCode));
		addSendValues("endDenSyuCode", Util.avoidNull(denSyuCode));

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

			String value, text;

			value = colum.get(6);
			text = (String) dataKbnMap.get(value);
			colum.add(12, value);
			colum.set(6, text);

			value = colum.get(7);
			text = (String) taSysKbnMap.get(value);
			colum.add(13, value);
			colum.set(7, text);

			value = colum.get(8);
			text = (String) datSaiBanFlgMap.get(value);
			colum.add(14, value);
			colum.set(8, text);

			value = colum.get(9);
			text = (String) taniMap.get(value);
			colum.add(15, value);
			colum.set(9, text);

			value = colum.get(10);
			text = (String) zeiKbnMap.get(value);
			colum.add(16, value);
			colum.set(10, text);

			value = colum.get(11);
			text = (String) swkInKbnMap.get(value);
			colum.add(17, value);
			colum.set(11, text);
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
		super.addSendValues("flag", isInsert ? "insert" : "update");
		super.addSendValues("kaiCode", (String) map.get("kaiCode"));
		super.addSendValues("dataKbn", (String) map.get("dataKbn"));
		super.addSendValues("denSyuCode", (String) map.get("denSyuCode"));
		super.addSendValues("sysKbn", (String) map.get("sysKbn"));
		super.addSendValues("denSyuName", (String) map.get("denSyuName"));
		super.addSendValues("denSyuName_S", (String) map.get("denSyuName_S"));
		super.addSendValues("denSyuName_K", (String) map.get("denSyuName_K"));
		super.addSendValues("taSysKbn", (String) map.get("taSysKbn"));
		super.addSendValues("datSaiBanFlg", (String) map.get("datSaiBanFlg"));
		super.addSendValues("tani", (String) map.get("tani"));
		super.addSendValues("zeiKbn", (String) map.get("zeiKbn"));
		super.addSendValues("swkInKbn", (String) map.get("swkInKbn"));
		super.addSendValues("prgID", PROGRAM_ID);

		if (!request(getServletName())) {
			// サーバ側のエラー場合
			errorHandler(panel);
		}
	}

	/**
	 * 削除処理
	 */
	void delete() {
		if (this.showConfirmMessage(panel, "Q00007", "")) {
			try {
				// 選択されている行の1つ目と2つ目のカラムを取得
				int nomRow = panel.ssJournal.getCurrentRow();
				TableDataModel model = panel.ssJournal.getDataSource();

				String kaiCode = (String) model.getTableDataItem(nomRow, 0); // 会社コード
				String denSyuCode = (String) model.getTableDataItem(nomRow, 1); // 伝票種別コード
				super.addSendValues("flag", "delete");
				super.addSendValues("kaiCode", kaiCode);
				super.addSendValues("denSyuCode", denSyuCode);

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
			String strBegin = panel.ctrlBeginSlipType.getValue();
			// 終了コードの取得
			String strEnd = panel.ctrlEndSlipType.getValue();
			// ボタンロック解除if
			if (!Util.isSmallerThen(strBegin, strEnd)) {
				// 警告メッセージ表示
				panel.ctrlBeginSlipType.getField().requestFocus();
				super.showMessage(panel, "W00036", "C00917");
				return false;
			}
			return reflesh();
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
	 */
	void outptExcel() {

		try {
			panel.ctrlBeginSlipType.getField().requestFocus();
			// 確認ダイアログ表示
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());

			String strBegin = panel.ctrlBeginSlipType.getValue();
			conds.put("beginDenSyuCode", strBegin);
			String strEnd = panel.ctrlEndSlipType.getValue();
			conds.put("endDenSyuCode", strEnd);
			conds.put("langCode", getLoginLanguage());

			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 表示データの取得
	 * 
	 * @return データ
	 * @throws IOException
	 */
	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		String kaiCode = getLoginUserCompanyCode();

		String beginDenSyuCode = panel.ctrlBeginSlipType.getValue();
		String endDenSyuCode = panel.ctrlEndSlipType.getValue();

		beginDenSyuCode = beginDenSyuCode.trim();
		endDenSyuCode = endDenSyuCode.trim();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginDenSyuCode", Util.avoidNull(beginDenSyuCode));
		addSendValues("endDenSyuCode", Util.avoidNull(endDenSyuCode));

		if (!request(getServletName())) {
			errorHandler(panel);
			return false;
		}

		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();

		Iterator recordIte = getResultList().iterator();
		if (!recordIte.hasNext()) {
			panel.ctrlBeginSlipType.getField().requestFocus();
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
			cells.add(row, colum);

			String value, text;

			value = colum.get(6);
			text = (String) dataKbnMap.get(value);
			colum.add(12, value);
			colum.set(6, text);

			value = colum.get(7);
			text = (String) taSysKbnMap.get(value);
			colum.add(13, value);
			colum.set(7, text);

			value = colum.get(8);
			text = (String) datSaiBanFlgMap.get(value);
			colum.add(14, value);
			colum.set(8, text);

			value = colum.get(9);
			text = (String) taniMap.get(value);
			colum.add(15, value);
			colum.set(9, text);

			value = colum.get(10);
			text = (String) zeiKbnMap.get(value);
			colum.add(16, value);
			colum.set(10, text);

			value = colum.get(11);
			text = (String) swkInKbnMap.get(value);
			colum.add(17, value);
			colum.set(11, text);

		}

		panel.setDataList(cells);
		return dataExists;
	}
}
