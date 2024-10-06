package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * �x���敪�R���{�{�b�N�X
 * 
 * @author ais
 */
public class TPaymentDivisionComboBox extends TLabelComboBox {

	/** UID */
	private static final long serialVersionUID = 152234308781802217L;

	/** �Վ��萔 */
	public static final String TEMPORARY_VALUE = "00";

	/** �莞�萔 */
	public static final String ONTIME_VALUE = "10";

	/**
	 * �x���敪�R���{�{�b�N�X
	 */
	public TPaymentDivisionComboBox() {
		super();

		initComponents();

		new TPaymentDivisionComboBoxCtrl(this);
	}

	/** ��ʍ\�z */
	private void initComponents() {
		this.setComboSize(90);
		this.setLabelSize(63);
		this.setLangMessageID("C00682");
	}

	/**
	 * �Վ����I������Ă��邩�ǂ����𔻕ʂ���
	 * 
	 * @return true:�Վ��Afalse:�莞
	 */
	public boolean isTemporarySelected() {
		return TEMPORARY_VALUE.equals(this.getComboBox().getSelectedItemValue());
	}
}
