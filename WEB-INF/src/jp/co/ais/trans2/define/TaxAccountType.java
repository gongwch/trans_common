package jp.co.ais.trans2.define;

/**
 * 消費税計算書区分
 * @author AIS
 *
 */
public enum TaxAccountType {
	/** 課税売上*/
	SaleTax(1),
	/** 免税売上 */
	SaleFreeTax(2),
	/** 非課税売上 */
	SaleNonTax(3),
	/** 課税仕入 */
	PurchaseTax(4),
	/** 非課税仕入 */
	PurchaseNonTax(5);
	
	@SuppressWarnings("unused")
	private int value;

	private TaxAccountType(int value) {
		this.value = value;
	}

	/**
	 * 消費税分類に紐付くenumを返します。
	 * @param zeiKeiKbn 消費税分類
	 * @return 消費税分類に紐付くenum
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
