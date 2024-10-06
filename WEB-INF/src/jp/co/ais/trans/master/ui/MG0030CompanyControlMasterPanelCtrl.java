package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * 会社コントロールマスタ
 * 
 * @author yanwei
 */
public class MG0030CompanyControlMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	protected static final String PROGRAM_ID = "MG0030";

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0030CompanyControlMasterServlet";

	/** パネル */
	protected MG0030CompanyControlMasterPanel panel;

	protected Map cmpHmKbnMap;

	protected Map jidMap;

	/** 新規と複写区分 */
	protected static int INSERT_KBN = 0;

	/** 編集区分 */
	protected static int UPD_KBN = 1;

	/**
	 * コンストラクタ.
	 */
	public MG0030CompanyControlMasterPanelCtrl() {
		try {
			// 一覧画面の初期化
			initCtrl();
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		panel.btnCopy.setVisible(false);
		// 言語の指定された名称を表示する
		cmpHmKbnMap = new LinkedHashMap();
		cmpHmKbnMap.put("0", this.getWord("C00282"));
		cmpHmKbnMap.put("1", this.getWord("C02160"));
		cmpHmKbnMap.put("2", this.getWord("C02161"));
		cmpHmKbnMap.put("3", this.getWord("C00446"));

		// 言語の指定された名称を表示する
		jidMap = new LinkedHashMap();
		jidMap.put("0", this.getWord("C00412"));
		jidMap.put("1", this.getWord("C02162"));
		jidMap.put("2", this.getWord("C02163"));
		jidMap.put("3", this.getWord("C02164"));
		jidMap.put("4", this.getWord("C02165"));
		jidMap.put("5", this.getWord("C00528"));
		jidMap.put("6", this.getWord("C00467"));
		jidMap.put("7", this.getWord("C00980"));
		jidMap.put("8", this.getWord("C00596"));

		try {
			reflesh();
		} catch (Exception ex) {
			errorHandler(panel.getParentFrame(), ex, "E00010");
		}

	}

	/**
	 * コントロール構成
	 */
	public void initCtrl() {
		// 一覧画面の初期化
		panel = new MG0030CompanyControlMasterPanel(this);
	}

	/**
	 * パネル取得
	 * 
	 * @return 会社コントロールマスタパネル
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * 編集処理
	 */
	void update() {
		try {
			// 編集画面の初期化
			MG0030EditDisplayDialogCtrl dialog = new MG0030EditDisplayDialogCtrl(panel.getParentFrame(), "C00977");
			dialog.setSelectMap(cmpHmKbnMap, jidMap);
			// 当前行を取得する
			int nomRow = panel.ssCompanyCodeRoleList.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssCompanyCodeRoleList.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 会社コードの設定
			map.put("kaiCode", model.getTableDataItem(nomRow, 0));
			// 会社コード の設定
			map.put("cmpKmkName", model.getTableDataItem(nomRow, 1));
			// 補助科目名称 の設定
			map.put("cmpHkmName", model.getTableDataItem(nomRow, 2));
			// 内訳科目管理の設定
			map.put("cmpUkmKbn", model.getTableDataItem(nomRow, 36));
			// 内訳科目名称の設定
			map.put("cmpUkmName", model.getTableDataItem(nomRow, 4));
			// 管理区分1-6の設定
			map.put("knrKbn1", model.getTableDataItem(nomRow, 37));
			map.put("knrKbn2", model.getTableDataItem(nomRow, 38));
			map.put("knrKbn3", model.getTableDataItem(nomRow, 39));
			map.put("knrKbn4", model.getTableDataItem(nomRow, 40));
			map.put("knrKbn5", model.getTableDataItem(nomRow, 41));
			map.put("knrKbn6", model.getTableDataItem(nomRow, 42));
			// 管理名称1-6の設定
			map.put("knrName1", model.getTableDataItem(nomRow, 11));
			map.put("knrName2", model.getTableDataItem(nomRow, 12));
			map.put("knrName3", model.getTableDataItem(nomRow, 13));
			map.put("knrName4", model.getTableDataItem(nomRow, 14));
			map.put("knrName5", model.getTableDataItem(nomRow, 15));
			map.put("knrName6", model.getTableDataItem(nomRow, 16));
			// 非会計明細区分1-3の設定
			map.put("cmpHmKbn1", model.getTableDataItem(nomRow, 43));
			map.put("cmpHmKbn2", model.getTableDataItem(nomRow, 44));
			map.put("cmpHmKbn3", model.getTableDataItem(nomRow, 45));
			// 非会計明細名称1-3の設定
			map.put("cmpHmName1", model.getTableDataItem(nomRow, 20));
			map.put("cmpHmName2", model.getTableDataItem(nomRow, 21));
			map.put("cmpHmName3", model.getTableDataItem(nomRow, 22));
			// 期首月の設定
			map.put("cmpKisyu", model.getTableDataItem(nomRow, 23));
			// 自動設定項目１-3の設定
			map.put("jid1", model.getTableDataItem(nomRow, 46));
			map.put("jid2", model.getTableDataItem(nomRow, 47));
			map.put("jid3", model.getTableDataItem(nomRow, 48));
			// 自動採番部桁数の設定
			map.put("autoNoKeta", model.getTableDataItem(nomRow, 27));
			// 伝票印刷区分の設定
			map.put("printKbn", model.getTableDataItem(nomRow, 49));
			// 印刷時の初期値の設定
			map.put("printDef", model.getTableDataItem(nomRow, 50));
			// 現場承認ﾌﾗｸﾞの設定
			map.put("cmpGShoninFlg", model.getTableDataItem(nomRow, 51));
			// 経理承認ﾌﾗｸﾞの設定
			map.put("cmpKShoninFlg", model.getTableDataItem(nomRow, 52));
			// 本邦通貨コードの設定
			map.put("curCode", model.getTableDataItem(nomRow, 32));
			// レート換算端数処理の設定
			map.put("rateKbn", model.getTableDataItem(nomRow, 53));
			// 仮受消費税端数処理の設定
			map.put("kuKbn", model.getTableDataItem(nomRow, 54));
			// 仮払消費税端数処理の設定
			map.put("kbKbn", model.getTableDataItem(nomRow, 55));
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
			modify(dialog.getDataList());
			// 表示データの取得
			// スプレッド更新
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			modifySpreadRow(kaiCode, UPD_KBN);
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
			MG0030EditDisplayDialogCtrl dialog = new MG0030EditDisplayDialogCtrl(panel.getParentFrame(), "C01738");
			dialog.setSelectMap(cmpHmKbnMap, jidMap);
			// 当前行を取得する
			int nomRow = panel.ssCompanyCodeRoleList.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssCompanyCodeRoleList.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 会社コード の設定
			map.put("kaiCode", model.getTableDataItem(nomRow, 0));
			// 科目名称の設定
			map.put("cmpKmkName", model.getTableDataItem(nomRow, 1));
			// 補助科目名称の設定
			map.put("cmpHkmName", model.getTableDataItem(nomRow, 2));
			// 内訳科目管理の設定
			map.put("cmpUkmKbn", model.getTableDataItem(nomRow, 36));
			// 内訳科目名称の設定
			map.put("cmpUkmName", model.getTableDataItem(nomRow, 4));
			// 管理区分1-6の設定
			map.put("knrKbn1", model.getTableDataItem(nomRow, 37));
			map.put("knrKbn2", model.getTableDataItem(nomRow, 38));
			map.put("knrKbn3", model.getTableDataItem(nomRow, 39));
			map.put("knrKbn4", model.getTableDataItem(nomRow, 40));
			map.put("knrKbn5", model.getTableDataItem(nomRow, 41));
			map.put("knrKbn6", model.getTableDataItem(nomRow, 42));
			// 管理名称1-6の設定
			map.put("knrName1", model.getTableDataItem(nomRow, 11));
			map.put("knrName2", model.getTableDataItem(nomRow, 12));
			map.put("knrName3", model.getTableDataItem(nomRow, 13));
			map.put("knrName4", model.getTableDataItem(nomRow, 14));
			map.put("knrName5", model.getTableDataItem(nomRow, 15));
			map.put("knrName6", model.getTableDataItem(nomRow, 16));
			// 非会計明細区分1-3の設定
			map.put("cmpHmKbn1", model.getTableDataItem(nomRow, 43));
			map.put("cmpHmKbn2", model.getTableDataItem(nomRow, 44));
			map.put("cmpHmKbn3", model.getTableDataItem(nomRow, 45));
			// 非会計明細名称1-3の設定
			map.put("cmpHmName1", model.getTableDataItem(nomRow, 20));
			map.put("cmpHmName2", model.getTableDataItem(nomRow, 21));
			map.put("cmpHmName3", model.getTableDataItem(nomRow, 22));
			// 期首月の設定
			map.put("cmpKisyu", model.getTableDataItem(nomRow, 23));
			// 自動設定項目１-3の設定
			map.put("jid1", model.getTableDataItem(nomRow, 46));
			map.put("jid2", model.getTableDataItem(nomRow, 47));
			map.put("jid3", model.getTableDataItem(nomRow, 48));
			// 自動採番部桁数の設定
			map.put("autoNoKeta", model.getTableDataItem(nomRow, 27));
			// 伝票印刷区分の設定
			map.put("printKbn", model.getTableDataItem(nomRow, 49));
			// 印刷時の初期値の設定
			map.put("printDef", model.getTableDataItem(nomRow, 50));
			// 現場承認ﾌﾗｸﾞの設定
			map.put("cmpGShoninFlg", model.getTableDataItem(nomRow, 51));
			// 経理承認ﾌﾗｸﾞの設定
			map.put("cmpKShoninFlg", model.getTableDataItem(nomRow, 52));
			// 本邦通貨コードの設定
			map.put("curCode", model.getTableDataItem(nomRow, 32));
			// レート換算端数処理の設定
			map.put("rateKbn", model.getTableDataItem(nomRow, 53));
			// 仮受消費税端数処理の設定
			map.put("kuKbn", model.getTableDataItem(nomRow, 54));
			// 仮払消費税端数処理の設定
			map.put("kbKbn", model.getTableDataItem(nomRow, 55));
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
			modify(dialog.getDataList());
			// スプレッド更新
			String kaiCode = dialog.getDataList().get("kaiCode").toString();
			modifySpreadRow(kaiCode, INSERT_KBN);

		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
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
		addSendValues("flag", "save");
		// 会社コード の設定
		addSendValues("kaiCode", (String) map.get("kaiCode"));
		// 科目名称の設定
		addSendValues("cmpKmkName", (String) map.get("cmpKmkName"));
		// 補助科目名称の設定
		addSendValues("cmpHkmName", (String) map.get("cmpHkmName"));
		// 内訳科目管理の設定
		addSendValues("cmpUkmName", (String) map.get("cmpUkmName"));
		// 内訳科目名称の設定
		addSendValues("cmpUkmKbn", (String) map.get("cmpUkmKbn"));
		// 管理区分1-6の設定
		addSendValues("knrKbn1", (String) map.get("knrKbn1"));
		addSendValues("knrKbn2", (String) map.get("knrKbn2"));
		addSendValues("knrKbn3", (String) map.get("knrKbn3"));
		addSendValues("knrKbn4", (String) map.get("knrKbn4"));
		addSendValues("knrKbn5", (String) map.get("knrKbn5"));
		addSendValues("knrKbn6", (String) map.get("knrKbn6"));
		// 管理名称1-6の設定
		addSendValues("knrName1", (String) map.get("knrName1"));
		addSendValues("knrName2", (String) map.get("knrName2"));
		addSendValues("knrName3", (String) map.get("knrName3"));
		addSendValues("knrName4", (String) map.get("knrName4"));
		addSendValues("knrName5", (String) map.get("knrName5"));
		addSendValues("knrName6", (String) map.get("knrName6"));
		// 非会計明細区分1-3の設定
		addSendValues("cmpHmKbn1", (String) map.get("cmpHmKbn1"));
		addSendValues("cmpHmKbn2", (String) map.get("cmpHmKbn2"));
		addSendValues("cmpHmKbn3", (String) map.get("cmpHmKbn3"));
		// 非会計明細名称1-3の設定
		addSendValues("cmpHmName1", (String) map.get("cmpHmName1"));
		addSendValues("cmpHmName2", (String) map.get("cmpHmName2"));
		addSendValues("cmpHmName3", (String) map.get("cmpHmName3"));
		// 期首月の設定
		addSendValues("cmpKisyu", (String) map.get("cmpKisyu"));
		// 自動設定項目１-3の設定
		addSendValues("jid1", (String) map.get("jid1"));
		addSendValues("jid2", (String) map.get("jid2"));
		addSendValues("jid3", (String) map.get("jid3"));
		// 自動採番部桁数の設定
		addSendValues("autoNoKeta", (String) map.get("autoNoKeta"));
		// 伝票印刷区分の設定
		addSendValues("printKbn", (String) map.get("printKbn"));
		// 印刷時の初期値の設定
		addSendValues("printDef", (String) map.get("printDef"));
		// 現場承認ﾌﾗｸﾞの設定
		addSendValues("cmpGShoninFlg", (String) map.get("cmpGShoninFlg"));
		// 経理承認ﾌﾗｸﾞの設定
		addSendValues("cmpKShoninFlg", (String) map.get("cmpKShoninFlg"));
		// 本邦通貨コードの設定
		addSendValues("curCode", (String) map.get("curCode"));
		// レート換算端数処理の設定
		addSendValues("rateKbn", (String) map.get("rateKbn"));
		// 仮受消費税端数処理の設定
		addSendValues("kuKbn", (String) map.get("kuKbn"));
		// 仮払消費税端数処理の設定
		addSendValues("kbKbn", (String) map.get("kbKbn"));
		// プログラムＩＤの設定
		addSendValues("prgID", PROGRAM_ID);
		if (!request(TARGET_SERVLET)) {
			// サーバ側のエラー場合
			errorHandler(panel);
		}
	}

	/**
	 * 削除処理
	 */
	void delete() {
		if (this.showConfirmMessage(panel, "Q00007", "")) {
			// 選択されている行の1つ目と2つ目のカラムを取得
			int nomRow = panel.ssCompanyCodeRoleList.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssCompanyCodeRoleList.getDataSource();
			// 会社コードの取得
			String kaiCode = (String) model.getTableDataItem(nomRow, 0);

			try {
				// 処理種別の設定
				addSendValues("flag", "delete");
				// 会社コードの設定
				addSendValues("kaiCode", kaiCode);

				if (!request(TARGET_SERVLET)) {
					// サーバ側のエラー場合
					errorHandler(panel);
				}

				// 表示データの取得
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

		// 送信するパラメータを設定
		addSendValues("flag", "find");

		if (!request(TARGET_SERVLET)) {
			// サーバ側のエラー場合
			errorHandler(panel);
		}

		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();
		// 結果を取得する
		Iterator recordIte = getResultList().iterator();
		// 値があるかどうか
		if (!recordIte.hasNext()) {
			showMessage(panel, "W00084");
			dataExists = false;
		}

		for (int row = 0; recordIte.hasNext(); row++) {
			Iterator dataIte = ((List) recordIte.next()).iterator();

			Vector<String> colum = new Vector<String>();

			for (int column = 0; dataIte.hasNext(); column++) {
				colum.add(column, (String) dataIte.next());
			}
			colum.remove(colum.size() - 1);

			String impossible = this.getWord("C00282");
			String possible = this.getWord("C00281");
			String value, text;

			// 内訳科目管理
			value = colum.get(3);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(36, value);
			colum.set(3, text);

			// 管理区分1
			value = colum.get(5);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(37, value);
			colum.set(5, text);

			// 管理区分2
			value = colum.get(6);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(38, value);
			colum.set(6, text);

			// 管理区分3
			value = colum.get(7);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(39, value);
			colum.set(7, text);

			// 管理区分4
			value = colum.get(8);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(40, value);
			colum.set(8, text);

			// 管理区分5
			value = colum.get(9);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(41, value);
			colum.set(9, text);

			// 管理区分6
			value = colum.get(10);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(42, value);
			colum.set(10, text);

			// 非会計明細区分1
			value = colum.get(17);
			text = (String) cmpHmKbnMap.get(value);
			colum.add(43, value);
			colum.set(17, text);

			// 非会計明細区分2
			value = colum.get(18);
			text = (String) cmpHmKbnMap.get(value);
			colum.add(44, value);
			colum.set(18, text);

			// 非会計明細区分3
			value = colum.get(19);
			text = (String) cmpHmKbnMap.get(value);
			colum.add(45, value);
			colum.set(19, text);

			// 自動設定項目１
			value = colum.get(24);
			text = (String) jidMap.get(value);
			colum.add(46, value);
			colum.set(24, text);

			// 自動設定項目2
			value = colum.get(25);
			text = (String) jidMap.get(value);
			colum.add(47, value);
			colum.set(25, text);

			// 自動設定項目3
			value = colum.get(26);
			text = (String) jidMap.get(value);
			colum.add(48, value);
			colum.set(26, text);

			value = colum.get(27);
			colum.set(27, "0".equals(value) ? "" : value);

			// 伝票印刷区分
			value = colum.get(28);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(49, value);
			colum.set(28, text);

			// 印刷時の初期値
			value = colum.get(29);
			text = ("0".equals(value) ? this.getWord("C02367") : this.getWord("C02368"));
			colum.add(50, value);
			colum.set(29, text);

			String impossible1 = this.getWord("C02099");
			String possible1 = this.getWord("C02100");

			// 現場承認ﾌﾗｸﾞ
			value = colum.get(30);
			text = ("0".equals(value) ? impossible1 : possible1);
			colum.add(51, value);
			colum.set(30, text);

			// 経理承認ﾌﾗｸﾞ
			value = colum.get(31);
			text = ("0".equals(value) ? impossible1 : possible1);
			colum.add(52, value);
			colum.set(31, text);

			// レート換算端数処理
			value = colum.get(33);
			text = ("0".equals(value) ? this.getWord("C00121") : this.getWord("C00215"));
			colum.add(53, value);
			colum.set(33, text);

			// 仮受消費税端数処理
			value = colum.get(34);
			if ("0".equals(value)) {
				text = this.getWord("C00121");
			} else if ("1".equals(value)) {
				text = this.getWord("C00120");
			} else {
				text = this.getWord("C00215");
			}
			colum.add(54, value);
			colum.set(34, text);

			// 仮払消費税端数処理
			value = colum.get(35);
			text = ("0".equals(value) ? this.getWord("C00121") : this.getWord("C00215"));
			colum.add(55, value);
			colum.set(35, text);
			cells.add(row, colum);
		}

		panel.setDataList(cells);

		// データ集を取得する
		TableDataModel model = panel.ssCompanyCodeRoleList.getDataSource();

		if (model.getNumRows() > 0) {
			String kaiCode = (String) model.getTableDataItem(0, 0);
			panel.btnDelete.setEnabled(checkCode(kaiCode));
		}

		return dataExists;
	}

	/**
	 * 
	 */
	public void isDeleteAbled() {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				// 当前行を取得する
				int nomRow = panel.ssCompanyCodeRoleList.getCurrentRow();
				// データ集を取得する
				TableDataModel model = panel.ssCompanyCodeRoleList.getDataSource();
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				panel.btnDelete.setEnabled(checkCode(kaiCode));
			}
		});
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
			this.download(panel, TARGET_SERVLET, conds);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	boolean checkCode(String kaiCode) {
		try {// 会社コード未入力
			if (Util.isNullOrEmpty(kaiCode)) {
				return false;
			}
			// 処理種別の設定
			addSendValues("flag", "checkcode");
			addSendValues("kaiCode", kaiCode);

			if (!request(TARGET_SERVLET)) {
				// サーバ側のエラー場合
				errorHandler(panel);
			}

			// 結果を取得
			List result = super.getResultList();
			// 結果を返す
			return (result.size() > 0);
		} catch (IOException e) {
			errorHandler(panel, e, "E00009");
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
		TTable ssPanelSpread = panel.ssCompanyCodeRoleList;
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
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
		// 結果を取得する
		List<List<String>> resultList = getResultList();

		// 値があるかどうか
		if (resultList == null || resultList.isEmpty()) {
			showMessage(panel, "W00100");
		}

		for (List<String> list : resultList) {
			list.remove(list.size() - 1);
		}

		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssCompanyCodeRoleList.getDataSource();
		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = modifyDs.getCells();
		// columの初期化
		Vector<String> colum = new Vector<String>();
		for (List<String> list : resultList) {

			for (String value : list) {
				// 結果を追加する
				colum.add(value);
			}

			String impossible = this.getWord("C00282");
			String possible = this.getWord("C00281");
			String value, text;

			// 内訳科目管理
			value = colum.get(3);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(36, value);
			colum.set(3, text);

			// 管理区分1
			value = colum.get(5);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(37, value);
			colum.set(5, text);

			// 管理区分2
			value = colum.get(6);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(38, value);
			colum.set(6, text);

			// 管理区分3
			value = colum.get(7);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(39, value);
			colum.set(7, text);

			// 管理区分4
			value = colum.get(8);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(40, value);
			colum.set(8, text);

			// 管理区分5
			value = colum.get(9);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(41, value);
			colum.set(9, text);

			// 管理区分6
			value = colum.get(10);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(42, value);
			colum.set(10, text);

			// 非会計明細区分1
			value = colum.get(17);
			text = (String) cmpHmKbnMap.get(value);
			colum.add(43, value);
			colum.set(17, text);

			// 非会計明細区分2
			value = colum.get(18);
			text = (String) cmpHmKbnMap.get(value);
			colum.add(44, value);
			colum.set(18, text);

			// 非会計明細区分3
			value = colum.get(19);
			text = (String) cmpHmKbnMap.get(value);
			colum.add(45, value);
			colum.set(19, text);

			// 自動設定項目１
			value = colum.get(24);
			text = (String) jidMap.get(value);
			colum.add(46, value);
			colum.set(24, text);

			// 自動設定項目2
			value = colum.get(25);
			text = (String) jidMap.get(value);
			colum.add(47, value);
			colum.set(25, text);

			// 自動設定項目3
			value = colum.get(26);
			text = (String) jidMap.get(value);
			colum.add(48, value);
			colum.set(26, text);

			value = colum.get(27);
			colum.set(27, "0".equals(value) ? "" : value);

			// 伝票印刷区分
			value = colum.get(28);
			text = ("0".equals(value) ? impossible : possible);
			colum.add(49, value);
			colum.set(28, text);

			// 印刷時の初期値
			value = colum.get(29);
			text = ("0".equals(value) ? this.getWord("C02367") : this.getWord("C02368"));
			colum.add(50, value);
			colum.set(29, text);

			String impossible1 = this.getWord("C02099");
			String possible1 = this.getWord("C02100");

			// 現場承認ﾌﾗｸﾞ
			value = colum.get(30);
			text = ("0".equals(value) ? impossible1 : possible1);
			colum.add(51, value);
			colum.set(30, text);

			// 経理承認ﾌﾗｸﾞ
			value = colum.get(31);
			text = ("0".equals(value) ? impossible1 : possible1);
			colum.add(52, value);
			colum.set(31, text);

			// レート換算端数処理
			value = colum.get(33);
			text = ("0".equals(value) ? this.getWord("C00121") : this.getWord("C00215"));
			colum.add(53, value);
			colum.set(33, text);

			// 仮受消費税端数処理
			value = colum.get(34);
			if ("0".equals(value)) {
				text = this.getWord("C00121");
			} else if ("1".equals(value)) {
				text = this.getWord("C00120");
			} else {
				text = this.getWord("C00215");
			}
			colum.add(54, value);
			colum.set(34, text);

			// 仮払消費税端数処理
			value = colum.get(35);
			text = ("0".equals(value) ? this.getWord("C00121") : this.getWord("C00215"));
			colum.add(55, value);
			colum.set(35, text);
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssCompanyCodeRoleList.getCurrentRow(), colum);
		}
		return cells;
	}

	/**
	 * マスタ選択行状態の保持
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {
		panel.ssCompanyCodeRoleList.setRowSelection(row, row);
		panel.ssCompanyCodeRoleList.setCurrentCell(row, 0);
	}

}
