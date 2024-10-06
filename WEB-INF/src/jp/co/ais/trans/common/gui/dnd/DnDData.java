package jp.co.ais.trans.common.gui.dnd;

/**
 * TT��ee�̊K�w�f�[�^�󂯓n���Ɏg�p����N���X
 * 
 * @author Yanwei
 */
public class DnDData {

	private String text1;

	private String text2;

	private String text3;

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public String getText3() {
		return text3;
	}

	public void setText3(String text3) {
		this.text3 = text3;
	}

	/** �R�[�h */
	private String code = "";

	/** ���� */
	private String name = "";

	/** ��ʊK�w�R�[�h */
	private String topCode = "";

	public DnDData() {
		//
	}

	public DnDData(String code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * �m�[�h�ɕ\�����閼�̂��擾����
	 * 
	 * @return �m�[�h�ɕ\�����閼��
	 */
	public String getViewName() {
		return this.toString();
	}

	/**
	 * �I�u�W�F�N�g�̃I���W�i�������\����Ԃ�.
	 * 
	 * @return �v���p�e�B.�R�[�h + ���p�X�y�[�X + �v���p�e�B.����
	 */
	@Override
	public String toString() {
		return this.code + " " + this.name;
	}

	/**
	 * �G���e�B�e�B�̔�r�Ɏg�p����B
	 * 
	 * @param obj ��r
	 * @return boolean
	 */
	public boolean equals(DnDData obj) {
		if (super.equals(obj)) {
			return true;
		} else {
			return this.code.equals(obj.getCode());
		}
	}

	/**
	 * �R�[�h
	 * 
	 * @return �R�[�h
	 */
	public String getCode() {
		return code;
	}

	/**
	 * �R�[�h
	 * 
	 * @param code �R�[�h�ݒ肷��
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * ����
	 * 
	 * @return ����
	 */
	public String getName() {
		return name;
	}

	/**
	 * ����
	 * 
	 * @param name ���̐ݒ肷��
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ��ʊK�w�R�[�h
	 * 
	 * @return ��ʊK�w�R�[�h
	 */
	public String getTopCode() {
		return topCode;
	}

	/**
	 * ��ʊK�w�R�[�h
	 * 
	 * @param topCode ��ʊK�w�R�[�h�ݒ肷��
	 */
	public void setTopCode(String topCode) {
		this.topCode = topCode;
	}

}