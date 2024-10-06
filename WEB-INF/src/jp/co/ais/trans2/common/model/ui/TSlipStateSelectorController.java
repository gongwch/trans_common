package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;

/**
 * �`�[�̃X�e�[�^�X�I���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TSlipStateSelectorController extends TController {

	/** �t�B�[���h */
	protected TSlipStateSelector field;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TSlipStateSelectorController(TSlipStateSelector field) {
		this.field = field;
		init();
	}

	/**
	 * ������
	 */
	public void init() {

		boolean isDefaultOn = ClientConfig.isFlagOn("trans.report.slip.state.default.on");

		field.chkEntry.setSelected(isDefaultOn);
		field.chkApprove.setSelected(isDefaultOn);

		// ���F���g��Ȃ��ꍇ�A���F�̃`�F�b�N�{�b�N�X�͔�\��
		if (!getCompany().getAccountConfig().isUseApprove() && !getCompany().getAccountConfig().isUseFieldApprove()) {
			field.chkApprove.setSelected(false);
			field.chkApprove.setVisible(false);
		}

	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * �o�^���܂ނ�
	 * 
	 * @return �o�^���܂ނ�
	 */
	public boolean isEntry() {
		return field.chkEntry.isSelected();
	}

	/**
	 * �o�^���܂ނ��ݒ�
	 * 
	 * @param isEntry �o�^���܂ނ�
	 */
	public void setEntry(boolean isEntry) {
		field.chkEntry.setSelected(isEntry);
	}

	/**
	 * ���F���܂ނ�
	 * 
	 * @return ���F���܂ނ�
	 */
	public boolean isApprove() {
		return field.chkApprove.isSelected();
	}

	/**
	 * ���F���܂ނ��ݒ�
	 * 
	 * @param isApprove ���F���܂ނ�
	 */
	public void setApprove(boolean isApprove) {
		field.chkApprove.setSelected(isApprove);
	}

}
