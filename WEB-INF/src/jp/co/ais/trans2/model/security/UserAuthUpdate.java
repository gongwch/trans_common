package jp.co.ais.trans2.model.security;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.*;

/**
 * ���[�U�F�؊Ǘ��}�X�^
 */
public class UserAuthUpdate extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** ���b�N�A�E�g���B�� */
	protected int rockcnt;

	/** ���b�N�A�E�g�J������ */
	protected String rockcnttime = null;

	/** �Œ�p�X���[�h�� */
	protected int minpwd;

	/** �p�X���[�h�L������ */
	protected String pwdterm = null;

	/** ���G���x�� */
	protected int diffilev;

	/** ����ێ��� */
	protected int histcnt;

	/** �o�^���t */
	protected Date insdate = null;

	/** �X�V���t */
	protected Date upddate = null;

	/** �v���O����ID */
	protected String prgId = null;

	/** ���[�UID */
	protected String userId = null;

	/** �p�X���[�h�����؂�ʒm���� */
	protected String pwdexp = null;

	/**
	 * @return ���b�N�A�E�g���B�񐔂�߂��܂��B
	 */
	public int getRockcnt() {
		return rockcnt;
	}

	/**
	 * @param ���b�N�A�E�g���B�񐔂�ݒ肵�܂��B
	 */
	public void setRockcnt(int rockcnt) {
		this.rockcnt = rockcnt;
	}

	/**
	 * @return ��ЃR�[�h��߂��܂��B
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param ��ЃR�[�h��ݒ肵�܂��B
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return ���b�N�A�E�g�J�����Ԃ�߂��܂��B
	 */
	public String getRockcnttime() {
		return rockcnttime;
	}

	/**
	 * @param ���b�N�A�E�g�J�����Ԃ�ݒ肵�܂��B
	 */
	public void setRockcnttime(String rockcnttime) {
		this.rockcnttime = rockcnttime;
	}

	/**
	 * @return �Œ�p�X���[�h����߂��܂��B
	 */
	public int getMinpwd() {
		return minpwd;
	}

	/**
	 * @param �Œ�p�X���[�h����ݒ肵�܂��B
	 */
	public void setMinpwd(int minpwd) {
		this.minpwd = minpwd;
	}

	/**
	 * @return �p�X���[�h�L�����Ԃ�߂��܂��B
	 */
	public String getPwdterm() {
		return pwdterm;
	}

	/**
	 * @param �p�X���[�h�L�����Ԃ�ݒ肵�܂��B
	 */
	public void setPwdterm(String pwdterm) {
		this.pwdterm = pwdterm;
	}

	/**
	 * @return ���G���x����߂��܂��B
	 */
	public int getDiffilev() {
		return diffilev;
	}

	/**
	 * @param ���G���x����ݒ肵�܂��B
	 */
	public void setDiffilev(int diffilev) {
		this.diffilev = diffilev;
	}

	/**
	 * @return ����ێ�����߂��܂��B
	 */
	public int getHistcnt() {
		return histcnt;
	}

	/**
	 * @param ����ێ�����ݒ肵�܂��B
	 */
	public void setHistcnt(int histcnt) {
		this.histcnt = histcnt;
	}

	/**
	 * �o�^���t��߂��܂�
	 * 
	 * @return true:����
	 */
	public Date getInsdate() {
		return insdate;
	}

	/**
	 * �o�^���t��ݒ肵�܂�
	 * 
	 * @param hasSubItem true:����
	 */
	public void setInsdate(Date insdate) {
		this.insdate = insdate;
	}

	/**
	 * �X�V���t��߂��܂�
	 * 
	 * @return true:����
	 */
	public Date getUpddate() {
		return upddate;
	}

	/**
	 * �X�V���t��ݒ肵�܂�
	 * 
	 * @param hasSubItem true:����
	 */
	public void setUpddate(Date upddate) {
		this.upddate = upddate;
	}

	/**
	 * �v���O����ID��߂��܂�
	 * 
	 * @return true:����
	 */
	public String getPrgid() {
		return prgId;
	}

	/**
	 * �v���O����ID��ݒ肵�܂�
	 * 
	 * @param hasSubItem true:����
	 */
	public void setPrgid(String prgId) {
		this.prgId = prgId;
	}

	/**
	 * ���[�UID��߂��܂�
	 * 
	 * @return true:����
	 */
	public String getUserid() {
		return userId;
	}

	/**
	 * ���[�UID��ݒ肵�܂�
	 * 
	 * @param hasSubItem true:����
	 */
	public void setUserid(String userId) {
		this.userId = userId;
	}

	/**
	 * �p�X���[�h�����؂�ʒm������߂��܂�
	 * 
	 * @return true:����
	 */
	public String getPwdexp() {
		return pwdexp;
	}

	/**
	 * �p�X���[�h�����؂�ʒm������ݒ肵�܂�
	 * 
	 * @param hasSubItem true:����
	 */
	public void setPwdexp(String pwdexp) {
		this.pwdexp = pwdexp;
	}

}
