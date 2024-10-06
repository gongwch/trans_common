package jp.co.ais.trans2.model.security;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.program.*;

/**
 * �v���O�������[��Entity
 * 
 * @author AIS
 */
public class ProgramRole extends TransferBase implements Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = 6908147419251825923L;

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

	/** �L�����ԊJ�n */
	protected Date termFrom = null;

	/** �L�����ԏI�� */
	protected Date termTo = null;

	/** �v���O�������� */
	protected String prgNameS = null;

	/** �V�X�e���敪 */
	protected String sysKdn = null;

	/** �v���O�����R�[�h */
	protected String prgCode = null;

	/** �o�^���t */
	protected Date inpDate;

	/** �X�V���t */
	protected Date updDate;

	/** �v���O����ID */
	protected String prgId;

	/** ���[�U�[ID */
	protected String usrId;

	/** ���������敪 */
	protected int procAuthKbn;

	/** �v���O�����R�[�h���X�g */
	protected List<Program> programList = null;

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
	 * @return termFrom��߂��܂��B
	 */
	public Date getTermFrom() {
		return termFrom;
	}

	/**
	 * @param termFrom termFrom��ݒ肵�܂��B
	 */
	public void setTermFrom(Date termFrom) {
		this.termFrom = termFrom;
	}

	/**
	 * @return termTo��߂��܂��B
	 */
	public Date getTermTo() {
		return termTo;
	}

	/**
	 * @param termTo termTo��ݒ肵�܂��B
	 */
	public void setTermTo(Date termTo) {
		this.termTo = termTo;
	}

	/**
	 * programCodeList���擾����B
	 * 
	 * @return List<String> programCodeList
	 */
	public List<Program> getProgramList() {
		return programList;
	}

	/**
	 * programList��ݒ肷��B
	 * 
	 * @param programList
	 */
	public void setProgramList(List<Program> programList) {
		this.programList = programList;
	}

	/**
	 * programCode��ǉ�����B
	 * 
	 * @param bean
	 */
	public void addProgramCodeList(Program bean) {

		if (Util.isNullOrEmpty(this.programList)) {
			this.programList = new LinkedList<Program>();
		}

		this.programList.add(bean);
	}

	/**
	 * prgNamek���擾����B
	 * 
	 * @return String prgNamek
	 */
	public String getPrgNameS() {
		return prgNameS;
	}

	/**
	 * prgNamek��ݒ肷��B
	 * 
	 * @param prgNamek
	 */
	public void setPrgNameS(String prgNamek) {
		this.prgNameS = prgNamek;
	}

	/**
	 * sysKdn���擾����B
	 * 
	 * @return String sysKdn
	 */
	public String getSysKdn() {
		return sysKdn;
	}

	/**
	 * sysKdn��ݒ肷��B
	 * 
	 * @param sysKdn
	 */
	public void setSysKdn(String sysKdn) {
		this.sysKdn = sysKdn;
	}

	/**
	 * prgCode���擾����B
	 * 
	 * @return String prgCode
	 */
	public String getPrgCode() {
		return prgCode;
	}

	/**
	 * prgCode��ݒ肷��B
	 * 
	 * @param prgCode
	 */
	public void setPrgCode(String prgCode) {
		this.prgCode = prgCode;
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

	/**
	 * ���������敪���擾����B
	 * 
	 * @return int procAuthKbn ���������敪
	 */
	public int getProcAuthKbn() {
		return procAuthKbn;
	}

	/**
	 * ���������敪��ݒ肷��B
	 * 
	 * @param procAuthKbn ���������敪
	 */
	public void setProcAuthKbn(int procAuthKbn) {
		this.procAuthKbn = procAuthKbn;
	}

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ProgramRole clone() {
		try {
			return (ProgramRole) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

}
