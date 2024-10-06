package jp.co.ais.trans2.model.slip.parts;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.slip.SWK_DTL.KESI_KBN;
import jp.co.ais.trans2.model.user.*;

/**
 * ��Њԕt�֏���
 */
public class CompanyTransfer extends TModel {

	/** true:BS����͌v���ЃR�[�h�̓��͒l�𗘗p<Server> */
	public static boolean isBsUseKCompany = ServerConfig.isFlagOn("trans.slip.bs.use.kcompany");

	/** ���O�C����ЃR�[�h */
	protected String baseCompanyCode;

	/** ��Њԕt�֏�� */
	protected Map<String, TransferConfig> confMap = new TreeMap<String, TransferConfig>();

	/** true:��ʒl�̌v�㕔��𗘗p<Server> */
	public static boolean isUseOriginalDepartment = ServerConfig
		.isFlagOn("trans.slip.company.transfer.use.original.dep");

	/** ����̃}�b�v **/
	protected HashMap<String, String> depMap = new HashMap<String, String>();

	/**
	 * ��Њԕt�ւ̏���
	 * 
	 * @param slip �`�[
	 * @return �t�ւ���̓`�[���X�g
	 * @throws TException
	 */
	public List<Slip> transfer(final Slip slip) throws TException {

		SWK_HDR hdr = slip.getHeader();
		List<SWK_DTL> dtlList = slip.getDetails();

		// ���ЃR�[�hset
		baseCompanyCode = hdr.getKAI_CODE();

		// �v���ЃR�[�h��Z�߂�
		Map<String, Company> kkaiCodeList = new LinkedHashMap<String, Company>();

		for (SWK_DTL dtlKcomp : slip.getDetails()) {
			kkaiCodeList.put(dtlKcomp.getSWK_K_KAI_CODE(), dtlKcomp.getAppropriateCompany());
		}

		// ��Ж��̓`�[
		Map<String, Slip> slipMap = new HashMap<String, Slip>(kkaiCodeList.size());

		// ���Ўd�� �w�b�_
		slipMap.put(baseCompanyCode, new Slip(hdr));

		// ���Ўd�� �w�b�_
		for (Map.Entry<String, Company> entry : kkaiCodeList.entrySet()) {

			if (baseCompanyCode.equals(entry.getKey())) {
				continue;
			}

			// �t�֐�̓`�[�쐬(�w�b�_�̂�)
			SWK_HDR thdr;
			if (entry.getValue() != null) {
				thdr = makeHeader(entry.getValue(), hdr);

			} else {
				thdr = makeHeader(entry.getKey(), hdr);
			}

			slipMap.put(entry.getKey(), new Slip(thdr));
		}

		// ����
		List<SWK_DTL> insertDtlList = new ArrayList<SWK_DTL>();

		// �s�ԍ��͉�Ђ��ׂ��č̔�
		int gyoNo = 0;

		boolean isJournalizedTax = slip.isJournalizedTax();

		// DB���畔������擾����
		DepartmentManager manager = (DepartmentManager) getComponent(DepartmentManager.class);

		for (SWK_DTL dtl : dtlList) {

			String kKaiCode = dtl.getSWK_K_KAI_CODE();

			if (baseCompanyCode.equals(kKaiCode)) {
				// ���Ж��� 0(����)
				insertDtlList.add(makeSelfDtl(baseCompanyCode, dtl, ++gyoNo, 0));

			} else {
				// �t�֌����� 1(����)
				SWK_DTL newdtl = makeOtherDtl(baseCompanyCode, dtl, ++gyoNo, 1, isJournalizedTax);

				// �s�E�v�ւ̒ǉ��Ή�
				if (isUseOriginalDepartment) {
					String depName = dtl.getSWK_DEP_NAME();

					if (Util.isNullOrEmpty(depName)) {
						String depCode = dtl.getSWK_DEP_CODE();
						if (depMap.containsKey(depCode)) {
							depName = depMap.get(depCode);
						} else {
							// ���匟������
							DepartmentSearchCondition condition = new DepartmentSearchCondition();
							condition.setCompanyCode(kKaiCode);
							condition.setCode(depCode);
							List<Department> depList = manager.get(condition);

							if (depList.size() > 0) {
								Department dep = depList.get(0);
								depMap.put(depCode, dep.getName());
								depName = dep.getName();
							}
						}
						dtl.setSWK_DEP_NAME(depName);
					}

					String tekiyo = Util.avoidNull(depName) + " " + newdtl.getSWK_GYO_TEK();
					tekiyo = StringUtil.leftBX(tekiyo, 80);
					newdtl.setSWK_GYO_TEK(tekiyo);
				}

				if (isBsUseKCompany) {
					newdtl.setBsDetail(null);
				}
				insertDtlList.add(newdtl);

				// �t�֐斾�� 2(����)
				newdtl = makeSelfDtl(kKaiCode, dtl, ++gyoNo, 2);
				insertDtlList.add(newdtl);

				// �t�֐斾��(�t��) 2(����)
				SWK_DTL tukeDtl = dtl.clone();
				tukeDtl.setSWK_K_KAI_CODE(baseCompanyCode);
				if (isBsUseKCompany) {
					tukeDtl.setBsDetail(null);
				}
				insertDtlList.add(makeOtherDtl(kKaiCode, tukeDtl, ++gyoNo, 2, isJournalizedTax));
			}
		}

		// �U�蕪��
		for (SWK_DTL dtl : insertDtlList) {
			String compCode = dtl.getKAI_CODE();
			slipMap.get(compCode).addDetail(dtl);
		}

		// ����Ŏd����N�[���邩���Z�b�g(���`�[���R�s�[)
		List<Slip> slips = new ArrayList(slipMap.values());
		for (Slip oneSlip : slips) {
			oneSlip.setJournalizedTax(slip.isJournalizedTax());
		}

		return slips;

	}

