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
 * 社員マスタダイアログ
 */
public class MG0160EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 社員マスタダイアログ */
	protected MG0160EditDisplayDialog dialog;

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0160EmployeeMasterServlet";
	}

	/** 処理区分 */
	protected boolean isUpdate = false;

	protected REFDialogCtrl ref1;

	protected REFDialogCtrl ref2;

	protected REFDialogCtrl ref3;

	/**
	 * コンストラクタ.
	 * 
	 * @param parent
	 * @param titleid
	 */
	public MG0160EditDisplayDialogCtrl(Frame parent, String titleid) {
		// 管理マスタダイアログの初期化
		dialog = createDialog(parent, titleid);

		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent ev) {
				dialog.isSettle = false;
				dialog.setVisible(false);
			}
		});

		init();

		dialog.ctrlTransferBranch.getField().setEditable(false);
		dialog.ctrlTransferBranch.getButton().setEnabled(false);
		dialog.rdoNormally.setSelected(true);

		dialog.ctrlEmployeeCode.getField().requestFocus();
		dialog.dtBeginDate.setValue(minInputDate);
		dialog.dtEndDate.setValue(maxInputDate);

	}

	/**
	 * @param parent 親コンテナー
	 * @param titleid タイトル
	 * @return ダイアログ
	 */
	protected MG0160EditDisplayDialog createDialog(Frame parent, String titleid) {
		return new MG0160EditDisplayDialog(parent, true, this, titleid);
	}

	void init() {

		// 振出銀行口座の設定
		ref1 = new REFDialogCtrl(dialog.ctrlDrawingBankAccount, dialog.getParentFrame());
		ref1.setColumnLabels("C00857", "C00124", "C00794");
		ref1.setTargetServlet("MP0030BankAccountMasterServlet");
		ref1.setTitleID("C02322");
		ref1.setShowsMsgOnError(false);

		dialog.ctrlDrawingBankAccount.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				ref1.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlDrawingBankAccount.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlDrawingBankAccount.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlDrawingBankAccount.getValue());
					dialog.ctrlDrawingBankAccount.getField().clearOldText();
					dialog.ctrlDrawingBankAccount.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		ref1.setREFListener(new REFAdapter() {

			@Override
			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "BankAccount");

				return keys;
			}

			// 銀行コードにて銀行マスタに存在
			@Override
			public void goodCodeInputted() {
				try {
					addSendValues("flag", "getrefitems");
					addSendValues("kaiCode", getLoginUserCompanyCode());
					addSendValues("cbkCbkCode", dialog.ctrlDrawingBankAccount.getField().getText());

					if (!request("MP0030BankAccountMasterServlet")) {
						errorHandler(dialog);
						return;
					}

					List result = getResultList().get(0);
					if (result != null && result.size() == 6) {
						String text1 = (String) result.get(1) + " " + (String) result.get(3);

						String rsText = (String) result.get(4);
						if ("1".equals(rsText)) {
							rsText = getWord("C00463");
						} else if ("2".equals(rsText)) {
							rsText = getWord("C00397");
						} else if ("3".equals(rsText)) {
							rsText = getWord("C00045");
						} else {
							rsText = getWord("C00368");
						}
						String text2 = rsText + " " + (String) result.get(5);

						dialog.ctrlDrawingBankAccount.getNotice().setEditable(true);
						dialog.ctrlDrawingBankAccount.getNotice().setText(text1);
						dialog.ctrlDrawingBankAccount.getNotice().setEditable(false);

						dialog.txtTransferAccountDepositType.setEditable(true);
						dialog.txtTransferAccountDepositType.setText(text2);
						dialog.txtTransferAccountDepositType.setEditable(false);
					}
				} catch (IOException e) {
					errorHandler(dialog, e, "E00009");
				}
			}

			// 銀行コードにて銀行マスタに存在しない
			@Override
			public void badCodeInputted() {
				dialog.ctrlDrawingBankAccount.getNotice().setEditable(true);
				dialog.ctrlDrawingBankAccount.getNotice().setText("");
				dialog.ctrlDrawingBankAccount.getNotice().setEditable(false);

				dialog.txtTransferAccountDepositType.setEditable(true);
				dialog.txtTransferAccountDepositType.setText("");
				dialog.txtTransferAccountDepositType.setEditable(false);
			}

			// 銀行コードをクリア
			@Override
			public void textCleared() {
				badCodeInputted();
			}
		});

		// 振込銀行コードの設定
		ref2 = new REFDialogCtrl(dialog.ctrlTransferBank, dialog.getParentFrame());
		ref2.setTargetServlet("MG0140BankMasterServlet");
		ref2.setTitleID("C02323");
		ref2.setColumnLabels("C00779", "C00781", "C00829");
		ref2.setShowsMsgOnError(false);
		ref2.setREFListener(new REFAdapter() {

			@Override
			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				keys.put("kind", "Bank");
				return keys;
			}

			@Override
			public void goodCodeInputted() {
				dialog.ctrlTransferBranch.getField().setEditableEnabled(true);
				dialog.ctrlTransferBranch.getButton().setEnabled(true);

			}

			@Override
			public void badCodeInputted() {
				dialog.ctrlTransferBranch.getField().setText(null);
				dialog.ctrlTransferBranch.getField().setEditableEnabled(false);
				dialog.ctrlTransferBranch.getNotice().setEditable(true);
				dialog.ctrlTransferBranch.getNotice().setText(null);
				dialog.ctrlTransferBranch.getNotice().setEditable(false);
				dialog.ctrlTransferBranch.getButton().setEnabled(false);
			}

			@Override
			public void codeChanged() {
				dialog.ctrlTransferBranch.getField().setText(null);
				dialog.ctrlTransferBranch.getNotice().setText(null);
			}
		});

		dialog.ctrlTransferBank.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				ref2.refreshName();
				if (dialog.ctrlTransferBank.isValueChanged()) {
					if (!Util.isNullOrEmpty(dialog.ctrlTransferBank.getValue().trim())
						&& !Util.isNullOrEmpty(dialog.ctrlTransferBank.getNotice().getText().trim())) {
						dialog.ctrlTransferBranch.getField().setText(null);
						dialog.ctrlTransferBranch.getNotice().setText(null);

					}
				}

				if (!Util.isNullOrEmpty(dialog.ctrlTransferBank.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlTransferBank.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlTransferBank.getValue());
					dialog.ctrlTransferBank.getField().clearOldText();
					dialog.ctrlTransferBank.getField().requestFocus();
					return false;

				}
				return true;
			}
		});

		// 振込支店コードの設定
		ref3 = new REFDialogCtrl(dialog.ctrlTransferBranch, dialog.getParentFrame());
		ref3.setTargetServlet("MG0140BankMasterServlet");
		ref3.setTitleID("C00778");
		ref3.setColumnLabels("C00780", "C00783", "C00785");
		ref3.setShowsMsgOnError(false);
		ref3.setREFListener(new REFAdapter() {

			@Override
			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				keys.put("bnkCode", dialog.ctrlTransferBank.getField().getText());
				keys.put("kind", "BankBranch");
				return keys;
			}

		});

		dialog.ctrlTransferBranch.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				ref3.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlTransferBranch.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlTransferBranch.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlTransferBranch.getValue());
					dialog.ctrlTransferBranch.getField().clearOldText();
					dialog.ctrlTransferBranch.getField().requestFocus();
					return false;

				}
				return true;
			}
		});

	}

	/**
	 * 表示
	 */
	void show() {
		dialog.setVisible(true);
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
	 * 値のセット
	 * 
	 * @param map セットする値
	 */

	void setValues(Map map) {
		dialog.ctrlTransferBranch.getButton().setEnabled(true);
		dialog.ctrlTransferBranch.getField().setEditable(true);

		// 社員コードの設定
		dialog.ctrlEmployeeCode.setValue((String) map.get("empCode"));
		// 社員名称の設定
		dialog.ctrlEmployeeName.setValue((String) map.get("empName"));
		// 社員略称の設定
		dialog.ctrlEmployeeAbbreviationName.setValue((String) map.get("empName_S"));
		// 社員検索名称の設定
		dialog.ctrlEmployeeNameForSearch.setValue((String) map.get("empName_K"));
		// 振込銀行ＣＤの設定
		dialog.ctrlTransferBank.setValue((String) map.get("empFuriBnkCode"));
		// 振込支店ＣＤの設定
		dialog.ctrlTransferBranch.setValue((String) map.get("empFuriStnCode"));
		// 振込口座番号の設定
		dialog.ctrlTransferAccountNumber.setValue((String) map.get("empYknNo"));
		// 振込口座預金種別の設定
		if (BooleanUtil.toBoolean(Integer.parseInt((String) map.get("empKozaKbn")))) {
			dialog.rdoNormally.setSelected(true);
			dialog.rdoInterim.setSelected(false);
		} else {
			dialog.rdoNormally.setSelected(false);
			dialog.rdoInterim.setSelected(true);
		}

		// 口座名義カナの設定
		dialog.ctrlAccountName.setValue((String) map.get("empYknKana"));
		// 振出銀行口座コードの設定
		dialog.ctrlDrawingBankAccount.setValue((String) map.get("empCbkCode"));
		// 開始年月日の設定
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// 終了年月日の設定
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// 編集モードのときは通貨コードが編集不可になる
		isUpdate = "update".equals(map.get("flag"));
		dialog.ctrlEmployeeCode.setEditable(!isUpdate); // 2006/12/29 Yanwei
		if (isUpdate) {
			dialog.ctrlEmployeeName.getField().requestFocus();
		}
		ref1.refreshName();
		ref2.refreshName();
		ref3.refreshName();
	}

	/**
	 * 閉じる
	 */
	void disposeDialog() {

		if (dialog.isSettle) {
			// 確定ボタン押下 チェックOKなら閉じる
			if (checkDialog()) {
				dialog.setVisible(false);
			}
		} else {
			// 戻るボタン押下
			// 画面を閉める
			dialog.setVisible(false);
		}

	}

	boolean checkDialog() {
		try {
			// 社員コードチェック
			if (Util.isNullOrEmpty(dialog.ctrlEmployeeCode.getValue())) {
				showMessage(dialog, "I00002", "C00697");
				dialog.ctrlEmployeeCode.requestFocus();
				return false;
			}

			if (!Util.isNullOrEmpty(dialog.ctrlEmployeeCode.getValue()) && !isUpdate
				&& checkCode(dialog.ctrlEmployeeCode.getValue())) {
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlEmployeeCode.requestFocus();
				return false;
			}

			// 社員名称チェック
			if (Util.isNullOrEmpty(dialog.ctrlEmployeeName.getValue())) {
				showMessage(dialog, "I00002", "C00807");
				dialog.ctrlEmployeeName.requestFocus();
				return false;
			}
			// 社員略称チェック
			if (Util.isNullOrEmpty(dialog.ctrlEmployeeAbbreviationName.getValue())) {
				showMessage(dialog, "I00002", "C00808");
				dialog.ctrlEmployeeAbbreviationName.requestFocus();
				return false;
			}
			// 社員検索名称チェック
			if (Util.isNullOrEmpty(dialog.ctrlEmployeeNameForSearch.getValue())) {
				showMessage(dialog, "I00002", "C00809");
				dialog.ctrlEmployeeNameForSearch.requestFocus();
				return false;
			}
			// 口座番号の桁数チェック
			if (!Util.isNullOrEmpty(dialog.ctrlTransferAccountNumber.getValue())
				&& dialog.ctrlTransferAccountNumber.getValue().length() != 7) {
				showMessage(dialog, "W00143", "7" + getWord("C02161"));
				dialog.ctrlTransferAccountNumber.requestFocus();
				return false;
			}

			// 開始年月日
			if (dialog.dtBeginDate.getValue() == null) {
				showMessage(dialog, "W00034", "C00055");
				dialog.dtBeginDate.getCalendar().requestFocus();
				return false;
			}
			// 終了年月日
			if (dialog.dtEndDate.getValue() == null) {
				showMessage(dialog, "W00034", "C00261");
				dialog.dtEndDate.getCalendar().requestFocus();
				return false;
			}

			// 開始年月日＜＝終了年月日にしてください
			if (Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue()) == false) {
				showMessage(dialog, "W00035", "");
				dialog.dtBeginDate.getCalendar().requestFocus();
				return false;
			}

			return true;
		} catch (IOException e) {
			errorHandler(dialog, e, "E00009");
			return false;
		}

	}

	/**
	 * 画面上表示データの取得
	 * 
	 * @return データ
	 */

	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();
		// 社員コードの設定
		map.put("empCode", dialog.ctrlEmployeeCode.getValue());
		// 社員名称の設定
		map.put("empName", dialog.ctrlEmployeeName.getValue());
		// 社員略称の設定
		map.put("empName_S", dialog.ctrlEmployeeAbbreviationName.getValue());
		// 社員検索名称の設定
		map.put("empName_K", dialog.ctrlEmployeeNameForSearch.getValue());
		// 振込銀行ＣＤの設定
		map.put("empFuriBnkCode", dialog.ctrlTransferBank.getValue());
		// 振込支店ＣＤの設定
		map.put("empFuriStnCode", dialog.ctrlTransferBranch.getValue());
		// 振込口座番号の設定
		map.put("empYknNo", dialog.ctrlTransferAccountNumber.getValue());
		// 振込口座預金種別の設定
		if (dialog.rdoNormally.isSelected()) {
			int rdo = 1;
			map.put("empKozaKbn", String.valueOf(rdo));
		} else {
			int rdo = 2;
			map.put("empKozaKbn", String.valueOf(rdo));
		}
		// 口座名義カナの設定
		map.put("empYknKana", dialog.ctrlAccountName.getValue());
		// 振出銀行口座コードの設定
		map.put("empCbkCode", dialog.ctrlDrawingBankAccount.getValue());
		// 開始年月日の設定
		map.put("strDate", dialog.dtBeginDate.getValue());
		// 終了年月日の設定
		map.put("endDate", dialog.dtEndDate.getValue());
		// 結果を返す
		return map;
	}

	boolean checkCode(String code) throws IOException {
		// 管理コード未入力
		if (Util.isNullOrEmpty(code)) {
			return false;
		}
		// 処理種別の設定
		addSendValues("flag", "checkcode");
		// 会社コードの設定
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// 社員コードの設定
		addSendValues("empCode", code);

		if (!request(getServletName())) {
			errorHandler(dialog);
			return true;
		}
		// 結果を取得
		List result = super.getResultList();
		// 結果を返す
		return (result.size() > 0);
	}
}
