package jp.co.ais.trans2.define;

/**
 * 拡張子
 * 
 * @author AIS
 */
public enum ExtensionType {

	/** CSVファイル */
	CSV("csv"),

	/** テキストファイル */
	TEXT("txt"),

	/** PDFファイル */
	PDF("pdf"),

	/** XLSファイル */
	XLS("xls"),

	/** XLSXファイル */
	XLSX("xlsx");

	/** 値 */
	public String value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private ExtensionType(String value) {
		this.value = value;
	}

	/**
	 * extensionを返す
	 * 
	 * @param extension 拡張子
	 * @return 拡張子名
	 */
	public static ExtensionType getType(String extension) {
		for (ExtensionType em : values()) {
			if (em.value == extension) {
				return em;
			}
		}

		return null;
	}

}
