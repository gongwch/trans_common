package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 排他制御ロジック
 */
public interface UserUnLockLogic {

	/**
	 * 会社コードでユーザ排他リストを取得
	 * 
	 * @param companyCode 会社コード
	 * @return ユーザ排他リスト
	 */
	List<PCI_CHK_CTL> getPCIListByCompanyCode(String companyCode);

	/**
	 * リスト削除
	 * 
	 * @param dtoList ユーザ排他リスト
	 */
	void delete(List<PCI_CHK_CTL> dtoList);
}
