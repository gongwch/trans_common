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
 * 取引先条件マスタ画面コントロール
 * 
 * @author mayongliang
 */
public class MG0155CustomerConditionMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	protected static final String PROGRAM_ID = "MG0155";

	/** 新規と複写区分 */
	private static int INSERT_KBN = 0;

	/** 編集区分 */
	private static int UPD_KBN = 1;

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0155CustomerConditionMasterServlet";
	}

	/** パネル */
	protected MG0155CustomerConditionMasterPanel panel;

	private REFDialogCtrl refBeginCustomer;

	private REFDialogCtrl refEndCustomer;

	/**
	 * コンストラクタ.
	 */
	public MG0155CustomerConditionMasterPanelCtrl() {
		try {
			// 一覧画面の初期化
			panel = new MG0155CustomerConditionMasterPanel(this);
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

		refBeginCustomer = new REFDialogCtrl(panel.ctrlBeginCustomer, panel.getParentFrame());
		refBeginCustomer.setTargetServlet("MG0150CustomerMasterServlet");
		refBeginCustomer.setTitleID("C02326");
		refBeginCustomer.setColumnLabels("C00786", "C00787", "C00836");
		refBeginCustomer.setShowsMsgOnError(false);
		refBeginCustomer.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 会社コードの設定
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndCustomer.getField().getText());
				keys.put("kind", "Customer");
				return keys;
			}

		});

		panel.ctrlBeginCustomer.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refBeginCustomer.refreshName();

				return true;
			}
		});

		// 終了コードの設定
		refEndCustomer = new REFDialogCtrl(panel.ctrlEndCustomer, panel.getParentFrame());
		refEndCustomer.setTargetServlet("MG0150CustomerMasterServlet");
		refEndCustomer.setTitleID("C02326");
		refEndCustomer.setColumnLabels("C00786", "C00787", "C00836");
		refEndCustomer.setShowsMsgOnError(false);
		refEndCustomer.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 会社コードの設定
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginCustomer.getField().getText());
				keys.put("kind", "Customer");
				return keys;
			}

		});

		panel.ctrlEndCustomer.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refEndCustomer.refreshName();

				return true;
			}
		});

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
			MG0155EditDisplayDialogCtrl dialog = new MG0155EditDisplayDialogCtrl(panel.getParentFrame(), "C01698");
			// 編集画面の表示
			dialog.show();
			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), true);
			// 選択行保持する
			String triCode = dialog.getDataList().get("triCode").toString();
			String triSjCode = dialog.getDataList().get("tjkCode").toString();
			modifySpreadRow(triCode, triSjCode, INSERT_KBN);
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
			MG0155EditDisplayDialogCtrl dialog = new MG0155EditDisplayDialogCtrl(panel.getParentFrame(), "C00977");
			// 当前行を取得する
			int nomRow = panel.ssCustomerCondition.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssCustomerCondition.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 取引先コードの設定
			map.put("triCode", model.getTableDataItem(nomRow, 1));
			// 取引先条件コードの設定
			map.put("tjkCode", model.getTableDataItem(nomRow, 3));
			// 振込手数料区分の設定
			map.put("furiTesuKen", model.getTableDataItem(nomRow, 30));
			// 支払条件締め日の設定
			map.put("sjcDate", model.getTableDataItem(nomRow, 5));
			// 支払条件締め後月の設定
			map.put("sjrMon", model.getTableDataItem(nomRow, 6));
			// 支払条件支払日の設定
			map.put("sjpDate", model.getTableDataItem(nomRow, 7));
			// 支払区分の設定
			map.put("sihaKbn", model.getTableDataItem(nomRow, 31));
			// 支払方法の設定
			map.put("sihaHouCode", model.getTableDataItem(nomRow, 9));
			// 振込振出銀行口座ｺｰﾄﾞの設定
			map.put("furiCbkCode", model.getTableDataItem(nomRow, 10));
			// 銀行コードの設定
			map.put("bnkCode", model.getTableDataItem(nomRow, 11));
			// 支店コードの設定
			map.put("bnkStnCode", model.getTableDataItem(nomRow, 12));
			// 預金種目の設定
			map.put("yknKbn", model.getTableDataItem(nomRow, 32));
			// 口座番号の設定
			map.put("yknNo", model.getTableDataItem(nomRow, 14));
			// 口座名義カナの設定
			map.put("yknKana", model.getTableDataItem(nomRow, 15));
			// 送金目的名の設定
			map.put("gsMktCode", model.getTableDataItem(nomRow, 16));
			// 被仕向銀行名称の設定
			map.put("gsBnkName", model.getTableDataItem(nomRow, 17));
			// 被仕向支店名称の設定
			map.put("gsStnName", model.getTableDataItem(nomRow, 18));
			// 口座名義の設定
			map.put("yknName", model.getTableDataItem(nomRow, 19));
			// 手数料区分の設定
			map.put("gsTesuKbn", model.getTableDataItem(nomRow, 33));
			// 手数料区分の設定
			map.put("sihaBnkName", model.getTableDataItem(nomRow, 21));
			// 支払支店名称の設定
			map.put("sihaStnName", model.getTableDataItem(nomRow, 22));
			// 支払銀行住所の設定
			map.put("sihaBnkAdr", model.getTableDataItem(nomRow, 23));
			// 支払銀行住所の設定
			map.put("keiyuBnkName", model.getTableDataItem(nomRow, 24));
			// 経由支店名称の設定
			map.put("keiyuStnName", model.getTableDataItem(nomRow, 25));
			// 経由支店名称の設定
			map.put("keiyuBnkAdr", model.getTableDataItem(nomRow, 26));
			// 受取人住所の設定
			map.put("ukeAdr", model.getTableDataItem(nomRow, 27));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 28)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 29)));
			// 処理種別の設定
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
			String triCode = dialog.getDataList().get("triCode").toString();
			String triSjCode = dialog.getDataList().get("tjkCode").toString();
			modifySpreadRow(triCode, triSjCode, UPD_KBN);
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
			MG0155EditDisplayDialogCtrl dialog = new MG0155EditDisplayDialogCtrl(panel.getParentFrame(), "C01738");
			// 当前行を取得する
			int nomRow = panel.ssCustomerCondition.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssCustomerCondition.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 取引先コードの設定
			map.put("triCode", model.getTableDataItem(nomRow, 1));
			// 取引先条件コード
			map.put("tjkCode", model.getTableDataItem(nomRow, 3));
			// 振込手数料区分
			map.put("furiTesuKen", model.getTableDataItem(nomRow, 30));
			// 支払条件締め日の設定
			map.put("sjcDate", model.getTableDataItem(nomRow, 5));
			// 支払条件締め後月の設定
			map.put("sjrMon", model.getTableDataItem(nomRow, 6));
			// 支払条件締め後月の設定
			map.put("sjpDate", model.getTableDataItem(nomRow, 7));
			// 支払区分の設定
			map.put("sihaKbn", model.getTableDataItem(nomRow, 31));
			// 支払方法の設定
			map.put("sihaHouCode", model.getTableDataItem(nomRow, 9));
			// 振込振出銀行口座ｺｰﾄﾞの設定
			map.put("furiCbkCode", model.getTableDataItem(nomRow, 10));
			// 銀行コードの設定
			map.put("bnkCode", model.getTableDataItem(nomRow, 11));
			// 銀行コードの設定
			map.put("bnkStnCode", model.getTableDataItem(nomRow, 12));
			// 預金種目の設定
			map.put("yknKbn", model.getTableDataItem(nomRow, 32));
			// 預金種目の設定
			map.put("yknNo", model.getTableDataItem(nomRow, 14));
			// 口座名義カナの設定
			map.put("yknKana", model.getTableDataItem(nomRow, 15));
			// 送金目的名の設定
			map.put("gsMktCode", model.getTableDataItem(nomRow, 16));
			// 被仕向銀行名称の設定
			map.put("gsBnkName", model.getTableDataItem(nomRow, 17));
			// 被仕向支店名称の設定
			map.put("gsStnName", model.getTableDataItem(nomRow, 18));
			// 口座名義の設定
			map.put("yknName", model.getTableDataItem(nomRow, 19));
			// 手数料区分の設定
			map.put("gsTesuKbn", model.getTableDataItem(nomRow, 33));
			// 支払銀行名称の設定
			map.put("sihaBnkName", model.getTableDataItem(nomRow, 21));
			// 支払支店名称の設定
			map.put("sihaStnName", model.getTableDataItem(nomRow, 22));
			// 支払銀行住所の設定
			map.put("sihaBnkAdr", model.getTableDataItem(nomRow, 23));
			// 経由銀行名称の設定
			map.put("keiyuBnkName", model.getTableDataItem(nomRow, 24));
			// 経由銀行名称の設定
			map.put("keiyuStnName", model.getTableDataItem(nomRow, 25));
			// 経由銀行住所の設定
			map.put("keiyuBnkAdr", model.getTableDataItem(nomRow, 26));
			// 受取人住所の設定
			map.put("ukeAdr", model.getTableDataItem(nomRow, 27));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 28)));
			// 開始年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 29)));
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

			String triCode = dialog.getDataList().get("triCode").toString();
			String triSjCode = dialog.getDataList().get("tjkCode").toString();
			modifySpreadRow(triCode, triSjCode, INSERT_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param triCode
	 * @param triSjCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String triCode, String triSjCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(triCode, triSjCode, updKbn);
		TTable ssPanelSpread = panel.ssCustomerCondition;
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
		lockBtn(panel.ssCustomerCondition.getDataSource().getNumRows() != 0);
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
	 * 新規、複写、編集の場合、スプレッド取得
	 * 
	 * @param triCode
	 * @param triSjCode
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected Vector<Vector> setModifyCell(String triCode, String triSjCode, int updKbn) throws IOException,
		TRequestException {
		String text;
		String value;
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		// 送信するパラメータを設定
		addSendValues("flag", "findOneInfo");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("triCode", Util.avoidNull(triCode));
		// 終了コードの設定
		addSendValues("triSjCode", Util.avoidNull(triSjCode));
		// サーブレットの接続先
		if (!request(getServletName())) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssCustomerCondition.getDataSource();
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

			// 振込手数料区分
			value = colum.get(4);
			text = ("1".equals(value) ? this.getWord("C00399") : this.getWord("C00327"));
			colum.add(30, value);
			colum.set(4, text);

			// 支払区分
			value = colum.get(8);
			text = ("00".equals(value) ? this.getWord("C02166") : this.getWord("C02167"));
			colum.add(31, value);
			colum.set(8, text);

			// 預金種目
			value = colum.get(13);

			if (value.equals("1")) {
				text = this.getWord("C00465");
			} else if (value.equals("2")) {
				text = this.getWord("C02154");
			} else if (value.equals("3")) {
				text = this.getWord("C02168");
			} else {
				text = this.getWord("C02169");
			}
			colum.add(32, value);
			colum.set(13, text);

			// 手数料区分
			value = colum.get(20);
			text = ("1".equals(value) ? this.getWord("C00021") : this.getWord("C02319"));
			colum.add(33, value);
			colum.set(20, text);
			try {
				colum.set(28, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(28))));
				colum.set(29, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(29))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(26), ex);
			}

		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssCustomerCondition.getCurrentRow(), colum);
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
		addSendValues("flag", isInsert ? "insert" : "update");
		// 会社コードの設定
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// 取引先コードの設定
		addSendValues("triCode", (String) map.get("triCode"));
		// 取引先コードの設定
		addSendValues("tjkCode", (String) map.get("tjkCode"));
		// 振込手数料区分の設定
		addSendValues("furiTesuKen", (String) map.get("furiTesuKen"));
		// 支払条件締め日の設定
		addSendValues("sjcDate", (String) map.get("sjcDate"));
		// 支払条件締め後月の設定
		addSendValues("sjrMon", (String) map.get("sjrMon"));
		// 支払条件支払日の設定
		addSendValues("sjpDate", (String) map.get("sjpDate"));
		// 支払区分の設定
		addSendValues("sihaKbn", (String) map.get("sihaKbn"));
		// 支払方法の設定
		addSendValues("sihaHouCode", (String) map.get("sihaHouCode"));
		// 振込振出銀行口座ｺｰﾄﾞの設定
		addSendValues("furiCbkCode", (String) map.get("furiCbkCode"));
		// 銀行コードの設定
		addSendValues("bnkCode", (String) map.get("bnkCode"));
		// 支店コードの設定
		addSendValues("bnkStnCode", (String) map.get("bnkStnCode"));
		// 預金種目の設定
		addSendValues("yknKbn", (String) map.get("yknKbn"));
		// 預金種目の設定
		addSendValues("yknNo", (String) map.get("yknNo"));
		// 預金種目の設定
		addSendValues("yknName", (String) map.get("yknName"));
		// 口座名義カナの設定
		addSendValues("yknKana", (String) map.get("yknKana"));
		// 送金目的名の設定
		addSendValues("gsMktCode", (String) map.get("gsMktCode"));
		// 送金目的名の設定
		addSendValues("gsStnName", (String) map.get("gsStnName"));
		// 被仕向銀行名称の設定
		addSendValues("gsBnkName", (String) map.get("gsBnkName"));
		// 手数料区分の設定
		addSendValues("gsTesuKbn", (String) map.get("gsTesuKbn"));
		// 手数料区分の設定
		addSendValues("sihaBnkName", (String) map.get("sihaBnkName"));
		// 支払支店名称の設定
		addSendValues("sihaStnName", (String) map.get("sihaStnName"));
		// 支払支店名称の設定
		addSendValues("sihaBnkAdr", (String) map.get("sihaBnkAdr"));
		// 経由銀行名称の設定
		addSendValues("keiyuBnkName", (String) map.get("keiyuBnkName"));
		// 経由支店名称の設定
		addSendValues("keiyuStnName", (String) map.get("keiyuStnName"));
		// 経由銀行住所の設定
		addSendValues("keiyuBnkAdr", (String) map.get("keiyuBnkAdr"));
		// 受取人住所の設定
		addSendValues("ukeAdr", (String) map.get("ukeAdr"));
		// 開始年月日の設定
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// 終了年月日の設定
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// プログラムＩＤの設定
		addSendValues("prgID", PROGRAM_ID);
		// サーブレットの接続先
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
				int nomRow = panel.ssCustomerCondition.getCurrentRow();
				// データ集を取得する
				TableDataModel model = panel.ssCustomerCondition.getDataSource();
				// 会社コードの取得
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// 取引先コードの取得
				String triCode = (String) model.getTableDataItem(nomRow, 1);
				// 取引先条件コードの取得
				String tjkCode = (String) model.getTableDataItem(nomRow, 3);
				// 処理種別の設定
				addSendValues("flag", "delete");
				// 会社コードの設定
				addSendValues("kaiCode", kaiCode);
				// 取引先コードの設定
				addSendValues("triCode", triCode);
				// 取引先条件コードの設定
				addSendValues("tjkCode", tjkCode);

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
	protected void deleteSpreadRow() {
		int row = panel.ssCustomerCondition.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssCustomerCondition.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssCustomerCondition.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssCustomerCondition.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// 保持しておいた値をスクロールバーにセット
			panel.ssCustomerCondition.getVertSB().setValue(0);
			panel.ssCustomerCondition.getHorizSB().setValue(0);
		}
		// ボタンロック解除
		lockBtn(panel.ssCustomerCondition.getDataSource().getNumRows() != 0);
	}

	/**
	 * マスタ選択行状態の保持
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {
		panel.ssCustomerCondition.setRowSelection(row, row);
		panel.ssCustomerCondition.setCurrentCell(row, 0);
	}

	/**
	 * 検索処理
	 * 
	 * @return boolean
	 */
	boolean find() {

		try {
			// 開始コードの取得
			String beginTriSjCode = panel.ctrlBeginCustomer.getField().getText();
			// 終了コードの取得
			String endTriSjCode = panel.ctrlEndCustomer.getField().getText();
			// 開始コードが終了コードより大きい
			if (Util.isSmallerThen(beginTriSjCode, endTriSjCode) == false) {
				// 警告メッセージ表示
				panel.ctrlBeginCustomer.getField().requestFocus();
				showMessage(panel, "W00036", "C00408");
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
	 * 画面リフレッシュ
	 * 
	 * @return boolean
	 * @throws IOException
	 */

	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		String text;
		String value;
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();
		// 開始コードの取得
		String beginTriSjCode = panel.ctrlBeginCustomer.getField().getText();
		// 終了コードの取得
		String endTriSjCode = panel.ctrlEndCustomer.getField().getText();

		beginTriSjCode = beginTriSjCode.trim();
		endTriSjCode = endTriSjCode.trim();
		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginTriSjCode", Util.avoidNull(beginTriSjCode));
		// 終了コードの設定
		addSendValues("endTriSjCode", Util.avoidNull(endTriSjCode));

		if (!request(getServletName())) {
			errorHandler(panel);
			return false;
		}
		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();

		Iterator recordIte = getResultList().iterator();
		if (!recordIte.hasNext()) {
			panel.ctrlBeginCustomer.getField().requestFocus();
			// 警告メッセージ表示
			showMessage(panel, "W00100");
			dataExists = false;
		}
		for (int row = 0; recordIte.hasNext(); row++) {
			Iterator dataIte = ((List) recordIte.next()).iterator();
			// サーブレットから送られてきた結果を配列にセット
			Vector<String> colum = new Vector<String>();

			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}

			// 振込手数料区分
			value = colum.get(4);
			text = ("1".equals(value) ? this.getWord("C00399") : this.getWord("C00327"));
			colum.add(30, value);
			colum.set(4, text);

			// 支払区分
			value = colum.get(8);
			text = ("00".equals(value) ? this.getWord("C02166") : this.getWord("C02167"));
			colum.add(31, value);
			colum.set(8, text);

			// 預金種目
			value = colum.get(13);

			if (value.equals("1")) {
				text = this.getWord("C00465");
			} else if (value.equals("2")) {
				text = this.getWord("C02154");
			} else if (value.equals("3")) {
				text = this.getWord("C02168");
			} else {
				text = this.getWord("C02169");
			}
			colum.add(32, value);
			colum.set(13, text);

			// 手数料区分
			value = colum.get(20);
			text = ("1".equals(value) ? this.getWord("C00021") : this.getWord("C02319"));
			colum.add(33, value);
			colum.set(20, text);
			try {
				colum.set(28, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(28))));
				colum.set(29, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(29))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(26), ex);
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
			panel.ctrlBeginCustomer.getField().requestFocus();
			// 確認ダイアログ表示
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginTriSjCode", panel.ctrlBeginCustomer.getValue());
			conds.put("endTriSjCode", panel.ctrlEndCustomer.getValue());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
