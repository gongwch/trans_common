package jp.co.ais.trans2.model;

/**
 * �����\�C���^�[�t�F�[�X <br>
 * ��ɁA�����@�\�łӂ�܂������L���邽�߂Ɏg�p����B
 * @author AIS
 *
 */
public interface TReferable {

	/**
	 * �R�[�h��Ԃ�
	 * @return �R�[�h
	 */
	public String getCode();

	/**
	 * �R�[�h���Z�b�g����
	 * @param code �R�[�h
	 */
	public void setCode(String code);

	/**
	 * ���̂�Ԃ�
	 * @return ����
	 */
	public String getNames();

	/**
	 * ���̂��Z�b�g����
	 * @param names ����
	 */
	public void setNames(String names);

}
