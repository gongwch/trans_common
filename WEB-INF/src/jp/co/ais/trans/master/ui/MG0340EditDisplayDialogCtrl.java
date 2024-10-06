package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 開示レベルマスタ
 */
public class MG0340EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 部門マスタダイアログ */
	private MG0340EditDisplayDialog dialog;

	/** 処理区分 */
	private boolean isUpdate;

	/** 判定用フラグ */
	private boolean flag = true;

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0340IndicationLevelMasterServlet";

	private REFDialogCtrl ref1;

	private REFDialogCtrl ref2;

	private REFDialogCtrl ref3;

	private REFDialogCtrl ref4;

	private Map dpkSskMap;

	private Map levelMap;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MG0340EditDisplayDialogCtrl(Frame parent, String titleid) {
		// 部門マスタダイアログの初期化
		dialog = new MG0340EditDisplayDialog(parent, true, this, titleid);

		ref1 = new REFDialogCtrl(dialog.ctrlUser, dialog.getParentFrame());
		ref1.setColumnLabels("C00589", "C00692", "C00693");
		ref1.setTargetServlet("MG0020UserMasterServlet");
		ref1.setTitleID(getWord("C02355"));
		ref1.setShowsMsgOnError(false);
		ref1.addIgnoredButton(dialog.btnReturn);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "User");
				return keys;
			}
		});

		dialog.ctrlUser.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlUser.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlUser.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlUser.getValue());
					dialog.ctrlUser.getField().clearOldText();
					dialog.ctrlUser.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		ref2 = new REFDialogCtrl(dialog.ItemSystem, dialog.getParentFrame());
		ref2.setColumnLabels("C00617", "C00619", "C00620");
		ref2.setTargetServlet("KmkTkMstServlet");
		ref2.setTitleID(getWord("C00609"));
		ref2.setShowsMsgOnError(false);
		ref2.addIgnoredButton(dialog.btnReturn);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "KmkTkMst");
				return keys;
			}
		});

		dialog.ItemSystem.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				if (!Util.isNullOrEmpty(dialog.ItemSystem.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ItemSystem.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ItemSystem.getValue());
					dialog.ItemSystem.getField().clearOldText();
					dialog.ItemSystem.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		ref3 = new REFDialogCtrl(dialog.ctrlDepartment, dialog.getParentFrame());
		ref3.setColumnLabels("C00698", "C00724", "C00725");
		ref3.setTargetServlet("MG0060DepartmentMasterServlet");
		ref3.setTitleID(getWord("C02338"));
		ref3.setShowsMsgOnError(false);
		ref3.addIgnoredButton(dialog.btnReturn);
		ref3.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("dpkSsk", getComboBoxSelectedValue(dialog.ctrlOrganizationCode.getComboBox()));
				keys.put("level", getComboBoxSelectedValue(dialog.ctrlHierarchicalLevel.getComboBox()));
				keys.put("parentDepCode", dialog.ctrlUpperDepartment.getField().getText());
				keys.put("kind", "DepartmentForMG0340");
				return keys;
			}
		});

		dialog.ctrlDepartment.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref3.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlDepartment.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlDepartment.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlDepartment.getValue());
					dialog.ctrlDepartment.getField().clearOldText();
					dialog.ctrlDepartment.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		ref4 = new REFDialogCtrl(dialog.ctrlUpperDepartment, dialog.getParentFrame());
		ref4.setColumnLabels("C00698", "C00724", "C00725");
		ref4.setTargetServlet("MG0060DepartmentMasterServlet");
		ref4.setTitleID(getWord("C02338"));
		ref4.setShowsMsgOnError(false);
		ref4.addIgnoredButton(dialog.btnReturn);
		ref4.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("dpkSsk", getComboBoxSelectedValue(dialog.ctrlOrganizationCode.getComboBox()));
				keys.put("level", getComboBoxSelectedValue(dialog.ctrlHierarchicalLevel.getComboBox()));
				keys.put("kind", "UpperDepartmentForMG0340");
				return keys;
			}

			public void goodCodeInputted() {
				if (flag) {
					dialog.ctrlDepartment.setEditable(true);
					dialog.ctrlDepartment.setButtonEnabled(true);
					flag = false;
				} else {
					if (!getWord("C00991").equals(
						dialog.ctrlHierarchicalLevel.getComboBox().getSelectedItem().toString())) {
						dialog.ctrlDepartment.setEditable(true);
						dialog.ctrlDepartment.setButtonEnabled(true);
						dialog.ctrlDepartment.setValue("");
						dialog.ctrlDepartment.setNoticeValue("");
					}
				}
			}

			public void badCodeInputted() {
				// 階層レベルが０の場合は、部門選択を出来るようにする。
				if (getWord("C00991").equals(dialog.ctrlHierarchicalLevel.getComboBox().getSelectedItem().toString())) {
					this.goodCodeInputted();
				} else {
					dialog.ctrlDepartment.setButtonEnabled(false);
					dialog.ctrlDepartment.setNoticeEditable(true);
					dialog.ctrlDepartment.setNoticeValue("");
					dialog.ctrlDepartment.setNoticeEditable(false);
					dialog.ctrlDepartment.setValue("");
					dialog.ctrlDepartment.setEditable(false);
				}
			}

			public void codeChanged() {
				dialog.ctrlDepartment.setNoticeValue("");
				dialog.ctrlDepartment.setValue("");
			}
		});

		dialog.ctrlUpperDepartment.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref4.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlUpperDepartment.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlUpperDepartment.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlUpperDepartment.getValue());
					dialog.ctrlUpperDepartment.getField().clearOldText();
					dialog.ctrlUpperDepartment.getField().requestFocus();
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

		try {
			loadDpkSskMapData();
		} catch (IOException e) {
			errorHandler(dialog, e, "E00009");
		}
		levelMap = new LinkedHashMap();
		levelMap.put("0", "C00991");
		levelMap.put("1", "C02126");
		levelMap.put("2", "C02127");
		levelMap.put("3", "C02128");
		levelMap.put("4", "C02129");
		levelMap.put("5", "C02130");
		levelMap.put("6", "C02131");
		levelMap.put("7", "C02132");
		levelMap.put("8", "C02133");
		levelMap.put("9", "C02134");
		this.translateMessageIDForMapData(levelMap);

		this.fillItemsToComboBox(dialog.ctrlOrganizationCode.getComboBox(), dpkSskMap, false);
		this.fillItemsToComboBox(dialog.ctrlHierarchicalLevel.getComboBox(), levelMap, false);

		dialog.ctrlUser.getField().requestFocus(isUpdate);

	}

	/**
	 * 表示
	 * 
	 * @param isEnabledDeptCode ユーザーコードエリア編集可(true)、付加(false)
	 */
	void show(boolean isEnabledDeptCode) {
		// 画面を表示する
		dialog.setVisible(true);
		// 部門コードの設定
		dialog.ctrlUser.setEnabled(isEnabledDeptCode);
	}

	/**
	 * 値のセット
	 * 
	 * @param map
	 */

	void setValues(Map map) {
		// ユーザーコードの設定
		dialog.ctrlUser.setValue((String) map.get("kjlUsrId"));
		// 科目体系ｺｰﾄﾞの設定
		dialog.ItemSystem.setValue((String) map.get("kjlKmtCode"));

		this.setComboBoxSelectedItem(dialog.ctrlHierarchicalLevel.getComboBox(), (String) map.get("kjlLvl"));

		this.setComboBoxSelectedItem(dialog.ctrlOrganizationCode.getComboBox(), (String) map.get("kjlDpkSsk"));

		// 部門コードの設定
		dialog.ctrlDepartment.setValue((String) map.get("kjlDepCode"));
		// 上位部門コードの設定
		dialog.ctrlUpperDepartment.setValue((String) map.get("kjlUpDepCode"));

		// 編集モードのときは部門コードが編集不可になる
		isUpdate = "update".equals(map.get("flag"));
		// 部門コードの設定

		dialog.ctrlUser.setButtonEnabled(!isUpdate);
		dialog.ctrlUser.setEditable(!isUpdate);
		dialog.ctrlUser.setNoticeEditable(!isUpdate);

		dialog.ItemSystem.setButtonEnabled(!isUpdate);
		dialog.ItemSystem.setEditable(!isUpdate);
		dialog.ItemSystem.setNoticeEditable(!isUpdate);

		dialog.ctrlOrganizationCode.setEnabled(!isUpdate);
		if (isUpdate) {
			dialog.ctrlHierarchicalLevel.getComboBox().requestFocus();
		}
		ref1.refreshName();
		ref2.refreshName();
		ref3.refreshName();
		ref4.refreshName();
	}

	boolean checkDialog() {
		try {// ユーザーコード未入力チェック
			if (Util.isNullOrEmpty(dialog.ctrlUser.getField().getText())) {
				// 警告メッセージ表示
				showMessage(dialog, "I00002", "C00528");
				dialog.ctrlUser.requestTextFocus();
				// エラーを返す
				return false;
			}

			// 部門検索名称未入力チェック
			if (Util.isNullOrEmpty(dialog.ItemSystem.getField().getText())) {
				// 警告メッセージ表示
				showMessage(dialog, "I00002", "C02048");
				dialog.ItemSystem.requestTextFocus();
				// エラーを返す
				return false;
			}

			// 組織コード未入力チェック
			if (Util.isNullOrEmpty(dialog.ctrlOrganizationCode.getComboBox().getSelectedItem().toString())) {
				// 警告メッセージ表示
				showMessage(dialog, "I00002", "C00335");
				dialog.ctrlOrganizationCode.getComboBox().requestFocus();
				// エラーを返す
				return false;
			}

			// ユーザーコードが存在していますチェック

			if (!isUpdate
				&& checkCode(dialog.ctrlUser.getField().getText(), dialog.ItemSystem.getField().getText(), this
					.getComboBoxSelectedValue(dialog.ctrlOrganizationCode.getComboBox()))) {
				// 警告メッセージ表示
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlUser.requestTextFocus();
				// エラーを返す
				return false;
			}

			// 開示レベル未入力チェック
			if (Util.isNullOrEmpty(dialog.ctrlHierarchicalLevel.getComboBox().getSelectedItem().toString())) {
				// 警告メッセージ表示
				showMessage(dialog, "I00002", "C00060");
				dialog.ctrlHierarchicalLevel.requestFocus();
				// エラーを返す
				return false;
			}

			// 上位部門コード未入力チェック
			if (dialog.ctrlUpperDepartment.getField().isEditable()
				&& Util.isNullOrEmpty(dialog.ctrlUpperDepartment.getField().getText())) {
				// 警告メッセージ表示
				showMessage(dialog, "I00002", "C01909");
				dialog.ctrlUpperDepartment.requestTextFocus();
				// エラーを返す
				return false;
			}

			// 部門コード未入力チェック
			if (dialog.ctrlDepartment.getField().isEditable()
				&& Util.isNullOrEmpty(dialog.ctrlDepartment.getField().getText())) {
				// 警告メッセージ表示
				showMessage(dialog, "I00002", "C00698");
				dialog.ctrlDepartment.requestTextFocus();
				// エラーを返す
				return false;
			}

			// 成功を返
			return true;
		} catch (IOException e) {
			ClientErrorHandler.handledException(e);
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
			dialog.setVisible(false);
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
		// ユーザーコードの設定
		map.put("kjlUsrId", dialog.ctrlUser.getValue());
		// 部門コードの設定
		map.put("kjlDepCode", dialog.ctrlDepartment.getValue());
		// 上位部門コードの設定
		map.put("kjlUpDepCode", dialog.ctrlUpperDepartment.getValue());
		// 科目体系ｺｰﾄﾞの設定
		map.put("kjlKmtCode", dialog.ItemSystem.getValue());
		// 組織コードﾞの設定
		String dpkSsk = this.getComboBoxSelectedValue(dialog.ctrlOrganizationCode.getComboBox());
		map.put("kjlDpkSsk", dpkSsk);
		// 階層レベルの設定
		String level = this.getComboBoxSelectedValue(dialog.ctrlHierarchicalLevel.getComboBox());
		// level.substring(3);
		map.put("kjlLvl", level);
		// 会社コードの設定
		map.put("kaiCode", getLoginUserCompanyCode());
		// 結果を返す
		return map;
	}

	boolean checkCode(String userCode, String itemCode, String organizationCode) throws IOException {
		// 部門コード未入力
		if (Util.isNullOrEmpty(userCode)) return false;
		if (Util.isNullOrEmpty(itemCode)) return false;
		if (Util.isNullOrEmpty(organizationCode)) return false;
		// 処理種別の設定
		addSendValues("flag", "checkcode");
		// 会社コードの設定
		addSendValues("kaiCode", getLoginUserCompanyCode());

		addSendValues("kjlUsrId", userCode);
		addSendValues("kjlKmtCode", itemCode);
		addSendValues("kjlDpkSsk", organizationCode);
		if (!request(TARGET_SERVLET)) {
			errorHandler(dialog);
			return true;
		}

		// 結果を取得
		List result = super.getResultList();
		// 結果を返す
		return (result.size() > 0);
	}

	private void loadDpkSskMapData() throws IOException {
		addSendValues("flag", "getorganizations");
		addSendValues("kaiCode", getLoginUserCompanyCode());

		if (!request("MG0050DepartmentHierarchyMasterServlet")) {
			errorHandler(dialog);
			return;
		}
		List result = super.getResultList();
		dpkSskMap = new TreeMap();
		Iterator ite = result.iterator();
		while (ite.hasNext()) {
			List inner = (List) ite.next();

			// 組織コード
			String text = (String) inner.get(1);
			dpkSskMap.put(text, text);
		}

	}

	/**
	 * 階層レベル変更 0レベルなら上位組織コード制御不能
	 */
	void checkIsValidOrgLevel() {
		boolean isSelectZero = dialog.ctrlHierarchicalLevel.getComboBox().getSelectedIndex() == 0;

		controlDeptField(!isSelectZero, isSelectZero);
	}

	/**
	 * 組織コード変更 → 階層レベル0へ
	 */
	void changeOrgCode() {
		if (dialog.ctrlHierarchicalLevel.getComboBox().getItemCount() > 0) {
			dialog.ctrlHierarchicalLevel.getComboBox().setSelectedIndex(0);
		}
	}

	/**
	 * @param isUpperEdit 赤巻紙
	 * @param isDeptEdit 気巻紙
	 */
	private void controlDeptField(boolean isUpperEdit, boolean isDeptEdit) {
		dialog.ctrlUpperDepartment.getField().setText("");
		dialog.ctrlUpperDepartment.getNotice().setText("");
		dialog.ctrlUpperDepartment.getButton().setEnabled(isUpperEdit);
		dialog.ctrlUpperDepartment.setEditable(isUpperEdit);

		dialog.ctrlDepartment.getField().setText("");
		dialog.ctrlDepartment.getNotice().setText("");
		dialog.ctrlDepartment.setEditable(isDeptEdit);
		dialog.ctrlDepartment.getButton().setEnabled(isDeptEdit);
	}
}
