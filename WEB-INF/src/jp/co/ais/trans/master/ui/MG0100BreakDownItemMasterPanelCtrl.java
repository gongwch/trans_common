package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.List;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;

/**
 * 内訳科目マスタ画面コントロール
 */
public class MG0100BreakDownItemMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	protected static final String PROGRAM_ID = "MG0100";

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "MG0100BreakDownItemMasterServlet";

	/** 新規と複写区分 */
	protected static int INSERT_KBN = 0;

	/** 編集区分 */
	protected static int UPD_KBN = 1;

	/** パネル */
	protected MG0100BreakDownItemMasterPanel panel;

	protected Map triCodeFlgMap;

	/** 内訳科目マスタ編集画面 */
	MG0100EditDisplayDialogCtrl dialog;

	/**
	 * コンストラクタ.
	 */

	public MG0100BreakDownItemMasterPanelCtrl() {
		try {
			// 一覧画面の初期化
			panel = new MG0100BreakDownItemMasterPanel(this);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
		// 開始コードと終了コードの初期化
		panel.ctrlSubItem.getField().setEditable(false);
		panel.ctrlBeginBreakDownItem.getField().setEditable(false);
		panel.ctrlEndBreakDownItem.getField().setEditable(false);

		panel.ctrlSubItem.getButton().setEnabled(false);
		panel.ctrlBeginBreakDownItem.getButton().setEnabled(false);
		panel.ctrlEndBreakDownItem.getButton().setEnabled(false);

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				init();
			}
		});
		// 言語の指定された名称を表示する
		triCodeFlgMap = new LinkedHashMap();
		triCodeFlgMap.put("0", getWord("C01279"));
		triCodeFlgMap.put("2", getWord("C00401"));
		triCodeFlgMap.put("3", getWord("C00203"));
		triCodeFlgMap.put("4", getWord("C02122"));

	}

	protected void init() {
		panel.ctrlItem.requestTextFocus();

	}

	/**
	 * (科目)
	 */
	public void setItemData() {
		String itemCode = Util.avoidNull(panel.ctrlItem.getValue());
		panel.ctrlItem.getInputParameter().setSubItemDivision("1");
		panel.ctrlItem.getInputParameter().setItemCode(itemCode);
	}

	/**
	 * (補助科目)
	 */
	public void setSubItemData() {
		// 会社コード
		panel.ctrlSubItem.getInputParameter().setItemCode(panel.ctrlItem.getValue());
		String subItemCode = Util.avoidNull(panel.ctrlSubItem.getValue());
		panel.ctrlSubItem.getInputParameter().setBreakDownItemDivision("1");
		panel.ctrlSubItem.getInputParameter().setSubItemCode(subItemCode);
	}

	/**
	 * (内訳科目開始)
	 */
	public void setBeginBreakDownItemData() {
		// 会社コード
		panel.ctrlBeginBreakDownItem.getInputParameter().setItemCode(panel.ctrlItem.getValue());
		panel.ctrlBeginBreakDownItem.getInputParameter().setSubItemCode(panel.ctrlSubItem.getValue());
		String beginBreakDownItemCode = Util.avoidNull(panel.ctrlBeginBreakDownItem.getValue());
		panel.ctrlBeginBreakDownItem.getInputParameter().setBreakDownItemCode(beginBreakDownItemCode);
		panel.ctrlBeginBreakDownItem.getInputParameter().setEndCode(panel.ctrlEndBreakDownItem.getValue());
	}

	/**
	 * (内訳科目終了)
	 */
	public void setEndBreakDownItemData() {
		// 会社コード
		panel.ctrlEndBreakDownItem.getInputParameter().setItemCode(panel.ctrlItem.getValue());
		panel.ctrlEndBreakDownItem.getInputParameter().setSubItemCode(panel.ctrlSubItem.getValue());
		String endBreakDownItemCode = Util.avoidNull(panel.ctrlEndBreakDownItem.getValue());
		panel.ctrlEndBreakDownItem.getInputParameter().setBreakDownItemCode(endBreakDownItemCode);
		panel.ctrlEndBreakDownItem.getInputParameter().setBeginCode(panel.ctrlBeginBreakDownItem.getValue());
	}

	/**
	 * パネル取得
	 * 
	 * @return 内訳科目マスタパネル
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
			createEditDisplayDialog("C01698");

			dialog.setTriCodeFlgMap(triCodeFlgMap);

			// 編集画面の表示
			dialog.show();

			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}

			// データが編集する
			modify(dialog.getDataList(), true);
			// 選択行保持する
			String kmkCode = dialog.getDataList().get("kmkCode").toString();
			String hkmCode = dialog.getDataList().get("hkmCode").toString();
			String ukmCode = dialog.getDataList().get("ukmCode").toString();
			modifySpreadRow(kmkCode, hkmCode, ukmCode, INSERT_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * ダイアログ生成
	 * 
	 * @param titleID
	 */
	protected void createEditDisplayDialog(String titleID) {
		dialog = new MG0100EditDisplayDialogCtrl(panel.getParentFrame(), titleID);
	}

	/**
	 * 編集処理
	 */
	void update() {
		try {
			createEditDisplayDialog("C00977");

			dialog.setTriCodeFlgMap(triCodeFlgMap);
			// 当前行を取得する
			int nomRow = panel.ssBreakDownItem.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssBreakDownItem.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 科目コードの設定
			map.put("kmkCode", model.getTableDataItem(nomRow, 1));
			// 補助科目コードの設定
			map.put("hkmCode", model.getTableDataItem(nomRow, 2));
			// 内訳科目コードの設定
			map.put("ukmCode", model.getTableDataItem(nomRow, 3));
			// 内訳科目名称の設定
			map.put("ukmName", model.getTableDataItem(nomRow, 4));
			// 内訳科目略称の設定
			map.put("ukmName_S", model.getTableDataItem(nomRow, 5));
			// 内訳科目検索名称の設定
			map.put("ukmName_K", model.getTableDataItem(nomRow, 6));
			// 消費税コードの設定
			map.put("zeiCode", model.getTableDataItem(nomRow, 7));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 33)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 34)));
			// 入金伝票入力ﾌﾗｸﾞの設定
			map.put("glFlg1", model.getTableDataItem(nomRow, 35));
			// 出金伝票入力ﾌﾗｸﾞの設定
			map.put("glFlg2", model.getTableDataItem(nomRow, 36));
			// 振替伝票入力ﾌﾗｸﾞの設定
			map.put("glFlg3", model.getTableDataItem(nomRow, 37));
			// 経費精算伝票入力ﾌﾗｸﾞの設定
			map.put("apFlg1", model.getTableDataItem(nomRow, 38));
			// 請求書伝票入力ﾌﾗｸﾞの設定
			map.put("apFlg2", model.getTableDataItem(nomRow, 39));
			// 債権計上伝票入力ﾌﾗｸﾞの設定
			map.put("arFlg1", model.getTableDataItem(nomRow, 40));
			// 債権消込伝票入力ﾌﾗｸﾞの設定
			map.put("arFlg2", model.getTableDataItem(nomRow, 41));
			// 資産計上伝票入力ﾌﾗｸﾞの設定
			map.put("faFlg1", model.getTableDataItem(nomRow, 42));
			// 支払依頼伝票入力ﾌﾗｸﾞの設定
			map.put("faFlg2", model.getTableDataItem(nomRow, 43));
			// 多通貨入力フラグの設定
			map.put("mcrFlg", model.getTableDataItem(nomRow, 44));
			// 評価替対象フラグの設定
			map.put("excFlg", model.getTableDataItem(nomRow, 45));
			// 取引先入力フラグの設定
			map.put("triCodeFlg", model.getTableDataItem(nomRow, 46));
			// 発生日入力ﾌﾗｸﾞの設定
			map.put("hasFlg", model.getTableDataItem(nomRow, 47));
			// 社員入力フラグの設定
			map.put("empCodeFlg", model.getTableDataItem(nomRow, 48));
			// 管理１-6入力フラグの設定
			map.put("knrFlg1", model.getTableDataItem(nomRow, 49));
			map.put("knrFlg2", model.getTableDataItem(nomRow, 50));
			map.put("knrFlg3", model.getTableDataItem(nomRow, 51));
			map.put("knrFlg4", model.getTableDataItem(nomRow, 52));
			map.put("knrFlg5", model.getTableDataItem(nomRow, 53));
			map.put("knrFlg6", model.getTableDataItem(nomRow, 54));
			// 非会計1-3入力フラグの設定
			map.put("hmFlg1", model.getTableDataItem(nomRow, 55));
			map.put("hmFlg2", model.getTableDataItem(nomRow, 56));
			map.put("hmFlg3", model.getTableDataItem(nomRow, 57));
			// 売上課税入力フラグの設定
			map.put("uriZeiFlg", model.getTableDataItem(nomRow, 58));
			// 仕入課税入力フラグの設定
			map.put("sirZeiFlg", model.getTableDataItem(nomRow, 59));
			// 処理種別の設定ﾞの設定
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
			String kmkCode = dialog.getDataList().get("kmkCode").toString();
			String hkmCode = dialog.getDataList().get("hkmCode").toString();
			String ukmCode = dialog.getDataList().get("ukmCode").toString();
			modifySpreadRow(kmkCode, hkmCode, ukmCode, UPD_KBN);
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

			dialog.setTriCodeFlgMap(triCodeFlgMap);
			// 当前行を取得する
			int nomRow = panel.ssBreakDownItem.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssBreakDownItem.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 科目コードの設定
			map.put("kmkCode", model.getTableDataItem(nomRow, 1));
			// 補助科目コードの設定
			map.put("hkmCode", model.getTableDataItem(nomRow, 2));
			// 内訳科目コードの設定
			map.put("ukmCode", model.getTableDataItem(nomRow, 3));
			// 内訳科目名称の設定
			map.put("ukmName", model.getTableDataItem(nomRow, 4));
			// 内訳科目略称の設定
			map.put("ukmName_S", model.getTableDataItem(nomRow, 5));
			// 内訳科目検索名称の設定
			map.put("ukmName_K", model.getTableDataItem(nomRow, 6));
			// 消費税コードの設定
			map.put("zeiCode", model.getTableDataItem(nomRow, 7));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 33)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 34)));
			// 入金伝票入力ﾌﾗｸﾞの設定
			map.put("glFlg1", model.getTableDataItem(nomRow, 35));
			// 出金伝票入力ﾌﾗｸﾞの設定
			map.put("glFlg2", model.getTableDataItem(nomRow, 36));
			// 振替伝票入力ﾌﾗｸﾞの設定
			map.put("glFlg3", model.getTableDataItem(nomRow, 37));
			// 経費精算伝票入力ﾌﾗｸﾞの設定
			map.put("apFlg1", model.getTableDataItem(nomRow, 38));
			// 請求書伝票入力ﾌﾗｸﾞの設定
			map.put("apFlg2", model.getTableDataItem(nomRow, 39));
			// 債権計上伝票入力ﾌﾗｸﾞの設定
			map.put("arFlg1", model.getTableDataItem(nomRow, 40));
			// 債権消込伝票入力ﾌﾗｸﾞの設定
			map.put("arFlg2", model.getTableDataItem(nomRow, 41));
			// 資産計上伝票入力ﾌﾗｸﾞの設定
			map.put("faFlg1", model.getTableDataItem(nomRow, 42));
			// 支払依頼伝票入力ﾌﾗｸﾞの設定
			map.put("faFlg2", model.getTableDataItem(nomRow, 43));
			// 多通貨入力フラグの設定
			map.put("mcrFlg", model.getTableDataItem(nomRow, 44));
			// 評価替対象フラグの設定
			map.put("excFlg", model.getTableDataItem(nomRow, 45));
			// 取引先入力フラグの設定
			map.put("triCodeFlg", model.getTableDataItem(nomRow, 46));
			// 発生日入力ﾌﾗｸﾞの設定
			map.put("hasFlg", model.getTableDataItem(nomRow, 47));
			// 社員入力フラグの設定
			map.put("empCodeFlg", model.getTableDataItem(nomRow, 48));
			// 管理１-6入力フラグの設定
			map.put("knrFlg1", model.getTableDataItem(nomRow, 49));
			map.put("knrFlg2", model.getTableDataItem(nomRow, 50));
			map.put("knrFlg3", model.getTableDataItem(nomRow, 51));
			map.put("knrFlg4", model.getTableDataItem(nomRow, 52));
			map.put("knrFlg5", model.getTableDataItem(nomRow, 53));
			map.put("knrFlg6", model.getTableDataItem(nomRow, 54));
			// 非会計1-3入力フラグの設定
			map.put("hmFlg1", model.getTableDataItem(nomRow, 55));
			map.put("hmFlg2", model.getTableDataItem(nomRow, 56));
			map.put("hmFlg3", model.getTableDataItem(nomRow, 57));
			// 売上課税入力フラグの設定
			map.put("uriZeiFlg", model.getTableDataItem(nomRow, 58));
			// 仕入課税入力フラグの設定
			map.put("sirZeiFlg", model.getTableDataItem(nomRow, 59));
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

			String kmkCode = dialog.getDataList().get("kmkCode").toString();
			String hkmCode = dialog.getDataList().get("hkmCode").toString();
			String ukmCode = dialog.getDataList().get("ukmCode").toString();
			modifySpreadRow(kmkCode, hkmCode, ukmCode, INSERT_KBN);

		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
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
		// 科目コード
		addSendValues("kmkCode", (String) map.get("kmkCode"));
		// 補助科目コード
		addSendValues("hkmCode", (String) map.get("hkmCode"));
		// 内訳科目コード
		addSendValues("ukmCode", (String) map.get("ukmCode"));
		// 内訳科目名称
		addSendValues("ukmName", (String) map.get("ukmName"));
		// 内訳科目略称
		addSendValues("ukmName_S", (String) map.get("ukmName_S"));
		// 内訳科目検索名称
		addSendValues("ukmName_K", (String) map.get("ukmName_K"));
		// 消費税コード
		addSendValues("zeiCode", (String) map.get("zeiCode"));
		// 取引先入力フラグ
		addSendValues("triCodeFlg", (String) map.get("triCodeFlg"));
		// 入金伝票入力ﾌﾗｸﾞ
		addSendValues("glFlg1", (String) map.get("glFlg1"));
		// 出金伝票入力ﾌﾗｸﾞ
		addSendValues("glFlg2", (String) map.get("glFlg2"));
		// 振替伝票入力ﾌﾗｸﾞ
		addSendValues("glFlg3", (String) map.get("glFlg3"));
		// 経費精算伝票入力ﾌﾗｸﾞ
		addSendValues("apFlg1", (String) map.get("apFlg1"));
		// 請求書伝票入力ﾌﾗｸﾞ
		addSendValues("apFlg2", (String) map.get("apFlg2"));
		// 債権計上伝票入力ﾌﾗｸﾞ
		addSendValues("arFlg1", (String) map.get("arFlg1"));
		// 債権消込伝票入力ﾌﾗｸﾞ
		addSendValues("arFlg2", (String) map.get("arFlg2"));
		// 資産計上伝票入力ﾌﾗｸﾞ
		addSendValues("faFlg1", (String) map.get("faFlg1"));
		// 支払依頼伝票入力ﾌﾗｸﾞ
		addSendValues("faFlg2", (String) map.get("faFlg2"));
		// 発生日入力ﾌﾗｸﾞ
		addSendValues("hasFlg", (String) map.get("hasFlg"));
		// 社員入力フラグ
		addSendValues("empCodeFlg", (String) map.get("empCodeFlg"));
		// 管理１-6入力フラグ
		addSendValues("knrFlg1", (String) map.get("knrFlg1"));
		addSendValues("knrFlg2", (String) map.get("knrFlg2"));
		addSendValues("knrFlg3", (String) map.get("knrFlg3"));
		addSendValues("knrFlg4", (String) map.get("knrFlg4"));
		addSendValues("knrFlg5", (String) map.get("knrFlg5"));
		addSendValues("knrFlg6", (String) map.get("knrFlg6"));
		// 非会計1-3入力フラグ
		addSendValues("hmFlg1", (String) map.get("hmFlg1"));
		addSendValues("hmFlg2", (String) map.get("hmFlg2"));
		addSendValues("hmFlg3", (String) map.get("hmFlg3"));
		// 売上課税入力フラグ
		addSendValues("uriZeiFlg", (String) map.get("uriZeiFlg"));
		// 仕入課税入力フラグ
		addSendValues("sirZeiFlg", (String) map.get("sirZeiFlg"));
		// 多通貨入力フラグ
		addSendValues("mcrFlg", (String) map.get("mcrFlg"));
		// 評価替対象フラグ
		addSendValues("excFlg", (String) map.get("excFlg"));
		// 開始年月日
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// 終了年月日
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// プログラムＩＤの設定の設定
		addSendValues("prgID", PROGRAM_ID);
		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
			// サーバ側のエラー場合
			errorHandler(panel.getParentFrame());
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param kmkCode
	 * @param hkmCode
	 * @param ukmCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String kmkCode, String hkmCode, String ukmCode, int updKbn)
		throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(kmkCode, hkmCode, ukmCode, updKbn);
		TTable ssPanelSpread = panel.ssBreakDownItem;
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
		lockBtn(panel.ssBreakDownItem.getDataSource().getNumRows() != 0);
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
	 * 新規、複写、編集の場合、スプレッド取得
	 * 
	 * @param kmkCode
	 * @param hkmCode
	 * @param ukmCode
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected Vector<Vector> setModifyCell(String kmkCode, String hkmCode, String ukmCode, int updKbn)
		throws IOException, TRequestException {
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 科目コードの設定
		addSendValues("kmkCode", Util.avoidNull(kmkCode));
		// 補助科目コードの設定
		addSendValues("hkmCode", Util.avoidNull(hkmCode));
		// 開始コードの設定
		addSendValues("beginUkmCode", Util.avoidNull(ukmCode));
		// 終了コードの設定
		addSendValues("endUkmCode", Util.avoidNull(ukmCode));
		// サーブレットの接続先
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssBreakDownItem.getDataSource();
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

			// 多通貨入力フラグ
			String mcrFlg = colum.get(37);
			// 評価替対象フラグ
			String excFlg = colum.get(38);
			// 取引先入力フラグ
			String hasFlg = colum.get(17);
			// 発生日入力ﾌﾗｸﾞ
			String triCodeFlg = colum.get(18);
			// 社員入力フラグ
			String empCodeFlg = colum.get(19);
			// 管理１-6入力フラグ
			String knrFlg1 = colum.get(20);
			String knrFlg2 = colum.get(21);
			String knrFlg3 = colum.get(22);
			String knrFlg4 = colum.get(23);
			String knrFlg5 = colum.get(24);
			String knrFlg6 = colum.get(25);
			// 非会計1-3入力フラグ
			String hmFlg1 = colum.get(26);
			String hmFlg2 = colum.get(27);
			String hmFlg3 = colum.get(28);
			// 売上課税入力フラグ
			String uriZeiFlg = colum.get(29);
			// 仕入課税入力フラグ
			String sirZeiFlg = colum.get(30);
			// 開始年月日
			String strDate = colum.get(31);
			// 終了年月日
			String endDate = colum.get(32);
			String value, text;
			String impossible = this.getWord("C01279");
			String possible = this.getWord("C01276");

			// 入金伝票入力ﾌﾗｸﾞ
			colum.add(35, "");
			value = colum.get(8);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(35, value);
			colum.set(8, text);

			// 出金伝票入力ﾌﾗｸﾞ
			colum.add(36, "");
			value = colum.get(9);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(36, value);
			colum.set(9, text);

			// 振替伝票入力ﾌﾗｸﾞ
			colum.add(37, "");
			value = colum.get(10);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(37, value);
			colum.set(10, text);

			// 経費精算伝票入力ﾌﾗｸﾞ
			colum.add(38, "");
			value = colum.get(11);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(38, value);
			colum.set(11, text);

			// 請求書伝票入力ﾌﾗｸﾞ
			colum.add(39, "");
			value = colum.get(12);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(39, value);
			colum.set(12, text);

			// 債権計上伝票入力ﾌﾗｸﾞ
			colum.add(40, "");
			value = colum.get(13);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(40, value);
			colum.set(13, text);

			// 債権消込伝票入力ﾌﾗｸﾞ
			colum.add(41, "");
			value = colum.get(14);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(41, value);
			colum.set(14, text);

			// 資産計上伝票入力ﾌﾗｸﾞ
			colum.add(42, "");
			value = colum.get(15);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(42, value);
			colum.set(15, text);

			// 支払依頼伝票入力ﾌﾗｸﾞ
			colum.add(43, "");
			value = colum.get(16);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(43, value);
			colum.set(16, text);

			// 多通貨入力フラグ
			colum.add(44, "");
			value = mcrFlg.toString();
			text = ("0".equals(value) ? impossible : possible);
			colum.set(44, value);
			colum.set(17, text);

			// 評価替対象フラグ
			colum.add(45, "");
			value = excFlg.toString();
			text = ("0".equals(value) ? impossible : possible);
			colum.set(45, value);
			colum.set(18, text);

			// 取引先入力フラグ
			colum.add(46, "");
			value = hasFlg;
			text = (String) triCodeFlgMap.get(value);
			colum.set(46, value);
			colum.set(19, text);

			// 発生日入力ﾌﾗｸﾞ
			colum.add(47, "");
			value = triCodeFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(47, value);
			colum.set(20, text);

			String possible1 = this.getWord("C02371");
			// 社員入力フラグ
			colum.add(48, "");
			value = empCodeFlg;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(48, value);
			colum.set(21, text);

			// 管理１入力フラグ
			colum.add(49, "");
			value = knrFlg1;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(49, value);
			colum.set(22, text);

			// 管理2入力フラグ
			colum.add(50, "");
			value = knrFlg2;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(50, value);
			colum.set(23, text);

			// 管理3入力フラグ
			colum.add(51, "");
			value = knrFlg3;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(51, value);
			colum.set(24, text);

			// 管理4入力フラグ
			colum.add(52, "");
			value = knrFlg4;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(52, value);
			colum.set(25, text);

			// 管理5入力フラグ
			colum.add(53, "");
			value = knrFlg5;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(53, value);
			colum.set(26, text);

			// 管理6入力フラグ
			colum.add(54, "");
			value = knrFlg6;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(54, value);
			colum.set(27, text);

			// 非会計1入力フラグ
			colum.add(55, "");
			value = hmFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(55, value);
			colum.set(28, text);

			// 非会計2入力フラグ
			colum.add(56, "");
			value = hmFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(56, value);
			colum.set(29, text);

			// 非会計3入力フラグ
			colum.add(57, "");
			value = hmFlg3;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(57, value);
			colum.set(30, text);

			// 売上課税入力フラグ
			colum.add(58, "");
			value = uriZeiFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(58, value);
			colum.set(31, text);

			// 仕入課税入力フラグ
			colum.add(59, "");
			value = sirZeiFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(59, value);
			colum.set(32, text);

			try {
				colum.set(33, DateUtil.toYMDString(DateUtil.toYMDDate(strDate)));
				colum.set(34, DateUtil.toYMDString(DateUtil.toYMDDate(endDate)));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(33), ex);
			}
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssBreakDownItem.getCurrentRow(), colum);
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
				int nomRow = panel.ssBreakDownItem.getCurrentRow();
				// データ集を取得する
				TableDataModel model = panel.ssBreakDownItem.getDataSource();
				// 会社コードの取得
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// 科目コードの取得
				String kmkCode = (String) model.getTableDataItem(nomRow, 1);
				// 補助科目コードの取得
				String hkmCode = (String) model.getTableDataItem(nomRow, 2);
				// 内訳科目コードの取得
				String ukmCode = (String) model.getTableDataItem(nomRow, 3);
				// 処理種別の設定
				addSendValues("flag", "delete");
				// 会社コードの設定
				addSendValues("kaiCode", kaiCode);
				// 科目コードの設定
				addSendValues("kmkCode", kmkCode);
				// 補助科目コードの設定
				addSendValues("hkmCode", hkmCode);
				// 内訳科目コードの設定
				addSendValues("ukmCode", ukmCode);

				// サーブレットの接続先
				if (!request(TARGET_SERVLET)) {
					errorHandler(panel.getParentFrame());
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
		int row = panel.ssBreakDownItem.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssBreakDownItem.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssBreakDownItem.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssBreakDownItem.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// 保持しておいた値をスクロールバーにセット
			panel.ssBreakDownItem.getVertSB().setValue(0);
			panel.ssBreakDownItem.getHorizSB().setValue(0);
		}
		// ボタンロック解除
		lockBtn(panel.ssBreakDownItem.getDataSource().getNumRows() != 0);
	}

	/**
	 * マスタ選択行状態の保持
	 * 
	 * @param row
	 */
	protected void selectSpreadRow(int row) {

		panel.ssBreakDownItem.setRowSelection(row, row);
		panel.ssBreakDownItem.setCurrentCell(row, 0);

	}

	/**
	 * 検索処理
	 * 
	 * @return true:正常
	 */
	boolean find() {
		String beginBreakDownItem = panel.ctrlBeginBreakDownItem.getValue().toString();
		String endBreakDownItem = panel.ctrlEndBreakDownItem.getValue().toString();

		try {

			if (Util.isSmallerThen(beginBreakDownItem, endBreakDownItem) == false) {
				// 警告メッセージ表示
				panel.ctrlBeginBreakDownItem.getField().requestFocus();
				showMessage(panel, "W00036", "C00024");
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
	 * @return true:正常
	 * @throws IOException
	 */
	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();
		// 科目コードの取得
		String kmkCode = panel.ctrlItem.getField().getText();
		// 補助科目コードの取得
		String hkmCode = panel.ctrlSubItem.getField().getText();
		// 開始コードの設定
		String beginUkmCode = panel.ctrlBeginBreakDownItem.getField().getText();
		// 終了コードの設定
		String endUkmCode = panel.ctrlEndBreakDownItem.getField().getText();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 科目コードの設定
		addSendValues("kmkCode", Util.avoidNull(kmkCode));
		// 補助科目コードの設定
		addSendValues("hkmCode", Util.avoidNull(hkmCode));
		// 開始コードの設定
		addSendValues("beginUkmCode", Util.avoidNull(beginUkmCode));
		// 終了コードの設定
		addSendValues("endUkmCode", Util.avoidNull(endUkmCode));

		// 送信
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel.getParentFrame());
			return false;
		}

		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();
		// 結果を取得する
		Iterator recordIte = getResultList().iterator();
		// 値があるかどうか
		if (!recordIte.hasNext()) {
			panel.ctrlBeginBreakDownItem.getField().requestFocus();
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

			// 多通貨入力フラグ
			String mcrFlg = colum.get(37);
			// 評価替対象フラグ
			String excFlg = colum.get(38);
			// 取引先入力フラグ
			String hasFlg = colum.get(17);
			// 発生日入力ﾌﾗｸﾞ
			String triCodeFlg = colum.get(18);
			// 社員入力フラグ
			String empCodeFlg = colum.get(19);
			// 管理１-6入力フラグ
			String knrFlg1 = colum.get(20);
			String knrFlg2 = colum.get(21);
			String knrFlg3 = colum.get(22);
			String knrFlg4 = colum.get(23);
			String knrFlg5 = colum.get(24);
			String knrFlg6 = colum.get(25);
			// 非会計1-3入力フラグ
			String hmFlg1 = colum.get(26);
			String hmFlg2 = colum.get(27);
			String hmFlg3 = colum.get(28);
			// 売上課税入力フラグ
			String uriZeiFlg = colum.get(29);
			// 仕入課税入力フラグ
			String sirZeiFlg = colum.get(30);
			// 開始年月日
			String strDate = colum.get(31);
			// 終了年月日
			String endDate = colum.get(32);
			String value, text;
			String impossible = this.getWord("C01279");
			String possible = this.getWord("C01276");

			// 入金伝票入力ﾌﾗｸﾞ
			colum.add(35, "");
			value = colum.get(8);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(35, value);
			colum.set(8, text);

			// 出金伝票入力ﾌﾗｸﾞ
			colum.add(36, "");
			value = colum.get(9);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(36, value);
			colum.set(9, text);

			// 振替伝票入力ﾌﾗｸﾞ
			colum.add(37, "");
			value = colum.get(10);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(37, value);
			colum.set(10, text);

			// 経費精算伝票入力ﾌﾗｸﾞ
			colum.add(38, "");
			value = colum.get(11);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(38, value);
			colum.set(11, text);

			// 請求書伝票入力ﾌﾗｸﾞ
			colum.add(39, "");
			value = colum.get(12);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(39, value);
			colum.set(12, text);

			// 債権計上伝票入力ﾌﾗｸﾞ
			colum.add(40, "");
			value = colum.get(13);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(40, value);
			colum.set(13, text);

			// 債権消込伝票入力ﾌﾗｸﾞ
			colum.add(41, "");
			value = colum.get(14);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(41, value);
			colum.set(14, text);

			// 資産計上伝票入力ﾌﾗｸﾞ
			colum.add(42, "");
			value = colum.get(15);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(42, value);
			colum.set(15, text);

			// 支払依頼伝票入力ﾌﾗｸﾞ
			colum.add(43, "");
			value = colum.get(16);
			text = ("0".equals(value) ? impossible : possible);
			colum.set(43, value);
			colum.set(16, text);

			// 多通貨入力フラグ
			colum.add(44, "");
			value = mcrFlg.toString();
			text = ("0".equals(value) ? impossible : possible);
			colum.set(44, value);
			colum.set(17, text);

			// 評価替対象フラグ
			colum.add(45, "");
			value = excFlg.toString();
			text = ("0".equals(value) ? impossible : possible);
			colum.set(45, value);
			colum.set(18, text);

			// 取引先入力フラグ
			colum.add(46, "");
			value = hasFlg;
			text = (String) triCodeFlgMap.get(value);
			colum.set(46, value);
			colum.set(19, text);

			// 発生日入力ﾌﾗｸﾞ
			colum.add(47, "");
			value = triCodeFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(47, value);
			colum.set(20, text);

			String possible1 = this.getWord("C02371");
			// 社員入力フラグ
			colum.add(48, "");
			value = empCodeFlg;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(48, value);
			colum.set(21, text);

			// 管理１入力フラグ
			colum.add(49, "");
			value = knrFlg1;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(49, value);
			colum.set(22, text);

			// 管理2入力フラグ
			colum.add(50, "");
			value = knrFlg2;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(50, value);
			colum.set(23, text);

			// 管理3入力フラグ
			colum.add(51, "");
			value = knrFlg3;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(51, value);
			colum.set(24, text);

			// 管理4入力フラグ
			colum.add(52, "");
			value = knrFlg4;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(52, value);
			colum.set(25, text);

			// 管理5入力フラグ
			colum.add(53, "");
			value = knrFlg5;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(53, value);
			colum.set(26, text);

			// 管理6入力フラグ
			colum.add(54, "");
			value = knrFlg6;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(54, value);
			colum.set(27, text);

			// 非会計1入力フラグ
			colum.add(55, "");
			value = hmFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(55, value);
			colum.set(28, text);

			// 非会計2入力フラグ
			colum.add(56, "");
			value = hmFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(56, value);
			colum.set(29, text);

			// 非会計3入力フラグ
			colum.add(57, "");
			value = hmFlg3;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(57, value);
			colum.set(30, text);

			// 売上課税入力フラグ
			colum.add(58, "");
			value = uriZeiFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(58, value);
			colum.set(31, text);

			// 仕入課税入力フラグ
			colum.add(59, "");
			value = sirZeiFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(59, value);
			colum.set(32, text);

			try {
				colum.set(33, DateUtil.toYMDString(DateUtil.toYMDDate(strDate)));
				colum.set(34, DateUtil.toYMDString(DateUtil.toYMDDate(endDate)));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(33), ex);
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
			panel.ctrlBeginBreakDownItem.getField().requestFocus();
			// 確認ダイアログ表示
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}
			Map conds = new HashMap();
			// 送信するパラメータを設定
			conds.put("flag", "report");
			// 会社コードの取得
			conds.put("kaiCode", getLoginUserCompanyCode());
			conds.put("kmkCode", panel.ctrlItem.getField().getText());
			// 補助科目コードの取得
			conds.put("hkmCode", panel.ctrlSubItem.getField().getText());
			// 開始コードの取得
			conds.put("beginUkmCode", panel.ctrlBeginBreakDownItem.getField().getText());
			// 終了コードの取得
			conds.put("endUkmCode", panel.ctrlEndBreakDownItem.getField().getText());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, TARGET_SERVLET, conds);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

}
