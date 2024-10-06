package jp.co.ais.trans2.model.vessel;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.bunkertype.*;

/**
 * Vessel.
 */
public class Vessel extends TransferBase implements Cloneable, AutoCompletable, FilterableEntity {

	/**
	 * �N���[��
	 */
	@Override
	public Vessel clone() {
		try {
			Vessel bean = (Vessel) super.clone();

			return bean;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * Gets the possible strings for item.
	 * 
	 * @param bean the bean
	 * @return �\�����z��擾
	 */
	public static String[] getPossibleStringsForItem(Vessel bean) {
		String shipType = bean.getSHIP_TYPE_NAME();

		String ownTc = bean.getOwnerDivision();
		return new String[] { bean.getCode(), bean.getName(), shipType, ownTc };
	}

	/**
	 * Gets the preferred string for item.
	 * 
	 * @param item the item
	 * @return �\����
	 */
	public static String getPreferredStringForItem(Vessel item) {
		String[] possible = getPossibleStringsForItem(item);
		String preferred = null;
		if (possible != null && possible.length > 0) {
			StringBuilder sb = new StringBuilder();
			for (String str : possible) {
				if (sb.length() > 0) {
					sb.append("/");
				}
				sb.append(Util.avoidNull(str));
			}
			preferred = sb.toString();
		}
		return preferred;
	}

	/**
	 * Gets the owner division.<br>
	 * ���ӁFUI�����̂ݎg����
	 * 
	 * @return OWN/TC
	 */
	public String getOwnerDivision() {
		return OWNR_SHIP_NAME;
	}

	/**
	 * Gets the display text.
	 * 
	 * @return �\����
	 */
	public String getDisplayText() {
		return getPreferredStringForItem(this);
	}

	/**
	 * To string.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("KAI_CODE=");
		sb.append(companyCode);
		sb.append("/VESSEL_CODE=");
		sb.append(code);
		sb.append("/VESSEL_NAME=");
		sb.append(name);
		sb.append("/VESSEL_NAME_S=");
		sb.append(names);
		sb.append("/VESSEL_NAME_K=");
		sb.append(namek);
		return sb.toString();
	}

	/** ��ЃR�[�h. */
	protected String companyCode = null;

	/** Vessel�R�[�h. */
	protected String code = null;

	/** Vessel����. */
	protected String name = null;

	/** �����̔Ԃ�ON/OFF. */
	protected boolean autoNumber = false;

	/** �̔Ԍ���. */
	protected int digits = 3;

	/** �����̔Ԃ̊J�n�R�[�h */
	protected String autoNumberStartCode = null;

	/** Vessel����. */
	protected String names = null;

	/** Vessel��������. */
	protected String namek = null;

	/** �����i����R�[�h. */
	protected String stockCode = null;

	/** �����i���喼��. */
	protected String stockName = null;

	/** �R�����R�[�h. */
	protected String fuelCode = null;

	/** �R����喼��. */
	protected String fuelName = null;

	/** �_��^�C�v(�ݑD�A�ؑD). */
	protected String contractType = null;

	/** �Ώۃ^�C�v */
	protected int objectType = -1;

	/** OWNR_CODE. */
	protected String OWNR_CODE = null;

	/** OWNERSHIP_CODE. */
	protected String OWNR_SHIP_CODE = null;

	/** VESSEL_TYPE. */
	protected String SHIP_TYPE = null;

	/** �D�햼��. */
	protected String SHIP_TYPE_NAME = null;

	/** VESSEL_SIZE. */
	protected String SHIP_FORM = null;

	/** DWT_SMR_MT. */
	protected BigDecimal DWT_SMR_MT = null;

	/** DWT_TRP_MT. */
	protected BigDecimal DWT_TRP_MT = null;

	/** DWT_WTR_MT. */
	protected BigDecimal DWT_WTR_MT = null;

	/** DWT_SMR_LT. */
	protected BigDecimal DWT_SMR_LT = null;

	/** DWT_TRP_LT. */
	protected BigDecimal DWT_TRP_LT = null;

	/** DWT_WTR_LT. */
	protected BigDecimal DWT_WTR_LT = null;

	/** DRAFT_SMR_LT. */
	protected BigDecimal DRAFT_SMR_MT = null;

	/** DRAFT_TRP_MT. */
	protected BigDecimal DRAFT_TRP_MT = null;

	/** DRAFT_WTR_MT. */
	protected BigDecimal DRAFT_WTR_MT = null;

	/** DRAFT_SMR_LT. */
	protected BigDecimal DRAFT_SMR_LT = null;

	/** DRAFT_TRP_LT. */
	protected BigDecimal DRAFT_TRP_LT = null;

	/** DRAFT_WTR_LT. */
	protected BigDecimal DRAFT_WTR_LT = null;

	/** The LIGHT_WEIGHT_MT. */
	protected BigDecimal LIGHT_WEIGHT_MT = null;

	/** The LIGHT_WEIGHT_LT. */
	protected BigDecimal LIGHT_WEIGHT_LT = null;

	/** The LIGHT_WEIGHT_TRP. */
	protected BigDecimal LIGHT_WEIGHT_TRP = null;

	/** CALL SIGN. */
	protected String CALL_SIGN = null;

	/** PHONE 1. */
	protected String PHONE_1 = null;

	/** PHONE 2. */
	protected String PHONE_2 = null;

	/** FAX. */
	protected String FAX = null;

	/** E-MAIL. */
	protected String E_MAIL = null;

	/** TELEX. */
	protected String TELEX = null;

	/** CHARTERERS P&I CLUB NAME. */
	protected String CHTR_PI_NAME = null;

	/** OWNERS P&I CLUB NAME. */
	protected String OWR_PI_NAME = null;

	/** H&M CLUB VALUE. */
	protected BigDecimal HM_VALUE = null;

	/** NEXT DRY DOCK DATE. */
	protected Date NEXT_DRY_DOCK_DATE = null;

	/** BUILDER. */
	protected String BUILDER = null;

	/** LAUNCH_DATE. */
	protected Date LAUNCH_DATE = null;

	/** BUILD_DATE. */
	protected Date BUILD_DATE = null;

	/** CLASS. */
	protected String CLASS = null;

	/** FLAG. */
	protected String FLAG = null;

	/** IMO_NO. */
	protected String IMO_NO = null;

	/** The OFCL_NO. */
	protected String OFCL_NO = null;

	/** CRANE CAPACITY */
	protected BigDecimal CRN_CAP = null;

	/** LREG M. */
	protected BigDecimal LREG_M = null;

	/** LREG FT. */
	protected BigDecimal LREG_FT = null;

	/** INTERNATIONAL GROSS TON. */
	protected BigDecimal GROSS = null;

	/** INTERNATIONAL NET TON. */
	protected BigDecimal NET = null;

	/** PANAMA GROSS TON. */
	protected BigDecimal PANAMA_GROSS = null;

	/** PANAMA NET TON. */
	protected BigDecimal PANAMA_NET = null;

	/** SUEZ GROSS TON. */
	protected BigDecimal SUEZ_GROSS = null;

	/** SUEZ NET TON. */
	protected BigDecimal SUEZ_NET = null;

	/** The LOA M. */
	protected BigDecimal LOA_M = null;

	/** The LOA FT. */
	protected BigDecimal LOA_FT = null;

	/** The LPP M. */
	protected BigDecimal LPP_M = null;

	/** The LPP FT. */
	protected BigDecimal LPP_FT = null;

	/** BEAM_M. */
	protected BigDecimal BEAM_M = null;

	/** BEAM_FT. */
	protected BigDecimal BEAM_FT = null;

	/** DEPTH_M. */
	protected BigDecimal DEPTH_M = null;

	/** DEPTH_FT. */
	protected BigDecimal DEPTH_FT = null;

	/** AIR_DRAFT_M. */
	protected BigDecimal AIR_DRAFT_M = null;

	/** AIR_DRAFT_FT. */
	protected BigDecimal AIR_DRAFT_FT = null;

	/** ENGN_MAKER. */
	protected String ENGN_MAKER = null;

	/** ENGN_TYPE. */
	protected String ENGN_TYPE = null;

	/** NOR_BHP. */
	protected String NOR_BHP = null;

	/** NOR_NW. */
	protected String NOR_NW = null;

	/** NOR_RPM. */
	protected String NOR_RPM = null;

	/** MCR_BHP. */
	protected String MCR_BHP = null;

	/** MCR_NW. */
	protected String MCR_NW = null;

	/** MCR_RPM. */
	protected String MCR_RPM = null;

	/** YARD_NO. */
	protected String YARD_NO = null;

	/** MNG_CMP. */
	protected String MNG_CMP = null;

	/** REG_OWN. */
	protected String REG_OWN = null;

	/** ESTIMATE ONLY. */
	protected int EST_FLG = -1;

	/** RELET ONLY. */
	protected int RELET_FLG = -1;

	/** SUSPENDED VESSEL FLG. */
	protected int SUSPENDED_FLG = -1;

	/** SCRUBBER�敪 */
	protected int SCRUBBER_KBN = -1;

	/** �f�t�H���g����^�C�v���g�� */
	protected int USE_DEFAULT_BNKR_TYPE = -1;

	/** REMARKS. */
	protected String REMARKS = null;

	/** FLEET MV USER. */
	protected String FLEET_USR_CODE = null;

	/** �D�S���Җ� */
	protected String FLEET_USR_NAME = null;

	/** �D�S���җ��� */
	protected String FLEET_USR_NAME_S = null;

	/** STR_DATE. */
	protected Date STR_DATE;

	/** END_DATE. */
	protected Date END_DATE;

	/** INP_DATE. */
	protected Date INP_DATE;

	/** UPD_DATE. */
	protected Date UPD_DATE;

	/** PRG_ID. */
	protected String PRG_ID;

	/** The USR_ID. */
	protected String USR_ID;

	/** OWNR_SHIP_NAME. */
	protected String OWNR_SHIP_NAME = null;

	/** VESSEL_TYPE_NAME */
	protected String VESSEL_TYPE_NAME = null;

	/** VESSEL_SIZE_NAME */
	protected String VESSEL_SIZE_NAME = null;

	/** TRI_NAME */
	protected String TRI_NAME = null;

	/** DEPT_CODE */
	protected String DEPT_CODE = null;

	/** DEPT_NAME */
	protected String DEPT_NAME = null;

	/** DEPT_NAME_S */
	protected String DEPT_NAME_S = null;

	/** �X�s�[�h. */
	protected List<VesselSpeed> speedList = new ArrayList<VesselSpeed>();

	/** AUX�C��R������ */
	protected List<VesselSeaCons> auxSeaConsList = new ArrayList<VesselSeaCons>();

	/** �`�R������. */
	protected List<VesselPortCons> portConsList = new ArrayList<VesselPortCons>();

	/** Hold and tank list. */
	protected List<VesselHold> holdConsList = new ArrayList<VesselHold>();

	/** ����^�C�v�ݒ� */
	protected List<CM_BNKR_TYPE_MST> bnkrTypeList = new ArrayList<CM_BNKR_TYPE_MST>();

	/**
	 * ��ЃR�[�h���擾���܂��B.
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h��ݒ肵�܂��B.
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * Vessel�R�[�h���擾���܂��B.
	 * 
	 * @return Vessel�R�[�h
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Vessel�R�[�h��ݒ肵�܂��B.
	 * 
	 * @param code Vessel�R�[�h
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Vessel���̂��擾���܂��B.
	 * 
	 * @return Vessel����
	 */
	public String getName() {
		return name;
	}

	/**
	 * Vessel���̂�ݒ肵�܂��B.
	 * 
	 * @param name Vessel����
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * �����̔Ԃ�ON/OFF
	 * 
	 * @return autoNumber
	 */
	public boolean isAutoNumber() {
		return this.autoNumber;
	}

	/**
	 * �����̔Ԃ�ON/OFF
	 * 
	 * @param autoNumber
	 */
	public void setAutoNumber(boolean autoNumber) {
		this.autoNumber = autoNumber;
	}

	/**
	 * �̔Ԍ���
	 * 
	 * @return digit
	 */
	public int getDigits() {
		return this.digits;
	}

	/**
	 * �̔Ԍ���
	 * 
	 * @param digits
	 */
	public void setDigits(int digits) {
		this.digits = digits;
	}

	/**
	 * �����̔Ԃ̊J�n�R�[�h�̎擾
	 * 
	 * @return autoNumberStartCode �����̔Ԃ̊J�n�R�[�h
	 */
	public String getAutoNumberStartCode() {
		return autoNumberStartCode;
	}

	/**
	 * �����̔Ԃ̊J�n�R�[�h�̐ݒ�
	 * 
	 * @param autoNumberStartCode �����̔Ԃ̊J�n�R�[�h
	 */
	public void setAutoNumberStartCode(String autoNumberStartCode) {
		this.autoNumberStartCode = autoNumberStartCode;
	}

	/**
	 * Vessel���̂��擾���܂��B.
	 * 
	 * @return Vessel����
	 */
	public String getNames() {
		return names;
	}

	/**
	 * Vessel���̂�ݒ肵�܂��B.
	 * 
	 * @param names Vessel����
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * Vessel�������̂��擾���܂��B.
	 * 
	 * @return Vessel��������
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * Vessel�������̂�ݒ肵�܂��B.
	 * 
	 * @param namek Vessel��������
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * Gets the date from.
	 * 
	 * @return dateFrom��߂��܂��B
	 */
	public Date getDateFrom() {
		return STR_DATE;
	}

	/**
	 * Sets the date from.
	 * 
	 * @param dateFrom dateFrom��ݒ肵�܂��B
	 */
	public void setDateFrom(Date dateFrom) {
		this.STR_DATE = dateFrom;
	}

	/**
	 * Gets the date to.
	 * 
	 * @return dateTo��߂��܂��B
	 */
	public Date getDateTo() {
		return END_DATE;
	}

	/**
	 * Sets the date to.
	 * 
	 * @param dateTo dateTo��ݒ肵�܂��B
	 */
	public void setDateTo(Date dateTo) {
		this.END_DATE = dateTo;
	}

	/**
	 * �����i����R�[�h���擾���܂��B.
	 * 
	 * @return stockCode
	 */
	public String getStockCode() {
		return stockCode;
	}

	/**
	 * �����i����R�[�h��ݒ肵�܂��B.
	 * 
	 * @param stockCode stockCode
	 */
	public void setStockCode(String stockCode) {
		this.stockCode = stockCode;
	}

	/**
	 * �����i���喼�̂��擾���܂��B.
	 * 
	 * @return stockName
	 */
	public String getStockName() {
		return stockName;
	}

	/**
	 * �����i���喼�̂�ݒ肵�܂��B.
	 * 
	 * @param stockName stockName
	 */
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	/**
	 * �R�����R�[�h���擾���܂��B.
	 * 
	 * @return fuelCode
	 */
	public String getFuelCode() {
		return fuelCode;
	}

	/**
	 * �R�����R�[�h��ݒ肵�܂��B.
	 * 
	 * @param fuelCode fuelCode
	 */
	public void setFuelCode(String fuelCode) {
		this.fuelCode = fuelCode;
	}

	/**
	 * �R����喼�̂��擾���܂��B.
	 * 
	 * @return fuelName
	 */
	public String getFuelName() {
		return fuelName;
	}

	/**
	 * �R����喼�̂�ݒ肵�܂��B.
	 * 
	 * @param fuelName fuelName
	 */
	public void setFuelName(String fuelName) {
		this.fuelName = fuelName;
	}

	/**
	 * �Ώۃ^�C�v�擾
	 * 
	 * @return objectType
	 */
	public Integer getObjectType() {
		return objectType;
	}

	/**
	 * �Ώۃ^�C�v�ݒ�
	 * 
	 * @param objectType
	 */
	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	/**
	 * �_��^�C�v���擾���܂��B.
	 * 
	 * @return contractType
	 */
	public String getContractType() {
		return contractType;
	}

	/**
	 * �_��^�C�v��ݒ肵�܂��B.
	 * 
	 * @param contractType the new contract type
	 */
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}

	/**
	 * OWNR_CODE�̎擾.
	 * 
	 * @return OWNR_CODE OWNR_CODE�̎擾
	 */
	public String getOWNR_CODE() {
		return OWNR_CODE;
	}

	/**
	 * OWNR_CODE�̐ݒ�.
	 * 
	 * @param OWNR_CODE OWNR_CODE�̐ݒ�
	 */
	public void setOWNR_CODE(String OWNR_CODE) {
		this.OWNR_CODE = OWNR_CODE;
	}

	/**
	 * OWNERSHIP_CODE�̎擾.
	 * 
	 * @return OWNR_SHIP_CODE OWNERSHIP_CODE�̎擾
	 */
	public String getOWNR_SHIP_CODE() {
		return OWNR_SHIP_CODE;
	}

	/**
	 * OWNERSHIP_CODE�̐ݒ�.
	 * 
	 * @param OWNR_SHIP_CODE OWNERSHIP_CODE�̐ݒ�
	 */
	public void setOWNR_SHIP_CODE(String OWNR_SHIP_CODE) {
		this.OWNR_SHIP_CODE = OWNR_SHIP_CODE;
	}

	/**
	 * VESSEL_TYPE�̎擾.
	 * 
	 * @return SHIP_TYPE VESSEL_TYPE�̎擾
	 */
	public String getSHIP_TYPE() {
		return SHIP_TYPE;
	}

	/**
	 * VESSEL_TYPE�̐ݒ�.
	 * 
	 * @param SHIP_TYPE VESSEL_TYPE�̐ݒ�
	 */
	public void setSHIP_TYPE(String SHIP_TYPE) {
		this.SHIP_TYPE = SHIP_TYPE;
	}

	/**
	 * �D�햼�̂̎擾.
	 * 
	 * @return SHIP_TYPE_NAME �D�햼��
	 */
	public String getSHIP_TYPE_NAME() {
		return SHIP_TYPE_NAME;
	}

	/**
	 * �D�햼�̂̐ݒ�.
	 * 
	 * @param SHIP_TYPE_NAME �D�햼��
	 */
	public void setSHIP_TYPE_NAME(String SHIP_TYPE_NAME) {
		this.SHIP_TYPE_NAME = SHIP_TYPE_NAME;
	}

	/**
	 * VESSEL_SIZE�̎擾.
	 * 
	 * @return SHIP_FORM VESSEL_SIZE�̎擾
	 */
	public String getSHIP_FORM() {
		return SHIP_FORM;
	}

	/**
	 * VESSEL_SIZE�̐ݒ�.
	 * 
	 * @param SHIP_FORM VESSEL_SIZE�̐ݒ�
	 */
	public void setSHIP_FORM(String SHIP_FORM) {
		this.SHIP_FORM = SHIP_FORM;
	}

	/**
	 * Gets DWT_SMR_MTT.
	 * 
	 * @return DWT_SMR_MTT
	 */
	public BigDecimal getDWT_SMR_MT() {
		return DWT_SMR_MT;
	}

	/**
	 * Sets DWT_SMR_MTT.
	 * 
	 * @param DWT_SMR_MT the new dwt smr mt
	 */
	public void setDWT_SMR_MT(BigDecimal DWT_SMR_MT) {
		this.DWT_SMR_MT = DWT_SMR_MT;
	}

	/**
	 * Gets DWT_TRP_MT.
	 * 
	 * @return DWT_TRP_MT
	 */
	public BigDecimal getDWT_TRP_MT() {
		return DWT_TRP_MT;
	}

	/**
	 * Sets DWT_TRP_MT.
	 * 
	 * @param DWT_TRP_MT the new dwt trp mt
	 */
	public void setDWT_TRP_MT(BigDecimal DWT_TRP_MT) {
		this.DWT_TRP_MT = DWT_TRP_MT;
	}

	/**
	 * Gets DWT_WTR_MT.
	 * 
	 * @return DWT_WTR_MT
	 */
	public BigDecimal getDWT_WTR_MT() {
		return DWT_WTR_MT;
	}

	/**
	 * Sets DWT_WTR_MT.
	 * 
	 * @param DWT_WTR_MT the new dwt wtr mt
	 */
	public void setDWT_WTR_MT(BigDecimal DWT_WTR_MT) {
		this.DWT_WTR_MT = DWT_WTR_MT;
	}

	/**
	 * Gets DWT_SMR_LT.
	 * 
	 * @return DWT_SMR_LT
	 */
	public BigDecimal getDWT_SMR_LT() {
		return DWT_SMR_LT;
	}

	/**
	 * Sets DWT_SMR_LT.
	 * 
	 * @param DWT_SMR_LT the new dwt smr lt
	 */
	public void setDWT_SMR_LT(BigDecimal DWT_SMR_LT) {
		this.DWT_SMR_LT = DWT_SMR_LT;
	}

	/**
	 * Gets DWT_TRP_LT.
	 * 
	 * @return DWT_TRP_LT
	 */
	public BigDecimal getDWT_TRP_LT() {
		return DWT_TRP_LT;
	}

	/**
	 * Sets DWT_TRP_LT.
	 * 
	 * @param DWT_TRP_LT the new dwt trp lt
	 */
	public void setDWT_TRP_LT(BigDecimal DWT_TRP_LT) {
		this.DWT_TRP_LT = DWT_TRP_LT;
	}

	/**
	 * Gets DWT_WTR_LT.
	 * 
	 * @return DWT_WTR_LT
	 */
	public BigDecimal getDWT_WTR_LT() {
		return DWT_WTR_LT;
	}

	/**
	 * Sets DWT_WTR_LT.
	 * 
	 * @param DWT_WTR_LT the new dwt wtr lt
	 */
	public void setDWT_WTR_LT(BigDecimal DWT_WTR_LT) {
		this.DWT_WTR_LT = DWT_WTR_LT;
	}

	/**
	 * Gets DRAFT_SMR_MT.
	 * 
	 * @return DRAFT_SMR_MT
	 */
	public BigDecimal getDRAFT_SMR_MT() {
		return DRAFT_SMR_MT;
	}

	/**
	 * Sets DRAFT_SMR_MT.
	 * 
	 * @param DRAFT_SMR_MT the new draft smr mt
	 */
	public void setDRAFT_SMR_MT(BigDecimal DRAFT_SMR_MT) {
		this.DRAFT_SMR_MT = DRAFT_SMR_MT;
	}

	/**
	 * Gets DRAFT_TRP_MT.
	 * 
	 * @return DRAFT_TRP_MT
	 */
	public BigDecimal getDRAFT_TRP_MT() {
		return DRAFT_TRP_MT;
	}

	/**
	 * Sets DRAFT_TRP_MT.
	 * 
	 * @param DRAFT_TRP_MT the new draft trp mt
	 */
	public void setDRAFT_TRP_MT(BigDecimal DRAFT_TRP_MT) {
		this.DRAFT_TRP_MT = DRAFT_TRP_MT;
	}

	/**
	 * Gets DRAFT_WTR_MT.
	 * 
	 * @return DRAFT_WTR_MT
	 */
	public BigDecimal getDRAFT_WTR_MT() {
		return DRAFT_WTR_MT;
	}

	/**
	 * Sets DRAFT_WTR_MT.
	 * 
	 * @param DRAFT_WTR_MT the new draft wtr mt
	 */
	public void setDRAFT_WTR_MT(BigDecimal DRAFT_WTR_MT) {
		this.DRAFT_WTR_MT = DRAFT_WTR_MT;
	}

	/**
	 * Gets DRAFT_SMR_LT.
	 * 
	 * @return DRAFT_SMR_LT
	 */
	public BigDecimal getDRAFT_SMR_LT() {
		return DRAFT_SMR_LT;
	}

	/**
	 * Sets DRAFT_SMR_LT.
	 * 
	 * @param DRAFT_SMR_LT the new draft smr lt
	 */
	public void setDRAFT_SMR_LT(BigDecimal DRAFT_SMR_LT) {
		this.DRAFT_SMR_LT = DRAFT_SMR_LT;
	}

	/**
	 * Gets DRAFT_TRP_LT.
	 * 
	 * @return DRAFT_TRP_LT
	 */
	public BigDecimal getDRAFT_TRP_LT() {
		return DRAFT_TRP_LT;
	}

	/**
	 * Sets DRAFT_TRP_LT.
	 * 
	 * @param DRAFT_TRP_LT the new draft trp lt
	 */
	public void setDRAFT_TRP_LT(BigDecimal DRAFT_TRP_LT) {
		this.DRAFT_TRP_LT = DRAFT_TRP_LT;
	}

	/**
	 * Gets DRAFT_WTR_LT.
	 * 
	 * @return DRAFT_WTR_LT
	 */
	public BigDecimal getDRAFT_WTR_LT() {
		return DRAFT_WTR_LT;
	}

	/**
	 * Sets DRAFT_WTR_LT.
	 * 
	 * @param DRAFT_WTR_LT the new draft wtr lt
	 */
	public void setDRAFT_WTR_LT(BigDecimal DRAFT_WTR_LT) {
		this.DRAFT_WTR_LT = DRAFT_WTR_LT;
	}

	/**
	 * CALL SIGN�̎擾.
	 * 
	 * @return CALL_SIGN CALL SIGN�̎擾
	 */
	public String getCALL_SIGN() {
		return CALL_SIGN;
	}

	/**
	 * CALL SIGN�̐ݒ�.
	 * 
	 * @param CALL_SIGN CALL SIGN�̐ݒ�
	 */
	public void setCALL_SIGN(String CALL_SIGN) {
		this.CALL_SIGN = CALL_SIGN;
	}

	/**
	 * PHONE 1�̎擾.
	 * 
	 * @return PHONE_1 PHONE 1�̎擾
	 */
	public String getPHONE_1() {
		return PHONE_1;
	}

	/**
	 * PHONE�̐ݒ�.
	 * 
	 * @param PHONE_1 PHONE_1�̐ݒ�
	 */
	public void setPHONE_1(String PHONE_1) {
		this.PHONE_1 = PHONE_1;
	}

	/**
	 * PHONE 2�̎擾.
	 * 
	 * @return PHONE_2 PHONE 2�̎擾
	 */
	public String getPHONE_2() {
		return PHONE_2;
	}

	/**
	 * PHONE 2�̐ݒ�.
	 * 
	 * @param PHONE_2 PHONE_1�̐ݒ�
	 */
	public void setPHONE_2(String PHONE_2) {
		this.PHONE_2 = PHONE_2;
	}

	/**
	 * FAX�̎擾.
	 * 
	 * @return FAX FAX�̎擾
	 */
	public String getFAX() {
		return FAX;
	}

	/**
	 * FAX�̐ݒ�.
	 * 
	 * @param FAX FAX�̐ݒ�
	 */
	public void setFAX(String FAX) {
		this.FAX = FAX;
	}

	/**
	 * E-MAIL�̎擾.
	 * 
	 * @return E_MAIL E-MAIL�̎擾
	 */
	public String getE_MAIL() {
		return E_MAIL;
	}

	/**
	 * E-MAIL�̐ݒ�.
	 * 
	 * @param E_MAIL E-MAIL�̐ݒ�
	 */
	public void setE_MAIL(String E_MAIL) {
		this.E_MAIL = E_MAIL;
	}

	/**
	 * TELEX�̎擾.
	 * 
	 * @return TELEX TELEX�̎擾
	 */
	public String getTELEX() {
		return TELEX;
	}

	/**
	 * TELEX�̐ݒ�.
	 * 
	 * @param TELEX TELEX�̐ݒ�
	 */
	public void setTELEX(String TELEX) {
		this.TELEX = TELEX;
	}

	/**
	 * CHARTERERS P&I CLUB NAME�̎擾.
	 * 
	 * @return CHTR_PI_NAME CHARTERERS P&I CLUB NAME�̎擾
	 */
	public String getCHTR_PI_NAME() {
		return CHTR_PI_NAME;
	}

	/**
	 * CHARTERERS P&I CLUB NAME�̐ݒ�.
	 * 
	 * @param CHTR_PI_NAME CHARTERERS P&I CLUB NAME�̐ݒ�
	 */
	public void setCHTR_PI_NAME(String CHTR_PI_NAME) {
		this.CHTR_PI_NAME = CHTR_PI_NAME;
	}

	/**
	 * OWNERS P&I CLUB NAME�̎擾.
	 * 
	 * @return OWR_PI_NAME OWNERS P&I CLUB NAME�̎擾
	 */
	public String getOWR_PI_NAME() {
		return OWR_PI_NAME;
	}

	/**
	 * OWNERS P&I CLUB NAME�̐ݒ�.
	 * 
	 * @param OWR_PI_NAME OWNERS P&I CLUB NAME�̐ݒ�
	 */
	public void setOWR_PI_NAME(String OWR_PI_NAME) {
		this.OWR_PI_NAME = OWR_PI_NAME;
	}

	/**
	 * Gets LIGHT_WEIGHT_MT.
	 * 
	 * @return LIGHT_WEIGHT_MT
	 */
	public BigDecimal getLIGHT_WEIGHT_MT() {
		return LIGHT_WEIGHT_MT;
	}

	/**
	 * Sets the light weight mt.
	 * 
	 * @param LIGHT_WEIGHT_MT the new light weight mt
	 */
	public void setLIGHT_WEIGHT_MT(BigDecimal LIGHT_WEIGHT_MT) {
		this.LIGHT_WEIGHT_MT = LIGHT_WEIGHT_MT;
	}

	/**
	 * Gets LIGHT_WEIGHT_LT.
	 * 
	 * @return LIGHT_WEIGHT_LT
	 */
	public BigDecimal getLIGHT_WEIGHT_LT() {
		return LIGHT_WEIGHT_LT;
	}

	/**
	 * Sets LIGHT_WEIGHT_LT.
	 * 
	 * @param LIGHT_WEIGHT_LT the new light weight lt
	 */
	public void setLIGHT_WEIGHT_LT(BigDecimal LIGHT_WEIGHT_LT) {
		this.LIGHT_WEIGHT_LT = LIGHT_WEIGHT_LT;
	}

	/**
	 * Gets LIGHT_WEIGHT_TRP.
	 * 
	 * @return LIGHT_WEIGHT_TRP
	 */
	public BigDecimal getLIGHT_WEIGHT_TRP() {
		return LIGHT_WEIGHT_TRP;
	}

	/**
	 * Sets LIGHT_WEIGHT_TRP.
	 * 
	 * @param LIGHT_WEIGHT_TRP the new light weight trp
	 */
	public void setLIGHT_WEIGHT_TRP(BigDecimal LIGHT_WEIGHT_TRP) {
		this.LIGHT_WEIGHT_TRP = LIGHT_WEIGHT_TRP;
	}

	/**
	 * NEXT DRY DOCK DATE�̎擾.
	 * 
	 * @return NEXT_DRY_DOCK_DATE NEXT DRY DOCK DATE�̎擾
	 */
	public Date getNEXT_DRY_DOCK_DATE() {
		return NEXT_DRY_DOCK_DATE;
	}

	/**
	 * NEXT DRY DOCK DATE�̐ݒ�.
	 * 
	 * @param NEXT_DRY_DOCK_DATE NEXT DRY DOCK DATE�̐ݒ�
	 */
	public void setNEXT_DRY_DOCK_DATE(Date NEXT_DRY_DOCK_DATE) {
		this.NEXT_DRY_DOCK_DATE = NEXT_DRY_DOCK_DATE;
	}

	/**
	 * BUILDER�̐ݒ�.
	 * 
	 * @param BUILDER BUILDER�̐ݒ�
	 */
	public void setBUILDER(String BUILDER) {
		this.BUILDER = BUILDER;
	}

	/**
	 * Gets LAUNCH_DATE.
	 * 
	 * @return LAUNCH_DATE
	 */
	public Date getLAUNCH_DATE() {
		return LAUNCH_DATE;
	}

	/**
	 * Sets LAUNCH_DATE.
	 * 
	 * @param LAUNCH_DATE the new launch date
	 */
	public void setLAUNCH_DATE(Date LAUNCH_DATE) {
		this.LAUNCH_DATE = LAUNCH_DATE;
	}

	/**
	 * Gets BUILD_DATE.
	 * 
	 * @return BUILD_DATE
	 */
	public Date getBUILD_DATE() {
		return BUILD_DATE;
	}

	/**
	 * Sets BUILD_DATE.
	 * 
	 * @param BUILD_DATE the new builds the date
	 */
	public void setBUILD_DATE(Date BUILD_DATE) {
		this.BUILD_DATE = BUILD_DATE;
	}

	/**
	 * CLASS�̎擾.
	 * 
	 * @return CLASS CLASS�̎擾
	 */
	public String getCLASS() {
		return CLASS;
	}

	/**
	 * CLASS�̐ݒ�.
	 * 
	 * @param CLASS CLASS�̐ݒ�
	 */
	public void setCLASS(String CLASS) {
		this.CLASS = CLASS;
	}

	/**
	 * FLAG�̎擾.
	 * 
	 * @return FLAG FLAG�̎擾
	 */
	public String getFLAG() {
		return FLAG;
	}

	/**
	 * FLAG�̐ݒ�.
	 * 
	 * @param FLAG FLAG�̐ݒ�
	 */
	public void setFLAG(String FLAG) {
		this.FLAG = FLAG;
	}

	/**
	 * IMO_NO�̎擾.
	 * 
	 * @return IMO_NO IMO_NO�̎擾
	 */
	public String getIMO_NO() {
		return IMO_NO;
	}

	/**
	 * IMO_NO�̐ݒ�.
	 * 
	 * @param IMO_NO IMO_NO�̐ݒ�
	 */
	public void setIMO_NO(String IMO_NO) {
		this.IMO_NO = IMO_NO;
	}

	/**
	 * Get OFCL_NO.
	 * 
	 * @return OFCL_NO.
	 */
	public String getOFCL_NO() {
		return OFCL_NO;
	}

	/**
	 * Sets OFCL_NO..
	 * 
	 * @param OFCL_NO the new ofcl no
	 */
	public void setOfclNo(String OFCL_NO) {
		this.OFCL_NO = OFCL_NO;
	}

	/**
	 * CRANE CAPACITY�̎擾
	 * 
	 * @return CRN_CAP CRANE CAPACITY
	 */
	public BigDecimal getCRN_CAP() {
		return CRN_CAP;
	}

	/**
	 * CRANE CAPACITY�̐ݒ�
	 * 
	 * @param CRN_CAP CRANE CAPACITY
	 */
	public void setCRN_CAP(BigDecimal CRN_CAP) {
		this.CRN_CAP = CRN_CAP;
	}

	/**
	 * Gets LREG_M.
	 * 
	 * @return LREG_M
	 */
	public BigDecimal getLREG_M() {
		return LREG_M;
	}

	/**
	 * Sets LREG_M.
	 * 
	 * @param LREG_M the new lreg M
	 */
	public void setLregM(BigDecimal LREG_M) {
		this.LREG_M = LREG_M;
	}

	/**
	 * Gets LREG_FT.
	 * 
	 * @return LREG_FT
	 */
	public BigDecimal getLREG_FT() {
		return LREG_FT;
	}

	/**
	 * Sets LREG_FT.
	 * 
	 * @param LREG_FT the new lreg ft
	 */
	public void setLREG_FT(BigDecimal LREG_FT) {
		this.LREG_FT = LREG_FT;
	}

	/**
	 * Gets LPP_M.
	 * 
	 * @return LPP_M
	 */
	public BigDecimal getLPP_M() {
		return LPP_M;
	}

	/**
	 * Sets LPP_M.
	 * 
	 * @param LPP_M the new lpp m
	 */
	public void setLPP_M(BigDecimal LPP_M) {
		this.LPP_M = LPP_M;
	}

	/**
	 * Gets LPP_FT.
	 * 
	 * @return LPP_FT
	 */
	public BigDecimal getLPP_FT() {
		return LPP_FT;
	}

	/**
	 * Sets LPP_FT.
	 * 
	 * @param LPP_FT the new lpp ft
	 */
	public void setLPP_FT(BigDecimal LPP_FT) {
		this.LPP_FT = LPP_FT;
	}

	/**
	 * Sets OFCL_NO.
	 * 
	 * @param OFCL_NO the new ofcl no
	 */
	public void setOFCL_NO(String OFCL_NO) {
		this.OFCL_NO = OFCL_NO;
	}

	/**
	 * Sets LREG_M.
	 * 
	 * @param LREG_M the new lreg m
	 */
	public void setLREG_M(BigDecimal LREG_M) {
		this.LREG_M = LREG_M;
	}

	/**
	 * Sets BEAM_M.
	 * 
	 * @param BEAM_M the new beam m
	 */
	public void setBEAM_M(BigDecimal BEAM_M) {
		this.BEAM_M = BEAM_M;
	}

	/**
	 * INTERNATIONAL GROSS TON�̎擾.
	 * 
	 * @return GROSS INTERNATIONAL GROSS TON�̎擾
	 */
	public BigDecimal getGROSS() {
		return GROSS;
	}

	/**
	 * INTERNATIONAL GROSS TON�̐ݒ�.
	 * 
	 * @param GROSS the new gross
	 */
	public void setGROSS(BigDecimal GROSS) {
		this.GROSS = GROSS;
	}

	/**
	 * INTERNATIONAL NET TON�̎擾.
	 * 
	 * @return INITL_GT INTERNATIONAL NET TON�̎擾
	 */
	public BigDecimal getNET() {
		return NET;
	}

	/**
	 * INTERNATIONAL NET TON�̐ݒ�.
	 * 
	 * @param NET �̐ݒ�
	 */
	public void setNET(BigDecimal NET) {
		this.NET = NET;
	}

	/**
	 * Gets PANAMA_GROSS.
	 * 
	 * @return PANAMA_GROSS
	 */
	public BigDecimal getPANAMA_GROSS() {
		return PANAMA_GROSS;
	}

	/**
	 * Sets PANAMA_GROSS.
	 * 
	 * @param PANAMA_GROSS the new panama gross
	 */
	public void setPANAMA_GROSS(BigDecimal PANAMA_GROSS) {
		this.PANAMA_GROSS = PANAMA_GROSS;
	}

	/**
	 * Gets PANAMA_NET.
	 * 
	 * @return PANAMA_NET
	 */
	public BigDecimal getPANAMA_NET() {
		return PANAMA_NET;
	}

	/**
	 * Sets PANAMA_NET.
	 * 
	 * @param PANAMA_NET the new panama net
	 */
	public void setPANAMA_NET(BigDecimal PANAMA_NET) {
		this.PANAMA_NET = PANAMA_NET;
	}

	/**
	 * Gets SUEZ_GROSS.
	 * 
	 * @return SUEZ_GROSS
	 */
	public BigDecimal getSUEZ_GROSS() {
		return SUEZ_GROSS;
	}

	/**
	 * Sets SUEZ_GROSS.
	 * 
	 * @param SUEZ_GROSS the new suez gross
	 */
	public void setSUEZ_GROSS(BigDecimal SUEZ_GROSS) {
		this.SUEZ_GROSS = SUEZ_GROSS;
	}

	/**
	 * Gets SUEZ_NET.
	 * 
	 * @return SUEZ_NET
	 */
	public BigDecimal getSUEZ_NET() {
		return SUEZ_NET;
	}

	/**
	 * Sets SUEZ_NET.
	 * 
	 * @param SUEZ_NET the new suez net
	 */
	public void setSUEZ_NET(BigDecimal SUEZ_NET) {
		this.SUEZ_NET = SUEZ_NET;
	}

	/**
	 * Gets LOA_M.
	 * 
	 * @return LOA_M
	 */
	public BigDecimal getLOA_M() {
		return LOA_M;
	}

	/**
	 * Sets LOA_M.
	 * 
	 * @param LOA_M the new loa m
	 */
	public void setLOA_M(BigDecimal LOA_M) {
		this.LOA_M = LOA_M;
	}

	/**
	 * Gets LOA_FT.
	 * 
	 * @return LOA_FT
	 */
	public BigDecimal getLOA_FT() {
		return LOA_FT;
	}

	/**
	 * Sets LOA_FT.
	 * 
	 * @param LOA_FT the new loa ft
	 */
	public void setLOA_FT(BigDecimal LOA_FT) {
		this.LOA_FT = LOA_FT;
	}

	/**
	 * Gets BEAM_M.
	 * 
	 * @return BEAM_M
	 */
	public BigDecimal getBEAM_M() {
		return BEAM_M;
	}

	/**
	 * Gets BEAM_FT.
	 * 
	 * @return BEAM_FT
	 */
	public BigDecimal getBEAM_FT() {
		return BEAM_FT;
	}

	/**
	 * Sets BEAM_FT.
	 * 
	 * @param BEAM_FT the new beam ft
	 */
	public void setBEAM_FT(BigDecimal BEAM_FT) {
		this.BEAM_FT = BEAM_FT;
	}

	/**
	 * Gets DEPTH_M.
	 * 
	 * @return DEPTH_M
	 */
	public BigDecimal getDEPTH_M() {
		return DEPTH_M;
	}

	/**
	 * Sets DEPTH_M.
	 * 
	 * @param DEPTH_M the new depth m
	 */
	public void setDEPTH_M(BigDecimal DEPTH_M) {
		this.DEPTH_M = DEPTH_M;
	}

	/**
	 * Gets DEPTH_FT.
	 * 
	 * @return DEPTH_FT
	 */
	public BigDecimal getDEPTH_FT() {
		return DEPTH_FT;
	}

	/**
	 * Sets DEPTH_FT.
	 * 
	 * @param DEPTH_FT the new depth ft
	 */
	public void setDEPTH_FT(BigDecimal DEPTH_FT) {
		this.DEPTH_FT = DEPTH_FT;
	}

	/**
	 * Gets AIR_DRAFT_M.
	 * 
	 * @return AIR_DRAFT_M
	 */
	public BigDecimal getAIR_DRAFT_M() {
		return AIR_DRAFT_M;
	}

	/**
	 * Sets AIR_DRAFT_M.
	 * 
	 * @param AIR_DRAFT_M the new air draft m
	 */
	public void setAIR_DRAFT_M(BigDecimal AIR_DRAFT_M) {
		this.AIR_DRAFT_M = AIR_DRAFT_M;
	}

	/**
	 * Gets AIR_DRAFT_FT.
	 * 
	 * @return AIR_DRAFT_FT
	 */
	public BigDecimal getAIR_DRAFT_FT() {
		return AIR_DRAFT_FT;
	}

	/**
	 * Sets AIR_DRAFT_FT.
	 * 
	 * @param AIR_DRAFT_FT the new air draft ft
	 */
	public void setAIR_DRAFT_FT(BigDecimal AIR_DRAFT_FT) {
		this.AIR_DRAFT_FT = AIR_DRAFT_FT;
	}

	/**
	 * Gets BUILDER.
	 * 
	 * @return BUILDER
	 */
	public String getBUILDER() {
		return BUILDER;
	}

	/**
	 * Gets ENGN_MAKER.
	 * 
	 * @return ENGN_MAKER
	 */
	public String getENGN_MAKER() {
		return ENGN_MAKER;
	}

	/**
	 * Sets ENGN_MAKER.
	 * 
	 * @param ENGN_MAKER the new engn maker
	 */
	public void setENGN_MAKER(String ENGN_MAKER) {
		this.ENGN_MAKER = ENGN_MAKER;
	}

	/**
	 * Gets ENGN_TYPE.
	 * 
	 * @return ENGN_TYPE
	 */
	public String getENGN_TYPE() {
		return ENGN_TYPE;
	}

	/**
	 * Sets ENGN_TYPE.
	 * 
	 * @param ENGN_TYPE the new engn type
	 */
	public void setENGN_TYPE(String ENGN_TYPE) {
		this.ENGN_TYPE = ENGN_TYPE;
	}

	/**
	 * Gets NOR_BHP.
	 * 
	 * @return NOR_BHP
	 */
	public String getNOR_BHP() {
		return NOR_BHP;
	}

	/**
	 * Sets NOR_BHP.
	 * 
	 * @param NOR_BHP the new nor bhp
	 */
	public void setNOR_BHP(String NOR_BHP) {
		this.NOR_BHP = NOR_BHP;
	}

	/**
	 * Gets NOR_NW.
	 * 
	 * @return NOR_NW
	 */
	public String getNOR_NW() {
		return NOR_NW;
	}

	/**
	 * Sets NOR_NW.
	 * 
	 * @param NOR_NW the new nor nw
	 */
	public void setNOR_NW(String NOR_NW) {
		this.NOR_NW = NOR_NW;
	}

	/**
	 * Gets NOR_RPM.
	 * 
	 * @return NOR_RPM
	 */
	public String getNOR_RPM() {
		return NOR_RPM;
	}

	/**
	 * Sets NOR_RPM.
	 * 
	 * @param NOR_RPM the new nor rpm
	 */
	public void setNOR_RPM(String NOR_RPM) {
		this.NOR_RPM = NOR_RPM;
	}

	/**
	 * Gets MCR_BHP.
	 * 
	 * @return MCR_BHP
	 */
	public String getMCR_BHP() {
		return MCR_BHP;
	}

	/**
	 * Sets MCR_BHP.
	 * 
	 * @param MCR_BHP the new mcr bhp
	 */
	public void setMCR_BHP(String MCR_BHP) {
		this.MCR_BHP = MCR_BHP;
	}

	/**
	 * Gets MCR_N.
	 * 
	 * @return MCR_N
	 */
	public String getMCR_NW() {
		return MCR_NW;
	}

	/**
	 * Sets MCR_NW.
	 * 
	 * @param MCR_NW the new mcr nw
	 */
	public void setMCR_NW(String MCR_NW) {
		this.MCR_NW = MCR_NW;
	}

	/**
	 * Gets MCR_RPM.
	 * 
	 * @return MCR_RPM
	 */
	public String getMCR_RPM() {
		return MCR_RPM;
	}

	/**
	 * Sets MCR_RPM.
	 * 
	 * @param MCR_RPM the new mcr rpm
	 */
	public void setMCR_RPM(String MCR_RPM) {
		this.MCR_RPM = MCR_RPM;
	}

	/**
	 * Gets the yard no.
	 * 
	 * @return the yard no
	 */
	public String getYARD_NO() {
		return YARD_NO;
	}

	/**
	 * Sets YARD_NO .
	 * 
	 * @param YARD_NO the new yard no
	 */
	public void setYARD_NO(String YARD_NO) {
		this.YARD_NO = YARD_NO;
	}

	/**
	 * Gets MNG_CMP.
	 * 
	 * @return MNG_CMP
	 */
	public String getMNG_CMP() {
		return MNG_CMP;
	}

	/**
	 * Sets MNG_CMP.
	 * 
	 * @param MNG_CMP the new mng cmp
	 */
	public void setMNG_CMP(String MNG_CMP) {
		this.MNG_CMP = MNG_CMP;
	}

	/**
	 * Gets REG_OWN.
	 * 
	 * @return REG_OWN
	 */
	public String getREG_OWN() {
		return REG_OWN;
	}

	/**
	 * Sets REG_OWN.
	 * 
	 * @param REG_OWN the new reg own
	 */
	public void setREG_OWN(String REG_OWN) {
		this.REG_OWN = REG_OWN;
	}

	/**
	 * ESTIMATE ONLY�̎擾.
	 * 
	 * @return EST_FLG ESTIMATE ONLY�̎擾
	 */
	public int getEST_FLG() {
		return EST_FLG;
	}

	/**
	 * ESTIMATE ONLY�̐ݒ�.
	 * 
	 * @param EST_FLG ESTIMATE ONLY�̐ݒ�
	 */
	public void setEST_FLG(int EST_FLG) {
		this.EST_FLG = EST_FLG;
	}

	/**
	 * RELET ONLY�̎擾.
	 * 
	 * @return EST_FLG ESTIMATE ONLY�̎擾
	 */
	public int getRELET_FLG() {
		return RELET_FLG;
	}

	/**
	 * RELET ONLY�̐ݒ�.
	 * 
	 * @param RELET_FLG ESTIMATE ONLY�̐ݒ�
	 */
	public void setRELET_FLG(int RELET_FLG) {
		this.RELET_FLG = RELET_FLG;
	}

	/**
	 * SUSPENDED VESSEL FLG�̎擾.
	 * 
	 * @return SUSPENDED_FLG SUSPENDED VESSEL FLG�̎擾
	 */
	public int getSUSPENDED_FLG() {
		return SUSPENDED_FLG;
	}

	/**
	 * SUSPENDED VESSEL FLG�̐ݒ�.
	 * 
	 * @param SUSPENDED_FLG SUSPENDED VESSEL FLG�̐ݒ�
	 */
	public void setSUSPENDED_FLG(int SUSPENDED_FLG) {
		this.SUSPENDED_FLG = SUSPENDED_FLG;
	}

	/**
	 * SCRUBBER�敪�̎擾
	 * 
	 * @return SCRUBBER_KBN SCRUBBER�敪
	 */
	public int getSCRUBBER_KBN() {
		return SCRUBBER_KBN;
	}

	/**
	 * SCRUBBER�敪�̐ݒ�
	 * 
	 * @param SCRUBBER_KBN SCRUBBER�敪
	 */
	public void setSCRUBBER_KBN(int SCRUBBER_KBN) {
		this.SCRUBBER_KBN = SCRUBBER_KBN;
	}

	/**
	 * �f�t�H���g����^�C�v���g���̎擾
	 * 
	 * @return USE_DEFAULT_BNKR_TYPE �f�t�H���g����^�C�v���g��
	 */
	public int getUSE_DEFAULT_BNKR_TYPE() {
		return USE_DEFAULT_BNKR_TYPE;
	}

	/**
	 * �f�t�H���g����^�C�v���g���̐ݒ�
	 * 
	 * @param USE_DEFAULT_BNKR_TYPE �f�t�H���g����^�C�v���g��
	 */
	public void setUSE_DEFAULT_BNKR_TYPE(int USE_DEFAULT_BNKR_TYPE) {
		this.USE_DEFAULT_BNKR_TYPE = USE_DEFAULT_BNKR_TYPE;
	}

	/**
	 * REMARKS�̎擾.
	 * 
	 * @return REMARKS REMARKS�̎擾
	 */
	public String getREMARKS() {
		return REMARKS;
	}

	/**
	 * REMARKS�̐ݒ�.
	 * 
	 * @param REMARKS REMARKS�̐ݒ�
	 */
	public void setREMARKS(String REMARKS) {
		this.REMARKS = REMARKS;
	}

	/**
	 * FLEET MV USER���擾���܂�.
	 * 
	 * @return FLEET MV USER
	 */
	public String getFLEET_USR_CODE() {
		return FLEET_USR_CODE;
	}

	/**
	 * FLEET MV USER��ݒ肵�܂�.
	 * 
	 * @param fLEET_USR_CODE FLEET MV USER
	 */
	public void setFLEET_USR_CODE(String fLEET_USR_CODE) {
		FLEET_USR_CODE = fLEET_USR_CODE;
	}

	/**
	 * �D�S���Җ��̎擾
	 * 
	 * @return FLEET_USR_NAME �D�S���Җ�
	 */
	public String getFLEET_USR_NAME() {
		return FLEET_USR_NAME;
	}

	/**
	 * �D�S���Җ��̐ݒ�
	 * 
	 * @param FLEET_USR_NAME �D�S���Җ�
	 */
	public void setFLEET_USR_NAME(String FLEET_USR_NAME) {
		this.FLEET_USR_NAME = FLEET_USR_NAME;
	}

	/**
	 * �D�S���җ��̂̎擾
	 * 
	 * @return FLEET_USR_NAME_S �D�S���җ���
	 */
	public String getFLEET_USR_NAME_S() {
		return FLEET_USR_NAME_S;
	}

	/**
	 * �D�S���җ��̂̐ݒ�
	 * 
	 * @param FLEET_USR_NAME_S �D�S���җ���
	 */
	public void setFLEET_USR_NAME_S(String FLEET_USR_NAME_S) {
		this.FLEET_USR_NAME_S = FLEET_USR_NAME_S;
	}

	/**
	 * Gets STR_DATE
	 * 
	 * @return STR_DATE
	 */
	public Date getSTR_DATE() {
		return STR_DATE;
	}

	/**
	 * Sets STR_DATE
	 * 
	 * @param STR_DATE
	 */
	public void setSTR_DATE(Date STR_DATE) {
		this.STR_DATE = STR_DATE;
	}

	/**
	 * Gets END_DATE
	 * 
	 * @return END_DATE
	 */
	public Date getEND_DATE() {
		return END_DATE;
	}

	/**
	 * Sets END_DATE
	 * 
	 * @param END_DATE
	 */
	public void setEND_DATE(Date END_DATE) {
		this.END_DATE = END_DATE;
	}

	/**
	 * Gets INP_DATE
	 * 
	 * @return INP_DATE
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * Sets INP_DATE
	 * 
	 * @param INP_DATE
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * Gets UPD_DATE
	 * 
	 * @return UPD_DATE
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * Sets UPD_DATE
	 * 
	 * @param UPD_DATE
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * Gets PRG_ID
	 * 
	 * @return PRG_ID
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * Sets PRG_ID
	 * 
	 * @param PRG_ID
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * Gets USR_ID
	 * 
	 * @return USR_ID
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * Sets USR_ID
	 * 
	 * @param USR_ID
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * @return OWNR_SHIP_NAME
	 */
	public String getOWNR_SHIP_NAME() {
		return OWNR_SHIP_NAME;
	}

	/**
	 * @param cODE_MST_NAME
	 */
	public void setOWNR_SHIP_NAME(String cODE_MST_NAME) {
		OWNR_SHIP_NAME = cODE_MST_NAME;
	}

	/**
	 * @return VESSEL_TYPE_NAME
	 */
	public String getVESSEL_TYPE_NAME() {
		return VESSEL_TYPE_NAME;
	}

	/**
	 * @param cODE_NAME_TYPE
	 */
	public void setVESSEL_TYPE_NAME(String cODE_NAME_TYPE) {
		VESSEL_TYPE_NAME = cODE_NAME_TYPE;
	}

	/**
	 * @return VESSEL_SIZE_NAME
	 */
	public String getVESSEL_SIZE_NAME() {
		return VESSEL_SIZE_NAME;
	}

	/**
	 * @param cODE_NAME_FORM
	 */
	public void setVESSEL_SIZE_NAME(String cODE_NAME_FORM) {
		VESSEL_SIZE_NAME = cODE_NAME_FORM;
	}

	/**
	 * @return TRI_NAME
	 */
	public String getTRI_NAME() {
		return TRI_NAME;
	}

	/**
	 * @param tRI_NAME
	 */
	public void setTRI_NAME(String tRI_NAME) {
		TRI_NAME = tRI_NAME;
	}

	/**
	 * @return DEPT_CODE
	 */
	public String getDEPT_CODE() {
		return DEPT_CODE;
	}

	/**
	 * @param dEPT_CODE
	 */
	public void setDEPT_CODE(String dEPT_CODE) {
		DEPT_CODE = dEPT_CODE;
	}

	/**
	 * @return DEPT_NAME
	 */
	public String getDEPT_NAME() {
		return DEPT_NAME;
	}

	/**
	 * @param dEPT_NAME
	 */
	public void setDEPT_NAME(String dEPT_NAME) {
		DEPT_NAME = dEPT_NAME;
	}

	/**
	 * @return DEPT_NAME_S
	 */
	public String getDEPT_NAME_S() {
		return DEPT_NAME_S;
	}

	/**
	 * @param dEPT_NAME_S
	 */
	public void setDEPT_NAME_S(String dEPT_NAME_S) {
		DEPT_NAME_S = dEPT_NAME_S;
	}

	/**
	 * �X�s�[�h�̎擾.
	 * 
	 * @return speedList �X�s�[�h
	 */
	public List<VesselSpeed> getSpeedList() {
		return speedList;
	}

	/**
	 * �X�s�[�h�̐ݒ�.
	 * 
	 * @param speedList �X�s�[�h
	 */
	public void setSpeedList(List<VesselSpeed> speedList) {
		if (speedList == null) {
			this.speedList = new ArrayList<VesselSpeed>();
			return;
		}
		this.speedList = speedList;
	}

	/**
	 * �X�s�[�h�̑}��
	 * 
	 * @param speeds �X�s�[�h
	 */
	public void addSpeed(VesselSpeed... speeds) {

		if (speedList == null) {
			this.speedList = new ArrayList<VesselSpeed>();
		}
		for (VesselSpeed entity : speeds) {
			this.speedList.add(entity);
		}
	}

	/**
	 * AUX�q�CSEA_CONS���X�g�̎擾
	 * 
	 * @return auxSeaConsList AUX�q�CSEA_CONS���X�g
	 */
	public List<VesselSeaCons> getAuxSeaConsList() {
		return auxSeaConsList;
	}

	/**
	 * AUX�q�CSEA_CONS���X�g�̐ݒ�
	 * 
	 * @param auxSeaConsList AUX�q�CSEA_CONS���X�g
	 */
	public void setAuxSeaConsList(List<VesselSeaCons> auxSeaConsList) {
		if (auxSeaConsList == null) {
			this.auxSeaConsList = new ArrayList<VesselSeaCons>();
			return;
		}
		this.auxSeaConsList = auxSeaConsList;
	}

	/**
	 * AUX�q�C�R������X�g�̑}��
	 * 
	 * @param auxSeaConses AUX�q�C�R������X�g
	 */
	public void addAuxSeaCons(VesselSeaCons... auxSeaConses) {
		if (auxSeaConsList == null) {
			this.auxSeaConsList = new ArrayList<VesselSeaCons>();
		}
		for (VesselSeaCons entity : auxSeaConses) {
			this.auxSeaConsList.add(entity);
		}
	}

	/**
	 * �`�R������̎擾.
	 * 
	 * @return portConsList �`�R������
	 */
	public List<VesselPortCons> getPortConsList() {
		return portConsList;
	}

	/**
	 * �`�R������̐ݒ�.
	 * 
	 * @param portConsList �`�R������
	 */
	public void setPortConsList(List<VesselPortCons> portConsList) {
		if (portConsList == null) {
			this.portConsList = new ArrayList<VesselPortCons>();
			return;
		}
		this.portConsList = portConsList;
	}

	/**
	 * �`�R������̑}��
	 * 
	 * @param portConses
	 */
	public void addPortCons(VesselPortCons... portConses) {

		if (portConsList == null) {
			this.portConsList = new ArrayList<VesselPortCons>();
		}
		for (VesselPortCons entity : portConses) {
			this.portConsList.add(entity);
		}
	}

	/**
	 * Gets the hold consumption list.
	 * 
	 * @return list
	 */
	public List<VesselHold> getHoldConsList() {
		return holdConsList;
	}

	/**
	 * Sets the hold consumption list.
	 * 
	 * @param list
	 */
	public void setHoldConsList(List<VesselHold> list) {
		if (list == null) {
			this.holdConsList = new ArrayList<VesselHold>();
			return;
		}
		this.holdConsList = list;
	}

	/**
	 * ����^�C�v���X�g���擾
	 * 
	 * @return list
	 */
	public List<CM_BNKR_TYPE_MST> getBunkerTypeList() {
		return bnkrTypeList;
	}

	/**
	 * ����^�C�v���X�g��ݒ�
	 * 
	 * @param list
	 */
	public void setBunkerTypeList(List<CM_BNKR_TYPE_MST> list) {
		if (list == null) {
			this.bnkrTypeList = new ArrayList<CM_BNKR_TYPE_MST>();
			return;
		}
		this.bnkrTypeList = list;
	}

	/** SHORT NAME */
	protected String SHORT_NAME = null;

	/**
	 * @return SHORT NAME
	 */
	public String getSHORT_NAME() {
		return SHORT_NAME;
	}

	/**
	 * @param sHORT_NAME SHORT NAME
	 */
	public void setSHORT_NAME(String sHORT_NAME) {
		SHORT_NAME = sHORT_NAME;
	}

}
