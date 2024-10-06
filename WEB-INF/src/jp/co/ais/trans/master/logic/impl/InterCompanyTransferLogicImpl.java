package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 会社間付替データ操作
 */
public class InterCompanyTransferLogicImpl extends CommonLogicBaseImpl {

	/** 会社間付替マスタDao */
	private KTK_MSTDao dao;

	/** 会社間付替マスタ実体 */
	private KTK_MST ktkMstentity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao KTK_MSTDao
	 */
	public InterCompanyTransferLogicImpl(KTK_MSTDao dao) {
		// 会社間付替マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * KTK_MSTインスタンスの設定.
	 * 
	 * @param entity KTK_MST
	 */
	public void setEntity(KTK_MST entity) {
		// 会社間付替マスタ実体を返す
		this.ktkMstentity = entity;
	}

	/**
	 * 結果を検索する
	 * 
	 * @param conditions Map
	 */
	public List find(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 開始コードの取得
		String beginKtkKaiCode = (String) conditions.get("beginKtkKaiCode");
		// 終了コードの取得
		String endKtkKaiCode = (String) conditions.get("endKtkKaiCode");
		// 開始コードも終了コードもない場合
		if (Util.isNullOrEmpty(beginKtkKaiCode) && Util.isNullOrEmpty(endKtkKaiCode)) {
			// 結果を返す
			return dao.getAllKTK_MST2(kaiCode);
			// 開始コードだけない場合
		} else if (Util.isNullOrEmpty(beginKtkKaiCode)) {
			// 結果を返す
			return dao.getKtkInfoTo(kaiCode, endKtkKaiCode);
			// 終了コードだけない場合
		} else if (Util.isNullOrEmpty(endKtkKaiCode)) {
			// 結果を返す
			return dao.getKtkInfoFrom(kaiCode, beginKtkKaiCode);
			// 開始コードと終了コード全部ある場合
		} else {
			// 結果を返す
			return dao.getKtkInfo(kaiCode, beginKtkKaiCode, endKtkKaiCode);
		}
	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys Map
	 */
	public Object findOne(Map keys) {
		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");
		// 付替会社コードの取得
		String ktkKaiCode = (String) keys.get("ktkKaiCode");
		// 結果を返す
		return dao.getKTK_MSTByKaicodeKtkkaicode(kaiCode, ktkKaiCode);
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 付替会社コードの取得
		String ktkKaiCode = (String) conditions.get("ktkKaiCode");
		// 会社コードの設定
		ktkMstentity.setKAI_CODE(kaiCode);
		// 付替会社コードの設定
		ktkMstentity.setKTK_KAI_CODE(ktkKaiCode);
		// データを削除する
		dao.delete(ktkMstentity);
	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// 実体の初期化
		KTK_MST entity = (KTK_MST) dto;

		// データを登録する
		dao.insert(entity);
	}

	/**
	 * データを更新する
	 * 
	 * @param dto Object
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// 実体の初期化
		KTK_MST entity = (KTK_MST) dto;

		// データを更新する
		dao.update(entity);
	}

	/**
	 * 編集元のレコードの取得。 プロパーティ「Entity」から、主キーを取得し、 daoのメソッドを呼出し、DBから編集元のレコードを取得する
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		KTK_MST entity = (KTK_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String ktkkaiCode = entity.getKTK_KAI_CODE();

		return dao.getKTK_MSTByKaicodeKtkkaicode(kaiCode, ktkkaiCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		KTK_MST entity = (KTK_MST) dto;
		return entity.getKTK_KAI_CODE();
	}
}
