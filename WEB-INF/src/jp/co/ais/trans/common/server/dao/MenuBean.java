package jp.co.ais.trans.common.server.dao;

import java.util.*;

import jp.co.ais.trans.common.server.*;

/**
 * ���j���[�pBean �]���p�ɁAPRG_MST����J�����𔲐�
 */
public class MenuBean implements TInterfaceHasToObjectArray {

	/** �e�[�u���� */
	public static final String TABLE = "PRG_MST";

	/** �V�X�e���R�[�h */
	private String sYS_CODE = "";

	/** �v���O�����R�[�h */
	private String pRG_CODE = "";

	/** �v���O�������� */
	private String pRG_NAME;

	/** �v���O�������� */
	private String pRG_NAME_S = "";

	/** �������x�� */
	private Integer kEN = 9;

	/** �R�����g */
	private String cOM;

	/** ���[�h���W���[�� */
	private String lD_NAME;

	/** �e�v���O�����R�[�h */
	private String pARENT_PRG_CODE = "";

	/** ���j���[�敪 */
	private boolean mENU_KBN;

	/** �\������ */
	private int dISP_INDEX;

	/**
	 * �V�X�e���R�[�h�擾
	 * 
	 * @return �V�X�e���R�[�h
	 */
	public String getSYS_CODE() {
		return sYS_CODE;
	}

	/**
	 * �V�X�e���R�[�h�ݒ�
	 * 
	 * @param sYS_CODE �V�X�e���R�[�h
	 */
	public void setSYS_CODE(String sYS_CODE) {
		this.sYS_CODE = sYS_CODE;
	}

	/**
	 * �v���O�����R�[�h�擾
	 * 
	 * @return �v���O�����R�[�h
	 */
	public String getPRG_CODE() {
		return pRG_CODE;
	}

	/**
	 * �v���O�����R�[�h�ݒ�
	 * 
	 * @param pRG_CODE �v���O�����R�[�h
	 */
	public void setPRG_CODE(String pRG_CODE) {
		this.pRG_CODE = pRG_CODE;
	}

	/**
	 * �v���O�������̎擾
	 * 
	 * @return �v���O��������
	 */
	public String getPRG_NAME() {
		return pRG_NAME;
	}

	/**
	 * �v���O�������̐ݒ�
	 * 
	 * @param pRG_NAME �v���O��������
	 */
	public void setPRG_NAME(String pRG_NAME) {
		this.pRG_NAME = pRG_NAME;
	}

	/**
	 * �v���O��������
	 * 
	 * @return �v���O��������
	 */
	public String getPRG_NAME_S() {
		return pRG_NAME_S;
	}

	/**
	 * �v���O�������̐ݒ�
	 * 
	 * @param pRG_NAME_S �v���O��������
	 */
	public void setPRG_NAME_S(String pRG_NAME_S) {
		this.pRG_NAME_S = pRG_NAME_S;
	}

	/**
	 * �������x���擾
	 * 
	 * @return �������x��
	 */
	public Integer getKEN() {
		return kEN;
	}

	/**
	 * �������x���ݒ�.
	 * 
	 * @param kEN �������x��
	 */
	public void setKEN(Integer kEN) {
		this.kEN = (kEN == null) ? 9 : kEN.intValue();
	}

	/**
	 * �R�����g�擾
	 * 
	 * @return �R�����g
	 */
	public String getCOM() {
		return cOM;
	}

	/**
	 * �R�����g�ݒ�
	 * 
	 * @param cOM �R�����g
	 */
	public void setCOM(String cOM) {
		this.cOM = cOM;
	}

	/**
	 * ���[�h���W���[�����擾
	 * 
	 * @return ���[�h���W���[����
	 */
	public String getLD_NAME() {
		return lD_NAME;
	}

	/**
	 * ���[�h���W���[�����ݒ�
	 * 
	 * @param lD_NAME ���[�h���W���[����
	 */
	public void setLD_NAME(String lD_NAME) {
		this.lD_NAME = lD_NAME;
	}

	/**
	 * �e�R�[�h�擾
	 * 
	 * @return �e�R�[�h
	 */
	public String getPARENT_PRG_CODE() {
		return pARENT_PRG_CODE;
	}

	/**
	 * �e�R�[�h�ݒ�
	 * 
	 * @param pARENT_PRG_CODE �e�R�[�h
	 */
	public void setPARENT_PRG_CODE(String pARENT_PRG_CODE) {
		this.pARENT_PRG_CODE = pARENT_PRG_CODE;
	}

	/**
	 * ���j���[�敪�ݒ�
	 * 
	 * @param isMenu true:���j���[, false:���
	 */
	public void setMENU_KBN(boolean isMenu) {
		this.mENU_KBN = isMenu;
	}

	/**
	 * ���j���[�敪����
	 * 
	 * @return true:���j���[, false:���
	 */
	public boolean isMENU_KBN() {
		return this.mENU_KBN;
	}

