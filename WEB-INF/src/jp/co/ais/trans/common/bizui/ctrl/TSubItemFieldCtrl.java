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
 * �⏕�Ȗڃt�B�[���h�R���g���[��
 */
public class TSubItemFieldCtrl extends TAppletClientBase {

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "TAccountItemUnitServlet";

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** �t�B�[���h */
	protected TSubItemField field;

	/** �R�[�h(����) */
	protected String codeLabel = getWord("C00174");

	/**
	 * �R���X�g���N�^
	 * 
	 * @param subItemField
	 */
	public TSubItemFieldCtrl(TSubItemField subItemField) {

		this.field = subItemField;
	}

	/**
	 * �⏕�Ȗڃ}�X�^��������
	 * 
	 * @return boolean
	 */
	public boolean search() {

		try {
			// �⏕�Ȗڃ}�X�^�ꗗ�̏ꍇ
			REFDialogMasterCtrl dialog = new REFDialogMasterCtrl(field, REFDialogMasterCtrl.HKM_MST);

			// ��ЃR�[�h
			String companyCode;
			if (Util.isNullOrEmpty(field.getInputParameter().getCompanyCode())) {
				companyCode = super.getLoginUserCompanyCode();
			} else {
				companyCode = field.getInputParameter().getCompanyCode();
			}

			// �����ݒ� Dialog
			setCondition(dialog, companyCode);

			// ���ސݒ�A��������
			if (showDefaultCode && !Util.isNullOrEmpty(field.getValue())) {
				dialog.setCode(String.valueOf(field.getValue()));
				dialog.searchData(false);
			}

			// �����_�C�A���O��\��
			dialog.show();

			boolean isSettled = dialog.isSettle();

			// �m��̏ꍇ
			if (isSettled) {
				String[] info = dialog.getCurrencyInfo();

				// �t�B�[���h�Ɠ���R�[�h���I�΂ꂽ�ꍇ�͏����Ȃ�
				if (field.getValue().equals(info[0]) && !field.getNoticeValue().isEmpty()) {
					return false;
				}

				// �a���ȖڃR�[�h
				field.setValue(info[0]);

				// �a���Ȗڗ���
				field.setNoticeValue(info[1]);

				field.getField().pushOldText();

				// ���X�g�t�H�[�J�X����
				setupInfo();
			}

			return isSettled;

		} catch (Exception e) {
			errorHandler(field, e, "E00009");

			return false;
		} finally {
			field.getField().requestFocus();
		}
	}

	/**
	 * �⏕�Ȗڃ��X�g�t�H�[�J�X����<BR>
	 * 
	 * @return boolean
	 */
	public boolean setupInfo() {

		try {
			// OutputParameter�̒l���N���A����
			field.getOutputParameter().clear();

			// ��ЃR�[�h
			String companyCode;
			if (Util.isNullOrEmpty(field.getInputParameter().getCompanyCode())) {
				companyCode = super.getLoginUserCompanyCode();
			} else {
				companyCode = field.getInputParameter().getCompanyCode();
			}

			// �⏕�Ȗڃ��R�[�h
			String subItemCode = field.getValue();

			if (Util.isNullOrEmpty(subItemCode)) {
				// �ȖڃR�[�h���̃u�����N�ŃZ�b�g����B
				field.setNoticeValue("");

				return true;
			}

			// �⏕�Ȗڃ}�X�^�f�[�^������
			Map<String, String> map = findInfo(companyCode, subItemCode);

			// �w��R�[�h�̑��݃`�F�b�N
			field.getOutputParameter().setExist(!"0".equals(map.get("existFlag")));

			// �⏕�ȖڃR�[�h���݂̏ꍇ
			if (field.getOutputParameter().isExist()) {

				// �⏕�Ȗږ��̂��Z�b�g
				field.setNoticeValue(Util.avoidNull(map.get("HKM_NAME_S")));

				// �擾�����l��OutputParameter�ɃZ�b�g
				setResultData(map);

				return true;

			} else {
				if (field.isCheckMode()) {
					// ���݂��܂���B
					showMessage(field, "W00081", (Object) field.getButtonText() + codeLabel);
				}

				field.clearOldText();

				// �⏕�ȖڃR�[�h���̃u�����N�ŃZ�b�g����B
				field.setNoticeValue("");

				// �⏕�ȖڃR�[�h���X�g�t�H�[�J�X���擾����B
				field.requestTextFocus();

				return !field.isCheckMode();
			}

		} catch (Exception e) {
			errorHandler(this.field);

			return false;
		}
	}

	/**
	 * �擾�����l��OutputParameter�ɃZ�b�g
	 * 
	 * @param resultMap �擾�����f�[�^
	 */
	protected void setResultData(Map resultMap) {

		AccountItemOutputParameter param = field.getOutputParameter();

		// �Ȗڗ���
		param.setSubItemAbbrName(Util.avoidNull(resultMap.get("HKM_NAME_S")));
		// ����敪
		param.setBreakDownItemFlag(Util.avoidNull(resultMap.get("UKM_KBN")));
		// ����ېœ��̓t���O
		param.setSalesTaxInputFlag(Util.avoidNull(resultMap.get("URI_ZEI_FLG")));
		// �d���ېœ��̓t���O
		param.setPurchaseTaxationInputFlag(Util.avoidNull(resultMap.get("SIR_ZEI_FLG")));
		// ����ŋ敪
		param.setConsumptionTaxDivision(Util.avoidNull(resultMap.get("SZEI_KEI_KBN")));
		// ����ŃR�[�h
		param.setConsumptionTaxCode(Util.avoidNull(resultMap.get("ZEI_CODE")));
		// ����ŗ���
		param.setConsumptionTaxAbbrName(Util.avoidNull(resultMap.get("ZEI_NAME_S")));
		// �������̓t���O
		param.setCustomerInputFlag(Util.avoidNull(resultMap.get("TRI_CODE_FLG")));
		// �Ј��R�[�h
		param.setEmployeeCode(Util.avoidNull(resultMap.get("EMP_CODE")));
		// �Ј�����
		param.setEmployeeAbbrName(Util.avoidNull(resultMap.get("EMP_NAME_S")));
		// �Ј����̓t���O
		param.setEmployeeInputFlag(Util.avoidNull(resultMap.get("EMP_CODE_FLG")));
		// �Ǘ�1���̓t���O
		param.setManagement1InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_1")));
		// �Ǘ�2���̓t���O
		param.setManagement2InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_2")));
		// �Ǘ�3���̓t���O
		param.setManagement3InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_3")));
		// �Ǘ�4���̓t���O
		param.setManagement4InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_4")));
		// �Ǘ�5���̓t���O
		param.setManagement5InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_5")));
		// �Ǘ�6���̓t���O
		param.setManagement6InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_6")));
		// ���v����1���̓t���O
		param.setNonAccountingDetail1Flag(Util.avoidNull(resultMap.get("HM_FLG_1")));
		// ���v����2���̓t���O
		param.setNonAccountingDetail2Flag(Util.avoidNull(resultMap.get("HM_FLG_2")));
		// ���v����3���̓t���O
		param.setNonAccountingDetail3Flag(Util.avoidNull(resultMap.get("HM_FLG_3")));
		// ���������̓t���O
		param.setAccrualDateInputFlag(Util.avoidNull(resultMap.get("HAS_FLG")));
		// ���ʉݓ��̓t���O
		param.setMultipleCurrencyInputFlag(Util.avoidNull(resultMap.get("MCR_FLG")));
	}

	/**
	 * �����ݒ� Dialog
	 * 
	 * @param dialog �����_�C�A���O
	 * @param companyCode ��ЃR�[�h
	 */
	protected void setCondition(REFDialogMasterCtrl dialog, String companyCode) {
		// ��ЃR�[�h
		dialog.setKaiCode(Util.avoidNull(companyCode));
		// �ȖڃR�[�h
		dialog.setKmkCode(Util.avoidNull(field.getInputParameter().getItemCode()));
		// �`�[���t
		dialog.setSlipDate(Util.avoidNull(field.getInputParameter().getSlipDate()));
		// �U�֓`�[���̓t���O
		dialog.setFurikaeFlg(Util.avoidNull(field.getInputParameter().getTransferSlipInputFlag()));
		// �����`�[���̓t���O
		dialog.setNyuKin(Util.avoidNull(field.getInputParameter().getRecivingSlipInputFlag()));
		// �o���`�[���̓t���O
		dialog.setShutsuKin(Util.avoidNull(field.getInputParameter().getDrawingSlipInputFlag()));
		// �o��Z�`�[���̓t���O
		dialog.setKeihiFlg(Util.avoidNull(field.getInputParameter().getExpenseInputFlag()));
		// ���v����̓t���O
		dialog.setSaimuFlg(Util.avoidNull(field.getInputParameter().getSaimuFlg()));
		// ���v����̓t���O
		dialog.setSaikenFlg(Util.avoidNull(field.getInputParameter().getSaikenFlg()));
		// �������`�[���̓t���O
		dialog.setSaikesiFlg(Util.avoidNull(field.getInputParameter().getAccountsRecivableErasingSlipInputFlag()));
		// ���Y�v��`�[���̓t���O
		dialog.setSisanFlg(Util.avoidNull(field.getInputParameter().getAssetsAppropriatingSlipInputFlag()));
		// �x���˗��`�[���̓t���O
		dialog.setSiharaiFlg(Util.avoidNull(field.getInputParameter().getPaymentRequestSlipInputFlag()));

		// �J�n�R�[�h
		dialog.setBeginCode(Util.avoidNull(field.getInputParameter().getBeginCode()));
		// �I���R�[�h
		dialog.setEndCode(Util.avoidNull(field.getInputParameter().getEndCode()));

		// �]���֑Ώۃt���O
		dialog.setExcFlg(Util.avoidNull(field.getInputParameter().getRevaluationObjectFlag()));
		// ����敪
		dialog.setUkmKbn(Util.avoidNull(this.field.getInputParameter().getBreakDownItemDivision()));
		// ����ź���
		dialog.setZeiCode(Util.avoidNull(this.field.getInputParameter().getConsumptionTaxCode()));
		// ���������׸�
		dialog.setTriCodeFlag(Util.avoidNull(this.field.getInputParameter().getCustomerInputFlag()));
		// �����������׸�
		dialog.setHasFlg(Util.avoidNull(this.field.getInputParameter().getAccrualDateInputFlag()));
		// �Ј������׸�
		dialog.setEmpCodeFlg(Util.avoidNull(this.field.getInputParameter().getEmployeeInputFlag()));
		// �Ǘ�1
		dialog.setKnrFlg1(Util.avoidNull(this.field.getInputParameter().getManagement1InputFlag()));
		// �Ǘ�2
		dialog.setKnrFlg2(Util.avoidNull(this.field.getInputParameter().getManagement2InputFlag()));
		// �Ǘ�3
		dialog.setKnrFlg3(Util.avoidNull(this.field.getInputParameter().getManagement3InputFlag()));
		// �Ǘ�4
		dialog.setKnrFlg4(Util.avoidNull(this.field.getInputParameter().getManagement4InputFlag()));
		// �Ǘ�5
		dialog.setKnrFlg5(Util.avoidNull(this.field.getInputParameter().getManagement5InputFlag()));
		// �Ǘ�6
		dialog.setKnrFlg6(Util.avoidNull(this.field.getInputParameter().getManagement6InputFlag()));
		// ���v1
		dialog.setHmFlg1(Util.avoidNull(this.field.getInputParameter().getNonAccountingDetail1Flag()));
		// ���v2
		dialog.setHmFlg2(Util.avoidNull(this.field.getInputParameter().getNonAccountingDetail2Flag()));
		// ���v3
		dialog.setHmFlg3(Util.avoidNull(this.field.getInputParameter().getNonAccountingDetail3Flag()));
		// ����ېœ����׸�
		dialog.setUriZeiFlg(Util.avoidNull(this.field.getInputParameter().getSalesTaxInputFlag()));
		// �d���ېœ����׸�
		dialog.setSirZeiFlg(Util.avoidNull(this.field.getInputParameter().getPurchaseTaxationInputFlag()));
		// ���ʉݓ����׸�
		dialog.setMcrFlg(Util.avoidNull(this.field.getInputParameter().getMultipleCurrencyInputFlag()));
	}

	/**
	 * �⏕�Ȗڃ}�X�^�f�[�^������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param subItemCode �⏕�ȖڃR�[�h
	 * @return �⏕�Ȗڃ}�X�^�f�[�^
	 * @throws TRequestException
	 * @throws IOException
	 */
	protected Map<String, String> findInfo(String companyCode, String subItemCode) throws TRequestException,
		IOException {

		boolean isWaitCursor = super.isWaitCursorNow(field);

		if (!isWaitCursor) {
			setWaitCursor(field);
		}

		try {
			// �⏕�Ȗڃ}�X�^�����t���O
			addSendValues("FLAG", "HKM_MST");
			// ��ЃR�[�h
			addSendValues("KAI_CODE", companyCode);
			// �ȖڃR�[�h
			addSendValues("KMK_CODE", Util.avoidNull(field.getInputParameter().getItemCode()));
			// �⏕�ȖڃR�[�h
			addSendValues("HKM_CODE", subItemCode);
			// �`�[���t
			addSendValues("SLIP_DATE", Util.avoidNull(field.getInputParameter().getSlipDate()));
			// �����`�[���̓t���O
			addSendValues("NYU_KIN", Util.avoidNull(field.getInputParameter().getRecivingSlipInputFlag()));
			// �o���`�[���̓t���O
			addSendValues("SHUTSU_KIN", Util.avoidNull(field.getInputParameter().getDrawingSlipInputFlag()));
			// �U�֓`�[���̓t���O
			addSendValues("FURIKAE_FLG", Util.avoidNull(field.getInputParameter().getTransferSlipInputFlag()));
			// �o��Z�`�[���̓t���O
			addSendValues("KEIHI_FLG", Util.avoidNull(field.getInputParameter().getExpenseInputFlag()));
			// ���v����̓t���O
			addSendValues("SAIMU_FLG", Util.avoidNull(field.getInputParameter().getSaimuFlg()));
			// ���v����̓t���O
			addSendValues("SAIKEN_FLG", Util.avoidNull(field.getInputParameter().getSaikenFlg()));
			// �������`�[���̓t���O
			addSendValues("SAIKESI_FLG",
				Util.avoidNull(field.getInputParameter().getAccountsRecivableErasingSlipInputFlag()));
			// ���Y�v��`�[���̓t���O
			addSendValues("SISAN_FLG", Util.avoidNull(field.getInputParameter().getAssetsAppropriatingSlipInputFlag()));
			// �x���˗��`�[���̓t���O
			addSendValues("SIHARAI_FLG", Util.avoidNull(field.getInputParameter().getPaymentRequestSlipInputFlag()));

			// �]���֑Ώۃt���O
			addSendValues("EXC_FLG", Util.avoidNull(field.getInputParameter().getRevaluationObjectFlag()));
			// ����敪
			addSendValues("UKM_KBN", Util.avoidNull(this.field.getInputParameter().getBreakDownItemDivision()));
			// ����ź���
			addSendValues("ZEI_CODE", Util.avoidNull(this.field.getInputParameter().getConsumptionTaxCode()));
			// ���������׸�
			addSendValues("TRI_CODE_FLG", Util.avoidNull(this.field.getInputParameter().getCustomerInputFlag()));
			// �����������׸�
			addSendValues("HAS_FLG", Util.avoidNull(this.field.getInputParameter().getAccrualDateInputFlag()));
			// �Ј������׸�
			addSendValues("EMP_CODE_FLG", Util.avoidNull(this.field.getInputParameter().getEmployeeInputFlag()));
			// �Ǘ�1
			addSendValues("KNR_FLG1", Util.avoidNull(this.field.getInputParameter().getManagement1InputFlag()));
			// �Ǘ�2
			addSendValues("KNR_FLG2", Util.avoidNull(this.field.getInputParameter().getManagement2InputFlag()));
			// �Ǘ�3
			addSendValues("KNR_FLG3", Util.avoidNull(this.field.getInputParameter().getManagement3InputFlag()));
			// �Ǘ�4
			addSendValues("KNR_FLG4", Util.avoidNull(this.field.getInputParameter().getManagement4InputFlag()));
			// �Ǘ�5
			addSendValues("KNR_FLG5", Util.avoidNull(this.field.getInputParameter().getManagement5InputFlag()));
			// �Ǘ�6
			addSendValues("KNR_FLG6", Util.avoidNull(this.field.getInputParameter().getManagement6InputFlag()));
			// ���v1
			addSendValues("HM_FLG1", Util.avoidNull(this.field.getInputParameter().getNonAccountingDetail1Flag()));
			// ���v2
			addSendValues("HM_FLG2", Util.avoidNull(this.field.getInputParameter().getNonAccountingDetail2Flag()));
			// ���v3
			addSendValues("HM_FLG3", Util.avoidNull(this.field.getInputParameter().getNonAccountingDetail3Flag()));
			// ����ېœ����׸�
			addSendValues("URI_ZEI_FLG", Util.avoidNull(this.field.getInputParameter().getSalesTaxInputFlag()));
			// �d���ېœ����׸�
			addSendValues("SIR_ZEI_FLG", Util.avoidNull(this.field.getInputParameter().getPurchaseTaxationInputFlag()));
			// ���ʉݓ����׸�
			addSendValues("MCR_FLG", Util.avoidNull(this.field.getInputParameter().getMultipleCurrencyInputFlag()));

			// �T�[�u���b�g�̐ڑ���
			if (!request(TARGET_SERVLET)) {
				throw new TRequestException();
			}

			// �f�[�^�擾
			return getResult();

		} finally {
			if (!isWaitCursor) {
				setDefaultCursor(field);
			}
		}
	}
}
