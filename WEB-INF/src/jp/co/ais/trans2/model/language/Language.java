package jp.co.ais.trans2.model.language;

import jp.co.ais.trans.common.dt.*;

/**
 * ����Entity
 * 
 * @author AIS
 */
public class Language extends TransferBase {

	/** serialVersionUID */
	private static final long serialVersionUID = -6136170995614029871L;

	/** ����R�[�h */
	protected String code;

	/** ���ꖼ */
	protected String name;

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
	 * @return code��߂��܂��B
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name code��ݒ肵�܂��B
	 */
	public void setName(String name) {
		this.name = name;
	}

}
