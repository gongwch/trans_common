package jp.co.ais.trans2.common.gui;

import java.math.*;

import jp.co.ais.trans.common.gui.*;

/**
 * ���l�͈̓t�B�[���h
 * 
 * @author AIS
 */
public class TNumericRange extends TTextRange {

	/**
	 * �R���|�[�l���g������������<BR>
	 */
	@Override
	protected void initComponents() {
		fieldFrom = new TLabelNumericField();
		fieldTo = new TLabelNumericField();
	}

	/**
	 * �R���|�[�l���g�i�J�n�j���擾����
	 * 
	 * @return �R���|�[�l���g�i�J�n�j
	 */
	@Override
	public TLabelNumericField getFieldFrom() {
		return (TLabelNumericField) fieldFrom;
	}

	/**
	 * �R���|�[�l���g�i�I���j���擾����
	 * 
	 * @return �R���|�[�l���g�i�I���j
	 */
	@Override
	public TLabelNumericField getFieldTo() {
		return (TLabelNumericField) fieldTo;
	}

	/**
	 * �R���|�[�l���g�i�J�n�j�̒l���擾����
	 * 
	 * @return �R���|�[�l���g�i�J�n�j�̒l
	 */
	public BigDecimal getBigDecimalFrom() {
		return getFieldFrom().getBigDecimal();
	}

	/**
	 * �R���|�[�l���g�i�I���j�̒l���擾����
	 * 
	 * @return �R���|�[�l���g�i�I���j�̒l
	 */
	public BigDecimal getBigDecimalTo() {
		return getFieldTo().getBigDecimal();
	}

	/**
	 * �l��ݒ肷��
	 * 
	 * @param valueFrom �R���|�[�l���g�i�J�n�j �ݒ�l
	 * @param valueTo �R���|�[�l���g�i�I���j �ݒ�l
	 */
	public void setValue(Number valueFrom, Number valueTo) {
		getFieldFrom().setNumber(valueFrom);
		getFieldTo().setNumber(valueTo);
	}

	/**
	 * �uFROM <= TO�v���r����.
	 * 
	 * @return �uFROM <= TO�v�܂��́A�ǂ��炩���u�����N�Ȃ��true.
	 */
	@Override
	public boolean isSmallerFrom() {
		return getBigDecimalFrom().compareTo(getBigDecimalTo()) != 1;
	}
}
