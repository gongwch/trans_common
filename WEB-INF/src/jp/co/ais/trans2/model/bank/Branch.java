package jp.co.ais.trans2.model.bank;

import jp.co.ais.trans2.model.*;


/**
 * 支店情報
 * @author AIS
 */
public class Branch extends Bank implements Cloneable, TReferable {

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Branch clone() {
		return (Branch) super.clone();
	}

}
