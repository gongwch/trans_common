package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.config.*;

/**
 * �x�������@�t�B�[���h�̃R���g���[��
 * 
 * @author roh
 */
public class TPaymentMethodFieldCtrl extends TAppletClientBase {

	/** Field */
	protected TPaymentMethodField field;

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "MP0040PaymentMethodMasterServlet";

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/**
	 * �R���X�g���N�^
	 * 
	 * @param itemField
	 */
	public TPaymentMethodFieldCtrl(TPaymentMethodField itemField) {
		this.field = itemField;
	}

	/**
	 * ���[�X�g�t�H�[�J�X�̑Ή�
	 * 
	 * @return boolean
	 */
	public boolean setPaymentNotice() {
		try {
			String code = field.getValue();

			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.clearOldText();
				field.setBean(null);
				return true;
			}

			// �x�������@���̂̏K��
			AP_HOH_MST apHohMst = getFieldDataList(code);

			// ���ʖ����̏ꍇ
			if (Util.isNullOrEmpty(apHohMst)) {
				showMessage(field, "W00081", "C00864");
				field.setNoticeValue("");
				field.clearOldText();
				field.requestTextFocus();
				// �x�������R�[�h���Z�b�g
				field.setNaiCode("");
				field.setBean(null);
				return false;
			}

			// �L�����Ԃ̃t���O
			if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
				Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
				Date strDate = apHohMst.getSTR_DATE();
				Date endDate = apHohMst.getEND_DATE();
				if (termDate.after(endDate) || strDate.after(termDate)) {
					if (!showConfirmMessage(field, "Q00025", "C00233")) {
						return false;
					}
				}
			}

			field.setNoticeValue(Util.avoidNull(apHohMst.getHOH_HOH_NAME()));
			// �x�������R�[�h���Z�b�g
			field.setNaiCode(Util.avoidNull(apHohMst.getHOH_NAI_CODE()));
			field.setBean(apHohMst);
			return true;

		} catch (Exception ex) {
			errorHandler(field, ex);
			return false;
		}
	}

	/**
	 * �t�B�[���h�f�[�^���X�g�擾
	 * 
	 * @param code �x�������@�R�[�h
	 * @return List �ڍ׃��X�g
	 * @throws TException
	 */
	protected AP_HOH_MST getFieldDataList(String code) throws TException {

		try {
			// ���M����p�����[�^��ݒ�
			// �敪flag
			addSendValues("flag", "refPayment");

			ApHohMstParameter param = new ApHohMstParameter();

			// ��ЃR�[�h
			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				param.setKaiCode(field.getCompanyCode());
			} else {
				param.setKaiCode(getLoginUserCompanyCode());
			}
			// �x�����@�R�[�h
			param.setHohCode(code);
			// �x���Ώۋ敪
			param.setHohSihKbn(Util.avoidNull(field.getSerchSihKbn()));
			// �x�����@�����R�[�h NOT����
			param.setNotHohNaiCode(Util.avoidNull(field.getSerchNotNaiCode()));
			// �x�����@�����R�[�h
			if (!Util.isNullOrEmpty(field.getSerchNaiCode())) {
				param.setHohNaiCode(new String[] { field.getSerchNaiCode() });
			}
			// �x�����@���X�g
			if (field.getHohCodes() != null && !field.getHohCodes().isEmpty()) {
				param.setHohCodes(field.getHohCodes());
			}

			addSendObject(param);

			if (!request(TARGET_SERVLET)) {
				// ����ɏ�������܂���ł���
				throw new TRequestException();
			}

			return (AP_HOH_MST) getResultDto(AP_HOH_MST.class);

		} catch (IOException ex) {
			throw new TException(ex, "E00009");
		}
	}

	/**
	 * �_�C�A���O�\��
	 */
	public void showSearchDialog() {
		try {
			Map<String, String> param = new HashMap<String, String>();

			// top�̃Z�b�g
			param.put("top", "C02350");
			param.put("code", "C00864"); // �R�[�h�̃Z�b�g
			param.put("nameS", "C02657"); // ����
			param.put("nameK", "C00866"); // ��������

			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.PAYMENT_MST, param);

			searchdialog.setStartCode(""); // �J�n�R�[�h
			searchdialog.setEndCode(""); // �I���R�[�h
			searchdialog.setServerName("refPayment");

			Map<String, String> otherParam = new HashMap<String, String>();

			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				otherParam.put("companyCode", field.getCompanyCode());
			} else {
				otherParam.put("companyCode", getLoginUserCompanyCode());
			}

			// �x���Ώۋ敪
			otherParam.put("sihKbn", Util.avoidNull(field.getSerchSihKbn()));
			// �x�����@�����R�[�h NOT����
			otherParam.put("notNaiCode", Util.avoidNull(field.getSerchNotNaiCode()));
			// �x�����@�����R�[�h
			otherParam.put("naiCode", Util.avoidNull(field.getSerchNaiCode()));

			otherParam.put("termBasisDate", field.getTermBasisDate());

			// �x�����@���X�g
			if (field.getHohCodes() != null && !field.getHohCodes().isEmpty()) {
				searchdialog.setCodes(field.getHohCodes());
			}

			searchdialog.setParamMap(otherParam);

			// ���ސݒ�A��������
			if (showDefaultCode && !Util.isNullOrEmpty(field.getValue())) {
				searchdialog.setCode(String.valueOf(field.getValue()));
				searchdialog.searchData(false);
			}

			searchdialog.show();

			showDialog(searchdialog);

		} catch (Exception e) {
			errorHandler(field, e, "E00009");
		}
	}

	/**
	 * �����_�C�A���O�\��<BR>
	 * 
	 * @param dialog �_�C�A���O
	 * @return boolean �F true �m��̏ꍇ false ����̏ꍇ
	 */
	protected boolean showDialog(REFDialogMasterCtrl dialog) {

		// �m��̏ꍇ
		if (dialog.isSettle()) {

			String[] info = dialog.getCurrencyInfo();
			field.setValue(info[0]); // �R�[�h���Z�b�g����
			field.setNoticeValue(info[1]); // ���̂��Z�b�g����
			field.getField().requestFocus();

			return true;
		}

		field.getField().requestFocus();

		return false;
	}
}
