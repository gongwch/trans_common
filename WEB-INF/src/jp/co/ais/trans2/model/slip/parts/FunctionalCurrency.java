package jp.co.ais.trans2.model.slip.parts;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.currency.Currency;
import jp.co.ais.trans2.model.slip.*;

/**
 * �@�\�ʉ݊֘A
 */
public class FunctionalCurrency extends TModel {

	/** �ʉ݃��[�g�}�l�[�W�� */
	protected RateManager rateManager;

	/** �����v�p */
	protected LossOrProfit lossOrProfit;

	/** ���[�g */
	protected Map<String, Map<String, BigDecimal>> rateMap = new TreeMap<String, Map<String, BigDecimal>>();

	/** �v���� */
	protected Company kcompany;

	/**
	 * �@�\�ʉݎd��̒ǉ�
	 * 
	 * @param motoSlip ���`�[
	 * @return �쐬��`�[
	 * @throws TException
	 */
	public Slip create(Slip motoSlip) throws TException {
		rateMap.clear();

		// �`�[�̉�Џ��
		CompanyManager companyManager = (CompanyManager) getComponent(CompanyManager.class);
		kcompany = companyManager.get(motoSlip.getCompanyCode());

		lossOrProfit = (LossOrProfit) getComponent(LossOrProfit.class);
		lossOrProfit.setCompany(kcompany);

		Slip slip = motoSlip.clone();
		slip.setCompany(kcompany);
		slip.getHeader().setAccountBook(AccountBook.IFRS); // �����敪��IFRS��

		// �@�\�ʉݎd��ɒu������
		makeFunctional(slip);

		// ����Ŏd��̒ǉ�
		if (!slip.isJournalizedTax()) {
			addTax(slip);
		}

		// �ב֍����v�̎d��쐬
		lossOrProfit.addLossOrProfit(slip, CurrencyType.FUNCTIONAL);

		// �`��
		slip.buildSlip();

		return slip;
	}

	/**
	 * �@�\�ʉݎd��̒ǉ�
	 * 
	 * @param slip �Ώۓ`�[
	 * @throws TException
	 */
	protected void makeFunctional(Slip slip) throws TException {

		AccountConfig config = kcompany.getAccountConfig(); // ��v���
		Currency keyCurrency = config.getKeyCurrency(); // ��ʉ�
		Currency functionCurrency = config.getFunctionalCurrency(); // �@�\�ʉ�

		// IFRS�d��쐬
		List<SWK_DTL> list = new ArrayList<SWK_DTL>();

		// ��Ƌ@�\�������ꍇ�́A���e�͂��̂܂܂�IFRS�d���ǉ�
		if (keyCurrency.getCode().equals(functionCurrency.getCode())) {
			for (SWK_DTL dtl : slip.getDetails()) {
				SWK_DTL fdtl = dtl.clone();
				fdtl.setCurrencyType(CurrencyType.FUNCTIONAL);
				fdtl.setAccountBook(AccountBook.IFRS);

				list.add(fdtl);
			}

			slip.setDetails(list);

			return;
		}

		// �@�\�ʉ݃R�[�h
		String funcCurrncyCode = functionCurrency.getCode();

		// �v�Z�@
		TCalculator calculator = TCalculatorFactory.getCalculator();
		TExchangeAmount exchangeParam = TCalculatorFactory.createExchangeAmount(); // ����
		exchangeParam.setConvertType(config.getConvertType() == ConvertType.MULTIPLICATION ? ConvertType.DIVIDE
			: ConvertType.MULTIPLICATION); // ���Z���@(�|/��) �Y����Ђ̋t
		exchangeParam.setExchangeFraction(config.getExchangeFraction()); // ���Z���@(�ۂ�)
		exchangeParam.setDigit(functionCurrency.getDecimalPoint()); // �@�\�ʉ݂̏����_����

		for (SWK_DTL dtl : slip.getDetails()) {
			// �ב֍����v�d��͏��O
			if (lossOrProfit.isProfitOrLoss(dtl)) {
				continue;
			}

			// �@�\�ʉݎd��s
			SWK_DTL fdtl = dtl.clone();
			fdtl.setCurrencyType(CurrencyType.FUNCTIONAL);
			fdtl.setAccountBook(AccountBook.IFRS);

			// �d��s�̒ʉ݃R�[�h
			String curCode = fdtl.getSWK_CUR_CODE();

			// �d��ʉ݁��@�\�ʉ�
			if (funcCurrncyCode.equals(curCode)) {
				fdtl.setSWK_CUR_RATE(BigDecimal.ONE); // ���[�g
				fdtl.setSWK_KIN(fdtl.getSWK_IN_KIN()); // �M�݋��z
				fdtl.setSWK_ZEI_KIN(fdtl.getSWK_IN_ZEI_KIN()); // ����Ŋz(�M��)

				list.add(fdtl);
				continue;
			}

			// �d��ʉ݁��@�\�ʉ� �����Z

			// �@�\�ʉ݃��[�g
			BigDecimal funcRate = getRate(fdtl);
			fdtl.setSWK_CUR_RATE(funcRate);

			exchangeParam.setRate(funcRate);
			exchangeParam.setRatePow(dtl.getCUR_RATE_POW()); // ���[�g�W��

			// �M��
			exchangeParam.setForeignAmount(dtl.getSWK_IN_KIN());
			fdtl.setSWK_KIN(calculator.exchangeKeyAmount(exchangeParam));

			// ����Ŋz(�M��)
			if (dtl.getSWK_IN_ZEI_KIN() != null) {
				exchangeParam.setForeignAmount(dtl.getSWK_IN_ZEI_KIN());
				fdtl.setSWK_ZEI_KIN(calculator.exchangeKeyAmount(exchangeParam));

			} else {
				fdtl.setSWK_ZEI_KIN(null);
			}

			// �������N���A
			fdtl.setSWK_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);
			fdtl.setSWK_KESI_DATE(null); // �����`�[���t
			fdtl.setSWK_KESI_DEN_NO(null); // �����`�[�ԍ�
			fdtl.setSWK_SOUSAI_DATE(null); // ���E�`�[���t
			fdtl.setSWK_SOUSAI_DEN_NO(null); // ���E�`�[�ԍ�
			fdtl.setSWK_SOUSAI_GYO_NO(null); // ���E�s�ԍ�

			fdtl.setSWK_APAR_KESI_KBN(SWK_DTL.KESI_KBN.NOMAL);// AP/AR�����敪
			fdtl.setSWK_APAR_DEN_NO(null);// AP/AR�����`�[�ԍ�
			fdtl.setSWK_APAR_GYO_NO(null);// AP/AR�����s�ԍ�

			fdtl.setAP_ZAN(null);
			fdtl.setAR_ZAN(null);
			fdtl.setBsDetail(null);

			list.add(fdtl);
		}

