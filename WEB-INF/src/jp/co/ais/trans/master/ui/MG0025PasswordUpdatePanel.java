package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * パスワード変更
 * 
 * @author liuchengcheng
 */

public class MG0025PasswordUpdatePanel extends TPanelBusiness {

	/** UID */
	private static final long serialVersionUID = 1L;

	MG0025PasswordUpdatePanelCtrl ctrl;

	/**
	 * Creates new form MG0025PasswordUpdatePane l
	 * 
	 * @param ctrl
	 */
	public MG0025PasswordUpdatePanel(MG0025PasswordUpdatePanelCtrl ctrl) {
		initComponents();
		this.ctrl = ctrl;
		super.initPanel();

	}

	/** 確定されたかどうか */
	boolean isSettle = false;

	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TMainHeaderPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnReturn = new TButton();
		pnlDetail = new TPanel();
		txtUserName = new TTextField();
		ctrlNewPassword = new TLabelPasswordField();
		ctrlNewPasswordConfirmation = new TLabelPasswordField();
		ctrlUserCode = new TLabelField();
		pnlFooter = new TPanel();

		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 320, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				isSettle = true;
				ctrl.disposeDialog();
			}
		});

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);
		btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				isSettle = false;
				ctrl.disposeDialog();
			}
		});
		btnReturn.setForClose(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(pnlButton, gridBagConstraints);

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(800, 80));
		pnlDetail.setMinimumSize(new Dimension(800, 80));
		pnlDetail.setPreferredSize(new Dimension(800, 80));

		txtUserName.setEnabled(true);
		txtUserName.setEditable(false);
		txtUserName.setMaxLength(40);
		txtUserName.setMaximumSize(new Dimension(410, 20));
		txtUserName.setMinimumSize(new Dimension(410, 20));
		txtUserName.setPreferredSize(new Dimension(410, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		pnlDetail.add(txtUserName, gridBagConstraints);

		ctrlNewPassword.setLabelSize(110);
		ctrlNewPassword.setFieldSize(120);
		ctrlNewPassword.setLangMessageID("C00742");
		ctrlNewPassword.setMaxLength(10);
		ctrlNewPassword.setTabControlNo(1);
		ctrlNewPassword.setFieldEnabled(true);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlNewPassword, gridBagConstraints);

		ctrlNewPasswordConfirmation.setLabelSize(110);
		ctrlNewPasswordConfirmation.setFieldSize(120);
		ctrlNewPasswordConfirmation.setLangMessageID("C00305");
		ctrlNewPasswordConfirmation.setMaxLength(10);
		ctrlNewPasswordConfirmation.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlNewPasswordConfirmation, gridBagConstraints);

		ctrlUserCode.setEditable(false);
		ctrlUserCode.setLabelSize(110);
		ctrlUserCode.setEnabled(true);
		ctrlUserCode.setFieldSize(120);
		ctrlUserCode.setLangMessageID("C00993");
		ctrlUserCode.setMaxLength(10);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		pnlDetail.add(ctrlUserCode, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(pnlDetail, gridBagConstraints);

		pnlFooter.setLayout(new GridBagLayout());

		pnlFooter.setMaximumSize(new Dimension(800, 480));
		pnlFooter.setMinimumSize(new Dimension(800, 480));
		pnlFooter.setPreferredSize(new Dimension(800, 480));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlFooter, gridBagConstraints);
	}

	TButton btnReturn;

	TImageButton btnSettle;

	TLabelField ctrlUserCode;

	TLabelPasswordField ctrlNewPassword;

	TLabelPasswordField ctrlNewPasswordConfirmation;

	TMainHeaderPanel pnlButton;

	TPanel pnlDetail;

	TTextField txtUserName;

	TPanel pnlFooter;

}
