package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.remark.*;

/**
 * �E�v�����R���|�[�l���g
 * 
 * @author AIS
 */
public class TRemarkReference extends TReference {

	/** �R���g���[�� */
	protected TRemarkReferenceController controller;

	/**
	 * �R���X�g���N�^
	 */
	public TRemarkReference() {
		controller = new TRemarkReferenceController(this);

		this.name.setEditable(true);
		this.name.setMaxLength(80);
		TGuiUtil.setComponentWidth(name, 500);
		resize();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param isSlipRemark true:�`�[�E�v false:�s�E�v
	 */
	public TRemarkReference(boolean isSlipRemark) {
		this();

		if (isSlipRemark) {
			getSearchCondition().setSlipRowRemark(false);
			btn.setLangMessageID("C00569");

		} else {
			getSearchCondition().setSlipRemark(false);
			btn.setLangMessageID("C00119");
		}
	}

	@Override
	public TReferenceController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public RemarkSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public Remark getEntity() {
		return controller.getEntity();
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param remark �E�v
	 */
	public void setEntity(Remark remark) {
		controller.setEntity(remark);
	}

}
