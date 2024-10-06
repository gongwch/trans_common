package jp.co.ais.trans2.model.user;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * ���[�U�[�Ǘ������N���X�ł��B
 * 
 * @author AIS
 */
public class UserManagerImpl extends TModel implements UserManager {

	/**
	 * Dao�t�@�N�g��
	 * 
	 * @return Dao
	 */
	protected UserDao getDao() {
		UserDao dao = (UserDao) getComponent(UserDao.class);
		return dao;
	}

	public User get(String companyCode, String userCode) throws TException {
		UserDao dao = getDao();
		return dao.get(companyCode, userCode);
	}

	public User get(String companyCode, String userCode, String password) throws TException {
		UserDao dao = getDao();
		return dao.get(companyCode, userCode, password);
	}

	public List<User> get(UserSearchCondition condition) throws TException {
		UserDao dao = getDao();
		return dao.get(condition);
	}

	public String getPrinterName(String companyCode, String userCode) throws TException {
		UserDao dao = getDao();
		return dao.getPrinterName(companyCode, userCode);
	}

	public void entry(User user) throws TException {
		UserDao dao = getDao();
		dao.entry(user);
	}

	public void modify(User user) throws TException {
		UserDao dao = getDao();
		dao.modify(user);
	}

	public void delete(User user) throws TException {
		UserDao dao = getDao();
		dao.delete(user);
	}

	public void entryLookandFeel(User user) throws TException {
		UserDao dao = getDao();
		dao.entryLookandFeel(user);
	}

	/**
	 * ���[�U�[�}�X�^�ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̃��[�U�[�}�X�^�ꗗ
	 * @throws TException
	 */
	public byte[] getExcel(UserSearchCondition condition) throws TException {

		List<User> users = get(condition);
		if (users == null || users.isEmpty()) {
			return null;
		}

		UserExcel layout = new UserExcel(getUser().getLanguage());
		byte[] data = layout.getExcel(users);

		return data;

	}

	/**
	 * @param user
	 * @throws TException
	 */
	public void updatePrinter(User user) throws TException {
		UserDao dao = getDao();
		dao.updatePrinter(user);
	}

	/**
	 * �_�b�V���{�[�h�����R���g���[���ꗗ�擾
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�����郆�[�U�[���
	 * @throws TException
	 */
	public List<USR_DASH_CTL> getDashBoardSysKbn(UserSearchCondition condition) throws TException {

		UserDao dao = getDao();
		return dao.getDashBoardSysKbn(condition);
	}

}
