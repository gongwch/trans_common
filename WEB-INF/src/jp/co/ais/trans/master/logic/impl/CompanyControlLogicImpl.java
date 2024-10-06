package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 会社コントロールマスタ操作
 */
public class CompanyControlLogicImpl extends CommonLogicBaseImpl implements CompanyControlLogic {

	/** 会社コントロールマスタDao */
	private CMP_MSTDao dao;

	/** 会社コントロールマスタ実体 */
	private CMP_MST entity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao CMP_MSTDao
	 */
	public CompanyControlLogicImpl(CMP_MSTDao dao) {
		// 会社コントロールマスタDaoを返す
		this.dao = dao;
	}

	/**
	 * CMP_MSTインスタンスの設定.
	 * 
	 * @param entity CMP_MST
	 */
	public void setEntity(CMP_MST entity) {
		// 会社コントロールマスタ実体を返す
		this.entity = entity;
	}

	/**
	 * 結果を検索する
	 * 
	 * @param conditions Map
	 */
	public List find(Map conditions) {

		// 会社コードの取得
		String kaiCode = Util.avoidNull(conditions.get("kaiCode"));
		String prgCode = Util.avoidNull(conditions.get("prgCode"));

		// 結果を返す
		return dao.getAllCMP_MST(kaiCode,prgCode);
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
		return dao.getCMP_MST_ByKaicode(kaiCode);
	}

	public Object findOne(String kaiCode) {
		// 結果を返す
		return dao.getCMP_MST_ByKaicode(kaiCode);
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 会社コードの設定
		entity.setKAI_CODE(kaiCode);
		// データを削除する
		dao.delete(entity);
	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// 実体の初期化
		CMP_MST entity1 = (CMP_MST) dto;

		// データを登録する
		dao.insert(entity1);
	}

	/**
	 * データを更新する
	 * 
	 * @param dto Object
	 */
	protected void updateImpl(Object dto, Object oldDto) {
		// 実体の初期化
		CMP_MST entity1 = (CMP_MST) dto;

		// データを更新する
		dao.update(entity1);
	}

	/**
	 * 編集元のレコードの取得。 プロパーティ「Entity」から、主キーを取得し、 daoのメソッドを呼出し、DBから編集元のレコードを取得する
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		CMP_MST entity1 = (CMP_MST) dto;
		String kaiCode = entity1.getKAI_CODE();

		return dao.getCMP_MST_ByKaicode(kaiCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		CMP_MST entity1 = (CMP_MST) dto;
		return entity1.getKAI_CODE();
	}

	public List getREFItems(Map keys) {
		CMP_MST entity1 = (CMP_MST) this.findOne(keys);
		List<Object> list = new ArrayList<Object>(1);

		if (entity1 != null) {
			// 結果の初期化
			List<Object> result = new ArrayList<Object>(6);

			result.add(entity1.getCMP_KMK_NAME());

			result.add(entity1.getCMP_HKM_NAME());
			// 終了
			result.add(entity1.getCMP_UKM_KBN());

			result.add(entity1.getCMP_UKM_NAME());
			// 結果の設定
			list.add(result);
		} else {
			// 結果の削除
			list.add(null);
		}
		// 結果を返す
		return list;
	}
}
