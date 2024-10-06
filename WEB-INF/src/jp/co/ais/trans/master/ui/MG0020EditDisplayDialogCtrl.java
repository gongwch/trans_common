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
 * ユーザーマスタDialogCtrl
 */
public class MG0020EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** ユーザーマスタDialog */
	protected MG0020EditDisplayDialog dialog;

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0020UserMasterServlet";
	}

	protected boolean isUpdate = false;

	protected REFDialogCtrl ref1;

	protected REFDialogCtrl ref2;

	protected REFDialogCtrl ref3;

	protected REFDialogCtrl ref4;

	/** old会社コード */
	private String oldCmpCode = "";

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MG0020EditDisplayDialogCtrl(Frame parent, String titleid) {
		dialog = new MG0020EditDisplayDialog(parent, true, this, titleid);
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});

		init();

		dialog.rdoAccountingPersonExcluding.setSelected(true);
		dialog.ctrlDepartmentCode.getField().setEditable(false);
		dialog.ctrlDepartmentCode.getButton().setEnabled(false);

		dialog.ctrlEmployeeCode.getField().setEditable(false);
		dialog.ctrlEmployeeCode.getButton().setEnabled(false);

		dialog.ctrlLanguageCode.getField().setEditable(false);
		dialog.ctrlLanguageCode.getButton().setEnabled(false);
		dialog.ctrlUserCode.getField().requestFocus();
		dialog.dtBeginDate.setValue(DateUtil.getDate(1900, 1, 1));
		dialog.dtEndDate.setValue(DateUtil.getDate(2099, 12, 31));

	}

	private Map updKenMap;

	protected void init() {

		/**
		 * 環境設定（会社） keys:なし
		 */
		ref1 = new REFDialogCtrl(dialog.ctrlCompanyCode, dialog.getParentFrame());
		ref1.setTargetServlet("MG0010EnvironmentalSettingMasterServlet");
		ref1.setTitleID("C00053");
		ref1.setColumnLabelIDs("C00596", "C00686", null);
		ref1.setShowsMsgOnError(false);
		ref1.addIgnoredButton(dialog.btnReturn);
		ref1.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 会社コードの設定

				keys.put("kind", "EnvironmentalSetting");
				keys.put("kaiCode", dialog.ctrlCompanyCode.getField().getText());
				return keys;
			}

			public void goodCodeInputted() {
				dialog.ctrlEmployeeCode.getField().setEditableEnabled(true);
				dialog.ctrlEmployeeCode.getButton().setEnabled(true);

				dialog.ctrlDepartmentCode.getField().setEditableEnabled(true);
				dialog.ctrlDepartmentCode.getButton().setEnabled(true);

				dialog.ctrlLanguageCode.getField().setEditableEnabled(true);
				dialog.ctrlLanguageCode.getButton().setEnabled(true);

				codeChanged();
			}

			public void badCodeInputted() {

				dialog.ctrlEmployeeCode.getField().setEditableEnabled(false);
				dialog.ctrlEmployeeCode.getButton().setEnabled(false);

				dialog.ctrlDepartmentCode.getField().setEditableEnabled(false);
				dialog.ctrlDepartmentCode.getButton().setEnabled(false);

				dialog.ctrlLanguageCode.getField().setEditableEnabled(false);
				dialog.ctrlLanguageCode.getButton().setEnabled(false);

				codeChanged();
			}

			public void codeChanged() {
				dialog.ctrlEmployeeCode.getField().setText(null);
				dialog.ctrlEmployeeCode.getNotice().setText(null);

				dialog.ctrlDepartmentCode.getField().setText(null);
				dialog.ctrlDepartmentCode.getNotice().setText(null);

				dialog.ctrlLanguageCode.getField().setText(null);
				dialog.ctrlLanguageCode.getNotice().setText(null);
			}
		}

		);

		dialog.ctrlCompanyCode.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlCompanyCode.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlCompanyCode.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlCompanyCode.getValue());
					dialog.ctrlCompanyCode.getField().clearOldText();
					dialog.ctrlCompanyCode.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		ref2 = new REFDialogCtrl(dialog.ctrlEmployeeCode, dialog.getParentFrame());
		ref2.setTargetServlet("MG0160EmployeeMasterServlet");
		ref2.setTitleID(getWord("C00913"));
		ref2.setColumnLabelIDs("C00697", "C00808", "C00809");
		ref2.setShowsMsgOnError(false);
		ref2.addIgnoredButton(dialog.btnReturn);
		ref2.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 会社コードの設定

				keys.put("kind", "Employee");
				keys.put("kaiCode", dialog.ctrlCompanyCode.getField().getText());
				return keys;
			}
		});

		dialog.ctrlEmployeeCode.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlEmployeeCode.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlEmployeeCode.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlEmployeeCode.getValue());
					dialog.ctrlEmployeeCode.getField().clearOldText();
					dialog.ctrlEmployeeCode.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		/**
		 * 部門 keys:会社コード
		 */
		ref3 = new REFDialogCtrl(dialog.ctrlDepartmentCode, dialog.getParentFrame());
		ref3.setTargetServlet("MG0060DepartmentMasterServlet");
		ref3.setTitleID(getWord("C02338"));
		ref3.setColumnLabelIDs("C00698", "C00724", "C00725");
		ref3.setShowsMsgOnError(false);
		ref3.addIgnoredButton(dialog.btnReturn);
		ref3.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 会社コードの設定

				keys.put("kind", "Department");
				keys.put("kaiCode", dialog.ctrlCompanyCode.getField().getText());

				return keys;
			}
		});

		dialog.ctrlDepartmentCode.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref3.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlDepartmentCode.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlDepartmentCode.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlDepartmentCode.getValue());
					dialog.ctrlDepartmentCode.getField().clearOldText();
					dialog.ctrlDepartmentCode.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

		/**
		 * 言語 keys:会社コード
		 */
		ref4 = new REFDialogCtrl(dialog.ctrlLanguageCode, dialog.getParentFrame());
		ref4.setTargetServlet("LanguageMasterServlet");
		ref4.setTitleID("C00153");
		ref4.setColumnLabelIDs("C00699", null, null);
		ref4.setShowsMsgOnError(false);
		ref4.addIgnoredButton(dialog.btnReturn);
		ref4.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 会社コードの設定

				keys.put("kind", "Language");
				keys.put("kaiCode", dialog.ctrlCompanyCode.getField().getText());
				return keys;
			}
		});

		dialog.ctrlLanguageCode.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				ref4.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlLanguageCode.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlLanguageCode.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlLanguageCode.getValue());
					dialog.ctrlLanguageCode.getField().clearOldText();
					dialog.ctrlLanguageCode.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

	}

	/**
	 * @param map
	 */
	public void setUpdKenMap(Map map) {
		this.updKenMap = map;

		this.fillItemsToComboBox(dialog.ctrlUpdateAuthorityLevel.getComboBox(), updKenMap, false);
	}

	/**
	 * 表示
	 */
	void show() {
		dialog.setVisible(true);
	}

	/**
	 * 値のセット
	 * 
	 * @param map セットする値
	 */

	void setValues(Map map) {

		boolean boo;
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("keiRiKbn")));
		dialog.rdoAccountingPersonExcluding.setSelected(!boo);
		dialog.rdoAccountingPerson.setSelected(boo);

		this.setComboBoxSelectedItem(dialog.ctrlUpdateAuthorityLevel.getComboBox(), this.getWord((String) map
			.get("updKen")));

		dialog.ctrlUserCode.setValue((String) map.get("usrCode"));
		dialog.ctrlUserName.setValue((String) map.get("usrName"));
		dialog.ctrlUserAbbreviatedName.setValue((String) map.get("usrName_S"));
		dialog.txtUserNameForSearch.setValue((String) map.get("usrName_K"));
		dialog.ctrlPassword.setValue((String) map.get("password"));
		dialog.numProcessingAuthorityLevel.setValue((String) map.get("prcKen"));
		dialog.ctrlCompanyCode.setValue((String) map.get("kaiCode"));

		String ss = ((String) map.get("sysKbn")).substring(0, 1);
		boolean mm = BooleanUtil.toBoolean(Integer.parseInt(ss));
		dialog.chkBasicAccounting.setSelected(mm);

		String ss1 = ((String) map.get("sysKbn")).substring(1, 2);
		boolean mm1 = BooleanUtil.toBoolean(Integer.parseInt(ss1));
		dialog.chkAccountsPayableManagement.setSelected(mm1);

		String ss2 = ((String) map.get("sysKbn")).substring(2, 3);
		boolean mm2 = BooleanUtil.toBoolean(Integer.parseInt(ss2));
		dialog.chkAccountsReceivableManagement.setSelected(mm2);

		String ss3 = ((String) map.get("sysKbn")).substring(3, 4);
		boolean mm3 = BooleanUtil.toBoolean(Integer.parseInt(ss3));
		dialog.chkFixedAsset.setSelected(mm3);

		String ss4 = ((String) map.get("sysKbn")).substring(4, 5);
		boolean mm4 = BooleanUtil.toBoolean(Integer.parseInt(ss4));
		dialog.chkNotePayable.setSelected(mm4);

		String ss5 = ((String) map.get("sysKbn")).substring(5, 6);
		boolean mm5 = BooleanUtil.toBoolean(Integer.parseInt(ss5));
		dialog.chkBillReceivable.setSelected(mm5);

		String ss6 = ((String) map.get("sysKbn")).substring(6, 7);
		boolean mm6 = BooleanUtil.toBoolean(Integer.parseInt(ss6));
		dialog.chkManagementAccounting.setSelected(mm6);

		String ss7 = ((String) map.get("sysKbn")).substring(7, 8);
		boolean mm7 = BooleanUtil.toBoolean(Integer.parseInt(ss7));
		dialog.chkParentCompanyConnection.setSelected(mm7);

		String ss8 = ((String) map.get("sysKbn")).substring(8, 9);
		boolean mm8 = BooleanUtil.toBoolean(Integer.parseInt(ss8));
		dialog.chkHeadquartersBranchAccounting.setSelected(mm8);

		String ss9 = ((String) map.get("sysKbn")).substring(9, 10);
		boolean mm9 = BooleanUtil.toBoolean(Integer.parseInt(ss9));
		dialog.chkMultiCurrencyAccounting.setSelected(mm9);

		String ss10 = ((String) map.get("sysKbn")).substring(10, 11);
		boolean mm10 = BooleanUtil.toBoolean(Integer.parseInt(ss10));
		dialog.chkBudgetManagement.setSelected(mm10);

		String ss11 = ((String) map.get("sysKbn")).substring(11, 12);
		boolean mm11 = BooleanUtil.toBoolean(Integer.parseInt(ss11));
		dialog.chkFundAdministration.setSelected(mm11);

		String ss12 = ((String) map.get("sysKbn")).substring(12, 13);
		boolean mm12 = BooleanUtil.toBoolean(Integer.parseInt(ss12));
		dialog.chkSubsidiaryCompanyConnection.setSelected(mm12);

		String ss13 = ((String) map.get("sysKbn")).substring(13, 14);
		boolean mm13 = BooleanUtil.toBoolean(Integer.parseInt(ss13));
		dialog.chkGroupAccounting.setSelected(mm13);

		String ss14 = ((String) map.get("sysKbn")).substring(14, 15);
		boolean mm14 = BooleanUtil.toBoolean(Integer.parseInt(ss14));
		dialog.chkPortfolioManagement.setSelected(mm14);

		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// 編集モードのときは摘要コード、摘要区分とが編集不可になる
		isUpdate = "update".equals(map.get("flag"));
		dialog.ctrlUserCode.setEditable(!isUpdate);
		if (isUpdate) {
			dialog.ctrlUserName.getField().requestFocus();
			this.oldCmpCode = (String) map.get("kaiCode");
		}

		ref1.refreshName();

		dialog.ctrlDepartmentCode.setValue((String) map.get("depCode"));
		dialog.ctrlEmployeeCode.setValue((String) map.get("empCode"));
		dialog.ctrlLanguageCode.setValue((String) map.get("lngCode"));

		ref2.refreshName();
		ref3.refreshName();
		ref4.refreshName();

	}

	boolean checkDialog() {

		// 経理担当者区分チェック
		if (!dialog.rdoAccountingPerson.isSelected() && !dialog.rdoAccountingPersonExcluding.isSelected()) {
			showMessage(dialog, "I00002", "C00139");
			dialog.rdoAccountingPersonExcluding.requestFocus();
			return false;
		}

		// ユーザーコードチェック
		if (Util.isNullOrEmpty(dialog.ctrlUserCode.getValue())) {
			showMessage(dialog, "I00002", "C00589");
			dialog.ctrlUserCode.requestFocus();
			return false;
		}

		// ユーザー名称チェック
		if (Util.isNullOrEmpty(dialog.ctrlUserName.getValue())) {
			showMessage(dialog, "I00002", "C00691");
			dialog.ctrlUserName.requestFocus();
			return false;
		}

		// ユーザー略称チェック
		if (Util.isNullOrEmpty(dialog.ctrlUserAbbreviatedName.getValue())) {
			showMessage(dialog, "I00002", "C00692");
			dialog.ctrlUserAbbreviatedName.requestFocus();
			return false;
		}

		// ユーザー検索名称チェック
		if (Util.isNullOrEmpty(dialog.txtUserNameForSearch.getValue())) {
			showMessage(dialog, "I00002", "C00693");
			dialog.txtUserNameForSearch.requestFocus();
			return false;
		}

		// パスワードチェック
		if (Util.isNullOrEmpty(dialog.ctrlPassword.getValue())) {
			showMessage(dialog, "I00002", "C00597");
			dialog.ctrlPassword.requestFocus();
			return false;
		}
		// 処理権限レベルチェック
		if (Util.isNullOrEmpty(dialog.numProcessingAuthorityLevel.getValue())) {
			showMessage(dialog, "I00002", "C00297");
			dialog.numProcessingAuthorityLevel.requestFocus();
			return false;
		}

		// 会社コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlCompanyCode.getValue())) {
			showMessage(dialog, "I00002", "C00596");
			dialog.ctrlCompanyCode.requestTextFocus();
			return false;
		}

		// 社員コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlEmployeeCode.getValue())) {
			showMessage(dialog, "I00002", "C00697");
			dialog.ctrlEmployeeCode.requestTextFocus();
			return false;
		}

		// 所属部門コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlDepartmentCode.getValue())) {
			showMessage(dialog, "I00002", "C00698");
			dialog.ctrlDepartmentCode.requestTextFocus();
			return false;
		}

		// 言語コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlLanguageCode.getValue())) {
			showMessage(dialog, "I00002", "C00699");
			dialog.ctrlLanguageCode.requestTextFocus();
			return false;
		}

		// 開始年月日
		if (Util.isNullOrEmpty(dialog.dtBeginDate.getValue())) {
			super.showMessage(dialog, "W00034", "C00055");
			dialog.dtBeginDate.requestFocus();
			return false;
		}

		// 終了年月日
		if (Util.isNullOrEmpty(dialog.dtEndDate.getValue())) {
			super.showMessage(dialog, "W00034", "C00261");
			dialog.dtEndDate.requestFocus();
			return false;

		}

		// 開始年月日,終了年月日チェック
		if ((!Util.isNullOrEmpty(dialog.dtBeginDate.getValue()) && !Util.isNullOrEmpty(dialog.dtEndDate.getValue()))
			&& !Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue())) {
			super.showMessage(dialog, "W00035", "");
			dialog.dtBeginDate.requestFocus();
			return false;
		}

		if (!Util.isNullOrEmpty(dialog.ctrlUserCode.getValue())) {
			if (isUpdate && !this.oldCmpCode.equals(dialog.ctrlCompanyCode.getValue())) {
				if (checkCode(dialog.ctrlUserCode.getValue())) {
					// 警告メッセージ表示 既に存在します。 {会社コード}
					showMessage(dialog, "W00005", "C00596");
					dialog.ctrlCompanyCode.requestTextFocus();
					// エラーを返す
					return false;
				}
			}

			if (!isUpdate && checkCode(dialog.ctrlUserCode.getValue())) {
				// 警告メッセージ表示
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlUserCode.requestFocus();
				// エラーを返す
				return false;
			}
		}

		return true;

	}

	/**
	 * 閉じる
	 */
	void disposeDialog() {
		// エラーがある場合にはダイアログを閉じない
		if (dialog.isSettle) {

			if (checkDialog()) {
				dialog.setVisible(false);
			}
		} else {
			dialog.setVisible(false);
		}

	}

	/**
	 * 確定ボタンが押されたかどうか
	 * 
	 * @return 確定の場合true
	 */
	boolean isSettle() {
		return dialog.isSettle;
	}

	/**
	 * 画面上表示データの取得
	 * 
	 * @return データ
	 */

	Map getDataList() {
		StringBuffer s1 = new StringBuffer();
		s1.append(BooleanUtil.toInt(dialog.chkBasicAccounting.isSelected()));
		s1.append(BooleanUtil.toInt(dialog.chkAccountsPayableManagement.isSelected()));
		s1.append(BooleanUtil.toInt(dialog.chkAccountsReceivableManagement.isSelected()));
		s1.append(BooleanUtil.toInt(dialog.chkFixedAsset.isSelected()));
		s1.append(BooleanUtil.toInt(dialog.chkNotePayable.isSelected()));
		s1.append(BooleanUtil.toInt(dialog.chkBillReceivable.isSelected()));
		s1.append(BooleanUtil.toInt(dialog.chkManagementAccounting.isSelected()));
		s1.append(BooleanUtil.toInt(dialog.chkParentCompanyConnection.isSelected()));
		s1.append(BooleanUtil.toInt(dialog.chkHeadquartersBranchAccounting.isSelected()));
		s1.append(BooleanUtil.toInt(dialog.chkMultiCurrencyAccounting.isSelected()));
		s1.append(BooleanUtil.toInt(dialog.chkBudgetManagement.isSelected()));
		s1.append(BooleanUtil.toInt(dialog.chkFundAdministration.isSelected()));
		s1.append(BooleanUtil.toInt(dialog.chkSubsidiaryCompanyConnection.isSelected()));
		s1.append(BooleanUtil.toInt(dialog.chkGroupAccounting.isSelected()));
		s1.append(BooleanUtil.toInt(dialog.chkPortfolioManagement.isSelected()));
		String var = s1.toString();
		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("sysKbn", var);
		map.put("usrCode", dialog.ctrlUserCode.getValue());
		map.put("password", dialog.ctrlPassword.getValue());
		map.put("lngCode", dialog.ctrlLanguageCode.getValue());
		map.put("usrName", dialog.ctrlUserName.getValue());
		map.put("usrName_S", dialog.ctrlUserAbbreviatedName.getValue());
		map.put("usrName_k", dialog.txtUserNameForSearch.getValue());
		map.put("depCode", dialog.ctrlDepartmentCode.getValue());
		map.put("empCode", dialog.ctrlEmployeeCode.getValue());
		map.put("kaiCode", dialog.ctrlCompanyCode.getValue());
		map.put("oldKaiCode", this.oldCmpCode);

		if ("".equals(getComboBoxSelectedValue(dialog.ctrlUpdateAuthorityLevel.getComboBox()))) {
			map.put("updKen", "0");
		} else map.put("updKen", getComboBoxSelectedValue(dialog.ctrlUpdateAuthorityLevel.getComboBox()));
		boolean boo;
		boo = dialog.rdoAccountingPersonExcluding.isSelected();
		if (boo) {
			map.put("keiriKbn", "0");
		} else {
			map.put("keiriKbn", "1");
		}
		map.put("prcKen", dialog.numProcessingAuthorityLevel.getValue());
		map.put("strDate", dialog.dtBeginDate.getValue());
		map.put("endDate", dialog.dtEndDate.getValue());

		return map;
	}

	boolean checkCode(String usrCode) {
		if (Util.isNullOrEmpty(usrCode)) {
			return false;
		}
		addSendValues("flag", "checkcode");
		addSendValues("kaiCode", dialog.ctrlCompanyCode.getValue());
		addSendValues("usrCode", usrCode);

		try {
			request(getServletName());
		} catch (IOException ex) {
			super.errorHandler(dialog, ex, "E00009");
		}

		List result = super.getResultList();
		return (result.size() > 0);
	}
}
