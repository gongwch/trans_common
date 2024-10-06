package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * 会社の任意選択コンポーネントダイアログ
 * 
 * @author AIS
 */
public class TCompanyOptionalSelectorDialog extends TOptionalSelectorDialog {

	/**
	 * テーブルのカラム
	 * 
	 * @author AIS
	 */
	public enum CMP_SEL_SC {
		/** BEAN */
		entity,
		/** コード */
		code,
		/** 名称 */
		name
	}

	/**
	 * @param parent
	 * @param mordal
	 */
	public TCompanyOptionalSelectorDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

}
