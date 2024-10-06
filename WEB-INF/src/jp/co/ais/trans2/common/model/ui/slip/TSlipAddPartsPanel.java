package jp.co.ais.trans2.common.model.ui.slip;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;

/**
 * ���z���̓w�b�_�t���`�[�p�l��
 */
public abstract class TSlipAddPartsPanel extends TSlipPanel {

	/** �v�㕔�� */
	public TDepartmentReference ctrlDepartment;

	/** �Ȗ� */
	public TItemGroup ctrlItem;

	/** �ʉ� */
	public TCurrencyReference ctrlCurrency;

	/** ���[�g */
	public TLabelNumericField ctrlRate;

	/** ���͋��z */
	public TLabelNumericField ctrlInputAmount;

	/** �M�݋��z */
	public TLabelNumericField ctrlKeyAmount;

	@Override
	public void initComponents() {
		super.initComponents();

		// �w�b�_
		ctrlDepartment = new TDepartmentReference();
		ctrlItem = new TItemGroup();

		ctrlCurrency = new TCurrencyReference();
		ctrlRate = new TLabelNumericField();
		ctrlInputAmount = new TLabelNumericField();
		ctrlKeyAmount = new TLabelNumericField();

		// �����ݒ� --

		// �v�㕔��
		ctrlDepartment.btn.setLangMessageID("C00863");

		// �x���ʉ�
		ctrlCurrency.setEditable(false);
		ctrlCurrency.name.setVisible(false);
		ctrlCurrency.resize();

		// ���[�g
		ctrlRate.setEditable(false);
		ctrlRate.setLangMessageID("C00556");
		ctrlRate.setMaxLength(13, 4);
		ctrlRate.setPositiveOnly(true);

		// ���͋��z
		ctrlInputAmount.setLangMessageID("C00574");
		ctrlInputAmount.setMaxLength(13, 4);
		ctrlInputAmount.setChangeRedOfMinus(true);

		// �M�݋��z
		ctrlKeyAmount.setEditable(false);
		ctrlKeyAmount.setLangMessageID("C00576");
		ctrlKeyAmount.setMaxLength(13, 4);
		ctrlKeyAmount.setChangeRedOfMinus(true);
	}

	/**
	 * �w�b�_�[�ʉ݂��f�t�H���g�ʉ݂Ƃ��Ďg����
	 * 
	 * @return true:�w�b�_�[�ʉ݂��f�t�H���g�Ŗ��׍s�������\������
	 */
	public boolean isUseHeaderDefaultCurreny() {
		return false;
	}
}
