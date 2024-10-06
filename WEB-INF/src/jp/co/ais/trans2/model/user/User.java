package jp.co.ais.trans2.model.user;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.aprvrole.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.security.*;

/**
 * ���[�U�[
 * 
 * @author AIS
 */
public class User extends TransferBase implements TReferable, AutoCompletable, FilterableEntity {

	/** �A�N�Z�X�����t���O�J�E���g */
	public static final int ACCESS_FLAG_COUNT = 10;

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

	/** �p�X���[�h */
	protected String password = null;

	/** �������� */
	protected Department department = null;

	/** �Ј� */
	protected Employee employee = null;

	/** ����R�[�h */
	protected String language = null;

	/** ���ꖼ�� */
	protected String languageName = null;

	/** �v���O�������[�� */
	protected ProgramRole programRole = null;

	/** �J�����[�� */
	protected UserRole userRole = null;

	/** ���F�����O���[�v */
	protected AprvRoleGroup aprvRoleGroup = null;

	/** INV. SIGNER DEPT */
	protected String signerDept = null;

	/** INV. SIGNER TITLE */
	protected String signerTitle = null;

	/** INV. SIGNER NAME */
	protected String signerName = null;

	/** INV. SIGN FILE NAME */
	protected String sineFileName = null;

	/** INV. SIGN FILE */
	protected USR_SIGN sineFile = null;

	/** E-MAIL */
	protected String eMailAddress = null;

	/** �`�[�X�V���� */
	protected SlipRole slipRole = null;

	/** �o�������� */
	protected UserAccountRole userAccountRole = null;

	/** �L�����ԊJ�n */
	protected Date dateFrom = null;

	/** �L�����ԏI�� */
	protected Date dateTo = null;

	/** LF�� */
	protected String lfName = "";

	/** �F */
	protected String lfColorType = "";

	/** LF��TabbedPanel��Type */
	protected MenuType menuType;

	/** �N���C�A���g�v�����^���� */
	protected String printerName = null;

	/** USR_DASH_CTL list */
	protected List<USR_DASH_CTL> USR_DASH_CTLList = null;

	/** �A�N�Z�X�����t���O�P */
	protected int AccessPermissionFlag1 = 0;

	/** �A�N�Z�X�����t���O�Q */
	protected int AccessPermissionFlag2 = 0;

	/** �A�N�Z�X�����t���O�R */
	protected int AccessPermissionFlag3 = 0;

	/** �A�N�Z�X�����t���O�S */
	protected int AccessPermissionFlag4 = 0;

	/** �A�N�Z�X�����t���O�T */
	protected int AccessPermissionFlag5 = 0;

	/** �A�N�Z�X�����t���O�U */
	protected int AccessPermissionFlag6 = 0;

	/** �A�N�Z�X�����t���O�V */
	protected int AccessPermissionFlag7 = 0;

	/** �A�N�Z�X�����t���O�W */
	protected int AccessPermissionFlag8 = 0;

	/** �A�N�Z�X�����t���O�X */
	protected int AccessPermissionFlag9 = 0;

	/** �A�N�Z�X�����t���O�P�O */
	protected int AccessPermissionFlag10 = 0;

