package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;

/**
 * 任意選択(個別指定)コンポーネントのコントローラ基底クラス
 * 
 * @author AIS
 */
public abstract class TOptionalSelectorController extends TController {

	/** 任意選択(個別指定)コンポーネント */
	protected TOptionalSelector field;

	/** 個別選択ダイアログ */
	protected TOptionalSelectorDialog dialog = null;

	/** ダイアログが開かれた時のデータ(戻るボタン時の退避用) */
	protected Vector dataWhenOpened = null;

	/** ダイアログが開かれた時のデータ(戻るボタン時の退避用) */
	protected Vector dataSelectedWhenOpened = null;

	/**
	 * コンストラクタ
	 * 
	 * @param field 任意選択コンポーネント
	 */
	public TOptionalSelectorController(TOptionalSelector field) {

		this.field = field;

		// 初期化
		init();

	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * 初期処理
	 */
	protected void init() {

		// イベントを追加する
		addEvent();

		field.cbo.setEnabled(false);
	}

	/** 条件テキストリスナ */
	protected KeyListener txtListener = new KeyAdapter() {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				e.consume();

				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				loadListData();
				filterListData();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	};

	/**
	 * イベント定義
	 */
	protected void addEvent() {

		// 個別選択ボタン押下
		field.btn.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btn_Click();
			}
		});

	}

	/**
	 * ダイアログの初期処理
	 */
	protected void initDialog() {

		// イベント定義
		addDialogEvent();

		// ダイアログのタイトル
		dialog.setTitle(getWord(getDialogCaption()));

		// 選択一覧初期化
		initTable();
		refresh();
		saveListData();
		
	}

	/**
	 * 個別選択ダイアログのイベント定義
	 */
	protected void addDialogEvent() {
		// 各条件(テキスト)
		dialog.txtSearchCode.addKeyListener(txtListener);
		dialog.txtSearchName.addKeyListener(txtListener);

		// 一覧から選択ボタン押下
		dialog.btnTableSelect.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnTableSelect_Click();
			}
		});

		// 一覧から選択を取消ボタン押下
		dialog.btnTableCancel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnTableCancel_Click();
			}
		});

		// 確定ボタン押下
		dialog.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnSettle_Click(e);
			}
		});

		// 戻るボタン押下
		dialog.btnBack.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				btnBack_Click();
			}
		});

	}

	/**
	 * 個別選択ボタン押下
	 */
	protected void btn_Click() {

		// 個別選択ダイアログ取得
		if (dialog == null) {
			dialog = getSelectorDialog();
			initDialog();
		}

		dialog.txtSearchCode.clear();
		dialog.txtSearchName.clear();
		loadItemLevel();
		loadListData();
		filterListData();

		// 表示
		dialog.setVisible(true);

	}

	/**
	 * [一覧から選択]ボタン押下
	 */
	protected abstract void btnTableSelect_Click();

	/**
	 * [一覧から選択を取消]ボタン押下
	 */
	protected abstract void btnTableCancel_Click();

	/**
	 * [確定]ボタン押下
	 * 
	 * @param e
	 */
	protected void btnSettle_Click(@SuppressWarnings("unused") ActionEvent e) {

		// 1000件以上は不可
		if (dialog.tblSelectedList.getRowCount() > 1000) {
			super.showMessage(dialog, "I00438"); // 任意選択は1000件以下にしてください。
			return;
		}

		btnSettle_Click();
		saveSelectedListData();

	}

	/**
	 * [確定]ボタン押下
	 */
	protected abstract void btnSettle_Click();

	/**
	 * [戻る]ボタン押下
	 */
	protected void btnBack_Click() {

		dialog.setVisible(false);

	}

	/**
	 * selectorのdialogのファクトリ
	 * 
	 * @return selectorのdialog
	 */
	protected abstract TOptionalSelectorDialog getSelectorDialog();

	/**
	 * 検索ダイアログのキャプションを返す
	 * 
	 * @return 検索ダイアログのキャプション
	 */
	protected abstract String getDialogCaption();

	/**
	 * テーブルの初期化
	 */
	protected abstract void initTable();

	/**
	 * 選択候補テーブルの一覧をリフレッシュ(再取得)
	 */
	public abstract void refresh();

	/**
	 * @return ダイアログ取得
	 */
	public TOptionalSelectorDialog getDialog() {
		return dialog;
	}

	/**
	 * @param dialog
	 */
	public void setDialog(TOptionalSelectorDialog dialog) {
		this.dialog = dialog;
	}

	/**
	 * 選択されたコードリストを返す
	 * 
	 * @return 選択されたコードリスト
	 */
	public abstract List<String> getCodeList();

	/**
	 * クリアする
	 */
	public void clear() {
		dataWhenOpened = null;
		dataSelectedWhenOpened = null;
		field.cbo.removeAllItems();
		field.cbo.setEnabled(false);
		if (dialog != null) {
			dialog.tblList.removeRow();
			dialog.tblSelectedList.removeRow();
			refresh();
		}
	}

	/**
	 * リストの絞込み
	 */
	public void filterListData() {

		int codeCheck = 0;
		int namesCheck = 0;
		int codeCol = 0;
		int namesCol = 1;

		// 絞込み
		for (int i = dialog.tblList.getRowCount() - 1; i >= 0; i--) {
			if (!Util.isNullOrEmpty(dialog.txtSearchCode.getText())) {
				codeCheck =
					dialog.tblList.getRowValueAt(
						i, codeCol).toString().indexOf(dialog.txtSearchCode.getText());
			}
			if (!Util.isNullOrEmpty(dialog.txtSearchName.getText())) {
				namesCheck = 
					dialog.tblList.getRowValueAt(
						i, namesCol).toString().indexOf(dialog.txtSearchName.getText());
			}
			
			if (codeCheck != 0 || namesCheck == -1) {
				// 絞込み対象外ならリストから削除
				dialog.tblList.removeRow(i);

			} else {
				// 絞込み対象でも選択済みなら削除
				for (int j = 0; j < dialog.tblSelectedList.getRowCount(); j++) {
					if (dialog.tblList.getRowValueAt(i, codeCol).equals(
						dialog.tblSelectedList.getRowValueAt(j, codeCol))) {
						// リストから削除
						dialog.tblList.removeRow(i);

						break;
					}
				}
			}
		}
	}

	/**
	 * 検索リストの退避
	 */
	public void saveListData() {

		// データを退避
		DefaultTableModel tableModel = (DefaultTableModel) dialog.tblList.getModel();
		dataWhenOpened = (Vector) tableModel.getDataVector().clone();

		if (dialog.tblList.getRowCount() > 0) {
			dialog.tblList.setRowSelectionInterval(0, 0);
		}

	}

	/**
	 * 選択リストの退避
	 */
	public void saveSelectedListData() {

		// データを退避
		DefaultTableModel tableSelectedModel = (DefaultTableModel) dialog.tblSelectedList.getModel();
		dataSelectedWhenOpened = (Vector) tableSelectedModel.getDataVector().clone();

		if (dialog.tblSelectedList.getRowCount() > 0) {
			dialog.tblSelectedList.setRowSelectionInterval(0, 0);
		}

	}

	/**
	 * 科目レベルの戻し
	 */
	public void loadItemLevel() {
		return;
	}
	
	/**
	 * 検索リストの戻し
	 */
	public void loadListData() {

		// 退避から戻す
		dialog.tblList.removeRow();
		dialog.tblSelectedList.removeRow();

		DefaultTableModel tableModel = (DefaultTableModel) dialog.tblList.getModel();
		if (dataWhenOpened != null) {
			for (int i = 0; i < dataWhenOpened.size(); i++) {
				tableModel.addRow((Vector) dataWhenOpened.get(i));
			}
		}

		DefaultTableModel tableSelectedModel = (DefaultTableModel) dialog.tblSelectedList.getModel();
		if (dataSelectedWhenOpened != null) {
			for (int i = 0; i < dataSelectedWhenOpened.size(); i++) {
				tableSelectedModel.addRow((Vector) dataSelectedWhenOpened.get(i));
			}
		}

	}

	/**
	 * @param codeList
	 */
	public void setCodeList(List<String> codeList) {
		// 
		
	}

}