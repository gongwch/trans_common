package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * 船の任意選択コンポーネントダイアログ
 * 
 * @author AIS
 */
public class TVesselOptionalSelectorDialog extends TOptionalSelectorDialog {

	/**
	 * テーブルのカラム
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** コード */
		code,
		/** 略称 */
		names,
		/** エンティティ */
		entity
	}

	/**
	 * @param parent
	 * @param mordal
	 */
	public TVesselOptionalSelectorDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

}
