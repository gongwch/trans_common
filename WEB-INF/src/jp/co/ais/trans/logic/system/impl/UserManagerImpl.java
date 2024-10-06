package jp.co.ais.trans.logic.system.impl;

import java.util.*;

import org.seasar.framework.container.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.logic.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���[�U�F�؃r�W�l�X���W�b�N
 * 
 * @author AIS
 */
public class UserManagerImpl implements UserManager {

	/** �R���e�i */
	protected S2Container container;

	/** ���[�U�}�X�^Dao */
	protected USR_MSTDao usrDao;

	/** ���}�X�^Dao */
	protected ENV_MSTDao envDao;

	/** ����}�X�^Dao */
	protected BMN_MSTDao bmnDao;

	/** ��ЃR���g���[��Dao */
	protected CMP_MSTDao cmpDao;

	/** PCI�`�F�b�N�R���g���[��Dao */
	protected PCI_CHK_CTLDao pciDao;

	/** �ʉ݃}�X�^DAO */
	protected CUR_MSTDao curDao;

	/** �Ј��}�X�^DAO */
	protected EMP_MSTDao empDao;

	/** ��ЃR�[�h */
	protected String companyCode;

	/** ���[�U�R�[�h */
	protected String userCode;

	/** �G���[���b�Z�[�W */
	protected String errorMsg;

	/** ���[�U�}�X�^ */
	protected USR_MST usr;

	/** ����}�X�^ */
	private BMN_MST bmn;

	/** �Ј��}�X�^ */
	private EMP_MST emp;

	/** ���ݒ�}�X�^ */
	protected ENV_MST env;

	/** ��ЃR���g���[���}�X�^ */
	protected CMP_MST cmp;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param container �R���e�i
	 */
	public UserManagerImpl(S2Container container) {
		this.container = container;
	}

	/**
	 * @param bmnDao
	 */
	public void setBmnDao(BMN_MSTDao bmnDao) {
		this.bmnDao = bmnDao;
	}

	/**
	 * @param cmpDao
	 */
	public void setCmpDao(CMP_MSTDao cmpDao) {
		this.cmpDao = cmpDao;
	}

	/**
	 * @param curDao
	 */
	public void setCurDao(CUR_MSTDao curDao) {
		this.curDao = curDao;
	}

	/**
	 * @param envDao
	 */
	public void setEnvDao(ENV_MSTDao envDao) {
		this.envDao = envDao;
	}

	/**
	 * @param pciDao
	 */
	public void setPciDao(PCI_CHK_CTLDao pciDao) {
		this.pciDao = pciDao;
	}

	/**
	 * @param usrDao
	 */
	public void setUsrDao(USR_MSTDao usrDao) {
		this.usrDao = usrDao;
	}

	/**
	 * @param empDao
	 */
	public void setEmpDao(EMP_MSTDao empDao) {
		this.empDao = empDao;
	}

	/**
	 * ��ЃR�[�h�ݒ�
	 * 
	 * @param compCode ��ЃR�[�h
	 */
	public void setCompanyCode(String compCode) {
		this.companyCode = compCode;
	}

	/**
	 * ���[�U�R�[�h�ݒ�
	 * 
	 * @param userCode ���[�U�R�[�h
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * �Ή��G���[���b�Z�[�W
	 * 
	 * @return int errorMsg
	 */
	public String getErrorMsg() {
		return this.errorMsg;
	}

	/**
	 * ���O�C������
	 */
	public void login() {
		// ���O�C�����̏�������
		if (ServerConfig.isLoginManagedMode()) {
			PCI_CHK_CTL pcientity = new PCI_CHK_CTL();
			// ��ЃR�[�hSET
			pcientity.setKAI_CODE(companyCode);
			// ���[�U�[�R�[�hSET
			pcientity.setUSR_ID(userCode);
			// �`�F�b�N�C������SET
			pcientity.setPCI_CHECK_IN_TIME(Util.getCurrentDate());

			// �C���T�[�g
			pciDao.insert(pcientity);
		}

		// �����r������
		this.unlockAll();
	}

	/**
	 * ���O�A�E�g����
	 */
	public void logout() {

		// ���O�C�����̍폜
		pciDao.delete(companyCode, userCode);

		// �����r������
		this.unlockAll();
	}

	/**
	 * �����r������
	 */
	private void unlockAll() {
		Lock lock = (Lock) container.getComponent(Lock.class);
		lock.setCode(this.companyCode, this.userCode);
		lock.unlockAll();
	}

	/**
	 * ���[�U���ƍ�����i���O�C���F�؁j.<br>
	 * <ul>
	 * <li> ��ЃR�[�h�`�F�b�N
	 * <li> ���[�U�[���E�p�X���[�h�`�F�b�N
	 * <li> ���d���O�C������
	 * <li> ���O�C���K�萔����
	 * </ul>
	 * 
	 * @param password ���[�U�p�X���[�h
	 * @return true �F�ؐ��� false �F�؎��s
	 */
	public boolean collatedUser(String password) {

		ENV_MST envMst = envDao.getENV_MSTByKaicode(companyCode);

		// ��Б��݃`�F�b�N
		if (envMst == null) {
			errorMsg = "W01002"; // �w�肳�ꂽ��ЃR�[�h�͌�����܂���ł���
			return false;
		}

		USR_MST usrMst = usrDao.getUSR_MSTForLogin(companyCode, userCode, DateUtil.toYMDDate(Util.getCurrentDate()));

		// ���[�U���݃`�F�b�N(�L�����Ԋ܂�)
		if (usrMst == null) {
			this.errorMsg = "W01003"; // ���[�U�A�܂��́A�p�X���[�h�Ɍ�肪����܂�
			return false;
		}

		// ���d���O�C���`�F�b�N
		PCI_CHK_CTL pciChkCtl = pciDao.getPCI_CHK_CTLByKaicodeUsrid(companyCode, userCode);
		if (pciChkCtl != null) {
			this.errorMsg = "W01004"; // ���Ƀ��O�C�����Ă���
			return false;
		}

		// ���b�N�A�E�g���W�b�N
		UserLockout loLogic = (UserLockout) container.getComponent(UserLockout.class);
		loLogic.setCode(this.companyCode, this.userCode);

		// ���b�N�A�E�g�m�F
		if (loLogic.isLockoutManaged() && loLogic.isLockoutStatus()) {
			this.errorMsg = "W01009"; // ���b�N�A�E�g����Ă��܂�
			return false;
		}

		// �p�X���[�h���W�b�N
		UserPassword pwLogic = (UserPassword) container.getComponent(UserPassword.class);
		pwLogic.setCode(this.companyCode, this.userCode);

		// �p�X���[�h�F��
		if (!pwLogic.equalsNowPassword(password)) {
			this.errorMsg = "W01003"; // ���[�U�A�܂��́A�p�X���[�h�Ɍ�肪����܂�

			if (loLogic.isLockoutManaged()) {
				// �p�X���[�h�s�����͂Ń��b�N�A�E�g�J�E���g�A�b�v
				loLogic.countUp();
			}

			return false;
		}

		// ���O�C���K�萔�`�F�b�N
		if (ServerConfig.getRegulatedNumber() > 0 && pciDao.getCount(companyCode) >= ServerConfig.getRegulatedNumber()) {
			this.errorMsg = "W01005"; // �K�萔�I�[�o�[
			return false;

		}

		// ���b�N�A�E�g�J�E���g�N���A
		loLogic.clearLockout();

		return true;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.UserManager#makeUserInfo()
	 */
	public TUserInfo makeUserInfo() throws TException {

		// ��{���[�U�[���擾
		usr = usrDao.getUSR_MSTByKaicodeUsrcode(companyCode, userCode);

		// ����R�[�hnull�`�F�b�N
		if (usr == null || Util.isNullOrEmpty(usr.getDEP_CODE())) {
			throw new TException("W00196");
		}

		// ������擾
		bmn = bmnDao.getBMN_MSTByKaicodeDepcode(companyCode, usr.getDEP_CODE());

		if (bmn == null) {
			throw new TException("W00196");
		}

		// �Ј����擾
		emp = empDao.getEMP_MSTByKaicodeEmpcode(companyCode, usr.getEMP_CODE());

		TUserInfo userInfo = new TUserInfo();
		userInfo.setCompanyCode(companyCode); // ��ЃR�[�h
		userInfo.setUserCode(userCode); // ���[�U�[�R�[�h

		userInfo.setUserName(usr.getUSR_NAME());// ���[�U�[��
		userInfo.setEmployerCode(usr.getEMP_CODE()); // �Ј��R�[�h
		userInfo.setEmployerShortName(emp.getEMP_NAME_S()); // �Ј�����
		userInfo.setDepartmentCode(usr.getDEP_CODE()); // ��������R�[�h
		userInfo.setDepartmentShortName(bmn.getDEP_NAME_S()); // �������喼����
		userInfo.setProcessLevel(usr.getPRC_KEN()); // �����������x��
		userInfo.setUpdateLevel(usr.getUPD_KEN()); // �X�V�������x��
		userInfo.setAccountChargePerson(Util.avoidNullAsInt(usr.getKEIRI_KBN()) == 1); // �o���敪
		userInfo.setUserLanguage(usr.getLNG_CODE()); // ����R�[�h

		// ��Џ��
		userInfo.setCompanyInfo(makeCompanyInfo());

		return userInfo;
	}

	/**
	 * �w�肳�ꂽ��Џ��̎擾
	 * 
	 * @return ��Џ��
	 * @throws TException
	 */
	protected TCompanyInfo makeCompanyInfo() throws TException {

		// ���(���ݒ�)���擾
		env = envDao.getENV_MSTByKaicode(companyCode);

		if (env == null) {
			throw new TException("W00196");
		}

		// ��Дw�i�F �ݒ�Ȃ��Ȃ�f�t�H���g�̍���
		if (Util.isNullOrEmpty(env.getFORECOLOR())) {
			env.setFORECOLOR("#000000");
		}

		// ��ЃR���g���[�����擾
		cmp = cmpDao.getCMP_MST_ByKaicode(companyCode);

		if (cmp == null) {
			throw new TException("W00196");
		}

		// ��Џ��
		TCompanyInfo compInfo = new TCompanyInfo();
		compInfo.setCompanyCode(companyCode);
		compInfo.setCompanyName(env.getKAI_NAME()); // ��Ж�
		compInfo.setCompanyNameS(env.getKAI_NAME_S()); // ��З���
		compInfo.setForeColor(env.getFORECOLOR()); // �w�i�F

		compInfo.setItemName(cmp.getCMP_KMK_NAME()); // �u�Ȗځv����
		compInfo.setSubItemName(cmp.getCMP_HKM_NAME()); // �u�⏕�Ȗځv����
		compInfo.setBreakDownItem(cmp.getCMP_UKM_KBN()); // �u����Ȗځv���p�t���O
		compInfo.setBreakDownItemName(cmp.getCMP_UKM_NAME()); // �u����Ȗځv����
		compInfo.setBaseCurrencyCode(cmp.getCUR_CODE()); // ��ʉ�

		// �Ǘ��敪1�`6�̎g�p����
		int[] mgDivs = new int[6];
		mgDivs[0] = cmp.getKNR_KBN_1();
		mgDivs[1] = cmp.getKNR_KBN_2();
		mgDivs[2] = cmp.getKNR_KBN_3();
		mgDivs[3] = cmp.getKNR_KBN_4();
		mgDivs[4] = cmp.getKNR_KBN_5();
		mgDivs[5] = cmp.getKNR_KBN_6();
		compInfo.setManageDivs(mgDivs);

		// �Ǘ��敪1�`6�̖���
		String[] mgDivNames = new String[6];
		mgDivNames[0] = Util.avoidNull(cmp.getKNR_NAME_1());
		mgDivNames[1] = Util.avoidNull(cmp.getKNR_NAME_2());
		mgDivNames[2] = Util.avoidNull(cmp.getKNR_NAME_3());
		mgDivNames[3] = Util.avoidNull(cmp.getKNR_NAME_4());
		mgDivNames[4] = Util.avoidNull(cmp.getKNR_NAME_5());
		mgDivNames[5] = Util.avoidNull(cmp.getKNR_NAME_6());
		compInfo.setManageDivNames(mgDivNames);

		// ���v���׋敪1�`3
		int[] nonAcDetailDivs = new int[3];
		nonAcDetailDivs[0] = cmp.getCMP_HM_KBN_1();
		nonAcDetailDivs[1] = cmp.getCMP_HM_KBN_2();
		nonAcDetailDivs[2] = cmp.getCMP_HM_KBN_3();
		compInfo.setNonAccountingDetailDivs(nonAcDetailDivs);

		// ���v���ז���1�`3����
		String[] nonAcDetailNames = new String[3];
		nonAcDetailNames[0] = Util.avoidNull(cmp.getCMP_HM_NAME_1());
		nonAcDetailNames[1] = Util.avoidNull(cmp.getCMP_HM_NAME_2());
		nonAcDetailNames[2] = Util.avoidNull(cmp.getCMP_HM_NAME_3());
		compInfo.setNonAccountingDetailNames(nonAcDetailNames);

		// ����
		compInfo.setBeginningOfPeriodMonth(cmp.getCMP_KISYU());

		// ���[�g���Z�[�������敪
		compInfo.setRateConversionFraction(cmp.getRATE_KBN());

		// �������ʎc���\���̔���
		compInfo.setLedgerEachDayBalanceView(BizUtil.isLedgerEachDayBalanceView(companyCode));

		// �ʉݏ��(�R�[�h�Ə����_����)
		compInfo.setCurrencyDigits(getCompanyCurrency());

		// ������̏����l
		compInfo.setPrintDef(cmp.getPRINT_DEF());
		// �`�[����敪
		compInfo.setPrintKbn(cmp.getPRINT_KBN());

		// ���ڃv�����^�o�͋敪
		compInfo.setDirectPrintKbn(0);

		return compInfo;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.UserManager#getCompanyCurrency()
	 */
	public Map getCompanyCurrency() {
		List<CUR_MST> list = curDao.getCUR_MSTByKaicode(this.companyCode);
		Map<String, String> codeDigits = new HashMap<String, String>(list.size());

		for (CUR_MST entity : list) {
			codeDigits.put(entity.getCUR_CODE(), Util.avoidNull(entity.getDEC_KETA()));
		}

		return codeDigits;
	}
}
