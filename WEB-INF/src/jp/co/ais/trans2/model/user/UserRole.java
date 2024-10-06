package jp.co.ais.trans2.model.user;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���[�U�[���[���}�X�^
 * 
 * @author AIS
 */
public class UserRole extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode;

	/** ���[���R�[�h */
	protected String code;

	/** ���[������ */
	protected String name;

	/** ���� */
	protected String names = null;

	/** �������� */
	protected String namek = null;

	/** �L�����ԊJ�n */
	protected Date termFrom = null;

	/** �L�����ԏI�� */
	protected Date termTo = null;

	/** �o�^���t */
	protected Date inpDate;

	/** �X�V���t */
	protected Date updDate;

	/** �v���O����ID */
	protected String prgId;

	/** ���[�U�[ID */
	protected String usrId;

	/** ����J�����x�� */
	protected List<RoleDepartmentLevel> depLvlList = null;

	/** �ȖڊJ�����x�� */
	protected List<RoleItemLevel> itemLvlList = null;

	/**
	 * depLvlList���擾����B
	 * 
	 * @return List<RoleDepartmentLevel> depLvlList
	 */
	public List<RoleDepartmentLevel> getDepLvlList() {
		return depLvlList;
	}

	/**
	 * depLvlList��ݒ肷��B
	 * 
	 * @param depLvlList
	 */
	public void setDepLvlList(List<RoleDepartmentLevel> depLvlList) {
		this.depLvlList = depLvlList;
	}

	/**
	 * depLvlList��ǉ�����B
	 * 
	 * @param bean
	 */
	public void addDepLvlList(RoleDepartmentLevel bean) {

		if (Util.isNullOrEmpty(this.depLvlList)) {
			this.depLvlList = new LinkedList<RoleDepartmentLevel>();
		}

		this.depLvlList.add(bean);
	}

	/**
	 * itemLvlList���擾����B
	 * 
	 * @return List<RoleItemLevel> itemLvlList
	 */
	public List<RoleItemLevel> getItemLvlList() {
		return itemLvlList;
	}

	/**
	 * itemLvlList��ݒ肷��B
	 * 
	 * @param itemLvlList
	 */
	public void setItemLvlList(List<RoleItemLevel> itemLvlList) {
		this.itemLvlList = itemLvlList;
	}

	/**
	 * itemLvlList��ǉ�����B
	 * 
	 * @param bean
	 */
	public void addItemLvlList(RoleItemLevel bean) {

		if (Util.isNullOrEmpty(this.itemLvlList)) {
			this.itemLvlList = new LinkedList<RoleItemLevel>();
		}

		this.itemLvlList.add(bean);
	}

	/**
	 * code���擾����B
	 * 
	 * @return String code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * code��ݒ肷��B
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

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
	 * name���擾����B
	 * 
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * name��ݒ肷��B
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * namek���擾����B
	 * 
	 * @return String namek
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * namek��ݒ肷��B
	 * 
	 * @param namek
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * names���擾����B
	 * 
	 * @return String names
	 */
	public String getNames() {
		return names;
	}

	/**
	 * names��ݒ肷��B
	 * 
	 * @param names
	 */
	public void setNames(String names) {
		this.names = names;
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
	 * termFrom���擾����B
	 * 
	 * @return Date termFrom
	 */
	public Date getTermFrom() {
		return termFrom;
	}

	/**
	 * termFrom��ݒ肷��B
	 * 
	 * @param termFrom
	 */
	public void setTermFrom(Date termFrom) {
		this.termFrom = termFrom;
	}

	/**
	 * termTo���擾����B
	 * 
	 * @return Date termTo
	 */
	public Date getTermTo() {
		return termTo;
	}

	/**
	 * termTo��ݒ肷��B
	 * 
	 * @param termTo
	 */
	public void setTermTo(Date termTo) {
		this.termTo = termTo;
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
