package jp.co.ais.trans.master.common.servlet;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

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
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �����_�C�A���O�N���X<BR>
 * ���ʌ����_�C�A���O��ʂ̃T�[�u���b�g
 * <p>
 * 
 * @author Yit
 */
public class REFDialogMasterServlet extends TServletBase {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * doPost()���\�b�h��POST�`���ő��M���ꂽ�f�[�^������
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @throws ServletException �ُ�
	 */
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

		// ����pflag
		String strFlag = req.getParameter("FLAG");

		try {
			// ����
			if ("BMN_MST".equals(strFlag)) {
				// ����}�X�^�ꗗ
				searchBmnMstData(req, resp);
			} else if ("KMK_MST".equals(strFlag)) {
				// �Ȗڌ���
				searchKmkMstData(req, resp);
			} else if ("HKM_MST".equals(strFlag)) {
				// �⏕�Ȗڃ}�X�^�ꗗ
				searchHkmMstData(req, resp);
			} else if ("UKM_MST".equals(strFlag)) {
				// ����Ȗڃ}�X�^�ꗗ����
				searchUkmMstData(req, resp);
			} else if ("ENV_MST".equals(strFlag)) {
				// ���ݒ�}�X�^�ꗗ����
				searchEnvMstData(req, resp);
			} else if ("BankAccount".equals(strFlag)) {
				// ��s�����}�X�^�ꗗ����
				searchModifyReservationBankAccount(req, resp);
			} else if ("BankAccountB".equals(strFlag)) {
				// ��s�����}�X�^�ꗗ����(��s���ɋ�s�}�X�^.��s��+��s�}�X�^.�x�X��)
				searchModifyReservationBankAccountB(req, resp);
			} else if ("refCustomer".equals(strFlag)) {
				// �����}�X�^�ꗗ����
				searchRefCustomer(req, resp);
			} else if ("refCurrency".equals(strFlag)) {
				// �ʉ݃}�X�^�ꗗ����
				searchRefCurrency(req, resp);
			} else if ("refDepartment".equals(strFlag)) {
				// ����}�X�^�ꗗ����
				searchRefDepartment(req, resp);
			} else if ("refMemo".equals(strFlag)) {
				// �E�v�}�X�^�ꗗ�����i�s�E�v�A�`�[�E�v���ʁj
				searchRefMemo(req, resp);
			} else if ("refTax".equals(strFlag)) {
				// ����Ń}�X�^�ꗗ����
				searchRefTax(req, resp);
			} else if ("refEmployee".equals(strFlag)) {
				// �Ј������}�X�^�ꗗ����
				searchRefEmployee(req, resp);
			} else if ("refPayment".equals(strFlag)) {
				// �x�������@�}�X�^�ꗗ����
				searchRefPayment(req, resp);
			} else if ("refPaymentCondition".equals(strFlag)) {
				// �x�������}�X�^�ꗗ����
				searchRefPaymentCon(req, resp);
			} else if ("EMP_MST".equals(strFlag)) {
				// �Ј��}�X�^�ꗗ
				searchEmpMstData(req, resp);
			} else if ("KNR_MST".equals(strFlag)) {
				// �Ǘ��}�X�^�ꗗ
				searchKnrMstData(req, resp);
			} else if ("KMT_MST".equals(strFlag)) {
				// �Ȗڑ̌n�}�X�^�ꗗ
				searchKmkTkMstData(req, resp);
			} else if ("APP_KAI".equals(strFlag)) {
				// �v���Ќ���
				searchRefCompany(req, resp);
			} else if ("BNK_BNK_MST".equals(strFlag)) {
				// ��s����
				searchBMKMstData(req, resp);
			} else if ("BNK_STN_MST".equals(strFlag)) {
				// ��s�x�X����
				searchBMKSTNMstData(req, resp);
			}

		} catch (Exception e) {
			super.handledException(e, req, resp);
		}
	}

	/**
	 * ����}�X�^����
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	private void searchBmnMstData(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		DepartmentLogic logic = (DepartmentLogic) container.getComponent(DepartmentLogic.class);

		// ��ЃR�[�h
		String strKaiCode = req.getParameter("KAI_CODE");
		// ����R�[�h
		String strDepCode = req.getParameter("DEP_CODE");
		// ���嗪��
		String strDepName = req.getParameter("DEP_NAME");
		// ���匟������
		String strDepNameK = req.getParameter("DEP_NAME_K");
		// �`�[���t
		String strSlipDate = req.getParameter("SLIP_DATE");
		// �g�D�R�[�h
		String strDpkSsk = req.getParameter("DPK_SSK");
		// �z������(0:�܂� 1:�܂܂Ȃ�)
		String strBmnKbn = req.getParameter("BMN_KBN");
		// ��ʕ��庰��
		String strUpBmnCode = req.getParameter("UP_BMN_CODE");
		// �K�w����
		String strDpkLvl = req.getParameter("DPK_LVL");

		// �������庰��
		String strBaseBmnCode = req.getParameter("BASE_BMN_CODE");
		// �����K�w����
		String strBaseDpkLvl = req.getParameter("BASE_DPK_LVL");

		// Like�𗘗p���邽��
		strDepCode = DBUtil.getLikeStatement(strDepCode, DBUtil.NORMAL_CHAR);
		strDepName = DBUtil.getLikeStatement(strDepName, DBUtil.UNICODE_CHAR);
		strDepNameK = DBUtil.getLikeStatement(strDepNameK, DBUtil.UNICODE_CHAR);

		List list = null;
		// ���ʂ��擾����B
		list = logic.searchBmnMstData(strKaiCode, strDepCode, strDepName, strDepNameK, strSlipDate, strDpkSsk,
			strBmnKbn, strUpBmnCode, strDpkLvl, strBaseDpkLvl, strBaseBmnCode);
		dispatchResultListObject(req, resp, list);

	}

	/**
	 * ���ݒ�}�X�^�ꗗ����
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchEnvMstData(HttpServletRequest req, HttpServletResponse resp) throws ParseException {

		// ��ЃR�[�h
		String compCode = Util.avoidNull(req.getParameter("KAI_CODE"));
		// ��Ж���
		String shortName = Util.avoidNull(req.getParameter("KAI_NAME_S"));
		// ���O�C����ЃR�[�h
		String loginKaiCode = Util.avoidNull(req.getParameter("LOGIN_KAI_CODE"));
		// �g�D�R�[�h
		String strDpkSsk = req.getParameter("DPK_SSK");
		// �z�����(0:�܂� 1:�܂܂Ȃ�)
		String strCmpKbn = req.getParameter("COMPANY_KBN");
		// ��ʉ�к���
		String strUpCmpCode = req.getParameter("UP_COMPANY_CODE");
		// �K�w����
		String strDpkLvl = req.getParameter("DPK_LVL");
		// ������к���
		String strBaseCmpCode = req.getParameter("BASE_COMPANY_CODE");
		// �����K�w����
		String strBaseDpkLvl = req.getParameter("BASE_DPK_LVL");

		// Like�𗘗p���邽��
		if (!Util.isNullOrEmpty(compCode)) {
			compCode = DBUtil.getLikeStatement(compCode, DBUtil.NORMAL_CHAR);
		}

		if (!Util.isNullOrEmpty(shortName)) {
			shortName = DBUtil.getLikeStatement(shortName, DBUtil.UNICODE_CHAR);
		}

		S2Container container = SingletonS2ContainerFactory.getContainer();
		CommonLogic logic = (CommonLogic) container.getComponent("EnvironmentalSettingLogic");

		Map param = new TreeMap();
		param.put("KAI_CODE", compCode);
		param.put("KAI_NAME_S", shortName);
		param.put("LOGIN_KAI_CODE", loginKaiCode);
		param.put("DPK_SSK", strDpkSsk);
		param.put("CMP_KBN", strCmpKbn);
		param.put("UP_CMP_CODE", strUpCmpCode);
		param.put("DPK_LVL", strDpkLvl);
		param.put("BASE_CMP_CODE", strBaseCmpCode);
		param.put("BASE_DPK_LVL", strBaseDpkLvl);

		dispatchResultListObject(req, resp, logic.findREF(param));
	}

	/**
	 * �Ȗڈꗗ����
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	private void searchKmkMstData(HttpServletRequest req, HttpServletResponse resp) {
		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();

			TAccountItemUnitLogic logic = (TAccountItemUnitLogic) container.getComponent(TAccountItemUnitLogic.class);
			// ��ЃR�[�h
			String strKaiCode = req.getParameter("KAI_CODE");
			// �ȖڃR�[�h
			String strKmkCode = req.getParameter("KMK_CODE");
			// �Ȗڗ���
			String strKmkName = req.getParameter("KMK_NAME");
			// �Ȗڌ�������
			String strKmkNameK = req.getParameter("KMK_NAME_K");
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
			// AR�Ȗڐ���敪
			String strKmKCntUnAr = Util.avoidNull(req.getParameter("KMK_CNT_AR"));
			// AR����敪(�����p)
			String strKmkCntUnAr = Util.avoidNull(req.getParameter("KMK_CNT_UN_AR"));
			// AP�Ȗڐ���敪
			String strKmkCntUnAp = req.getParameter("KMK_CNT_AP");
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
			String kmkTkCode = Util.avoidNull(req.getParameter("KMK_TK_CODE"));
			// �Ȗڑ̌n�t���O
			String kmkSystemFlg = Util.avoidNull(req.getParameter("KMK_TK_FLG"));

			// Like�𗘗p���邽��
			strKmkCode = DBUtil.getLikeStatement(strKmkCode, DBUtil.NORMAL_CHAR);
			strKmkName = DBUtil.getLikeStatement(strKmkName, DBUtil.UNICODE_CHAR);
			strKmkNameK = DBUtil.getLikeStatement(strKmkNameK, DBUtil.UNICODE_CHAR);

			// �p�����[�^�[��ݒ肷��
			AccountItemInputParameter inputParameter = new AccountItemInputParameter();
			inputParameter.setCompanyCode(strKaiCode); // ��к���
			inputParameter.setItemCode(strKmkCode); // �Ȗں���
			inputParameter.setItemAbbrName(strKmkName); // �Ȗڗ���
			inputParameter.setItemNameForSearch(strKmkNameK); // �Ȗڌ�������
			inputParameter.setSlipDate(strSlipDate); // �`�[���t
			inputParameter.setBsAccountErasingDivision(strKesiKbn); // BS��������敪
			inputParameter.setSummaryDivision(strSumKbn); // �W�v�敪
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
			inputParameter.setGlItemCtrlDivision(strKmkCntGl); // GL�Ȗڐ���敪
			inputParameter.setArItemCtrlDivision(strKmKCntUnAr); // AR�Ȗڐ���敪
			inputParameter.setUnArItemCtrlDivision(strKmkCntUnAr); // AR����敪(�����p)
			inputParameter.setApItemCtrlDivision(strKmkCntUnAp); // AP�Ȗڐ���敪

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
			inputParameter.setItemSystemCode(kmkTkCode); // �Ȗڑ̌n�R�[�h
			inputParameter.setItemSystemFlg(kmkSystemFlg); // �Ȗڑ̌n�t���O

			inputParameter.setBeginCode(Util.avoidNull(req.getParameter("BEGIN_CODE"))); // �J�n�R�[�h
			inputParameter.setEndCode(Util.avoidNull(req.getParameter("END_CODE"))); // �I���R�[�h

			dispatchResultListObject(req, resp, logic.getItemInfoAll(inputParameter));

		} catch (TException e) {
			dispatchError(req, resp, e);
		}
	}

	/**
	 * �⏕�Ȗڃ}�X�^�ꗗ����
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	private void searchHkmMstData(HttpServletRequest req, HttpServletResponse resp) {
		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();

			TAccountItemUnitLogic logic = (TAccountItemUnitLogic) container.getComponent(TAccountItemUnitLogic.class);
			// ��ЃR�[�h
			String strKaiCode = req.getParameter("KAI_CODE");
			// �ȖڃR�[�h
			String strKmkCode = req.getParameter("KMK_CODE");
			// �⏕�ȖڃR�[�h
			String strHkmCode = req.getParameter("HKM_CODE");
			// �⏕�Ȗڗ���
			String strHkmName = req.getParameter("HKM_NAME");
			// �⏕�Ȗڌ�������
			String strHkmNameK = req.getParameter("HKM_NAME_K");
			// �`�[���t
			String strSlipDate = req.getParameter("SLIP_DATE");
			// �U�֓`�[���̓t���O
			String strFurikaeFlg = req.getParameter("FURIKAE_FLG");
			// �����`�[���̓t���O
			String strNyuKin = req.getParameter("NYU_KIN");
			// �o���`�[���̓t���O
			String strShutsuKin = req.getParameter("SHUTSU_KIN");
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

			// Like�𗘗p���邽��
			strHkmCode = DBUtil.getLikeStatement(strHkmCode, DBUtil.NORMAL_CHAR);
			strHkmName = DBUtil.getLikeStatement(strHkmName, DBUtil.UNICODE_CHAR);
			strHkmNameK = DBUtil.getLikeStatement(strHkmNameK, DBUtil.UNICODE_CHAR);

			// �p�����[�^�[��ݒ肷��
			AccountItemInputParameter inputParameter = new AccountItemInputParameter();
			inputParameter.setCompanyCode(strKaiCode); // ��к���
			inputParameter.setItemCode(strKmkCode); // �Ȗں���
			inputParameter.setSubItemCode(strHkmCode); // �⏕�Ȗں���
			inputParameter.setSubItemAbbrName(strHkmName); // �⏕�Ȗڗ���
			inputParameter.setSubItemNameForSearch(strHkmNameK); // �⏕�Ȗڌ�������
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

			inputParameter.setBeginCode(Util.avoidNull(req.getParameter("BEGIN_CODE"))); // �J�n�R�[�h
			inputParameter.setEndCode(Util.avoidNull(req.getParameter("END_CODE"))); // �I���R�[�h

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

			dispatchResultListObject(req, resp, logic.getSubItemInfoAll(inputParameter));

		} catch (TException e) {
			dispatchError(req, resp, e);
		}

	}

	/**
	 * ����Ȗڃ}�X�^�ꗗ����
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	private void searchUkmMstData(HttpServletRequest req, HttpServletResponse resp) {
		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();

			TAccountItemUnitLogic logic = (TAccountItemUnitLogic) container.getComponent(TAccountItemUnitLogic.class);
			// ��ЃR�[�h
			String strKaiCode = req.getParameter("KAI_CODE");
			// �ȖڃR�[�h
			String strKmkCode = req.getParameter("KMK_CODE");
			// �⏕�ȖڃR�[�h
			String strHkmCode = req.getParameter("HKM_CODE");
			// ����ȖڃR�[�h
			String strUkmCode = req.getParameter("UKM_CODE");
			// ����Ȗڗ���
			String strUkmName = req.getParameter("UKM_NAME");
			// ����Ȗڌ�������
			String strUkmNameK = req.getParameter("UKM_NAME_K");
			// �`�[���t
			String strSlipDate = req.getParameter("SLIP_DATE");
			// �U�֓`�[���̓t���O
			String strFurikaeFlg = req.getParameter("FURIKAE_FLG");
			// �����`�[���̓t���O
			String strNyuKin = req.getParameter("NYU_KIN");
			// �o���`�[���̓t���O
			String strShutsuKin = req.getParameter("SHUTSU_KIN");
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

			// Like�𗘗p���邽��
			strUkmCode = DBUtil.getLikeStatement(strUkmCode, DBUtil.NORMAL_CHAR);
			strUkmName = DBUtil.getLikeStatement(strUkmName, DBUtil.UNICODE_CHAR);
			strUkmNameK = DBUtil.getLikeStatement(strUkmNameK, DBUtil.UNICODE_CHAR);

			// �p�����[�^�[��ݒ肷��
			AccountItemInputParameter inputParameter = new AccountItemInputParameter();
			inputParameter.setCompanyCode(strKaiCode); // ��к���
			inputParameter.setItemCode(strKmkCode); // �Ȗں���
			inputParameter.setSubItemCode(strHkmCode); // �⏕�Ȗں���
			inputParameter.setBreakDownItemCode(strUkmCode); // ����Ȗں���
			inputParameter.setBreakDownItemAbbrName(strUkmName); // ���󗪏�
			inputParameter.setBreakDownItemNameForSearch(strUkmNameK); // ���󌟍�����
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

			inputParameter.setBeginCode(Util.avoidNull(req.getParameter("BEGIN_CODE"))); // �J�n�R�[�h
			inputParameter.setEndCode(Util.avoidNull(req.getParameter("END_CODE"))); // �I���R�[�h

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

			dispatchResultListObject(req, resp, logic.getBreakDownItemInfoAll(inputParameter));

		} catch (TException e) {
			dispatchError(req, resp, e);
		}
	}

	/**
	 * ��s��������
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchModifyReservationBankAccount(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = null;

		/**
		 * ���������[�N�΍�
		 */
		container = SingletonS2ContainerFactory.getContainer();

		BankAccountLogic logic = (BankAccountLogic) container.getComponent(BankAccountLogic.class);

		// ��ЃR�[�h
		String kaiCode = req.getParameter("KaiCode");

		// �R�[�h
		String code = req.getParameter("Code");
		// ��������
		String nameS = req.getParameter("NameS");
		// ��������
		String nameK = req.getParameter("NameK");
		// �Ј��x�����t���O
		boolean empKbn = BooleanUtil.toBoolean(req.getParameter("empKbn"));
		// �ЊO�x�����t���O
		boolean outKbn = BooleanUtil.toBoolean(req.getParameter("outKbn"));

		String termBasisDate = "";
		Date BaseDate = null;
		Timestamp stampBasisDate = null;
		if (!Util.isNullOrEmpty(req.getParameter("termBasisDate"))) {
			termBasisDate = req.getParameter("termBasisDate");
			try {
				BaseDate = DateUtil.toYMDDate(termBasisDate);
			} catch (ParseException e) {
				throw new TRuntimeException();
			}
			stampBasisDate = DateUtil.toTimestamp(BaseDate);
		}

		// Like�𗘗p���邽��
		code = DBUtil.getLikeStatement(code, DBUtil.NORMAL_CHAR);
		nameS = DBUtil.getLikeStatement(nameS, DBUtil.UNICODE_CHAR);
		nameK = DBUtil.getLikeStatement(nameK, DBUtil.UNICODE_CHAR);

		// ���ʂ��擾����B
		List<Object> list = logic.searchReservationBankAccount(kaiCode, code, nameS, nameK, outKbn, empKbn,
			stampBasisDate);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * ��s�����}�X�^�ꗗ����(��s���ɋ�s�}�X�^.��s��+��s�}�X�^.�x�X��)
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchModifyReservationBankAccountB(HttpServletRequest req, HttpServletResponse resp)
		throws ParseException {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// dao�̏�����
		AP_CBK_MSTDao dao = (AP_CBK_MSTDao) container.getComponent(AP_CBK_MSTDao.class);

		ApCbkMstParameter param = new ApCbkMstParameter();
		// ��ЃR�[�h
		param.setKaiCode(req.getParameter("KaiCode"));

		// ��s�����R�[�h
		param.setLikeCbkCode(DBUtil.getLikeStatement(req.getParameter("Code"), DBUtil.NORMAL_CHAR));
		// ��������
		param.setLikeNameS(DBUtil.getLikeStatement(req.getParameter("NameS"), DBUtil.UNICODE_CHAR));
		// �����ԍ�
		param.setLikeCbkYknNo(DBUtil.getLikeStatement(req.getParameter("NameK"), DBUtil.NORMAL_CHAR));
		// �Ј��x���t���O
		param.setEmpFbKbn(BooleanUtil.toBoolean(req.getParameter("empKbn")));
		// �ЊO�x���t���O
		param.setOutFbKbn(BooleanUtil.toBoolean(req.getParameter("outKbn")));
		// ��s�������X�g
		param.setCbkCodes((List<String>) getObjectParameter(req));

		// �L�����ԓ��t
		String termBasisDate = req.getParameter("termBasisDate");
		if (!Util.isNullOrEmpty(termBasisDate)) {
			param.setTermBasisDate(DateUtil.toYMDDate(termBasisDate));
		}

		// ���ʂ��擾����B
		List<Object> list = dao.getApCbkMst(param);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * ����挟��
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchRefCustomer(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = null;

		/**
		 * ���������[�N�΍�
		 */
		container = SingletonS2ContainerFactory.getContainer();

		CustomerLogic logic = (CustomerLogic) container.getComponent(CustomerLogic.class);

		// ��ЃR�[�h
		String kaiCode = req.getParameter("kaiCode");

		// �����R�[�h
		String code = req.getParameter("triCode");
		// ��������
		String nameS = req.getParameter("sName");
		// ��������
		String nameK = req.getParameter("kName");
		// �J�n�R�[�h
		String beginCode = Util.avoidNull(req.getParameter("beginCode"));
		// �I���R�[�h
		String endCode = Util.avoidNull(req.getParameter("endCode"));

		// �d����Ɠ��Ӑ�t���O
		boolean siire = BooleanUtil.toBoolean(req.getParameter("siire"));
		boolean tokui = BooleanUtil.toBoolean(req.getParameter("tokui"));

		// �L������
		String termBasisDate = "";
		Date BaseDate = null;
		Timestamp stampBasisDate = null;
		if (!Util.isNullOrEmpty(req.getParameter("termBasisDate"))) {
			termBasisDate = req.getParameter("termBasisDate");

			BaseDate = DateUtil.toYMDDate(termBasisDate);

			stampBasisDate = DateUtil.toTimestamp(BaseDate);
		}

		// Like�𗘗p���邽��
		code = DBUtil.getLikeStatement(code, DBUtil.NORMAL_CHAR);
		nameS = DBUtil.getLikeStatement(nameS, DBUtil.UNICODE_CHAR);
		nameK = DBUtil.getLikeStatement(nameK, DBUtil.UNICODE_CHAR);

		// ���ʂ��擾����B
		List<Object> list = logic.refSearchCustomer(kaiCode, code, nameS, nameK, stampBasisDate, siire, tokui,
			beginCode, endCode);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * �ʉ݃_�C�A���O����
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchRefCurrency(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = null;

		/**
		 * ���������[�N�΍�
		 */
		container = SingletonS2ContainerFactory.getContainer();

		CurrencyLogic logic = (CurrencyLogic) container.getComponent(CurrencyLogic.class);

		// ��ЃR�[�h
		String kaiCode = req.getParameter("kaiCode");

		// �����R�[�h
		String code = req.getParameter("curCode");
		// ��������
		String nameS = req.getParameter("sName");
		// ��������
		String nameK = req.getParameter("kName");

		// �J�n�R�[�h
		String beginCode = Util.avoidNull(req.getParameter("beginCode"));
		// �I���R�[�h
		String endCode = Util.avoidNull(req.getParameter("endCode"));

		String termBasisDate = "";
		Date BaseDate = null;
		Timestamp stampBasisDate = null;
		if (!Util.isNullOrEmpty(req.getParameter("termBasisDate"))) {
			termBasisDate = req.getParameter("termBasisDate");

			BaseDate = DateUtil.toYMDDate(termBasisDate);

			stampBasisDate = DateUtil.toTimestamp(BaseDate);
		}

		// Like�𗘗p���邽��
		code = DBUtil.getLikeStatement(code, DBUtil.NORMAL_CHAR);
		nameS = DBUtil.getLikeStatement(nameS, DBUtil.UNICODE_CHAR);
		nameK = DBUtil.getLikeStatement(nameK, DBUtil.UNICODE_CHAR);

		// ���ʂ��擾����B
		List<Object> list = logic.refDailogCurrency(kaiCode, code, nameS, nameK, stampBasisDate, beginCode, endCode);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * �v���Ѓ_�C�A���O����
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchRefCompany(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = null;

		/**
		 * ���������[�N�΍�
		 */
		container = SingletonS2ContainerFactory.getContainer();

		TAppropriateCompanyLogic logic = (TAppropriateCompanyLogic) container
			.getComponent(TAppropriateCompanyLogic.class);

		AppropriateCompany param = (AppropriateCompany) this.getDtoParameter(req, AppropriateCompany.class);
		// ����敪
		param.setBlnOptKbn(true);

		// ��ʉ݃R�[�h
		param.setCUR_CODE(refTServerUserInfo(req).getCompanyInfo().getBaseCurrencyCode());

		// Like�𗘗p���邽��
		param.setKAI_CODE(DBUtil.getLikeStatement(param.getKAI_CODE(), DBUtil.NORMAL_CHAR));
		param.setKAI_NAME_S(DBUtil.getLikeStatement(param.getKAI_NAME_S(), DBUtil.UNICODE_CHAR));

		// ���ʂ��擾����B
		List list = logic.conditionSearch(param);

		dispatchResultDtoList(req, resp, list);

	}

	/**
	 * ����_�C�A���O����
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchRefDepartment(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = null;

		/**
		 * ���������[�N�΍�
		 */
		container = SingletonS2ContainerFactory.getContainer();

		DepartmentLogic logic = (DepartmentLogic) container.getComponent(DepartmentLogic.class);

		// ��ЃR�[�h
		String kaiCode = req.getParameter("kaiCode");

		// �����R�[�h
		String depCode = req.getParameter("depCode");
		// ��������
		String sName = req.getParameter("sName");
		// ��������
		String kName = req.getParameter("kName");
		// �g�D
		String organization = req.getParameter("organization");
		// ��ʕ���
		String upperDepart = req.getParameter("upper");
		// �g�D���x�� - �k���l�Ƃ̔�r���I��������߁A���Ƃ̃��x���ɖ߂��B
		int level = (Integer.parseInt(req.getParameter("level"))) - 1;
		// �W�v
		boolean sumDepart = BooleanUtil.toBoolean(req.getParameter("sum"));
		// ����
		boolean inputDepart = BooleanUtil.toBoolean(req.getParameter("input"));

		String termBasisDate = "";
		Date BaseDate = null;
		Timestamp stampBasisDate = null;
		if (!Util.isNullOrEmpty(req.getParameter("termBasisDate"))) {
			termBasisDate = req.getParameter("termBasisDate");

			BaseDate = DateUtil.toYMDDate(termBasisDate);

			stampBasisDate = DateUtil.toTimestamp(BaseDate);
		}

		// Like�𗘗p���邽��
		depCode = DBUtil.getLikeStatement(depCode, DBUtil.NORMAL_CHAR);
		sName = DBUtil.getLikeStatement(sName, DBUtil.UNICODE_CHAR);
		kName = DBUtil.getLikeStatement(kName, DBUtil.UNICODE_CHAR);

		String beginCode = req.getParameter("beginCode");
		String endCode = req.getParameter("endCode");

		// ���ʂ��擾����B
		List<Object> list = logic.refSearchDepartment(kaiCode, depCode, sName, kName, stampBasisDate, organization,
			level, upperDepart, sumDepart, inputDepart, beginCode, endCode);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * �s�E�v�E�`�[�E�v�_�C�A���O����
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchRefMemo(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = null;

		/**
		 * ���������[�N�΍�
		 */
		container = SingletonS2ContainerFactory.getContainer();

		MemoLogic logic = (MemoLogic) container.getComponent(MemoLogic.class);

		TUserInfo userInfo = refTServerUserInfo(req);

		// ��ЃR�[�h
		String kaiCode;
		if (!Util.isNullOrEmpty(req.getParameter("kaiCode"))) {
			kaiCode = req.getParameter("kaiCode");
		} else {
			kaiCode = userInfo.getCompanyCode();
		}

		// �����R�[�h
		String tekCode = req.getParameter("tekCode");
		// ��������
		String sName = req.getParameter("sName");
		// ��������
		String kName = req.getParameter("kName");
		// �f�[�^�^�C�v
		String slipType = req.getParameter("slipType");
		// �E�v�敪
		int memoType = Integer.parseInt(req.getParameter("memoType"));

		String termBasisDate = "";
		Date BaseDate = null;
		Timestamp stampBasisDate = null;
		if (!Util.isNullOrEmpty(req.getParameter("termBasisDate"))) {
			termBasisDate = req.getParameter("termBasisDate");

			BaseDate = DateUtil.toYMDDate(termBasisDate);

			stampBasisDate = DateUtil.toTimestamp(BaseDate);
		}

		// Like�𗘗p���邽��
		tekCode = DBUtil.getLikeStatement(tekCode, DBUtil.NORMAL_CHAR);
		sName = DBUtil.getLikeStatement(sName, DBUtil.UNICODE_CHAR);
		kName = DBUtil.getLikeStatement(kName, DBUtil.UNICODE_CHAR);

		// ���ʂ��擾����B
		List<Object> list = logic.refDailog(kaiCode, tekCode, sName, kName, memoType, slipType, stampBasisDate);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * ����Ń_�C�A���O����
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchRefTax(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = null;
		/**
		 * ���������[�N�΍�
		 */
		container = SingletonS2ContainerFactory.getContainer();

		ConsumptionTaxLogic logic = (ConsumptionTaxLogic) container.getComponent(ConsumptionTaxLogic.class);

		// ��ЃR�[�h
		String kaiCode = req.getParameter("kaiCode");

		// �����R�[�h
		String zeiCode = req.getParameter("zeiCode");
		// ��������
		String sName = req.getParameter("sName");
		// ��������
		String kName = req.getParameter("kName");

		String sales = req.getParameter("sales");

		String purchase = req.getParameter("purchase");

		String elseTax = req.getParameter("elseTax");

		String termBasisDate = "";
		Date BaseDate = null;
		Timestamp stampBasisDate = null;
		if (!Util.isNullOrEmpty(req.getParameter("termBasisDate"))) {
			termBasisDate = req.getParameter("termBasisDate");

			BaseDate = DateUtil.toYMDDate(termBasisDate);

			stampBasisDate = DateUtil.toTimestamp(BaseDate);
		}

		// Like�𗘗p���邽��
		zeiCode = DBUtil.getLikeStatement(zeiCode, DBUtil.NORMAL_CHAR);
		sName = DBUtil.getLikeStatement(sName, DBUtil.UNICODE_CHAR);
		kName = DBUtil.getLikeStatement(kName, DBUtil.UNICODE_CHAR);

		// ���ʂ��擾����B
		List<Object> list = logic.refDailog(kaiCode, zeiCode, sName, kName, sales, purchase, elseTax, stampBasisDate);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * �Ј������_�C�A���O����
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */

	private void searchRefEmployee(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = null;
		/**
		 * ���������[�N�΍�
		 */
		container = SingletonS2ContainerFactory.getContainer();

		EmployeeLogic logic = (EmployeeLogic) container.getComponent(EmployeeLogic.class);
		// ��ЃR�[�h
		String kaiCode = req.getParameter("kaiCode");

		// �Ј��R�[�h
		String empCode = req.getParameter("empCode");
		// ��������
		String sName = req.getParameter("sName");
		// ��������
		String kName = req.getParameter("kName");

		String user = req.getParameter("user");

		String depCode = req.getParameter("depCode");

		// �J�n�R�[�h
		String beginCode = Util.avoidNull(req.getParameter("beginCode"));
		// �I���R�[�h
		String endCode = Util.avoidNull(req.getParameter("endCode"));

		String termBasisDate = "";
		Date BaseDate = null;
		Timestamp stampBasisDate = null;
		if (!Util.isNullOrEmpty(req.getParameter("termBasisDate"))) {
			termBasisDate = req.getParameter("termBasisDate");

			BaseDate = DateUtil.toYMDDate(termBasisDate);

			stampBasisDate = DateUtil.toTimestamp(BaseDate);
		}

		// Like�𗘗p���邽��
		empCode = DBUtil.getLikeStatement(empCode, DBUtil.NORMAL_CHAR);
		sName = DBUtil.getLikeStatement(sName, DBUtil.UNICODE_CHAR);
		kName = DBUtil.getLikeStatement(kName, DBUtil.UNICODE_CHAR);

		// ���ʂ��擾����B
		List<Object> list = logic.refDailog(kaiCode, empCode, sName, kName, stampBasisDate, user, depCode, beginCode,
			endCode);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * �x�������@�_�C�A���O����
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchRefPayment(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = null;
		/**
		 * ���������[�N�΍�
		 */
		container = SingletonS2ContainerFactory.getContainer();

		AP_HOH_MSTDao dao = (AP_HOH_MSTDao) container.getComponent(AP_HOH_MSTDao.class);

		ApHohMstParameter param = new ApHohMstParameter();
		// ��ЃR�[�h
		param.setKaiCode(req.getParameter("kaiCode"));
		// �x�����@�R�[�h
		param.setLikeHohCode(DBUtil.getLikeStatement(req.getParameter("hohCode"), DBUtil.NORMAL_CHAR));
		// ����
		param.setLikeHohName(DBUtil.getLikeStatement(req.getParameter("sName"), DBUtil.UNICODE_CHAR));
		// ��������
		param.setLikeHohNameK(DBUtil.getLikeStatement(req.getParameter("kName"), DBUtil.UNICODE_CHAR));
		// �x���Ώۋ敪
		param.setHohSihKbn(req.getParameter("sihKbn"));
		// �x�������R�[�h
		if (!Util.isNullOrEmpty(req.getParameter("naiCode"))) {
			param.setHohNaiCode(new String[] { req.getParameter("naiCode") });
		}
		// �x�������R�[�h�iNOT�����j
		param.setNotHohNaiCode(req.getParameter("notNaiCode"));
		// �L�����ԓ��t
		String termBasisDate = req.getParameter("termBasisDate");
		if (!Util.isNullOrEmpty(termBasisDate)) {
			param.setTermBasisDate(DateUtil.toYMDDate(termBasisDate));
		}

		// �x�����@�R�[�h���X�g
		param.setHohCodes((List<String>) getObjectParameter(req));

		List<Object> list = dao.getApHohMst(param);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * �x�������_�C�A���O����
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchRefPaymentCon(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CustomerConditionLogic logic = (CustomerConditionLogic) container.getComponent(CustomerConditionLogic.class);

		// ��ЃR�[�h
		String kaiCode = req.getParameter("kaiCode");

		// �����R�[�h
		String triCode = req.getParameter("triCode");

		// ���������R�[�h
		String tjkCode = DBUtil.getLikeStatement(req.getParameter("tjkCode"), DBUtil.NORMAL_CHAR);

		String termBasisDate = "";
		Date BaseDate = null;
		Timestamp stampBasisDate = null;
		if (!Util.isNullOrEmpty(req.getParameter("termBasisDate"))) {
			termBasisDate = req.getParameter("termBasisDate");

			BaseDate = DateUtil.toYMDDate(termBasisDate);

			stampBasisDate = DateUtil.toTimestamp(BaseDate);
		}

		// ���ʂ��擾����B
		List list = logic.refDailog(kaiCode, triCode, tjkCode, stampBasisDate);

		dispatchResultList(req, resp, list);
	}

	/**
	 * �Ј��}�X�^����
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	private void searchEmpMstData(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = null;

		container = SingletonS2ContainerFactory.getContainer();

		InputPersonLogic logic = (InputPersonLogic) container.getComponent(InputPersonLogic.class);
		// ��ЃR�[�h
		String strKaiCode = req.getParameter("KAI_CODE");
		// �Ј��R�[�h
		String strEmpCode = req.getParameter("EMP_CODE");
		// �Ј�����
		String strEmpName = req.getParameter("EMP_NAME");
		// �Ј���������
		String strEmpNameK = req.getParameter("EMP_NAME_K");
		// �`�[���t
		String strSlipDate = req.getParameter("SLIP_DATE");
		// ��������R�[�h�敪
		String strDepCodeKbn = Util.avoidNull(req.getParameter("DEP_CODE_KBN"));
		// ��������R�[�h
		String strDepCode = Util.avoidNull(req.getParameter("DEP_CODE"));
		// Like�𗘗p���邽��
		strEmpCode = DBUtil.getLikeStatement(strEmpCode, DBUtil.NORMAL_CHAR);
		strEmpName = DBUtil.getLikeStatement(strEmpName, DBUtil.UNICODE_CHAR);
		strEmpNameK = DBUtil.getLikeStatement(strEmpNameK, DBUtil.UNICODE_CHAR);

		List list = null;
		// ���ʂ��擾����B
		list = logic.searchEmpMstData(strKaiCode, strEmpCode, strEmpName, strEmpNameK, strSlipDate, strDepCodeKbn,
			strDepCode);
		dispatchResultListObject(req, resp, list);

	}

	/**
	 * �Ǘ��}�X�^����
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	private void searchKnrMstData(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = null;

		container = SingletonS2ContainerFactory.getContainer();

		ManagementLogic logic = (ManagementLogic) container.getComponent(ManagementLogic.class);
		// ��ЃR�[�h
		String strKaiCode = req.getParameter("KAI_CODE");
		// �Ј��R�[�h
		String strKnrCode = req.getParameter("KNR_CODE");
		// �Ј�����
		String strKnrName = req.getParameter("KNR_NAME");
		// �Ј���������
		String strKnrNameK = req.getParameter("KNR_NAME_K");
		// �`�[���t
		String strSlipDate = req.getParameter("SLIP_DATE");
		// �Ǘ��\�t���O
		String strKnrFlg = req.getParameter("KNR_FLAG");

		// �J�n�R�[�h
		String startCode = req.getParameter("START_CODE");
		// �I���R�[�h
		String endCode = req.getParameter("END_CODE");

		// Like�𗘗p���邽��
		strKnrCode = DBUtil.getLikeStatement(strKnrCode, DBUtil.NORMAL_CHAR);
		strKnrName = DBUtil.getLikeStatement(strKnrName, DBUtil.UNICODE_CHAR);
		strKnrNameK = DBUtil.getLikeStatement(strKnrNameK, DBUtil.UNICODE_CHAR);

		List list = null;
		// ���ʂ��擾����B
		if ("1".equals(strKnrFlg)) {
			// �Ǘ�1�}�X�^
			list = logic.searchKnr1MstData(strKaiCode, strKnrCode, strKnrName, strKnrNameK, strSlipDate, startCode,
				endCode);
		} else if ("2".equals(strKnrFlg)) {
			// �Ǘ�2�}�X�^
			list = logic.searchKnr2MstData(strKaiCode, strKnrCode, strKnrName, strKnrNameK, strSlipDate, startCode,
				endCode);
		} else if ("3".equals(strKnrFlg)) {
			// �Ǘ�3�}�X�^
			list = logic.searchKnr3MstData(strKaiCode, strKnrCode, strKnrName, strKnrNameK, strSlipDate, startCode,
				endCode);
		} else if ("4".equals(strKnrFlg)) {
			// �Ǘ�4�}�X�^
			list = logic.searchKnr4MstData(strKaiCode, strKnrCode, strKnrName, strKnrNameK, strSlipDate, startCode,
				endCode);
		} else if ("5".equals(strKnrFlg)) {
			// �Ǘ�5�}�X�^
			list = logic.searchKnr5MstData(strKaiCode, strKnrCode, strKnrName, strKnrNameK, strSlipDate, startCode,
				endCode);
		} else if ("6".equals(strKnrFlg)) {
			// �Ǘ�6�}�X�^
			list = logic.searchKnr6MstData(strKaiCode, strKnrCode, strKnrName, strKnrNameK, strSlipDate, startCode,
				endCode);
		}

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * �Ȗڑ̌n���̃}�X�^����
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	private void searchKmkTkMstData(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = null;

		container = SingletonS2ContainerFactory.getContainer();

		KmkTkMstLogic logic = (KmkTkMstLogic) container.getComponent(KmkTkMstLogic.class);
		// ��ЃR�[�h
		String strKaiCode = req.getParameter("KAI_CODE");
		// �Ȗڑ̌n���̃R�[�h
		String strKmtCode = req.getParameter("KMT_CODE");
		// �Ȗڑ̌n���̗���
		String strKmtName = req.getParameter("KMT_NAME");
		// �Ȗڑ̌n���̌�������
		String strKmtNameK = req.getParameter("KMT_NAME_K");
		// TODO �R���g���[���p�����[�^�[�m�F
		// �J�n�R�[�h
		String startCode = req.getParameter("START_CODE");
		// �I���R�[�h
		String endCode = req.getParameter("END_CODE");

		// Like�𗘗p���邽��
		strKmtCode = DBUtil.getLikeStatement(strKmtCode, DBUtil.NORMAL_CHAR);
		strKmtName = DBUtil.getLikeStatement(strKmtName, DBUtil.UNICODE_CHAR);
		strKmtNameK = DBUtil.getLikeStatement(strKmtNameK, DBUtil.UNICODE_CHAR);

		List list = null;
		// ���ʂ��擾����B
		list = logic.searchKmtMstData(strKaiCode, strKmtCode, strKmtName, strKmtNameK, startCode, endCode);
		dispatchResultListObject(req, resp, list);

	}

	/**
	 * ��s�}�X�^����
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	private void searchBMKMstData(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		BankLogic logic = (BankLogic) container.getComponent(BankLogic.class);

		BnkMstParameter param = (BnkMstParameter) getObjectParameter(req);

		// Like�𗘗p���邽��
		param.setLikeBnkCode(DBUtil.getLikeStatement(param.getLikeBnkCode(), DBUtil.NORMAL_CHAR));
		param.setLikeBnkName(DBUtil.getLikeStatement(param.getLikeBnkName(), DBUtil.UNICODE_CHAR));
		param.setLikeBnkNameK(DBUtil.getLikeStatement(param.getLikeBnkNameK(), DBUtil.UNICODE_CHAR));

		dispatchResultObject(req, resp, logic.getBankList(param));
	}

	/**
	 * ��s�}�X�^���� �i�x�X���j
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	private void searchBMKSTNMstData(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		BankLogic logic = (BankLogic) container.getComponent(BankLogic.class);

		BnkMstParameter param = (BnkMstParameter) getObjectParameter(req);

		// Like�𗘗p���邽��
		param.setLikeBnkStnCode(DBUtil.getLikeStatement(param.getLikeBnkStnCode(), DBUtil.NORMAL_CHAR));
		param.setLikeBnkStnName(DBUtil.getLikeStatement(param.getLikeBnkStnName(), DBUtil.UNICODE_CHAR));
		param.setLikeBnkStnNameK(DBUtil.getLikeStatement(param.getLikeBnkStnNameK(), DBUtil.UNICODE_CHAR));

		dispatchResultObject(req, resp, logic.getStnList(param));
	}

}