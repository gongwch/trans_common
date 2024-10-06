package jp.co.ais.trans.common.server.di;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.server.dao.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.dao.CMP_MSTDao;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.entity.CMP_MST;

/**
 * ���擾���W�b�N
 */
public class InformationLogicImpl implements InformationLogic {

	/** �J�����x���}�X�^DAO */
	private IndicationLevelDao levelDao;

	/** ����K�w�}�X�^DAO */
	private DPK_MSTDao dpkMstDao;

	/** ����}�X�^DAO */
	private BMN_MSTDao bmnMstDao;

	/** �Ȗڃ}�X�^DAO */
	private KMK_MSTDao kmkMstDao;

	/** �⏕�Ȗڃ}�X�^DAO */
	private HKM_MSTDao hkmMstDao;

	/** ����Ȗڃ}�X�^DAO */
	private UKM_MSTDao ukmMstDao;

	/** ��ЃR�[�h */
	private String companyCode;

	/** ���[�U�R�[�h */
	private String userCode;

	/** ��Ѓ}�X�^Dao */
	private CMP_MSTDao cmpMstDao;

	/** ���Z�R���g���[���}�X�^Dao */
	private GL_CTL_MSTDao glCtlMstDao;

	/** ���ߐ���e�[�u��Dao */
	private SIM_CTLDao simCtlDao;

	/** �ʉ݃}�X�^�����pDAO */
	private CUR_MSTDao curMstDao;

	/** ��ЊK�w�}�X�^�����pDAO */
	private EVK_MSTDao evkMstDao;
	
	/** ���ݒ�}�X�^Dao */
	private ENV_MSTDao envMstDao;

	/**
	 * �J�����x���}�X�^DAO�ݒ�
	 * 
	 * @param levelDao
	 */
	public void setIndicationLevelDao(IndicationLevelDao levelDao) {
		this.levelDao = levelDao;
	}

	/**
	 * ����K�w�}�X�^DAO�̐ݒ�.
	 * 
	 * @param dPK_MSTDao DPK_MSTDao
	 */
	public void setDPK_MSTDao(DPK_MSTDao dPK_MSTDao) {
		this.dpkMstDao = dPK_MSTDao;
	}

	/**
	 * ����}�X�^DAO�̐ݒ�.
	 * 
	 * @param bMN_MSTDao BMN_MSTDao
	 */
	public void setBMN_MSTDao(BMN_MSTDao bMN_MSTDao) {
		this.bmnMstDao = bMN_MSTDao;
	}

	/**
	 * �Ȗڃ}�X�^DAO�ݒ�
	 * 
	 * @param kmkMstDao
	 */
	public void setKMK_MSTDao(KMK_MSTDao kmkMstDao) {
		this.kmkMstDao = kmkMstDao;
	}

	/**
	 * �⏕�Ȗڃ}�X�^DAO�ݒ�
	 * 
	 * @param hkmMstDao
	 */
	public void setHKM_MSTDao(HKM_MSTDao hkmMstDao) {
		this.hkmMstDao = hkmMstDao;
	}

