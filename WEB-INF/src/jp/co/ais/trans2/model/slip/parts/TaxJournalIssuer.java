package jp.co.ais.trans2.model.slip.parts;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * ����Ŋ֘A
 */
public class TaxJournalIssuer extends TModel {

	/** % */
	public static final BigDecimal NUM_PERCENT = new BigDecimal("0.01");

	/** 100,000,000,000,000,000 */
	public static final BigDecimal NUM_MAX_PAD = new BigDecimal("100000000000000000"); // 18��

	/** �ʉ݃^�C�v */
	protected CurrencyType currencyType = CurrencyType.KEY;

	/** ����Ń}�l�[�W�� */
	protected ConsumptionTaxManager taxManager = (ConsumptionTaxManager) getComponent(ConsumptionTaxManager.class);

	/** ����Ń}�b�v */
	protected Map<String, ConsumptionTax> taxMap = new HashMap<String, ConsumptionTax>();

	/** ����ŏ��� */
	protected ConsumptionTaxSearchCondition param = new ConsumptionTaxSearchCondition();

	/** ����Ŋ���s(����) */
	protected AutoJornalAccount kariukeKmk;

	/** ����Ŋ���s(����)) */
	protected AutoJornalAccount karibaraiKmk;

	/** �`�[ */
	protected Slip slip;

	/** �`�[��� */
	protected SlipType slipType;

	/**
	 * �ʉ݃^�C�v
	 * 
	 * @param currencyType �ʉ݃^�C�v
	 */
	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	/**
	 * �`�[�ɏ���Ŏd���ǉ��B<br>
	 * �{�̊���̏���ŃR�[�h�A����Ŋz�����Ƃ�<br>
	 * ����Ŋ�����쐬���A�`�[�ɃZ�b�g�B
	 * 
	 * @param motoSlip �`�[
	 * @param maxRowNum �ő�s�ԍ�
	 * @return �ő�s�ԍ�
	 * @throws TException
	 */
	public int addJournal(Slip motoSlip, int maxRowNum) throws TException {

		// �ʉ݂��Ƃɕ�����
		Map<String, List<SWK_DTL>> map = splitCurrencyDetails(motoSlip.getDetails());

		// �Ώۂ�������Δԍ���Ԃ��ďI��
		if (map.isEmpty()) {
			return maxRowNum;
		}

		// �`�[
		this.slip = motoSlip;

		// ��ЃR�[�h
		String companyCode = motoSlip.getCompanyCode();

		// ����Ōv�Z�p�p�����[�^�ɉ�ЃR�[�h�Z�b�g
		this.param.setCompanyCode(companyCode);

		// ����Ŋ���s(����)
		this.kariukeKmk = getAutoItem(companyCode, AutoJornalAccountType.RECEIVE_TAX); // �������ŉȖ�

		// ����Ŋ���s(����)
		this.karibaraiKmk = getAutoItem(companyCode, AutoJornalAccountType.PAY_TAX); // ��������ŉȖ�

		this.slipType = getSlipType(slip.getSlipType());

		for (Map.Entry<String, List<SWK_DTL>> entry : map.entrySet()) {
			maxRowNum = addTaxDetails(entry.getKey(), entry.getValue(), maxRowNum);
		}

		return maxRowNum;
	}

	/**
	 * �ʉ݂��Ƃɕ�����
	 * 
	 * @param list
	 * @return �ʉ݂��Ƃɕ�����}�b�v(key=�ʉ݃R�[�h)
	 */
	public static Map<String, List<SWK_DTL>> splitCurrencyDetails(List<SWK_DTL> list) {

		// �ʉ݂��Ƃɕ�����
		Map<String, List<SWK_DTL>> map = new LinkedHashMap<String, List<SWK_DTL>>();

		for (SWK_DTL dtl : list) {
			// ����Ŕ������Ȃ��ꍇ�̓X�L�b�v
			if (DecimalUtil.isNullOrZero(dtl.getSWK_ZEI_KIN())) {
				continue;
			}

			String currency = dtl.getSWK_CUR_CODE();

			List<SWK_DTL> dtlList = map.get(currency);

			if (dtlList == null) {
				dtlList = new ArrayList<SWK_DTL>();
				map.put(currency, dtlList);
			}

			dtlList.add(dtl);
		}

		return map;
	}

	/**
	 * �����P�ʂ��Ƃɕ�����
	 * 
	 * @param taxDtlMap �����P�ʂ��Ƃɕ�����
	 * @param oriMap �����P�ʂ��Ƃɕ�����
	 * @param list �Ώێd�󃊃X�g
	 * @throws TException
	 */
	public void initTaxOccurMap(Map<String, SWK_DTL> taxDtlMap, Map<String, List<SWK_DTL>> oriMap, List<SWK_DTL> list)
		throws TException {
		for (SWK_DTL dtl : list) {

			ConsumptionTax tax = dtl.getTax();

			if (tax == null) {
				tax = getTax(dtl.getSWK_K_KAI_CODE(), dtl.getSWK_ZEI_CODE()); // �Ď擾
			}

			// 2023INVOICE���x�Ή�
			if (Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
				continue;
			}
			// keySet:�ŋ敪(NULL�l��)+�ݎ؋敪+�����CD�̐ŋ敪+����ŃR�[�h+�ʉ�
			String key = DecimalUtil.toBigDecimalNVL(dtl.getSWK_ZEI_KBN()) + "<>" + dtl.getSWK_DC_KBN() + "<>"
				+ tax.getTaxType().value + "<>" + dtl.getSWK_ZEI_CODE() + "<>" + dtl.getSWK_CUR_CODE();
			SWK_DTL taxDtl = null;
			List<SWK_DTL> oriSortList = null;

			if (taxDtlMap.containsKey(key)) {
				taxDtl = taxDtlMap.get(key);
				oriSortList = oriMap.get(key);

			} else {
				taxDtl = new SWK_DTL();
				taxDtl.setAddonInfo(new SlipDetailAddonInfo());
				taxDtl.setSWK_ZEI_KBN(dtl.getSWK_ZEI_KBN());
				taxDtl.setSWK_DC_KBN(dtl.getSWK_DC_KBN());
				taxDtl.setSWK_CUR_CODE(dtl.getSWK_CUR_CODE());
				taxDtl.setSWK_ZEI_CODE(dtl.getSWK_ZEI_CODE());
				taxDtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
				taxDtl.setSWK_ZEI_KIN(BigDecimal.ZERO);
				taxDtl.setTax(tax);

				// Map�Z�b�g
				taxDtlMap.put(key, taxDtl);

				oriSortList = new ArrayList<SWK_DTL>(); // ���z�̍~�����s�ԍ��̏���
				oriMap.put(key, oriSortList);
			}
			// ����Ŋz���Z
			taxDtl.setSWK_IN_ZEI_KIN(add(taxDtl.getSWK_IN_ZEI_KIN(), dtl.getSWK_IN_ZEI_KIN()));
			taxDtl.setSWK_ZEI_KIN(add(taxDtl.getSWK_ZEI_KIN(), dtl.getSWK_ZEI_KIN()));

			// ������Ŋz������
			taxDtl.getAddonInfo().setValue("ORI_SWK_IN_ZEI_KIN", taxDtl.getSWK_IN_ZEI_KIN());
			taxDtl.getAddonInfo().setValue("ORI_SWK_ZEI_KIN", taxDtl.getSWK_ZEI_KIN());

			// ���d��̃N���[��������
			oriSortList.add(dtl.clone());
		}
	}

