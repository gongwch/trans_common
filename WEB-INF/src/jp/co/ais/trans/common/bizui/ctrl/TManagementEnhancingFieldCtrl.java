package jp.co.ais.trans.common.bizui.ctrl;

import java.io.*;
import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.config.*;

/**
 * �Ǘ��}�X�^�����t�B�[���h�g���R���g���[��
 */
public class TManagementEnhancingFieldCtrl extends TManagementFieldCtrl {

	/** �����T�[�u���b�g */
	protected static final String CUSTOMER_TARGET_SERVLET = "MG0150CustomerMasterServlet";

	/** �����T�[�u���b�g */
	protected static final String EMPLOYEE_SERVLET = "MG0160EmployeeMasterServlet";

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** �t�B�[���h */
	protected TManagementEnhancingField panel;

	/**  */
	protected boolean sts;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param panel �t�B�[���h
	 */
	public TManagementEnhancingFieldCtrl(TManagementEnhancingField panel) {
		super(panel);
		this.panel = panel;
	}

	/**
	 * �Ǘ��}�X�^��������
	 */
	@Override
	public void managementMouseClicked() {
		if (panel.getManagementType() == TManagementEnhancingField.TYPE_CUSTOMER) {
			customerMouseClicked();
		} else if (panel.getManagementType() == TManagementEnhancingField.TYPE_EMP) {
			empMouseClicked();
		} else {
			super.managementMouseClicked();
		}
	}

	/**
	 * ���X�g�t�H�[�J�X����<BR>
	 */
	@Override
	public boolean managementLostFocus() {
		if (panel.getManagementType() == TManagementEnhancingField.TYPE_CUSTOMER) {
			sts = customerLostFocus();
		} else if (panel.getManagementType() == TManagementEnhancingField.TYPE_EMP) {
			sts = employeeLostFocus();
		} else {
			sts = super.managementLostFocus();
		}
		return sts;
	}

	/**
	 * ����惍�X�g�t�H�[�J�X����
	 * 
	 * @return true:���폈��
	 */
	public boolean customerLostFocus() {
		try {
			String code = panel.getValue();

			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			if (Util.isNullOrEmpty(code)) {
				panel.setNoticeValue("");
				panel.clearOldText();
				return true;
			}

			TRI_MST triMst = getCustomerFieldDataList(code);

			// ���ʖ����̏ꍇ
			if (triMst == null) {
				if (panel.isCheckMode()) {
					if (panel.isLoanCustomer()) {
						showMessage(panel, "W00081", "C02689");
					} else {
						showMessage(panel, "W00081", "C00786");
					}
				}
				return returnFalse();
			}
			if (!Util.isNullOrEmpty(panel.getTermBasisDate())) {
				Date termDate = null;
				Date strDate = null;
				Date endDate = null;
				try {
					termDate = DateUtil.toYMDDate(panel.getTermBasisDate());
					strDate = DateUtil.toYMDDate(triMst.getSTR_DATE());
					endDate = DateUtil.toYMDDate(triMst.getEND_DATE());
				} catch (ParseException e) {
					// �����������ꂽ catch �u���b�N
					errorHandler(panel, e);
					return false;
				}
				if (termDate.after(endDate) || strDate.after(termDate)) {

					if (!showConfirmMessage(panel, "Q00025", "C00408")) {
						return false;
					}
				}

			}
			// ���̃Z�b�g
			panel.setNoticeValue(Util.avoidNull(triMst.getTRI_NAME_S()));

			return true;
		} catch (TRequestException e) {
			errorHandler(panel);
			return returnFalse();

		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
			return returnFalse();
		}
	}

	/**
	 * ���̂��N���A����false��Ԃ�
	 * 
	 * @return false
	 */
	protected boolean returnFalse() {
		panel.setNoticeValue("");
		panel.clearOldText();
		panel.requestTextFocus();
		return false;
	}

	/**
	 * �Ј����[�X�g�t�H�[�J�X����
	 * 
	 * @return true:���폈��
	 */
	public boolean employeeLostFocus() {
		try {
			String code = panel.getValue();

			// �e�L�X�g�t�B�[���h�ɕ����񂪓��͂���Ă����Ƃ��̂ݗL��
			if (Util.isNullOrEmpty(code)) {
				panel.setNoticeValue("");
				panel.clearOldText();
				return true;
			}

			List list = getEmployeeFieldDataList(code);

			// ���ʖ����̏ꍇ
			if (list.isEmpty()) {
				if (panel.isCheckMode()) {
					showMessage(panel, "W00081", "C00697");
				}
				panel.setNoticeValue("");
				panel.clearOldText();
				panel.requestTextFocus();
				return !panel.isCheckMode();
			}

			// �L�����Ԃ̃t���O
			if (!Util.isNullOrEmpty(panel.getTermBasisDate())) {
				Date termDate = null;
				Date strDate = null;
				Date endDate = null;

				termDate = DateUtil.toYMDDate(panel.getTermBasisDate());
				strDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(2));
				endDate = DateUtil.toYMDDate((String) ((List) list.get(0)).get(3));

				if (termDate.after(endDate) || strDate.after(termDate)) {

					if (!showConfirmMessage(panel, "Q00025", "C00246")) {
						return false;
					}
				}

			}

			StringBuilder bfieldName = new StringBuilder();
			bfieldName.append((String) ((List) list.get(0)).get(1));

			panel.setNoticeValue(bfieldName.toString());

			return true;
		} catch (Exception e) {
			errorHandler(panel, e);
			return false;
		}

	}

	/**
	 * �����_�C�A���O�\��
	 */
	public void customerMouseClicked() {
		try {
			Map<String, String> initParam = new HashMap<String, String>();

			if (panel.isLoanCustomer()) {
				initParam = getLoanDialogParam();
			} else {
				initParam = getCustomerSearchDialogParam();
			}
			REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(panel, REFDialogMasterCtrl.CUSTOMER_MST,
				initParam);

			searchdialog.setStartCode(""); // �J�n�R�[�h
			searchdialog.setEndCode(""); // �I���R�[�h
			searchdialog.setServerName("refCustomer");
			Map<String, String> otherParam = new HashMap<String, String>();

			if (!Util.isNullOrEmpty(panel.getCompanyCode())) {
				otherParam.put("companyCode", panel.getCompanyCode());
			} else {
				otherParam.put("companyCode", getLoginUserCompanyCode());
			}
			otherParam.put("termBasisDate", panel.getTermBasisDate());
			otherParam.put("beginCode", panel.getBeginCode());
			otherParam.put("endCode", panel.getEndCode());
			searchdialog.setParamMap(otherParam);

			// ���ސݒ�A��������
			if (showDefaultCode && !Util.isNullOrEmpty(panel.getField().getValue())) {
				searchdialog.setCode(String.valueOf(panel.getField().getValue()));
				searchdialog.searchData(false);
			}

			searchdialog.show();

			// �����_�C�A���O��\��

			showDialog(searchdialog);

		} catch (Exception e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * �Ј��_�C�A���O�\��
	 */
	public void empMouseClicked() {
		if (panel.isEnabled()) {
			try {

				Map<String, String> initParam = getEmployeeSearchDialogParam();
				REFDialogMasterCtrl searchdialog = new REFDialogMasterCtrl(panel, REFDialogMasterCtrl.EMP_SEARCH_MST,
					initParam);

				searchdialog.setStartCode(""); // �J�n�R�[�h
				searchdialog.setEndCode(""); // �I���R�[�h
				searchdialog.setServerName("refEmployee");
				Map<String, String> otherParam = new HashMap<String, String>();

				if (!Util.isNullOrEmpty(panel.getCompanyCode())) {
					otherParam.put("companyCode", panel.getCompanyCode());
				} else {
					otherParam.put("companyCode", getLoginUserCompanyCode());
				}
				otherParam.put("termBasisDate", panel.getTermBasisDate());
				otherParam.put("beginCode", panel.getBeginCode());
				otherParam.put("endCode", panel.getEndCode());
				searchdialog.setParamMap(otherParam);

				// ���ސݒ�A��������
				if (showDefaultCode && !Util.isNullOrEmpty(panel.getField().getValue())) {
					searchdialog.setCode(String.valueOf(panel.getField().getValue()));
					searchdialog.searchData(false);
				}

				searchdialog.show();

				showDialog(searchdialog);

			} catch (Exception e) {
				errorHandler(panel, e, "E00009");
			}

		}

	}

	/**
	 * �����t�B�[���h�f�[�^���X�g�擾
	 * 
	 * @param code �R�[�h
	 * @return �ڍ׃��X�g
	 * @throws TException
	 */
	public TRI_MST getCustomerFieldDataList(String code) throws TException {

		try {
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "CustomerInfo"); // �敪flag
			if (!Util.isNullOrEmpty(panel.getCompanyCode())) {
				addSendValues("companyCode", panel.getCompanyCode());
			} else {
				addSendValues("companyCode", getLoginUserCompanyCode());
			}
			addSendValues("customerCode", code);
			if (!request(CUSTOMER_TARGET_SERVLET)) {
				throw new TRequestException();
			}

			return (TRI_MST) getResultObject();

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}

	/**
	 * �Ј��t�B�[���h�f�[�^���X�g�擾
	 * 
	 * @param code �R�[�h
	 * @return �ڍ׃��X�g
	 * @throws TException
	 */
	protected List getEmployeeFieldDataList(String code) throws TException {

		try {
			// ���M����p�����[�^��ݒ�
			addSendValues("flag", "refEmployee"); // �敪flag
			if (!Util.isNullOrEmpty(panel.getCompanyCode())) {
				addSendValues("kaiCode", panel.getCompanyCode());
			} else {
				addSendValues("kaiCode", getLoginUserCompanyCode());
			}
			addSendValues("empCode", code);
			if (!request(EMPLOYEE_SERVLET)) {
				// ����ɏ�������܂���ł���
				throw new TRequestException();
			}

			return getResultList();

		} catch (IOException ex) {
			throw new TException(ex);
		}
	}

	/**
	 * ����挟��dialog�����l�̃Z�b�g
	 * 
	 * @return param
	 */
	protected Map<String, String> getCustomerSearchDialogParam() {

		Map<String, String> param = new HashMap<String, String>();

		param.put("top", "C01676"); // top�̃Z�b�g
		param.put("code", "C00786"); // �R�[�h�̃Z�b�g
		param.put("nameS", "C00787"); // ����
		param.put("nameK", "C00836"); // ��������

		return param;
	}

	/**
	 * �Ј�����dialog�����l�̃Z�b�g
	 * 
	 * @return param
	 */
	protected Map<String, String> getEmployeeSearchDialogParam() {
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
	 * @return true �m��̏ꍇ false ����̏ꍇ
	 */
	protected boolean showDialog(REFDialogMasterCtrl dialog) {
		// �m��̏ꍇ
		if (dialog.isSettle()) {
			String[] info = dialog.getCurrencyInfo();
			panel.setValue(info[0]); // �R�[�h���Z�b�g����
			panel.setNoticeValue(info[1]); // ���̂��Z�b�g����
			panel.getField().requestFocus();
			return true;
		}
		panel.getField().requestFocus();
		return false;
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
		param.put("nameS", getWord("C02690") + getWord("C00518")); // ����
		param.put("nameK", getWord("C02690") + getWord("C00160")); // ��������

		return param;
	}
}
