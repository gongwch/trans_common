package jp.co.ais.trans.common.gui;

/**
 * ラベル半角変換テキストフィールド（ラベルテキストフィールド拡張）
 */
public class TLabelHarfSizeCharConvertField extends TLabelField {

	/**
	 * Constructor. <br>
	 * 子itemを初期化する.
	 */
	public TLabelHarfSizeCharConvertField() {
		super();
	}

	/**
	 * テキストフィールド生成
	 * 
	 * @return テキストフィールド
	 */
	@Override
	protected THarfSizeCharConvertField createTextField() {
		return new THarfSizeCharConvertField();
	}
}
