package jp.co.ais.trans2.model.slip.parts;

import java.math.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �����v
 */
public class LossOrProfit extends TModel {

	/** �ב֍��� ��� */
	protected AutoJornalAccount loss;

	/** �ב֍��v ��� */
	protected AutoJornalAccount profit;

	/**
	 * @see jp.co.ais.trans2.model.TModel#setCompany(jp.co.ais.trans2.model.company.Company)
	 */
	@Override
	public void setCompany(Company company) {
		super.setCompany(company);

		if (company == null) {
			return;
		}

		loss = getAutoItem(AutoJornalAccountType.EXCHANGE_LOSE);
		profit = getAutoItem(AutoJornalAccountType.EXCHANGE_GAIN);
	}

	/**
	 * �ב֍����v�̎d��ǉ�(��ʉ�=�����敪:�����A�@�\�ʉ�=�����敪:IFRS)
	 * 
	 * @param currencyType �ʉ݃^�C�v
	 * @param slip �ǉ��\��̓`�[
	 */
	public void addLossOrProfit(Slip slip, CurrencyType currencyType) {
		addLossOrProfit(slip, currencyType, currencyType == CurrencyType.KEY ? AccountBook.BOTH : AccountBook.IFRS);
	}

	/**
	 * �ב֍����v�̎d��ǉ�
	 * 
	 * @param slip �ǉ��\��̓`�[
	 * @param currencyType �ʉ݃^�C�v
	 * @param accountBook �����敪
	 */
	public void addLossOrProfit(Slip slip, CurrencyType currencyType, AccountBook accountBook) {
		BigDecimal sa = BigDecimal.ZERO;

		for (SWK_DTL dtl : slip.getDetails()) {
			sa = sa.add(dtl.isDR() ? dtl.getSWK_KIN() : dtl.getSWK_KIN().negate());
		}

		if (sa.compareTo(BigDecimal.ZERO) == 0) {
			return; // ���Ȃ�
		}

		boolean isDR = sa.compareTo(BigDecimal.ZERO) < 0;

		SWK_DTL fdtl = slip.createDetail();
		fdtl.setCurrencyType(currencyType);
		fdtl.setAccountBook(accountBook);

		// ���v�A�����Ȗڂ��Z�b�g
		AutoJornalAccount aja = isDR ? loss : profit;
		fdtl.setSWK_KMK_CODE(aja.getItemCode());
		fdtl.setSWK_KMK_NAME(aja.getItemName());
		fdtl.setSWK_HKM_CODE(aja.getSubItemCode());
		fdtl.setSWK_HKM_NAME(aja.getSubItemName());
		fdtl.setSWK_UKM_CODE(aja.getDetailItemCode());
		fdtl.setSWK_UKM_NAME(aja.getDetailItemName());
		fdtl.setSWK_DEP_CODE(aja.getDepertmentCode());
		fdtl.setSWK_DEP_NAME(aja.getDepertmentName());
		fdtl.setAppropriateCompany(getCompany()); // �v����
		fdtl.setDC(isDR ? Dc.DEBIT : Dc.CREDIT); // �ݎ�

		// �ʉ�
		Currency currency;
		switch (currencyType) {
			case FUNCTIONAL:
				currency = getCompany().getAccountConfig().getFunctionalCurrency();
				break;

			default:
				currency = getCompany().getAccountConfig().getKeyCurrency();
				break;
		}

		fdtl.setSWK_CUR_CODE(currency.getCode()); // �ʉ݃R�[�h
		fdtl.setSWK_IN_KIN(sa.abs()); // ���͋��z
		fdtl.setSWK_CUR_RATE(BigDecimal.ONE); // ���[�g1
		fdtl.setSWK_KIN(sa.abs()); // ����z
		fdtl.setSWK_ZEI_KBN(SWK_DTL.ZEI_KBN.TAX_NONE); // ��ې�
		fdtl.setSWK_IN_ZEI_KIN(BigDecimal.ZERO); // ����Ŋz(�O��)
		fdtl.setSWK_ZEI_KIN(BigDecimal.ZERO); // ����Ŋz
		fdtl.setHAS_DATE(slip.getSlipDate()); // ������
		fdtl.setSWK_AUTO_KBN(SWK_DTL.AUTO_KBN.AUTO); // ����
		fdtl.setSWK_GYO_TEK(slip.getRemarks()); // �s�E�v

		slip.addDetail(fdtl);
	}

	/**
	 * �w��d�󂪈ב֍����v�d�󂩂ǂ�������
	 * 
	 * @param dtl �d��
	 * @return true:�ב֍����v�d��
	 */
	public boolean isProfitOrLoss(SWK_DTL dtl) {
		boolean isProfit = profit.equalsItem(dtl.getSWK_KMK_CODE(), dtl.getSWK_HKM_CODE(), dtl.getSWK_UKM_CODE());
		boolean isLoss = loss.equalsItem(dtl.getSWK_KMK_CODE(), dtl.getSWK_HKM_CODE(), dtl.getSWK_UKM_CODE());

		return isProfit || isLoss;
	}

	/**
	 * �����d��Ȗڂ��擾����.
	 * 
	 * @param kmkCnt �Ȗڐ���敪
	 * @return �����d��Ȗ�
	 */
	protected AutoJornalAccount getAutoItem(AutoJornalAccountType kmkCnt) {
		try {
			SlipManager manager = (SlipManager) getComponent(SlipManager.class);

			String companyCode = getCompanyCode();
			AutoJornalAccount bean = manager.getAutoJornalAccount(companyCode, kmkCnt);

			if (bean == null) {
				throw new TRuntimeException("I00160", companyCode);//���[{0}]�̎����d��Ȗ�:�ב֍����v���ݒ肳��Ă��܂���B
			}

			return bean;

		} catch (TException ex) {
			throw new TRuntimeException(ex);
		}
	}
}
