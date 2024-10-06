package jp.co.ais.trans2.model.port;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.model.*;

/**
 * Port
 */
public class Port extends TransferBase implements Cloneable, AutoCompletable, FilterableEntity {

	/** �N���[�� */
	@Override
	public Port clone() {
		try {
			return (Port) super.clone();
		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return �\����
	 */
	public String getDisplayText() {
		StringBuilder sb = new StringBuilder();

		sb.append(Util.avoidNull(getName()));
		sb.append("/");
		sb.append(Util.avoidNull(COU_NAME));
		if (!Util.isNullOrEmpty(UNLOCODE)) {
			sb.append("/");
			sb.append(Util.avoidNull(UNLOCODE));
		}

		return sb.toString();
	}

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** Port�R�[�h */
	protected String code = null;

	/** Port���� */
	protected String name = null;

	/** Port���́i���q�p�j */
	protected String name_N = null;

	/** Port���� */
	protected String names = null;

	/** Port�������� */
	protected String namek = null;

	/** �L�����ԊJ�n */
	protected Date dateFrom = null;

	/** Update Date */
	protected Date UPD_DATE = null;

	/** �L�����ԏI�� */
	protected Date dateTo = null;

	/** Remarks */
	protected String REMARKS = null;

	/** REGION_CODE */
	protected String REGION_CODE = null;

	/** REGION_CODE */
	protected String REGION_NAME = null;

	/** MLIT_REGION_CODE */
	protected String MLIT_REGION_CODE = null;

	/** MLIT_COUNTRY_CODE */
	protected String MLIT_COUNTRY_CODE = null;

	/** MLIT_REGION_NAME */
	protected String MLIT_REGION_NAME = null;

	/** MLIT_COUNTRY_NAME */
	protected String MLIT_COUNTRY_NAME = null;

	/** LINER_REMARKS */
	protected String LINER_REMARKS;

	/** ���ۃR�[�h */
	protected String UNLOCODE = null;

	/** COUNTRY CODE */
	protected String COU_CODE = null;

	/** LAT */
	protected BigDecimal LAT = null;

	/** LNG */
	protected BigDecimal LNG = null;

	/** ���� */
	protected BigDecimal GMT_TIMEZONE = null;

	/** ���� */
	protected String COU_NAME = null;

	/** S_ECA_FLG */
	protected int S_ECA_FLG = -1;

	/** IPP_FLG */
	protected int IPP_FLG = -1;

	/** ���[�U�[ID */
	protected String USR_ID = null;

	/** Port Liner Charge */
	protected List<PortLinerCharge> linerChargeList = new ArrayList<PortLinerCharge>();

	/** T/S�` ���Y��p�D�J�X�^�}�C�Y */
	protected String TS_PORT_CODE;

	/** T/S�` ���Y��p�D�J�X�^�}�C�Y */
	protected String TS_PORT_NAME;

	/** �W���`�㗝�X�R�[�h */
	protected String STD_PORT_AGENT_CODE = null;

	/** �㗝�X���� */
	protected String STD_PORT_AGENT_NAME = null;

	/** �W���`�ʉ݃R�[�h */
	protected String PCG_EST_CUR_CODE = null;

	/** �W���`����z */
	protected BigDecimal PCG_EST_AMT = null;

	/**
	 * ��ЃR�[�h���擾���܂��B
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h��ݒ肵�܂��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * Port�R�[�h���擾���܂��B
	 * 
	 * @return Port�R�[�h
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Port�R�[�h��ݒ肵�܂��B
	 * 
	 * @param code Port�R�[�h
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Port���̂��擾���܂��B
	 * 
	 * @return Port����
	 */
	public String getName() {
		return name;
	}

	/**
	 * Port���̂�ݒ肵�܂��B
	 * 
	 * @param name Port����
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Port���́i���q�p�j���擾���܂��B
	 * 
	 * @return Port���́i���q�p�j
	 */
	public String getName_N() {
		return name_N;
	}

	/**
	 * Port���́i���q�p�j��ݒ肵�܂��B
	 * 
	 * @param name_N Port���́i���q�p�j
	 */
	public void setName_N(String name_N) {
		this.name_N = name_N;
	}

	/**
	 * Port���̂��擾���܂��B
	 * 
	 * @return Port����
	 */
	public String getNames() {
		return names;
	}

	/**
	 * Port���̂�ݒ肵�܂��B
	 * 
	 * @param names Port����
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * Port�������̂��擾���܂��B
	 * 
	 * @return Port��������
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * Port�������̂�ݒ肵�܂��B
	 * 
	 * @param namek Port��������
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * Port�������̂�ݒ肵�܂��B
	 * 
	 * @param MLIT_REGION_CODE Port��������
	 */
	public void setMLIT_REGION_CODE(String MLIT_REGION_CODE) {
		this.MLIT_REGION_CODE = MLIT_REGION_CODE;
	}

	/**
	 * Port�������̂��擾���܂��B
	 * 
	 * @return MLIT Region Code
	 */
	public String getMLIT_REGION_CODE() {
		return MLIT_REGION_CODE;
	}

	/**
	 * Port�������̂�ݒ肵�܂��B
	 * 
	 * @param MLIT_REGION_NAME Port��������
	 */
	public void setMLIT_REGION_NAME(String MLIT_REGION_NAME) {
		this.MLIT_REGION_NAME = MLIT_REGION_NAME;
	}

	/**
	 * Port�������̂��擾���܂��B
	 * 
	 * @return MLIT Region Code
	 */
	public String getMLIT_REGION_NAME() {
		return MLIT_REGION_NAME;
	}

	/**
	 * Port�������̂�ݒ肵�܂��B
	 * 
	 * @param MLIT_COUNTRY_CODE Port��������
	 */
	public void setMLIT_COUNTRY_CODE(String MLIT_COUNTRY_CODE) {
		this.MLIT_COUNTRY_CODE = MLIT_COUNTRY_CODE;
	}

	/**
	 * Port�������̂��擾���܂��B
	 * 
	 * @return MLIT Region Code
	 */
	public String getMLIT_COUNTRY_CODE() {
		return MLIT_COUNTRY_CODE;
	}

	/**
	 * Port�������̂�ݒ肵�܂��B
	 * 
	 * @param MLIT_COUNTRY_NAME Port��������
	 */
	public void setMLIT_COUNTRY_NAME(String MLIT_COUNTRY_NAME) {
		this.MLIT_COUNTRY_NAME = MLIT_COUNTRY_NAME;
	}

	/**
	 * Port�������̂��擾���܂��B
	 * 
	 * @return MLIT Region Code
	 */
	public String getMLIT_COUNTRY_NAME() {
		return MLIT_COUNTRY_NAME;
	}

	/**
	 * Port�������̂�ݒ肵�܂��B
	 * 
	 * @param LINER_REMARKS Port��������
	 */
	public void setLINER_REMARKS(String LINER_REMARKS) {
		this.LINER_REMARKS = LINER_REMARKS;
	}

	/**
	 * @return REGION_CODE
	 */
	public String getREGION_CODE() {
		return REGION_CODE;
	}

	/**
	 * @param REGION_CODE
	 */
	public void setREGION_CODE(String REGION_CODE) {
		this.REGION_CODE = REGION_CODE;
	}

	/**
	 * @return REGION_NAME
	 */
	public String getREGION_NAME() {
		return REGION_NAME;
	}

	/**
	 * @param rEGION_NAME
	 */
	public void setREGION_NAME(String rEGION_NAME) {
		REGION_NAME = rEGION_NAME;
	}

	/**
	 * Port�������̂��擾���܂��B
	 * 
	 * @return Liner Remarks
	 */
	public String getLINER_REMARKS() {
		return LINER_REMARKS;
	}

	/**
	 * @return IPP_FLG
	 */
	public int getIPP_FLG() {
		return IPP_FLG;
	}

	/**
	 * @param IPP_FLG
	 */
	public void setIPP_FLG(int IPP_FLG) {
		this.IPP_FLG = IPP_FLG;
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
	 * Remarks���擾���܂��B
	 * 
	 * @return REMARKS
	 */
	public String getREMARKS() {
		return REMARKS;
	}

	/**
	 * remarks��ݒ肵�܂��B
	 * 
	 * @param REMARKS
	 */
	public void setREMARKS(String REMARKS) {
		this.REMARKS = REMARKS;
	}

	/**
	 * Remarks���擾���܂��B
	 * 
	 * @return REMARKS
	 */
	public String getRemarks() {
		return REMARKS;
	}

	/**
	 * remarks��ݒ肵�܂��B
	 * 
	 * @param REMARKS
	 */
	public void setRemarks(String REMARKS) {
		this.REMARKS = REMARKS;
	}

	/**
	 * ���ۃR�[�h�̎擾
	 * 
	 * @return UNLOCODE ���ۃR�[�h
	 */
	public String getUNLOCODE() {
		return UNLOCODE;
	}

	/**
	 * ���ۃR�[�h�̐ݒ�
	 * 
	 * @param UNLOCODE ���ۃR�[�h
	 */
	public void setUNLOCODE(String UNLOCODE) {
		this.UNLOCODE = UNLOCODE;
	}

	/**
	 * GET COUNTRY CODE
	 * 
	 * @return Port�R�[�h
	 */
	public String getCOU_CODE() {
		return COU_CODE;
	}

	/**
	 * @param COU_CODE
	 */
	public void setCOU_CODE(String COU_CODE) {
		this.COU_CODE = COU_CODE;
	}

	/**
	 * @return the gMT_TIMEZONE
	 */
	public BigDecimal getGMT_TIMEZONE() {
		return GMT_TIMEZONE;
	}

	/**
	 * @param gMT_TIMEZONE the gMT_TIMEZONE to set
	 */
	public void setGMT_TIMEZONE(BigDecimal gMT_TIMEZONE) {
		GMT_TIMEZONE = gMT_TIMEZONE;
	}

	/**
	 * @return COU_NAME
	 */
	public String getCOU_NAME() {
		return COU_NAME;
	}

	/**
	 * @param cOU_NAME
	 */
	public void setCOU_NAME(String cOU_NAME) {
		COU_NAME = cOU_NAME;
	}

	/**
	 * @return LAT
	 */
	public BigDecimal getLAT() {
		return LAT;
	}

	/**
	 * @param lAT
	 */

	public void setLAT(BigDecimal lAT) {
		LAT = lAT;
	}

	/**
	 * @return LNG
	 */
	public BigDecimal getLNG() {
		return LNG;
	}

	/**
	 * @param lNG
	 */
	public void setLNG(BigDecimal lNG) {
		LNG = lNG;
	}

	/**
	 * @return S_ECA_FLG
	 */
	public int getS_ECA_FLG() {
		return S_ECA_FLG;
	}

	/**
	 * @param s_ECA_FLG
	 */
	public void setS_ECA_FLG(int s_ECA_FLG) {
		S_ECA_FLG = s_ECA_FLG;
	}

	/**
	 * ���[�U�[�ҏW���擾���܂��B
	 * 
	 * @return USR_ID
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ���[�U�[�ҏW��ݒ肵�܂��B
	 * 
	 * @param USR_ID
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * @return updDate��߂��܂��B
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * @param UPD_DATE UPD_DATE��ݒ肵�܂��B
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * linerChargeList�̎擾.
	 * 
	 * @return linerChargeList
	 */
	public List<PortLinerCharge> getChargeList() {
		return linerChargeList;
	}

	/**
	 * linerChargeList�̐ݒ�.
	 * 
	 * @param linerChargeList
	 */
	public void setChargeList(List<PortLinerCharge> linerChargeList) {
		this.linerChargeList = linerChargeList;
	}

	/**
	 * T/S�` ���擾���܂��B
	 * 
	 * @return TS_PORT_CODE T/S�`
	 */
	public String getTS_PORT_CODE() {
		return TS_PORT_CODE;
	}

	/**
	 * T/S�` ��ݒ肵�܂��B
	 * 
	 * @param TS_PORT_CODE T/S�`
	 */
	public void setTS_PORT_CODE(String TS_PORT_CODE) {
		this.TS_PORT_CODE = TS_PORT_CODE;
	}

	/**
	 * T/S�`���� ���擾���܂��B
	 * 
	 * @return TS_PORT_NAME T/S�`����
	 */
	public String getTS_PORT_NAME() {
		return TS_PORT_NAME;
	}

	/**
	 * T/S�`���� ��ݒ肵�܂��B
	 * 
	 * @param TS_PORT_NAME T/S�`����
	 */
	public void setTS_PORT_NAME(String TS_PORT_NAME) {
		this.TS_PORT_NAME = TS_PORT_NAME;
	}

	/**
	 * �W���`�㗝�X�R�[�h
	 * 
	 * @return sTD_PORT_AGENT_CODE
	 */
	public String getSTD_PORT_AGENT_CODE() {
		return STD_PORT_AGENT_CODE;
	}

	/**
	 * �W���`�㗝�X�R�[�h
	 * 
	 * @param sTD_PORT_AGENT_CODE �Z�b�g���� sTD_PORT_AGENT_CODE
	 */
	public void setSTD_PORT_AGENT_CODE(String sTD_PORT_AGENT_CODE) {
		STD_PORT_AGENT_CODE = sTD_PORT_AGENT_CODE;
	}

	/**
	 * �W���`�㗝�X���̂��擾
	 * 
	 * @return �W���`�㗝�X����
	 */
	public String getSTD_PORT_AGENT_NAME() {
		return STD_PORT_AGENT_NAME;
	}

	/**
	 * �W���`�㗝�X���̂��Z�b�g����
	 * 
	 * @param sTD_PORT_AGENT_NAME �W���`�㗝�X����
	 */
	public void setSTD_PORT_AGENT_NAME(String sTD_PORT_AGENT_NAME) {
		STD_PORT_AGENT_NAME = sTD_PORT_AGENT_NAME;
	}

	/**
	 * �W���`�ʉ݃R�[�h
	 * 
	 * @return pCG_EST_CUR_CODE
	 */
	public String getPCG_EST_CUR_CODE() {
		return PCG_EST_CUR_CODE;
	}

	/**
	 * �W���`�ʉ݃R�[�h
	 * 
	 * @param pCG_EST_CUR_CODE �Z�b�g���� pCG_EST_CUR_CODE
	 */
	public void setPCG_EST_CUR_CODE(String pCG_EST_CUR_CODE) {
		PCG_EST_CUR_CODE = pCG_EST_CUR_CODE;
	}

	/**
	 * �W���`����z
	 * 
	 * @return pCG_EST_AMT
	 */
	public BigDecimal getPCG_EST_AMT() {
		return PCG_EST_AMT;
	}

	/**
	 * �W���`����z
	 * 
	 * @param pCG_EST_AMT �Z�b�g���� pCG_EST_AMT
	 */
	public void setPCG_EST_AMT(BigDecimal pCG_EST_AMT) {
		PCG_EST_AMT = pCG_EST_AMT;
	}

}
