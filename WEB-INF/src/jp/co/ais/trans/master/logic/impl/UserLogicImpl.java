package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * ユーザマスタ操作
 */
public class UserLogicImpl extends CommonLogicBaseImpl implements UserLogic {

	/** コンテナ */
	protected S2Container container;

	/** ユーザーマスタDao */
	protected USR_MSTDao dao;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param container コンテナ
	 */
	public UserLogicImpl(S2Container container) {
		this.container = container;
	}

	/**
	 * ユーザDao設定
	 * 
	 * @param usrMstDao USR_MSTDao
	 */
	public void setUSR_MSTDao(USR_MSTDao usrMstDao) {
		this.dao = usrMstDao;
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
		String beginUsrCode = (String) conditions.get("beginUsrCode");
		// 終了コードの取得
		String endUsrCode = (String) conditions.get("endUsrCode");

		// 結果を返す
		return dao.getUsrMstData(kaiCode, beginUsrCode, endUsrCode);
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
		String usrCode = (String) keys.get("usrCode");
		// 結果を返す
		return dao.getUSR_MSTByKaicodeUsrcode(kaiCode, usrCode);
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
			USR_MST e = (USR_MST) iterator.next();
			// 実体の初期化
			REFEntity e2 = new REFEntity();
			// ユーザーコードの設定
			e2.setCode(e.getUSR_CODE());
			// ユーザー略称の設定
			e2.setName_S(e.getUSR_NAME_S());
			// ユーザー検索名称の設定
			e2.setName_K(e.getUSR_NAME_K());
			// データ集の設定
			list2.add(e2);
		}
		// 結果を返す
		return list2;
	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys
	 */
	public String findName(Map keys) {
		// コード
		String code = (String) keys.get("code");
		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");
		// 実体の初期化
		USR_MST entity = dao.getRefName(kaiCode, code);

		// 実体を返す
		return (entity == null ? null : entity.getUSR_NAME_S());
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		USR_MST entity = (USR_MST) container.getComponent(USR_MST.class);

		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// ユーザーコードの取得
		String usrCode = (String) conditions.get("usrCode");
		// 会社コードの設定
		entity.setKAI_CODE(kaiCode);
		// ユーザーコードの設定
		entity.setUSR_CODE(usrCode);
		// データを削除する
		dao.delete(entity);

		S2Container scontainer = SingletonS2ContainerFactory.getContainer();
		LOCK_OUT_TBLDao lockDao = (LOCK_OUT_TBLDao) scontainer.getComponent(LOCK_OUT_TBLDao.class);
		PWD_HST_TBLDao historyDao = (PWD_HST_TBLDao) scontainer.getComponent(PWD_HST_TBLDao.class);
		PCI_CHK_CTLDao pciDao = (PCI_CHK_CTLDao) scontainer.getComponent(PCI_CHK_CTLDao.class);
		pciDao.delete(kaiCode, usrCode);
		historyDao.deleteByPK(kaiCode, usrCode);
		lockDao.deleteByPK(kaiCode, usrCode);

	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// データを登録する
		dao.insert((USR_MST) dto);
	}

	/**
	 * データを更新する
	 * 
	 * @param dto Object
	 */
	protected void updateImpl(Object dto, Object oldDto) {

		// データを更新する
		dao.update((USR_MST) dto);
	}

	/**
	 * 編集元のレコードの取得。 プロパーティ「Entity」から、主キーを取得し、 daoのメソッドを呼出し、DBから編集元のレコードを取得する
	 * 
	 * @param dto
	 * @return Object
	 */
	protected Object getOldEntity(Object dto) {
		USR_MST entity = (USR_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String usrCode = entity.getUSR_CODE();

		return dao.getUSR_MSTByKaicodeUsrcode(kaiCode, usrCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。 *
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		USR_MST entity = (USR_MST) dto;
		return entity.getUSR_CODE();
	}

	public List getREFItems(Map keys) {
		USR_MST entity = (USR_MST) this.findOne(keys);
		List<Object> list = new ArrayList<Object>(1);

		if (entity != null) {
			// 結果の初期化
			List<Object> result = new ArrayList<Object>(6);

			String empCode = entity.getEMP_CODE();
			String depCode = entity.getDEP_CODE();

			result.add(empCode);
			// 銀行名の設定
			result.add(depCode != null ? entity.getDEP_CODE() : "");

			result.add(entity.getSTR_DATE());
			// 終了
			result.add(entity.getEND_DATE());
			// 結果の設定
			list.add(result);
		} else {
			// 結果の削除
			list.add(null);
		}
		// 結果を返す
		return list;
	}

	/**
	 * 会社コードでユーザリストを習得
	 * 
	 * @param kaiCode 会社コード
	 * @return List<USR_MST> ユーザlist
	 */
	public List<USR_MST> searchUsrList(String kaiCode) {
		return dao.getUSR_MSTByKaicode(kaiCode);
	}

	/**
	 * 更新ユーザマスタ
	 * 
	 * @param entity ユーザマスタ
	 * @param oldKaiCode old会社コード
	 */
	public void updateUsrMst(USR_MST entity, String oldKaiCode) {
		dao.updateUsrMst(entity, oldKaiCode);
	}
}
