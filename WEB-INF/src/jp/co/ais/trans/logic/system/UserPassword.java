package jp.co.ais.trans.logic.system;

import java.io.*;

/**
 * �p�X���[�h�ύX���W�b�N
 */
public interface UserPassword extends Serializable {

	/**
	 * �R�[�h�ݒ�
	 * 
	 * @param compCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 */
	public void setCode(String compCode, String userCode);

	/**
	 * ���݂̃p�X���[�h�Ɠ��ꂩ�ǂ������`�F�b�N����
	 * 
	 * @param password �p�X���[�h
	 * @return true:���݂̃p�X���[�h�Ɠ���
	 */
	public boolean equalsNowPassword(String password);

	/**
	 * �p�X���[�h�Ǘ����s�����ǂ���
	 * 
	 * @return true:�p�X���[�h�Ǘ�����
	 */
	public boolean isPasswordManaged();

	/**
	 * �p�X���[�h���L�����Ԃ��I�[�o�[���Ă��邩�`�F�b�N
	 * 
	 * @return boolean true:�L�����ԃI�[�o�[
	 */
	public boolean isPasswordTermOrver();

	/**
	 * �p�X���[�h�̑Ó����`�F�b�N
	 * 
	 * @param password �Ώۃp�X���[�h
	 * @return true:�p�X���[�h���Ó�
	 */
	public boolean assertPassword(String password);

	/**
	 * �������`�F�b�N
	 * 
	 * @param password �p�X���[�h
	 * @return boolean true:�������K��𖞂���
	 */
	public boolean assertLength(String password);

	/**
	 * ���G���x���`�F�b�N
	 * 
	 * @param password �p�X���[�h
	 * @return boolean true:���G���x���𖞂����Ă���
	 */
	public boolean assertComplicateLevel(String password);

	/**
	 * �p�X���[�h�����X�V
	 * 
	 * @param password �p�X���[�h
	 * @return boolean true:�p�X���[�h������ɑ���
	 */
	public boolean containtsHistory(String password);

	/**
	 * �p�X���[�h���̍X�V
	 * 
	 * @param password �p�X���[�h
	 * @param prgID �v���O����ID
	 */
	public void update(String password, String prgID);

	/**
	 * �G���[���e�����b�Z�[�WID�ŕԂ�
	 * 
	 * @return ���b�Z�[�WID
	 */
	public String getErrorMessageId();

	/**
	 * �G���[���b�Z�[�WID�̃o�C���h�������X�g��Ԃ�
	 * 
	 * @return �o�C���h�������X�g
	 */
	public Object[] getErrorArgs();

	/**
	 * �p�X���[�h�L�������؂�ʒm�����`�F�b�N
	 * 
	 * @return boolean true: �p�X���[�h�L�������؂�̒ʒm������
	 */
	public boolean isPwdLimitMsgNotice();

	/**
	 * �p�X���[�h�L�������܂ł̎c������Ԃ�
	 * 
	 * @return �p�X���[�h�L�������܂ł̎c����
	 */
	public int getPwdLimitMsg();

	/**
	 * �p�X���[�h�����X�V
	 * 
	 * @param password
	 */
	public void updateHistory(String password);

}
