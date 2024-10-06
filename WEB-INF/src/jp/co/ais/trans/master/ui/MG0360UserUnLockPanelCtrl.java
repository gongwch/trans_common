package jp.co.ais.trans.master.ui;

import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 排他管理マスタコントロール
 */
public class MG0360UserUnLockPanelCtrl extends TPanelCtrlBase {

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0360UserUnLockServlet";

	/** パネル */
	private MG0360UserUnLockPanel panel;

	/** スプレッドCheckBox選択イベント */
	private ItemListener ssCbxListener = new ItemListener() {

		public void itemStateChanged(ItemEvent e) {
			int focusRow = panel.lockTable.getCurrentRow();
			panel.lockTable.setRowSelection(focusRow, focusRow);
		}
	};

	/** データソース */
	private JCVectorDataSource ds;

	/**
	 * パネル取得
	 * 
	 * @return パネル
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * コンストラクタ
	 */
	public MG0360UserUnLockPanelCtrl() {
		panel = new MG0360UserUnLockPanel(this);
		try {
			setSpreadData(new ArrayList<PCI_CHK_CTL>(0));

			// 画面更新
			reflesh();
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * テーブル更新 （排他ユーザリスト習得）
	 * 
	 * @throws IOException
	 */
	private void reflesh() throws IOException {
		addSendValues("flag", "find");

		// 送信
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return;
		}

		// サーブレットから送られてきた結果を配列にセット
		List<PCI_CHK_CTL> list = (List<PCI_CHK_CTL>) super.getResultObject();

		// 指定会社のログイン情報がないとリストを構成しない
		if (list.size() == 0) {
			// ボタンイベントを消す
			panel.btnSettle.setEnabled(false);
			setSpreadData(new ArrayList<PCI_CHK_CTL>(0));
			return;
		}

		// スプレッドシート構成
		setSpreadData(list);
	}

	/**
	 * データをテーブルに設定する。
	 * 
	 * @param list 排他テータリスト
	 */
	private void setSpreadData(List<PCI_CHK_CTL> list) {
		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>(list.size());

		for (PCI_CHK_CTL record : list) {
			Vector<String> colum = new Vector<String>(4);

			// VECTORにインデックスを使うため、値を設定
			colum.add(0, "");

			// ユーザコード
			colum.add(1, record.getUSR_ID());

			// ユーザ名
			colum.add(2, record.getUSR_NAME());

			// ログイン日時
			colum.add(3, DateUtil.toYMDHMSString(record.getPCI_CHECK_IN_TIME()));

			cells.add(colum);
		}

		// SSデータの構築
		ds = new JCVectorDataSource();

		// セルの挿入
		ds.setCells(cells);

		// カラムの数の指定
		ds.setNumColumns(4);
		ds.setNumRows(cells.size());

		// 対象ﾁｪｯｸﾎﾞｯｸｽの構築
		for (int i = 0; i < cells.size(); i++) {
			TCheckBox tCheckBox = new TCheckBox();
			tCheckBox.setOpaque(false);
			tCheckBox.setHorizontalAlignment(JCTableEnum.CENTER);
			tCheckBox.addItemListener(ssCbxListener);

			panel.lockTable.setComponent(i, 0, tCheckBox);
		}

		// データセット
		panel.lockTable.setDataSource(ds);
	}

	/**
	 * 選択された排他リスト解除
	 */
	protected void deleteDto() {
		// 送信
		try {
			List<String> list = new LinkedList<String>();

			// 画面に選択されたデータ
			for (int nomRow = 0; nomRow < ds.getNumRows(); nomRow++) {
				boolean status = ((TCheckBox) this.panel.lockTable.getComponent(nomRow, 0)).isSelected();

				if (status) {
					// チェックボックスが選択されている場合
					list.add((String) ds.getTableDataItem(nomRow, 1));
				}
			}

			// 一つ以上選択
			if (list.size() == 0) {
				showMessage(panel, "W00195");
				return;
			}

			addSendValues("flag", "delete");
			addSendList(list);

			if (!request(TARGET_SERVLET)) {
				errorHandler(panel);
				return;
			}

			// 画面更新
			reflesh();

			showMessage(panel, "I00008");// 登録成功

		} catch (Exception a) {
			errorHandler(panel.getParentFrame(), a, "E00009");
		}
	}
}
