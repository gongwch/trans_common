package jp.co.ais.trans2.common.model.ui.slip;

import static jp.co.ais.trans2.define.SlipState.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 伝票検索コントロール
 */
public class TSlipSearchCtrl extends TController {

	/** 取消 */
	public static int CANCEL_OPTION = 0;

	/** 確定 */
	public static int OK_OPTION = 1;

	/** ステート */
	protected int option = CANCEL_OPTION;

	/** 一覧ダイアログ */
	protected TSlipSearchDialog dialog;

	/** 伝票種別 */
	protected String[] slipTypes = new String[0];

	/** データ区分 */
	protected String[] dataKinds = new String[0];

	/** true:時、ユーザのみフラグ無効 */
	protected static boolean notUseSelfOnly = ClientConfig.isFlagOn("trans.slip.copy.notuse.selfonly");

	/** true:時、参照権限無効 */
	protected static boolean notUsePermission = ClientConfig.isFlagOn("trans.slip.copy.notuse.permission");

	/** 最大複写検索対象件数 */
	protected static int maxHeaderCount = Util.avoidNullAsInt(ClientConfig.getProperty("trans.slip.copy.max.header.count"));

	/** 編集か複写か(true:編集) */
	protected boolean isModify = true;

	/** 他シスを含めるかどうか */
	protected boolean includeOtherSystem = false;

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
	 * @param mainView 元コンポーネント
	 */
	public TSlipSearchCtrl(TPanel mainView) {
		dialog = createDialog(mainView);

		// イベント登録
		addEvent();

		// ダイアログ初期化
		initDialogView();
	}

	/**
	 * 一覧ダイアログ生成
	 * 
	 * @param mainView 元コンポーネント
	 * @return 一覧ダイアログ
	 */
	protected TSlipSearchDialog createDialog(TPanel mainView) {
		return new TSlipSearchDialog(mainView);
	}

	/**
	 * 複写に切替
	 */
	public void switchCopyMode() {

		this.isModify = false;

		// 複写のみ
		dialog.cmbUpdDivision.addTextValueItem(APPROVE, getWord(APPROVE.getName()));
		dialog.cmbUpdDivision.addTextValueItem(UPDATE, getWord(UPDATE.getName()));
		dialog.ctrlCopyMode.setVisible(true);
	}

	/**
	 * 複写モード選択パネルを非表示
	 */
	public void switchNonVisibleCopyMode() {
		dialog.ctrlCopyMode.setVisible(false);
	}

