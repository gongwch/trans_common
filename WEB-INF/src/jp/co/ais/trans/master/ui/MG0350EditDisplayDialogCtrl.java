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
 * 管理マスタダイアログ コントロール
 */
public class MG0350EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 管理マスタダイアログ */
	private MG0350EditDisplayDialog dialog;

	/** 処理区分 */
	private boolean isUpdate;

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0350InterCompanyTransferMasterServlet";

	private REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MG0350EditDisplayDialogCtrl(Frame parent, String titleid) {
		// 管理マスタダイアログの初期化
		dialog = new MG0350EditDisplayDialog(parent, true, this, titleid);

		dialog.ctrlItemUnit.getTSubItemField().setEditable(false);
		dialog.ctrlItemUnit.getTSubItemField().setButtonEnabled(false);

		dialog.ctrlItemUnit.getTSubItemField().setEditable(false);
		dialog.ctrlItemUnit.getTSubItemField().setButtonEnabled(false);
		// 付替会社の設定
		ref1 = new REFDialogCtrl(dialog.ctrlTransferCompany, dialog.getParentFrame());
		ref1.setColumnLabels("C00596", "C00686", null);
		ref1.setTargetServlet("MG0010EnvironmentalSettingMasterServlet");
		ref1.setTitleID(getWord("C00053"));
		ref1.setShowsMsgOnError(false);
		ref1.addIgnoredButton(dialog.btnReturn);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "EnvironmentalSetting");
				return keys;
			}
		});

		dialog.ctrlTransferCompany.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlTransferCompany.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlTransferCompany.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlTransferCompany.getValue());
					dialog.ctrlTransferCompany.getField().clearOldText();
					dialog.ctrlTransferCompany.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		// 付替計上部門の設定
		ref2 = new REFDialogCtrl(dialog.ctrlTransferAppropriateDepartment, dialog.getParentFrame());
		ref2.setColumnLabels("C00698", "C00724", "C00725");
		ref2.setTargetServlet("MG0060DepartmentMasterServlet");
		ref2.setTitleID(getWord("C02338"));
		ref2.setShowsMsgOnError(false);
		ref2.addIgnoredButton(dialog.btnReturn);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "Department");
				return keys;
			}
		});

		dialog.ctrlTransferAppropriateDepartment.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlTransferAppropriateDepartment.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlTransferAppropriateDepartment.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlTransferAppropriateDepartment.getValue());
					dialog.ctrlTransferAppropriateDepartment.getField().clearOldText();
					dialog.ctrlTransferAppropriateDepartment.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		// fix bug: 確定ボタン押下して UI チェックが失敗の場合は、
		// ダイアログを閉じ時、dialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});
		dialog.ctrlTransferCompany.getField().requestFocus();

	}

	/**
	 * (科目)
	 */
	public void setItemData() {
		// 会社コードsetSummaryDivision
		dialog.ctrlItemUnit.getTItemField().getInputParameter().setSummaryDivision("0");
	}

	/**
	 * (補助科目)
	 */
	public void setSubItemData() {
		// 会社コード
		dialog.ctrlItemUnit.getTSubItemField().getInputParameter().setItemCode(
			dialog.ctrlItemUnit.getTItemField().getValue());
	}

	/**
	 * (内訳科目)
	 */
	public void setBreakDownItemData() {
		// 会社コード
		dialog.ctrlItemUnit.getTBreakDownItemField().getInputParameter().setItemCode(
			dialog.ctrlItemUnit.getTItemField().getValue());
		dialog.ctrlItemUnit.getTBreakDownItemField().getInputParameter().setSubItemCode(
			dialog.ctrlItemUnit.getTSubItemField().getValue());
	}

	/**
	 * 表示
	 * 
	 * @param isEnabledknrCode 付替会社エリア編集可(true)、付加(false)
	 */
	void show(boolean isEnabledknrCode) {
		// 画面を表示する
		dialog.setVisible(true);
		// 部門コードの設定
		dialog.ctrlTransferCompany.setEnabled(isEnabledknrCode);
	}

	/**
	 * 値のセット
	 * 
	 * @param map
	 */
	void setValues(Map map) {

		String itemCode = Util.avoidNull(map.get("ktkKmkCode"));
		String subItemCode = Util.avoidNull(map.get("ktkHkmCode"));
		String breakDownItemCode = Util.avoidNull(map.get("ktkUkmCode"));

		// 科目パラメータ
		dialog.ctrlItemUnit.getInputParameter().setItemCode(itemCode);
		dialog.ctrlItemUnit.getInputParameter().setSubItemCode(subItemCode);
		dialog.ctrlItemUnit.getInputParameter().setBreakDownItemCode(breakDownItemCode);

		// 科目
		dialog.ctrlItemUnit.setItemCode(itemCode);

		// 補助科目
		dialog.ctrlItemUnit.setSubItemCode(subItemCode);

		// 内訳科目
		dialog.ctrlItemUnit.setBreakDownItemCode(breakDownItemCode);

		dialog.ctrlTransferCompany.setValue((String) map.get("ktkKaiCode"));
		if (!("".equals(map.get("ktkKaiCode")))) {
			dialog.ctrlTransferCompany.setEditable(false);
			dialog.ctrlTransferCompany.setButtonEnabled(false);
		}
		dialog.ctrlTransferAppropriateDepartment.setValue((String) map.get("ktkDepCode"));

		// 編集モードのときは付替会社コードが編集不可になる
		isUpdate = "update".equals(map.get("flag"));

		// 会社コードの設定
		dialog.ctrlTransferCompany.setEnabled(true);
		dialog.ctrlTransferCompany.setEditable(!isUpdate);

		ref1.refreshName();
		ref2.refreshName();

		if (isUpdate) {
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					dialog.ctrlTransferAppropriateDepartment.getField().requestFocus();
					dialog.ctrlTransferCompany.setEnabled(false);
					dialog.ctrlTransferCompany.setEditable(false);
				}
			});
		} else {
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					dialog.ctrlTransferCompany.getField().requestFocus();
				}
			});
		}
	}

	boolean checkDialog() {
		try {// 付替会社チェック
			if (Util.isNullOrEmpty(dialog.ctrlTransferCompany.getValue())) {
				showMessage(dialog, "I00002", "C00846");
				dialog.ctrlTransferCompany.requestTextFocus();
				return false;
			}
			// 付替会社チェック

			if (!isUpdate && checkCode(dialog.ctrlTransferCompany.getField().getText())) {
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlTransferCompany.requestTextFocus();
				return false;
			}

			// 付替計上部門チェック
			if (Util.isNullOrEmpty(dialog.ctrlTransferAppropriateDepartment.getValue())) {
				showMessage(dialog, "I00002", "C00847");
				dialog.ctrlTransferAppropriateDepartment.requestTextFocus();
				return false;
			}
			// 付替科目チェック
			if (Util.isNullOrEmpty(dialog.ctrlItemUnit.getTItemField().getValue())) {
				showMessage(dialog, "I00002", "C02052");
				dialog.ctrlItemUnit.getTItemField().requestTextFocus();
				return false;
			}

			// 付替補助科目チェック
			if (dialog.ctrlItemUnit.getTSubItemField().getField().isEditable()
				&& Util.isNullOrEmpty(dialog.ctrlItemUnit.getTSubItemField().getValue())) {
				showMessage(dialog, "I00002", "C02053");
				dialog.ctrlItemUnit.getTSubItemField().requestTextFocus();
				return false;
			}

			// 内訳科目チェック
			if (dialog.ctrlItemUnit.getTSubItemField().getField().isEditable()
				&& Util.isNullOrEmpty(dialog.ctrlItemUnit.getTSubItemField().getValue())) {
				showMessage(dialog, "I00002", "C02054");
				dialog.ctrlItemUnit.getTSubItemField().requestTextFocus();
				return false;
			}
			return true;
		} catch (IOException e) {
			errorHandler(dialog, e, "E00009");
			return false;
		}
	}

	/**
	 * 閉じる
	 */
	void disposeDialog() {

		if (dialog.isSettle) {
			// 確定ボタン押下 チェックOKなら閉じる
			if (!this.checkDialog()) return;
			dialog.setVisible(!this.checkDialog());

		} else {
			// 画面を閉める
			dialog.setVisible(false);// 閉じる
		}
	}

	/**
	 * 確定ボタンが押されたかどうか
	 * 
	 * @return 確定の場合true
	 */
	boolean isSettle() {
		// 処理種別の設定
		return dialog.isSettle;
	}

	/**
	 * 画面上表示データの取得
	 * 
	 * @return データ
	 */
	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();

		map.put("ktkKaiCode", dialog.ctrlTransferCompany.getValue());
		map.put("ktkDepCode", dialog.ctrlTransferAppropriateDepartment.getValue());
		map.put("ktkKmkCode", dialog.ctrlItemUnit.getTItemField().getValue());
		map.put("ktkHkmCode", dialog.ctrlItemUnit.getTSubItemField().getValue());
		map.put("ktkUkmCode", dialog.ctrlItemUnit.getTBreakDownItemField().getValue());
		map.put("kaiCode", getLoginUserCompanyCode());
		// 結果を返す
		return map;
	}

	boolean checkCode(String code) throws IOException {
		if (Util.isNullOrEmpty(code)) {
			return false;
		}
		// 処理種別の設定
		addSendValues("flag", "checkcode");
		addSendValues("kaiCode", getLoginUserCompanyCode());
		addSendValues("ktkKaiCode", code);

		if (!request(TARGET_SERVLET)) {
			errorHandler(dialog);
			return false;
		}

		// 結果を取得
		List result = super.getResultList();
		// 結果を返す
		return (result.size() > 0);
	}
}