	/**
	 * @param code
	 * @return �`�[���
	 * @throws TException
	 */
	protected SlipType getSlipType(String code) throws TException {
		if (Util.isNullOrEmpty(code)) {
			return null;
		}

		SlipTypeManager stm = get(SlipTypeManager.class);
		SlipType bean = stm.get(getCompanyCode(), code);

		return bean;
	}

	/**
	 * �`�[�ɏ���Ŏd���ǉ����܂��B<br>
	 * �{�̊���̏���ŃR�[�h�A����Ŋz�����Ƃ�<br>
	 * ����Ŋ�����쐬���A�`�[�ɃZ�b�g���܂��B
	 * 
	 * @param currency
	 * @param dtlList
	 * @param maxRowNum �ő�s�ԍ�
	 * @return �ő�s�ԍ�
	 * @throws TException
	 */
	public int addTaxDetails(String currency, List<SWK_DTL> dtlList, int maxRowNum) throws TException {

		// �`�[��ʂ����f�Ώ�
		boolean isInvoice = getCompany().isCMP_INV_CHK_FLG() && slipType != null && slipType.isINV_SYS_FLG();
		Currency cur = getCurrency(currency);

		// ����ې�(����)
		SWK_DTL uriDR = createUriDrDetail(cur);

		SWK_DTL uriCR = uriDR.clone(); // �ݕ�
		uriCR.setDC(Dc.CREDIT);

		// �d���ې�(����)
		SWK_DTL sireDR = createSireDrDetail(cur);

		SWK_DTL sireCR = sireDR.clone(); // �ݕ�
		sireCR.setDC(Dc.CREDIT);

		// 2023INVOICE���x�Ή��p�F�d��Map
		Map<String, SWK_DTL> taxDtlMap = new LinkedHashMap<String, SWK_DTL>();
		Map<String, List<SWK_DTL>> oriMap = new LinkedHashMap<String, List<SWK_DTL>>(); // ���ېőΏێd��}�b�v

		if (isInvoice) {
			// 2023INVOICE���x�Ή�

			// ����Ŋ֘A�}�b�v������
			initTaxOccurMap(taxDtlMap, oriMap, dtlList);

		} else {
			// ��������

			for (SWK_DTL dtl : dtlList) {
				if (Util.isNullOrEmpty(dtl.getSWK_ZEI_CODE())) {
					continue;
				}

				ConsumptionTax tax = dtl.getTax();

				if (tax == null) {
					tax = getTax(dtl.getSWK_K_KAI_CODE(), dtl.getSWK_ZEI_CODE()); // �Ď擾
				}

				if (tax == null) {
					continue;
				}

				switch (tax.getTaxType()) {
					case SALES: // ����ېł̏ꍇ�A�������łɉ��Z
						if (dtl.isDR()) {
							uriDR.setSWK_IN_KIN(uriDR.getSWK_IN_KIN().add(dtl.getSWK_IN_ZEI_KIN()));
							uriDR.setSWK_KIN(uriDR.getSWK_KIN().add(dtl.getSWK_ZEI_KIN()));

						} else {
							uriCR.setSWK_IN_KIN(uriCR.getSWK_IN_KIN().add(dtl.getSWK_IN_ZEI_KIN()));
							uriCR.setSWK_KIN(uriCR.getSWK_KIN().add(dtl.getSWK_ZEI_KIN()));
						}

						break;

					case PURCHAESE: // �d���ېł̏ꍇ�A��������łɉ��Z
						if (dtl.isDR()) {
							sireDR.setSWK_IN_KIN(sireDR.getSWK_IN_KIN().add(dtl.getSWK_IN_ZEI_KIN()));
							sireDR.setSWK_KIN(sireDR.getSWK_KIN().add(dtl.getSWK_ZEI_KIN()));

						} else {
							sireCR.setSWK_IN_KIN(sireCR.getSWK_IN_KIN().add(dtl.getSWK_IN_ZEI_KIN()));
							sireCR.setSWK_KIN(sireCR.getSWK_KIN().add(dtl.getSWK_ZEI_KIN()));
						}

						break;
				}
			}
		}

		if (isInvoice) {
			// 2023INVOICE���x�Ή�

			// �Ώۂ�������Δԍ���Ԃ�
			if (taxDtlMap.entrySet() == null || taxDtlMap.entrySet().isEmpty()) {
				return maxRowNum;
			}

			for (String key : taxDtlMap.keySet()) {
				SWK_DTL dtl = taxDtlMap.get(key);

				if (dtl.getCurrency() == null) {
					dtl.setCurrency(cur);
				}

				int zeiKbn = dtl.getSWK_ZEI_KBN();
				int dcKbn = dtl.getSWK_DC_KBN();
				int taxType = dtl.getTax().getTaxType().value;
				// �E�v�p
				String zeiName = dtl.getTax().getName();
				String zeiCalcType = zeiKbn == TaxCalcType.OUT.value ? getWord(TaxCalcType.OUT.getName())
					: getWord(TaxCalcType.IN.getName());

				// �o�ߑ[�u�d��ǉ�
				maxRowNum = addTransferTaxDetail(oriMap, key, dtl, maxRowNum);

				if (!DecimalUtil.isNullOrZero(dtl.getSWK_ZEI_KIN())
					|| !DecimalUtil.isNullOrZero(dtl.getSWK_IN_ZEI_KIN())) {
					// ����ł�100%��p�֐U�ւ����ꍇ�͏������Ȃ�

					if (dcKbn == Dc.DEBIT.value && taxType == TaxType.SALES.value) {
						// �ؕ��F����
						SWK_DTL taxDtl = uriDR.clone();
						taxDtl.setSWK_ZEI_KBN(zeiKbn);
						taxDtl.setSWK_KIN(dtl.getSWK_ZEI_KIN());
						taxDtl.setSWK_IN_KIN(dtl.getSWK_IN_ZEI_KIN());
						taxDtl.setSWK_GYO_TEK(zeiCalcType + " " + zeiName); // �Ōv�Z ����Ŗ���
						taxDtl.setSWK_GYO_NO(++maxRowNum);
						slip.addDetail(taxDtl);

					} else if (dcKbn == Dc.CREDIT.value && taxType == TaxType.SALES.value) {
						// �ݕ��F����
						SWK_DTL taxDtl = uriCR.clone();
						taxDtl.setDC(Dc.CREDIT);
						taxDtl.setSWK_ZEI_KBN(zeiKbn);
						taxDtl.setSWK_KIN(dtl.getSWK_ZEI_KIN());
						taxDtl.setSWK_IN_KIN(dtl.getSWK_IN_ZEI_KIN());
						taxDtl.setSWK_GYO_TEK(zeiCalcType + " " + zeiName);
						taxDtl.setSWK_GYO_NO(++maxRowNum);
						slip.addDetail(taxDtl);

					} else if (dcKbn == Dc.DEBIT.value && taxType == TaxType.PURCHAESE.value) {
						// �ؕ��F�d��
						SWK_DTL taxDtl = sireDR.clone();
						taxDtl.setSWK_ZEI_KBN(zeiKbn);
						taxDtl.setSWK_KIN(dtl.getSWK_ZEI_KIN());
						taxDtl.setSWK_IN_KIN(dtl.getSWK_IN_ZEI_KIN());
						taxDtl.setSWK_GYO_TEK(zeiCalcType + " " + zeiName);
						taxDtl.setSWK_GYO_NO(++maxRowNum);
						slip.addDetail(taxDtl);

					} else if (dcKbn == Dc.CREDIT.value && taxType == TaxType.PURCHAESE.value) {
						// �ݕ��F�d��
						SWK_DTL taxDtl = sireCR.clone();
						taxDtl.setDC(Dc.CREDIT);
						taxDtl.setSWK_ZEI_KBN(zeiKbn);
						taxDtl.setSWK_KIN(dtl.getSWK_ZEI_KIN());
						taxDtl.setSWK_IN_KIN(dtl.getSWK_IN_ZEI_KIN());
						taxDtl.setSWK_GYO_TEK(zeiCalcType + " " + zeiName);
						taxDtl.setSWK_GYO_NO(++maxRowNum);
						slip.addDetail(taxDtl);
					}
				}
			}

		} else {
			// ��������
			// ���z������Ώ���ōs��ǉ��B���z���}�C�i�X�Ȃ�ݎؔ��]�Ńv���X��

			// ����ې�(�؎�) �ؕ�
			if (!DecimalUtil.isZero(uriDR.getSWK_KIN())) {
				uriDR.setSWK_GYO_NO(++maxRowNum);
				slip.addDetail(uriDR);
			}

			// �d���ې�(����) �ؕ�
			if (!DecimalUtil.isZero(sireDR.getSWK_KIN())) {
				sireDR.setSWK_GYO_NO(++maxRowNum);
				slip.addDetail(sireDR);
			}

			// ����ې�(�؎�) �ݕ�
			if (!DecimalUtil.isZero(uriCR.getSWK_KIN())) {
				uriCR.setSWK_GYO_NO(++maxRowNum);
				slip.addDetail(uriCR);
			}

			// �d���ې�(����) �ݕ�
			if (!DecimalUtil.isZero(sireCR.getSWK_KIN())) {
				sireCR.setSWK_GYO_NO(++maxRowNum);
				slip.addDetail(sireCR);
			}
		}

		return maxRowNum;
	}

