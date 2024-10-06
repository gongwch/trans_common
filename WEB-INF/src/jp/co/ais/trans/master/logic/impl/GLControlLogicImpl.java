package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * GLコントロールマスタ操作
 */
public class GLControlLogicImpl extends CommonLogicBaseImpl {

	/** GLコントロールマスタDao */
	private GL_CTL_MSTDao dao;

	/** GLコントロールマスタ実体 */
	private GL_CTL_MST glCtlMstentity;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao GL_CTL_MSTDao
	 */
	public GLControlLogicImpl(GL_CTL_MSTDao dao) {
		// GLコントロールマスタDaoを返す
		this.dao = dao;
	}

	/**
	 * GL_CTL_MSTインスタンスの設定.
	 * 
	 * @param entity GL_CTL_MST
	 */
	public void setEntity(GL_CTL_MST entity) {
		// GLコントロールマスタ実体を返す
		this.glCtlMstentity = entity;
	}

	/**
	 * 結果を検索する
	 * 
	 * @param conditions Map
	 */
	public List find(Map conditions) {
		// 会社コードの取得
		String kaiCode = Util.avoidNull(conditions.get("kaiCode"));

		// 結果を返す
		return dao.getAllGL_CTL_MST(kaiCode);
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
		return dao.getGL_CTL_MST(kaiCode);
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
		glCtlMstentity.setKAI_CODE(kaiCode);
		// 結果を返す
		dao.delete(glCtlMstentity);
	}

	/**
	 * データを登録する
	 * 
	 * @param dto Object
	 */
	protected void insertImpl(Object dto) {
		// 実体の初期化
		GL_CTL_MST entity = (GL_CTL_MST) dto;

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
		GL_CTL_MST entity = (GL_CTL_MST) dto;

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
		GL_CTL_MST entity = (GL_CTL_MST) dto;
		String kaiCode = entity.getKAI_CODE();

		return dao.getGL_CTL_MSTByIKaicode(kaiCode);
	}

	/**
	 * 編集元のレコードの主キーの取得。 プロパーティ「Entity」から、主キーを取得し、 通常場合は、会社コード以外の主キーを戻る。
	 * 
	 * @param dto
	 * @return String
	 */
	protected String getPrimaryCode(Object dto) {
		GL_CTL_MST entity = (GL_CTL_MST) dto;
		return entity.getKAI_CODE();
	}
}
