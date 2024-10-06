package jp.co.ais.trans2.model;

/**
 * 検索可能インターフェース <br>
 * 主に、検索機能でふるまいを共有するために使用する。
 * @author AIS
 *
 */
public interface TReferable {

	/**
	 * コードを返す
	 * @return コード
	 */
	public String getCode();

	/**
	 * コードをセットする
	 * @param code コード
	 */
	public void setCode(String code);

	/**
	 * 略称を返す
	 * @return 略称
	 */
	public String getNames();

	/**
	 * 略称をセットする
	 * @param names 略称
	 */
	public void setNames(String names);

}
