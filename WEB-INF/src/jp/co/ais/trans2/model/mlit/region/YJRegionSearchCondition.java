package jp.co.ais.trans2.model.mlit.region;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * �A�����э��E�n��}�X�^�̌�������
 * 
 * @author AIS
 */
public class YJRegionSearchCondition extends TransferBase implements Cloneable {

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public YJRegionSearchCondition clone() {
		try {
			return (YJRegionSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** ���R�[�h */
	protected String regionCode = null;

	/** �n��R�[�h */
	protected String countryCode = null;

	/** �J�n���R�[�h */
	protected String regionCodeFrom = null;

	/** �I�����R�[�h */
	protected String regionCodeTo = null;

	/** �J�n�n��R�[�h */
	protected String countryCodeFrom = null;

	/** �I���n��R�[�h */
	protected String countryCodeTo = null;

	/** ���R�[�h�O����v */
	protected String regionCodeLike = null;

	/** ������like */
	protected String regionNameLike = null;

	/** �n��R�[�h�O����v */
	protected String countryCodeLike = null;

	/** �n�於��like */
	protected String countryNameLike = null;

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return companyCode ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * ���R�[�h�̎擾
	 * 
	 * @return regionCode ���R�[�h
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * ���R�[�h�̐ݒ�
	 * 
	 * @param regionCode ���R�[�h
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * �n��R�[�h�̎擾
	 * 
	 * @return countryCode �n��R�[�h
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * �n��R�[�h�̐ݒ�
	 * 
	 * @param countryCode �n��R�[�h
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * �J�n���R�[�h�̎擾
	 * 
	 * @return regionCodeFrom �J�n���R�[�h
	 */
	public String getRegionCodeFrom() {
		return regionCodeFrom;
	}

	/**
	 * �J�n���R�[�h�̐ݒ�
	 * 
	 * @param regionCodeFrom �J�n���R�[�h
	 */
	public void setRegionCodeFrom(String regionCodeFrom) {
		this.regionCodeFrom = regionCodeFrom;
	}

	/**
	 * �I�����R�[�h�̎擾
	 * 
	 * @return regionCodeTo �I�����R�[�h
	 */
	public String getRegionCodeTo() {
		return regionCodeTo;
	}

	/**
	 * �I�����R�[�h�̐ݒ�
	 * 
	 * @param regionCodeTo �I�����R�[�h
	 */
	public void setRegionCodeTo(String regionCodeTo) {
		this.regionCodeTo = regionCodeTo;
	}

	/**
	 * �J�n�n��R�[�h�̎擾
	 * 
	 * @return countryCodeFrom �J�n�n��R�[�h
	 */
	public String getCountryCodeFrom() {
		return countryCodeFrom;
	}

	/**
	 * �J�n�n��R�[�h�̐ݒ�
	 * 
	 * @param countryCodeFrom �J�n�n��R�[�h
	 */
	public void setCountryCodeFrom(String countryCodeFrom) {
		this.countryCodeFrom = countryCodeFrom;
	}

	/**
	 * �I���n��R�[�h�̎擾
	 * 
	 * @return countryCodeTo �I���n��R�[�h
	 */
	public String getCountryCodeTo() {
		return countryCodeTo;
	}

	/**
	 * �I���n��R�[�h�̐ݒ�
	 * 
	 * @param countryCodeTo �I���n��R�[�h
	 */
	public void setCountryCodeTo(String countryCodeTo) {
		this.countryCodeTo = countryCodeTo;
	}

	/**
	 * ���R�[�h�O����v�̎擾
	 * 
	 * @return regionCodeLike ���R�[�h�O����v
	 */
	public String getRegionCodeLike() {
		return regionCodeLike;
	}

	/**
	 * ���R�[�h�O����v�̐ݒ�
	 * 
	 * @param regionCodeLike ���R�[�h�O����v
	 */
	public void setRegionCodeLike(String regionCodeLike) {
		this.regionCodeLike = regionCodeLike;
	}

	/**
	 * ������like�̎擾
	 * 
	 * @return regionNameLike ������like
	 */
	public String getRegionNameLike() {
		return regionNameLike;
	}

	/**
	 * ������like�̐ݒ�
	 * 
	 * @param regionNameLike ������like
	 */
	public void setRegionNameLike(String regionNameLike) {
		this.regionNameLike = regionNameLike;
	}

	/**
	 * �n��R�[�h�O����v�̎擾
	 * 
	 * @return countryCodeLike �n��R�[�h�O����v
	 */
	public String getCountryCodeLike() {
		return countryCodeLike;
	}

	/**
	 * �n��R�[�h�O����v�̐ݒ�
	 * 
	 * @param countryCodeLike �n��R�[�h�O����v
	 */
	public void setCountryCodeLike(String countryCodeLike) {
		this.countryCodeLike = countryCodeLike;
	}

	/**
	 * �n�於��like�̎擾
	 * 
	 * @return countryNameLike �n�於��like
	 */
	public String getCountryNameLike() {
		return countryNameLike;
	}

	/**
	 * �n�於��like�̐ݒ�
	 * 
	 * @param countryNameLike �n�於��like
	 */
	public void setCountryNameLike(String countryNameLike) {
		this.countryNameLike = countryNameLike;
	}
}
