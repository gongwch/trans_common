package jp.co.ais.trans.common.gui;

import java.util.List;

/**
 * JTableの行タイトル、列タイトルのメッセージIDを登録／取得する interface. 
 * <br>
 * JTableのColumnLabelとRowLabelに、メッセージIDを登録／取得する.
 */
public interface TInterfaceTableLangMessageID {

	/**
	 * 列タイトルのmessageID取得
	 * 
	 * @return messageID配列
	 */
	public String[] getColumnTitleMessageID();

	/**
	 * 列タイトルのmessageID設定(配列版)
	 * 
	 * @param messageIDs
	 */
	public void setColumnTitleMessageID(String[] messageIDs);

	/**
	 * 列タイトルのmessageID設定(List版)
	 * 
	 * @param list
	 */
	public void setColumnTitleMessageID(List<String> list);

	/**
	 * 行タイトルのmessageID取得
	 * 
	 * @return messageID配列
	 */
	public String[] getRowTitleMessageID();

	/**
	 * 行タイトルのmessageID設定(配列版)
	 * 
	 * @param messageIDs
	 */
	public void setRowTitleMessageID(String[] messageIDs);

	/**
	 * 行タイトルのmessageID設定(List版)
	 * 
	 * @param list
	 */
	public void setRowTitleMessageID(List<String> list);
}
