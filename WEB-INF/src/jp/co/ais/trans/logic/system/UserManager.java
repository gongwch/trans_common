package jp.co.ais.trans.logic.system;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;

/**
 * ���[�U�F�؃��W�b�N
 * 
 * @author AIS
 */
public interface UserManager {

	/**
	 * ��ЃR�[�h�ݒ�
	 * 
	 * @param compCode ��ЃR�[�h
	 */
	public void setCompanyCode(String compCode);

	/**
	 * ���[�U�R�[�h�ݒ�
	 * 
	 * @param userCode ���[�U�R�[�h
	 */
	public void setUserCode(String userCode);

	/**
	 * ���[�U���ƍ�����i���O�C���F�؁j.<br>
	 * ��ЃR�[�h�������`�F�b�N/���[�U�R�[�h�A<br>
	 * �p�X���[�h�������`�F�b�N/���d���O�C���`�F�b�N���s��
	 * 
	 * @param password �p�X���[�h
	 * @return true �F��OK false �G���[
	 */
	public boolean collatedUser(String password);

	/**
	 * �G���[���b�Z�[�W
	 * 
	 * @return �G���[���b�Z�[�W
	 */
	public String getErrorMsg();

	/**
	 * ���O�C������
	 */
	public void login();

	/**
	 * ���O�A�E�g����
	 */
	public void logout();

	/**
	 * ���[�U���E��Џ����\�z����
	 * 
	 * @return ���[�U���
	 * @throws TException
	 */
	public TUserInfo makeUserInfo() throws TException;

	/**
	 * ��ЃR�[�h�ɕR�Â��ʉݏ��(key:�ʉ݃R�[�h value:�����_����)
	 * 
	 * @return �ʉݏ�񃊃X�g
	 */
	public Map getCompanyCurrency();

}
