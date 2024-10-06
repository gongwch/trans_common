package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 部門階層マスタ操作
 */
public class DepartmentHierarchyLogicImpl extends CommonLogicBaseImpl implements DepartmentHierarchyLogic {

	/** 部門階層マスタDao */
	protected DPK_MSTDao dao;

	/** 部門階層マスタ実体 */
	protected DPK_MST entity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao DPK_MSTDao
	 */
	public DepartmentHierarchyLogicImpl(DPK_MSTDao dao) {
		// 部門階層マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * DPK_MSTインスタンスの設定.
	 * 
	 * @param entity DPK_MST
	 */
	public void setEntity(DPK_MST entity) {
		// 部門階層マスタ実体を返す
		this.entity = entity;
	}

	/**
	 * 結果を検索する
	 * 
	 * @param conditions Map
	 */
	public List find(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 組織コードの取得
		String dpkSsk = (String) conditions.get("dpkSsk");

		List result = dao.find(kaiCode, dpkSsk);

		for (int i = 0; i < result.size(); i++) {
			for (int j = i + 1; j < result.size(); j++) {
				DPK_MST entity1 = (DPK_MST) result.get(i);
				DPK_MST entity2 = (DPK_MST) result.get(j);

				int compare = compareTo(entity1.getDPK_LVL_0(), entity2.getDPK_LVL_0());

				if (compare == 0) {
					compare = compareTo(entity1.getDPK_LVL_1(), entity2.getDPK_LVL_1());

					if (compare == 0) {
						compare = compareTo(entity1.getDPK_LVL_2(), entity2.getDPK_LVL_2());

						if (compare == 0) {
							compare = compareTo(entity1.getDPK_LVL_3(), entity2.getDPK_LVL_3());

							if (compare == 0) {
								compare = compareTo(entity1.getDPK_LVL_4(), entity2.getDPK_LVL_4());

								if (compare == 0) {
									compare = compareTo(entity1.getDPK_LVL_5(), entity2.getDPK_LVL_5());

									if (compare == 0) {
										compare = compareTo(entity1.getDPK_LVL_6(), entity2.getDPK_LVL_6());

										if (compare == 0) {
											compare = compareTo(entity1.getDPK_LVL_7(), entity2.getDPK_LVL_7());

											if (compare == 0) {
												compare = compareTo(entity1.getDPK_LVL_8(), entity2.getDPK_LVL_8());

												if (compare == 0) {
													compare = compareTo(entity1.getDPK_LVL_9(), entity2.getDPK_LVL_9());
												}
											}
										}
									}
								}
							}
						}
					}
				}

				if (compare > 0) {
					result.set(i, entity2);
					result.set(j, entity1);
				}
			}
		}

		// 結果を返す
		return result;
	}

	private int compareTo(String a, String b) {
		if (a == null) {
			return -1;
		} else if (b == null) {
			return 1;
		} else {
			return a.compareTo(b);
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
		// 組織コードの取得
		String dpkSsk = (String) keys.get("dpkSsk");
		// 部門コードの取得
		String dpkDepCode = (String) keys.get("dpkDepCode");
		// 結果を返す
		return dao.getDPK_MSTByKaicodeDpksskDpkdepcode(kaiCode, dpkSsk, dpkDepCode);
	}

	/**
	 * データを削除する
	 * 
	 * @param keys Map
	 */
	public void delete(Map keys) {
		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");
		// 組織コードの取得
		String dpkSsk = (String) keys.get("dpkSsk");
		// 部門コードの取得
		String dpkDepCode = (String) keys.get("dpkDepCode");
		// 会社コードの設定
		entity.setKAI_CODE(kaiCode);
		// 組織コードの設定
		entity.setDPK_SSK(dpkSsk);
		// 部門コードの設定
		entity.setDPK_DEP_CODE(dpkDepCode);
		// データを削除する
		dao.delete(entity);
	}

	/**
	 * データを削除する
	 * 
	 * @param entity1 DPK_MST
	 */
	public void delete(DPK_MST entity1) {
		// データを削除する
		dao.delete(entity1);
	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// 実体の初期化
		DPK_MST entity1 = (DPK_MST) dto;

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
		DPK_MST entity1 = (DPK_MST) dto;

		// データを更新する
		dao.update(entity1);
	}

	/**
	 * 結果を検索する
	 * 
	 * @param kaiCode String
	 */
	public List getOrganizations(String kaiCode) {
		// 結果を返す
		return dao.getOrganizations(kaiCode);
	}

	/**
	 * 編集元のレコードの取得。 プロパーティ「Entity」から、主キーを取得し、 daoのメソッドを呼出し、DBから編集元のレコードを取得する
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		DPK_MST entity1 = (DPK_MST) dto;
		String kaiCode = entity1.getKAI_CODE();
		String dpkSSK = entity1.getDPK_SSK();
		String dpkdepCode = entity1.getDPK_DEP_CODE();

		return dao.getDPK_MSTByKaicodeDpksskDpkdepcode(kaiCode, dpkSSK, dpkdepCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		DPK_MST entity1 = (DPK_MST) dto;
		return entity1.getDPK_SSK();
	}

	public List getREFItems(Map keys) {
		DPK_MST entity1 = (DPK_MST) this.findOne(keys);
		List<Object> list = new ArrayList<Object>(1);

		if (entity1 != null) {
			// 結果の初期化
			List<Object> result = new ArrayList<Object>(10);

			int triCode = entity1.getDPK_LVL();
			// 部門レベル
			result.add(triCode);
			// 属するレベルごとに部門コードを持ってくる。
			// 上位部門設定の対応
			result.add(entity1.getDPK_LVL_0());
			result.add(entity1.getDPK_LVL_1());
			result.add(entity1.getDPK_LVL_2());
			result.add(entity1.getDPK_LVL_3());
			result.add(entity1.getDPK_LVL_4());
			result.add(entity1.getDPK_LVL_5());
			result.add(entity1.getDPK_LVL_6());
			result.add(entity1.getDPK_LVL_7());
			result.add(entity1.getDPK_LVL_8());
			result.add(entity1.getDPK_LVL_9());

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