	/**
	 * 検索ダイアログを表示
	 */
	protected void addEvent() {
		// 各条件(テキスト)
		dialog.txtSlipNo.addKeyListener(txtListener);
		dialog.txtSlipDate.addKeyListener(txtListener);
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
	 * ダイアログの初期設定
	 */
	protected void initDialogView() {

		// 更新区分ダイアログ

		// 現場承認を使うか
		boolean useField = getCompany().getAccountConfig().isUseFieldApprove();

		// 更新区分リスト
		List stateList = new LinkedList<TComboBox.TTextValue>();

		// 選択なし
		stateList.add(new TComboBox.TTextValue("", null));

		// 登録
		stateList.add(new TComboBox.TTextValue(getWord(ENTRY.getName()), ENTRY));

		// 現場否認
		if (useField) {
			stateList.add(new TComboBox.TTextValue(getWord(FIELD_DENY.getName()), FIELD_DENY));
		}

		// 否認
		stateList.add(new TComboBox.TTextValue(getWord(DENY.getName()), DENY));

		// 現場承認
		if (useField) {
			stateList.add(new TComboBox.TTextValue(getWord(FIELD_APPROVE.getName()), FIELD_APPROVE));
		}

		dialog.cmbUpdDivision.addItemList(stateList);
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
	 * 伝票検索
	 */
	protected void searchList() {
		try {

			// 入力チェック
			if (!checkInput()) {
				return;
			}

			dialog.tbl.removeRow();

			// 伝票検索
			List<SWK_HDR> list = (List<SWK_HDR>) request(SlipManager.class, "getHeader", getCondition());

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

		} catch (TWarningException ex) {
			showMessage(ex.getMessage());

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
		if (!dialog.txtSlipDate.isEmpty()) {
			String txtDate = dialog.txtSlipDate.getText().toString();
			int length = txtDate.length();

			switch (length) {
				case 6:
					String txtYMDate = txtDate.substring(0, 4) + "/" + txtDate.substring(4, 6);
					if (DateUtil.isYMDate(txtYMDate)) {
						dialog.txtSlipDate.setText(txtYMDate);
					}
					break;
				case 8:
					String txtYMDDate = txtDate.substring(0, 4) + "/" + txtDate.substring(4, 6) + "/"
						+ txtDate.substring(6, 8);
					if (DateUtil.isYMDDate(txtYMDDate)) {
						dialog.txtSlipDate.setText(txtYMDDate);
					}
					break;
			}
		}

		if (!dialog.txtSlipDate.isEmpty() && !dialog.txtSlipDate.isDateFormat()) {
			showMessage(dialog, "I00113");// 正しい日付規格を入力してください。
			dialog.txtSlipDate.requestFocus();
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
		condition.setSlipDateFrom(dialog.txtSlipDate.getStartDate()); // 伝票日付From
		condition.setSlipDateTo(dialog.txtSlipDate.getEndDate()); // 伝票日付To
		condition.setDepartmentCodeLike(dialog.txtDepCode.getText()); // 部門コード
		condition.setDepartmentNamesLike(dialog.txtDepNames.getText()); // 部門略称
		condition.setRemarksLike(dialog.txtSlipRemarks.getText()); // 摘要
		condition.addSlipType(slipTypes); // 伝票種別
		condition.addDataKind(dataKinds); // データ区分
		condition.setIncludeOtherSystem(includeOtherSystem); // 他シス含める

		condition.setGroupAccountOff(); // 付替伝票は除く
		condition.setIfType(JornalIfType.ENTRY); // 仕訳インターフェース区分 0:登録

		condition.setOrder(" SWK_DEN_NO DESC "); // 伝票番号降順

		if (!notUsePermission) {
			// 参照権限が有効の場合のみ、制限

			// 複写の場合、依頼者のみ(NULL可能)
			if (!isModify && !notUseSelfOnly) {
				condition.setRequestEmpCode(getUser().getEmployee().getCode());
				condition.setRequestEmpCodeIncledNull(true);
			}

			if (!isModify) {
				// 複写時に最大ヘッダー取得件数制限
				condition.setMaxHeaderCount(maxHeaderCount);
			}

			// 以外、依頼者、依頼部門で制限
			if (getUser().getSlipRole() == SlipRole.SELF) {
				condition.setRequestEmpCode(getUser().getEmployee().getCode());
				condition.setRequestEmpCodeIncledNull(false);

			} else if (getUser().getSlipRole() == SlipRole.DEPARTMENT) {
				condition.setRequestDeptCode(getUser().getDepartment().getCode());
				condition.setRequestDeptCodeIncledNull(false);
			}
		}

		// 修正の場合は、排他されてないデータが対象
		if (isModify) {
			condition.setNotLocked();
		}

		// 更新区分
		SlipState state = (SlipState) dialog.cmbUpdDivision.getSelectedItemValue();
		if (state != null) {
			condition.addSlipState(state);

		} else if (isModify) {
			condition.addSlipState(ENTRY, FIELD_DENY, DENY);
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
		List<Object> ssList = new ArrayList<Object>(5);
		ssList.add(hdr);
		ssList.add(hdr.getSWK_DEN_NO());
		ssList.add(DateUtil.toYMDString(hdr.getSWK_DEN_DATE()));
		ssList.add(hdr.getSWK_DEP_CODE());
		ssList.add(hdr.getSWK_DEP_NAME_S());
		ssList.add(hdr.getSWK_TEK());
		ssList.add(getWord(getSlipStateName(hdr.getSWK_UPD_KBN())));

		return ssList;
	}

	/**
	 * 選択データの取得
	 * 
	 * @return ヘッダ
	 */
	public SWK_HDR getSelectedRow() {

		return (SWK_HDR) dialog.tbl.getSelectedRowValueAt(TSlipSearchDialog.SC.bean);
	}

	/**
	 * 赤伝モードが選択されたかどうか
	 * 
	 * @return true:選択された
	 */
	public boolean isSelectedCancel() {
		return dialog.ctrlCopyMode.isSelected(1);
	}

	/**
	 * 逆伝モードが選択されたかどうか
	 * 
	 * @return true:選択された
	 */
	public boolean isSelectedReverse() {
		return dialog.ctrlCopyMode.isSelected(2);
	}

	/**
	 * 他シスを含めるかどうか
	 * 
	 * @param includeOtherSystem true:含める
	 */
	public void setIncludeOtherSystem(boolean includeOtherSystem) {
		this.includeOtherSystem = includeOtherSystem;
	}
}
