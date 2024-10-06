package jp.co.ais.trans2.common.viewer;

import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans2.common.ledger.*;

/**
 * 帳票Viewerのレイアウト基底
 * 
 * @author AIS
 */
public abstract class TViewerLayout {

	/** LedgerBook */
	protected LedgerBook book;

	/**
	 * Viewerの初期化
	 * 
	 * @param viewer
	 * @throws TException
	 */
	public abstract void init(TFrameViewer viewer) throws TException;

	/**
	 * ヘッダー描画
	 * 
	 * @param header ヘッダー
	 * @throws TException
	 */
	public abstract void drawHeader(LedgerSheetHeader header) throws TException;

	/**
	 * 明細描画
	 * 
	 * @param detail
	 * @throws TException
	 */
	public abstract void drawDetail(List<LedgerSheetDetail> detail) throws TException;

	/**
	 * Book取得
	 * 
	 * @return Book
	 */
	public LedgerBook getBook() {
		return book;
	}

	/**
	 * Book設定
	 * 
	 * @param book Book
	 */
	public void setBook(LedgerBook book) {
		this.book = book;
	}

	/**
	 * ログインユーザの言語コードに対する単語文字を返す.
	 * 
	 * @param wordID 単語ID
	 * @return 単語文字
	 */
	public String getWord(String wordID) {
		String lang = TClientLoginInfo.getInstance().getUserLanguage();
		return MessageUtil.getWord(lang, wordID);
	}

	/**
	 * ログインユーザの言語コードに対する単語文字(略語)を返す.
	 * 
	 * @param wordID 単語ID
	 * @return 単語文字(略語)
	 */
	public String getShortWord(String wordID) {
		String lang = TClientLoginInfo.getInstance().getUserLanguage();
		return MessageUtil.getShortWord(lang, wordID);
	}
}
