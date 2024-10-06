package jp.co.ais.trans.common.gui.dnd;

/**
 * TTｒeeの階層データ受け渡しに使用するクラス
 * 
 * @author Yanwei
 */
public class DnDData {

	private String text1;

	private String text2;

	private String text3;

	public String getText1() {
		return text1;
	}

	public void setText1(String text1) {
		this.text1 = text1;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public String getText3() {
		return text3;
	}

	public void setText3(String text3) {
		this.text3 = text3;
	}

	/** コード */
	private String code = "";

	/** 名称 */
	private String name = "";

	/** 上位階層コード */
	private String topCode = "";

	public DnDData() {
		//
	}

	public DnDData(String code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * ノードに表示する名称を取得する
	 * 
	 * @return ノードに表示する名称
	 */
	public String getViewName() {
		return this.toString();
	}

	/**
	 * オブジェクトのオリジナル文字表現を返す.
	 * 
	 * @return プロパティ.コード + 半角スペース + プロパティ.名称
	 */
	@Override
	public String toString() {
		return this.code + " " + this.name;
	}

	/**
	 * エンティティの比較に使用する。
	 * 
	 * @param obj 比較
	 * @return boolean
	 */
	public boolean equals(DnDData obj) {
		if (super.equals(obj)) {
			return true;
		} else {
			return this.code.equals(obj.getCode());
		}
	}

	/**
	 * コード
	 * 
	 * @return コード
	 */
	public String getCode() {
		return code;
	}

	/**
	 * コード
	 * 
	 * @param code コード設定する
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 名称
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 名称
	 * 
	 * @param name 名称設定する
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 上位階層コード
	 * 
	 * @return 上位階層コード
	 */
	public String getTopCode() {
		return topCode;
	}

	/**
	 * 上位階層コード
	 * 
	 * @param topCode 上位階層コード設定する
	 */
	public void setTopCode(String topCode) {
		this.topCode = topCode;
	}

}