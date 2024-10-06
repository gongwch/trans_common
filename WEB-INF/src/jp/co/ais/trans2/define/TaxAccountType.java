package jp.co.ais.trans2.define;

/**
 * ����Ōv�Z���敪
 * @author AIS
 *
 */
public enum TaxAccountType {
	/** �ېŔ���*/
	SaleTax(1),
	/** �ƐŔ��� */
	SaleFreeTax(2),
	/** ��ېŔ��� */
	SaleNonTax(3),
	/** �ېŎd�� */
	PurchaseTax(4),
	/** ��ېŎd�� */
	PurchaseNonTax(5);
	
	@SuppressWarnings("unused")
	private int value;

	private TaxAccountType(int value) {
		this.value = value;
	}

	/**
	 * ����ŕ��ނɕR�t��enum��Ԃ��܂��B
	 * @param zeiKeiKbn ����ŕ���
	 * @return ����ŕ��ނɕR�t��enum
	 */
	public final static TaxAccountType getTaxAccountType(int taxAccountType) {

		if (TaxAccountType.SaleTax.value == taxAccountType) {
			return TaxAccountType.SaleTax;
		} else if (TaxAccountType.SaleFreeTax.value == taxAccountType) {
			return TaxAccountType.SaleFreeTax;
		} else if (TaxAccountType.SaleNonTax.value == taxAccountType) {
			return TaxAccountType.SaleNonTax;
		} else if (TaxAccountType.PurchaseTax.value == taxAccountType) {
			return TaxAccountType.PurchaseTax;
		} else if (TaxAccountType.PurchaseNonTax.value == taxAccountType) {
			return TaxAccountType.PurchaseNonTax;
		}
		return null;
	}

}
