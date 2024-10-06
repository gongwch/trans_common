package jp.co.ais.trans2.common.ui;

import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.server.servlet.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans2.common.fw.*;
import jp.co.ais.trans2.common.fw.TContainer;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.program.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ログインのサーブレット
 * 
 * @author AIS
 */
public class TLoginServlet extends TServletBase {

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
	 * WEBアクセス時セッション情報を確立する.
	 */
	@Override
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) {
		try {

			String reqVersion = Util.avoidNull(req.getParameter("systemVersion"));

			ServerLogger.debug("systemVersion:" + version);
			ServerLogger.debug("reqVersion:" + reqVersion);

			// E00041">ご利用端末のシステムバージョンとサーバのシステムバージョンが異なります。\n起動中のアプリケーションを全て閉じた後、再度ログインを行ってください。
			if (!Util.isNullOrEmpty(reqVersion)) {
				if (!"local".equals(version) && !"local".equals(reqVersion)) {
					if (!version.equals(reqVersion)) {
						super.dispatchResultObject(req, resp, new TException("E00041"));
						return;
					}
				}
			}

			// イベントハンドリング
			String methodName = Util.avoidNull(req.getParameter("methodName"));
			List<Object> arg = (List<Object>) super.getObjectParameter(req, "methodArg");

			if ("authenticateUser".equals(methodName)) {

				String compCode = (String) arg.get(0);
				String userCode = (String) arg.get(1);
				String password = (String) arg.get(2);

				// ユーザ認証
				authenticateUser(compCode, userCode, password);

				// ログイン証明を元に、セッション証明を構築する
				String sessionID = initSession(req, new LoginCertification(compCode, userCode));

				// ログインユーザ情報構築(TRANS1.0用)
				S2Container container = SingletonS2ContainerFactory.getContainer();
				jp.co.ais.trans.logic.system.UserManager logic = (jp.co.ais.trans.logic.system.UserManager) container
					.getComponent("old-UserManager");
				logic.setCompanyCode(compCode);
				logic.setUserCode(userCode);

				// ログイン規定数チェック
				if (ServerConfig.getRegulatedNumber() > 0) {

					PCI_CHK_CTLDao pciDao = (PCI_CHK_CTLDao) container.getComponent(PCI_CHK_CTLDao.class);
					int currentCount = pciDao.getCount(compCode);

					// デバッグログ
					ServerLogger.debug("regulated number:" + ServerConfig.getRegulatedNumber() + "、current:"
						+ currentCount);

					if (currentCount >= ServerConfig.getRegulatedNumber()) {
						throw new TException("W01005"); // 規定数オーバー
					}
				}

				req.getSession().setAttribute(TServerEnv.SYS_PREFIX + "userinfo", logic.makeUserInfo());

				// ログイン処理
				entry(compCode, userCode);

				// ログイン処理終了
				ServerLogger.debug("login:" + getSession(req).getId() + ", " + compCode + ", " + userCode);

				dispatchResultObject(req, resp, sessionID);

			} else if ("canEntry".equals(methodName)) {

				// ロック判定

				UserExclusiveManager manager = (UserExclusiveManager) getContainer().getComponent(
					UserExclusiveManager.class);
				String companyCode = Util.avoidNull(arg.get(0));
				String userCode = Util.avoidNull(arg.get(1));
				String password = Util.avoidNull(arg.get(2));
				UserExclusive userExclusive = manager.canEntry(companyCode, userCode, password);
				dispatchResultObject(req, resp, userExclusive);

			} else if ("cancelExclude".equals(methodName)) {

				// 解除処理

				UserExclusiveManager manager = (UserExclusiveManager) getContainer().getComponent(
					UserExclusiveManager.class);
				String companyCode = Util.avoidNull(arg.get(0));
				String userCode = Util.avoidNull(arg.get(1));
				if (!Util.isNullOrEmpty(companyCode) && !Util.isNullOrEmpty(userCode)) {
					manager.cancelExclude(companyCode, userCode);
				}

				{
					// ログ生成
					Log log = new Log();

					// dummy会社
					Company company = new Company();
					company.setCode(companyCode);
					// dummyユーザ
					User user = new User();
					user.setCode(userCode);
					user.setName(getWord(req, "C30176")); // 強制解除

					log.setDate(Util.getCurrentDate());
					log.setCompany(company);
					log.setUser(user);
					Program program = new Program();
					program.setCode(null);
					log.setProgram(program);
					try {
						log.setIp(req.getRemoteAddr());
					} catch (Exception e) {
						log.setIp("Unknown");
					}
					log.setMessage("LOGOUT");

					LogManagerImpl logger = (LogManagerImpl) getContainer().getComponent(LogManager.class);
					logger.setCompany(company);
					logger.setUser(user);
					logger.setNow(log.getDate());
					logger.log(log);
				}

				dispatchResultSuccess(req, resp);

			}

		} catch (Exception e) {
			dispatchError(req, resp, e);
		}
	}

	/**
	 * ユーザー認証
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザコード
	 * @param password パスワード
	 * @throws TException
	 */
	public void authenticateUser(String companyCode, String userCode, String password) throws TException {
		TContainer container = TContainerFactory.getContainer();
		UserAuthenticater ua = (UserAuthenticater) container.getComponent(UserAuthenticater.class);
		ua.authenticateUser(companyCode, userCode, password);
	}

	/**
	 * ログイン処理
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザコード
	 * @throws TException
	 */
	public void entry(String companyCode, String userCode) throws TException {
		TContainer container = TContainerFactory.getContainer();
		SystemManager manager = (SystemManager) container.getComponent(SystemManager.class);
		manager.entryUser(companyCode, userCode);
	}

	/**
	 * ログイン情報をセッションへ構築する
	 * 
	 * @param req Request
	 * @param cer ログイン証明クラス
	 * @return セッションID
	 */
	protected String initSession(HttpServletRequest req, LoginCertification cer) {
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

		return session.getId();
	}
}
