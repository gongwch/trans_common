package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * 開示レベルマスタ
 */
public class MG0340EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = 1676471781962376481L;

	/** コントロールクラス */
	private MG0340EditDisplayDialogCtrl ctrl;

	/** 確定されたかどうか */
	boolean isSettle = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param modal モーダルダイアログ可否(true: モーダルモード)
	 * @param ctrl コントロールクラス
	 * @param titleid
	 */
	MG0340EditDisplayDialog(Frame parent, boolean modal, MG0340EditDisplayDialogCtrl ctrl, String titleid) {
		super(parent, modal);
		this.ctrl = ctrl;
		this.setResizable(false);
		initComponents();
		setLangMessageID(titleid);
		setSize(650, 400);
		super.initDialog();
	}

	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnReturn = new TButton();
		pnlHeader = new TPanel();
		ItemSystem = new TButtonField();
		ctrlUser = new TButtonField();
		pnlOutputUnit = new TPanel();
		ctrlOrganizationCode = new TLabelComboBox();
		ctrlHierarchicalLevel = new TLabelComboBox();
		ctrlUpperDepartment = new TButtonField();
		ctrlDepartment = new TButtonField();

		ctrlOrganizationCode.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					ctrl.checkIsValidOrgLevel();
				}
			}
		});

		ctrlHierarchicalLevel.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					ctrl.checkIsValidOrgLevel();
				}
			}
		});

		getContentPane().setLayout(new GridBagLayout());
		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(650, 40));
		pnlButton.setMinimumSize(new Dimension(650, 40));
		pnlButton.setPreferredSize(new Dimension(650, 40));
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(7);
		btnSettle.addActionListener(new ActionListener() {

			/**
			 * 確定ボタン押下の処理
			 */
			public void actionPerformed(ActionEvent evt) {
				isSettle = true;
				ctrl.disposeDialog();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 330, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(8);
		btnReturn.addActionListener(new ActionListener() {

			/**
			 * 戻りボタン押下の処理
			 */
			public void actionPerformed(ActionEvent evt) {
				isSettle = false;
				ctrl.disposeDialog();
			}
		});
		btnReturn.setForClose(true);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 20, 0, 20);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());

		pnlHeader.setMaximumSize(new Dimension(650, 60));
		pnlHeader.setMinimumSize(new Dimension(650, 60));
		pnlHeader.setPreferredSize(new Dimension(650, 60));

		ItemSystem.setButtonSize(85);
		ItemSystem.setFieldSize(35);
		ItemSystem.setLangMessageID("C00609");
		ItemSystem.setMaxLength(2);
		ItemSystem.setMaximumSize(new Dimension(420, 20));
		ItemSystem.setMinimumSize(new Dimension(420, 20));
		ItemSystem.setNoticeSize(350);
		ItemSystem.setTabControlNo(2);
		ItemSystem.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		pnlHeader.add(ItemSystem, gridBagConstraints);

		ctrlUser.setButtonSize(85);
		ctrlUser.setFieldSize(120);
		ctrlUser.setLangMessageID("C00528");
		ctrlUser.setMaxLength(10);
		ctrlUser.setMaximumSize(new Dimension(420, 20));
		ctrlUser.setMinimumSize(new Dimension(420, 20));
		ctrlUser.setNoticeSize(350);
		ctrlUser.setTabControlNo(1);
		ctrlUser.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlHeader.add(ctrlUser, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		getContentPane().add(pnlHeader, gridBagConstraints);

		pnlOutputUnit.setLayout(new GridBagLayout());

		pnlOutputUnit.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlOutputUnit.setLangMessageID("C01159");
		pnlOutputUnit.setMaximumSize(new Dimension(600, 140));
		pnlOutputUnit.setMinimumSize(new Dimension(600, 140));
		pnlOutputUnit.setPreferredSize(new Dimension(600, 140));
		ctrlOrganizationCode.setLabelSize(60);
		ctrlOrganizationCode.setLangMessageID("C00335");
		ctrlOrganizationCode.setTabControlNo(3);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 25, 0, 0);
		pnlOutputUnit.add(ctrlOrganizationCode, gridBagConstraints);

		ctrlHierarchicalLevel.setLabelSize(60);
		ctrlHierarchicalLevel.setLangMessageID("C00060");
		ctrlHierarchicalLevel.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(7, 25, 0, 0);
		pnlOutputUnit.add(ctrlHierarchicalLevel, gridBagConstraints);

		ctrlUpperDepartment.setButtonSize(85);
		ctrlUpperDepartment.setFieldSize(120);
		ctrlUpperDepartment.setLangMessageID("C00719");
		ctrlUpperDepartment.setMaxLength(10);
		ctrlUpperDepartment.setMaximumSize(new Dimension(420, 20));
		ctrlUpperDepartment.setMinimumSize(new Dimension(420, 20));
		ctrlUpperDepartment.setNoticeSize(350);
		ctrlUpperDepartment.setTabControlNo(5);
		ctrlUpperDepartment.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(7, 0, 0, 0);
		pnlOutputUnit.add(ctrlUpperDepartment, gridBagConstraints);

		ctrlDepartment.setButtonSize(85);
		ctrlDepartment.setFieldSize(120);
		ctrlDepartment.setLangMessageID("C01302");
		ctrlDepartment.setMaxLength(10);
		ctrlDepartment.setMaximumSize(new Dimension(420, 20));
		ctrlDepartment.setMinimumSize(new Dimension(420, 20));
		ctrlDepartment.setNoticeSize(350);
		ctrlDepartment.setTabControlNo(6);
		ctrlDepartment.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(7, 0, 10, 0);
		pnlOutputUnit.add(ctrlDepartment, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(0, 0, 20, 0);
		getContentPane().add(pnlOutputUnit, gridBagConstraints);

		pack();
	}

	TButton btnReturn;

	TImageButton btnSettle;

	TButtonField ctrlDepartment;

	TLabelComboBox ctrlHierarchicalLevel;

	TButtonField ItemSystem;

	TLabelComboBox ctrlOrganizationCode;

	TButtonField ctrlUpperDepartment;

	TButtonField ctrlUser;

	TPanel pnlButton;

	TPanel pnlHeader;

	TPanel pnlOutputUnit;

}
