package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 部門階層マスタ
 */
public class MG0050NewOrganizationDialogCtrl extends PanelAndDialogCtrlBase {

	protected Frame parent;

	/** プログラムID */
	protected static final String PROGRAM_ID = "MG0050";

	protected MG0050NewOrganizationDialog dialog;

	protected String TARGET_SERVLET = "MG0050DepartmentHierarchyMasterServlet";

	protected REFDialogCtrl ref1;

	/**
	 * @param parent
	 */
	public MG0050NewOrganizationDialogCtrl(Frame parent) {
		this.parent = parent;
		dialog = new MG0050NewOrganizationDialog(parent, true, this);

		// fix bug: 確定ボタン押下して UI チェックが失敗の場合は、
		// ダイアログを閉じ時、dialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});

		init();
	}

	/**
	 * ダイアログ初期化
	 */
	private void init() {
		dialog.ctrlOrganizationCode.setValue("");
		dialog.ctrlLevel0.setValue("");
		dialog.ctrlLevel0.getNotice().setEditable(true);
		dialog.ctrlLevel0.getNotice().setText("");
		dialog.ctrlLevel0.getNotice().setEditable(false);

		// 部門REF
		ref1 = new REFDialogCtrl(dialog.ctrlLevel0, dialog.getParentFrame());
		ref1.setColumnLabels("C00698", "C00724", "C00725");
		ref1.setTargetServlet("MG0060DepartmentMasterServlet");
		ref1.setTitleID("C02338");
		ref1.setShowsMsgOnError(false);
		ref1.addIgnoredButton(dialog.btnReturn);

		dialog.ctrlLevel0.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlLevel0.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlLevel0.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlLevel0.getValue());
					dialog.ctrlLevel0.getField().clearOldText();
					dialog.ctrlLevel0.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map map = new HashMap();
				map.put("kaiCode", getLoginUserCompanyCode());
				map.put("kind", "DepartmentAll");
				return map;
			}
		});

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				dialog.ctrlOrganizationCode.requestFocus();
			}
		});

	}

	/**
	 * @return boolean
	 */
	public boolean isSettle() {
		return dialog.isSettle();
	}

	/**
	 * 
	 */
	public void show() {
		dialog.setVisible(true);
	}

	/**
	 * 
	 */
	public void disposeDialog() {
		try {
			if (isSettle()) {
				if (checkDialog()) {
					if (this.showConfirmMessage(dialog, "Q00005", "")) {
						addSendValues("flag", "createorganization");
						addSendValues("kaiCode", super.getLoginUserCompanyCode());
						addSendValues("dpkSsk", dialog.ctrlOrganizationCode.getField().getText());
						addSendValues("dpkDepCode", dialog.ctrlLevel0.getField().getText());

						// プログラムＩＤの設定
						super.addSendValues("prgID", PROGRAM_ID);

						if (!request(TARGET_SERVLET)) {
							errorHandler(dialog);
							return;
						}

						dialog.setVisible(false);
					}
				}
			} else {
				dialog.setVisible(false);
			}
		} catch (IOException e) {
			errorHandler(dialog, e, "E00009");
		}
	}

	/**
	 * @return boolean
	 */
	boolean checkCode() {
		addSendValues("flag", "checksskcode");
		addSendValues("kaiCode", super.getLoginUserCompanyCode());
		addSendValues("dpkSsk", dialog.ctrlOrganizationCode.getValue());

		try {
			if (!request(TARGET_SERVLET)) {
				errorHandler(dialog);
				return true;
			}

			// 結果を取得
			List result = super.getResultList();
			// 結果を返す
			return (result.size() > 0);
		} catch (IOException e) {
			errorHandler(dialog, e, "E00009");
			return false;
		}
	}

	private boolean checkDialog() {

		if (Util.isNullOrEmpty(dialog.ctrlOrganizationCode.getField().getText())) {
			showMessage(dialog, "I00002", "C00335");
			dialog.ctrlOrganizationCode.requestFocus();
			return false;
		}

		String code = dialog.ctrlOrganizationCode.getField().getText();
		if (!Util.isNullOrEmpty(code) && checkCode()) {
			showMessage(dialog, "W00043", "");
			dialog.ctrlOrganizationCode.requestFocus();
			return false;
		}

		if (Util.isNullOrEmpty(dialog.ctrlLevel0.getField().getText())) {
			showMessage(dialog, "I00002", "C00722");
			dialog.ctrlLevel0.requestTextFocus();
			return false;
		}
		return true;
	}
}
