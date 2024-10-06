package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 伝票種別マスタ画面Servlet (MG0330)
 * 
 * @author ISFnet China
 */
public class MG0330SlipTypeMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = -8074345142450019509L;

	@Override
	protected String getLogicClassName() {
		return "SlipTypeLogic";
	}

	protected String getREFKeyFields() {
		return "kaiCode";
	}

	/** 主キーの取得 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 伝票種別ｺｰﾄﾞの設定
		map.put("denSyuCode", req.getParameter("denSyuCode"));
		// 結果を返す
		return map;
	}

	/** 検索条件の取得 */
	protected Map getFindConditions(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 伝票種別ｺｰﾄﾞの設定
		map.put("denSyuCode", req.getParameter("denSyuCode"));
		// 開始コードの設定
		map.put("beginDenSyuCode", req.getParameter("beginDenSyuCode"));
		// 終了コードの設定
		map.put("endDenSyuCode", req.getParameter("endDenSyuCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) {
		// 実体の初期化
		DEN_SYU_MST denSyuMST = new DEN_SYU_MST();
		// 会社コードの設定
		denSyuMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 伝票種別ｺｰﾄﾞの設定
		denSyuMST.setDEN_SYU_CODE(req.getParameter("denSyuCode"));
		// システム区分 の設定
		denSyuMST.setSYS_KBN(req.getParameter("sysKbn"));
		// 伝票種別名称の設定
		denSyuMST.setDEN_SYU_NAME(req.getParameter("denSyuName"));
		// 伝票種別略称の設定
		denSyuMST.setDEN_SYU_NAME_S(req.getParameter("denSyuName_S"));
		// 帳票タイトルの設定
		denSyuMST.setDEN_SYU_NAME_K(req.getParameter("denSyuName_K"));
		// ﾃﾞｰﾀ区分の設定
		denSyuMST.setDATA_KBN(req.getParameter("dataKbn"));
		// 他ｼｽﾃﾑ区分の取得
		int taSysKbn = Integer.parseInt(req.getParameter("taSysKbn"));
		// 他ｼｽﾃﾑ区分の設定
		denSyuMST.setTA_SYS_KBN(taSysKbn);
		// 伝票番号採番ﾌﾗｸﾞの取得
		int datSaibanFlg = Integer.parseInt(req.getParameter("datSaiBanFlg"));
		// 伝票番号採番ﾌﾗｸﾞの設定
		denSyuMST.setDAT_SAIBAN_FLG(datSaibanFlg);
		// 受入単位の取得
		int tani = Integer.parseInt(req.getParameter("tani"));
		// 受入単位の設定
		denSyuMST.setTANI(tani);
		// 消費税計算区分の取得
		int zeiKbn = Integer.parseInt(req.getParameter("zeiKbn"));
		// 消費税計算区分の設定
		denSyuMST.setZEI_KBN(zeiKbn);
		// 仕訳インターフェース区分の取得
		int swkInKbn = Integer.parseInt(req.getParameter("swkInKbn"));
		// 仕訳インターフェース区分の設定
		denSyuMST.setSWK_IN_KBN(swkInKbn);
		// プログラムIDの設定
		denSyuMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return denSyuMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new SlipTypeMasterReportExcelDefine();
	}

	/**
	 * その他の場合
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// 判定用flag
		String flag = req.getParameter("flag");
		// 検索
		if ("refSlip".equals(flag)) {
			searchRefSlip(req, resp);
		}
	}

	/**
	 * 初期化種別データ取得する
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	private void searchRefSlip(HttpServletRequest req, HttpServletResponse resp) {

		// 会社コード
		String kaiCode = req.getParameter("kaiCode");

		// true:他システムから取り込んだ伝票種別を対象とする
		boolean isIncludeSystemEls = BooleanUtil.toBoolean(req.getParameter("isIncludeSystemEls"));

		S2Container s2Container = SingletonS2ContainerFactory.getContainer();

		SlipTypeLogic logic = (SlipTypeLogic) s2Container.getComponent(SlipTypeLogic.class);

		// 伝票種別略称リスト
		List lstDenSyuNames = logic.getDenSyuNames(kaiCode, isIncludeSystemEls);

		super.dispatchResultDtoList(req, resp, lstDenSyuNames);
	}

}
