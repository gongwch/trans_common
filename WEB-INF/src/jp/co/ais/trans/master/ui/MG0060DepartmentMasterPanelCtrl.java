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
 * 部門マスタ画面コントロール
 */
public class MG0060DepartmentMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	private static final String PROGRAM_ID = "MG0060";

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0060DepartmentMasterServlet";

	/** 新規と複写区分 */
	protected static int INSERT_KBN = 0;

	/** 編集区分 */
	protected static int UPD_KBN = 1;

	/** パネル */
	protected MG0060DepartmentMasterPanel panel;

	private REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	/** 入力のテキスト */
	private String labelInput = getWord("C01275");

	/** 集計のテキスト */
	private String labelSummary = getWord("C00255");

	protected MG0060EditDisplayDialogCtrl dialog;

	/**
	 * コンストラクタ.
	 */
	public MG0060DepartmentMasterPanelCtrl() {
		try {
			// 一覧画面の初期化
			panel = new MG0060DepartmentMasterPanel(this);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
		panel.ctrlBeginDepartment.setMaxLength(10);
		panel.ctrlEndDepartment.setMaxLength(10);
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				init();
			}
		});

	}

	void init() {
		panel.ctrlBeginDepartment.requestTextFocus();
		// 開始コードの設定

		ref1 = new REFDialogCtrl(panel.ctrlBeginDepartment, panel.getParentFrame());
		ref1.setTargetServlet("MG0060DepartmentMasterServlet");
		ref1.setTitleID(getWord("C02338"));
		ref1.setColumnLabelIDs("C00698", "C00724", "C00725");
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 会社コードの設定
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "DepartmentAll");
				keys.put("endCode", panel.ctrlEndDepartment.getField().getText());
				return keys;
			}
		});

		panel.ctrlBeginDepartment.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}
		});
		// 終了コードの設定
		ref2 = new REFDialogCtrl(panel.ctrlEndDepartment, panel.getParentFrame());
		ref2.setTargetServlet("MG0060DepartmentMasterServlet");
		ref2.setTitleID(getWord("C02338"));
		ref2.setColumnLabelIDs("C00698", "C00724", "C00725");
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 会社コードの設定
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "DepartmentAll");
				keys.put("beginCode", panel.ctrlBeginDepartment.getField().getText());
				return keys;
			}
		});
		panel.ctrlEndDepartment.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				return true;
			}
		});

	}

	/**
	 * パネル取得
	 * 
	 * @return 部門マスタパネル
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
		dialog = new MG0060EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * 新規登録処理
	 */
	void insert() {
		try {
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
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			String depCode = dialog.getDataList().get("depCode").toString();
			modifySpreadRow(kaiCode, depCode, INSERT_KBN);
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
			int nomRow = panel.ssDepartmentList.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssDepartmentList.getDataSource();
			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) return;
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 部門コードの設定
			map.put("depCode", model.getTableDataItem(nomRow, 1));
			// 部門名称の設定
			map.put("depName", model.getTableDataItem(nomRow, 2));
			// 部門略称の設定
			map.put("depName_S", model.getTableDataItem(nomRow, 3));
			// 部門検索名称の設定
			map.put("depName_K", model.getTableDataItem(nomRow, 4));
			// 人員数１の設定
			map.put("men1", model.getTableDataItem(nomRow, 5));
			// 人員数２の設定
			map.put("men2", model.getTableDataItem(nomRow, 6));
			// 床面積の設定
			map.put("space", model.getTableDataItem(nomRow, 7));
			// 部門区分の設定
			map.put("depKbn", model.getTableDataItem(nomRow, 15));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 10)));
			// 処理種別の設定
			map.put("flag", "update");
			// データを画面に設定する
			dialog.setValues(map);
			// 編集画面の表示
			dialog.show(false);
			// 編集画面が開けていました
			if (!dialog.isSettle()) return;
			// データを編集する
			modify(dialog.getDataList(), false);
			// スプレッド更新
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			String depCode = dialog.getDataList().get("depCode").toString();
			modifySpreadRow(kaiCode, depCode, UPD_KBN);
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
			int nomRow = panel.ssDepartmentList.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssDepartmentList.getDataSource();
			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) return;
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 部門コードの設定
			map.put("depCode", model.getTableDataItem(nomRow, 1));
			// 部門名称の設定
			map.put("depName", model.getTableDataItem(nomRow, 2));
			// 部門略称の設定
			map.put("depName_S", model.getTableDataItem(nomRow, 3));
			// 部門検索名称の設定
			map.put("depName_K", model.getTableDataItem(nomRow, 4));
			// 人員数１の設定
			map.put("men1", model.getTableDataItem(nomRow, 5));
			// 人員数２の設定
			map.put("men2", model.getTableDataItem(nomRow, 6));
			// 床面積の設定
			map.put("space", model.getTableDataItem(nomRow, 7));
			// 部門区分の設定
			map.put("depKbn", model.getTableDataItem(nomRow, 15));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 10)));
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

			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			String depCode = dialog.getDataList().get("depCode").toString();
			modifySpreadRow(kaiCode, depCode, INSERT_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param kaiCode
	 * @param depCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String kaiCode, String depCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(kaiCode, depCode, updKbn);
		TTable ssPanelSpread = panel.ssDepartmentList;
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
		lockBtn(panel.ssDepartmentList.getDataSource().getNumRows() != 0);
	}

	/**
	 * 新規、複写、編集の場合、スプレッド取得
	 * 
	 * @param kaiCode
	 * @param depCode
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String kaiCode, String depCode, int updKbn) throws IOException,
		TRequestException {
		// 処理種別の設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginDepCode", Util.avoidNull(depCode));
		// 終了コードの設定
		addSendValues("endDepCode", Util.avoidNull(depCode));

		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
		// 結果を取得する
		List<List<String>> resultList = getResultList();

		// 値があるかどうか
		if (resultList.isEmpty()) {
			panel.ctrlBeginDepartment.getField().requestFocus();
			showMessage(panel, "W00100");
		}

		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssDepartmentList.getDataSource();
		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = modifyDs.getCells();
		// columの初期化
		Vector<String> colum = new Vector<String>();
		for (List<String> list : resultList) {

			for (String value : list) {
				// 結果を追加する
				colum.add(value);
			}

			// men1
			colum.set(5, NumberFormatUtil.formatNumber(colum.get(5)));

			// men2
			colum.set(6, NumberFormatUtil.formatNumber(colum.get(6)));

			// space
			colum.set(7, NumberFormatUtil.formatNumber(colum.get(7), 2));

			// 日付
			colum.set(9, toYMDString(colum.get(9)));
			colum.set(10, toYMDString(colum.get(10)));

			// 部門区分の追加
			colum.add(15, colum.get(8));

			// 部門区分内容の設定
			colum.set(8, "0".equals(colum.get(8)) ? labelInput : labelSummary);

		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssDepartmentList.getCurrentRow(), colum);
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
		// 会社コードの設定
		super.addSendValues("kaiCode", (String) map.get("kaiCode"));
		// 部門コードの設定
		super.addSendValues("depCode", (String) map.get("depCode"));
		// 部門名称の設定
		super.addSendValues("depName", (String) map.get("depName"));
		// 部門略称の設定
		super.addSendValues("depName_S", (String) map.get("depName_S"));
		// 部門検索名称の設定
		super.addSendValues("depName_K", (String) map.get("depName_K"));
		// 人員数１の設定
		super.addSendValues("men1", (String) map.get("men1"));
		// 人員数２の設定
		super.addSendValues("men2", (String) map.get("men2"));
		// 床面積の設定
		super.addSendValues("space", (String) map.get("space"));
		// 部門区分の設定
		super.addSendValues("depKbn", (String) map.get("depKbn"));
		// 開始年月日の設定
		super.addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// 終了年月日の設定
		super.addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// プログラムＩＤの設定
		super.addSendValues("prgID", PROGRAM_ID);

		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
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
				int nomRow = panel.ssDepartmentList.getCurrentRow();
				// データ集を取得する
				TableDataModel model = panel.ssDepartmentList.getDataSource();
				// 会社コードの取得
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// 部門コードの取得
				String depCode = (String) model.getTableDataItem(nomRow, 1);
				// 処理種別の設定
				super.addSendValues("flag", "delete");
				// 会社コードの設定
				super.addSendValues("kaiCode", kaiCode);
				// 部門コードの設定
				super.addSendValues("depCode", depCode);

				// サーバ側のエラー場合
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
		int row = panel.ssDepartmentList.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssDepartmentList.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssDepartmentList.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssDepartmentList.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// 保持しておいた値をスクロールバーにセット
			panel.ssDepartmentList.getVertSB().setValue(0);
			panel.ssDepartmentList.getHorizSB().setValue(0);
		}
		// ボタンロック解除
		lockBtn(panel.ssDepartmentList.getDataSource().getNumRows() != 0);
	}

	/**
	 * マスタ選択行状態の保持
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {
		panel.ssDepartmentList.setRowSelection(row, row);
		panel.ssDepartmentList.setCurrentCell(row, 0);
	}

	/**
	 * 検索処理
	 * 
	 * @return true:正常
	 */
	boolean find() {
		try {
			// 開始コードの取得
			String strBegin = panel.ctrlBeginDepartment.getValue();
			// 終了コードの取得
			String strEnd = panel.ctrlEndDepartment.getValue();

			// 開始コードが終了コードより大きい
			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				panel.ctrlBeginDepartment.getField().requestFocus();
				// 警告メッセージ表示
				super.showMessage(panel, "W00036", "C00467");
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
	private boolean reflesh() throws IOException {

		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();
		// 開始コードの取得
		String beginDepCode = panel.ctrlBeginDepartment.getValue();
		// 終了コードの取得
		String endDepCode = panel.ctrlEndDepartment.getValue();
		// 処理種別の設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginDepCode", Util.avoidNull(beginDepCode));
		// 終了コードの設定
		addSendValues("endDepCode", Util.avoidNull(endDepCode));

		// 送信
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return false;
		}

		boolean dataExists = true;

		// 結果を取得する
		List<List<String>> resultList = getResultList();

		// 値があるかどうか
		if (resultList.isEmpty()) {
			panel.ctrlBeginDepartment.getField().requestFocus();
			showMessage(panel, "W00100");
			dataExists = false;
		}

		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();

		for (List<String> list : resultList) {

			// columの初期化
			Vector<String> colum = new Vector<String>();
			for (String value : list) {
				// 結果を追加する
				colum.add(value);
			}

			// men1
			colum.set(5, NumberFormatUtil.formatNumber(colum.get(5)));

			// men2
			colum.set(6, NumberFormatUtil.formatNumber(colum.get(6)));

			// space
			colum.set(7, NumberFormatUtil.formatNumber(colum.get(7), 2));

			// 日付
			colum.set(9, toYMDString(colum.get(9)));
			colum.set(10, toYMDString(colum.get(10)));

			// 部門区分の追加
			colum.add(15, colum.get(8));

			// 部門区分内容の設定
			colum.set(8, "0".equals(colum.get(8)) ? labelInput : labelSummary);

			// 結果集を追加する
			cells.add(colum);
		}

		// 結果を表示する
		panel.setDataList(cells);

		return dataExists;
	}

	/**
	 * 不正日付文字をYYYY/MM/DD形式に切り替える.<br>
	 * 変換できない場合、通知の意味でそのまま値を表示する
	 * 
	 * @param value 対象値
	 * @return 変換後の値
	 */
	private String toYMDString(String value) {
		try {
			return DateUtil.toYMDString(DateUtil.toYMDDate(value));

		} catch (ParseException e) {
			ClientErrorHandler.handledException(e);
			return value;
		}
	}

	/**
	 * エクセルリスト出力
	 */

	void outptExcel() {

		try {
			panel.ctrlBeginDepartment.getField().requestFocus();
			// 確認ダイアログ表示
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}

			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginDepCode", panel.ctrlBeginDepartment.getValue());
			conds.put("endDepCode", panel.ctrlEndDepartment.getValue());

			conds.put("langCode", getLoginLanguage());

			// サーブレットの接続先
			this.download(panel, TARGET_SERVLET, conds);

		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