	/**
	 * �\�������擾
	 * 
	 * @return �\������
	 */
	public int getDISP_INDEX() {
		return dISP_INDEX;
	}

	/**
	 * �\�������ݒ�
	 * 
	 * @param dISP_INDEX �\������
	 */
	public void setDISP_INDEX(int dISP_INDEX) {
		this.dISP_INDEX = dISP_INDEX;
	}

	/**
	 * Bean���f�[�^�����X�g�`���Ŏ擾.
	 * 
	 * @return ���X�g�`���f�[�^
	 */
	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(sYS_CODE);
		list.add(pRG_CODE);
		list.add(pRG_NAME);
		list.add(pRG_NAME_S);
		list.add(kEN);
		list.add(cOM);
		list.add(lD_NAME);
		list.add(pARENT_PRG_CODE);
		list.add(mENU_KBN);
		list.add(dISP_INDEX);

		return list;
	}

	/**
	 * ������ϊ�
	 * 
	 * @return ������
	 */
	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/sYS_CODE=").append(sYS_CODE);
		buff.append("/pRG_CODE=").append(pRG_CODE);
		buff.append("/pRG_NAME=").append(pRG_NAME);
		buff.append("/pRG_NAME_S=").append(pRG_NAME_S);
		buff.append("/kEN=").append(kEN);
		buff.append("/cOM=").append(cOM);
		buff.append("/lD_NAME=").append(lD_NAME);
		buff.append("/pARENT_PRG_CODE=").append(pARENT_PRG_CODE);
		buff.append("/mENU_KBN=").append(isMENU_KBN());
		buff.append("/dISP_INDEX=").append(dISP_INDEX);
		buff.append("]");
		return buff.toString();
	}

	// �s�v�J������`(Dao�̓s�����`)
	private String kAI_CODE = "";

	private String pRG_NAME_K;

	private Date sTR_DATE;

	private Date eND_DATE;

	private Date iNP_DATE;

	private Date uPD_DATE;

	private String pRG_ID;

	private String uSR_ID;

	/**
	 * ��ЃR�[�h
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @param kAI_CODE ��ЃR�[�h
	 */
	public void setKAI_CODE(String kAI_CODE) {
		this.kAI_CODE = kAI_CODE;
	}

	/**
	 * �v���O������������
	 * 
	 * @return �v���O������������
	 */
	public String getPRG_NAME_K() {
		return pRG_NAME_K;
	}

	/**
	 * �v���O������������
	 * 
	 * @param pRG_NAME_K �v���O������������
	 */
	public void setPRG_NAME_K(String pRG_NAME_K) {
		this.pRG_NAME_K = pRG_NAME_K;
	}

	/**
	 * �J�n��
	 * 
	 * @return �J�n��
	 */
	public Date getSTR_DATE() {
		return sTR_DATE;
	}

	/**
	 * �J�n��
	 * 
	 * @param sTR_DATE �J�n��
	 */
	public void setSTR_DATE(Date sTR_DATE) {
		this.sTR_DATE = sTR_DATE;
	}

	/**
	 * �I����
	 * 
	 * @return �I����
	 */
	public Date getEND_DATE() {
		return eND_DATE;
	}

	/**
	 * �I����
	 * 
	 * @param eND_DATE �I����
	 */
	public void setEND_DATE(Date eND_DATE) {
		this.eND_DATE = eND_DATE;
	}

	/**
	 * �o�^����
	 * 
	 * @return �o�^����
	 */
	public Date getINP_DATE() {
		return iNP_DATE;
	}

	/**
	 * �o�^����
	 * 
	 * @param iNP_DATE �o�^����
	 */
	public void setINP_DATE(Date iNP_DATE) {
		this.iNP_DATE = iNP_DATE;
	}

	/**
	 * �X�V����
	 * 
	 * @return �X�V����
	 */
	public Date getUPD_DATE() {
		return uPD_DATE;
	}

	/**
	 * �X�V����
	 * 
	 * @param uPD_DATE �X�V����
	 */
	public void setUPD_DATE(Date uPD_DATE) {
		this.uPD_DATE = uPD_DATE;
	}

	/**
	 * �v���O����ID
	 * 
	 * @return �v���O����ID
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * �v���O����ID
	 * 
	 * @param pRG_ID �v���O����ID
	 */
	public void setPRG_ID(String pRG_ID) {
		this.pRG_ID = pRG_ID;
	}

	/**
	 * ���[�UID
	 * 
	 * @return ���[�UID
	 */
	public String getUSR_ID() {
		return uSR_ID;
	}

	/**
	 * ���[�UID
	 * 
	 * @param uSR_ID ���[�UID
	 */
	public void setUSR_ID(String uSR_ID) {
		this.uSR_ID = uSR_ID;
	}
}
