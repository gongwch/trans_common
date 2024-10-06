package jp.co.ais.trans2.op.model.ui;

import java.util.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.payment.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * �������������R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class OPPaymentSettingReferenceController extends TPaymentSettingReferenceController {

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public OPPaymentSettingReferenceController(TReference field) {
		super(field);
	}

	/**
	 * @return �_�C�A���O��ʂł̌����������擾
	 */
	@Override
	public PaymentSettingSearchCondition getCondition() {
		return condition;
	}

	/**
	 * @param condition_
	 * @return list
	 */
	@Override
	protected List<PaymentSetting> getList(PaymentSettingSearchCondition condition_) {
		return super.getList(condition_);
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����
	 */
	public void refleshEntity() {
		PaymentSetting bean = getInputtedEntity();
		setEntity(bean);
	}

}
