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
 * 管理マスタ画面コントロール
 * 
 * @author ookawara
 */
public class MG0210Management4MasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	protected static final String PROGRAM_ID = "MG0210";

	/** 新規と複写区分 */
	protected static int INSERT_KBN = 0;

	/** 編集区分 */
	protected static int UPD_KBN = 1;

	MG0210EditDisplayDialogCtrl dialog;

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0210Management4MasterServlet";
	}

	/**  */
	public String[] columnName;

	/** パネル */
	protected MG0210Management4MasterPanel panel;

	String knrName4;

	private REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	/**
	 * コンストラクタ.
	 */
	public MG0210Management4MasterPanelCtrl() {
		knrName4 = CompanyControlHelper210.getInstance(this.getLoginUserCompanyCode()).getKnrName4();
		columnName = new String[] { "C00596", knrName4 + this.getWord("C00174"), knrName4 + this.getWord("C00518"),
				knrName4 + this.getWord("C00548"), knrName4 + this.getWord("C00160"), "C00055", "C00261" };
		try {
			// 一覧画面の初期化
			panel = new MG0210Management4MasterPanel(this);
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

		panel.ctrlBeginManagement.getButton().setText(knrName4);
		panel.ctrlEndManagement.getButton().setText(knrName4);

	}

	void init() {
		// 開始コードの設定
		ref1 = new REFDialogCtrl(panel.ctrlBeginManagement, panel.getParentFrame());
		ref1.setTargetServlet(getServletName());
		ref1.setTitleID(knrName4 + this.getWord("C00500"));
		ref1.setPrefixID(knrName4);
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				// 会社コードの設定
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndManagement.getField().getText());
				keys.put("kind", "Management4");
				return keys;
			}
		});

		panel.ctrlBeginManagement.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();

				return true;
			}
		});

		// 終了コードの設定
		ref2 = new REFDialogCtrl(panel.ctrlEndManagement, panel.getParentFrame());
		ref2.setTargetServlet(getServletName());
		ref2.setTitleID(knrName4 + this.getWord("C00500"));
		ref2.setPrefixID(knrName4);
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				// 会社コードの設定
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginManagement.getField().getText());
				keys.put("kind", "Management4");
				return keys;
			}
		});

		panel.ctrlEndManagement.setInputVerifier(new TInputVerifier() {

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
		dialog = new MG0210EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
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
			modifySpreadRow(dialog.getDataList().get("knrCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(e);
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
			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) return;
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 管理4ｺｰﾄﾞの設定
			map.put("knrCode", model.getTableDataItem(nomRow, 1));
			// 管理4名称の設定
			map.put("knrName", model.getTableDataItem(nomRow, 2));
			// 管理4略称の設定
			map.put("knrName_S", model.getTableDataItem(nomRow, 3));
			// 管理4検索名称の設定
			map.put("knrName_K", model.getTableDataItem(nomRow, 4));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 5)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 6)));
			// 処理種別の設定
			map.put("flag", "update");
			// データを画面に設定する
			dialog.setValues(map);
			// 編集画面を表示する
			dialog.show(false);
			// 編集画面が開けていました
			if (!dialog.isSettle()) return;
			// データが編集する
			modify(dialog.getDataList(), false);
			// スプレッド更新
			modifySpreadRow(dialog.getDataList().get("knrCode").toString(), UPD_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(e);
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
			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) return;
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 管理コードの設定
			map.put("knrCode", model.getTableDataItem(nomRow, 1));
			// 管理4名称の設定
			map.put("knrName", model.getTableDataItem(nomRow, 2));
			// 管理4略称の設定
			map.put("knrName_S", model.getTableDataItem(nomRow, 3));
			// 管理4検索名称の設定
			map.put("knrName_K", model.getTableDataItem(nomRow, 4));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 5)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 6)));
			// 処理種別の設定
			map.put("flag", "insert");
			// データを画面に設定する
			dialog.setValues(map);
			// 編集画面の表示
			dialog.show(true);
			// 編集画面が開けていました
			if (!dialog.isSettle()) return;
			// データが編集する
			modify(dialog.getDataList(), true);
			// スプレッド更新
			modifySpreadRow(dialog.getDataList().get("knrCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(e);
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
		// 管理コードの設定
		super.addSendValues("knrCode", (String) map.get("knrCode"));
		// 管理4名称の設定
		super.addSendValues("knrName", (String) map.get("knrName"));
		// 管理4略称の設定
		super.addSendValues("knrName_S", (String) map.get("knrName_S"));
		// 管理4検索名称の設定
		super.addSendValues("knrName_K", (String) map.get("knrName_K"));
		// 開始年月日の設定
		super.addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// 開始年月日の設定
		super.addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// プログラムＩＤの設定
		super.addSendValues("prgID", PROGRAM_ID);
		// サーブレットの接続先
		if (!request(getServletName())) {
			// サーバ側のエラー場合
			errorHandler(panel);
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param krnCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String krnCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(krnCode, updKbn);
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
	 * @param knrCode
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String knrCode, int updKbn) throws IOException, TRequestException {
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		// 処理種別の設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginknrCode", Util.avoidNull(knrCode));
		// 終了コードの設定
		addSendValues("endknrCode", Util.avoidNull(knrCode));

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
				colum.set(5, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(5))));
				colum.set(6, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(6))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(5), ex);
			}
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
				// 管理コードの取得
				String knrCode = (String) model.getTableDataItem(nomRow, 1);
				// 処理種別の設定
				super.addSendValues("flag", "delete");
				// 会社コードの設定
				super.addSendValues("kaiCode", kaiCode);
				// 管理コードの設定
				super.addSendValues("knrCode", knrCode);
				// サーブレットの接続先
				if (!request(getServletName())) {
					errorHandler(panel);
					return;
				}
				deleteSpreadRow();
			} catch (IOException e) {
				// 正常に処理されませんでした
				errorHandler(e);
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
			String strBegin = panel.ctrlBeginManagement.getValue();
			// 終了コードの取得
			String strEnd = panel.ctrlEndManagement.getValue();
			// 開始コードが終了コードより大きい
			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				// 警告メッセージ表示
				panel.ctrlBeginManagement.getField().requestFocus();
				showMessage(panel, "W00036", knrName4);
				return false;
			}

			// 表示データの取得
			return reflesh();
			// ボタンロック解除

		} catch (IOException e) {
			// ボタンロックの設定
			lockBtn(false);

			// 正常に処理されませんでした
			errorHandler(e);
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
		boolean dataExists = true;
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();
		// 開始コードの取得
		String beginknrCode = panel.ctrlBeginManagement.getValue();
		// 終了コードの取得
		String endknrCode = panel.ctrlEndManagement.getValue();
		// 処理種別の設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginknrCode", Util.avoidNull(beginknrCode));
		// 終了コードの設定
		addSendValues("endknrCode", Util.avoidNull(endknrCode));

		// サーバ側のエラー場合
		if (!request(getServletName())) {
			errorHandler(panel);
			return false;
		}
		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();
		// 結果を取得する
		Iterator recordIte = getResultList().iterator();
		if (!recordIte.hasNext()) {
			panel.ctrlBeginManagement.getField().requestFocus();
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
			cells.add(row, colum);
			try {
				colum.set(5, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(5))));
				colum.set(6, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(6))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName(), ex);
			}
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
			panel.ctrlBeginManagement.getField().requestFocus();
			// 確認ダイアログ表示
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginknrCode", panel.ctrlBeginManagement.getValue());
			conds.put("endknrCode", panel.ctrlEndManagement.getValue());
			conds.put("langCode", getLoginLanguage());
			this.download(panel, getServletName(), conds);

		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(e);
		}
	}
}
