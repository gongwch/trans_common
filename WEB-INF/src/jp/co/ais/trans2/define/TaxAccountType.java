package jp.co.ais.trans2.define;

/**
 * ΑοΕvZζͺ
 * @author AIS
 *
 */
public enum TaxAccountType {
	/** ΫΕγ*/
	SaleTax(1),
	/** ΖΕγ */
	SaleFreeTax(2),
	/** ρΫΕγ */
	SaleNonTax(3),
	/** ΫΕdό */
	PurchaseTax(4),
	/** ρΫΕdό */
	PurchaseNonTax(5);
	
	@SuppressWarnings("unused")
	private int value;

	private TaxAccountType(int value) {
		this.value = value;
	}

	/**
	 * ΑοΕͺήΙRt­enumπΤ΅ά·B
	 * @param zeiKeiKbn ΑοΕͺή
	 * @return ΑοΕͺήΙRt­enum
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
