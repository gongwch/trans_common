package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 
 */
public class MP0040EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 支払方法マスタダイアログ */
	protected MP0040EditDisplayDialog dialog;

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MP0040PaymentMethodMasterServlet";

	/** 処理区分 */
	protected boolean isUpdate;

	/** 処理区分 */
	protected boolean isInsert;

	/** 計上部門名称 */
	REFDialogCtrl refHohDepCode;

	/** 判定用FLAG */
	boolean flag1 = true;

	/** 判定用フラグ */
	boolean flag2 = true;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MP0040EditDisplayDialogCtrl(Frame parent, String titleid) {
		// 支払方法マスタダイアログの初期化
		dialog = createDialog(parent, titleid);
		// タイトルを設定する
		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});

		init();

		setItemCondition();
	}

	/**
	 * @param parent 親コンテナー
	 * @param titleid タイトル
	 * @return ダイアログ
	 */
	protected MP0040EditDisplayDialog createDialog(Frame parent, String titleid) {
		return new MP0040EditDisplayDialog(parent, true, this, titleid);
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
	private void init() {
		// 集計区分の設定
		dialog.rdoExternalPayment.setSelected(true);

		// 補助科目コードの初期化
		dialog.ctrlItemUnit.getTSubItemField().setEditable(false);
		dialog.ctrlItemUnit.getTSubItemField().setButtonEnabled(false);
		// 内訳科目コードの初期化
		dialog.ctrlItemUnit.getTBreakDownItemField().setEditable(false);
		dialog.ctrlItemUnit.getTBreakDownItemField().setButtonEnabled(false);
		// 計上部門コードチェック
		refHohDepCode = new REFDialogCtrl(dialog.ctrlAppropriateDepartment, dialog.getParentFrame());
		refHohDepCode.setColumnLabels("C00698", "C00724", "C00725");
		refHohDepCode.setTargetServlet("MG0060DepartmentMasterServlet");
		refHohDepCode.setTitleID("C02338");
		refHohDepCode.setShowsMsgOnError(false);
		refHohDepCode.addIgnoredButton(dialog.btnReturn);

		dialog.ctrlAppropriateDepartment.setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				refHohDepCode.refreshName();
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

		refHohDepCode.setREFListener(new REFAdapter() {

			@Override
			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "Department");
				return keys;
			}

		});

		dialog.dtBeginDate.setValue(PanelAndDialogCtrlBase.minInputDate);
		dialog.dtEndDate.setValue(PanelAndDialogCtrlBase.maxInputDate);

		dialog.ctrlPaymentMethodCode.getField().requestFocus();
	}

	// mapの初期化
	private Map hohNaiCodeMap;

	/**
	 * @param map
	 */
	public void setHohNaiCodeMap(Map map) {
		this.hohNaiCodeMap = map;

		this.fillItemsToComboBox(dialog.ctrlPaymentInternalCode.getComboBox(), hohNaiCodeMap);
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
		// 支払方法コードの設定
		dialog.ctrlPaymentMethodCode.setValue((String) map.get("hohHohCode"));
		// 支払方法名の設定
		dialog.ctrlPaymentMethodName.setValue((String) map.get("hohHohName"));
		// 支払方法検索名称の設定
		dialog.ctrlPaymentMethodNameForSearch.setValue((String) map.get("hohHohName_K"));

		String itemCode = Util.avoidNull(map.get("hohKmkCode"));
		String subItemCode = Util.avoidNull(map.get("hohHkmCode"));

		// 科目パラメータ
		dialog.ctrlItemUnit.getInputParameter().setItemCode(itemCode);
		dialog.ctrlItemUnit.getInputParameter().setSubItemCode(subItemCode);

		// 科目
		dialog.ctrlItemUnit.setItemCode(itemCode);
		// 補助科目
		dialog.ctrlItemUnit.setSubItemCode(subItemCode);
		// 内訳科目
		dialog.ctrlItemUnit.setBreakDownItemCode(Util.avoidNull(map.get("hohUkmCode")));
		// 計上部門コードの設定
		dialog.ctrlAppropriateDepartment.setValue((String) map.get("hohDepCode"));
		// 支払対象区分の設定
		boolean boo = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("hohSihKbn")));
		dialog.rdoExternalPayment.setSelected(boo);
		dialog.rdoEmployeePayment.setSelected(!boo);
		// 支払内部コードの設定
		this.setComboBoxSelectedItem(dialog.ctrlPaymentInternalCode.getComboBox(), (String) map.get("hohNaiCode"));
		// 開始年月日の設定
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// 終了年月日の設定
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// 編集モードのときは通貨コードが編集不可になる
		isUpdate = "update".equals(map.get("flag"));
		dialog.ctrlPaymentMethodCode.setEditable(isUpdate == false);

		isInsert = "insert".equals(map.get("flag"));
		// 支払方法コード
		if (isUpdate) {
			dialog.ctrlPaymentMethodName.getField().requestFocus();
		}
		refHohDepCode.refreshName();
	}

	/**
	 * 閉じる
	 */
	void disposeDialog() {

		if (dialog.isSettle) {
			// 確定ボタン押下 チェックOKなら閉じる
			if (!checkDialog()) return;
			dialog.setVisible(false);
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
		// 支払方法コードの設定
		map.put("hohHohCode", dialog.ctrlPaymentMethodCode.getValue());
		// 支払方法名の設定
		map.put("hohHohName", dialog.ctrlPaymentMethodName.getValue());
		// 支払方法検索名称の設定
		map.put("hohHohName_K", dialog.ctrlPaymentMethodNameForSearch.getValue());
		// 科目コードの設定
		map.put("hohKmkCode", dialog.ctrlItemUnit.getTItemField().getValue());
		// 補助科目コードの設定
		map.put("hohHkmCode", dialog.ctrlItemUnit.getTSubItemField().getValue());
		// 内訳科目コードの設定
		map.put("hohUkmCode", dialog.ctrlItemUnit.getTBreakDownItemField().getValue());
		// 計上部門コードの設定
		map.put("hohDepCode", dialog.ctrlAppropriateDepartment.getValue());
		// 支払対象区分の設定
		map.put("hohSihKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoExternalPayment.isSelected())));
		// 支払内部コードの設定
		map.put("hohNaiCode", this.getComboBoxSelectedValue(dialog.ctrlPaymentInternalCode.getComboBox()));
		// 開始年月日の設定
		map.put("strDate", dialog.dtBeginDate.getValue());
		// 終了年月日の設定
		map.put("endDate", dialog.dtEndDate.getValue());
		// 結果を返す
		return map;
	}

	boolean checkCode(String code) {
		try {
			// 支払方法コード未入力
			if (Util.isNullOrEmpty(code)) {
				return false;
			}
			// 処理種別の設定
			addSendValues("flag", "checkcode");
			// 会社コードの設定
			addSendValues("kaiCode", getLoginUserCompanyCode());
			// 支払方法コードの設定
			addSendValues("hohHohCode", code);

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
	public boolean checkDialog() {

		// 支払方法コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlPaymentMethodCode.getValue())) {
			showMessage(dialog, "I00002", "C00864");
			dialog.ctrlPaymentMethodCode.requestFocus();
			return false;
		}

		if (!isUpdate && checkCode(dialog.ctrlPaymentMethodCode.getValue())) {
			showMessage(dialog, "W00005", "C00174");
			dialog.ctrlPaymentMethodCode.requestFocus();
			return false;
		}

		// 支払方法名チェック
		if (Util.isNullOrEmpty(dialog.ctrlPaymentMethodName.getValue())) {
			showMessage(dialog, "I00002", "C00865");
			dialog.ctrlPaymentMethodName.requestFocus();
			return false;
		}

		// 支払方法検索名称チェック
		if (Util.isNullOrEmpty(dialog.ctrlPaymentMethodNameForSearch.getValue())) {
			showMessage(dialog, "I00002", "C00866");
			dialog.ctrlPaymentMethodNameForSearch.requestFocus();
			return false;
		}

		// 科目コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlItemUnit.getTItemField().getValue())) {
			showMessage(dialog, "I00002", "C00572");
			dialog.ctrlItemUnit.getTItemField().requestTextFocus();
			return false;
		}

		// 補助科目コードチェック
		if (dialog.ctrlItemUnit.getTSubItemField().getField().isEditable()
			&& Util.isNullOrEmpty(dialog.ctrlItemUnit.getTSubItemField().getValue())) {
			showMessage(dialog, "I00002", "C00890");
			dialog.ctrlItemUnit.getTSubItemField().requestTextFocus();
			return false;
		}

		// 内訳を使う場合のみチェック
		TCompanyInfo compInfo = TClientLoginInfo.getInstance().getCompanyInfo();
		if (compInfo.isUseBreakDownItem()) {
			// 内訳科目コードチェック
			if (dialog.ctrlItemUnit.getTBreakDownItemField().getField().isEditable()
				&& Util.isNullOrEmpty(dialog.ctrlItemUnit.getTBreakDownItemField().getValue())) {
				showMessage(dialog, "I00002", "C00603");
				dialog.ctrlItemUnit.getTBreakDownItemField().requestTextFocus();
				return false;
			}
		}

		// 計上部門コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlAppropriateDepartment.getValue())) {
			showMessage(dialog, "I00002", "C00863");
			dialog.ctrlAppropriateDepartment.requestTextFocus();
			return false;
		}

		// 支払対象区分チェック
		if (!dialog.rdoEmployeePayment.isSelected() && !dialog.rdoExternalPayment.isSelected()) {
			showMessage(dialog, "I00002", "C01096");
			dialog.rdoEmployeePayment.requestFocus();
			return false;
		}

		// 支払内部コードの設定
		if (Util.isNullOrEmpty(this.getComboBoxSelectedValue(dialog.ctrlPaymentInternalCode.getComboBox()))) {
			showMessage(dialog, "I00002", "C01097");
			dialog.ctrlPaymentInternalCode.getComboBox().requestFocus();
			return false;
		}

		// 支払内部コードの設定（社外支払いの場合、内部コード<=10だとエラー）
		if (dialog.rdoExternalPayment.isSelected()) {
			// 支払い内部コードが10以下のときはエラー
			if (dialog.ctrlPaymentInternalCode.getComboBox().getSelectedIndex() < 5) {
				// 警告メッセージ表示
				showMessage(dialog, "W00208");
				dialog.ctrlPaymentInternalCode.getComboBox().requestFocus();
				// エラーを返す
				return false;
			}
		}

		// 支払内部コードの設定（社員支払いの場合、内部コード>10だとエラー）
		if (dialog.rdoEmployeePayment.isSelected()) {
			// 支払い内部コードが10以下のときはエラー
			if (dialog.ctrlPaymentInternalCode.getComboBox().getSelectedIndex() > 4) {
				// 警告メッセージ表示
				showMessage(dialog, "W00209");
				dialog.ctrlPaymentInternalCode.getComboBox().requestFocus();
				// エラーを返す
				return false;
			}
		}

		// 開始年月日
		if (dialog.dtBeginDate.getValue() == null) {
			showMessage(dialog, "W00034", "C00055");
			dialog.dtBeginDate.requestFocus();
			return false;
		}

		// 終了年月日
		if (dialog.dtEndDate.getValue() == null) {
			showMessage(dialog, "W00034", "C00261");
			dialog.dtEndDate.requestFocus();
			return false;
		}

		// 開始年月日＜＝終了年月日にしてくださいチェック
		if (!Util.isNullOrEmpty(dialog.dtBeginDate.getValue()) && !Util.isNullOrEmpty(dialog.dtEndDate.getValue())) {
			if (Util.isSmallerThenByYMD(dialog.dtBeginDate.getValue(), dialog.dtEndDate.getValue()) == false) {
				showMessage(dialog, "W00035", "");
				dialog.dtBeginDate.getCalendar().requestFocus();
				return false;
			}
		}

		return true;
	}
}
