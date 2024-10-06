package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.common.exception.TInformationException;
import jp.co.ais.trans2.common.exception.TPasswordTermComeThroughException;
import jp.co.ais.trans2.common.exception.TWarningException;
import jp.co.ais.trans2.model.company.Company;
import jp.co.ais.trans2.model.user.User;

/**
 * �p�X���[�h�Ǘ��C���^�[�t�F�[�X
 * @author AIS
 *
 */
public interface PasswordManager {

	/**
	 * �p�X���[�h�|���V�[��Ԃ�
	 * @param company ���
	 * @return �p�X���[�h�|���V�[
	 * @throws TException
	 */
	public PasswordPolicy getPasswordPolicy(String companyCode) throws TException;
		
	/**
	 * �p�X���[�h���L�����ԓ������`�F�b�N����B
	 * @param company ���
	 * @param user ���[�U�[
	 * @throws TPasswordTermComeThroughException �L�����Ԑ؂�Exception
	 */
	public void isInTerm(Company company, User user) throws TPasswordTermComeThroughException, TException;

	/**
	 * �p�X���[�h���X�V����
	 * @param company ���
	 * @param user ���[�U�[
	 * @param password �p�X���[�h
	 * @throws TWarningException, TException
	 */
	public void updatePassword(Company company, User user, String password) throws TInformationException, TException;

}
