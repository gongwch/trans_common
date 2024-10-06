package jp.co.ais.trans2.model.tag;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * MG0460TagMaster - �tⳃ}�X�^ - SearchCondition Class
 * 
 * @author AIS
 */
public class TagSearchCondition extends TransferBase implements Cloneable {

	/** ��ЃR�[�h�̒�` */
	protected String companyCode = null;

	/** �tⳃR�[�h�̒�` */
	protected String tagCode = null;

	/** �tⳃ^�C�g���̒�` */
	protected String tagTitle = null;

	/** �tⳃR�[�h�B�������̒�` */
	protected String codeLike = null;

	/** �tⳃ^�C�g���B�������̒�` */
	protected String titleLike = null;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public TagSearchCondition clone() {
		try {
			return (TagSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �tⳃR�[�h�̎擾
	 * 
	 * @return tagCode
	 */
	public String getTagCode() {
		return tagCode;
	}

	/**
	 * �tⳃR�[�h�̐ݒ�
	 * 
	 * @param tagCode
	 */
	public void setTagCode(String tagCode) {
		this.tagCode = tagCode;
	}

	/**
	 * �tⳃ^�C�g���̎擾
	 * 
	 * @return tagTitle
	 */
	public String getTagTitle() {
		return tagTitle;
	}

	/**
	 * �tⳃ^�C�g���̐ݒ�
	 * 
	 * @param tagTitle
	 */
	public void setTagTitle(String tagTitle) {
		this.tagTitle = tagTitle;
	}

	/**
	 * �tⳃR�[�h�B�������̎擾
	 * 
	 * @return codeLike
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * �tⳃR�[�h�B�������̐ݒ�
	 * 
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * �tⳃ^�C�g���B�������̎擾
	 * 
	 * @return titleLike
	 */
	public String getTitleLike() {
		return titleLike;
	}

	/**
	 * �tⳃ^�C�g���B�������̐ݒ�
	 * 
	 * @param titleLike
	 */
	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

}