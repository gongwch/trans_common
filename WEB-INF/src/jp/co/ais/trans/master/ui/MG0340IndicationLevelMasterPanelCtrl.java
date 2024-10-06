package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
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
 * 開示レベルマスタ画面コントロール
 */
public class MG0340IndicationLevelMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	private static final String PROGRAM_ID = "MG0340";

	/** 新規と複写区分 */
	private static int INSERT_KBN = 0;

	/** 編集区分 */
	private static int UPD_KBN = 1;

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0340IndicationLevelMasterServlet";

	/** パネル */
	private MG0340IndicationLevelMasterPanel panel;

	private REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	/**
	 * コンストラクタ.
	 */
	public MG0340IndicationLevelMasterPanelCtrl() {
		try {
			panel = new MG0340IndicationLevelMasterPanel(this);
			// ボタンロック
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		// 画面初期化
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				init();
			}
		});
	}

	/**
	 * 画面初期化
	 */
	void init() {
		ref1 = new REFDialogCtrl(panel.ctrlBeginUser, panel.getParentFrame());
		ref1.setColumnLabels("C00589", "C00692", "C00693");
		ref1.setTargetServlet("MG0020UserMasterServlet");
		ref1.setTitleID(getWord("C02355"));
		ref1.setShowsMsgOnError(false);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndUser.getField().getText());
				keys.put("kind", "User");
				return keys;
			}
		});

		panel.ctrlBeginUser.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				return true;
			}
		});

		ref2 = new REFDialogCtrl(panel.ctrlEndUser, panel.getParentFrame());
		ref2.setColumnLabels("C00589", "C00692", "C00693");
		ref2.setTargetServlet("MG0020UserMasterServlet");
		ref2.setTitleID(getWord("C02355"));
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginUser.getField().getText());
				keys.put("kind", "User");
				return keys;
			}
		});

		panel.ctrlEndUser.setInputVerifier(new TInputVerifier() {

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
	 * 新規登録処理
	 */
	void insert() {
		try {
			MG0340EditDisplayDialogCtrl dialog = new MG0340EditDisplayDialogCtrl(panel.getParentFrame(), "C01698");

			dialog.show(true);

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), true);
			unlockBtn();
			// 選択行保持する
			String kjlUsrId = dialog.getDataList().get("kjlUsrId").toString();
			String kjlKmtCode = dialog.getDataList().get("kjlKmtCode").toString();
			String kjlDpkSsk = dialog.getDataList().get("kjlDpkSsk").toString();
			modifySpreadRow(kjlUsrId, kjlKmtCode, kjlDpkSsk, INSERT_KBN);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 編集処理
	 */
	void update() {
		try {
			MG0340EditDisplayDialogCtrl dialog = new MG0340EditDisplayDialogCtrl(panel.getParentFrame(), "C00977");

			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();
			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("kjlUsrId", model.getTableDataItem(nomRow, 1));
			map.put("kjlKmtCode", model.getTableDataItem(nomRow, 2));
			map.put("kjlDpkSsk", model.getTableDataItem(nomRow, 3));
			map.put("kjlLvl", model.getTableDataItem(nomRow, 7));
			map.put("kjlUpDepCode", model.getTableDataItem(nomRow, 5));
			map.put("kjlDepCode", model.getTableDataItem(nomRow, 6));
			map.put("flag", "update");

			dialog.setValues(map);
			dialog.show(false);

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), false);

			// スプレッド更新
			String kjlUsrId = dialog.getDataList().get("kjlUsrId").toString();
			String kjlKmtCode = dialog.getDataList().get("kjlKmtCode").toString();
			String kjlDpkSsk = dialog.getDataList().get("kjlDpkSsk").toString();
			modifySpreadRow(kjlUsrId, kjlKmtCode, kjlDpkSsk, UPD_KBN);
		} catch (Exception e) {
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
			ClientLogger.error(this.getClass().getName(), e);
		}
	}

	/**
	 * 複写処理
	 */
	public void copy() {
		try {
			MG0340EditDisplayDialogCtrl dialog = new MG0340EditDisplayDialogCtrl(panel.getParentFrame(), "C01738");

			int nomRow = panel.ssJournal.getCurrentRow();
			TableDataModel model = panel.ssJournal.getDataSource();
			// データチェック
			if (Util.isNullOrEmpty(model.getTableDataItem(nomRow, 1))) {
				return;
			}
			Map<String, Object> map = new TreeMap<String, Object>();
			map.put("kjlUsrId", model.getTableDataItem(nomRow, 1));
			map.put("kjlKmtCode", model.getTableDataItem(nomRow, 2));
			map.put("kjlDpkSsk", model.getTableDataItem(nomRow, 3));
			map.put("kjlLvl", model.getTableDataItem(nomRow, 7));
			map.put("kjlUpDepCode", model.getTableDataItem(nomRow, 5));
			map.put("kjlDepCode", model.getTableDataItem(nomRow, 6));
			map.put("flag", "insert");

			dialog.setValues(map);
			dialog.show(true);

			if (!dialog.isSettle()) {
				return;
			}

			modify(dialog.getDataList(), true);

			String kjlUsrId = dialog.getDataList().get("kjlUsrId").toString();
			String kjlKmtCode = dialog.getDataList().get("kjlKmtCode").toString();
			String kjlDpkSsk = dialog.getDataList().get("kjlDpkSsk").toString();
			// スプレッド更新
			modifySpreadRow(kjlUsrId, kjlKmtCode, kjlDpkSsk, INSERT_KBN);
		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param kjlUsrId
	 * @param kjlKmtCode
	 * @param kjlDpkSsk
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	private void modifySpreadRow(String kjlUsrId, String kjlKmtCode, String kjlDpkSsk, int updKbn)
		throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(kjlUsrId, kjlKmtCode, kjlDpkSsk, updKbn);
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
	 * @param kjlUsrId
	 * @param kjlKmtCode
	 * @param kjlDpkSsk
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	private Vector<Vector> setModifyCell(String kjlUsrId, String kjlKmtCode, String kjlDpkSsk, int updKbn)
		throws IOException, TRequestException {
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		addSendValues("flag", "findOne");
		addSendValues("kaiCode", kaiCode);
		addSendValues("kjlUsrId", kjlUsrId);
		addSendValues("kjlKmtCode", kjlKmtCode);
		addSendValues("kjlDpkSsk", kjlDpkSsk);

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
			Iterator dataIte = ((List) recordIte.next()).iterator();

			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}
			String text = colum.get(4);
			colum.set(4, this.getWord("C01739") + text);
			colum.set(7, text);
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
	private void modify(Map map, boolean isInsert) throws IOException {
		super.addSendValues("flag", isInsert ? "insert" : "update");
		super.addSendValues("kaiCode", getLoginUserCompanyCode());
		super.addSendValues("kjlUsrId", (String) map.get("kjlUsrId"));
		super.addSendValues("kjlKmtCode", (String) map.get("kjlKmtCode"));
		super.addSendValues("kjlDpkSsk", (String) map.get("kjlDpkSsk"));
		super.addSendValues("kjlLvl", (String) map.get("kjlLvl"));
		super.addSendValues("kjlUpDepCode", (String) map.get("kjlUpDepCode"));
		super.addSendValues("kjlDepCode", (String) map.get("kjlDepCode"));
		super.addSendValues("prgID", PROGRAM_ID);

		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return;
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
				TableDataModel model = panel.ssJournal.getDataSource();
				// 会社コード
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// ユーザーコード
				String kjlUsrId = (String) model.getTableDataItem(nomRow, 1);
				// 科目体系ｺｰﾄﾞ
				String kjlKmtCode = (String) model.getTableDataItem(nomRow, 2);
				// 組織コード
				String kjlDpkSsk = (String) model.getTableDataItem(nomRow, 3);

				super.addSendValues("flag", "delete");
				super.addSendValues("kaiCode", kaiCode);
				super.addSendValues("kjlUsrId", kjlUsrId);
				super.addSendValues("kjlKmtCode", kjlKmtCode);
				super.addSendValues("kjlDpkSsk", kjlDpkSsk);

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
		try {

			// 開始コードの取得
			String strBegin = panel.ctrlBeginUser.getField().getText();
			// 終了コードの取得
			String strEnd = panel.ctrlEndUser.getField().getText();
			// 開始コードが終了コードより大きい
			if (Util.isSmallerThen(strBegin, strEnd) == false) {
				// 警告メッセージ表示
				panel.ctrlBeginUser.getField().requestFocus();
				showMessage(panel, "W00036", "C00528 ");
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
			panel.ctrlBeginUser.getField().requestFocus();
			// 確認ダイアログ表示
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			conds.put("flag", "report");
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("beginKjlUsrId", panel.ctrlBeginUser.getValue());
			conds.put("endKjlUsrId", panel.ctrlEndUser.getValue());
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
	private boolean reflesh() throws IOException {
		boolean dataExists = true;
		String kaiCode = getLoginUserCompanyCode();

		String beginKjlUsrId = panel.ctrlBeginUser.getValue();
		String endKjlUsrId = panel.ctrlEndUser.getValue();

		beginKjlUsrId = beginKjlUsrId.trim();
		endKjlUsrId = endKjlUsrId.trim();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		addSendValues("kaiCode", kaiCode);
		addSendValues("beginKjlUsrId", Util.avoidNull(beginKjlUsrId));
		addSendValues("endKjlUsrId", Util.avoidNull(endKjlUsrId));

		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return false;
		}

		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();

		Iterator recordIte = getResultList().iterator();
		if (!recordIte.hasNext()) {
			panel.ctrlBeginUser.getField().requestFocus();
			// 警告メッセージ表示
			showMessage(panel, "W00100");
			dataExists = false;
		}
		for (int row = 0; recordIte.hasNext(); row++) {
			Iterator dataIte = ((List) recordIte.next()).iterator();

			Vector<String> colum = new Vector<String>();

			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}
			String text = colum.get(4);
			colum.set(4, this.getWord("C01739") + text);
			colum.set(7, text);

			cells.add(row, colum);
		}

		panel.setDataList(cells);
		return dataExists;
	}
}