	/**
	 * �t�֐��Ђ̃w�b�_�쐬
	 * 
	 * @param company ���
	 * @param hdr ���w�b�_
	 * @return �t�֐��Ђ̃w�b�_
	 * @throws TException
	 */
	protected SWK_HDR makeHeader(Company company, SWK_HDR hdr) throws TException {

		SWK_HDR newHdr = makeHeader(company.getCode(), hdr);

		AccountConfig conf = company.getAccountConfig();
		newHdr.setKEY_CUR_CODE(conf.getKeyCurrency().getCode());
		newHdr.setKEY_CUR_DEC_KETA(conf.getKeyCurrency().getDecimalPoint());
		newHdr.setFUNC_CUR_CODE(conf.getFunctionalCurrency().getCode());
		newHdr.setFUNC_CUR_DEC_KETA(conf.getFunctionalCurrency().getDecimalPoint());

		return newHdr;
	}

	/**
	 * �t�֐��Ђ̃w�b�_�쐬
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param hdr ���w�b�_
	 * @return �t�֐��Ђ̃w�b�_
	 * @throws TException
	 */
	protected SWK_HDR makeHeader(String companyCode, SWK_HDR hdr) throws TException {

		SWK_HDR newHdr = hdr.clone();

		// ��ЃR�[�h
		newHdr.setKAI_CODE(companyCode);

		// �r���t���O
		newHdr.setSWK_SHR_KBN(0);

		// ��Њԕt�֓`�[�敪
		newHdr.setSWK_TUKE_KBN(1);

		// �ȖڃR�[�h SWK_KMK_CODE NULL
		newHdr.setItem(null);

		// �v�㕔��R�[�h SWK_DEP_CODE NULL
		newHdr.setDepartment(null);

		// �����R�[�h SWK_TRI_CODE NULL
		newHdr.setCustomer(null);

		// �Ј��R�[�h SWK_EMP_CODE NULL
		newHdr.setEmployee(null);

		// �E�v�R�[�h NULL
		newHdr.setSWK_TEK_CODE(null);

		// ���o���M�݋��z SWK_KIN 0
		newHdr.setSWK_KIN(BigDecimal.ZERO);

		// ���F�� SWK_SYO_EMP_CODE NULL
		newHdr.setSWK_SYO_EMP_CODE(null);
		newHdr.setSWK_SYO_EMP_NAME(null);
		newHdr.setSWK_SYO_EMP_NAME_S(null);

		// ���F�� SWK_SYO_DATE NULL
		newHdr.setSWK_SYO_DATE(null);

		// ڰ� SWK_CUR_RATE NULL
		newHdr.setSWK_CUR_RATE(null);

		// ���o�����͋��z SWK_IN_KIN NULL
		newHdr.setSWK_IN_KIN(null);

		// �x���敪 SWK_SIHA_KBN NULL
		newHdr.setSWK_SIHA_KBN(null);

		// �x���� SWK_SIHA_DATE NULL
		newHdr.setSWK_SIHA_DATE(null);

		// �x�����@ SWK_HOH_CODE NULL
		newHdr.setPaymentMethod(null);

		// �ۗ��敪 SWK_HORYU_KBN NULL(0:�ۗ����Ȃ�)
		newHdr.setSWK_HORYU_KBN(0);

		// �������z SWK_KARI_KIN NULL
		newHdr.setSWK_KARI_KIN(null);

		// �o����z SWK_KEIHI_KIN NULL
		newHdr.setSWK_KEIHI_KIN(null);

		// �x�����z(�M��) SWK_SIHA_KIN NULL
		newHdr.setSWK_SIHA_KIN(null);

		// ���������}�X�^�̔�d�������a�����
		newHdr.setSWK_TJK_YKN_KBN(0);

		// ���������}�X�^�̔�d�������ԍ�
		newHdr.setSWK_TJK_YKN_NO(null);

		// ����������s��(�x����s��)
		newHdr.setSWK_TJK_BNK_NAME_S(null);

		// ����������s������(�x����s�x�X��)
		newHdr.setSWK_TJK_BNK_STN_NAME_S(null);

		// �����\���`�[�ԍ� SWK_KARI_DR_DEN_NO NULL
		newHdr.setSWK_KARI_DR_DEN_NO(null);

		// �������Z�`�[�ԍ� SWK_KARI_CR_DEN_NO NULL
		newHdr.setSWK_KARI_CR_DEN_NO(null);

		// �x�����ϋ敪 SWK_KESAI_KBN NULL(0:�ʏ�)
		newHdr.setSWK_KESAI_KBN(0);

		// �����v�㕔��R�[�h SWK_KARI_DEP_CODE NULL
		newHdr.setSWK_KARI_DEP_CODE(null);
		newHdr.setSWK_KARI_DEP_NAME(null);
		newHdr.setSWK_KARI_DEP_NAME_S(null);

		// ������������ SWK_TJK_CODE NULL
		newHdr.setSWK_TJK_CODE(null);

		// ���͉������z SWK_IN_KARI_KIN NULL
		newHdr.setSWK_IN_KARI_KIN(null);

		// ���͌o����z SWK_IN_KEIHI_KIN NULL
		newHdr.setSWK_IN_KEIHI_KIN(null);

		// ���͎x�����z SWK_IN_SIHA_KIN NULL
		newHdr.setSWK_IN_SIHA_KIN(null);

		// �������ʉݺ��� SWK_INV_CUR_CODE NULL
		newHdr.setSWK_INV_CUR_CODE(null);

		// �������ʉ�ڰ� SWK_INV_CUR_RATE NULL
		newHdr.setSWK_INV_CUR_RATE(null);

		// �������ʉ݋��z SWK_INV_KIN NULL
		newHdr.setSWK_INV_KIN(null);

		// ��s�������� SWK_CBK_CODE NULL
		newHdr.setBankAccount(null);

		// ���Z�\��� SWK_SSY_DATE NULL
		newHdr.setSWK_SSY_DATE(null);

		// ��tNO SWK_UTK_NO NULL
		newHdr.setSWK_UTK_NO(null);

		// �����ʉݺ��� SWK_KARI_CUR_CODE NULL
		newHdr.setSWK_KARI_CUR_CODE(null);

		// �����ʉ�ڰ� SWK_KARI_CUR_RATE NULL
		newHdr.setSWK_KARI_CUR_RATE(null);

		// �����敪 SWK_SEI_KBN NULL
		newHdr.setSWK_SEI_KBN(null);

		// �����\��� SWK_AR_DATE NULL
		newHdr.setSWK_AR_DATE(null);

		// �ʉݺ��� SWK_CUR_CODE NULL
		newHdr.setSWK_CUR_CODE(null);

		User user = getEmployee(companyCode, hdr.getSWK_IRAI_EMP_CODE());

		if (user != null) {
			// ��t����
			newHdr.setSWK_UKE_DEP_CODE(user.getDepartment().getCode());
			newHdr.setSWK_UKE_DEP_NAME(user.getDepartment().getName());
			newHdr.setSWK_UKE_DEP_NAME_S(user.getDepartment().getNames());
			// �˗�����
			newHdr.setSWK_IRAI_DEP_CODE(user.getDepartment().getCode());
			newHdr.setSWK_IRAI_DEP_NAME(user.getDepartment().getName());
			newHdr.setSWK_IRAI_DEP_NAME_S(user.getDepartment().getNames());
			// �˗��Ј�
			newHdr.setSWK_IRAI_EMP_CODE(user.getEmployee().getCode());
			newHdr.setSWK_IRAI_EMP_NAME(user.getEmployee().getName());
			newHdr.setSWK_IRAI_EMP_NAME_S(user.getEmployee().getNames());
		}

		return newHdr;
	}

