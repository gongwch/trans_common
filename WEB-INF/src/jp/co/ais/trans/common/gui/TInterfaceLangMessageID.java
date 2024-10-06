package jp.co.ais.trans.common.gui;

/***************************************************************************************************
 * 
 * messageID言語変換 interface.
 * 
 **************************************************************************************************/
public interface TInterfaceLangMessageID {

	/**
	 * GUIアイテムに設定されたメッセージID取得
	 * 
	 * @return メッセージID
	 */
	String getLangMessageID();

	/**
	 * GUIアイテムにメッセージIDを設定
	 * 
	 * @param langMessageID メッセージID
	 */
	void setLangMessageID(String langMessageID);

}
