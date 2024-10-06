package jp.co.ais.trans2.model.slip.panel;

/**
 * 伝票ロジックファクトリー
 */
public interface SlipPanelLogicFactory {

	/**
	 * 伝票種別を元にロジッククラスを返す.
	 * 
	 * @param slipType 伝票種別
	 * @return ロジッククラス
	 */
	public SlipPanelLogic getLogic(String slipType);

}
