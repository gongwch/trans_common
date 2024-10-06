package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.util.*;

/**
 * MG0040決算コントロールマスタ
 */
public class MG0040EditDisplayDialogCtrl extends PanelAndDialogCtrlBase {

	/** GLコントロールマスタダイアログ */
	protected MG0040EditDisplayDialog dialog;

	/**
	 * 接続先Servlet名称
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "MG0040GLControlMasterServlet";
	}

	/** 処理区分 */

	protected boolean isUpdate;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleid
	 */
	MG0040EditDisplayDialogCtrl(Frame parent, String titleid) {
		// GLコントロールマスタダイアログの初期化
		dialog = new MG0040EditDisplayDialog(parent, true, this, titleid);
		dialog.numClosingAccountsStage.getField().setMaxLength(1);
		// ダイアログを閉じ時、dialog.isSettle = true;
		dialog.addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent ev) {
				dialog.btnReturn.doClick();
			}
		});
		// 決算段階区分の初期化
		dialog.rdoDo.setSelected(true);
		// 決算伝票入力区分の初期化
		dialog.rdoOneYear.setSelected(true);
		// 元帳日別残高表示区分の初期化
		dialog.rdoVisible.setSelected(true);
		// 評価替レート区分の初期化
		dialog.rdoEndMonthRate.setSelected(true);

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
		// 会社コード
		dialog.ctrlCompanyCode.setValue((String) map.get("kaiCode"));
		getKaiName((String) map.get("kaiCode"));
		// 決算段階区分
		String value = (String) map.get("ksdKbn");
		if ("0".equals(value)) {
			dialog.rdoDo.setSelected(false);
			dialog.rdoDonot.setSelected(true);
			dialog.numClosingAccountsStage.getField().setEditable(false);
		} else {
			dialog.rdoDonot.setSelected(false);
			dialog.rdoDo.setSelected(true);
			dialog.numClosingAccountsStage.setValue(value);
		}
		// 決算伝票入力区分
		String i = (String) map.get("ksnNyuKbn");
		if ("0".equals(i)) {
			dialog.rdoOneYear.setSelected(true);
		} else if ("1".equals(i)) {
			dialog.rdoHalfYear.setSelected(true);
		} else if ("2".equals(i)) {
			dialog.rdoQuarter.setSelected(true);
		} else if ("3".equals(i)) {
			dialog.rdoMonthly.setSelected(true);
		}
		// 元帳日別残高表示区分
		boolean boo2 = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("mtZanHyjKbn")));
		dialog.rdoVisible.setSelected(boo2);
		dialog.rdoNotVisible.setSelected(!boo2);
		// 評価替レート区分
		boolean boo3 = BooleanUtil.toBoolean(Integer.parseInt((String) map.get("excRateKbn")));
		dialog.rdoEndMonthRate.setSelected(!boo3);
		dialog.rdoNextMonthBeginRate.setSelected(boo3);

		// 編集モードのときは通貨コードが編集不可になる
		isUpdate = "save".equals(map.get("flag"));
		dialog.ctrlCompanyCode.getField().setEditable(!isUpdate);
		dialog.rdoDo.requestFocus();

	}

	boolean checkDialog() {
		// 決算段階区分
		if (!dialog.rdoDo.isSelected() && !dialog.rdoDonot.isSelected()) {
			showMessage(dialog, "I00002", "C00718");
			dialog.rdoDo.requestFocus();
			return false;
		}
		if (dialog.rdoDo.isSelected() && Util.isNullOrEmpty(dialog.numClosingAccountsStage.getField().getText())) {
			showMessage(dialog, "I00002", "C00718");
			dialog.numClosingAccountsStage.requestFocus();
			return false;
		}

		String value = dialog.numClosingAccountsStage.getValue();
		if (dialog.rdoDo.isSelected() && !Util.isNullOrEmpty(value)
			&& (Integer.parseInt(value) < 1 || 9 < Integer.parseInt(value))) {
			showMessage(dialog, "W00065", 1, 9);
			dialog.numClosingAccountsStage.requestFocus();
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

		// 決算段階区分
		String value;
		if (dialog.rdoDo.isSelected()) {
			value = dialog.numClosingAccountsStage.getField().getText();
		} else {
			value = "0";
		}
		map.put("ksdKbn", value);

		String i;
		if (dialog.rdoOneYear.isSelected()) {
			i = "0";
		} else if (dialog.rdoHalfYear.isSelected()) {
			i = "1";
		} else if (dialog.rdoQuarter.isSelected()) {
			i = "2";
		} else {
			i = "3";
		}
		map.put("ksnNyuKbn", i);
		// 決算伝票入力区分

		// 元帳日別残高表示区分
		map.put("mtZanHyjKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoNotVisible.isSelected() == false)));
		// 評価替レート区分
		map.put("excRateKbn", String.valueOf(BooleanUtil.toInt(dialog.rdoEndMonthRate.isSelected() == false)));
		// 結果を返す
		return map;
	}

	boolean checkCode(String code) {
		try {
			// コード未入力
			if (Util.isNullOrEmpty(code)) {
				return false;
			}
			// 処理種別の設定
			addSendValues("flag", "checkcode");
			// 会社コードの設定
			addSendValues("kaiCode", getLoginUserCompanyCode());

			if (!request(getServletName())) {
				// サーバ側のエラー場合
				errorHandler(dialog);
			}

			// 結果を取得
			List result = super.getResultList();
			// 結果を返す
			return (result.size() > 0);
		} catch (IOException e) {
			errorHandler(dialog);
			return false;
		}
	}

	/**
	 * 
	 */
	public void selectedSet() {
		boolean boo = dialog.rdoDo.isSelected();
		if (boo) {
			dialog.numClosingAccountsStage.setEditable(boo);
		} else {
			dialog.numClosingAccountsStage.setValue("");
			dialog.numClosingAccountsStage.setEditable(boo);
		}
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
				dialog.txtCompanyName.setText(name_S);
			}
		} catch (IOException e) {
			errorHandler(dialog);
		}
	}

}
