package jp.co.ais.trans2.common.model.ui;

import java.awt.Frame;
import jp.co.ais.trans2.common.gui.TOptionalSelectorDialog;

/**
 * 部門の任意選択コンポーネントダイアログ
 * 
 * @author AIS
 */
public class TDepartmentOptionalSelectorDialog extends TOptionalSelectorDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 5346971094984929490L;

	/**
	 * テーブルのカラム
	 * 
	 * @author AIS
	 */
	public enum SC {
		code, names, entity
	}

	/**
	 * @param parent
	 * @param mordal
	 */
	public TDepartmentOptionalSelectorDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

}
