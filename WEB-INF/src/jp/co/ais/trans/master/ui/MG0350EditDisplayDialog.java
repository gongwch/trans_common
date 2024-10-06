package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 会社間付替マスタ
 */
public class MG0350EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -6040386552135663169L;

	/** コントロールクラス */
	private MG0350EditDisplayDialogCtrl ctrl;

	/** 確定されたかどうか */
	boolean isSettle = false;

	/**
	 * @param parent
	 * @param modal
	 * @param ctrl
	 * @param titleid
	 */
	public MG0350EditDisplayDialog(Frame parent, boolean modal, MG0350EditDisplayDialogCtrl ctrl, String titleid) {
		// 親フレームを設定
		super(parent, modal);
		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);
		// 画面の初期化
		initComponents();
		setLangMessageID(titleid);
		// 画面の設定
		setSize(680, 350);
		super.initDialog();
	}

	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TPanel();
		btnSettle = new TButton();
		btnReturn = new TButton();
		pnlDetail = new TPanel();
		ctrlTransferAppropriateDepartment = new TButtonField();
		ctrlTransferCompany = new TButtonField();
		ctrlItemUnit = new TAccountItemUnit();

		getContentPane().setLayout(new GridBagLayout());

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(450, 40));
		pnlButton.setMinimumSize(new Dimension(450, 40));
		pnlButton.setPreferredSize(new Dimension(450, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints.anchor = GridBagConstraints.SOUTH;
		gridBagConstraints.insets = new Insets(15, 0, 5, 10);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				// 開ける種別の設定
				isSettle = true;
				// 画面を閉める
				ctrl.disposeDialog();
			}
		});
		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(15, 0, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);
		btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				// 開ける種別の設定
				isSettle = false;
				// 画面を閉める
				ctrl.disposeDialog();
			}
		});
		btnReturn.setForClose(true);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 140, 0, 10);
		getContentPane().add(pnlButton, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 415, 0, 10);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(660, 155));
		pnlDetail.setMinimumSize(new Dimension(660, 155));
		pnlDetail.setPreferredSize(new Dimension(660, 155));
		ctrlItemUnit.setTabControlNo(3);
		ctrlItemUnit.getTItemField().setLangMessageID("C00848");
		ctrlItemUnit.getTSubItemField().setLangMessageID("C00849");
		ctrlItemUnit.getTBreakDownItemField().setLangMessageID("C00850");
		ctrlItemUnit.getTItemField().setImeMode(false);
		ctrlItemUnit.getTSubItemField().setImeMode(false);
		ctrlItemUnit.getTBreakDownItemField().setImeMode(false);
		ctrlItemUnit.getTItemField().setMaxLength(10);
		ctrlItemUnit.getTSubItemField().setMaxLength(10);
		ctrlItemUnit.getTBreakDownItemField().setMaxLength(10);
		ctrlItemUnit.getTItemField().setButtonSize(100);
		ctrlItemUnit.getTSubItemField().setButtonSize(100);
		ctrlItemUnit.getTBreakDownItemField().setButtonSize(100);
		ctrlItemUnit.getTItemField().setFieldSize(120);
		ctrlItemUnit.getTSubItemField().setFieldSize(120);
		ctrlItemUnit.getTBreakDownItemField().setFieldSize(120);
		ctrlItemUnit.getTItemField().setNoticeSize(410);
		ctrlItemUnit.getTSubItemField().setNoticeSize(410);
		ctrlItemUnit.getTBreakDownItemField().setNoticeSize(410);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlItemUnit, gridBagConstraints);

		ctrlTransferAppropriateDepartment.setButtonSize(100);
		ctrlTransferAppropriateDepartment.setFieldSize(120);
		ctrlTransferAppropriateDepartment.setLangMessageID("C00847");
		ctrlTransferAppropriateDepartment.setMaxLength(10);
		ctrlTransferAppropriateDepartment.setMaximumSize(new Dimension(420, 20));
		ctrlTransferAppropriateDepartment.setMinimumSize(new Dimension(420, 20));
		ctrlTransferAppropriateDepartment.setNoticeSize(410);
		ctrlTransferAppropriateDepartment.setTabControlNo(2);
		ctrlTransferAppropriateDepartment.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(25, 10, 0, 10);
		pnlDetail.add(ctrlTransferAppropriateDepartment, gridBagConstraints);

		ctrlTransferCompany.setButtonSize(100);
		ctrlTransferCompany.setFieldSize(120);
		ctrlTransferCompany.setLangMessageID("C00846");
		ctrlTransferCompany.setMaxLength(10);
		ctrlTransferCompany.setMaximumSize(new Dimension(420, 20));
		ctrlTransferCompany.setMinimumSize(new Dimension(420, 20));
		ctrlTransferCompany.setNoticeSize(410);
		ctrlTransferCompany.setTabControlNo(1);
		ctrlTransferCompany.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(5, 10, 0, 10);
		pnlDetail.add(ctrlTransferCompany, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 0, 20, 0);
		getContentPane().add(pnlDetail, gridBagConstraints);

		pack();

		// 「科目」ボタンを押下
		ctrlItemUnit.getTItemField().addCallControl(new CallBackListener() {

			public void before() {
				ctrl.setItemData();
			}
		});

		// 「補助科目」ボタンを押下
		ctrlItemUnit.getTSubItemField().addCallControl(new CallBackListener() {

			public void before() {
				ctrl.setSubItemData();
			}
		});

		// 「内訳科目」ボタンを押下
		ctrlItemUnit.getTBreakDownItemField().addCallControl(new CallBackListener() {

			public void before() {
				ctrl.setBreakDownItemData();
			}
		});
	}

	TButton btnReturn;

	TButton btnSettle;

	TButtonField ctrlTransferAppropriateDepartment;

	TButtonField ctrlTransferCompany;

	TPanel pnlButton;

	TPanel pnlDetail;

	TAccountItemUnit ctrlItemUnit;

}
