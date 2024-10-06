package jp.co.ais.trans.common.bizui.ctrl;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 支払区分コンボボックスのコントロール
 * 
 * @author ais
 */
public class TPaymentDivisionComboBoxCtrl extends TAppletClientBase {

	/**
	 * コンストラクタ
	 * 
	 * @param field Field
	 */
	public TPaymentDivisionComboBoxCtrl(TPaymentDivisionComboBox field) {
		TComboBox combo = field.getComboBox();
		combo.addTextValueItem(TPaymentDivisionComboBox.ONTIME_VALUE, getWord("C00380")); // 定時
		combo.addTextValueItem(TPaymentDivisionComboBox.TEMPORARY_VALUE, getWord("C00555")); // 臨時
	}
}
