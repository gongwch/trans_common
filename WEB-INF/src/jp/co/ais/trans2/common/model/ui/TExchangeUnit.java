package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.TPanel;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * ���Z���j�b�g�R���|�[�l���g<br>
 * �ʉ݁A���[�g�A�O�݋��z�A�M�݋��z�ō\�������B
 * @author AIS
 *
 */
public class TExchangeUnit extends TPanel {

	/** �ʉ݃t�B�[���h */
	public TCurrencyReference ctrlCurrencyReference; 

	/** ���[�g�t�B�[���h */
	public TRate ctrlRate;

	/** �O�݋��z�t�B�[���h */
	public TForeignAmount ctrlForeignAmount;

	/** ����z�t�B�[���h */
	public TKeyAmount ctrlKeyAmount;

	/** �R���g���[�� */
	public TExchangeUnitController controller;

	/**
	 * 
	 *
	 */
	public TExchangeUnit() {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

		// �R���g���[������
		controller = new TExchangeUnitController(this);

	}

	/**
	 * �R���|�[�l���g������������<BR>
	 *
	 */
	protected void initComponents() {
		ctrlCurrencyReference = new TCurrencyReference();
		ctrlRate = new TRate();
		ctrlForeignAmount = new TForeignAmount();
		ctrlKeyAmount = new TKeyAmount();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 *
	 */
	protected void allocateComponents() {
		// for override �܂��͂�����g���ꍇ�͎������ĉ������B
	}

	/**
     * Tab���̐ݒ�
     * @param tabControlNo �^�u��
     */
    public void setTabControlNo(int tabControlNo) {
    	ctrlCurrencyReference.setTabControlNo(tabControlNo);
    }

    /**
     * �ʉ݂��Z�b�g����B<br>
     * �O�݃t�B�[���h�͓��Y�ʉ݂̏����_�����Ńt�H�[�}�b�g�����
     * @param currency �ʉ�
     */
    public void setCurrency(Currency currency) {
    	controller.setCurrency(currency);
    }

}
