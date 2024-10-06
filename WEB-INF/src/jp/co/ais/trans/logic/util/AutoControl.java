package jp.co.ais.trans.logic.util;

/**
 * �����̔ԃR���g���[���N���X
 * 
 * @author nagahashi
 */
public interface AutoControl {

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

	/**
	 * �����ݒ荀�ڂ̎擾(�`�[��ʒǉ�)
	 * 
	 * @param division �����ݒ�̋敪
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param depCode ���͕���
	 * @param systemDivision �V�X�e���敪
	 * @param slipDate �`�[���t(yyyy/mm/dd�`��)
	 * @param slipType �`�[���
	 * @param kisyu
	 * @return �����ݒ荀��
	 */
	public String getAutoSetting(String division, String companyCode, String userCode, String depCode,
		String systemDivision, String slipDate, String slipType, Integer kisyu);

	/**
	 * �v���t�B�b�N�X�̎擾
	 * 
	 * @param division �����ݒ�̋敪
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param depCode ���͕���
	 * @param systemDivision �V�X�e���敪
	 * @param slipDate �`�[���t(yyyy/mm/dd�`��)
	 * @param slipType �`�[���
	 * @param kisyu
	 * @return �v���t�B�b�N�X
	 */
	public String getPrefix(String division, String companyCode, String userCode, String depCode,
		String systemDivision, String slipDate, String slipType, Integer kisyu);

	/**
	 * �T�t�B�b�N�X�̎擾<br>
	 * �J�X�^�}�C�Y�p
	 * 
	 * @param division �����ݒ�̋敪
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param departmentCode ���͕���
	 * @param systemDivision �V�X�e���敪
	 * @param slipDate �`�[���t(yyyy/mm/dd�`��)
	 * @param slipType �`�[���
	 * @param kisyu
	 * @return �T�t�B�b�N�X
	 */
	public String getSuffix(String division, String companyCode, String userCode, String departmentCode,
		String systemDivision, String slipDate, String slipType, Integer kisyu);

}
