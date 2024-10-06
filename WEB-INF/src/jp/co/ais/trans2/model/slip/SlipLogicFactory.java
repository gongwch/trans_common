package jp.co.ais.trans2.model.slip;

/**
 * 伝票ロジックファクトリー
 */
public interface SlipLogicFactory {

	/**
	 * 伝票種別を元にロジッククラスを返す.
	 * 
	 * @param slipType 伝票種別
	 * @return ロジッククラス
	 */
	public SlipLogic getLogic(String slipType);
	
	/**
	 * 伝票種別を元にロジッククラスを返す.
	 * 
	 * @param slipType 伝票種別
	 * @param dataType 
	 * @return ロジッククラス
	 */
	public SlipLogic getLogic(String slipType, String dataType);

}
