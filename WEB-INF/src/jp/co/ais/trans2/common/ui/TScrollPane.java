package jp.co.ais.trans2.common.ui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.TDialog;

/**
 * スクロールパネル
 */
public class TScrollPane extends JScrollPane {

	/** 元パネル */
	protected TPanelBusiness panel = null;

	/** 元パネル */
	protected TDialog dialog = null;

	/** 表示View */
	protected JPanel view;

	/**
	 * @param dialog
	 */
	public TScrollPane(TTabbedDialog dialog) {
		super(dialog.getDialogBasePanel());

		// 初期化
		init(dialog);
	}

	/**
	 * @param pnl
	 */
	public TScrollPane(TPanel pnl) {
		super(pnl);

		// 初期化
		init(pnl, false);
	}

	/**
	 * @param pnl
	 */
	public TScrollPane(TPanelBusiness pnl) {
		super(null);

		// 初期化
		init(pnl, true);
	}

	/**
	 * @return 業務パネル
	 */
	public TPanelBusiness getPanel() {
		return panel;
	}

	/**
	 * @return ダイアログ
	 */
	public TDialog getDialog() {
		return dialog;
	}

	/**
	 * 初期化
	 * 
	 * @param cont
	 */
	public void init(TTabbedDialog cont) {
		init(cont.getDialogBasePanel(), true);
	}

	/**
	 * 初期化
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

		// ステップ
		getVerticalScrollBar().setUnitIncrement(40);
		getHorizontalScrollBar().setUnitIncrement(40);
	}

	/**
	 * @return 表示View
	 */
	public JPanel getViewPanel() {
		return view;
	}
}
