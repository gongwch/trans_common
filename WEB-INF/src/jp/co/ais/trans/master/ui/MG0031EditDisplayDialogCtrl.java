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
 * 会社コントロールマスタDialogCtrl
 * 
 * @author yanwei
 */
public class MG0031EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 会社コントロールマスタダイアログ */
	protected MG0031EditDisplayDialog dialog;

	private REFDialogCtrl refCurName;

	/** 処理区分 */

	private boolean isUpdate;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MG0031EditDisplayDialogCtrl(Frame parent, String titleid) {
		// 会社コントロールマスタダイアログの初期化
		dialog = new MG0031EditDisplayDialog(parent, true, this, titleid);
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});
		// レート換算端数処理の初期化
		dialog.rdoRateConversionRoundDown.setSelected(true);
		// 仮受消費税端数処理の初期化
		dialog.rdoTemporaryReceivingConsumptionTaxRoundDown.setSelected(true);
		// 仮払消費税端数処理の初期化
		dialog.rdoTemporaryPaymentConsumptionTaxRoundDown.setSelected(true);

		dialog.ctrlItemName.getField().requestFocus();
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				checkClicked();
				dialog.ctrlNotAccountingDetailDivision1.getComboBox().addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ev) {
						checkClicked();
					}
				});
				dialog.ctrlNotAccountingDetailDivision2.getComboBox().addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ev) {
						checkClicked();
					}
				});
				dialog.ctrlNotAccountingDetailDivision3.getComboBox().addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent ev) {
						checkClicked();
					}
				});
			}
		});

		// 科目ダイアログを表示

		refCurName = new REFDialogCtrl(dialog.ctrlKeyCurrency, dialog.getParentFrame());
		refCurName.setTargetServlet("MG0300CurrencyMasterServlet");
		refCurName.setTitleID(getWord("C01985"));
		refCurName.setColumnLabelIDs("C00665", "C00881", "C00882");
		refCurName.setShowsMsgOnError(false);
		refCurName.addIgnoredButton(dialog.btnReturn);
		refCurName.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map<String, Object> keys = new HashMap<String, Object>();
				// 会社コードの設定

				keys.put("kind", "Currency");
				keys.put("kaiCode", getLoginUserCompanyCode());
				return keys;
			}
		});

		dialog.ctrlKeyCurrency.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				refCurName.refreshName();
				if (!Util.isNullOrEmpty(dialog.ctrlKeyCurrency.getValue().trim())
					&& Util.isNullOrEmpty(dialog.ctrlKeyCurrency.getNotice().getText().trim())) {
					showMessage(dialog, "W00081", dialog.ctrlKeyCurrency.getValue());
					dialog.ctrlKeyCurrency.getField().clearOldText();
					dialog.ctrlKeyCurrency.getField().requestFocus();
					return false;
				}
				return true;
			}
		});

	}

	/**
	 * @param cmpHmKbnMap
	 * @param jidMap
	 */
	public void setSelectMap(Map cmpHmKbnMap, Map jidMap) {
		// 非会計明細区分1-3の初期化
		this.fillItemsToComboBox(dialog.ctrlNotAccountingDetailDivision1.getComboBox(), cmpHmKbnMap);
		this.fillItemsToComboBox(dialog.ctrlNotAccountingDetailDivision2.getComboBox(), cmpHmKbnMap);
		this.fillItemsToComboBox(dialog.ctrlNotAccountingDetailDivision3.getComboBox(), cmpHmKbnMap);
		// 自動設定項目１-3の初期化
		this.fillItemsToComboBox(dialog.ctrlAutomaticSettingItem1.getComboBox(), jidMap, false);
		this.fillItemsToComboBox(dialog.ctrlAutomaticSettingItem2.getComboBox(), jidMap, false);
		this.fillItemsToComboBox(dialog.ctrlAutomaticSettingItem3.getComboBox(), jidMap, false);
	}

	/**
	 * 表示
	 */
	void show() {
		// 画面を表示する
		dialog.setVisible(true);
	}

	/**
	 * 値のセット
	 * 
	 * @param map セットする値
	 */

	void setValues(Map map) {
		boolean boo;
		// 会社コードの設定
		dialog.ctrlCompanyCode.setEnabled(true);
		dialog.ctrlCompanyCode.getField().setEditable(true);
		dialog.ctrlCompanyCode.setValue((String) map.get("kaiCode"));
		dialog.ctrlCompanyCode.getField().setEditable(false);
		// 会社名
		getKaiName((String) map.get("kaiCode"));

		// 科目名称
		dialog.ctrlItemName.setValue((String) map.get("cmpKmkName"));
		// 補助科目名称
		dialog.ctrlSubItemName.setValue((String) map.get("cmpHkmName"));
		// 内訳科目管理
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("cmpUkmKbn")));
		dialog.chkBreakDownItemManagement.setSelected(boo);
		// 内訳科目名称
		dialog.txtBreakDownItemManagement.setValue(map.get("cmpUkmName"));
		// 管理区分1-6
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrKbn1")));
		dialog.chkManagementDivision1.setSelected(boo);
		dialog.txtManagementDivision1.setValue(map.get("knrName1"));
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrKbn2")));
		dialog.chkManagementDivision2.setSelected(boo);
		dialog.txtManagementDivision2.setValue(map.get("knrName2"));
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrKbn3")));
		dialog.chkManagementDivision3.setSelected(boo);
		dialog.txtManagementDivision3.setValue(map.get("knrName3"));
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrKbn4")));
		dialog.chkManagementDivision4.setSelected(boo);
		dialog.txtManagementDivision4.setValue(map.get("knrName4"));
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrKbn5")));
		dialog.chkManagementDivision5.setSelected(boo);
		dialog.txtManagementDivision5.setValue(map.get("knrName5"));
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("knrKbn6")));
		dialog.chkManagementDivision6.setSelected(boo);
		dialog.txtManagementDivision6.setValue(map.get("knrName6"));
		// 非会計明細名称1-3
		this.setComboBoxSelectedItem(dialog.ctrlNotAccountingDetailDivision1.getComboBox(), (String) map
			.get("cmpHmKbn1"));
		dialog.txtNotAccountingDetailDivision1.setValue(map.get("cmpHmName1"));
		this.setComboBoxSelectedItem(dialog.ctrlNotAccountingDetailDivision2.getComboBox(), (String) map
			.get("cmpHmKbn2"));
		dialog.txtNotAccountingDetailDivision2.setValue(map.get("cmpHmName2"));
		this.setComboBoxSelectedItem(dialog.ctrlNotAccountingDetailDivision3.getComboBox(), (String) map
			.get("cmpHmKbn3"));
		dialog.txtNotAccountingDetailDivision3.setValue(map.get("cmpHmName3"));
		// 期首月
		dialog.numBeginningOfPeriodMonth.setValue((String) map.get("cmpKisyu"));
		// 自動設定項目１-3
		this.setComboBoxSelectedItem(dialog.ctrlAutomaticSettingItem1.getComboBox(), (String) map.get("jid1"));
		if (map.get("jid1").equals("")) {
			dialog.ctrlAutomaticSettingItem1.getComboBox().setSelectedIndex(0);
		}
		this.setComboBoxSelectedItem(dialog.ctrlAutomaticSettingItem2.getComboBox(), (String) map.get("jid2"));
		if (map.get("jid2").equals("")) {
			dialog.ctrlAutomaticSettingItem2.getComboBox().setSelectedIndex(0);
		}
		this.setComboBoxSelectedItem(dialog.ctrlAutomaticSettingItem3.getComboBox(), (String) map.get("jid3"));
		if (map.get("jid3").equals("")) {
			dialog.ctrlAutomaticSettingItem3.getComboBox().setSelectedIndex(0);
		}
		// 自動採番部桁数
		dialog.numAutomaticNumberingDigits.setValue((String) map.get("autoNoKeta"));
		// 伝票印刷区分
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("printKbn")));
		dialog.chkSlipPrintDivision.setSelected(boo);
		// 印刷時の初期値
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("printDef")));
		dialog.chkPrintTimeInitialValue.setSelected(boo);
		// 現場承認ﾌﾗｸﾞ
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("cmpGShoninFlg")));
		dialog.chkSiteApproval.setSelected(boo);
		// 経理承認ﾌﾗｸﾞ
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("cmpKShoninFlg")));
		dialog.chkAdministrationApproval.setSelected(boo);
		// 本邦通貨コード
		dialog.ctrlKeyCurrency.setValue((String) map.get("curCode"));

		String value;
		// レート換算端数処理
		value = (String) map.get("rateKbn");
		if ("0".equals(value)) {
			dialog.rdoRateConversionRoundDown.setSelected(true);
		} else {
			dialog.rdoRateConversionHalfAdjust.setSelected(true);
		}

		// 仮受消費税端数処理
		value = (String) map.get("kuKbn");
		if ("0".equals(value)) {
			dialog.rdoTemporaryReceivingConsumptionTaxRoundDown.setSelected(true);
			dialog.rdoTemporaryReceivingConsumptionRoundUp.setSelected(false);
			dialog.rdoTemporaryReceivingConsumptionTaxHalfAdjust.setSelected(false);
		} else if ("1".equals(value)) {
			dialog.rdoTemporaryReceivingConsumptionTaxRoundDown.setSelected(false);
			dialog.rdoTemporaryReceivingConsumptionRoundUp.setSelected(true);
			dialog.rdoTemporaryReceivingConsumptionTaxHalfAdjust.setSelected(false);
		} else {
			dialog.rdoTemporaryReceivingConsumptionTaxRoundDown.setSelected(false);
			dialog.rdoTemporaryReceivingConsumptionRoundUp.setSelected(false);
			dialog.rdoTemporaryReceivingConsumptionTaxHalfAdjust.setSelected(true);
		}
		// 仮受消費税端数処理
		value = (String) map.get("kbKbn");
		if ("0".equals(value)) {
			dialog.rdoTemporaryPaymentConsumptionTaxRoundDown.setSelected(true);
		} else {
			dialog.rdoTemporaryPaymentConsumptionTaxHalfAdjust.setSelected(true);
		}

		// 直接印刷区分
		boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("directKbn")));
		dialog.chkDirectPrintDivision.setSelected(boo);

		// 編集モードのときは摘要コード、摘要区分とが編集不可になる
		isUpdate = "update".equals(map.get("flag"));

		refCurName.refreshName();
		if (isUpdate) {
			dialog.ctrlItemName.getField().requestFocus();
		}

	}

	boolean checkDialog() {
		// 科目名称
		if (Util.isNullOrEmpty(dialog.ctrlItemName.getValue())) {
			showMessage(dialog, "I00002", "C00700");
			dialog.ctrlItemName.requestFocus();
			return false;
		}

		// 補助科目名称
		if (Util.isNullOrEmpty(dialog.ctrlSubItemName.getValue())) {
			showMessage(dialog, "I00002", "C00701");
			dialog.ctrlSubItemName.requestFocus();
			return false;
		}
		// 内訳科目名称
		if (dialog.chkBreakDownItemManagement.isSelected() == true
			&& Util.isNullOrEmpty(dialog.txtBreakDownItemManagement.getText())) {
			showMessage(dialog, "I00002", "C00702");
			dialog.txtBreakDownItemManagement.requestFocus();
			return false;
		}

		// 管理名称１
		if (dialog.chkManagementDivision1.isSelected() == true
			&& Util.isNullOrEmpty(dialog.txtManagementDivision1.getText())) {
			showMessage(dialog, "I00002", "C00703");
			dialog.txtManagementDivision1.requestFocus();
			return false;
		}

		// 管理名称2
		if (dialog.chkManagementDivision2.isSelected() == true
			&& Util.isNullOrEmpty(dialog.txtManagementDivision2.getText())) {
			showMessage(dialog, "I00002", "C00704");
			dialog.txtManagementDivision2.requestFocus();
			return false;
		}

		// 管理名称3
		if (dialog.chkManagementDivision3.isSelected() == true
			&& Util.isNullOrEmpty(dialog.txtManagementDivision3.getText())) {
			showMessage(dialog, "I00002", "C00705");
			dialog.txtManagementDivision3.requestFocus();
			return false;
		}
		// 管理名称4
		if (dialog.chkManagementDivision4.isSelected() == true
			&& Util.isNullOrEmpty(dialog.txtManagementDivision4.getText())) {
			showMessage(dialog, "I00002", "C00706");
			dialog.txtManagementDivision4.requestFocus();
			return false;
		}
		// 管理名称5
		if (dialog.chkManagementDivision5.isSelected() == true
			&& Util.isNullOrEmpty(dialog.txtManagementDivision5.getText())) {
			showMessage(dialog, "I00002", "C00707");
			dialog.txtManagementDivision5.requestFocus();
			return false;
		}

		// 管理名称6
		if (dialog.chkManagementDivision6.isSelected() == true
			&& Util.isNullOrEmpty(dialog.txtManagementDivision6.getText())) {
			showMessage(dialog, "I00002", "C00708");
			dialog.txtManagementDivision6.requestFocus();
			return false;
		}

		// 非会計明細区分1
		if (Util.isNullOrEmpty(dialog.ctrlNotAccountingDetailDivision1.getComboBox().getSelectedItem().toString())) {
			showMessage(dialog, "I00002", "C01294");
			dialog.ctrlNotAccountingDetailDivision1.requestFocus();
			return false;
		}

		// 非会計明細区分2
		if (Util.isNullOrEmpty(dialog.ctrlNotAccountingDetailDivision2.getComboBox().getSelectedItem().toString())) {
			showMessage(dialog, "I00002", "C00944");
			dialog.ctrlNotAccountingDetailDivision2.requestFocus();
			return false;
		}

		// 非会計明細区分３
		if (Util.isNullOrEmpty(dialog.ctrlNotAccountingDetailDivision3.getComboBox().getSelectedItem().toString())) {
			showMessage(dialog, "I00002", "C00945");
			dialog.ctrlNotAccountingDetailDivision3.requestFocus();
			return false;
		}
		// 非会計明細名称1
		if (!"0".equals(this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision1.getComboBox()))) {
			if (Util.isNullOrEmpty(dialog.txtNotAccountingDetailDivision1.getText())) {
				showMessage(dialog, "I00002", "C00709");
				dialog.txtNotAccountingDetailDivision1.requestFocus();
				return false;
			}
		}

		// 非会計明細名称２
		if (!"0".equals(this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision2.getComboBox()))) {
			if (Util.isNullOrEmpty(dialog.txtNotAccountingDetailDivision2.getText())) {
				showMessage(dialog, "I00002", "C00710");
				dialog.txtNotAccountingDetailDivision2.requestFocus();
				return false;
			}
		}

		// 非会計明細名称3
		if (!"0".equals(this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision3.getComboBox()))) {
			if (Util.isNullOrEmpty(dialog.txtNotAccountingDetailDivision3.getText())) {
				showMessage(dialog, "I00002", "C00711");
				dialog.txtNotAccountingDetailDivision3.requestFocus();
				return false;
			}
		}
		// 自動採番部桁数
		if (Util.isNullOrEmpty(dialog.numAutomaticNumberingDigits.getValue())) {
			showMessage(dialog, "I00002", "C00224");
			dialog.numAutomaticNumberingDigits.requestFocus();
			return false;
		}

		String strNum = dialog.numAutomaticNumberingDigits.getField().getText();
		if (!Util.isNullOrEmpty(strNum) && (20 < Integer.parseInt(strNum) || Integer.parseInt(strNum) < 1)) {
			// 警告メッセージ表示
			showMessage(dialog, "W00065", 1, 20);
			dialog.numAutomaticNumberingDigits.requestFocus();
			// エラーを返す
			return false;
		}

		// 期首月
		if (Util.isNullOrEmpty(dialog.numBeginningOfPeriodMonth.getValue())) {
			showMessage(dialog, "I00002", "C00105");
			dialog.numBeginningOfPeriodMonth.requestFocus();
			return false;
		}

		String strNumw = dialog.numBeginningOfPeriodMonth.getField().getText();
		if (!Util.isNullOrEmpty(strNumw) && (12 < Integer.parseInt(strNumw) || Integer.parseInt(strNumw) < 1)) {
			// 警告メッセージ表示
			showMessage(dialog, "W00065", 1, 12);
			dialog.numBeginningOfPeriodMonth.requestFocus();
			// エラーを返す
			return false;
		}
		// 現場承認
		if (!dialog.chkAdministrationApproval.isSelected() && dialog.chkSiteApproval.isSelected()) {
			showMessage(dialog, "W00206");
			dialog.chkAdministrationApproval.requestFocus();
			return false;

		}
		// 本邦通貨コード
		if (Util.isNullOrEmpty(dialog.ctrlKeyCurrency.getField().getText())) {
			showMessage(dialog, "I00002", "C00717");
			dialog.ctrlKeyCurrency.requestTextFocus();
			return false;
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
		// 会社コードの設定
		map.put("kaiCode", dialog.ctrlCompanyCode.getValue());
		// 科目名称
		map.put("cmpKmkName", dialog.ctrlItemName.getValue());
		// 補助科目名称
		map.put("cmpHkmName", dialog.ctrlSubItemName.getValue());
		// 内訳科目管理
		map.put("cmpUkmKbn", String.valueOf(BooleanUtil.toInt(dialog.chkBreakDownItemManagement.isSelected())));
		// 内訳科目名称
		map.put("cmpUkmName", dialog.txtBreakDownItemManagement.getValue());
		// 管理区分1-6
		map.put("knrKbn1", String.valueOf(BooleanUtil.toInt(dialog.chkManagementDivision1.isSelected())));
		map.put("knrKbn2", String.valueOf(BooleanUtil.toInt(dialog.chkManagementDivision2.isSelected())));
		map.put("knrKbn3", String.valueOf(BooleanUtil.toInt(dialog.chkManagementDivision3.isSelected())));
		map.put("knrKbn4", String.valueOf(BooleanUtil.toInt(dialog.chkManagementDivision4.isSelected())));
		map.put("knrKbn5", String.valueOf(BooleanUtil.toInt(dialog.chkManagementDivision5.isSelected())));
		map.put("knrKbn6", String.valueOf(BooleanUtil.toInt(dialog.chkManagementDivision6.isSelected())));
		// 管理名称1-6
		map.put("knrName1", dialog.txtManagementDivision1.getText());
		map.put("knrName2", dialog.txtManagementDivision2.getText());
		map.put("knrName3", dialog.txtManagementDivision3.getText());
		map.put("knrName4", dialog.txtManagementDivision4.getText());
		map.put("knrName5", dialog.txtManagementDivision5.getText());
		map.put("knrName6", dialog.txtManagementDivision6.getText());
		// 管理名称1-3
		map.put("cmpHmKbn1", this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision1.getComboBox()));
		map.put("cmpHmKbn2", this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision2.getComboBox()));
		map.put("cmpHmKbn3", this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision3.getComboBox()));
		// 非会計明細名称1-3
		map.put("cmpHmName1", dialog.txtNotAccountingDetailDivision1.getText());
		map.put("cmpHmName2", dialog.txtNotAccountingDetailDivision2.getText());
		map.put("cmpHmName3", dialog.txtNotAccountingDetailDivision3.getText());
		// 期首月
		map.put("cmpKisyu", dialog.numBeginningOfPeriodMonth.getField().getText());
		// 自動設定項目１-3
		map.put("jid1", this.getComboBoxSelectedValue(dialog.ctrlAutomaticSettingItem1.getComboBox()));
		map.put("jid2", this.getComboBoxSelectedValue(dialog.ctrlAutomaticSettingItem2.getComboBox()));
		map.put("jid3", this.getComboBoxSelectedValue(dialog.ctrlAutomaticSettingItem3.getComboBox()));
		// 自動採番部桁数
		map.put("autoNoKeta", dialog.numAutomaticNumberingDigits.getField().getText());
		// 伝票印刷区分
		map.put("printKbn", String.valueOf(BooleanUtil.toInt(dialog.chkSlipPrintDivision.isSelected())));
		// 印刷時の初期値
		map.put("printDef", String.valueOf(BooleanUtil.toInt(dialog.chkPrintTimeInitialValue.isSelected())));
		// 現場承認ﾌﾗｸﾞ
		map.put("cmpGShoninFlg", String.valueOf(BooleanUtil.toInt(dialog.chkSiteApproval.isSelected())));
		// 経理承認ﾌﾗｸﾞ
		map.put("cmpKShoninFlg", String.valueOf(BooleanUtil.toInt(dialog.chkAdministrationApproval.isSelected())));
		// 本邦通貨コード
		map.put("curCode", dialog.ctrlKeyCurrency.getValue());

		// レート換算端数処理
		if (dialog.rdoRateConversionRoundDown.isSelected()) {
			map.put("rateKbn", "0");
		} else {
			map.put("rateKbn", "2");
		}
		// 仮受消費税端数処理
		if (dialog.rdoTemporaryReceivingConsumptionTaxRoundDown.isSelected()) {
			map.put("kuKbn", "0");
		} else if (dialog.rdoTemporaryReceivingConsumptionRoundUp.isSelected()) {
			map.put("kuKbn", "1");
		} else {
			map.put("kuKbn", "2");
		}
		// 仮払消費税端数処理
		if (dialog.rdoTemporaryPaymentConsumptionTaxRoundDown.isSelected()) {
			map.put("kbKbn", "0");
		} else {
			map.put("kbKbn", "2");
		}

		// 直接印刷区分
		map.put("directKbn", String.valueOf(BooleanUtil.toInt(dialog.chkDirectPrintDivision.isSelected())));

		// 結果を返す
		return map;
	}

	void getKaiName(String kaiCode) {
		try {
			addSendValues("flag", "findone");
			addSendValues("kaiCode", kaiCode);

			if (!request("MG0010EnvironmentalSettingMasterServlet")) {
				// サーバ側のエラー場合
				errorHandler(dialog);
			}

			List result = getResultList();
			if (result != null && result.size() > 0) {
				// 会社略称
				String name_S = (String) ((List) result.get(0)).get(2);
				dialog.ctrlCompany.setText(name_S);
			}
		} catch (IOException e) {
			errorHandler(dialog);
		}
	}

	/**
	 * 
	 */
	public void checkClicked() {
		// 管理区分1-6
		dialog.txtBreakDownItemManagement.setEditable(dialog.chkBreakDownItemManagement.isSelected());
		dialog.txtManagementDivision1.setEditable(dialog.chkManagementDivision1.isSelected());
		dialog.txtManagementDivision2.setEditable(dialog.chkManagementDivision2.isSelected());
		dialog.txtManagementDivision3.setEditable(dialog.chkManagementDivision3.isSelected());
		dialog.txtManagementDivision4.setEditable(dialog.chkManagementDivision4.isSelected());
		dialog.txtManagementDivision5.setEditable(dialog.chkManagementDivision5.isSelected());
		dialog.txtManagementDivision6.setEditable(dialog.chkManagementDivision6.isSelected());

		// 管理名称1-6
		if (!dialog.chkBreakDownItemManagement.isSelected()) {
			dialog.txtBreakDownItemManagement.setValue("");
		}

		if (!dialog.chkManagementDivision1.isSelected()) {
			dialog.txtManagementDivision1.setValue("");
		}

		if (!dialog.chkManagementDivision2.isSelected()) {
			dialog.txtManagementDivision2.setValue("");
		}

		if (!dialog.chkManagementDivision3.isSelected()) {
			dialog.txtManagementDivision3.setValue("");
		}

		if (!dialog.chkManagementDivision4.isSelected()) {
			dialog.txtManagementDivision4.setValue("");
		}

		if (!dialog.chkManagementDivision5.isSelected()) {
			dialog.txtManagementDivision5.setValue("");
		}

		if (!dialog.chkManagementDivision6.isSelected()) {
			dialog.txtManagementDivision6.setValue("");
		}

		// 非会計明細名称1-3
		if (!Util.isNullOrEmpty(dialog.ctrlNotAccountingDetailDivision1.getComboBox().getSelectedItem().toString())
			&& !"0".equals(this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision1.getComboBox()))) {
			dialog.txtNotAccountingDetailDivision1.setEditable(true);
		} else {
			dialog.txtNotAccountingDetailDivision1.setValue("");
			dialog.txtNotAccountingDetailDivision1.setEditable(false);
		}

		if (!Util.isNullOrEmpty(dialog.ctrlNotAccountingDetailDivision2.getComboBox().getSelectedItem().toString())
			&& !"0".equals(this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision2.getComboBox()))) {
			dialog.txtNotAccountingDetailDivision2.setEditable(true);
		} else {
			dialog.txtNotAccountingDetailDivision2.setValue("");
			dialog.txtNotAccountingDetailDivision2.setEditable(false);
		}

		if (!Util.isNullOrEmpty(dialog.ctrlNotAccountingDetailDivision3.getComboBox().getSelectedItem().toString())
			&& !"0".equals(this.getComboBoxSelectedValue(dialog.ctrlNotAccountingDetailDivision3.getComboBox()))) {
			dialog.txtNotAccountingDetailDivision3.setEditable(true);
		} else {
			dialog.txtNotAccountingDetailDivision3.setValue("");
			dialog.txtNotAccountingDetailDivision3.setEditable(false);
		}
	}
}