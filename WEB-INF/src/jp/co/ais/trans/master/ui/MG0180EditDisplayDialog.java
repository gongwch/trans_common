package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * @author wangjing
 */
public class MG0180EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	protected MG0180EditDisplayDialogCtrl ctrl;

	/** 確定されたかどうか */
	protected boolean isSettle = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param modal モーダルダイアログ可否(true: モーダルモード)
	 * @param ctrl コントロールクラス
	 * @param titleid
	 */
	MG0180EditDisplayDialog(Frame parent, boolean modal, MG0180EditDisplayDialogCtrl ctrl, String titleid) {
		// 親フレームを設定
		super(parent, modal);
		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);
		// 画面の初期化
		initComponents();
		setLangMessageID(titleid);
		// 画面の設定
		setSize(600, 350);
		super.initDialog();
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlDetail = new TPanel();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		ctrlManagementCode = new TLabelField();
		ctrlManagementAbbreviationName = new TLabelField();
		ctrlManagementNameForSearch = createForSearchCtrl();
		ctrlManagementName = new TLabelField();
		pnlButton = new TPanel();
		btnSettle = new TButton();
		btnReturn = new TButton();

		getContentPane().setLayout(new GridBagLayout());

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(600, 185));
		pnlDetail.setMinimumSize(new Dimension(600, 185));
		pnlDetail.setPreferredSize(new Dimension(600, 185));
		dtBeginDate.setLabelSize(110);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(5);

		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(dtBeginDate, gridBagConstraints);

		dtEndDate.setLabelSize(110);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(6);

		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 30, 0);
		pnlDetail.add(dtEndDate, gridBagConstraints);

		ctrlManagementCode.setFieldSize(120);
		ctrlManagementCode.setLabelHAlignment(-1);
		ctrlManagementCode.setLabelSize(110);
		ctrlManagementCode.setLangMessageID("C00814");
		ctrlManagementCode.getField().setAllowedSpace(false);
		ctrlManagementCode.setMaxLength(10);
		ctrlManagementCode.setTabControlNo(1);
		ctrlManagementCode.setImeMode(false);
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlManagementCode, gridBagConstraints);

		ctrlManagementAbbreviationName.setFieldSize(230);
		ctrlManagementAbbreviationName.setLabelHAlignment(-1);
		ctrlManagementAbbreviationName.setLabelSize(110);
		ctrlManagementAbbreviationName.setLangMessageID("C00816");
		ctrlManagementAbbreviationName.setMaxLength(20);
		ctrlManagementAbbreviationName.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlManagementAbbreviationName, gridBagConstraints);

		ctrlManagementNameForSearch.setFieldSize(450);
		ctrlManagementNameForSearch.setLabelHAlignment(-1);
		ctrlManagementNameForSearch.setLabelSize(110);
		ctrlManagementNameForSearch.setLangMessageID("C00817");
		ctrlManagementNameForSearch.setMaxLength(40);
		ctrlManagementNameForSearch.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlManagementNameForSearch, gridBagConstraints);

		ctrlManagementName.setFieldSize(450);
		ctrlManagementName.setLabelHAlignment(-1);
		ctrlManagementName.setLabelSize(110);
		ctrlManagementName.setLangMessageID("C00815");
		ctrlManagementName.setMaxLength(40);
		ctrlManagementName.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlManagementName, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		getContentPane().add(pnlDetail, gridBagConstraints);

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(230, 40));
		pnlButton.setMinimumSize(new Dimension(230, 40));
		pnlButton.setPreferredSize(new Dimension(230, 40));
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints.anchor = GridBagConstraints.SOUTH;
		gridBagConstraints.insets = new Insets(15, 0, 5, 10);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnSettleActionPerformed();
			}
		});

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(8);
		btnReturn.setForClose(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(15, 0, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);
		btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnReturnActionPerformed();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 350, 0, 10);
		getContentPane().add(pnlButton, gridBagConstraints);

		pack();
	}

	/**
	 * 検索名称フィールド作成
	 * 
	 * @return 検索名称フィールド
	 */
	protected TLabelField createForSearchCtrl() {
		return new TLabelField();
	}

	/**
	 * 確定ボタン押下の処理
	 */
	protected void btnSettleActionPerformed() {
		// 開ける種別の設定
		isSettle = true;
		// 画面を閉める
		ctrl.disposeDialog();
	}

	/**
	 * 戻るボタン押下の処理
	 */
	protected void btnReturnActionPerformed() {
		// 開ける種別の設定
		isSettle = false;
		// 画面を閉める
		ctrl.disposeDialog();
	}

	TButton btnReturn;

	TButton btnSettle;

	TLabelField ctrlManagementAbbreviationName;

	TLabelField ctrlManagementCode;

	TLabelField ctrlManagementName;

	TLabelField ctrlManagementNameForSearch;

	TLabelPopupCalendar dtBeginDate;

	TLabelPopupCalendar dtEndDate;

	TPanel pnlButton;

	TPanel pnlDetail;

}
