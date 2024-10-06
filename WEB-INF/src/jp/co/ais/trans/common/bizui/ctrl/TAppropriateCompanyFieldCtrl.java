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
import jp.co.ais.trans2.common.ui.*;

/**
 * �v���Ѓt�B�[���h�̃R���g���[��
 * 
 * @author zhangbo
 */
public class TAppropriateCompanyFieldCtrl extends TAppletClientBase {

	/** �v���Ѓt�B�[���h */
	protected TAppropriateCompanyField field;

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "TAppropriateCompanyFieldServlet";

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/**
	 * �R���X�g���N�^
	 * 
	 * @param itemField
	 */
	public TAppropriateCompanyFieldCtrl(TAppropriateCompanyField itemField) {
		this.field = itemField;
	}

	/**
	 * �v���Ђւ̒l�Z�b�g
	 * 
	 * @return boolean
	 */
	public boolean setCompanyInfo() {

		try {

			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			if (Util.isNullOrEmpty(field.getValue())) {
				field.setNoticeValue("");
				field.clearOldText();

				return true;
			}

			// �v���Ѓf�[�^
			List<AppropriateCompany> list = getFieldDataList();

			// ���ʖ����̏ꍇ
			if (list.isEmpty()) {
				if (!field.isFocusOut()) {
					showMessage(field, "I00051");
					field.setNoticeValue("");
					field.clearOldText();
					field.requestTextFocus();

				}
				field.setBln(false);
				return false;
			}

			// �L�����ԃ`�F�b�N
			if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
				Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
				Date strDate = DateUtil.toYMDDate(list.get(0).getSTR_DATE());
				Date endDate = DateUtil.toYMDDate(list.get(0).getEND_DATE());
				if (termDate.after(endDate) || strDate.after(termDate)) {
					if (!field.isFocusOut()) {
						if (!showConfirmMessage(field, "Q00046", "C01052")) {
							field.clearOldText();
							field.requestTextFocus();
							field.setBln(false);
							return false;
						}
					} else {
						field.setBln(false);
						return false;
					}
				}
			}

			field.setNoticeValue(list.get(0).getKAI_NAME_S());
			field.setBln(true);
			return true;
		} catch (Exception e) {
			errorHandler(field, e);
			return false;
		}

	}

	/**
	 * �t�B�[���h�f�[�^���X�g�擾
	 * 
	 * @return �ڍ׃��X�g
	 * @throws TException
	 */
	public List<AppropriateCompany> getFieldDataList() throws TException {

		try {
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "find"); // �敪flag

			AppropriateCompany param = new AppropriateCompany();
			// �{�M�ʉ݃R�[�h
			param.setCUR_CODE(TLoginInfo.getCompany().getAccountConfig().getKeyCurrency().getCode());
			// ��ЃR�[�h
			param.setKAI_CODE(field.getValue());
			// ����敪
			param.setBlnOptKbn(false);
			// �J�n��ЃR�[�h
			param.setStrCompanyCode(field.getStrCompanyCode());
			// �J�n��ЃR�[�h
			param.setEndCompanyCode(field.getEndCompanyCode());
			// �ʉ݋敪
			param.setCurKbn(field.isCurKbn());

			addSendDto(param);
			if (!request(TARGET_SERVLET)) {
				throw new TRequestException();
			}

			return this.getResultDtoList(AppropriateCompany.class);

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}

	/**
	 * �_�C�A���O�\��
	 * 
	 * @return boolean
	 */
	public boolean showSearchDialog() {
		try {

			Map<String, String> initParam = getSearchDialogParam();
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.APP_KAI, initParam);
			searchdialog.setCurKbn(field.isCurKbn());
			searchdialog.setBeginCode(field.getStrCompanyCode());
			searchdialog.setEndCode(field.getEndCompanyCode());

			// ���ސݒ�A��������
			if (showDefaultCode && !Util.isNullOrEmpty(field.getValue())) {
				searchdialog.setCode(String.valueOf(field.getValue()));
				searchdialog.searchData(false);
			}

			searchdialog.show();

			// �����_�C�A���O��\��
			boolean isSettled = showDialog(searchdialog);

			return isSettled;

		} catch (Exception e) {
			errorHandler(field, e, "E00009");
			return false;
		}

	}

	/**
	 * ����dialog�����l�̃Z�b�g
	 * 
	 * @return param �^�C�g��
	 */
	protected Map<String, String> getSearchDialogParam() {

		Map<String, String> param = new HashMap<String, String>();

		param.put("top", "C01686"); // top�̃Z�b�g
		param.put("code", "C00596"); // �R�[�h�̃Z�b�g
		param.put("nameS", "C00686"); // ����

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
			field.setValue(info[0]);
			field.getField().requestFocus();
			return true;
		}
		field.getField().requestFocus();
		return false;
	}

}
