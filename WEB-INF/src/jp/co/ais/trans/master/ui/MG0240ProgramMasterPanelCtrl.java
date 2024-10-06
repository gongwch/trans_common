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
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * プログラムマスタ画面コiントロール
 */
public class MG0240ProgramMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	private static final String PROGRAM_ID = "MG0240";

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
		return "MG0240ProgramMasterServlet";
	}

	/** パネル */
	protected MG0240ProgramMasterPanel panel;

	// システム区分のデータ
	protected Map sysMap;

	private REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	MG0240EditDisplayDialogCtrl dialog;

	/**
	 * コンストラクタ.
	 */
	public MG0240ProgramMasterPanelCtrl() {
		try {
			// 一覧画面の初期化
			panel = new MG0240ProgramMasterPanel(this);

			loadSysMapData();

			// 画面初期化
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					init();
				}
			});
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * 画面初期化
	 */
	void init() {
		// 開始コードの設定
		ref1 = new REFDialogCtrl(panel.ctrlBeginProgram, panel.getParentFrame());
		ref1.setColumnLabels("C00818", "C00820", "C00821");
		ref1.setTargetServlet("MG0240ProgramMasterServlet");
		ref1.setTitleID(getWord("C02147"));
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndProgram.getField().getText());
				keys.put("kind", "Program");
				return keys;
			}
		});

		panel.ctrlBeginProgram.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}
		});
		// 終了コードの設定
		ref2 = new REFDialogCtrl(panel.ctrlEndProgram, panel.getParentFrame());
		ref2.setColumnLabels("C00818", "C00820", "C00821");
		ref2.setTargetServlet("MG0240ProgramMasterServlet");
		ref2.setTitleID(getWord("C02147"));
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				// mapの初期化
				Map keys = new HashMap();
				// 会社コードの設定
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginProgram.getField().getText());
				keys.put("kind", "Program");
				// データを返す
				return keys;
			}
		});

		panel.ctrlEndProgram.setInputVerifier(new TInputVerifier() {

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
		dialog = new MG0240EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * 新規登録処理
	 */
	void insert() {
		try {
			// 編集画面の初期化
			createEditDisplayDialog("C01698");
			// 編集画面の表示
			Map<String, Object> map = new TreeMap<String, Object>();
			// 編集画面が開けていました
			map.put("flag", "insert");

			dialog.setSysMap(sysMap);
			dialog.setValues(map);
			dialog.show(true);

			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), true);
			lockBtn(true);
			// 選択行保持する
			modifySpreadRow(dialog.getDataList().get("prgCode").toString(), INSERT_KBN);
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
			// 当前行を取得する
			int nomRow = panel.ssJournal.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssJournal.getDataSource();

			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("prgCode", model.getTableDataItem(nomRow, 2));
			map.put("sysCode", model.getTableDataItem(nomRow, 1));
			map.put("prgName", model.getTableDataItem(nomRow, 3));
			map.put("prgName_S", model.getTableDataItem(nomRow, 4));
			map.put("prgName_K", model.getTableDataItem(nomRow, 5));
			map.put("ken", model.getTableDataItem(nomRow, 6));
			map.put("com", model.getTableDataItem(nomRow, 7));
			map.put("ldName", model.getTableDataItem(nomRow, 8));
			map.put("parentPrgCode", model.getTableDataItem(nomRow, 15));
			map.put("menuKbn", model.getTableDataItem(nomRow, 16));
			// 表示順序 追加
			map.put("dispIndex", model.getTableDataItem(nomRow, 13));
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 11)));
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 12)));

			map.put("flag", "update");

			dialog.setSysMap(sysMap);
			dialog.setValues(map);
			dialog.show(false);
			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), false);
			// 選択行保持する
			modifySpreadRow(dialog.getDataList().get("prgCode").toString(), UPD_KBN);
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
			// 当前行を取得する
			createEditDisplayDialog("C01738");
			// データ集を取得する
			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();

			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("prgCode", model.getTableDataItem(nomRow, 2));
			map.put("sysCode", model.getTableDataItem(nomRow, 1));
			map.put("prgName", model.getTableDataItem(nomRow, 3));
			map.put("prgName_S", model.getTableDataItem(nomRow, 4));
			map.put("prgName_K", model.getTableDataItem(nomRow, 5));
			map.put("ken", model.getTableDataItem(nomRow, 6));
			map.put("com", model.getTableDataItem(nomRow, 7));
			map.put("ldName", model.getTableDataItem(nomRow, 8));
			map.put("parentPrgCode", model.getTableDataItem(nomRow, 15));
			map.put("menuKbn", model.getTableDataItem(nomRow, 16));
			// 表示順序 追加
			map.put("dispIndex", model.getTableDataItem(nomRow, 13));
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 11)));
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 12)));

			map.put("flag", "copy");

			dialog.setSysMap(sysMap);
			dialog.setValues(map);
			// 編集画面の表示
			dialog.show(true);
			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), true);
			// スプレッド更新
			modifySpreadRow(dialog.getDataList().get("prgCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param prgCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String prgCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(prgCode, updKbn);
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
	 * @param prgCode
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String prgCode, int updKbn) throws IOException, TRequestException {
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginPrgCode", Util.avoidNull(prgCode));
		addSendValues("endPrgCode", Util.avoidNull(prgCode));

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
				// 結果集を追加する
				colum.add(column, (String) dataIte.next());
			}

			try {
				colum.set(11, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(9))));
				colum.set(12, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(10))));
			} catch (ParseException ex) {
				ClientLogger.error(
					this.getClass().getName() + ":" + "Date Parse error:" + colum.get(9) + colum.get(10), ex);
			}
			colum.set(9, colum.get(15));

			// 表示順序
			colum.set(13, colum.get(17));

			String text;
			String value;

			value = colum.get(16);
			text = ("0".equals(value) ? this.getWord("C00519") : this.getWord("C02170"));
			colum.set(10, text);

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
		// 処理種別の設定
		super.addSendValues("flag", isInsert ? "insert" : "update");
		super.addSendValues("kaiCode", (String) map.get("kaiCode"));
		super.addSendValues("prgCode", (String) map.get("prgCode"));
		super.addSendValues("sysCode", (String) map.get("sysCode"));
		super.addSendValues("prgName", (String) map.get("prgName"));
		super.addSendValues("prgName_S", (String) map.get("prgName_S"));
		super.addSendValues("prgName_K", (String) map.get("prgName_K"));
		super.addSendValues("ken", (String) map.get("ken"));
		super.addSendValues("com", (String) map.get("com"));
		super.addSendValues("ldName", (String) map.get("ldName"));
		super.addSendValues("parentPrgCode", (String) map.get("parentPrgCode"));
		super.addSendValues("menuKbn", (String) map.get("menuKbn"));
		// 表示順序 追加
		super.addSendValues("dispIndex", (String) map.get("dispIndex"));
		super.addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		super.addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		super.addSendValues("prgID", PROGRAM_ID);
		// サーブレットの接続先
		if (!request(getServletName())) {
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
				// データ集を取得する
				TableDataModel model = panel.ssJournal.getDataSource();

				String kaiCode = (String) model.getTableDataItem(nomRow, 0); // 会社コード
				String sysCode = (String) model.getTableDataItem(nomRow, 1); // システムコード
				String prgCode = (String) model.getTableDataItem(nomRow, 2); // プログラムコード
				super.addSendValues("flag", "delete");
				super.addSendValues("kaiCode", kaiCode);
				super.addSendValues("sysCode", sysCode);
				super.addSendValues("prgCode", prgCode);

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
			String strBegin = panel.ctrlBeginProgram.getValue();
			// 終了コードの取得
			String strEnd = panel.ctrlEndProgram.getValue();
			// 開始コードが終了コードより大きい
			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				// 警告メッセージ表示
				showMessage(panel, "W00036", "C00477");
				panel.ctrlBeginProgram.getField().requestFocus();
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
	 * 画面リフレッシュ
	 */
	void outptExcel() {

		try {
			panel.ctrlBeginProgram.getField().requestFocus();
			// 確認ダイアログ表示
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");

			conds.put("kaiCode", getLoginUserCompanyCode());
			String strBegin = panel.ctrlBeginProgram.getValue();
			conds.put("beginPrgCode", strBegin);
			String strEnd = panel.ctrlEndProgram.getValue();
			conds.put("endPrgCode", strEnd);
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

		String beginPrgCode = panel.ctrlBeginProgram.getValue();
		String endPrgCode = panel.ctrlEndProgram.getValue();

		beginPrgCode = beginPrgCode.trim();
		endPrgCode = endPrgCode.trim();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginPrgCode", Util.avoidNull(beginPrgCode));
		addSendValues("endPrgCode", Util.avoidNull(endPrgCode));

		// 送信
		if (!request(getServletName())) {
			errorHandler(panel);
			return false;
		}
		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();

		Iterator recordIte = getResultList().iterator();
		if (!recordIte.hasNext()) {
			panel.ctrlBeginProgram.getField().requestFocus();
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
				// 結果集を追加する
				colum.add(column, (String) dataIte.next());

			}

			try {
				colum.set(11, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(9))));
				colum.set(12, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(10))));
			} catch (ParseException ex) {
				ClientLogger.error(
					this.getClass().getName() + ":" + "Date Parse error:" + colum.get(9) + colum.get(10), ex);
			}
			colum.set(9, colum.get(15));

			// 表示順序
			colum.set(13, colum.get(17));

			String text;
			String value;

			value = colum.get(16);
			text = ("0".equals(value) ? this.getWord("C00519") : this.getWord("C02170"));
			colum.set(10, text);

			cells.add(row, colum);
		}
		// 結果を表示する
		panel.setDataList(cells);
		return dataExists;
	}

	private void loadSysMapData() throws IOException {
		addSendValues("flag", "find");
		addSendValues("kaiCode", getLoginUserCompanyCode());
		addSendValues("beginSysKbn", "");
		addSendValues("endSysKbn", "");

		if (!request("MG0320SystemDivisionMasterServlet")) {
			errorHandler(panel);
			return;
		}
		List result = super.getResultList();
		sysMap = new HashMap();
		Iterator ite = result.iterator();
		while (ite.hasNext()) {
			List inner = (List) ite.next();

			// システム区分略称
			String text = (String) inner.get(3);
			// システム区分コード
			String code = (String) inner.get(1);
			sysMap.put(code, text);
		}
	}
}
