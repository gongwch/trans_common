package jp.co.ais.trans.common.server.servlet;

import java.math.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.server.di.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.util.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * ���ʃT�[�u���b�g
 */
public class InformationServlet extends TServletBase {

	/** UID */
	protected static final long serialVersionUID = -5319125982776071994L;

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

			if ("OrganizationCode".equals(flag)) {
				// �g�D�R�[�h�Z�b�g
				dispatchOrgCode(req, resp);
			} else if ("CmpOrganizationCode".equals(flag)) {
				// ��ВP�ʂ̑g�D�R�[�h�Z�b�g
				dispatchCmpOrgCode(req, resp);
			} else if ("OrganizationInfo".equals(flag)) {
				// �J�����x�����
				dispatchOrgData(req, resp);
			} else if ("ItemInfo".equals(flag)) {
				// �Ȗڏ��
				dispatchItemData(req, resp);
			} else if ("isCode".equals(flag)) {
				// ���̂��擾����
				dispatchgIsCode(req, resp);
			} else if ("CmpInfo".equals(flag)) {
				// ��ЃR���g���[���}�X�^�ꗗ
				dispatchCmpMstData(req, resp);
			} else if ("Knrmst".equals(flag)) {
				// �Ǘ��R�[�h���擾
				dispatchKnrMstData(req, resp);
			} else if ("ACCOUNT_STAGE".equals(flag)) {
				// ���Z�i�K���擾����
				findAccountStage(req, resp);
			} else if ("KSD_KBN".equals(flag)) {
				// ���Z�����擾����
				getKsdKbn(req, resp);
			} else if ("FindCurData".equals(flag)) {
				// �ʉ݃f�[�^����������
				findCurMst(req, resp);
			} else if ("convertForeign".equals(flag)) {
				// �O�݂���ʉ݂Ɋ��Z����
				convertForeign(req, resp);
			} else if ("FindRate".equals(flag)) {
				// ���[�g����
				findRate(req, resp);
			} else if ("GetItemSystem".equals(flag)) {
				// ��{�Ȗڑ̌n���̂��擾����
				getItemSystemValue(req, resp);
			}

		} catch (Exception e) {
			handledException(e, req, resp);
		}
	}

	/**
	 * �g�D�R�[�h�Z�b�g
	 * 
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 */
	protected void dispatchOrgCode(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);
		logic.setCompanyCode(refTServerUserInfo(req).getCompanyCode());

		String[] orgCodes = logic.getOrganizationCodeList();

		// �ԐM
		dispatchResult(req, resp, "orgCodes", StringUtil.toDelimitString(orgCodes));
	}

	/**
	 * ��ВP�ʂ̑g�D�R�[�h�Z�b�g
	 * 
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 */
	protected void dispatchCmpOrgCode(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);
		logic.setCompanyCode(refTServerUserInfo(req).getCompanyCode());

		String[] orgCodes = logic.getCmpOrganizationCodeList();

		// �ԐM
		dispatchResult(req, resp, "orgCodes", StringUtil.toDelimitString(orgCodes));
	}

	/**
	 * �J�����x�����Z�b�g
	 * 
	 * @param req
	 * @param resp
	 */
	protected void dispatchOrgData(HttpServletRequest req, HttpServletResponse resp) {

		try {
			String strKaiCode = req.getParameter("KAI_CODE"); // ��ЃR�[�h
			String strUsrId = req.getParameter("USR_ID"); // ���O�C�����[�U�R�[�h
			String strOrganizationCode = req.getParameter("ORGANIZATION_CODE"); // �g�D�R�[�h
			String strKmtCode = req.getParameter("KMT_CODE"); // �Ȗڑ̌n�R�[�h

			S2Container container = SingletonS2ContainerFactory.getContainer();
			InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);

			logic.setCompanyCode(strKaiCode);
			logic.setUserCode(strUsrId);

			// �f�[�^�[���擾����
			Map resultMap = logic.getIndicationLevelData(strKmtCode, strOrganizationCode);

			dispatchResultMap(req, resp, resultMap);

		} catch (TException e) {
			// �f�[�^��������Ȃ������ꍇ�A����dispatch���Ȃ��B
			dispatchResultMap(req, resp, new TreeMap());
		}
	}

	/**
	 * �Ȗڏ��Z�b�g
	 * 
	 * @param req
	 * @param resp
	 */
	protected void dispatchItemData(HttpServletRequest req, HttpServletResponse resp) {
		try {

			String itemKind = Util.avoidNull(req.getParameter("ITEM_KIND")); // �Ȗڎ��
			String strKaiCode = Util.avoidNull(req.getParameter("KAI_CODE")); // ��ЃR�[�h
			String kmkCode = Util.avoidNull(req.getParameter("KMK_CODE")); // �ȖڃR�[�h
			String hkmCode = Util.avoidNull(req.getParameter("HKM_CODE")); // �⏕�ȖڃR�[�h
			String ukmCode = Util.avoidNull(req.getParameter("UKM_CODE")); // ����ȖڃR�[�h

			S2Container container = SingletonS2ContainerFactory.getContainer();
			InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);

			logic.setCompanyCode(strKaiCode);

			Map resultMap;

			// �p�����[�^�L���őΏۉȖڂ����߂�
			if ("Item".equals(itemKind)) {
				// �Ȗ�
				resultMap = logic.getItemData(kmkCode);

			} else if ("SubItem".equals(itemKind)) {
				// �⏕�Ȗ�
				resultMap = logic.getSubItemData(kmkCode, hkmCode);

			} else if ("BreakDownItem".equals(itemKind)) {
				// ����Ȗ�
				resultMap = logic.getBreakDownItemData(kmkCode, hkmCode, ukmCode);
			} else {
				dispatchError(req, resp, "S01001");
				return;
			}

			dispatchResultMap(req, resp, resultMap);

		} catch (TException e) {
			// �f�[�^��������Ȃ������ꍇ�A����dispatch���Ȃ��B
			dispatchResultMap(req, resp, new TreeMap());
		}
	}

	/**
	 * ���̂��擾����
	 * 
	 * @param req
	 * @param resp
	 */
	protected void dispatchgIsCode(HttpServletRequest req, HttpServletResponse resp) {
		// ���O�C��������ЃR�[�h
		String strKaiCode = String.valueOf(req.getParameter("kaiCode"));
		// ����ź���/���庰��/�z�����庰��
		String strFieldCode = String.valueOf(req.getParameter("fieldCode"));
		// ����ź���/���庰��/�z�����庰�ނ̋敪
		String strType = String.valueOf(req.getParameter("type"));
		// ��ʂ̑g�D����
		String strOrganizationCode = String.valueOf(req.getParameter("organizationCode"));
		// ��ʂ̊K�w����
		Integer intHierarchicalLevel = Integer.valueOf(String.valueOf(req.getParameter("panelLevel")));
		// �J�����x��
		Integer intkjlLvl = Integer.valueOf(String.valueOf(req.getParameter("Level")));
		// �J������R�[�h
		String strkjlDepCode = String.valueOf(req.getParameter("depCode"));
		// ��ʕ���R�[�h
		String strUpCode = String.valueOf(req.getParameter("upDepCode"));

		// �ԋp����
		String strNameReturn = "";

		// S2Container
		S2Container container = SingletonS2ContainerFactory.getContainer();

		// ��ʕ���̏ꍇ
		if ("UpDep".equals(strType)) {

			InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);
			Integer intHierarchicalLevelUp = Integer.valueOf(intHierarchicalLevel) - 1;
			strNameReturn = logic.organizationSearchNameS(strKaiCode, strOrganizationCode, strFieldCode,
				intHierarchicalLevelUp, intkjlLvl, strkjlDepCode, strType);
			// ����̏ꍇ
		} else if ("Dep".equals(strType)) {

			InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);
			Integer intpanelkjlLvl = Integer.valueOf(intHierarchicalLevel) - 1;
			strNameReturn = logic.organizationSearchNameS(strKaiCode, strOrganizationCode, strFieldCode,
				intHierarchicalLevel, intpanelkjlLvl, strUpCode, strType);
			// ��ʉ�Ђ̏ꍇ
		} else if ("UpCompany".equals(strType)) {

			InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);
			Integer intHierarchicalLevelUp = Integer.valueOf(intHierarchicalLevel) - 1;
			strNameReturn = logic.organizationSearchNameS(strKaiCode, strOrganizationCode, strFieldCode,
				intHierarchicalLevelUp, intkjlLvl, strkjlDepCode, strType);
			// OwnerCompany�̏ꍇ
		} else if ("Company".equals(strType)) {

			InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);
			Integer intpanelkjlLvl = Integer.valueOf(intHierarchicalLevel) - 1;
			strNameReturn = logic.organizationSearchNameS(strKaiCode, strOrganizationCode, strFieldCode,
				intHierarchicalLevel, intpanelkjlLvl, strUpCode, strType);
		}

		dispatchResult(req, resp, "key", strNameReturn);
	}

	/**
	 * ��ЃR���g���[���}�X�^�ꗗ�擾
	 * 
	 * @param req
	 * @param resp
	 */
	protected void dispatchCmpMstData(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = null;

		container = SingletonS2ContainerFactory.getContainer();

		InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);
		// ��ЃR�[�h
		String strKaiCode = req.getParameter("KAI_CODE");

		Map map = new HashMap();
		// ���ʂ��擾����B
		CMP_MST cmpMst = logic.getCmpMstDeta(strKaiCode);
		if (cmpMst != null) {
			map.put("G_SHONIN_FLG", String.valueOf(Util.avoidNull(cmpMst.getCMP_G_SHONIN_FLG())));
			map.put("K_SHONIN_FLG", String.valueOf(Util.avoidNull(cmpMst.getCMP_K_SHONIN_FLG())));
			map.put("CMP_HM_KBN_1", String.valueOf(Util.avoidNull(cmpMst.getCMP_HM_KBN_1())));
			map.put("CMP_HM_KBN_2", String.valueOf(Util.avoidNull(cmpMst.getCMP_HM_KBN_2())));
			map.put("CMP_HM_KBN_3", String.valueOf(Util.avoidNull(cmpMst.getCMP_HM_KBN_3())));
			map.put("CMP_HM_NAME_1", String.valueOf(Util.avoidNull(cmpMst.getCMP_HM_NAME_1())));
			map.put("CMP_HM_NAME_2", String.valueOf(Util.avoidNull(cmpMst.getCMP_HM_NAME_2())));
			map.put("CMP_HM_NAME_3", String.valueOf(Util.avoidNull(cmpMst.getCMP_HM_NAME_3())));
			map.put("FLAG", "1");
		} else {
			map.put("G_SHONIN_FLG", "");
			map.put("K_SHONIN_FLG", "");
			map.put("CMP_HM_KBN_1", "");
			map.put("CMP_HM_KBN_2", "");
			map.put("CMP_HM_KBN_3", "");
			map.put("CMP_HM_NAME_1", "");
			map.put("CMP_HM_NAME_2", "");
			map.put("CMP_HM_NAME_3", "");
			map.put("FLAG", "0");
		}
		dispatchResultMap(req, resp, map);

	}

	/**
	 * �Ǘ��}�X�^���擾
	 * 
	 * @param req
	 * @param resp
	 */
	protected void dispatchKnrMstData(HttpServletRequest req, HttpServletResponse resp) {

		TUserInfo userInfo = refTServerUserInfo(req);
		// ��ЃR�[�h
		String strKaiCode = userInfo.getCompanyCode();
		// �Ǘ��R�[�h
		String strManagementCode = req.getParameter("KNR_CODE");
		// �`�[���t
		String strSlipDate = req.getParameter("SLIP_DATE");
		// �Ǘ��t���O
		String strManagementFlag = req.getParameter("KNR_FLG");

		// �J�n�R�[�h
		String beginCode = req.getParameter("BEGIN_CODE");
		// �I���R�[�h
		String endCode = req.getParameter("END_CODE");

		S2Container container = SingletonS2ContainerFactory.getContainer();

		ManagementLogic logic = (ManagementLogic) container.getComponent(ManagementLogic.class);

		// �p�����[�^�[��ݒ肷��
		Map<String, String> mapCondition = new HashMap<String, String>();
		mapCondition.put("kaiCode", strKaiCode); // ��ЃR�[�h
		mapCondition.put("slipDate", strSlipDate); // �`�[���t
		mapCondition.put("managementFlag", strManagementFlag); // �Ǘ��\�t���O
		mapCondition.put("managementCode", strManagementCode); // �Ǘ��R�[�h
		mapCondition.put("beginCode", beginCode); // �J�n�R�[�h
		mapCondition.put("endCode", endCode); // �I���R�[�h

		Map<String, Object> map = new TreeMap<String, Object>();

		// �Ǘ��}�X�^���擾
		Map KnrInfoMap = logic.getKnrName(mapCondition);

		if (Util.isNullOrEmpty(KnrInfoMap.get("strKnrName"))) {
			map.put("existFlag", "0");
			map.put("KNR_NAME_S_1", "");
			map.put("KNR_NAME_S_2", "");
			map.put("KNR_NAME_S_3", "");
			map.put("KNR_NAME_S_4", "");
			map.put("KNR_NAME_S_5", "");
			map.put("KNR_NAME_S_6", "");
		} else {
			map.put("existFlag", "1");
			// �Ǘ�1���擾����ꍇ
			if ("1".equals(strManagementFlag)) {
				map.put("KNR_NAME_S_1", KnrInfoMap.get("strKnrName"));
				map.put("STR_DATE", KnrInfoMap.get("strStrDate"));
				map.put("END_DATE", KnrInfoMap.get("strEndDate"));
			}
			// �Ǘ�2���擾����ꍇ
			else if ("2".equals(strManagementFlag)) {
				map.put("KNR_NAME_S_2", KnrInfoMap.get("strKnrName"));
				map.put("STR_DATE", KnrInfoMap.get("strStrDate"));
				map.put("END_DATE", KnrInfoMap.get("strEndDate"));
			}
			// �Ǘ�3���擾����ꍇ
			else if ("3".equals(strManagementFlag)) {
				map.put("KNR_NAME_S_3", KnrInfoMap.get("strKnrName"));
				map.put("STR_DATE", KnrInfoMap.get("strStrDate"));
				map.put("END_DATE", KnrInfoMap.get("strEndDate"));
			}
			// �Ǘ�4���擾����ꍇ
			else if ("4".equals(strManagementFlag)) {
				map.put("KNR_NAME_S_4", KnrInfoMap.get("strKnrName"));
				map.put("STR_DATE", KnrInfoMap.get("strStrDate"));
				map.put("END_DATE", KnrInfoMap.get("strEndDate"));
			}
			// �Ǘ�5���擾����ꍇ
			else if ("5".equals(strManagementFlag)) {
				map.put("KNR_NAME_S_5", KnrInfoMap.get("strKnrName"));
				map.put("STR_DATE", KnrInfoMap.get("strStrDate"));
				map.put("END_DATE", KnrInfoMap.get("strEndDate"));
			}
			// �Ǘ�6���擾����ꍇ
			else if ("6".equals(strManagementFlag)) {
				map.put("KNR_NAME_S_6", KnrInfoMap.get("strKnrName"));
				map.put("STR_DATE", KnrInfoMap.get("strStrDate"));
				map.put("END_DATE", KnrInfoMap.get("strEndDate"));
			}
		}

		this.dispatchResultMap(req, resp, map);
	}

	/**
	 * ���Z�i�K���擾����
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	protected void findAccountStage(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);

		// �`�[���t
		String strSlipDate = req.getParameter("SLIP_DATE");
		// ��ЃR�[�h
		String strKaiCode = req.getParameter("KAI_CODE");
		// �`�[���t�̔N�x�ƌ��x���擾����
		int year = BizUtil.getFiscalYear(strSlipDate, strKaiCode);
		int month = BizUtil.getFiscalMonth(strSlipDate, strKaiCode);
		// SIM_MON = 0�̏ꍇ��SIM_MON = 1�Ƃ��Ĉ����Ă��������B
		if (month == 0) {
			month = 1;
		}

		// ���߃R���g���[���}�X�^(SIM_CTL)���猈�Z�敪���擾����
		SIM_CTL simCtl = logic.findSimCtl(strKaiCode, year, month);

		Map<String, Object> map = new HashMap<String, Object>();
		if (simCtl != null) {
			map.put("KSN_KBN", String.valueOf(simCtl.getKSN_KBN() + 1));
		} else {
			// ���݂��Ȃ��ꍇ��1��\������B
			map.put("KSN_KBN", "1");
		}

		// GL�R���g���[����茈�Z�i�K���擾�i���i�K�̌��Z�敪�Ɣ�r���邽�߁j
		GL_CTL_MST glCtl = logic.findGlCtlMstInfo(strKaiCode);
		if (glCtl != null) {
			map.put("KSD_KBN", String.valueOf(glCtl.getKSD_KBN()));
		} else {
			// ���݂��Ȃ��ꍇ��1��\������B
			map.put("KSD_KBN", "1");
		}

		dispatchResultMap(req, resp, map);
	}

	/**
	 * ���Z�������擾����
	 * 
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 */
	protected void getKsdKbn(HttpServletRequest req, HttpServletResponse resp) {

		TUserInfo userInfo = refTServerUserInfo(req);
		// ��ЃR�[�h
		String strKaiCode = userInfo.getCompanyCode();

		S2Container s2Container = SingletonS2ContainerFactory.getContainer();

		InformationLogic logic = (InformationLogic) s2Container.getComponent(InformationLogic.class);

		Map<String, Object> map = new HashMap<String, Object>();

		// GL�R���g���[����茈�Z�i�K���擾�i���i�K�̌��Z�敪�Ɣ�r���邽�߁j
		GL_CTL_MST glCtl = logic.findGlCtlMstInfo(strKaiCode);
		if (glCtl != null) {
			map.put("KSD_KBN", String.valueOf(glCtl.getKSD_KBN()));
		} else {
			// ���݂��Ȃ��ꍇ��1��\������B
			map.put("KSD_KBN", "1");
		}

		// �߂�
		super.dispatchResultMap(req, resp, map);
	}

	/**
	 * �ʉ݃}�X�^�f�[�^������
	 * 
	 * @param req
	 * @param resp
	 */
	protected void findCurMst(HttpServletRequest req, HttpServletResponse resp) {
		try {

			S2Container s2Container = SingletonS2ContainerFactory.getContainer();

			InformationLogic logic = (InformationLogic) s2Container.getComponent(InformationLogic.class);

			TUserInfo userInfo = refTServerUserInfo(req);
			// ��ЃR�[�h
			String strKaiCode = userInfo.getCompanyCode();
			// �ʉݺ���
			String strCurCode = req.getParameter("CUR_CODE");
			// �`�[���t
			String strSlipDate = req.getParameter("DATE");

			Date slipDate = null;

			// �`�[���t
			if (!Util.isNullOrEmpty(strSlipDate)) {
				// �`�[���t
				slipDate = DateUtil.toYMDDate(strSlipDate);
			}

			Map<String, Object> map = new HashMap<String, Object>();

			// �ʉ݃}�X�^�f�[�^���擾����
			CUR_MST curMst = logic.findCurMstInfo(strKaiCode, strCurCode, slipDate);
			if (curMst != null) {

				// �����_�ȉ�����
				int intDecKeta = curMst.getDEC_KETA();
				map.put("DEC_KETA", String.valueOf(intDecKeta));

				// ���[�g�W��
				int rateRow = curMst.getRATE_POW();
				map.put("RATE_POW", String.valueOf(rateRow));

				// ���Z�敪
				int convKbn = curMst.getCONV_KBN();
				map.put("CONV_KBN", String.valueOf(convKbn));

			}

			dispatchResultMap(req, resp, map);
		} catch (ParseException e) {
			// ignore
		}
	}

	/**
	 * �O�݂���ʉ݂Ɋ��Z
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	protected void convertForeign(HttpServletRequest req, HttpServletResponse resp) throws ParseException {

		S2Container s2Container = SingletonS2ContainerFactory.getContainer();

		InformationLogic logic = (InformationLogic) s2Container.getComponent(InformationLogic.class);

		// �����̃p�����[�^��ݒ肷��
		TUserInfo userInfo = refTServerUserInfo(req);
		// ��ЃR�[�h
		String kaiCode = userInfo.getCompanyCode();
		String slipDate = req.getParameter("rateBaseDate"); // ���[�g����t
		String baseCurCode = req.getParameter("baseCurCode");
		String foreginCurCode = req.getParameter("foreginCurCode");
		String money = req.getParameter("money");
		String rate = req.getParameter("rate");

		// �O�݋��z
		BigDecimal foreignMoney = new BigDecimal(money);

		// ���Z���[�g
		double numRate = Double.parseDouble(rate);

		CMP_MST cmpMst = logic.getCmpMstDeta(kaiCode);
		CUR_MST baseCurMst = logic.findCurMstInfo(kaiCode, baseCurCode, null);
		CUR_MST foreginCurMst = logic.findCurMstInfo(kaiCode, foreginCurCode, null);

		// �O�݂���ʉ݂Ɋ��Z���z
		BigDecimal baseMoney = BizUtil.convertToBaseCurrency(foreignMoney, numRate, baseCurMst, foreginCurMst, cmpMst);

		// �M�݋��z�i�[
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("baseMoney", String.valueOf(baseMoney));

		dispatchResultMap(req, resp, map);
	}

	/**
	 * �w��ʉ݂̎w����t�ɂ����郌�[�g��Ԃ�
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	protected void findRate(HttpServletRequest req, HttpServletResponse resp) throws ParseException {

		TUserInfo userInfo = refTServerUserInfo(req);
		// ��ЃR�[�h
		String strKaiCode = userInfo.getCompanyCode();
		// �ʉݺ���
		String strCurCode = req.getParameter("CUR_CODE");
		// �w����t
		String strDate = req.getParameter("OCCUR_DATE");

		// �w��ʉ݂̎w����t�ɂ����郌�[�g��Ԃ�
		double lngRate = BizUtil.getCurrencyRate(strCurCode, DateUtil.toYMDDate(strDate), strKaiCode);

		// ���[�g�i�[
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CUR_RATE", String.valueOf(lngRate));

		dispatchResultMap(req, resp, map);
	}

	/**
	 * ��{�Ȗڑ̌n���擾����
	 * 
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 */
	protected void getItemSystemValue(HttpServletRequest req, HttpServletResponse resp) {

		TUserInfo userInfo = refTServerUserInfo(req);
		// ��ЃR�[�h
		String strKaiCode = userInfo.getCompanyCode();

		// �p�����[�^��Map
		Map conditionMap = new HashMap();

		// ��ЃR�[�h���擾����
		conditionMap.put("KAI_CODE", strKaiCode);
		// ��{�Ȗڑ̌n�R�[�h���擾����
		conditionMap.put("ITEM_SYSTEM_CODE", req.getParameter("ITEM_SYSTEM_CODE"));

		S2Container s2Container = SingletonS2ContainerFactory.getContainer();

		// KmkTkMstLogic���쐬����
		KmkTkMstLogic logic = (KmkTkMstLogic) s2Container.getComponent(KmkTkMstLogic.class);

		// �߂�
		super.dispatchResultMap(req, resp, logic.getItemSystemValue(conditionMap));
	}

}
