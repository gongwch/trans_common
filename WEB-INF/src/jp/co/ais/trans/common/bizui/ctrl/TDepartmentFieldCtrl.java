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
 * ����t�B�[���h�̃R���g���[
 */
public class TDepartmentFieldCtrl extends TAppletClientBase {

	/** ����t�B�[���h */
	protected TDepartmentField field;

	/** �����T�[�u���b�g ����̂� */
	protected static final String TARGET_SERVLET = "MG0060DepartmentMasterServlet";

	/** �����T�[�u���b�g ����Ƒg�D */
	protected static final String TARGET_SERVLET_DPK = "MG0050DepartmentHierarchyMasterServlet";

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/**
	 * �R���X�g���N�^
	 * 
	 * @param itemField ����t�B�[���h
	 */
	public TDepartmentFieldCtrl(TDepartmentField itemField) {
		this.field = itemField;
	}

	/**
	 * ����ւ̒l�Z�b�g�i���[�X�g�t�H�[�J�X�Ή��j
	 * 
	 * @return boolean
	 */
	public boolean setDepartNotice() {

		try {
			String code = field.getValue();
			String dpkCode = field.getOrganization();

			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			if (Util.isNullOrEmpty(code)) {
				field.setNoticeValue("");
				field.clearOldText();
				return true;
			}

			// ���喼�̂̏K��
			BMN_MST bean = findData(code);

			// �`�F�b�N���[�h�I���̏ꍇ
			if (field.isChekcMode()) {

				// ���ʖ����̏ꍇ
				if (bean == null) {
					showMessage(field, "W00081", "C00698");
					return this.returnFalse();
				}

				// �L�����ԃ`�F�b�N
				if (!Util.isNullOrEmpty(field.getTermBasisDate())) {
					Date termDate = DateUtil.toYMDDate(field.getTermBasisDate());
					if (termDate.after(bean.getEND_DATE()) || bean.getSTR_DATE().after(termDate)) {
						if (!showConfirmMessage(field, "Q00025", "C00467")) {

							return this.returnFalse();
						}
					}
				}

				if (!field.isSumDepartment() && 1 == bean.getDEP_KBN()) {
					// �W�v����͓��͂ł��܂���B
					String word = getWord("C00255") + getWord("C00467");
					showMessage(field, "W00280", word);

					return this.returnFalse();
				}

				if (!field.isInputDepartment() && 0 == bean.getDEP_KBN()) {
					// ���͕���͓��͂ł��܂���
					showMessage(field, "W00280", "C01280");

					return this.returnFalse();
				}

				// �g�D�R�[�h���ݒ肳��Ă���B
				if (!Util.isNullOrEmpty(dpkCode)) {

					// ����R�[�h�Ƒg�D�R�[�h�����킹��B
					List listDpk = getFieldDataListDpk(code, dpkCode);

					if (listDpk.isEmpty()) {

						// �w��̕���͎w��̑g�D�ɑ��݂��܂���B
						showMessage(field, "W00281");
						return this.returnFalse();
					}

					// �w��̑g�D�����݂���ꍇ�B
					if (field.getDepartmentLevel() != 0 && field.getDepartmentLevel() != TDepartmentField.NOT_SET) {

						// ��ʃ��x�����ݒ肳��Ă���ꍇ�B���x�������̒l�ɖ߂��i�|�P�j
						int getLevel = (field.getDepartmentLevel()) - 1;
						int searchLevel = Integer.parseInt((String) ((List) listDpk.get(0)).get(0));

						// �ݒ背�x���ƌ������x�����Ⴄ�ꍇ�G���[���o��
						if (getLevel != searchLevel) {
							// �w��̕���͕���K�w���x��@DPK_LVL�ł�
							showMessage(field, "W00282", String.valueOf(searchLevel));

							return this.returnFalse();

						} else if (getLevel == searchLevel) {

							// �ݒ��ʕ��僌�x������v����ʕ���R�[�h���ݒ肳��Ă���ꍇ
							if (!Util.isNullOrEmpty(field.getUpperDepartment())) {
								String upperLevelCode = (String) ((List) listDpk.get(0)).get(getLevel + 1);

								// �w��̏�ʕ���ƈႤ�ꍇ�G���[���o���B
								if (!field.getUpperDepartment().equals(upperLevelCode)) {
									showMessage(field, "W01008", String.valueOf(searchLevel));

									return this.returnFalse();
								}
							}
						}
					}
				}

			} else {

				if (bean != null) {
					if (!field.isSumDepartment() && 1 == bean.getDEP_KBN()) {
						// �W�v�敪 = False ���� �W�v����R�[�h�̏ꍇ ���̂�\�����Ȃ�
						field.setNoticeValue("");
						return true;
					}

					if (!field.isInputDepartment() && 0 == bean.getDEP_KBN()) {
						// ���͋敪 = False ���� ���͕���R�[�h�̏ꍇ ���̂�\�����Ȃ�
						field.setNoticeValue("");
						return true;
					}
				}
			}

			field.setNoticeValue(bean != null ? bean.getDEP_NAME_S() : "");

			return true;

		} catch (TRequestException e) {
			errorHandler(field);
			return this.returnFalse();

		} catch (Exception e) {
			errorHandler(field, e);
			return this.returnFalse();
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
	 * @param code ����R�[�h
	 * @return �ڍ�
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected BMN_MST findData(String code) throws IOException, TRequestException {

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "DepartmentInfo"); // �敪flag
		if (Util.isNullOrEmpty(field.getCompanyCode())) {
			field.setCompanyCode(super.getLoginUserCompanyCode());
		}

		addSendValues("companyCode", field.getCompanyCode());
		addSendValues("deptCode", code);

		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}

		return (BMN_MST) getResultDto(BMN_MST.class);
	}

	/**
	 * �t�B�[���h�f�[�^���X�g�擾 �i����Ƒg�D�Ŏw��̃��x�����K���j
	 * 
	 * @param depCode ����R�[�h
	 * @param dpkCode �g�D�R�[�h
	 * @return �ڍ׃��X�g
	 * @throws IOException
	 * @throws TRequestException
	 */
	protected List getFieldDataListDpk(String depCode, String dpkCode) throws IOException, TRequestException {

		// ���M����p�����[�^��ݒ�
		addSendValues("flag", "refHierarchy"); // �敪flag

		if (Util.isNullOrEmpty(field.getCompanyCode())) {
			field.setCompanyCode(super.getLoginUserCompanyCode());
		}

		addSendValues("kaiCode", field.getCompanyCode());
		addSendValues("dpkDepCode", depCode);
		addSendValues("dpkSsk", dpkCode);

		if (!request(TARGET_SERVLET_DPK)) {
			throw new TRequestException();
		}

		return getResultList();
	}

	/**
	 * �_�C�A���O�\��
	 */
	public void showSearchDialog() {

		try {

			Map<String, String> initParam = getSearchDialogParam();
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.DEPARTMENT_MST,
				initParam);

			searchdialog.setStartCode(field.getBeginCode()); // �J�n�R�[�h
			searchdialog.setEndCode(field.getEndCode()); // �I���R�[�h
			searchdialog.setServerName("refDepartment");
			Map<String, String> otherParam = new HashMap<String, String>();

			if (Util.isNullOrEmpty(field.getCompanyCode())) {
				field.setCompanyCode(super.getLoginUserCompanyCode());
			}
			otherParam.put("companyCode", field.getCompanyCode());
			// ���僌�x���w��
			if (field.getDepartmentLevel() == 0) {
				otherParam.put("level", String.valueOf(TDepartmentField.NOT_SET));
			} else {
				otherParam.put("level", String.valueOf(field.getDepartmentLevel()));
			}

			otherParam.put("sum", BooleanUtil.toString(field.isSumDepartment()));
			otherParam.put("input", BooleanUtil.toString(field.isInputDepartment()));
			otherParam.put("organization", field.getOrganization());
			otherParam.put("termBasisDate", field.getTermBasisDate());
			otherParam.put("upper", field.getUpperDepartment());
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
		param.put("top", "C01687"); // top�̃Z�b�g
		param.put("code", "C00698"); // �R�[�h�̃Z�b�g
		param.put("nameS", "C00724"); // ����
		param.put("nameK", "C00725"); // ��������

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
