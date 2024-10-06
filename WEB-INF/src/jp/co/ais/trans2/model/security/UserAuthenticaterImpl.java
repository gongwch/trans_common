package jp.co.ais.trans2.model.security;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ���[�U�[�F�؃C���^�[�t�F�[�X����
 * 
 * @author AIS
 */
public class UserAuthenticaterImpl extends TModel implements UserAuthenticater {

	/**
	 * ���[�U�[�F��
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param password �p�X���[�h
	 */
	public boolean authenticateUser(String companyCode, String userCode, String password) throws TWarningException,
		TException {

		// �p�X���[�h�|���V�[�擾
		PasswordManager pm = (PasswordManager) getComponent(PasswordManager.class);
		PasswordPolicy pp = pm.getPasswordPolicy(companyCode);

		// ���[�U�[��������Ȃ��ꍇ�G���[
		UserManager um = (UserManager) getComponent(UserManager.class);
		UserSearchCondition condition = new UserSearchCondition();
		condition.setCompanyCode(companyCode);
		condition.setCode(userCode);
		List<User> list = um.get(condition);

		if (list == null || list.isEmpty()) {
			throw new TWarningException("I00156");// ���͏��Ɍ�肪����܂��B
		}

		User user = list.get(0);

		if (user == null) {
			throw new TWarningException("I00156");// ���͏��Ɍ�肪����܂��B
		}

		// �p�X���[�h�ԈႢ�̏ꍇ
		if (!user.getPassword().equals(password)) {

			// ���b�N�A�E�g����ꍇ�A���s�񐔂��C���N�������g
			if (pp != null && pp.getLockOutAllowedCount() > 0) {
				failedAuthenticateUser(companyCode, userCode);
			}
			throw new TWarningException("I00156");// ���͏��Ɍ�肪����܂��B
		}

		// �L�������`�F�b�N
		Date validTerm = Util.getCurrentYMDDate();

		if (validTerm != null) {
			Date from = user.getDateFrom();
			Date to = user.getDateTo();
			if ((from != null && !Util.isSmallerThenByYMD(from, validTerm))
				|| (to != null && !Util.isSmallerThenByYMD(validTerm, to))) {
				throw new TWarningException("I00442");// ���O�C���L���������߂��Ă��邽�߁A���O�C���ł��܂���B
			}
		}

		// ���b�N�A�E�g����Ă���ꍇ�G���[
		if (pp != null && pp.getLockOutAllowedCount() > 0) {

			LoginLockOutDao loDao = (LoginLockOutDao) getComponent(LoginLockOutDao.class);
			LoginLockOutInformation lockOutInfo = loDao.getLoginLockOutInformation(companyCode, userCode);

			if (lockOutInfo != null) {

				// ���s�񐔂����Ɋ���񐔂ɒB���Ă���΃��O�C���s��
				if (pp.getLockOutAllowedCount() <= lockOutInfo.getFailedCount()) {

					// ���b�N�A�E�g���莞�Ԃ��o�߂��Ă��Ȃ���΃��O�C���s��
					// �X�V����̊���
					Date lockoutReleaseTime = DateUtil.addMinute(lockOutInfo.getFailedTimestamp(),
						pp.getLockOutReleaseTime());
					Date currentDate = Util.getCurrentDate();
					boolean isRelease = currentDate.compareTo(lockoutReleaseTime) > 0;

					if (pp.getLockOutReleaseTime() == 0 || !isRelease) {
						throw new TWarningException("W01009");
					}

				}

				// ���O�C�����������ꍇ���b�N�A�E�g����
				releaseLoginLockOut(companyCode, userCode);

			}

		}

		return true;

	}

	public void failedAuthenticateUser(String companyCode, String userCode) throws TException {

		LoginLockOutDao dao = (LoginLockOutDao) getComponent(LoginLockOutDao.class);
		dao.failedAuthenticateUser(companyCode, userCode);

	}

	/**
	 * ���b�N�A�E�g����
	 * 
	 * @param companyCode
	 * @param userCode
	 * @throws TException
	 */
	public void releaseLoginLockOut(String companyCode, String userCode) throws TException {

		LoginLockOutDao dao = (LoginLockOutDao) getComponent(LoginLockOutDao.class);
		dao.releaseLoginLockOut(companyCode, userCode);

	}

}
