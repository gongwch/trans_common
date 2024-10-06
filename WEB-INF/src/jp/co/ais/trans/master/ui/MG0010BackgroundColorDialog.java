package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

import jp.co.ais.trans.common.gui.*;

/**
 * îwåiêFê›íËDialog
 */
public class MG0010BackgroundColorDialog extends TDialog {

	/** serialVersionUID */
	private static final long serialVersionUID = 6204267609082452333L;

	protected MG0010BackgroundColorDialogCtrl ctrl;

	/**
	 * Creates new form MG0010BackgroundColorDialogaaaa
	 * 
	 * @param parent
	 * @param modal
	 * @param ctrl
	 */
	public MG0010BackgroundColorDialog(Dialog parent, boolean modal, MG0010BackgroundColorDialogCtrl ctrl) {
		super(parent, modal);
		this.ctrl = ctrl;
		this.setResizable(false);
		initComponents();
		super.initDialog();
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TPanel();
		lblColorSample = new TLabel();
		txtColorSample = new TTextField();
		btnReturn = new TButton();
		btnSettle = new TButton();
		pnlDetail = new TPanel();
		jslRed = new JSlider();
		jslGreen = new JSlider();
		jslBlue = new JSlider();
		lblRed = new TLabel();
		txtRed = new TTextField();
		txtBlue = new TTextField();
		txtGreen = new TTextField();
		lblGreen = new TLabel();
		lblBlue = new TLabel();
		lblBeginNumRed = new TLabel();
		lblEndNumBlue = new TLabel();
		lblBeginNumGreen = new TLabel();
		lblBeginNumBlue = new TLabel();
		lblEndNumRed = new TLabel();
		lblEndNumGreen = new TLabel();

		getContentPane().setLayout(new GridBagLayout());

		setLangMessageID("C00977");
		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(520, 40));
		pnlButton.setMinimumSize(new Dimension(520, 40));
		pnlButton.setPreferredSize(new Dimension(520, 40));
		lblColorSample.setLangMessageID("C00016");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		pnlButton.add(lblColorSample, gridBagConstraints);

		txtColorSample.setBorder(null);
		txtColorSample.setMaximumSize(new Dimension(75, 20));
		txtColorSample.setMinimumSize(new Dimension(75, 20));
		txtColorSample.setPreferredSize(new Dimension(75, 20));
		txtColorSample.setEditable(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		pnlButton.add(txtColorSample, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(8);
		btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.disposeDialog(false);
			}
		});
		btnReturn.setForClose(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(10, 10, 0, 0);
		pnlButton.add(btnReturn, gridBagConstraints);
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(7);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.disposeDialog(true);
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(10, 96, 0, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(500, 80));
		pnlDetail.setMinimumSize(new Dimension(500, 80));
		pnlDetail.setPreferredSize(new Dimension(500, 80));
		jslRed.setMaximumSize(new Dimension(300, 25));
		jslRed.setMaximum(255);
		jslRed.setMinimumSize(new Dimension(300, 25));
		jslRed.setPreferredSize(new Dimension(300, 25));
		jslRed.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				ctrl.setRed(jslRed.getValue());
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		pnlDetail.add(jslRed, gridBagConstraints);

		jslGreen.setMaximumSize(new Dimension(300, 25));
		jslGreen.setMaximum(255);
		jslGreen.setMinimumSize(new Dimension(300, 25));
		jslGreen.setPreferredSize(new Dimension(300, 25));
		jslGreen.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				ctrl.setGreen(jslGreen.getValue());
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		pnlDetail.add(jslGreen, gridBagConstraints);

		jslBlue.setMaximumSize(new Dimension(300, 25));
		jslBlue.setMaximum(255);
		jslBlue.setMinimumSize(new Dimension(300, 25));
		jslBlue.setPreferredSize(new Dimension(300, 25));
		jslBlue.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				ctrl.setBlue(jslBlue.getValue());
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 2;
		pnlDetail.add(jslBlue, gridBagConstraints);
		lblRed.setLangMessageID("C00003");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		pnlDetail.add(lblRed, gridBagConstraints);

		txtRed.setBackground(new Color(255, 0, 0));
		txtRed.setEnabled(false);
		txtRed.setMaxLength(3);
		txtRed.setMaximumSize(new Dimension(35, 20));
		txtRed.setMinimumSize(new Dimension(35, 20));
		txtRed.setPreferredSize(new Dimension(35, 20));
		txtRed.setTabControlNo(1);
		pnlDetail.add(txtRed, new GridBagConstraints());

		txtBlue.setBackground(new Color(0, 0, 255));
		txtBlue.setEnabled(false);
		txtBlue.setMaxLength(3);
		txtBlue.setMaximumSize(new Dimension(35, 20));
		txtBlue.setMinimumSize(new Dimension(35, 20));
		txtBlue.setPreferredSize(new Dimension(35, 20));
		txtBlue.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		pnlDetail.add(txtBlue, gridBagConstraints);

		txtGreen.setBackground(new Color(0, 255, 0));
		txtGreen.setEnabled(false);
		txtGreen.setMaxLength(3);
		txtGreen.setMaximumSize(new Dimension(35, 20));
		txtGreen.setMinimumSize(new Dimension(35, 20));
		txtGreen.setPreferredSize(new Dimension(35, 20));
		txtGreen.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		pnlDetail.add(txtGreen, gridBagConstraints);

		lblGreen.setLangMessageID("C00509");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		pnlDetail.add(lblGreen, gridBagConstraints);

		lblBlue.setLangMessageID("C00002");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		pnlDetail.add(lblBlue, gridBagConstraints);

		lblBeginNumRed.setText("0");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 20, 0, 0);
		pnlDetail.add(lblBeginNumRed, gridBagConstraints);

		lblEndNumBlue.setText("255");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 2;
		pnlDetail.add(lblEndNumBlue, gridBagConstraints);

		lblBeginNumGreen.setText("0");

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 20, 0, 0);
		pnlDetail.add(lblBeginNumGreen, gridBagConstraints);

		lblBeginNumBlue.setText("0");

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(0, 20, 0, 0);
		pnlDetail.add(lblBeginNumBlue, gridBagConstraints);

		lblEndNumRed.setText("255");

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 0;
		pnlDetail.add(lblEndNumRed, gridBagConstraints);

		lblEndNumGreen.setText("255");

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 1;
		pnlDetail.add(lblEndNumGreen, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 0, 10, 0);
		getContentPane().add(pnlDetail, gridBagConstraints);

		pack();
	}

	TButton btnReturn;

	TButton btnSettle;

	JSlider jslBlue;

	JSlider jslGreen;

	JSlider jslRed;

	TLabel lblBeginNumBlue;

	TLabel lblBeginNumGreen;

	TLabel lblBeginNumRed;

	TLabel lblBlue;

	TTextField txtColorSample;

	TLabel lblColorSample;

	TLabel lblEndNumBlue;

	TLabel lblEndNumGreen;

	TLabel lblEndNumRed;

	TLabel lblGreen;

	TLabel lblRed;

	TPanel pnlButton;

	TPanel pnlDetail;

	TTextField txtGreen;

	TTextField txtRed;

	TTextField txtBlue;

}