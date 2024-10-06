package jp.co.ais.trans.common.gui.dnd;

/**
 * TOrderedTable データ設定用エンティティ
 */
public class OrderedData {

	/** 入力名称 */
	private String editableName;

	/** 表示区分 */
	private int hyjKbn;

	/** コード */
	private String code;

	/** 名称 */
	private String name;

	/**
	 * 表示区分を取得する
	 * 
	 * @return 表示区分
	 */
	public int getHyjKbn() {
		return hyjKbn;
	}

	/**
	 * 表示区分を設定する
	 * 
	 * @param hyjKbn
	 */
	public void setHyjKbn(int hyjKbn) {
		this.hyjKbn = hyjKbn;
	}

	/**
	 * コードを取得する
	 * 
	 * @return コード
	 */
	public String getCode() {
		return code;
	}

	/**
	 * コードを設定する
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 名称を取得する
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名称を設定する
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 入力名称を取得する
	 * 
	 * @return 入力名称
	 */
	public String getEditableName() {
		return editableName;
	}

	/**
	 * 入力名称を設定する
	 * 
	 * @param editableName
	 */
	public void setEditableName(String editableName) {
		this.editableName = editableName;
	}
	
	/**
	 * エンティティの比較に使用する。
	 * 
	 * @param obj 比較
	 * @return boolean
	 */
	public boolean equals(OrderedData obj) {
		if (super.equals(obj)) {
			return true;
		} else {
			return this.code.equals(obj.getCode());
		}
	}

}
