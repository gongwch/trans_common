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
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 銀行口座マスタ画面コントロール
 * 
 * @author yangjing
 */
public class MP0030BankAccountMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	protected static final String PROGRAM_ID = "MP0030";

	/** 新規と複写区分 */
	protected static int INSERT_KBN = 0;

	/** 編集区分 */
	protected static int UPD_KBN = 1;

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "MP0030BankAccountMasterServlet";

	/** パネル */
	protected MP0030BankAccountMasterPanel panel;

	/** 単語Map */
	protected Map yknKbnMap;

	MP0030EditDisplayDialogCtrl dialog;

	/**
	 * コンストラクタ.
	 */
	public MP0030BankAccountMasterPanelCtrl() {

		panel = new MP0030BankAccountMasterPanel(this);

		try {
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					init();
				}
			});
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel, e, "E00010");
		}

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
	 * パネル初期化
	 */
	protected void init() {

		// 銀行口座開始コード
		final REFDialogCtrl ref1 = new REFDialogCtrl(panel.ctrlBeginBankAccount, panel.getParentFrame());
		ref1.setColumnLabels("C00857", "C00124", "C00794");
		ref1.setTargetServlet(TARGET_SERVLET);
		ref1.setTitleID("C02322");
		ref1.setShowsMsgOnError(false);

		panel.ctrlBeginBankAccount.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}

		});

		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndBankAccount.getField().getText());

				return keys;
			}

			// 銀行コードにて銀行マスタに存在
			public void goodCodeInputted() {

				addSendValues("flag", "getrefitems");
				addSendValues("kaiCode", getLoginUserCompanyCode());
				addSendValues("cbkCbkCode", panel.ctrlBeginBankAccount.getField().getText());

				try {
					// 送信
					if (!request(TARGET_SERVLET)) {
						errorHandler(panel);
						return;
					}

				} catch (IOException ex) {
					// 正常に処理されませんでした
					errorHandler(panel, ex, "E00009");
					return;
				}

				List result = getResultList().get(0);

				if (result != null && result.size() >= 6) {
					String text1 = (String) result.get(1) + " " + (String) result.get(3);

					String rsText = (String) result.get(4);

					if ("1".equals(rsText)) {
						rsText = getWord("C00463");
					} else if ("2".equals(rsText)) {
						rsText = getWord("C00397");
					} else if ("3".equals(rsText)) {
						rsText = getWord("C00045");
					} else {
						rsText = getWord("C00368");
					}

					String text2 = rsText + " " + (String) result.get(5);

					panel.ctrlBeginBankAccount.getNotice().setText(text1);
					panel.txtBeginDepositTypeAccountNumber.setText(text2);
				}
			}

			// 銀行コードにて銀行マスタに存在しない
			public void badCodeInputted() {
				panel.ctrlBeginBankAccount.getNotice().clear();
				panel.txtBeginDepositTypeAccountNumber.clear();
			}

			// 銀行コードをクリア
			public void textCleared() {
				badCodeInputted();
			}
		});

		// 銀行口座開始コード
		final REFDialogCtrl ref2 = new REFDialogCtrl(panel.ctrlEndBankAccount, panel.getParentFrame());
		ref2.setColumnLabels("C00857", "C00124", "C00794");
		ref2.setTargetServlet("MP0030BankAccountMasterServlet");
		ref2.setTitleID("C02322");
		ref2.setShowsMsgOnError(false);

		panel.ctrlEndBankAccount.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				return true;
			}
		});

		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginBankAccount.getField().getText());

				return keys;
			}

			// 銀行コードにて銀行マスタに存在
			public void goodCodeInputted() {

				addSendValues("flag", "getrefitems");
				addSendValues("kaiCode", getLoginUserCompanyCode());
				addSendValues("cbkCbkCode", panel.ctrlEndBankAccount.getField().getText());

				try {

					// 送信
					if (!request(TARGET_SERVLET)) {
						errorHandler(panel);
						return;
					}

				} catch (IOException ex) {
					// 正常に処理されませんでした
					errorHandler(panel, ex, "E00009");
					return;
				}

				List result = getResultList().get(0);

				if (result != null && result.size() >= 6) {
					String text1 = (String) result.get(1) + " " + (String) result.get(3);

					String rsText = (String) result.get(4);

					if ("1".equals(rsText)) {
						rsText = getWord("C00463");
					} else if ("2".equals(rsText)) {
						rsText = getWord("C00397");
					} else if ("3".equals(rsText)) {
						rsText = getWord("C00045");
					} else {
						rsText = getWord("C00368");
					}

					String text2 = rsText + " " + (String) result.get(5);

					panel.ctrlEndBankAccount.getNotice().setText(text1);
					panel.txtEndDepositTypeAccountNumber.setText(text2);
				}
			}

			// 銀行コードにて銀行マスタに存在しない
			public void badCodeInputted() {
				panel.ctrlEndBankAccount.getNotice().clear();
				panel.txtEndDepositTypeAccountNumber.clear();
			}

			// 銀行コードをクリア
			public void textCleared() {
				badCodeInputted();
			}
		});

		// 言語の指定された名称を表示する
		yknKbnMap = new LinkedHashMap();
		yknKbnMap.put("1", getWord("C00463"));
		yknKbnMap.put("2", getWord("C00397"));
		yknKbnMap.put("3", getWord("C00045"));
		yknKbnMap.put("4", getWord("C00368"));

		panel.txtBeginDepositTypeAccountNumber.setEnabled(true);
		panel.txtEndDepositTypeAccountNumber.setEnabled(true);
	}

	/**
	 * 新規登録処理
	 */
	void insert() {
		try {
			// 編集画面の初期化
			createEditDisplayDialog("C01698");

			dialog.setYknKbnMap(yknKbnMap);
			// 編集画面の表示
			dialog.show();

			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}

			// データが編集する
			modify(dialog.getDataList(), true);

			// 選択行保持する
			modifySpreadRow(dialog.getDataList().get("cbkCbkCode").toString(), INSERT_KBN);

		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * ダイアログ生成
	 * 
	 * @param titleId
	 */
	protected void createEditDisplayDialog(String titleId) {
		dialog = new MP0030EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * 編集処理
	 */

	void update() {
		try {
			createEditDisplayDialog("C00977");

			dialog.setYknKbnMap(yknKbnMap);

			// 当前行を取得する
			int nomRow = panel.ssJournal.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssJournal.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 銀行口座コードの設定
			map.put("cbkCbkCode", model.getTableDataItem(nomRow, 1));
			// 銀行口座名称の設定
			map.put("cbkName", model.getTableDataItem(nomRow, 2));
			// 通貨コードの設定
			map.put("curCode", model.getTableDataItem(nomRow, 3));
			// 銀行コードの設定
			map.put("cbkBnkCode", model.getTableDataItem(nomRow, 5));
			// 支店コードの設定
			map.put("cbkStnCode", model.getTableDataItem(nomRow, 7));
			// 振込依頼人コードの設定
			map.put("cbkIraiEmpCode", model.getTableDataItem(nomRow, 9));
			// 振込依頼人名の設定
			map.put("cbkIraiName", model.getTableDataItem(nomRow, 10));
			// 振込依頼人名（漢字）の設定
			map.put("cbkIraiName_J", model.getTableDataItem(nomRow, 11));
			// 振込依頼人名（英字）の設定
			map.put("cbkIraiName_E", model.getTableDataItem(nomRow, 12));
			// 預金種目の設定
			map.put("cbkYknKbn", model.getTableDataItem(nomRow, 23));
			// 口座番号の設定
			map.put("cbkYknNo", model.getTableDataItem(nomRow, 14));
			// 社員ＦＢ区分の設定
			map.put("cbkEmpFbKbn", model.getTableDataItem(nomRow, 24));
			// 社外ＦＢ区分の設定
			map.put("cbkOutFbKbn", model.getTableDataItem(nomRow, 25));
			// 計上部門コードの設定
			map.put("cbkDepCode", model.getTableDataItem(nomRow, 17));
			// 科目コードの設定
			map.put("cbkKmkCode", model.getTableDataItem(nomRow, 18));
			// 補助科目コードの設定
			map.put("cbkHkmCode", model.getTableDataItem(nomRow, 19));
			// 内訳科目コードの設定
			map.put("cbkUkmCode", model.getTableDataItem(nomRow, 20));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 21)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 22)));
			// 処理種別の設定ﾞ
			map.put("flag", "update");

			// データを画面に設定するﾞ
			dialog.setValues(map);

			// 編集画面を表示するﾞ
			dialog.show();

			// 編集画面が開けていましたﾞ
			if (!dialog.isSettle()) {
				return;
			}

			// データが編集する
			modify(dialog.getDataList(), false);

			// スプレッド更新
			modifySpreadRow(dialog.getDataList().get("cbkCbkCode").toString(), UPD_KBN);

		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * 複写処理
	 */

	public void copy() {
		try {
			createEditDisplayDialog("C01738");

			dialog.setYknKbnMap(yknKbnMap);

			// 当前行を取得する
			int nomRow = panel.ssJournal.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssJournal.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 銀行口座コードの設定
			map.put("cbkCbkCode", model.getTableDataItem(nomRow, 1));
			// 銀行口座名称の設定
			map.put("cbkName", model.getTableDataItem(nomRow, 2));
			// 通貨コードの設定
			map.put("curCode", model.getTableDataItem(nomRow, 3));
			// 銀行コードの設定
			map.put("cbkBnkCode", model.getTableDataItem(nomRow, 5));
			// 支店コードの設定
			map.put("cbkStnCode", model.getTableDataItem(nomRow, 7));
			// 振込依頼人コードの設定
			map.put("cbkIraiEmpCode", model.getTableDataItem(nomRow, 9));
			// 振込依頼人名の設定
			map.put("cbkIraiName", model.getTableDataItem(nomRow, 10));
			// 振込依頼人名（漢字）の設定
			map.put("cbkIraiName_J", model.getTableDataItem(nomRow, 11));
			// 振込依頼人名（英字）の設定
			map.put("cbkIraiName_E", model.getTableDataItem(nomRow, 12));
			// 預金種目の設定
			map.put("cbkYknKbn", model.getTableDataItem(nomRow, 23));
			// 口座番号の設定
			map.put("cbkYknNo", model.getTableDataItem(nomRow, 14));
			// 社員ＦＢ区分の設定
			map.put("cbkEmpFbKbn", model.getTableDataItem(nomRow, 24));
			// 社外ＦＢ区分の設定
			map.put("cbkOutFbKbn", model.getTableDataItem(nomRow, 25));
			// 計上部門コードの設定
			map.put("cbkDepCode", model.getTableDataItem(nomRow, 17));
			// 科目コードの設定
			map.put("cbkKmkCode", model.getTableDataItem(nomRow, 18));
			// 補助科目コードの設定
			map.put("cbkHkmCode", model.getTableDataItem(nomRow, 19));
			// 内訳科目コードの設定
			map.put("cbkUkmCode", model.getTableDataItem(nomRow, 20));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 21)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 22)));
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
			modifySpreadRow(dialog.getDataList().get("cbkCbkCode").toString(), INSERT_KBN);

		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel, e, "E00009");
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
		// 銀行口座コード
		addSendValues("cbkCbkCode", (String) map.get("cbkCbkCode"));
		// 銀行口座名称
		addSendValues("cbkName", (String) map.get("cbkName"));
		// 通貨コード
		addSendValues("curCode", (String) map.get("curCode"));
		// 銀行コード
		addSendValues("cbkBnkCode", (String) map.get("cbkBnkCode"));
		// 支店コード
		addSendValues("cbkStnCode", (String) map.get("cbkStnCode"));
		// 振込依頼人コード
		addSendValues("cbkIraiEmpCode", (String) map.get("cbkIraiEmpCode"));
		// 振込依頼人名
		addSendValues("cbkIraiName", (String) map.get("cbkIraiName"));
		// 振込依頼人名（漢字）
		addSendValues("cbkIraiName_J", (String) map.get("cbkIraiName_J"));
		// 振込依頼人名（英字）
		addSendValues("cbkIraiName_E", (String) map.get("cbkIraiName_E"));
		// 口座番号
		addSendValues("cbkYknNo", (String) map.get("cbkYknNo"));
		// 計上部門コード
		addSendValues("cbkDepCode", (String) map.get("cbkDepCode"));
		// 科目コード
		addSendValues("cbkKmkCode", (String) map.get("cbkKmkCode"));
		// 補助科目コード
		addSendValues("cbkHkmCode", (String) map.get("cbkHkmCode"));
		// 内訳科目コード
		addSendValues("cbkUkmCode", (String) map.get("cbkUkmCode"));
		// 社員ＦＢ区分
		addSendValues("cbkEmpFbKbn", (String) map.get("cbkEmpFbKbn"));
		// 社外ＦＢ区分
		addSendValues("cbkOutFbKbn", (String) map.get("cbkOutFbKbn"));
		// 預金種目
		addSendValues("cbkYknKbn", (String) map.get("cbkYknKbn"));
		// 開始年月日
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// 終了年月日
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// プログラムＩＤの設定の設定
		addSendValues("prgID", PROGRAM_ID);

		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param cbkCbkCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 * @throws ParseException
	 */
	protected void modifySpreadRow(String cbkCbkCode, int updKbn) throws TRequestException, IOException, ParseException {
		Vector<Vector> cells = setModifyCell(cbkCbkCode, updKbn);
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
	 * @param cbkCbkCode
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 * @throws ParseException
	 */
	protected Vector<Vector> setModifyCell(String cbkCbkCode, int updKbn) throws IOException, TRequestException,
		ParseException {
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginCbkCbkCode", Util.avoidNull(cbkCbkCode));
		// 開始コードの設定
		addSendValues("endCbkCbkCode", Util.avoidNull(cbkCbkCode));
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

			if (Util.isNullOrEmpty(colum.get(21))) {
				colum.set(21, "");
			} else {
				colum.set(21, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(21))));
			}

			if (Util.isNullOrEmpty(colum.get(22))) {
				colum.set(22, "");
			} else {
				colum.set(22, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(22))));
			}

			String value, text;

			// 預金種目
			value = colum.get(13);
			text = (String) yknKbnMap.get(value);
			colum.set(23, value);
			colum.set(13, text);

			// 社員ＦＢ区分 0:社員ＦＢ以外 1:社員ＦＢ用
			value = colum.get(15);
			text = ("0".equals(value) ? this.getWord("C02148") : this.getWord("C02149"));
			colum.set(24, value);
			colum.set(15, text);

			// 社外ＦＢ区分
			value = colum.get(16);
			text = ("0".equals(value) ? this.getWord("C02150") : this.getWord("C02151"));
			colum.set(25, value);
			colum.set(16, text);
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
		try {
			if (!this.showConfirmMessage(panel, "Q00007", "")) {
				return;
			}

			// 選択されている行の1つ目と2つ目のカラムを取得
			int nomRow = panel.ssJournal.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssJournal.getDataSource();
			// 会社コードの取得
			String kaiCode = (String) model.getTableDataItem(nomRow, 0);
			// 銀行口座コードの取得
			String cbkCbkCode = (String) model.getTableDataItem(nomRow, 1);
			// 処理種別の設定
			addSendValues("flag", "delete");
			// 会社コードの設定
			addSendValues("kaiCode", kaiCode);
			// 銀行口座コードの設定
			addSendValues("cbkCbkCode", cbkCbkCode);

			// サーブレットの接続先
			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
			}

			deleteSpreadRow();

		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel, e, "E00009");
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
		panel.btnDelete.setEnabled(boo);
		panel.btnEdit.setEnabled(boo);
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
	protected boolean find() {
		try {
			String beginBankAccount = panel.ctrlBeginBankAccount.getValue();
			String endBankAccount = panel.ctrlEndBankAccount.getValue();

			if (Util.isSmallerThen(beginBankAccount, endBankAccount) == false) {
				// 警告メッセージ表示
				panel.ctrlBeginBankAccount.getField().requestFocus();
				showMessage(panel, "W00036", "C00857 ");
				return false;
			}

			// 表示データの取得
			return reflesh();

		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel, e, "E00009");
			return false;
		}
	}

	/**
	 * 画面リフレッシュ
	 * 
	 * @return boolean
	 * @throws IOException
	 * @throws ParseException
	 */
	protected boolean reflesh() throws IOException, ParseException {

		boolean dataExists = true;

		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();
		// 開始コードの取得
		String beginCbkCbkCode = panel.ctrlBeginBankAccount.getValue();
		// 終了コードの取得
		String endCbkCbkCode = panel.ctrlEndBankAccount.getValue();

		beginCbkCbkCode = beginCbkCbkCode.trim();
		endCbkCbkCode = endCbkCbkCode.trim();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginCbkCbkCode", Util.avoidNull(beginCbkCbkCode));
		// 開始コードの設定
		addSendValues("endCbkCbkCode", Util.avoidNull(endCbkCbkCode));

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
			panel.ctrlBeginBankAccount.getField().requestFocus();
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

			if (Util.isNullOrEmpty(colum.get(21))) {
				colum.set(21, "");
			} else {
				colum.set(21, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(21))));
			}

			if (Util.isNullOrEmpty(colum.get(22))) {
				colum.set(22, "");
			} else {
				colum.set(22, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(22))));
			}

			String value, text;

			// 預金種目
			value = colum.get(13);
			text = (String) yknKbnMap.get(value);
			colum.set(23, value);
			colum.set(13, text);

			// 社員ＦＢ区分 0:社員ＦＢ以外 1:社員ＦＢ用
			value = colum.get(15);
			text = ("0".equals(value) ? this.getWord("C02148") : this.getWord("C02149"));
			colum.set(24, value);
			colum.set(15, text);

			// 社外ＦＢ区分
			value = colum.get(16);
			text = ("0".equals(value) ? this.getWord("C02150") : this.getWord("C02151"));
			colum.set(25, value);
			colum.set(16, text);

			cells.add(row, colum);
		}

		panel.setDataList(cells);
		panel.setBtnLock(cells.size() > 0);

		return dataExists;
	}

	/**
	 * エクセルリスト出力
	 */
	void outptExcel() {
		try {
			panel.ctrlBeginBankAccount.getField().requestFocus();
			// 確認ダイアログ表示
			if (!showConfirmMessage(panel, "Q00011", new Object[0])) {
				return;
			}

			Map conds = new HashMap();

			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginCbkCbkCode", panel.ctrlBeginBankAccount.getValue()); // 開始
			conds.put("endCbkCbkCode", panel.ctrlEndBankAccount.getValue()); // 終了
			conds.put("langCode", getLoginLanguage());

			this.download(panel, TARGET_SERVLET, conds);

		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel, e, "E00009");
		}
	}
}
