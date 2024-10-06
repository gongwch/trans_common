package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.util.*;

/**
 * 消費税マスタダイアログコントロール
 */
public class MG0130EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** 消費税マスタダイアログ */
	protected MG0130EditDisplayDialog dialog;

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0130ConsumptionTaxMasterServlet";

	/** 処理区分 */
	private boolean isUpdate;

	/** 売上仕入区分 */
	private Map usKbnMap;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MG0130EditDisplayDialogCtrl(Frame parent, String titleid) {
		// 消費税マスタダイアログの初期化
		dialog = createDialog(parent, titleid);
		// タイトルを設定する

		dialog.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent ev) {
				dialog.isSettle = false;
				dialog.setVisible(false);
			}
		});

		// 集計区分の設定
		dialog.rdoDisUse.setSelected(true);
		dialog.numOutputOrder.setEditable(false);

		dialog.dtBeginDate.setValue(minInputDate);
		dialog.dtEndDate.setValue(maxInputDate);
		dialog.ctrlConsumptionTaxCode.getField().requestFocus();

	}

	/**
	 * @param parent 親コンテナー
	 * @param titleid タイトル
	 * @return ダイアログ
	 */
	protected MG0130EditDisplayDialog createDialog(Frame parent, String titleid) {
		return new MG0130EditDisplayDialog(parent, true, this, titleid);
	}

	/**
	 * @param map
	 */
	public void setUsKbnMap(Map map) {
		this.usKbnMap = map;

		this.fillItemsToComboBox(dialog.ctrlSalesPurchesesDivision.getComboBox(), usKbnMap, false);
	}

	/**
	 * 表示
	 * 
	 * @param isEnabledCurCode 消費税コードエリア編集可(true)、不可(false)
	 */
	void show(boolean isEnabledCurCode) {
		dialog.setVisible(true);
		dialog.ctrlConsumptionTaxCode.setEditable(isEnabledCurCode);
	}

	/**
	 * 値のセット
	 * 
	 * @param map セットする値
	 */
	void setValues(Map map) {
		// 消費税コードの設定
		dialog.ctrlConsumptionTaxCode.setValue((String) map.get("zeiCode"));
		// 消費税名称の設定
		dialog.ctrlConsumptionTaxName.setValue((String) map.get("zeiName"));
		// 消費税略称の設定
		dialog.ctrlConsumptionTaxAbbreviationName.setValue((String) map.get("zeiName_S"));
		// 消費税検索名称の設定
		dialog.ctrlConsumptionTaxNameForSearch.setValue((String) map.get("zeiName_K"));
		// 売上仕入区分の設定
		this.setComboBoxSelectedItem(dialog.ctrlSalesPurchesesDivision.getComboBox(), (String) map.get("usKbn"));
		// 消費税計算書出力順序の設定
		String boo = (String) map.get("szeiKeiKbn");
		if (Util.isNullOrEmpty(boo)) {
			dialog.rdoDisUse.setSelected(true);
			dialog.rdoUse.setSelected(false);
			dialog.numOutputOrder.getField().setEditable(false);
		} else {
			dialog.rdoDisUse.setSelected(false);
			dialog.rdoUse.setSelected(true);
			dialog.numOutputOrder.setEditable(true);
			dialog.numOutputOrder.setValue((String) map.get("szeiKeiKbn"));
		}
		// 消費税率の設定
		String numCon;
		DecimalFormat format = new DecimalFormat("##.0");
		numCon = format.format(Double.valueOf((String) map.get("zeiRate")));
		dialog.numConsumptionTaxRate.setValue(numCon);
		// 開始年月日の設定
		dialog.dtBeginDate.setValue((Date) map.get("strDate"));
		// 終了年月日の設定
		dialog.dtEndDate.setValue((Date) map.get("endDate"));

		// 編集モードのときは通貨コードが編集不可になる
		isUpdate = "update".equals(map.get("flag"));
		dialog.ctrlConsumptionTaxCode.setEditable(!isUpdate); // 2006/12/29 Yanwei
		if (isUpdate) {
			dialog.ctrlConsumptionTaxName.getField().requestFocus();
		}

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
		try { // 消費税コード
			if (Util.isNullOrEmpty(dialog.ctrlConsumptionTaxCode.getValue())) {
				showMessage(dialog, "I00002", "C00573");
				dialog.ctrlConsumptionTaxCode.requestFocus();
				return false;
			}

			if (!isUpdate && checkCode(dialog.ctrlConsumptionTaxCode.getValue())) {
				showMessage(dialog, "W00005", "C00174");
				dialog.ctrlConsumptionTaxCode.requestFocus();
				return false;
			}

			// 消費税名称
			if (Util.isNullOrEmpty(dialog.ctrlConsumptionTaxName.getValue())) {
				showMessage(dialog, "I00002", "C00774");
				dialog.ctrlConsumptionTaxName.requestFocus();
				return false;
			}

			// 消費税略称
			if (Util.isNullOrEmpty(dialog.ctrlConsumptionTaxAbbreviationName.getValue())) {
				showMessage(dialog, "I00002", "C00775");
				dialog.ctrlConsumptionTaxAbbreviationName.requestFocus();
				return false;
			}

			// 消費税検索名称
			if (Util.isNullOrEmpty(dialog.ctrlConsumptionTaxNameForSearch.getValue())) {
				showMessage(dialog, "I00002", "C00828");
				dialog.ctrlConsumptionTaxNameForSearch.requestFocus();
				return false;
			}

			// 売上仕入区分
			if (Util.isNullOrEmpty(dialog.ctrlSalesPurchesesDivision.getComboBox().getSelectedItem().toString())) {
				showMessage(dialog, "I00002", "C01283");
				dialog.ctrlSalesPurchesesDivision.getComboBox().requestFocus();
				return false;
			}

			// 消費税計算書
			if (!dialog.rdoDisUse.isSelected() && !dialog.rdoUse.isSelected()) {
				showMessage(dialog, "I00002", "C01176");
				dialog.rdoDisUse.requestFocus();
				return false;
			}

			if (!dialog.rdoDisUse.isSelected()) {
				if (Util.isNullOrEmpty(dialog.numOutputOrder.getValue())) {
					showMessage(dialog, "I00002", "C00776");
					dialog.numOutputOrder.requestFocus();
					return false;
				}
			}

			if (!dialog.rdoDisUse.isSelected()) {
				String value = dialog.numOutputOrder.getValue();
				if (!Util.isNullOrEmpty(value)) {
					if (Integer.parseInt(value) < 1 || 9 < Integer.parseInt(value)) {
						showMessage(dialog, "W00065", 1, 9);
						dialog.numOutputOrder.requestFocus();
						return false;
					}
				}
			}

			// 消費税率
			if (Util.isNullOrEmpty(dialog.numConsumptionTaxRate.getValue())) {
				showMessage(dialog, "I00002", "C00777");
				dialog.numConsumptionTaxRate.requestFocus();
				return false;
			}

			String value = dialog.numConsumptionTaxRate.getValue();
			if (!Util.isNullOrEmpty(value)
				&& (Float.parseFloat((dialog.numConsumptionTaxRate.getValue())) < 0 || 99.9f < Float
					.parseFloat(dialog.numConsumptionTaxRate.getValue().trim()))) {
				showMessage(dialog, "W00065", "0.0", "99.9");
				dialog.numConsumptionTaxRate.requestFocus();
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
		// 消費税コードの設定
		map.put("zeiCode", dialog.ctrlConsumptionTaxCode.getValue());
		// 消費税名称の設定
		map.put("zeiName", dialog.ctrlConsumptionTaxName.getValue());
		// 消費税略称の設定
		map.put("zeiName_S", dialog.ctrlConsumptionTaxAbbreviationName.getValue());
		// 消費税検索名称の設定
		map.put("zeiName_K", dialog.ctrlConsumptionTaxNameForSearch.getValue());
		// 売上仕入区分の設定
		map.put("usKbn", this.getComboBoxSelectedValue(dialog.ctrlSalesPurchesesDivision.getComboBox()));
		// 消費税計算書出力順序の設定
		map.put("szeiKeiKbn", dialog.numOutputOrder.getValue());
		// 消費税率の設定
		map.put("zeiRate", dialog.numConsumptionTaxRate.getValue());
		// 開始年月日の設定
		map.put("strDate", dialog.dtBeginDate.getValue());
		// 終了年月日の設定
		map.put("endDate", dialog.dtEndDate.getValue());
		// 結果を返す
		return map;
	}

	boolean checkCode(String code) throws IOException {
		// 科目コード未入力
		if (Util.isNullOrEmpty(code)) {
			return false;
		}
		// 処理種別の設定
		addSendValues("flag", "checkcode");
		// 会社コードの設定
		addSendValues("kaiCode", getLoginUserCompanyCode());
		// 消費税コードの設定
		addSendValues("zeiCode", code);

		if (!request(TARGET_SERVLET)) {
			errorHandler(dialog);
			return true;
		}
		// 結果を取得
		List result = super.getResultList();
		// 結果を返す
		return (result.size() > 0);
	}

	/**
	 * 消費税計算書の初期化
	 */
	public void selectedSet() {
		boolean boo = dialog.rdoUse.isSelected();
		if (boo) {
			dialog.numOutputOrder.setEditable(boo);
		} else {
			dialog.numOutputOrder.getField().setText("");
			dialog.numOutputOrder.setEditable(boo);

		}
	}
}
