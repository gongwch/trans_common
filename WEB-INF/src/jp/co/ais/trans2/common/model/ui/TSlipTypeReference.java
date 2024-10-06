package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.slip.*;


/**
 * �`�[��ʌ����R���|�[�l���g
 */
public class TSlipTypeReference extends TReference {

	/** serialVersionUID */
	private static final long serialVersionUID = -1369641370951036568L;

	/** �R���g���[�� */
	protected TSlipTypeReferenceController controller;

	/**
	 *
	 */
	public TSlipTypeReference() {
		controller = new TSlipTypeReferenceController(this);
	}

	@Override
	protected TReferenceController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public SlipTypeSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public SlipType getEntity() {
		return controller.getEntity();
	}

	/**
	 * �G���e�B�e�B���Z�b�g
	 * 
	 * @param sliptype �G���e�B�e�B
	 */
	public void setEntity(SlipType sliptype) {
		controller.setEntity(sliptype);
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

}
