package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * �`�[�����~�`�F�b�N
 */
public class TSlipPrintStopCheck extends TCheckBox {

	/**
	 * �R���X�g���N�^.
	 */
	public TSlipPrintStopCheck() {
		super();

		allocateComponents();
	}

	/**
	 * ������
	 */
	public void allocateComponents() {
		this.setLangMessageID("C01249");
		this.setEnterFocusable(false);
		this.setOpaque(false);

		TGuiUtil.setComponentSize(this, new Dimension(100, 20));

		if (!TLoginInfo.getCompany().getAccountConfig().isSlipPrint()) {
			this.setVisible(false);
			this.setSelected(true);

			return;
		}

		clear();
	}

	/**
	 * ������
	 */
	public void clear() {
		// ������̏����l PRINT_DEF
		boolean def = TLoginInfo.getCompany().getAccountConfig().getSlipPrintDefault();
		this.setSelected(!def);
	}
}
