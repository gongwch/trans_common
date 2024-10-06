package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans2.common.config.*;

/**
 * �x�������t�B�[���h�̃R���g���[��
 * 
 * @author roh
 */
public class TPaymentConditionFieldCtrl extends TAppletClientBase {

	/** �t�B�[���h */
	protected TPaymentConditionField field;

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "MG0155CustomerConditionMasterServlet";

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/**
	 * �R���X�g���N�^
	 * 
	 * @param itemField �x���������t�B�[���h
	 */
	public TPaymentConditionFieldCtrl(TPaymentConditionField itemField) {
		this.field = itemField;
	}

	/**
	 * ���[�X�g�t�H�[�J�X�̑Ή�
	 * 
	 * @return boolean
	 */
	public boolean setPaymentConditionNotice() {

		try {
			String code = field.getValue();

			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.clearOldText();
				return true;
			}

			// �x�����������̂̏K��
			List list = getFieldDataList(code);

			// ���ʖ����̏ꍇ
			if (list.isEmpty()) {
				showMessage(field, "W00081", "C00672");
				field.setNoticeValue("");
				field.clearOldText();
				field.requestTextFocus();
				return false;
			}
			// �L�����Ԃ̃t���O
			if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
				Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
				Date strDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(2));
				Date endDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(3));
				if (termDate.after(endDate) || strDate.after(termDate)) {
					if (!showConfirmMessage(field, "Q00025", "C00238")) {
						return false;
					}
				}

			}

			StringBuilder bfieldName = new StringBuilder();
			bfieldName.append((String) ((List) list.get(0)).get(1));

			field.setNoticeValue(bfieldName.toString());

			return true;
		} catch (Exception e) {
			errorHandler(field, e);
			return false;
		}

	}

	/**
	 * �t�B�[���h�f�[�^���X�g�擾
	 * 
	 * @param code �x���������R�[�h
	 * @return �ڍ׃��X�g
	 * @throws TException
	 */
	protected List getFieldDataList(String code) throws TException {

		try {
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "refPaymentCondition"); // �敪flag
			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				addSendValues("kaiCode", field.getCompanyCode());
			} else {
				addSendValues("kaiCode", getLoginUserCompanyCode());
			}
			addSendValues("tjkCode", code);
			addSendValues("triCode", Util.avoidNull(field.getCustomerCode()));
			if (!request(TARGET_SERVLET)) {
				// �N���C�A���g ��M��̓G���[�B
				errorHandler(field, "S00002");
			}

			return getResultList();

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}

	/**
	 * �_�C�A���O�\��
	 */
	public void showSearchDialog() {
		try {

			Map<String, String> initParam = getSearchDialogParam();
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.PAYMENTCON_MST,
				initParam);

			searchdialog.setStartCode(""); // �J�n�R�[�h
			searchdialog.setEndCode(""); // �I���R�[�h
			searchdialog.setServerName("refPaymentCondition");
			Map<String, String> otherParam = new HashMap<String, String>();

			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				otherParam.put("companyCode", field.getCompanyCode());
			} else {
				otherParam.put("companyCode", getLoginUserCompanyCode());
			}

			otherParam.put("termBasisDate", field.getTermBasisDate());
			otherParam.put("triCode", field.getCustomerCode());
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
	 * ����dialog�����l�̃Z�b�g
	 * 
	 * @return param �^�C�g��
	 */
	protected Map<String, String> getSearchDialogParam() {
		Map<String, String> param = new HashMap<String, String>();

		param.put("top", "C02325"); // top�̃Z�b�g
		param.put("code", "C00672"); // �R�[�h�̃Z�b�g
		param.put("nameS", "C00779"); // ����
		param.put("nameK", "C00866"); // ��������
		param.put("kbn", "2");// �f�[�^�敪
		return param;
	}

	/**
	 * �����_�C�A���O�\��<BR>
	 * 
	 * @param dialog �_�C�A���O
	 * @return boolean : true �m��̏ꍇ false ����̏ꍇ
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
