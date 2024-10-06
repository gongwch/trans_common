package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * 会社階層マスタ 新規組織ダイアログ
 * 
 * @author chchcj
 */
public class OW0140EditNewOrganizationDialog extends jp.co.ais.trans.common.gui.TDialog {

	private static final long serialVersionUID = -7654838924043528010L;

	private OW0140EditNewOrganizationDialogCtrl ctrl;

	/** 確定されたかどうか */
	boolean isSettle = false;

	/**
	 * Creates new form OW0140NewOrganizationDialog
	 * 
	 * @param parent
	 */
	public OW0140EditNewOrganizationDialog(Frame parent) {
		this(parent, true);
	}

	/**
	 * Creates new form OW0140EditNewOrganizationDialog
	 * 
	 * @param parent
	 * @param modal
	 */
	public OW0140EditNewOrganizationDialog(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		// ** messageID変換, tab順登録のため、initComponents()の後に必ず呼ぶこと */
		super.initDialog();
	}

	/**
	 * @param parent
	 * @param modal
	 * @param ctrl
	 * @param titleID
	 */
	public OW0140EditNewOrganizationDialog(Frame parent, boolean modal, OW0140EditNewOrganizationDialogCtrl ctrl,
		String titleID) {
		super(parent, modal);
		this.ctrl = ctrl;
		setLangMessageID(titleID);
		initComponents();
		// ** messageID変換, tab順登録のため、initComponents()の後に必ず呼ぶこと */
		super.initDialog();

	}

	/**
	 * フレームを返す
	 */
	public Frame getParentFrame() {
		return super.getParentFrame();
	}

	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		pnlButton = new jp.co.ais.trans.common.gui.TPanel();
		btnSettle = new jp.co.ais.trans.common.gui.TButton();
		btnReturn = new jp.co.ais.trans.common.gui.TButton();
		pnlDetial = new jp.co.ais.trans.common.gui.TPanel();
		ctrlLevel0 = new jp.co.ais.trans.common.gui.TButtonField();
		ctrlOrganizationCode = new jp.co.ais.trans.common.gui.TLabelField();

		setResizable(false);

		getContentPane().setLayout(new java.awt.GridBagLayout());
		pnlButton.setLayout(new java.awt.GridBagLayout());

		pnlButton.setMaximumSize(new java.awt.Dimension(700, 40));
		// AIS(D) LIUCHENGJIE
		pnlButton.setMinimumSize(new java.awt.Dimension(700, 40));
		// AIS(D) LIUCHENGJIE
		pnlButton.setPreferredSize(new java.awt.Dimension(700, 40));
		// AIS(D) LIUCHENGJIE
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new java.awt.Dimension(110, 25));
		btnSettle.setMinimumSize(new java.awt.Dimension(110, 25));
		btnSettle.setPreferredSize(new java.awt.Dimension(110, 25));
		btnSettle.setTabControlNo(3);
		btnSettle.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnSettleActionPerformed();
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(10, 390, 5, 0);
		// AIS(D) LIUCHENGJIE
		pnlButton.add(btnSettle, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new java.awt.Dimension(110, 25));
		btnReturn.setMinimumSize(new java.awt.Dimension(110, 25));
		btnReturn.setPreferredSize(new java.awt.Dimension(110, 25));
		btnReturn.setTabControlNo(4);
		btnReturn.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnReturnActionPerformed();
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);

		getContentPane().add(pnlButton, new java.awt.GridBagConstraints());

		pnlDetial.setLayout(new java.awt.GridBagLayout());

		pnlDetial.setMaximumSize(new java.awt.Dimension(700, 85));
		pnlDetial.setMinimumSize(new java.awt.Dimension(700, 85));
		pnlDetial.setPreferredSize(new java.awt.Dimension(700, 85));

		ctrlLevel0.setButtonSize(85);
		ctrlLevel0.setFieldSize(120);
		ctrlLevel0.setLangMessageID("C00722");
		ctrlLevel0.setMaxLength(10);
		ctrlLevel0.setNoticeSize(450);
		ctrlLevel0.setTabControlNo(2);
		ctrlLevel0.setImeMode(false);
		ctrlLevel0.getButton().addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ctrlLevel0ActionPerformed();
			}
		});
		ctrlLevel0.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				if (!ctrlLevel0.isValueChanged()) {
					return true;
				}
				return ctrlLevel0FocusLost();
			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 30, 0);
		pnlDetial.add(ctrlLevel0, gridBagConstraints);

		ctrlOrganizationCode.setFieldSize(120);
		ctrlOrganizationCode.setLabelSize(60);
		ctrlOrganizationCode.setLangMessageID("C00335");
		ctrlOrganizationCode.getField().setAllowedSpace(false);
		ctrlOrganizationCode.setMaxLength(5);
		ctrlOrganizationCode.setTabControlNo(1);
		ctrlOrganizationCode.setImeMode(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 25, 5, 0);
		// AIS(D) LIUCHENGJIE
		pnlDetial.add(ctrlOrganizationCode, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		getContentPane().add(pnlDetial, gridBagConstraints);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void ctrlLevel0ActionPerformed() {// GEN-FIRST:event_ctrlLevel0MouseClicked
		ctrl.doOwnerCompanyClick(ctrlLevel0);
	}// GEN-LAST:event_ctrlLevel0MouseClicked

	private void btnReturnActionPerformed() {// GEN-FIRST:event_btnReturnMouseClicked
		isSettle = false;
		ctrl.disposeDialog();
	}// GEN-LAST:event_btnReturnMouseClicked

	private void btnSettleActionPerformed() {// GEN-FIRST:event_btnSettleMouseClicked
		isSettle = true;
		ctrl.disposeDialog();
	}// GEN-LAST:event_btnSettleMouseClicked

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new OW0140EditNewOrganizationDialog(new javax.swing.JFrame(), true).setVisible(true);
			}
		});
	}

	private boolean ctrlLevel0FocusLost() {
		return ctrl.doOwnerCompanyLostFocus(ctrlLevel0);
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	jp.co.ais.trans.common.gui.TButton btnReturn;

	jp.co.ais.trans.common.gui.TButton btnSettle;

	jp.co.ais.trans.common.gui.TButtonField ctrlLevel0;

	jp.co.ais.trans.common.gui.TLabelField ctrlOrganizationCode;

	jp.co.ais.trans.common.gui.TPanel pnlButton;

	jp.co.ais.trans.common.gui.TPanel pnlDetial;
	// End of variables declaration//GEN-END:variables

}
