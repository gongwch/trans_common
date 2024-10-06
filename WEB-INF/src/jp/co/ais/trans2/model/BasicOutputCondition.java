package jp.co.ais.trans2.model;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * 出力単位条件の基礎クラス
 * 
 * @param <T> 出力単位基礎エンティティ
 */
public abstract class BasicOutputCondition<T extends TReferable> extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** 組織コード */
	protected String organizationCode = null;

	/** 階層レベル */
	protected int level = 0;

	/** 配下組織を含むか */
	protected boolean includeUnder = false;

	/** 上位組織 */
	protected T superior = null;

	/** 開始組織 */
	protected T from = null;

	/** 終了組織 */
	protected T to = null;

	/** 個別選択組織 */
	protected List<T> optinalEntities = null;


	/**
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コード
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return 開始組織
	 */
	public T getFrom() {
		return from;
	}

	/**
	 * 開始組織
	 * 
	 * @param from
	 */
	public void setFrom(T from) {
		this.from = from;
	}

	/**
	 * @return 組織コード
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}

	/**
	 * 組織コード
	 * 
	 * @param organizationCode
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	/**
	 * @return 終了組織
	 */
	public T getTo() {
		return to;
	}

	/**
	 * 終了組織
	 * 
	 * @param to
	 */
	public void setTo(T to) {
		this.to = to;
	}

	/**
	 * @return 配下組織を含むか
	 */
	public boolean isIncludeUnder() {
		return includeUnder;
	}

	/**
	 * 配下組織を含むか
	 * 
	 * @param includeUnder
	 */
	public void setIncludeUnder(boolean includeUnder) {
		this.includeUnder = includeUnder;
	}

	/**
	 * @return 階層レベル
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * 階層レベル
	 * 
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return 個別選択組織
	 */
	public List<T> getOptionalEntities() {
		return optinalEntities;
	}

	/**
	 * 個別選択組織
	 * 
	 * @param optionalEntities
	 */
	public void setOptionalEntities(List<T> optionalEntities) {
		this.optinalEntities = optionalEntities;
	}

	/**
	 * @return 上位組織
	 */
	public T getSuperior() {
		return superior;
	}

	/**
	 * 上位組織
	 * 
	 * @param superior
	 */
	public void setSuperior(T superior) {
		this.superior = superior;
	}

	/**
	 * 上位組織のレベルを返す
	 * 
	 * @return 上位組織のレベル
	 */
	public int getSuperiorLevel() {
		return getLevel() - 1;
	}

	/**
	 * 個別選択された組織のコードリストを返す
	 * 
	 * @return 個別選択された組織のコードリスト
	 */
	public List<String> getOptionalEntitiesCode() {
		if (optinalEntities == null || optinalEntities.isEmpty()) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		for (T bean : optinalEntities) {
			list.add(bean.getCode());
		}
		return list;
	}

	/**
	 * テーブルIDの取得
	 * 
	 * @return tableID テーブルID
	 */
	public abstract String getTableID();

}
