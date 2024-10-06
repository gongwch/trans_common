package jp.co.ais.trans2.op.model.item;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * OP�A�C�e����������
 */
public class OPItemSearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** Item�R�[�h */
	protected String code = null;

	/** Item�R�[�h�O����v */
	protected String codeLike = null;

	/** Item�R�[�hFrom */
	protected String codeFrom = null;

	/** Item�R�[�hTo */
	protected String codeTo = null;

	/** ����like */
	protected String namesLike = null;

	/** ��������like */
	protected String namekLike = null;

	/** �_��^�C�v */
	protected OPContractType contractType = null;

	/** �A�C�e������敪 */
	protected OPItemControlDivision itemControlDivision = null;

	/** �A�C�e��SUB�敪 */
	protected OPItemSubDivision itemSubDivision = null;

	/** �A�C�e��SUB�R�[�h�i�����j */
	protected List<OPItemSubDivision> itemSubDivisions = null;

	/** �R�[�h���X�g */
	protected List<String> codeList = null;

	/** BUNKER_TYPE_CODE */
	protected String brkrTypeCode = null;

	/** OWNR_SHIP_CODE */
	protected String ownrShipCode = null;

	/** �ݎ؋敪 */
	protected Dc dc = null;

	/** SA�敪 */
	protected OPSaKbn sa = null;

	/** �Ȗ�-�⏕-����̖��̕\�� */
	protected boolean showAccountName = false;

	/** �R�~�b�V�����敪 */
	protected int ADCOM_KBN = -1;

	/** Brokarage�敪 */
	protected int BRKR_KBN = -1;

	/** �ŏI�X�V���� */
	protected Date lastUpdateDate = null;

	/** ��Ȗڂ̖��̂̂ݕ\�� */
	protected boolean onlyShowAccountName = false;

	/** ���݌��� */
	protected int nowCount = 0;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public OPItemSearchCondition clone() {
		try {
			return (OPItemSearchCondition) super.clone();

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
	 * Item�R�[�hFrom���擾���܂��B
	 * 
	 * @return Item�R�[�hFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * Item�R�[�hFrom��ݒ肵�܂��B
	 * 
	 * @param codeFrom Item�R�[�hFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * Item�R�[�hTo���擾���܂��B
	 * 
	 * @return Item�R�[�hTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * Item�R�[�hTo��ݒ肵�܂��B
	 * 
	 * @param codeTo SAItem�R�[�hTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
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
	 * �_��^�C�v�̎擾
	 * 
	 * @return contractType �_��^�C�v
	 */
	public OPContractType getContractType() {
		return contractType;
	}

	/**
	 * �_��^�C�v�̐ݒ�
	 * 
	 * @param opContractType �_��^�C�v
	 */
	public void setContractType(OPContractType opContractType) {
		this.contractType = opContractType;
	}

	/**
	 * �A�C�e������敪�̎擾
	 * 
	 * @return itemControlDivision �A�C�e������敪
	 */
	public OPItemControlDivision getItemControlDivision() {
		return itemControlDivision;
	}

	/**
	 * �A�C�e������敪�̐ݒ�
	 * 
	 * @param opItemControlDivision �A�C�e������敪
	 */
	public void setItemControlDivision(OPItemControlDivision opItemControlDivision) {
		this.itemControlDivision = opItemControlDivision;
	}

	/**
	 * �A�C�e��SUB�敪�̎擾
	 * 
	 * @return itemSubDivision �A�C�e��SUB�敪
	 */
	public OPItemSubDivision getItemSubDivision() {
		return itemSubDivision;
	}

	/**
	 * �A�C�e��SUB�敪�̐ݒ�
	 * 
	 * @param opItemSubDivision �A�C�e��SUB�敪
	 */
	public void setItemSubDivision(OPItemSubDivision opItemSubDivision) {
		this.itemSubDivision = opItemSubDivision;
	}

	/**
	 * �A�C�e��SUB�R�[�h�i�����j�̎擾
	 * 
	 * @return itemSubDivisions �A�C�e��SUB�R�[�h�i�����j
	 */
	public List<OPItemSubDivision> getItemSubDivisions() {
		return itemSubDivisions;
	}

	/**
	 * �A�C�e��SUB�R�[�h�i�����j�̐ݒ�
	 * 
	 * @param itemSubDivisions �A�C�e��SUB�R�[�h�i�����j
	 */
	public void setItemSubDivisions(List<OPItemSubDivision> itemSubDivisions) {
		this.itemSubDivisions = itemSubDivisions;
	}

	/**
	 * �ݎ؋敪�̎擾
	 * 
	 * @return dc �ݎ؋敪
	 */
	public Dc getDc() {
		return dc;
	}

	/**
	 * �ݎ؋敪�̐ݒ�
	 * 
	 * @param dc �ݎ؋敪
	 */
	public void setDc(Dc dc) {
		this.dc = dc;
	}

	/**
	 * SA�敪�̎擾
	 * 
	 * @return sa SA�敪
	 */
	public OPSaKbn getSa() {
		return sa;
	}

	/**
	 * SA�敪�̐ݒ�
	 * 
	 * @param sa SA�敪
	 */
	public void setSa(OPSaKbn sa) {
		this.sa = sa;
	}

	/**
	 * �Ȗ�-�⏕-����̖��̕\���̎擾
	 * 
	 * @return showAccountName �Ȗ�-�⏕-����̖��̕\��
	 */
	public boolean isShowAccountName() {
		return showAccountName;
	}

	/**
	 * �Ȗ�-�⏕-����̖��̕\���̐ݒ�
	 * 
	 * @param showAccountName �Ȗ�-�⏕-����̖��̕\��
	 */
	public void setShowAccountName(boolean showAccountName) {
		this.showAccountName = showAccountName;
	}

	/**
	 * @return brkrTypeCode��߂��܂��B
	 */
	public String getBrkrTypeCode() {
		return brkrTypeCode;
	}

	/**
	 * @param brkrTypeCode ��ݒ肵�܂��B
	 */
	public void setBrkrTypeCode(String brkrTypeCode) {
		this.brkrTypeCode = brkrTypeCode;
	}

	/**
	 * �R�~�b�V�����敪�̎擾
	 * 
	 * @return ADCOM_KBN �R�~�b�V�����敪
	 */
	public int getADCOM_KBN() {
		return ADCOM_KBN;
	}

	/**
	 * �R�~�b�V�����敪�̐ݒ�
	 * 
	 * @param ADCOM_KBN �R�~�b�V�����敪
	 */
	public void setADCOM_KBN(int ADCOM_KBN) {
		this.ADCOM_KBN = ADCOM_KBN;
	}

	/**
	 * Brokerage�敪�̎擾
	 * 
	 * @return BRKR_KBN Brokerage�敪
	 */
	public int getBRKR_KBN() {
		return BRKR_KBN;
	}

	/**
	 * Brokerage�敪�̐ݒ�
	 * 
	 * @param BRKR_KBN Brokerage�敪
	 */
	public void setBRKR_KBN(int BRKR_KBN) {
		this.BRKR_KBN = BRKR_KBN;
	}

	/**
	 * @see jp.co.ais.trans2.model.FilterableCondition#getValidTerm()
	 */
	public Date getValidTerm() {
		return null;
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
	 * ��Ȗڂ̖��̂̂ݕ\���̎擾
	 * 
	 * @return onlyShowAccountName ��Ȗڂ̖��̂̂ݕ\��
	 */
	public boolean isOnlyShowAccountName() {
		return onlyShowAccountName;
	}

	/**
	 * ��Ȗڂ̖��̂̂ݕ\���̐ݒ�
	 * 
	 * @param onlyShowAccountName ��Ȗڂ̖��̂̂ݕ\��
	 */
	public void setOnlyShowAccountName(boolean onlyShowAccountName) {
		this.onlyShowAccountName = onlyShowAccountName;
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
	 * OWNR_SHIP_CODE �̎擾
	 * 
	 * @return ownrShipCode OWNR_SHIP_CODE
	 */
	public String getOwnrShipCode() {
		return ownrShipCode;
	}

	/**
	 * OWNR_SHIP_CODE �̐ݒ�
	 * 
	 * @param ownrShipCode OWNR_SHIP_CODE
	 */
	public void setOwnrShipCode(String ownrShipCode) {
		this.ownrShipCode = ownrShipCode;
	}

	/**
	 * �R�[�h���X�g�̎擾
	 * 
	 * @return codeList �R�[�h���X�g
	 */
	public List<String> getCodeList() {
		return codeList;
	}

	/**
	 * �R�[�h���X�g�̐ݒ�
	 * 
	 * @param codeList �R�[�h���X�g
	 */
	public void setCodeList(List<String> codeList) {
		this.codeList = codeList;
	}

}
