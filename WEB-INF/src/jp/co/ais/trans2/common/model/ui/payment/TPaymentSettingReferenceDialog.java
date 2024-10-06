package jp.co.ais.trans2.common.model.ui.payment;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * �����x�������}�X�^REF�_�C�A���O<br>
 * boolean TPaymentSettingReferenceController.IS_USE_ACCOUNT_KANA ��ON�̏ꍇ�g��
 */
public class TPaymentSettingReferenceDialog extends TReferenceDialog {

	/** �������`�J�i */
	public TTextField accountKana;

	/** �����_�C�A���O�̗�ԍ���` */
	public enum SC {
		/** Bean */
		bean,
		/** �R�[�h */
		code,
		/** ���� */
		names,
		/** �������� */
		namek,
		/** �������`�J�i */
		accountKana
	}

	/**
	 * @param controller �R���g���[��
	 */
	public TPaymentSettingReferenceDialog(TPaymentSettingReferenceController controller) {
		super(controller);
	}

	/**
	 * �R���|�[�l���g������
	 */
	@Override
	protected void initComponents() {
		super.initComponents();

		accountKana = new TTextField();

		tbl = new TTable();
		tbl.getTableHeader().setReorderingAllowed(false);
		tbl.addColumn(SC.bean, "", -1);
		tbl.addColumn(SC.code, getWord(controller.getCodeCaption()), 100);
		tbl.addColumn(SC.names, getWord(controller.getNamesCaption()), 160);
		tbl.addColumn(SC.namek, getWord(controller.getNamekCaption()), 160);
		tbl.addColumn(SC.accountKana, getWord("C00168"), 160);
	}

	/**
	 * �R���|�[�l���g�z�u
	 */
	@Override
	protected void allocateComponents() {
		super.allocateComponents();

		setPreferredSize(new Dimension(750, 520));

		// ��������
		GridBagConstraints gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 0);
		pnlSearch.add(namek, gc);

		// ���l�Z��
		int length = 160;
		accountKana.setMinimumSize(new Dimension(length, 20));
		accountKana.setPreferredSize(new Dimension(length, 20));
		gc = new GridBagConstraints();
		gc.insets = new Insets(0, 0, 0, 0);
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.anchor = GridBagConstraints.NORTHWEST;

		pnlSearch.add(accountKana, gc);

		pack();
	}

	/**
	 * Tab���Z�b�g
	 */
	@Override
	protected void setTabIndex() {
		int i = 0;
		code.setTabControlNo(i++);
		names.setTabControlNo(i++);
		namek.setTabControlNo(i++);
		accountKana.setTabControlNo(i++);
		if (ClientConfig.isFlagOn("trans.ref.table.focusable")) {
			tbl.setTabControlNo(i++);
			tbl.setEnterToButton(true);
		}
		btnSearch.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnBack.setTabControlNo(i++);
	}

}
