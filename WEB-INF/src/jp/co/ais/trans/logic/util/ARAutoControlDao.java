package jp.co.ais.trans.logic.util;

import jp.co.ais.trans2.define.*;

/**
 * AR�������ԍ��p�����̔�Dao�C���^�[�t�F�[�X
 * 
 * @author AIS
 */
public interface ARAutoControlDao {

	/**
	 * �v���t�B�b�N�X�̎擾
	 * 
	 * @param division �����ݒ�̋敪
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param depCode ���͕���
	 * @param slipDate �`�[���t(yyyy/mm/dd�`��)
	 * @return �v���t�B�b�N�X
	 */
	public String getPrefix(String division, String companyCode, String userCode, String depCode, String slipDate);

	/**
	 * �����ݒ荀�ڂ̎擾
	 * 
	 * @param jid �����ݒ�̋敪
	 * @param jidName �����ݒ�̌ŗL����
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param depCode ���͕���
	 * @param slipDate �`�[���t(yyyy/mm/dd�`��)
	 * @return �����ݒ荀��
	 */
	public String getAutoSetting(InvoiceNoAdopt jid, String jidName, String companyCode, String userCode,
		String depCode, String slipDate);

	/**
	 * �T�t�B�b�N�X�̎擾<br>
	 * �J�X�^�}�C�Y�p
	 * 
	 * @param division �����ݒ�̋敪
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param departmentCode ���͕���
	 * @param slipDate �`�[���t(yyyy/mm/dd�`��)
	 * @return �T�t�B�b�N�X
	 */
	public String getSuffix(String division, String companyCode, String userCode, String departmentCode, String slipDate);

	/**
	 * �����̔Ԃ��ꂽ�ԍ����擾<br>
	 * �����̔ԃR���g���[���̍X�V���s��
	 * 
	 * @param companyCode
	 * @param userCode
	 * @param prifix
	 * @param increase
	 * @return �����̔Ԕԍ�
	 */
	public int getAutoNumber(String companyCode, String userCode, String prifix, int increase);

}