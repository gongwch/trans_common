package jp.co.ais.trans2.model.user;

import java.util.Date;
import jp.co.ais.trans.common.dt.TransferBase;

/**
 * ���[������J�����x��
 * 
 * @author AIS
 */
public class RoleDepartmentLevel extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode;

	/** ���[���R�[�h */
	protected String roleCode;

	/** ����R�[�h */
	protected String depCode;

	/** ����敪 */
	protected Integer DepDivision;

	/** �o�^���t */
	protected Date inpDate;

	/** �X�V���t */
	protected Date updDate;

	/** �v���O����ID */
	protected String prgId;

	/** ���[�U�[ID */
	protected String usrId;

	/**
	 * companyCode���擾����B
	 * 
	 * @return String companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * companyCode��ݒ肷��B
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * depCode���擾����B
	 * 
	 * @return String depCode
	 */
	public String getDepCode() {
		return depCode;
	}

	/**
	 * depCode��ݒ肷��B
	 * 
	 * @param depCode
	 */
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	/**
	 * depDivision���擾����B
	 * 
	 * @return Integer depDivision
	 */
	public Integer getDepDivision() {
		return DepDivision;
	}

	/**
	 * depDivision��ݒ肷��B
	 * 
	 * @param depDivision
	 */
	public void setDepDivision(Integer depDivision) {
		DepDivision = depDivision;
	}

	/**
	 * inpDate���擾����B
	 * 
	 * @return Date inpDate
	 */
	public Date getInpDate() {
		return inpDate;
	}

	/**
	 * inpDate��ݒ肷��B
	 * 
	 * @param inpDate
	 */
	public void setInpDate(Date inpDate) {
		this.inpDate = inpDate;
	}

	/**
	 * prgId���擾����B
	 * 
	 * @return String prgId
	 */
	public String getPrgId() {
		return prgId;
	}

	/**
	 * prgId��ݒ肷��B
	 * 
	 * @param prgId
	 */
	public void setPrgId(String prgId) {
		this.prgId = prgId;
	}

	/**
	 * roleCode���擾����B
	 * 
	 * @return String roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}

	/**
	 * roleCode��ݒ肷��B
	 * 
	 * @param roleCode
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * updDate���擾����B
	 * 
	 * @return Date updDate
	 */
	public Date getUpdDate() {
		return updDate;
	}

	/**
	 * updDate��ݒ肷��B
	 * 
	 * @param updDate
	 */
	public void setUpdDate(Date updDate) {
		this.updDate = updDate;
	}

	/**
	 * usrId���擾����B
	 * 
	 * @return String usrId
	 */
	public String getUsrId() {
		return usrId;
	}

	/**
	 * usrId��ݒ肷��B
	 * 
	 * @param usrId
	 */
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

}