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
public class MG0370SlipUnLockPanelCtrl extends TPanelCtrlBase {

	/** 処理サーブレット */
	private static final String TARGET_SERVLET = "MG0370SlipUnLockServlet";

	/** ユーザリスト */
	private List<USR_MST> userList;

	/** システム区分リスト */
	private List<SYS_MST> systemList;

	/** パネル */
	private MG0370SlipUnLockPanel panel;

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
	@Override
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * コンストラクタ
	 */
	public MG0370SlipUnLockPanelCtrl() {
		panel = new MG0370SlipUnLockPanel(this);

		try {
			initList();
			setSpreadData(new ArrayList<SlipUnlockObject>(0));

			// 画面更新
			reflesh();
		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00010");
		}
	}

	/**
	 * ユーザ名称、システム名称の取得
	 * 
	 * @throws IOException
	 */
	private void initList() throws IOException {
		addSendValues("flag", "getUser");
		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return;
		}
		userList = (List<USR_MST>) super.getResultObject();

		addSendValues("flag", "getSystem");

		if (!request(TARGET_SERVLET)) {
			errorHandler(panel);
			return;
		}
		systemList = (List<SYS_MST>) super.getResultObject();
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
		List<SlipUnlockObject> list = (List<SlipUnlockObject>) super.getResultObject();

		// 指定会社のログイン情報がないとリストを構成しない
		if (list == null || list.size() == 0) {
			// ボタンイベントを消す
			panel.btnSettle.setEnabled(false);
			setSpreadData(new ArrayList<SlipUnlockObject>(0));
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
	private void setSpreadData(List<SlipUnlockObject> list) {
		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>(list.size());

		for (SlipUnlockObject record : list) {
			Vector<Object> colum = new Vector<Object>(9);

			// VECTORにインデックスを使うため、値を設定
			colum.add(0, "");

			// サブシステム名
			colum.add(1, getSystemName(record.getSubSystemCode()));

			// 伝票番号
			colum.add(2, record.getSilpNo());

			// 伝票区分
			colum.add(3, record.getSilpName());

			// 伝票日付
			colum.add(4, DateUtil.toYMDHMString(record.getSilpDate()));

			// 伝票摘要
			colum.add(5, record.getSilpTek());

			// 排他ユーザコード
			colum.add(6, record.getUserCode());

			// 排他ユーザ名称
			colum.add(7, getUserName(record.getUserCode()));

			// 排他実行日
			colum.add(8, DateUtil.toYMDHMString(record.getExclusiveDate()));

			// データ
			colum.add(9, record);

			cells.add(colum);
		}

		// SSデータの構築
		ds = new JCVectorDataSource();

		// セルの挿入
		ds.setCells(cells);

		// カラムの数の指定
		ds.setNumColumns(9);
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
			List<SlipUnlockObject> list = new LinkedList<SlipUnlockObject>();

			// 画面に選択されたデータ
			for (int nomRow = 0; nomRow < ds.getNumRows(); nomRow++) {
				boolean status = ((TCheckBox) this.panel.lockTable.getComponent(nomRow, 0)).isSelected();

				if (status) {
					SlipUnlockObject slip = new SlipUnlockObject();
					slip = (SlipUnlockObject) ds.getTableDataItem(nomRow, 9);
					slip.setSilpDate(slip.getSilpDate());
					// チェックボックスが選択されている場合
					list.add(slip);
				}
			}

			// 一つ以上選択
			if (list.size() == 0) {
				showMessage(panel, "W00195");
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
	 * サブシステム区分単語取得
	 * 
	 * @param kbn
	 * @return サブシステム区分単語
	 */
	private String getSystemName(int kbn) {
		for (SYS_MST system : systemList) {
			String sysKbn = "";
			if (kbn == SYS_MST.AP_HDR || kbn == SYS_MST.AP_PTN) {
				sysKbn = SYS_MST.AP;
			}
			if (kbn == SYS_MST.AR_HDR || kbn == SYS_MST.AR_PTN) {
				sysKbn = SYS_MST.AR;
			}
			if (kbn == SYS_MST.GL_HDR || kbn == SYS_MST.GL_PTN) {
				sysKbn = SYS_MST.GL;
			}
			if (kbn == SYS_MST.LM_HDR) {
				sysKbn = SYS_MST.LM;
			}
			if (system.getSYS_KBN().equals(sysKbn)) {
				return system.getSYS_KBN_NAME();
			}
		}
		return "";
	}

	/**
	 * カラム構成用メソッド
	 * 
	 * @param word1
	 * @param word2
	 * @return ワード
	 */
	protected String getLinkedWord(String word1, String word2) {
		String word = getWord(word1) + getWord(word2);
		return word;
	}
}
