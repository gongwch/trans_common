package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 内訳科目マスタ編集画面コントロール
 */
public class MG0100EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 内訳科目マスタダイアログ */
	protected MG0100EditDisplayDialog dialog;

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0100BreakDownItemMasterServlet";

	/** 処理区分 */
	protected boolean isUpdate = false;

	private Map triCodeFlgMap;

	REFDialogCtrl refKmkCode;

	REFDialogCtrl refHkmCode;

	REFDialogCtrl refZeiCode;

	/**
	 * コンストラクタ
	 */
	MG0100EditDisplayDialogCtrl() {
		// 処理なし
	}

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MG0100EditDisplayDialogCtrl(Frame parent, String titleid) {
		// 補助科目マスタダイアログの初期化
		dialog = new MG0100EditDisplayDialog(parent, true, this, titleid);
		// タイトルを設定する
		dialog.ctrlItem.getField().requestFocus();
		dialog.ctrlItem.getField().setEnabled(true);

		CompanyControlHelper10 cc = CompanyControlHelper10.getInstance(this.getLoginUserCompanyCode());

		// 管理１-6入力フラグが言語の指定された名称を表示する。
		String knrName;
		knrName = cc.getKnrName1() + this.getWord("C02386");
		dialog.chkManagement1InputFlag.setText(knrName);

		knrName = cc.getKnrName2() + this.getWord("C02386");
		dialog.chkManagement2InputFlag.setText(knrName);

		knrName = cc.getKnrName3() + this.getWord("C02386");
		dialog.chkManagement3InputFlag.setText(knrName);

		knrName = cc.getKnrName4() + this.getWord("C02386");
		dialog.chkManagement4InputFlag.setText(knrName);

		knrName = cc.getKnrName5() + this.getWord("C02386");
		dialog.chkManagement5InputFlag.setText(knrName);

		knrName = cc.getKnrName6() + this.getWord("C02386");
		dialog.chkManagement6InputFlag.setText(knrName);

		// 非会計1-3入力フラグが言語の指定された名称を表示する。
		String hmName;
		hmName = cc.getCmpHmName1() + this.getWord("C02386");
		dialog.chkNotAccounting1InputFlag.setText(hmName);

		hmName = cc.getCmpHmName2() + this.getWord("C02386");
		dialog.chkNotAccounting2InputFlag.setText(hmName);

		hmName = cc.getCmpHmName3() + this.getWord("C02386");
		dialog.chkNotAccounting3InputFlag.setText(hmName);

		// 科目コードの初期化
		dialog.ctrlSubItem.getField().setEditable(false);
		dialog.ctrlSubItem.getButton().setEnabled(false);
		init();
		// ダイアログを閉じ時、dialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});

		dialog.dtBeginDate.setValue(DateUtil.getDate(1900, 1, 1));
		dialog.dtEndDate.setValue(DateUtil.getDate(2099, 12, 31));

	}

	protected void init() {

		// 消費税コードチェック

		refZeiCode = new REFDialogCtrl(dialog.ctrlConsumptionTax, dialog.getParentFrame());
		refZeiCode.setTargetServlet("MG0130ConsumptionTaxMasterServlet");
		refZeiCode.setTitleID(getWord("C02324"));
		refZeiCode.setColumnLabelIDs("C00573", "C00775", "C00828");
		refZeiCode.setShowsMsgOnError(false);
		refZeiCode.addIgnoredButton(dialog.btnReturn);
		refZeiCode.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 会社コードの設定

				keys.put("kind", "ConsumptionTax");
				keys.put("kaiCode", getLoginUserCompanyCode());

				return keys;
			}
		});

		dialog.ctrlConsumptionTax.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refZeiCode.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlConsumptionTax.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlConsumptionTax.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlConsumptionTax.getValue());
					dialog.ctrlConsumptionTax.getField().clearOldText();
					dialog.ctrlConsumptionTax.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

	}

	/**
	 * (科目)
	 */
	public void setItemData() {
		String itemCode = Util.avoidNull(dialog.ctrlItem.getValue());
		dialog.ctrlItem.getInputParameter().setSubItemDivision("1");
		dialog.ctrlItem.getInputParameter().setItemCode(itemCode);
	}

	/**
	 * (補助科目)
	 */
	public void setSubItemData() {
		// 会社コード
		dialog.ctrlSubItem.getInputParameter().setItemCode(dialog.ctrlItem.getValue());
		String subItemCode = Util.avoidNull(dialog.ctrlSubItem.getValue());
		dialog.ctrlSubItem.getInputParameter().setBreakDownItemDivision("1");
		dialog.ctrlSubItem.getInputParameter().setSubItemCode(subItemCode);
	}

	/**
	 * @param map
	 */
	// mapの初期化
	public void setTriCodeFlgMap(Map map) {
		this.triCodeFlgMap = map;

		this.fillItemsToComboBox(dialog.ctrlCustomerInputFlag.getComboBox(), triCodeFlgMap, false);
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
		String itemCode = (String) map.get("kmkCode");
		dialog.ctrlItem.getInputParameter().setItemCode(itemCode);
		dialog.ctrlItem.setValue(itemCode);
		dialog.ctrlItem.getField().pushOldText();
		// 補助科目コードの設定
		dialog.ctrlSubItem.getInputParameter().setItemCode(itemCode);
		String subItemCode = (String) map.get("hkmCode");
		dialog.ctrlSubItem.getInputParameter().setSubItemCode(subItemCode);
		dialog.ctrlSubItem.setValue(subItemCode);

		// 内訳科目コードの設定
		dialog.ctrlBreakDownItemCode.setValue((String) map.get("ukmCode"));
		// 内訳科目名称の設定
		dialog.ctrlBreakDownItemName.setValue((String) map.get("ukmName"));
		// 内訳科目略称の設定
		dialog.ctrlBreakDownItemAbbreviationName.setValue((String) map.get("ukmName_S"));
		// 内訳科目検索名称の設定
		dialog.ctrlBreakDownItemNameForSearch.setValue((String) map.get("ukmName_K"));
		// 消費税コードの設定
		dialog.ctrlConsumptionTax.setValue((String) map.get("zeiCode"));
		// 取引先入力フラグの設定
		this.setComboBoxSelectedItem(dialog.ctrlCustomerInputFlag.getComboBox(), (String) map.get("triCodeFlg"));
		// 入金伝票入力ﾌﾗｸﾞの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("glFlg1")));
		dialog.chkReceivingSlipInputFlag.setSelected(boo);
		// 出金伝票入力ﾌﾗｸﾞの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("glFlg2")));
		dialog.chkPaymentSlipInputFlag.setSelected(boo);
		// 振替伝票入力ﾌﾗｸﾞの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("glFlg3")));
		dialog.chkTransferSlipInputFlag.setSelected(boo);
		// 経費精算伝票入力ﾌﾗｸﾞの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("apFlg1")));
		dialog.chkExpenseInputFlag.setSelected(boo);
		// 請求書伝票入力ﾌﾗｸﾞの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("apFlg2")));
		dialog.chkAccountsPayableAppropriateSlipInputFlag.setSelected(boo);
		// 債権計上伝票入力ﾌﾗｸﾞの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("arFlg1")));
		dialog.chkAccountsReceivableAppropriateSlipInputFlag.setSelected(boo);
		// 債権消込伝票入力ﾌﾗｸﾞの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("arFlg2")));
		dialog.chkAccountsReceivableErasingSlipInputFlag.setSelected(boo);
		// 資産計上伝票入力ﾌﾗｸﾞの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("faFlg1")));
		dialog.chkAssetsAppropriateSlipInputFlag.setSelected(boo);
		// 支払依頼伝票入力ﾌﾗｸﾞの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("faFlg2")));
		dialog.chkPaymentRequestSlipInputFlag.setSelected(boo);
		// 発生日入力ﾌﾗｸﾞの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("hasFlg")));
		dialog.chkOccurrenceDateInputFlag.setSelected(boo);
		// 社員入力フラグの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("empCodeFlg")));
		dialog.chkEmployeeInputFlag.setSelected(boo);
		// 管理１-6入力フラグの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrFlg1")));
		dialog.chkManagement1InputFlag.setSelected(boo);
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrFlg2")));
		dialog.chkManagement2InputFlag.setSelected(boo);
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrFlg3")));
		dialog.chkManagement3InputFlag.setSelected(boo);
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrFlg4")));
		dialog.chkManagement4InputFlag.setSelected(boo);
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrFlg5")));
		dialog.chkManagement5InputFlag.setSelected(boo);
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrFlg6")));
		dialog.chkManagement6InputFlag.setSelected(boo);
		// 非会計1-3入力フラグの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("hmFlg1")));
		dialog.chkNotAccounting1InputFlag.setSelected(boo);
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("hmFlg2")));
		dialog.chkNotAccounting2InputFlag.setSelected(boo);
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("hmFlg3")));
		dialog.chkNotAccounting3InputFlag.setSelected(boo);
		// 売上課税入力フラグの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("uriZeiFlg")));
		dialog.chkSalesTaxInputFlag.setSelected(boo);
		// 仕入課税入力フラグの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("sirZeiFlg")));
		dialog.chkPurchesesTaxInputFlag.setSelected(boo);
		// 多通貨入力フラグの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("mcrFlg")));
		dialog.chkMultiCurrencyInputFlag.setSelected(boo);
		// 評価替対象フラグの設定
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("excFlg")));
		dialog.ctrlRevaluationObjectFlag.setSelected(boo);
		// 開始年月日の設定
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// 終了年月日の設定
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// refKmkCode.refreshName();
		// refHkmCode.refreshName();
		refZeiCode.refreshName();
		// 編集モードのときは摘要コード、摘要区分とが編集不可になる
		isUpdate = "update".equals(map.get("flag"));
		// 科目コードの設定

		dialog.ctrlItem.getField().setEnabled(true);
		dialog.ctrlItem.getField().setEditable(!isUpdate);
		dialog.ctrlItem.getButton().setEnabled(!isUpdate);

		dialog.ctrlSubItem.getField().setEnabled(true);
		dialog.ctrlSubItem.getField().setEditable(false);
		dialog.ctrlSubItem.getButton().setEnabled(false);

		if (!isUpdate) {
			if (dialog.ctrlSubItem.getField() != null) {
				dialog.ctrlSubItem.getField().setEnabled(true);
				dialog.ctrlSubItem.getField().setEditable(true);
				dialog.ctrlSubItem.getButton().setEnabled(true);
			}
		}
		dialog.ctrlBreakDownItemCode.getField().setEnabled(true);
		dialog.ctrlBreakDownItemCode.getField().setEditable(!isUpdate);

		if (isUpdate) {
			dialog.ctrlBreakDownItemName.getField().requestFocus();
		}

	}

	/**
	 * 閉じる
	 * 
	 * @return true:OK
	 * @throws IOException
	 */
	boolean checkDialog() throws IOException {
		// 科目コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlItem.getValue())) {
			showMessage(dialog, "I00002", "C00077");
			dialog.ctrlItem.requestTextFocus();
			return false;
		}

		if (!Util.isNullOrEmpty(dialog.ctrlItem.getValue())) {
			if (!isUpdate
				&& checkCode(dialog.ctrlItem.getValue(), dialog.ctrlSubItem.getValue(), dialog.ctrlBreakDownItemCode
					.getValue())) {
				// 警告メッセージ表示
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlBreakDownItemCode.requestTextFocus();
				// エラーを返す
				return false;
			}
		}

		// 補助科目コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlSubItem.getValue())) {
			showMessage(dialog, "I00002", "C00487");
			dialog.ctrlSubItem.requestTextFocus();
			return false;
		}

		// 内訳科目コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlBreakDownItemCode.getValue())) {
			showMessage(dialog, "I00002", "C00876");
			dialog.ctrlBreakDownItemCode.requestFocus();
			return false;
		}

		// 内訳科目名称チェック
		if (Util.isNullOrEmpty(dialog.ctrlBreakDownItemName.getValue())) {
			showMessage(dialog, "I00002", "C00877");
			dialog.ctrlBreakDownItemName.requestFocus();
			return false;
		}

		// 内訳科目略称チェック
		if (Util.isNullOrEmpty(dialog.ctrlBreakDownItemAbbreviationName.getValue())) {
			showMessage(dialog, "I00002", "C00878");
			dialog.ctrlBreakDownItemAbbreviationName.requestFocus();
			return false;
		}

		// 内訳科目検索名称チェック
		if (Util.isNullOrEmpty(dialog.ctrlBreakDownItemNameForSearch.getValue())) {
			showMessage(dialog, "I00002", "C00879");
			dialog.ctrlBreakDownItemNameForSearch.requestFocus();
			return false;
		}

		// 取引先入力フラグチェック
		if (Util.isNullOrEmpty(dialog.ctrlCustomerInputFlag.getComboBox().getSelectedItem().toString())) {
			showMessage(dialog, "I00002", "C01134");
			dialog.ctrlCustomerInputFlag.requestFocus();
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

		// 消費税コードチェック
		MG0080InputCheckerShouhiZei checker = new MG0080InputCheckerShouhiZei(dialog);

		if (!checker.isValid(dialog.ctrlConsumptionTax.getValue(), dialog.chkSalesTaxInputFlag.isSelected(),
			dialog.chkPurchesesTaxInputFlag.isSelected())) {
			dialog.ctrlConsumptionTax.requestTextFocus();
			return false;
		}
		return true;

	}

	void disposeDialog() {
		try {
			// エラーがある場合にはダイアログを閉じない
			if (dialog.isSettle) {
				if (checkDialog()) {
					dialog.setVisible(false);
				}
			} else {
				dialog.setVisible(false);
			}

		} catch (Exception ex) {
			errorHandler(dialog, ex);
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

		// 科目コードの設定
		map.put("kmkCode", dialog.ctrlItem.getValue());
		// 補助科目コードの設定
		map.put("hkmCode", dialog.ctrlSubItem.getValue());
		// 内訳科目コードの設定
		map.put("ukmCode", dialog.ctrlBreakDownItemCode.getValue());
		// 内訳科目名称の設定
		map.put("ukmName", dialog.ctrlBreakDownItemName.getValue());
		// 内訳科目略称の設定
		map.put("ukmName_S", dialog.ctrlBreakDownItemAbbreviationName.getValue());
		// 内訳科目検索名称の設定
		map.put("ukmName_K", dialog.ctrlBreakDownItemNameForSearch.getValue());
		// 消費税コードの設定
		map.put("zeiCode", dialog.ctrlConsumptionTax.getValue());
		// 取引先入力フラグの設定
		map.put("triCodeFlg", this.getComboBoxSelectedValue(dialog.ctrlCustomerInputFlag.getComboBox()));
		// 入金伝票入力ﾌﾗｸﾞの設定
		map.put("glFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkReceivingSlipInputFlag.isSelected())));
		// 出金伝票入力ﾌﾗｸﾞの設定
		map.put("glFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkPaymentSlipInputFlag.isSelected())));
		// 振替伝票入力ﾌﾗｸﾞの設定
		map.put("glFlg3", String.valueOf(BooleanUtil.toInt(dialog.chkTransferSlipInputFlag.isSelected())));
		// 経費精算伝票入力ﾌﾗｸﾞの設定
		map.put("apFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkExpenseInputFlag.isSelected())));
		// 経費精算伝票入力ﾌﾗｸﾞの設定
		map.put("apFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkAccountsPayableAppropriateSlipInputFlag
			.isSelected())));
		// 債権計上伝票入力ﾌﾗｸﾞの設定
		map.put("arFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkAccountsReceivableAppropriateSlipInputFlag
			.isSelected())));
		// 債権計上伝票入力ﾌﾗｸﾞの設定
		map.put("arFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkAccountsReceivableErasingSlipInputFlag
			.isSelected())));
		// 資産計上伝票入力ﾌﾗｸﾞの設定
		map.put("faFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkAssetsAppropriateSlipInputFlag.isSelected())));
		// 資産計上伝票入力ﾌﾗｸﾞの設定
		map.put("faFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkPaymentRequestSlipInputFlag.isSelected())));
		// 発生日入力ﾌﾗｸﾞ
		map.put("hasFlg", String.valueOf(BooleanUtil.toInt(dialog.chkOccurrenceDateInputFlag.isSelected())));
		// 社員入力フラグ
		map.put("empCodeFlg", String.valueOf(BooleanUtil.toInt(dialog.chkEmployeeInputFlag.isSelected())));
		// 管理１-6入力フラグ
		map.put("knrFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkManagement1InputFlag.isSelected())));
		map.put("knrFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkManagement2InputFlag.isSelected())));
		map.put("knrFlg3", String.valueOf(BooleanUtil.toInt(dialog.chkManagement3InputFlag.isSelected())));
		map.put("knrFlg4", String.valueOf(BooleanUtil.toInt(dialog.chkManagement4InputFlag.isSelected())));
		map.put("knrFlg5", String.valueOf(BooleanUtil.toInt(dialog.chkManagement5InputFlag.isSelected())));
		map.put("knrFlg6", String.valueOf(BooleanUtil.toInt(dialog.chkManagement6InputFlag.isSelected())));
		// 管理１-3入力フラグの設定
		map.put("hmFlg1", String.valueOf(BooleanUtil.toInt(dialog.chkNotAccounting1InputFlag.isSelected())));
		map.put("hmFlg2", String.valueOf(BooleanUtil.toInt(dialog.chkNotAccounting2InputFlag.isSelected())));
		map.put("hmFlg3", String.valueOf(BooleanUtil.toInt(dialog.chkNotAccounting3InputFlag.isSelected())));
		// 非会計1-3入力フラグの設定
		map.put("uriZeiFlg", String.valueOf(BooleanUtil.toInt(dialog.chkSalesTaxInputFlag.isSelected())));
		// 仕入課税入力フラグの設定
		map.put("sirZeiFlg", String.valueOf(BooleanUtil.toInt(dialog.chkPurchesesTaxInputFlag.isSelected())));
		// 多通貨入力フラグの設定
		map.put("mcrFlg", String.valueOf(BooleanUtil.toInt(dialog.chkMultiCurrencyInputFlag.isSelected())));
		// 評価替対象フラグの設定
		map.put("excFlg", String.valueOf(BooleanUtil.toInt(dialog.ctrlRevaluationObjectFlag.isSelected())));
		// 開始年月日の設定
		map.put("strDate", dialog.dtBeginDate.getValue());
		// 終了年月日の設定
		map.put("endDate", dialog.dtEndDate.getValue());
		// 結果を返す
		return map;
	}

	boolean checkCode(String kmkCode, String hkmCode, String ukmCode) throws IOException {
		// 科目コード未入力
		if (Util.isNullOrEmpty(kmkCode) || Util.isNullOrEmpty(hkmCode) || Util.isNullOrEmpty(ukmCode)) {
			return false;
		}
		// 処理種別の設定
		addSendValues("flag", "checkcode");
		// 会社コードの設定
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// 科目コードの設定
		addSendValues("kmkCode", kmkCode);
		// 補助科目コードの設定
		addSendValues("hkmCode", hkmCode);
		// 内訳科目コードの設定
		addSendValues("ukmCode", ukmCode);

		// サーバ側のエラー場合
		if (!request(TARGET_SERVLET)) {
			errorHandler(dialog);
			return true;
		}

		// 結果を取得
		List result = super.getResultList();

		// 結果を返す
		return (result.size() > 0);
	}
}

class CompanyControlHelper10 extends TAppletClientBase {

	private static Map instances = Collections.synchronizedMap(new HashMap());

	private String knrKbn1;

	private String knrKbn2;

	private String knrKbn3;

	private String knrKbn4;

	private String knrKbn5;

	private String knrKbn6;

	private String knrName1;

	private String knrName2;

	private String knrName3;

	private String knrName4;

	private String knrName5;

	private String knrName6;

	private String cmpHmKbn1;

	private String cmpHmKbn2;

	private String cmpHmKbn3;

	private String cmpHmName1;

	private String cmpHmName2;

	private String cmpHmName3;

	private String kmkName;

	private String hkmName;

	private String ukmName;

	private String kmkKbn = "1";

	private String hkmKbn = "1";

	private String ukmKbn;

	/**
	 * @param companyCode
	 * @return instance
	 */
	public static CompanyControlHelper10 getInstance(String companyCode) {
		if (!instances.containsKey(companyCode)) {
			CompanyControlHelper10 instance = new CompanyControlHelper10(companyCode);
			instances.put(companyCode, instance);
		}
		return (CompanyControlHelper10) instances.get(companyCode);
	}

	/**
	 * @param companyCode
	 */
	public static void reload(String companyCode) {
		instances.remove(companyCode);
	}

	private static final String TARGET_SERVLET = "MG0030CompanyControlMasterServlet";

	private CompanyControlHelper10(String companyCode) {
		try {
			addSendValues("flag", "findone");
			addSendValues("kaiCode", companyCode);

			if (!request(TARGET_SERVLET)) {
				errorHandler();
			}

			List result = super.getResultList();
			if (result != null && result.size() > 0) {
				List inner = (List) result.get(0);

				knrKbn1 = (String) inner.get(5);
				knrKbn2 = (String) inner.get(6);
				knrKbn3 = (String) inner.get(7);
				knrKbn4 = (String) inner.get(8);
				knrKbn5 = (String) inner.get(9);
				knrKbn6 = (String) inner.get(10);
				knrName1 = (String) inner.get(11);
				knrName2 = (String) inner.get(12);
				knrName3 = (String) inner.get(13);
				knrName4 = (String) inner.get(14);
				knrName5 = (String) inner.get(15);
				knrName6 = (String) inner.get(16);

				cmpHmKbn1 = (String) inner.get(17);
				cmpHmKbn2 = (String) inner.get(18);
				cmpHmKbn3 = (String) inner.get(19);
				cmpHmName1 = (String) inner.get(20);
				cmpHmName2 = (String) inner.get(21);
				cmpHmName3 = (String) inner.get(22);

				kmkName = (String) inner.get(1);
				hkmName = (String) inner.get(2);
				ukmName = (String) inner.get(4);
				ukmKbn = (String) inner.get(3);
			}

			if (Util.isNullOrEmpty(knrKbn1) || "0".equals(knrKbn1) || Util.isNullOrEmpty(knrName1)) {
				knrKbn1 = "0";
				knrName1 = "C01025";
			}
			if (Util.isNullOrEmpty(knrKbn2) || "0".equals(knrKbn2) || Util.isNullOrEmpty(knrName2)) {
				knrKbn2 = "0";
				knrName2 = "C01027";
			}
			if (Util.isNullOrEmpty(knrKbn3) || "0".equals(knrKbn3) || Util.isNullOrEmpty(knrName3)) {
				knrKbn3 = "0";
				knrName3 = "C01029";
			}
			if (Util.isNullOrEmpty(knrKbn4) || "0".equals(knrKbn4) || Util.isNullOrEmpty(knrName4)) {
				knrKbn4 = "0";
				knrName4 = "C01031";
			}
			if (Util.isNullOrEmpty(knrKbn5) || "0".equals(knrKbn5) || Util.isNullOrEmpty(knrName5)) {
				knrKbn5 = "0";
				knrName5 = "C01033";
			}
			if (Util.isNullOrEmpty(knrKbn6) || "0".equals(knrKbn6) || Util.isNullOrEmpty(knrName6)) {
				knrKbn6 = "0";
				knrName6 = "C01035";
			}

			if (Util.isNullOrEmpty(cmpHmKbn1) || "0".equals(cmpHmKbn1) || Util.isNullOrEmpty(cmpHmName1)) {
				cmpHmKbn1 = "0";
				cmpHmName1 = "C01291";
			}
			if (Util.isNullOrEmpty(cmpHmKbn2) || "0".equals(cmpHmKbn2) || Util.isNullOrEmpty(cmpHmName2)) {
				cmpHmKbn2 = "0";
				cmpHmName2 = "C01292";
			}
			if (Util.isNullOrEmpty(cmpHmKbn3) || "0".equals(cmpHmKbn3) || Util.isNullOrEmpty(cmpHmName3)) {
				cmpHmKbn3 = "0";
				cmpHmName3 = "C01293";
			}

			if (Util.isNullOrEmpty(kmkKbn) || "0".equals(kmkKbn) || Util.isNullOrEmpty(kmkName)) {
				kmkKbn = "0";
				kmkName = "C00077";
			}
			if (Util.isNullOrEmpty(hkmKbn) || "0".equals(hkmKbn) || Util.isNullOrEmpty(hkmName)) {
				hkmKbn = "0";
				hkmName = "C00488";
			}
			if (Util.isNullOrEmpty(ukmKbn) || "0".equals(ukmKbn) || Util.isNullOrEmpty(ukmName)) {
				ukmKbn = "0";
				ukmName = "C00025";
			}
		} catch (Exception ex) {
			// 正常に処理されませんでした
			ClientErrorHandler.handledException(ex);
		}
	}

	/**
	 * @return 名称
	 */
	public String getKnrName1() {
		if ("0".equals(knrKbn1)) {
			return this.getWord(knrName1);
		}
		return knrName1;
	}

	/**
	 * @return 名称
	 */
	public String getKnrName2() {
		if ("0".equals(knrKbn2)) {
			return this.getWord(knrName2);
		}
		return knrName2;
	}

	/**
	 * @return 名称
	 */
	public String getKnrName3() {
		if ("0".equals(knrKbn3)) {
			return this.getWord(knrName3);
		}
		return knrName3;
	}

	/**
	 * @return 名称
	 */
	public String getKnrName4() {
		if ("0".equals(knrKbn4)) {
			return this.getWord(knrName4);
		}
		return knrName4;
	}

	/**
	 * @return 名称
	 */
	public String getKnrName5() {
		if ("0".equals(knrKbn5)) {
			return this.getWord(knrName5);
		}
		return knrName5;
	}

	/**
	 * @return 名称
	 */
	public String getKnrName6() {
		if ("0".equals(knrKbn6)) {
			return this.getWord(knrName6);
		}
		return knrName6;
	}

	/**
	 * @return 名称
	 */
	public String getCmpHmName1() {
		if ("0".equals(cmpHmKbn1)) {
			return this.getWord(cmpHmName1);
		}
		return cmpHmName1;
	}

	/**
	 * @return 名称
	 */
	public String getCmpHmName2() {
		if ("0".equals(cmpHmKbn2)) {
			return this.getWord(cmpHmName2);
		}
		return cmpHmName2;
	}

	/**
	 * @return 名称
	 */
	public String getCmpHmName3() {
		if ("0".equals(cmpHmKbn3)) {
			return this.getWord(cmpHmName3);
		}
		return cmpHmName3;
	}

	/**
	 * @return 名称
	 */
	public String getKmkName() {
		if ("0".equals(kmkKbn)) {
			return this.getWord(kmkName);
		}
		return kmkName;
	}

	/**
	 * @return 名称
	 */
	public String getHkmName() {
		if ("0".equals(hkmKbn)) {
			return this.getWord(hkmName);
		}
		return hkmName;
	}

	/**
	 * @return 名称
	 */
	public String getUkmName() {
		if ("0".equals(ukmKbn)) {
			return this.getWord(ukmName);
		}
		return ukmName;
	}
}
