package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * 下位/上位部門設定ダイアログ
 * 
 * @author liuchengcheng
 */
public class MG0050DepartmentSelectionDialog extends TDialog {

	/** 親フレーム */
	protected Frame parent;

	/** コントローラ */
	protected MG0050DepartmentHierarchyMasterPanelCtrl ctrl;

	protected Map depMap;

	/** 確定されたかどうか */
	private boolean isSettle = false;

	/** 入力された部門コード */
	private String selectedDepCode;

	/**
	 * コンストラクタ
	 * 
	 * @param _parent
	 * @param modal
	 * @param _ctrl
	 * @param isChooseChild
	 * @param _depMap
	 * @param depCode
	 * @param depName
	 * @param wordID
	 */
	public MG0050DepartmentSelectionDialog(Frame _parent, boolean modal,
		MG0050DepartmentHierarchyMasterPanelCtrl _ctrl, boolean isChooseChild, Map _depMap, String depCode,
		String depName, String wordID) {
		super(_parent, modal);

		this.ctrl = _ctrl;
		this.depMap = _depMap;

		setLangMessageID(wordID);
		this.setResizable(false);

		// 画面の初期化
		initComponents(isChooseChild);

		if (isChooseChild) {
			initUpperDepartment(depCode, depName);
			ctrlLowerDepartment.getField().requestFocus();

		} else {
			initLowerDepartment(depCode, depName);
			ctrlUpperDepartment.getField().requestFocus();
		}

		super.initDialog();
	}

	/**
	 * 画面構築
	 * 
	 * @param isChooseChild
	 */
	protected void initComponents(final boolean isChooseChild) {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnReturn = new TButton();
		pnlDetail = new TPanel();
		ctrlLowerDepartment = new TButtonField();
		ctrlUpperDepartment = new TButtonField();

		getContentPane().setLayout(new GridBagLayout());
		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(520, 40));
		pnlButton.setMinimumSize(new Dimension(520, 40));
		pnlButton.setPreferredSize(new Dimension(520, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(3);
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));

		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {

				if (Util.isNullOrEmpty(ctrlLowerDepartment.getField().getText())) {
					ctrl.showMessagePub(parent, "I00002", "C00720");
					return;

				} else if (Util.isNullOrEmpty(ctrlUpperDepartment.getField().getText())) {
					ctrl.showMessagePub(parent, "I00002", "C00719");
					ctrlUpperDepartment.getField().requestFocus();
					return;
				}

				if (isChooseChild) {
					selectedDepCode = ctrlLowerDepartment.getField().getText();
				} else {
					selectedDepCode = ctrlUpperDepartment.getField().getText();
				}

				String childDepCode = ctrlLowerDepartment.getField().getText();
				String parentDepCode = ctrlUpperDepartment.getField().getText();

				// 部門Ｐ は部門Ｃ の上位部門の場合は、部門Ｐ は部門Ｃ の下位部門になって出来ません
				if (ctrl.isParentChild(parentDepCode, childDepCode)) {
					ctrl.showMessagePub(parent, "W00154", childDepCode, parentDepCode);

					if (ctrlUpperDepartment.isEditable()) {
						ctrlUpperDepartment.getField().requestFocus();

					} else if (ctrlLowerDepartment.isEditable()) {
						ctrlLowerDepartment.getField().requestFocus();
					}

					return;
				}

				if (ctrl.checkLevelOverflow(parentDepCode, childDepCode)) {
					ctrl.showMessagePub(parent, "W00153");

					if (ctrlUpperDepartment.isEditable()) {
						ctrlUpperDepartment.getField().requestFocus();

					} else if (ctrlLowerDepartment.isEditable()) {
						ctrlLowerDepartment.getField().requestFocus();
					}

					return;
				}

				isSettle = true;
				setVisible(false);
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 192, 0, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(4);
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setForClose(true);
		btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				isSettle = false;
				setVisible(false);
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 0, 0);
		pnlButton.add(btnReturn, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(660, 55));

		pnlDetail.setMinimumSize(new Dimension(660, 55));

		pnlDetail.setPreferredSize(new Dimension(660, 55));

		ctrlLowerDepartment.setButtonSize(85);
		ctrlLowerDepartment.setFieldSize(120);
		ctrlLowerDepartment.setLangMessageID("C00720");
		ctrlLowerDepartment.setMaxLength(10);
		ctrlLowerDepartment.setNoticeSize(410);
		ctrlLowerDepartment.setTabControlNo(2);
		ctrlLowerDepartment.setImeMode(false);
		ctrlLowerDepartment.setMaxLength(10);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		pnlDetail.add(ctrlLowerDepartment, gridBagConstraints);

		ctrlUpperDepartment.setButtonSize(85);
		ctrlUpperDepartment.setFieldSize(120);
		ctrlUpperDepartment.setLangMessageID("C00719");
		ctrlUpperDepartment.setMaxLength(10);
		ctrlUpperDepartment.setNoticeSize(410);
		ctrlUpperDepartment.setTabControlNo(1);
		ctrlUpperDepartment.setImeMode(false);
		ctrlUpperDepartment.setMaxLength(10);

