package jp.co.ais.trans2.common.model.ui.slip;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.balance.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * ���ׂ̃R���o�[�^�[
 */
public class TDetailConverter extends TController {

	/** ��� */
	public Company company = null;

	/** �������Ή��t���O */
	public boolean isUseHasDate = ClientConfig.isFlagOn("trans.slip.bs.use.occurdate");

	/** �v�Z���W�b�N */
	public TCalculator calculator = TCalculatorFactory.getCalculator();

	/**
	 * @param dialog
	 * @return �I�����c�����X�g
	 */
	public List<AP_ZAN> getSelectedAPBalanceList(TApBalanceListDialogCtrl dialog) {
		return dialog.getSelectedList();
	}

	/**
	 * �c�����X�g�ϊ�
	 * 
	 * @param zan ���c��
	 * @return ���X�g
	 */
	public SWK_DTL toDetail(AP_ZAN zan) {
		SWK_DTL dtl = createSlipDetail();

		dtl.setAP_ZAN(zan);

		// ��ЃR�[�h
		dtl.setKAI_CODE(getCompanyCode());

		// �v����
		dtl.setSWK_K_KAI_CODE(zan.getKAI_CODE());
		dtl.setSWK_K_KAI_NAME_S(zan.getKAI_NAME_S());

		// �v�㕔��
		dtl.setSWK_DEP_CODE(zan.getZAN_DEP_CODE());
		dtl.setSWK_DEP_NAME_S(zan.getZAN_DEP_NAME_S());

		// �Ȗ�
		dtl.setSWK_KMK_CODE(zan.getZAN_KMK_CODE());
		dtl.setSWK_KMK_NAME_S(zan.getZAN_KMK_NAME_S());

		// �⏕�Ȗ�
		dtl.setSWK_HKM_CODE(zan.getZAN_HKM_CODE());
		dtl.setSWK_HKM_NAME_S(zan.getZAN_HKM_NAME_S());

		// ����Ȗ�
		dtl.setSWK_UKM_CODE(zan.getZAN_UKM_CODE());
		dtl.setSWK_UKM_NAME_S(zan.getZAN_UKM_NAME_S());

		// ������
		dtl.setHAS_DATE(zan.getZAN_DEN_DATE());

		// �����(��ې�)
		dtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE);

		// �s�E�v
		dtl.setSWK_GYO_TEK(zan.getZAN_TEK());

		// �ؕ�
		dtl.setDC(Dc.DEBIT);

		// ���͋��z(�O��)
		dtl.setSWK_IN_KIN(zan.getZAN_IN_SIHA_KIN());

		// �M�݋��z
		dtl.setSWK_KIN(zan.getZAN_KIN());

		// �����
		dtl.setSWK_TRI_CODE(zan.getZAN_SIHA_CODE());
		dtl.setSWK_TRI_NAME_S(zan.getZAN_SIHA_NAME_S());

		// �Ј�
		// �Ǘ�1�`6
		// ������
		// ���v����1�`3

		// �ʉ�
		dtl.setSWK_CUR_CODE(zan.getZAN_CUR_CODE()); // �ʉ݃R�[�h
		dtl.setCUR_DEC_KETA(zan.getZAN_CUR_DEC_KETA()); // �����_�ȉ�����
		dtl.setCUR_RATE_POW(zan.getZAN_CUR_RATE_POW()); // ���[�g�W��

		// ���Z���[�g
		dtl.setSWK_CUR_RATE(zan.getZAN_CUR_RATE());

		// �����敪
		dtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);

		return buildDetail(dtl);
	}

	/**
	 * @param dialog
	 * @return �I�����c�����X�g
	 */
	public List<AR_ZAN> getSelectedARBalanceList(TArBalanceListDialogCtrl dialog) {
		return dialog.getSelectedList();
	}

	/**
	 * �c�����X�g�ϊ�
	 * 
	 * @param zan ���c��
	 * @return ���X�g
	 */
	public SWK_DTL toDetail(AR_ZAN zan) {
		SWK_DTL dtl = createSlipDetail();

		dtl.setAR_ZAN(zan);

		// ��ЃR�[�h
		dtl.setKAI_CODE(getCompanyCode());

		// �v����
		dtl.setSWK_K_KAI_CODE(zan.getKAI_CODE());
		dtl.setSWK_K_KAI_NAME_S(zan.getKAI_NAME_S());

		// �v�㕔��
		dtl.setSWK_DEP_CODE(zan.getZAN_DEP_CODE());
		dtl.setSWK_DEP_NAME_S(zan.getZAN_DEP_NAME_S());

		// �Ȗ�
		dtl.setSWK_KMK_CODE(zan.getZAN_KMK_CODE());
		dtl.setSWK_KMK_NAME_S(zan.getZAN_KMK_NAME_S());

		// �⏕�Ȗ�
		dtl.setSWK_HKM_CODE(zan.getZAN_HKM_CODE());
		dtl.setSWK_HKM_NAME_S(zan.getZAN_HKM_NAME_S());

		// ����Ȗ�
		dtl.setSWK_UKM_CODE(zan.getZAN_UKM_CODE());
		dtl.setSWK_UKM_NAME_S(zan.getZAN_UKM_NAME_S());

		// ������
		dtl.setHAS_DATE(zan.getZAN_SEI_DEN_DATE());

		// �����(��ې�)
		dtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE);

		// �s�E�v
		dtl.setSWK_GYO_TEK(zan.getZAN_TEK());

		// �ݕ�
		dtl.setDC(Dc.CREDIT);

		// ���͋��z(�O��)
		dtl.setSWK_IN_KIN(zan.getNON_KESI_IN_KIN());

		// �M�݋��z
		dtl.setSWK_KIN(zan.getNON_KESI_KIN());

		// �����
		dtl.setSWK_TRI_CODE(zan.getZAN_TRI_CODE());
		dtl.setSWK_TRI_NAME_S(zan.getZAN_TRI_NAME_S());

		// �ʉ�
		dtl.setSWK_CUR_CODE(zan.getZAN_CUR_CODE()); // �ʉ݃R�[�h
		dtl.setCUR_DEC_KETA(zan.getZAN_CUR_DEC_KETA()); // �����_�ȉ�����
		dtl.setCUR_RATE_POW(zan.getZAN_CUR_RATE_POW()); // ���[�g�W��

		// ���Z���[�g
		dtl.setSWK_CUR_RATE(zan.getZAN_CUR_RATE());

		// �����敪
		dtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);

		return buildDetail(dtl);
	}

	/**
	 * @param dialog
	 * @return �I��BS���胊�X�g
	 */
	public List<SWK_DTL> getSelectedBSList(BSEraseDialogCtrl dialog) {
		return dialog.getSelectedList();
	}

	/**
	 * BS�����ϊ�
	 * 
	 * @param bs BS����
	 * @param kcompany �v����
	 * @return �d��W���[�i��
	 */
	public SWK_DTL toDetail(SWK_DTL bs, Company kcompany) {
		SWK_DTL dtl = createSlipDetail();

		if (kcompany == null) {
			kcompany = getCompany();
		}

		// BS����������̐ݒ�
		dtl.setBsDetail(bs);

		// ��ЃR�[�h
		dtl.setKAI_CODE(getCompanyCode());

		// �v����
		dtl.setSWK_K_KAI_CODE(bs.getKAI_CODE()); // ��ɍĐݒ菈���ŏ㏑������

		// �v�㕔��
		dtl.setSWK_DEP_CODE(bs.getSWK_DEP_CODE());
		dtl.setSWK_DEP_NAME_S(bs.getSWK_DEP_NAME_S());

		// �Ȗ�
		dtl.setSWK_KMK_CODE(bs.getSWK_KMK_CODE());
		dtl.setSWK_KMK_NAME_S(bs.getSWK_KMK_NAME_S());

		// �⏕�Ȗ�
		dtl.setSWK_HKM_CODE(bs.getSWK_HKM_CODE());
		dtl.setSWK_HKM_NAME_S(bs.getSWK_HKM_NAME_S());

		// ����Ȗ�
		dtl.setSWK_UKM_CODE(bs.getSWK_UKM_CODE());
		dtl.setSWK_UKM_NAME_S(bs.getSWK_UKM_NAME_S());

		// ������
		if (isUseHasDate) {
			dtl.setHAS_DATE(bs.getHAS_DATE());
		} else {
			dtl.setHAS_DATE(bs.getSWK_DEN_DATE());
		}

		// �����(��ې�)
		dtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE);
		dtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO);
		dtl.setSWK_ZEI_KIN(BigDecimal.ZERO);

		// �s�E�v
		dtl.setSWK_GYO_TEK(getBsDetailRowRemark(bs));

		// �ݕ�
		dtl.setDC(bs.isDR() ? Dc.CREDIT : Dc.DEBIT);

		// ���͋��z(�O��)
		dtl.setSWK_IN_KIN(bs.getSWK_IN_KIN());

		// �M�݋��z
		dtl.setSWK_KIN(bs.getSWK_KIN());

		// �����
		dtl.setSWK_TRI_CODE(bs.getSWK_TRI_CODE());
		dtl.setSWK_TRI_NAME_S(bs.getSWK_TRI_NAME_S());

		// �Ј�
		dtl.setSWK_EMP_CODE(bs.getSWK_EMP_CODE());
		dtl.setSWK_EMP_NAME_S(bs.getSWK_EMP_NAME_S());

		// �Ǘ�1
		dtl.setSWK_KNR_CODE_1(bs.getSWK_KNR_CODE_1());
		dtl.setSWK_KNR_NAME_S_1(bs.getSWK_KNR_NAME_S_1());

		// �Ǘ�2
		dtl.setSWK_KNR_CODE_2(bs.getSWK_KNR_CODE_2());
		dtl.setSWK_KNR_NAME_S_2(bs.getSWK_KNR_NAME_S_2());

		// �Ǘ�3
		dtl.setSWK_KNR_CODE_3(bs.getSWK_KNR_CODE_3());
		dtl.setSWK_KNR_NAME_S_3(bs.getSWK_KNR_NAME_S_3());

		// �Ǘ�4
		dtl.setSWK_KNR_CODE_4(bs.getSWK_KNR_CODE_4());
		dtl.setSWK_KNR_NAME_S_4(bs.getSWK_KNR_NAME_S_4());

		// �Ǘ�5
		dtl.setSWK_KNR_CODE_5(bs.getSWK_KNR_CODE_5());
		dtl.setSWK_KNR_NAME_S_5(bs.getSWK_KNR_NAME_S_5());

		// �Ǘ�6
		dtl.setSWK_KNR_CODE_6(bs.getSWK_KNR_CODE_6());
		dtl.setSWK_KNR_NAME_S_6(bs.getSWK_KNR_NAME_S_6());

		// ���v����1
		dtl.setSWK_HM_1(bs.getSWK_HM_1());

		// ���v����2
		dtl.setSWK_HM_2(bs.getSWK_HM_2());

		// ���v����3
		dtl.setSWK_HM_3(bs.getSWK_HM_3());

		// �ʉ�
		dtl.setSWK_CUR_CODE(bs.getSWK_CUR_CODE()); // �ʉ݃R�[�h
		dtl.setCUR_DEC_KETA(bs.getCUR_DEC_KETA()); // �����_�ȉ�����
		dtl.setCUR_RATE_POW(bs.getCUR_RATE_POW()); // ���[�g�W��

		// ���Z���[�g
		dtl.setSWK_CUR_RATE(bs.getSWK_CUR_RATE());

		// �����敪
		dtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.FORWARD);

		// �t�ւ���Ђ̊�ʉ݂��قȂ�ꍇ�ɕϊ����s��
		String parentKeyCur = getCompany().getAccountConfig().getKeyCurrency().getCode();
		String groupKeyCur = kcompany.getAccountConfig().getKeyCurrency().getCode();
		if (!Util.equals(parentKeyCur, groupKeyCur)) {
			String curCode = dtl.getSWK_CUR_CODE();

			if (Util.equals(parentKeyCur, curCode)) {
				// 1. �d��̒ʉ݃R�[�h���t�ւ���Ђ̊�ʉ݃R�[�h
				// ��ʉ݋��z�����͋��z
				dtl.setSWK_CUR_RATE(BigDecimal.ONE);
				dtl.setSWK_KIN(dtl.getSWK_IN_KIN());
				dtl.setSWK_ZEI_KIN(dtl.getSWK_IN_ZEI_KIN());
			} else {
				// 2. �t���ւ���Ђ̊�ʉ݂ƈقȂ邽�߁A��ʉ݋��z�Čv�Z
				Currency currency = dtl.getCurrency();
				if (currency == null) {
					if (Util.equals(dtl.getSWK_CUR_CODE(), getCompany().getAccountConfig().getKeyCurrency().getCode())) {
						currency = getCompany().getAccountConfig().getKeyCurrency();
					} else {
						currency = getCurrency(getCompany().getCode(), dtl.getSWK_CUR_CODE());
					}
				}

				if (currency != null) {
					dtl.setCurrency(currency);
				}

				// ��ʉ݋��z�����͋��z�~(��)���������[�g
				BigDecimal rate = getCurrentRate(getCompany(), currency, dtl.getHAS_DATE());
				BigDecimal kin = convertToBase(getCompany(), dtl.getSWK_IN_KIN(), rate, currency);
				BigDecimal zeiKin = convertToBase(getCompany(), dtl.getSWK_IN_ZEI_KIN(), rate, currency);

				dtl.setSWK_CUR_RATE(rate);
				dtl.setSWK_KIN(kin);
				dtl.setSWK_ZEI_KIN(zeiKin);
			}
		}

		return buildDetail(dtl, kcompany);
	}

	/**
	 * �ʉݏ��̎擾
	 * 
	 * @param companyCode
	 * @param currencyCode
	 * @return �ʉݏ��
	 */
	protected Currency getCurrency(String companyCode, String currencyCode) {
		try {

			CurrencySearchCondition condition = new CurrencySearchCondition();
			condition.setCompanyCode(companyCode);
			condition.setCode(currencyCode);

			List<Currency> list = (List<Currency>) request(CurrencyManager.class, "get", condition);
			if (list != null && !list.isEmpty()) {
				return list.get(0);
			}

			// ���[{0}] �ʉ�[{1}]���o�^����Ă��܂���B
			ClientLogger.error(getMessage("W01124", getWord("C00053") + "[" + company + "]" + getWord("C00371") + "["
				+ currencyCode + "]"));

			return null;
		} catch (TException e) {
			ClientLogger.error(e);
			return null;
		}
	}

	/**
	 * ���[�g�̎擾
	 * 
	 * @param kcompany �v����
	 * @param currency ���גʉ�
	 * @param baseDate ���
	 * @return ���[�g
	 */
	protected BigDecimal getCurrentRate(Company kcompany, Currency currency, Date baseDate) {

		if (kcompany == null || currency == null || baseDate == null) {
			return null;
		}

		if (kcompany.getAccountConfig().getKeyCurrency().getCode().equals(currency.getCode())) {
			return BigDecimal.ONE;
		}

		try {
			BigDecimal rate = (BigDecimal) request(RateManager.class, "getRate", currency, baseDate);

			if (rate == null) {
				// ���[{0}]�ʉ�[{1}]������[{2}]�̎��Ѓ��[�g���ݒ肳��Ă��܂���B
				ClientLogger.error(getMessage("I00339", kcompany.getCode(), currency.getCode(),
					DateUtil.toYMDString(baseDate)));
			}

			return rate;
		} catch (TException e) {
			ClientLogger.error(e);
			return null;
		}
	}

	/**
	 * ���͋��z������z
	 * 
	 * @param kcompany
	 * @param foreignAmount �O�݋��z
	 * @param rate ���[�g
	 * @param currency �O�ݒʉ�
	 * @return ��ʉ݋��z
	 */
	public BigDecimal convertToBase(Company kcompany, BigDecimal foreignAmount, BigDecimal rate, Currency currency) {

		if (rate == null) {
			return null;
		}

		if (foreignAmount == null) {
			return null;
		}

		if (currency == null) {
			return null;
		}

		if (DecimalUtil.isNullOrZero(rate)) {
			return BigDecimal.ZERO;
		}

		if (DecimalUtil.isNullOrZero(foreignAmount)) {
			return BigDecimal.ZERO;
		}

		AccountConfig conf = kcompany.getAccountConfig();

		// ���Z
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(conf.getExchangeFraction());
		param.setConvertType(conf.getConvertType());
		param.setDigit(conf.getKeyCurrency().getDecimalPoint());
		param.setForeignAmount(foreignAmount);
		param.setRate(rate);
		param.setRatePow(currency.getRatePow());

		return calculator.exchangeKeyAmount(param);
	}

	/**
	 * @param bs BS����
	 * @return �s�E�v
	 */
	public String getBsDetailRowRemark(SWK_DTL bs) {
		String prefix = "";
		try {
			prefix = ClientConfig.getProperty("trans.slip.bs.detail.row.remark.prefix");
		} catch (Throwable e) {
			// �G���[�Ȃ�
		}

		return StringUtil.leftBX(prefix + bs.getSWK_DEN_NO() + " " + bs.getSWK_GYO_TEK(), 80);
	}

	/**
	 * �d��̃G���e�B�e�B���[���I�ɃZ�b�g.
	 * 
	 * @param dtl �d��
	 * @return �d��
	 */
	public SWK_DTL buildDetail(SWK_DTL dtl) {
		return buildDetail(dtl, getCompany());
	}

	/**
	 * �d��̃G���e�B�e�B���[���I�ɃZ�b�g.
	 * 
	 * @param dtl �d��
	 * @param kcompany �v���ЃR�[�h
	 * @return �d��
	 */
	public SWK_DTL buildDetail(SWK_DTL dtl, Company kcompany) {

		String companyCode = dtl.getSWK_K_KAI_CODE();

		// �v����(���ЈȊO�Ȃ��O��)
		// ���ЈȊO�̎d����������݂����ꍇ�́A�T�[�o����擾����悤�ɃJ�X�^�}�C�Y��.
		dtl.setAppropriateCompany(kcompany);

		// �v�㕔��
		Department dept = createDepartment();
		dept.setCompanyCode(companyCode);
		dept.setCode(dtl.getSWK_DEP_CODE());
		dept.setNames(dtl.getSWK_DEP_NAME_S());
		dtl.setDepartment(dept);

		// �Ȗ�
		Item kmk = createitem();
		kmk.setCompanyCode(companyCode);
		kmk.setCode(dtl.getSWK_KMK_CODE());
		kmk.setNames(dtl.getSWK_KMK_NAME_S());

		// �⏕�Ȗ�
		if (!Util.isNullOrEmpty(dtl.getSWK_HKM_CODE())) {
			SubItem hkm = createSubItem();
			hkm.setCompanyCode(companyCode);
			hkm.setCode(dtl.getSWK_HKM_CODE());
			hkm.setNames(dtl.getSWK_HKM_NAME_S());
			kmk.setSubItem(hkm);

			// ����Ȗ�
			if (!Util.isNullOrEmpty(dtl.getSWK_UKM_CODE())) {
				DetailItem ukm = createDetailItem();
				ukm.setCompanyCode(companyCode);
				ukm.setCode(dtl.getSWK_UKM_CODE());
				ukm.setNames(dtl.getSWK_UKM_NAME_S());
				hkm.setDetailItem(ukm);
			}
		}

		dtl.setItem(kmk);

		// �ʉ�
		Currency currency = createCurrency();
		currency.setCompanyCode(companyCode);
		currency.setCode(dtl.getSWK_CUR_CODE());
		currency.setDecimalPoint(dtl.getCUR_DEC_KETA());
		currency.setRatePow(dtl.getCUR_RATE_POW());
		dtl.setCurrency(currency);

		return dtl;
	}

	/**
	 * ��Џ���߂��܂��B
	 * 
	 * @return ��Џ��
	 */
	@Override
	public Company getCompany() {
		if (company != null) {
			return company;
		}
		return TLoginInfo.getCompany();
	}

	/**
	 * ��Ђ̐ݒ�
	 * 
	 * @param company ���
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * @return ���׎d��
	 */
	public SWK_DTL createSlipDetail() {
		return new SWK_DTL();
	}

	/**
	 * @return ����
	 */
	public Department createDepartment() {
		return new Department();
	}

	/**
	 * @return �Ȗ�
	 */
	public Item createitem() {
		return new Item();
	}

	/**
	 * @return �⏕�Ȗ�
	 */
	public SubItem createSubItem() {
		return new SubItem();
	}

	/**
	 * @return ����Ȗ�
	 */
	public DetailItem createDetailItem() {
		return new DetailItem();
	}

	/**
	 * @return �ʉ�
	 */
	public Currency createCurrency() {
		return new Currency();
	}
}
