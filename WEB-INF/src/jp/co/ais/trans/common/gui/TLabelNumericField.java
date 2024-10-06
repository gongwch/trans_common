package jp.co.ais.trans.common.gui;

import java.math.*;

/**
 * TPanel�ɁA�^�u���A���b�Z�[�WID�C���^�[�t�F�C�X��ǉ���������item. ���l���͗p. <br>
 * �qitem.
 * <ol>
 * <li>label TLabel
 * <li>field TTextField
 * </ol>
 */
public class TLabelNumericField extends TLabelField {

	/**
	 * Creates a new instance of TLabelNumericField
	 */
	public TLabelNumericField() {
		super();

		super.setImeMode(false);
	}

	/**
	 * �e�L�X�g�t�B�[���h����
	 * 
	 * @return �e�L�X�g�t�B�[���h
	 */
	@Override
	protected TTextField createTextField() {
		return new TNumericField();
	}

	/**
	 * �ő包���ݒ�.<br>
	 * setMaxLength(17,4)�� #,###,###,###.##0.0000
	 * 
	 * @param maxLength �ő包��(�������A���������킹��)
	 * @param decimalPoint �����_����
	 */
	public void setMaxLength(int maxLength, int decimalPoint) {
		((TNumericField) field).setMaxLength(maxLength, decimalPoint);
	}

	/**
	 * ���l�^�̃t�H�[�}�b�g
	 * 
	 * @param format �t�H�[�}�b�g
	 */
	public void setNumericFormat(String format) {
		((TNumericField) field).setNumericFormat(format);
	}

	/**
	 * �t�H�[�}�b�g(���l�^)
	 * 
	 * @return ���l�^�̃t�H�[�}�b�g
	 */
	public String getNumericFormat() {
		return ((TNumericField) field).getNumericFormat();
	}

	/**
	 * ��ɔ�IME���[�h.
	 * 
	 * @deprecated ��ɔ�IME���[�h.
	 */
	@Override
	public void setImeMode(boolean flag) {
		super.setImeMode(false);
	}

	/**
	 * �����̂݃��[�h�ݒ�
	 */
	public void setPositiveOnly() {
		setPositiveOnly(true);
	}

	/**
	 * �����̂݃��[�h�ݒ�
	 * 
	 * @param isPositive true�̏ꍇ�A�����̂�
	 */
	public void setPositiveOnly(boolean isPositive) {
		((TNumericField) field).setPositiveOnly(isPositive);
	}

	/**
	 * �p�l�����TTextField field�C���X�^���X��Ԃ�.
	 * 
	 * @return field
	 */
	@Override
	public TNumericField getField() {
		return (TNumericField) super.getField();
	}

	/**
	 * �ύX���ꂽ���`�F�b�N����
	 * 
	 * @return �ύX����Ă���ꍇ�Atrue
	 */
	@Override
	public boolean isValueChanged() {

		return this.field.isValueChanged();
	}

	/**
	 * �ύX���ꂽ���`�F�b�N����(null�܂�)
	 * 
	 * @return �ύX����Ă���ꍇ�Atrue
	 */
	public boolean isValueChanged2() {

		return this.field.isValueChanged2();
	}

	/**
	 * int�^�ŕ\�����l���擾����
	 * 
	 * @return ���l
	 */
	public int getIntValue() {
		return getField().getInt();
	}

	/**
	 * double�^�ŕ\�����l���擾����<br>
	 * double�ł͂Ȃ�BigDecimal��.
	 * 
	 * @deprecated double�g����
	 * @return ���l
	 */
	public double getDoubleValue() {
		return getField().getDouble();
	}

	/**
	 * BigDecimal�^�ŕ\�����l���擾����
	 * 
	 * @return ���l
	 */
	public BigDecimal getBigDecimalValue() {
		return getField().getBigDecimal();
	}

	/**
	 * BigDecimal�^�ŕ\�����l���擾����
	 * 
	 * @return ���l
	 */
	public BigDecimal getBigDecimal() {
		return getField().getBigDecimal();
	}

	/**
	 * �\�����������̂܂܎擾
	 * 
	 * @return �\������
	 */
	public String getInputText() {
		return getField().getInputText();
	}

