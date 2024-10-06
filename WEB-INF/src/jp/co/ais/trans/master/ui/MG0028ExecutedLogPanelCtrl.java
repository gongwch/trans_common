package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 実行ログ参照コントロール
 * 
 * @author roh
 */
public class MG0028ExecutedLogPanelCtrl extends TPanelCtrlBase {

	/** 日付基準ソート */
	private final int SORT_DATE = 1;

	/** ユーザコード基準ソード */
	private final int SORT_USR_CODE = 2;

	/** プログラム基準ソード */
	private final int SORT_PRG_CODE = 3;

	/** 処理サーブレット(プログラム） */
	protected static final String TARGET_SERVLET = "MG0028ExecutedLogServlet";

	/** パネル */
	protected MG0028ExecutedLogPanel panel;

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
	public MG0028ExecutedLogPanelCtrl() {
		panel = new MG0028ExecutedLogPanel(this);
		panel.fedStartDate.getCalendar().requestFocus();

		// 検索条件イベント登録
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				initEvent();
			}
		});

		// メッセージ登録
		initMessage();

		// テーブル初期構成
		setTableDs(new ArrayList(0));
	}

	/**
	 * 検索フィールドのイベントを登録
	 */
	protected void initEvent() {

		// 開始ユーザコードの設定
		final REFDialogCtrl startUserDialog = new REFDialogCtrl(panel.fedStartUser, panel.getParentFrame());
		startUserDialog.setTargetServlet("MG0020UserMasterServlet");
		startUserDialog.setTitleID(getWord("C02355"));
		startUserDialog.setColumnLabelIDs("C00589", "C00692", "C00693");
		startUserDialog.setShowsMsgOnError(false);
		startUserDialog.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "User");
				keys.put("endCode", panel.fedEndUser.getField().getText());
				return keys;
			}

		});

		// 終了ユーザコードの設定
		final REFDialogCtrl endUserDialog = new REFDialogCtrl(panel.fedEndUser, panel.getParentFrame());
		endUserDialog.setTargetServlet("MG0020UserMasterServlet");
		endUserDialog.setTitleID(getWord("C02355"));
		endUserDialog.setColumnLabelIDs("C00589", "C00692", "C00693");
		endUserDialog.setShowsMsgOnError(false);
		endUserDialog.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("kind", "User");
				keys.put("beginCode", panel.fedStartUser.getField().getText());
				return keys;
			}

		});

		// 開始ユーザコードのローストフォーカス設定
		panel.fedStartUser.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				startUserDialog.refreshName();
				if (!startUserDialog.isCodeValid()) {
					showMessage(panel, "W00081");
					return false;
				}
				return true;
			}

		});

		// 終了ユーザコードのローストフォーカス設定
		panel.fedEndUser.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				endUserDialog.refreshName();
				if (!endUserDialog.isCodeValid()) {
					showMessage(panel, "W00081");
					return false;
				}
				return true;
			}

		});

		// 開始プログラムコードの設定
		final REFDialogCtrl startProgramDialog = new REFDialogCtrl(panel.fedStartProgram, panel.getParentFrame());
		startProgramDialog.setColumnLabels("C00818", "C00820", "C00821");
		startProgramDialog.setTargetServlet("MG0240ProgramMasterServlet");
		startProgramDialog.setTitleID(getWord("C02147"));
		startProgramDialog.setShowsMsgOnError(false);
		startProgramDialog.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("endCode", panel.fedEndProgram.getField().getText());
				keys.put("kind", "Program");
				return keys;
			}

		});

		// 開始プログラムコードのローストフォーカス設定
		panel.fedStartProgram.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				startProgramDialog.refreshName();
				if (!startProgramDialog.isCodeValid()) {
					showMessage(panel, "W00081");
					return false;
				}
				return true;
			}

		});

		// 終了プログラムコードの設定
		final REFDialogCtrl endProgramDialog = new REFDialogCtrl(panel.fedEndProgram, panel.getParentFrame());
		endProgramDialog.setColumnLabels("C00818", "C00820", "C00821");
		endProgramDialog.setTargetServlet("MG0240ProgramMasterServlet");
		endProgramDialog.setTitleID(getWord("C02147"));
		endProgramDialog.setShowsMsgOnError(false);
		endProgramDialog.setREFListener(new REFAdapter() {

			public Map primaryKeysAppending() {
				Map keys = new HashMap();
				keys.put("kaiCode", getLoginUserCompanyCode());
				keys.put("beginCode", panel.fedStartProgram.getField().getText());
				keys.put("kind", "Program");
				return keys;
			}

		});

		// 終了プログラムコードのローストフォーカス設定
		panel.fedEndProgram.setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				endProgramDialog.refreshName();
				if (!endProgramDialog.isCodeValid()) {
					showMessage(panel, "W00081");
					return false;
				}
				return true;
			}

		});
	}

	/**
	 * メッセージ設定
	 */
	protected void initMessage() {
		// ラベル名設定
		panel.fedStartDate.getLabel().setText(getWord("C00347") + getWord("C02909"));
		panel.pnlUser.setLangMessageID(getWord("C00347") + getWord("C00528"));
		panel.pnlProgram.setLangMessageID(getWord("C00347") + getWord("C00477"));

		// コンボボックスの構成
		panel.sortCombo.getComboBox().addTextValueItem(SORT_DATE, getWord("C00218") + getWord("C02906"));
		panel.sortCombo.getComboBox().addTextValueItem(SORT_USR_CODE, getWord("C00528"));
		panel.sortCombo.getComboBox().addTextValueItem(SORT_PRG_CODE, getWord("C00477"));
	}

	/**
	 * エクセル出力
	 */
	protected void exportToExcel() {
		panel.fedStartDate.requestFocus();
		// 検索条件の検証
		if (!checkPanel()) {
			return;
		}

		// サブレットに伝送するパラメータ
		Map<String, String> map = new HashMap<String, String>();
		map.put("flag", "report");
		map.put("startDate", panel.fedStartDate.getCalendar().getText());
		map.put("endDate", panel.fedEndDate.getCalendar().getText());
		map.put("startUser", panel.fedStartUser.getValue());
		map.put("endUser", panel.fedEndUser.getValue());
		map.put("startPrg", panel.fedStartProgram.getValue());
		map.put("endPrg", panel.fedEndProgram.getValue());
		map.put("sort", String.valueOf(panel.sortCombo.getComboBox().getSelectedItemValue()));
		map.put("loginFlag", BooleanUtil.toString(panel.checkProgram.isSelected()));

		// エクセルで表示する
		this.download(panel, TARGET_SERVLET, map);
	}

	/**
	 * 検索条件によるログ情報をスプレッドシートを表示
	 */
	protected void searchLog() {

		try {
			// 検索条件の検証
			if (!checkPanel()) {
				return;
			}

			// サブレットに伝送するパラメータ
			addSendValues("flag", "findLog");
			addSendValues("startDate", panel.fedStartDate.getCalendar().getText());
			addSendValues("endDate", panel.fedEndDate.getCalendar().getText());
			addSendValues("startUser", panel.fedStartUser.getValue());
			addSendValues("endUser", panel.fedEndUser.getValue());
			addSendValues("startPrg", panel.fedStartProgram.getValue());
			addSendValues("endPrg", panel.fedEndProgram.getValue());
			addSendValues("sort", String.valueOf(panel.sortCombo.getComboBox().getSelectedItemValue()));
			addSendValues("loginFlag", BooleanUtil.toString(panel.checkProgram.isSelected()));

			if (!request(TARGET_SERVLET)) {
				setTableDs(new ArrayList(0));
				errorHandler(panel);
				return;
			}

			// ログ一覧習得
			List<ExecutedLog> dtoList = getResultDtoList(ExecutedLog.class);

			// データソース設定
			setTableDs(dtoList);

			// データなしの場合、メッセージを表示する
			if (dtoList.isEmpty()) {
				panel.fedStartDate.requestFocus();
				showMessage(panel, "W00100");
			}

		} catch (Exception e) {
			errorHandler(panel.getParentFrame(), e, "E00009");
		}
	}

	/**
	 * テーブルにデータソース構成
	 * 
	 * @param dtoList ログリスト
	 */
	protected void setTableDs(List<ExecutedLog> dtoList) {
		// サーブレットから送られてきた結果を配列にセット
		Vector<Vector> cells = new Vector<Vector>(dtoList.size());

		for (ExecutedLog record : dtoList) {
			Vector<String> colum = new Vector<String>(7);

			colum.add(0, DateUtil.toYMDHMSString(record.getEXCUTED_DATE())); // 実行日付
			colum.add(1, record.getUSR_CODE()); // ユーザコード
			colum.add(2, record.getUSR_NAME()); // ユーザ名
			colum.add(3, record.getIP_ADDRESS()); // IPアドレス
			colum.add(4, record.getPROGRAM_CODE()); // プログラムコード
			colum.add(5, record.getPROGRAM_NAME()); // プログラム名称
			colum.add(6, record.getSTATE()); // 状態

			cells.add(colum);
		}

		// SSデータの構築
		JCVectorDataSource ds = new JCVectorDataSource();

		// セルの挿入
		ds.setCells(cells);

		// カラムの数の指定
		ds.setNumColumns(7);
		ds.setNumRows(cells.size());

		// データセット
		panel.tblLog.setDataSource(ds);
	}

	/**
	 * 入力条件を検証する。
	 * 
	 * @return boolean 入力欄チェック
	 */
	protected boolean checkPanel() {

		Date startDate = panel.fedStartDate.getValue();
		Date endDate = panel.fedEndDate.getValue();

		// 検索開始時間と終了時間の比較
		if (!Util.isSmallerThenByYMD(startDate, endDate)) {
			showMessage(panel.getParentFrame(), "W00035");
			panel.fedStartDate.requestTextFocus();
			return false;
		}

		// 検索ユーザコードの比較(両方入力された場合のみ）
		if (!panel.fedStartUser.getValue().equals("") && !panel.fedEndUser.getValue().equals("")) {
			if (panel.fedStartUser.getValue().compareTo(panel.fedEndUser.getValue()) > 0) {
				showMessage(panel.getParentFrame(), "W00036");
				panel.fedStartUser.requestTextFocus();
				return false;
			}
		}

		// 検索プログラムコードの比較(両方入力された場合のみ）
		if (!panel.fedStartProgram.getValue().equals("") && !panel.fedEndProgram.getValue().equals("")) {
			if (panel.fedStartProgram.getValue().compareTo(panel.fedEndProgram.getValue()) > 0) {
				showMessage(panel.getParentFrame(), "W00036");
				panel.fedStartProgram.requestTextFocus();
				return false;
			}
		}

		// 処理の続行を問う
		if (!showConfirmMessage(panel.getParentFrame(), "Q00011")) {
			return false;
		}

		return true;
	}
}
