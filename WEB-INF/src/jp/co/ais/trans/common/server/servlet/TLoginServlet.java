package jp.co.ais.trans.common.server.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;

/**
 * ログイン処理 ログアウト処理
 * 
 * @author AIS
 */
public final class TLoginServlet extends TServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 1543576010924868389L;

	/** 強制パスワード入力 */
	protected static final int PWD_CHK = 4;

	/** 強制ログイン（認証済み） */
	protected static final int PWD_UNCHK = 5;

	/**
	 * Post method 処理. <br>
	 * セッション認証しない.
	 * 
	 * @param req
	 * @param resp
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

		super.doPost(req, resp, false);
	}

	/**
	 * doPost()メソッドでPOST形式で送信されたデータを処理
	 */
	public void doMethodPost(HttpServletRequest req, HttpServletResponse resp) {

		try {
			String flag = Util.avoidNull(req.getParameter("flag"));

			if ("noticeLogin".equals(flag)) {
				// ログインログ
				logLogin(req, true);
				dispatchResultSuccess(req, resp);
				return;
			}

			login(req, resp);

		} catch (TException e) {

			dispatchJspErrorForLink(req, resp, e.getMessageID(), e.getMessageArgs());
		} catch (TRuntimeException e) {

			ServerErrorHandler.handledException(e);
			dispatchJspErrorForLink(req, resp, e.getMessageID(), e.getMessageArgs());
		} catch (Exception e) {

			ServerErrorHandler.handledException(e);
			dispatchJspErrorForLink(req, resp, "E00009");
		}
	}

	/**
	 * ログイン処理
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @throws IOException
	 * @throws ServletException
	 * @throws TException
	 */
	protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,
		TException {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		UserManager logic = (UserManager) container.getComponent("old-UserManager");

		String flagStr = req.getParameter("flag");

		if (Util.isNullOrEmpty(flagStr) || !Util.isNumber(flagStr.trim())) {
			ServerLogger.warning("access failed. " + "/login?flag=" + flagStr);

			// 情報が欠けている場合、トップページへ遷移
			getServletContext().getRequestDispatcher(TServerEnv.TOP_DIR.toString()).forward(req, resp);
			return;
		}

		// flag=0:ログイン flag=1:ログアウト
		int flag = Integer.parseInt(flagStr.trim());

		if (flag == 0 || flag == 3 || flag == PWD_CHK || flag == PWD_UNCHK) {
			// ログイン処理 ユーザ認証チェック、ログイン情報の書込、強制排他解除

			String kaiCode = req.getParameter("kaiCode");
			String usrCode = req.getParameter("usrCode");
			String password = req.getParameter("password");

			logic.setCompanyCode(kaiCode);
			logic.setUserCode(usrCode);

			if (flag == PWD_CHK) {
				UserPassword pwLogic = (UserPassword) container.getComponent(UserPassword.class);
				pwLogic.setCode(kaiCode, usrCode);
				req.setAttribute("kaiCode", kaiCode);
				req.setAttribute("usrCode", usrCode);
				req.setAttribute("alert.msg.brank", getMessage(req, "W00009", "C00597"));
				req.setAttribute("alert.msg.notEquals", getMessage(req, "W00074"));
				req.setAttribute("label.pwd", getWord(req, "C00742"));
				req.setAttribute("label.pwd.confirm", getWord(req, "C00305"));

				// パスワード有効期限切れメッセージ通知で「OK」を選択→パスワード更新画面に移動
				getServletContext().getRequestDispatcher(TServerEnv.TOP_DIR + "password.jsp").forward(req, resp);
				return;
			}

			if (flag != PWD_UNCHK) {
				if (flag == 0) {
					// ログイン画面から来た場合

					// ユーザ認証(存在、パスワード、ロックアウト)
					if (!logic.collatedUser(password)) {

						// ログイン認証失敗、対応するerrorMsg表示
						req.setAttribute("errorMsg", super.getMessage(req, logic.getErrorMsg()));

						// エラー付きトップページを開く
						getServletContext().getRequestDispatcher(TServerEnv.TOP_DIR + "index.jsp").forward(req, resp);

						return;
					}

					UserPassword pwLogic = (UserPassword) container.getComponent(UserPassword.class);
					pwLogic.setCode(kaiCode, usrCode);

					// パスワード有効期間のチェック(期間が過ぎた場合、パスワード更新画面を開く）
					if (pwLogic.isPasswordManaged() && pwLogic.isPasswordTermOrver()) {
						req.setAttribute("kaiCode", kaiCode);
						req.setAttribute("usrCode", usrCode);
						req.setAttribute("errorMsg", getMessage(req, "I00026")); // パスワード有効期間が過ぎています

						// 画面表示文字
						req.setAttribute("alert.msg.brank", getMessage(req, "W00009", "C00597"));
						req.setAttribute("alert.msg.notEquals", getMessage(req, "W00074"));
						req.setAttribute("label.pwd", getWord(req, "C00742"));
						req.setAttribute("label.pwd.confirm", getWord(req, "C00305"));

						// パスワード更新画面に移動
						getServletContext().getRequestDispatcher(TServerEnv.TOP_DIR + "password.jsp")
							.forward(req, resp);
						return;
					}

					// パスワード有効期限切れメッセージ通知のチェック
					if (!pwLogic.isPwdLimitMsgNotice()) {
						req.setAttribute("kaiCode", kaiCode);
						req.setAttribute("usrCode", usrCode);
						req.setAttribute("termMsg", getMessage(req, "Q90003", pwLogic.getPwdLimitMsg()));

						// パスワード有効期限切れメッセージ通知で「はい」を選択→パスワード更新画面に移動
						getServletContext().getRequestDispatcher(TServerEnv.TOP_DIR + "passwordmsg.jsp").forward(req,
							resp);
						return;

					}

				} else if (flag == 3) {
					// パスワード変更から来た場合
					UserPassword pwLogic = (UserPassword) container.getComponent(UserPassword.class);
					pwLogic.setCode(kaiCode, usrCode);

					// パスワード管理する場合は、各チェックを行う
					boolean assertPwd = !pwLogic.isPasswordManaged() || pwLogic.assertPassword(password);

					if (!assertPwd) {
						req.setAttribute("kaiCode", kaiCode);
						req.setAttribute("usrCode", usrCode);
						req.setAttribute("errorMsg", getMessage(req, pwLogic.getErrorMessageId(), pwLogic
							.getErrorArgs()));

						// 画面表示文字
						req.setAttribute("alert.msg.brank", getMessage(req, "W00009", "C00597"));
						req.setAttribute("alert.msg.notEquals", getMessage(req, "W00074"));
						req.setAttribute("label.pwd", getWord(req, "C00742"));
						req.setAttribute("label.pwd.confirm", getWord(req, "C00305"));

						getServletContext().getRequestDispatcher(TServerEnv.TOP_DIR + "password.jsp")
							.forward(req, resp);
						return;
					}

					// パスワード変更、履歴作成
					pwLogic.update(password, usrCode);
				}
			}

			// ログイン証明を元に、セッション証明を構築する
			initSession(req, new LoginCertification(kaiCode, usrCode));

			// ログインユーザ情報構築
			req.getSession().setAttribute(TServerEnv.SYS_PREFIX + "userinfo", logic.makeUserInfo());

			// ログイン処理
			logic.login();

			ServerLogger.debug("login:" + getSession(req).getId() + ", " + kaiCode + ", " + usrCode);

			// メインページを開く
			getServletContext().getRequestDispatcher(TServerEnv.TOP_DIR + "appletindex.jsp").forward(req, resp);

		} else {

			HttpSession session = req.getSession(false);

			// ログアウト処理 ログイン情報の消去 排他解除
			if (session != null) {
				String kaiCode = (String) session.getAttribute(TServerEnv.SYS_PREFIX + "ccd");
				String usrCode = (String) session.getAttribute(TServerEnv.SYS_PREFIX + "ucd");

				ServerLogger.debug("logout:" + session.getId() + ", " + kaiCode + ", " + usrCode);

				// ログアウトログ出力
				logLogin(req, false);

				// ロジックに渡すパラメータを設定
				logic.setCompanyCode(kaiCode);
				logic.setUserCode(usrCode);

				// ログアウト処理
				logic.logout();

				session.setAttribute(TServerEnv.SYS_PREFIX + "isLogout", true);

				// セッション情報破棄
				session.invalidate();
			}
		}
	}

	/**
	 * ログイン情報をセッションへ構築する
	 * 
	 * @param req Request
	 * @param cer ログイン証明クラス
	 */
	protected void initSession(HttpServletRequest req, LoginCertification cer) {
		if (req == null || cer == null) {
			throw new IllegalArgumentException("HttpServletRequest or LoginCertification is null.");
		}

		if (!cer.isCertified()) {
			throw new RuntimeException("not login authentication.");
		}

		HttpSession session = req.getSession(true);

		// 認証署名
		TServerCertification cret = new TServerCertification();
		cret.setCertified(true);
		session.setAttribute(TServerEnv.SYS_PREFIX + "certificationification", cret);

		// ログイン会社コード, ユーザコード
		session.setAttribute(TServerEnv.SYS_PREFIX + "ccd", cer.getCompanyCode());
		session.setAttribute(TServerEnv.SYS_PREFIX + "ucd", cer.getUserCode());
	}

	/**
	 * ログイン・ログアウトの実行ログ出力
	 * 
	 * @param req リクエスト
	 * @param isLogin true:ログイン、false:ログアウト
	 */
	protected void logLogin(HttpServletRequest req, boolean isLogin) {

		TUserInfo userInfo = refTServerUserInfo(req);

		if (userInfo == null) {
			return;
		}

		String userCode = userInfo.getUserCode();
		String userName = userInfo.getUserName();

		String ip = Util.avoidNull(req.getSession().getAttribute(TServerEnv.SYS_PREFIX + "ipadd"));

		// ログ出力
		ExecutedLogger logger = ExecutedLogger.getInstance(userInfo.getCompanyCode());

		if (isLogin) {
			logger.logLogin(userCode, userName, ip);

		} else {
			logger.logLogout(userCode, userName, ip);
		}
	}
}