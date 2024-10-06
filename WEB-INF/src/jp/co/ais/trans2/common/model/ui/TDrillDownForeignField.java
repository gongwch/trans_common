package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;

/**
 * �h�����_�E���̊O�݃t�B�[���h
 */
public class TDrillDownForeignField {

	/** �O�ݎؕ��v */
	public TLabelNumericField txDebitInSum;

	/** �O�ݑݕ��v */
	public TNumericField txCreditInSum;

	/** �O�� */
	public TTextField txCur;

	/**
	 * �R���X�g���N�^�[
	 */
	public TDrillDownForeignField() {
		txDebitInSum = new TLabelNumericField();
		txCreditInSum = new TNumericField();
		txCur = new TTextField();
	}

	/**
	 * �\��/��\���ݒ�
	 * 
	 * @param flag
	 */
	public void setVisible(boolean flag) {
		txDebitInSum.setVisible(flag);
		txCreditInSum.setVisible(flag);
		txCur.setVisible(flag);
	}

	/**
	 * @return �\��/��\��
	 */
	public boolean isVisible() {
		return txDebitInSum.isVisible();
	}
}
