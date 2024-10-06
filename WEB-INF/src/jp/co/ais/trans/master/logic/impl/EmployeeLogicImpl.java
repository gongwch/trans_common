package jp.co.ais.trans.master.logic.impl;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 社員マスタ操作
 */
public class EmployeeLogicImpl extends CommonLogicBaseImpl implements EmployeeLogic {

	/** 社員マスタDao */
	protected EMP_MSTDao dao;

	/** 社員マスタ実体 */
	protected EMP_MST empMstentity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao EMP_MSTDao
	 */
	public EmployeeLogicImpl(EMP_MSTDao dao) {
		// 社員マスタDaoを返す
		this.dao = dao;
	}

	/**
	 * EMP_MSTインスタンスの設定.
	 * 
	 * @param entity EMP_MST
	 */
	public void setEntity(EMP_MST entity) {
		// 社員マスタ実体を返す
		this.empMstentity = entity;
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
		String beginEmpCode = (String) conditions.get("beginEmpCode");
		// 終了コードの取得
		String endEmpCode = (String) conditions.get("endEmpCode");
		// 開始コードも終了コードもない場合
		if (Util.isNullOrEmpty(beginEmpCode) && Util.isNullOrEmpty(endEmpCode)) {
			// 結果を返す
			return dao.getAllEMP_MST2(kaiCode);
			// 開始コードだけない場合
		} else if (Util.isNullOrEmpty(beginEmpCode)) {
			// 結果を返す
			return dao.getEmpInfoTo(kaiCode, endEmpCode);
			// 終了コードだけない場合
		} else if (Util.isNullOrEmpty(endEmpCode)) {
			// 結果を返す
			return dao.getEmpInfoFrom(kaiCode, beginEmpCode);
			// 開始コードと終了コード全部ある場合
		} else {
			// 結果を返す
			return dao.getEmpInfo(kaiCode, beginEmpCode, endEmpCode);
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
		// 社員コードの取得
		String empCode = (String) keys.get("empCode");
		// 結果を返す
		return dao.getEMP_MSTByKaicodeEmpcode(kaiCode, empCode);
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
			EMP_MST e = (EMP_MST) iterator.next();
			// 実体の初期化
			REFEntity e2 = new REFEntity();
			// 社員コードの設定
			e2.setCode(e.getEMP_CODE());
			// 社員略称の設定
			e2.setName_S(e.getEMP_NAME_S());
			// 社員検索名称の設定
			e2.setName_K(e.getEMP_NAME_K());
			// データ集の設定
			list2.add(e2);
		}
		// 結果を返す
		return list2;
	}

	/**
	 * 結果を検索する
	 * 
	 * @param keys String,keys Map
	 */
	public String findName(Map keys) {
		// コード
		String code = (String) keys.get("code");
		// 会社コードの取得
		String kaiCode = (String) keys.get("kaiCode");
		// 実体の初期化
		EMP_MST entity = dao.getEMP_MSTByKaicodeEmpcode(kaiCode, code);
		// 実体を返す
		return (entity == null ? null : entity.getEMP_NAME_S());
	}

	/**
	 * データを削除する
	 * 
	 * @param conditions Map
	 */
	public void delete(Map conditions) {
		// 会社コードの取得
		String kaiCode = (String) conditions.get("kaiCode");
		// 社員コードの取得
		String empCode = (String) conditions.get("empCode");
		// 会社コードの設定
		empMstentity.setKAI_CODE(kaiCode);
		// 社員コードの設定
		empMstentity.setEMP_CODE(empCode);
		// データを削除する
		dao.delete(empMstentity);
	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// 実体の初期化
		EMP_MST entity = (EMP_MST) dto;

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
		EMP_MST entity = (EMP_MST) dto;

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
		EMP_MST entity = (EMP_MST) dto;
		String kaiCode = entity.getKAI_CODE();
		String empCode = entity.getEMP_CODE();

		return dao.getEMP_MSTByKaicodeEmpcode(kaiCode, empCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		EMP_MST entity = (EMP_MST) dto;
		return entity.getEMP_CODE();
	}

	/**
	 * ユーザ情報取得
	 * 
	 * @param keys ユーザコード
	 * @return List ユーザ情報
	 */
	public List getREFItems(Map keys) {
		EMP_MST entity = (EMP_MST) this.findOne(keys);
		List<Object> list = new ArrayList<Object>(1);

		if (entity != null) {
			// 結果の初期化
			List<Object> result = new ArrayList<Object>(6);

			String empCode = entity.getEMP_CODE();
			String empName = entity.getEMP_NAME_S();

			result.add(empCode);
			// 銀行名の設定
			result.add(empName != null ? entity.getEMP_NAME_S() : "");

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
	 * ダイアログに表示されるユーザリスト取得
	 * 
	 * @param kaiCode
	 * @param empCode
	 * @param sName
	 * @param kName
	 * @param termBasisDate
	 * @param user
	 * @param depCode
	 * @param beginCode
	 * @param endCode
	 * @return ユーザリスト
	 */
	public List<Object> refDailog(String kaiCode, String empCode, String sName, String kName, Timestamp termBasisDate,
		String user, String depCode, String beginCode, String endCode) {

		return dao.refDialogSearch(kaiCode, empCode, sName, kName, termBasisDate, user, depCode, beginCode, endCode);
	}
}
