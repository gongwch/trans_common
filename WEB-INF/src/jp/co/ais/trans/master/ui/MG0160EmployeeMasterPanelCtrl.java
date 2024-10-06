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
 * 社員マスタ画面コントロール
 * 
 * @author ookawara
 */
public class MG0160EmployeeMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	protected static final String PROGRAM_ID = "MG0160";

	/** 新規と複写区分 */
	protected static int INSERT_KBN = 0;

	/** 編集区分 */
	protected static int UPD_KBN = 1;

	MG0160EditDisplayDialogCtrl dialog;

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0160EmployeeMasterServlet";
	}

	/** パネル */
	protected MG0160EmployeeMasterPanel panel;

	protected REFDialogCtrl refBeginEmployee;

	protected REFDialogCtrl refEndEmployee;

	/**
	 * コンストラクタ.
	 */
	public MG0160EmployeeMasterPanelCtrl() {
		try {
			// 一覧画面の初期化
			panel = new MG0160EmployeeMasterPanel(this);
			// ボタンロック
			lockBtn(false);

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

		refBeginEmployee = new REFDialogCtrl(panel.ctrlBeginEmployee, panel.getParentFrame());
		refBeginEmployee.setColumnLabelIDs("C00697", "C00808", "C00809");
		refBeginEmployee.setTargetServlet(getServletName());
		refBeginEmployee.setTitleID("C00913");
		refBeginEmployee.setShowsMsgOnError(false);
		refBeginEmployee.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				// 会社コードの設定
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndEmployee.getField().getText());
				keys.put("kind", "Employee");
				return keys;
			}
		});

		panel.ctrlBeginEmployee.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refBeginEmployee.refreshName();

				return true;
			}
		});

		// 終了コードの設定
		refEndEmployee = new REFDialogCtrl(panel.ctrlEndEmployee, panel.getParentFrame());
		refEndEmployee.setColumnLabelIDs("C00697", "C00808", "C00809");
		refEndEmployee.setTargetServlet(getServletName());
		refEndEmployee.setTitleID("C00913");
		refEndEmployee.setShowsMsgOnError(false);
		refEndEmployee.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				// 会社コードの設定
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginEmployee.getField().getText());
				keys.put("kind", "Employee");
				return keys;
			}
		});

		panel.ctrlEndEmployee.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refEndEmployee.refreshName();

				return true;
			}
		});
	}

	/**
	 * パネル取得
	 * 
	 * @return 社員マスタパネル
	 */
	public TPanelBusiness getPanel() {
		// パネルを返す
		return this.panel;
	}

	/**
	 * ボタンロックの制御
	 * 
	 * @param boo
	 */
	void lockBtn(boolean boo) {
		panel.btnDelete.setEnabled(boo);
		panel.btnEdit.setEnabled(boo);
		panel.btnCopy.setEnabled(boo);
	}

	/**
	 * 検索処理
	 * 
	 * @return boolean
	 */
	boolean find() {
		try {
			// 開始コードの取得
			String strBegin = panel.ctrlBeginEmployee.getValue();
			// 終了コードの取得
			String strEnd = panel.ctrlEndEmployee.getValue();
			// 開始コードが終了コードより大きい
			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				// 警告メッセージ表示
				panel.ctrlBeginEmployee.getField().requestFocus();
				super.showMessage(panel, "W00036", "C00246");
				return false;
			}

			// 表示データの取得
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
	 * Dialog生成
	 * 
	 * @param titleId
	 */
	protected void createEditDisplayDialog(String titleId) {
		dialog = new MG0160EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * 新規登録処理
	 */
	void insert() {
		try {
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
			modifySpreadRow(dialog.getDataList().get("empCode").toString(), INSERT_KBN);
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
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 社員コードの設定
			map.put("empCode", model.getTableDataItem(nomRow, 1));
			// 社員名称
			map.put("empName", model.getTableDataItem(nomRow, 2));
			// 社員略称
			map.put("empName_S", model.getTableDataItem(nomRow, 3));
			// 社員検索名称
			map.put("empName_K", model.getTableDataItem(nomRow, 4));
			// 振込銀行ＣＤ
			map.put("empFuriBnkCode", model.getTableDataItem(nomRow, 5));
			// 振込支店ＣＤ
			map.put("empFuriStnCode", model.getTableDataItem(nomRow, 6));
			// 振込口座番号
			map.put("empYknNo", model.getTableDataItem(nomRow, 7));
			// 振込口座預金種別
			map.put("empKozaKbn", model.getTableDataItem(nomRow, 13));
			// 口座名義カナ
			map.put("empYknKana", model.getTableDataItem(nomRow, 9));
			// 振出銀行口座コード
			map.put("empCbkCode", model.getTableDataItem(nomRow, 10));
			// 開始年月日
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 11)));
			// 終了年月日
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 12)));
			// 処理種別の設定
			map.put("flag", "update");
			// データを画面に設定する
			dialog.setValues(map);
			dialog.show();
			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), false);
			// スプレッド更新
			modifySpreadRow(dialog.getDataList().get("empCode").toString(), UPD_KBN);
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
			int nomRow = panel.ssJournal.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssJournal.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 社員コードの設定
			map.put("empCode", model.getTableDataItem(nomRow, 1));
			// 社員名称の設定
			map.put("empName", model.getTableDataItem(nomRow, 2));
			// 社員略称の設定
			map.put("empName_S", model.getTableDataItem(nomRow, 3));
			// 社員検索名称の設定
			map.put("empName_K", model.getTableDataItem(nomRow, 4));
			// 振込銀行ＣＤの設定
			map.put("empFuriBnkCode", model.getTableDataItem(nomRow, 5));
			// 振込支店ＣＤの設定
			map.put("empFuriStnCode", model.getTableDataItem(nomRow, 6));
			// 振込口座番号の設定
			map.put("empYknNo", model.getTableDataItem(nomRow, 7));
			// 振込口座預金種別の設定
			map.put("empKozaKbn", model.getTableDataItem(nomRow, 13));
			// 口座名義カナの設定
			map.put("empYknKana", model.getTableDataItem(nomRow, 9));
			// 振出銀行口座コードの設定
			map.put("empCbkCode", model.getTableDataItem(nomRow, 10));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 11)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 12)));
			// 処理種別の設定
			map.put("flag", "insert");
			// データを画面に設定する
			dialog.setValues(map);
			dialog.show();
			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集す
			modify(dialog.getDataList(), true);
			modifySpreadRow(dialog.getDataList().get("empCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param empCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String empCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(empCode, updKbn);
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
	 * @param empCode
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String empCode, int updKbn) throws IOException, TRequestException {
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		// 処理種別の設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginEmpCode", Util.avoidNull(empCode));
		// 終了コードの設定
		addSendValues("endEmpCode", Util.avoidNull(empCode));

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
				colum.set(11, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(11))));
				colum.set(12, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(12))));
			} catch (ParseException ex) {
				// 正常に処理されませんでした
				errorHandler(panel);
			}

			// 振込口座預金種別
			String value = colum.get(8);
			String text = ("1".equals(value) ? this.getWord("C00465") : this.getWord("C02154"));
			colum.add(13, value);
			colum.set(8, text);

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
		if (this.showConfirmMessage(panel, "Q00007", "")) {
			try {
				// 選択されている行の1つ目と2つ目のカラムを取得
				int nomRow = panel.ssJournal.getCurrentRow();
				// データ集を取得する
				TableDataModel model = panel.ssJournal.getDataSource();
				// 会社コードの取得
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// 社員コードの取得
				String empCode = (String) model.getTableDataItem(nomRow, 1);
				// 処理種別の設定
				addSendValues("flag", "delete");
				// 会社コードの設定
				addSendValues("kaiCode", kaiCode);
				// 社員コードの取得
				addSendValues("empCode", empCode);

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
		// 社員コードの設定
		addSendValues("empCode", (String) map.get("empCode"));
		// 社員名称の設定
		addSendValues("empName", (String) map.get("empName"));
		// 社員略称の設定
		addSendValues("empName_S", (String) map.get("empName_S"));
		// 社員検索名称の設定
		addSendValues("empName_K", (String) map.get("empName_K"));
		// 振込銀行ＣＤの設定
		addSendValues("empFuriBnkCode", (String) map.get("empFuriBnkCode"));
		// 振込支店ＣＤの設定
		addSendValues("empFuriStnCode", (String) map.get("empFuriStnCode"));
		// 振込口座番号の設定
		addSendValues("empYknNo", (String) map.get("empYknNo"));
		// 振込口座預金種別の設定
		addSendValues("empKozaKbn", (String) map.get("empKozaKbn"));
		// 口座名義カナの設定
		addSendValues("empYknKana", (String) map.get("empYknKana"));
		// 振出銀行口座コードの設定
		addSendValues("empCbkCode", (String) map.get("empCbkCode"));
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
	 * 画面リフレッシュ
	 * 
	 * @return boolean
	 * @throws IOException
	 */
	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();
		// 開始コードの取得
		String beginEmpCode = panel.ctrlBeginEmployee.getValue();
		// 終了コードの取得
		String endEmpCode = panel.ctrlEndEmployee.getValue();

		// 処理種別の設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginEmpCode", Util.avoidNull(beginEmpCode));
		// 終了コードの設定
		addSendValues("endEmpCode", Util.avoidNull(endEmpCode));

		// 送信
		if (!request(getServletName())) {
			errorHandler(panel);
			return false;
		}

		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();
		// 結果を取得する
		Iterator recordIte = getResultList().iterator();
		// 値があるかどうか
		if (!recordIte.hasNext()) {
			panel.ctrlBeginEmployee.getField().requestFocus();
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
				colum.set(11, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(11))));
				colum.set(12, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(12))));
			} catch (ParseException ex) {
				// 正常に処理されませんでした
				errorHandler(panel);
			}

			// 振込口座預金種別
			String value = colum.get(8);
			String text = ("1".equals(value) ? this.getWord("C00465") : this.getWord("C02154"));
			colum.add(13, value);
			colum.set(8, text);

			cells.add(row, colum);
		}

		// 結果を表示する
		panel.setDataList(cells);
		return dataExists;
	}

	/**
	 * エクセルリスト出力
	 */

	void outptExcel() {

		try {
			panel.ctrlBeginEmployee.getField().requestFocus();
			// 確認ダイアログ表示
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginEmpCode", panel.ctrlBeginEmployee.getValue());
			conds.put("endEmpCode", panel.ctrlEndEmployee.getValue());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
