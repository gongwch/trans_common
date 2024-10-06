package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * ユーザーマスタ画面Servlet (MG0020)
 * 
 * @author ISFnet China
 */
public class MG0020UserMasterServlet extends MasterServletBase {

	/** シリアルUID */
	static final long serialVersionUID = 3686838932095874002L;

	@Override
	protected String getLogicClassName() {
		return "UserLogic";
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
		// ユーザーコードの設定
		map.put("usrCode", req.getParameter("usrCode"));
		// 結果を返す
		return map;
	}

	/** 検索条件の取得 */
	protected Map getFindConditions(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 開始コードの設定
		map.put("beginUsrCode", req.getParameter("beginUsrCode"));
		// 終了コードの設定
		map.put("endUsrCode", req.getParameter("endUsrCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		USR_MST usrMST = new USR_MST();

		//
		usrMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 会社コードの設定
		usrMST.setUSR_CODE(req.getParameter("usrCode"));
		// ユーザーコードの設定
		usrMST.setPASSWORD(req.getParameter("password"));
		// パスワードの設定
		usrMST.setLNG_CODE(req.getParameter("lngCode"));
		// 言語コードの設定
		usrMST.setUSR_NAME(req.getParameter("usrName"));
		// ユーザー名称の設定
		usrMST.setUSR_NAME_S(req.getParameter("usrName_S"));
		// ユーザー略称の設定
		usrMST.setUSR_NAME_K(req.getParameter("usrName_k"));
		// システム区分の取得
		String sysKbn = req.getParameter("sysKbn");
		// システム区分の設定
		usrMST.setSYS_KBN(sysKbn);
		// 処理権限レベルの取得
		int prcKen = Integer.parseInt(req.getParameter("prcKen"));
		// 処理権限レベルの設定
		usrMST.setPRC_KEN(prcKen);
		// 社員コードの設定
		usrMST.setEMP_CODE(req.getParameter("empCode"));
		// 所属部門コードの設定
		usrMST.setDEP_CODE(req.getParameter("depCode"));
		// 経理担当者区分の取得
		int keiriKbn = Integer.parseInt(req.getParameter("keiriKbn"));
		// 経理担当者区分の設定
		usrMST.setKEIRI_KBN(keiriKbn);
		// 更新権限レベルの取得
		int updKen = Integer.parseInt(req.getParameter("updKen"));
		// 更新権限レベルの設定
		usrMST.setUPD_KEN(updKen);
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		usrMST.setSTR_DATE(strDate);
		// 終了年月日の設定
		usrMST.setEND_DATE(endDate);
		// プログラムIDの設定
		usrMST.setPRG_ID(req.getParameter("prgID")); // プログラムID
		// パスワード更新日付設定
		Date pwdDate = null;
		pwdDate = DateUtil.toYMDDate(req.getParameter("pwdDate"));
		usrMST.setPWD_UPD_DATE(pwdDate);
		// 結果を返す
		return usrMST;
	}

	/**
	 * その他の場合
	 * 
	 * @throws ParseException
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) throws ParseException {

		String flag = req.getParameter("flag");

		// ユーザ検索
		if ("refEmployee".equals(flag)) {
			searchRefUser(req, resp);

			// パスワード登録及び更新
		} else if ("passwordupdate".equals(flag)) {
			updatePassword(req, resp);

			// パスワードのチェックだけを行う
		} else if ("passCheck".equals(flag)) {
			passwordCheck(req, resp);

		} else if ("insertPwd".equals(flag)) {
			insertPwdHistory(req, resp);

		} else if ("searchUsrList".equals(flag)) {
			searchWithKaiCode(req, resp);

		} else if ("updateUsrData".equals(flag)) {
			updateUsrData(req, resp);
		}

	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new UserMasterReportExcelDefine();
	}

	/**
	 * 更新ユーザマスタ
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void updateUsrData(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		// old会社コード
		String oldKaiCode = req.getParameter("oldKaiCode");

		USR_MST entity = (USR_MST) this.getEntity(req);
		entity.setUPD_DATE(Util.getCurrentDate());
		entity.setUSR_ID(this.refTServerUserInfo(req).getUserCode());

		S2Container container = SingletonS2ContainerFactory.getContainer();
		UserLogic logic = (UserLogic) container.getComponent(UserLogic.class);

		logic.updateUsrMst(entity, oldKaiCode);

		// 結果の設定
		super.dispatchResultSuccess(req, resp);
	}

	/**
	 * パスワード更新
	 * 
	 * @param req
	 * @param resp
	 */
	private void updatePassword(HttpServletRequest req, HttpServletResponse resp) {

		String newPassword = req.getParameter("newPassword"); // 新しいパスワード
		String prgID = req.getParameter("prgID"); // プログラムID

		S2Container container = SingletonS2ContainerFactory.getContainer();

		TUserInfo userInfo = refTServerUserInfo(req);
		UserPassword logic = (UserPassword) container.getComponent(UserPassword.class);
		logic.setCode(userInfo.getCompanyCode(), userInfo.getUserCode());

		// 複雑レベル、及びパスワード長さ条件を満たしていない場合
		boolean assertPwd = !logic.isPasswordManaged() || logic.assertPassword(newPassword);

		if (!assertPwd) {
			dispatchError(req, resp, logic.getErrorMessageId(), logic.getErrorArgs());
			return;
		}

		// データを更新する
		logic.update(newPassword, prgID);

		// 結果の設定
		super.dispatchResultSuccess(req, resp);
	}

	/**
	 * ユーザ検索 <br>
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchRefUser(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		UserLogic logic = (UserLogic) container.getComponent(UserLogic.class);

		// keysの初期化
		Map keys = new HashMap();
		keys.put("kaiCode", req.getParameter("kaiCode"));
		keys.put("usrCode", req.getParameter("usrCode"));

		// ユーザリスト習得
		List list = logic.getREFItems(keys);

		super.dispatchResultList(req, resp, list);

	}

	/**
	 * パスワードだけを渡し、検証を行う<br>
	 * 結果としてエラーメッセージを返信するか、検証成功を知らせる
	 * 
	 * @param req
	 * @param resp
	 */
	private void passwordCheck(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		TUserInfo userInfo = refTServerUserInfo(req);
		UserPassword logic = (UserPassword) container.getComponent(UserPassword.class);
		logic.setCode(userInfo.getCompanyCode(), userInfo.getUserCode());

		// パスワードの取得
		String newPassword = req.getParameter("password");

		// 複雑レベル、及びパスワード長さ条件を満たしていない場合
		boolean assertPwd = !logic.isPasswordManaged() || logic.assertPassword(newPassword);

		if (!assertPwd) {
			dispatchError(req, resp, logic.getErrorMessageId(), logic.getErrorArgs());
			return;
		}

		super.dispatchResultSuccess(req, resp);
	}

	/**
	 * パスワード履歴新規作成
	 * 
	 * @param req
	 * @param resp
	 */
	private void insertPwdHistory(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		PasswordHistoryLogic logic = (PasswordHistoryLogic) container.getComponent(PasswordHistoryLogic.class);
		UserAuthLogic usrAuthLogic = (UserAuthLogic) container.getComponent(UserAuthLogic.class);
		TUserInfo usrInfo = refTServerUserInfo(req);
		USR_AUTH_MST usrDto = usrAuthLogic.find(usrInfo.getCompanyCode());
		// ユーザ認証マスタが存在すると初期履歴を書く
		if (usrDto != null) {
			PWD_HST_TBL pwdDto = new PWD_HST_TBL();
			pwdDto.setKAI_CODE(req.getParameter("kaiCode"));
			pwdDto.setUSR_CODE(req.getParameter("usrCode"));
			pwdDto.setPASSWORD(req.getParameter("password"));
			pwdDto.setINP_DATE(Util.getCurrentDate());

			logic.insert(pwdDto);
		}

		super.dispatchResultSuccess(req, resp);

	}

	/**
	 * 会社別ユーザリスト取得
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchWithKaiCode(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		UserLogic logic = (UserLogic) container.getComponent(UserLogic.class);
		TUserInfo userInfo = refTServerUserInfo(req);

		// 会社コードでユーザリストを習得
		List dtoList = logic.searchUsrList(userInfo.getCompanyCode());
		super.dispatchResultDtoList(req, resp, dtoList);

	}
}
