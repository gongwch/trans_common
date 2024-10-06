package jp.co.ais.trans.common.server.di;

import jp.co.ais.trans.master.entity.*;

/**
 * �}�X�^�Q�ƃ��W�b�N
 */
public interface TReferenceAuthorityLogic {

	/**
	 * ���O�C�����[�U�[�̏������嗪�̎擾<BR>
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param curCode �e�L�X�g�t�B�[���h�ɕ�����
	 * @return BMN_MST ����}�X�^
	 */
	public BMN_MST getBMN_MSTByKaicodeDepcode(String kaiCode, String curCode);

	/**
	 * ���O�C�����[�U�[�̎Ј��R�[�h���擾<BR>
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param curCode �e�L�X�g�t�B�[���h�ɕ�����
	 * @return USR_MST ���[�U�[�}�X�^
	 */
	public USR_MST getUSR_MSTByKaicodeUsercode(String kaiCode, String curCode);

	/**
	 * ���O�C�����[�U�[�̎Ј����̂��擾<BR>
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param empCode �e�L�X�g�t�B�[���h�ɕ�����
	 * @param depCode ��������R�[�h
	 * @return EMP_MST ���[�U�[�}�X�^
	 */
	public EMP_MST getEMP_MSTByKaiCodeEmpCode(String kaiCode, String empCode, String depCode);
}
