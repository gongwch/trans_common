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
 * 取引先マスタ画面コントロール
 * 
 * @author mayongliang
 */

public class MG0150CustomerMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	protected static final String PROGRAM_ID = "MG0150";

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
		return "MG0150CustomerMasterServlet";
	}

	/** パネル */
	protected MG0150CustomerMasterPanel panel;

	private REFDialogCtrl refBeginCustomer;

	private REFDialogCtrl refEndCustomer;

	/**
	 * コンストラクタ.
	 */
	public MG0150CustomerMasterPanelCtrl() {
		try {
			// 一覧画面の初期化
			panel = new MG0150CustomerMasterPanel(this);
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
		refBeginCustomer.setTargetServlet(getServletName());
		refBeginCustomer.setTitleID("C02326");
		refBeginCustomer.setColumnLabels("C00786", "C00787", "C00836");
		refBeginCustomer.setShowsMsgOnError(false);
		refBeginCustomer.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 銀行コードの設定
				keys.put("kind", "Customer");
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.ctrlEndCustomer.getField().getText());
				// データを返す
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
		refEndCustomer.setTargetServlet(getServletName());
		refEndCustomer.setTitleID("C02326");
		refEndCustomer.setColumnLabels("C00786", "C00787", "C00836");
		refEndCustomer.setShowsMsgOnError(false);
		refEndCustomer.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 銀行コードの設定
				keys.put("kind", "Customer");
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.ctrlBeginCustomer.getField().getText());
				// データを返す
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
		return this.panel;
	}

	/**
	 * 新規登録処理
	 */
	void insert() {
		try {
			// 編集画面の初期化
			MG0150EditDisplayDialogCtrl dialog = new MG0150EditDisplayDialogCtrl(panel.getParentFrame(), "C01698");
			// 編集画面の表示
			dialog.show();
			// 編集画面が開けていました
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), "insert");
			// 選択行保持する
			modifySpreadRow(dialog.getDataList().get("triCode").toString(), INSERT_KBN);
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
			MG0150EditDisplayDialogCtrl dialog = new MG0150EditDisplayDialogCtrl(panel.getParentFrame(), "C00977");
			// 当前行を取得する
			int nomRow = panel.ssCustomer.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssCustomer.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 取引先コードの設定
			map.put("triCode", model.getTableDataItem(nomRow, 1));
			// 取引先名称の設定
			map.put("triName", model.getTableDataItem(nomRow, 2));
			// 取引先略称の設定
			map.put("triName_S", model.getTableDataItem(nomRow, 3));
			// 取引先検索名称の設定
			map.put("triName_K", model.getTableDataItem(nomRow, 4));
			// 郵便番号の設定
			map.put("zip", model.getTableDataItem(nomRow, 6));
			// 住所カナの設定
			map.put("jyuKana", model.getTableDataItem(nomRow, 7));
			// 住所１の設定
			map.put("jyu1", model.getTableDataItem(nomRow, 8));
			// 住所２の設定
			map.put("jyu2", model.getTableDataItem(nomRow, 9));
			// 電話番号の設定
			map.put("tel", model.getTableDataItem(nomRow, 10));
			// FAX番号の設定
			map.put("fax", model.getTableDataItem(nomRow, 11));
			// 集計相手先コードの設定
			map.put("sumCode", model.getTableDataItem(nomRow, 12));
			// 仕入先区分の設定
			map.put("siireKbn", model.getTableDataItem(nomRow, 25));
			// 得意先区分の設定
			map.put("tokuiKbn", model.getTableDataItem(nomRow, 26));
			// 入金条件締め日の設定
			map.put("njCDate", model.getTableDataItem(nomRow, 15));
			// 入金条件締め後月の設定
			map.put("njRMon", model.getTableDataItem(nomRow, 16));
			// 入金条件入金日の設定
			map.put("njPDate", model.getTableDataItem(nomRow, 17));
			// 入金銀行口座ｺｰﾄﾞの設定
			map.put("nknCbkCode", model.getTableDataItem(nomRow, 18));
			// 取引形態区分の設定
			map.put("triKbn", model.getTableDataItem(nomRow, 27));
			// スポット伝票番号の設定
			map.put("spotDenNo", model.getTableDataItem(nomRow, 21));
			// 事業所名称の設定
			map.put("jigName", model.getTableDataItem(nomRow, 5));
			// 振込依頼人名の設定
			map.put("iraiName", model.getTableDataItem(nomRow, 20));
			// 入金手数料区分の設定
			map.put("nyuTesuKbn", model.getTableDataItem(nomRow, 28));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 23)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 24)));
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
			modify(dialog.getDataList(), "update");
			// スプレッド更新
			modifySpreadRow(dialog.getDataList().get("triCode").toString(), UPD_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * spot処理
	 */
	public void spot() {
		try {
			// SPOT取引であるか否か
			boolean bol = checkTriKbn();
			// SPOT取引でない場合
			if (!bol) {
				// 警告メッセージ表示
				showMessage(panel, "W00210");
				return;
			}

			MG0150EditDisplayDialogCtrl dialog = new MG0150EditDisplayDialogCtrl(panel.getParentFrame(), "C02425");
			// 当前行を取得する
			int nomRow = panel.ssCustomer.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssCustomer.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 取引先コードの設定
			map.put("triCode", model.getTableDataItem(nomRow, 1));
			// 取引先名称の設定
			map.put("triName", model.getTableDataItem(nomRow, 2));
			// 取引先略称の設定
			map.put("triName_S", model.getTableDataItem(nomRow, 3));
			// 取引先検索名称の設定
			map.put("triName_K", model.getTableDataItem(nomRow, 4));
			// 郵便番号の設定
			map.put("zip", model.getTableDataItem(nomRow, 6));
			// 住所カナの設定
			map.put("jyuKana", model.getTableDataItem(nomRow, 7));
			// 住所１の設定
			map.put("jyu1", model.getTableDataItem(nomRow, 8));
			// 住所２の設定
			map.put("jyu2", model.getTableDataItem(nomRow, 9));
			// 電話番号の設定
			map.put("tel", model.getTableDataItem(nomRow, 10));
			// FAX番号の設定
			map.put("fax", model.getTableDataItem(nomRow, 11));
			// 集計相手先コードの設定
			map.put("sumCode", model.getTableDataItem(nomRow, 12));
			// 仕入先区分の設定
			map.put("siireKbn", model.getTableDataItem(nomRow, 25));
			// 得意先区分の設定
			map.put("tokuiKbn", model.getTableDataItem(nomRow, 26));
			// 入金条件締め日の設定
			map.put("njCDate", model.getTableDataItem(nomRow, 15));
			// 入金条件締め日の設定
			map.put("njRMon", model.getTableDataItem(nomRow, 16));
			// 入金条件入金日の設定
			map.put("njPDate", model.getTableDataItem(nomRow, 17));
			// 入金銀行口座ｺｰﾄﾞの設定
			map.put("nknCbkCode", model.getTableDataItem(nomRow, 18));
			// 取引形態区分の設定
			map.put("triKbn", "00");
			// スポット伝票番号の設定
			map.put("spotDenNo", model.getTableDataItem(nomRow, 21));
			// 事業所名称の設定
			map.put("jigName", model.getTableDataItem(nomRow, 5));
			// 振込依頼人名の設定
			map.put("iraiName", model.getTableDataItem(nomRow, 20));
			// 入金手数料区分の設定
			map.put("nyuTesuKbn", model.getTableDataItem(nomRow, 28));
			// 開始年月日の設定
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 23)));
			// 終了年月日の設定
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 24)));
			// 処理種別の設定
			map.put("flag", "spot");

			// 取引コードをセット
			dialog.setTriCode((String) model.getTableDataItem(nomRow, 1));

			// データを画面に設定するﾞ
			dialog.setValues(map);
			// 編集画面を表示するﾞ
			dialog.show();
			// 編集画面が開けていましたﾞ
			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), "spot");
			// 表示データの取得
			this.reflesh();
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 取引形態区分チェック
	 * 
	 * @return true: SPOT取引先である false:SPOT取引先ではない
	 * @throws IOException
	 */
	private boolean checkTriKbn() throws IOException {
		// 当前行を取得する
		int nomRow = panel.ssCustomer.getCurrentRow();
		// データ集を取得する
		TableDataModel model = panel.ssCustomer.getDataSource();

		String triCode = (String) model.getTableDataItem(nomRow, 1);

		// 処理種別の設定
		addSendValues("flag", "checkTriKbn");
		// 会社コードの設定
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// 支払コードの設定
		addSendValues("triCode", triCode);

		// サーブレットの接続先
		if (!request(getServletName())) {
			errorHandler(panel);
		}

		Map data = super.getResult();

		if ("00".equals(data.get("tRI_KBN"))) {
			return false;
		}

		return true;
	}

	/**
	 * 複写処理
	 */
	public void copy() {
		try {
			MG0150EditDisplayDialogCtrl dialog = new MG0150EditDisplayDialogCtrl(panel.getParentFrame(), "C01738");
			// 当前行を取得する
			int nomRow = panel.ssCustomer.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssCustomer.getDataSource();
			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 取引先コードの設定
			map.put("triCode", model.getTableDataItem(nomRow, 1));
			// 取引先名称
			map.put("triName", model.getTableDataItem(nomRow, 2));
			// 取引先略称
			map.put("triName_S", model.getTableDataItem(nomRow, 3));
			// 取引先検索名称
			map.put("triName_K", model.getTableDataItem(nomRow, 4));
			// 郵便番号
			map.put("zip", model.getTableDataItem(nomRow, 6));
			// 住所カナ
			map.put("jyuKana", model.getTableDataItem(nomRow, 7));
			// 住所１
			map.put("jyu1", model.getTableDataItem(nomRow, 8));
			// 住所２
			map.put("jyu2", model.getTableDataItem(nomRow, 9));
			// 電話番号
			map.put("tel", model.getTableDataItem(nomRow, 10));
			// FAX番号
			map.put("fax", model.getTableDataItem(nomRow, 11));
			// 集計相手先コード
			map.put("sumCode", model.getTableDataItem(nomRow, 12));
			// 仕入先区分
			map.put("siireKbn", model.getTableDataItem(nomRow, 25));
			// 得意先区分
			map.put("tokuiKbn", model.getTableDataItem(nomRow, 26));
			// 入金条件締め日
			map.put("njCDate", model.getTableDataItem(nomRow, 15));
			// 入金条件締め後月
			map.put("njRMon", model.getTableDataItem(nomRow, 16));
			// 入金条件入金日
			map.put("njPDate", model.getTableDataItem(nomRow, 17));
			// 入金銀行口座ｺｰﾄﾞ
			map.put("nknCbkCode", model.getTableDataItem(nomRow, 18));
			// 取引形態区分
			map.put("triKbn", model.getTableDataItem(nomRow, 27));
			// スポット伝票番号
			map.put("spotDenNo", model.getTableDataItem(nomRow, 21));
			// 事業所名称
			map.put("jigName", model.getTableDataItem(nomRow, 5));
			// 振込依頼人名
			map.put("iraiName", model.getTableDataItem(nomRow, 20));
			// 入金手数料区分
			map.put("nyuTesuKbn", model.getTableDataItem(nomRow, 28));
			// 開始年月日
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 23)));
			// 終了年月日
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 24)));
			// 処理種別の設定
			map.put("flag", "insert");
			// 編集画面の表示
			dialog.setValues(map);
			dialog.show();

			if (!dialog.isSettle()) {
				return;
			}
			// データが編集する
			modify(dialog.getDataList(), "insert");

			modifySpreadRow(dialog.getDataList().get("triCode").toString(), INSERT_KBN);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * 変更処理
	 * 
	 * @param map データ
	 * @param flag 新規(true) or 更新(false)
	 * @throws IOException
	 */
	protected void modify(Map map, String flag) throws IOException {
		// 処理種別の設定
		addSendValues("flag", flag);
		// 会社コードの設定
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// 取引先コードの設定
		addSendValues("triCode", (String) map.get("triCode"));
		// 過去の取引先コードの設定
		addSendValues("oldTriCode", (String) map.get("oldTriCode"));
		// 取引先名称の設定
		addSendValues("triName", (String) map.get("triName"));
		// 取引先略称の設定
		addSendValues("triName_S", (String) map.get("triName_S"));
		// 取引先検索名称の設定
		addSendValues("triName_K", (String) map.get("triName_K"));
		// 郵便番号の設定
		addSendValues("zip", (String) map.get("zip"));
		// 住所カナの設定
		addSendValues("jyuKana", (String) map.get("jyuKana"));
		// 住所１の設定
		addSendValues("jyu1", (String) map.get("jyu1"));
		// 住所２の設定
		addSendValues("jyu2", (String) map.get("jyu2"));
		// 電話番号の設定
		addSendValues("tel", (String) map.get("tel"));
		// FAX番号の設定
		addSendValues("fax", (String) map.get("fax"));
		// 集計相手先コードの設定
		addSendValues("sumCode", (String) map.get("sumCode"));
		// 仕入先区分の設定
		addSendValues("siireKbn", (String) map.get("siireKbn"));
		// 得意先区分の設定
		addSendValues("tokuiKbn", (String) map.get("tokuiKbn"));
		// 入金条件締め日の設定
		addSendValues("njCDate", (String) map.get("njCDate"));
		// 入金条件締め後月の設定
		addSendValues("njRMon", (String) map.get("njRMon"));
		// 入金条件入金日の設定
		addSendValues("njPDate", (String) map.get("njPDate"));
		// 入金銀行口座ｺｰﾄﾞの設定
		addSendValues("nknCbkCode", (String) map.get("nknCbkCode"));
		// 取引形態区分の設定
		addSendValues("triKbn", (String) map.get("triKbn"));
		// スポット伝票番号の設定
		addSendValues("spotDenNo", (String) map.get("spotDenNo"));
		// 事業所名称の設定
		addSendValues("jigName", (String) map.get("jigName"));
		// 振込依頼人名の設定
		addSendValues("iraiName", (String) map.get("iraiName"));
		// 入金手数料区分の設定
		addSendValues("nyuTesuKbn", (String) map.get("nyuTesuKbn"));
		// 開始年月日の設定
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// 終了年月日の設定
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// プログラムＩＤの設定
		addSendValues("prgID", PROGRAM_ID);

		// サーブレットの接続先
		if (!request(getServletName())) {
			errorHandler(panel);
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param triCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	void modifySpreadRow(String triCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(triCode, updKbn);
		TTable ssPanelSpread = panel.ssCustomer;
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
		lockBtn(panel.ssCustomer.getDataSource().getNumRows() != 0);
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
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	Vector<Vector> setModifyCell(String triCode, int updKbn) throws IOException, TRequestException {
		String text;
		String value;
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginTriCode", Util.avoidNull(triCode));
		// 終了コードの設定
		addSendValues("endTriCode", Util.avoidNull(triCode));

		// サーブレットの接続先
		if (!request(getServletName())) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssCustomer.getDataSource();
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

			for (int column = 0; dataIte.hasNext(); column++) {
				// 結果集を追加する
				colum.add(column, (String) dataIte.next());
			}

			// 事業所名称
			String jigName = colum.get(20);
			// 振込依頼人名
			String iraiName = colum.get(21);
			colum.set(20, iraiName);
			// スポット伝票番号
			String spotDenNo = colum.get(19);
			colum.set(21, spotDenNo);
			// 取引形態区分
			String triKbn = colum.get(18);
			// 入金銀行口座ｺｰﾄﾞ
			String nknCbkCode = colum.get(17);
			colum.set(18, nknCbkCode);
			// 入金条件入金日
			String njPDate = colum.get(16);
			colum.set(17, njPDate);
			// 入金条件締め後月
			String njRMon = colum.get(15);
			colum.set(16, njRMon);
			// 入金条件締め日
			String njCDate = colum.get(14);
			colum.set(15, njCDate);
			// 得意先区分
			String tokuiKbn = colum.get(13);
			// 仕入先区分
			String siireKbn = colum.get(12);
			// 集計相手先コード
			String sumCode = colum.get(11);
			colum.set(12, sumCode);
			// FAX番号
			String fax = colum.get(10);
			colum.set(11, fax);
			// 住所２
			String jyu2 = colum.get(8);
			// 住所１
			String jyu1 = colum.get(7);
			colum.set(8, jyu1);
			// 住所カナ
			String jyuKana = colum.get(6);
			colum.set(7, jyuKana);
			// 郵便番号
			String zip = colum.get(5);
			colum.set(6, zip);
			colum.set(5, jigName);
			// 電話番号
			String tel = colum.get(9);
			colum.set(10, tel);
			colum.set(9, jyu2);

			// 仕入先区分
			value = siireKbn;
			text = ("0".equals(value) ? this.getWord("C01295") : this.getWord("C00203"));
			colum.add(25, value);
			colum.set(13, text);

			// 得意先区分
			value = tokuiKbn;
			text = ("0".equals(value) ? this.getWord("C01296") : this.getWord("C00401"));
			colum.add(26, value);
			colum.set(14, text);

			// 取引形態区分
			value = triKbn;
			text = ("00".equals(value) ? this.getWord("C00372") : this.getWord("C00308"));
			colum.add(27, value);
			colum.set(19, text);

			// 入金手数料
			value = colum.get(22);
			text = ("1".equals(value) ? this.getWord("C00399") : this.getWord("C00327"));
			colum.add(28, value);
			colum.set(22, text);

			try {
				colum.set(23, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(23))));
				colum.set(24, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(24))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(23), ex);
			}
		}
		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssCustomer.getCurrentRow(), colum);
		}
		return cells;
	}

	/**
	 * 削除処理
	 */
	void delete() {
		if (this.showConfirmMessage(panel, "Q00007")) {
			try {
				// 選択されている行の1つ目と2つ目のカラムを取得
				int nomRow = panel.ssCustomer.getCurrentRow();
				// データ集を取得する
				TableDataModel model = panel.ssCustomer.getDataSource();
				// 会社コードの取得
				String kaiCode = (String) model.getTableDataItem(nomRow, 0);
				// 会社コードの取得
				String triCode = (String) model.getTableDataItem(nomRow, 1);
				// 取引先コードの取得
				addSendValues("flag", "delete");
				// 会社コードの設定
				addSendValues("kaiCode", kaiCode);
				// 取引先コードの設定
				addSendValues("triCode", triCode);

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
	void deleteSpreadRow() {
		int row = panel.ssCustomer.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssCustomer.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssCustomer.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssCustomer.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// 保持しておいた値をスクロールバーにセット
			panel.ssCustomer.getVertSB().setValue(0);
			panel.ssCustomer.getHorizSB().setValue(0);
		}
		// ボタンロック解除
		lockBtn(panel.ssCustomer.getDataSource().getNumRows() != 0);
	}

	/**
	 * マスタ選択行状態の保持
	 * 
	 * @param row
	 */
	private void selectSpreadRow(int row) {

		panel.ssCustomer.setRowSelection(row, row);
		panel.ssCustomer.setCurrentCell(row, 0);

	}

	/**
	 * 検索処理
	 * 
	 * @return true:正常
	 */
	boolean find() {
		try {
			String beginCustomer = panel.ctrlBeginCustomer.getValue().toString();
			String endCustomer = panel.ctrlEndCustomer.getValue().toString();

			if (Util.isSmallerThen(beginCustomer, endCustomer) == false) {
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
	 * @return true:正常
	 * @throws IOException
	 */
	protected boolean reflesh() throws IOException {
		boolean dataExists = true;
		String text;
		String value;
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();
		// 開始コードの取得
		String beginTriCode = panel.ctrlBeginCustomer.getValue();
		// 終了コードの取得
		String endTriCode = panel.ctrlEndCustomer.getValue();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginTriCode", Util.avoidNull(beginTriCode));
		// 終了コードの設定
		addSendValues("endTriCode", Util.avoidNull(endTriCode));

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
			panel.ctrlBeginCustomer.getField().requestFocus();
			showMessage(panel, "W00100");
			dataExists = false;
		}

		for (int row = 0; recordIte.hasNext(); row++) {
			// 結果の内容を取得する
			Iterator dataIte = ((List) recordIte.next()).iterator();

			// 結果を追加する
			Vector<String> colum = new Vector<String>();

			for (int column = 0; dataIte.hasNext(); column++) {
				// 結果集を追加する
				colum.add(column, (String) dataIte.next());
			}

			// 事業所名称
			String jigName = colum.get(20);
			// 振込依頼人名
			String iraiName = colum.get(21);
			colum.set(20, iraiName);
			// スポット伝票番号
			String spotDenNo = colum.get(19);
			colum.set(21, spotDenNo);
			// 取引形態区分
			String triKbn = colum.get(18);
			// 入金銀行口座ｺｰﾄﾞ
			String nknCbkCode = colum.get(17);
			colum.set(18, nknCbkCode);
			// 入金条件入金日
			String njPDate = colum.get(16);
			colum.set(17, njPDate);
			// 入金条件締め後月
			String njRMon = colum.get(15);
			colum.set(16, njRMon);
			// 入金条件締め日
			String njCDate = colum.get(14);
			colum.set(15, njCDate);
			// 得意先区分
			String tokuiKbn = colum.get(13);
			// 仕入先区分
			String siireKbn = colum.get(12);
			// 集計相手先コード
			String sumCode = colum.get(11);
			colum.set(12, sumCode);
			// FAX番号
			String fax = colum.get(10);
			colum.set(11, fax);
			// 住所２
			String jyu2 = colum.get(8);
			// 住所１
			String jyu1 = colum.get(7);
			colum.set(8, jyu1);
			// 住所カナ
			String jyuKana = colum.get(6);
			colum.set(7, jyuKana);
			// 郵便番号
			String zip = colum.get(5);
			colum.set(6, zip);
			colum.set(5, jigName);
			// 電話番号
			String tel = colum.get(9);
			colum.set(10, tel);
			colum.set(9, jyu2);

			// 仕入先区分
			value = siireKbn;
			text = ("0".equals(value) ? this.getWord("C01295") : this.getWord("C00203"));
			colum.add(25, value);
			colum.set(13, text);

			// 得意先区分
			value = tokuiKbn;
			text = ("0".equals(value) ? this.getWord("C01296") : this.getWord("C00401"));
			colum.add(26, value);
			colum.set(14, text);

			// 取引形態区分
			value = triKbn;
			text = ("00".equals(value) ? this.getWord("C00372") : this.getWord("C00308"));
			colum.add(27, value);
			colum.set(19, text);

			// 入金手数料
			value = colum.get(22);
			text = ("1".equals(value) ? this.getWord("C00399") : this.getWord("C00327"));
			colum.add(28, value);
			colum.set(22, text);

			cells.add(row, colum);
			try {
				colum.set(23, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(23))));
				colum.set(24, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(24))));
			} catch (ParseException ex) {
				ClientLogger.error(this.getClass().getName() + ":" + "Date Parse error:" + colum.get(23), ex);
			}
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
			String beginCustomer = panel.ctrlBeginCustomer.getValue().toString();
			String endCustomer = panel.ctrlEndCustomer.getValue().toString();

			if (Util.isSmallerThen(beginCustomer, endCustomer) == false) {
				// 警告メッセージ表示
				panel.ctrlBeginCustomer.getField().requestFocus();
				showMessage(panel, "W00036", "C00408");
				return;
			}

			// 確認ダイアログ表示
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}

			Map conds = new HashMap();
			// 送信するパラメータを設定
			conds.put("flag", "report");
			// 会社コードの取得
			conds.put("kaiCode", getLoginUserCompanyCode());
			// 開始コードの取得
			conds.put("beginTriCode", panel.ctrlBeginCustomer.getValue());
			// 終了コードの取得
			conds.put("endTriCode", panel.ctrlEndCustomer.getValue());
			conds.put("langCode", getLoginLanguage());

			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
