package jp.co.ais.trans2.common.ui;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * BI�{�^���ǉ��̃��C�����
 */
public class TBIMain extends TMain {

	/** Dr.Sum�{�^�� */
	public TButton btnDrSum;

	/**
	 * �R���|�[�l���g�̏�����
	 */
	@Override
	public void initComponents() {
		super.initComponents();

		btnDrSum = new TButton();
	}

	/**
	 * �w�b�_�[�̈�̔z�u
	 */
	@Override
	public void allocateHeader() {
		super.allocateHeader();

		// Dr.Sum�{�^��
		btnDrSum.setIcon(ResourceUtil.getImage(this.getClass(), "images/dr.sum.png"));
		TGuiUtil.setComponentSize(btnDrSum, new Dimension(108, 35));
		btnDrSum.setBorderPainted(false);
		btnDrSum.setOpaque(false);
		btnDrSum.setFocusable(false);

		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 9;
		gc.gridheight = 2;
		gc.insets = new Insets(0, 15, 2, 0);
		gc.anchor = GridBagConstraints.SOUTHEAST;
		pnlHeader.add(btnDrSum, gc);

	}

	/**
	 * @return �E��̃A�C�R���\��GridX
	 */
	@Override
	protected int getIconStartIndex() {
		return 10;
	}

}
