package jp.co.ais.trans.common.gui;

/**
 * ラベル、フィールドコンポ
 */
public interface ILabelField {

	/**
	 * Label getter
	 * 
	 * @return ラベル
	 */
	public TLabel getLabel();

	/**
	 * label 幅の設定.
	 * 
	 * @param size 幅
	 */
	public void setLabelSize(int size);

	/**
	 * label 幅の取得
	 * 
	 * @return 幅
	 */
	public int getLabelSize();
}
