package jp.co.ais.trans2.common.model.ui.slip;

/**
 * �`�[�p�^�[�����׃R���|�[�l���g
 */
public class TSlipPatternDetailPanel extends TSlipDetailPanel {

	/**
	 * �R���g���[���[�̎擾
	 * 
	 * @return �R���g���[���[
	 */
	@Override
	protected TSlipDetailPanelCtrl createController() {
		return new TSlipPatternDetailPanelCtrl(this);
	}

}
