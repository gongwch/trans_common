package jp.co.ais.trans2.common.model.ui.slip;

/**
 * �d�󖾍׃p�l��
 * 
 * @author AIS
 */
public class TFormSlipPatternDetailPanel extends TFormSlipDetailPanel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param parent �e
	 */
	public TFormSlipPatternDetailPanel(TSlipPatternPanel parent) {
		super(parent);
	}

	/**
	 * �R���g���[���[�̎擾
	 * 
	 * @return �R���g���[���[
	 */
	@Override
	public TFormSlipPatternDetailPanelCtrl createController() {
		return new TFormSlipPatternDetailPanelCtrl(this);
	}

	/**
	 * �R���g���[���擾
	 * 
	 * @return �R���g���[��
	 */
	@Override
	public TFormSlipPatternDetailPanelCtrl getController() {
		return (TFormSlipPatternDetailPanelCtrl) controller;
	}

}
