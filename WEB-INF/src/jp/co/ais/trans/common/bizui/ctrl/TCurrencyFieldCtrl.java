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
 * �ʉ݃t�B�[���h�̃R���g���[��
 * 
 * @author roh
 */
public class TCurrencyFieldCtrl extends TAppletClientBase {

	/** �ʉ݃t�B�[���h */
	protected TCurrencyField field;

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "MG0300CurrencyMasterServlet";

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/**
	 * �R���X�g���N�^
	 * 
	 * @param itemField
	 */
	public TCurrencyFieldCtrl(TCurrencyField itemField) {
		this.field = itemField;
	}

	/**
	 * �����ւ̒l�Z�b�g
	 * 
	 * @return boolean
	 */
	public boolean setCurrencyInfo() {

		try {
			String code = field.getValue();

			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.clearOldText();
				return true;
			}

			// �ʉ݃f�[�^
			List<List<String>> list = getFieldDataList(code);

			// ���ʖ����̏ꍇ
			if (list.isEmpty()) {
				if (!field.isFocusOut()) {
					showMessage(field, "W00081", "C00665");
					field.setNoticeValue("");
					field.clearOldText();
					field.requestTextFocus();
				}
				return false;
			}

			// �L�����ԃ`�F�b�N
			if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
				Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
				Date strDate = DateUtil.toYMDDate(list.get(0).get(2));
				Date endDate = DateUtil.toYMDDate(list.get(0).get(3));
				if (termDate.after(endDate) || strDate.after(termDate)) {
					if (!field.isFocusOut()) {
						if (!showConfirmMessage(field, "Q00025", "C00371")) {
							field.clearOldText();
							field.requestTextFocus();
							return false;
						}
					} else {
						return false;
					}
				}
			}

			field.setNoticeValue(list.get(0).get(1));
			if (Util.isNullOrEmpty(list.get(0).get(4))) {
				field.setDecKeta(0);
			} else {
				field.setDecKeta((Integer.parseInt(list.get(0).get(4))));
			}

			return true;
		} catch (Exception e) {
			errorHandler(field, e);
			return false;
		}

	}

	/**
	 * �t�B�[���h�f�[�^���X�g�擾
	 * 
	 * @param code �ʉ݃R�[�h
	 * @return �ڍ׃��X�g
	 * @throws TException
	 */
	public List<List<String>> getFieldDataList(String code) throws TException {

		try {
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "refCurrency"); // �敪flag
			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				addSendValues("kaiCode", field.getCompanyCode());
			} else {
				addSendValues("kaiCode", getLoginUserCompanyCode());
			}
			addSendValues("curCode", code);
			if (!request(TARGET_SERVLET)) {
				throw new TRequestException();
			}

			return getResultList();

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
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.CURRENCY_MST,
				initParam);

			searchdialog.setStartCode(""); // �J�n�R�[�h
			searchdialog.setEndCode(""); // �I���R�[�h
			searchdialog.setServerName("refCurrency");
			Map<String, String> otherParam = new HashMap<String, String>();

			if (!Util.isNullOrEmpty(field.getCompanyCode())) {
				otherParam.put("companyCode", field.getCompanyCode());
			} else {
				otherParam.put("companyCode", getLoginUserCompanyCode());
			}

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

		param.put("top", "C01674"); // top�̃Z�b�g
		param.put("code", "C00665"); // �R�[�h�̃Z�b�g
		param.put("nameS", "C00881"); // ����
		param.put("nameK", "C00882"); // ��������

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
