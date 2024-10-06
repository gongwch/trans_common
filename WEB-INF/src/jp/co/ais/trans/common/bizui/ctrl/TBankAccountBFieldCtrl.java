package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.config.*;

/**
 * ��s�����t�B�[���h�̃R���g���[��(��s���ɋ�s�}�X�^.��s��+��s�}�X�^.�x�X����\������)
 * 
 * @author roh
 */
public class TBankAccountBFieldCtrl extends TAppletClientBase {

	/** �����T�[�u���b�g */
	protected static final String SEARCH_SERVLET = "MP0030BankAccountMasterServlet";

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** �t�B�[���h */
	protected TBankAccountBField field;

	/** ���b�Z�[�W�p�p�l���擾 */
	public TPanelBusiness panel;

	/**
	 * �R���X�g���N�^ field
	 * 
	 * @param field
	 */
	public TBankAccountBFieldCtrl(TBankAccountBField field) {
		this.field = field;
	}

	/**
	 * ��s�����ւ̒l�Z�b�g
	 * 
	 * @return boolean
	 */
	public boolean setBankAccountNotice() {

		try {
			String code = field.getValue();

			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.clearOldText();
				field.setBean(null);
				return true;
			}

			// �����R�[�h�ŏڍ׃��X�g�K��
			AP_CBK_MST cbkMst = getFieldData(code);

			// ���ʖ����̏ꍇ
			if (Util.isNullOrEmpty(cbkMst)) {
				showMessage(field, "W00081", "C01879");
				field.setNoticeValue("");
				field.clearOldText();
				field.requestTextFocus();
				field.setBean(null);
				return false;
			}

			// �L�����ԃ`�F�b�N
			if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
				Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
				if (termDate.after(cbkMst.getEND_DATE()) || cbkMst.getSTR_DATE().after(termDate)) {
					if (!showConfirmMessage(field, "Q00025", "C00857")) {
						field.clearOldText();
						field.requestTextFocus();
						field.setBean(null);
						return false;
					}
				}
			}

			// �Ј��x���t���O�K�p
			if (field.isEmpKbn()) {
				if (String.valueOf(cbkMst.getCBK_EMP_FB_KBN()).equals("0")) {
					if (!showConfirmMessage(field, "Q00052", "C01117")) {
						return false;
					}
				}
			}

			// �ЊO�x���t���O�K�p
			if (field.isOutKbn()) {
				if (String.valueOf(cbkMst.getCBK_OUT_FB_KBN()).equals("0")) {
					if (!showConfirmMessage(field, "Q00052", "C01122")) {
						return false;
					}
				}
			}

			// ���̃Z�b�g
			field.setNoticeValue(cbkMst.getBNK_NAME_S() + " " + cbkMst.getBNK_STN_NAME_S());
			field.setBean(cbkMst);
			return true;
		} catch (Exception e) {
			errorHandler(field, e);
			return false;
		}

	}

	/**
	 * �t�B�[���h�f�[�^���X�g�擾
	 * 
	 * @param code �����R�[�h
	 * @return �ڍ׃��X�g
	 * @throws TException
	 */
	protected AP_CBK_MST getFieldData(String code) throws TException {

		try {
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "getCbkData"); // �敪flag

			ApCbkMstParameter param = new ApCbkMstParameter();

			// ��ЃR�[�h
			if (Util.isNullOrEmpty(field.getCompanyCode())) {
				param.setKaiCode(getLoginUserCompanyCode());
			} else {
				param.setKaiCode(field.getCompanyCode());
			}
			param.setCbkCode(code);

			if (field.getCbkCodes() != null && !field.getCbkCodes().isEmpty()) {
				param.setCbkCodes(field.getCbkCodes());
			}

			addSendObject(param);
			if (!request(SEARCH_SERVLET)) {
				// �N���C�A���g ��M��̓G���[�B
				errorHandler(field, "S00002");
			}

			return (AP_CBK_MST) getResultDto(AP_CBK_MST.class);

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}

	/**
	 * �a����ڂ̑�����Ή�
	 * 
	 * @param kbn �����敪
	 * @return �P��
	 */
	public String getWordByDepositType(String kbn) {
		if ("1".equals(kbn)) {
			return getWord("C00463"); // ���� C00463
		} else if ("2".equals(kbn)) {
			return getWord("C00397"); // ���� C00397
		} else if ("3".equals(kbn)) {
			return getWord("C00045"); // �O�� C00045
		} else if ("4".equals(kbn)) {
			return getWord("C00368"); // ���~ C00368
		} else {
			return "";
		}
	}

	/**
	 * ���̃t�B�[���h�ւ̒l�Z�b�g
	 */
	public void showSearchDialog() {
		try {
			Map<String, String> initParam = getSearchDialogParam();
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.BANK_ACCOUNT_B,
				initParam);

			searchdialog.setStartCode(""); // �J�n�R�[�h
			searchdialog.setEndCode(""); // �I���R�[�h
			searchdialog.setServerName("BankAccountB");
			Map<String, String> otherParam = new HashMap<String, String>();
			if (Util.isNullOrEmpty(field.getCompanyCode())) {
				field.setCompanyCode(super.getLoginUserCompanyCode());
			}
			otherParam.put("companyCode", field.getCompanyCode());

			// �Ј��A�O���̃t���O��ݒ�
			otherParam.put("empKbn", BooleanUtil.toString(field.isEmpKbn()));
			otherParam.put("outKbn", BooleanUtil.toString(field.isOutKbn()));
			otherParam.put("termBasisDate", field.getTermBasisDate());
			// ��s�������X�g
			if (field.getCbkCodes() != null && !field.getCbkCodes().isEmpty()) {
				searchdialog.setCbkCodes(field.getCbkCodes());
			}
			searchdialog.setParamMap(otherParam);

			// ���ސݒ�A��������
			if (showDefaultCode && !Util.isNullOrEmpty(field.getValue())) {
				searchdialog.setCode(String.valueOf(field.getValue()));
				searchdialog.searchData(false);
			}

			searchdialog.show();

			// �m��̏ꍇ
			if (searchdialog.isSettle()) {
				String[] info = searchdialog.getCurrencyInfo();
				field.setValue(info[0]); // �R�[�h���Z�b�g����
				field.setNoticeValue(info[1]); // ���̂��Z�b�g����
			}

			field.getField().requestFocus();

		} catch (Exception e) {
			errorHandler(field, e, "E00009");
		}
	}

	/**
	 * ����dialog�����l�̃Z�b�g
	 * 
	 * @return param �^�C�g����
	 */
	protected Map<String, String> getSearchDialogParam() {

		Map<String, String> param = new HashMap<String, String>();
		if (!Util.isNullOrEmpty(field.getRefTitle())) {
			param.put("top", field.getRefTitle()); // top�̃Z�b�g
		} else {
			param.put("top", "C02519"); // top�̃Z�b�g
		}
		param.put("code", "C01879"); // �R�[�h�̃Z�b�g
		param.put("nameS", "C10361"); // ���̂̃Z�b�g
		param.put("nameK", "C00794"); // �����ԍ��̃Z�b�g

		return param;
	}

}
