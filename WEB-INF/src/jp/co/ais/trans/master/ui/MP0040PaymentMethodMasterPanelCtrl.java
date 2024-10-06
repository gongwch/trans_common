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
 * 支払方法マスタ画面コントロール
 * 
 * @author ISFnet China
 */
public class MP0040PaymentMethodMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	private static final String PROGRAM_ID = "MP0040";

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MP0040PaymentMethodMasterServlet";

	/** 新規と複写区分 */
	private static int INSERT_KBN = 0;

	/** 編集区分 */
	private static int UPD_KBN = 1;

	/** パネル */
	protected MP0040PaymentMethodMasterPanel panel;

	protected Map hohNaiCodeMap;

	protected REFDialogCtrl ref1;

	protected REFDialogCtrl ref2;

	/**
	 * コンストラクタ.
	 */

	public MP0040PaymentMethodMasterPanelCtrl() {
		try {
			// 一覧画面の初期化
			panel = new MP0040PaymentMethodMasterPanel(this);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				init();
			}
		});
	}

	/**
	 * パネル初期化
	 */
	private void init() {
		// 開始コードの設定
		ref1 = new REFDialogCtrl(panel.ctrlBeginPaymentMethod, panel.getParentFrame());
		ref1.setColumnLabels("C00864", "C00865", "C00866");
		ref1.setTargetServlet("MP0040PaymentMethodMasterServlet");
		ref1.setTitleID("C02350");
		ref1.setShowsMsgOnError(false);

		panel.ctrlBeginPaymentMethod.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}

		});

		ref1.setREFListener(new REFAdapter() {

			@Override
			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndPaymentMethod.getField().getText());
				keys.put("kind", "PaymentMethod");

				return keys;
			}

		});

		// 終了コードの設定
		ref2 = new REFDialogCtrl(panel.ctrlEndPaymentMethod, panel.getParentFrame());
		ref2.setColumnLabels("C00864", "C00865", "C00866");
		ref2.setTargetServlet("MP0040PaymentMethodMasterServlet");
		ref2.setTitleID("C02350");
		ref2.setShowsMsgOnError(false);

		panel.ctrlEndPaymentMethod.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				return true;
			}

		});

		ref2.setREFListener(new REFAdapter() {

			@Override
			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginPaymentMethod.getField().getText());
				keys.put("kind", "PaymentMethod");
				return keys;
			}

		});

		// 言語の指定された名称を表示する
		hohNaiCodeMap = new LinkedHashMap();
		hohNaiCodeMap.put("01", getWord("C02135"));
		hohNaiCodeMap.put("03", getWord("C02136"));
		hohNaiCodeMap.put("04", getWord("C02137"));
		hohNaiCodeMap.put("10", getWord("C02138"));
		hohNaiCodeMap.put("11", getWord("C00154"));
		hohNaiCodeMap.put("12", getWord("C02139"));
		hohNaiCodeMap.put("13", getWord("C02140"));
		hohNaiCodeMap.put("14", getWord("C02141"));
		hohNaiCodeMap.put("15", getWord("C00230"));
		hohNaiCodeMap.put("16", getWord("C02142"));
		hohNaiCodeMap.put("17", getWord("C00331"));
		hohNaiCodeMap.put("18", getWord("C02143"));
		hohNaiCodeMap.put("19", getWord("C02144"));
		hohNaiCodeMap.put("99", getWord("C00338"));

		panel.chkEmployeePayment.setSelected(true);
		panel.chkExternalPayment.setSelected(true);

	}

	/**
	 * パネル取得
	 * 
	 * @return 管理マスタパネル
	 */
	@Override
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * Dialog生成
	 * 
	 * @param titleId
	 * @return 支払方法マスタのダイアログ
	 */
	protected MP0040EditDisplayDialogCtrl createEditDisplayDialogCtrl(String titleId) {
		return new MP0040EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * 新規登録処理
	 */
	void insert() {
		try {
			// 編集画面の初期化
			MP0040EditDisplayDialogCtrl dialog = createEditDisplayDialogCtrl("C01698");
			dialog.setHohNaiCodeMap(hohNaiCodeMap);
			// 編集画面の表示
			dialog.show();
			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), true);
			unlockBtn();
			// 選択行保持する
			modifySpreadRow(dialog.getDataList().get("hohHohCode").toString(), INSERT_KBN);
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
			MP0040EditDisplayDialogCtrl dialog = createEditDisplayDialogCtrl("C00977");
			dialog.setHohNaiCodeMap(hohNaiCodeMap);
			// 当前行を取得する
			int nomRow = panel.ssJournal.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssJournal.getDataSource();
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 支払方法コードの設定
			map.put("hohHohCode", model.getTableDataItem(nomRow, 1));
			// 支払方法名の設定
			map.put("hohHohName", model.getTableDataItem(nomRow, 2));
			// 支払方法名の設定
			map.put("hohHohName_K", model.getTableDataItem(nomRow, 3));
			// 科目コードの設定
			map.put("hohKmkCode", model.getTableDataItem(nomRow, 4));
			// 補助科目コードの設定
			map.put("hohHkmCode", model.getTableDataItem(nomRow, 5));
			// 補助科目コードの設定
			map.put("hohUkmCode", model.getTableDataItem(nomRow, 6));
			// 計上部門コードの設定
			map.put("hohDepCode", model.getTableDataItem(nomRow, 7));
			// 支払対象区分の設定
			map.put("hohSihKbn", model.getTableDataItem(nomRow, 12));
			// 支払内部コードの設定
			map.put("hohNaiCode", model.getTableDataItem(nomRow, 13));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 10)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 11)));
			// 処理種別の設定
			map.put("flag", "update");
			// データを画面に設定するﾞ
			dialog.setValues(map);
			// 編集画面を表示するﾞ
			dialog.show();

			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), false);
			// スプレッド更新
			modifySpreadRow(dialog.getDataList().get("hohHohCode").toString(), UPD_KBN);
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
			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();

			// データチェック
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
			MP0040EditDisplayDialogCtrl dialog = createEditDisplayDialogCtrl("C01738");
			dialog.setHohNaiCodeMap(hohNaiCodeMap);
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
			// 支払方法コードの設定
			map.put("hohHohCode", model.getTableDataItem(nomRow, 1));
			// 支払方法名の設定
			map.put("hohHohName", model.getTableDataItem(nomRow, 2));
			// 支払方法検索名称の設定
			map.put("hohHohName_K", model.getTableDataItem(nomRow, 3));
			// 科目コードの設定
			map.put("hohKmkCode", model.getTableDataItem(nomRow, 4));
			// 補助科目コードの設定
			map.put("hohHkmCode", model.getTableDataItem(nomRow, 5));
			// 内訳科目コードの設定
			map.put("hohUkmCode", model.getTableDataItem(nomRow, 6));
			// 計上部門コードの設定
			map.put("hohDepCode", model.getTableDataItem(nomRow, 7));
			// 支払対象区分の設定
			map.put("hohSihKbn", model.getTableDataItem(nomRow, 12));
			// 支払内部コードの設定
			map.put("hohNaiCode", model.getTableDataItem(nomRow, 13));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 10)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 11)));
			// 処理種別の設定
			map.put("flag", "insert");
			// 編集画面の表示
			dialog.setValues(map);
			dialog.show();
			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), true);
			// スプレッド更新
			modifySpreadRow(dialog.getDataList().get("hohHohCode").toString(), INSERT_KBN);
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

	private void modify(Map map, boolean isInsert) throws IOException {
		// 処理種別の設定
		super.addSendValues("flag", isInsert ? "insert" : "update");
		// 会社コードの設定
		super.addSendValues("kaiCode", getLoginUserCompanyCode());
		// 支払方法コードの設定
		super.addSendValues("hohHohCode", (String) map.get("hohHohCode"));
		// 支払方法名の設定
		super.addSendValues("hohHohName", (String) map.get("hohHohName"));
		// 支払方法検索名称の設定
		super.addSendValues("hohHohName_K", (String) map.get("hohHohName_K"));
		// 科目コードの設定
		super.addSendValues("hohKmkCode", (String) map.get("hohKmkCode"));
		// 補助科目コードの設定
		super.addSendValues("hohHkmCode", (String) map.get("hohHkmCode"));
		// 内訳科目コードの設定
		super.addSendValues("hohUkmCode", (String) map.get("hohUkmCode"));
		// 計上部門コードの設定
		super.addSendValues("hohDepCode", (String) map.get("hohDepCode"));
		// 支払対象区分の設定
		super.addSendValues("hohSihKbn", (String) map.get("hohSihKbn"));
		// 支払内部コードの設定
		super.addSendValues("hohNaiCode", (String) map.get("hohNaiCode"));
		// 開始年月日の設定
		super.addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// 終了年月日の設定
		super.addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// プログラムＩＤの設定
		super.addSendValues("prgID", PROGRAM_ID);
		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return;
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param hohHohCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	private void modifySpreadRow(String hohHohCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(hohHohCode, updKbn);
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
	 * @param hohHohCode
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String hohHohCode, int updKbn) throws IOException, TRequestException {
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginHohHohCode", Util.avoidNull(hohHohCode));
		// 終了コードの設定
		addSendValues("endHohHohCode", Util.avoidNull(hohHohCode));

		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
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
				colum.set(10, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(10))));
				colum.set(11, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(11))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(10), ex);
			}
			// 支払内部コード
			String value;
			String text;

			value = colum.get(8);
			text = ("0".equals(value) ? getWord("C01119") : getWord("C01124"));
			colum.add(12, value);
			colum.set(8, text);

			value = colum.get(9);
			text = (String) hohNaiCodeMap.get(value);
			colum.add(13, value);
			colum.set(9, text);
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
				// 会社コードの取得
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// 支払方法コードの取得
				String hohHohCode = (String) model.getTableDataItem(nomRow, 1);
				// 処理種別の設定
				super.addSendValues("flag", "delete");
				// 会社コードの設定
				super.addSendValues("kaiCode", kaiCode);
				// 支払方法コードの設定
				super.addSendValues("hohHohCode", hohHohCode);

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
		String beginPaymentMethod = panel.ctrlBeginPaymentMethod.getValue().toString();
		String endPaymentmethod = panel.ctrlEndPaymentMethod.getValue().toString();

		try {
			if (!panel.chkEmployeePayment.isSelected() && !panel.chkExternalPayment.isSelected()) {
				// 警告メッセージ表示
				panel.chkEmployeePayment.requestFocus();
				showMessage(panel, "I00041", "C01096");
				return false;
			}

			if (Util.isSmallerThen(beginPaymentMethod, endPaymentmethod) == false) {
				// 警告メッセージ表示
				panel.ctrlBeginPaymentMethod.getField().requestFocus();
				showMessage(panel, "W00036", "C00233");
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
	 * エクセルリスト出力
	 */
	void outptExcel() {

		try {
			panel.ctrlBeginPaymentMethod.getField().requestFocus();
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
			conds.put("beginHohHohCode", panel.ctrlBeginPaymentMethod.getValue());
			// 終了コードの取得
			conds.put("endHohHohCode", panel.ctrlEndPaymentMethod.getValue());
			// 支払対象区分:社員支払
			conds.put("includeEmployeePayment", panel.chkEmployeePayment.isSelected() ? "1" : "0");
			// 支払対象区分:社外支払
			conds.put("includeExternalPayment", panel.chkExternalPayment.isSelected() ? "1" : "0");
			conds.put("langCode", getLoginLanguage());
			this.download(panel, TARGET_SERVLET, conds);
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
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();
		// 開始コードの取得
		String beginHohHohCode = panel.ctrlBeginPaymentMethod.getValue();
		// 終了コードの取得
		String endHohHohCode = panel.ctrlEndPaymentMethod.getValue();

		beginHohHohCode = beginHohHohCode.trim();
		endHohHohCode = endHohHohCode.trim();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginHohHohCode", Util.avoidNull(beginHohHohCode));
		// 終了コードの設定
		addSendValues("endHohHohCode", Util.avoidNull(endHohHohCode));
		// 支払対象区分:社員支払
		addSendValues("includeEmployeePayment", panel.chkEmployeePayment.isSelected() ? "1" : "0");
		// 支払対象区分:社外支払
		addSendValues("includeExternalPayment", panel.chkExternalPayment.isSelected() ? "1" : "0");

		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return false;
		}
		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();
		Iterator recordIte = getResultList().iterator();
		// 値があるかどうか
		if (!recordIte.hasNext()) {
			panel.ctrlBeginPaymentMethod.getField().requestFocus();
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
				colum.set(10, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(10))));
				colum.set(11, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(11))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(10), ex);
			}
			// 支払内部コード
			String value;
			String text;

			value = colum.get(8);
			text = ("0".equals(value) ? getWord("C01119") : getWord("C01124"));
			colum.add(12, value);
			colum.set(8, text);

			value = colum.get(9);
			text = (String) hohNaiCodeMap.get(value);
			colum.add(13, value);
			colum.set(9, text);

			cells.add(row, colum);
		}

		panel.setDataList(cells);
		panel.setBtnLock(cells.size() > 0);
		return dataExists;
	}
}
