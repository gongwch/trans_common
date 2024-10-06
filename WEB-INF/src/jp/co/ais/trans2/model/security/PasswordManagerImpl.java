package jp.co.ais.trans2.model.security;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans.common.util.DateUtil;
import jp.co.ais.trans2.common.exception.TInformationException;
import jp.co.ais.trans2.common.exception.TPasswordTermComeThroughException;
import jp.co.ais.trans2.model.TModel;
import jp.co.ais.trans2.model.company.Company;
import jp.co.ais.trans2.model.user.User;

/**
 * �p�X���[�h�Ǘ�����
 * 
 * @author AIS
 */
public class PasswordManagerImpl extends TModel implements PasswordManager {

	public PasswordPolicy getPasswordPolicy(String companyCode) throws TException {

		PasswordPolicyDao ppDao = (PasswordPolicyDao) getComponent(PasswordPolicyDao.class);
		Company company = new Company();
		company.setCode(companyCode);
		PasswordPolicy pp = ppDao.get(company);

		return pp;
	}

	public void isInTerm(Company company, User user) throws TPasswordTermComeThroughException, TException {

		// 1.�p�X���[�h�Ǘ����s�������`�F�b�N
		PasswordPolicy pp = getPasswordPolicy(company.getCode());

		// �Ǘ����Ȃ��ꍇ�̓`�F�b�N�I��
		if (pp == null) {
			return;
		}

		// �p�X���[�h�L�����ԃ`�F�b�N. 0�̏ꍇ�̓`�F�b�N�Ȃ�
		if (pp.getPasswordTerm() == 0) {
			return;
		}

		// ���[�U�[�̃p�X���[�h�������擾
		PasswordPolicyDao ppDao = (PasswordPolicyDao) getComponent(PasswordPolicyDao.class);
		List<Password> passwordHistory = ppDao.getPasswordHistory(company, user);

		// �����������ꍇ�͋����ύX
		if (passwordHistory == null || passwordHistory.isEmpty()) {
			throw new TPasswordTermComeThroughException();
		}

		Date lastDate = passwordHistory.get(0).getUpdateDate();

		// �����؂�`�F�b�N
		Date currentDate = DateUtil.addDay(lastDate, pp.getPasswordTerm());
		int remainDays = DateUtil.getDayCount(getNow(), currentDate);

		if (remainDays <= 0) {
			TPasswordTermComeThroughException e = new TPasswordTermComeThroughException();
			e.setDaysBeforePasswordTermComeThrough(remainDays);
			throw e;
		}

		// �ύX���O�ʒm
		if (pp.getDaysNoticePasswordTermComeThrough() != 0) {

			// �ʒm���t���ł���Βʒm
			if (remainDays <= pp.getDaysNoticePasswordTermComeThrough()) {
				TPasswordTermComeThroughException e = new TPasswordTermComeThroughException();
				e.setDaysBeforePasswordTermComeThrough(remainDays);
				throw e;
			}
		}
	}

	public void updatePassword(Company company, User user, String password) throws TInformationException, TException {

		// �p�X���[�h�|���V�[�擾
		PasswordPolicyDao ppDao = (PasswordPolicyDao) getComponent(PasswordPolicyDao.class);
		PasswordPolicy pp = ppDao.get(company);

		// �p�X���[�h�`�F�b�N
		if (pp != null) {
			checkPassword(company, user, pp, password);
		}

		// �p�X���[�h�X�V
		ppDao.updatePassword(company, user, password);
		ppDao.entryPasswordHistory(company, user, password);

	}

	/**
	 * �p�X���[�h�̗L���`�F�b�N
	 * 
	 * @param company
	 * @param user
	 * @param pp
	 * @param password
	 * @throws TInformationException
	 * @throws TException
	 */
	protected void checkPassword(Company company, User user, PasswordPolicy pp, String password)
		throws TInformationException, TException {

		// �������`�F�b�N
		if (pp.getPasswordMinLength() > 0) {
			if (password.length() < pp.getPasswordMinLength()) {
				throw new TInformationException("I00027", Integer.toString(pp.getPasswordMinLength()));
			}
		}

		// ���G���x��
		// ���ꂼ��̕������A�X�L�R�[�h�ŕ��ʁAcomplicate���X�g�ɂ��̋敪�������B
		Set<Integer> complicateList = new HashSet<Integer>();

		// �e�����𔻒�pint�z��ɕς���B
		for (char schar : password.toCharArray()) {
			String str = Character.toString(schar);

			if (str.matches("[A-Z]")) {
				complicateList.add(1); // �啶��

			} else if (str.matches("[a-z]")) {
				complicateList.add(2); // ������

			} else if (str.matches("[0-9]")) {
				complicateList.add(3); // ����

			} else if (str.matches("[!-~]")) {
				complicateList.add(4); // �L��
			}
		}

		// ���G���x�����ݒ���Ⴂ�ꍇ�G���[���o���B
		if (complicateList.size() < pp.getComplicateLevel()) {
			throw new TInformationException("I00028", Integer.toString(pp.getComplicateLevel()));
		}

		// �����`�F�b�N
		if (pp.getHistoryMaxCount() > 0) {

			PasswordPolicyDao ppDao = (PasswordPolicyDao) getComponent(PasswordPolicyDao.class);
			List<Password> passwordHistory = ppDao.getPasswordHistory(company, user);

			if (passwordHistory != null && passwordHistory.size() >= pp.getHistoryMaxCount()) {
				for (int i = 0; i < pp.getHistoryMaxCount(); i++) {
					Password ph = passwordHistory.get(i);
					if (ph.getPassword().equals(password)) {
						throw new TInformationException("I00029");
					}
				}
			}

		}

	}
}
