package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
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
 * 銀行マスタ画面コントロール
 * 
 * @author ookawara
 */
public class MG0140BankMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	private static final String PROGRAM_ID = "MG0140";

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0140BankMasterServlet";

	/** 新規と複写区分 */
	private static int INSERT_KBN = 0;

	/** 編集区分 */
	private static int UPD_KBN = 1;

	/** パネル */
	protected MG0140BankMasterPanel panel;

	/** 銀行 Enterを押下FLAG */
	boolean inputFlag = false;

	protected REFDialogCtrl refBank;

	protected REFDialogCtrl refBeginBankBranch;

	protected REFDialogCtrl refEndBankBranch;

	/** 銀行マスタダイアログ */
	MG0140EditDisplayDialogCtrl dialog;

	/**
	 * コンストラクタ.
	 */
	public MG0140BankMasterPanelCtrl() {
		try {
			// 一覧画面の初期化
			panel = new MG0140BankMasterPanel(this);

		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		// 開始コードと終了コードの初期化
		panel.ctrlBeginBankBranch.getField().setEditable(false);
		panel.ctrlEndBankBranch.getField().setEditable(false);
		panel.ctrlBeginBankBranch.getButton().setEnabled(false);
		panel.ctrlEndBankBranch.getButton().setEnabled(false);

		// 画面初期化
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				init();
			}
		});

	}

	void init() {
		// 銀行コードの設定

		refBank = new REFDialogCtrl(panel.ctrlBank, panel.getParentFrame());
		refBank.setTargetServlet(TARGET_SERVLET);
		refBank.setTitleID("C02323");
		refBank.setColumnLabels("C00779", "C00781", "C00829");
		refBank.setShowsMsgOnError(false);
		refBank.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				keys.put("kind", "Bank");
				return keys;
			}

			public void goodCodeInputted() {
				panel.ctrlBeginBankBranch.getField().setEditableEnabled(true);
				panel.ctrlEndBankBranch.getField().setEditableEnabled(true);
				panel.ctrlBeginBankBranch.getButton().setEnabled(true);
				panel.ctrlEndBankBranch.getButton().setEnabled(true);

			}

			public void badCodeInputted() {
				panel.ctrlBeginBankBranch.getField().setText(null);
				panel.ctrlBeginBankBranch.getField().setEditableEnabled(false);
				panel.ctrlBeginBankBranch.getNotice().setEditable(true);
				panel.ctrlBeginBankBranch.getNotice().setText(null);
				panel.ctrlBeginBankBranch.getNotice().setEditable(false);
				panel.ctrlBeginBankBranch.getButton().setEnabled(false);

				panel.ctrlEndBankBranch.getField().setText(null);
				panel.ctrlEndBankBranch.getField().setEditableEnabled(false);
				panel.ctrlEndBankBranch.getNotice().setEditable(true);
				panel.ctrlEndBankBranch.getNotice().setText(null);
				panel.ctrlEndBankBranch.getNotice().setEditable(false);
				panel.ctrlEndBankBranch.getButton().setEnabled(false);
			}

		});

		// 銀行 Enterキを押下
		panel.ctrlBank.getField().addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {

				if (evt.getKeyCode() == KeyEvent.VK_ENTER && inputFlag == false) {
					refBank.refreshName();
					if (panel.ctrlBank.isValueChanged()) {
						if (!Util.isNullOrEmpty(panel.ctrlBank.getValue().trim())
							&& !Util.isNullOrEmpty(panel.ctrlBank.getNotice().getText().trim())) {
							panel.ctrlBeginBankBranch.getField().requestFocus();
							panel.ctrlBeginBankBranch.getField().setText(null);
							panel.ctrlBeginBankBranch.getNotice().setText(null);
							panel.ctrlEndBankBranch.getField().setText(null);
							panel.ctrlEndBankBranch.getNotice().setText(null);
						}
					}

					if (!Util.isNullOrEmpty(panel.ctrlBank.getValue().trim())
						&& Util.isNullOrEmpty(panel.ctrlBank.getNotice().getText().trim())) {
						showMessage(panel, "W00081", panel.ctrlBank.getValue());
						panel.ctrlBank.getField().clearOldText();
						panel.ctrlBank.getField().requestFocus();
					}
				}
				inputFlag = false;
			}
		});

		panel.ctrlBank.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refBank.refreshName();
				if (panel.ctrlBank.isValueChanged()) {
					if (!Util.isNullOrEmpty(panel.ctrlBank.getValue().trim())
						&& !Util.isNullOrEmpty(panel.ctrlBank.getNotice().getText().trim())) {
						panel.ctrlBeginBankBranch.getField().setText(null);
						panel.ctrlBeginBankBranch.getNotice().setText(null);

						panel.ctrlEndBankBranch.getField().setText(null);
						panel.ctrlEndBankBranch.getNotice().setText(null);
					}
				}

				if (!Util.isNullOrEmpty(panel.ctrlBank.getValue().trim())
					&& Util.isNullOrEmpty(panel.ctrlBank.getNotice().getText().trim())) {
					showMessage(panel, "W00081", panel.ctrlBank.getValue());
					panel.ctrlBank.getField().clearOldText();
					panel.ctrlBank.getField().requestFocus();
					inputFlag = true;
					return false;

				}
				return true;
			}
		});

		// 開始コードの設定
		refBeginBankBranch = new REFDialogCtrl(panel.ctrlBeginBankBranch, panel.getParentFrame());
		refBeginBankBranch.setTargetServlet(TARGET_SERVLET);
		refBeginBankBranch.setTitleID("C00778");
		refBeginBankBranch.setColumnLabels("C00780", "C00783", "C00785");
		refBeginBankBranch.setShowsMsgOnError(false);
		refBeginBankBranch.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 銀行コードの設定
				keys.put("kind", "BankBranch");
				keys.put("bnkCode", panel.ctrlBank.getField().getText());
				keys.put("endCode", panel.ctrlEndBankBranch.getField().getText());
				// データを返す
				return keys;
			}
		});

		panel.ctrlBeginBankBranch.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refBeginBankBranch.refreshName();
				return true;
			}
		});

		// 終了コードの設定
		refEndBankBranch = new REFDialogCtrl(panel.ctrlEndBankBranch, panel.getParentFrame());
		refEndBankBranch.setTargetServlet(TARGET_SERVLET);
		refEndBankBranch.setTitleID("C00778");
		refEndBankBranch.setColumnLabels("C00780", "C00783", "C00785");
		refEndBankBranch.setShowsMsgOnError(false);
		refEndBankBranch.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 銀行コードの設定
				keys.put("kind", "BankBranch");
				keys.put("bnkCode", panel.ctrlBank.getField().getText());
				keys.put("beginCode", panel.ctrlBeginBankBranch.getField().getText());
				// データを返す
				return keys;
			}
		});

		panel.ctrlEndBankBranch.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refEndBankBranch.refreshName();
				return true;
			}
		});

	}

	/**
	 * 新規登録処理
	 */
	void insert() {
		try {
			// 編集画面の初期化
			createEditDisplayDialog("C01698");
			// 編集画面の表示
			dialog.show();
			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), true);
			// 選択行保持する
			String bankCode = dialog.getDataList().get("bnkCode").toString();
			String bnkStnCode = dialog.getDataList().get("bnkStnCode").toString();
			modifySpreadRow(bankCode, bnkStnCode, INSERT_KBN);
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
			int nomRow = panel.ssBank.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssBank.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 銀行コードの設定
			map.put("bnkCode", model.getTableDataItem(nomRow, 0));
			// 銀行支店コードの設定
			map.put("bnkStnCode", model.getTableDataItem(nomRow, 1));
			// 銀行名の設定
			map.put("bnkName_S", model.getTableDataItem(nomRow, 2));
			// 銀行カナ名称の設定
			map.put("bnkKana", model.getTableDataItem(nomRow, 3));
			// 銀行検索名称の設定
			map.put("bnkName_K", model.getTableDataItem(nomRow, 4));
			// 銀行支店名の設定
			map.put("bnkStnName_S", model.getTableDataItem(nomRow, 5));
			// 銀行支店カナ名称の設定
			map.put("bnkStnKana", model.getTableDataItem(nomRow, 6));
			// 銀行支店検索名称の設定
			map.put("bnkStnName_K", model.getTableDataItem(nomRow, 7));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 8)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			// 処理種別の設定
			map.put("flag", "update");
			// データを画面に設定する
			dialog.setValues(map);
			// 編集画面を表示する
			dialog.show();
			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), false);
			// スプレッド更新
			String bankCode = dialog.getDataList().get("bnkCode").toString();
			String bnkStnCode = dialog.getDataList().get("bnkStnCode").toString();
			modifySpreadRow(bankCode, bnkStnCode, UPD_KBN);
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
			// 当前行を取得する
			int nomRow = panel.ssBank.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssBank.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 銀行コードの設定
			map.put("bnkCode", model.getTableDataItem(nomRow, 0));
			// 銀行支店コードの設定
			map.put("bnkStnCode", model.getTableDataItem(nomRow, 1));
			// 銀行名の設定
			map.put("bnkName_S", model.getTableDataItem(nomRow, 2));
			// 銀行カナ名称の設定
			map.put("bnkKana", model.getTableDataItem(nomRow, 3));
			// 銀行検索名称の設定
			map.put("bnkName_K", model.getTableDataItem(nomRow, 4));
			// 銀行支店名の設定
			map.put("bnkStnName_S", model.getTableDataItem(nomRow, 5));
			// 銀行支店カナ名称の設定
			map.put("bnkStnKana", model.getTableDataItem(nomRow, 6));
			// 銀行支店検索名称の設定
			map.put("bnkStnName_K", model.getTableDataItem(nomRow, 7));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 8)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			// 処理種別の設定
			map.put("flag", "insert");
			// データを画面に設定する
			dialog.setValues(map);
			// 編集画面の表示
			dialog.show();
			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), true);

			String bankCode = dialog.getDataList().get("bnkCode").toString();
			String bnkStnCode = dialog.getDataList().get("bnkStnCode").toString();
			modifySpreadRow(bankCode, bnkStnCode, INSERT_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 検索処理
	 * 
	 * @return 検索結果
	 */
	protected boolean find() {
		String beginBreakDownItem = panel.ctrlBeginBankBranch.getValue().toString();
		String endBreakDownItem = panel.ctrlEndBankBranch.getValue().toString();

		try {
			if (Util.isSmallerThen(beginBreakDownItem, endBreakDownItem) == false) {
				// 警告メッセージ表示
				panel.ctrlBeginBankBranch.getField().requestFocus();
				showMessage(panel, "W00036", "C00778");
				return false;
			}

			return reflesh();

		} catch (IOException e) {
			// ボタンロックの設定
			lockBtn(false);
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
		return false;
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param bnkCode
	 * @param bankBranchCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	private void modifySpreadRow(String bnkCode, String bankBranchCode, int updKbn) throws TRequestException,
		IOException {
		Vector<Vector> cells = setModifyCell(bnkCode, bankBranchCode, updKbn);
		TTable ssPanelSpread = panel.ssBank;
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
		lockBtn(panel.ssBank.getDataSource().getNumRows() != 0);
	}

	/**
	 * 新規、複写、編集の場合、スプレッド取得
	 * 
	 * @param bnkCode
	 * @param bankBranchCode
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String bnkCode, String bankBranchCode, int updKbn) throws IOException,
		TRequestException {
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 銀行コードの設定
		addSendValues("bnkCode", bnkCode);
		// 開始コードの設定
		addSendValues("beginBnkStnCode", Util.avoidNull(bankBranchCode));
		// 終了コードの設定
		addSendValues("endBnkStnCode", Util.avoidNull(bankBranchCode));

		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssBank.getDataSource();
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
				colum.set(8, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(8))));
				colum.set(9, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(9))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(8), ex);
			}
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssBank.getCurrentRow(), colum);
		}
		return cells;
	}

	/**
	 * 画面リフレッシュ
	 * 
	 * @return データが存在するかどうか
	 * @throws IOException
	 */

	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();
		// 銀行コードの取得
		String bnkCode = panel.ctrlBank.getValue();
		// 開始コードの取得
		String beginBnkStnCode = panel.ctrlBeginBankBranch.getValue();
		// 終了コードの取得
		String endBnkStnCode = panel.ctrlEndBankBranch.getValue();
		beginBnkStnCode = beginBnkStnCode.trim();
		endBnkStnCode = endBnkStnCode.trim();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 銀行コードの設定
		addSendValues("bnkCode", bnkCode);
		// 開始コードの設定
		addSendValues("beginBnkStnCode", Util.avoidNull(beginBnkStnCode));
		// 終了コードの設定
		addSendValues("endBnkStnCode", Util.avoidNull(endBnkStnCode));

		// サーバ側のエラー場合
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
			panel.ctrlBeginBankBranch.getField().requestFocus();
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
				colum.set(8, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(8))));
				colum.set(9, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(9))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(8), ex);
			}
			cells.add(row, colum);
		}
		// 結果を表示する
		panel.setDataList(cells);
		return dataExists;
	}

	/**
	 * パネル取得
	 * 
	 * @return 通貨マスタパネル
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * ボタンロックの制御
	 * 
	 * @param boo
	 */
	void lockBtn(boolean boo) {
		panel.btnCopy.setEnabled(boo);
		panel.btnEdit.setEnabled(boo);
		panel.btnDelete.setEnabled(boo);
	}

	/**
	 * 削除処理
	 */
	void delete() {
		if (this.showConfirmMessage(panel, "Q00007", "")) {
			try {
				// 選択されている行の1つ目と2つ目のカラムを取得
				int nomRow = panel.ssBank.getCurrentRow();
				// データ集を取得する
				TableDataModel model = panel.ssBank.getDataSource();
				// 銀行コードの取得
				String bnkCode = (String) model.getTableDataItem(nomRow, 0);
				// 銀行支店コードの設定
				String bnkStnCode = (String) model.getTableDataItem(nomRow, 1);
				// 処理種別の設定
				addSendValues("flag", "delete");
				// 銀行コードの取得
				addSendValues("bnkCode", bnkCode);
				// 銀行支店コードの設定
				addSendValues("bnkStnCode", bnkStnCode);

				// サーブレットの接続先
				request(TARGET_SERVLET);
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
		int row = panel.ssBank.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssBank.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssBank.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssBank.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// 保持しておいた値をスクロールバーにセット
			panel.ssBank.getVertSB().setValue(0);
			panel.ssBank.getHorizSB().setValue(0);
		}
		// ボタンロック解除
		lockBtn(panel.ssBank.getDataSource().getNumRows() != 0);
	}

	/**
	 * マスタ選択行状態の保持
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {

		panel.ssBank.setRowSelection(row, row);
		panel.ssBank.setCurrentCell(row, 0);

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
		addSendValues("flag", isInsert ? "insert" : "update");
		// 銀行コードの設定
		addSendValues("bnkCode", (String) map.get("bnkCode"));
		// 銀行支店コードの設定
		addSendValues("bnkStnCode", (String) map.get("bnkStnCode"));
		// 銀行名の設定
		addSendValues("bnkName_S", (String) map.get("bnkName_S"));
		// 銀行カナ名称の設定
		addSendValues("bnkKana", (String) map.get("bnkKana"));
		// 銀行検索名称の設定
		addSendValues("bnkName_K", (String) map.get("bnkName_K"));
		// 銀行支店名の設定
		addSendValues("bnkStnName_S", (String) map.get("bnkStnName_S"));
		// 銀行支店カナ名称の設定
		addSendValues("bnkStnKana", (String) map.get("bnkStnKana"));
		// 銀行支店検索名称の設定
		addSendValues("bnkStnName_K", (String) map.get("bnkStnName_K"));
		// 開始年月日の設定
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// 終了年月日の設定
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// プログラムＩＤの設定
		addSendValues("prgID", PROGRAM_ID);
		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
		}
	}

	/**
	 * エクセルリスト出力
	 */

	void outptExcel() {

		try {
			panel.ctrlBeginBankBranch.getField().requestFocus();
			// 確認ダイアログ表示
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}

			Map conds = new HashMap();
			// 送信するパラメータを設定
			conds.put("flag", "report");
			// 銀行コードの取得
			conds.put("bnkCode", panel.ctrlBank.getValue());
			// 開始コードの取得
			conds.put("beginBnkStnCode", panel.ctrlBeginBankBranch.getField().getText());
			// 終了コードの取得
			conds.put("endBnkStnCode", panel.ctrlEndBankBranch.getField().getText());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, TARGET_SERVLET, conds);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * Dialog生成
	 * 
	 * @param titleId
	 */
	protected void createEditDisplayDialog(String titleId) {
		dialog = new MG0140EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}
}
