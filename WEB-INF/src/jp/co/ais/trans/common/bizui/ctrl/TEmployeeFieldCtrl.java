package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans2.common.config.*;

/**
 * �Ј������t�B�[���h�̃R���g���[��
 * 
 * @author roh
 */
public class TEmployeeFieldCtrl extends TAppletClientBase {

	/** �Ј����� �t�B�[���h */
	protected TEmployeeField field;

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "MG0160EmployeeMasterServlet";

	/** �����T�[�u���b�g�i���[�U���W�b�N�Ή��j */
	protected static final String TARGET_SERVLET_USER = "MG0020UserMasterServlet";

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** �L�����ԃt���O */
	public boolean termBasisDateFlag = true;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param itemField (��ʁj
	 */
	public TEmployeeFieldCtrl(TEmployeeField itemField) {
		this.field = itemField;
	}

	/**
	 * ���[�X�g�t�H�[�J�X�̑Ή�
	 * 
	 * @return boolean
	 */
	public boolean setEmployeeNotice() {

		try {
			String code = field.getValue();

			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.clearOldText();
				return true;
			}

			// �Ј��R�[�h�Ńf�[�^�擾
			List list = getFieldDataList(code);

			// ���ʖ����̏ꍇ
			if (list.isEmpty()) {
				showMessage(field, "W00081", "C00697");
				field.setNoticeValue("");
				field.clearOldText();
				field.requestTextFocus();
				return false;
			}

			if (termBasisDateFlag) {
				// �L�����Ԃ̃t���O
				if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
					Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
					Date strDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(2));
					Date endDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(3));

					if (termDate.after(endDate) || strDate.after(termDate)) {
						// �Θb�_�C�A���O���J���A���s�̉ۂ�₤
						if (!showConfirmMessage(field, "Q00025", "C00246")) {
							field.requestTextFocus();
							return false;
						}
					}
				}
			}

			// ���[�U�o�^�t���O�����݂���Ƃ�
			if (field.isUser()) {
				// ���[�U�f�[�^���K��
				List userList = getFieldUserDataList(code);
				if (userList.isEmpty()) {
					showMessage(field, "W01006");
					return false;
				}

				// ���[�U�o�^���ꂽ�l�̕���ݒ肪����Ƃ�
				if (!Util.isNullOrEmpty(field.getDepartmentCode())) {
					List userRow = (List) userList.get(0);
					if (!field.getDepartmentCode().equals(userRow.get(1))) {
						showMessage(field, "W01007");
						return false;
					}
				}
			}

			StringBuilder bfieldName = new StringBuilder();
			bfieldName.append((String) ((List) list.get(0)).get(1));

			// �ڍ׃t�B�[���h�ɒl��ݒ�
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
	 * @param code �Ј��R�[�h
	 * @return �ڍ׃��X�g
	 * @throws TException
	 */
	protected List getFieldDataList(String code) throws TException {

		try {
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "refEmployee"); // �敪flag
			if (Util.isNullOrEmpty(field.getCompanyCode())) {
				field.setCompanyCode(getLoginUserCompanyCode());
			}
			addSendValues("kaiCode", field.getCompanyCode());
			addSendValues("empCode", code);
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
	 * �t�B�[���h�f�[�^���X�g�擾(���[�U�}�X�^�Ή��j
	 * 
	 * @param code ���[�U�R�[�h
	 * @return �ڍ׃��X�g
	 * @throws TException
	 */
	protected List getFieldUserDataList(String code) throws TException {

		try {
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "refEmployee"); // �敪flag
			if (Util.isNullOrEmpty(field.getCompanyCode())) {
				field.setCompanyCode(getLoginUserCompanyCode());
			}
			addSendValues("kaiCode", field.getCompanyCode());
			addSendValues("usrCode", code);
			if (!request(TARGET_SERVLET_USER)) {
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
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.EMP_SEARCH_MST,
				initParam);
			searchdialog.setStartCode(""); // �J�n�R�[�h
			searchdialog.setEndCode(""); // �I���R�[�h
			searchdialog.setServerName("refEmployee");

			// �����p�p�����[�^���_�C�A���O�ɐݒ肷��B
			Map<String, String> otherParam = new HashMap<String, String>();

			if (Util.isNullOrEmpty(field.getCompanyCode())) {
				field.setCompanyCode(getLoginUserCompanyCode());
			}
			otherParam.put("companyCode", field.getCompanyCode());
			otherParam.put("termBasisDate", field.getTermBasisDate());
			otherParam.put("user", String.valueOf(field.isUser()));
			otherParam.put("depCode", field.getDepartmentCode());
			otherParam.put("beginCode", field.getBeginCode());// �J�n�R�[�h
			otherParam.put("endCode", field.getEndCode()); // �I���R�[�h
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

		param.put("top", "C01677"); // top�̃Z�b�g
		param.put("code", "C00697"); // �R�[�h�̃Z�b�g
		param.put("nameS", "C00808"); // ����
		param.put("nameK", "C00809"); // ��������

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
