package jp.co.ais.trans.common.bizui.ctrl;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;

/**
 * �x���敪�R���{�{�b�N�X�̃R���g���[��
 * 
 * @author ais
 */
public class TPaymentDivisionComboBoxCtrl extends TAppletClientBase {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field Field
	 */
	public TPaymentDivisionComboBoxCtrl(TPaymentDivisionComboBox field) {
		TComboBox combo = field.getComboBox();
		combo.addTextValueItem(TPaymentDivisionComboBox.ONTIME_VALUE, getWord("C00380")); // �莞
		combo.addTextValueItem(TPaymentDivisionComboBox.TEMPORARY_VALUE, getWord("C00555")); // �Վ�
	}
}