	/**
	 * �o�ߑ[�u�d��ǉ�<br>
	 * dtl.getTax()�͎��O�Ƀ}�b�v�����������̂ŁANULL���蓾�Ȃ�
	 * 
	 * @param oriMap
	 * @param key
	 * @param dtl
	 * @param maxRowNum
	 * @return �Ō�s�ԍ�
	 */
	public int addTransferTaxDetail(Map<String, List<SWK_DTL>> oriMap, String key, SWK_DTL dtl, int maxRowNum) {
		BigDecimal transferRate = null;
		String prefix = "";

		// �o�ߑ[�u�Ȃ�����K�i�����&&(�o�ߑ[�u�T�������ݒ� OR �ݒ�l��100%)
		boolean hasNoTransferRate = !dtl.getTax().isNO_INV_REG_FLG() || dtl.getTax().isNO_INV_REG_FLG() //
			&& (dtl.getTax().getKEKA_SOTI_RATE() == null
				|| DecimalUtil.equals(dtl.getTax().getKEKA_SOTI_RATE(), DecimalUtil.HUNDRED));

		if (!hasNoTransferRate) {
			// �o�ߑ[�u�T���\��
			transferRate = subtract(DecimalUtil.HUNDRED, dtl.getTax().getKEKA_SOTI_RATE()); // 100% - 80%

			if (DecimalUtil.isNullOrZero(dtl.getTax().getKEKA_SOTI_RATE())) {
				// 100%�T���̂��߁A����ŔF�߂Ȃ�
				prefix = "";
			} else {
				prefix = getMessage("I01121", NumberFormatUtil.formatNumber(transferRate, 0) + "%");
			}
		}

		if (!hasNoTransferRate) {
			// �o�ߑ[�u�̐U�֔䗦�����O

			// ���d����x�[�X�Ōv�Z����
			List<SWK_DTL> oriSortList = oriMap.get(key);

			// �������������s��
			transferAdjst(getCompany(), slip, dtl, transferRate, oriSortList);

			boolean isDebugTax = ServerConfig.isFlagOn("trans.slip.debug.tax.transfer");

			// �d���ݒ肷��
			for (SWK_DTL ori : oriSortList) {

				if (DecimalUtil.isNullOrZero(ori.getSWK_KIN()) && DecimalUtil.isNullOrZero(ori.getSWK_IN_KIN())) {
					// ���z���[���͑ΏۊO
					continue;
				}

				// �s�v���N���A
				ori.setAP_ZAN(null);
				ori.setAR_ZAN(null);
				ori.setBsDetail(null);
				ori.setSWK_AUTO_KBN(1);
				ori.setSWK_ZEI_KIN(DecimalUtil.negate(ori.getSWK_KIN())); // ��ʉ݋��z�̃}�C�i�X
				ori.setSWK_IN_ZEI_KIN(DecimalUtil.negate(ori.getSWK_IN_KIN())); // ���͋��z�̃}�C�i�X
				ori.setSWK_ZEI_RATE(BigDecimal.ZERO);
				ori.setSWK_KESI_KBN(0);
				ori.setSWK_KESI_DEN_NO(null);
				ori.setSWK_KESI_DATE(null);
				ori.setSWK_APAR_KESI_KBN(0);
				ori.setSWK_APAR_DEN_NO(null);
				ori.setSWK_APAR_GYO_NO(null);

				String suffix = "";

				if (isDebugTax) {
					suffix = ":N"
						+ (ori.getAddonInfo() != null ? Util.avoidNull(ori.getAddonInfo().getValue("NEED_ADJ")) : "0")
						+ ":G" + ori.getSWK_GYO_NO();
				}

				// �o�ߑ[�u:
				ori.setSWK_GYO_TEK(StringUtil.leftBX(prefix + ori.getSWK_GYO_TEK() + suffix, 80));
				if (ori.getAddonInfo() != null) {
					ori.setSWK_ORI_GYO_NO(Util.avoidNullAsInt(ori.getAddonInfo().getValue("ORI_GYO_NO"))); // ���s�ԍ�
				}
				ori.setSWK_GYO_NO(++maxRowNum);
				ori.setSWK_FREE_KBN(SWK_DTL.FREE_KBN.KEKA_SOTI); // 1:�o�ߑ[�u
				slip.addDetail(ori);
			}
		}

		return maxRowNum;
	}

	/**
	 * �v�Z�Ώە���̊�ʉݍ��v��Ԃ�<br>
	 * ���ӁF�o�ߑ[�u�̐U�֊z�̍��v�ł͂Ȃ�
	 * 
	 * @param list �Ώۃ��X�g
	 * @param startIndex �J�nINDEX
	 * @return ����
	 */
	protected static BigDecimal getBunbo(List<SWK_DTL> list, int startIndex) {
		if (startIndex >= list.size()) {
			return BigDecimal.ZERO;
		}

		BigDecimal total = BigDecimal.ZERO;

		for (int i = startIndex; i < list.size(); i++) {
			BigDecimal zei = list.get(i).getSWK_KIN(); // ��ʉ݋��z�x�[�X
			total = add(total, zei);
		}

		return total;
	}

