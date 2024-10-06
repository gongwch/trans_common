package jp.co.ais.trans.master.ui;

import java.awt.event.*;
import java.io.*;
import java.util.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 排他管理マスタコントロール
 */
public class MG0380BatchUnLockPanelCtrl extends TPanelCtrlBase {

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0380BatchUnLockServlet";

	/** プログラムリスト */
	private List<PRG_MST> programList;

	/** ユーザリスト */
	private List<USR_MST> userList;

	/** パネル */
	private MG0380BatchUnLockPanel panel;

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
	public MG0380BatchUnLockPanelCtrl() {
		panel = new MG0380BatchUnLockPanel(this);

		try {
			initList(); // プログラム名称、排他ユーザ名称の取得
			setSpreadData(new ArrayList<BAT_CTL>(0));

			// 画面更新
			reflesh();
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * プログラム名称、ユーザ名称の取得
	 * 
	 * @throws IOException
	 */
	private void initList() throws IOException {

		// プログラム名称
		addSendValues("flag", "getProgram");
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return;
		}
		programList = (List<PRG_MST>) super.getResultObject();

		// ユーザ名称
		addSendValues("flag", "getUser");
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return;
		}
		userList = (List<USR_MST>) super.getResultObject();
	}

	/**
	 * テーブル更新 （排他バッチリスト習得）
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
		List<BAT_CTL> list = (List<BAT_CTL>) super.getResultObject();

		// 指定会社のログイン情報がないとリストを構成しない
		if (list.size() == 0) {
			// ボタンイベントを消す
			panel.btnSettle.setEnabled(false);
			setSpreadData(new ArrayList<BAT_CTL>(0));
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
	private void setSpreadData(List<BAT_CTL> list) {
		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>(list.size());

		final int ColNoChkBox = 0; // チェックボックス
		final int ColNoPrgID = 1; // プログラムコード
		final int ColNoPrgName = 2; // プログラム名称
		final int ColNoUsrID = 3; // 排他ユーザコード
		final int ColNoUsrName = 4; // 排他ユーザ名称
		final int ColNoBatDate = 5; // 排他実行日
		final int ColNoRecord = 6; // レコード

		for (BAT_CTL record : list) {
			Vector<Object> colum = new Vector<Object>(9);

			// VECTORにインデックスを使うため、値を設定
			colum.add(ColNoChkBox, "");

			// プログラムコード
			colum.add(ColNoPrgID, record.getBAT_ID());

			// プログラム名称
			colum.add(ColNoPrgName, getProgramName(record.getBAT_ID()));

			// 排他ユーザコード
			colum.add(ColNoUsrID, record.getUSR_ID());

			// 排他ユーザ名称
			colum.add(ColNoUsrName, getUserName(record.getUSR_ID()));

			// 排他実行日
			colum.add(ColNoBatDate, DateUtil.toYMDHMSString (record.getBAT_STR_DATE()));

			// データ
			colum.add(ColNoRecord, record);

			cells.add(colum);
		}

		// SSデータの構築
		ds = new JCVectorDataSource();

		// セルの挿入
		ds.setCells(cells);

		// カラムの数の指定
		ds.setNumColumns(6);
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
	 * プログラム名称取得
	 * 
	 * @param kbn プログラムコード
	 * @return プログラム名称
	 */
	private String getProgramName(String kbn) {
		for (PRG_MST program : programList) {
			if (program.getPRG_CODE().equals(kbn)) {
				return program.getPRG_NAME();
			}
		}
		return "";
	}

	/**
	 * ユーザ名称取得
	 * 
	 * @param kbn
	 * @return ユーザ名称
	 */
	private String getUserName(String kbn) {
		for (USR_MST user : userList) {
			if (user.getUSR_CODE().equals(kbn)) {
				return user.getUSR_NAME();
			}
		}
		return "";
	}

	/**
	 * 選択された排他リスト解除
	 */
	protected void deleteDto() {
		// 送信
		try {
			List<BAT_CTL> list = new LinkedList<BAT_CTL>();

			// 画面に選択されたデータ
			for (int nomRow = 0; nomRow < ds.getNumRows(); nomRow++) {
				boolean status = ((TCheckBox) this.panel.lockTable.getComponent(nomRow, 0)).isSelected();

				if (status) {
					// チェックボックスが選択されている場合
					list.add((BAT_CTL) ds.getTableDataItem(nomRow, 6));
				}
			}

			// 一つ以上選択
			if (list.size() == 0) {
				showMessage(panel, "W00195");
				return;
			}
			
			//	実行確認ﾒｯｾｰｼﾞ
			if (!showConfirmMessage(panel, "Q00016")) {
				return;
			}

			addSendValues("flag", "delete");
			addSendObject(list);

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
