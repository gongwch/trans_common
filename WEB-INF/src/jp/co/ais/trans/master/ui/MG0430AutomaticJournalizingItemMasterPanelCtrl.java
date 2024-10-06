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
 * 自動仕訳科目マスタ画面コントロール
 */
public class MG0430AutomaticJournalizingItemMasterPanelCtrl extends PanelAndDialogCtrlBase {

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0430AutomaticJournalizingItemMasterServlet";
	}

	/** パネル */
	protected MG0430AutomaticJournalizingItemMasterPanel panel;

	protected Map kmkCntMap;

	protected REFDialogCtrl ref1;

	String setCode = "another";

	/**
	 * コンストラクタ.
	 */

	public MG0430AutomaticJournalizingItemMasterPanelCtrl() {
		try {
			panel = new MG0430AutomaticJournalizingItemMasterPanel(this);
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}

		panel.btnSettle.setEnabled(false);
		panel.btnCancellation.setEnabled(false);
		getKaiName();

		Map map = new TreeMap();

		map = getKmkCntName();

		kmkCntMap = new LinkedHashMap();

		kmkCntMap = map;

		this.translateMessageIDForMapData(kmkCntMap);
		this.fillItemsToComboBox(panel.ctrlItemControlDivision.getComboBox(), kmkCntMap);

		panel.ctrlCompanyCode.setValue(getLoginUserCompanyCode());
		panel.ctrlCompanyCode.setEditable(false);
		panel.ctrlCompanyCode.setEnabled(true);
		panel.ctrlAppropriateDepartment.getField().setEditable(false);
		panel.ctrlAppropriateDepartment.getNotice().setEditable(false);
		panel.ctrlAppropriateDepartment.getButton().setEnabled(false);
		panel.ctrlItemUnit.setEditMode(false);

		panel.ctrlItemControlDivision.getComboBox().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				loadData();
			}
		});

		EventQueue.invokeLater(new Runnable() {

			public void run() {
				panel.ctrlItemControlDivision.getComboBox().requestFocus();
			}
		});

		// 画面初期化
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				init();
			}
		});
	}

	/**
	 * 画面初期化
	 */
	void init() {
		ref1 = new REFDialogCtrl(panel.ctrlAppropriateDepartment, panel.getParentFrame());
		ref1.setColumnLabels("C00698", "C00724", "C00725");
		ref1.setTargetServlet("MG0060DepartmentMasterServlet");
		ref1.setTitleID(getWord("C02338"));
		ref1.setShowsMsgOnError(false);
		ref1.addIgnoredButton(panel.btnCancellation);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "Department");
				return keys;
			}
		});

		panel.ctrlAppropriateDepartment.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				if (!Util.isNullOrEmpty(panel.ctrlAppropriateDepartment.getValue().trim())
					&& Util.isNullOrEmpty(panel.ctrlAppropriateDepartment.getNotice().getText().trim())) {
					showMessage(panel, "W00081", panel.ctrlAppropriateDepartment.getValue());
					panel.ctrlAppropriateDepartment.getField().clearOldText();
					panel.ctrlAppropriateDepartment.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

	}

	/**
	 * パネル取得
	 * 
	 * @return 管理マスタパネル
	 */
	public TPanelBusiness getPanel() {
		// パネルを返す
		return this.panel;
	}

	void loadData() {
		try {
			if (getCode().equals("cancel")) {
				// 確認メッセージを表示する.
				if (!super.showConfirmMessage(panel.getParentFrame(), "Q00002", (Object) "")) {
					return;
				}
			}

			String value = panel.ctrlItemControlDivision.getComboBox().getSelectedItem().toString();

			panel.btnCancellation.setEnabled(!Util.isNullOrEmpty(value));
			panel.btnSettle.setEnabled(!Util.isNullOrEmpty(value));

			if (Util.isNullOrEmpty(this.getComboBoxSelectedValue(panel.ctrlItemControlDivision.getComboBox()))) {
				panel.ctrlAppropriateDepartment.getButton().setEnabled(false);
				panel.ctrlAppropriateDepartment.setEditable(false);
				panel.ctrlAppropriateDepartment.setValue("");
				panel.ctrlAppropriateDepartment.getNotice().setValue("");
				panel.ctrlItemUnit.setEditMode(false);
				panel.ctrlItemUnit.clearCode();
				return;
			}

			addSendValues("flag", "findone");
			addSendValues("kaiCode", super.getLoginUserCompanyCode());
			addSendValues("kmkCnt", this.getComboBoxSelectedValue(panel.ctrlItemControlDivision.getComboBox()));

			if (!request(getServletName())) {
				errorHandler(panel);
				return;
			}

			List result = super.getResultList();
			if (result != null && result.size() > 0) {
				panel.ctrlItemUnit.setEditMode(true);
				panel.ctrlItemUnit.clearCode();
				List inner = (List) result.get(0);

				String itemCode = (String) inner.get(3);
				String subItemCode = (String) inner.get(4);
				String breakDownItemCode = (String) inner.get(5);

				// 科目パラメータ
				panel.ctrlAppropriateDepartment.getButton().setEnabled(true);
				panel.ctrlAppropriateDepartment.setEditable(true);
				panel.ctrlAppropriateDepartment.setValue((String) inner.get(6));

				panel.ctrlItemUnit.setItemCode(itemCode);
				ref1.refreshName();
				panel.ctrlItemUnit.setSubItemCode(subItemCode);
				panel.ctrlItemUnit.setBreakDownItemCode(breakDownItemCode);

			} else {
				panel.ctrlItemUnit.setEditMode(true);
				panel.ctrlItemUnit.clearCode();
				panel.ctrlAppropriateDepartment.getButton().setEnabled(true);
				panel.ctrlAppropriateDepartment.setEditable(true);
				panel.ctrlAppropriateDepartment.setValue("");

				ref1.refreshName();
			}
		} catch (IOException e) {
			errorHandler(panel, e, "E00009");
		}
	}

	boolean isValid() {
		if (Util.isNullOrEmpty(panel.ctrlCompanyCode.getValue())) {
			showMessage(panel, "I00002", "C00596");
			panel.ctrlCompanyCode.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(panel.ctrlAppropriateDepartment.getValue())) {
			showMessage(panel, "I00002", "C00863");
			panel.ctrlAppropriateDepartment.requestTextFocus();
			return false;
		}

		if (panel.ctrlItemUnit.getTItemField().getField().isEditable()
			&& Util.isNullOrEmpty(panel.ctrlItemUnit.getTItemField().getValue())) {
			showMessage(panel, "I00002", "C00572");
			panel.ctrlItemUnit.getTItemField().requestTextFocus();
			return false;
		}

		if (panel.ctrlItemUnit.getTSubItemField().getField().isEditable()
			&& Util.isNullOrEmpty(panel.ctrlItemUnit.getTSubItemField().getValue())) {
			showMessage(panel, "I00002", "C00602");
			panel.ctrlItemUnit.getTSubItemField().requestTextFocus();
			return false;
		}

		if (panel.ctrlItemUnit.getTBreakDownItemField().getField().isEditable()
			&& Util.isNullOrEmpty(panel.ctrlItemUnit.getTBreakDownItemField().getValue())) {
			showMessage(panel, "I00002", "C00603");
			panel.ctrlItemUnit.getTBreakDownItemField().requestTextFocus();
			return false;
		}
		return true;
	}

	void save() {
		try {
			if (!this.isValid()) return;

			// 確認メッセージを表示する.
			if (!super.showConfirmMessage(panel.getParentFrame(), "Q00004", (Object) "")) {
				return;
			}

			addSendValues("flag", "save");
			addSendValues("kaiCode", super.getLoginUserCompanyCode());
			addSendValues("kmkCnt", this.getComboBoxSelectedValue(panel.ctrlItemControlDivision.getComboBox()));
			addSendValues("kmkCntName", ((TTextValue) panel.ctrlItemControlDivision.getComboBox().getSelectedItem())
				.getText());
			addSendValues("kmkCode", panel.ctrlItemUnit.getTItemField().getValue());
			addSendValues("hkmCode", panel.ctrlItemUnit.getTSubItemField().getValue());
			addSendValues("ukmCode", panel.ctrlItemUnit.getTBreakDownItemField().getValue());
			addSendValues("depCode", panel.ctrlAppropriateDepartment.getValue());

			if (!request(getServletName())) {
				errorHandler(panel);
			} else {
				// 完了メッセージを表示する。
				showMessage(panel.getParentFrame(), "I00008", (Object) "");
			}
		} catch (IOException e) {
			errorHandler(panel, e, "E00009");
		}
	}

	void getKaiName() {
		// 送信するパラメータを設定
		addSendValues("flag", "findone");
		addSendValues("kaiCode", getLoginUserCompanyCode());

		try {
			if (!request("MG0010EnvironmentalSettingMasterServlet")) {
				errorHandler(panel);
			}

			List result = getResultList();
			if (result != null && result.size() > 0) {
				// 会社略称
				String name_S = (String) ((List) result.get(0)).get(2);
				panel.txtCompanyName.setText(name_S);
				panel.txtCompanyName.setEditable(false);
			}
		} catch (IOException e) {
			errorHandler(panel, e, "E00009");
		}
	}

	/**
	 * 科目制御名称取得用
	 * 
	 * @return Map
	 */
	protected Map getKmkCntName() {
		// 送信するパラメータを設定
		String kaiCode = getLoginUserCompanyCode();
		addSendValues("flag", "findKmkCntName");
		addSendValues("kaiCode", kaiCode);

		try {
			if (!request(getServletName())) {
				errorHandler(panel);
			}

			Map map = new TreeMap();

			map = getResult();

			return map;
		} catch (IOException e) {
			errorHandler(panel, e, "E00009");
			return null;
		}
	}

	/**
	 * 取り消しボタンを押下したかどうか
	 * 
	 * @param setCode
	 */
	void setCode(String setCode) {
		this.setCode = setCode;
	}

	/**
	 * @return String
	 */
	public String getCode() {
		return setCode;
	}

}