	/**
	 * �d��W���[�i���̍\�z (����)
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param dtl �Ώۖ���
	 * @param gyoNo �s�ԍ�
	 * @param swkTukeKbn �t�֋敪
	 * @return �쐬�d��
	 */
	protected SWK_DTL makeSelfDtl(String kaiCode, SWK_DTL dtl, int gyoNo, int swkTukeKbn) {

		SWK_DTL newDtl = dtl.clone();

		newDtl.setKAI_CODE(kaiCode);
		newDtl.setSWK_GYO_NO(gyoNo);
		newDtl.setSWK_TUKE_KBN(swkTukeKbn);

		return newDtl;
	}

	/**
	 * �d��W���[�i���̍\�z(���v����)
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param dtl �Ώۖ���
	 * @param gyoNo �s�ԍ�
	 * @param tukeKbn �t�֋敪
	 * @param isJournalizedTax �����
	 * @return �쐬�d��
	 * @throws TException
	 */
	protected SWK_DTL makeOtherDtl(String kaiCode, SWK_DTL dtl, int gyoNo, int tukeKbn, boolean isJournalizedTax)
		throws TException {

		// ��Њԕt�փ}�X�^����擾
		String fromCompany = kaiCode;
		String toCompany = dtl.getSWK_K_KAI_CODE();
		TransferConfig conf = getConfig(fromCompany, toCompany);

		SWK_DTL newDtl = dtl.clone();

		newDtl.setKAI_CODE(kaiCode);
		newDtl.setSWK_GYO_NO(gyoNo);

		if (!baseCompanyCode.equals(kaiCode)) {
			if (dtl.getSWK_DC_KBN() == 0) {
				newDtl.setSWK_DC_KBN(1);

			} else {
				newDtl.setSWK_DC_KBN(0);
			}
		}

		newDtl.setSWK_KMK_CODE(conf.getTransferItemCode());
		newDtl.setSWK_KMK_NAME(conf.getTransferItemName());
		newDtl.setSWK_KMK_NAME_S(conf.getTransferItemNames());
		newDtl.setSWK_HKM_CODE(conf.getTransferSubItemCode());
		newDtl.setSWK_HKM_NAME(conf.getTransferSubItemName());
		newDtl.setSWK_HKM_NAME_S(conf.getTransferSubItemNames());
		newDtl.setSWK_UKM_CODE(conf.getTransferDetailItemCode());
		newDtl.setSWK_UKM_NAME(conf.getTransferDetailItemName());
		newDtl.setSWK_UKM_NAME_S(conf.getTransferDetailItemNames());
		newDtl.setSWK_DEP_CODE(conf.getTransferDepertmentCode());
		newDtl.setSWK_DEP_NAME(conf.getTransferDepertmentName());
		newDtl.setSWK_DEP_NAME_S(conf.getTransferDepertmentNames());
		newDtl.setSWK_TRI_CODE(conf.getTransferCustomerCode());
		newDtl.setSWK_TRI_NAME(conf.getTransferCustomerName());
		newDtl.setSWK_TRI_NAME_S(conf.getTransferCustomerNames());

		if (isJournalizedTax) {
			newDtl.setSWK_KIN(newDtl.getSWK_KIN());

		} else {
			newDtl.setSWK_KIN(newDtl.getSWK_KIN().add(newDtl.getSWK_ZEI_KIN()));
		}
		newDtl.setSWK_ZEI_KBN(2);
		newDtl.setSWK_ZEI_KIN(BigDecimal.ZERO);
		newDtl.setSWK_ZEI_RATE(BigDecimal.ZERO);
		newDtl.setSWK_ZEI_CODE(null);
		newDtl.setSWK_ZEI_NAME(null);
		newDtl.setSWK_ZEI_NAME_S(null);

		if (isJournalizedTax) {
			newDtl.setSWK_IN_KIN(newDtl.getSWK_IN_KIN());

		} else {
			newDtl.setSWK_IN_KIN(newDtl.getSWK_IN_KIN().add(newDtl.getSWK_IN_ZEI_KIN()));
		}
		newDtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);

