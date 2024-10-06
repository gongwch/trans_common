package jp.co.ais.trans2.common.gui;

import java.awt.Dimension;

import jp.co.ais.trans.common.gui.TComboBox;
import jp.co.ais.trans.common.gui.TPanel;
import jp.co.ais.trans.common.gui.TTextField;

public class TTableFilterPopupMenuItem extends TPanel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7262586455569975556L;

	protected TTextField field;

	public TTableFilterPopupMenuItem() {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

	}

	protected void initComponents() {
		field = new TTextField();		
	}

	protected void allocateComponents() {

		setPreferredSize(new Dimension(200, 25));
		setLayout(null);

		field.setSize(200, 25);
		field.setLocation(0, 0);
		add(field);

	}

}
