package jp.co.ais.trans.master.ui.servlet;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 会社コントロールマスタ画面Servlet (MG0030)
 * 
 * @author ISFnet China
 */
public class MG0030CompanyControlMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 2216242694312678519L;

	@Override
	protected String getLogicClassName() {
		return "CompanyControlLogic";
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
		// プログラムコード
		map.put("prgCode", req.getParameter("prgCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) {
		// 実体の初期化
		CMP_MST cmpMST = new CMP_MST();
		// 会社コードの設定
		cmpMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 科目名称の設定
		cmpMST.setCMP_KMK_NAME(req.getParameter("cmpKmkName"));
		// 補助科目名称の設定
		cmpMST.setCMP_HKM_NAME(req.getParameter("cmpHkmName"));
		// 内訳科目名称の設定
		cmpMST.setCMP_UKM_NAME(req.getParameter("cmpUkmName"));
		// 管理名称1の設定
		cmpMST.setKNR_NAME_1(req.getParameter("knrName1"));
		// 管理名称2の設定
		cmpMST.setKNR_NAME_2(req.getParameter("knrName2"));
		// 管理名称3の設定
		cmpMST.setKNR_NAME_3(req.getParameter("knrName3"));
		// 管理名称4の設定
		cmpMST.setKNR_NAME_4(req.getParameter("knrName4"));
		// 管理名称5の設定
		cmpMST.setKNR_NAME_5(req.getParameter("knrName5"));
		// 管理名称6の設定
		cmpMST.setKNR_NAME_6(req.getParameter("knrName6"));
		// 非会計明細名称1の設定
		cmpMST.setCMP_HM_NAME_1(req.getParameter("cmpHmName1"));
		// 非会計明細名称2の設定
		cmpMST.setCMP_HM_NAME_2(req.getParameter("cmpHmName2"));
		// 非会計明細名称3の設定
		cmpMST.setCMP_HM_NAME_3(req.getParameter("cmpHmName3"));
		// 自動設定項目１の設定
		cmpMST.setJID_1(req.getParameter("jid1"));
		// 自動設定項目2の設定
		cmpMST.setJID_2(req.getParameter("jid2"));
		// 自動設定項目3の設定
		cmpMST.setJID_3(req.getParameter("jid3"));
		// 本邦通貨コードの設定
		cmpMST.setCUR_CODE(req.getParameter("curCode"));
		// 内訳科目管理の取得
		int cmpUkmKbn = Integer.parseInt(req.getParameter("cmpUkmKbn"));
		// 管理区分1の取得
		int knrKbn1 = Integer.parseInt(req.getParameter("knrKbn1"));
		// 管理区分2の取得
		int knrKbn2 = Integer.parseInt(req.getParameter("knrKbn2"));
		// 管理区分3の取得
		int knrKbn3 = Integer.parseInt(req.getParameter("knrKbn3"));
		// 管理区分4の取得
		int knrKbn4 = Integer.parseInt(req.getParameter("knrKbn4"));
		// 管理区分5の取得
		int knrKbn5 = Integer.parseInt(req.getParameter("knrKbn5"));
		// 管理区分6の取得
		int knrKbn6 = Integer.parseInt(req.getParameter("knrKbn6"));
		// 非会計明細区分1の取得
		int cmpHmKbn1 = Integer.parseInt(req.getParameter("cmpHmKbn1"));
		// 非会計明細区分2の取得
		int cmpHmKbn2 = Integer.parseInt(req.getParameter("cmpHmKbn2"));
		// 非会計明細区分3の取得
		int cmpHmKbn3 = Integer.parseInt(req.getParameter("cmpHmKbn3"));
		// 期首月の取得
		Integer cmpKisyu = new Integer(Integer.parseInt(req.getParameter("cmpKisyu")));
		// 自動採番部桁数の取得
		int autoNoKeta = Integer.parseInt(req.getParameter("autoNoKeta"));
		// 伝票印刷区分の取得
		int printKbn = Integer.parseInt(req.getParameter("printKbn"));
		// 印刷時の初期値の取得
		int printDef = Integer.parseInt(req.getParameter("printDef"));
		// 現場承認ﾌﾗｸﾞの取得
		int cmpGShoninFlg = Integer.parseInt(req.getParameter("cmpGShoninFlg"));
		// 経理承認ﾌﾗｸﾞの取得
		int cmpKShoninFlg = Integer.parseInt(req.getParameter("cmpKShoninFlg"));
		// レート換算端数処理の取得
		int rateKbn = Integer.parseInt(req.getParameter("rateKbn"));
		// 仮受消費税端数処理の取得
		int kuKbn = Integer.parseInt(req.getParameter("kuKbn"));
		// 仮払消費税端数処理の取得
		int kbKbn = Integer.parseInt(req.getParameter("kbKbn"));
		// 直接印刷区分
		int dirKbn = Util.avoidNullAsInt(req.getParameter("directKbn"));
		// 内訳科目管理の設定
		cmpMST.setCMP_UKM_KBN(cmpUkmKbn);
		// 管理区分1の設定
		cmpMST.setKNR_KBN_1(knrKbn1);
		// 管理区分2の設定
		cmpMST.setKNR_KBN_2(knrKbn2);
		// 管理区分3の設定
		cmpMST.setKNR_KBN_3(knrKbn3);
		// 管理区分4の設定
		cmpMST.setKNR_KBN_4(knrKbn4);
		// 管理区分5の設定
		cmpMST.setKNR_KBN_5(knrKbn5);
		// 管理区分6の設定
		cmpMST.setKNR_KBN_6(knrKbn6);
		// 非会計明細区分1の設定
		cmpMST.setCMP_HM_KBN_1(cmpHmKbn1);
		// 非会計明細区分2の設定
		cmpMST.setCMP_HM_KBN_2(cmpHmKbn2);
		// 非会計明細区分3の設定
		cmpMST.setCMP_HM_KBN_3(cmpHmKbn3);
		// 期首月の設定
		cmpMST.setCMP_KISYU(cmpKisyu);
		// 自動採番部桁数の設定
		cmpMST.setAUTO_NO_KETA(autoNoKeta);
		// 伝票印刷区分の設定
		cmpMST.setPRINT_KBN(printKbn);
		// 印刷時の初期値の設定
		cmpMST.setPRINT_DEF(printDef);
		// 現場承認ﾌﾗｸﾞの設定
		cmpMST.setCMP_G_SHONIN_FLG(cmpGShoninFlg);
		// 経理承認ﾌﾗｸﾞの設定
		cmpMST.setCMP_K_SHONIN_FLG(cmpKShoninFlg);
		// レート換算端数処理の設定
		cmpMST.setRATE_KBN(rateKbn);
		// 仮受消費税端数処理の設定
		cmpMST.setKU_KBN(kuKbn);
		// 仮払消費税端数処理の設定
		cmpMST.setKB_KBN(kbKbn);
		// プログラムIDの設定
		cmpMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return cmpMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new CompanyControlMasterReportExcelDefine("");
	}

	/**
	 * Excelリスト作成
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 * @throws IOException
	 * @throws TException
	 */
	protected void report(HttpServletRequest req, HttpServletResponse resp) throws ParseException, IOException,
		TException {

		String langCode = req.getParameter("langCode");

		S2Container container = SingletonS2ContainerFactory.getContainer();
		CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());

		Map conditions = this.getFindConditions(req);
		List list = logic.find(conditions);

		if (list == null || list.isEmpty()) {
			// データがありません。
			throw new TException("W00100");
		}

		if (list.size() >= 65535) {
			throw new TException("W00213");
		}

		ReportExcelDefine red = new CompanyControlMasterReportExcelDefine(Util.avoidNull(req.getParameter("prgCode")));

		// 会社コードをセット
		red.setKaiCode(req.getParameter("kaiCode"));
		// 言語コードをセット
		red.setLangCode(langCode);

		OutputUtil util = new OutputUtil(red, langCode);
		util.createExcel(list);
		byte[] bytes = util.getBinary();

		super.dispatchExcel(req, resp, red.getFileName(), bytes);
	}

	/**
	 * その他の場合
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// 判定用flag
		String flag = req.getParameter("flag");
		// 検索
		if ("refControl".equals(flag)) {
			searchRefControl(req, resp);

			// コントロール情報取得
		} else if ("searchByKaiCode".equals(flag)) {
			searchByKaiCode(req, resp);
		}
	}

	/**
	 * 会社コードでコントロール情報を取得(LIST)
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchRefControl(HttpServletRequest req, HttpServletResponse resp) {
		// containerの初期化
		S2Container container = null;

		// flag: "checkNaiCode"
		try {

			container = SingletonS2ContainerFactory.getContainer();
			// logicの初期化
			CompanyControlLogic logic = (CompanyControlLogic) container.getComponent(CompanyControlLogic.class);
			// keysの初期化
			Map keys = new HashMap();
			keys.put("kaiCode", req.getParameter("kaiCode"));
			// 実体の初期化
			List list = logic.getREFItems(keys);

			super.dispatchResultList(req, resp, list);

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	/**
	 * 会社コードでコントロール情報を取得(DTO)
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchByKaiCode(HttpServletRequest req, HttpServletResponse resp) {
		// containerの初期化
		S2Container container = null;

		try {

			container = SingletonS2ContainerFactory.getContainer();
			// logicの初期化
			CompanyControlLogic logic = (CompanyControlLogic) container.getComponent(CompanyControlLogic.class);
			// keysの初期化
			Map keys = new HashMap();
			keys.put("kaiCode", req.getParameter("kaiCode"));
			// 実体の初期化
			CMP_MST cmp = (CMP_MST) logic.findOne(keys);

			super.dispatchResultDto(req, resp, cmp);

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

}
