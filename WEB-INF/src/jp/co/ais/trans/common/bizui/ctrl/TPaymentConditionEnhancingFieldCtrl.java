package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.util.*;

/**
 * �x�������t�B�[���h�g���R���g���[��
 */
public class TPaymentConditionEnhancingFieldCtrl extends TPaymentConditionFieldCtrl {

	/** �����T�[�u���b�g */
	private static final String TARGET_SERVLET = "MG0155CustomerConditionMasterServlet";

	/** field */
	private TPaymentConditionEnhancingField field;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TPaymentConditionEnhancingFieldCtrl(TPaymentConditionEnhancingField field) {
		super(field);
		this.field = field;
	}

	/**
	 * �x��������̃��[�X�g�t�H�[�J�X����
	 */
	@Override
	public boolean setPaymentConditionNotice() {

		try {
			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			if (Util.isNullOrEmpty(field.getValue())) {
				field.setNoticeValue("");
				field.clearOldText();
				return true;
			}

			Map<String, String> map = getPaymentConditionInfo();

			// ���ʖ����̏ꍇ
			if (Util.isNullOrEmpty(map.get("tjkcode"))) {
				showMessage(field, "W00081", "C00672");
				field.setNoticeValue("");
				field.clearOldText();
				field.requestTextFocus();
				setParameter(map);
				return false;
			}
			// �L�����Ԃ̃t���O
			if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
				Date termDate = null;
				Date strDate = null;
				Date endDate = null;

				termDate = DateUtil.toYMDDate(field.getTermBasisDate());
				strDate = DateUtil.toYMDDate(Util.avoidNull(map.get("strDate")));
				endDate = DateUtil.toYMDDate(Util.avoidNull(map.get("endDate")));

				if (termDate.after(endDate) || strDate.after(termDate)) {

					if (!showConfirmMessage(field, "Q00025", "C00238")) {
						// �L�������t���O��false
						field.setIsTermBasisDate(false);
						field.requestTextFocus();
						return false;
					}
				}
			}

			// �L�������t���O��true
			field.setIsTermBasisDate(true);
			field.requestTextFocus();

			setParameter(map);

			field.setNoticeValue(Util.avoidNull(map.get("bnkname")));

			return true;

		} catch (Exception e) {
			errorHandler(field, e);
			return false;
		}
	}

	/**
	 * �K�v����ݒ�
	 * 
	 * @param map ���
	 */
	private void setParameter(Map<String, String> map) {
		PaymentInformationParameter param = field.getParameter();
		// ������
		param.clear();
		// �x�����@�R�[�h
		param.setSihaHouCode(Util.avoidNull(map.get("sihahouCode")));
		// �x�����@����
		param.setSihaHouName(Util.avoidNull(map.get("sihahouName")));
		// �U�o��s�R�[�h
		param.setHuriCode(Util.avoidNull(map.get("huricode")));
		// �U�o��s����
		param.setHuriName(Util.avoidNull(map.get("huriname")));
		// �x���敪
		param.setSihaKbn(Util.avoidNull(map.get("sihakbn")));
		// �x����
		param.setSihaDate(Util.avoidNull(map.get("sihaDate")));
		// �x�������R�[�h
		param.setSihaNaiCode(Util.avoidNull(map.get("naicode")));
		// �f�[�^�L�肩�̔���
		if ("0".equals(Util.avoidNull(map.get("flag")))) {
			// �f�[�^�Ȃ�
			param.setFlag(false);
		} else {
			// �f�[�^�L��
			param.setFlag(true);
		}
	}

	/**
	 * �f�t�H���g�̎x�����������擾
	 */
	public void setDefaultPaymentCondition() {
		// �x�������}�X�^�f�[�^������
		Map<String, String> map = getDefaultPaymentCondition();

		field.setValue(Util.avoidNull(map.get("tjkCode")));
		field.setNoticeValue(Util.avoidNull(map.get("bnkname")));
		setPaymentConditionNotice();
	}

	/**
	 * �x�����s�A�����ԍ�������
	 * 
	 * @return �x�����s�A�����ԍ�
	 */
	private Map<String, String> getPaymentConditionInfo() {
		try {
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "paymentConditionInfo");
			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				addSendValues("kaiCode", field.getCompanyCode());
			} else {
				addSendValues("kaiCode", getLoginUserCompanyCode());
			}
			// �x����R�[�h
			addSendValues("triCode", Util.avoidNull(field.getCustomerCode()));
			// �x��������R�[�h
			addSendValues("tjkCode", Util.avoidNull(field.getValue()));
			// �`�[���t
			addSendValues("slipDate", DateUtil.toYMDString(field.getSlipDate()));

			// �T�[�u���b�g�̐ڑ���
			if (!request(TARGET_SERVLET)) {
				errorHandler(field);
			}

			return getResult();
		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			errorHandler(field, e, "E00009");
			return null;
		}
	}

	/**
	 * �x�����s�A�����ԍ�������
	 * 
	 * @return �x�����s�A�����ԍ�
	 */
	private Map<String, String> getDefaultPaymentCondition() {
		try {
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "defaultPaymentCondition");
			// ��ЃR�[�h
			addSendValues("kaiCode", getLoginUserCompanyCode());
			// �x����R�[�h
			addSendValues("triCode", Util.avoidNull(field.getCustomerCode()));
			// �x��������R�[�h
			addSendValues("curCode", Util.avoidNull(field.getCurrencyCode()));

			// �T�[�u���b�g�̐ڑ���
			if (!request(TARGET_SERVLET)) {
				errorHandler(field);
			}

			return getResult();

		} catch (IOException e) {
			// ����ɏ�������܂���ł���
			errorHandler(field, e, "E00009");
			return null;
		}
	}
}
