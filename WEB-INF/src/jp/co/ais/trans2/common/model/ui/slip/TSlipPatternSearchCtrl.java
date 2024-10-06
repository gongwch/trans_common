package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * パターン検索コントロール
 */
public class TSlipPatternSearchCtrl extends TController {

	/** 取消 */
	public static int CANCEL_OPTION = 0;

	/** 確定 */
	public static int OK_OPTION = 1;

	/** ステート */
	protected int option = CANCEL_OPTION;

	/** パターン一覧ダイアログ */
	protected TSlipPatternSearchDialog dialog;

	/** 伝票種別 */
	protected String[] slipTypes = new String[0];

	/** データ区分 */
	protected String[] dataKinds = new String[0];

	/** true:時、ユーザのみフラグ無効 */
	protected static boolean notUseSelfOnly = ClientConfig.isFlagOn("trans.slip.pattern.notuse.selfonly");

	/** true:時、参照権限無効 */
	protected static boolean notUsePermission = ClientConfig.isFlagOn("trans.slip.pattern.notuse.permission");

	/** 自ユーザのみかどうか */
	protected boolean selfOnly = false;

	/** 排他中データを含むかどうか */
	protected boolean includeLocked = false;

	/** 条件テキストリスナ */
	protected KeyListener txtListener = new KeyAdapter() {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				e.consume();

				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				searchList();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		}
	};

	/**
	 * コンストラクタ.
	 * 
	 * @param mainView
	 */
	public TSlipPatternSearchCtrl(TPanel mainView) {
		dialog = createDialog(mainView);

		// イベント登録
		addEvent();
	}

	/**
	 * 一覧ダイアログ生成
	 * 
	 * @param mainView 元コンポーネント
	 * @return 一覧ダイアログ
	 */
	protected TSlipPatternSearchDialog createDialog(TPanel mainView) {
		dialog = new TSlipPatternSearchDialog(mainView);
		return dialog;
	}

	/**
	 * 自身のデータのみを検索
	 */
	public void switchSelfOnly() {
		this.selfOnly = true;
	}

	/**
	 * 排他データを含むかどうか
	 * 
	 * @param includeLocked true:含む
	 */
	public void setIncludeLocked(boolean includeLocked) {
		this.includeLocked = includeLocked;
	}

	/**
	 * イベント登録
	 */
	protected void addEvent() {

		// 各条件(テキスト)
		dialog.txtSlipNo.addKeyListener(txtListener);
		dialog.txtEntryDate.addKeyListener(txtListener);
		dialog.txtDepCode.addKeyListener(txtListener);
		dialog.txtDepNames.addKeyListener(txtListener);
		dialog.txtSlipRemarks.addKeyListener(txtListener);

		// 検索ボタン
		dialog.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				searchList();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 確定ボタン
		dialog.btnEnter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				option = OK_OPTION;

				dialog.setVisible(false);
			}
		});

		// 取消ボタン
		dialog.btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setVisible(false);
			}
		});
	}

	/**
	 * 伝票種別の設定
	 * 
	 * @param slipType 伝票種別
	 */
	public void setSlipType(String... slipType) {
		this.slipTypes = slipType;
	}

	/**
	 * データ区分の設定
	 * 
	 * @param dataType データ区分
	 */
	public void setDataKind(String... dataType) {
		this.dataKinds = dataType;
	}

	/**
	 * 表示
	 * 
	 * @return 選択結果(XX_OPTION)
	 */
	public int show() {
		dialog.txtSlipNo.requestFocus();
		dialog.setVisible(true);

		return option;
	}

	/**
	 * パターン検索
	 */
	protected void searchList() {
		try {

			// 入力チェック
			if (!checkInput()) {
				return;
			}

			dialog.tbl.removeRow();

			// 伝票検索
			List<SWK_HDR> list = (List<SWK_HDR>) request(SlipManager.class, "getPatternHeader", getCondition());

			// 結果を一覧に表示
			for (SWK_HDR hdr : list) {
				dialog.tbl.addRow(getRow(hdr));
			}

			dialog.btnEnter.setEnabled(!list.isEmpty());

			if (list.isEmpty()) {
				showMessage(dialog, "I00022");
				dialog.txtSlipNo.requestFocus();

			} else {
				dialog.tbl.setRowSelection(0);
				dialog.tbl.requestFocus();
			}

		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * 入力チェック
	 * 
	 * @return false:NG
	 */
	protected boolean checkInput() {
		if (!dialog.txtEntryDate.isEmpty()) {
			String txtDate = dialog.txtEntryDate.getText().toString();
			int length = txtDate.length();

			switch (length) {
				case 6:
					String txtYMDate = txtDate.substring(0, 4) + "/" + txtDate.substring(4, 6);
					if (DateUtil.isYMDate(txtYMDate)) {
						dialog.txtEntryDate.setText(txtYMDate);
					}
					break;
				case 8:
					String txtYMDDate = txtDate.substring(0, 4) + "/" + txtDate.substring(4, 6) + "/" + txtDate.substring(6, 8);
					if (DateUtil.isYMDDate(txtYMDDate)) {
						dialog.txtEntryDate.setText(txtYMDDate);
					}
					break;
			}
		}

		if (!dialog.txtEntryDate.isEmpty() && !dialog.txtEntryDate.isDateFormat()) {
			showMessage(dialog, "I00113");// 正しい日付規格を入力してください。
			dialog.txtEntryDate.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * 検索条件
	 * 
	 * @return 検索条件
	 */
	protected SlipCondition getCondition() {
		SlipCondition condition = new SlipCondition();
		condition.setCompanyCode(getCompanyCode()); // 会社コード
		condition.setSlipNoLike(dialog.txtSlipNo.getText()); // 伝票番号
		condition.setEntryDateFrom(dialog.txtEntryDate.getStartDate()); // 登録日付From
		condition.setEntryDateTo(dialog.txtEntryDate.getEndDate()); // 登録日付To
		condition.setDepartmentCodeLike(dialog.txtDepCode.getText()); // 部門コード
		condition.setDepartmentNamesLike(dialog.txtDepNames.getText()); // 部門略称
		condition.setRemarksLike(dialog.txtSlipRemarks.getText()); // 摘要
		condition.addSlipType(slipTypes); // 伝票種別
		condition.addDataKind(dataKinds); // データ区分
		condition.setOrder(" SWK_DEN_NO "); // パターン番号の昇順

		if (!notUsePermission) {
			// 参照権限が有効の場合のみ、制限

			// 仕訳辞書の場合、依頼者のみ(NULL可能)
			if (selfOnly && !notUseSelfOnly) {
				condition.setRequestEmpCode(getUser().getEmployee().getCode());
				condition.setRequestEmpCodeIncledNull(true);
			}

			// 依頼者、依頼部門で制限
			if (getUser().getSlipRole() == SlipRole.SELF) {
				condition.setRequestEmpCode(getUser().getEmployee().getCode());
				condition.setRequestEmpCodeIncledNull(false);

			} else if (getUser().getSlipRole() == SlipRole.DEPARTMENT) {
				condition.setRequestDeptCode(getUser().getDepartment().getCode());
				condition.setRequestDeptCodeIncledNull(false);
			}
		}

		if (!includeLocked) {
			// 排他されてないデータが対象
			condition.setNotLocked();
		}

		return condition;
	}

	/**
	 * 一覧表示項目
	 * 
	 * @param hdr ヘッダ
	 * @return 一覧表示項目データ
	 */
	protected List<Object> getRow(SWK_HDR hdr) {
		List<Object> ssList = new ArrayList<Object>(4);
		ssList.add(hdr);
		ssList.add(hdr.getSWK_DEN_NO());

		if (hdr.getSWK_INP_DATE() != null) {
			ssList.add(DateUtil.toYMDString(hdr.getSWK_INP_DATE()));
		} else {
			ssList.add(DateUtil.toYMDString(hdr.getINP_DATE()));
		}
		ssList.add(hdr.getSWK_DEP_CODE());
		ssList.add(hdr.getSWK_DEP_NAME_S());
		ssList.add(hdr.getSWK_TEK());

		return ssList;
	}

	/**
	 * 選択データの取得
	 * 
	 * @return ヘッダ
	 */
	public SWK_HDR getSelectedRow() {

		return (SWK_HDR) dialog.tbl.getSelectedRowValueAt(TSlipPatternSearchDialog.SC.bean);
	}
}
