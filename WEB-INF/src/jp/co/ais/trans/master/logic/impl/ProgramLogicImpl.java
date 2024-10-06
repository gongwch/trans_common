package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * プログラムマスタ操作
 */
public class ProgramLogicImpl extends CommonLogicBaseImpl implements ProgramLogic {

	/** プログラムマスタDao */
	protected PRG_MSTDao dao;

	/** プログラムマスタ実体 */
	protected PRG_MST prgentity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao PRG_MSTDao
	 */
	public ProgramLogicImpl(PRG_MSTDao dao) {
		// プログラムマスタDaoを返す
		this.dao = dao;
	}

	/**
	 * PRG_MSTインスタンスの設定.
	 * 
	 * @param entity PRG_MST
	 */
	public void setEntity(PRG_MST entity) {
		// プログラムマスタ実体を返す
		this.prgentity = entity;
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
		String beginPrgCode = (String) conditions.get("beginPrgCode");
		// 終了コードの取得
		String endPrgCode = (String) conditions.get("endPrgCode");
		// 開始コードも終了コードもない場合
		if (Util.isNullOrEmpty(beginPrgCode) && Util.isNullOrEmpty(endPrgCode)) {
			// 結果を返す
			return dao.getAllPRG_MST2(kaiCode);
			// 開始コードだけない場合
		} else if (Util.isNullOrEmpty(beginPrgCode)) {
			// 結果を返す
			return dao.getPrgInfoTo(kaiCode, endPrgCode);
			// 終了コードだけない場合
		} else if (Util.isNullOrEmpty(endPrgCode)) {
			// 結果を返す
			return dao.getPrgInfoFrom(kaiCode, beginPrgCode);
			// 開始コードと終了コード全部ある場合
		} else {
			// 結果を返す
			return dao.getPrgInfo2(kaiCode, beginPrgCode, endPrgCode);
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
		// システムコードの取得
		String sysCode = (String) keys.get("sysCode");
		// プログラムコードの取得
		String prgCode = (String) keys.get("prgCode");
		// 結果を返す
		return dao.getPRG_MSTByKaicodeSyscodePrgcode(kaiCode, sysCode, prgCode);
	}

	/**
	 * 結果を検索する
	 * 
	 * @param conditions Map,name_S String,name_K String,keys Map
	 */
	public List findREF(Map conditions) {
		// コード
		String code = (String) conditions.get("code");
		// 略称
		String name_S = (String) conditions.get("name_S");
		// 検索名称
		String name_K = (String) conditions.get("name_K");
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		String beginCode = (String) conditions.get("beginCode");
		String endCode = (String) conditions.get("endCode");
		// 結果の取得
		List list = dao.conditionSearch(kaiCode, code, name_S, name_K, beginCode, endCode);
		// データ集の初期化
		List list2 = new ArrayList(list.size());
		// 結果の設定
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			// 結果の取得
			PRG_MST e = (PRG_MST) iterator.next();
			// 実体の初期化
			REFEntity e2 = new REFEntity();
			// プログラムコードの設定
			e2.setCode(e.getPRG_CODE());
			// プログラム略称の設定
			e2.setName_S(e.getPRG_NAME_S());
			// プログラム検索名称の設定
			e2.setName_K(e.getPRG_NAME_K());
			// データ集の設定
			list2.add(e2);
		}
		// 結果を返す
		return list2;
	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys Map
	 */
	public String findName(Map keys) {
		// コード
		String code = (String) keys.get("code");
		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");
		// 実体の初期化
		PRG_MST entity = dao.getPRG_MSTByKaicodePrgcode(kaiCode, code);
		// 実体を返す
		return (entity == null ? null : entity.getPRG_NAME_S());
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// システムコードの取得
		String sysCode = (String) conditions.get("sysCode");
		// プログラムコードの取得
		String prgCode = (String) conditions.get("prgCode");
		// 会社コードの設定
		prgentity.setKAI_CODE(kaiCode);
		// システムコードの設定
		prgentity.setSYS_CODE(sysCode);
		// プログラムコードの設定
		prgentity.setPRG_CODE(prgCode);
		// データを削除する
		dao.delete(prgentity);
	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// 実体の初期化
		PRG_MST entity = (PRG_MST) dto;

		dao.insert(entity);
	}

	/**
	 * データを更新する
	 * 
	 * @param dto Object
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// 実体の初期化
		PRG_MST entity = (PRG_MST) dto;

		dao.update(entity);
	}

	/**
	 * 編集元のレコードの取得。 プロパーティ「Entity」から、主キーを取得し、 daoのメソッドを呼出し、DBから編集元のレコードを取得する
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		PRG_MST entity = (PRG_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String sysCode = entity.getSYS_CODE();
		String prgCode = entity.getPRG_CODE();

		return dao.getPRG_MSTByKaicodeSyscodePrgcode(kaiCode, sysCode, prgCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。 *
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		PRG_MST entity = (PRG_MST) dto;
		return entity.getPRG_CODE();
	}

	/**
	 * @see jp.co.ais.trans.master.logic.ProgramLogic#searchProgramList(java.lang.String)
	 */
	public List<PRG_MST> searchProgramList(String kaiCode) {
		return dao.getAllPRG_MST2(kaiCode);
	}
}
