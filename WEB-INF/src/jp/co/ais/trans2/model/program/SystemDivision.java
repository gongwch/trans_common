package jp.co.ais.trans2.model.program;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.define.*;

/**
 * �V�X�e���敪
 * 
 * @author AIS
 */
public class SystemDivision extends TransferBase {

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

	/** �O���V�X�e���敪 */
	protected OuterSystemType outerSystemType = null;

	/**
	 * @return code��߂��܂��B
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code code��ݒ肵�܂��B
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return companyCode��߂��܂��B
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode companyCode��ݒ肵�܂��B
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return name��߂��܂��B
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name name��ݒ肵�܂��B
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return namek��߂��܂��B
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * @param namek namek��ݒ肵�܂��B
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * @return names��߂��܂��B
	 */
	public String getNames() {
		return names;
	}

	/**
	 * @param names names��ݒ肵�܂��B
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * �O���V�X�e���敪�̎擾
	 * 
	 * @return foreignSystemType �O���V�X�e���敪
	 */
	public OuterSystemType getOuterSystemType() {
		return outerSystemType;
	}

	/**
	 * �O���V�X�e���敪�̐ݒ�
	 * 
	 * @param foreignSystemType �O���V�X�e���敪
	 */
	public void setOuterSystemType(OuterSystemType foreignSystemType) {
		this.outerSystemType = foreignSystemType;
	}

}
