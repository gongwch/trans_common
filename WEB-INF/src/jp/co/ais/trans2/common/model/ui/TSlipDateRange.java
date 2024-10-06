package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;

/**
 * �`�[���t�͈̓t�B�[���h
 * 
 * @author AIS
 */
public class TSlipDateRange extends TDateRange {

	/** serialVersionUID */
	private static final long serialVersionUID = -5671715054170282481L;

	/** �R���g���[�� */
	protected TSlipDateRangeController controller;

	/**
	 * �R���X�g���N�^.
	 */
	public TSlipDateRange() {

		this(TYPE_YMD);

	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param calType ���t�\���`��
	 */
	public TSlipDateRange(int calType) {

		super(calType);

		// �R���g���[������
		controller = new TSlipDateRangeController(this);

	}

	/**
	 * �R���|�[�l���g������������<BR>
	 * 
	 * @param calType ���t�\���`��
	 */
	@Override
	protected void initComponents(int calType) {
		dateFrom = new TSlipDate(calType);
		dateTo = new TSlipDate(calType);
	}

	/**
	 * �N�x�ׂ����`�F�b�N����
	 * 
	 * @return true:�N�x���ׂ��ł���
	 */
	public boolean isOverFiscalYear() {
		return controller.isOverFiscalYear();
	}

	/**
	 * �`�[���t�i�J�n�j���擾����
	 * 
	 * @return �`�[���t�i�J�n�j
	 */
	@Override
	public TSlipDate getDateFrom() {
		return (TSlipDate) dateFrom;
	}

	/**
	 * �`�[���t�i�I���j���擾����
	 * 
	 * @return �`�[���t�i�I���j
	 */
	@Override
	public TSlipDate getDateTo() {
		return (TSlipDate) dateTo;
	}

}