		slip.setDetails(list);
	}

	/**
	 * �@�\�ʉݎd��̏���Ŏd���ǉ�
	 * 
	 * @param slip �`�[
	 * @throws TException
	 */
	protected void addTax(Slip slip) throws TException {
		TaxJournalIssuer logic = (TaxJournalIssuer) getComponent(TaxJournalIssuer.class);
		logic.setCurrencyType(CurrencyType.FUNCTIONAL);
		logic.addJournal(slip, slip.getDetailCount());
	}

	/**
	 * �@�\�ʉ݃��[�g�擾
	 * 
	 * @param dtl �Ώێd��
	 * @return ���[�g
	 * @throws TException
	 */
	protected BigDecimal getRate(SWK_DTL dtl) throws TException {
		if (rateManager == null) {
			rateManager = (RateManager) getComponent(RateManager.class);
		}

		// ���[�g���(������)
		Date rateDate = dtl.getHAS_DATE();
		if (rateDate == null) {
			rateDate = dtl.getSWK_DEN_DATE(); // �������������ꍇ�͓`�[���t�̃��[�g
		}

		// �d��s�̒ʉ݃R�[�h
		String curCode = dtl.getSWK_CUR_CODE();

		return getRate(rateDate, curCode);
	}

	/**
	 * �@�\�ʉ݃��[�g�擾
	 * 
	 * @param date �Ώۓ�
	 * @param curCode �ʉ݃R�[�h
	 * @return ���[�g
	 * @throws TException
	 */
	protected BigDecimal getRate(Date date, String curCode) throws TException {
		if (kcompany.getAccountConfig().getFunctionalCurrency().getCode().equals(curCode)) {
			return BigDecimal.ONE;
		}

		Map<String, BigDecimal> map = rateMap.get(curCode);

		if (map == null) {
			map = new TreeMap<String, BigDecimal>();
			rateMap.put(curCode, map);
		}

		BigDecimal rate = map.get(DateUtil.toYMDString(date));

		if (rate == null) {
			// �Ώۓ��t����ŐV�̃��[�g�擾
			rateManager.setCompany(kcompany);
			rate = rateManager.getFunctionalRate(curCode, date);

			if (rate == null) {
				throw new TException(getMessage("I00158", kcompany.getCode(), curCode, DateUtil.toYMDString(date)));
				// ���[{0}] �ʉ�[{1}] ������[{2}] �̋@�\�ʉ݃��[�g���ݒ肳��Ă��܂���B
			}

			map.put(DateUtil.toYMDString(date), rate);
		}

		return rate;
	}
}
