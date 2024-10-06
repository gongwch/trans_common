package jp.co.ais.trans.logic.util;

import jp.co.ais.trans.common.except.*;

/**
 * トラオペ用自動採番Daoインターフェース
 * 
 * @author AIS
 */
public interface OPAutoControlDao {

	/**
	 * トラオペ用：自動採番された番号を取得<br>
	 * 自動採番コントロールの更新も行う
	 * 
	 * @param companyCode
	 * @param usrCode
	 * @param prgCode
	 * @param prifix
	 * @param increase
	 * @return トラオペ用自動採番ID
	 * @throws TException
	 */
	public int getAutoId(String companyCode, String usrCode, String prgCode, String prifix, int increase)
		throws TException;
}