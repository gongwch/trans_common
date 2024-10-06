package jp.co.ais.trans2.common.gui;

import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * �FUI�g��Left��Tab�R���|�[�l���g
 */
public class TLeftColorTabbedPane extends TLeftTabbedPane {

	/**
	 * �R���X�g���N�^.
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
