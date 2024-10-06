package jp.co.ais.trans2.common.model.ui.slip;

/**
 * �d�󖾍׃R���g���[��
 * 
 * @author AIS
 */
public class TFormSlipPatternDetailPanelCtrl extends TFormSlipDetailPanelCtrl {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param panel �p�l��
	 */
	protected TFormSlipPatternDetailPanelCtrl(TFormSlipPatternDetailPanel panel) {
		super(panel);
	}

	/**
	 * ��ʕ\����������
	 */
	@Override
	protected void initView() {
		super.initView();

		// ��/���c��/BS�����\��
		getView().pnlHeader.setVisible(false);
	}

	@Override
	public TFormSlipPatternDetailPanel getView() {
		return (TFormSlipPatternDetailPanel) panel;
	}

	/**
	 * @return T-Form ����Panel
	 */
	@Override
	protected TFormDCInputPanel createTFormDCInputPanel() {
		return new TFormDCPatternInputPanel();
	}
}
