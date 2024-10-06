package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 環境設定マスタ操作
 */
public class EnvironmentalSettingLogicImpl extends CommonLogicBaseImpl {

	/** 環境設定マスタDao */
	private ENV_MSTDao dao;

	/** 環境設定マスタ実体 */
	private ENV_MST envMstentity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao ENV_MSTDao
	 */
	public EnvironmentalSettingLogicImpl(ENV_MSTDao dao) {
		// 環境設定マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * ENV_MSTインスタンスの設定.
	 * 
	 * @param entity ENV_MST
	 */
	public void setEntity(ENV_MST entity) {
		// 環境設定マスタ実体を返す
		this.envMstentity = entity;
	}

	/**
	 * 結果を検索する
	 * 
	 * @param conditions Map
	 */
	public List find(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");

		if (Util.isNullOrEmpty(kaiCode)) {
			// 結果を返す
			return dao.getENV_MSTByKaicode1();
		} else {
			List list = new ArrayList();
			ENV_MST envMst = dao.getENV_MSTByKaicode(kaiCode);
			list.add(envMst);
			// 結果を返す
			return list;
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

		// 結果を返す
		return dao.getENV_MSTByKaicode(kaiCode);
	}

	/**
	 * 結果を検索する
	 * 
	 * @param conditions Map,name_S String,name_K String,keys Map
	 */
	public List findREF(Map conditions) {
		// ログイン会社コード
		String loginCompCode = Util.avoidNull(conditions.get("LOGIN_KAI_CODE"));
		// 入力会社コード
		String inCompCode = "";
		String inCompName_S = "";
		if (Util.avoidNull(conditions.get("refFlg")).equals("1")) {
			// 入力会社コード
			inCompCode = Util.avoidNull(conditions.get("code"));
			// 入力会社略称
			inCompName_S = Util.avoidNull(conditions.get("name_S"));
		} else {
			// 入力会社コード
			inCompCode = Util.avoidNull(conditions.get("KAI_CODE"));
			// 入力会社略称
			inCompName_S = Util.avoidNull(conditions.get("KAI_NAME_S"));
		}

		// 組織コード
		String dpkSsk = Util.avoidNull(conditions.get("DPK_SSK"));
		// 配下会社(0:含む 1:含まない)
		String cmpKbn = Util.avoidNull(conditions.get("CMP_KBN"));
		// 上位会社ｺｰﾄﾞ
		String upCmpCode = Util.avoidNull(conditions.get("UP_CMP_CODE"));
		// 階層ﾚﾍﾞﾙ
		String dpkLvl = Util.avoidNull(conditions.get("DPK_LVL"));
		// 初期会社ｺｰﾄﾞ
		String baseCmpCode = Util.avoidNull(conditions.get("BASE_CMP_CODE"));
		// 初期階層ﾚﾍﾞﾙ
		String baseDpkLvl = Util.avoidNull(conditions.get("BASE_DPK_LVL"));

		List list = null;
		if (dpkSsk != null && !"".equals(dpkSsk)) {
			// データを取得する。
			list = dao.conditionLvlSearch(loginCompCode, inCompCode, inCompName_S, dpkSsk, cmpKbn, upCmpCode, dpkLvl,
				baseCmpCode, baseDpkLvl);
		} else {
			// データを取得する。
			list = dao.conditionSearch(inCompCode, inCompName_S);
		}

		// データ集の初期化
		List list2 = new ArrayList(list.size());
		// 結果の設定
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// 結果の取得
			ENV_MST e = (ENV_MST) iterator.next();
			// 実体の初期化
			REFEntity e2 = new REFEntity();
			// 会社コードの設定
			e2.setCode(e.getKAI_CODE());
			// 会社略称の設定
			e2.setName_S(e.getKAI_NAME_S());
			// 会社検索名称の設定
			e2.setName_K("");
			// データ集の設定
			list2.add(e2);
		}
		// 結果を返す
		return list2;
	}

	/**
	 * 名称を取得する
	 * 
	 * @param keys Map
	 */
	public String findName(Map keys) {
		// コード
		String code = (String) keys.get("code");
		// 実体の初期化
		ENV_MST entity = dao.getENV_MSTByKaicode(code);
		// 実体を返す
		return (entity == null ? null : entity.getKAI_NAME_S());
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 会社名称の取得
		String kaiName = (String) conditions.get("kaiName");
		// 会社コードの設定
		envMstentity.setKAI_CODE(kaiCode);
		// 会社名称の設定
		envMstentity.setKAI_NAME(kaiName);
		// データを削除する
		dao.delete(envMstentity);
	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// 実体の初期化
		ENV_MST entity = (ENV_MST) dto;

		// データを登録する
		dao.insert(entity);
	}

	/**
	 * データを更新する
	 * 
	 * @param dto Object
	 * @param oldDto
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// 実体の初期化
		ENV_MST entity = (ENV_MST) dto;
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
		ENV_MST entity = (ENV_MST) dto;
		String kaiCode = entity.getKAI_CODE();

		return dao.getENV_MSTByKaicode(kaiCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		ENV_MST entity = (ENV_MST) dto;
		return entity.getKAI_CODE();
	}
}
