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
 * �����t�B�[���h�̃R���g���[��
 * 
 * @author roh
 */
public class TCustomerFieldCtrl extends TAppletClientBase {

	/** �����t�B�[���h */
	protected TCustomerField field;

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "MG0150CustomerMasterServlet";

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/**
	 * �R���X�g���N�^
	 * 
	 * @param itemField
	 */
	public TCustomerFieldCtrl(TCustomerField itemField) {
		this.field = itemField;
	}

	/**
	 * �����ւ̒l�Z�b�g
	 * 
	 * @return boolean
	 */
	public boolean setCustomerNotice() {

		try {
			String code = field.getValue();

			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.clearOldText();
				return true;
			}

			// ����於�̂̏K��
			TRI_MST bean = findData(code);

			// �`�F�b�N���[�h�I���̏ꍇ
			if (field.isCheckMode()) {

				// ���ʖ����̏ꍇ
				if (bean == null) {
					// �ؓ���Ǝ����̕\���ؑ�
					if (field.isLoanCustomer()) {
						showMessage(field, "W00081", "C02689");
					} else {
						showMessage(field, "W00081", "C00786");
					}
					return returnFalse();
				}

				// �L�����ԃ`�F�b�N
				if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
					Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());

					if (termDate.after(bean.getEND_DATE()) || bean.getSTR_DATE().after(termDate)) {
						if (!showConfirmMessage(field, "Q00025", "C00408")) {
							return returnFalse();
						} else {
							field.requestTextFocus();
						}
					}
				}

				// �d����t���O
				if (field.isSiire() && !field.isTokui()) {
					if (0 == bean.getSIIRE_KBN()) {
						if (!showConfirmMessage(field.getParentFrame(), "Q00026", "C01089")) {
							return returnFalse();
						}
					}
				}

				// ���Ӑ�t���O
				if (field.isTokui() && !field.isSiire()) {
					if (0 == bean.getTOKUI_KBN()) {
						if (!showConfirmMessage(field.getParentFrame(), "Q00026", "C01261")) {
							return returnFalse();
						}
					}
				}

				// �d����t���OOR���Ӑ�t���O
				if (field.isTokui() && field.isSiire()) {
					if (0 == bean.getTOKUI_KBN() && 0 == bean.getSIIRE_KBN()) {
						if (!showConfirmMessage(field.getParentFrame(), "Q00026", getWord("C01089") + ","
							+ getWord("C01261"))) {
							return returnFalse();
						}
					}
				}
			}
			// ���̃Z�b�g
			field.setNoticeValue(bean != null ? bean.getTRI_NAME_S() : "");

			return true;

		} catch (TRequestException e) {
			errorHandler(field);
			return returnFalse();

		} catch (Exception e) {
			errorHandler(field, e, "E00009");
			return returnFalse();
		}
	}

	/**
	 * ���̂��N���A����false��Ԃ�
	 * 
	 * @return false
	 */
	protected boolean returnFalse() {
		field.setNoticeValue("");
		field.clearOldText();
		field.requestTextFocus();
		return false;
	}

	/**
	 * �t�B�[���h�f�[�^�擾
	 * 
	 * @param code �����R�[�h
	 * @return �ڍ�
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected TRI_MST findData(String code) throws TRequestException, IOException {

		addSendValues("flag", "CustomerInfo"); // �敪flag

		if (Util.isNullOrEmpty(field.getCompanyCode())) {
			field.setCompanyCode(getLoginUserCompanyCode());
		}
		addSendValues("companyCode", field.getCompanyCode());
		addSendValues("customerCode", code);

		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}

		return (TRI_MST) getResultObject();
	}

	/**
	 * �_�C�A���O�\��
	 */
	public void showSearchDialog() {
		try {

			Map<String, String> initParam = new HashMap<String, String>();

			if (field.isLoanCustomer()) {
				initParam = getLoanDialogParam();
			} else {
				initParam = getSearchDialogParam();
			}

			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.CUSTOMER_MST,
				initParam);

			searchdialog.setStartCode(field.getBeginCode()); // �J�n�R�[�h
			searchdialog.setEndCode(field.getEndCode()); // �I���R�[�h
			searchdialog.setServerName("refCustomer");
			Map<String, String> otherParam = new HashMap<String, String>();

			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				otherParam.put("companyCode", field.getCompanyCode());
			} else {
				otherParam.put("companyCode", getLoginUserCompanyCode());
			}

			// �d����A����Setter��boolean�l��ݒ�B
			otherParam.put("siire", BooleanUtil.toString(field.isSiire()));
			otherParam.put("tokui", BooleanUtil.toString(field.isTokui()));
			otherParam.put("termBasisDate", field.getTermBasisDate());
			otherParam.put("beginCode", field.getBeginCode());// �J�n�R�[�h
			otherParam.put("endCode", field.getEndCode()); // �I���R�[�h
			searchdialog.setParamMap(otherParam);

			// ���ސݒ�A��������
			if (showDefaultCode && !Util.isNullOrEmpty(field.getValue())) {
				searchdialog.setCode(String.valueOf(field.getValue()));
				searchdialog.searchData(false);
			}

			searchdialog.show();

			// �����_�C�A���O��\��

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

		param.put("top", "C01676"); // top�̃Z�b�g
		param.put("code", "C00786"); // �R�[�h�̃Z�b�g
		param.put("nameS", "C00787"); // ����
		param.put("nameK", "C00836"); // ��������

		return param;
	}

	/**
	 * �ؓ��挟��dialog�����l�̃Z�b�g
	 * 
	 * @return param �^�C�g��
	 */
	protected Map<String, String> getLoanDialogParam() {

		Map<String, String> param = new HashMap<String, String>();

		param.put("top", (getWord("C02690") + getWord("C02406"))); // top�̃Z�b�g //�}�X�^�ꗗ
		param.put("code", "C02689"); // �R�[�h�̃Z�b�g
		param.put("nameS", getWord("C02690") + getWord("C00548")); // ����
		param.put("nameK", getWord("C02690") + getWord("C00160")); // ��������

		return param;
	}

	/**
	 * �����_�C�A���O�\��<BR>
	 * 
	 * @param dialog �_�C�A���O
	 * @return true �m��̏ꍇ false ����̏ꍇ
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

	/**
	 * Notice�t�B�[���h�F���X�g�t�H�[�J�X����
	 * 
	 * @return boolean
	 */
	public boolean noticeLostFocus() {
		// ��������
		return true;
	}
}
