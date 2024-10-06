package jp.co.ais.trans.common.ui;

import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;

/**
 * ログイン用パネル(未使用)
 * 
 * @author chchcj
 */
public class LOGINPanel extends TPanelBusiness {

	/** コントローラ */
	private LOGINPanelCtrl ctrl;

	/**
	 * Creates new form NewJPanel
	 * 
	 * @param ctrl コントローラ
	 */
	public LOGINPanel(LOGINPanelCtrl ctrl) {
		this.ctrl = ctrl;
		initComponents();
	}

	/**
	 * 画面構築
	 */
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		pnlDetial = new TPanel();
		ctrlCompanyCode = new TLabelField();
		ctrlUserCode = new TLabelField();
		ctrlPassword = new TLabelField();
		btnCancel = new TButton();
		btnLogin = new TButton();

		setLayout(new java.awt.GridBagLayout());

		setMaximumSize(new java.awt.Dimension(300, 300));
		setMinimumSize(new java.awt.Dimension(300, 300));
		pnlDetial.setLayout(new java.awt.GridBagLayout());

		pnlDetial.setMaximumSize(new java.awt.Dimension(300, 260));
		pnlDetial.setMinimumSize(new java.awt.Dimension(300, 260));
		pnlDetial.setPreferredSize(new java.awt.Dimension(300, 260));
		ctrlCompanyCode.setFieldSize(75);
		ctrlCompanyCode.setLabelSize(55);
		ctrlCompanyCode.setLangMessageID("C00596");
		ctrlCompanyCode.setMaxLength(10);
		ctrlCompanyCode.setTabControlNo(1);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(50, 52, 5, 0);
		pnlDetial.add(ctrlCompanyCode, gridBagConstraints);

		ctrlUserCode.setFieldSize(75);
		ctrlUserCode.setLabelSize(77);
		ctrlUserCode.setLangMessageID("C00589");
		ctrlUserCode.setMaxLength(10);
		ctrlUserCode.setTabControlNo(2);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 30, 5, 0);
		pnlDetial.add(ctrlUserCode, gridBagConstraints);

		ctrlPassword.setFieldSize(75);
		ctrlPassword.setLabelSize(55);
		ctrlPassword.setLangMessageID("C00597");
		ctrlPassword.setMaxLength(10);
		ctrlPassword.setTabControlNo(3);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 52, 20, 0);
		pnlDetial.add(ctrlPassword, gridBagConstraints);

		btnCancel.setLangMessageID("C00978");
		btnCancel.setMaximumSize(new java.awt.Dimension(113, 25));
		btnCancel.setMinimumSize(new java.awt.Dimension(113, 25));
		btnCancel.setPreferredSize(new java.awt.Dimension(113, 25));
		btnCancel.setTabControlNo(5);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 0);
		pnlDetial.add(btnCancel, gridBagConstraints);

		btnLogin.setLangMessageID("C00992");
		btnLogin.setMaximumSize(new java.awt.Dimension(113, 25));
		btnLogin.setMinimumSize(new java.awt.Dimension(113, 25));
		btnLogin.setPreferredSize(new java.awt.Dimension(113, 25));
		btnLogin.setTabControlNo(4);

		btnLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.search();

			}
		});

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
		pnlDetial.add(btnLogin, gridBagConstraints);

		add(pnlDetial, new java.awt.GridBagConstraints());

	}

	TButton btnCancel;

	TButton btnLogin;

	TLabelField ctrlCompanyCode;

	TLabelField ctrlPassword;

	TLabelField ctrlUserCode;

	private TPanel pnlDetial;
}