	/**
	 * @param company ���
	 * @param slip
	 * @param dtl ����Ŗ���
	 * @param transferRate
	 * @param oriList �\�[�g�O���X�g
	 */
	protected static void transferAdjst(Company company, Slip slip, SWK_DTL dtl, BigDecimal transferRate,
		List<SWK_DTL> oriList) {

		int keyDigits = company.getAccountConfig().getKeyCurrency().getDecimalPoint();
		if (slip != null && slip.getHeader().getSWK_ADJ_KBN() == AccountBook.IFRS.value) {
			keyDigits = company.getAccountConfig().getFunctionalCurrency().getDecimalPoint();
		}
		int digits = dtl.getCUR_DEC_KETA();
		BigDecimal zeiKin = dtl.getSWK_ZEI_KIN();
		BigDecimal zeiInKin = dtl.getSWK_IN_ZEI_KIN();
		BigDecimal totalFuriKin = getTransferTotalAmount(company, keyDigits, zeiKin, transferRate); // ���Ώۏ���Ŋz
		BigDecimal totalFuriInKin = getTransferTotalAmount(company, digits, zeiInKin, transferRate); // ���Ώۓ��͏���Ŋz
		BigDecimal divFuriKin = multiply(keyDigits, totalFuriKin, BigDecimal.ONE); // �����_�ȉ�����
		BigDecimal divFuriInKin = multiply(digits, totalFuriInKin, BigDecimal.ONE); // �����_�ȉ�����

		// �U�֍ςݐŊz�����炷
		dtl.setSWK_ZEI_KIN(subtract(dtl.getSWK_ZEI_KIN(), divFuriKin));
		dtl.setSWK_IN_ZEI_KIN(subtract(dtl.getSWK_IN_ZEI_KIN(), divFuriInKin));

		// 1. 1��ڂ̓V���v���������s��
		for (int i = 0; i < oriList.size(); i++) {
			SWK_DTL ori = oriList.get(i);

			if (ori.getAddonInfo() == null) {
				ori.setAddonInfo(new SlipDetailAddonInfo());
			}

			// ���s�ԍ�������
			ori.getAddonInfo().setValue("ORI_GYO_NO", ori.getSWK_GYO_NO());
			ori.getAddonInfo().setValue("ORI_SWK_KIN", ori.getSWK_KIN()); // �����z
			ori.getAddonInfo().setValue("DIV_TTL_KIN", divFuriKin); // �o�ߑ[�u�����z���z
			ori.getAddonInfo().setValue("DIV_TTL_IN_KIN", divFuriInKin); // �o�ߑ[�u�����z���z(����)

			if (hasDecimalPoint(keyDigits, ori, transferRate)) {
				ori.getAddonInfo().setValue("NEED_ADJ", "1"); // �[������d��A2��ڒ����ΏۂƂ���
			} else {
				ori.getAddonInfo().setValue("NEED_ADJ", "0"); // �[���Ȃ��d��A2��ڒ����ΏۊO�Ƃ���
			}

			// 1��ڌo�ߑ[�u���z��ݒ肷��
			BigDecimal furiKin = getSimpleTransferAmount(keyDigits, ori.getSWK_ZEI_KIN(), transferRate); // ���Ώۏ���Ŋz
			BigDecimal furiInKin = getSimpleTransferAmount(digits, ori.getSWK_IN_ZEI_KIN(), transferRate); // ���Ώۓ��͏���Ŋz

			ori.setSWK_KIN(furiKin);
			ori.setSWK_IN_KIN(furiInKin);

			ori.getAddonInfo().setValue("DIV_KIN", furiKin); // �o�ߑ[�u�����z
			ori.getAddonInfo().setValue("DIV_IN_KIN", furiInKin); // �o�ߑ[�u�����z(����)

			if (DecimalUtil.isNullOrZero(ori.getSWK_KIN()) && DecimalUtil.isNullOrZero(ori.getSWK_IN_KIN())) {
				// 1��ڌv�Z���ʂ��[���Ȃ�A�ΏۊO
				ori.getAddonInfo().setValue("NEED_ADJ", "2"); // �[���Ȃ��d��A2��ڒ����ΏۊO�Ƃ���
			}

		}

		// 2. �o�ߑ[�u�z�����v����
		BigDecimal resumFuriKin = BigDecimal.ZERO;
		BigDecimal resumFuriInKin = BigDecimal.ZERO;
		for (int i = 0; i < oriList.size(); i++) {
			SWK_DTL ori = oriList.get(i);

			resumFuriKin = add(resumFuriKin, ori.getSWK_KIN());
			resumFuriInKin = add(resumFuriInKin, ori.getSWK_IN_KIN());
		}

		// 3. ���z
		BigDecimal saKin = subtract(totalFuriKin, resumFuriKin);
		BigDecimal saInKin = subtract(totalFuriInKin, resumFuriInKin);
		TaxJournalIssuerComparator comp = null;

		boolean isDebugTax = ServerConfig.isFlagOn("trans.slip.debug.tax.transfer");
		if (isDebugTax) {
			// �����O���
			debugSWK_DTL("-- ----�����O���----", oriList, keyDigits, digits);
		}

		if (saKin.signum() == 1 //
			|| saKin.signum() == 0 && saInKin.signum() == 1 //
		) {
			// ��ʉ݋��z���z���O
			// ��ʉ݋��z���z���O�����͋��z���z���O
			comp = new TaxJournalIssuerComparator(true); // ���z�~��
			Collections.sort(oriList, comp);

			int i = 1;
			for (SWK_DTL d : oriList) {
				d.setSWK_GYO_NO(i++);
			}

			if (isDebugTax) {
				// ��������
				debugSWK_DTL("-- ----���z���O��������----", oriList, keyDigits, digits);
			}

		} else if (saKin.signum() == -1 //
			|| saKin.signum() == 0 && saInKin.signum() == -1 //
		) {
			// ��ʉ݋��z���z���O
			// ��ʉ݋��z���z���O�����͋��z���z���O
			comp = new TaxJournalIssuerComparator(false); // ���z����
			Collections.sort(oriList, comp);

			int i = 1;
			for (SWK_DTL d : oriList) {
				d.setSWK_GYO_NO(i++);
			}

			if (isDebugTax) {
				// ��������
				debugSWK_DTL("-- ----���z���O��������----", oriList, keyDigits, digits);
			}

		} else {
			// 2��ڒ����s�v�A�߂�
			return;
		}

		BigDecimal adjUnit = new BigDecimal("1").movePointLeft(keyDigits); // ��ʉ݋��z�����P��
		BigDecimal adjInUnit = new BigDecimal("1").movePointLeft(digits); // ���͋��z�����P��

		if (adjUnit.signum() != saKin.signum()) {
			adjUnit = adjUnit.abs();
			if (saKin.signum() == -1) {
				adjUnit = DecimalUtil.negate(adjUnit);
			}
		}
		if (adjInUnit.signum() != saInKin.signum()) {
			adjInUnit = adjInUnit.abs();
			if (saInKin.signum() == -1) {
				adjInUnit = DecimalUtil.negate(adjInUnit);
			}
		}

		// 4. 2��ڂ͒����P�ʂŒ������s��
		int i = 0;

		while (!DecimalUtil.isNullOrZero(saKin) || !DecimalUtil.isNullOrZero(saInKin)) {

			if (i >= oriList.size()) {
				// �ő�T�C�Y�ɂȂ��Ă������ł��Ȃ������ꍇ�A1�s�ڂ̒������s��
				SWK_DTL first = oriList.get(0);
				first.setSWK_KIN(add(first.getSWK_KIN(), saKin));
				first.setSWK_IN_KIN(add(first.getSWK_IN_KIN(), saInKin));

				first.getAddonInfo().setValue("DIV_KIN", first.getSWK_KIN()); // �o�ߑ[�u�����z
				first.getAddonInfo().setValue("DIV_IN_KIN", first.getSWK_IN_KIN()); // �o�ߑ[�u�����z(����)

				first.getAddonInfo().setValue("DIV_SA_2_KIN", saKin); // �o�ߑ[�u�����z:���z
				first.getAddonInfo().setValue("DIV_SA_2_IN_KIN", saKin); // �o�ߑ[�u�����z(����):���z
				break;
			}

			SWK_DTL ori = oriList.get(i++);

			if (ori.getAddonInfo() != null && Util.equals("1", Util.avoidNull(ori.getAddonInfo().getValue("NEED_ADJ"))) //
				&& (!DecimalUtil.isNullOrZero(ori.getSWK_KIN()) || !DecimalUtil.isNullOrZero(ori.getSWK_IN_KIN()))) {
				// �����Ώۂ̈�Ԑ擪�B���я��͊��Ƀ\�[�g�ς�

				if (!DecimalUtil.isNullOrZero(saKin)) {
					ori.setSWK_KIN(add(ori.getSWK_KIN(), adjUnit));
					saKin = subtract(saKin, adjUnit);
				}
				if (!DecimalUtil.isNullOrZero(saInKin)) {
					ori.setSWK_IN_KIN(add(ori.getSWK_IN_KIN(), adjInUnit));
					saInKin = subtract(saInKin, adjInUnit);
				}

				ori.getAddonInfo().setValue("DIV_KIN", ori.getSWK_KIN()); // �o�ߑ[�u�����z
				ori.getAddonInfo().setValue("DIV_IN_KIN", ori.getSWK_IN_KIN()); // �o�ߑ[�u�����z(����)

				ori.getAddonInfo().setValue("DIV_SA_1_KIN", adjUnit); // �o�ߑ[�u�����z:���z
				ori.getAddonInfo().setValue("DIV_SA_1_IN_KIN", adjInUnit); // �o�ߑ[�u�����z(����):���z
			}
		}

		// ���s�ԍ����ōēx�\�[�g��������
		comp.useOriginalRowNo();

		// �ēx�\�[�g���s��
		Collections.sort(oriList, comp);

		if (isDebugTax) {
			// ��������
			debugSWK_DTL("-- ----�ēx�\�[�g����----", oriList, keyDigits, digits);
		}

	}

