package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ロックアウト画面コントロール
 */
public class MG0027LockOutMasterPanelCtrl extends TPanelCtrlBase {

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0027LockOutMasterServlet";

	/** 処理サーブレット(ユーザ認証） */
	private static final String TARGET_SERVLET_AUTH = "MG0026UserAuthUpdateServlet";

	/** パネル */
	public MG0027LockOutMasterPanel panel;

	/** スプレッドCheckBox選択イベント */
	private ItemListener ssCbxListener = new ItemListener() {

		public void itemStateChanged(ItemEvent e) {
			int focusRow = panel.lockTable.getCurrentRow();
			panel.lockTable.setRowSelection(focusRow, focusRow);
		}
	};

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
	public MG0027LockOutMasterPanelCtrl() {
		panel = new MG0027LockOutMasterPanel(this);
		try {
			setSpreadData(new ArrayList(0), 0);

			reflesh();
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * テーブル更新 （ロックアウトリスト習得）
	 * 
	 * @throws IOException
	 */
	protected void reflesh() throws IOException {
		addSendValues("flag", "find");

		// 送信
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return;
		}

		// サーブレットから送られてきた結果を配列にセット
		List<LOCK_OUT_TBL> list = super.getResultDtoList(LOCK_OUT_TBL.class);

		// 指定会社のロックアウト情報がないとリスト構成なし
		if (list.size() == 0) {
			// ボタンのイベントを消す
			panel.btnSettle.setEnabled(false);
			setSpreadData(new ArrayList(0), 0);
			return;
		}

		// ユーザ認証マスタからロックアウト開放分数を習得
		addSendValues("flag", "findByKaiCode");

		if (!request(TARGET_SERVLET_AUTH)) {
			errorHandler(panel);
			return;
		}

		USR_AUTH_MST usrAuthDto = (USR_AUTH_MST) getResultDto(USR_AUTH_MST.class);
		int lockoutTerm;

		// 認証情報がないとロックアウトリストなし
		if (usrAuthDto == null) {
			return;
		}
		lockoutTerm = usrAuthDto.getLOCK_OUT_RELEASE_TIME();
		setSpreadData(list, lockoutTerm);
	}

	/**
	 * データをテーブルに設定する。
	 * 
	 * @param list ロックアウトリスト
	 * @param lockoutTerm 開放分数
	 */
	public void setSpreadData(List<LOCK_OUT_TBL> list, int lockoutTerm) {

		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>(list.size());

		long sysDate = (Util.getCurrentDate().getTime()) / 60000;

		CellStyleModel defaultStyle = panel.lockTable.getDefaultCellStyle();

		// cellスタイル調整用インデックス
		int rowIndex = 0;

		for (LOCK_OUT_TBL record : list) {
			Vector<String> colum = new Vector<String>(4);

			// VECTORにインデックスを使うため、値を設定
			colum.add(0, "");

			// ユーザコード
			colum.add(1, record.getUSR_CODE());

			// ユーザ名
			colum.add(2, record.getUSR_NAME());

			// 失敗日付
			colum.add(3, DateUtil.toYMDHMSString(record.getFAIL_DATE()));

			// ０設定、情報なしは処理しない
			if (lockoutTerm != 0.0) {
				long failDate = (record.getFAIL_DATE().getTime()) / 60000;

				// ロックアウト開放分数が経過した場合。赤い文字で表現
				if (lockoutTerm < (sysDate - failDate)) {
					JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
					centerStyle.setForeground(Color.RED);
					centerStyle.setHorizontalAlignment(SwingConstants.CENTER);
					panel.lockTable.setCellStyle(new JCCellRange(rowIndex, 3, rowIndex, 3), centerStyle);

					JCCellStyle leftStyle = new JCCellStyle(defaultStyle);
					leftStyle.setForeground(Color.RED);
					leftStyle.setHorizontalAlignment(SwingConstants.LEFT);
					panel.lockTable.setCellStyle(new JCCellRange(rowIndex, 1, rowIndex, 2), leftStyle);
				}
			}
			cells.add(colum);
			rowIndex = rowIndex + 1;
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
	 * ロックアウト解除
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

			reflesh();

			showMessage(panel, "I00008");

		} catch (Exception a) {
			errorHandler(panel.getParentFrame(), a, "E00009");
		}
	}
}
