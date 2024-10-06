package jp.co.ais.trans.master.ui.servlet;

import java.math.*;
import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 支払方針マスタ画面Servlet (MP0050)
 * 
 * @author ISFnet China
 */
public class MP0050PaymentPolicyMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = -3113517618787449938L;

	@Override
	protected String getLogicClassName() {
		return "PaymentPolicyLogic";
	}

	protected String getREFKeyFields() {
		return null;
	}

	/** 主キーの取得 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 結果を返す
		return map;
	}

	/** 検索条件の取得 */
	protected Map getFindConditions(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 手数料科目コードの設定
		map.put("kmkCode", req.getParameter("kmkCode"));
		// 手数料補助科目コードの設定
		map.put("hkmCode", req.getParameter("hkmCode"));
		// 手数料内訳科目コードの設定
		map.put("ukmCode", req.getParameter("ukmCode"));
		// 手数料計上部門コードの設定
		map.put("hohDepCode", req.getParameter("hohDepCode"));
		// 手数料消費税コードの設定
		map.put("shhTesuZeiCode", req.getParameter("shhTesuZeiCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		AP_SHH_MST apShhMST = new AP_SHH_MST();
		// 会社コードの設定
		apShhMST.setKAI_CODE( req.getParameter("kaiCode"));
		// 手数料科目コード の設定
		apShhMST.setSHH_TESU_KMK_CODE( req.getParameter("shhTesuKmkCode"));
		// 手数料補助科目コードの設定
		apShhMST.setSHH_TESU_HKM_CODE( req.getParameter("shhTesuHkmCode"));
		// 手数料内訳科目コードの設定
		apShhMST.setSHH_TESU_UKM_CODE( req.getParameter("shhTesuUkmCode"));
		// 手数料計上部門コードの設定
		apShhMST.setSHH_TESU_DEP_CODE( req.getParameter("shhTesuDepCode"));
		// 手数料消費税コードの設定
		apShhMST.setSHH_TESU_ZEI_CODE( req.getParameter("shhTesuZeiCode"));
		// 外国送金作成フラグの設定
		apShhMST.setSHH_GS_PRINT_KBN( req.getParameter("shhGsPrintKbn"));
		// 請求書番号フラグの設定
		apShhMST.setSHH_SEI_NO_KBN( req.getParameter("shhSeiNoKbn"));
		// 定時支払（１日）の取得
		int shhSiha1 = Integer.parseInt( req.getParameter("shhSiha1"));
		// 定時支払（5日）の取得
		int shhSiha5 = Integer.parseInt( req.getParameter("shhSiha5"));
		// 定時支払（10日）の取得
		int shhSiha10 = Integer.parseInt( req.getParameter("shhSiha10"));
		// 定時支払（15日）の取得
		int shhSiha15 = Integer.parseInt( req.getParameter("shhSiha15"));
		// 定時支払（20日）の取得
		int shhSiha20 = Integer.parseInt( req.getParameter("shhSiha20"));
		// 定時支払（25日）の取得
		int shhSiha25 = Integer.parseInt( req.getParameter("shhSiha25"));
		// 定時支払（末日）の取得
		int shhSiha30 = Integer.parseInt( req.getParameter("shhSiha30"));
		// 銀行休日区分（1日）の取得
		int shhBnkKbn1 = Integer.parseInt( req.getParameter("shhBnkKbn1"));
		// 銀行休日区分（5日）の取得
		int shhBnkKbn5 = Integer.parseInt( req.getParameter("shhBnkKbn5"));
		// 銀行休日区分（10日）の取得
		int shhBnkKbn10 = Integer.parseInt( req.getParameter("shhBnkKbn10"));
		// 銀行休日区分（15日）の取得
		int shhBnkKbn15 = Integer.parseInt( req.getParameter("shhBnkKbn15"));
		// 銀行休日区分（20日）の取得
		int shhBnkKbn20 = Integer.parseInt( req.getParameter("shhBnkKbn20"));
		// 銀行休日区分（25日）の取得
		int shhBnkKbn25 = Integer.parseInt( req.getParameter("shhBnkKbn25"));
		// 銀行休日区分（末日）の取得
		int shhBnkKbn30 = Integer.parseInt( req.getParameter("shhBnkKbn30"));
		// 支払下限額の取得
		BigDecimal shhSihaMin = new BigDecimal( req.getParameter("shhSihaMin"));
		// 振込手数料下限額の取得
		BigDecimal shhFuriMin = new BigDecimal( req.getParameter("shhFuriMin"));
		// 定時支払（１日）の設定
		apShhMST.setSHH_SIHA_1(shhSiha1);
		// 定時支払（5日）の設定
		apShhMST.setSHH_SIHA_5(shhSiha5);
		// 定時支払（10日）の設定
		apShhMST.setSHH_SIHA_10(shhSiha10);
		// 定時支払（15日）の設定
		apShhMST.setSHH_SIHA_15(shhSiha15);
		// 定時支払（20日）の設定
		apShhMST.setSHH_SIHA_20(shhSiha20);
		// 定時支払（25日）の設定
		apShhMST.setSHH_SIHA_25(shhSiha25);
		// 定時支払（末日）の設定
		apShhMST.setSHH_SIHA_30(shhSiha30);
		// 銀行休日区分（1日）の設定
		apShhMST.setSHH_BNK_KBN_1(shhBnkKbn1);
		// 銀行休日区分（5日）の設定
		apShhMST.setSHH_BNK_KBN_5(shhBnkKbn5);
		// 銀行休日区分（10日）の設定
		apShhMST.setSHH_BNK_KBN_10(shhBnkKbn10);
		// 銀行休日区分（15日）の設定
		apShhMST.setSHH_BNK_KBN_15(shhBnkKbn15);
		// 銀行休日区分（20日）の設定
		apShhMST.setSHH_BNK_KBN_20(shhBnkKbn20);
		// 銀行休日区分（25日）の設定
		apShhMST.setSHH_BNK_KBN_25(shhBnkKbn25);
		// 銀行休日区分（末日）の設定
		apShhMST.setSHH_BNK_KBN_30(shhBnkKbn30);
		// 支払下限額の設定
		apShhMST.setSHH_SIHA_MIN(shhSihaMin);
		// 振込手数料下限額の設定
		apShhMST.setSHH_FURI_MIN(shhFuriMin);
		// 開始年月日の初期化
		Date shhInpDate = null;
		// 終了年月日の初期化
		Date updDate = null;
		// 開始年月日の取得
		shhInpDate = DateUtil.toYMDDate( req.getParameter("shhInpDate"));
		// 終了年月日の取得
		updDate = DateUtil.toYMDDate( req.getParameter("updDate"));
		// 開始年月日の設定
		apShhMST.setSHH_INP_DATE(shhInpDate);
		// 終了年月日の設定
		apShhMST.setUPD_DATE(updDate);
		// プログラムIDの設定
		apShhMST.setPRG_ID( req.getParameter("prgID"));
		// 結果を返す
		return apShhMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return null;
	}
}