	/**
	 * @param title
	 * @param oriList
	 * @param keyDigits
	 * @param digits
	 */
	@SuppressWarnings("unused")
	protected static void debugSWK_DTL(String title, List<SWK_DTL> oriList, int keyDigits, int digits) {
		System.out.println(title);

		for (SWK_DTL d : oriList) {
			StringBuilder sb = new StringBuilder();
			sb.append(d.getSWK_GYO_NO()).append("\t");
			sb.append(d.getSWK_KMK_CODE()).append("-").append(Util.avoidNull(d.getSWK_HKM_CODE())).append(":");
			sb.append(d.getSWK_KMK_NAME_S()).append("-").append(Util.avoidNull(d.getSWK_HKM_NAME_S()));

			int len = sb.length();
			int maxLen = Math.max(len, 50);

			if (len < maxLen) {
				sb.append(StringUtil.spaceFill("", maxLen - len));
			}

			sb.append("\t�����z:");
			sb.append(NumberFormatUtil
				.formatNumber(DecimalUtil.toBigDecimalNVL(d.getAddonInfo().getValue("ORI_SWK_KIN")), keyDigits));

			sb.append("\t�o�ߑ[�u���z:");
			sb.append(NumberFormatUtil.formatNumber(d.getSWK_KIN(), keyDigits));

			len = sb.length();
			maxLen = Math.max(len, 80);

			if (len < maxLen) {
				sb.append(StringUtil.spaceFill("", maxLen - len));
			}

			sb.append("\t�s�E�v:").append(d.getSWK_GYO_TEK());

			if (d.getAddonInfo() != null) {
				// ���s�ԍ�
				sb.append("\t���s�ԍ�:").append(d.getAddonInfo().getValue("ORI_GYO_NO"));
				// �[������d��A2��ڒ����ΏۂƂ���
				sb.append("\t�����Ώ�[=1]:").append(Util.avoidNullAsInt(d.getAddonInfo().getValue("NEED_ADJ")));
			}

			sb.append("\t�o�ߑ[�u���z�̑��z:");
			sb.append(NumberFormatUtil
				.formatNumber(DecimalUtil.toBigDecimalNVL(d.getAddonInfo().getValue("DIV_TTL_KIN")), keyDigits));
			sb.append("\t�o�ߑ[�u���z:");
			sb.append(NumberFormatUtil.formatNumber(DecimalUtil.toBigDecimalNVL(d.getAddonInfo().getValue("DIV_KIN")),
				keyDigits));
			sb.append("\t�o�ߑ[�u���z(���z����1):");
			sb.append(NumberFormatUtil
				.formatNumber(DecimalUtil.toBigDecimalNVL(d.getAddonInfo().getValue("DIV_SA_1_KIN")), keyDigits));
			sb.append("\t�o�ߑ[�u���z(���z����2):");
			sb.append(NumberFormatUtil
				.formatNumber(DecimalUtil.toBigDecimalNVL(d.getAddonInfo().getValue("DIV_SA_2_KIN")), keyDigits));

			System.out.println(sb.toString());
		}
	}

	/**
	 * �o�ߑ[�u���z�̎擾
	 * 
	 * @param company
	 * @param digits
	 * @param zeiKin
	 * @param transferRate
	 * @return �o�ߑ[�u���z(������Ŋz-�T���\�z)
	 */
	protected static BigDecimal getTransferTotalAmount(Company company, int digits, BigDecimal zeiKin,
		BigDecimal transferRate) {

		if (DecimalUtil.equals(DecimalUtil.HUNDRED, transferRate)) {
			// 100%�T���̂��߁A����ŔF�߂Ȃ�
			return zeiKin;
		}

		// ��
		// ����Ŋz��107
		// �T���\����80%
		// �o�ߑ[�u���z��107 - (107 * 80%�̉�В[������)
		// �؂�̂Ē[�������̏ꍇ
		// �o�ߑ[�u���z��107 - 85 �� 22

		// �T���\�z���v�Z����
		BigDecimal amount = multiply(15, zeiKin, subtract(DecimalUtil.HUNDRED, transferRate), NUM_PERCENT);
		BigDecimal kojyo = BigDecimal.ZERO;

		// ��Аݒ�Œ[������(��������Œ[�������𗘗p)
		ExchangeFraction ef = company.getAccountConfig().getPaymentFunction();
		switch (ef) {
			case TRUNCATE: // �؎�
				kojyo = DecimalUtil.truncateNum(amount, digits);
				break;

			case ROUND_UP: // �؏�
				kojyo = DecimalUtil.ceilingNum(amount, digits);
				break;

			case ROUND_OFF: // �l�̌ܓ�
				kojyo = DecimalUtil.roundNum(amount, digits);
				break;

		}

		// ������Ŋz-�T���\�z
		return subtract(zeiKin, kojyo);
	}

	/**
	 * ��ʉݏ���Ŋz�~�o�ߑ[�u�T���\���̒[������
	 * 
	 * @param keyDigits
	 * @param ori
	 * @param transferRate
	 * @return true:�����_�ȉ������A�[������������
	 */
	protected static boolean hasDecimalPoint(int keyDigits, SWK_DTL ori, BigDecimal transferRate) {

		if (DecimalUtil.equals(DecimalUtil.HUNDRED, transferRate)) {
			// 100%�T���̂��߁A����ŔF�߂Ȃ�
			return false;
		}

		// ��
		// ����Ŋz�~�o�ߑ[�u�T���\��

		// �����_�ȉ��������������Ȃ��P�[�X
		// ����Ŋz��100
		// �o�ߑ[�u�T���\����20%
		// ����Ŋz�~�o�ߑ[�u�T���\����100*20%=20

		// �����_�ȉ���������������P�[�X
		// ����Ŋz��101
		// �o�ߑ[�u�T���\����20%
		// ����Ŋz�~�o�ߑ[�u�T���\����101*20%=20.2 <> 20

		// ����Ŋz�~�o�ߑ[�u�T���\��
		BigDecimal transferKin = multiply(15, ori.getSWK_ZEI_KIN(), transferRate, NUM_PERCENT);
		// �w�菬���_�ȉ������Ŏl�̌ܓ�
		BigDecimal checkKin = DecimalUtil.roundNum(transferKin, keyDigits);

		// ��r
		if (!equals(transferKin, checkKin)) {
			return true;
		}

		return false;
	}

