package jp.co.ais.trans.common.server.di;

import java.text.*;
import java.util.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �������W�b�N�N���X
 */
public class TAccountItemUnitLogicImpl implements TAccountItemUnitLogic {

	/** �Ȗڌ����pDAO */
	protected KMK_MSTDao kmkMstDao = null;

	/** �⏕�Ȗڌ����pDAO */
	protected HKM_MSTDao hkmMstDao = null;

	/** ����Ȗڌ����pDAO */
	protected UKM_MSTDao ukmMstDao = null;

	/**
	 * �f�t�H���g�R���X�g���N�^
	 * 
	 * @param dao �Ȗڌ����pDAO
	 */
	public TAccountItemUnitLogicImpl(KMK_MSTDao dao) {
		kmkMstDao = dao;
	}

	/**
	 * �⏕�Ȗڌ����pDAO��ݒ肷��B
	 * 
	 * @param hkmMstDao �⏕�Ȗڌ����pDAO
	 */
	public void setHkmMstDao(HKM_MSTDao hkmMstDao) {
		this.hkmMstDao = hkmMstDao;
	}

	/**
	 * ����Ȗڌ����pDAO��ݒ肷��B
	 * 
	 * @param ukmMstDao ����Ȗڌ����pDAO
	 */
	public void setUkmMstDao(UKM_MSTDao ukmMstDao) {
		this.ukmMstDao = ukmMstDao;
	}

	/**
	 * �ȖڃR�[�h�I�����\���f�[�^�擾�B
	 * 
	 * @param inputParameter �p�����[�^�[Map
	 * @return map �f�[�^�}�b�v
	 * @throws TException
	 */
	public Map<String, Object> getItemInfo(AccountItemInputParameter inputParameter) throws TException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// �`�[���t
			if (!Util.isNullOrEmpty(inputParameter.getSlipDate())) {
				// �`�[���t
				inputParameter.setDateSlipDate(DateUtil.toYMDDate(inputParameter.getSlipDate()));
			} else {
				// �`�[���t
				inputParameter.setDateSlipDate(null);
			}

			KMK_MST kmkMst = kmkMstDao.findByParameter(inputParameter);

			if (kmkMst != null) {
				map.put("existFlag", "1");
				// �Ȗڗ���
				map.put("KMK_NAME_S", String.valueOf(kmkMst.getKMK_NAME_S()));
				// �⏕�敪
				map.put("HKM_KBN", String.valueOf(kmkMst.getHKM_KBN()));
				// ����ېœ��̓t���O
				map.put("URI_ZEI_FLG", String.valueOf(kmkMst.getURI_ZEI_FLG()));
				// �d���ېœ��̓t���O
				map.put("SIR_ZEI_FLG", String.valueOf(kmkMst.getSIR_ZEI_FLG()));
				// ����ŃR�[�h
				map.put("ZEI_CODE", kmkMst.getZEI_CODE());
				// �����
				map.putAll(getSzeiMst(inputParameter.getCompanyCode(), kmkMst.getZEI_CODE()));
				// �Ј�����
				map.put("EMP_NAME_S", getEmpName(inputParameter.getCompanyCode(), inputParameter.getEmployeeCode()));
				// �������̓t���O
				map.put("TRI_CODE_FLG", String.valueOf(kmkMst.getTRI_CODE_FLG()));
				// �Ј����̓t���O
				map.put("EMP_CODE_FLG", String.valueOf(kmkMst.getEMP_CODE_FLG()));
				// �Ǘ�1���̓t���O
				map.put("KNR_FLG_1", String.valueOf(kmkMst.getKNR_FLG_1()));
				// �Ǘ�2���̓t���O
				map.put("KNR_FLG_2", String.valueOf(kmkMst.getKNR_FLG_2()));
				// �Ǘ�3���̓t���O
				map.put("KNR_FLG_3", String.valueOf(kmkMst.getKNR_FLG_3()));
				// �Ǘ�4���̓t���O
				map.put("KNR_FLG_4", String.valueOf(kmkMst.getKNR_FLG_4()));
				// �Ǘ�5���̓t���O
				map.put("KNR_FLG_5", String.valueOf(kmkMst.getKNR_FLG_5()));
				// �Ǘ�6���̓t���O
				map.put("KNR_FLG_6", String.valueOf(kmkMst.getKNR_FLG_6()));
				// ���v����1���̓t���O
				map.put("HM_FLG_1", String.valueOf(kmkMst.getHM_FLG_1()));
				// ���v����2���̓t���O
				map.put("HM_FLG_2", String.valueOf(kmkMst.getHM_FLG_2()));
				// ���v����3���̓t���O
				map.put("HM_FLG_3", String.valueOf(kmkMst.getHM_FLG_3()));
				// ���������̓t���O
				map.put("HAS_FLG", String.valueOf(kmkMst.getHAS_FLG()));
				// ���ʉݓ��̓t���O
				map.put("MCR_FLG", String.valueOf(kmkMst.getMCR_FLG()));
				// �Ј��R�[�h
				map.put("EMP_CODE", inputParameter.getEmployeeCode());
				// GL�Ȗڐ���敪
				map.put("KMK_CNT_GL", String.valueOf(kmkMst.getKMK_CNT_GL()));
				// AP�Ȗڐ���敪
				map.put("KMK_CNT_AP", String.valueOf(kmkMst.getKMK_CNT_AP()));
				// AR�Ȗڐ���敪
				map.put("KMK_CNT_AR", String.valueOf(kmkMst.getKMK_CNT_AR()));

			} else {
				map.put("existFlag", "0");
				// �Ȗڗ���
				map.put("KMK_NAME_S", "");
			}

