package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.*;

/**
 * ログ管理実装
 * 
 * @author AIS
 */
public class LogManagerImpl extends TModel implements LogManager {

	public void log(Log log) throws TException {

		if (!Util.isNullOrEmpty(log.getInfo())) {
			// ログ用情報あり
			ServerLogger.info(log.toString());
		} else {
			ServerLogger.debug(log.toString());
		}

		// DB登録の場合
		if (ServerConfig.isExeLogDBMode()) {
			LogDao dao = (LogDao) getComponent(LogDao.class);
			dao.entry(log);
			// ログファイル登録の場合
		} else {

			String companyCode = getCompanyCode();
			if (Util.isNullOrEmpty(companyCode)) {
				// 処理不要
				return;
			}

			ExecutedLogger logger = ExecutedLogger.getInstance(Util.avoidNull(companyCode));
			if (Util.avoidNull(log.getProgram().getCode()).equals("")) {
				if (log.getMessage().equals("Login")) {
					logger.logLogin(log.getUser().getCode(), log.getUser().getName(), log.getIp());
				} else {
					logger.logLogout(log.getUser().getCode(), log.getUser().getName(), log.getIp());
				}
			} else {
				logger.log(log.getUser().getCode(), log.getUser().getName(), log.getIp(), log.getProgram().getCode(), log.getMessage());
			}
		}

		// 開発環境はチェック不要
		if (ServerConfig.isSystemRegulatedNumberCheck() && ServerConfig.isLoginManagedMode()) {

			// サブシステム判定
			String prgCode = Util.avoidNull(log.getProgram().getCode()); // プログラムコード
			String msg = Util.avoidNull(log.getMessage()); // メッセージ

			// プログラム開く、閉じるタイミングで判定を行う

			SystemManager systemMng = (SystemManager) getComponent(SystemManager.class);
			if (prgCode.equals("")) {
				// PGコードnull、メッセージがLogin以外はLogoutとする
				systemMng.closeAllProgram(log.getCompany().getCode(), log.getUser().getCode());

			} else if (msg.equals("IN")) {
				// プログラムを開く
				systemMng.openProgram(log);

			} else if (msg.equals("OUT")) {
				systemMng.closeProgram(log.getCompany().getCode(), log.getUser().getCode(), prgCode);
			}
		}
	}
}
