package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;

/**
 * 会社階層マスタ 新規組織ダイアログ コントロール
 * 
 * @author ookawara
 */
public class OW0140EditNewOrganizationDialogCtrl extends TAppletClientBase {

	/** DIVESTMEENTダイアログ */
	private OW0140EditNewOrganizationDialog dialog;

	/** 処理サーブレット */
	private static final String TRAGET_SERVLET = "OW0140CompanyHierarchicalMasterServlet";

	private String kaiCode = "";

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param titleID
	 */
	OW0140EditNewOrganizationDialogCtrl(Frame parent, String titleID) {
		dialog = new OW0140EditNewOrganizationDialog(parent, true, this, titleID);
		dialog.setSize(720, 160);
		kaiCode = getLoginUserCompanyCode();
	}

	/**
	 * 表示
	 */
	void show() {
		dialog.setVisible(true);
	}

	/**
	 * 閉じる
	 */
	void disposeDialog() {
		try {
			// エラーがある場合にはダイアログを閉じない
			if (dialog.isSettle) {
				if (checkDialog()) {
					dialog.setVisible(false);
				} else {
					dialog.isSettle = false;
				}
			} else {
				dialog.setVisible(false);
			}
		} catch (IOException e) {
			// errorHandler(dialog.getParentFrame(), e, "E00009");
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

	boolean checkDialog() throws IOException {
		// 組織コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlOrganizationCode.getValue())) {
			// エラーメッセージ出力
			super.showMessage(dialog, "I00002", "C00335");
			dialog.ctrlOrganizationCode.requestTextFocus();
			return false;
		}
		// レベル０コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlLevel0.getValue())) {
			// エラーメッセージ出力
			super.showMessage(dialog, "I00002", "C00748");
			dialog.ctrlLevel0.requestTextFocus();
			return false;
		}
		if (isHavedOrgCode(this.kaiCode, (dialog.ctrlOrganizationCode.getValue()).trim())) {

			dialog.ctrlOrganizationCode.setValue("");
			dialog.ctrlOrganizationCode.requestTextFocus();
			return false;
		}
		return true;
	}

	/**
	 * 画面上表示データの取得
	 * 
	 * @return データ
	 */
	Map getDataList() {
		Map<String, Object> map = new TreeMap<String, Object>();

		map.put("orgCode", dialog.ctrlOrganizationCode.getValue());
		map.put("lvl0Code", dialog.ctrlLevel0.getValue());
		map.put("lvl0Name", dialog.ctrlLevel0.getNoticeValue());

		return map;
	}

	boolean doOwnerCompanyLostFocus(TButtonField bfield) {

		try {
			if (Util.isNullOrEmpty(bfield.getValue())) {
				bfield.setNoticeValue(""); // 空文字セット
				return true;
			}

			// テキストフィールドに文字列が入力されていたときのみ有効
			String code = bfield.getValue();

			if (Util.isNullOrEmpty(code)) {
				bfield.setNoticeValue(""); // 空文字セット
				bfield.clearOldText();

				return true;
			}

			addSendValues("flag", "findCompanyName");
			addSendValues("kaiCode", code);

			// 送信

			if (!request(TRAGET_SERVLET)) {
				// 警告メッセージ表示（該当コードは存在しません）
				errorHandler(dialog);

				bfield.clearOldText();
				bfield.requestTextFocus();

				return false;
			}

			// 略称をセット
			bfield.setNoticeValue(Util.avoidNull(getResult().get("name")));

			return true;

		} catch (IOException e) {
			// 正常に処理されませんでした
			errorHandler(dialog, e, "E00009");

			bfield.setValue("");
			bfield.setNoticeValue("");

			return false;
		}
	}

	/**
	 * 開始（終了）検索処理
	 * 
	 * @param bfield
	 */
	void doOwnerCompanyClick(TButtonField bfield) {
		try {
			REFDialogMasterCtrl dialogRef = new REFDialogMasterCtrl(this.dialog, REFDialogMasterCtrl.ENV_MST);

			// ｺｰﾄﾞ設定、自動検索
			if (!Util.isNullOrEmpty(bfield.getValue())) {
				dialogRef.setCode(bfield.getValue());
				dialogRef.searchData(false);
			}

			dialogRef.show();

			if (dialogRef.isSettle()) {
				String[] info = dialogRef.getCurrencyInfo();
				bfield.setValue(info[0]);
				bfield.setNoticeValue(info[1]);
			}
			bfield.requestTextFocus();

		} catch (Exception e) {
			errorHandler(dialog, e, "E00009");
		}
	}

	/**
	 * @param compCode
	 * @param sskCode
	 * @return boolean
	 * @throws IOException
	 */
	boolean isHavedOrgCode(String compCode, String sskCode) throws IOException {
		super.clearSendValues();
		super.addSendValues("flag", "getSskCode");

		super.addSendValues("kaiCode", compCode);
		super.addSendValues("sskCode", sskCode);

		if (!request(TRAGET_SERVLET)) {
			errorHandler(dialog);
		}
		return false;

	}

}
