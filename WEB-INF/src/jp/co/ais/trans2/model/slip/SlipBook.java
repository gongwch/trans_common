package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans2.common.ledger.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 伝票Book
 * 
 * @author AIS
 */
public class SlipBook extends LedgerBook {

	/** 会社情報 */
	protected Company company;

	/** 開始ページ番号 */
	protected int startPageNo = 0;

	/** 総ページ数 */
	protected int totalPageCount = 0;

	/**
	 * コンストラクター
	 */
	public SlipBook() {
		// 1Pに7行表示
		setMaxRowCount(7);
	}

	/**
	 * @return 会社
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company 会社
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * 開始ページ番号の取得
	 * 
	 * @return startPageNo 開始ページ番号
	 */
	public int getStartPageNo() {
		return startPageNo;
	}

	/**
	 * 開始ページ番号の設定
	 * 
	 * @param startPageNo 開始ページ番号
	 */
	public void setStartPageNo(int startPageNo) {
		this.startPageNo = startPageNo;
	}

	/**
	 * 総ページ数の取得
	 * 
	 * @return totalPageCount 総ページ数
	 */
	public int getTotalPageCount() {
		return totalPageCount;
	}

	/**
	 * 総ページ数の設定
	 * 
	 * @param totalPageCount 総ページ数
	 */
	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

}