	/**
	 * �w�����Ŋz�~�o�ߑ[�u�T���\���̋��z
	 * 
	 * @param digits
	 * @param zeiKin
	 * @param transferRate
	 * @return �w�����Ŋz�~�o�ߑ[�u�T���\���̋��z
	 */
	protected static BigDecimal getSimpleTransferAmount(int digits, BigDecimal zeiKin, BigDecimal transferRate) {

		if (DecimalUtil.equals(DecimalUtil.HUNDRED, transferRate)) {
			// 100%�T���̂��߁A����ŔF�߂Ȃ�
			return zeiKin;
		}

		// �[�����������Ȃ��ꍇ�ɁA�o�ߑ[�u�T���\���Ōv�Z�������z��Ԃ�

		// ����Ŋz�~�o�ߑ[�u�T���\��
		BigDecimal transferKin = multiply(15, zeiKin, transferRate, NUM_PERCENT);
		// �w�菬���_�ȉ������Ŏl�̌ܓ�
		BigDecimal checkKin = DecimalUtil.roundNum(transferKin, digits);

		return checkKin;
	}

	/**
	 * @param dtl
	 * @return �\�[�g�L�[
	 */
	protected static String getOriSortKey(SWK_DTL dtl) {
		// ���z�̍~�����s�ԍ��̏���
		BigDecimal amt = subtract(NUM_MAX_PAD, dtl.getSWK_KIN());
		int rowNo = 1000000 + dtl.getSWK_GYO_NO();
		return NumberFormatUtil.formatNumber(amt, 4) + "<>" + Integer.toString(rowNo);
	}

	/**
	 * @param map
	 * @return �[���̔��f�d��
	 */
	protected static SWK_DTL getTransferAdjustDetail(Map<String, SWK_DTL> map) {

		String lastKey = null;
		for (String key : map.keySet()) {
			// �ŏI�L�[���g��
			lastKey = key;
		}

		return map.get(lastKey);
	}

	/**
	 * @param a
	 * @param b
	 * @return �a(a + b)
	 */
	protected static BigDecimal add(BigDecimal a, BigDecimal b) {
		return DecimalUtil.avoidNull(a).add(DecimalUtil.avoidNull(b));
	}

	/**
	 * @param a
	 * @param b
	 * @return ��(a - b)
	 */
	protected static BigDecimal subtract(BigDecimal a, BigDecimal b) {
		return DecimalUtil.avoidNull(a).subtract(DecimalUtil.avoidNull(b));
	}

	/**
	 * ���ʂ̌v�Z(a * b)
	 * 
	 * @param a
	 * @param b
	 * @param digits
	 * @return ����
	 */
	public static BigDecimal multiply(BigDecimal a, BigDecimal b, int digits) {
		return DecimalUtil.roundNum(DecimalUtil.avoidNull(a).multiply(DecimalUtil.avoidNull(b)), digits);
	}

	/**
	 * ���ʂ̌v�Z(a * b)<br>
	 * �o�ߑ[�u��p�֐��Ƃ��āA�l�̌ܓ��Œ�
	 * 
	 * @param digits
	 * @param nums
	 * @return ����
	 */
	public static BigDecimal multiply(int digits, BigDecimal... nums) {
		BigDecimal total = BigDecimal.ONE;
		if (nums.length > 0) {
			for (BigDecimal num : nums) {
				total = multiply(total, num, 15);
			}
		}
		return DecimalUtil.roundNum(total, digits);
	}

	/**
	 * ���l�����r
	 * 
	 * @param a
	 * @param b
	 * @return true:����
	 */
	public static boolean equals(BigDecimal a, BigDecimal b) {
		if (a == null && b != null) {
			return false;
		}
		if (a != null && b == null) {
			return false;
		}
		if (a == null && b == null) {
			return true;
		}

		return a.compareTo(b) == 0;
	}

