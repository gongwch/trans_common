package jp.co.ais.trans.common.server.servlet;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.server.di.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���ʃT�[�u���b�g
 */
public class TAccountItemUnitServlet extends TServletBase {

	/** UID */
	private static final long serialVersionUID = -5319125982776071994L;

	/**
	 * ���N�G�X�g����������
	 * 
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 * @throws ServletException
	 */
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

		try {
			// ����pflag
			String flag = req.getParameter("FLAG");

			// �Ȗڌ���
			if ("KMK_MST".equals(flag)) {
				findKmkMst(req, resp);
			}
			// �⏕�Ȗڌ���
			else if ("HKM_MST".equals(flag)) {
				findHkmMst(req, resp);
			}
			// ����Ȗڌ���
			else if ("UKM_MST".equals(flag)) {
				findUkmMst(req, resp);
			}

		} catch (Exception e) {
			handledException(e, req, resp);
		}
	}

	/**
	 * �Ȗڃ}�X�^�̃f�[�^���擾����
	 * 
	 * @param req
	 * @param resp
	 */
	private void findKmkMst(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// �����̃p�����[�^��ݒ肷��B// �����̃p�����[�^��ݒ肷��B
			TUserInfo userInfo = refTServerUserInfo(req);
			String strEmpCode = userInfo.getEmployerCode(); // ���O�C�����[�U�[�Ј��R�[�h

			// ��ЃR�[�h
			String strKaiCode = req.getParameter("KAI_CODE");
			// �ȖڃR�[�h
			String strKmkCode = req.getParameter("KMK_CODE");
			// �`�[���t
			String strSlipDate = req.getParameter("SLIP_DATE");
			// BS��������敪
			String strKesiKbn = req.getParameter("KESI_KBN");
			// �W�v�敪
			String strSumKbn = req.getParameter("SUM_KBN");
			// �f�k�Ȗڐ���敪
			String strKmkCntGl = req.getParameter("KMK_CNT_GL");
			// ����R�[�h
			String strBmnCode = req.getParameter("BMN_CODE");
			// �]���֑Ώۃt���O
			String strExcFlg = req.getParameter("EXC_FLG");
			// �����`�[���̓t���O
			String strNyuKin = req.getParameter("NYU_KIN");
			// �o���`�[���̓t���O
			String strShutsuKin = req.getParameter("SHUTSU_KIN");
			// �U�֓`�[���̓t���O
			String strFurikaeFlg = req.getParameter("FURIKAE_FLG");
			// �o��Z�`�[���̓t���O
			String strKeihiFlg = Util.avoidNull(req.getParameter("KEIHI_FLG"));
			// ���v����̓t���O
			String strSaimuFlg = Util.avoidNull(req.getParameter("SAIMU_FLG"));
			// ���v����̓t���O
			String strSaikenFlg = Util.avoidNull(req.getParameter("SAIKEN_FLG"));
			// �������`�[���̓t���O
			String strSaikesiFlg = Util.avoidNull(req.getParameter("SAIKESI_FLG"));
			// ���Y�v��`�[���̓t���O
			String strSisanFlg = Util.avoidNull(req.getParameter("SISAN_FLG"));
			// �x���˗��`�[���̓t���O
			String strSiharaiFlg = Util.avoidNull(req.getParameter("SIHARAI_FLG"));
			// AR����敪
			String strKmkCntAr = Util.avoidNull(req.getParameter("KMK_CNT_AR"));
			// AR����敪(�����p)
			String strKmkCntUnAr = Util.avoidNull(req.getParameter("KMK_CNT_UN_AR"));
			// AP����敪
			String strKmkCntAp = Util.avoidNull(req.getParameter("KMK_CNT_AP"));

			// �Ȗڎ��
			String strKmkShu = Util.avoidNull(req.getParameter("KMK_SHU"));
			// �ݎ؋敪
			String strDcKbn = Util.avoidNull(req.getParameter("DC_KBN"));
			// �⏕�敪
			String strHkmKbn = Util.avoidNull(req.getParameter("HKM_KBN"));
			// �Œ蕔�庰��
			String strKoteiDepCode = Util.avoidNull(req.getParameter("KOTEI_DEP_CODE"));
			// ����ź���
			String strZeiCode = Util.avoidNull(req.getParameter("ZEI_CODE"));
			// ���������׸�
			String strTriCodeFlg = Util.avoidNull(req.getParameter("TRI_CODE_FLG"));
			// �����������׸�
			String strHasFlg = Util.avoidNull(req.getParameter("HAS_FLG"));
			// �Ј������׸�
			String strEmpCodeFlg = Util.avoidNull(req.getParameter("EMP_CODE_FLG"));
			// �Ǘ�1
			String strKnrFlg1 = Util.avoidNull(req.getParameter("KNR_FLG1"));
			// �Ǘ�2
			String strKnrFlg2 = Util.avoidNull(req.getParameter("KNR_FLG2"));
			// �Ǘ�3
			String strKnrFlg3 = Util.avoidNull(req.getParameter("KNR_FLG3"));
			// �Ǘ�4
			String strKnrFlg4 = Util.avoidNull(req.getParameter("KNR_FLG4"));
			// �Ǘ�5
			String strKnrFlg5 = Util.avoidNull(req.getParameter("KNR_FLG5"));
			// �Ǘ�6
			String strKnrFlg6 = Util.avoidNull(req.getParameter("KNR_FLG6"));
			// ���v1
			String strHmFlg1 = Util.avoidNull(req.getParameter("HM_FLG1"));
			// ���v2
			String strHmFlg2 = Util.avoidNull(req.getParameter("HM_FLG2"));
			// ���v3
			String strHmFlg3 = Util.avoidNull(req.getParameter("HM_FLG3"));
			// ����ېœ����׸�
			String strUriZeiFlg = Util.avoidNull(req.getParameter("URI_ZEI_FLG"));
			// �d���ېœ����׸�
			String strSirZeiFlg = Util.avoidNull(req.getParameter("SIR_ZEI_FLG"));
			// ���ʉݓ����׸�
			String strMcrFlg = Util.avoidNull(req.getParameter("MCR_FLG"));
			// BG�Ȗڐ���敪
			String strKmkCntBg = Util.avoidNull(req.getParameter("KMK_CNT_BG"));
			// ���E�Ȗڐ���敪
			String strKmkCntSousai = Util.avoidNull(req.getParameter("KMK_CNT_SOUSAI"));
			// �Ȗڑ̌n�R�[�h
			String kmkSystemCode = Util.avoidNull(req.getParameter("KMK_TK_CODE"));
			// �Ȗڑ̌n�t���O
			String kmkSystemFlg = Util.avoidNull(req.getParameter("KMK_TK_FLG"));

			// �p�����[�^�[��ݒ肷��
			AccountItemInputParameter inputParameter = new AccountItemInputParameter();
			inputParameter.setCompanyCode(strKaiCode); // ��к���
			inputParameter.setEmployeeCode(strEmpCode); // �Ј�����
			inputParameter.setItemCode(strKmkCode); // �Ȗں���
			inputParameter.setSlipDate(strSlipDate); // �`�[���t
			inputParameter.setBsAccountErasingDivision(strKesiKbn); // BS��������敪
			inputParameter.setSummaryDivision(strSumKbn); // �W�v�敪
			inputParameter.setGlItemCtrlDivision(strKmkCntGl); // GL�Ȗڐ���敪
			inputParameter.setDepartmentCode(strBmnCode); // ���庰��
			inputParameter.setRevaluationObjectFlag(strExcFlg); // �]���֑Ώۃt���O
			inputParameter.setRecivingSlipInputFlag(strNyuKin); // ���o���`�[���̓t���O
			inputParameter.setDrawingSlipInputFlag(strShutsuKin); // �o���`�[���̓t���O
			inputParameter.setTransferSlipInputFlag(strFurikaeFlg); // �U�֓`�[���̓t���O
			inputParameter.setExpenseInputFlag(strKeihiFlg); // �o��Z�`�[���̓t���O
			inputParameter.setSaimuFlg(strSaimuFlg); // ���v����̓t���O
			inputParameter.setSaikenFlg(strSaikenFlg); // ���v����̓t���O
			inputParameter.setAccountsRecivableErasingSlipInputFlag(strSaikesiFlg); // �������`�[���̓t���O
			inputParameter.setAssetsAppropriatingSlipInputFlag(strSisanFlg); // ���Y�v��`�[���̓t���O
			inputParameter.setPaymentRequestSlipInputFlag(strSiharaiFlg); // �x���˗��`�[���̓t���O
			inputParameter.setArItemCtrlDivision(strKmkCntAr); // AR�Ȗڐ���敪
			inputParameter.setUnArItemCtrlDivision(strKmkCntUnAr); // AR����敪(�����p)
			inputParameter.setApItemCtrlDivision(strKmkCntAp); // AP�Ȗڐ���敪

			inputParameter.setItemType(strKmkShu); // �Ȗڎ��
			inputParameter.setDebitAndCreditDivision(strDcKbn); // �ݎ؋敪
			inputParameter.setSubItemDivision(strHkmKbn); // �⏕�敪
			inputParameter.setFixedDepartment(strKoteiDepCode); // �Œ蕔�庰��
			inputParameter.setConsumptionTaxCode(strZeiCode); // ����ź���
			inputParameter.setCustomerInputFlag(strTriCodeFlg); // ���������׸�
			inputParameter.setAccrualDateInputFlag(strHasFlg); // �����������׸�
			inputParameter.setEmployeeInputFlag(strEmpCodeFlg); // �Ј������׸�
			inputParameter.setManagement1InputFlag(strKnrFlg1); // �Ǘ�1
			inputParameter.setManagement2InputFlag(strKnrFlg2); // �Ǘ�2
			inputParameter.setManagement3InputFlag(strKnrFlg3); // �Ǘ�3
			inputParameter.setManagement4InputFlag(strKnrFlg4); // �Ǘ�4
			inputParameter.setManagement5InputFlag(strKnrFlg5); // �Ǘ�5
			inputParameter.setManagement6InputFlag(strKnrFlg6); // �Ǘ�6
			inputParameter.setNonAccountingDetail1Flag(strHmFlg1); // ���v1
			inputParameter.setNonAccountingDetail2Flag(strHmFlg2); // ���v2
			inputParameter.setNonAccountingDetail3Flag(strHmFlg3); // ���v3
			inputParameter.setSalesTaxInputFlag(strUriZeiFlg); // ����ېœ����׸�
			inputParameter.setPurchaseTaxationInputFlag(strSirZeiFlg); // �d���ېœ����׸�
			inputParameter.setMultipleCurrencyInputFlag(strMcrFlg); // ���ʉݓ����׸�
			inputParameter.setBgItemCtrlDivision(strKmkCntBg); // BG�Ȗڐ���敪
			inputParameter.setCounterbalanceAdjustmentCtrlDivision(strKmkCntSousai); // ���E�Ȗڐ���敪
			inputParameter.setItemSystemCode(kmkSystemCode); // �Ȗڑ̌n�R�[�h
			inputParameter.setItemSystemFlg(kmkSystemFlg); // �Ȗڑ̌n�t���O

			S2Container container = SingletonS2ContainerFactory.getContainer();
			TAccountItemUnitLogic logic = (TAccountItemUnitLogic) container.getComponent(TAccountItemUnitLogic.class);

			// �Ȗڂ�����
			Map<String, Object> map = logic.getItemInfo(inputParameter);

			dispatchResultMap(req, resp, map);
		} catch (TException e) {
			dispatchError(req, resp, e);
		}
	}

	/**
	 * �⏕�Ȗڃ}�X�^�̃f�[�^���擾����
	 * 
	 * @param req
	 * @param resp
	 * @throws TException
	 */
	private void findHkmMst(HttpServletRequest req, HttpServletResponse resp) throws TException {

		// �����̃p�����[�^��ݒ肷��B// �����̃p�����[�^��ݒ肷��B
		TUserInfo userInfo = refTServerUserInfo(req);
		String strEmpCode = userInfo.getEmployerCode(); // ���O�C�����[�U�[�Ј��R�[�h

		// ��ЃR�[�h
		String strKaiCode = req.getParameter("KAI_CODE");
		// �ȖڃR�[�h
		String strKmkCode = req.getParameter("KMK_CODE");
		// �⏕�ȖڃR�[�h
		String strHkmCode = req.getParameter("HKM_CODE");
		// �`�[���t
		String strSlipDate = req.getParameter("SLIP_DATE");
		// �����`�[���̓t���O
		String strNyuKin = req.getParameter("NYU_KIN");
		// �o���`�[���̓t���O
		String strShutsuKin = req.getParameter("SHUTSU_KIN");
		// �U�֓`�[���̓t���O
		String strFurikaeFlg = req.getParameter("FURIKAE_FLG");
		// �o��Z�`�[���̓t���O
		String strKeihiFlg = Util.avoidNull(req.getParameter("KEIHI_FLG"));
		// ���v����̓t���O
		String strSaimuFlg = Util.avoidNull(req.getParameter("SAIMU_FLG"));
		// ���v����̓t���O
		String strSaikenFlg = Util.avoidNull(req.getParameter("SAIKEN_FLG"));
		// �������`�[���̓t���O
		String strSaikesiFlg = Util.avoidNull(req.getParameter("SAIKESI_FLG"));
		// ���Y�v��`�[���̓t���O
		String strSisanFlg = Util.avoidNull(req.getParameter("SISAN_FLG"));
		// �x���˗��`�[���̓t���O
		String strSiharaiFlg = Util.avoidNull(req.getParameter("SIHARAI_FLG"));

		// �]���֑Ώۃt���O
		String strExcFlg = req.getParameter("EXC_FLG");
		// ����敪
		String strUkmKbn = req.getParameter("UKM_KBN");
		// ����ź���
		String strZeiCode = Util.avoidNull(req.getParameter("ZEI_CODE"));
		// ���������׸�
		String strTriCodeFlg = Util.avoidNull(req.getParameter("TRI_CODE_FLG"));
		// �����������׸�
		String strHasFlg = Util.avoidNull(req.getParameter("HAS_FLG"));
		// �Ј������׸�
		String strEmpCodeFlg = Util.avoidNull(req.getParameter("EMP_CODE_FLG"));
		// �Ǘ�1
		String strKnrFlg1 = Util.avoidNull(req.getParameter("KNR_FLG1"));
		// �Ǘ�2
		String strKnrFlg2 = Util.avoidNull(req.getParameter("KNR_FLG2"));
		// �Ǘ�3
		String strKnrFlg3 = Util.avoidNull(req.getParameter("KNR_FLG3"));
		// �Ǘ�4
		String strKnrFlg4 = Util.avoidNull(req.getParameter("KNR_FLG4"));
		// �Ǘ�5
		String strKnrFlg5 = Util.avoidNull(req.getParameter("KNR_FLG5"));
		// �Ǘ�6
		String strKnrFlg6 = Util.avoidNull(req.getParameter("KNR_FLG6"));
		// ���v1
		String strHmFlg1 = Util.avoidNull(req.getParameter("HM_FLG1"));
		// ���v2
		String strHmFlg2 = Util.avoidNull(req.getParameter("HM_FLG2"));
		// ���v3
		String strHmFlg3 = Util.avoidNull(req.getParameter("HM_FLG3"));
		// ����ېœ����׸�
		String strUriZeiFlg = Util.avoidNull(req.getParameter("URI_ZEI_FLG"));
		// �d���ېœ����׸�
		String strSirZeiFlg = Util.avoidNull(req.getParameter("SIR_ZEI_FLG"));
		// ���ʉݓ����׸�
		String strMcrFlg = Util.avoidNull(req.getParameter("MCR_FLG"));

		// �p�����[�^�[��ݒ肷��
		AccountItemInputParameter inputParameter = new AccountItemInputParameter();
		inputParameter.setCompanyCode(strKaiCode); // ��к���
		inputParameter.setEmployeeCode(strEmpCode); // �Ј�����
		inputParameter.setItemCode(strKmkCode); // �Ȗں���
		inputParameter.setSubItemCode(strHkmCode); // �⏕�Ȗں���
		inputParameter.setSlipDate(strSlipDate); // �`�[���t
		inputParameter.setRecivingSlipInputFlag(strNyuKin); // ���o���`�[���̓t���O
		inputParameter.setDrawingSlipInputFlag(strShutsuKin); // �o���`�[���̓t���O
		inputParameter.setTransferSlipInputFlag(strFurikaeFlg); // �U�֓`�[���̓t���O
		inputParameter.setExpenseInputFlag(strKeihiFlg); // �o��Z�`�[���̓t���O
		inputParameter.setSaimuFlg(strSaimuFlg); // ���v����̓t���O
		inputParameter.setSaikenFlg(strSaikenFlg); // ���v����̓t���O
		inputParameter.setAccountsRecivableErasingSlipInputFlag(strSaikesiFlg); // �������`�[���̓t���O
		inputParameter.setAssetsAppropriatingSlipInputFlag(strSisanFlg); // ���Y�v��`�[���̓t���O
		inputParameter.setPaymentRequestSlipInputFlag(strSiharaiFlg); // �x���˗��`�[���̓t���O

		inputParameter.setRevaluationObjectFlag(strExcFlg); // �]���֑Ώۃt���O
		inputParameter.setBreakDownItemDivision(strUkmKbn); // ����敪
		inputParameter.setConsumptionTaxCode(strZeiCode); // ����ź���
		inputParameter.setCustomerInputFlag(strTriCodeFlg); // ���������׸�
		inputParameter.setAccrualDateInputFlag(strHasFlg); // �����������׸�
		inputParameter.setEmployeeInputFlag(strEmpCodeFlg); // �Ј������׸�
		inputParameter.setManagement1InputFlag(strKnrFlg1); // �Ǘ�1
		inputParameter.setManagement2InputFlag(strKnrFlg2); // �Ǘ�2
		inputParameter.setManagement3InputFlag(strKnrFlg3); // �Ǘ�3
		inputParameter.setManagement4InputFlag(strKnrFlg4); // �Ǘ�4
		inputParameter.setManagement5InputFlag(strKnrFlg5); // �Ǘ�5
		inputParameter.setManagement6InputFlag(strKnrFlg6); // �Ǘ�6
		inputParameter.setNonAccountingDetail1Flag(strHmFlg1); // ���v1
		inputParameter.setNonAccountingDetail2Flag(strHmFlg2); // ���v2
		inputParameter.setNonAccountingDetail3Flag(strHmFlg3); // ���v3
		inputParameter.setSalesTaxInputFlag(strUriZeiFlg); // ����ېœ����׸�
		inputParameter.setPurchaseTaxationInputFlag(strSirZeiFlg); // �d���ېœ����׸�
		inputParameter.setMultipleCurrencyInputFlag(strMcrFlg); // ���ʉݓ����׸�

		S2Container container = SingletonS2ContainerFactory.getContainer();
		TAccountItemUnitLogic logic = (TAccountItemUnitLogic) container.getComponent(TAccountItemUnitLogic.class);

		// �⏕�Ȗڂ�����
		Map<String, Object> map = logic.getSubItemInfo(inputParameter);

		dispatchResultMap(req, resp, map);
	}

	/**
	 * ����Ȗڃ}�X�^�̃f�[�^���擾����
	 * 
	 * @param req
	 * @param resp
	 * @throws TException
	 */
	private void findUkmMst(HttpServletRequest req, HttpServletResponse resp) throws TException {

		// �����̃p�����[�^��ݒ肷��
		TUserInfo userInfo = refTServerUserInfo(req);
		String strEmpCode = userInfo.getEmployerCode(); // ���O�C�����[�U�[�Ј��R�[�h

		// ��ЃR�[�h
		String strKaiCode = req.getParameter("KAI_CODE");
		// �ȖڃR�[�h
		String strKmkCode = req.getParameter("KMK_CODE");
		// �⏕�ȖڃR�[�h
		String strHkmCode = req.getParameter("HKM_CODE");
		// ����ȖڃR�[�h
		String strUkmCode = req.getParameter("UKM_CODE");
		// �`�[���t
		String strSlipDate = req.getParameter("SLIP_DATE");
		// �����`�[���̓t���O
		String strNyuKin = req.getParameter("NYU_KIN");
		// �o���`�[���̓t���O
		String strShutsuKin = req.getParameter("SHUTSU_KIN");
		// �U�֓`�[���̓t���O
		String strFurikaeFlg = req.getParameter("FURIKAE_FLG");
		// �o��Z�`�[���̓t���O
		String strKeihiFlg = Util.avoidNull(req.getParameter("KEIHI_FLG"));
		// ���v����̓t���O
		String strSaimuFlg = Util.avoidNull(req.getParameter("SAIMU_FLG"));
		// ���v����̓t���O
		String strSaikenFlg = Util.avoidNull(req.getParameter("SAIKEN_FLG"));
		// �������`�[���̓t���O
		String strSaikesiFlg = Util.avoidNull(req.getParameter("SAIKESI_FLG"));
		// ���Y�v��`�[���̓t���O
		String strSisanFlg = Util.avoidNull(req.getParameter("SISAN_FLG"));
		// �x���˗��`�[���̓t���O
		String strSiharaiFlg = Util.avoidNull(req.getParameter("SIHARAI_FLG"));

		// �]���֑Ώۃt���O
		String strExcFlg = req.getParameter("EXC_FLG");
		// ����ź���
		String strZeiCode = Util.avoidNull(req.getParameter("ZEI_CODE"));
		// ���������׸�
		String strTriCodeFlg = Util.avoidNull(req.getParameter("TRI_CODE_FLG"));
		// �����������׸�
		String strHasFlg = Util.avoidNull(req.getParameter("HAS_FLG"));
		// �Ј������׸�
		String strEmpCodeFlg = Util.avoidNull(req.getParameter("EMP_CODE_FLG"));
		// �Ǘ�1
		String strKnrFlg1 = Util.avoidNull(req.getParameter("KNR_FLG1"));
		// �Ǘ�2
		String strKnrFlg2 = Util.avoidNull(req.getParameter("KNR_FLG2"));
		// �Ǘ�3
		String strKnrFlg3 = Util.avoidNull(req.getParameter("KNR_FLG3"));
		// �Ǘ�4
		String strKnrFlg4 = Util.avoidNull(req.getParameter("KNR_FLG4"));
		// �Ǘ�5
		String strKnrFlg5 = Util.avoidNull(req.getParameter("KNR_FLG5"));
		// �Ǘ�6
		String strKnrFlg6 = Util.avoidNull(req.getParameter("KNR_FLG6"));
		// ���v1
		String strHmFlg1 = Util.avoidNull(req.getParameter("HM_FLG1"));
		// ���v2
		String strHmFlg2 = Util.avoidNull(req.getParameter("HM_FLG2"));
		// ���v3
		String strHmFlg3 = Util.avoidNull(req.getParameter("HM_FLG3"));
		// ����ېœ����׸�
		String strUriZeiFlg = Util.avoidNull(req.getParameter("URI_ZEI_FLG"));
		// �d���ېœ����׸�
		String strSirZeiFlg = Util.avoidNull(req.getParameter("SIR_ZEI_FLG"));
		// ���ʉݓ����׸�
		String strMcrFlg = Util.avoidNull(req.getParameter("MCR_FLG"));

		// �p�����[�^�[��ݒ肷��
		AccountItemInputParameter inputParameter = new AccountItemInputParameter();
		inputParameter.setCompanyCode(strKaiCode); // ��к���
		inputParameter.setEmployeeCode(strEmpCode); // �Ј�����
		inputParameter.setItemCode(strKmkCode); // �Ȗں���
		inputParameter.setSubItemCode(strHkmCode); // �⏕�Ȗں���
		inputParameter.setBreakDownItemCode(strUkmCode); // ����Ȗں���
		inputParameter.setSlipDate(strSlipDate); // �`�[���t
		inputParameter.setRecivingSlipInputFlag(strNyuKin); // ���o���`�[���̓t���O
		inputParameter.setDrawingSlipInputFlag(strShutsuKin); // �o���`�[���̓t���O
		inputParameter.setTransferSlipInputFlag(strFurikaeFlg); // �U�֓`�[���̓t���O
		inputParameter.setExpenseInputFlag(strKeihiFlg); // �o��Z�`�[���̓t���O
		inputParameter.setSaimuFlg(strSaimuFlg); // ���v����̓t���O
		inputParameter.setSaikenFlg(strSaikenFlg); // ���v����̓t���O
		inputParameter.setAccountsRecivableErasingSlipInputFlag(strSaikesiFlg); // �������`�[���̓t���O
		inputParameter.setAssetsAppropriatingSlipInputFlag(strSisanFlg); // ���Y�v��`�[���̓t���O
		inputParameter.setPaymentRequestSlipInputFlag(strSiharaiFlg); // �x���˗��`�[���̓t���O

		inputParameter.setRevaluationObjectFlag(strExcFlg); // �]���֑Ώۃt���O
		inputParameter.setConsumptionTaxCode(strZeiCode); // ����ź���
		inputParameter.setCustomerInputFlag(strTriCodeFlg); // ���������׸�
		inputParameter.setAccrualDateInputFlag(strHasFlg); // �����������׸�
		inputParameter.setEmployeeInputFlag(strEmpCodeFlg); // �Ј������׸�
		inputParameter.setManagement1InputFlag(strKnrFlg1); // �Ǘ�1
		inputParameter.setManagement2InputFlag(strKnrFlg2); // �Ǘ�2
		inputParameter.setManagement3InputFlag(strKnrFlg3); // �Ǘ�3
		inputParameter.setManagement4InputFlag(strKnrFlg4); // �Ǘ�4
		inputParameter.setManagement5InputFlag(strKnrFlg5); // �Ǘ�5
		inputParameter.setManagement6InputFlag(strKnrFlg6); // �Ǘ�6
		inputParameter.setNonAccountingDetail1Flag(strHmFlg1); // ���v1
		inputParameter.setNonAccountingDetail2Flag(strHmFlg2); // ���v2
		inputParameter.setNonAccountingDetail3Flag(strHmFlg3); // ���v3
		inputParameter.setSalesTaxInputFlag(strUriZeiFlg); // ����ېœ����׸�
		inputParameter.setPurchaseTaxationInputFlag(strSirZeiFlg); // �d���ېœ����׸�
		inputParameter.setMultipleCurrencyInputFlag(strMcrFlg); // ���ʉݓ����׸�

		S2Container container = SingletonS2ContainerFactory.getContainer();
		TAccountItemUnitLogic logic = (TAccountItemUnitLogic) container.getComponent(TAccountItemUnitLogic.class);

		// ����Ȗڂ�����
		Map<String, Object> map = logic.getBreakDownItemInfo(inputParameter);

		dispatchResultMap(req, resp, map);
	}
}
