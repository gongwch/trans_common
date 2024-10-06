package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * �a����ʑI���R���{�{�b�N�X
 * 
 * @author AIS
 */
public class TDepositKindComboBox extends TLabelComboBox {

	/**  */
	public TDepositKindComboBoxController ctrl;

	/**
	 * 
	 */
	public TDepositKindComboBox() {
		super();
		init();
		createController();

	}

	/**
	 * 
	 */
	protected void createController() {
		this.ctrl = new TDepositKindComboBoxController(this);
	}

	/**
	 * 
	 */
	protected void init() {
		setComboSize(75);
		setLabelSize(130);
		setLangMessageID("C01326");
	}

	/**
	 * �I�����ꂽ�a����ʂ�Ԃ�
	 * 
	 * @return DepositKind
	 */
	public DepositKind getSelectedDepositKind() {
		return ctrl.getSelectedDepositKind();
	}

	/**
	 * �a����ʂ��Z�b�g����
	 * 
	 * @param depositKind
	 */
	public void setSelectedDepositKind(DepositKind depositKind) {
		ctrl.setSelectedDepositKind(depositKind);
	}

}
