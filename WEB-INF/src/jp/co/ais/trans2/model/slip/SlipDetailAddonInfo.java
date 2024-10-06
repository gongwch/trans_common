package jp.co.ais.trans2.model.slip;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * �`�[�ǉ����
 */
public class SlipDetailAddonInfo extends TransferBase {

	/**
	 * ������o��
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(map);
		return sb.toString();
	}

	/** �}�b�v(�L�[, �l) */
	protected Map<String, Object> map;

	/** true:�T�Z�Afalse:���� */
	public static final String KEY_ESTIMATE_FLAG = "estimateFlag";

	/** �ݕ��R�[�h(CARGO) */
	public static final String KEY_CARGO_CODE = "cargoCode";

	/** �ݕ�����(CARGO) */
	public static final String KEY_CARGO_NAME = "cargoName";

	/** �׎�R�[�h(CARGO) */
	public static final String KEY_CHTR_CODE = "chtrCode";

	/** ����FROM�J�n���� */
	public static final String KEY_PERIOD_FROM = "periodFrom";

	/** ����TO�I������ */
	public static final String KEY_PERIOD_TO = "periodTo";

	/** ���ԓ���(FROM-TO)�����_�ȉ�5��(9,5) */
	public static final String KEY_DURATION = "duration";

	/** VC�_��UID */
	public static final String KEY_VCC_UID = "vccUID";

	/** VC�_��ԍ� */
	public static final String KEY_VCC_CTRT_NO = "vccContractNo";

	/** TC�_��UID */
	public static final String KEY_TCC_UID = "tccUID";

	/** TC�_��ԍ� */
	public static final String KEY_TCC_CTRT_NO = "tccContractNo";

	/** �q�C�^�C�v */
	public static final String KEY_VOY_TYPE = "voyType";

	/** �^������ */
	public static final String KEY_FRT_QTY = "frtQty";

	/** ���ʒP�ʃR�[�h */
	public static final String KEY_FRT_QTY_UNIT_CODE = "frtQtyUnitCode";

	/** ����R�[�h */
	public static final String KEY_BUNKER_TYPE_CODE = "bunkerTypeCode";

	/** �R������ */
	public static final String KEY_BUNKER_QTY = "bunkerQty";

	/** �R���P�� */
	public static final String KEY_BUNKER_PRICE = "bunkerPrice";

	/** �R������� */
	public static final String KEY_BUNKER_SUPPLY_DATE = "bunkerSupplyDate";

	/** �R������� */
	public static final String KEY_BUNKER_SUPPLIER_CODE = "bunkerSupplierCode";

	/** VOY_UID */
	public static final String KEY_VOY_UID = "voyUID";

	/**
	 * �R���X�g���N�^�[
	 */
	public SlipDetailAddonInfo() {
		map = new HashMap<String, Object>();
	}

	/**
	 * �ݒ肷��<�L�[, �l>
	 * 
	 * @param key
	 * @param value
	 */
	public void setValue(String key, Object value) {
		map.put(key, value);
	}

	/**
	 * �ݒ�l���擾����
	 * 
	 * @param key
	 * @return �l
	 */
	public Object getValue(String key) {
		return map.get(key);
	}

	/**
	 * �ݒ�l���擾����(�����񃂁[�h)
	 * 
	 * @param key
	 * @return �l
	 */
	public String getString(String key) {
		Object obj = map.get(key);
		return Util.avoidNull(obj);
	}

	/**
	 * �ݒ�l���擾����(�����񃂁[�h)
	 * 
	 * @param key
	 * @return �l
	 */
	public BigDecimal getNumber(String key) {
		String str = getString(key);

		if (!Util.isNumber(str)) {
			return null;
		}

		return DecimalUtil.toBigDecimalNULL(str);
	}

	/**
	 * true:�T�Z�Afalse:���т̎擾
	 * 
	 * @return estimateFlag true:�T�Z�Afalse:����
	 */
	public Boolean getEstimateFlag() {
		return (Boolean) getValue(KEY_ESTIMATE_FLAG);
	}

	/**
	 * true:�T�Z�Afalse:���т̐ݒ�
	 * 
	 * @param estimateFlag true:�T�Z�Afalse:����
	 */
	public void setEstimateFlag(Boolean estimateFlag) {
		setValue(KEY_ESTIMATE_FLAG, estimateFlag);
	}

	/**
	 * �ݕ��R�[�h(CARGO)�̎擾
	 * 
	 * @return cargoCode �ݕ��R�[�h(CARGO)
	 */
	public String getCargoCode() {
		return (String) getValue(KEY_CARGO_CODE);
	}

	/**
	 * �ݕ��R�[�h(CARGO)�̐ݒ�
	 * 
	 * @param cargoCode �ݕ��R�[�h(CARGO)
	 */
	public void setCargoCode(String cargoCode) {
		setValue(KEY_CARGO_CODE, cargoCode);
	}

	/**
	 * �ݕ�����(CARGO)�̎擾
	 * 
	 * @return cargoName �ݕ�����(CARGO)
	 */
	public String getCargoName() {
		return (String) getValue(KEY_CARGO_NAME);
	}

	/**
	 * �ݕ�����(CARGO)�̐ݒ�
	 * 
	 * @param cargoName �ݕ�����(CARGO)
	 */
	public void setCargoName(String cargoName) {
		setValue(KEY_CARGO_NAME, cargoName);
	}

	/**
	 * �׎�R�[�h(VCC CHTR)�̎擾
	 * 
	 * @return chtrCode �׎�R�[�h(VCC CHTR)
	 */
	public String getChtrCode() {
		return (String) getValue(KEY_CHTR_CODE);
	}

	/**
	 * �׎�R�[�h(VCC CHTR)�̐ݒ�
	 * 
	 * @param chtrCode �׎�R�[�h(VCC CHTR)
	 */
	public void setChtrCode(String chtrCode) {
		setValue(KEY_CHTR_CODE, chtrCode);
	}

	/**
	 * ����FROM�J�n�����̎擾
	 * 
	 * @return periodFrom ����FROM�J�n����
	 */
	public Date getPeriodFrom() {
		return (Date) getValue(KEY_PERIOD_FROM);
	}

	/**
	 * ����FROM�J�n�����̐ݒ�
	 * 
	 * @param periodFrom ����FROM�J�n����
	 */
	public void setPeriodFrom(Date periodFrom) {
		setValue(KEY_PERIOD_FROM, periodFrom);
	}

	/**
	 * ����TO�I�������̎擾
	 * 
	 * @return periodTo ����TO�I������
	 */
	public Date getPeriodTo() {
		return (Date) getValue(KEY_PERIOD_TO);
	}

	/**
	 * ����TO�I�������̐ݒ�
	 * 
	 * @param periodTo ����TO�I������
	 */
	public void setPeriodTo(Date periodTo) {
		setValue(KEY_PERIOD_TO, periodTo);
	}

	/**
	 * ���ԓ���(FROM-TO)�����_�ȉ�5��(9,5)�̎擾
	 * 
	 * @return duration ���ԓ���(FROM-TO)�����_�ȉ�5��(9,5)
	 */
	public BigDecimal getDuration() {
		return (BigDecimal) getValue(KEY_DURATION);
	}

	/**
	 * ���ԓ���(FROM-TO)�����_�ȉ�5��(9,5)�̐ݒ�
	 * 
	 * @param duration ���ԓ���(FROM-TO)�����_�ȉ�5��(9,5)
	 */
	public void setDuration(BigDecimal duration) {
		setValue(KEY_DURATION, duration);
	}

	/**
	 * VC�_��UID�̎擾
	 * 
	 * @return vccUID VC�_��UID
	 */
	public String getVccUID() {
		return (String) getValue(KEY_VCC_UID);
	}

	/**
	 * VC�_��UID�̐ݒ�
	 * 
	 * @param vccUID VC�_��UID
	 */
	public void setVccUID(String vccUID) {
		setValue(KEY_VCC_UID, vccUID);
	}

	/**
	 * VC�_��ԍ��̎擾
	 * 
	 * @return vccContractNo VC�_��ԍ�
	 */
	public String getVccContractNo() {
		return (String) getValue(KEY_VCC_CTRT_NO);
	}

	/**
	 * VC�_��ԍ��̐ݒ�
	 * 
	 * @param vccContractNo VC�_��ԍ�
	 */
	public void setVccContractNo(String vccContractNo) {
		setValue(KEY_VCC_CTRT_NO, vccContractNo);
	}

	/**
	 * TC�_��UID�̎擾
	 * 
	 * @return tccContractNo TC�_��ԍ�
	 */
	public String getTccUID() {
		return (String) getValue(KEY_TCC_UID);
	}

	/**
	 * TC�_��UID�̐ݒ�
	 * 
	 * @param tccUID TC�_��UID
	 */
	public void setTccUID(String tccUID) {
		setValue(KEY_TCC_UID, tccUID);
	}

	/**
	 * TC�_��ԍ��̎擾
	 * 
	 * @return tccContractNo TC�_��ԍ�
	 */
	public String getTccContractNo() {
		return (String) getValue(KEY_TCC_CTRT_NO);
	}

	/**
	 * TC�_��ԍ��̐ݒ�
	 * 
	 * @param tccContractNo TC�_��ԍ�
	 */
	public void setTccContractNo(String tccContractNo) {
		setValue(KEY_TCC_CTRT_NO, tccContractNo);
	}

	/**
	 * �q�C�^�C�v�̎擾
	 * 
	 * @return voyType �q�C�^�C�v
	 */
	public String getVoyType() {
		return (String) getValue(KEY_VOY_TYPE);
	}

	/**
	 * �q�C�^�C�v�̐ݒ�
	 * 
	 * @param voyType �q�C�^�C�v
	 */
	public void setVoyType(String voyType) {
		setValue(KEY_VOY_TYPE, voyType);
	}

	/**
	 * �^�����ʂ̎擾
	 * 
	 * @return frtQty �^������
	 */
	public BigDecimal getFrtQty() {
		return (BigDecimal) getValue(KEY_FRT_QTY);
	}

	/**
	 * �^�����ʂ̐ݒ�
	 * 
	 * @param frtQty �^������
	 */
	public void setFrtQty(BigDecimal frtQty) {
		setValue(KEY_FRT_QTY, frtQty);
	}

	/**
	 * ���ʒP�ʃR�[�h�̎擾
	 * 
	 * @return frtQtyUnitCode ���ʒP�ʃR�[�h
	 */
	public String getFrtQtyUnitCode() {
		return (String) getValue(KEY_FRT_QTY_UNIT_CODE);
	}

	/**
	 * ���ʒP�ʃR�[�h�̐ݒ�
	 * 
	 * @param frtQtyUnitCode ���ʒP�ʃR�[�h
	 */
	public void setFrtQtyUnitCode(String frtQtyUnitCode) {
		setValue(KEY_FRT_QTY_UNIT_CODE, frtQtyUnitCode);
	}

	/**
	 * ����R�[�h�̎擾
	 * 
	 * @return bunkerTypeCode ����R�[�h
	 */
	public String getBunkerTypeCode() {
		return (String) getValue(KEY_BUNKER_TYPE_CODE);
	}

	/**
	 * ����R�[�h�̐ݒ�
	 * 
	 * @param bunkerTypeCode ����R�[�h
	 */
	public void setBunkerTypeCode(String bunkerTypeCode) {
		setValue(KEY_BUNKER_TYPE_CODE, bunkerTypeCode);
	}

	/**
	 * �R�����ʂ̎擾
	 * 
	 * @return bunkerQty �R������
	 */
	public BigDecimal getBunkerQty() {
		return (BigDecimal) getValue(KEY_BUNKER_QTY);
	}

	/**
	 * �R�����ʂ̐ݒ�
	 * 
	 * @param bunkerQty �R������
	 */
	public void setBunkerQty(BigDecimal bunkerQty) {
		setValue(KEY_BUNKER_QTY, bunkerQty);
	}

	/**
	 * �R���P���̎擾
	 * 
	 * @return bunkerPrice �R���P��
	 */
	public BigDecimal getBunkerPrice() {
		return (BigDecimal) getValue(KEY_BUNKER_PRICE);
	}

	/**
	 * �R���P���̐ݒ�
	 * 
	 * @param bunkerPrice �R���P��
	 */
	public void setBunkerPrice(BigDecimal bunkerPrice) {
		setValue(KEY_BUNKER_PRICE, bunkerPrice);
	}

	/**
	 * �R��������̎擾
	 * 
	 * @return bunkerSupplyDate �R�������
	 */
	public Date getBunkerSupplyDate() {
		return (Date) getValue(KEY_BUNKER_SUPPLY_DATE);
	}

	/**
	 * �R��������̐ݒ�
	 * 
	 * @param bunkerSupplyDate �R�������
	 */
	public void setBunkerSupplyDate(Date bunkerSupplyDate) {
		setValue(KEY_BUNKER_SUPPLY_DATE, bunkerSupplyDate);
	}

	/**
	 * �R�������̎擾
	 * 
	 * @return bunkerSupplier �R�������
	 */
	public String getBunkerSupplierCode() {
		return (String) getValue(KEY_BUNKER_SUPPLIER_CODE);
	}

	/**
	 * �R�������̐ݒ�
	 * 
	 * @param bunkerSupplierCode �R�������
	 */
	public void setBunkerSupplierCode(String bunkerSupplierCode) {
		setValue(KEY_BUNKER_SUPPLIER_CODE, bunkerSupplierCode);
	}

	/**
	 * �q�CUID�̎擾
	 * 
	 * @return voyUID �q�CUID
	 */
	public String getVoyUID() {
		return (String) getValue(KEY_VOY_UID);
	}

	/**
	 * �q�CUID�̐ݒ�
	 * 
	 * @param voyUID �q�CUID
	 */
	public void setVoyUID(String voyUID) {
		setValue(KEY_VOY_UID, voyUID);
	}

}
