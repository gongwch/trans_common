package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.TPanelBusiness;
import jp.co.ais.trans2.common.client.TController;

/**
 * �`�[���t�͈̓t�B�[���h�̃R���g���[��
 * 
 * @author AIS
 */
public class TSlipDateRangeController extends TController {

	/** �`�[���t�͈̓t�B�[���h */
	protected TSlipDateRange slipDateRange;

	/**
	 * @param slipDateRange �`�[���t�͈̓t�B�[���h
	 */
	public TSlipDateRangeController(TSlipDateRange slipDateRange) {
		this.slipDateRange = slipDateRange;
		init();
	}

	/**
	 * ������
	 */
	protected void init() {
		//
	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * �N�x�ׂ����`�F�b�N����
	 * 
	 * @return true:�N�x���ׂ��ł���
	 */
	public boolean isOverFiscalYear() {

		// �N�x�ׂ��`�F�b�N
		int yearFrom = getCompany().getFiscalPeriod().getFiscalYear(slipDateRange.dateFrom.getValue());
		int yearTo = getCompany().getFiscalPeriod().getFiscalYear(slipDateRange.dateTo.getValue());
		if (yearFrom != yearTo) {
			return true;
		}

		return false;

	}

}