	/**
	 * ����ې�(����)�d��W���[�i���̍쐬
	 * 
	 * @param cur �ʉݏ��
	 * @return ����ې�(����)�d��W���[�i��
	 */
	protected SWK_DTL createUriDrDetail(Currency cur) {

		// ����ې�(����)
		SWK_DTL uriDR = slip.createDetail(); // �ؕ�
		uriDR.setCurrencyType(currencyType); // BookNo.
		uriDR.setSWK_KMK_CODE(kariukeKmk.getItemCode());
		uriDR.setSWK_KMK_NAME(kariukeKmk.getItemName());
		uriDR.setSWK_KMK_NAME_S(kariukeKmk.getItemNames());
		uriDR.setSWK_HKM_CODE(kariukeKmk.getSubItemCode());
		uriDR.setSWK_HKM_NAME(kariukeKmk.getSubItemName());
		uriDR.setSWK_HKM_NAME_S(kariukeKmk.getSubItemNames());
		uriDR.setSWK_UKM_CODE(kariukeKmk.getDetailItemCode());
		uriDR.setSWK_UKM_NAME(kariukeKmk.getDetailItemName());
		uriDR.setSWK_UKM_NAME_S(kariukeKmk.getDetailItemNames());
		uriDR.setSWK_DEP_CODE(kariukeKmk.getDepertmentCode());
		uriDR.setSWK_DEP_NAME(kariukeKmk.getDepertmentName());
		uriDR.setSWK_DEP_NAME_S(kariukeKmk.getDepertmentNames());
		uriDR.setSWK_K_KAI_CODE(slip.getCompanyCode());
		uriDR.setDC(Dc.DEBIT);
		uriDR.setCurrency(cur);
		uriDR.setSWK_CUR_RATE(null);
		uriDR.setSWK_AUTO_KBN(SWK_DTL.AUTO_KBN.AUTO);
		uriDR.setSWK_IN_KIN(BigDecimal.ZERO);
		uriDR.setSWK_KIN(BigDecimal.ZERO);
		uriDR.setHAS_DATE(null);
		uriDR.setTaxJornal(true);
		uriDR.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE); // ��ې�
		uriDR.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
		uriDR.setSWK_ZEI_KIN(BigDecimal.ZERO);
		uriDR.setSWK_GYO_TEK(slip.getRemarks()); // �E�v
		return uriDR;
	}

	/**
	 * �d���ې�(����)�d��W���[�i���̍쐬
	 * 
	 * @param cur �ʉݏ��
	 * @return �d���ې�(����)�d��W���[�i��
	 */
	protected SWK_DTL createSireDrDetail(Currency cur) {

		// �d���ې�(����)
		SWK_DTL sireDR = slip.createDetail(); // �ؕ�
		sireDR.setCurrencyType(currencyType); // BookNo.
		sireDR.setSWK_KMK_CODE(karibaraiKmk.getItemCode());
		sireDR.setSWK_KMK_NAME(karibaraiKmk.getItemName());
		sireDR.setSWK_KMK_NAME_S(karibaraiKmk.getItemNames());
		sireDR.setSWK_HKM_CODE(karibaraiKmk.getSubItemCode());
		sireDR.setSWK_HKM_NAME(karibaraiKmk.getSubItemName());
		sireDR.setSWK_HKM_NAME_S(karibaraiKmk.getSubItemNames());
		sireDR.setSWK_UKM_CODE(karibaraiKmk.getDetailItemCode());
		sireDR.setSWK_UKM_NAME(karibaraiKmk.getDetailItemName());
		sireDR.setSWK_UKM_NAME_S(karibaraiKmk.getDetailItemNames());
		sireDR.setSWK_DEP_CODE(karibaraiKmk.getDepertmentCode());
		sireDR.setSWK_DEP_NAME(karibaraiKmk.getDepertmentName());
		sireDR.setSWK_DEP_NAME_S(karibaraiKmk.getDepertmentNames());
		sireDR.setSWK_K_KAI_CODE(slip.getCompanyCode());
		sireDR.setDC(Dc.DEBIT);
		sireDR.setCurrency(cur);
		sireDR.setSWK_CUR_RATE(null);
		sireDR.setSWK_AUTO_KBN(SWK_DTL.AUTO_KBN.AUTO);
		sireDR.setSWK_IN_KIN(BigDecimal.ZERO);
		sireDR.setSWK_KIN(BigDecimal.ZERO);
		sireDR.setHAS_DATE(null);
		sireDR.setTaxJornal(true);
		sireDR.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE); // ��ې�
		sireDR.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
		sireDR.setSWK_ZEI_KIN(BigDecimal.ZERO);
		sireDR.setSWK_GYO_TEK(slip.getRemarks()); // �E�v
		return sireDR;
	}

	/**
	 * ����Ŏ擾
	 * 
	 * @param kCompanyCode �v���ЃR�[�h
	 * @param taxCode ����ŃR�[�h
	 * @return ����ŏ��
	 * @throws TException
	 */
	protected ConsumptionTax getTax(String kCompanyCode, String taxCode) throws TException {

		if (taxMap == null) {
			taxMap = new HashMap<String, ConsumptionTax>();
		}

		String companyCode = kCompanyCode;
		if (Util.isNullOrEmpty(kCompanyCode)) {
			companyCode = getCompanyCode();
		}

		String key = companyCode + "<>" + taxCode;

		if (taxMap.containsKey(key)) {
			return taxMap.get(key);
		}

		param.setCompanyCode(companyCode);
		param.setCode(taxCode);
		List<ConsumptionTax> list = taxManager.get(param);

		if (list == null || list.isEmpty()) {
			throw new TException(getMessage("I00129", "C00286", taxCode)); // �����[{0}]�̎擾�Ɏ��s���܂����B
		}

		ConsumptionTax bean = list.get(0);
		taxMap.put(key, bean);

		return bean;
	}

	/**
	 * �ʉݏ��擾
	 * 
	 * @param currencyCode �ʉ݃R�[�h
	 * @return �ʉ�
	 * @throws TException
	 */
	protected Currency getCurrency(String currencyCode) throws TException {
		CurrencySearchCondition condition = new CurrencySearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(currencyCode);

		CurrencyManager curManager = (CurrencyManager) getComponent(CurrencyManager.class);
		List<Currency> list = curManager.get(condition);

		if (list == null || list.isEmpty()) {
			throw new TException(getMessage("I00129", "C00371", currencyCode)); // �J���p //"�ʉ�[{0}]�̎擾�Ɏ��s���܂����B",
		}

		return list.get(0);
	}

	/**
	 * �����d��Ȗڂ��擾����.
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param kmkCnt �Ȗڐ���敪
	 * @return �����d��Ȗ�
	 * @throws TException
	 */
	protected AutoJornalAccount getAutoItem(String companyCode, AutoJornalAccountType kmkCnt) throws TException {
		SlipManager manager = (SlipManager) getComponent(SlipManager.class);

		AutoJornalAccount bean = manager.getAutoJornalAccount(companyCode, kmkCnt);

		if (bean == null) {
			throw new TException(getMessage("I00163", companyCode));// "���[{0}]�̎����d��Ȗ�:����/��������ł��ݒ肳��Ă��܂���B
		}

		return bean;
	}

	/**
	 * �`�[�ɏ���Ŏd���ǉ��B<br>
	 * �{�̊���̏���ŃR�[�h�A����Ŋz�����Ƃ�<br>
	 * ����Ŋ�����쐬���A�`�[�ɃZ�b�g�B
	 * 
	 * @param motoSlip �`�[
	 * @param maxRowNum �ő�s�ԍ�
	 * @return �ő�s�ԍ�
	 * @throws TException
	 */
	public int addTransferJournal(Slip motoSlip, int maxRowNum) throws TException {

		// �`�[
		this.slip = motoSlip;

		// ��ЃR�[�h
		String companyCode = motoSlip.getCompanyCode();

		// ����Ōv�Z�p�p�����[�^�ɉ�ЃR�[�h�Z�b�g
		this.param.setCompanyCode(companyCode);

		this.slipType = getSlipType(slip.getSlipType());

		// �`�[��ʂ����f�Ώ�
		boolean isInvoice = getCompany().isCMP_INV_CHK_FLG() && slipType != null && slipType.isINV_SYS_FLG();

		if (!isInvoice) {
			return maxRowNum;
		}

		// ���O�`�F�b�N(�o�ߑ[�u�d��Ƃ��Ď�荞��ł���ꍇ�͏����s�v)
		for (SWK_DTL dtl : motoSlip.getDetails()) {
			if (dtl.getSWK_FREE_KBN() == SWK_DTL.FREE_KBN.KEKA_SOTI) {
				return maxRowNum;
			}
		}

		// �ʉ݂��Ƃɕ�����
		Map<String, List<SWK_DTL>> map = splitCurrencyDetails(motoSlip.getDetails());

		// �Ώۂ�������Δԍ���Ԃ��ďI��
		if (map.isEmpty()) {
			return maxRowNum;
		}

		// ����Ŋ���s(����)
		this.kariukeKmk = getAutoItem(companyCode, AutoJornalAccountType.RECEIVE_TAX); // �������ŉȖ�

		// ����Ŋ���s(����)
		this.karibaraiKmk = getAutoItem(companyCode, AutoJornalAccountType.PAY_TAX); // ��������ŉȖ�

		boolean hasChanged = false;

		for (Map.Entry<String, List<SWK_DTL>> entry : map.entrySet()) {

			String currency = entry.getKey();
			Currency cur = getCurrency(currency);
			List<SWK_DTL> dtlList = entry.getValue();

			// ����ې�(����)
			SWK_DTL uriDR = createUriDrDetail(cur);

			SWK_DTL uriCR = uriDR.clone(); // �ݕ�
			uriCR.setDC(Dc.CREDIT);

			// �d���ې�(����)
			SWK_DTL sireDR = createSireDrDetail(cur);

			SWK_DTL sireCR = sireDR.clone(); // �ݕ�
			sireCR.setDC(Dc.CREDIT);

			// 2023INVOICE���x�Ή��p�F�d��Map
			Map<String, SWK_DTL> taxDtlMap = new LinkedHashMap<String, SWK_DTL>();
			Map<String, List<SWK_DTL>> oriMap = new LinkedHashMap<String, List<SWK_DTL>>(); // ���ېőΏێd��}�b�v

			// ����Ŋ֘A�}�b�v������
			initTaxOccurMap(taxDtlMap, oriMap, dtlList);

			// �Ώۂ�������Δԍ���Ԃ�
			if (taxDtlMap.entrySet() == null || taxDtlMap.entrySet().isEmpty()) {
				return maxRowNum;
			}

			for (String key : taxDtlMap.keySet()) {
				SWK_DTL dtl = taxDtlMap.get(key);

				if (DecimalUtil.isNullOrZero(dtl.getTax().getKEKA_SOTI_RATE())) {
					// �o�ߑ[�u���ݒ�
					continue;
				}

				if (dtl.getCurrency() == null) {
					dtl.setCurrency(cur);
				}

				int zeiKbn = dtl.getSWK_ZEI_KBN();
				int dcKbn = dtl.getSWK_DC_KBN();
				int taxType = dtl.getTax().getTaxType().value;
				// �E�v�p
				String zeiName = dtl.getTax().getName();
				String zeiCalcType = zeiKbn == TaxCalcType.OUT.value ? getWord(TaxCalcType.OUT.getName())
					: getWord(TaxCalcType.IN.getName());

				// �o�ߑ[�u�d��ǉ�
				maxRowNum = addTransferTaxDetail(oriMap, key, dtl, maxRowNum);

				// // �Ŋz = ���̐Ŋz - �o�ߑ[�u������Ŋz (�ړI�͌o�ߑ[�u�����̂݁A�d�󒲐�����)
				// // ������Ŋz
				// BigDecimal zeiKin = DecimalUtil.toBigDecimalNVL(dtl.getAddonInfo().getValue("ORI_SWK_ZEI_KIN"));
				// BigDecimal zeiInKin = DecimalUtil.toBigDecimalNVL(dtl.getAddonInfo().getValue("ORI_SWK_IN_ZEI_KIN"));
				// zeiKin = subtract(zeiKin, dtl.getSWK_ZEI_KIN());
				// zeiInKin = subtract(zeiInKin, dtl.getSWK_IN_ZEI_KIN());
				BigDecimal zeiKin = dtl.getSWK_ZEI_KIN();
				BigDecimal zeiInKin = dtl.getSWK_IN_ZEI_KIN();

				if (dcKbn == Dc.DEBIT.value && taxType == TaxType.SALES.value) {
					// �ؕ��F����
					SWK_DTL taxDtl = createUriDrDetail(cur);
					taxDtl.setAddonInfo(new SlipDetailAddonInfo());
					taxDtl.getAddonInfo().setValue("NEW_DTL_FLG", "1"); // �V�K�t���O
					taxDtl.setSWK_ZEI_KBN(zeiKbn);
					taxDtl.setSWK_KIN(zeiKin);
					taxDtl.setSWK_IN_KIN(zeiInKin);
					taxDtl.setSWK_GYO_TEK(zeiCalcType + " " + zeiName); // �Ōv�Z ����Ŗ���
					taxDtl.setSWK_GYO_NO(++maxRowNum);
					slip.addDetail(taxDtl);

				} else if (dcKbn == Dc.CREDIT.value && taxType == TaxType.SALES.value) {
					// �ݕ��F����
					SWK_DTL taxDtl = createUriDrDetail(cur);
					taxDtl.setAddonInfo(new SlipDetailAddonInfo());
					taxDtl.getAddonInfo().setValue("NEW_DTL_FLG", "1"); // �V�K�t���O
					taxDtl.setDC(Dc.CREDIT);
					taxDtl.setSWK_ZEI_KBN(zeiKbn);
					taxDtl.setSWK_KIN(zeiKin);
					taxDtl.setSWK_IN_KIN(zeiInKin);
					taxDtl.setSWK_GYO_TEK(zeiCalcType + " " + zeiName);
					taxDtl.setSWK_GYO_NO(++maxRowNum);
					slip.addDetail(taxDtl);

				} else if (dcKbn == Dc.DEBIT.value && taxType == TaxType.PURCHAESE.value) {
					// �ؕ��F�d��
					SWK_DTL taxDtl = createSireDrDetail(cur);
					taxDtl.setAddonInfo(new SlipDetailAddonInfo());
					taxDtl.getAddonInfo().setValue("NEW_DTL_FLG", "1"); // �V�K�t���O
					taxDtl.setSWK_ZEI_KBN(zeiKbn);
					taxDtl.setSWK_KIN(zeiKin);
					taxDtl.setSWK_IN_KIN(zeiInKin);
					taxDtl.setSWK_GYO_TEK(zeiCalcType + " " + zeiName);
					taxDtl.setSWK_GYO_NO(++maxRowNum);
					slip.addDetail(taxDtl);

				} else if (dcKbn == Dc.CREDIT.value && taxType == TaxType.PURCHAESE.value) {
					// �ݕ��F�d��
					SWK_DTL taxDtl = createSireDrDetail(cur);
					taxDtl.setAddonInfo(new SlipDetailAddonInfo());
					taxDtl.getAddonInfo().setValue("NEW_DTL_FLG", "1"); // �V�K�t���O
					taxDtl.setDC(Dc.CREDIT);
					taxDtl.setSWK_ZEI_KBN(zeiKbn);
					taxDtl.setSWK_KIN(zeiKin);
					taxDtl.setSWK_IN_KIN(zeiInKin);
					taxDtl.setSWK_GYO_TEK(zeiCalcType + " " + zeiName);
					taxDtl.setSWK_GYO_NO(++maxRowNum);
					slip.addDetail(taxDtl);
				}

				hasChanged = true;
			}
		}

		if (hasChanged) {
			// �ύX������ꍇ�Ɋ����捞�Ώۏ���Ŏd����폜����
			for (int i = slip.getDetailCount() - 1; i >= 0; i--) {
				SWK_DTL dtl = slip.getDetails().get(i);

				if (dtl.getAddonInfo() != null
					&& Util.equals("1", Util.avoidNull(dtl.getAddonInfo().getValue("NEW_DTL_FLG")))) {
					// �V�K�t���O���P�͑ΏۊO
					continue;
				}

				if (dtl.getSWK_AUTO_KBN() == 1 //
					&& isTaxAutoItem(dtl.getSWK_KMK_CODE(), dtl.getSWK_HKM_CODE(), dtl.getSWK_UKM_CODE())) {
					// SWK_AUTO_KBN=1�A�d��̉Ȗڂ���������ł͍폜�Ώ�
					slip.getDetails().remove(i);
				}

			}

			// �폜���ύX������΁A�o�����X�`�F�b�N���s��
			BigDecimal dr = BigDecimal.ZERO;
			BigDecimal cr = BigDecimal.ZERO;
			for (SWK_DTL dtl : slip.getDetails()) {
				dr = add(dr, dtl.getDebitKeyAmount());
				cr = add(cr, dtl.getCreditKeyAmount());
			}

			if (!equals(dr, cr)) {
				// W00121 �ݎ؂��o�����X���Ă��܂���B
				throw new TException("W00121");
			}

		}

		return maxRowNum;
	}

	/**
	 * @param itemCode
	 * @param subItemCode
	 * @param detailItemCode
	 * @return true:��������Ŏ����d��̉Ȗ�
	 */
	public boolean isTaxAutoItem(String itemCode, String subItemCode, String detailItemCode) {
		if (karibaraiKmk != null && //
			Util.equals(itemCode, karibaraiKmk.getItemCode()) //
			&& Util.equals(subItemCode, karibaraiKmk.getSubItemCode()) //
			&& Util.equals(detailItemCode, karibaraiKmk.getDetailItemCode()) //
		) {
			return true;
		}
		return false;
	}
}
