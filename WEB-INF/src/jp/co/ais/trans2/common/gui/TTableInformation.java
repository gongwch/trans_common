package jp.co.ais.trans2.common.gui;

import java.util.List;
import jp.co.ais.trans.common.dt.TransferBase;

/**
 * �e�[�u�����
 * @author AIS
 *
 */
public class TTableInformation extends TransferBase {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9194844537785228541L;

	/**
	 * �e�[�u������ۑ��A��������ۂ̃L�[
	 */
	protected String tableKeyName;

	/**
	 * ��
	 */
	protected List<Integer> widthList;

	/**
	 * ��̕\����
	 */
	protected List<Integer> displayOrder;

	/**
	 * @return tableKeyName��߂��܂��B
	 */
	public String getTableKeyName() {
		return tableKeyName;
	}

	/**
	 * @param tableKeyName tableKeyName��ݒ肵�܂��B
	 */
	public void setTableKeyName(String tableKeyName) {
		this.tableKeyName = tableKeyName;
	}

	/**
	 * @return widthList��߂��܂��B
	 */
	public List<Integer> getWidthList() {
		return widthList;
	}

	/**
	 * @param widthList widthList��ݒ肵�܂��B
	 */
	public void setWidthList(List<Integer> widthList) {
		this.widthList = widthList;
	}

	/**
	 * @return displayOrder��߂��܂��B
	 */
	public List<Integer> getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param displayOrder displayOrder��ݒ肵�܂��B
	 */
	public void setDisplayOrder(List<Integer> displayOrder) {
		this.displayOrder = displayOrder;
	}

}
