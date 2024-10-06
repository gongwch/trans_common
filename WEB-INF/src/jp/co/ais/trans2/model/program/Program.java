package jp.co.ais.trans2.model.program;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * �v���O����Entity
 * 
 * @author AIS
 */
public class Program extends TransferBase {

	/** serialVersionUID */
	private static final long serialVersionUID = 6908147419251825923L;

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �V�X�e���敪 */
	protected SystemClassification systemClassification = null;

	/** �V�X�e���敪�R�[�h */
	protected String sysCode = null;

	/** �R�[�h */
	protected String code = null;

	/** ���� */
	protected String name = null;

	/** ���� */
	protected String names = null;

	/** �������� */
	protected String namek = null;

	/** �R�����g */
	protected String comment = null;

	/** ���[�h�N���X���� */
	protected String loadClassName = null;

	/** �L�����ԊJ�n */
	protected Date termFrom = null;

	/** �L�����ԏI�� */
	protected Date termTo = null;

	/** ���������敪 */
	protected int procAuthKbn = -1;

	// TODO:�v���O�����}�X�^�ɂ�郁�j���[�\�z�ˍ폜�\��
	/** �e�v���O�����R�[�h */
	protected String parentPrgCode = null;

	// TODO:�v���O�����}�X�^�ɂ�郁�j���[�\�z�ˍ폜�\��
	/** ���j���[�敪 */
	protected int menuKbn = 0;

	// TODO:�v���O�����}�X�^�ɂ�郁�j���[�\�z�ˍ폜�\��
	/** �\���� */
	protected int displayIndex = 0;

	/**
	 * �V�X�e���敪�R�[�h�̎擾
	 * 
	 * @return sysCode �V�X�e���敪�R�[�h
	 */
	public String getSysCode() {
		return sysCode;
	}

	/**
	 * �V�X�e���敪�R�[�h�̐ݒ�
	 * 
	 * @param sysCode �V�X�e���敪�R�[�h
	 */
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
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
	 * @return comment��߂��܂��B
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment comment��ݒ肵�܂��B
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
	 * @return parentPrgCode��߂��܂��B
	 */
	public String getParentPrgCode() {
		return parentPrgCode;
	}

	/**
	 * @param parentPrgCode parentPrgCode��ݒ肵�܂��B
	 */
	public void setParentPrgCode(String parentPrgCode) {
		this.parentPrgCode = parentPrgCode;
	}

	/**
	 * @return menuKbn��߂��܂��B
	 */
	public int getMenuKbn() {
		return menuKbn;
	}

	/**
	 * @param menuKbn menuKbn��ݒ肵�܂��B
	 */
	public void setMenuKbn(int menuKbn) {
		this.menuKbn = menuKbn;
	}

	/**
	 * ���j���[���ǂ����𔻒肷��
	 * 
	 * @return true:���j���[�Afalse:�m�[�h
	 */
	public boolean isMenu() {
		return !BooleanUtil.toBoolean(this.menuKbn);
	}

	/**
	 * @return displayIndex��߂��܂��B
	 */
	public int getDisplayIndex() {
		return displayIndex;
	}

	/**
	 * @param displayIndex displayIndex��ݒ肵�܂��B
	 */
	public void setDisplayIndex(int displayIndex) {
		this.displayIndex = displayIndex;
	}

	/**
	 * @return loadClassName��߂��܂��B
	 */
	public String getLoadClassName() {
		return loadClassName;
	}

	/**
	 * @param loadClassName loadClassName��ݒ肵�܂��B
	 */
	public void setLoadClassName(String loadClassName) {
		this.loadClassName = loadClassName;
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
	 * ���������敪��ݒ肷��B
	 * 
	 * @param procAuthKbn ���������敪
	 */
	public void setProcAuthKbn(int procAuthKbn) {
		this.procAuthKbn = procAuthKbn;
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
	 * @return systemClassification��߂��܂��B
	 */
	public SystemClassification getSystemClassification() {
		return systemClassification;
	}

	/**
	 * @param systemClassification systemClassification��ݒ肵�܂��B
	 */
	public void setSystemClassification(SystemClassification systemClassification) {
		this.systemClassification = systemClassification;
	}

}