		pnlDetail.add(ctrlUpperDepartment, new GridBagConstraints());

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 0, 15, 0);
		getContentPane().add(pnlDetail, gridBagConstraints);

		pack();
	}

	/**
	 * 下位部門選択フィールドの構築
	 * 
	 * @param depCode 部門コード
	 * @param depName 部門名称
	 */
	private void initUpperDepartment(String depCode, String depName) {
		ctrlUpperDepartment.getField().setText(depCode);
		ctrlUpperDepartment.getField().setEditable(false);
		ctrlUpperDepartment.getNotice().setEditable(true);
		ctrlUpperDepartment.getNotice().setText(depName);
		ctrlUpperDepartment.getNotice().setEditable(false);
		ctrlUpperDepartment.getButton().setEnabled(false);

		ctrlLowerDepartment.getField().setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!ctrlLowerDepartment.isValueChanged()) {
					return true;
				}

				String code = ctrlLowerDepartment.getValue();

				if (Util.isNullOrEmpty(code)) {
					ctrlLowerDepartment.getNotice().setText("");
					return true;
				}

				MG0050DepartmentHierarchyMasterPanelCtrl.TCodeNameSNameK cnn = (MG0050DepartmentHierarchyMasterPanelCtrl.TCodeNameSNameK) depMap
					.get(code);

				boolean sts = (cnn != null);

				if (sts) {
					ctrlLowerDepartment.getNotice().setText(cnn.getName_S());

				} else {
					ctrl.showMessagePub(parent, "W00081", code);
					ctrlLowerDepartment.getField().requestFocus();
					ctrlLowerDepartment.clearOldText();
				}

				return sts;
			}
		});

		ctrlLowerDepartment.addButtonActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				MG0050DepartmentSelectionDialogREF ref = new MG0050DepartmentSelectionDialogREF(parent,
					MG0050DepartmentSelectionDialog.this, true, depMap, ctrl);

				ref.setTitle(getWord("C01687"));

				// ｺｰﾄﾞ設定、自動検索
				if (!Util.isNullOrEmpty(ctrlLowerDepartment.getValue())) {
					ref.setCode(ctrlLowerDepartment.getValue());
					ref.find(ctrlLowerDepartment.getValue(), null, null, false);
				}

				ref.setVisible(true);

				if (ref.isSettle) {
					ctrlLowerDepartment.setValue(ref.getSelectedCode());
					ctrlLowerDepartment.getNotice().setText(ref.getSelectedName_S());
				}

				ctrlLowerDepartment.getField().requestFocus();
			}
		});
	}

	/**
	 * 上位部門選択フィールドの構築
	 * 
	 * @param depCode 部門コード
	 * @param depName 部門名称
	 */
	private void initLowerDepartment(String depCode, String depName) {
		ctrlLowerDepartment.getField().setText(depCode);
		ctrlLowerDepartment.getField().setEditable(false);
		ctrlLowerDepartment.getNotice().setEditable(true);
		ctrlLowerDepartment.getNotice().setText(depName);
		ctrlLowerDepartment.getNotice().setEditable(false);
		ctrlLowerDepartment.getButton().setEnabled(false);

		ctrlUpperDepartment.getField().setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!ctrlUpperDepartment.isValueChanged()) {
					return true;
				}

				String code = ctrlUpperDepartment.getValue();

				if (Util.isNullOrEmpty(code)) {
					ctrlUpperDepartment.getNotice().setText("");
					return true;
				}

				MG0050DepartmentHierarchyMasterPanelCtrl.TCodeNameSNameK cnn = (MG0050DepartmentHierarchyMasterPanelCtrl.TCodeNameSNameK) depMap
					.get(code);

				boolean sts = (cnn != null);

				if (sts) {
					ctrlUpperDepartment.getNotice().setText(cnn.getName_S());

				} else {
					ctrl.showMessagePub(parent, "W00081", code);
					ctrlUpperDepartment.getField().requestFocus();
					ctrlUpperDepartment.clearOldText();
				}

				return sts;
			}
		});

		ctrlUpperDepartment.addButtonActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				MG0050DepartmentSelectionDialogREF ref = new MG0050DepartmentSelectionDialogREF(parent,
					MG0050DepartmentSelectionDialog.this, true, depMap, ctrl);

				String title = ctrl.getWord("C01687");
				ref.setTitle(title);

				// ｺｰﾄﾞ設定、自動検索
				if (!Util.isNullOrEmpty(ctrlUpperDepartment.getValue())) {
					ref.setCode(ctrlUpperDepartment.getValue());
					ref.find(ctrlUpperDepartment.getValue(), null, null, false);
				}

				ref.setVisible(true);

				if (ref.isSettle) {
					ctrlUpperDepartment.setValue(ref.getSelectedCode());
					ctrlUpperDepartment.getNotice().setText(ref.getSelectedName_S());
				}

				ctrlUpperDepartment.getField().requestFocus();
			}
		});
	}

	/**
	 * @return boolean
	 */
	public boolean isSettle() {
		return this.isSettle;
	}

	/**
	 * @return String
	 */
	public String getSelectedDepartmentCode() {
		return selectedDepCode;
	}

	TButton btnReturn;

	TImageButton btnSettle;

	TButtonField ctrlLowerDepartment;

	TButtonField ctrlUpperDepartment;

	TPanel pnlButton;

	TPanel pnlDetail;

}
