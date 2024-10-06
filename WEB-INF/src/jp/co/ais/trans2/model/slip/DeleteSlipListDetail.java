package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans2.common.ledger.*;

/**
 * 削除伝票リスト明細
 * 
 * @author AIS
 */
public class DeleteSlipListDetail extends LedgerSheetDetail {

	/** 削除日 */
	protected Date delDate = null;

	/** 削除伝票NO */
	protected String delSlipNo = "";

	/** 削除ユーザーID */
	protected String usrID = "";

	/** 削除ユーザー名 */
	protected String usrName = "";

	public String getDelSlipNo() {
		return delSlipNo;
	}

	public void setDelSlipNo(String delSlipNo) {
		this.delSlipNo = delSlipNo;
	}

	/**
	 * 削除日の取得
	 * 
	 * @return String
	 */
	public Date getDelDate() {
		return delDate;
	}

	/**
	 * 削除日の設定する
	 * 
	 * @param delDate
	 */
	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

	/**
	 * 削除ユーザーIDを取得する
	 * 
	 * @return ユーザーID
	 */
	public String getUsrID() {
		return usrID;
	}

	/**
	 * 削除ユーザーIDを設定する
	 * 
	 * @param usrID
	 */
	public void setUsrID(String usrID) {
		this.usrID = usrID;
	}

	/**
	 * 削除ユーザー名を取得する
	 * 
	 * @return ユーザー名
	 */
	public String getUsrName() {
		return usrName;
	}

	/**
	 * 削除ユーザー名を設定する
	 * 
	 * @param usrName
	 */
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}

}
