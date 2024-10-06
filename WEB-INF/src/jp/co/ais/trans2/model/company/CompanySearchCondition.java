package jp.co.ais.trans2.model.company;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * ��Ђ̌�������
 * 
 * @author AIS
 */
public class CompanySearchCondition extends TransferBase implements Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = -4640605841564467077L;

	/** ��ЃR�[�h�J�n */
	protected String codeFrom = null;

	/** ��ЃR�[�h�I�� */
	protected String codeTo = null;

	/** ��ЃR�[�h */
	protected String code = null;

	/** ��ЃR�[�h�O����v */
	protected String codeLike = null;

	/** ����like */
	protected String namesLike = null;

	/** �L������ */
	protected Date validTerm = null;

	/** �O���[�v��v�敪 */
	protected int groupAccountDivision = -1;

	/** ��ʉ� ��v */
	protected String keyCurrencyCode;

	/** ��ЃR�[�h(�����w��p) */
	protected Set<String> codeList = new LinkedHashSet<String>();

	/** ���Y���Z�i�K�ȊO�̉�� */
	protected int settlementStageOtherThan = -1;

	/** �@�\�ʉ� */
	protected String fncCurrencyCode;

	/** �w���ЃR�[�h�ȊO�̏ꍇ */
	protected String excludeCompanyCode;

	/** ��ʉ݃R�[�h���X�g */
	protected List<String> keyCurrencyCodeList = null;

	/** �ǉ���������SQL */
	protected String addonWhereSql = null;

	/** SPC����\�� */
	protected boolean notShowSpc = false;

	/** INVOICE�g�p(��Њ�b���p���)��\�����邩 */
	protected boolean showInvoice = false;

	/** �K�w */
	protected int level = -1;

	/** ��ʉ�ЃR�[�h */
	protected String superiorCompanyCode = null;

	/** ��Бg�D�R�[�h */
	protected String organizationCode = null;

	/** �z���g�D���܂ނ� */
	protected boolean includeUnder = false;

	/** �K�i���������s���Ǝғo�^�ԍ� */
	protected String invRegNo = null;

	/** AR�F�p��������SIGNER�\���� */
	protected boolean isShowARSignerEng = false;

	/** �C���{�C�X���x�t���O */
	protected boolean isInvoiceFlg = false;

	/**
	 * �w���ЃR�[�h�ȊO���擾����
	 * 
	 * @return String
	 */
	public String getExcludeCompanyCode() {
		return excludeCompanyCode;
	}

	/**
	 * �w���ЃR�[�h�ȊO�̐ݒ肷��
	 * 
	 * @param excludeKaiCode
	 */
	public void setExcludeCompanyCode(String excludeKaiCode) {
		this.excludeCompanyCode = excludeKaiCode;
	}

	/**
	 * �@�\�ʉ݂��擾����
	 * 
	 * @return String
	 */
	public String getFncCurrencyCode() {
		return fncCurrencyCode;
	}

	/**
	 * �@�\�ʉ݂̐ݒ肷��
	 * 
	 * @param fncCurrencyCode
	 */
	public void setFncCurrencyCode(String fncCurrencyCode) {
		this.fncCurrencyCode = fncCurrencyCode;
	}

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public CompanySearchCondition clone() {
		try {
			return (CompanySearchCondition) super.clone();

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
	 * @return codeFrom��߂��܂��B
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * @param codeFrom codeFrom��ݒ肵�܂��B
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * @return codeTo��߂��܂��B
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * @param codeTo codeTo��ݒ肵�܂��B
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * @return validTerm��߂��܂��B
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * @param validTerm validTerm��ݒ肵�܂��B
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
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
	 * �O���[�v��Ђ̂�
	 */
	public void setGroupAccountOnly() {
		this.groupAccountDivision = 1; // �g�p����

	}

	/**
	 * �O���[�v��v�敪
	 * 
	 * @return �O���[�v��v�敪
	 */
	public int getGroupAccountDivision() {
		return groupAccountDivision;
	}

	/**
	 * �O���[�v��v�敪
	 * 
	 * @param groupAccountDivision �O���[�v��v�敪
	 */
	public void setGroupAccountDivision(int groupAccountDivision) {
		this.groupAccountDivision = groupAccountDivision;
	}

	/**
	 * ��ʉ�(��v)
	 * 
	 * @return ��ʉ�(��v)
	 */
	public String getKeyCurrencyCode() {
		return keyCurrencyCode;
	}

	/**
	 * ��ʉ�(��v)
	 * 
	 * @param keyCurrencyCode ��ʉ�(��v)
	 */
	public void setKeyCurrencyCode(String keyCurrencyCode) {
		this.keyCurrencyCode = keyCurrencyCode;
	}

	/**
	 * ��ЃR�[�h(�����w��p)
	 * 
	 * @return ��ЃR�[�h(�����w��p)
	 */
	public Set<String> getCodeList() {
		return codeList;
	}

	/**
	 * ��ЃR�[�h(�����w��p)
	 * 
	 * @param codeList ��ЃR�[�h(�����w��p)
	 */
	public void setCodeList(Set<String> codeList) {
		this.codeList = codeList;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @param code_ ��ЃR�[�h
	 */
	public void addCode(String code_) {
		this.codeList.add(code_);
	}

	/**
	 * ���Y���Z�i�K�ȊO�̉��
	 * 
	 * @return ���Y���Z�i�K�ȊO�̉��
	 */
	public int getSettlementStageOtherThan() {
		return settlementStageOtherThan;
	}

	/**
	 * ���Y���Z�i�K�ȊO�̉��
	 * 
	 * @param settlementStageOtherThan
	 */
	public void setSettlementStageOtherThan(int settlementStageOtherThan) {
		this.settlementStageOtherThan = settlementStageOtherThan;
	}

	/**
	 * ��ʉ݃R�[�h���X�g�̎擾
	 * 
	 * @return keyCurrencyCodeList ��ʉ݃R�[�h���X�g
	 */
	public List<String> getKeyCurrencyCodeList() {
		return keyCurrencyCodeList;
	}

	/**
	 * ��ʉ݃R�[�h���X�g�̐ݒ�
	 * 
	 * @param keyCurrencyCodeList ��ʉ݃R�[�h���X�g
	 */
	public void setKeyCurrencyCodeList(List<String> keyCurrencyCodeList) {
		this.keyCurrencyCodeList = keyCurrencyCodeList;
	}

	/**
	 * �ǉ���������SQL�̎擾
	 * 
	 * @return addonWhereSql �ǉ���������SQL
	 */
	public String getAddonWhereSql() {
		return addonWhereSql;
	}

	/**
	 * �ǉ���������SQL�̐ݒ�
	 * 
	 * @param addonWhereSql �ǉ���������SQL
	 */
	public void setAddonWhereSql(String addonWhereSql) {
		this.addonWhereSql = addonWhereSql;
	}

	/**
	 * SPC����\���̎擾
	 * 
	 * @return notShowSpc SPC����\��
	 */
	public boolean isNotShowSpc() {
		return notShowSpc;
	}

	/**
	 * SPC����\���̐ݒ�
	 * 
	 * @param notShowSpc SPC����\��
	 */
	public void setNotShowSpc(boolean notShowSpc) {
		this.notShowSpc = notShowSpc;
	}

	/**
	 * INVOICE�g�p(��Њ�b���p���)�\�����邩
	 * 
	 * @return showInvoice INVOICE�g�p(��Њ�b���p���)�\�����邩
	 */
	public boolean isShowInvoice() {
		return showInvoice;
	}

	/**
	 * INVOICE�g�p(��Њ�b���p���)�\�����邩�̐ݒ�
	 * 
	 * @param showInvoice INVOICE�g�p(��Њ�b���p���)�\�����邩
	 */
	public void setShowInvoice(boolean showInvoice) {
		this.showInvoice = showInvoice;
	}

	/**
	 * �K�w�̎擾
	 * 
	 * @return level �K�w
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * �K�w�̐ݒ�
	 * 
	 * @param level �K�w
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * ��ʉ�ЃR�[�h�̎擾
	 * 
	 * @return superiorCompanyCode ��ʉ�ЃR�[�h
	 */
	public String getSuperiorCompanyCode() {
		return superiorCompanyCode;
	}

	/**
	 * ��ʉ�ЃR�[�h�̐ݒ�
	 * 
	 * @param superiorCompanyCode ��ʉ�ЃR�[�h
	 */
	public void setSuperiorCompanyCode(String superiorCompanyCode) {
		this.superiorCompanyCode = superiorCompanyCode;
	}

	/**
	 * ��Бg�D�R�[�h�̎擾
	 * 
	 * @return organizationCode ��Бg�D�R�[�h
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}

	/**
	 * ��Бg�D�R�[�h�̐ݒ�
	 * 
	 * @param organizationCode ��Бg�D�R�[�h
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	/**
	 * @return �z���g�D���܂ނ�
	 */
	public boolean isIncludeUnder() {
		return includeUnder;
	}

	/**
	 * �z���g�D���܂ނ�
	 * 
	 * @param includeUnder
	 */
	public void setIncludeUnder(boolean includeUnder) {
		this.includeUnder = includeUnder;
	}

	/**
	 * @return invRegNo��߂��܂��B
	 */
	public String getInvRegNo() {
		return invRegNo;
	}

	/**
	 * @param invRegNo invRegNo��ݒ肵�܂��B
	 */
	public void setInvRegNo(String invRegNo) {
		this.invRegNo = invRegNo;
	}

	/**
	 * @return isShowARSignerEng AR�F�p��������SIGNER�\����
	 */
	public boolean isShowARSignerEng() {
		return isShowARSignerEng;
	}

	/**
	 * AR�F�p��������SIGNER�\����
	 * 
	 * @param isShowARSignerEng
	 */
	public void setShowARSignerEng(boolean isShowARSignerEng) {
		this.isShowARSignerEng = isShowARSignerEng;
	}

	/**
	 * �C���{�C�X���x�t���O�̎擾
	 * 
	 * @return isInvoiceFlg �C���{�C�X���x�t���O
	 */
	public boolean isInvoiceFlg() {
		return isInvoiceFlg;
	}

	/**
	 * �C���{�C�X���x�t���O�̐ݒ�
	 * 
	 * @param isInvoiceFlg �C���{�C�X���x�t���O
	 */
	public void setInvoiceFlg(boolean isInvoiceFlg) {
		this.isInvoiceFlg = isInvoiceFlg;
	}

}