		newDtl.setSWK_AUTO_KBN(1);
		newDtl.setSWK_TUKE_KBN(tukeKbn);

		newDtl.setSWK_KESI_KBN(KESI_KBN.NOMAL);
		newDtl.setSWK_KESI_DATE(null);
		newDtl.setSWK_KESI_DEN_NO(null);
		newDtl.setSWK_SOUSAI_DATE(null);
		newDtl.setSWK_SOUSAI_DEN_NO(null);
		newDtl.setSWK_SOUSAI_GYO_NO(null);

		return newDtl;
	}

	/**
	 * �t�֏��̎擾
	 * 
	 * @param fromCompany
	 * @param toCompany
	 * @return �t�֏��
	 * @throws TException
	 */
	protected TransferConfig getConfig(String fromCompany, String toCompany) throws TException {

		TransferConfig conf = confMap.get(fromCompany + "<>" + toCompany);

		if (conf == null) {
			CompanyManager manager = (CompanyManager) getComponent(CompanyManager.class);
			List<TransferConfig> list = manager.getTransferConfig(fromCompany, toCompany);

			if (list == null || list.isEmpty()) {
				throw new TRuntimeException("W00107");
			}

			for (TransferConfig bean : list) {
				String from = bean.getCompanyCode();
				String to = bean.getTransferCompanyCode();

				confMap.put(from + "<>" + to, bean);
			}

			conf = confMap.get(fromCompany + "<>" + toCompany);
		}

		return conf;
	}

	/**
	 * ���[�U���擾
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param empCode �Ј��R�[�h
	 * @return ���[�U�}�X�^
	 * @throws TException
	 */
	protected User getEmployee(String companyCode, String empCode) throws TException {
		UserManager manager = (UserManager) getComponent(UserManager.class);
		UserSearchCondition condition = new UserSearchCondition();
		condition.setCompanyCode(companyCode);
		condition.setEmployeeCode(empCode);
		List<User> users = manager.get(condition);

		// �Ј��o�^����Ă��Ȃ����[�U�����O
		for (int i = users.size() - 1; 0 <= i; i--) {
			User user = users.get(i);
			if (Util.isNullOrEmpty(user.getEmployee().getName())) {
				users.remove(i);
			}
		}

		if (users.isEmpty()) {
			throw new TException("I00157", companyCode, empCode);// ���[{0}]�̃��[�U�}�X�^�A�܂��͎Ј��}�X�^[{1}]���ݒ肳��Ă��܂���B
		}

		// �ʏ�^�p�ł͖������A�������[�U�ɎЈ��R�[�h�����蓖�Ă��Ă���ꍇ�́A���O�C�����[�U�R�[�h�Ɠ���ɂ���
		String userCode = getUserCode();
		for (User user : users) {
			if (userCode.equals(user.getCode())) {
				return user;
			}
		}

		return users.get(0);
	}
}
