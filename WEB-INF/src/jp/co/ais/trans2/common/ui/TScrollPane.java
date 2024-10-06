package jp.co.ais.trans2.common.ui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.TDialog;

/**
 * �X�N���[���p�l��
 */
public class TScrollPane extends JScrollPane {

	/** ���p�l�� */
	protected TPanelBusiness panel = null;

	/** ���p�l�� */
	protected TDialog dialog = null;

	/** �\��View */
	protected JPanel view;

	/**
	 * @param dialog
	 */
	public TScrollPane(TTabbedDialog dialog) {
		super(dialog.getDialogBasePanel());

		// ������
		init(dialog);
	}

	/**
	 * @param pnl
	 */
	public TScrollPane(TPanel pnl) {
		super(pnl);

		// ������
		init(pnl, false);
	}

	/**
	 * @param pnl
	 */
	public TScrollPane(TPanelBusiness pnl) {
		super(null);

		// ������
		init(pnl, true);
	}

	/**
	 * @return �Ɩ��p�l��
	 */
	public TPanelBusiness getPanel() {
		return panel;
	}

	/**
	 * @return �_�C�A���O
	 */
	public TDialog getDialog() {
		return dialog;
	}

	/**
	 * ������
	 * 
	 * @param cont
	 */
	public void init(TTabbedDialog cont) {
		init(cont.getDialogBasePanel(), true);
	}

	/**
	 * ������
	 * 
	 * @param cont
	 * @param withScrollAdjustment
	 */
	public void init(Container cont, boolean withScrollAdjustment) {

		Component comp = cont;

		if (cont instanceof TPanelBusiness) {
			panel = (TPanelBusiness) cont;
		} else if (cont instanceof TTabbedDialog) {
			dialog = ((TTabbedDialog) cont).getDialog();
			comp = ((TTabbedDialog) cont).getDialogBasePanel();
		} else if (cont instanceof TTabbedPanel) {
			dialog = (TDialog) ((TTabbedPanel) cont).getDialog();
		}

		if (withScrollAdjustment) {
			view = new JPanel();
			view.setLayout(new GridBagLayout());
			GridBagConstraints gc = new GridBagConstraints();
			gc.weightx = 1;
			gc.weighty = 1;
			gc.fill = GridBagConstraints.BOTH;
			gc.anchor = GridBagConstraints.NORTHWEST;
			view.add(comp, gc);

			TGuiUtil.setComponentSize(view, ClientConfig.getMainViewWidth(), ClientConfig.getMainViewHeight());

			setViewportView(view);
		}

		// �X�e�b�v
		getVerticalScrollBar().setUnitIncrement(40);
		getHorizontalScrollBar().setUnitIncrement(40);
	}

	/**
	 * @return �\��View
	 */
	public JPanel getViewPanel() {
		return view;
	}
}
