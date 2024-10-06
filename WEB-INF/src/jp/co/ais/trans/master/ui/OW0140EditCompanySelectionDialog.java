package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * 会社階層マスタ EDITダイアログ
 * 
 * @author chchcj
 */
public class OW0140EditCompanySelectionDialog extends jp.co.ais.trans.common.gui.TDialog {

	private static final long serialVersionUID = 9099574354942742117L;

	/** コントロールクラス */
	private OW0140EditCompanySelectionDialogCtrl ctrl;

	/** 確定されたかどうか */
	boolean isSettle = false;

	/**
	 * Creates new form OW0140EditCompanySelectionDialog
	 * 
	 * @param parent
	 * @param modal
	 */
	public OW0140EditCompanySelectionDialog(Frame parent, boolean modal) {
		super(parent, modal);
		setLangMessageID("C01994");
		initComponents();
		super.initDialog();
	}

	/**
	 * @param parent
	 * @param modal
	 * @param ctrl
	 * @param strTitleID
	 */
	public OW0140EditCompanySelectionDialog(Frame parent, boolean modal, OW0140EditCompanySelectionDialogCtrl ctrl,
		String strTitleID) {
		super(parent, modal);
		this.ctrl = ctrl;
		setLangMessageID(strTitleID);
		initComponents();
		super.initDialog();
	}

	/**
	 * フレームを返す
	 */
	public Frame getParentFrame() {
		return super.getParentFrame();
	}

	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TPanel();
		btnSettle = new TButton();
		btnReturn = new TButton();
		pnlDetial = new TPanel();
		ctrlLowerCompany = new TButtonField();
		ctrlUpperCompany = new TButtonField();

		setResizable(false);

		getContentPane().setLayout(new GridBagLayout());

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(545, 40));
		pnlButton.setMinimumSize(new Dimension(545, 40));
		pnlButton.setPreferredSize(new Dimension(545, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(3);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnSettleActionPerformed();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 235, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(4);
		btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnReturnActionPerformed();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);

		getContentPane().add(pnlButton, new GridBagConstraints());

		pnlDetial.setLayout(new GridBagLayout());

		pnlDetial.setMaximumSize(new Dimension(545, 80));
		pnlDetial.setMinimumSize(new Dimension(545, 80));
		pnlDetial.setPreferredSize(new Dimension(545, 80));
		ctrlLowerCompany.setButtonSize(85);
		ctrlLowerCompany.setFieldSize(120);
		ctrlLowerCompany.setLangMessageID("C01488");
		ctrlLowerCompany.setMaxLength(10);
		ctrlLowerCompany.setNoticeSize(255);
		ctrlLowerCompany.setTabControlNo(2);
		ctrlLowerCompany.setImeMode(false);

		ctrlLowerCompany.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				ctrlLowerCompanyActionPerformed();
			}
		});
		ctrlLowerCompany.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				if (!ctrlLowerCompany.isValueChanged()) {
					return true;
				}
				return ctrlLowerCompanyFocusLost();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 30, 0);
		pnlDetial.add(ctrlLowerCompany, gridBagConstraints);

		ctrlUpperCompany.setButtonSize(85);
		ctrlUpperCompany.setFieldSize(120);
		ctrlUpperCompany.setLangMessageID("C01487");
		ctrlUpperCompany.setMaxLength(10);
		ctrlUpperCompany.setNoticeSize(255);
		ctrlUpperCompany.setTabControlNo(1);
		ctrlUpperCompany.setImeMode(false);
		ctrlUpperCompany.getButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				ctrlUpperCompanyActionPerformed();
			}
		});
		ctrlUpperCompany.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				if (!ctrlUpperCompany.isValueChanged()) {
					return true;
				}
				return ctrlUpperCompanyFocusLost();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		pnlDetial.add(ctrlUpperCompany, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		getContentPane().add(pnlDetial, gridBagConstraints);

		pack();
	}

	private void btnReturnActionPerformed() {
		isSettle = false;
		ctrl.disposeDialog();
	}

	private void btnSettleActionPerformed() {
		isSettle = true;
		ctrl.disposeDialog();
	}

	private void ctrlLowerCompanyActionPerformed() {
		ctrl.doOwnerCompanyClick(ctrlLowerCompany);
	}

	private void ctrlUpperCompanyActionPerformed() {
		ctrl.doOwnerCompanyClick(ctrlUpperCompany);
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				new OW0140EditCompanySelectionDialog(new JFrame(), true).setVisible(true);
			}
		});
	}

	private boolean ctrlLowerCompanyFocusLost() {
		return ctrl.doOwnerCompanyLostFocus(ctrlLowerCompany);
	}

	private boolean ctrlUpperCompanyFocusLost() {
		return ctrl.doOwnerCompanyLostFocus(ctrlUpperCompany);
	}

	TButton btnReturn;

	TButton btnSettle;

	TButtonField ctrlLowerCompany;

	TButtonField ctrlUpperCompany;

	TPanel pnlButton;

	TPanel pnlDetial;

}
