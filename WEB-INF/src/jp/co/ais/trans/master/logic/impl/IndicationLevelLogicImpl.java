package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 開示レベルデータ操作
 */
public class IndicationLevelLogicImpl extends CommonLogicBaseImpl {

	/** 開示レベルマスタDao */
	protected KJL_MSTDao dao;

	/** 開示レベルマスタ実体 */
	protected KJL_MST kjlMstentity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao KJL_MSTDao
	 */
	public IndicationLevelLogicImpl(KJL_MSTDao dao) {
		// 開示レベルマスタDaoを返す
		this.dao = dao;
	}

	/**
	 * KJL_MSTインスタンスの設定.
	 * 
	 * @param entity KJL_MST
	 */
	public void setEntity(KJL_MST entity) {
		// 開示レベルマスタ実体を返す
		this.kjlMstentity = entity;
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
		String beginKjlUsrId = (String) conditions.get("beginKjlUsrId");
		// 終了コードの取得
		String endKjlUsrId = (String) conditions.get("endKjlUsrId");
		// 開始コードも終了コードもない場合
		if (Util.isNullOrEmpty(beginKjlUsrId) && Util.isNullOrEmpty(endKjlUsrId)) {
			// 結果を返す
			return dao.getAllKJL_MST2(kaiCode);
			// 開始コードだけない場合
		} else if (Util.isNullOrEmpty(beginKjlUsrId)) {
			// 結果を返す
			return dao.getIndicationLevelInfoTo(kaiCode, endKjlUsrId);
			// 終了コードだけない場合
		} else if (Util.isNullOrEmpty(endKjlUsrId)) {
			// 結果を返す
			return dao.getIndicationLevelInfoFrom(kaiCode, beginKjlUsrId);
			// 開始コードと終了コード全部ある場合
		} else {
			// 結果を返す
			return dao.getIndicationLevelInfo(kaiCode, beginKjlUsrId, endKjlUsrId);
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
		// ユーザーコードの取得
		String kjlUsrId = (String) keys.get("kjlUsrId");
		// 科目体系コード
		String kjlKmtCode = (String) keys.get("kjlKmtCode");
		// 組織コード
		String kjlDpkSsk = (String) keys.get("kjlDpkSsk");

		// 結果を返す
		return dao.getKJL_MSTByKaicodeKjlusridKjlkmtcodeKjldpkssk(kaiCode, kjlUsrId, kjlKmtCode, kjlDpkSsk);
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// ユーザーコードの取得
		String kjlUsrId = (String) conditions.get("kjlUsrId");
		String kjlKmtCode = (String) conditions.get("kjlKmtCode");
		String kjlDpkSsk = (String) conditions.get("kjlDpkSsk");
		// 会社コードの設定
		kjlMstentity.setKAI_CODE(kaiCode);
		kjlMstentity.setKJL_USR_ID(kjlUsrId);
		kjlMstentity.setKJL_KMT_CODE(kjlKmtCode);
		kjlMstentity.setKJL_DPK_SSK(kjlDpkSsk);
		// ユーザーコードの設定

		// データを削除する
		dao.delete(kjlMstentity);
	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// 実体の初期化
		KJL_MST entity = (KJL_MST) dto;

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
		KJL_MST entity = (KJL_MST) dto;

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
		KJL_MST entity = (KJL_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String kjlusrID = entity.getKJL_USR_ID();
		String kjlkmtCode = entity.getKJL_KMT_CODE();
		String kjldpkSSK = entity.getKJL_DPK_SSK();

		return dao.getKJL_MSTByKaicodeKjlusridKjlkmtcodeKjldpkssk(kaiCode, kjlusrID, kjlkmtCode, kjldpkSSK);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @return String
	 */
	protected String getPrimaryCode() {
		// KJL_MST entity = (KJL_MST)dto;
		return null;
	}
}
