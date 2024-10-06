package jp.co.ais.trans.common.bizui.ctrl;

import java.awt.*;
import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans2.common.config.*;

/**
 * �Ȗڃt�B�[���h�R���g���[��
 */
public class TItemFieldCtrl extends TAppletClientBase {

	/** �����T�[�u���b�g */
	protected static final String TARGET_SERVLET = "TAccountItemUnitServlet";

	/** �_�C�A���O�̃R�[�h�̏����l�\�����邩 */
	protected boolean showDefaultCode = !ClientConfig.isFlagOn("trans.ref.not.show.default.code");

	/** �t�B�[���h */
	protected TItemField field;

	/** �R�[�h(����) */
	protected String codeLabel = getWord("C00174");

	/**
	 * �R���X�g���N�^
	 * 
	 * @param itemField �t�B�[���h
	 */
	public TItemFieldCtrl(TItemField itemField) {
		try {

			this.field = itemField;

		} catch (Exception e) {
			errorHandler(itemField, e, "E00010");
		}
	}

	// *************************************** �{�^���������̏���

	/**
	 * �Ȗڃ}�X�^��������
	 * 
	 * @return true:���폈��
	 */
	public boolean search() {
		try {
			// �Ȗڃ}�X�^�ꗗ�̏ꍇ
			Container parent = TGuiUtil.getParentFrameOrDialog(field);

			REFDialogMasterCtrl dialog = (parent instanceof Dialog) ? new REFDialogMasterCtrl((Dialog) parent,
				REFDialogMasterCtrl.KMK_MST) : new REFDialogMasterCtrl((Frame) parent, REFDialogMasterCtrl.KMK_MST);

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

			field.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

			boolean isSettled = dialog.isSettle();

			// �m��̏ꍇ
			if (isSettled) {
				String[] info = dialog.getCurrencyInfo();

				// �t�B�[���h�Ɠ���R�[�h���I�΂ꂽ�ꍇ�͏����Ȃ�
				if (field.getValue().equals(info[0]) && !field.getNoticeValue().isEmpty()) {
					return false;
				}

				// �ȖڃR�[�h
				field.setValue(info[0]);
				// �Ȗڗ���
				field.setNoticeValue(info[1]);

				field.getField().pushOldText();

				// �Ȗڏ��ݒ�
				setupInfo();
			}

			return isSettled;

		} catch (Exception e) {
			errorHandler(field, e, "E00009");

			return false;
		} finally {
			field.getField().requestFocus();

			field.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	// *************************************** LostFocus���̏���

	/**
	 * �Ȗڏ��ݒ�
	 * 
	 * @return true:���폈��
	 */
	public boolean setupInfo() {
		try {

			// Output�p�����[�^�̒l���N���A����
			field.getOutputParameter().clear();

			// �ȖڃR�[�h
			String strKmkCode = field.getField().getText();

			if (Util.isNullOrEmpty(strKmkCode)) {
				// �ȖڃR�[�h���̃u�����N�ŃZ�b�g����B
				field.setNoticeValue("");

				return true;
			}

			// �Ȗڃ}�X�^���擾
			buildOutput(strKmkCode);

			AccountItemOutputParameter outparam = field.getOutputParameter();

			// ���̂��Z�b�g
			field.setNoticeValue(outparam.getItemAbbrName());

			// �v�㕔��R�[�h���݂̏ꍇ
			if (!outparam.isExist()) {

				if (field.isCheckMode()) {
					// ���݂��܂���B
					showMessage(field, "W00081", (Object) field.getButtonText() + codeLabel);
					field.requestTextFocus();
					field.clearOldText();
				}

				return !field.isCheckMode();
			}

			return true;

		} catch (TException e) {
			errorHandler(this.field, e, e.getMessageID(), e.getMessageArgs());

			return false;
		} catch (Exception e) {
			errorHandler(this.field);

			return false;
		}
	}

	/**
	 * �����ݒ� Dialog
	 * 
	 * @param dialog
	 * @param companyCode ��ЃR�[�h
	 */
	protected void setCondition(REFDialogMasterCtrl dialog, String companyCode) {

		AccountItemInputParameter inparam = field.getInputParameter();

		// ��ЃR�[�h
		dialog.setKaiCode(Util.avoidNull(companyCode));
		// �`�[���t
		dialog.setSlipDate(Util.avoidNull(inparam.getSlipDate()));
		// BS��������敪
		dialog.setKesiKbn(Util.avoidNull(inparam.getBsAccountErasingDivision()));
		// �W�v�敪
		dialog.setSumKbn(Util.avoidNull(inparam.getSummaryDivision()));
		// �f�k�Ȗڐ���敪
		dialog.setKmkCntGl(Util.avoidNull(inparam.getGlItemCtrlDivision()));
		// AR����敪
		dialog.setkmkCntAr(Util.avoidNull(inparam.getArItemCtrlDivision()));
		// AR����敪(�����p)
		dialog.setkmkCntUnAr(Util.avoidNull(inparam.getUnArItemCtrlDivision()));
		// AP����敪
		dialog.setkmkCntAp(Util.avoidNull(inparam.getApItemCtrlDivision()));
		// ����R�[�h
		dialog.setBmnCode(Util.avoidNull(inparam.getDepartmentCode()));
		// �]���֑Ώۃt���O
		dialog.setExcFlg(Util.avoidNull(inparam.getRevaluationObjectFlag()));
		// �����`�[���̓t���O
		dialog.setNyuKin(Util.avoidNull(inparam.getRecivingSlipInputFlag()));
		// �o���`�[���̓t���O
		dialog.setShutsuKin(Util.avoidNull(inparam.getDrawingSlipInputFlag()));
		// �U�֓`�[���̓t���O
		dialog.setFurikaeFlg(Util.avoidNull(inparam.getTransferSlipInputFlag()));
		// �o��Z�`�[���̓t���O
		dialog.setKeihiFlg(Util.avoidNull(inparam.getExpenseInputFlag()));
		// ���v����̓t���O
		dialog.setSaimuFlg(Util.avoidNull(inparam.getSaimuFlg()));
		// ���v����̓t���O
		dialog.setSaikenFlg(Util.avoidNull(inparam.getSaikenFlg()));
		// �������`�[���̓t���O
		dialog.setSaikesiFlg(Util.avoidNull(inparam.getAccountsRecivableErasingSlipInputFlag()));
		// ���Y�v��`�[���̓t���O
		dialog.setSisanFlg(Util.avoidNull(inparam.getAssetsAppropriatingSlipInputFlag()));
		// �x���˗��`�[���̓t���O
		dialog.setSiharaiFlg(Util.avoidNull(inparam.getPaymentRequestSlipInputFlag()));

		// �Ȗڎ��
		dialog.setKmkShu(Util.avoidNull(inparam.getItemType()));
		// �ݎ؋敪
		dialog.setDcKbn(Util.avoidNull(inparam.getDebitAndCreditDivision()));
		// �⏕�敪
		dialog.setHkmKbn(Util.avoidNull(inparam.getSubItemDivision()));
		// �Œ蕔�庰��
		dialog.setKoteiDepCode(Util.avoidNull(inparam.getFixedDepartment()));
		// ����ź���
		dialog.setZeiCode(Util.avoidNull(inparam.getConsumptionTaxCode()));
		// ���������׸�
		dialog.setTriCodeFlag(Util.avoidNull(inparam.getCustomerInputFlag()));
		// �����������׸�
		dialog.setHasFlg(Util.avoidNull(inparam.getAccrualDateInputFlag()));
		// �Ј������׸�
		dialog.setEmpCodeFlg(Util.avoidNull(inparam.getEmployeeInputFlag()));
		// �Ǘ�1
		dialog.setKnrFlg1(Util.avoidNull(inparam.getManagement1InputFlag()));
		// �Ǘ�2
		dialog.setKnrFlg2(Util.avoidNull(inparam.getManagement2InputFlag()));
		// �Ǘ�3
		dialog.setKnrFlg3(Util.avoidNull(inparam.getManagement3InputFlag()));
		// �Ǘ�4
		dialog.setKnrFlg4(Util.avoidNull(inparam.getManagement4InputFlag()));
		// �Ǘ�5
		dialog.setKnrFlg5(Util.avoidNull(inparam.getManagement5InputFlag()));
		// �Ǘ�6
		dialog.setKnrFlg6(Util.avoidNull(inparam.getManagement6InputFlag()));
		// ���v1
		dialog.setHmFlg1(Util.avoidNull(inparam.getNonAccountingDetail1Flag()));
		// ���v2
		dialog.setHmFlg2(Util.avoidNull(inparam.getNonAccountingDetail2Flag()));
		// ���v3
		dialog.setHmFlg3(Util.avoidNull(inparam.getNonAccountingDetail3Flag()));
		// ����ېœ����׸�
		dialog.setUriZeiFlg(Util.avoidNull(inparam.getSalesTaxInputFlag()));
		// �d���ېœ����׸�
		dialog.setSirZeiFlg(Util.avoidNull(inparam.getPurchaseTaxationInputFlag()));
		// ���ʉݓ����׸�
		dialog.setMcrFlg(Util.avoidNull(inparam.getMultipleCurrencyInputFlag()));
		// BG�Ȗڐ���敪
		dialog.setKmkCntBg(Util.avoidNull(inparam.getBgItemCtrlDivision()));
		// ���E�Ȗڐ���敪
		dialog.setKmkCntSousai(Util.avoidNull(inparam.getCounterbalanceAdjustmentCtrlDivision()));

		// �J�n�R�[�h
		dialog.setBeginCode(Util.avoidNull(inparam.getBeginCode()));
		// �I���R�[�h
		dialog.setEndCode(Util.avoidNull(inparam.getEndCode()));
		// �Ȗڑ̌n�R�[�h
		dialog.setKmtCode(Util.avoidNull(inparam.getItemSystemCode()));
		// �Ȗڑ̌n�t���O
		dialog.setKmtFlg(Util.avoidNull(inparam.getItemSystemFlg()));

	}

	/**
	 * �Ȗڃ}�X�^�f�[�^���������A���\�z
	 * 
	 * @param strKmkCode �ȖڃR�[�h
	 * @throws IOException
	 * @throws TException
	 */
	protected void buildOutput(String strKmkCode) throws IOException, TException {

		// ��ЃR�[�h
		String companyCode;
		if (Util.isNullOrEmpty(field.getInputParameter().getCompanyCode())) {
			companyCode = super.getLoginUserCompanyCode();
		} else {
			companyCode = field.getInputParameter().getCompanyCode();
		}

		AccountItemInputParameter inparam = field.getInputParameter();

		// ���M����p�����[�^��ݒ�
		addSendValues("FLAG", "KMK_MST");
		// ��ЃR�[�h
		addSendValues("KAI_CODE", Util.avoidNull(companyCode));
		// �ȖڃR�[�h
		addSendValues("KMK_CODE", (Util.avoidNull(strKmkCode)));
		// �`�[���t
		addSendValues("SLIP_DATE", Util.avoidNull(inparam.getSlipDate()));
		// BS��������敪
		addSendValues("KESI_KBN", Util.avoidNull(inparam.getBsAccountErasingDivision()));
		// �W�v�敪
		addSendValues("SUM_KBN", Util.avoidNull(inparam.getSummaryDivision()));
		// �f�k�Ȗڐ���敪
		addSendValues("KMK_CNT_GL", Util.avoidNull(inparam.getGlItemCtrlDivision()));
		// AR����敪
		addSendValues("KMK_CNT_AR", Util.avoidNull(inparam.getArItemCtrlDivision()));
		// AR����敪(�����p)
		addSendValues("KMK_CNT_UN_AR", Util.avoidNull(inparam.getUnArItemCtrlDivision()));
		// AP����敪
		addSendValues("KMK_CNT_AP", Util.avoidNull(inparam.getApItemCtrlDivision()));
		// ����R�[�h
		addSendValues("BMN_CODE", Util.avoidNull(inparam.getDepartmentCode()));
		// �]���֑Ώۃt���O
		addSendValues("EXC_FLG", Util.avoidNull(inparam.getRevaluationObjectFlag()));
		// �����`�[���̓t���O
		addSendValues("NYU_KIN", Util.avoidNull(inparam.getRecivingSlipInputFlag()));
		// �o���`�[���̓t���O
		addSendValues("SHUTSU_KIN", Util.avoidNull(inparam.getDrawingSlipInputFlag()));
		// �U�֓`�[���̓t���O
		addSendValues("FURIKAE_FLG", Util.avoidNull(inparam.getTransferSlipInputFlag()));
		// �o��Z�`�[���̓t���O
		addSendValues("KEIHI_FLG", Util.avoidNull(inparam.getExpenseInputFlag()));
		// ���v����̓t���O
		addSendValues("SAIMU_FLG", Util.avoidNull(inparam.getSaimuFlg()));
		// ���v����̓t���O
		addSendValues("SAIKEN_FLG", Util.avoidNull(inparam.getSaikenFlg()));
		// �������`�[���̓t���O
		addSendValues("SAIKESI_FLG", Util.avoidNull(inparam.getAccountsRecivableErasingSlipInputFlag()));
		// ���Y�v��`�[���̓t���O
		addSendValues("SISAN_FLG", Util.avoidNull(inparam.getAssetsAppropriatingSlipInputFlag()));
		// �x���˗��`�[���̓t���O
		addSendValues("SIHARAI_FLG", Util.avoidNull(inparam.getPaymentRequestSlipInputFlag()));

		// �Ȗڎ��
		addSendValues("KMK_SHU", Util.avoidNull(inparam.getItemType()));
		// �ݎ؋敪
		addSendValues("DC_KBN", Util.avoidNull(inparam.getDebitAndCreditDivision()));
		// �⏕�敪
		addSendValues("HKM_KBN", Util.avoidNull(inparam.getSubItemDivision()));
		// �Œ蕔�庰��
		addSendValues("KOTEI_DEP_CODE", Util.avoidNull(inparam.getFixedDepartment()));
		// ����ź���
		addSendValues("ZEI_CODE", Util.avoidNull(inparam.getConsumptionTaxCode()));
		// ���������׸�
		addSendValues("TRI_CODE_FLG", Util.avoidNull(inparam.getCustomerInputFlag()));
		// �����������׸�
		addSendValues("HAS_FLG", Util.avoidNull(inparam.getAccrualDateInputFlag()));
		// �Ј������׸�
		addSendValues("EMP_CODE_FLG", Util.avoidNull(inparam.getEmployeeInputFlag()));
		// �Ǘ�1
		addSendValues("KNR_FLG1", Util.avoidNull(inparam.getManagement1InputFlag()));
		// �Ǘ�2
		addSendValues("KNR_FLG2", Util.avoidNull(inparam.getManagement2InputFlag()));
		// �Ǘ�3
		addSendValues("KNR_FLG3", Util.avoidNull(inparam.getManagement3InputFlag()));
		// �Ǘ�4
		addSendValues("KNR_FLG4", Util.avoidNull(inparam.getManagement4InputFlag()));
		// �Ǘ�5
		addSendValues("KNR_FLG5", Util.avoidNull(inparam.getManagement5InputFlag()));
		// �Ǘ�6
		addSendValues("KNR_FLG6", Util.avoidNull(inparam.getManagement6InputFlag()));
		// ���v1
		addSendValues("HM_FLG1", Util.avoidNull(inparam.getNonAccountingDetail1Flag()));
		// ���v2
		addSendValues("HM_FLG2", Util.avoidNull(inparam.getNonAccountingDetail2Flag()));
		// ���v3
		addSendValues("HM_FLG3", Util.avoidNull(inparam.getNonAccountingDetail3Flag()));
		// ����ېœ����׸�
		addSendValues("URI_ZEI_FLG", Util.avoidNull(inparam.getSalesTaxInputFlag()));
		// �d���ېœ����׸�
		addSendValues("SIR_ZEI_FLG", Util.avoidNull(inparam.getPurchaseTaxationInputFlag()));
		// ���ʉݓ����׸�
		addSendValues("MCR_FLG", Util.avoidNull(inparam.getMultipleCurrencyInputFlag()));
		// BG�Ȗڐ���敪
		addSendValues("KMK_CNT_BG", Util.avoidNull(inparam.getBgItemCtrlDivision()));
		// ���E�Ȗڐ���敪
		addSendValues("KMK_CNT_SOUSAI", Util.avoidNull(inparam.getCounterbalanceAdjustmentCtrlDivision()));
		// �Ȗڑ̌n�R�[�h
		addSendValues("KMK_TK_CODE", Util.avoidNull(inparam.getItemSystemCode()));
		// �Ȗڑ̌n�t���O
		addSendValues("KMK_TK_FLG", Util.avoidNull(inparam.getItemSystemFlg()));

		// �T�[�u���b�g�̐ڑ���
		if (!request(TARGET_SERVLET)) {
			throw new TRequestException();
		}

		Map<String, String> map = getResult();

		// �擾�����l��OutputParameter�ɃZ�b�g
		setResultData(map);
	}

	/**
	 * �擾�����l��Output�p�����[�^�ɃZ�b�g
	 * 
	 * @param resultMap �擾�����f�[�^
	 */
	protected void setResultData(Map resultMap) {

		AccountItemOutputParameter outparam = field.getOutputParameter();

		// �w��R�[�h�̑��݃`�F�b�N
		outparam.setExist(!"0".equals(resultMap.get("existFlag")));

		// �Ȗڗ���
		outparam.setItemAbbrName(Util.avoidNull(resultMap.get("KMK_NAME_S")));
		// �⏕�敪
		outparam.setSubItemFlag(Util.avoidNull(resultMap.get("HKM_KBN")));
		// ����ېœ��̓t���O
		outparam.setSalesTaxInputFlag(Util.avoidNull(resultMap.get("URI_ZEI_FLG")));
		// �d���ېœ��̓t���O
		outparam.setPurchaseTaxationInputFlag(Util.avoidNull(resultMap.get("SIR_ZEI_FLG")));
		// ����ŃR�[�h
		outparam.setConsumptionTaxCode(Util.avoidNull(resultMap.get("ZEI_CODE")));
		// ����ŋ敪
		outparam.setConsumptionTaxDivision(Util.avoidNull(resultMap.get("SZEI_KEI_KBN")));
		// ����ŗ���
		outparam.setConsumptionTaxAbbrName(Util.avoidNull(resultMap.get("ZEI_NAME_S")));
		// �������̓t���O
		outparam.setCustomerInputFlag(Util.avoidNull(resultMap.get("TRI_CODE_FLG")));
		// �Ј��R�[�h
		outparam.setEmployeeCode(Util.avoidNull(resultMap.get("EMP_CODE")));
		// �Ј�����
		outparam.setEmployeeAbbrName(Util.avoidNull(resultMap.get("EMP_NAME_S")));
		// �Ј����̓t���O
		outparam.setEmployeeInputFlag(Util.avoidNull(resultMap.get("EMP_CODE_FLG")));
		// �Ǘ�1���̓t���O
		outparam.setManagement1InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_1")));
		// �Ǘ�2���̓t���O
		outparam.setManagement2InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_2")));
		// �Ǘ�3���̓t���O
		outparam.setManagement3InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_3")));
		// �Ǘ�4���̓t���O
		outparam.setManagement4InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_4")));
		// �Ǘ�5���̓t���O
		outparam.setManagement5InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_5")));
		// �Ǘ�6���̓t���O
		outparam.setManagement6InputFlag(Util.avoidNull(resultMap.get("KNR_FLG_6")));
		// ���v����1���̓t���O
		outparam.setNonAccountingDetail1Flag(Util.avoidNull(resultMap.get("HM_FLG_1")));
		// ���v����2���̓t���O
		outparam.setNonAccountingDetail2Flag(Util.avoidNull(resultMap.get("HM_FLG_2")));
		// ���v����3���̓t���O
		outparam.setNonAccountingDetail3Flag(Util.avoidNull(resultMap.get("HM_FLG_3")));
		// ���������̓t���O
		outparam.setAccrualDateInputFlag(Util.avoidNull(resultMap.get("HAS_FLG")));
		// ���ʉݓ��̓t���O
		outparam.setMultipleCurrencyInputFlag(Util.avoidNull(resultMap.get("MCR_FLG")));
		// GL�Ȗڐ���敪
		outparam.setKmkCntGl(Util.avoidNull(resultMap.get("KMK_CNT_GL")));
		// AP�Ȗڐ���敪
		outparam.setKmkCntAp(Util.avoidNull(resultMap.get("KMK_CNT_AP")));
		// AR�Ȗڐ���敪
		outparam.setKmkCntAr(Util.avoidNull(resultMap.get("KMK_CNT_AR")));

	}
}