	/**
	 * �R���X�g���N�^.
	 */
	public User() {
		super();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param code ���[�U�R�[�h
	 */
	public User(String code) {
		this();
		this.code = code;
	}

	/**
	 * @return language��߂��܂��B
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language language��ݒ肵�܂��B
	 */
	public void setLanguage(String language) {
		this.language = language;
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
	 * @return dateFrom��߂��܂��B
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom dateFrom��ݒ肵�܂��B
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return dateTo��߂��܂��B
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo dateTo��ݒ肵�܂��B
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

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
	 * @return department��߂��܂��B
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * @param department department��ݒ肵�܂��B
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * @return signerDept��߂��܂��B
	 */
	public String getSignerDept() {
		return signerDept;
	}

	/**
	 * @param signerDept signerDept��ݒ肵�܂��B
	 */
	public void setSignerDept(String signerDept) {
		this.signerDept = signerDept;
	}

	/**
	 * @return signerTitle��߂��܂��B
	 */
	public String getSignerTitle() {
		return signerTitle;
	}

	/**
	 * @param signerTitle signerTitle��ݒ肵�܂��B
	 */
	public void setSignerTitle(String signerTitle) {
		this.signerTitle = signerTitle;
	}

	/**
	 * @return signerName��߂��܂��B
	 */
	public String getSignerName() {
		return signerName;
	}

	/**
	 * @param signerName signerName��ݒ肵�܂��B
	 */
	public void setSignerName(String signerName) {
		this.signerName = signerName;
	}

	/**
	 * @return sineFileName��߂��܂��B
	 */
	public String getSignFileName() {
		return sineFileName;
	}

	/**
	 * @param sineFileName signerName��ݒ肵�܂��B
	 */
	public void setSignFileName(String sineFileName) {
		this.sineFileName = sineFileName;
	}

	/**
	 * @return SignFile��߂��܂��B
	 */
	public USR_SIGN getSignFile() {
		return sineFile;
	}

	/**
	 * @param sineFile SignFile��ݒ肵�܂��B
	 */
	public void setSignFile(USR_SIGN sineFile) {
		this.sineFile = sineFile;
	}

	/**
	 * @return eMailAddress��߂��܂��B
	 */
	public String getEMailAddress() {
		return eMailAddress;
	}

	/**
	 * @param eMailAddress eMailAddress��ݒ肵�܂��B
	 */
	public void setEMailAddress(String eMailAddress) {
		this.eMailAddress = eMailAddress;
	}

	/**
	 * �`�[�X�V����
	 * 
	 * @return �`�[�X�V����
	 */
	public SlipRole getSlipRole() {
		return slipRole;
	}

	/**
	 * �`�[�X�V����
	 * 
	 * @param slipRole �`�[�X�V����
	 */
	public void setSlipRole(SlipRole slipRole) {
		this.slipRole = slipRole;
	}

	/**
	 * �o��������
	 * 
	 * @return �o��������
	 */
	public UserAccountRole getUserAccountRole() {
		return userAccountRole;
	}

	/**
	 * �o��������
	 * 
	 * @param accountRole �o��������
	 */
	public void setUserAccountRole(UserAccountRole accountRole) {
		this.userAccountRole = accountRole;
	}

	/**
	 * �o���S���҂��ۂ���Ԃ�
	 * 
	 * @return �o���S���҂��ۂ�
	 */
	public boolean isAccountant() {
		return (UserAccountRole.ACCOUNT == getUserAccountRole());
	}

	/**
	 * �Ј�
	 * 
	 * @return �Ј�
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * �Ј�
	 * 
	 * @param employee �Ј�
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return programRole
	 */
	public ProgramRole getProgramRole() {
		return programRole;
	}

	/**
	 * @param programRole
	 */
	public void setProgramRole(ProgramRole programRole) {
		this.programRole = programRole;
	}

	/**
	 * @return userRole
	 */
	public UserRole getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole
	 */
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	/**
	 * ���F�����O���[�v���擾
	 * 
	 * @return aprvRoleGroup
	 */
	public AprvRoleGroup getAprvRoleGroup() {
		return aprvRoleGroup;
	}

	/**
	 * ���F�����O���[�v�R�[�h���擾
	 * 
	 * @return aprvRoleGroup
	 */
	public String getAprvRoleGroupCode() {
		return aprvRoleGroup == null ? null : aprvRoleGroup.getAPRV_ROLE_GRP_CODE();
	}


	/**
	 * ���F�����O���[�v���Z�b�g����
	 * 
	 * @param aprvRoleGroup aprvRoleGroup
	 */
	public void setAprvRoleGroup(AprvRoleGroup aprvRoleGroup) {
		this.aprvRoleGroup = aprvRoleGroup;
	}

	/**
	 * @return languageName
	 */
	public String getLanguageName() {
		return languageName;
	}

	/**
	 * @param languageName
	 */
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	/**
	 * lfName���擾����B
	 * 
	 * @return String lfName
	 */
	public String getLfName() {
		return lfName;
	}

	/**
	 * lfName��ݒ肷��B
	 * 
	 * @param lfName
	 */
	public void setLfName(String lfName) {
		this.lfName = lfName;
	}

	/**
	 * lfColorType���擾����B
	 * 
	 * @return lfColorType
	 */
	public String getLfColorType() {
		return lfColorType;
	}

	/**
	 * lfColorType��ݒ肷��B
	 * 
	 * @param lfColorType
	 */
	public void setLfColorType(String lfColorType) {
		this.lfColorType = lfColorType;
	}

	/**
	 * ���j���[�^�C�v
	 * 
	 * @param type �^�C�v
	 */
	public void setMenuType(MenuType type) {
		this.menuType = type;
	}

	/**
	 * ���j���[�^�C�v1
	 * 
	 * @return true:���j���[�^�C�v1
	 */
	public MenuType getMenuType() {
		return menuType;
	}

	/**
	 * ���[�U�̌���R�[�h���p�ꂩ�ǂ���
	 * 
	 * @return true:�p��
	 */
	public boolean isEnglish() {
		return "en".equals(language);
	}

	/**
	 * �N���C�A���g�v�����^���̂�߂��܂��B
	 * 
	 * @return printerName
	 */
	public String getPrinterName() {
		return printerName;
	}

	/**
	 * @param printerName �N���C�A���g�v�����^���̂�ݒ肵�܂��B
	 */
	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	/**
	 * �A�N�Z�X�����t���O�P�̎擾
	 * 
	 * @return AccessPermissionFlag1 �A�N�Z�X�����t���O�P
	 */
	public int getAccessPermissionFlag1() {
		return AccessPermissionFlag1;
	}

	/**
	 * �A�N�Z�X�����t���O�P�̐ݒ�
	 * 
	 * @param AccessPermissionFlag1 �A�N�Z�X�����t���O�P
	 */
	public void setAccessPermissionFlag1(int AccessPermissionFlag1) {
		this.AccessPermissionFlag1 = AccessPermissionFlag1;
	}

	/**
	 * �A�N�Z�X�����t���O�Q�̎擾
	 * 
	 * @return AccessPermissionFlag2 �A�N�Z�X�����t���O�Q
	 */
	public int getAccessPermissionFlag2() {
		return AccessPermissionFlag2;
	}

	/**
	 * �A�N�Z�X�����t���O�Q�̐ݒ�
	 * 
	 * @param AccessPermissionFlag2 �A�N�Z�X�����t���O�Q
	 */
	public void setAccessPermissionFlag2(int AccessPermissionFlag2) {
		this.AccessPermissionFlag2 = AccessPermissionFlag2;
	}

	/**
	 * �A�N�Z�X�����t���O�R�̎擾
	 * 
	 * @return AccessPermissionFlag3 �A�N�Z�X�����t���O�R
	 */
	public int getAccessPermissionFlag3() {
		return AccessPermissionFlag3;
	}

	/**
	 * �A�N�Z�X�����t���O�R�̐ݒ�
	 * 
	 * @param AccessPermissionFlag3 �A�N�Z�X�����t���O�R
	 */
	public void setAccessPermissionFlag3(int AccessPermissionFlag3) {
		this.AccessPermissionFlag3 = AccessPermissionFlag3;
	}

	/**
	 * �A�N�Z�X�����t���O�S�̎擾
	 * 
	 * @return AccessPermissionFlag4 �A�N�Z�X�����t���O�S
	 */
	public int getAccessPermissionFlag4() {
		return AccessPermissionFlag4;
	}

	/**
	 * �A�N�Z�X�����t���O�S�̐ݒ�
	 * 
	 * @param AccessPermissionFlag4 �A�N�Z�X�����t���O�S
	 */
	public void setAccessPermissionFlag4(int AccessPermissionFlag4) {
		this.AccessPermissionFlag4 = AccessPermissionFlag4;
	}

	/**
	 * �A�N�Z�X�����t���O�T�̎擾
	 * 
	 * @return AccessPermissionFlag5 �A�N�Z�X�����t���O�T
	 */
	public int getAccessPermissionFlag5() {
		return AccessPermissionFlag5;
	}

	/**
	 * �A�N�Z�X�����t���O�T�̐ݒ�
	 * 
	 * @param AccessPermissionFlag5 �A�N�Z�X�����t���O�T
	 */
	public void setAccessPermissionFlag5(int AccessPermissionFlag5) {
		this.AccessPermissionFlag5 = AccessPermissionFlag5;
	}

	/**
	 * �A�N�Z�X�����t���O�U�̎擾
	 * 
	 * @return AccessPermissionFlag6 �A�N�Z�X�����t���O�U
	 */
	public int getAccessPermissionFlag6() {
		return AccessPermissionFlag6;
	}

	/**
	 * �A�N�Z�X�����t���O�U�̐ݒ�
	 * 
	 * @param AccessPermissionFlag6 �A�N�Z�X�����t���O�U
	 */
	public void setAccessPermissionFlag6(int AccessPermissionFlag6) {
		this.AccessPermissionFlag6 = AccessPermissionFlag6;
	}

	/**
	 * �A�N�Z�X�����t���O�V�̎擾
	 * 
	 * @return AccessPermissionFlag7 �A�N�Z�X�����t���O�V
	 */
	public int getAccessPermissionFlag7() {
		return AccessPermissionFlag7;
	}

	/**
	 * �A�N�Z�X�����t���O�V�̐ݒ�
	 * 
	 * @param AccessPermissionFlag7 �A�N�Z�X�����t���O�V
	 */
	public void setAccessPermissionFlag7(int AccessPermissionFlag7) {
		this.AccessPermissionFlag7 = AccessPermissionFlag7;
	}

	/**
	 * �A�N�Z�X�����t���O�W�̎擾
	 * 
	 * @return AccessPermissionFlag8 �A�N�Z�X�����t���O�W
	 */
	public int getAccessPermissionFlag8() {
		return AccessPermissionFlag8;
	}

	/**
	 * �A�N�Z�X�����t���O�W�̐ݒ�
	 * 
	 * @param AccessPermissionFlag8 �A�N�Z�X�����t���O�W
	 */
	public void setAccessPermissionFlag8(int AccessPermissionFlag8) {
		this.AccessPermissionFlag8 = AccessPermissionFlag8;
	}

	/**
	 * �A�N�Z�X�����t���O�X�̎擾
	 * 
	 * @return AccessPermissionFlag9 �A�N�Z�X�����t���O�X
	 */
	public int getAccessPermissionFlag9() {
		return AccessPermissionFlag9;
	}

	/**
	 * �A�N�Z�X�����t���O�X�̐ݒ�
	 * 
	 * @param AccessPermissionFlag9 �A�N�Z�X�����t���O�X
	 */
	public void setAccessPermissionFlag9(int AccessPermissionFlag9) {
		this.AccessPermissionFlag9 = AccessPermissionFlag9;
	}

	/**
	 * �A�N�Z�X�����t���O�P�O�̎擾
	 * 
	 * @return AccessPermissionFlag10 �A�N�Z�X�����t���O�P�O
	 */
	public int getAccessPermissionFlag10() {
		return AccessPermissionFlag10;
	}

	/**
	 * �A�N�Z�X�����t���O�P�O�̐ݒ�
	 * 
	 * @param AccessPermissionFlag10 �A�N�Z�X�����t���O�P�O
	 */
	public void setAccessPermissionFlag10(int AccessPermissionFlag10) {
		this.AccessPermissionFlag10 = AccessPermissionFlag10;
	}

	/**
	 * @return �C���N�������g�T�[�`�\���l
	 */
	public String getDisplayText() {
		return getCode() + " " + getName();
	}

	/**
	 * USR_DASH_CTL list�̎擾
	 * 
	 * @return USR_DASH_CTLList USR_DASH_CTL list
	 */
	public List<USR_DASH_CTL> getUSR_DASH_CTLList() {
		return USR_DASH_CTLList;
	}

	/**
	 * USR_DASH_CTL list�̐ݒ�
	 * 
	 * @param USR_DASH_CTLList USR_DASH_CTL list
	 */
	public void setUSR_DASH_CTLList(List<USR_DASH_CTL> USR_DASH_CTLList) {
		this.USR_DASH_CTLList = USR_DASH_CTLList;
	}

}
