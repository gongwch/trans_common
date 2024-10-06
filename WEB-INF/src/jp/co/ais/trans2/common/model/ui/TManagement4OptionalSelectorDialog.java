package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * 管理4の任意選択コンポーネントダイアログ
 * 
 * @author AIS
 */
public class TManagement4OptionalSelectorDialog extends TOptionalSelectorDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 6720856141179813605L;

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
	public TManagement4OptionalSelectorDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

}
