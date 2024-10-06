package jp.co.ais.trans2.model.vessel;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;

/**
 * Vessel�̌�������
 * 
 * @author AIS
 */
public class VesselSearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** Vessel�R�[�h */
	protected String code = null;

	/** Vessel�R�[�h�O����v */
	protected String codeLike = null;

	/** Vessel�R�[�hFrom */
	protected String codeFrom = null;

	/** Vessel�R�[�hTo */
	protected String codeTo = null;

	/** ���[�U�[�R�[�h */
	protected String fleetUsrCode = null;

	/** ����like */
	protected String namesLike = null;

	/** ��������like */
	protected String namekLike = null;

	/** The owner name like. */
	protected String ownerNameLike = null;

	/** The flag if include suspended. */
	protected boolean includeSuspended = false;

	/** The ship type code. */
	protected String shipTypeCode = null;

	/** The ship form code. */
	protected String shipFormCode = null;

	/** The ship owner code. */
	protected String shipOwnerCode = null;

	/** �L������ */
	protected Date validTerm = null;

	/** ���D�_��o�^�ςݑΏۂ� */
	protected boolean shipBuild = false;

	/** �R���Ǘ��p */
	protected boolean forBM = false;

	/** �R�[�h���X�g */
	protected List<String> codeList;

	/** �ڍ׏����܂�(�X�s�[�h�A�`�R������A�C��R������) */
	protected boolean includeDetail = false;

	/** RELET�D���܂܂�邩 */
	protected boolean includeRelet = true;

	/** �ŏI�X�V���� */
	protected Date lastUpdateDate = null;

	/** CALL_SIGN */
	protected String callsign = null;

	/** ���݌��� */
	protected int nowCount = 0;

	/** true:��������܂� */
	protected boolean includeBunkerType = false;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public VesselSearchCondition clone() {
		try {
			return (VesselSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
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
	 * Vessel�R�[�hFrom���擾���܂��B
	 * 
	 * @return Vessel�R�[�hFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * Vessel�R�[�hFrom��ݒ肵�܂��B
	 * 
	 * @param codeFrom Vessel�R�[�hFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * Vessel�R�[�hTo���擾���܂��B
	 * 
	 * @return Vessel�R�[�hTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * Vessel�R�[�hTo��ݒ肵�܂��B
	 * 
	 * @param codeTo Vessel�R�[�hTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * ���[�U�[�R�[�h
	 * 
	 * @return ���[�U�[�R�[�h
	 */
	public String getFleetUsrCode() {
		return fleetUsrCode;
	}

	/**
	 * ���[�U�[�R�[�h
	 * 
	 * @param vesselUsrCode ���[�U�[�R�[�h
	 */
	public void setFleetUsrCode(String vesselUsrCode) {
		this.fleetUsrCode = vesselUsrCode;
	}

	/**
	 * @return namesLike��߂��܂��B
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * @param namesLike namesLike��ݒ肵�܂��B
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * @return ��ЃR�[�h�O����v��߂��܂��B
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * @param codeLike ��ЃR�[�h�O����v��ݒ肵�܂��B
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * ��������like���擾���܂��B
	 * 
	 * @return ��������like
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * ��������like��ݒ肵�܂��B
	 * 
	 * @param namekLike ��������like
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * �L���������擾���܂��B
	 * 
	 * @return validTerm �L��������߂��܂��B
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * �L��������ݒ肵�܂��B
	 * 
	 * @param validTerm
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

	/**
	 * ���D�_��o�^�ς݂̂ݑΏۂɂ��邩
	 * 
	 * @return shipBuild true:���D�_��o�^�ς݂̂ݑΏ�
	 */
	public boolean isShipBuild() {
		return shipBuild;
	}

	/**
	 * ���D�_��o�^�ςݑΏۂ���ݒ肷��
	 * 
	 * @param shipBuild ���D�_��o�^�ς݃t���O true:���D�_��o�^�ς݂̂ݑΏ�
	 */
	public void setShipBuild(boolean shipBuild) {
		this.shipBuild = shipBuild;
	}

	/**
	 * Gets the owner name like.
	 * 
	 * @return the owner name like
	 */
	public String getOwnerNameLike() {
		return ownerNameLike;
	}

	/**
	 * Sets the owner name like.
	 * 
	 * @param ownerNameLike the new owner name like
	 */
	public void setOwnerNameLike(String ownerNameLike) {
		this.ownerNameLike = ownerNameLike;
	}

	/**
	 * Gets the ship type code.
	 * 
	 * @return the type code
	 */
	public String getShipTypeCode() {
		return shipTypeCode;
	}

	/**
	 * Sets the ship type code.
	 * 
	 * @param shipTypeCode the new type code
	 */
	public void setShipTypeCode(String shipTypeCode) {
		this.shipTypeCode = shipTypeCode;
	}

	/**
	 * Gets the ship form code.
	 * 
	 * @return the ship form code
	 */
	public String getShipFormCode() {
		return shipFormCode;
	}

	/**
	 * Sets the ship form code.
	 * 
	 * @param shipFormCode the new size code
	 */
	public void setShipForm(String shipFormCode) {
		this.shipFormCode = shipFormCode;
	}

	/**
	 * Gets the ship owner code.
	 * 
	 * @return the ship owner code
	 */
	public String getShipOwnerCode() {
		return shipOwnerCode;
	}

	/**
	 * Sets the ship owner code.
	 * 
	 * @param shipOwnerCode the new ship owner code
	 */
	public void setShipOwnerCode(String shipOwnerCode) {
		this.shipOwnerCode = shipOwnerCode;
	}

	/**
	 * Checks if is include suspended.
	 * 
	 * @return true, if is include suspended
	 */
	public boolean isIncludeSuspended() {
		return includeSuspended;
	}

	/**
	 * Sets the include suspended.
	 * 
	 * @param includeSuspended the new include suspended
	 */
	public void setIncludeSuspended(boolean includeSuspended) {
		this.includeSuspended = includeSuspended;
	}

	/**
	 * �R���Ǘ��p�̎擾
	 * 
	 * @return forBM �R���Ǘ��p
	 */
	public boolean isForBM() {
		return forBM;
	}

	/**
	 * �R���Ǘ��p�̐ݒ�
	 * 
	 * @param forBM �R���Ǘ��p
	 */
	public void setForBM(boolean forBM) {
		this.forBM = forBM;
	}

	/**
	 * @see jp.co.ais.trans2.model.FilterableCondition#getCodeList()
	 */
	public List<String> getCodeList() {
		return codeList;
	}

	/**
	 * @param codeList
	 */
	public void setCodeList(List<String> codeList) {
		this.codeList = codeList;
	}

	/**
	 * �ڍ׏����܂�(�X�s�[�h�A�`�R������A�C��R������)�̎擾
	 * 
	 * @return includeDetail �ڍ׏����܂�(�X�s�[�h�A�`�R������A�C��R������)
	 */
	public boolean isIncludeDetail() {
		return includeDetail;
	}

	/**
	 * �ڍ׏����܂�(�X�s�[�h�A�`�R������A�C��R������)�̐ݒ�
	 * 
	 * @param includeDetail �ڍ׏����܂�(�X�s�[�h�A�`�R������A�C��R������)
	 */
	public void setIncludeDetail(boolean includeDetail) {
		this.includeDetail = includeDetail;
	}

	/**
	 * RELET�D���܂܂�邩�̎擾
	 * 
	 * @return includeRelet RELET�D���܂܂�邩
	 */
	public boolean isIncludeRelet() {
		return includeRelet;
	}

	/**
	 * RELET�D���܂܂�邩�̐ݒ�
	 * 
	 * @param includeRelet RELET�D���܂܂�邩
	 */
	public void setIncludeRelet(boolean includeRelet) {
		this.includeRelet = includeRelet;
	}

	/**
	 * �ŏI�X�V�����̎擾
	 * 
	 * @return lastUpdateDate �ŏI�X�V����
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * �ŏI�X�V�����̐ݒ�
	 * 
	 * @param lastUpdateDate �ŏI�X�V����
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * CALL_SIGN�̎擾
	 * 
	 * @return callsign CALL_SIGN
	 */
	public String getCallsign() {
		return callsign;
	}

	/**
	 * CALL_SIGN�̐ݒ�
	 * 
	 * @param callsign CALL_SIGN
	 */
	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}

	/**
	 * ���݌����̎擾
	 * 
	 * @return nowCount ���݌���
	 */
	public int getNowCount() {
		return nowCount;
	}

	/**
	 * ���݌����̐ݒ�
	 * 
	 * @param nowCount ���݌���
	 */
	public void setNowCount(int nowCount) {
		this.nowCount = nowCount;
	}

	/**
	 * true:��������܂ނ̎擾
	 * 
	 * @return includeBunkerType true:��������܂�
	 */
	public boolean isIncludeBunkerType() {
		return includeBunkerType;
	}

	/**
	 * true:��������܂ނ̐ݒ�
	 * 
	 * @param includeBunkerType true:��������܂�
	 */
	public void setIncludeBunkerType(boolean includeBunkerType) {
		this.includeBunkerType = includeBunkerType;
	}

}