	/**
	 * Double�l�ݒ�. �t�H�[�}�b�g�ϊ�������<br>
	 * double�ł͂Ȃ�BigDecimal��.
	 * 
	 * @deprecated double�g����
	 * @param value ���l
	 */
	public void setDoubleValue(Double value) {
		getField().setDouble(value);
	}

	/**
	 * Number�l�ݒ�. �t�H�[�}�b�g�ϊ�������
	 * 
	 * @param value ���l
	 */
	public void setNumberValue(Number value) {
		getField().setNumber(value);
	}

	/**
	 * Number�l�ݒ�. �t�H�[�}�b�g�ϊ�������
	 * 
	 * @param value ���l
	 */
	public void setNumber(Number value) {
		getField().setNumber(value);
	}

	/**
	 * �t�H�[�}�b�g���ݒ肳��Ă��邩�ǂ���
	 * 
	 * @return true : �t�H�}�b�g�L�� false : �t�H�[�}�b�g�Ȃ�
	 */
	public boolean isFormatterExist() {
		return ((TNumericField) field).isFormatterExist();
	}

	/**
	 * �����_������0�t�H�[�}�b�g(ex:#.0000)�ŕύX����.
	 * 
	 * @param digit �����_����
	 * @see TNumericField#setDecimalPoint(int)
	 */
	public void setDecimalPoint(int digit) {
		((TNumericField) field).setDecimalPoint(digit);
	}

	/**
	 * �����_����
	 * 
	 * @return �����_����
	 * @see TNumericField#getDecimalPoint()
	 */
	public int getDecimalPoint() {
		return ((TNumericField) field).getDecimalPoint();
	}

	/**
	 * �����_������0�t�H�[�}�b�g(ex:#.0000)�ŕύX����.<br>
	 * TRANS1.0�Ή�
	 * 
	 * @param digit �����_����
	 */
	@Deprecated
	public void setFractionDegits(int digit) {
		setDecimalPoint(digit);
	}

	/**
	 * �����_����<br>
	 * TRANS1.0�Ή�
	 * 
	 * @return �����_����
	 */
	@Deprecated
	public int getFractionDegits() {
		return getDecimalPoint();
	}

	/**
	 * �}�C�i�X�l����͂��ꂽ�ہA�ԕ����ɕύX���邩�ǂ���.
	 * 
	 * @param isChangeRedOfMinus true:�ύX����
	 * @see TNumericField#setChangeRedOfMinus(boolean)
	 */
	public void setChangeRedOfMinus(boolean isChangeRedOfMinus) {
		((TNumericField) field).setChangeRedOfMinus(isChangeRedOfMinus);
	}

	/**
	 * �}�C�i�X�l����͂��ꂽ�ہA�ԕ����ɕύX���邩�ǂ���.
	 * 
	 * @return true:�ύX����
	 * @see TNumericField#isChangeRedOfMinus()
	 */
	public boolean isChangeRedOfMinus() {
		return ((TNumericField) field).isChangeRedOfMinus();
	}

	/**
	 * ���͒l���N���A����
	 */
	@Override
	public void clear() {
		this.field.clear();
	}

	/**
	 * ���͒l���[�����ǂ���.
	 * 
	 * @return true:�[��
	 */
	public boolean isZero() {
		return ((TNumericField) field).isZero();
	}

	/**
	 * �ő�l�̎擾
	 * 
	 * @return maxValue �ő�l
	 */
	public BigDecimal getMaxValue() {
		return getField().getMaxValue();
	}

	/**
	 * �ő�l�̐ݒ�
	 * 
	 * @param maxValue �ő�l
	 */
	public void setMaxValue(BigDecimal maxValue) {
		getField().setMaxValue(maxValue);
	}

	/**
	 * �ŏ��l�̎擾
	 * 
	 * @return minValue �ŏ��l
	 */
	public BigDecimal getMinValue() {
		return getField().getMinValue();
	}

	/**
	 * �ŏ��l�̐ݒ�
	 * 
	 * @param minValue �ŏ��l
	 */
	public void setMinValue(BigDecimal minValue) {
		getField().setMinValue(minValue);
	}
}
