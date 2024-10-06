package jp.co.ais.trans2.model.tax;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;

/**
 * ����Ń}�X�^��������
 */
public class ConsumptionTaxSearchCondition extends TransferBase implements Cloneable, FilterableCondition,
	OPLoginCondition {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** �R�[�h�O����v */
	protected String codeLike = null;

	/** ����like */
	protected String namesLike = null;

	/** ��������like */
	protected String namekLike = null;

	/** �J�n�R�[�h */
	protected String codeFrom = null;

	/** �I���R�[�h */
	protected String codeTo = null;

	/** �L������ */
	protected Date validTerm = null;

	/** ����ېł��܂߂邩 */
	protected boolean hasSales;

	/** �d���ېł��܂߂邩 */
	protected boolean hasPurcharse;

	/** �d������ł̂ݎ擾(�C���{�C�X�����p) */
	protected boolean purcharseOnliy = false;

	/** �ŏI�X�V���� */
	protected Date lastUpdateDate = null;

	/** ���݌��� */
	protected int nowCount = 0;

	/** ��K�i���������s���Ǝ҃t���O */
	protected boolean noInvRegFlg = false;

	/** �C���{�C�X���x�t���O */
	protected boolean invoiceFlg = false;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ConsumptionTaxSearchCondition clone() {
		try {
			return (ConsumptionTaxSearchCondition) super.clone();

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

	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * @param namesLike
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * ����ېł��܂߂邩
	 * 
	 * @param hasSales true:�܂߂�
	 */
	public void setHasSales(boolean hasSales) {
		this.hasSales = hasSales;
	}

	/**
	 * ����ېł��܂߂邩
	 * 
	 * @return true:�܂߂�
	 */
	public boolean isHasSales() {
		return hasSales;
	}

	/**
	 * �d���ېł��܂߂邩
	 * 
	 * @param hasPurcharse true:�܂߂�
	 */
	public void setHasPurcharse(boolean hasPurcharse) {
		this.hasPurcharse = hasPurcharse;
	}

	/**
	 * �d���ېł��܂߂邩
	 * 
	 * @return true:�܂߂�
	 */
	public boolean isHasPurcharse() {
		return hasPurcharse;
	}

	/**
	 * �d������ł̂ݎ擾(�C���{�C�X�����p)�̎擾
	 * 
	 * @return purcharseOnliy �d������ł̂ݎ擾(�C���{�C�X�����p)
	 */ 
	public boolean isPurcharseOnliy() { 
	     return purcharseOnliy;
	}
	/**
	 * �d������ł̂ݎ擾(�C���{�C�X�����p)�̐ݒ�
	 * 
	 * @param purcharseOnliy �d������ł̂ݎ擾(�C���{�C�X�����p)
	 */
	public void setPurcharseOnliy(boolean purcharseOnliy) {
	     this.purcharseOnliy = purcharseOnliy;
	}


	/**
	 * @see jp.co.ais.trans2.model.FilterableCondition#getCodeList()
	 */
	public List<String> getCodeList() {
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
	 * ��K�i���������s���Ǝ҃t���O�̎擾
	 * 
	 * @return noInvRegFlg ��K�i���������s���Ǝ҃t���O
	 */
	public boolean isNoInvRegFlg() {
		return noInvRegFlg;
	}

	/**
	 * ��K�i���������s���Ǝ҃t���O�̐ݒ�
	 * 
	 * @param noInvRegFlg ��K�i���������s���Ǝ҃t���O
	 */
	public void setNoInvRegFlg(boolean noInvRegFlg) {
		this.noInvRegFlg = noInvRegFlg;
	}

	/**
	 * �C���{�C�X���x�t���O�̎擾
	 * 
	 * @return invoiceFlg �C���{�C�X���x�t���O
	 */
	public boolean isInvoiceFlg() {
		return invoiceFlg;
	}

	/**
	 * �C���{�C�X���x�t���O�̐ݒ�
	 * 
	 * @param invoiceFlg �C���{�C�X���x�t���O
	 */
	public void setInvoiceFlg(boolean invoiceFlg) {
		this.invoiceFlg = invoiceFlg;
	}

}
