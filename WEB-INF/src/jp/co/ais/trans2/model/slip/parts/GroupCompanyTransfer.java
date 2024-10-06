package jp.co.ais.trans2.model.slip.parts;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.management.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.slip.SWK_DTL.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ��Њԕt�֏���<�O���[�v��v��>
 * 
 * @author AIS YF.CONG
 */
public class GroupCompanyTransfer extends TModel {

	/** SEP */
	public static final String KEY_SEP = "<>";

	/** true:BS����͌v���ЃR�[�h�̓��͒l�𗘗p<Server> */
	public static boolean isBsUseKCompany = ServerConfig.isFlagOn("trans.slip.bs.use.kcompany");

	/** true:�v���Ђ��Ǘ��P���p�����ė��p����<Server> */
	public static boolean isUseManager1ForGroup = ServerConfig.isFlagOn("trans.slip.group.use.knr1");

	/** ���O�C����ЃR�[�h */
	protected String baseCompanyCode;

	/** ��Њԕt�֏�� */
	protected Map<String, TransferConfig> confMap = new TreeMap<String, TransferConfig>();

	/** ��Џ�� */
	protected Map<String, Company> companyMap = new TreeMap<String, Company>();

	/** �ʉݏ�� */
	protected Map<String, Currency> currencyMap = new TreeMap<String, Currency>();

	/** ���[�g��� */
	protected Map<String, BigDecimal> rateMap = new TreeMap<String, BigDecimal>();

	/** �Ȗڏ�� */
	protected Map<String, Item> itemMap = new TreeMap<String, Item>();

	/** �Ǘ��P��� */
	protected Map<String, Management1> knr1Map = new HashMap<String, Management1>();

	/** ���Z�`�[���ǂ��� true:���Z�Afalse:�ʏ� */
	protected boolean isClosingSlip = false;

	/** �v�Z���W�b�N */
	protected TCalculator calculator = TCalculatorFactory.getCalculator();

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

		// ���Z�`�[���ǂ��� true:���Z�Afalse:�ʏ�
		isClosingSlip = slip.isClosingSlip();

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

		// DB���畔������擾����
		DepartmentManager manager = (DepartmentManager) getComponent(DepartmentManager.class);

		SlipManager sm = (SlipManager) getComponent(SlipManager.class);
		List<String> cmpKey = new ArrayList<String>();
		List<String> autoJornalKey = new ArrayList<String>();

		for (SWK_DTL dtl : dtlList) {

			String kKaiCode = dtl.getSWK_K_KAI_CODE();

			if (baseCompanyCode.equals(kKaiCode)) {
				// ���Ж��� 0(����)
				SWK_DTL detail = makeSelfDtl(baseCompanyCode, dtl, ++gyoNo, 0);
				insertDtlList.add(detail);

			} else {

				// ����ŉȖڂ̏��擾
				if (!cmpKey.contains(kKaiCode)) {
					// ��x�擾������Ђ͍Č������Ȃ�
					cmpKey.add(kKaiCode);
					int[] types = new int[] { AutoJornalAccountType.PAY_TAX.value,
							AutoJornalAccountType.RECEIVE_TAX.value };
					List<AutoJornalAccount> autoJornalList = sm.getAutoJornalAccounts(kKaiCode, types);
					if (autoJornalList != null && !autoJornalList.isEmpty()) {
						for (AutoJornalAccount bean : autoJornalList) {
							String key = bean.getCompanyCode() + "<>" + bean.getItemCode() + "<>"
								+ bean.getSubItemCode() + "<>" + bean.getDetailItemCode();
							autoJornalKey.add(key);
						}
					}
				}

				String key = kKaiCode + "<>" + dtl.getSWK_KMK_CODE() + "<>" + dtl.getSWK_HKM_CODE() + "<>"
					+ dtl.getSWK_UKM_CODE();

				if (dtl.getSWK_AUTO_KBN() != AUTO_KBN.AUTO //
					|| (dtl.getSWK_AUTO_KBN() == AUTO_KBN.AUTO && !autoJornalKey.contains(key))) {
					// �����d��敪�������ŏ���ŉȖڂ̏ꍇ�͍쐬���Ȃ��B

					// �t�֌����� 1(����)
					SWK_DTL detail = makeParentDtl(baseCompanyCode, dtl, ++gyoNo, 1);

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

						String tekiyo = Util.avoidNull(depName) + " " + detail.getSWK_GYO_TEK();
						tekiyo = StringUtil.leftBX(tekiyo, 80);
						detail.setSWK_GYO_TEK(tekiyo);
					}

					if (isBsUseKCompany) {
						detail.setBsDetail(null);
					}
					insertDtlList.add(detail);
				}

				// �t�֐斾�� 2(����)
				SWK_DTL tukemotodtl = makeChildDtl(kKaiCode, dtl, ++gyoNo, 2);
				insertDtlList.add(tukemotodtl);

				// �t�֐斾��(�t��) 2(����)
				if (dtl.getSWK_AUTO_KBN() != AUTO_KBN.AUTO //
					|| (dtl.getSWK_AUTO_KBN() == AUTO_KBN.AUTO && !autoJornalKey.contains(key))) {

					// �����d��敪�������ŏ���ŉȖڂ̏ꍇ�͍쐬���Ȃ��B
					SWK_DTL tukeautodtl = makeChildAutoDtl(kKaiCode, tukemotodtl, ++gyoNo, baseCompanyCode);
					if (isBsUseKCompany) {
						tukeautodtl.setBsDetail(null);
					}
					insertDtlList.add(tukeautodtl);
				}
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
	 * �d��W���[�i���̍\�z (���ЁF�e���)
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param dtl �Ώۖ���
	 * @param gyoNo �s�ԍ�
	 * @param swkTukeKbn �t�֋敪
	 * @return �쐬�d��
	 * @throws TException
	 */
	protected SWK_DTL makeParentDtl(String kaiCode, SWK_DTL dtl, int gyoNo, int swkTukeKbn) throws TException {

		// ��Њԕt�փ}�X�^����擾
		String fromCompany = kaiCode;
		String toCompany = dtl.getSWK_K_KAI_CODE();
		TransferConfig conf = getConfig(fromCompany, toCompany);

		SWK_DTL newDtl = dtl.clone();

		// ��ЃR�[�h �� ���O�C����ЃR�[�h
		newDtl.setKAI_CODE(kaiCode);

		// �s�ԍ� �� �Ώۍs�ԍ�
		newDtl.setSWK_GYO_NO(gyoNo);

		// �ȖڃR�[�h �� �t�֌��̉ȖڃR�[�h
		// �⏕�ȖڃR�[�h �� �t�֌��̕⏕�ȖڃR�[�h
		// ����ȖڃR�[�h �� �t�֌��̓���ȖڃR�[�h
		// �v�㕔��R�[�h �� �t�֌��̌v�㕔��R�[�h
		// �����R�[�h �� �t�֌��̎����R�[�h
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

		// �Ȗڏ��擾
		Item item = getItem(kaiCode, conf.getTransferItemCode(), conf.getTransferSubItemCode(),
			conf.getTransferDetailItemCode());

		// �Ȗڐݒ�t���O��OFF�̏ꍇ�A�����ݒ�s�v
		if (item != null && item.getPreferedItem() != null && item.getPreferedItem().isUseCustomer()) {
			newDtl.setSWK_TRI_CODE(conf.getTransferCustomerCode());
			newDtl.setSWK_TRI_NAME(conf.getTransferCustomerName());
			newDtl.setSWK_TRI_NAME_S(conf.getTransferCustomerNames());
		} else {
			newDtl.setCustomer(null);
		}

		newDtl.setEmployee(null);
		newDtl.setManagement1(null);
		newDtl.setManagement2(null);
		newDtl.setManagement3(null);
		newDtl.setManagement4(null);
		newDtl.setManagement5(null);
		newDtl.setManagement6(null);
		newDtl.setSWK_HM_1(null);
		newDtl.setSWK_HM_2(null);
		newDtl.setSWK_HM_3(null);

		if (isUseManager1ForGroup && item != null && item.getPreferedItem() != null
			&& item.getPreferedItem().isUseManagement1()) {
			Management1 mng1 = getManagement1(newDtl.getKAI_CODE(), dtl.getSWK_KNR_CODE_1());
			newDtl.setManagement1(mng1);
		}

		// ���͋��z�i�ō��݁j:�i���͋��z�i�Ŕ����j+���͏���Ŋz�j
		newDtl.setSWK_IN_KIN(dtl.getSWK_IN_KIN().add(dtl.getSWK_IN_ZEI_KIN()));

		// �M�݋��z�i�ō��݁j:�i�M�݋��z�i�Ŕ����j+�M�ݏ���Ŋz�j
		newDtl.setSWK_KIN(dtl.getSWK_KIN().add(dtl.getSWK_ZEI_KIN()));

		// ���͏���Ŋz �� 0
		newDtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);

		// �M�ݏ���Ŋz �� 0
		newDtl.setSWK_ZEI_KIN(BigDecimal.ZERO);

		// �ŋ敪 �� 2:��ې�
		newDtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE);

		// ����ŃR�[�h �� null
		newDtl.setSWK_ZEI_CODE(null);
		newDtl.setSWK_ZEI_NAME(null);
		newDtl.setSWK_ZEI_NAME_S(null);

		// �ŗ� �� 0
		newDtl.setSWK_ZEI_RATE(BigDecimal.ZERO);

		// �����d��敪 �� 1:����
		newDtl.setSWK_AUTO_KBN(SWK_DTL.AUTO_KBN.AUTO);

		// ��Њԕt�֓`�[�敪 �� 1:�t�֌������d��s
		newDtl.setSWK_TUKE_KBN(swkTukeKbn);

		newDtl.setSWK_KESI_KBN(KESI_KBN.NOMAL);
		newDtl.setSWK_KESI_DATE(null);
		newDtl.setSWK_KESI_DEN_NO(null);
		newDtl.setSWK_SOUSAI_DATE(null);
		newDtl.setSWK_SOUSAI_DEN_NO(null);
		newDtl.setSWK_SOUSAI_GYO_NO(null);

		return newDtl;
	}

	/**
	 * �d��W���[�i���̍\�z(���v���ЁF�q���)
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param dtl �Ώۖ���
	 * @param gyoNo �s�ԍ�
	 * @param tukeKbn �t�֋敪
	 * @return �쐬�d��
	 */
	protected SWK_DTL makeChildDtl(String kaiCode, SWK_DTL dtl, int gyoNo, int tukeKbn) {

		SWK_DTL newDtl = dtl.clone();

		// ��ЃR�[�h �� �v���ЃR�[�h
		newDtl.setKAI_CODE(kaiCode);

		// �s�ԍ� �� �@�̍s�ԍ��{�P
		newDtl.setSWK_GYO_NO(gyoNo);

		// ���Y��p�D�i��ʉ�JPY�j
		String baseCurrencyCode = getCompany().getAccountConfig().getKeyCurrency().getCode();

		// �q��Џ��
		Company company = getCompany(kaiCode);

		// �q��Њ�ʉ݃R�[�h
		String keyCurrencyCode = company.getAccountConfig().getKeyCurrency().getCode();

		// ���͒ʉ݃R�[�h
		String currencyCode = dtl.getSWK_CUR_CODE();

		// ���͒ʉ݃R�[�h
		Currency currency = getCurrency(kaiCode, currencyCode);

		// ���[�g �� �q��Ђ̃��[�g�}�X�^�ɐe��Ђ̖{�M�ʉ݂ɑ΂��郌�[�g�A���o����͓`�[���t�̓��͒ʉ݃��[�g
		BigDecimal rate = null;

		// ���͋��z
		BigDecimal foreignAmount = dtl.getSWK_IN_KIN();
		// �M�݋��z
		BigDecimal amount = null;
		// ���͏���Ŋz �� ���͎d��̓��͏���Ŋz
		BigDecimal foreignTaxAmount = dtl.getSWK_IN_ZEI_KIN();
		// �M�ݏ���Ŋz
		BigDecimal taxAmount = null;

		// ���[�g
		if (keyCurrencyCode.equals(baseCurrencyCode)) {
			// �e��Ђ̊�ʉ݁��q��Ђ̊�ʉ݂̏ꍇ

			// ���[�g�����̓��[�g
			rate = dtl.getSWK_CUR_RATE();
		} else {
			// �e��Ђ̊�ʉ݁����q��Ђ̊�ʉ݂̏ꍇ

			// ���[�g���q��Ђ̃��[�g�}�X�^�ɓ��͒ʉ݂ɑ΂��郌�[�g
			rate = getRate(kaiCode, currencyCode, newDtl);
		}

		// �M�݋��z
		if (keyCurrencyCode.equals(baseCurrencyCode)) {

			// �O.�e��Ђ̊�ʉ݁��C�O�q��Ђ̊�ʉ݂̏ꍇ

			// �M�݋��z���t�֎d��쐬���̖M�݋��z
			amount = dtl.getSWK_KIN();

		} else if (currencyCode.equals(keyCurrencyCode)) {

			// �P.���͓`�[�d��̒ʉ݁��C�O�q��Ђ̊�ʉ݂̏ꍇ

			// �M�݋��z���t�֎d��쐬���̓��͋��z
			amount = foreignAmount;

		} else {

			// �Q.���͓`�[�d��̒ʉ݁����C�O�q��Ђ̊�ʉ݂̏ꍇ�A�����͋��z

			// �M�݋��z���t�֎d��쐬���̓��͋��z�^���[�g
			amount = convertKeyAmount(foreignAmount, rate, company, currency);

		}

		// �M�ݏ���Ŋz���t�֎d��쐬���̓��͏���Ŋz�^���[�g
		taxAmount = convertKeyAmount(foreignTaxAmount, rate, company, currency);

		// �d��֔��f

		// �ʉ݃R�[�h
		newDtl.setSWK_CUR_CODE(currencyCode);

		// ���[�g
		newDtl.setSWK_CUR_RATE(rate);

		// ���͋��z
		newDtl.setSWK_IN_KIN(foreignAmount);

		// �M�݋��z
		newDtl.setSWK_KIN(amount);

		// ���͏���Ŋz
		newDtl.setSWK_IN_ZEI_KIN(foreignTaxAmount);

		// �M�ݏ���Ŋz
		newDtl.setSWK_ZEI_KIN(taxAmount);

		// ��Њԕt�֓`�[�敪 �� 2:�t�֐掩���d��s
		newDtl.setSWK_TUKE_KBN(tukeKbn);

		// �����d��敪 �� ���͎d��̎����d��敪
		newDtl.setSWK_AUTO_KBN(dtl.getSWK_AUTO_KBN());

		newDtl.setSWK_KESI_KBN(KESI_KBN.NOMAL);
		newDtl.setSWK_KESI_DATE(null);
		newDtl.setSWK_KESI_DEN_NO(null);
		newDtl.setSWK_SOUSAI_DATE(null);
		newDtl.setSWK_SOUSAI_DEN_NO(null);
		newDtl.setSWK_SOUSAI_GYO_NO(null);

		return newDtl;
	}

	/**
	 * �d��W���[�i���̍\�z(���v���ЁF�q��Ў����d��)
	 * 
	 * @param kaiCode ��ЃR�[�h
	 * @param dtl �Ώۖ���
	 * @param gyoNo �s�ԍ�
	 * @param toCompanyCode �e��ЃR�[�h
	 * @return �쐬�d��
	 * @throws TException
	 */
	protected SWK_DTL makeChildAutoDtl(String kaiCode, SWK_DTL dtl, int gyoNo, String toCompanyCode) throws TException {

		// ��Њԕt�փ}�X�^����擾
		String fromCompany = kaiCode;
		String toCompany = toCompanyCode;
		TransferConfig conf = getConfig(fromCompany, toCompany);

		SWK_DTL newDtl = dtl.clone();

		// �s�ԍ� �� �A�̍s�ԍ��{�P
		newDtl.setSWK_GYO_NO(gyoNo);

		// �ݎ؋敪 �� �t�ɂ���
		if (!baseCompanyCode.equals(kaiCode)) {
			newDtl.reverseDC();
		}

		// ���͋��z�i�ō��݁j:�i���͋��z�i�Ŕ����j+���͏���Ŋz�j
		newDtl.setSWK_IN_KIN(dtl.getSWK_IN_KIN().add(dtl.getSWK_IN_ZEI_KIN()));

		// �M�݋��z�i�ō��݁j:�i�M�݋��z�i�Ŕ����j+�M�ݏ���Ŋz�j
		newDtl.setSWK_KIN(dtl.getSWK_KIN().add(dtl.getSWK_ZEI_KIN()));

		// ���͏���Ŋz �� 0
		newDtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);

		// �M�ݏ���Ŋz �� 0
		newDtl.setSWK_ZEI_KIN(BigDecimal.ZERO);

		// �ŋ敪 �� 2:��ې�
		newDtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE);

		// ����ŃR�[�h �� null
		newDtl.setSWK_ZEI_CODE(null);
		newDtl.setSWK_ZEI_NAME(null);
		newDtl.setSWK_ZEI_NAME_S(null);

		// �ŗ� �� 0
		newDtl.setSWK_ZEI_RATE(BigDecimal.ZERO);

		// �ȖڃR�[�h �� �t�֐�̉ȖڃR�[�h
		// �⏕�ȖڃR�[�h �� �t�֐�̕⏕�ȖڃR�[�h
		// ����ȖڃR�[�h �� �t�֐�̓���ȖڃR�[�h
		// �v�㕔��R�[�h �� �t�֐�̌v�㕔��R�[�h
		// �����R�[�h �� �t�֐�̎����R�[�h
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

		// �Ȗڏ��擾
		Item item = getItem(kaiCode, conf.getTransferItemCode(), conf.getTransferSubItemCode(),
			conf.getTransferDetailItemCode());

		// �Ȗڐݒ�t���O��OFF�̏ꍇ�A�����ݒ�s�v
		if (item != null && item.getPreferedItem() != null && item.getPreferedItem().isUseCustomer()) {
			newDtl.setSWK_TRI_CODE(conf.getTransferCustomerCode());
			newDtl.setSWK_TRI_NAME(conf.getTransferCustomerName());
			newDtl.setSWK_TRI_NAME_S(conf.getTransferCustomerNames());
		} else {
			newDtl.setCustomer(null);
		}

		newDtl.setEmployee(null);
		newDtl.setManagement1(null);
		newDtl.setManagement2(null);
		newDtl.setManagement3(null);
		newDtl.setManagement4(null);
		newDtl.setManagement5(null);
		newDtl.setManagement6(null);
		newDtl.setSWK_HM_1(null);
		newDtl.setSWK_HM_2(null);
		newDtl.setSWK_HM_3(null);

		if (isUseManager1ForGroup && item != null && item.getPreferedItem() != null
			&& item.getPreferedItem().isUseManagement1()) {
			Management1 mng1 = getManagement1(newDtl.getKAI_CODE(), dtl.getSWK_KNR_CODE_1());
			newDtl.setManagement1(mng1);
		}

		// �����d��敪 �� 1:����
		newDtl.setSWK_AUTO_KBN(SWK_DTL.AUTO_KBN.AUTO);

		// �v���ЃR�[�h �� ���O�C����ЃR�[�h
		newDtl.setSWK_K_KAI_CODE(baseCompanyCode);

		newDtl.setSWK_KESI_KBN(KESI_KBN.NOMAL);
		newDtl.setSWK_KESI_DATE(null);
		newDtl.setSWK_KESI_DEN_NO(null);
		newDtl.setSWK_SOUSAI_DATE(null);
		newDtl.setSWK_SOUSAI_DEN_NO(null);
		newDtl.setSWK_SOUSAI_GYO_NO(null);

		return newDtl;

	}

	/**
	 * @param companyCode
	 * @param knr1Code
	 * @return �Ǘ��P
	 * @throws TException
	 */
	protected Management1 getManagement1(String companyCode, String knr1Code) throws TException {

		if (Util.isNullOrEmpty(companyCode) || Util.isNullOrEmpty(knr1Code)) {
			return null;
		}

		String key = companyCode + "<>" + knr1Code;

		if (knr1Map.containsKey(key)) {
			return knr1Map.get(key);
		}

		Management1Manager mm = (Management1Manager) getComponent(Management1Manager.class);
		Management1SearchCondition condition = new Management1SearchCondition();
		condition.setCompanyCode(companyCode);
		condition.setCode(knr1Code);
		List<Management1> list = mm.get(condition);
		Management1 bean = null;

		if (list != null && !list.isEmpty()) {
			bean = list.get(0);
		}

		knr1Map.put(key, bean);

		return bean;
	}

	/**
	 * ��Џ��̎擾
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return ��Џ��
	 */
	protected Company getCompany(String companyCode) {

		String key = companyCode;

		Company company = companyMap.get(key);

		if (company == null) {
			CompanyManager manager = (CompanyManager) getComponent(CompanyManager.class);
			CompanySearchCondition condition = new CompanySearchCondition();
			condition.setCode(companyCode);

			try {
				List<Company> list = manager.get(condition);

				if (list != null && !list.isEmpty()) {
					companyMap.put(key, list.get(0));
				}

				company = companyMap.get(key);

				companyMap.put(key, company);

			} catch (TException e) {
				// �Ȃ�
			}
		}

		return company;
	}

	/**
	 * �ʉ݂̎擾
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param currencyCode �ʉ݃R�[�h
	 * @return �ʉ�
	 */
	protected Currency getCurrency(String companyCode, String currencyCode) {

		String key = companyCode + "<>" + currencyCode;

		Currency currency = currencyMap.get(key);

		if (currency == null) {

			try {
				CurrencyManager manager = (CurrencyManager) getComponent(CurrencyManager.class);

				CurrencySearchCondition condition = new CurrencySearchCondition();
				condition.setCompanyCode(companyCode);
				condition.setCode(currencyCode);

				List<Currency> list = manager.get(condition);

				if (list != null && list.size() > 0) {
					currency = list.get(0);
				}

				currencyMap.put(key, currency);

			} catch (TException e) {
				// �Ȃ�
			}
		}

		return currency;
	}

	/**
	 * ���[�g�̎擾
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param currencyCode �ʉ݃R�[�h
	 * @param newDtl �d��W���[�i��
	 * @return ���[�g
	 */
	protected BigDecimal getRate(String companyCode, String currencyCode, SWK_DTL newDtl) {

		Date slipDate = newDtl.getHAS_DATE() != null ? newDtl.getHAS_DATE() : newDtl.getSWK_DEN_DATE();

		String key = companyCode + "<>" + currencyCode + "<>" + DateUtil.toYMDPlainString(slipDate) + "<>"
			+ newDtl.getAccountBook().value;

		BigDecimal rate = rateMap.get(key);

		if (rate == null) {

			try {
				RateManager manager = (RateManager) getComponent(RateManager.class);
				String tableName = "";

				if (isClosingSlip) {
					tableName = "RATE_KSN_MST";
				} else {
					tableName = "RATE_MST";
				}
				rate = manager.getRate(companyCode, currencyCode, slipDate, tableName);

				if (rate == null) {
					rate = BigDecimal.ONE;
				}

				rateMap.put(key, rate);

			} catch (TException e) {
				// �Ȃ�
			}
		}

		return rate;
	}

	/**
	 * �Ȗڏ��̎擾
	 * 
	 * @param companyCode ��к���
	 * @param itemCode �ȖڃR�[�h
	 * @param subCode �⏕�ȖڃR�[�h
	 * @param detailCode ����ȖڃR�[�h
	 * @return �Ȗڏ��
	 * @throws TException
	 */
	public Item getItem(String companyCode, String itemCode, String subCode, String detailCode) throws TException {

		if (Util.isNullOrEmpty(companyCode) || Util.isNullOrEmpty(itemCode)) {
			return null;
		}

		String key = companyCode + KEY_SEP + itemCode + KEY_SEP + subCode + KEY_SEP + detailCode;

		if (itemMap.containsKey(key)) {

			return itemMap.get(key);

		} else {

			// �Ȗڏ��擾
			ItemManager manager = (ItemManager) getComponent(ItemManager.class);
			Item bean = manager.getItem(companyCode, itemCode, subCode, detailCode);

			itemMap.put(key, bean);

			return bean;
		}

	}

	/**
	 * ���͏������Ɋ���z�Ɋ��Z
	 * 
	 * @param amount ���͋��z
	 * @param rate ���[�g
	 * @param company �Ώۉ��
	 * @param currency ���͒ʉ�
	 * @return ��ʉ݋��z
	 */
	protected BigDecimal convertKeyAmount(BigDecimal amount, BigDecimal rate, Company company, Currency currency) {

		if (DecimalUtil.isNullOrZero(amount)) {
			return BigDecimal.ZERO;
		}

		if (company == null || currency == null) {
			return BigDecimal.ZERO;
		}

		// ��v�n�ݒ�
		AccountConfig conf = company.getAccountConfig();

		if (conf == null) {
			return BigDecimal.ZERO;
		}

		// ��ʉ�
		Currency keyCurrency = conf.getKeyCurrency();

		if (keyCurrency == null) {
			return BigDecimal.ZERO;
		}

		// ���Z
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(company.getAccountConfig().getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(keyCurrency.getDecimalPoint());
		param.setForeignAmount(amount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
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
