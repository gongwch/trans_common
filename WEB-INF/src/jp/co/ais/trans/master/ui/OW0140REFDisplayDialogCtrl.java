package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.util.*;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;

/**
 * 会社階層マスタ Refダイアログ コントロール
 * 
 * @author liuguozheng
 */
public class OW0140REFDisplayDialogCtrl extends TAppletClientBase {

	/** ダイアログ */
	private OW0140REFDisplayDialog dialog;

	/** 処理サーブレット */

	protected Vector<Vector> cells = new Vector<Vector>();

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param cells
	 */
	OW0140REFDisplayDialogCtrl(Dialog parent, Vector<Vector> cells) {
		dialog = new OW0140REFDisplayDialog(parent, true, this);
		dialog.setSize(650, 500);
		dialog.txtCode.setMaxLength(10);
		dialog.txtNameForSearch.setMaxLength(20);

		this.cells = cells;

		lockBtn(cells.size() != 0);
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
	 * 現在選択されているセルの会社コードを取得
	 * 
	 * @return String[]
	 */
	String[] getCurrencyInfo() {

		// 選択されている行の1つ目のカラムを取得
		int numRow = dialog.ssJournal.getCurrentRow();
		TableDataModel model = dialog.ssJournal.getDataView();

		String curCode = (String) model.getTableDataItem(numRow, 0); // 会社コード
		String curName = (String) model.getTableDataItem(numRow, 1); // 会社名称

		return new String[] { curCode, curName };
	}

	/**
	 * 確定ボタン制御
	 * 
	 * @param bol
	 */
	void lockBtn(boolean bol) {
		dialog.btnSettle.setEnabled(bol);

	}

	/**
	 * 閉じる
	 */
	void disposeDialog() {
		dialog.setVisible(false);
	}

	/**
	 * 検索処理
	 */
	void condition() {

		condition(true);
	}

	/**
	 * 検索処理
	 * 
	 * @param msgFlg メッセージ表示フラグ true：表示 false:表示しない
	 */
	void condition(boolean msgFlg) {

		this.reflesh(msgFlg);
	}

	/**
	 * 画面リフレッシュ
	 * 
	 * @param msgFlg メッセージ表示フラグ true：表示 false:表示しない
	 */
	@SuppressWarnings("deprecation")
	private void reflesh(boolean msgFlg) {

		String code = Util.avoidNull(dialog.txtCode.getValue());
		String name = Util.avoidNull(dialog.txtNameForSearch.getValue());

		Vector<Vector> sarchCells = new Vector<Vector>();

		boolean checkCode = !Util.isNullOrEmpty(code);
		boolean checkName = !Util.isNullOrEmpty(name);

		for (int i = 0; i < cells.size(); i++) {
			boolean existCode = true;
			boolean existName = true;

			if (checkCode) {
				existCode = ((String) cells.get(i).get(0)).indexOf(code) != -1;
			}
			if (checkName) {
				existName = ((String) cells.get(i).get(1)).indexOf(name) != -1;
			}

			if (existCode && existName) {
				Vector<String> colum = new Vector<String>();
				colum.add(0, (String) cells.get(i).get(0));
				colum.add(1, (String) cells.get(i).get(1));
				sarchCells.add(colum);
			}
		}

		this.setDataList(sarchCells);

		if (sarchCells.size() == 0) {
			if (msgFlg) {
				super.showMessage(dialog, "W00100");
			}
			dialog.txtCode.requestFocus();
			lockBtn(false);
		} else {
			dialog.ssJournal.requestFocus();
			lockBtn(true);
		}
	}

	/**
	 * スプレッドデータの設定
	 * 
	 * @param cells スプレッドデータ
	 */
	void setDataList(Vector cells) {

		dialog.ds.setCells(cells);

		// Set the number of rows in the data source.
		dialog.ds.setNumRows(cells.size());

		dialog.ssJournal.setDataSource(dialog.ds);

		if (cells.size() > 0) {
			// 指定rowにフォーカスを当てる
			dialog.ssJournal.clearSelectedCells();
			dialog.ssJournal.setRowSelection(0, 0);
			dialog.ssJournal.setCurrentCell(0, 0);
		}
	}

	/**
	 * REF画面のｺｰﾄﾞを設定する
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		dialog.txtCode.setValue(code);
	}

}
