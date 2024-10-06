package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.*;

/**
 * ���O�Ǘ�����
 * 
 * @author AIS
 */
public class LogManagerImpl extends TModel implements LogManager {

	public void log(Log log) throws TException {

		if (!Util.isNullOrEmpty(log.getInfo())) {
			// ���O�p��񂠂�
			ServerLogger.info(log.toString());
		} else {
			ServerLogger.debug(log.toString());
		}

		// DB�o�^�̏ꍇ
		if (ServerConfig.isExeLogDBMode()) {
			LogDao dao = (LogDao) getComponent(LogDao.class);
			dao.entry(log);
			// ���O�t�@�C���o�^�̏ꍇ
		} else {

			String companyCode = getCompanyCode();
			if (Util.isNullOrEmpty(companyCode)) {
				// �����s�v
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

		// �J�����̓`�F�b�N�s�v
		if (ServerConfig.isSystemRegulatedNumberCheck() && ServerConfig.isLoginManagedMode()) {

			// �T�u�V�X�e������
			String prgCode = Util.avoidNull(log.getProgram().getCode()); // �v���O�����R�[�h
			String msg = Util.avoidNull(log.getMessage()); // ���b�Z�[�W

			// �v���O�����J���A����^�C�~���O�Ŕ�����s��

			SystemManager systemMng = (SystemManager) getComponent(SystemManager.class);
			if (prgCode.equals("")) {
				// PG�R�[�hnull�A���b�Z�[�W��Login�ȊO��Logout�Ƃ���
				systemMng.closeAllProgram(log.getCompany().getCode(), log.getUser().getCode());

			} else if (msg.equals("IN")) {
				// �v���O�������J��
				systemMng.openProgram(log);

			} else if (msg.equals("OUT")) {
				systemMng.closeProgram(log.getCompany().getCode(), log.getUser().getCode(), prgCode);
			}
		}
	}
}
