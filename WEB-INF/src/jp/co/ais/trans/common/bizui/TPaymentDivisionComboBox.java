package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 支払区分コンボボックス
 * 
 * @author ais
 */
public class TPaymentDivisionComboBox extends TLabelComboBox {

	/** UID */
	private static final long serialVersionUID = 152234308781802217L;

	/** 臨時定数 */
	public static final String TEMPORARY_VALUE = "00";

	/** 定時定数 */
	public static final String ONTIME_VALUE = "10";

	/**
	 * 支払区分コンボボックス
	 */
	public TPaymentDivisionComboBox() {
		super();

		initComponents();

		new TPaymentDivisionComboBoxCtrl(this);
	}

	/** 画面構築 */
	private void initComponents() {
		this.setComboSize(90);
		this.setLabelSize(63);
		this.setLangMessageID("C00682");
	}

	/**
	 * 臨時が選択されているかどうかを判別する
	 * 
	 * @return true:臨時、false:定時
	 */
	public boolean isTemporarySelected() {
		return TEMPORARY_VALUE.equals(this.getComboBox().getSelectedItemValue());
	}
}
