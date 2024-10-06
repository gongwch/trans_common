package jp.co.ais.trans.master.logic.impl;

import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * マスタベースロジック
 */
public abstract class CommonLogicBaseImpl implements CommonLogic {

	/** ユーザーID */
	String userID = null;

	/**
	 * 一覧画面での検索
	 * 
	 * @conditions 検索条件（Map形式）
	 */
	@SuppressWarnings("unused")
	public List find(Map conditions) throws ParseException {
		return null;
	}

	/**
	 * 特定のレコードの検索
	 * 
	 * @keys レコードの主キー（Map形式）
	 */
	@SuppressWarnings("unused")
	public Object findOne(Map keys) throws ParseException {
		return null;
	}

	/**
	 * REFダイアログ用の検索
	 * 
	 * @conditions 検索条件（Map形式、少なくともcode/name_S/name_K三つ条件を含む）
	 */
	@SuppressWarnings("unused")
	public List findREF(Map conditions) throws ParseException {
		return null;
	}

	/**
	 * ButtonFieldでコードを入力時、略称の検索
	 * 
	 * @keys 検索条件（Map形式、findREFと同じ条件）
	 */
	@SuppressWarnings("unused")
	public String findName(Map keys) throws ParseException {
		return null;
	}

	/**
	 * 新規場合の主キー重複の回避チェック
	 * 
	 * @keys レコードの主キー（Map形式）
	 */
	public boolean checkCode(Map keys) throws ParseException {
		Object entity = findOne(keys);
		return (entity != null);
	}

	/**
	 * レコードの削除
	 * 
	 * @keys レコードの主キー（Map形式）
	 */
	@SuppressWarnings("unused")
	public void delete(Map keys) throws ParseException {
		//
	}

	/**
	 * レコードの新規（該当レコード既に存在する場合は、エラーをスロー）<br>
	 * 下位クラスには、insertImplメソッドを実装してください。
	 * 
	 * @dto レコード
	 */
	public void insert(Object dto) throws TException {
		// 存在チェック
		Object oldDto = getOldEntity(dto);

		if (oldDto != null) {
			// エラー通知(すでに存在します)
			String code = getPrimaryCode(dto);

			if (code == null) {
				throw new TException("W00005");
			} else {
				throw new TException("W00005", code);
			}
		}

		MasterBase entity = (MasterBase) dto;

		// ユーザーIDの設定
		entity.setUSR_ID(userID);

		// 登録日付の設定
		entity.setINP_DATE(Util.getCurrentDate());

		// 更新日付をNULLにする
		entity.setUPD_DATE(null);

		insertImpl(entity);
	}

	/**
	 * レコードの変更（該当レコードは存在しない場合は、エラーをスロー）<br>
	 * 下位クラスには、updateImplメソッドを実装してください。
	 * 
	 * @dto レコード
	 */
	public void update(Object dto) throws TException {
		// 存在チェック
		MasterBase oldDto = (MasterBase) getOldEntity(dto);

		if (oldDto == null) {
			// エラー通知（該当コードは存在しません）
			String code = getPrimaryCode(dto);

			if (code == null) {
				throw new TException("W00081");
			} else {
				throw new TException("W00081", code);
			}
		}

		MasterBase entity = (MasterBase) dto;

		// ユーザーIDの設定
		entity.setUSR_ID(userID);
		// 変わらない項目の値を設定する
		entity.setINP_DATE(oldDto.getINP_DATE());
		// 更新日付の設定
		entity.setUPD_DATE(Util.getCurrentDate());

		updateImpl(entity, oldDto);
	}

	/**
	 * ユーザーIDをセット
	 * 
	 * @param userID
	 */
	public void setUserCode(String userID) {
		this.userID = userID;
	}

	/**
	 * レコードの登録（該当レコードは存在する場合は、変更を行う；存在しない場合は、新規を行う）<br>
	 * 下位クラスには、insertImplとupdateImplメソッドを実装してください。
	 * 
	 * @dto レコード
	 */
	public void save(Object dto) {
		// 存在チェック
		MasterBase oldDto = (MasterBase) getOldEntity(dto);

		if (oldDto == null) {
			MasterBase entity = (MasterBase) dto;
			// ユーザーIDの設定
			entity.setUSR_ID(userID);
			// 登録日付の設定
			entity.setINP_DATE(Util.getCurrentDate());
			// 更新日付をNULLにする
			entity.setUPD_DATE(null);
			insertImpl(dto);
		} else {
			MasterBase entity = (MasterBase) dto;
			// ユーザーIDの設定
			entity.setUSR_ID(userID);
			// 変わらない項目の値を設定する
			entity.setINP_DATE(oldDto.getINP_DATE());
			// 更新日付の設定
			entity.setUPD_DATE(Util.getCurrentDate());
			updateImpl(entity, oldDto);
		}
	}

	/**
	 * レコードの新規の実装
	 * 
	 * @param dto レコード
	 */
	@SuppressWarnings("unused")
	protected void insertImpl(Object dto) {
		//
	}

	/**
	 * レコードの新規の実装
	 * 
	 * @param dto レコード
	 * @param oldDto DBから編集元のレコード
	 */
	@SuppressWarnings("unused")
	protected void updateImpl(Object dto, Object oldDto) {
		//
	}

	/**
	 * 編集元のレコードの取得。<br>
	 * プロパーティ「Entity」から、主キーを取得し、<br>
	 * daoのメソッドを呼出し、DBから編集元のレコードを取得する
	 * 
	 * @param dto レコード
	 * @return 結果
	 */
	@SuppressWarnings("unused")
	protected Object getOldEntity(Object dto) {
		return null;
	}

	/**
	 * 編集元のレコードの主キーの取得。<br>
	 * 主キーを取得する。<br>
	 * 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return PrimaryCode
	 */
	@SuppressWarnings("unused")
	protected String getPrimaryCode(Object dto) {
		return null;
	}
}
