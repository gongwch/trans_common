package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * 会社階層マスタ EDITダイアログ コントロール
 * 
 * @author ookawara
 */
public class OW0140EditCompanySelectionDialogCtrl extends TAppletClientBase {

	/** DIVESTMEENTダイアログ */
	private OW0140EditCompanySelectionDialog dialog;

	protected Vector<ArrayList<String>> cells = new Vector<ArrayList<String>>();

	protected Vector<Vector> cells1 = new Vector<Vector>();

	/**
	 * コンストラクタ 親フレーム
	 * 
	 * @param parent
	 * @param cells
	 * @param titleid
	 */
	OW0140EditCompanySelectionDialogCtrl(Frame parent, Vector<ArrayList<String>> cells, String titleid) {
		dialog = new OW0140EditCompanySelectionDialog(parent, true, this, titleid);
		dialog.setSize(585, 180);
		this.cells = cells;

	}

	/**
	 * 表示
	 * 
	 * @param isEnabledCurCode
	 */
	void show(boolean isEnabledCurCode) {
		dialog.setVisible(true);
		dialog.ctrlLowerCompany.setEnabled(isEnabledCurCode);
		dialog.ctrlUpperCompany.setEnabled(!isEnabledCurCode);
		dialog.btnReturn.setEnabled(true);
		dialog.btnSettle.setEnabled(true);
	}

	/**
	 * 値のセット
	 * 
	 * @param map
	 */
	void setValues(Map map) {

		// 編集モードのときはDIVESTMEENTが編集不可になる
		if (map.get("flag") == "lowerCom") {
			dialog.ctrlLowerCompany.setEnabled(false);
			dialog.ctrlUpperCompany.setEnabled(true);

			dialog.ctrlLowerCompany.setValue((String) map.get("kaiCode"));
			dialog.ctrlLowerCompany.setNoticeValue((String) map.get("lvlName"));

		} else {
			dialog.ctrlLowerCompany.setEnabled(true);
			dialog.ctrlUpperCompany.setEnabled(false);
			dialog.ctrlLowerCompany.requestTextFocus();

			dialog.ctrlUpperCompany.setValue((String) map.get("kaiCode"));
			dialog.ctrlUpperCompany.setNoticeValue((String) map.get("lvlName"));

		}
		for (int i = 0; i < cells.size(); i++) {
			String kaicodeTemp = (cells.get(i)).get(2);
			if (kaicodeTemp.compareToIgnoreCase((String) map.get("kaiCode")) != 0) {
				Vector<String> colum = new Vector<String>();

				colum.add(0, cells.get(i).get(2));
				colum.add(1, cells.get(i).get(16));

				cells1.add(colum);
			}
		}
	}

	/**
	 * 閉じる
	 */
	void disposeDialog() {
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
	}

	/**
	 * 確定ボタンが押されたかどうか
	 * 
	 * @return 確定の場合true
	 */
	boolean isSettle() {
		return dialog.isSettle;
	}

	boolean checkDialog() {
		// 上位会社コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlUpperCompany.getValue())) {
			// エラーメッセージ出力
			super.showMessage(dialog, "I00002", "C00745");
			dialog.ctrlUpperCompany.requestTextFocus();
			return false;
		}
		// 下位会社コードチェック
		if (Util.isNullOrEmpty(dialog.ctrlLowerCompany.getValue())) {
			// エラーメッセージ出力
			super.showMessage(dialog, "I00002", "C00746");
			dialog.ctrlLowerCompany.requestTextFocus();
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

		map.put("lowerkaiCode", dialog.ctrlLowerCompany.getValue());
		map.put("upperkaiCode", dialog.ctrlUpperCompany.getValue());
		map.put("lowerkaiName", dialog.ctrlLowerCompany.getNoticeValue());
		map.put("upperkaiName", dialog.ctrlUpperCompany.getNoticeValue());

		return map;
	}

	void doOwnerCompanyClick(TButtonField bfield) {
		try {
			OW0140REFDisplayDialogCtrl dialogRef = new OW0140REFDisplayDialogCtrl(this.dialog, cells1);

			// ｺｰﾄﾞ設定、自動検索
			if (!Util.isNullOrEmpty(bfield.getValue())) {
				dialogRef.setCode(bfield.getValue());
				dialogRef.condition(false);
			}

			dialogRef.show();

			if (dialogRef.isSettle()) {
				String[] info = dialogRef.getCurrencyInfo();
				bfield.setValue(info[0]);
				bfield.setNoticeValue(info[1]);
				this.dialog.btnSettle.transferFocus();

			}
		} catch (Exception e) {
			errorHandler(this.dialog, e, "E00009");
		}
	}

	boolean doOwnerCompanyLostFocus(TButtonField bfield) {

		try {
			if (!bfield.isValueChanged()) {
				return true;
			}

			// テキストフィールドに文字列が入力されていたときのみ有効
			String code = bfield.getValue();

			if (Util.isNullOrEmpty(code)) {
				bfield.setNoticeValue(""); // 空文字セット
				return true;
			}

			int codeIndex = getKatCodeIndex(cells1, code);

			if (codeIndex >= 0) {

				// 略称をセット
				bfield.setNoticeValue(Util.avoidNull(cells1.get(codeIndex).get(1)));

			} else {
				// 警告メッセージ表示（該当コードは存在しません）
				showMessage(dialog, "W00081", code);

				bfield.setNoticeValue("");
				bfield.clearOldText();
				bfield.requestTextFocus();

				return false;
			}

		} catch (Exception e) {
			// 正常に処理されませんでした
			errorHandler(dialog, e, "E00009");

			bfield.setNoticeValue("");
			bfield.clearOldText();
		}

		return true;
	}

	int getKatCodeIndex(Vector<Vector> cellsTemp, String kaiCode) {

		int kaicodenum = cellsTemp.size();
		for (int i = 0; i < kaicodenum; i++) {
			String kaicodeTemp = (String) (cellsTemp.get(i)).get(0);
			if (kaicodeTemp.compareToIgnoreCase(kaiCode) == 0) {
				return i;
			}
		}
		return -1;
	}

}
