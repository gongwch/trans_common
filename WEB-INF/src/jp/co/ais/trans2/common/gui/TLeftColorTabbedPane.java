package jp.co.ais.trans2.common.gui;

import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * 色UI使うLeftのTabコンポーネント
 */
public class TLeftColorTabbedPane extends TLeftTabbedPane {

	/**
	 * コンストラクタ.
	 */
	public TLeftColorTabbedPane() {
		super();
	}

	@Override
	public void updateUI() {
		if (TUIManager.getLookAndFeelType() == LookAndFeelType.MINT) {
			setUI(TUIManager.getMenuUI(this));
			return;
		}

		super.updateUI();
	}
}
