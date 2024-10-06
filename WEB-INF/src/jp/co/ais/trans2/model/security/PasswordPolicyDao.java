package jp.co.ais.trans2.model.security;

import java.util.List;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.model.company.Company;
import jp.co.ais.trans2.model.user.User;

/**
 * �p�X���[�h�|���V�[�Ɋւ���Dao
 * @author AIS
 *
 */
public interface PasswordPolicyDao {

	/**
	 * �w���Ђ̃p�X���[�h�|���V�[��Ԃ�
	 * @param company ���
	 * @return �p�X���[�h�|���V�[
	 * @throws TException
	 */
	public PasswordPolicy get(Company company) throws TException;

	/**
	 * �w�胆�[�U�[�̃p�X���[�h������Ԃ�
	 * @param company ���
	 * @param user ���[�U�[
	 * @return �w�胆�[�U�[�̃p�X���[�h����
	 * @throws TException
	 */
	public List<Password> getPasswordHistory(Company company, User user) throws TException;

	/**
	 * �p�X���[�h�X�V
	 * @param company
	 * @param user
	 * @param password
	 */
	public void updatePassword(Company company, User user, String password) throws TException;

	/**
	 * �p�X���[�h����o�^
	 * @param company
	 * @param user
	 * @param password
	 */
	public void entryPasswordHistory(Company company, User user, String password) throws TException;

}
