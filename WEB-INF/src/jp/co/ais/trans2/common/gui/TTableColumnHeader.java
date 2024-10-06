package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.table.*;

/**
 * ƒwƒbƒ_•`‰æ‰Â”\‚ÌƒJƒ‰ƒ€
 */
public interface TTableColumnHeader {

	/**
	 * ƒwƒbƒ_•`‰æÒ‚Ìì¬
	 * 
	 * @param tbl
	 * @param backGround
	 * @param foreGround
	 * @return ƒwƒbƒ_•`‰æÒ
	 */
	public TableCellRenderer createHeaderRenderer(TTable tbl, Color backGround, Color foreGround);

}