			return map;
		} catch (ParseException e) {
			throw new TException(e, "E00009");
		}
	}

	/**
	 * �Ȗڈꗗ����
	 * 
	 * @param inputParameter �p�����[�^
	 * @return �Ȗڃf�[�^
	 * @throws TException
	 */
	public List getItemInfoAll(AccountItemInputParameter inputParameter) throws TException {
		try {
			// �`�[���t
			if (!Util.isNullOrEmpty(inputParameter.getSlipDate())) {
				// �`�[���t
				inputParameter.setDateSlipDate(DateUtil.toYMDDate(inputParameter.getSlipDate()));
			} else {
				// �`�[���t
				inputParameter.setDateSlipDate(null);
			}

			// �f�[�^���擾����B
			return kmkMstDao.findListByParameter(inputParameter);
		} catch (ParseException e) {
			throw new TException(e, "E00009");
		}
	}

	/**
	 * �⏕�ȖڃR�[�h�I�����\���f�[�^�擾�B
	 * 
	 * @param inputParameter �p�����[�^�[Map
	 * @return map �f�[�^�}�b�v
	 * @throws TException
	 */
	public Map<String, Object> getSubItemInfo(AccountItemInputParameter inputParameter) throws TException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// �`�[���t
			if (!Util.isNullOrEmpty(inputParameter.getSlipDate())) {
				// �`�[���t
				inputParameter.setDateSlipDate(DateUtil.toYMDDate(inputParameter.getSlipDate()));
			} else {
				// �`�[���t
				inputParameter.setDateSlipDate(null);
			}

			HKM_MST hkmMst = hkmMstDao.findByParameter(inputParameter);

			if (hkmMst != null) {
				map.put("existFlag", "1");
				// �Ȗڗ���
				map.put("HKM_NAME_S", String.valueOf(hkmMst.getHKM_NAME_S()));
				// �⏕�敪
				map.put("UKM_KBN", String.valueOf(hkmMst.getUKM_KBN()));
				// ����ېœ��̓t���O
				map.put("URI_ZEI_FLG", String.valueOf(hkmMst.getURI_ZEI_FLG()));
				// �d���ېœ��̓t���O
				map.put("SIR_ZEI_FLG", String.valueOf(hkmMst.getSIR_ZEI_FLG()));
				// ����ŃR�[�h
				map.put("ZEI_CODE", hkmMst.getZEI_CODE());
				// ����ŃR�[�h
				map.putAll(getSzeiMst(inputParameter.getCompanyCode(), hkmMst.getZEI_CODE()));
				// �Ј�����
				map.put("EMP_NAME_S", getEmpName(inputParameter.getCompanyCode(), inputParameter.getEmployeeCode()));
				// �������̓t���O
				map.put("TRI_CODE_FLG", String.valueOf(hkmMst.getTRI_CODE_FLG()));
				// �Ј����̓t���O
				map.put("EMP_CODE_FLG", String.valueOf(hkmMst.getEMP_CODE_FLG()));
				// �Ǘ�1���̓t���O
				map.put("KNR_FLG_1", String.valueOf(hkmMst.getKNR_FLG_1()));
				// �Ǘ�2���̓t���O
				map.put("KNR_FLG_2", String.valueOf(hkmMst.getKNR_FLG_2()));
				// �Ǘ�3���̓t���O
				map.put("KNR_FLG_3", String.valueOf(hkmMst.getKNR_FLG_3()));
				// �Ǘ�4���̓t���O
				map.put("KNR_FLG_4", String.valueOf(hkmMst.getKNR_FLG_4()));
				// �Ǘ�5���̓t���O
				map.put("KNR_FLG_5", String.valueOf(hkmMst.getKNR_FLG_5()));
				// �Ǘ�6���̓t���O
				map.put("KNR_FLG_6", String.valueOf(hkmMst.getKNR_FLG_6()));
				// ���v����1���̓t���O
				map.put("HM_FLG_1", String.valueOf(hkmMst.getHM_FLG_1()));
				// ���v����2���̓t���O
				map.put("HM_FLG_2", String.valueOf(hkmMst.getHM_FLG_2()));
				// ���v����3���̓t���O
				map.put("HM_FLG_3", String.valueOf(hkmMst.getHM_FLG_3()));
				// ���������̓t���O
				map.put("HAS_FLG", String.valueOf(hkmMst.getHAS_FLG()));
				// ���ʉݓ��̓t���O
				map.put("MCR_FLG", String.valueOf(hkmMst.getMCR_FLG()));
				// �Ј��R�[�h
				map.put("EMP_CODE", inputParameter.getEmployeeCode());
			} else {
				map.put("existFlag", "0");
				// �Ȗڗ���
				map.put("HKM_NAME_S", "");
			}

			return map;
		} catch (ParseException e) {
			throw new TException(e, "E00009");
		}
	}

	/**
	 * �⏕�Ȗڈꗗ����
	 * 
	 * @param inputParameter �p�����[�^
	 * @return �⏕�Ȗڃf�[�^
	 */
	public List getSubItemInfoAll(AccountItemInputParameter inputParameter) throws TException {
		try {
			// �`�[���t
			if (!Util.isNullOrEmpty(inputParameter.getSlipDate())) {
				// �`�[���t
				inputParameter.setDateSlipDate(DateUtil.toYMDDate(inputParameter.getSlipDate()));
			} else {
				// �`�[���t
				inputParameter.setDateSlipDate(null);
			}
			// �f�[�^���擾����B
			return hkmMstDao.findListByParameter(inputParameter);

		} catch (ParseException e) {
			throw new TException(e, "E00009");
		}
	}

	/**
	 * ����ȖڃR�[�h�I�����\���f�[�^�擾�B
	 * 
	 * @param inputParameter �p�����[�^�[Map
	 * @return map �f�[�^�}�b�v
	 * @throws TException
	 */
	public Map<String, Object> getBreakDownItemInfo(AccountItemInputParameter inputParameter) throws TException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			// �`�[���t
			if (!Util.isNullOrEmpty(inputParameter.getSlipDate())) {
				// �`�[���t
				inputParameter.setDateSlipDate(DateUtil.toYMDDate(inputParameter.getSlipDate()));
			} else {
				// �`�[���t
				inputParameter.setDateSlipDate(null);
			}

			UKM_MST ukmMst = ukmMstDao.findByParameter(inputParameter);

			if (ukmMst != null) {
				map.put("existFlag", "1");
				// �Ȗڗ���
				map.put("UKM_NAME_S", String.valueOf(ukmMst.getUKM_NAME_S()));
				// ����ېœ��̓t���O
				map.put("URI_ZEI_FLG", String.valueOf(ukmMst.getURI_ZEI_FLG()));
				// �d���ېœ��̓t���O
				map.put("SIR_ZEI_FLG", String.valueOf(ukmMst.getSIR_ZEI_FLG()));
				// ����ŃR�[�h
				map.put("ZEI_CODE", ukmMst.getZEI_CODE());
				// ����ŃR�[�h
				map.putAll(getSzeiMst(inputParameter.getCompanyCode(), ukmMst.getZEI_CODE()));
				// �Ј�����
				map.put("EMP_NAME_S", getEmpName(inputParameter.getCompanyCode(), inputParameter.getEmployeeCode()));
				// �������̓t���O
				map.put("TRI_CODE_FLG", String.valueOf(ukmMst.getTRI_CODE_FLG()));
				// �Ј����̓t���O
				map.put("EMP_CODE_FLG", String.valueOf(ukmMst.getEMP_CODE_FLG()));
				// �Ǘ�1���̓t���O
				map.put("KNR_FLG_1", String.valueOf(ukmMst.getKNR_FLG_1()));
				// �Ǘ�2���̓t���O
				map.put("KNR_FLG_2", String.valueOf(ukmMst.getKNR_FLG_2()));
				// �Ǘ�3���̓t���O
				map.put("KNR_FLG_3", String.valueOf(ukmMst.getKNR_FLG_3()));
				// �Ǘ�4���̓t���O
				map.put("KNR_FLG_4", String.valueOf(ukmMst.getKNR_FLG_4()));
				// �Ǘ�5���̓t���O
				map.put("KNR_FLG_5", String.valueOf(ukmMst.getKNR_FLG_5()));
				// �Ǘ�6���̓t���O
				map.put("KNR_FLG_6", String.valueOf(ukmMst.getKNR_FLG_6()));
				// ���v����1���̓t���O
				map.put("HM_FLG_1", String.valueOf(ukmMst.getHM_FLG_1()));
				// ���v����2���̓t���O
				map.put("HM_FLG_2", String.valueOf(ukmMst.getHM_FLG_2()));
				// ���v����3���̓t���O
				map.put("HM_FLG_3", String.valueOf(ukmMst.getHM_FLG_3()));
				// ���������̓t���O
				map.put("HAS_FLG", String.valueOf(ukmMst.getHAS_FLG()));
				// ���ʉݓ��̓t���O
				map.put("MCR_FLG", String.valueOf(ukmMst.getMCR_FLG()));
				// �Ј��R�[�h
				map.put("EMP_CODE", inputParameter.getEmployeeCode());
			} else {
				map.put("existFlag", "0");
				// �Ȗڗ���
				map.put("UKM_NAME_S", "");
			}

			return map;

		} catch (ParseException e) {
			throw new TException(e, "E00009");
		}
	}

	/**
	 * ����Ȗڈꗗ����
	 * 
	 * @param inputParameter �p�����[�^
	 * @return ����Ȗڃf�[�^
	 * @throws TException
	 */
	public List getBreakDownItemInfoAll(AccountItemInputParameter inputParameter) throws TException {
		try {
			// �`�[���t
			if (!Util.isNullOrEmpty(inputParameter.getSlipDate())) {
				// �`�[���t
				inputParameter.setDateSlipDate(DateUtil.toYMDDate(inputParameter.getSlipDate()));
			} else {
				// �`�[���t
				inputParameter.setDateSlipDate(null);
			}

			// �f�[�^���擾����B
			return ukmMstDao.findListByParameter(inputParameter);

		} catch (ParseException e) {
			throw new TException(e, "E00009");
		}
	}

	/**
	 * �Ј����̂��擾����B
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param empCode �Ј��R�[�h
	 * @return ZeiName �Ј�����
	 */
	protected String getEmpName(String kaiCode, String empCode) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		EMP_MSTDao emp = (jp.co.ais.trans.master.dao.EMP_MSTDao) container
			.getComponent(jp.co.ais.trans.master.dao.EMP_MSTDao.class);

		EMP_MST empMst = emp.getEMP_MSTByKaicodeEmpcode(kaiCode, empCode);
		if (empMst != null) {
			return empMst.getEMP_NAME_S();
		}

		return "";
	}

	/**
	 * ����ŏ����擾
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param zeiCode ����ŃR�[�h
	 * @return ZeiName ����ŗ���
	 */
	protected Map<String, Object> getSzeiMst(String kaiCode, String zeiCode) {

		if (Util.isNullOrEmpty(zeiCode)) {
			return new HashMap<String, Object>(0);
		}

		Map<String, Object> map = new HashMap<String, Object>();

		S2Container container = SingletonS2ContainerFactory.getContainer();
		SZEI_MSTDao szei = (jp.co.ais.trans.master.dao.SZEI_MSTDao) container
			.getComponent(jp.co.ais.trans.master.dao.SZEI_MSTDao.class);

		SZEI_MST szeiMst = szei.getSZEI_MSTByKaicodeSzeicode(kaiCode, zeiCode);

		if (szeiMst != null) {
			map.put("ZEI_NAME_S", szeiMst.getZEI_NAME_S());

			// �d�������ォ��ېł�
			String szeiKeiKbn = "0";

			if (szeiMst.getUS_KBN() != 0 && szeiMst.getZEI_RATE() != 0) {
				szeiKeiKbn = Integer.toString(szeiMst.getUS_KBN());
			}

			map.put("SZEI_KEI_KBN", szeiKeiKbn);

		}

		return map;
	}
}
