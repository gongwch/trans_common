package jp.co.ais.trans.master.ui;

import java.io.*;
import java.text.*;
import java.util.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;

/**
 * 科目マスタ画面コントロール
 */
public class MG0080ItemMasterPanelCtrl extends TPanelCtrlBase {

	/** プログラムID */
	protected static final String PROGRAM_ID = "MG0080";

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
		return "MG0080ItemMasterServlet";
	}

	/** パネル */
	public MG0080ItemMasterPanel panel;

	/** 科目種別 */
	public Map kmkShuMap;

	/** 補助区分 */
	public Map hkmKbnMap;

	/** ＧＬ科目制御区分 */
	public Map kmkCntGlMap;

	/** AP科目制御区分 */
	public Map kmkCntApMap;

	/** AR科目制御区分 */
	public Map kmkCntArMap;

	/** 取引先入力フラグ */
	public Map triCodeFlgMap;

	/** 評価替対象フラグ */
	public Map excFlgMap;

	/** BG科目制御区分 */
	public Map kmkCntBgMap;

	/** 相殺科目制御区分 */
	public Map kmkCntSousaiMap;

	/** BS勘定消込区分 */
	public Map kesiKbnMap;

	/** 科目マスタ編集画面 */
	MG0080EditDisplayDialogCtrl dialog;

	/**
	 * コンストラクタ.
	 */

	public MG0080ItemMasterPanelCtrl() {
		try {
			// 一覧画面の初期化
			panel = new MG0080ItemMasterPanel(this);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		// 科目種別の初期化
		kmkShuMap = new LinkedHashMap();
		kmkShuMap.put("0", getWord("C02108"));
		kmkShuMap.put("1", getWord("C02109"));
		kmkShuMap.put("2", getWord("C02110"));
		kmkShuMap.put("3", getWord("C02111"));
		kmkShuMap.put("9", getWord("C02112"));

		// 補助区分の初期化
		hkmKbnMap = new LinkedHashMap();
		hkmKbnMap.put("0", getWord("C00412"));
		hkmKbnMap.put("1", getWord("C00006"));

		// ＧＬ科目制御区分の初期化
		kmkCntGlMap = new LinkedHashMap();
		kmkCntGlMap.put("00", getWord("C00372"));
		kmkCntGlMap.put("04", getWord("C02114"));
		kmkCntGlMap.put("05", getWord("C02115"));
		kmkCntGlMap.put("07", getWord("C02117"));

		// AP科目制御区分の初期化
		kmkCntApMap = new LinkedHashMap();
		kmkCntApMap.put("00", getWord("C00372"));
		kmkCntApMap.put("01", getWord("C02118"));
		kmkCntApMap.put("02", getWord("C02119"));

		// AR科目制御区分の初期化
		kmkCntArMap = new LinkedHashMap();
		kmkCntArMap.put("00", getWord("C00372"));
		kmkCntArMap.put("01", getWord("C02120"));
		kmkCntArMap.put("02", getWord("C02121"));

		// 取引先入力フラグの初期化
		triCodeFlgMap = new LinkedHashMap();
		triCodeFlgMap.put("0", getWord("C01279"));
		triCodeFlgMap.put("2", getWord("C00401"));
		triCodeFlgMap.put("3", getWord("C00203"));
		triCodeFlgMap.put("4", getWord("C02122"));

		// 評価替対象フラグの初期化
		excFlgMap = new LinkedHashMap();
		excFlgMap.put("0", getWord("C02099"));
		excFlgMap.put("1", getWord("C02123"));
		excFlgMap.put("2", getWord("C02124"));

		// BG科目制御区分の初期化
		kmkCntBgMap = new LinkedHashMap();
		kmkCntBgMap.put("00", getWord("C00372"));
		kmkCntBgMap.put("11", getWord("C02125"));

		// 相殺科目制御区分の初期化
		kmkCntSousaiMap = new LinkedHashMap();
		kmkCntSousaiMap.put("0", getWord("C02099"));
		kmkCntSousaiMap.put("1", getWord("C02100"));

		// BS勘定消込区分の初期化
		kesiKbnMap = new LinkedHashMap();
		kesiKbnMap.put("0", getWord("C02099"));
		kesiKbnMap.put("1", getWord("C02100"));

		setBeginItemCondition();
		setEndItemCondition();
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
	 * 
	 */
	public void setBeginItemCondition() {
		// ログインユーザーの会社コード
		String itemCode = Util.avoidNull(panel.ctrlBeginItem.getValue());
		panel.ctrlBeginItem.getInputParameter().setCompanyCode(super.getLoginUserCompanyCode());
		panel.ctrlBeginItem.getInputParameter().setItemCode(itemCode);
	}

	/**
	 * 
	 */
	public void setEndItemCondition() {
		// ログインユーザーの会社コード
		String itemCode = Util.avoidNull(panel.ctrlEndItem.getValue());
		panel.ctrlEndItem.getInputParameter().setCompanyCode(super.getLoginUserCompanyCode());
		panel.ctrlEndItem.getInputParameter().setItemCode(itemCode);
	}

	/**
	 * 新規登録処理
	 */
	void insert() {
		try {
			// 編集画面の初期化
			createEditDisplayDialog("C01698");
			// リストボックスの値を作る
			dialog.setSelectedMap(kmkShuMap, hkmKbnMap, kmkCntGlMap, kmkCntApMap, kmkCntArMap, triCodeFlgMap,
				excFlgMap, kmkCntBgMap, kmkCntSousaiMap, kesiKbnMap);

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
			modifySpreadRow(kmkCode, INSERT_KBN);
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
		dialog = new MG0080EditDisplayDialogCtrl(panel.getParentFrame(), titleID);
	}

	/**
	 * 編集処理
	 */

	void update() {
		try {
			// 編集画面の初期化
			createEditDisplayDialog("C00977");

			// リストボックスの値を作る
			dialog.setSelectedMap(kmkShuMap, hkmKbnMap, kmkCntGlMap, kmkCntApMap, kmkCntArMap, triCodeFlgMap,
				excFlgMap, kmkCntBgMap, kmkCntSousaiMap, kesiKbnMap);

			// 当前行を取得する
			int nomRow = panel.ssItem.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssItem.getDataSource();

			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 科目コード
			map.put("kmkCode", model.getTableDataItem(nomRow, 1));
			// 科目名称
			map.put("kmkName", model.getTableDataItem(nomRow, 2));
			// 科目略称
			map.put("kmkName_S", model.getTableDataItem(nomRow, 3));
			// 科目検索名称
			map.put("kmkName_K", model.getTableDataItem(nomRow, 4));
			// 集計区分
			map.put("sumKbn", model.getTableDataItem(nomRow, 46));
			// 科目種別
			map.put("kmkShu", model.getTableDataItem(nomRow, 47));
			// 貸借区分
			map.put("dcKbn", model.getTableDataItem(nomRow, 48));
			// 補助区分
			map.put("hkmKbn", model.getTableDataItem(nomRow, 49));
			// 固定部門ｺｰﾄﾞ
			map.put("koteiDepCode", model.getTableDataItem(nomRow, 9));
			// 消費税コード
			map.put("zeiCode", model.getTableDataItem(nomRow, 10));
			// ＧＬ科目制御区分
			map.put("kmkCntGl", model.getTableDataItem(nomRow, 50));
			// AP科目制御区分
			map.put("kmkCntAp", model.getTableDataItem(nomRow, 51));
			// AR科目制御区分
			map.put("kmkCntAr", model.getTableDataItem(nomRow, 52));
			// BG科目制御区分
			map.put("kmkCntBg", model.getTableDataItem(nomRow, 53));
			// 取引先入力フラグ
			map.put("triCodeFlg", model.getTableDataItem(nomRow, 54));
			// 発生日入力ﾌﾗｸﾞ
			map.put("hasFlg", model.getTableDataItem(nomRow, 55));
			// 相殺科目制御区分
			map.put("kmkCntSousai", model.getTableDataItem(nomRow, 56));
			// BS勘定消込区分
			map.put("kesiKbn", model.getTableDataItem(nomRow, 57));
			// 評価替対象フラグ
			map.put("excFlg", model.getTableDataItem(nomRow, 58));
			// 入金伝票入力ﾌﾗｸﾞ
			map.put("glFlg1", model.getTableDataItem(nomRow, 59));
			// 出金伝票入力ﾌﾗｸﾞ
			map.put("glFlg2", model.getTableDataItem(nomRow, 60));
			// 振替伝票入力ﾌﾗｸﾞ
			map.put("glFlg3", model.getTableDataItem(nomRow, 61));
			// 経費精算伝票入力ﾌﾗｸﾞ
			map.put("apFlg1", model.getTableDataItem(nomRow, 62));
			// 請求書伝票入力ﾌﾗｸﾞ
			map.put("apFlg2", model.getTableDataItem(nomRow, 63));
			// 債権計上伝票入力ﾌﾗｸﾞ
			map.put("arFlg1", model.getTableDataItem(nomRow, 64));
			// 債権消込伝票入力ﾌﾗｸﾞ
			map.put("arFlg2", model.getTableDataItem(nomRow, 65));
			// 資産計上伝票入力ﾌﾗｸﾞ
			map.put("faFlg1", model.getTableDataItem(nomRow, 66));
			// 支払依頼伝票入力ﾌﾗｸﾞ
			map.put("faFlg2", model.getTableDataItem(nomRow, 67));
			// 多通貨入力フラグ
			map.put("mcrFlg", model.getTableDataItem(nomRow, 68));
			// 社員入力フラグ
			map.put("empCodeFlg", model.getTableDataItem(nomRow, 69));
			// 管理１-6入力フラグ
			map.put("knrFlg1", model.getTableDataItem(nomRow, 70));
			map.put("knrFlg2", model.getTableDataItem(nomRow, 71));
			map.put("knrFlg3", model.getTableDataItem(nomRow, 72));
			map.put("knrFlg4", model.getTableDataItem(nomRow, 73));
			map.put("knrFlg5", model.getTableDataItem(nomRow, 74));
			map.put("knrFlg6", model.getTableDataItem(nomRow, 75));
			// 非会計1-3入力フラグ
			map.put("hmFlg1", model.getTableDataItem(nomRow, 76));
			map.put("hmFlg2", model.getTableDataItem(nomRow, 77));
			map.put("hmFlg3", model.getTableDataItem(nomRow, 78));
			// 売上課税入力フラグ
			map.put("uriZeiFlg", model.getTableDataItem(nomRow, 79));
			// 仕入課税入力フラグ
			map.put("sirZeiFlg", model.getTableDataItem(nomRow, 80));
			// 借方資金コード
			map.put("sknCodeDr", model.getTableDataItem(nomRow, 42));
			// 貸方資金コード
			map.put("sknCodeCr", model.getTableDataItem(nomRow, 43));
			// 開始年月日
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 44)));
			// 終了年月日
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 45)));
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
			String kmkCode = dialog.getDataList().get("kmkCode").toString();
			modifySpreadRow(kmkCode, UPD_KBN);
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
			// 編集画面の初期化
			createEditDisplayDialog("C01738");

			// リストボックスの値を作る
			dialog.setSelectedMap(kmkShuMap, hkmKbnMap, kmkCntGlMap, kmkCntApMap, kmkCntArMap, triCodeFlgMap,
				excFlgMap, kmkCntBgMap, kmkCntSousaiMap, kesiKbnMap);
			// 当前行を取得する
			int nomRow = panel.ssItem.getCurrentRow();
			// データ集を取得する
			TableDataModel model = panel.ssItem.getDataSource();

			// mapの初期化
			Map<String, Object> map = new TreeMap<String, Object>();
			// 科目コード
			map.put("kmkCode", model.getTableDataItem(nomRow, 1));
			// 科目名称
			map.put("kmkName", model.getTableDataItem(nomRow, 2));
			// 科目略称
			map.put("kmkName_S", model.getTableDataItem(nomRow, 3));
			// 科目検索名称
			map.put("kmkName_K", model.getTableDataItem(nomRow, 4));
			// 集計区分
			map.put("sumKbn", model.getTableDataItem(nomRow, 46));
			// 科目種別
			map.put("kmkShu", model.getTableDataItem(nomRow, 47));
			// 貸借区分
			map.put("dcKbn", model.getTableDataItem(nomRow, 48));
			// 補助区分
			map.put("hkmKbn", model.getTableDataItem(nomRow, 49));
			// 固定部門ｺｰﾄﾞ
			map.put("koteiDepCode", model.getTableDataItem(nomRow, 9));
			// 消費税コード
			map.put("zeiCode", model.getTableDataItem(nomRow, 10));
			// ＧＬ科目制御区分
			map.put("kmkCntGl", model.getTableDataItem(nomRow, 50));
			// AP科目制御区分
			map.put("kmkCntAp", model.getTableDataItem(nomRow, 51));
			// AR科目制御区分
			map.put("kmkCntAr", model.getTableDataItem(nomRow, 52));
			// BG科目制御区分
			map.put("kmkCntBg", model.getTableDataItem(nomRow, 53));
			// 取引先入力フラグ
			map.put("triCodeFlg", model.getTableDataItem(nomRow, 54));
			// 発生日入力ﾌﾗｸﾞ
			map.put("hasFlg", model.getTableDataItem(nomRow, 55));
			// 相殺科目制御区分
			map.put("kmkCntSousai", model.getTableDataItem(nomRow, 56));
			// BS勘定消込区分
			map.put("kesiKbn", model.getTableDataItem(nomRow, 57));
			// 評価替対象フラグ
			map.put("excFlg", model.getTableDataItem(nomRow, 58));
			// 入金伝票入力ﾌﾗｸﾞ
			map.put("glFlg1", model.getTableDataItem(nomRow, 59));
			// 出金伝票入力ﾌﾗｸﾞ
			map.put("glFlg2", model.getTableDataItem(nomRow, 60));
			// 振替伝票入力ﾌﾗｸﾞ
			map.put("glFlg3", model.getTableDataItem(nomRow, 61));
			// 経費精算伝票入力ﾌﾗｸﾞ
			map.put("apFlg1", model.getTableDataItem(nomRow, 62));
			// 請求書伝票入力ﾌﾗｸﾞ
			map.put("apFlg2", model.getTableDataItem(nomRow, 63));
			// 債権計上伝票入力ﾌﾗｸﾞ
			map.put("arFlg1", model.getTableDataItem(nomRow, 64));
			// 債権消込伝票入力ﾌﾗｸﾞ
			map.put("arFlg2", model.getTableDataItem(nomRow, 65));
			// 資産計上伝票入力ﾌﾗｸﾞ
			map.put("faFlg1", model.getTableDataItem(nomRow, 66));
			// 支払依頼伝票入力ﾌﾗｸﾞ
			map.put("faFlg2", model.getTableDataItem(nomRow, 67));
			// 多通貨入力フラグ
			map.put("mcrFlg", model.getTableDataItem(nomRow, 68));
			// 社員入力フラグ
			map.put("empCodeFlg", model.getTableDataItem(nomRow, 69));
			// 管理１-6入力フラグ
			map.put("knrFlg1", model.getTableDataItem(nomRow, 70));
			map.put("knrFlg2", model.getTableDataItem(nomRow, 71));
			map.put("knrFlg3", model.getTableDataItem(nomRow, 72));
			map.put("knrFlg4", model.getTableDataItem(nomRow, 73));
			map.put("knrFlg5", model.getTableDataItem(nomRow, 74));
			map.put("knrFlg6", model.getTableDataItem(nomRow, 75));
			// 非会計1-3入力フラグ
			map.put("hmFlg1", model.getTableDataItem(nomRow, 76));
			map.put("hmFlg2", model.getTableDataItem(nomRow, 77));
			map.put("hmFlg3", model.getTableDataItem(nomRow, 78));
			// 売上課税入力フラグ
			map.put("uriZeiFlg", model.getTableDataItem(nomRow, 79));
			// 仕入課税入力フラグ
			map.put("sirZeiFlg", model.getTableDataItem(nomRow, 80));
			// 借方資金コード
			map.put("sknCodeDr", model.getTableDataItem(nomRow, 42));
			// 貸方資金コード
			map.put("sknCodeCr", model.getTableDataItem(nomRow, 43));
			// 開始年月日
			map.put("strDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 44)));
			// 終了年月日
			map.put("endDate", DateUtil.toYMDDate((String) model.getTableDataItem(nomRow, 45)));
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

			String kmkCode = dialog.getDataList().get("kmkCode").toString();
			modifySpreadRow(kmkCode, INSERT_KBN);
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
	public void modify(Map map, boolean isInsert) throws IOException {
		addSendValues("flag", isInsert ? "insert" : "update");
		// 会社コード
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// 科目コード
		addSendValues("kmkCode", (String) map.get("kmkCode"));
		// 科目名称
		addSendValues("kmkName", (String) map.get("kmkName"));
		// 科目略称
		addSendValues("kmkName_S", (String) map.get("kmkName_S"));
		// 科目検索名称
		addSendValues("kmkName_K", (String) map.get("kmkName_K"));
		// 集計区分
		addSendValues("sumKbn", (String) map.get("sumKbn"));
		// 科目種別
		addSendValues("kmkShu", (String) map.get("kmkShu"));
		// 貸借区分
		addSendValues("dcKbn", (String) map.get("dcKbn"));
		// 補助区分
		addSendValues("hkmKbn", (String) map.get("hkmKbn"));
		// ＧＬ科目制御区分
		addSendValues("kmkCntGl", (String) map.get("kmkCntGl"));
		// AP科目制御区分
		addSendValues("kmkCntAp", (String) map.get("kmkCntAp"));
		// AR科目制御区分
		addSendValues("kmkCntAr", (String) map.get("kmkCntAr"));
		// 固定部門ｺｰﾄﾞ
		addSendValues("koteiDepCode", (String) map.get("koteiDepCode"));
		// 消費税コード
		addSendValues("zeiCode", (String) map.get("zeiCode"));
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
		// 取引先入力フラグ
		addSendValues("triCodeFlg", (String) map.get("triCodeFlg"));
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
		// BG科目制御区分
		addSendValues("kmkCntBg", (String) map.get("kmkCntBg"));
		// 借方資金コード
		addSendValues("sknCodeDr", (String) map.get("sknCodeDr"));
		// 貸方資金コード
		addSendValues("sknCodeCr", (String) map.get("sknCodeCr"));
		// 相殺科目制御区分
		addSendValues("kmkCntSousai", (String) map.get("kmkCntSousai"));
		// BS勘定消込区分
		addSendValues("kesiKbn", (String) map.get("kesiKbn"));
		// 開始年月日
		addSendValues("strDate", DateUtil.toYMDHMSString((Date) map.get("strDate")));
		// 終了年月日
		addSendValues("endDate", DateUtil.toYMDHMSString((Date) map.get("endDate")));
		// プログラムＩＤ
		addSendValues("prgID", PROGRAM_ID);

		// サーブレットの接続先
		if (!request(getServletName())) {
			// サーバ側のエラー場合
			errorHandler(panel.getParentFrame());
		}
	}

	/**
	 * 新規、複写、編集の場合、スプレッド更新
	 * 
	 * @param kmkCode
	 * @param updKbn
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected void modifySpreadRow(String kmkCode, int updKbn) throws TRequestException, IOException {
		Vector<Vector> cells = setModifyCell(kmkCode, updKbn);
		TTable ssPanelSpread = panel.ssItem;
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
		lockBtn(panel.ssItem.getDataSource().getNumRows() != 0);
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
	 * @param updKbn
	 * @return 更新のセール
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected Vector<Vector> setModifyCell(String kmkCode, int updKbn) throws IOException, TRequestException {
		String kaiCode = getLoginUserCompanyCode();
		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginKmkCode", Util.avoidNull(kmkCode));
		// 終了コードの設定
		addSendValues("endKmkCode", Util.avoidNull(kmkCode));

		// サーブレットの接続先
		if (!request(getServletName())) {
			throw new TRequestException();
		}
		JCVectorDataSource modifyDs = (JCVectorDataSource) panel.ssItem.getDataSource();
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
				colum.add(column, (String) dataIte.next());
			}

			String impossible = this.getWord("C01279");
			String possible = this.getWord("C01276");

			// 転換に対応する
			String text;
			String value;

			boolean inPut = !colum.get(5).equals("0");

			// ＧＬ科目制御区分
			String kmkCntGl = colum.get(9);
			// AP科目制御区分
			String kmkCntAp = colum.get(10);
			// AR科目制御区分
			String kmkCntAr = colum.get(11);
			// BG科目制御区分
			String kmkCntBg = colum.get(39);
			// 取引先入力フラグ
			String triCodeFlg = colum.get(23);
			// 発生日入力ﾌﾗｸﾞ
			String hasFlg = colum.get(24);
			// 相殺科目制御区分
			String kmkCntSousai = colum.get(42);
			// BS勘定消込区分
			String kesiKbn = colum.get(43);
			// 評価替対象フラグ
			String excFlg = colum.get(38);
			// 入金伝票入力ﾌﾗｸﾞ
			String glFlg1 = colum.get(14);
			// 出金伝票入力ﾌﾗｸﾞ
			String glFlg2 = colum.get(15);
			// 振替伝票入力ﾌﾗｸﾞ
			String glFlg3 = colum.get(16);
			// 経費精算伝票入力ﾌﾗｸﾞ
			String apFlg1 = colum.get(17);
			// 請求書伝票入力ﾌﾗｸﾞ
			String apFlg2 = colum.get(18);
			// 債権計上伝票入力ﾌﾗｸﾞ
			String arFlg1 = colum.get(19);
			// 債権消込伝票入力ﾌﾗｸﾞ
			String atFlg2 = colum.get(20);
			// 資産計上伝票入力ﾌﾗｸﾞ
			String faFlg1 = colum.get(21);
			// 支払依頼伝票入力ﾌﾗｸﾞ
			String faFlg2 = colum.get(22);
			// 多通貨入力フラグ
			String mcrFlg = colum.get(37);
			// 社員入力フラグ
			String empCodeFlg = colum.get(25);
			// 管理１-6入力フラグ
			String knrFlg1 = colum.get(26);
			String knrFlg2 = colum.get(27);
			String knrFlg3 = colum.get(28);
			String knrFlg4 = colum.get(29);
			String knrFlg5 = colum.get(30);
			String knrFlg6 = colum.get(31);
			// 非会計1入力フラグ
			String hmFlg1 = colum.get(32);
			String hmFlg2 = colum.get(33);
			String hmFlg3 = colum.get(34);
			// 売上課税入力フラグ
			String uriZeiFlg = colum.get(35);
			// 仕入課税入力フラグ
			String sirZeiFlg = colum.get(36);

			// 固定部門ｺｰﾄﾞ
			colum.set(9, colum.get(12));
			// 消費税コード
			colum.set(10, colum.get(13));

			// 借方資金コード
			colum.set(42, colum.get(40));
			// 貸方資金コード
			colum.set(43, colum.get(41));

			// 集計区分
			colum.add(46, "");
			value = colum.get(5);
			if ("0".equals(value)) {
				text = this.getWord("C02157");
			} else if ("1".equals(value)) {
				text = this.getWord("C02158");
			} else {
				text = this.getWord("C02159");
			}
			colum.set(46, value);
			colum.set(5, text);

			// 科目種別
			colum.add(47, "");
			value = colum.get(6);
			text = (String) kmkShuMap.get(value);
			colum.set(47, value);
			colum.set(6, text);

			// 貸借区分
			colum.add(48, "");
			value = colum.get(7);
			text = ("0".equals(value) ? this.getWord("C01125") : this.getWord("C01228"));
			colum.set(48, value);
			colum.set(7, text);

			// 補助区分
			colum.add(49, "");
			value = colum.get(8);
			text = (String) hkmKbnMap.get(value);
			colum.set(49, value);
			colum.set(8, inPut ? "" : text);

			// ＧＬ科目制御区分
			colum.add(50, "");
			value = kmkCntGl;
			text = (String) kmkCntGlMap.get(value);
			colum.set(50, value);
			colum.set(11, inPut ? "" : text);

			// AP科目制御区分
			colum.add(51, "");
			value = kmkCntAp;
			text = (String) kmkCntApMap.get(value);
			colum.set(51, value);
			colum.set(12, inPut ? "" : text);

			// AR科目制御区分
			colum.add(52, "");
			value = kmkCntAr;
			text = (String) kmkCntArMap.get(value);
			colum.set(52, value);
			colum.set(13, inPut ? "" : text);

			// BG科目制御区分
			colum.add(53, "");
			value = kmkCntBg;
			text = (String) kmkCntBgMap.get(value);
			colum.set(53, value);
			colum.set(14, inPut ? "" : text);

			// 取引先入力フラグ
			colum.add(54, "");
			value = triCodeFlg;
			text = (String) triCodeFlgMap.get(value);
			colum.set(54, value);
			colum.set(15, inPut ? "" : text);

			// 発生日入力ﾌﾗｸﾞ
			colum.add(55, "");
			value = hasFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(55, value);
			colum.set(16, inPut ? "" : text);

			// 相殺科目制御区分
			colum.add(56, "");
			value = kmkCntSousai;
			text = (String) kmkCntSousaiMap.get(value);
			colum.set(56, value);
			colum.set(17, inPut ? "" : text);

			// BS勘定消込区分
			colum.add(57, "");
			value = kesiKbn;
			text = ("0".equals(value) ? this.getWord("C02099") : this.getWord("C02100"));
			colum.set(57, value);
			colum.set(18, inPut ? "" : text);

			// 評価替対象フラグ
			colum.add(58, "");
			value = excFlg;
			text = (String) excFlgMap.get(value);
			colum.set(58, value);
			colum.set(19, inPut ? "" : text);

			// 入金伝票入力ﾌﾗｸﾞ
			colum.add(59, "");
			value = glFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(59, value);
			colum.set(20, inPut ? "" : text);

			// 出金伝票入力ﾌﾗｸﾞ
			colum.add(60, "");
			value = glFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(60, value);
			colum.set(21, inPut ? "" : text);

			// 振替伝票入力ﾌﾗｸﾞ
			colum.add(61, "");
			value = glFlg3;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(61, value);
			colum.set(22, inPut ? "" : text);

			// 経費精算伝票入力ﾌﾗｸﾞ
			colum.add(62, "");
			value = apFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(62, value);
			colum.set(23, inPut ? "" : text);

			// 請求書伝票入力ﾌﾗｸﾞ
			colum.add(63, "");
			value = apFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(63, value);
			colum.set(24, inPut ? "" : text);

			// 債権計上伝票入力ﾌﾗｸﾞ
			colum.add(64, "");
			value = arFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(64, value);
			colum.set(25, inPut ? "" : text);

			// 債権消込伝票入力ﾌﾗｸﾞ
			colum.add(65, "");
			value = atFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(65, value);
			colum.set(26, inPut ? "" : text);

			// 資産計上伝票入力ﾌﾗｸﾞ
			colum.add(66, "");
			value = faFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(66, value);
			colum.set(27, inPut ? "" : text);

			// 支払依頼伝票入力ﾌﾗｸﾞ
			colum.add(67, "");
			value = faFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(67, value);
			colum.set(28, inPut ? "" : text);

			// 多通貨入力フラグ
			colum.add(68, "");
			value = mcrFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(68, value);
			colum.set(29, inPut ? "" : text);

			String possible1 = this.getWord("C02371");
			// 社員入力フラグ
			colum.add(69, "");
			value = empCodeFlg;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(69, value);
			colum.set(30, inPut ? "" : text);

			// 管理１入力フラグ
			colum.add(70, "");
			value = knrFlg1;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(70, value);
			colum.set(31, inPut ? "" : text);

			// 管理2入力フラグ
			colum.add(71, "");
			value = knrFlg2;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(71, value);
			colum.set(32, inPut ? "" : text);

			// 管理3入力フラグ
			colum.add(72, "");
			value = knrFlg3;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(72, value);
			colum.set(33, inPut ? "" : text);

			// 管理4入力フラグ
			colum.add(73, "");
			value = knrFlg4;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(73, value);
			colum.set(34, inPut ? "" : text);

			// 管理5入力フラグ
			colum.add(74, "");
			value = knrFlg5;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(74, value);
			colum.set(35, inPut ? "" : text);

			// 管理6入力フラグ
			colum.add(75, "");
			value = knrFlg6;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(75, value);
			colum.set(36, inPut ? "" : text);

			// 非会計1入力フラグ
			colum.add(76, "");
			value = hmFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(76, value);
			colum.set(37, inPut ? "" : text);

			// 非会計2入力フラグ
			colum.add(77, "");
			value = hmFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(77, value);
			colum.set(38, inPut ? "" : text);

			// 非会計3入力フラグ
			colum.add(78, "");
			value = hmFlg3;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(78, value);
			colum.set(39, inPut ? "" : text);

			// 売上課税入力フラグ
			colum.add(79, "");
			value = uriZeiFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(79, value);
			colum.set(40, inPut ? "" : text);

			// 仕入課税入力フラグ
			colum.add(80, "");
			value = sirZeiFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(80, value);
			colum.set(41, inPut ? "" : text);

			try {
				colum.set(44, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(44))));
				colum.set(45, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(45))));
			} catch (ParseException e) {
				ClientLogger.error(this.getClass().getName() + ":" + colum.get(9), e);
			}
		}

		if (updKbn == INSERT_KBN) {
			cells.add(cells.size(), colum);

		} else if (updKbn == UPD_KBN) {
			cells.set(panel.ssItem.getCurrentRow(), colum);
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
				int nomRow = panel.ssItem.getCurrentRow();
				// データ集を取得する
				TableDataModel model = panel.ssItem.getDataSource();

				String kaiCode = (String) model.getTableDataItem(nomRow, 0); // 会社コード
				String kmkCode = (String) model.getTableDataItem(nomRow, 1); // 科目コード
				// 処理種別の設定
				addSendValues("flag", "delete");
				// 会社コードの設定
				addSendValues("kaiCode", kaiCode);
				// 科目コードの取得
				addSendValues("kmkCode", kmkCode);

				// サーブレットの接続先
				if (!request(getServletName())) {
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
		int row = panel.ssItem.getCurrentRow();

		JCVectorDataSource deleteDs = (JCVectorDataSource) panel.ssItem.getDataSource();
		Vector<Vector> cells = deleteDs.getCells();
		Vector rowData = cells.get(row);
		cells.remove(rowData);
		deleteDs.setCells(cells);
		deleteDs.setNumRows(cells.size());
		panel.ssItem.setDataSource(deleteDs);
		if (cells.size() == 0) {
			panel.ssItem.setRowSelection(-999, 0);
		} else {
			selectSpreadRow(0);
			// 保持しておいた値をスクロールバーにセット
			panel.ssItem.getVertSB().setValue(0);
			panel.ssItem.getHorizSB().setValue(0);
		}
		// ボタンロック解除
		lockBtn(panel.ssItem.getDataSource().getNumRows() != 0);
	}

	/**
	 * マスタ選択行状態の保持
	 * 
	 * @param row
	 */
	protected void selectSpreadRow(int row) {
		panel.ssItem.setRowSelection(row, row);
		panel.ssItem.setCurrentCell(row, 0);
	}

	/**
	 * 検索処理
	 * 
	 * @return boolean
	 */
	boolean find() {
		try {
			String beginItem = panel.ctrlBeginItem.getValue().toString();
			String endItem = panel.ctrlEndItem.getValue().toString();

			if (Util.isSmallerThen(beginItem, endItem) == false) {
				// 警告メッセージ表示
				panel.ctrlBeginItem.getField().requestFocus();
				showMessage(panel, "W00036", "C00077");
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
	public boolean reflesh() throws IOException {
		boolean dataExists = true;
		// 会社コードの取得
		String kaiCode = getLoginUserCompanyCode();

		// 開始コードの取得
		String beginKmkCode = panel.ctrlBeginItem.getValue();
		// 終了コードの取得
		String endKmkCode = panel.ctrlEndItem.getValue();

		// 送信するパラメータを設定
		addSendValues("flag", "find");
		// 会社コードの設定
		addSendValues("kaiCode", kaiCode);
		// 開始コードの設定
		addSendValues("beginKmkCode", Util.avoidNull(beginKmkCode));
		// 終了コードの設定
		addSendValues("endKmkCode", Util.avoidNull(endKmkCode));

		// 送信
		if (!request(getServletName())) {
			errorHandler(panel.getParentFrame());
			return false;
		}

		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>();

		// 結果を取得する
		Iterator recordIte = getResultList().iterator();

		// 値があるかどうか
		if (!recordIte.hasNext()) {
			panel.ctrlBeginItem.getField().requestFocus();
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
				colum.add(column, (String) dataIte.next());
			}

			String impossible = this.getWord("C01279");
			String possible = this.getWord("C01276");

			// 転換に対応する
			String text;
			String value;

			boolean inPut = !colum.get(5).equals("0");

			// ＧＬ科目制御区分
			String kmkCntGl = colum.get(9);
			// AP科目制御区分
			String kmkCntAp = colum.get(10);
			// AR科目制御区分
			String kmkCntAr = colum.get(11);
			// BG科目制御区分
			String kmkCntBg = colum.get(39);
			// 取引先入力フラグ
			String triCodeFlg = colum.get(23);
			// 発生日入力ﾌﾗｸﾞ
			String hasFlg = colum.get(24);
			// 相殺科目制御区分
			String kmkCntSousai = colum.get(42);
			// BS勘定消込区分
			String kesiKbn = colum.get(43);
			// 評価替対象フラグ
			String excFlg = colum.get(38);
			// 入金伝票入力ﾌﾗｸﾞ
			String glFlg1 = colum.get(14);
			// 出金伝票入力ﾌﾗｸﾞ
			String glFlg2 = colum.get(15);
			// 振替伝票入力ﾌﾗｸﾞ
			String glFlg3 = colum.get(16);
			// 経費精算伝票入力ﾌﾗｸﾞ
			String apFlg1 = colum.get(17);
			// 請求書伝票入力ﾌﾗｸﾞ
			String apFlg2 = colum.get(18);
			// 債権計上伝票入力ﾌﾗｸﾞ
			String arFlg1 = colum.get(19);
			// 債権消込伝票入力ﾌﾗｸﾞ
			String atFlg2 = colum.get(20);
			// 資産計上伝票入力ﾌﾗｸﾞ
			String faFlg1 = colum.get(21);
			// 支払依頼伝票入力ﾌﾗｸﾞ
			String faFlg2 = colum.get(22);
			// 多通貨入力フラグ
			String mcrFlg = colum.get(37);
			// 社員入力フラグ
			String empCodeFlg = colum.get(25);
			// 管理１-6入力フラグ
			String knrFlg1 = colum.get(26);
			String knrFlg2 = colum.get(27);
			String knrFlg3 = colum.get(28);
			String knrFlg4 = colum.get(29);
			String knrFlg5 = colum.get(30);
			String knrFlg6 = colum.get(31);
			// 非会計1入力フラグ
			String hmFlg1 = colum.get(32);
			String hmFlg2 = colum.get(33);
			String hmFlg3 = colum.get(34);
			// 売上課税入力フラグ
			String uriZeiFlg = colum.get(35);
			// 仕入課税入力フラグ
			String sirZeiFlg = colum.get(36);

			// 固定部門ｺｰﾄﾞ
			colum.set(9, colum.get(12));
			// 消費税コード
			colum.set(10, colum.get(13));

			// 借方資金コード
			colum.set(42, colum.get(40));
			// 貸方資金コード
			colum.set(43, colum.get(41));

			// 集計区分
			colum.add(46, "");
			value = colum.get(5);
			if ("0".equals(value)) {
				text = this.getWord("C02157");
			} else if ("1".equals(value)) {
				text = this.getWord("C02158");
			} else {
				text = this.getWord("C02159");
			}
			colum.set(46, value);
			colum.set(5, text);

			// 科目種別
			colum.add(47, "");
			value = colum.get(6);
			text = (String) kmkShuMap.get(value);
			colum.set(47, value);
			colum.set(6, text);

			// 貸借区分
			colum.add(48, "");
			value = colum.get(7);
			text = ("0".equals(value) ? this.getWord("C01125") : this.getWord("C01228"));
			colum.set(48, value);
			colum.set(7, text);

			// 補助区分
			colum.add(49, "");
			value = colum.get(8);
			text = (String) hkmKbnMap.get(value);
			colum.set(49, value);
			colum.set(8, inPut ? "" : text);

			// ＧＬ科目制御区分
			colum.add(50, "");
			value = kmkCntGl;
			text = (String) kmkCntGlMap.get(value);
			colum.set(50, value);
			colum.set(11, inPut ? "" : text);

			// AP科目制御区分
			colum.add(51, "");
			value = kmkCntAp;
			text = (String) kmkCntApMap.get(value);
			colum.set(51, value);
			colum.set(12, inPut ? "" : text);

			// AR科目制御区分
			colum.add(52, "");
			value = kmkCntAr;
			text = (String) kmkCntArMap.get(value);
			colum.set(52, value);
			colum.set(13, inPut ? "" : text);

			// BG科目制御区分
			colum.add(53, "");
			value = kmkCntBg;
			text = (String) kmkCntBgMap.get(value);
			colum.set(53, value);
			colum.set(14, inPut ? "" : text);

			// 取引先入力フラグ
			colum.add(54, "");
			value = triCodeFlg;
			text = (String) triCodeFlgMap.get(value);
			colum.set(54, value);
			colum.set(15, inPut ? "" : text);

			// 発生日入力ﾌﾗｸﾞ
			colum.add(55, "");
			value = hasFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(55, value);
			colum.set(16, inPut ? "" : text);

			// 相殺科目制御区分
			colum.add(56, "");
			value = kmkCntSousai;
			text = (String) kmkCntSousaiMap.get(value);
			colum.set(56, value);
			colum.set(17, inPut ? "" : text);

			// BS勘定消込区分
			colum.add(57, "");
			value = kesiKbn;
			text = ("0".equals(value) ? this.getWord("C02099") : this.getWord("C02100"));
			colum.set(57, value);
			colum.set(18, inPut ? "" : text);

			// 評価替対象フラグ
			colum.add(58, "");
			value = excFlg;
			text = (String) excFlgMap.get(value);
			colum.set(58, value);
			colum.set(19, inPut ? "" : text);

			// 入金伝票入力ﾌﾗｸﾞ
			colum.add(59, "");
			value = glFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(59, value);
			colum.set(20, inPut ? "" : text);

			// 出金伝票入力ﾌﾗｸﾞ
			colum.add(60, "");
			value = glFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(60, value);
			colum.set(21, inPut ? "" : text);

			// 振替伝票入力ﾌﾗｸﾞ
			colum.add(61, "");
			value = glFlg3;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(61, value);
			colum.set(22, inPut ? "" : text);

			// 経費精算伝票入力ﾌﾗｸﾞ
			colum.add(62, "");
			value = apFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(62, value);
			colum.set(23, inPut ? "" : text);

			// 請求書伝票入力ﾌﾗｸﾞ
			colum.add(63, "");
			value = apFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(63, value);
			colum.set(24, inPut ? "" : text);

			// 債権計上伝票入力ﾌﾗｸﾞ
			colum.add(64, "");
			value = arFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(64, value);
			colum.set(25, inPut ? "" : text);

			// 債権消込伝票入力ﾌﾗｸﾞ
			colum.add(65, "");
			value = atFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(65, value);
			colum.set(26, inPut ? "" : text);

			// 資産計上伝票入力ﾌﾗｸﾞ
			colum.add(66, "");
			value = faFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(66, value);
			colum.set(27, inPut ? "" : text);

			// 支払依頼伝票入力ﾌﾗｸﾞ
			colum.add(67, "");
			value = faFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(67, value);
			colum.set(28, inPut ? "" : text);

			// 多通貨入力フラグ
			colum.add(68, "");
			value = mcrFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(68, value);
			colum.set(29, inPut ? "" : text);

			String possible1 = this.getWord("C02371");
			// 社員入力フラグ
			colum.add(69, "");
			value = empCodeFlg;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(69, value);
			colum.set(30, inPut ? "" : text);

			// 管理１入力フラグ
			colum.add(70, "");
			value = knrFlg1;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(70, value);
			colum.set(31, inPut ? "" : text);

			// 管理2入力フラグ
			colum.add(71, "");
			value = knrFlg2;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(71, value);
			colum.set(32, inPut ? "" : text);

			// 管理3入力フラグ
			colum.add(72, "");
			value = knrFlg3;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(72, value);
			colum.set(33, inPut ? "" : text);

			// 管理4入力フラグ
			colum.add(73, "");
			value = knrFlg4;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(73, value);
			colum.set(34, inPut ? "" : text);

			// 管理5入力フラグ
			colum.add(74, "");
			value = knrFlg5;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(74, value);
			colum.set(35, inPut ? "" : text);

			// 管理6入力フラグ
			colum.add(75, "");
			value = knrFlg6;
			text = ("0".equals(value) ? impossible : possible1);
			colum.set(75, value);
			colum.set(36, inPut ? "" : text);

			// 非会計1入力フラグ
			colum.add(76, "");
			value = hmFlg1;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(76, value);
			colum.set(37, inPut ? "" : text);

			// 非会計2入力フラグ
			colum.add(77, "");
			value = hmFlg2;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(77, value);
			colum.set(38, inPut ? "" : text);

			// 非会計3入力フラグ
			colum.add(78, "");
			value = hmFlg3;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(78, value);
			colum.set(39, inPut ? "" : text);

			// 売上課税入力フラグ
			colum.add(79, "");
			value = uriZeiFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(79, value);
			colum.set(40, inPut ? "" : text);

			// 仕入課税入力フラグ
			colum.add(80, "");
			value = sirZeiFlg;
			text = ("0".equals(value) ? impossible : possible);
			colum.set(80, value);
			colum.set(41, inPut ? "" : text);

			try {
				colum.set(44, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(44))));
				colum.set(45, DateUtil.toYMDString(DateUtil.toYMDDate(colum.get(45))));
			} catch (ParseException e) {
				ClientLogger.error(this.getClass().getName() + ":" + colum.get(9), e);
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
			panel.ctrlBeginItem.getField().requestFocus();
			// 確認ダイアログ表示
			if (!showConfirmMessage(panel.getParentFrame(), "Q00011", new Object[0])) {
				return;
			}

			Map conds = new LinkedHashMap();
			// 送信するパラメータを設定
			conds.put("flag", "report");
			// 会社コードの取得
			conds.put("kaiCode", getLoginUserCompanyCode());
			// 開始コードの取得
			conds.put("beginKmkCode", panel.ctrlBeginItem.getValue());
			// 終了コードの取得
			conds.put("endKmkCode", panel.ctrlEndItem.getValue());
			conds.put("langCode", getLoginLanguage());
			this.download(panel, getServletName(), conds);
		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}
}
