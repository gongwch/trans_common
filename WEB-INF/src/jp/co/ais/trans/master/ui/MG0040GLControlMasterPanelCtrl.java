package jp.co.ais.trans.master.ui;

import java.io.*;
import java.util.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;

/**
 * GLコントロールマスタ画面コントロール
 * 
 * @author ISFnet China
 */

public class MG0040GLControlMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	protected static final String PROGRAM_ID = "MG0040";

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
		return "MG0040GLControlMasterServlet";
	}

	/** パネル */
	protected MG0040GLControlMasterPanel panel;

	/**
	 * コンストラクタ.
	 */

	public MG0040GLControlMasterPanelCtrl() {
		try {
			// 一覧画面の初期化
			panel = new MG0040GLControlMasterPanel(this);
			/**
			 * 検索処理
			 */
			this.reflesh();
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * パネル取得
	 * 
	 * @return GLコントロールマスタパネル
	 */
	public TPanelBusiness getPanel() {
		// パネルを返す
		return this.panel;
	}

	/**
	 * 編集処理
	 */
	void update() {
		try {
			// 編集画面の初期化
			MG0040EditDisplayDialogCtrl dialog = new MG0040EditDisplayDialogCtrl(panel.getParentFrame(), "C00977");
			// 当前行を取得する
			int nomRow = panel.ssGLCodeRoleList.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssGLCodeRoleList.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 会社コードの設定
			map.put("kaiCode", model.getTableDataItem(nomRow, 0));
			// 決算段階区分
			map.put("ksdKbn", model.getTableDataItem(nomRow, 12));
			// 決算伝票入力区分
			map.put("ksnNyuKbn", model.getTableDataItem(nomRow, 9));
			// 元帳日別残高表示区分
			map.put("mtZanHyjKbn", model.getTableDataItem(nomRow, 10));
			// 評価替レート区分
			map.put("excRateKbn", model.getTableDataItem(nomRow, 11));
			// 処理種別の設定
			map.put("flag", "save");
			// データを画面に設定する
			dialog.setValues(map);
			// 編集画面を表示する
			dialog.show();
			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList());

			// スプレッド更新
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			modifySpreadRow(kaiCode, UPD_KBN);

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
			// 当前行を取得する
			int nomRow = panel.ssGLCodeRoleList.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssGLCodeRoleList.getDataSource();

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
	 * 変更処理
	 * 
	 * @param map データ
	 * @throws IOException
	 */
	protected void modify(Map map) throws IOException {
		// 処理種別の設定
		super.addSendValues("flag", "save");
		// 会社コードの設定
		super.addSendValues("kaiCode", (String) map.get("kaiCode"));
		// 決算段階区分
		super.addSendValues("ksdKbn", (String) map.get("ksdKbn"));
		// 決算伝票入力区分
		super.addSendValues("ksnNyuKbn", (String) map.get("ksnNyuKbn"));
		// 元帳日別残高表示区分
		super.addSendValues("mtZanHyjKbn", (String) map.get("mtZanHyjKbn"));
		// 評価替レート区分
		super.addSendValues("excRateKbn", (String) map.get("excRateKbn"));
		// プログラムＩＤの設定
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
				int nomRow = panel.ssGLCodeRoleList.getCurrentRow();
				// データ集を取得する
				TableDataModel model = panel.ssGLCodeRoleList.getDataSource();
				// 会社コードの取得
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// 処理種別の設定
				super.addSendValues("flag", "delete");
				// 会社コードの設定
				super.addSendValues("kaiCode", kaiCode);

				if (!request(getServletName())) {
					// サーバ側のエラー場合
					errorHandler(panel);
				}

				// スプレッド更新
				modifySpreadRow(kaiCode, UPD_KBN);

			} catch (Exception e) {
				// 正常に処理されませんでした
				errorHandler(panel.getParentFrame(), e, "E00009");
			}
		}
	}

	/**
	 * 検索処理
	 * 
	 * @return boolean
	 */
	boolean find() {

		try {
			// ボタンロック解除
			panel.ssGLCodeRoleList.getDataSource();
			return reflesh();
		} catch (IOException e) {
			// 正常に処理されませんでした
			lockBtn(false);
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
		panel.btnListOutput.setEnabled(boo);
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
	 * 表示データの取得
	 * 
	 * @return データ
	 * @throws IOException
	 */
	protected boolean reflesh() throws IOException {

		boolean dataExists = true;

		// 処理種別の設定
		addSendValues("flag", "find");

		if (!request(getServletName())) {
			// サーバ側のエラー場合
			errorHandler(panel);
		}

		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();
		// 結果を取得する
		Iterator recordIte = getResultList().iterator();
		// 値があるかどうか
		if (!recordIte.hasNext()) {
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

			String value;
			String text = "";
			int i = Integer.parseInt(colum.get(2));

			// 決算伝票入力区分
			if (i == 0) {
				text = this.getWord("C00009");
			} else if (i == 1) {
				text = this.getWord("C00435");
			} else if (i == 2) {
				text = this.getWord("C00239");
			} else if (i == 3) {
				text = this.getWord("C00147");
			}
			colum.add(9, Integer.toString(i));
			colum.set(2, text);

			// 0:(日別残高を)表示しない 1:(日別残高を)表示する
			value = colum.get(3);
			text = ("0".equals(value) ? this.getWord("C02369") : this.getWord("C02370"));
			colum.add(10, value);
			colum.set(3, text);

			// 0:月末レート 1:翌月月初レート
			value = colum.get(4);
			text = ("0".equals(value) ? this.getWord("C00148") : this.getWord("C00535"));
			colum.add(11, value);
			colum.set(4, text);

			// 決算段階区分
			int col1 = Integer.parseInt(colum.get(1));
			if (col1 == 0) {
				colum.set(1, this.getWord("C00038"));
			} else if (col1 > 0) {
				colum.set(1, col1 + this.getWord("C00373"));
			} else {
				colum.set(1, String.valueOf(col1));
			}
			colum.add(12, String.valueOf(col1));

			cells.add(row, colum);
		}

		panel.setDataList(cells);

		// データ集を取得する
		TableDataModel model = panel.ssGLCodeRoleList.getDataSource();

		if (model.getNumRows() > 0) {
			String kaiCode = (String) model.getTableDataItem(0, 0);
			panel.btnDelete.setEnabled(checkCode(kaiCode));
		}

		return dataExists;
	}

	/**
	 * エクセルリスト出力
	 */
	void outptExcel() {

		try {

			if (!find()) return;
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}

			Map conds = new HashMap();
			// 送信するパラメータを設定
			conds.put("flag", "report");
			// 会社コードの取得
			conds.put("kaiCode", "");
			conds.put("langCode", getLoginLanguage());

			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	boolean checkCode(String kaiCode) {
		try {
			// 会社コード未入力
			if (Util.isNullOrEmpty(kaiCode)) {
				return false;
			}
			// 処理種別の設定
			addSendValues("flag", "checkcode");
			addSendValues("kaiCode", kaiCode);

			if (!request(getServletName())) {
				// サーバ側のエラー場合
				errorHandler(panel);
			}

			// 結果を取得
			List result = super.getResultList();
			// 結果を返す
			return (result.size() > 0);
		} catch (IOException e) {
			errorHandler(panel);
			return false;
		}
	}

	/**
	 * 新規、編集、削除の場合、スプレッド更新
	 * 
	 * @param kaiCode 会社コード
	 * @param updKbn 更新区分
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String kaiCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(kaiCode, updKbn);
		TTable ssPanelSpread = panel.ssGLCodeRoleList;
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
		panel.btnDelete.setEnabled(checkCode(kaiCode));
	}

	/**
	 * 新規、編集、削除の場合、スプレッド取得
	 * 
	 * @param kaiCode 会社コード
	 * @param updKbn 更新区分
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected Vector<Vector> setModifyCell(String kaiCode, int updKbn) throws IOException, TRequestException {
		// 処理種別の設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);

		// サーブレットの接続先
		if (!request(getServletName())) {
			throw new TRequestException();
		}
		// 結果を取得する
		List<List<String>> resultList = getResultList();

		// 値があるかどうか
		if (resultList == null || resultList.isEmpty()) {
			showMessage(panel, "W00100");
		}

		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssGLCodeRoleList.getDataSource();
		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = modifyDs.getCells();
		// columの初期化
		Vector<String> colum = new Vector<String>();
		for (List<String> list : resultList) {

			for (String value : list) {
				// 結果を追加する
				colum.add(value);
			}

			String value;
			String text = "";
			int i = Integer.parseInt(colum.get(2));

			// 決算伝票入力区分
			if (i == 0) {
				text = this.getWord("C00009");
			} else if (i == 1) {
				text = this.getWord("C00435");
			} else if (i == 2) {
				text = this.getWord("C00239");
			} else if (i == 3) {
				text = this.getWord("C00147");
			}
			colum.add(9, Integer.toString(i));
			colum.set(2, text);

			// 0:(日別残高を)表示しない 1:(日別残高を)表示する
			value = colum.get(3);
			text = ("0".equals(value) ? this.getWord("C02369") : this.getWord("C02370"));
			colum.add(10, value);
			colum.set(3, text);

			// 0:月末レート 1:翌月月初レート
			value = colum.get(4);
			text = ("0".equals(value) ? this.getWord("C00148") : this.getWord("C00535"));
			colum.add(11, value);
			colum.set(4, text);

			// 決算段階区分
			int col1 = Integer.parseInt(colum.get(1));
			if (col1 == 0) {
				colum.set(1, this.getWord("C00038"));
			} else if (col1 > 0) {
				colum.set(1, col1 + this.getWord("C00373"));
			} else {
				colum.set(1, String.valueOf(col1));
			}
			colum.add(12, String.valueOf(col1));
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssGLCodeRoleList.getCurrentRow(), colum);
		}
		return cells;
	}

	/**
	 * マスタ選択行状態の保持
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {
		panel.ssGLCodeRoleList.setRowSelection(row, row);
		panel.ssGLCodeRoleList.setCurrentCell(row, 0);
	}

}
