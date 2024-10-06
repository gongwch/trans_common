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
 * 環境設定マスタパネル
 */
public class MG0010EnvironmentalSettingMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	private static final String PROGRAM_ID = "MG0010";

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0010EnvironmentalSettingMasterServlet";

	/** パネル */
	protected MG0010EnvironmentalSettingMasterPanel panel;

	/** 新規と複写区分 */
	protected static int INSERT_KBN = 0;

	/** 編集区分 */
	protected static int UPD_KBN = 1;

	/**
	 * コンストラクタ.
	 */
	public MG0010EnvironmentalSettingMasterPanelCtrl() {
		try {
			panel = new MG0010EnvironmentalSettingMasterPanel(this);

		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
		// find
		this.find();
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
	 * @param titleId タイトルID
	 * @return 請求区分マスタ編集画面Ctrl
	 */
	protected MG0010EditDisplayDialogCtrl createDialogCtrl(String titleId) {
		return new MG0010EditDisplayDialogCtrl(panel.getParentFrame(), titleId);
	}

	/**
	 * 新規登録処理
	 */
	void insert() {
		try {
			MG0010EditDisplayDialogCtrl dialog = createDialogCtrl("C01698");

			dialog.show(true);

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), true);
			unlockBtn();

			// スプレッド更新 選択行保持する
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			modifySpreadRow(kaiCode, INSERT_KBN);

		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 編集処理
	 */

	void update() {
		try {
			MG0010EditDisplayDialogCtrl dialog = createDialogCtrl("C00977");

			int nomRow = panel.ssEnvironmentalSettingList.getCurrentRow();
			TableDataModel model = panel.ssEnvironmentalSettingList.getDataSource();
			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("kaiCode", model.getTableDataItem(nomRow, 0));
			map.put("kaiName", model.getTableDataItem(nomRow, 1));
			map.put("kaiName_S", model.getTableDataItem(nomRow, 2));
			map.put("jyu1", model.getTableDataItem(nomRow, 3));
			map.put("jyu2", model.getTableDataItem(nomRow, 4));
			map.put("jyuKana", model.getTableDataItem(nomRow, 5));
			map.put("zip", model.getTableDataItem(nomRow, 6));
			map.put("tel", model.getTableDataItem(nomRow, 7));
			map.put("fax", model.getTableDataItem(nomRow, 8));
			map.put("foreColor", model.getTableDataItem(nomRow, 11));
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 10)));
			map.put("flag", "update");

			dialog.setValues(map);
			dialog.show(false);

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), false);

			// スプレッド更新 選択行保持する
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			modifySpreadRow(kaiCode, UPD_KBN);

		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * データ処理
	 */
	void enter() {
		try {
			int nomRow = panel.ssEnvironmentalSettingList.getCurrentRow();
			TableDataModel model = panel.ssEnvironmentalSettingList.getDataSource();

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
			MG0010EditDisplayDialogCtrl dialog = createDialogCtrl("C01738");
			int nomRow = panel.ssEnvironmentalSettingList.getCurrentRow();
			TableDataModel model = panel.ssEnvironmentalSettingList.getDataSource();
			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("kaiCode", model.getTableDataItem(nomRow, 0));
			map.put("kaiName", model.getTableDataItem(nomRow, 1));
			map.put("kaiName_S", model.getTableDataItem(nomRow, 2));
			map.put("jyu1", model.getTableDataItem(nomRow, 3));
			map.put("jyu2", model.getTableDataItem(nomRow, 4));
			map.put("jyuKana", model.getTableDataItem(nomRow, 5));
			map.put("zip", model.getTableDataItem(nomRow, 6));
			map.put("tel", model.getTableDataItem(nomRow, 7));
			map.put("fax", model.getTableDataItem(nomRow, 8));
			map.put("foreColor", model.getTableDataItem(nomRow, 11));
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 9)));
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 10)));
			map.put("flag", "insert");

			dialog.setValues(map);
			dialog.show(true);

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), true);

			// スプレッド更新 選択行保持する
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			modifySpreadRow(kaiCode, INSERT_KBN);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 変更処理
	 * 
	 * @param map データ
	 * @param isInsert 新規(true) or 更新(false)
	 * @throws IOException
	 * @throws TRequestException
	 */

	protected void modify(Map map, boolean isInsert) throws IOException, TRequestException {
		super.addSendValues("flag", isInsert ? "insert" : "update");
		super.addSendValues("kaiCode", (String) map.get("kaiCode"));
		super.addSendValues("kaiName", (String) map.get("kaiName"));
		super.addSendValues("kaiName_S", (String) map.get("kaiName_S"));
		super.addSendValues("jyu1", (String) map.get("jyu1"));
		super.addSendValues("jyu2", (String) map.get("jyu2"));
		super.addSendValues("jyuKana", (String) map.get("jyuKana"));
		super.addSendValues("zip", (String) map.get("zip"));
		super.addSendValues("tel", (String) map.get("tel"));
		super.addSendValues("fax", (String) map.get("fax"));
		super.addSendValues("foreColor", (String) map.get("foreColor"));
		super.addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		super.addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		super.addSendValues("prgID", PROGRAM_ID);

		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
	}

	/**
	 * 削除処理
	 */
	void delete() {
		if (this.showConfirmMessage(panel, "Q00007", "")) {
			try {

				// 選択されている行の1つ目と2つ目のカラムを取得
				int nomRow = panel.ssEnvironmentalSettingList.getCurrentRow();
				TableDataModel model = panel.ssEnvironmentalSettingList.getDataSource();
				String kaiCode = (String) model.getTableDataItem(nomRow, 0); // 会社コード
				super.addSendValues("flag", "delete");
				super.addSendValues("kaiCode", kaiCode);

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
	 * 検索処理
	 */
	void find() {

		try {
			this.reflesh();
			// ボタンロック解除

		} catch (IOException e) {
			lockBtn(false);

			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
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
	}

	/**
	 * 画面リフレッシュ
	 * 
	 * @throws IOException private void reflesh() throws IOException { panel.setDataList(getDataList()); }
	 */

	/**
	 * 表示データの取得
	 * 
	 * @throws IOException
	 */
	protected void reflesh() throws IOException {

		// 送信するパラメータを設定
		addSendValues("flag", "find");

		// 送信
		if (request(TARGET_SERVLET)) {

			// サーブレットから送られてきた結果を配列にセット
			Vector<Vector> cells = new Vector<Vector>();

			Iterator recordIte = getResultList().iterator();

			// 値があるかどうか
			if (!recordIte.hasNext()) {
				showMessage(panel, "W00100", "");
			}

			for (int row = 0; recordIte.hasNext(); row++) {
				Iterator dataIte = ((List) recordIte.next()).iterator();

				Vector<String> colum = new Vector<String>();

				for (int column = 0; dataIte.hasNext(); column++) {
					colum.add(column, (String) dataIte.next());
				}

				String jyu1, jyu2, jyuKana, zip, tel, fax, strDate, endDate;

				jyu1 = colum.get(11);
				jyu2 = colum.get(12);
				jyuKana = colum.get(10);
				zip = colum.get(9);
				tel = colum.get(13);
				fax = colum.get(14);
				try {
					strDate = DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(3)));
					endDate = DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(4)));
					colum.set(9, strDate);
					colum.set(10, endDate);
				} catch (Exception e) {
					errorHandler(panel.getParentFrame(), e, "E00009");
				}
				colum.set(3, jyu1);
				colum.set(4, jyu2);
				colum.set(5, jyuKana);
				colum.set(6, zip);
				colum.set(7, tel);
				colum.set(8, fax);

				colum.set(11, colum.get(15));
				cells.add(row, colum);
			}

			panel.setDataList(cells);
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param kaiCode 会社コード
	 * @param updKbn 更新区分
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String kaiCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(kaiCode, updKbn);
		TTable ssPanelSpread = panel.ssEnvironmentalSettingList;
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
		lockBtn(panel.ssEnvironmentalSettingList.getDataSource().getNumRows() != 0);
	}

	/**
	 * 新規、複写、編集の場合、スプレッド取得
	 * 
	 * @param kaiCode 会社コード
	 * @param updKbn 更新区分
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String kaiCode, int updKbn) throws IOException, TRequestException {
		// 処理種別の設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);

		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
		// 結果を取得する
		List<List<String>> resultList = getResultList();

		// 値があるかどうか
		if (resultList == null || resultList.isEmpty()) {
			showMessage(panel, "W00100");
		}

		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssEnvironmentalSettingList.getDataSource();
		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = modifyDs.getCells();
		// columの初期化
		Vector<String> colum = new Vector<String>();

		for (List<String> list : resultList) {

			for (String value : list) {
				// 結果を追加する
				colum.add(value);
			}

			String jyu1, jyu2, jyuKana, zip, tel, fax, strDate, endDate;

			jyu1 = colum.get(11);
			jyu2 = colum.get(12);
			jyuKana = colum.get(10);
			zip = colum.get(9);
			tel = colum.get(13);
			fax = colum.get(14);
			try {
				strDate = DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(3)));
				endDate = DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(4)));
				colum.set(9, strDate);
				colum.set(10, endDate);
			} catch (Exception e) {
				errorHandler(panel.getParentFrame(), e, "E00009");
			}
			colum.set(3, jyu1);
			colum.set(4, jyu2);
			colum.set(5, jyuKana);
			colum.set(6, zip);
			colum.set(7, tel);
			colum.set(8, fax);

			colum.set(11, colum.get(15));
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssEnvironmentalSettingList.getCurrentRow(), colum);
		}
		return cells;
	}

	/**
	 * マスタ選択行状態の保持
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {
		panel.ssEnvironmentalSettingList.setRowSelection(row, row);
		panel.ssEnvironmentalSettingList.setCurrentCell(row, 0);
	}

	/**
	 * 削除場合、スプレッド更新
	 */
	private void deleteSpreadRow() {
		int row = panel.ssEnvironmentalSettingList.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssEnvironmentalSettingList.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssEnvironmentalSettingList.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssEnvironmentalSettingList.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// 保持しておいた値をスクロールバーにセット
			panel.ssEnvironmentalSettingList.getVertSB().setValue(0);
			panel.ssEnvironmentalSettingList.getHorizSB().setValue(0);
		}
		// ボタンロック解除
		lockBtn(panel.ssEnvironmentalSettingList.getDataSource().getNumRows() != 0);
	}

}
