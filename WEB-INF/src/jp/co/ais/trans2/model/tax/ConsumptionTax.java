package jp.co.ais.trans2.model.tax;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * ����ŏ��
 */
public class ConsumptionTax extends TransferBase implements TReferable, AutoCompletable, FilterableEntity {

	@Override
	public String toString() {
		return getCode() + " " + getName();
	}

	/**
	 * @return �\����
	 */
	public String getDisplayText() {
		StringBuilder sb = new StringBuilder();
		sb.append(getCode());
		sb.append(" / ");
		sb.append(Util.avoidNull(getName()));
		sb.append(" / ");
		sb.append(Util.avoidNull(getNames()));

		return sb.toString();
	}

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

	/** ����Ń��[�g */
	protected BigDecimal rate;

	/** �L�����ԊJ�n */
	protected Date dateFrom = null;

	/** �L�����ԏI�� */
	protected Date dateTo = null;

	/** �ŋ敪 */
	protected TaxType taxType = null;

	/** ����Ōv�Z���� �ǉ� */
	protected boolean zeiTaxConsumption = false;

	/** �o�͏��� �ǉ� */
	protected String odr = null;

	/** ��K�i���������s���Ǝ҃t���O */
	protected boolean NO_INV_REG_FLG = false;

	/** �o�ߑ[�u�T���\��(1%�`99%) */
	protected BigDecimal KEKA_SOTI_RATE = null;

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
	 * ����Ń��[�g
	 * 
	 * @return ����Ń��[�g
	 */
	public BigDecimal getRate() {
		return rate;
	}

	/**
	 * ����Ń��[�g
	 * 
	 * @param rate ����Ń��[�g
	 */
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	/**
	 * ����Ōv�Z����
	 * 
	 * @return true:����Ōv�Z�� �ǉ�
	 */
	public boolean isTaxConsumption() {
		return zeiTaxConsumption;
	}

	/**
	 * ����Ōv�Z����
	 * 
	 * @param zieTaxConsumption true:����Ōv�Z�� �ǉ�
	 */
	public void setTaxConsumption(boolean zieTaxConsumption) {
		this.zeiTaxConsumption = zieTaxConsumption;
	}

	/**
	 * �ŋ敪
	 * 
	 * @return taxType �ŋ敪
	 */
	public TaxType getTaxType() {
		return taxType;
	}

	/**
	 * �ŋ敪
	 * 
	 * @param taxType �ŋ敪
	 */
	public void setTaxType(TaxType taxType) {
		this.taxType = taxType;
	}

	/**
	 * �o�͏������擾 �ǉ�
	 * 
	 * @return odr
	 */
	public String getOdr() {
		return odr;
	}

	/**
	 * �o�͏������Z�b�g �ǉ�
	 * 
	 * @param odr
	 */
	public void setOdr(String odr) {
		this.odr = odr;
	}

	/**
	 * ��K�i���������s���Ǝ҃t���O�̎擾
	 * 
	 * @return NO_INV_REG_FLG ��K�i���������s���Ǝ҃t���O
	 */
	public boolean isNO_INV_REG_FLG() {
		return NO_INV_REG_FLG;
	}

	/**
	 * ��K�i���������s���Ǝ҃t���O�̐ݒ�
	 * 
	 * @param NO_INV_REG_FLG ��K�i���������s���Ǝ҃t���O
	 */
	public void setNO_INV_REG_FLG(boolean NO_INV_REG_FLG) {
		this.NO_INV_REG_FLG = NO_INV_REG_FLG;
	}

	/**
	 * �o�ߑ[�u�T���\��(1%�`99%)�̎擾
	 * 
	 * @return KEKA_SOTI_RATE �o�ߑ[�u�T���\��(1%�`99%)
	 */
	public BigDecimal getKEKA_SOTI_RATE() {
		return KEKA_SOTI_RATE;
	}

	/**
	 * �o�ߑ[�u�T���\��(1%�`99%)�̐ݒ�
	 * 
	 * @param KEKA_SOTI_RATE �o�ߑ[�u�T���\��(1%�`99%)
	 */
	public void setKEKA_SOTI_RATE(BigDecimal KEKA_SOTI_RATE) {
		this.KEKA_SOTI_RATE = KEKA_SOTI_RATE;
	}

}
