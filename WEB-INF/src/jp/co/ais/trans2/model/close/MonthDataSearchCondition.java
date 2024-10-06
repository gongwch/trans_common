package jp.co.ais.trans2.model.close;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 月次更新検索条件
 * 
 * @author TRANS(D) YF.CONG
 */
public class MonthDataSearchCondition extends TransferBase {

	/** 会社コードリスト */
	protected List<String> companyCodeList;

	/**
	 * 会社コードリストの取得
	 * 
	 * @return companyCodeList 会社コードリスト
	 */
	public List<String> getCompanyCodeList() {
		return companyCodeList;
	}

	/**
	 * 会社コードリストの設定
	 * 
	 * @param companyCodeList 会社コードリスト
	 */
	public void setCompanyCodeList(List<String> companyCodeList) {
		this.companyCodeList = companyCodeList;
	}

}
