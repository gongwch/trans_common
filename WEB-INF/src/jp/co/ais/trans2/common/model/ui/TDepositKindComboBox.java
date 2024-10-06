package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * 預金種別選択コンボボックス
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
	 * 選択された預金種別を返す
	 * 
	 * @return DepositKind
	 */
	public DepositKind getSelectedDepositKind() {
		return ctrl.getSelectedDepositKind();
	}

	/**
	 * 預金種別をセットする
	 * 
	 * @param depositKind
	 */
	public void setSelectedDepositKind(DepositKind depositKind) {
		ctrl.setSelectedDepositKind(depositKind);
	}

}
