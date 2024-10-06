package jp.co.ais.trans.common.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;

/**
 * パスワード変更ダイアログ
 */
public class PasswordUpdateDialog extends TDialog {

	/** UID */
	private static final long serialVersionUID = 1L;

	/** コントロール */
	private PasswordUpdateDialogCtrl ctrl;

	/** 確定されたかどうか */
	boolean isSettle = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param ctrl コントロール
	 */
	public PasswordUpdateDialog(Frame parent, PasswordUpdateDialogCtrl ctrl) {
		super(parent, true);
		this.ctrl = ctrl;

		setLangMessageID("C02318");

		setSize(500, 225);
		initComponents();

		initDialog();
	}

	/**
	 * 画面構築
	 */
	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		btnSettle = new TButton();
		btnReturn = new TButton();
		pnlDetail = new TPanel();
		pnlButton = new TPanel();
		pnlFooter = new TPanel();
		txtUserName = new TTextField();
		ctrlNewPassword = new TLabelPasswordField();
		ctrlNewPasswordConfirmation = new TLabelPasswordField();
		ctrlUserCode = new TLabelField();
		setLayout(new GridBagLayout());

		pnlDetail.setLayout(new GridBagLayout());
		pnlDetail.setMaximumSize(new Dimension(500, 225));
		pnlDetail.setMinimumSize(new Dimension(500, 225));
		pnlDetail.setPreferredSize(new Dimension(500, 225));

		pnlButton.setLayout(new GridBagLayout());
		pnlButton.setMaximumSize(new Dimension(480, 30));
		pnlButton.setMinimumSize(new Dimension(480, 30));
		pnlButton.setPreferredSize(new Dimension(480, 30));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		pnlDetail.add(pnlButton, gridBagConstraints);

		pnlFooter.setLayout(new GridBagLayout());
		pnlFooter.setMaximumSize(new Dimension(500, 150));
		pnlFooter.setMinimumSize(new Dimension(500, 150));
		pnlFooter.setPreferredSize(new Dimension(500, 150));

		gridBagConstraints = new GridBagConstraints();
		btnSettle.setLayout(new GridBagLayout());
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(100, 25));
		btnSettle.setMinimumSize(new Dimension(100, 25));
		btnSettle.setPreferredSize(new Dimension(100, 25));
		btnSettle.setTabControlNo(3);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 240, 0, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				isSettle = true;
				ctrl.disposeDialog();
			}
		});

		btnReturn.setLayout(new GridBagLayout());
		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setTabControlNo(4);
		btnReturn.setMaximumSize(new Dimension(100, 25));
		btnReturn.setMinimumSize(new Dimension(100, 25));
		btnReturn.setPreferredSize(new Dimension(100, 25));
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		pnlButton.add(btnReturn, gridBagConstraints);

		btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				isSettle = false;
				ctrl.disposeDialog();
			}
		});

		txtUserName.setLayout(new GridBagLayout());
		txtUserName.setEnabled(true);
		txtUserName.setEditable(false);
		txtUserName.setMaxLength(20);
		txtUserName.setMaximumSize(new Dimension(260, 20));
		txtUserName.setMinimumSize(new Dimension(260, 20));
		txtUserName.setPreferredSize(new Dimension(260, 20));
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 10, 25);
		pnlFooter.add(txtUserName, gridBagConstraints);

		ctrlNewPassword.setLayout(new GridBagLayout());
		ctrlNewPassword.setFieldSize(105);
		ctrlNewPassword.setLabelSize(105);
		ctrlNewPassword.setLangMessageID("C00742");
		ctrlNewPassword.setMaxLength(10);
		ctrlNewPassword.setTabControlNo(1);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		pnlFooter.add(ctrlNewPassword, gridBagConstraints);

		ctrlNewPasswordConfirmation.setLayout(new GridBagLayout());
		ctrlNewPasswordConfirmation.setFieldSize(105);
		ctrlNewPasswordConfirmation.setLabelSize(105);
		ctrlNewPasswordConfirmation.setLangMessageID("C00305");
		ctrlNewPasswordConfirmation.setMaxLength(10);
		ctrlNewPasswordConfirmation.setTabControlNo(2);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(0, 0, 25, 0);
		pnlFooter.add(ctrlNewPasswordConfirmation, gridBagConstraints);

		ctrlUserCode.setLayout(new GridBagLayout());
		ctrlUserCode.setEditable(false);
		ctrlUserCode.setEnabled(true);
		ctrlUserCode.setFieldSize(105);
		ctrlUserCode.setLabelSize(105);
		ctrlUserCode.setLangMessageID("C00993");
		ctrlUserCode.setMaxLength(10);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		pnlFooter.add(ctrlUserCode, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		pnlDetail.add(pnlFooter, gridBagConstraints);

		getContentPane().add(pnlDetail, gridBagConstraints);
	}

	TButton btnReturn;

	TButton btnSettle;

	TPanel pnlButton;

	TPanel pnlFooter;

	TLabelField ctrlUserCode;

	TLabelPasswordField ctrlNewPassword;

	TLabelPasswordField ctrlNewPasswordConfirmation;

	TPanel pnlDetail;

	TTextField txtUserName;
}
