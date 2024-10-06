package jp.co.ais.trans2.model.program;

import java.awt.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �V�X�e��
 * 
 * @author AIS
 */
public class SystemClassification extends TransferBase {

	/** serialVersionUID */
	private static final long serialVersionUID = 5555796352356967433L;

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** ���� */
	protected String name = null;

	/** ���� */
	protected String names = null;

	/** �������� */
	protected String namek = null;

	/** �\������ */
	protected int dispIndex = 0;

	/** �v���O�����O���[�v�̃J���[ */
	protected Color menuColor = null;

	/**
	 * ��ЃR�[�h���擾����B
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h��ݒ肷��B
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �R�[�h���擾����B
	 * 
	 * @return �R�[�h
	 */
	public String getCode() {
		return code;
	}

	/**
	 * �R�[�h��ݒ肷��B
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * ���̂��擾����B
	 * 
	 * @return ����
	 */
	public String getName() {
		return name;
	}

	/**
	 * ���̂�ݒ肷��B
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ���̂��擾����B
	 * 
	 * @return ����
	 */
	public String getNames() {
		return names;
	}

	/**
	 * ���̂�ݒ肷��B
	 * 
	 * @param names
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * �������̂��擾����B
	 * 
	 * @return ��������
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * �������̂�ݒ肷��B
	 * 
	 * @param namek
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * �\���������擾����B
	 * 
	 * @return �\������
	 */
	public int getDispIndex() {
		return dispIndex;
	}

	/**
	 * �\��������ݒ肷��B
	 * 
	 * @param dispIndex
	 */
	public void setDispIndex(int dispIndex) {
		this.dispIndex = dispIndex;
	}

	/**
	 * �v���O�����O���[�v�̃J���[���擾����B
	 * 
	 * @return �v���O�����O���[�v�̃J���[
	 */
	public Color getMenuColor() {
		return menuColor;
	}

	/**
	 * �v���O�����O���[�v�̃J���[��ݒ肷��B
	 * 
	 * @param menuColor
	 */
	public void setMenuColor(Color menuColor) {
		this.menuColor = menuColor;
	}

}
