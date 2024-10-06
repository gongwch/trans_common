package jp.co.ais.trans2.model.calc;

/**
 * �v�Z�֘AFactory
 */
public class TCalculatorFactory {

	//TODO S2�ō����ւ�
	
	/**
	 * TCalculator�擾
	 * 
	 * @return TCalculator
	 */
	public static TCalculator getCalculator() {
		return new TCalculator();
	}

	/**
	 * �O�݊��Z�p�����[�^�𐶐�
	 * 
	 * @return ���Z�p�����[�^
	 */
	public static TExchangeAmount createExchangeAmount() {
		return new TExchangeAmount();
	}

	/**
	 * �Ŋz�v�Z�p�����[�^�𐶐�
	 * 
	 * @return �Ŋz�v�Z�p�����[�^
	 */
	public static TTaxCalculation createTaxCalculation() {
		return new TTaxCalculation();
	}
}
