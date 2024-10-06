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
 * @author yangjing
 */
public class MP0030EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 銀行口座マスタダイアログ */
	protected MP0030EditDisplayDialog dialog;

	/** 処理サーブレット */
	protected static final String TARGET_SERVLET = "MP0030BankAccountMasterServlet";

	/** mapの初期化 */
	protected Map yknKbnMap;

	/** 処理区分 */
	protected boolean isUpdate = false;

	/** 通貨コード */
	protected REFDialogCtrl refCurCode;

	/** 銀行コード */
	protected REFDialogCtrl refCbkBnkCode;

	/** 支店コード */
	protected REFDialogCtrl refCbkStnCode;

	/** 計上部門コード */
	protected REFDialogCtrl refCbkDepCode;

	/**
	 * コンストラクタ
	 */
	protected MP0030EditDisplayDialogCtrl() {
		// 処理なし
	}

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MP0030EditDisplayDialogCtrl(Frame parent, String titleid) {
		// 銀行口座マスタダイアログの初期化
		dialog = new MP0030EditDisplayDialog(parent, true, this, titleid);
		// タイトルを設定する
		// ダイアログを閉じ時、dialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});

		init();

		setItemCondition();

	}

	/**
	 * 科目コンポーネント 条件設定(ヘッダ)
	 */
	public void setItemCondition() {
		// ログインユーザーの会社コード
		dialog.ctrlItemUnit.getInputParameter().setCompanyCode(super.getLoginUserCompanyCode());
	}

	/**
	 * ダイアログ初期化
	 */
	protected void init() {
		// 集計区分の設定
		dialog.rdoEmployeeFBDivisionException.setSelected(true);
		dialog.rdoExternalFBException.setSelected(true);

		// 支店・補助・内訳を初期化
		dialog.ctrlBranch.setEnabled(true);
		dialog.ctrlBranch.setEditable(false);
		dialog.ctrlBranch.setButtonEnabled(false);
		dialog.ctrlItemUnit.getTSubItemField().setEnabled(true);
		dialog.ctrlItemUnit.getTSubItemField().setEditable(false);
		dialog.ctrlItemUnit.getTSubItemField().setButtonEnabled(false);
		dialog.ctrlItemUnit.getTBreakDownItemField().setEnabled(true);
		dialog.ctrlItemUnit.getTBreakDownItemField().setEditable(false);
		dialog.ctrlItemUnit.getTBreakDownItemField().setButtonEnabled(false);

		// 通貨コード
		refCurCode = new REFDialogCtrl(dialog.ctrlCurrency, dialog.getParentFrame());
		refCurCode.setColumnLabels("C00665", "C00881", "C00882");
		refCurCode.setTargetServlet("MG0300CurrencyMasterServlet");
		refCurCode.setTitleID("C01985");
		refCurCode.setShowsMsgOnError(false);
		refCurCode.addIgnoredButton(dialog.btnReturn);

		refCurCode.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				/* KIND */
				keys.put("kind", "Currency");
				return keys;
			}
		});

		dialog.ctrlCurrency.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refCurCode.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlCurrency.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlCurrency.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlCurrency.getValue());
					dialog.ctrlCurrency.getField().clearOldText();
					dialog.ctrlCurrency.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		// 銀行コード
		refCbkBnkCode = new REFDialogCtrl(dialog.ctrlBank, dialog.getParentFrame());
		refCbkBnkCode.setColumnLabels("C00779", "C00781", "C00829");
		refCbkBnkCode.setTargetServlet("MG0140BankMasterServlet");
		refCbkBnkCode.setTitleID("C02323");
		refCbkBnkCode.setShowsMsgOnError(false);
		refCbkBnkCode.addIgnoredButton(dialog.btnReturn);

		refCbkBnkCode.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				/* KIND */
				keys.put("kind", "Bank");
				return keys;
			}

			// 銀行コードにて銀行マスタに存在
			public void goodCodeInputted() {
				dialog.ctrlBranch.setEditable(true);
				dialog.ctrlBranch.setButtonEnabled(true);

			}

			// 銀行コードにて銀行マスタに存在しない
			public void badCodeInputted() {
				dialog.ctrlBranch.setValue("");
				dialog.ctrlBranch.setEditable(false);
				dialog.ctrlBranch.setNoticeEditable(true);
				dialog.ctrlBranch.setNoticeValue("");
				dialog.ctrlBranch.setNoticeEditable(false);
				dialog.ctrlBranch.setButtonEnabled(false);
			}

			// 銀行コードをクリア
			public void codeChanged() {
				dialog.ctrlBranch.setValue("");
				dialog.ctrlBranch.setNoticeValue("");
			}
		});

		dialog.ctrlBank.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refCbkBnkCode.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlBank.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlBank.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlBank.getValue());
					dialog.ctrlBank.getField().clearOldText();
					dialog.ctrlBank.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		// 支店
		refCbkStnCode = new REFDialogCtrl(dialog.ctrlBranch, dialog.getParentFrame());
		refCbkStnCode.setColumnLabels("C00780", "C00783", "C00785");
		refCbkStnCode.setTargetServlet("MG0140BankMasterServlet");
		refCbkStnCode.setTitleID("C00778");
		refCbkStnCode.setShowsMsgOnError(false);
		refCbkStnCode.addIgnoredButton(dialog.btnReturn);

		refCbkStnCode.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("bnkCode", dialog.ctrlBank.getField().getText());
				/* KIND */
				keys.put("kind", "BankBranch");
				return keys;
			}
		});

		dialog.ctrlBranch.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refCbkStnCode.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlBranch.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlBranch.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlBranch.getValue());
					dialog.ctrlBranch.getField().clearOldText();
					dialog.ctrlBranch.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		// 計上部門
		refCbkDepCode = new REFDialogCtrl(dialog.ctrlAppropriateDepartment, dialog.getParentFrame());
		refCbkDepCode.setColumnLabels("C00698", "C00724", "C00725");
		refCbkDepCode.setTargetServlet("MG0060DepartmentMasterServlet");
		refCbkDepCode.setTitleID("C02338");
		refCbkDepCode.setShowsMsgOnError(false);
		refCbkDepCode.addIgnoredButton(dialog.btnReturn);

		refCbkDepCode.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				/* KIND */
				keys.put("kind", "Department");
				return keys;
			}
		});

		dialog.ctrlAppropriateDepartment.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refCbkDepCode.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlAppropriateDepartment.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlAppropriateDepartment.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlAppropriateDepartment.getValue());
					dialog.ctrlAppropriateDepartment.getField().clearOldText();
					dialog.ctrlAppropriateDepartment.getField().requestFocus();
					return false;
				}
				return true;
			}

		});

		dialog.dtBeginDate.setValue(minInputDate);
		dialog.dtEndDate.setValue(maxInputDate);
		dialog.ctrlBankAccount.requestTextFocus();

	}

	/**
	 * @param map
	 */
	public void setYknKbnMap(Map map) {
		this.yknKbnMap = map;

		this.fillItemsToComboBox(dialog.ctrlDepositType.getComboBox(), yknKbnMap);

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
		// 科目コードの設定
		dialog.ctrlBankAccount.setValue((String) map.get("cbkCbkCode"));
		// 銀行口座名称の設定
		dialog.txtBankAccountName.setValue(map.get("cbkName"));
		// 通貨コードの設定
		dialog.ctrlCurrency.setValue((String) map.get("curCode"));
		// 銀行コードの設定
		dialog.ctrlBank.setValue((String) map.get("cbkBnkCode"));
		dialog.ctrlBranch.setEditable(true);
		dialog.ctrlBranch.setButtonEnabled(true);
		// 支店コードの設定
		dialog.ctrlBranch.setValue((String) map.get("cbkStnCode"));
		// 振込依頼人コードの設定
		dialog.ctrlTransferRequesterCode.setValue((String) map.get("cbkIraiEmpCode"));
		// 振込依頼人名の設定
		dialog.ctrlTransferRequesterKanaName.setValue((String) map.get("cbkIraiName"));
		// 振込依頼人名（漢字）の設定
		dialog.ctrlRemittanceRequesterKanjiName.setValue((String) map.get("cbkIraiName_J"));
		// 振込依頼人名（英字）の設定
		dialog.ctrlRemittanceRequesterEnglishName.setValue((String) map.get("cbkIraiName_E"));
		// 預金種目の設定
		this.setComboBoxSelectedItem(dialog.ctrlDepositType.getComboBox(), (String) map.get("cbkYknKbn"));
		// 口座番号の設定
		dialog.ctrlAccountNumber.setValue((String) map.get("cbkYknNo"));
		// 社員ＦＢ区分の設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("cbkEmpFbKbn")));
		dialog.rdoEmployeeFBDivisionException.setSelected(!boo);
		dialog.rdoEmployeeFBUse.setSelected(boo);
		// 社外ＦＢ区分の設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("cbkOutFbKbn")));
		dialog.rdoExternalFBException.setSelected(!boo);
		dialog.rdoExternalFBUse.setSelected(boo);
		// 計上部門コードの設定
		dialog.ctrlAppropriateDepartment.setValue((String) map.get("cbkDepCode"));

		String itemCode = Util.avoidNull(map.get("cbkKmkCode"));
		String subItemCode = Util.avoidNull(map.get("cbkHkmCode"));

		// 科目パラメータ
		dialog.ctrlItemUnit.getInputParameter().setItemCode(itemCode);
		dialog.ctrlItemUnit.getInputParameter().setSubItemCode(subItemCode);

		// 科目
		dialog.ctrlItemUnit.setItemCode(itemCode);

		// 補助科目
		dialog.ctrlItemUnit.setSubItemCode(subItemCode);

		// 内訳科目
		dialog.ctrlItemUnit.setBreakDownItemCode(Util.avoidNull(map.get("cbkUkmCode")));

		// 開始年月日の設定
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// 終了年月日の設定
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// 編集モードのときは摘要コード、摘要区分とが編集不可になる
		isUpdate = "update".equals(map.get("flag"));
		dialog.ctrlBankAccount.setEditable(!isUpdate);

		// 通貨名称の値を作る
		refCurCode.refreshName();
		// 銀行名称の値を作る
		refCbkBnkCode.refreshName();
		// 支店名称の値を作る
		refCbkStnCode.refreshName();
		// 計上部門コードの値を作る
		refCbkDepCode.refreshName();

		if (isUpdate) {
			dialog.txtBankAccountName.requestFocus();
		}
	}

	/**
	 * 閉じる
	 */
	void disposeDialog() {

		// エラーがある場合にはダイアログを閉じない
		if (dialog.isSettle) {
			// 画面必須入力項目のチャック
			if (checkDialog()) {
				dialog.setVisible(false);
			} else {
				dialog.isSettle = false;
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
		Map<String, Object> map = new TreeMap<String, Object>();
		// 銀行口座コードの設定
		map.put("cbkCbkCode", dialog.ctrlBankAccount.getValue());
		// 銀行口座名称の設定
		map.put("cbkName", dialog.txtBankAccountName.getValue());
		// 通貨コードの設定
		map.put("curCode", dialog.ctrlCurrency.getValue());
		// 銀行コードの設定
		map.put("cbkBnkCode", dialog.ctrlBank.getValue());
		// 支店コードの設定
		map.put("cbkStnCode", dialog.ctrlBranch.getValue());
		// 振込依頼人コードの設定
		map.put("cbkIraiEmpCode", dialog.ctrlTransferRequesterCode.getValue());
		// 振込依頼人名の設定
		map.put("cbkIraiName", dialog.ctrlTransferRequesterKanaName.getValue());
		// 振込依頼人名（漢字）の設定
		map.put("cbkIraiName_J", dialog.ctrlRemittanceRequesterKanjiName.getValue());
		// 振込依頼人名（英字）の設定
		map.put("cbkIraiName_E", dialog.ctrlRemittanceRequesterEnglishName.getValue());
		// 預金種目の設定
		map.put("cbkYknKbn", this.getComboBoxSelectedValue(dialog.ctrlDepositType.getComboBox()));
		// 口座番号の設定
		map.put("cbkYknNo", dialog.ctrlAccountNumber.getValue());
		// 社員ＦＢ区分の設定
		map.put("cbkEmpFbKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoEmployeeFBUse.isSelected())));
		// 社外ＦＢ区分の設定
		map.put("cbkOutFbKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoExternalFBUse.isSelected())));
		// 計上部門コードの設定
		map.put("cbkDepCode", dialog.ctrlAppropriateDepartment.getValue());
		// 科目コードの設定
		map.put("cbkKmkCode", dialog.ctrlItemUnit.getTItemField().getValue());
		// 補助科目コードの設定
		map.put("cbkHkmCode", dialog.ctrlItemUnit.getTSubItemField().getValue());
		// 内訳科目コードの設定
		map.put("cbkUkmCode", dialog.ctrlItemUnit.getTBreakDownItemField().getValue());
		// 開始年月日の設定
		map.put("strDate", dialog.dtBeginDate.getValue());
		// 終了年月日の設定
		map.put("endDate", dialog.dtEndDate.getValue());
		// 結果を返す
		return map;
	}

	boolean checkCode(String cbkCbkCode) {
		// 銀行口座コード未入力
		if (Util.isNullOrEmpty(cbkCbkCode)) {
			return false;
		}
		// 処理種別の設定
		addSendValues("flag", "checkcode");
		// 会社コードの設定
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// 銀行口座コードの設定
		addSendValues("cbkCbkCode", cbkCbkCode);

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

	/**
	 * ダイアログの入力項目チェック
	 * 
	 * @return true チェック成功 false エラーがある
	 */
	boolean checkDialog() {

		// 銀行口座コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlBankAccount.getValue())) {
			// エラーメッセージ出力
			super.showMessage(dialog, "I00002", "C00857");
			dialog.ctrlBankAccount.requestFocus();
			return false;
		}

		if (!isUpdate && checkCode(dialog.ctrlBankAccount.getValue())) {
			showMessage(dialog, "W00005", "C00174");
			dialog.ctrlBankAccount.requestFocus();
			return false;
		}

		// 銀行口座名称
		if (Util.isNullOrEmpty(dialog.txtBankAccountName.getValue())) {
			// エラーメッセージ出力
			super.showMessage(dialog, "I00002", "C02145");
			dialog.txtBankAccountName.requestFocus();
			return false;
		}

		// 通貨チェック
		if (Util.isNullOrEmpty(dialog.ctrlCurrency.getValue())) {
			// エラーメッセージ出力
			super.showMessage(dialog, "I00002", "C00371");
			dialog.ctrlCurrency.requestTextFocus();
			return false;
		}

		// 銀行チェック
		if (Util.isNullOrEmpty(dialog.ctrlBank.getValue())) {
			// エラーメッセージ出力
			super.showMessage(dialog, "I00002", "C00124");
			dialog.ctrlBank.requestTextFocus();
			return false;
		}

		// 支店チェック
		if (Util.isNullOrEmpty(dialog.ctrlBranch.getValue())) {
			// エラーメッセージ出力
			super.showMessage(dialog, "I00002", "C00222");
			dialog.ctrlBranch.requestTextFocus();
			return false;
		}

		// 振込依頼人コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlTransferRequesterCode.getValue())) {
			// エラーメッセージ出力
			super.showMessage(dialog, "I00002", "C00858");
			dialog.ctrlTransferRequesterCode.requestFocus();
			return false;
		}

		// 振込依頼人名の設定
		if (Util.isNullOrEmpty(dialog.ctrlTransferRequesterKanaName.getValue())) {
			// エラーメッセージ出力
			super.showMessage(dialog, "I00002", "C00859");
			dialog.ctrlTransferRequesterKanaName.requestFocus();
			return false;
		}

		// 口座番号チェック
		if (Util.isNullOrEmpty(dialog.ctrlAccountNumber.getValue())) {
			// エラーメッセージ出力
			super.showMessage(dialog, "I00002", "C00794");
			dialog.ctrlAccountNumber.requestFocus();
			return false;
		}

		// 預金種目チェック
		if (Util.isNullOrEmpty(dialog.ctrlDepositType.getComboBox().getSelectedItem().toString())) {
			// エラーメッセージ出力
			super.showMessage(dialog, "I00002", "C01326");
			dialog.ctrlDepositType.getComboBox().requestFocus();
			return false;
		}

		// 計上部門チェック
		if (Util.isNullOrEmpty(dialog.ctrlAppropriateDepartment.getValue())) {
			// エラーメッセージ出力
			super.showMessage(dialog, "I00002", "C00863");
			dialog.ctrlAppropriateDepartment.requestTextFocus();
			return false;
		}

		// 科目チェック
		if (Util.isNullOrEmpty(dialog.ctrlItemUnit.getTItemField().getValue())) {
			// エラーメッセージ出力
			super.showMessage(dialog, "I00002", "C00572");
			dialog.ctrlItemUnit.getTItemField().requestTextFocus();
			return false;
		}

		// 補助チェック
		if (dialog.ctrlItemUnit.getTSubItemField().getField().isEditable()
			&& Util.isNullOrEmpty(dialog.ctrlItemUnit.getTSubItemField().getValue())) {
			// エラーメッセージ出力
			super.showMessage(dialog, "I00002", "C00602");
			dialog.ctrlItemUnit.getTSubItemField().requestTextFocus();
			return false;
		}

		// 内訳チェック
		if (dialog.ctrlItemUnit.getTBreakDownItemField().getField().isEditable()
			&& Util.isNullOrEmpty(dialog.ctrlItemUnit.getTBreakDownItemField().getValue())) {
			showMessage(dialog, "I00002", "C00603");
			dialog.ctrlItemUnit.getTBreakDownItemField().requestTextFocus();
			return false;
		}

		// 開始年月日
		if (Util.isNullOrEmpty(dialog.dtBeginDate.getValue())) {
			showMessage(dialog, "W00034", "C00055");
			dialog.dtBeginDate.getCalendar().requestFocus();
			return false;
		}

		// 終了年月日
		if (Util.isNullOrEmpty(dialog.dtEndDate.getValue())) {
			showMessage(dialog, "W00034", "C00261");
			dialog.dtEndDate.getCalendar().requestFocus();
			return false;
		}

		// 開始年月日＜＝終了年月日にしてください
		if (Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue()) == false) {
			dialog.dtBeginDate.getCalendar().requestFocus();
			showMessage(dialog, "W00035", "");
			return false;
		}

		return true;

	}
}
