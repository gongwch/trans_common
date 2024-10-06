package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * 補助科目の任意選択コンポーネントダイアログ
 * 
 * @author AIS
 */
public class TSubItemOptionalSelectorDialog extends TOptionalSelectorDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 6720856141179813605L;

	/**
	 * テーブルのカラム
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** コード */
		code,
		/** 名 */
		names,
		/** ENTITY */
		entity
	}

	/**
	 * @param parent
	 * @param mordal
	 */
	public TSubItemOptionalSelectorDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

}
