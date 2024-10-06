package jp.co.ais.trans2.model.tag;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * @author AIS
 */
public class Tag extends TransferBase {

	/** ��ЃR�[�h�̒�` */
	protected String companyCode = null;

	/** �tⳃR�[�h�̒�` */
	protected String code = null;

	/** �tⳐF�̒�` */
	protected Color color = null;

	/** �tⳃ^�C�g���̒�` */
	protected String title = null;

	/** �o�^�����̒�` */
	protected Date inpDate = null;

	/** �X�V�����̒�` */
	protected Date updDate = null;

	/** �v���O����ID�̒�` */
	protected String programCode = null;

	/** ���[�U�[ID�̒�` */
	protected String userCode = null;

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
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * �tⳃR�[�h�̐ݒ�
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * �tⳐF�̎擾
	 * 
	 * @return color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * �tⳐF�̐ݒ�
	 * 
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * �tⳃ^�C�g���̎擾
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * �tⳃ^�C�g���̐ݒ�
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * �o�^�����̎擾
	 * 
	 * @return inpDate
	 */
	public Date getInpDate() {
		return inpDate;
	}

	/**
	 * �o�^�����̐ݒ�
	 * 
	 * @param inpDate
	 */
	public void setInpDate(Date inpDate) {
		this.inpDate = inpDate;
	}

	/**
	 * �o�^�����̎擾
	 * 
	 * @return updDate
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * �o�^�����̐ݒ�
	 * 
	 * @param updDate
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	/**
	 * �v���O����ID�̎擾
	 * 
	 * @return programCode
	 */
	public String getProgramCode() {
		return programCode;
	}

	/**
	 * �v���O����ID�̐ݒ�
	 * 
	 * @param programCode
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	/**
	 * ���[�U�[ID�̎擾
	 * 
	 * @return userCode
	 */
	public String getUserCode() {
		return programCode;
	}

	/**
	 * ���[�U�[ID�̐ݒ�
	 * 
	 * @param userCode
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}