	/**
	 * ����Ȗڃ}�X�^DAO�ݒ�
	 * 
	 * @param ukmMstDao
	 */
	public void setUKM_MSTDao(UKM_MSTDao ukmMstDao) {
		this.ukmMstDao = ukmMstDao;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * ���[�U�R�[�h
	 * 
	 * @param userCode ���[�U�R�[�h
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * ��ЃR���g���[���}�X�^�}�X�^DAO�ݒ�
	 * 
	 * @param cmpMstDao
	 */
	public void setCMP_MSTDao(CMP_MSTDao cmpMstDao) {
		this.cmpMstDao = cmpMstDao;
	}

	/**
	 * ���Z�R���g���[���}�X�^Dao�C���X�^���X�̐ݒ�<BR>
	 * dicon�t�@�C����property�Ƃ��Ē�`����GL_CTL_MSTDao���n�����B
	 * 
	 * @param glCtlMstDao ���Z�R���g���[���}�X�^Dao
	 */
	public void setGlCtlMstDao(GL_CTL_MSTDao glCtlMstDao) {
		this.glCtlMstDao = glCtlMstDao;
	}

	/**
	 * ���ߐ���e�[�u��Dao�C���X�^���X�̐ݒ�<BR>
	 * dicon�t�@�C����property�Ƃ��Ē�`����SimCtlDao���n�����B
	 * 
	 * @param simCtlDao ���ߐ���e�[�u��Dao
	 */
	public void setSimCtlDao(SIM_CTLDao simCtlDao) {
		this.simCtlDao = simCtlDao;
	}

	/**
	 * �ʉ݃}�X�^�����pDAO��ݒ肷��B
	 * 
	 * @param curMstDao �ʉ݃}�X�^�����pDAO
	 */
	public void setCurMstDao(CUR_MSTDao curMstDao) {
		this.curMstDao = curMstDao;
	}

	/**
	 * ��ЊK�w�}�X�^�����pDAO��ݒ肷��B
	 * 
	 * @param evkMstDao ��ЊK�w�}�X�^�����pDAO
	 */
	public void setEvkMstDao(EVK_MSTDao evkMstDao) {
		this.evkMstDao = evkMstDao;
	}
	
	/**
	 * ���ݒ�}�X�^�����pDAO��ݒ肷��B
	 * 
	 * @param envMstDao ���ݒ�}�X�^�����pDAO
	 */
	public void setEnvMstDao(ENV_MSTDao envMstDao) {
		this.envMstDao = envMstDao;
	}

	/**
	 * ����P�ʂ̑g�D�R�[�h���X�g���擾
	 * 
	 * @return �g�D�R�[�h���X�g
	 */
	public String[] getOrganizationCodeList() {

		// ����K�wϽ�.�g�D���ނ������Ɍ�������
		List<DPK_MST> dpkList = dpkMstDao.getDpkSsk(this.companyCode);

		String[] orgCodes = new String[dpkList.size()];

		for (int i = 0; i < dpkList.size(); i++) {
			orgCodes[i] = dpkList.get(i).getDPK_SSK();
		}

		return orgCodes;
	}

	/**
	 * ��ВP�ʂ̑g�D�R�[�h���X�g���擾
	 * 
	 * @return �g�D�R�[�h���X�g
	 */
	public String[] getCmpOrganizationCodeList() {

		// ��ЊK�wϽ�.�g�D���ނ������Ɍ�������
		List<EVK_MST> envList = evkMstDao.getDpkSsk(this.companyCode);

		String[] orgCodes = new String[envList.size()];

		for (int i = 0; i < envList.size(); i++) {
			orgCodes[i] = envList.get(i).getDPK_SSK();
		}

		return orgCodes;
	}

	/**
	 * �J�����x�������擾����
	 * 
	 * @param kmkCode �Ȗڑ̌n�R�[�h
	 * @param orgCode �g�D�R�[�h
	 * @return �J�����x��
	 * @throws TException
	 */
	public Map getIndicationLevelData(String kmkCode, String orgCode) throws TException {

		IndicationLevel entity = levelDao.getIndicationLevel(companyCode, userCode, kmkCode, orgCode);

		if (entity == null) {
			throw new TException("W00100", "C00057", companyCode, userCode, kmkCode, orgCode);
		}

		return entity.toObjectMap();
	}

	/**
	 * �Ȗړ��������擾����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param kmkCode �ȖڃR�[�h
	 * @param hkmCode �⏕�ȖڃR�[�h
	 * @param ukmCode ����ȖڃR�[�h
	 * @return ItemUnionData �Ȗړ������f�[�^
	 */
	public ItemUnionData getItemUnionData(String kaiCode, String kmkCode, String hkmCode, String ukmCode) {

		ItemUnionData bean = new ItemUnionData();

		bean.setKAI_CODE(kaiCode);
		bean.setKMK_MST(getItemDataBean(kaiCode, kmkCode));
		bean.setHKM_MST(getSubItemDataBean(kaiCode, kmkCode, hkmCode));
		bean.setUKM_MST(getBreakDownItemDataBean(kaiCode, kmkCode, hkmCode, ukmCode));

		return bean;
	}

	/**
	 * �Ȗڏ����擾����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param kmkCode �ȖڃR�[�h
	 * @return KMK_MST �Ȗڏ��
	 */
	public KMK_MST getItemDataBean(String kaiCode, String kmkCode) {

		KMK_MST kmkMst = new KMK_MST();
		kmkMst = this.kmkMstDao.getKMK_MSTByKaicodeKmkcode(kaiCode, kmkCode);

		return kmkMst;
	}

	/**
	 * �⏕�Ȗڏ����擾����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param kmkCode �ȖڃR�[�h
	 * @param hkmCode �⏕�ȖڃR�[�h
	 * @return HKM_MST �⏕�Ȗڏ��
	 */
	public HKM_MST getSubItemDataBean(String kaiCode, String kmkCode, String hkmCode) {

		HKM_MST hkmMst = new HKM_MST();
		hkmMst = this.hkmMstDao.getHKM_MSTByKaicodeKmkcodeHkmcode(kaiCode, kmkCode, hkmCode);

		return hkmMst;
	}

	/**
	 * ����Ȗڏ����擾����
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param kmkCode �ȖڃR�[�h
	 * @param hkmCode �⏕�ȖڃR�[�h
	 * @param ukmCode ����ȖڃR�[�h
	 * @return UKM_MST ����Ȗڏ��
	 */
	public UKM_MST getBreakDownItemDataBean(String kaiCode, String kmkCode, String hkmCode, String ukmCode) {

		UKM_MST ukmMst = new UKM_MST();
		ukmMst = this.ukmMstDao.getUKM_MSTByKaicodeKmkcodeHkmcodeUkmcode(kaiCode, kmkCode, hkmCode, ukmCode);

		return ukmMst;
	}

	/**
	 * �Ȗڏ����擾����
	 * 
	 * @param kmkCode �ȖڃR�[�h
	 * @return �Ȗڏ��
	 * @throws TException
	 */
	public Map getItemData(String kmkCode) throws TException {

		KMK_MST kmkMst = this.kmkMstDao.getKMK_MSTByKaicodeKmkcode(companyCode, kmkCode);

		if (kmkMst == null) {
			throw new TException("W00100", "C00077", companyCode, kmkCode);
		}

		return kmkMst.toObjectMap();
	}

	/**
	 * �⏕�Ȗڏ����擾����
	 * 
	 * @param kmkCode �ȖڃR�[�h
	 * @param hkmCode �⏕�ȖڃR�[�h
	 * @return �⏕�Ȗڏ��
	 * @throws TException
	 */
	public Map getSubItemData(String kmkCode, String hkmCode) throws TException {

		// KMK_MST���擾����
		HKM_MST hkmMst = this.hkmMstDao.getHKM_MSTByKaicodeKmkcodeHkmcode(companyCode, kmkCode, hkmCode);

		if (hkmMst == null) {
			throw new TException("W00100", "C00488", companyCode, kmkCode, hkmCode);
		}

		return hkmMst.toObjectMap();
	}

	/**
	 * ����Ȗڏ����擾����
	 * 
	 * @param kmkCode �ȖڃR�[�h
	 * @param hkmCode �⏕�ȖڃR�[�h
	 * @param ukmCode ����ȖڃR�[�h
	 * @return ����Ȗڏ��
	 * @throws TException
	 */
	public Map getBreakDownItemData(String kmkCode, String hkmCode, String ukmCode) throws TException {

		// KMK_MST���擾����
		UKM_MST ukmMst = this.ukmMstDao
			.getUKM_MSTByKaicodeKmkcodeHkmcodeUkmcode(companyCode, kmkCode, hkmCode, ukmCode);

		if (ukmMst == null) {
			throw new TException("W00100", "C00025", companyCode, kmkCode, hkmCode, ukmCode);
		}

		return ukmMst.toObjectMap();
	}

	/**
	 * �g�D�R���|�[�l���g�̗��̂��擾����B<BR>
	 * 
	 * @param strKaiCode ��ЃR�[�h
	 * @param strDpkSsk ��ʓ��͂̑g�D����
	 * @param strDepCode ��ʓ��͂̕��庰��
	 * @param intpanelLevel ���嗪�̂��������ɁA��ʓ��͂����K�w���فB ��ʕ��嗪�̂��������ɁA��ʓ��͂����K�w���x��-1
	 * @param intkjlLvl ��ʕ��嗪�̂��������ɁA�J�����x���i�������̃��x���j ���嗪�̂��������ɁA��ʓ��͂����K�w���x��-1
	 * @param strkjlDepCode ��ʕ��嗪�̂��������ɁA�J�����庰�ށi�������̕���R�[�h�j�B ���嗪�̂��������ɁA��ʓ��͂�����ʕ���R�[�h
	 * @param strType �g�D�R���|�[�l���g�̃^�C�v
	 * @return ���嗪��
	 */
	public String organizationSearchNameS(String strKaiCode, String strDpkSsk, String strDepCode,
		Integer intpanelLevel, Integer intkjlLvl, String strkjlDepCode, String strType) {

		String name_s = "";
		// ����̏ꍇ
		if (strType.equals("UpDep") || strType.equals("Dep")) {

			name_s = bmnMstDao.getDepNameS(strKaiCode, strDpkSsk, strDepCode, intpanelLevel, intkjlLvl, strkjlDepCode);
			// ��Ђ̏ꍇ
		} else if (strType.equals("UpCompany") || strType.equals("Company")) {
			
			name_s = envMstDao.getCmpNameS(strKaiCode, strDpkSsk, strDepCode, intpanelLevel, intkjlLvl, strkjlDepCode);
		}

		return name_s;

	}

	/**
	 * ��Ѓ}�X�^�f�[�^�擾
	 */
	public CMP_MST getCmpMstDeta(String kaiCode) {
		return cmpMstDao.getCMP_MST_ByKaicode(kaiCode);
	}

	/**
	 * ���ߐ���e�[�u���f�[�^������
	 * 
	 * @param kaiCode
	 * @param year
	 * @param month
	 * @return ���ߐ���e�[�u���f�[�^
	 */
	public SIM_CTL findSimCtl(String kaiCode, int year, int month) {

		return simCtlDao.getSimCtlByKaiCodeNendoSimmon(kaiCode, year, month);
	}

	/**
	 * GL�R���g���[������
	 * 
	 * @param strKaiCode ��ЃR�[�h
	 * @return GL�R���g���[��
	 */
	public GL_CTL_MST findGlCtlMstInfo(String strKaiCode) {
		return glCtlMstDao.getGL_CTL_MSTByIKaicode(strKaiCode);
	}

	/**
	 * �ʉ݃}�X�^����
	 * 
	 * @param strKaiCode ��ЃR�[
	 * @param strCurCode �ʉ݃R�[�h
	 * @param slipDate �`�[���t
	 * @return �ʉ݃}�X�^���X�g
	 */
	public CUR_MST findCurMstInfo(String strKaiCode, String strCurCode, Date slipDate) {
		return curMstDao.searchCurMstInfo(strKaiCode, strCurCode, slipDate);
	}

}
