package jp.co.ais.trans2.model.bank;

import jp.co.ais.trans2.model.*;


/**
 * x“Xî•ñ
 * @author AIS
 */
public class Branch extends Bank implements Cloneable, TReferable {

	/**
	 * ƒNƒ[ƒ“
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Branch clone() {
		return (Branch) super.clone();
	}

}
