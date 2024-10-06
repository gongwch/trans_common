package jp.co.ais.trans2.common.gui;

import java.util.List;
import jp.co.ais.trans.common.dt.TransferBase;

/**
 * テーブル情報
 * @author AIS
 *
 */
public class TTableInformation extends TransferBase {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9194844537785228541L;

	/**
	 * テーブル情報を保存、復元する際のキー
	 */
	protected String tableKeyName;

	/**
	 * 列幅
	 */
	protected List<Integer> widthList;

	/**
	 * 列の表示順
	 */
	protected List<Integer> displayOrder;

	/**
	 * @return tableKeyNameを戻します。
	 */
	public String getTableKeyName() {
		return tableKeyName;
	}

	/**
	 * @param tableKeyName tableKeyNameを設定します。
	 */
	public void setTableKeyName(String tableKeyName) {
		this.tableKeyName = tableKeyName;
	}

	/**
	 * @return widthListを戻します。
	 */
	public List<Integer> getWidthList() {
		return widthList;
	}

	/**
	 * @param widthList widthListを設定します。
	 */
	public void setWidthList(List<Integer> widthList) {
		this.widthList = widthList;
	}

	/**
	 * @return displayOrderを戻します。
	 */
	public List<Integer> getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param displayOrder displayOrderを設定します。
	 */
	public void setDisplayOrder(List<Integer> displayOrder) {
		this.displayOrder = displayOrder;
	}

}
