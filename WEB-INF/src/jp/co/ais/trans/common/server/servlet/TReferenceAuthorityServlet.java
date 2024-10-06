package jp.co.ais.trans.common.server.servlet;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.server.di.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 共通サーブレット
 */
public class TReferenceAuthorityServlet extends TServletBase {

	/** UID */
	private static final long serialVersionUID = -5319125982776071994L;

	/**
	 * リクエストを処理する
	 * 
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 * @throws ServletException
	 */
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

		try {
			// 判定用flag
			String flag = req.getParameter("FLAG");

			// 初期情報取得
			if ("INIT".equals(flag)) {
				init(req, resp);
			} else if ("DEPARTMENT".equals(flag)) {
				departmentName(req, resp);
			} else if ("PERSON".equals(flag)) {
				// 入力者略称取得する
				this.empName(req, resp);
			}

		} catch (Exception e) {
			handledException(e, req, resp);
		}
	}

	/**
	 * 入力者略称検索
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	private void empName(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		TReferenceAuthorityLogic logic = (TReferenceAuthorityLogic) container
			.getComponent(TReferenceAuthorityLogic.class);

		// 会社コード
		String kaiCode = Util.avoidNull(req.getParameter("KAI_CODE"));
		// 所属部門コード
		String depCode = Util.avoidNull(req.getParameter("BMN_CODE"));
		// テキストフィールドに文字列
		String empCode = Util.avoidNull(req.getParameter("EMP_CODE"));

		// 会社コード、テキストフィールドに文字列からデータがあるかを検索
		EMP_MST cur = logic.getEMP_MSTByKaiCodeEmpCode(kaiCode, empCode, depCode);

		Map<String, Object> map = new HashMap<String, Object>();
		// データがある場合
		if (cur != null) {
			map.put("existFlag", "1");
			map.put("EMP_NAME_S", Util.avoidNull(cur.getEMP_NAME_S()));
		} else {
			map.put("existFlag", "0");
			map.put("EMP_NAME_S", "");
		}

		dispatchResultMap(req, resp, map);
	}

	/**
	 * 入力部門略称検索
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	private void departmentName(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		TReferenceAuthorityLogic logic = (TReferenceAuthorityLogic) container
			.getComponent(TReferenceAuthorityLogic.class);

		// 会社コード
		String kaiCode = Util.avoidNull(req.getParameter("KAI_CODE"));
		// テキストフィールドに文字列
		String bmnCode = Util.avoidNull(req.getParameter("BMN_CODE"));

		// 会社コード、テキストフィールドに文字列からデータがあるかを検索
		BMN_MST bmnMst = logic.getBMN_MSTByKaicodeDepcode(kaiCode, bmnCode);

		Map<String, Object> map = new HashMap<String, Object>();

		// データがある場合
		if (bmnMst != null) {
			map.put("existFlag", "1");
			map.put("DEP_NAME_S", Util.avoidNull(bmnMst.getDEP_NAME_S()));
		} else {
			map.put("existFlag", "0");
			map.put("DEP_NAME_S", "");
		}
		dispatchResultMap(req, resp, map);

	}

	/**
	 * 初期データを取得する
	 * 
	 * @param req
	 * @param resp
	 */
	private void init(HttpServletRequest req, HttpServletResponse resp) {
		TUserInfo userInfo = this.refTServerUserInfo(req);

		// 会社コード
		String kaiCode = userInfo.getCompanyCode();
		String usrCode = userInfo.getUserCode();

		Map<String, Object> map = new TreeMap<String, Object>();

		// 初期化のデーターを取得する
		S2Container s2Container = SingletonS2ContainerFactory.getContainer();

		TReferenceAuthorityLogic logic = (TReferenceAuthorityLogic) s2Container
			.getComponent(TReferenceAuthorityLogic.class);

		String depCode = "";
		String empCode = "";

		// ユーザーマスタを取得
		USR_MST usrMst = logic.getUSR_MSTByKaicodeUsercode(kaiCode, usrCode);

		if (usrMst != null) {

			// 更新権限レベル
			map.put("updKen", Util.avoidNull(usrMst.getUPD_KEN()));
			// ユーザーの所属部門コード
			depCode = Util.avoidNull(usrMst.getDEP_CODE());
			// ログインユーザーの社員コード
			empCode = Util.avoidNull(usrMst.getEMP_CODE());

			// 部門コード
			map.put("bmnCode", Util.avoidNull(depCode));
			// 社員コード
			map.put("empCode", Util.avoidNull(empCode));

		}

		// 社員マスタを取得
		EMP_MST empMst = logic.getEMP_MSTByKaiCodeEmpCode(kaiCode, empCode, depCode);

		if (empMst != null) {
			// 社員略称
			map.put("empNameS", Util.avoidNull(empMst.getEMP_NAME_S()));
		} else {
			// 社員略称
			map.put("empNameS", "");
		}

		// 部門マスタを取得
		BMN_MST bmnMst = logic.getBMN_MSTByKaicodeDepcode(kaiCode, depCode);

		if (bmnMst != null) {
			// 部門略称
			map.put("bmnNameS", Util.avoidNull(bmnMst.getDEP_NAME_S()));
		} else {
			map.put("bmnNameS", "");
		}

		dispatchResultMap(req, resp, map);
	}
}
