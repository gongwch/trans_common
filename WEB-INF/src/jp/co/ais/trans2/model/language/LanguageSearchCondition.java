package jp.co.ais.trans2.model.language;

import jp.co.ais.trans.common.dt.TransferBase;

/**
 * 	����R�[�h�̌�������
 * @author AIS
 *
 */
public class LanguageSearchCondition extends TransferBase {

	/** serialVersionUID */
	private static final long serialVersionUID = -5782442959967008907L;

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

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

}
